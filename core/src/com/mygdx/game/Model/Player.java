package com.mygdx.game.Model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Enum.EPlayerState;
import com.mygdx.game.Utils.PlayerAnimationManager;

import java.util.EnumSet;

public class Player {
    private Vector2 position;
    private boolean facingRight;
    private float scale;
    private boolean isAttacking;
    private boolean isMoving;

    private float speed = 1000f;

    private EPlayerState currentState;
    private final PlayerAnimationManager animationManager;

    public Player(float startX, float startY) {
        position = new Vector2(startX, startY);
        facingRight = true;
        scale = 5f;

        animationManager = new PlayerAnimationManager();
        currentState = EPlayerState.IDLE;

        isAttacking = false;
        isMoving = false;
    }

    public void render(SpriteBatch batch, float cameraX, float cameraY) {

        boolean looping = EnumSet.of(EPlayerState.WALKING, EPlayerState.IDLE, EPlayerState.RUN).contains(currentState);

        if (!looping && animationManager.isAnimationFinished(currentState)) {
            isAttacking = false;
            currentState = EPlayerState.IDLE;
        }

        if(currentState == EPlayerState.RUN) {
            speed = 2000f;
        } else speed = 1000f;

        TextureRegion currentFrame = animationManager.getCurrentFrame(currentState, looping);

        float renderX = cameraX - (currentFrame.getRegionWidth() * scale) / 2f;
        float renderY = cameraY - (currentFrame.getRegionHeight() * scale) / 2f - 310;

        if (facingRight) {
            batch.draw(currentFrame, renderX, renderY, currentFrame.getRegionWidth() * scale, currentFrame.getRegionHeight() * scale);
        } else {
            batch.draw(currentFrame, renderX + currentFrame.getRegionWidth() * scale, renderY, -currentFrame.getRegionWidth() * scale, currentFrame.getRegionHeight() * scale);
        }
    }

    public void setState(EPlayerState newState) {
        if (isAttacking) return;

        if (currentState != newState) {
            currentState = newState;

            if (newState == EPlayerState.ATTACK_1 || newState == EPlayerState.ATTACK_2 || newState == EPlayerState.ATTACK_3) {
                isAttacking = true;
                isMoving = false;
            }

            if (newState == EPlayerState.WALKING) {
                isMoving = true;
            } else if (newState == EPlayerState.IDLE) {
                isMoving = false;
            }

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

    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }
}
