package com.goblet.entities;

import com.badlogic.gdx.Input;

/**
 * Created by johan on 5/16/16.
 */
public enum Direction {
    IDLE, LEFT, RIGHT, UP, DOWN, ATTACK;

    public static Direction keyCodeTranslate(int keycode){
        switch (keycode){
            case Input.Keys.LEFT:
                return Direction.LEFT;
            case Input.Keys.RIGHT:
                return Direction.RIGHT;
            case Input.Keys.UP:
                return Direction.UP;
            case Input.Keys.DOWN:
                return Direction.DOWN;
            case Input.Keys.A:
                return Direction.LEFT;
            case Input.Keys.D:
                return Direction.RIGHT;
            case Input.Keys.W:
                return Direction.UP;
            case Input.Keys.S:
                return Direction.DOWN;
            default:
                return Direction.IDLE;
        }
    }

}
