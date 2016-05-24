package com.goblet.entities;

import com.goblet.level.Position;
import com.goblet.gameEngine.Hitbox;
import com.goblet.graphics.SpriteAnimation;

import java.util.HashMap;

/**
 * Created by Johan on 2016-05-24.
 */
public class Enemy extends Entity{

    private Movement movement;
    private HashMap<Direction, SpriteAnimation> animations = new HashMap<Direction, SpriteAnimation>();
    private Hitbox hitbox;

    public Enemy(int xPos, int yPos, String atlasLocation, int idleFrames, int moveFrames, int attackFrames){
        super(xPos, yPos);
        animations.put(Direction.IDLE, new SpriteAnimation(spriteLocation + atlasLocation + "_idle.pack", idleFrames, 1.0f, 1.0f, 1f));
        animations.put(Direction.DOWN, new SpriteAnimation(spriteLocation + atlasLocation + "_walk.pack", moveFrames, 1.0f, 1.0f, 1/5f));
        animations.put(Direction.ATTACK, new SpriteAnimation(spriteLocation + atlasLocation + "_walk.pack", attackFrames, 1.0f, 1.0f, 1/5f));
        movement = new Movement(50.0f);
        currentAnimation = animations.get(Direction.IDLE);
    }

    public void update(Player player, float deltaTime){
        Position playerPosition = player.getPosition();
        System.out.println("X: " + Math.abs(playerPosition.getX() - position.getX() + currentAnimation.getSpriteWidth()/2));
        if (Math.abs(playerPosition.getX() - (position.getX() + currentAnimation.getSpriteWidth()/2)) < 20.0f){
            movement.setMoveFlag(Direction.LEFT, false);
            movement.setMoveFlag(Direction.RIGHT, false);
        }
        else {
            boolean moveLeft = playerPosition.getX() < position.getX() + currentAnimation.getSpriteWidth()/2;
            movement.setMoveFlag(Direction.LEFT, moveLeft);
            movement.setMoveFlag(Direction.RIGHT, !moveLeft);
        }
        if (Math.abs(playerPosition.getY() - (position.getY() + currentAnimation.getSpriteHeight()/2)) < 20.0f){
        movement.setMoveFlag(Direction.DOWN, false);
        movement.setMoveFlag(Direction.UP, false);
        }
        else {
        boolean moveDown = playerPosition.getY() < position.getY() + currentAnimation.getSpriteHeight()/2;
        movement.setMoveFlag(Direction.DOWN, moveDown);
        movement.setMoveFlag(Direction.UP, !moveDown);
        }
        this.update(deltaTime);
    }

    public void selectAnimation(){
        if (movement.getMoveFlag(Direction.DOWN) || movement.getMoveFlag(Direction.LEFT) || movement.getMoveFlag(Direction.UP) || movement.getMoveFlag(Direction.RIGHT)){
            setAnimation(Direction.DOWN);
        } else {
            setAnimation(Direction.IDLE);
        }
    }

    private void setAnimation(Direction dir) {
        if (currentAnimation != animations.get(dir)) {
            currentAnimation = animations.get(dir);
            timeSinceAnimationStart = 0;
        }
    }

    @Override
    public void update(float deltaTime) {
        selectAnimation();
        timeSinceAnimationStart += deltaTime;
        position.setPosition(position.getX() + deltaTime*movement.getMovementX(), position.getY() + deltaTime*movement.getMovementY());
        //hitbox.updatePosition(position);
    }
}
