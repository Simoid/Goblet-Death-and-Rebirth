package com.goblet.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Johan on 2016-05-24.
 */
public class Box {

    private Position position;
    private float width;
    private float height;
    private float offsetX;
    private float offsetY;

    public Box(Position position, float width, float height, float offsetX, float offsetY) {
        this.position = new Position(position);
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public boolean collides(Box otherBox){
        boolean xCollides = false;
        boolean yCollides = false;
        if (position.getX() > otherBox.getX() && position.getX() < otherBox.getX() + otherBox.getWidth()){
            xCollides = true;
        } else if (otherBox.getX() > position.getX() && otherBox.getX() < position.getX() + width) {
            xCollides = true;
        }
        if (position.getY() > otherBox.getY() && position.getY() < otherBox.getY() + otherBox.getHeight()){
            yCollides = true;
        } else if (otherBox.getY() > position.getY() && otherBox.getY() < position.getY() + height) {
            yCollides = true;
        }
        return (xCollides && yCollides);
    }

    public void updatePosition(Position playerPosition){
        position.setPosition(playerPosition.getX() - offsetX, playerPosition.getY() - offsetY);
    }

    public float getX(){
        return position.getX();
    }

    public float getY(){
        return position.getY();
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public boolean containsPosition(Position position){
        return position.getX() < this.position.getX() + this.width && position.getX() > this.position.getX() &&
                position.getY() < this.position.getY() + this.height && position.getY() > this.position.getY();
    }
}
