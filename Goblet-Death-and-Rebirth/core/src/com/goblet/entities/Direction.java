package com.goblet.entities;

import com.badlogic.gdx.Input;

/**
 * En enum som beskriver riktningar (och även lite annat, som IDLE och ATTACK, letar fortfarande efter ett bättre namn
 * än Direction).
 * Created by johan on 5/16/16.
 */
public enum Direction {
    IDLE, LEFT, RIGHT, UP, DOWN, ATTACK;

    /**
     * Översätter en keycode till motsvarande Direction.
     * @param keycode Keycoden som ska översättas.
     * @return Directionen som motsvarar keycoden.
     */
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
            case Input.Keys.Z:
                return Direction.ATTACK;
            default:
                return Direction.IDLE;
        }
    }

}
