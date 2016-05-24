package com.goblet.gameEngine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.goblet.level.Position;
/**
 * Created by Johan on 2016-05-24.
 */
public class Hitbox {

    private Position position;
    private float width;
    private float height;
    private ShapeRenderer shapeRenderer;
    private boolean drawHitbox;
    private float offsetX;
    private float offsetY;

    public Hitbox(Position position, float width, float height, float offsetX, float offsetY){
        this.position = new Position(position);
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        shapeRenderer = new ShapeRenderer();
    }

    public boolean collides(Hitbox otherHitbox){
        boolean xCollides = false;
        boolean yCollides = false;
        if (position.getX() > otherHitbox.getX() && position.getX() < otherHitbox.getX() + otherHitbox.getWidth()){
            xCollides = true;
        } else if (otherHitbox.getX() > position.getX() && otherHitbox.getX() < position.getX() + width) {
            xCollides = true;
        }
        if (position.getY() > otherHitbox.getY() && position.getY() < otherHitbox.getY() + otherHitbox.getHeight()){
            yCollides = true;
        } else if (otherHitbox.getY() > position.getY() && otherHitbox.getY() < position.getY() + height) {
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

    public boolean getDrawFlag(){
        return drawHitbox;
    }

    public void setDrawFlag(boolean drawHitbox){
        this.drawHitbox = drawHitbox;
    }

    public void draw(Batch batch, Color color){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(position.getX(), position.getY(), width, height);
        shapeRenderer.end();
    }

}
