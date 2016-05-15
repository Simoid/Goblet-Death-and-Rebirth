package com.goblet.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.goblet.graphics.SpriteAnimation;
import com.sun.javafx.geom.Point2D;

import java.util.HashMap;

/**
 * En klass för spelaren som objekt.
 * Innehåller spelarens position och dess animationer, samt håller reda på vad som ska
 * hända när piltangenterna används.
 * Created by Johan on 2016-05-14.
 */
public class Player {

    private enum Direction{
        IDLE, LEFT, RIGHT, UP, DOWN
    }

    private float moveSpeed = 100.0f;
    private int scale = 5;

    private float timeSinceAnimationStart;
    private String spriteLocation = "assets/sprites/";
    private HashMap<Direction, SpriteAnimation> animations = new HashMap<Direction, SpriteAnimation>();
    private HashMap<Direction, Boolean> movementBools = new HashMap<Direction, Boolean>();
    private HashMap<Direction, Float> xMovement = new HashMap<Direction, Float>();
    private HashMap<Direction, Float> yMovement = new HashMap<Direction, Float>();
    private float currentXMovement = 0f;
    private float currentYMovement = 0f;
    private SpriteAnimation currentAnimation;
    private Point2D position;


    public Player(int xPos, int yPos){
        position = new Point2D(xPos, yPos);
        animations.put(Direction.DOWN, new SpriteAnimation(spriteLocation + "mc_frontwalk.pack", 4, scale, 1/5f));
        animations.put(Direction.UP, new SpriteAnimation(spriteLocation + "mc_frontwalk.pack", 1, scale, 1f));
        animations.put(Direction.LEFT, new SpriteAnimation(spriteLocation + "mc_frontwalk.pack", 1, scale, 1f));
        animations.put(Direction.RIGHT, new SpriteAnimation(spriteLocation + "mc_frontwalk.pack", 1, scale, 1f));
        animations.put(Direction.IDLE, new SpriteAnimation(spriteLocation + "mc_frontwalk.pack", 1, scale, 1f));
        currentAnimation = animations.get(Direction.IDLE);
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

        timeSinceAnimationStart = 0;
    }

    private Direction translateKey(int keycode){
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
        }
        return Direction.IDLE;
    }

    public void increaseScale(){
        scale++;
        updateScale();
    }

    public void decreaseScale(){
        scale--;
        updateScale();
    }

    private void updateScale(){
        for(SpriteAnimation animation : animations.values()){
            animation.setScale(scale);
        }
    }

    public void startMove(int keycode){
        Direction dir = translateKey(keycode);
        if (dir == Direction.IDLE){
            return;
        }
        if (!movementBools.get(dir)){
            currentXMovement += xMovement.get(dir);
            currentYMovement += yMovement.get(dir);
            movementBools.put(dir, true);
            currentAnimation = animations.get(dir);
            timeSinceAnimationStart = 0;
            if (currentYMovement == 0f && currentXMovement == 0f){
                currentAnimation = animations.get(Direction.IDLE);
            }
        }
    }

    public void update(float deltaTime){
        timeSinceAnimationStart += deltaTime;
        position.setLocation(position.x + deltaTime*currentXMovement, position.y + deltaTime*currentYMovement);
    }

    public void stopMove(int keycode){
        Direction dir = translateKey(keycode);
        if (dir == Direction.IDLE){
            return;
        }
        if (movementBools.get(dir)){
            currentXMovement -= xMovement.get(dir);
            currentYMovement -= yMovement.get(dir);
            movementBools.put(dir, false);
            timeSinceAnimationStart = 0;
            if (currentYMovement == 0f && currentXMovement == 0f){
                currentAnimation = animations.get(Direction.IDLE);
            } else if (movementBools.get(Direction.DOWN) && !movementBools.get(Direction.UP)){
                currentAnimation = animations.get(Direction.DOWN);
            } else if (movementBools.get(Direction.UP) && !movementBools.get(Direction.DOWN)){
                currentAnimation = animations.get(Direction.UP);
            } else if (movementBools.get(Direction.LEFT) && !movementBools.get(Direction.RIGHT)){
                currentAnimation = animations.get(Direction.LEFT);
            } else if (movementBools.get(Direction.RIGHT) && !movementBools.get(Direction.LEFT)){
                currentAnimation = animations.get(Direction.RIGHT);
            }
        }
    }

    public void draw(Batch batch){
        currentAnimation.draw(batch, position.x, position.y, timeSinceAnimationStart);
    }

}
