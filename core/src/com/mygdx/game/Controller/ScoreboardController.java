package com.mygdx.game.Controller;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.AssetManager.GameAssetManager;
import com.mygdx.game.Model.Player;

public class ScoreboardController {
    Camera camera;
    Player player;
    ChestController chest;

    BitmapFont font;

    Texture playerHeader;
    Texture treasure;
    Texture coin;
    Texture heart;

    public ScoreboardController(Camera camera, Player player, ChestController chestController) {
        this.camera = camera;
        this.player = player;
        this.chest = chestController;

        playerHeader = GameAssetManager.getManager().get("player/HEADER.png", Texture.class);
        treasure = GameAssetManager.getManager().get("utils/treasure.png", Texture.class);
        coin = GameAssetManager.getManager().get("utils/coin.png", Texture.class);
        heart = GameAssetManager.getManager().get("utils/heart.png", Texture.class);

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(4f);
    }

    public void render(SpriteBatch batch, float cameraX) {
        batch.draw(playerHeader,
                cameraX - 930,
                450,
                playerHeader.getWidth() * 5,
                playerHeader.getHeight() * 5);

        int initial = 880;
        for(int i = 0; i < player.getLife(); i++) {
            initial -= 50;
            batch.draw(heart,
                    cameraX - initial,
                    450,
                    (float) heart.getWidth() / 4,
                    (float) heart.getHeight() / 4);
        }

        batch.draw(coin,
                cameraX - 925,
                370,
                (float) coin.getWidth() / 5,
                (float) coin.getHeight() / 5);
        font.draw(batch, String.valueOf(player.getCoinPoints()), cameraX - 830, 430);


        batch.draw(treasure,
                cameraX -920,
                310,
                treasure.getWidth() * 4,
                treasure.getHeight() * 4);
        font.draw(batch, String.valueOf(chest.getTreasurePoints()), cameraX - 830, 358);


    }

    public void dispose() {
        playerHeader.dispose();
    }
}
