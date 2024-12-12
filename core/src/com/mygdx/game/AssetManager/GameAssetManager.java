package com.mygdx.game.AssetManager;

import com.badlogic.gdx.assets.AssetManager;
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

        manager.finishLoading();
    }

    public void dispose () {
        manager.dispose();
    }
}