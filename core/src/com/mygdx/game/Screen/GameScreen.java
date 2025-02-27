package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.AssetManager.GameAssetManager;
import com.mygdx.game.Controller.ChestController;
import com.mygdx.game.Controller.EnemyController;
import com.mygdx.game.Controller.PlayerController;
import com.mygdx.game.Controller.ScoreboardController;
import com.mygdx.game.Model.Player;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Utils.ParallaxLayer;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {

    private MyGdxGame game;

    private SpriteBatch batch;
    private ParallaxLayer[] layers;
    private Camera camera;
    private Player player;

    private PlayerController playerController;
    private ChestController chestController;
    private EnemyController enemyController;
    private ScoreboardController scoreboardController;

    private Integer points = 0;

    BitmapFont font;

    public GameScreen(MyGdxGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        font = new BitmapFont();
        font.getData().setScale(5f);

        camera = new OrthographicCamera(1920, 1080);

        layers = new ParallaxLayer[3];
        layers[0] = new ParallaxLayer(GameAssetManager.getManager().get("background/background_layer1.png", Texture.class), -0.6f, true, false);
        layers[1] = new ParallaxLayer(GameAssetManager.getManager().get("background/background_layer2.png", Texture.class), -0.8f, true, false);
        layers[2] = new ParallaxLayer(GameAssetManager.getManager().get("background/background_layer3.png", Texture.class), -1f, true, false);

        for (ParallaxLayer layer : layers) {
            layer.setCamera(camera);
        }


        player = new Player();
        playerController = new PlayerController(player);
        Gdx.input.setInputProcessor(playerController);

        chestController = new ChestController(camera, player);

        enemyController = new EnemyController(camera, player);

        scoreboardController = new ScoreboardController(camera, player, chestController);

        this.game.setMusic(GameAssetManager.getManager().get("music/Ambience.mp3", Music.class));
    }

    @Override
    public void render(float delta) {
        if(playerController.restartGame) {
            show();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.position.x +=  player.getSpeed() * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.position.x -= player.getSpeed() * delta;
        camera.update();

        // para garantir que o batch desenhe os elementos usando a matriz de projeção da câmera
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (ParallaxLayer layer : layers) {
            layer.render(batch);
        }

        chestController.render(batch);
        enemyController.render(batch);
        scoreboardController.render(batch, camera.position.x);

        player.render(batch, camera.position.x);

        if(player.getLife() == 0) {
            font.setColor(Color.RED);
            font.draw(batch, "Você morreu", camera.position.x - 170, camera.position.y + 130);
        }

        if(chestController.getTreasurePoints() >= 10) {
            player.setWinner(true);
            font.setColor(Color.GOLD);
            font.draw(batch, "Você venceu!", camera.position.x - 170, camera.position.y + 130);
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        chestController.dispose();
        enemyController.dispose();
        scoreboardController.dispose();
        for (ParallaxLayer layer : layers) {
            layer.dispose();
        }
        GameAssetManager.getManager().dispose();
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}

