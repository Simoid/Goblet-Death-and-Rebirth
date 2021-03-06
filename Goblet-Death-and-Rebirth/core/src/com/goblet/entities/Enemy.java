package com.goblet.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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
    private float maxHealth;
    private float currentHealth;
    private float damageTaken;
    private Texture healthBar;
    private Texture currentHealthBar;
    private boolean damageCooldown;
    private float damageCooldownCounter;
    private AttackType attackType;
    private boolean flight;
    private int score;
    private Sound damageSound;

    /**
     * Konstruktorn för fiendeklassen.
     * @param atlasLocation Pathen till filerna som fienden ska använda sig av.
     * @param moveFrames Hur många frames fiendens animation har när den rör sig.
     * @param attackFrames Hur många frames fiendens animation har när den attackerar.
     * @param movementSpeed Fiendens rörelsehastighet.
     */
    public Enemy(Position position, String atlasLocation, int moveFrames, int attackFrames, int score, float movementSpeed, float attackRange, AttackType attackType,  Box hitBox,
                 float moveAnimationSpeed, float attackAnimationSpeed, float maxHealth, float damageTaken, boolean flight){
        super(position, hitBox);
        this.attackRange = attackRange;
        this.attackSpeed = attackAnimationSpeed * attackFrames;
        this.attackType = attackType;
        this.score = score;
        this.flight = flight;
        animations.put(Direction.DOWN, new SpriteAnimation(spriteLocation + atlasLocation + "_move.pack", moveFrames,  moveAnimationSpeed,true));

        if (attackType == AttackType.MELEEAREA) {
            animations.put(Direction.ATTACK, new SpriteAnimation(spriteLocation + atlasLocation + "_attack.pack", attackFrames, attackAnimationSpeed, false));
        }
        movement = new Movement(movementSpeed);
        currentAnimation = animations.get(Direction.DOWN);

        this.maxHealth = maxHealth/10;
        this.currentHealth = this.maxHealth;
        this.damageTaken = damageTaken/10;
        healthBar = new Texture(Gdx.files.internal("assets/sprites/hitbox/emptyHealthBarSmall.png"));
        currentHealthBar = new Texture(Gdx.files.internal("assets/sprites/hitbox/fullHealthBarSmall.png"));
        damageCooldownCounter = 0;
        damageSound = Gdx.audio.newSound(Gdx.files.internal("assets/audio/enemy_damage.ogg"));
    }


    /**
     * Uppdaterar fienden och flyttar den mot spelaren.
     * @param player Objektet för spelaren.
     * @param deltaTime Tiden som har passerat sen senaste uppdateringen.
     */
    public void updateTowardsPlayer(Player player, float deltaTime){
        Position playerPosition = player.getPosition();
        if (damageCooldown){
            damageCooldownCounter += deltaTime;
            if (damageCooldownCounter >= 0.6){
                damageCooldown = false;
            }
        }
        if (attackType == attackType.MELEEAREA && currentAnimation == animations.get(Direction.ATTACK) && !currentAnimation.isAnimationComplete(timeSinceAnimationStart)){
            timeSinceAnimationStart += deltaTime;
            timeSinceDamageTaken += deltaTime;
            damageCooldownCounter += deltaTime;
            return;
        }
        if (Math.abs(player.getHitbox().getMiddlePos().getX() - (hitbox.getMiddlePos().getX())) < attackRange/Math.sqrt(2)){
            movement.setMoveFlag(Direction.LEFT, false);
            movement.setMoveFlag(Direction.RIGHT, false);
        }
        else {
            boolean moveLeft = playerPosition.getX() < position.getX();
            movement.setMoveFlag(Direction.LEFT, moveLeft);
            movement.setMoveFlag(Direction.RIGHT, !moveLeft);
        }
        if (Math.abs(player.getHitbox().getMiddlePos().getY() - (hitbox.getMiddlePos().getY())) < attackRange/Math.sqrt(2)){
        movement.setMoveFlag(Direction.DOWN, false);
        movement.setMoveFlag(Direction.UP, false);
        }
        else {
        boolean moveDown = playerPosition.getY() < position.getY();
        movement.setMoveFlag(Direction.DOWN, moveDown);
        movement.setMoveFlag(Direction.UP, !moveDown);
        }
        selectAnimation(player);
        if (attackType == AttackType.MOVEMENT && hitbox.collides(player.getHitbox())){
            player.takeDamage();
        }
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
        if (attackType == AttackType.MELEEAREA) {
            if (hitbox.collides(player.getHitbox())) {
                setAnimation(Direction.ATTACK);
                timeSinceAnimationStart = 0;
            } else if (movement.getMoveFlag(Direction.DOWN) || movement.getMoveFlag(Direction.LEFT) || movement.getMoveFlag(Direction.UP) || movement.getMoveFlag(Direction.RIGHT)) {
                setAnimation(Direction.DOWN);
            } else {
                setAnimation(Direction.ATTACK);
                timeSinceAnimationStart = 0;
            }
        }
    }

    public boolean canFly(){
        return flight;
    }

    public void takeDamage(){
        if (!damageCooldown) {
            damageSound.play(0.5f);
            damageCooldown = true;
            damageCooldownCounter = 0;
            currentHealth -= damageTaken;
            if (currentHealth <= 0) {
                die();
            }
        }
    }

    public int getScoreGain(){
        return score;
    }

    /**
     * Ritar ut fienden.
     * @param batch Batchen som ska ritas ut på.
     */
    public void draw(Batch batch){
        if (!damageCooldown || (int)(timeSinceDamageTaken * 20)%2 == 0) {
            currentAnimation.draw(batch, position.getX() - currentAnimation.getSpriteWidth() / 2, position.getY() - currentAnimation.getSpriteHeight() / 2, timeSinceAnimationStart);
        } else {
            batch.setColor(1, 1, 1, 0.5f);
            currentAnimation.draw(batch, position.getX() - currentAnimation.getSpriteWidth() / 2, position.getY() - currentAnimation.getSpriteHeight() / 2, timeSinceAnimationStart);
            batch.setColor(1, 1, 1, 1);
        }
        //hitbox.draw(batch);
        if(currentHealth != maxHealth) {
            batch.draw(healthBar, position.getX() - maxHealth / 2 - 1, position.getY() - currentAnimation.getSpriteHeight() / 4, maxHealth +2, 4);
            if (currentHealth > 0) {
                batch.draw(currentHealthBar, position.getX() - maxHealth / 2 , position.getY() - currentAnimation.getSpriteHeight() / 4, currentHealth, 4);
            }
        }
    }

}
