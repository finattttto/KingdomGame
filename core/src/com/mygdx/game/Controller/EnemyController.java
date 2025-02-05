package com.mygdx.game.Controller;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.AssetManager.GameAssetManager;
import com.mygdx.game.Model.Enemy;
import com.mygdx.game.Model.Player;

import java.util.ArrayList;
import java.util.List;

public class EnemyController {
    Camera camera;
    Player player;
    private List<Enemy> enemies;


    public EnemyController(Camera camera, Player player) {
        this.camera = camera;
        getEnemies().add(new Enemy(camera, player, 3000f));
        getEnemies().add(new Enemy(camera, player, 3500f));
        getEnemies().add(new Enemy(camera, player, 3800f));
        getEnemies().add(new Enemy(camera, player, 4000f));
//        getEnemies().add(new Enemy(camera, player, 5000f));
//        getEnemies().add(new Enemy(camera, player, 75000f));
    }

    public void render(SpriteBatch batch) {
        if (camera == null) return;
        for(Enemy e: getEnemies()) {
            e.render(batch);
        }
    }

    public List<Enemy> getEnemies() {
        if(enemies == null) enemies = new ArrayList<>();
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void dispose() {
        for(Enemy e: getEnemies()) {
            e.dispose();
        }
    }
}
