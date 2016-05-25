package com.goblet.gameEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.goblet.level.Position;

/**
 * Created by Johan on 2016-05-24.
 */
public class Box {

    private Position position;
    private float width;
    private float height;
    private Texture texture;

    public Box(Position position, float width, float height) {
        this.position = position;
        this.width = width;
        this.height = height;
        texture = new Texture(Gdx.files.internal("assets/sprites/hitbox/hitbox.png"));
    }

    public boolean collides(Box otherBox){
        boolean xCollides = false;
        boolean yCollides = false;
        if (this.getX() > otherBox.getX() && this.getX() < otherBox.getX() + otherBox.getWidth()){
            xCollides = true;
        } else if (otherBox.getX() > this.getX() && otherBox.getX() < this.getX() + width) {
            xCollides = true;
        }
        if (this.getY() > otherBox.getY() && this.getY() < otherBox.getY() + otherBox.getHeight()){
            yCollides = true;
        } else if (otherBox.getY() > this.getY() && otherBox.getY() < this.getY() + height) {
            yCollides = true;
        }
        return (xCollides && yCollides);
    }

    public void updatePosition(Position newPosition){
        this.position = newPosition;
    }

    public float getX(){
        return position.getX() - width/2;
    }

    public float getY(){
        return position.getY() - height/2;
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

    public void draw(Batch batch){
        batch.draw(texture, position.getX() - width/2, position.getY()-height/2, width, height);
    }
}
