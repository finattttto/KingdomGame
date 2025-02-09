package com.mygdx.game.Utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.AssetManager.GameAssetManager;
import com.mygdx.game.Enum.EPlayerState;

import java.util.EnumMap;

public class PlayerAnimationManager {
    //mapeia todos os estados e as animações
    private final EnumMap<EPlayerState, Animation<Sprite>> animations;
    private float stateTime;

    public PlayerAnimationManager() {
        animations = new EnumMap<>(EPlayerState.class);
        stateTime = 0f;

        loadAnimation(EPlayerState.IDLE, "IDLE.png", 96, 84, 0.2f);
        loadAnimation(EPlayerState.WALKING, "WALK.png", 96, 84, 0.1f);
        loadAnimation(EPlayerState.ATTACK_1, "ATTACK_1.png", 96, 84, 0.1f);
        loadAnimation(EPlayerState.ATTACK_2, "ATTACK_2.png", 96, 84, 0.1f);
        loadAnimation(EPlayerState.ATTACK_3, "ATTACK_3.png", 96, 84, 0.1f);
        loadAnimation(EPlayerState.RUN, "RUN.png", 96, 84, 0.1f);
        loadAnimation(EPlayerState.DEATH, "DEATH.png", 96, 84, 0.1f);
        loadAnimation(EPlayerState.DEFEND, "DEFEND.png", 96, 84, 0.1f);
        loadAnimation(EPlayerState.HURT, "HURT.png", 96, 84, 0.1f);
        loadAnimation(EPlayerState.JUMP, "JUMP.png", 96, 84, 0.1f);
    }

    // carrega as animações
    private void loadAnimation(EPlayerState state, String texturePath, int frameWidth, int frameHeight, float frameDuration) {
        Texture spriteSheet = GameAssetManager.getManager().get("player/" + texturePath, Texture.class);
        TextureRegion[][] tempFrames = TextureRegion.split(spriteSheet, frameWidth, frameHeight);

        Array<Sprite> frames = new Array<>();
        for (TextureRegion[] row : tempFrames) {
            for (TextureRegion frame : row) {
                frames.add(new Sprite(frame));
            }
        }

        // representa uma animação com varios frames, retirados do sprite
        Animation<Sprite> animation = new Animation<>(frameDuration, frames, Animation.PlayMode.NORMAL);
        animations.put(state, animation);
    }

    public Sprite getCurrentFrame(EPlayerState state, boolean loop) {
        Animation<Sprite> animation = animations.get(state);

        if (state == EPlayerState.DEATH) {
            if (stateTime >= animation.getAnimationDuration() - 0.016f) {
                return animation.getKeyFrame(animation.getAnimationDuration() - 0.016f, loop);
            }
        }

        if (loop) {
            // avança o tempo da animação infinito
            stateTime += 0.016f; // 60fps
        } else {
            // avança o tempo da animação, pelo tempo definido
            stateTime = Math.min(stateTime + 0.016f, animation.getAnimationDuration());
        }

        return animation.getKeyFrame(stateTime, loop);
    }

    public void resetStateTime() {
        stateTime = 0f;
    }

    public boolean isAnimationFinished(EPlayerState state) {
        Animation<Sprite> animation = animations.get(state);
        return stateTime >= animation.getAnimationDuration();
    }
}
