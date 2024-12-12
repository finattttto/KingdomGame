package com.mygdx.game.Model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Enum.EPlayerState;
import com.mygdx.game.Utils.PlayerAnimationManager;

public class Player {
    private Vector2 position;
    private boolean facingRight;
    private float scale;

    private EPlayerState currentState;
    private final PlayerAnimationManager animationManager;

    public Player(float startX, float startY) {
        position = new Vector2(startX, startY);
        facingRight = true;
        scale = 5f;

        animationManager = new PlayerAnimationManager();
        currentState = EPlayerState.IDLE;
    }

    public void render(SpriteBatch batch, float cameraX, float cameraY) {
        TextureRegion currentFrame = animationManager.getCurrentFrame(currentState, currentState == EPlayerState.WALKING || currentState == EPlayerState.IDLE);

        float renderX = cameraX - (currentFrame.getRegionWidth() * scale) / 2f;
        float renderY = cameraY - (currentFrame.getRegionHeight() * scale) / 2f - 310;

        if (facingRight) {
            batch.draw(currentFrame, renderX, renderY, currentFrame.getRegionWidth() * scale, currentFrame.getRegionHeight() * scale);
        } else {
            batch.draw(currentFrame, renderX + currentFrame.getRegionWidth() * scale, renderY, -currentFrame.getRegionWidth() * scale, currentFrame.getRegionHeight() * scale);
        }
    }

    public void setState(EPlayerState newState) {
        if (currentState != newState) {
            currentState = newState;
            animationManager.resetStateTime();
        }
    }

    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void dispose() {
        // Libere recursos se necessário
    }
}
