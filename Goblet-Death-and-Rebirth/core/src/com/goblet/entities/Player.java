package com.goblet.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.goblet.gameEngine.Hitbox;
import com.goblet.graphics.SpriteAnimation;
import com.goblet.level.Position;
import com.goblet.level.Room;


import java.util.HashMap;

/**
 * En klass för spelaren som objekt.
 * Innehåller spelarens position och dess animationer, samt håller reda på vad som ska
 * hända när piltangenterna används.
 * Created by Johan on 2016-05-14.
 */
public class Player extends Entity{

    private Movement movement;

    private HashMap<Direction, SpriteAnimation> animations = new HashMap<Direction, SpriteAnimation>();
    private Hitbox hitbox;


    /**
     * Konstruktorn
     * @param xPos
     * @param yPos
     */
    public Player(int xPos, int yPos){
        super(xPos, yPos);

        animations.put(Direction.DOWN, new SpriteAnimation(spriteLocation + "mc/mc_move_down.pack", 4, 1.0f, 1.0f, 1/5f));
        animations.put(Direction.UP, new SpriteAnimation(spriteLocation + "mc/mc_move_up.pack", 4, 1.0f, 1.0f, 1/5f));
        animations.put(Direction.LEFT, new SpriteAnimation(spriteLocation + "mc/mc_move_left.pack", 4, 1.0f, 1.0f, 1/5f));
        animations.put(Direction.RIGHT, new SpriteAnimation(spriteLocation + "mc/mc_move_right.pack", 4, 1.0f, 1.0f, 1/5f));
        animations.put(Direction.IDLE, new SpriteAnimation(spriteLocation + "mc/mc_idle.pack", 2, 1.0f, 1.0f, 1f));

        currentAnimation = animations.get(Direction.IDLE);

        movement = new Movement(moveSpeed);
        timeSinceAnimationStart = 0;

        hitbox = new Hitbox(position, 11f, 19f, 2f, 4f);
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
     * Sätter animationen för spelaren.
     * Om animationen redan är startad ska den inte startas om, så därför kollar metoden
     * först om animationen redan körs.
     * @param dir Vilken animation som ska spelas.
     */
    private void setAnimation(Direction dir){
        if (currentAnimation != animations.get(dir)) {
            currentAnimation = animations.get(dir);
            timeSinceAnimationStart = 0;
        }
    }

    /**
     * Uppdaterar spelarens position och animation.
     * @param deltaTime Hur lång tid som har passerat sedan senaste uppdateringen.
     */
    public void update(float deltaTime){
        timeSinceAnimationStart += deltaTime;
        position.setPosition(position.getX() + deltaTime*movement.getMovementX(), position.getY() + deltaTime*movement.getMovementY());
        hitbox.updatePosition(position);
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

    /**
     * Ändrar spelarens position.
     */
    private void setPosition(Position newPosition){
        position = newPosition;
    }

    public Position getPosition(){
        return position;
    }

    public void shouldDrawHitbox(){
        if (!hitbox.getDrawFlag()) {
            hitbox.setDrawFlag(true);
        } else {
            hitbox.setDrawFlag(false);
        }
    }

    /**
     * Tar bort de objekt som javas GC inte tar hand om från minnet.
     */
    public void dispose(){
        for (SpriteAnimation animation : animations.values()){
            animation.dispose();
        }
    }
}
