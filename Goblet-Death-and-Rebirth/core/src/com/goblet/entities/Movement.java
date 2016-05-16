package com.goblet.entities;

import java.util.HashMap;

/**
 * Created by johan on 5/16/16.
 */
public class Movement {

    private HashMap<Direction, Boolean> movementBools = new HashMap<Direction, Boolean>();
    private HashMap<Direction, Float> xMovement = new HashMap<Direction, Float>();
    private HashMap<Direction, Float> yMovement = new HashMap<Direction, Float>();
    private float currentMovementX = 0f;
    private float currentMovementY = 0f;

    public Movement(float moveSpeed){

        movementBools.put(Direction.DOWN, false);
        movementBools.put(Direction.UP, false);
        movementBools.put(Direction.LEFT, false);
        movementBools.put(Direction.RIGHT, false);

        xMovement.put(Direction.DOWN, 0f);
        xMovement.put(Direction.UP, 0f);
        xMovement.put(Direction.LEFT, -moveSpeed);
        xMovement.put(Direction.RIGHT, moveSpeed);

        yMovement.put(Direction.DOWN, -moveSpeed);
        yMovement.put(Direction.UP, moveSpeed);
        yMovement.put(Direction.LEFT, 0f);
        yMovement.put(Direction.RIGHT, 0f);

    }

    public float getMovementX(Direction dir) {
        return xMovement.get(dir);
    }

    public boolean getMoveFlag(Direction dir){
        return movementBools.get(dir);
    }

    public float getMovementY() {
        return currentMovementY;
    }

    public float getMovementX(){
        return currentMovementX;
    }

    public void setMoveFlag(Direction dir, boolean flag){
        movementBools.put(dir, flag);
    }

    public void addMovementX(Direction dir){
        currentMovementX += xMovement.get(dir);
    }

    public void addMovementY(Direction dir){
        currentMovementY += yMovement.get(dir);
    }

    public void subMovementX(Direction dir){
        currentMovementX -= xMovement.get(dir);
    }

    public void subMovementY(Direction dir){
        currentMovementY -= yMovement.get(dir);
    }

}
