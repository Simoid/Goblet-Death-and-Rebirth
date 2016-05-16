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
    private String spriteLocation = "assets/sprites/mc/";

    private Movement movement;

    private HashMap<Direction, SpriteAnimation> animations = new HashMap<Direction, SpriteAnimation>();
    private SpriteAnimation currentAnimation;
    private Point2D position;


    public Player(int xPos, int yPos){
        position = new Point2D(xPos, yPos);

        animations.put(Direction.DOWN, new SpriteAnimation(spriteLocation + "mc_move_down.pack", 4, scale, 1/5f));
        animations.put(Direction.UP, new SpriteAnimation(spriteLocation + "mc_move_up.pack", 4, scale, 1/5f));
        animations.put(Direction.LEFT, new SpriteAnimation(spriteLocation + "mc_move_left.pack", 4, scale, 1/5f));
        animations.put(Direction.RIGHT, new SpriteAnimation(spriteLocation + "mc_move_right.pack", 4, scale, 1/5f));
        animations.put(Direction.IDLE, new SpriteAnimation(spriteLocation + "mc_idle.pack", 2, scale, 1f));
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
            selectAnimation();
        }
    }

    private void selectAnimation(){
        if (movement.getMoveFlag(Direction.DOWN) && ! movement.getMoveFlag(Direction.UP)){
            setAnimation(Direction.DOWN);
        } else if (movement.getMoveFlag(Direction.UP) && ! movement.getMoveFlag(Direction.DOWN)){
            setAnimation(Direction.UP);
        } else if (movement.getMoveFlag(Direction.LEFT) && ! movement.getMoveFlag(Direction.RIGHT)){
            setAnimation(Direction.LEFT);
        } else if (movement.getMoveFlag(Direction.RIGHT) && ! movement.getMoveFlag(Direction.LEFT)){
            setAnimation(Direction.RIGHT);
        } else {
            setAnimation(Direction.IDLE);
        }
    }

    private void setAnimation(Direction dir){
        if (currentAnimation != animations.get(dir)) {
            currentAnimation = animations.get(dir);
            timeSinceAnimationStart = 0;
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
            selectAnimation();
        }
    }

    public void draw(Batch batch){
        currentAnimation.draw(batch, position.x, position.y, timeSinceAnimationStart);
    }

}
