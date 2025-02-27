package com.mygdx.game.Controller;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.AssetManager.GameAssetManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.mygdx.game.Model.Player;


public class ChestController {
    Camera camera;
    Player player; // ta aqui so pra contabilizar os pontos
    Texture texture;
    Texture coin;
    ParticleEffect effect;

    float chestWorldX = 0f;
    float chestYPosition = 0f;
    float scaledHeight;
    float scaledWidth;

    float scale = 0.15f;
    int treasurePoints = 0;
    int coinTime = 0;

    public ChestController(Camera camera, Player player) {
        this.texture = GameAssetManager.getManager().get("utils/treasure.png", Texture.class);
        this.coin = GameAssetManager.getManager().get("utils/coin.png", Texture.class);
        this.camera = camera;
        this.player = player;

        chestYPosition = -380;
        chestWorldX = camera.position.x - 50;

        scaledWidth = camera.viewportHeight * scale;
        scaledHeight = scaledWidth / ((float) texture.getWidth() / texture.getHeight());

        coin = GameAssetManager.getManager().get("utils/coin.png", Texture.class);

        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("gold/gold.p"), Gdx.files.internal("particle"));
        effect.setPosition(chestWorldX + 85f, chestYPosition + 120f);
        effect.start(); // start aqui, ou no render?
    }

    public void render(SpriteBatch batch) {
        if (camera == null) return;

        batch.draw(texture,
                chestWorldX,
                chestYPosition,
                scaledWidth,
                scaledHeight
        );

        if(isChestInCollisionArea()) {
            effect.draw(batch, Gdx.graphics.getDeltaTime());
            if(player.getCoinPoints() > 0) {
                treasurePoints += player.getCoinPoints();
                player.setCoinPoints(0);
                coinTime = 60;
            }
        } else {
            effect.reset();
        }

        if(coinTime > 0) {
            coinTime -= 2;
            batch.draw(coin,
                    chestWorldX + 50,
                    -200 + -coinTime,
                    (float) coin.getWidth() / 6,
                    (float) coin.getHeight() / 6);
        }
    }

    public boolean isChestInCollisionArea() {
            float chestX = chestWorldX;
            float cameraX = camera.position.x;
            return cameraX >= chestX - 100f && cameraX <= chestX + 250f;
    }

    public void dispose() {
        texture.dispose();
        effect.dispose();
    }

    public int getTreasurePoints() {
        return treasurePoints;
    }

    public void setTreasurePoints(int treasurePoints) {
        this.treasurePoints = treasurePoints;
    }
}
