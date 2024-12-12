package com.mygdx.game.Utils;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.AssetManager.GameAssetManager;
import com.mygdx.game.Enum.EPlayerState;

import java.util.EnumMap;

public class PlayerAnimationManager {
    private final EnumMap<EPlayerState, Animation<TextureRegion>> animations;
    private float stateTime;

    private boolean running = false;

    public PlayerAnimationManager() {
        animations = new EnumMap<>(EPlayerState.class);
        stateTime = 0f;

        loadAnimation(EPlayerState.IDLE, "IDLE.png", 96, 84, 0.2f);
        loadAnimation(EPlayerState.WALKING, "WALK.png", 96, 84, 0.1f);
        loadAnimation(EPlayerState.ATTACK_1, "ATTACK_1.png", 96, 84, 0.1f);
        loadAnimation(EPlayerState.ATTACK_2, "ATTACK_2.png", 96, 84, 0.1f);
        loadAnimation(EPlayerState.ATTACK_3, "ATTACK_3.png", 96, 84, 0.1f);
    }

    private void loadAnimation(EPlayerState state, String texturePath, int frameWidth, int frameHeight, float frameDuration) {

        Texture spriteSheet = GameAssetManager.getManager().get( "player/" + texturePath, Texture.class);
        TextureRegion[][] tempFrames = TextureRegion.split(spriteSheet, frameWidth, frameHeight);

        Array<TextureRegion> frames = new Array<>();
        for (TextureRegion[] row : tempFrames) {
            for (TextureRegion frame : row) {
                frames.add(frame);
            }
        }

        Animation<TextureRegion> animation = new Animation<>(frameDuration, frames, Animation.PlayMode.NORMAL);
        animations.put(state, animation);
    }

    public TextureRegion getCurrentFrame(EPlayerState state, boolean loop) {
        Animation<TextureRegion> animation = animations.get(state);

        if (animation == null) {
            throw new IllegalStateException("Animação não encontrada para o estado: " + state);
        }

        if (loop) {
            stateTime += 0.016f; // 60fps
        } else {
            stateTime = Math.min(stateTime + 0.016f, animation.getAnimationDuration());
        }

        return animation.getKeyFrame(stateTime, loop);
    }

    public void resetStateTime() {
        stateTime = 0f;
    }
}