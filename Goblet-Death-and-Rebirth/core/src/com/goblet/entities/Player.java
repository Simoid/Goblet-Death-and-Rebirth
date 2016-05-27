package com.goblet.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.goblet.gameEngine.Box;
import com.goblet.graphics.SpriteAnimation;
import com.goblet.level.Position;

import java.util.HashMap;

/**
 * En klass för spelaren som objekt.
 * Innehåller spelarens position och dess animationer.
 * Created by Johan on 2016-05-14.
 */
public class Player extends Entity{

    private final float damageCooldown = 1.2f;

    private int hP;
    private int maxHP;
    private Box HorizontalAttack;
    private Box VerticalAttack;
    private HashMap<Direction, SpriteAnimation> attackAnimations;
    private float timeSinceAttackAnimation;
    private boolean attackFlag;
    private boolean keepAttacking;
    private Position attackPosition;
    private Direction currentAttackDirection;
    private SpriteAnimation currentAttackAnimation;

    /**
     * Konstruktorn för player.
     * @param xPos Startpositionen i x-led.
     * @param yPos Startpositionen i y-led.
     */
    public Player(int xPos, int yPos, float moveSpeed){
        super(new Position(xPos, yPos), new Box(new Position(xPos, yPos), 13f, 25f, 0, 0));
        attackAnimations = new HashMap<Direction, SpriteAnimation>();
        attackAnimations.put(Direction.RIGHT, new SpriteAnimation(spriteLocation + "mc/mc_attack_right.pack",4,1/9f, false));
        attackAnimations.put(Direction.LEFT, new SpriteAnimation(spriteLocation + "mc/mc_attack_left.pack",4,1/9f, false));
        attackAnimations.put(Direction.UP, new SpriteAnimation(spriteLocation + "mc/mc_attack_up.pack",4,1/9f, false));
        attackAnimations.put(Direction.DOWN, new SpriteAnimation(spriteLocation + "mc/mc_attack_down.pack",4,1/9f, false));
        attackAnimations.put(Direction.IDLE, new SpriteAnimation(spriteLocation + "mc/mc_attack_down.pack",4,1/9f, false));
        currentAttackAnimation = animations.get(Direction.DOWN);

        for (SpriteAnimation anim : attackAnimations.values()){
            anim.changeScale(2.0f);
        }

        HorizontalAttack = new Box(this.position,27*2.0f,18*2.0f,0,0);
        VerticalAttack = new Box(this.position,18*2.0f,27*2.0f,0,0);
        attackPosition = new Position(0, 0);

        maxHP = 3;
        hP = maxHP;

        animations.put(Direction.DOWN, new SpriteAnimation(spriteLocation + "mc/mc_move_down.pack", 4, 1/5f, true));
        animations.put(Direction.UP, new SpriteAnimation(spriteLocation + "mc/mc_move_up.pack", 4, 1/5f, true));
        animations.put(Direction.LEFT, new SpriteAnimation(spriteLocation + "mc/mc_move_left.pack", 4, 1/5f, true));
        animations.put(Direction.RIGHT, new SpriteAnimation(spriteLocation + "mc/mc_move_right.pack", 4, 1/5f, true));
        animations.put(Direction.IDLE, new SpriteAnimation(spriteLocation + "mc/mc_idle.pack", 2, 1f, true));

        currentAnimation = animations.get(Direction.IDLE);
        currentAttackDirection = Direction.IDLE;

        movement = new Movement(moveSpeed);
        timeSinceAttackAnimation = 0;
        timeSinceAnimationStart = 0;
        timeSinceDamageTaken = 0;

    }

    @Override
    public void draw(Batch batch){
        currentAnimation.draw(batch, position.getX() - currentAnimation.getSpriteWidth()/2, position.getY() - currentAnimation.getSpriteHeight()/2, timeSinceAnimationStart);
        if (attackFlag){
            currentAttackAnimation.draw(batch, attackPosition.getX() - currentAttackAnimation.getSpriteWidth()/2, attackPosition.getY() - currentAttackAnimation.getSpriteHeight()/2, timeSinceAttackAnimation);
        }
    }


    /**
     * Uppdaterar karaktärens position och animation.
     * @param deltaTime Hur lång tid som har passerat sedan senaste uppdateringen.
     */
    @Override
    public void update(float deltaTime){
        timeSinceAnimationStart += deltaTime;
        timeSinceDamageTaken += deltaTime;
        if (attackFlag) {
            timeSinceAttackAnimation += deltaTime;
        }
        if (keepAttacking && timeSinceAttackAnimation == 0){
            attackFlag = true;
        }
        selectAnimation();
        if (timeSinceAttackAnimation >= 5/9f){
            attackFlag = false;
            timeSinceAttackAnimation = 0;
        }
        position.setPosition(position.getX() + deltaTime*movement.getMovementX(), position.getY() + deltaTime*movement.getMovementY());
        hitbox.updatePosition(position);
        if (attackFlag) {
            updateAttackHitbox();
        }
    }

