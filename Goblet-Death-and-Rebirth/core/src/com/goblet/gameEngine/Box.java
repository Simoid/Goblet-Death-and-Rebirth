package com.goblet.gameEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.Map;
import com.goblet.entities.Direction;
import com.goblet.level.Position;

import java.util.Collections;
import java.util.HashMap;

/**
 * En klass som beskriver en rektangel och har metoder för att upptäcka kollisioner.
 *
 * Klassen är tänkt att vara använd av andra klasser som ritar ut textures men vill ha en hitbox som inte
 * är exakt samma storlek som texturen.
 *
 * Created by Johan on 2016-05-24.
 */
public class Box {

    private Position position;
    private Position middlePos;
    private float width;
    private float height;
    private float offsetX;
    private float offsetY;
    private Texture blueSquare;
    private Texture redSquare;

    /**
     * Konstruktorn för klassen.
     * @param position Positionen av texturen som ska ha en hitbox.
     * @param width Bredden på hitboxen.
     * @param height Höjden på hitboxen.
     * @param offsetX Hur stor förskjutning hitboxen ska ha i x-led jämfört med position.
     * @param offsetY Hur stor förskjutning hitboxen ska ha i y-led jämfört med position.
     */
    public Box(Position position, float width, float height, float offsetX, float offsetY) {
        this.position = position;
        middlePos = new Position(position.getX() + offsetX, position.getY() + offsetY);
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        blueSquare = new Texture(Gdx.files.internal("assets/sprites/hitbox/hitbox.png"));
        redSquare = new Texture(Gdx.files.internal("assets/sprites/hitbox/position.png"));
    }

    /**
     * Kollar i fall en hitbox kolliderar med en annan.
     * @param otherBox Den andra hitboxen.
     * @return true om hitboxerna överlappar varandra, false annars.
     */
    public boolean collides(Box otherBox){
        boolean xCollides = false;
        boolean yCollides = false;
        if (this.getX() >= otherBox.getX() && this.getX() <= otherBox.getX() + otherBox.getWidth()){
            xCollides = true;
        } else if (otherBox.getX() >= this.getX() && otherBox.getX() <= this.getX() + width) {
            xCollides = true;
        }
        if (this.getY() >= otherBox.getY() && this.getY() <= otherBox.getY() + otherBox.getHeight()){
            yCollides = true;
        } else if (otherBox.getY() >= this.getY() && otherBox.getY() <= this.getY() + height) {
            yCollides = true;
        }
        return (xCollides && yCollides);
    }

    /**
     * Uppdaterar hitboxens position.
     * @param newPosition Den nya positionen för texturen som hitboxen används till.
     */
    public void updatePosition(Position newPosition){
        this.position = newPosition;
        middlePos.setPosition(position.getX() + offsetX, position.getY() + offsetY);
    }

    public float getOffsetX(){
        return offsetX;
    }

    public float getOffsetY(){
        return offsetY;
    }

    public float getX(){
        return position.getX() - width/2 + offsetX;
    }

    public float getY(){
        return position.getY() - height/2 + offsetY;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public Position getMiddlePos(){
        return middlePos;
    }

    /**
     * Ritar ut hitboxen. Används bara för debugging.
     * @param batch
     */
    public void draw(Batch batch){
        batch.draw(blueSquare, this.getX(), this.getY(), width, height);
    }

    /**
     * Ritar ut en prick i mitten av hitboxen. Används bara för debugging.
     * @param batch
     */
    public void drawPosition(Batch batch){
        batch.draw(redSquare, this.getX(), this.getY(), 1, 1);
        batch.draw(redSquare, this.getX() + this.getWidth(), this.getY() + this.getWidth(), 1, 1);
    }

    public void dispose(){
        redSquare.dispose();
        blueSquare.dispose();
    }

    public Direction getRelativeXDirectionTo(Box otherBox){
        if (this.getMiddlePos().getX() < otherBox.getMiddlePos().getX()){
            return Direction.LEFT;
        } else {
            return Direction.RIGHT;
        }
    }

    public Direction getBiggestIntersectionDirection(Box otherBox){
        HashMap<Direction, Float> intersectMap = new HashMap<Direction, Float>();
        intersectMap.put(Direction.RIGHT, this.getX() + this.getWidth() - otherBox.getX());
        intersectMap.put(Direction.LEFT, otherBox.getX() + otherBox.getWidth() - this.getX());
        intersectMap.put(Direction.UP, this.getY() + this.getHeight() - otherBox.getY());
        intersectMap.put(Direction.DOWN, otherBox.getY() + otherBox.getHeight() - this.getY());
        float minValueInMap=(Collections.min(intersectMap.values()));
        for (java.util.Map.Entry<Direction, Float> entry : intersectMap.entrySet()){
            if (entry.getValue() == minValueInMap){
                return entry.getKey();
            }
        }
        return Direction.IDLE;
    }

}
