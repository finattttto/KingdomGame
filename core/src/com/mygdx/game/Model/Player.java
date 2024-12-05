package com.mygdx.game.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private Texture texture;       // Textura do player
    private Vector2 position;      // Posição do player
    private boolean facingRight;   // Indica a direção para qual o player está virado
    private float scale;           // Escala para aumentar/diminuir o tamanho da imagem

    public Player(String texturePath, float startX, float startY) {
        texture = new Texture(texturePath);
        position = new Vector2(startX, startY);
        facingRight = true; // Começa virado para a direita
        scale = 1.5f;       // Escala padrão (1.5x maior)
    }

    public void render(SpriteBatch batch, float cameraX, float cameraY) {
        // Desenhar o player relativo ao centro da câmera
        float renderX = cameraX - (texture.getWidth() * scale) / 2f;
        float renderY = cameraY - (texture.getHeight() * scale) / 2f - 340;

        // Inverter a textura se estiver virado à esquerda
        if (facingRight) {
            batch.draw(texture, renderX, renderY, texture.getWidth() * scale, texture.getHeight() * scale);
        } else {
            batch.draw(texture, renderX + texture.getWidth() * scale, renderY, -texture.getWidth() * scale, texture.getHeight() * scale);
        }
    }

    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void dispose() {
        texture.dispose();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }
}
