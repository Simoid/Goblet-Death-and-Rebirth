package com.goblet.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.goblet.gameEngine.Box;
import com.goblet.graphics.SpriteAnimation;
import com.goblet.level.Position;

/**
 * En klass för spelaren som objekt.
 * Innehåller spelarens position och dess animationer.
 * Created by Johan on 2016-05-14.
 */
public class Player extends Entity{

    private final float damageCooldown = 2.0f;

    private int hP;
    private int maxHP;
    private Box HorizontalAttack;
    private Box VerticalAttack;
    private SpriteAnimation attackRight;
    private SpriteAnimation attackLeft;
    private SpriteAnimation attackUp;
    private SpriteAnimation attackDown;
    private SpriteAnimation currentAttack;
    private float timeSinceAttackAnimation;

    /**
     * Konstruktorn för player.
     * @param xPos Startpositionen i x-led.
     * @param yPos Startpositionen i y-led.
     */
    public Player(int xPos, int yPos, float moveSpeed){
        super(new Position(xPos, yPos), new Box(new Position(xPos, yPos), 13f, 25f, 0, 0));
        attackRight = new SpriteAnimation(spriteLocation + "mc/mc_attack_right.pack",4,1/9f,false);
        attackLeft = new SpriteAnimation(spriteLocation + "mc/mc_attack_Left.pack",4,1/9f,false);
        attackUp = new SpriteAnimation(spriteLocation + "mc/mc_attack_Up.pack",4,1/9f,false);
        attackDown = new SpriteAnimation(spriteLocation + "mc/mc_attack_Down.pack",4,1/9f,false);

        HorizontalAttack = new Box(this.position,27,18,0,0);
        VerticalAttack = new Box(this.position,18,27,0,0);

        maxHP = 3;
        hP = maxHP;

        animations.put(Direction.DOWN, new SpriteAnimation(spriteLocation + "mc/mc_move_down.pack", 4, 1/5f, true));
        animations.put(Direction.UP, new SpriteAnimation(spriteLocation + "mc/mc_move_up.pack", 4, 1/5f, true));
        animations.put(Direction.LEFT, new SpriteAnimation(spriteLocation + "mc/mc_move_left.pack", 4, 1/5f, true));
        animations.put(Direction.RIGHT, new SpriteAnimation(spriteLocation + "mc/mc_move_right.pack", 4, 1/5f, true));
        animations.put(Direction.IDLE, new SpriteAnimation(spriteLocation + "mc/mc_idle.pack", 2, 1f, true));

        currentAnimation = animations.get(Direction.IDLE);

        movement = new Movement(moveSpeed);
        timeSinceAttackAnimation = 0;
        timeSinceAnimationStart = 0;
        timeSinceDamageTaken = 0;

    }

    @Override
    public void draw(Batch batch){

        attackRight.draw(batch,position.getX() ,position.getY(),timeSinceAttackAnimation);
        currentAnimation.draw(batch, position.getX() - currentAnimation.getSpriteWidth()/2, position.getY() - currentAnimation.getSpriteHeight()/2, timeSinceAnimationStart);
        //hitbox.draw(batch);
        //hitbox.drawPosition(batch);
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
        if (dir == Direction.IDLE){
            return;
        }
        if (!movement.getMoveFlag(dir)){
            movement.setMoveFlag(dir, true);
            selectAnimation();
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
     * Metod som anropas när en tangent släpps.
     * Om spelaren rör sig åt det hållet som tangenten pekar åt så slutar spelaren att röra sig åt det hållet.
     * @param keycode Tangenten som släpptes.
     */
    public void keyReleased(int keycode){
        Direction dir = Direction.keyCodeTranslate(keycode);
        if (dir == Direction.IDLE){
            return;
        }
        if (movement.getMoveFlag(dir)){
            movement.setMoveFlag(dir, false);
            selectAnimation();
        }
    }

}
