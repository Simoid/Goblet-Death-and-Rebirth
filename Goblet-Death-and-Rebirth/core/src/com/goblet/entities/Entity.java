package com.goblet.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.goblet.level.Position;

/**
 * Created by Johan on 2016-05-22.
 */
public abstract class Entity {

    protected float moveSpeed = 100.0f;
    protected float timeSinceAnimationStart;
    protected String spriteLocation = "assets/sprites/";
    protected Position position;

    public Entity(float xPos, float yPos){
        position = new Position(xPos, yPos);
    }

    public abstract void draw(Batch batch);

    public abstract void update(float deltaTime);

}
