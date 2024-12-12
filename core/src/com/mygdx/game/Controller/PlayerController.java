package com.mygdx.game.Controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.Enum.EPlayerState;
import com.mygdx.game.Model.Player;

public class PlayerController implements InputProcessor {
    private Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                player.setFacingRight(false);
                player.setState(EPlayerState.WALKING);
                break;
            case Input.Keys.RIGHT:
                player.setFacingRight(true);
                player.setState(EPlayerState.WALKING);
                break;
            case Input.Keys.A:
                player.setState(EPlayerState.ATTACK_1);
                break;
            case Input.Keys.S:
                player.setState(EPlayerState.ATTACK_2);
                break;
            case Input.Keys.D:
                player.setState(EPlayerState.ATTACK_3);
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.RIGHT) {
            player.setState(EPlayerState.IDLE);
        } else if (keycode == Input.Keys.A || keycode == Input.Keys.S || keycode == Input.Keys.D) {
            player.setState(EPlayerState.IDLE);
        }
        return true;
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
