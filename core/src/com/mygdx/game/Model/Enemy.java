package com.mygdx.game.Model;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.AssetManager.GameAssetManager;
import com.mygdx.game.Enum.EEnemyState;
import com.mygdx.game.Enum.EPlayerState;
import com.mygdx.game.Utils.EnemyAnimationManager;
import com.mygdx.game.Utils.PlayerAnimationManager;

import java.util.EnumSet;

public class Enemy {
    Camera camera;
    Player player;

    int life = 3;
    boolean death = false;
    boolean attackAccounted = false;
    boolean isAttacking = false;
    boolean isHiting = false;
    boolean facingRight = false;

    float enemyWorldX = 0f;
    float enemyWorldY = 0f;

    float positionX = 0f;
    float viewportWidth;

    float scale = 0.15f;
    float factor = 0.3f;

    private EEnemyState currentState;
    private final EnemyAnimationManager animationManager;

    private Sprite currentSprite;

    public Enemy(Camera camera, Player player, float positionX) {
        this.camera = camera;
        this.viewportWidth = camera.viewportWidth;
        this.positionX = positionX;
        this.player = player;

        float yOffset = camera.position.y * factor;
        enemyWorldY = camera.position.y - camera.viewportHeight / 2 + yOffset + 200; // +160 para ir pra cima

        animationManager = new EnemyAnimationManager();
        currentState = EEnemyState.IDLE;

        currentSprite = new Sprite(animationManager.getCurrentFrame(currentState, true));
        currentSprite.setScale(scale);

        facingRight = false;
    }

    public void render(SpriteBatch batch) {
        if ((life == 0 && currentState != EEnemyState.HIT && currentState != EEnemyState.DEATH) || death) return;
        float xOffset = camera.position.x * factor;

        boolean looping = EnumSet.of(EEnemyState.IDLE, EEnemyState.RUN).contains(currentState);
        if (!looping && animationManager.isAnimationFinished(currentState)) {
            if(currentState == EEnemyState.DEATH) {
                death = true;
                return;
            }
            isAttacking = false;
            isHiting = false;
            setState(EEnemyState.IDLE);
        }

        enemyWorldX = camera.position.x - viewportWidth / 2 + xOffset + positionX;

        updateState();

        currentSprite.setRegion(animationManager.getCurrentFrame(currentState, true));
        currentSprite.setScale(5f);
        currentSprite.setPosition(enemyWorldX, enemyWorldY);

        if (facingRight) currentSprite.setFlip(false, false);
        else currentSprite.setFlip(true, false);

        currentSprite.draw(batch);
    }

    int count = 0;

    public void updateState() {
        float distanceToPlayer = Math.abs(camera.position.x - enemyWorldX);
        float combatThreshold = 100f;

        // verifica se o player esta dentro da area determina, caso positivo, ele avança
        if(life == 0) {
            setState(EEnemyState.DEATH);
        } else if (distanceToPlayer > combatThreshold) {
            moveTowardsPlayer();
        } else {
            // verifica se o player esta atacando, e contabiliza uma vez o dano por ataque
            if(player.isAttacking() && !attackAccounted) {
                setState(EEnemyState.HIT);
                animationManager.resetStateTime();
                attackAccounted = true;
                life--;
            } else  {
                if(!player.isAttacking()) {
                    attackAccounted = false;
                }
                count++;
                if(count > 60) {
                    setState(EEnemyState.ATTACK);
                    count = 0;
                } else {
                    setState(EEnemyState.IDLE);
                }
            }
        }

    }

    public void setState(EEnemyState newState) {
        if (currentState != newState) {
            if(isHiting) return;
            if(isAttacking && newState != EEnemyState.HIT) return;
            currentState = newState;
            animationManager.resetStateTime();
            count = 0;
            if(newState == EEnemyState.ATTACK) {
                isAttacking = true;
            } else if(newState == EEnemyState.HIT) {
                isHiting = true;
            }
        }
    }

    private void moveTowardsPlayer() {
        if (camera.position.x >= enemyWorldX - 500f && camera.position.x <= enemyWorldX) {
            setState(EEnemyState.RUN);
            facingRight = false;
            positionX -= 3f;
        } else if (camera.position.x > enemyWorldX && camera.position.x <= enemyWorldX + 500f) {
            setState(EEnemyState.RUN);
            facingRight = true;
            positionX += 3f;
        } else {
            setState(EEnemyState.IDLE);
        }
    }

    public void dispose() {
        currentSprite.getTexture().dispose();
    }
}
