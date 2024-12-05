package com.mygdx.game.Controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.Model.Player;

public class PlayerController implements InputProcessor {
    private Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT) {
            player.setFacingRight(false);
        } else if (keycode == Input.Keys.RIGHT) {
            player.setFacingRight(true);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
