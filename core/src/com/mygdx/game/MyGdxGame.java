package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Controller.PlayerController;
import com.mygdx.game.Model.Player;
import com.mygdx.game.Utils.ParallaxLayer;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;

	ParallaxLayer[] layers;
	Camera camera;

	Player player;
	PlayerController playerController;


	@Override
	public void create () {
		batch = new SpriteBatch();

		camera = new OrthographicCamera(1920, 1080);

		layers = new ParallaxLayer[3];
		layers[0] = new ParallaxLayer(new Texture("background_layer1.png"), 0.1f, true, false);
		layers[1] = new ParallaxLayer(new Texture("background_layer2.png"), 0.2f, true, false);
		layers[2] = new ParallaxLayer(new Texture("background_layer3.png"), 0.3f, true, false);

		for(ParallaxLayer layer : layers) {
			layer.setCamera(camera);
		}

		player = new Player("player.png", 1920 / 2f - 32, 1080 / 2f - 400);
		playerController = new PlayerController(player);
		Gdx.input.setInputProcessor(playerController);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		int speed = 1000;
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.position.x -= speed * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.position.x += speed * Gdx.graphics.getDeltaTime();
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
	public void dispose () {
		batch.dispose();
		player.dispose();
	}
}
