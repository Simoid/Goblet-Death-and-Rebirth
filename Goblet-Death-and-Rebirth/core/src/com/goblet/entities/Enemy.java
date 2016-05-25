package com.goblet.entities;

import com.goblet.level.Box;
import com.goblet.level.Position;
import com.goblet.graphics.SpriteAnimation;

/**
 * En klass för fienderna i spelet.
 *
 * Innehåller metoder för att röra sig mot spelarens position.
 *
 * Created by Johan on 2016-05-24.
 */
public class Enemy extends Entity{


    /**
     * Konstruktorn för fiendeklassen.
     * @param atlasLocation Pathen till filerna som fienden ska använda sig av.
     * @param moveFrames Hur många frames fiendens animation har när den rör sig.
     * @param attackFrames Hur många frames fiendens animation har när den attackerar.
     * @param movementSpeed Fiendens rörelsehastighet.
     */
    public Enemy(Position position, String atlasLocation, int moveFrames, int attackFrames, float movementSpeed, int health, int damage, String moveType, String attackType,  Box hitBox){
        super(position);
        //animations.put(Direction.IDLE, new SpriteAnimation(spriteLocation + atlasLocation + "_idle.pack", idleFrames, 1.0f, 1.0f, 1f));
        animations.put(Direction.DOWN, new SpriteAnimation(spriteLocation + atlasLocation + "_walk.pack", moveFrames, 1.0f, 1.0f, 1/5f));
        animations.put(Direction.ATTACK, new SpriteAnimation(spriteLocation + atlasLocation + "_attack.pack", attackFrames, 1.0f, 1.0f, 1f/attackFrames));
        movement = new Movement(movementSpeed);
        currentAnimation = animations.get(Direction.DOWN);
    }

    /**
     * Uppdaterar fienden och flyttar den mot spelaren.
     * @param player Objektet för spelaren.
     * @param deltaTime Tiden som har passerat sen senaste uppdateringen.
     */
    public void updateTowardsPlayer(Player player, float deltaTime){
        Position playerPosition = player.getPosition();
        if (Math.abs(playerPosition.getX() - (position.getX())) < 20.0f){
            movement.setMoveFlag(Direction.LEFT, false);
            movement.setMoveFlag(Direction.RIGHT, false);
        }
        else {
            boolean moveLeft = playerPosition.getX() < position.getX();
            movement.setMoveFlag(Direction.LEFT, moveLeft);
            movement.setMoveFlag(Direction.RIGHT, !moveLeft);
        }
        if (Math.abs(playerPosition.getY() - (position.getY())) < 20.0f){
        movement.setMoveFlag(Direction.DOWN, false);
        movement.setMoveFlag(Direction.UP, false);
        }
        else {
        boolean moveDown = playerPosition.getY() < position.getY();
        movement.setMoveFlag(Direction.DOWN, moveDown);
        movement.setMoveFlag(Direction.UP, !moveDown);
        }
        selectAnimation();
        this.update(deltaTime);
    }

    public void attack(String attackType, int damage){

    }

    /**
     * Väljer animation för fienden, beroende på i fall den attackerar, rör sig eller står still.
     */
    public void selectAnimation(){
        if (movement.getMoveFlag(Direction.DOWN) || movement.getMoveFlag(Direction.LEFT) || movement.getMoveFlag(Direction.UP) || movement.getMoveFlag(Direction.RIGHT)){
            setAnimation(Direction.DOWN);
        } else {
            setAnimation(Direction.ATTACK);
        }
    }

}
