package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.AssetManager.GameAssetManager;
import com.mygdx.game.Screen.GameScreen;

public class MyGdxGame extends Game {

	private final GameAssetManager gameAssetManager = new GameAssetManager();

	private Music music;

	@Override
	public void create() {
		gameAssetManager.init();
		this.setScreen(new GameScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		gameAssetManager.dispose();
		screen.dispose();
		music.dispose();
	}

	public void restart() {

	}

	public void setMusic(Music music) {
		this.music = music;
		music.play();
		music.setVolume(0.6f);
		music.setLooping(true);
	}

	public void stopMusic() {
		music.stop();
	}

	public GameAssetManager getGameAssetManager() {
		return gameAssetManager;
	}
}
