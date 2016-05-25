package com.goblet.entities;

import com.goblet.gameEngine.Box;
import com.goblet.graphics.SpriteAnimation;
import com.goblet.level.Position;

/**
 * En klass för spelaren som objekt.
 * Innehåller spelarens position och dess animationer.
 * Created by Johan on 2016-05-14.
 */
public class Player extends Entity{

    /**
     * Konstruktorn för player.
     * @param xPos Startpositionen i x-led.
     * @param yPos Startpositionen i y-led.
     */
    public Player(int xPos, int yPos, float moveSpeed){
        super(new Position(xPos, yPos), new Box(new Position(xPos, yPos), 11f, 19f));

        animations.put(Direction.DOWN, new SpriteAnimation(spriteLocation + "mc/mc_move_down.pack", 4, 1/5f, true));
        animations.put(Direction.UP, new SpriteAnimation(spriteLocation + "mc/mc_move_up.pack", 4, 1/5f, true));
        animations.put(Direction.LEFT, new SpriteAnimation(spriteLocation + "mc/mc_move_left.pack", 4, 1/5f, true));
        animations.put(Direction.RIGHT, new SpriteAnimation(spriteLocation + "mc/mc_move_right.pack", 4, 1/5f, true));
        animations.put(Direction.IDLE, new SpriteAnimation(spriteLocation + "mc/mc_idle.pack", 2, 1f, true));

        currentAnimation = animations.get(Direction.IDLE);

        movement = new Movement(moveSpeed);
        timeSinceAnimationStart = 0;

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
