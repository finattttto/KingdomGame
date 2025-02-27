package com.mygdx.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.Enum.EPlayerState;
import com.mygdx.game.Model.Player;
import com.mygdx.game.MyGdxGame;

public class PlayerController implements InputProcessor {
    private Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

    private boolean isCtrlPressed;
    public boolean restartGame;

    @Override
    public boolean keyDown(int keycode) {

        if(player.getPlayerIsDeath() || player.isWinner()) {
            if(keycode == Input.Keys.ENTER) {
                restartGame = true;
            }
            return false;
        }

        if (player.isAttacking()) {
            return false;
        }

        switch (keycode) {
            case Input.Keys.LEFT:
                player.setFacingRight(false);
                if (isCtrlPressed) {
                    player.setState(EPlayerState.RUN);
                } else {
                    player.setState(EPlayerState.WALKING);
                }
                break;
            case Input.Keys.RIGHT:
                player.setFacingRight(true);
                if (isCtrlPressed) {
                    player.setState(EPlayerState.RUN);
                } else {
                    player.setState(EPlayerState.WALKING);
                }
                break;
            case Input.Keys.CONTROL_LEFT:
            case Input.Keys.CONTROL_RIGHT:
                isCtrlPressed = true;
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    player.setState(EPlayerState.RUN);
                } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    player.setState(EPlayerState.RUN);
                }
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
            case Input.Keys.C:
                player.setState(EPlayerState.DEFEND);
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
            case Input.Keys.RIGHT:
                if (isCtrlPressed) {
                    player.setState(EPlayerState.IDLE);
                } else {
                    player.setState(EPlayerState.IDLE);
                }
                break;
            case Input.Keys.CONTROL_LEFT:
            case Input.Keys.CONTROL_RIGHT:
                isCtrlPressed = false;
                // Voltar para WALKING caso esteja pressionando LEFT ou RIGHT
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    player.setState(EPlayerState.WALKING);
                } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    player.setState(EPlayerState.WALKING);
                }
                break;
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
