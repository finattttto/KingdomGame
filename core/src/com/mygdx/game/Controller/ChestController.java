package com.mygdx.game.Controller;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.AssetManager.GameAssetManager;
import com.mygdx.game.Model.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;




public class ChestController {
    Camera camera;
    Texture texture;
    float chestWorldX = 0f;
    float viewportWidth;

    float scale = 0.15f;

    boolean isConflicted = false;
    boolean particleUsed = false;
    TextureAtlas particleAtlas; //<-declara um atlas com a partícula
    ParticleEffect effect;


    public ChestController(Camera camera) {
        this.texture = GameAssetManager.getManager().get("utils/treasure.png", Texture.class); // Carregue a textura do baú
        this.camera = camera;
        this.viewportWidth = camera.viewportWidth;

        //TextureAtlas particleAtlas = new TextureAtlas(Gdx.files.internal("particle/particleAtlas.atlas"));
        effect = new ParticleEffect();
        // effect.load(Gdx.files.internal("particle/test.p"), particleAtlas);
        // effect.load(Gdx.files.internal("particle/test.p"), Gdx.files.internal("particle"));
        effect.load(Gdx.files.internal("gold/gold.p"), Gdx.files.internal("particle"));

    }

    public void render(SpriteBatch batch) {
        if (camera == null) return;

        float factor = 0.3f;
        float textureAspectRatio = (float) texture.getWidth() / texture.getHeight();

        float scaledWidth = camera.viewportHeight * scale;
        float scaledHeight = scaledWidth / textureAspectRatio;

        float xOffset = camera.position.x * factor;
        float yOffset = camera.position.y * factor;

        float chestYPosition = camera.position.y - camera.viewportHeight / 2 + yOffset + 160; // +160 para ir pra cima
        chestWorldX = camera.position.x - viewportWidth / 2 + xOffset + 500;

        batch.draw(texture,
                chestWorldX,
                chestYPosition,
                scaledWidth,
                scaledHeight
        );

        if(isConflicted) {
            if(!particleUsed) {
                effect.start();
                particleUsed = true;
            }
            effect.start();
            effect.setPosition(chestWorldX + 85f, chestYPosition + 120f);
            effect.draw(batch, Gdx.graphics.getDeltaTime());
        }
    }

    public void isChestInCollisionArea(Player player) {
            float playerX = player.getPosition().x;
            float chestX = chestWorldX;
            if( playerX >= chestX - 500f && playerX <= chestX + 500f) {
                isConflicted = true;
            } else isConflicted = false;
    }

    public void dispose() {
        texture.dispose();
        effect.dispose();
    }
}
