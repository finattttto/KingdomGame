package com.mygdx.game.Model;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.AssetManager.GameAssetManager;
import com.mygdx.game.Enum.EPlayerState;
import com.mygdx.game.Utils.PlayerAnimationManager;

import java.util.EnumSet;

public class Player {
    private boolean facingRight = true;
    private float scale = 5f;
    private boolean isAttacking = false;
    private boolean isMoving = false;
    private boolean isWinner = false;

    private float speed = 1000f;

    private EPlayerState currentState;
    private final PlayerAnimationManager animationManager;

    private Sprite currentSprite;

    private Sound sound;
    private Long soundId;

    private Integer coinPoints = 0;
    private Integer life = 3;

    private Texture coin;
    private Integer coinTime = 0;


    public Player() {
        animationManager = new PlayerAnimationManager();
        currentState = EPlayerState.IDLE;
        // inicializa o Sprite com um frame parado
        currentSprite = new Sprite(animationManager.getCurrentFrame(currentState, true));
        currentSprite.setScale(scale);

        coin = GameAssetManager.getManager().get("utils/coin.png", Texture.class);
    }

    public void render(SpriteBatch batch, float cameraX) {
        boolean looping = EnumSet.of(EPlayerState.WALKING, EPlayerState.IDLE, EPlayerState.RUN, EPlayerState.DEATH).contains(currentState);

        if (!looping && animationManager.isAnimationFinished(currentState)) {
            isAttacking = false;
            currentState = EPlayerState.IDLE;
        }

        if (currentState == EPlayerState.RUN) speed = 420f;
        else speed = 230f;

        // att o frame atual e ajusta o Sprite
        currentSprite.setRegion(animationManager.getCurrentFrame(currentState, looping));

        currentSprite.setPosition(cameraX, -340);
        currentSprite.setScale(scale);

        currentSprite.setFlip(!facingRight, false);

        currentSprite.draw(batch);

        if(coinTime > 0) {
            coinTime -= 4;
            batch.draw(coin,
                    cameraX + 30,
                    -200 + -coinTime,
                    (float) coin.getWidth() / 6,
                    (float) coin.getHeight() / 6);
        }
    }

    public void setState(EPlayerState newState) {
        if (isAttacking) return;

        if (currentState != newState) {
            // muda o estado
            currentState = newState;

            // aqui valida para que não repita uma animação enquanto outra esta sendo executada
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

            // reinicia a animação
            animationManager.resetStateTime();
        }
    }

    public void setSound(EPlayerState state) {
        if (soundId != null) sound.stop(soundId);
        switch (state) {
            case RUN:
            case WALKING:
                sound = GameAssetManager.getManager().get("sounds/player/Step_grass.mp3", Sound.class);
                soundId = sound.play(state == EPlayerState.WALKING ? 0.1f : 0.3f);
                sound.setPitch(soundId, state == EPlayerState.WALKING ? 1.2f : 1.3f);
                sound.setLooping(soundId, true);
                break;
            case ATTACK_1:
            case ATTACK_2:
                sound = GameAssetManager.getManager().get("sounds/player/Sword_1.mp3", Sound.class);
                soundId = sound.play(0.7f);
                break;
            case ATTACK_3:
                sound = GameAssetManager.getManager().get("sounds/player/Sword_2.mp3", Sound.class);
                soundId = sound.play(0.8f);
                break;
            case HURT:
                sound = GameAssetManager.getManager().get("sounds/player/Hit_1.mp3", Sound.class);
                soundId = sound.play(0.8f);
                break;
            case DEFEND:
                sound = GameAssetManager.getManager().get("sounds/player/Defend.mp3", Sound.class);
                soundId = sound.play(0.8f);
                break;
            default:
                if (soundId != null) {
                    sound.stop(soundId);
                }
        }
    }

    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }

    public void enemyEliminated() {
        this.coinPoints++;
        coinTime = 60;
    }

    public void enemyAttacking() {
        if(currentState != EPlayerState.DEFEND) {
            life--;
            if(life == 0) {
                setState(EPlayerState.DEATH);
            } else setState(EPlayerState.HURT);
        }
    }


    public void dispose() {
        sound.dispose();
    }

    public boolean isAttacking() {
        return isAttacking;
    }


    public float getSpeed() {
        return speed;
    }

    public Integer getCoinPoints() {
        return coinPoints;
    }

    public void setCoinPoints(Integer coinPoints) {
        this.coinPoints = coinPoints;
    }

    public Integer getLife() {
        return life;
    }

    public void setLife(Integer life) {
        this.life = life;
    }

    public boolean getPlayerIsDeath() {
        return life == 0;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }
}
