package com.goblet.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.goblet.level.Box;
import com.goblet.graphics.SpriteAnimation;
import com.goblet.level.Position;

import java.util.HashMap;

/**
 * En abstraktklass för olika karaktärer i spelet.
 *
 * Klassen använder sig av många mindre klasser för att hålla det simpelt.
 *
 * Den har ett movementobjekt som sköter matematiska beräkningar för rörelser (håller rörelsehastigheten konstant även
 * diagonalt.)
 *
 * Den har ett positionsobjekt för att hålla reda på karaktärens position.
 *
 * Den har en HashMap som innehåller olika animationer för karaktären, och ett fält för den animationen som spelas nu.
 *
 * Den har metoder för att rita ut animationerna och uppdatera positionen för karaktären.
 *
 * Klasserna som ärver denna klass lägger till sina egna metoder för att bestämma vilken animation som ska användas.
 *
 * Created by Johan on 2016-05-22.
 */
public abstract class Entity {

    protected Box box;
    protected Movement movement;
    protected Position position;

    protected float timeSinceAnimationStart;
    protected String spriteLocation = "assets/sprites/";
    protected SpriteAnimation currentAnimation;
    protected HashMap<Direction, SpriteAnimation> animations = new HashMap<Direction, SpriteAnimation>();

    public Entity(float xPos, float yPos){
        position = new Position(xPos, yPos);
    }

    public void draw(Batch batch){
        currentAnimation.draw(batch, position.getX() - currentAnimation.getSpriteWidth()/2, position.getY() - currentAnimation.getSpriteHeight()/2, timeSinceAnimationStart);
    }

    /**
     * Sätter animationen för spelaren.
     * Om animationen redan är startad ska den inte startas om, så därför kollar metoden
     * först om animationen redan körs.
     * @param dir Vilken animation som ska spelas.
     */
    protected void setAnimation(Direction dir){
        if (currentAnimation != animations.get(dir)) {
            currentAnimation = animations.get(dir);
            timeSinceAnimationStart = 0;
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

    /**
     * Uppdaterar spelarens position och animation.
     * @param deltaTime Hur lång tid som har passerat sedan senaste uppdateringen.
     */
    public void update(float deltaTime){
        timeSinceAnimationStart += deltaTime;
        position.setPosition(position.getX() + deltaTime*movement.getMovementX(), position.getY() + deltaTime*movement.getMovementY());
    }


    /**
     * Returnerar karaktärens position.
     * @return Karaktärens position.
     */
    public Position getPosition(){
        return position;
    }

    /**
     * Ändrar karaktärens position.
     */
    public void setPosition(Position newPosition){
        position = newPosition;
    }

}
