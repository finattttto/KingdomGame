package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.AssetManager.GameAssetManager;
import com.mygdx.game.Controller.PlayerController;
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

    public GameScreen(MyGdxGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera(1920, 1080);

        layers = new ParallaxLayer[3];
        layers[0] = new ParallaxLayer(GameAssetManager.getManager().get("background/background_layer1.png", Texture.class), 0.1f, true, false);
        layers[1] = new ParallaxLayer(GameAssetManager.getManager().get("background/background_layer2.png", Texture.class), 0.2f, true, false);
        layers[2] = new ParallaxLayer(GameAssetManager.getManager().get("background/background_layer3.png", Texture.class), 0.3f, true, false);

        for (ParallaxLayer layer : layers) {
            layer.setCamera(camera);
        }

        player = new Player(1920 / 2f - 32, 1080 / 2f - 400);
        playerController = new PlayerController(player);
        Gdx.input.setInputProcessor(playerController);

        this.game.setMusic(GameAssetManager.getManager().get("music/Ambience.mp3", Music.class));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.position.x -= player.getSpeed() * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.position.x += player.getSpeed() * delta;
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (ParallaxLayer layer : layers) {
            layer.render(batch);
        }

        player.render(batch, camera.position.x, camera.position.y);
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
        for (ParallaxLayer layer : layers) {
            layer.dispose();
        }
    }
}

