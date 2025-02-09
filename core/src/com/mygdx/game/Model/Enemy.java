package com.mygdx.game.Model;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.AssetManager.GameAssetManager;
import com.mygdx.game.Enum.EEnemyState;
import com.mygdx.game.Utils.EnemyAnimationManager;

import java.util.EnumSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    float scale = 5f;

    int timeToAttack = 0;

    private EEnemyState currentState;
    private final EnemyAnimationManager animationManager;

    private Sprite currentSprite;

    private Sound sound;
    private Long soundId;

    public Enemy(Camera camera, Player player, float positionX) {
        this.camera = camera;
        this.player = player;

        enemyWorldY = camera.position.y - 340;
        enemyWorldX = camera.position.x + positionX;

        animationManager = new EnemyAnimationManager();
        currentState = EEnemyState.IDLE;

        currentSprite = new Sprite(animationManager.getCurrentFrame(currentState, true));
        currentSprite.setScale(scale);

        facingRight = false;
    }

    public void render(SpriteBatch batch) {
        if ((life == 0 && currentState != EEnemyState.HIT && currentState != EEnemyState.DEATH) || death) return;

        boolean looping = EnumSet.of(EEnemyState.IDLE, EEnemyState.RUN).contains(currentState);
        if (!looping && animationManager.isAnimationFinished(currentState)) {
            if(currentState == EEnemyState.DEATH) {
                death = true;
                player.enemyEliminated();
                return;
            }
            isAttacking = false;
            isHiting = false;
            setState(EEnemyState.IDLE);
        }


        updateState();

        currentSprite.setRegion(animationManager.getCurrentFrame(currentState, true));
        currentSprite.setPosition(enemyWorldX, enemyWorldY);
        currentSprite.setFlip(!facingRight, false);

        currentSprite.draw(batch);
    }

    public void updateState() {
        if(player.getPlayerIsDeath()) return;
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
                timeToAttack++;
                if(timeToAttack > 60) {
                    setState(EEnemyState.ATTACK);
                    timeToAttack = 0;

                    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                    scheduler.schedule(() -> {
                        player.enemyAttacking();
                        scheduler.shutdown();
                    }, 300, TimeUnit.MILLISECONDS);

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
            timeToAttack = 0;
            if(newState == EEnemyState.ATTACK) {
                isAttacking = true;
            } else if(newState == EEnemyState.HIT) {
                isHiting = true;
            }

            setSound(newState);
        }
    }

    private void moveTowardsPlayer() {
        if (camera.position.x >= enemyWorldX - 500f && camera.position.x <= enemyWorldX) {
            setState(EEnemyState.RUN);
            facingRight = false;
            enemyWorldX -= 3f;
        } else if (camera.position.x > enemyWorldX && camera.position.x <= enemyWorldX + 500f) {
            setState(EEnemyState.RUN);
            facingRight = true;
            enemyWorldX += 3f;
        } else {
            setState(EEnemyState.IDLE);
        }
    }

    public void dispose() {
        currentSprite.getTexture().dispose();
    }

    public void setSound(EEnemyState state) {
        if (soundId != null) sound.stop(soundId);
        switch (state) {
            case RUN:
                sound = GameAssetManager.getManager().get("sounds/player/Step_grass.mp3", Sound.class);
                soundId = sound.play(0.1f);
                sound.setPitch(soundId, 1.1f);
                sound.setLooping(soundId, true);
                break;
            case ATTACK:
                sound = GameAssetManager.getManager().get("sounds/player/Sword_1.mp3", Sound.class);
                soundId = sound.play(0.3f);
                break;
            case HIT:
                sound = GameAssetManager.getManager().get("sounds/player/Hit_1.mp3", Sound.class);
                soundId = sound.play(0.3f);
                break;
            default:
                if (soundId != null) {
                    sound.stop(soundId);
                }
        }
    }

}
