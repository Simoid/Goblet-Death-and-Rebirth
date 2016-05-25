package com.goblet.entities;

import com.goblet.gameEngine.Box;
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

    private float attackRange;
    private float attackSpeed;

    /**
     * Konstruktorn för fiendeklassen.
     * @param atlasLocation Pathen till filerna som fienden ska använda sig av.
     * @param moveFrames Hur många frames fiendens animation har när den rör sig.
     * @param attackFrames Hur många frames fiendens animation har när den attackerar.
     * @param movementSpeed Fiendens rörelsehastighet.
     */
    public Enemy(Position position, String atlasLocation, int moveFrames, int attackFrames, float movementSpeed, int health, float attackRange, String moveType, String attackType,  Box hitBox,
                 float moveAnimationSpeed, float attackAnimationSpeed){
        super(position, hitBox);
        this.attackRange = attackRange;
        this.attackSpeed = attackAnimationSpeed * attackFrames;
        animations.put(Direction.DOWN, new SpriteAnimation(spriteLocation + atlasLocation + "_walk.pack", moveFrames,  moveAnimationSpeed,true));
        animations.put(Direction.ATTACK, new SpriteAnimation(spriteLocation + atlasLocation + "_attack.pack", attackFrames,attackAnimationSpeed,false));
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
        if (currentAnimation == animations.get(Direction.ATTACK) && !currentAnimation.isAnimationComplete(timeSinceAnimationStart)){
            timeSinceAnimationStart += deltaTime;
            return;
        }
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
        selectAnimation(player);
        this.update(deltaTime);
    }

    /**
     * Returnerar en boolean som beksriver om fienden har kommit tillräckligt långt i en attackanimation för att göra skada.
     * @return true om fiender har gjort 2/3 av sin attack, false annars.
     */
    public boolean isAttacking(){
        return currentAnimation == animations.get(Direction.ATTACK) && timeSinceAnimationStart > attackSpeed * 2 / 3;
    }

    public float getAttackRange(){
        return attackRange;
    }

    /**
     * Väljer animation för fienden, beroende på i fall den attackerar, rör sig eller står still.
     */
    public void selectAnimation(Player player){
        if (hitbox.collides(player.getHitbox())){
            setAnimation(Direction.ATTACK);
            timeSinceAnimationStart = 0;
        }
        else if (movement.getMoveFlag(Direction.DOWN) || movement.getMoveFlag(Direction.LEFT) || movement.getMoveFlag(Direction.UP) || movement.getMoveFlag(Direction.RIGHT)){
            setAnimation(Direction.DOWN);
        } else {
            setAnimation(Direction.ATTACK);
            timeSinceAnimationStart = 0;
        }
    }

}
