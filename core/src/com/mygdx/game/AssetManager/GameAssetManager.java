package com.mygdx.game.AssetManager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GameAssetManager {

    static AssetManager manager;

    public static AssetManager getManager() {
        return manager;
    }

    public void init () {
        manager = new AssetManager();

        manager.load("background/background_layer1.png", Texture.class);
        manager.load("background/background_layer2.png", Texture.class);
        manager.load("background/background_layer3.png", Texture.class);

        manager.load("player/IDLE.png", Texture.class);
        manager.load("player/WALK.png", Texture.class);
        manager.load("player/ATTACK_1.png", Texture.class);
        manager.load("player/ATTACK_2.png", Texture.class);
        manager.load("player/ATTACK_3.png", Texture.class);
        manager.load("player/DEATH.png", Texture.class);
        manager.load("player/DEFEND.png", Texture.class);
        manager.load("player/HURT.png", Texture.class);
        manager.load("player/JUMP.png", Texture.class);
        manager.load("player/RUN.png", Texture.class);

        // sounds

        manager.load("sounds/player/Step_grass.mp3", Sound.class);
        manager.load("sounds/player/Sword_1.mp3", Sound.class);
        manager.load("sounds/player/Sword_2.mp3", Sound.class);
        manager.load("sounds/player/Hit_1.mp3", Sound.class);
        manager.load("sounds/player/Death.mp3", Sound.class);
        manager.load("sounds/player/Defend.mp3", Sound.class);

        manager.load("music/Ambience.mp3", Music.class);

        manager.finishLoading();
    }

    public void dispose () {
        manager.dispose();
    }
}