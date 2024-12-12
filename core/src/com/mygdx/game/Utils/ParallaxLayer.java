package com.mygdx.game.Utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ParallaxLayer {
    Texture texture;
    float factor;
    boolean wrapHorizontally;
    boolean wrapVertically;
    Camera camera;

    public ParallaxLayer(Texture texture, float factor, boolean wrapHorizontally, boolean wrapVertically) {
        this.texture = texture;
        this.factor = factor;
        this.wrapHorizontally = wrapHorizontally;
        this.wrapVertically = wrapVertically;
        this.texture.setWrap(
                this.wrapHorizontally ? Texture.TextureWrap.Repeat : Texture.TextureWrap.ClampToEdge,
                this.wrapVertically ? Texture.TextureWrap.Repeat : Texture.TextureWrap.ClampToEdge

        );

    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void render(SpriteBatch batch) {
        if (camera == null) return;

        float textureAspectRatio = (float) texture.getWidth() / texture.getHeight();
        float scaledWidth = camera.viewportHeight * textureAspectRatio;

        float xOffset = (camera.position.x * factor) % scaledWidth;
        float yOffset = (camera.position.y * factor) % camera.viewportHeight;

        if (xOffset < 0) xOffset += scaledWidth;
        if (yOffset < 0) yOffset += camera.viewportHeight;

        for (float x = -scaledWidth + xOffset; x < camera.viewportWidth; x += scaledWidth) {
            for (float y = -camera.viewportHeight + yOffset; y < camera.viewportHeight; y += camera.viewportHeight) {
                batch.draw(texture,
                        camera.position.x - camera.viewportWidth / 2 + x,
                        camera.position.y - camera.viewportHeight / 2 + y,
                        scaledWidth,
                        camera.viewportHeight
                );
            }
        }
    }

    public void dispose() {
        texture.dispose();
    }


}
