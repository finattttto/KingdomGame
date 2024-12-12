package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.mygdx.game.AssetManager.GameAssetManager;
import com.mygdx.game.Screen.GameScreen;

public class MyGdxGame extends Game {

	private final GameAssetManager gameAssetManager = new GameAssetManager();

	@Override
	public void create() {
		gameAssetManager.init();
		this.setScreen(new GameScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		gameAssetManager.dispose();
	}

	public GameAssetManager getGameAssetManager() {
		return gameAssetManager;
	}
}