    private void updateAttackHitbox(){
        switch(currentAttackDirection){
            default:
            case IDLE:
            case DOWN:
                attackPosition.setPosition(position.getX(), position.getY() - currentAnimation.getSpriteHeight());
                HorizontalAttack.updatePosition(attackPosition);
                break;
            case UP:
                attackPosition.setPosition(position.getX(), position.getY() + currentAnimation.getSpriteHeight());
                HorizontalAttack.updatePosition(attackPosition);
                break;
            case LEFT:
                attackPosition.setPosition(position.getX() - currentAnimation.getSpriteWidth(), position.getY());
                VerticalAttack.updatePosition(attackPosition);
                break;
            case RIGHT:
                attackPosition.setPosition(position.getX() + currentAnimation.getSpriteWidth(), position.getY());
                VerticalAttack.updatePosition(attackPosition);
                break;
        }
    }

    public Box getAttackHitbox(){
        switch(currentAttackDirection){
            default:
            case DOWN:
            case UP:
                return HorizontalAttack;
            case RIGHT:
            case LEFT:
                return VerticalAttack;
        }
    }

    public boolean isAttacking(){
        return attackFlag;
    }

    public Position getPosition(){ return this.position; }

    /**
     * Returnernar hur mycket hp spelaren har.
     * @return Hur mycket hp spelaren har.
     */
    public int getHP(){
        return hP;
    }

    /**
     * Returnerar det maximala hp:et som spelaren kan ha.
     * @return Det maximala hp:et som spelaren kan ha.
     */
    public int getMaxHP(){
        return maxHP;
    }

    /**
     * Gör skada på spelaren.
     */
    public void takeDamage(){
        if (timeSinceDamageTaken > damageCooldown) {
            hP--;
            timeSinceDamageTaken = 0;
        }
    }

    public void attack(){
        return;
    }

    /**
     * Ökar storleken på spelarens sprite.
     */
    public void increaseScale(){
        for(SpriteAnimation animation : animations.values()){
            animation.changeScale(2f);
        }
    }

    /**
     * Minkskar storleken på spelarens sprite.
     */
    public void decreaseScale() {
        for (SpriteAnimation animation : animations.values()) {
            animation.changeScale(0.5f);
        }
    }

    /**
     * Metod som anropas när en tangent trycks ned.
     * Metoden kollar i fall tangenten redan är nedtryckt (till exempel om användaren tabar ut
     * och släpper en tangent, sedan trycker ned den igen) för att säkerställa att en tangent inte
     * räknas två gånger.
     * @param keycode Tangenten som trycktes ned.
     */
    public void keyPressed(int keycode){
        Direction dir = Direction.keyCodeTranslate(keycode);
        if (dir == Direction.ATTACK){
            keepAttacking = true;
        } else if (!movement.getMoveFlag(dir)){
            movement.setMoveFlag(dir, true);
        }
    }

    /**
     * Väljer den animationen som ska spelas.
     * Denna funktion används då flera tangenter kan tryckas samtidigt, och bestämmer en prioritetsordning
     * för de olika riktningarnas animationer:
     * NER, UPP, VÄNSTER, HÖGER, STILLA.
     */
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

    /**
     * Sätter animationen för spelaren.
     * Om animationen redan är startad ska den inte startas om, så därför kollar metoden
     * först om animationen redan körs.
     * @param dir Vilken animation som ska spelas.
     */
    @Override
    protected void setAnimation(Direction dir){
        if (!attackFlag) {
            currentAttackDirection = dir;
        }
        if (currentAnimation != animations.get(dir)) {
            currentAnimation = animations.get(dir);
            timeSinceAnimationStart = 0;
        }
        if (!attackFlag){
            currentAttackAnimation = attackAnimations.get(dir);
        }
    }

    /**
     * Metod som anropas när en tangent släpps.
     * Om spelaren rör sig åt det hållet som tangenten pekar åt så slutar spelaren att röra sig åt det hållet.
     * @param keycode Tangenten som släpptes.
     */
    public void keyReleased(int keycode){
        Direction dir = Direction.keyCodeTranslate(keycode);
        if (dir == Direction.ATTACK){
            keepAttacking = false;
        } else if (movement.getMoveFlag(dir)){
            movement.setMoveFlag(dir, false);
        }
    }

}
