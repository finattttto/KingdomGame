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

public class Enemy {
    Camera camera;
    Texture texture;
    Player player;

    int life = 3;
    boolean attackAccounted = false;

    float enemyWorldX = 0f;
    float enemyWorldY = 0f;

    float positionX = 0f;
    float viewportWidth;

    float scale = 0.15f;
    float factor = 0.3f;

//    private EEnemyState currentState;
//    private final EnemyAnimationManager animationManager;

    private Sprite currentSprite;

    public Enemy(Camera camera, Player player, float positionX) {
        this.texture = GameAssetManager.getManager().get("utils/enemy.png", Texture.class);
        this.camera = camera;
        this.viewportWidth = camera.viewportWidth;
        this.positionX = positionX;
        this.player = player;

        float yOffset = camera.position.y * factor;
        enemyWorldY = camera.position.y - camera.viewportHeight / 2 + yOffset + 160; // +160 para ir pra cima

//        animationManager = new EnemyAnimationManager();
//        currentState = EEnemyState.IDLE;
//
//        currentSprite = new Sprite(animationManager.getCurrentFrame(currentState, true));
//        currentSprite.setScale(scale);
    }

    public void render(SpriteBatch batch) {
        if (camera == null) return;
        if (life == 0) return;
        float textureAspectRatio = (float) texture.getWidth() / texture.getHeight();
        float scaledWidth = camera.viewportHeight * scale;
        float scaledHeight = scaledWidth / textureAspectRatio;
        float xOffset = camera.position.x * factor;

        enemyWorldX = camera.position.x - viewportWidth / 2 + xOffset + positionX;

        float distanceToPlayer = Math.abs(camera.position.x - enemyWorldX);
        float combatThreshold = 100f;

        // verifica se o player esta dentro da area determina, caso positivo, ele avança
        if (distanceToPlayer > combatThreshold) {
            if (camera.position.x >= enemyWorldX - 500f && camera.position.x <= enemyWorldX) {
                positionX -= 3f;
            } else if (camera.position.x > enemyWorldX && camera.position.x <= enemyWorldX + 500f) {
                positionX += 3f;
            }
        } else {
            // verifica se o player esta atacando, e contabiliza uma vez o dano por ataque
            if(player.isAttacking() && !attackAccounted) {
                attackAccounted = true;
                life--;
            } else if(!player.isAttacking() && attackAccounted) {
                attackAccounted = false;
            }
        }

        batch.draw(texture,
                enemyWorldX,
                enemyWorldY,
                scaledWidth,
                scaledHeight
        );
    }

    public void dispose() {
        texture.dispose();
    }
}
