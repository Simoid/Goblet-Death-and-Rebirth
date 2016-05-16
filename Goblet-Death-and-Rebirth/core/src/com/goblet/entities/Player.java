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

    private float moveSpeed = 100.0f;
    private int scale = 5;

    private float timeSinceAnimationStart;
    private String spriteLocation = "assets/sprites/";

    private Movement movement;

    private HashMap<Direction, SpriteAnimation> animations = new HashMap<Direction, SpriteAnimation>();
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

        movement = new Movement(moveSpeed);
        timeSinceAnimationStart = 0;
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
        Direction dir = Direction.keyCodeTranslate(keycode);
        if (dir == Direction.IDLE){
            return;
        }
        if (!movement.getMoveFlag(dir)){
            movement.addMovementX(dir);
            movement.addMovementY(dir);
            movement.setMoveFlag(dir, true);
            currentAnimation = animations.get(dir);
            timeSinceAnimationStart = 0;
            selectAnimation();
        }
    }

    private void selectAnimation(){
        if (movement.getMoveFlag(Direction.DOWN) && ! movement.getMoveFlag(Direction.UP)){
            currentAnimation = animations.get(Direction.DOWN);
        } else if (movement.getMoveFlag(Direction.UP) && ! movement.getMoveFlag(Direction.DOWN)){
            currentAnimation = animations.get(Direction.UP);
        } else if (movement.getMoveFlag(Direction.LEFT) && ! movement.getMoveFlag(Direction.RIGHT)){
            currentAnimation = animations.get(Direction.LEFT);
        } else if (movement.getMoveFlag(Direction.RIGHT) && ! movement.getMoveFlag(Direction.LEFT)){
            currentAnimation = animations.get(Direction.RIGHT);
        } else {
            currentAnimation = animations.get(Direction.IDLE);
        }

    }

    public void update(float deltaTime){
        timeSinceAnimationStart += deltaTime;
        position.setLocation(position.x + deltaTime*movement.getMovementX(), position.y + deltaTime*movement.getMovementY());
    }

    public void stopMove(int keycode){
        Direction dir = Direction.keyCodeTranslate(keycode);
        if (dir == Direction.IDLE){
            return;
        }
        if (movement.getMoveFlag(dir)){
            movement.subMovementX(dir);
            movement.subMovementY(dir);
            movement.setMoveFlag(dir, false);
            timeSinceAnimationStart = 0;
            selectAnimation();
        }
    }

    public void draw(Batch batch){
        currentAnimation.draw(batch, position.x, position.y, timeSinceAnimationStart);
    }

}
