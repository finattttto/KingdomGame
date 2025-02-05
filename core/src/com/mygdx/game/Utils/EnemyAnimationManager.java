package com.mygdx.game.Utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.AssetManager.GameAssetManager;
import com.mygdx.game.Enum.EEnemyState;

import java.util.EnumMap;

public class EnemyAnimationManager {
    private final EnumMap<EEnemyState, Animation<Sprite>> animations;
    private float stateTime;

    public EnemyAnimationManager() {
        animations = new EnumMap<>(EEnemyState.class);
        stateTime = 0f;

        loadAnimation(EEnemyState.IDLE, "IDLE.png", 150, 150, 0.2f);
        loadAnimation(EEnemyState.RUN, "RUN.png", 150, 150, 0.1f);
        loadAnimation(EEnemyState.DEATH, "DEATH.png", 150, 150, 0.1f);
        loadAnimation(EEnemyState.ATTACK, "ATTACK.png", 150, 150, 0.1f);
        loadAnimation(EEnemyState.HIT, "HIT.png", 150, 150, 0.1f);
    }

    // carrega as animações
    private void loadAnimation(EEnemyState state, String texturePath, int frameWidth, int frameHeight, float frameDuration) {
        Texture spriteSheet = GameAssetManager.getManager().get("enemy/" + texturePath, Texture.class);
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

    public Sprite getCurrentFrame(EEnemyState state, boolean loop) {
        Animation<Sprite> animation = animations.get(state);

        if (animation == null) {
            throw new IllegalStateException("Animação não encontrada para o estado: " + state);
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

    public boolean isAnimationFinished(EEnemyState state) {
        Animation<Sprite> animation = animations.get(state);
        return stateTime >= animation.getAnimationDuration();
    }
}
