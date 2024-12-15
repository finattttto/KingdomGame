package com.mygdx.game.Model;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AssetManager.GameAssetManager;
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

    private Sound sound;
    private Long soundId;

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
        } else speed = 800f;

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

            boolean attacking = EnumSet.of(EPlayerState.ATTACK_1, EPlayerState.ATTACK_2, EPlayerState.ATTACK_3).contains(currentState);

            if (attacking) {
                isAttacking = true;
                isMoving = false;
            }

            if (newState == EPlayerState.WALKING) {
                isMoving = true;

            } else if (newState == EPlayerState.IDLE) {
                isMoving = false;
            }

            setSound(newState);

            animationManager.resetStateTime();
        }
    }

    public void setSound(EPlayerState state) {
        if(soundId != null) sound.stop(soundId);
        switch (state) {
            case RUN:
            case WALKING:
                sound = GameAssetManager.getManager().get( "sounds/player/Step_grass.mp3", Sound.class);
                soundId = sound.play(state == EPlayerState.WALKING ? 0.1f : 0.3f);
                sound.setPitch(soundId, state == EPlayerState.WALKING ? 1.2f : 1.3f);
                sound.setLooping(soundId, true);
                break;
            case ATTACK_1:
            case ATTACK_2:
                sound = GameAssetManager.getManager().get( "sounds/player/Sword_1.mp3", Sound.class);
                soundId = sound.play(0.7f);
                break;
            case ATTACK_3:
                sound = GameAssetManager.getManager().get( "sounds/player/Sword_2.mp3", Sound.class);
                soundId = sound.play(0.8f);
                break;
            case HURT:
                sound = GameAssetManager.getManager().get( "sounds/player/Hit_1.mp3", Sound.class);
                soundId = sound.play(0.8f);
                break;
            case DEFEND:
                sound = GameAssetManager.getManager().get( "sounds/player/Defend.mp3", Sound.class);
                soundId = sound.play(0.8f);
                break;
            default:
                if(soundId != null) {
                    sound.stop(soundId);
                }
        }
    }

    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void dispose() {
        sound.dispose();
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
