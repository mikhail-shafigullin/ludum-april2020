package ru.alttabber.ludum.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import ru.alttabber.ludum.units.Player;
import ru.alttabber.ludum.utils.UniqueArrayList;

import java.util.*;

public class InputController implements InputProcessor {

    PlayerInput currentPlayerInput = PlayerInput.IDLE;
    UniqueArrayList<Integer> activeInputs = new UniqueArrayList<>();

    @Override
    public boolean keyDown(int keycode) {

        switch (keycode){
            case Input.Keys.UP:
            case Input.Keys.DOWN:
            case Input.Keys.LEFT:
            case Input.Keys.RIGHT:
                activeInputs.add(keycode);
                break;
        }

        setPlayerInput();

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.UP:
            case Input.Keys.DOWN:
            case Input.Keys.LEFT:
            case Input.Keys.RIGHT:
                activeInputs.remove(keycode);
                break;
        }
        setPlayerInput();

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
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public PlayerInput getCurrentPlayerInput() {
        return currentPlayerInput;
    }

    private void setPlayerInput(){
        if(!activeInputs.isEmpty()){
            switch (activeInputs.getLast()){
                case Input.Keys.UP:
                    currentPlayerInput = PlayerInput.UP;
                    break;
                case Input.Keys.DOWN:
                    currentPlayerInput = PlayerInput.DOWN;
                    break;
                case Input.Keys.LEFT:
                    currentPlayerInput = PlayerInput.LEFT;
                    break;
                case Input.Keys.RIGHT:
                    currentPlayerInput = PlayerInput.RIGHT;
                    break;
            }
        }

        if(activeInputs.contains(Input.Keys.UP) && activeInputs.contains(Input.Keys.LEFT)){
            currentPlayerInput = PlayerInput.UPLEFT;
        }
        if(activeInputs.contains(Input.Keys.UP) && activeInputs.contains(Input.Keys.RIGHT)){
            currentPlayerInput = PlayerInput.UPRIGHT;
        }
        if(activeInputs.contains(Input.Keys.DOWN) && activeInputs.contains(Input.Keys.LEFT)){
            currentPlayerInput = PlayerInput.DOWNLEFT;
        }
        if(activeInputs.contains(Input.Keys.DOWN) && activeInputs.contains(Input.Keys.RIGHT)){
            currentPlayerInput = PlayerInput.DOWNRIGHT;
        }

        if(activeInputs.isEmpty()){
            currentPlayerInput = PlayerInput.IDLE;
        }

        System.out.println(activeInputs.toString());
    }
}
