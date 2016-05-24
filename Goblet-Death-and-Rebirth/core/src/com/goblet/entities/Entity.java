package com.goblet.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.goblet.graphics.SpriteAnimation;
import com.goblet.level.Position;

/**
 * Created by Johan on 2016-05-22.
 */
public abstract class Entity {

    protected float moveSpeed = 100.0f;
    protected float timeSinceAnimationStart;
    protected String spriteLocation = "assets/sprites/";
    protected Position position;
    protected SpriteAnimation currentAnimation;

    public Entity(float xPos, float yPos){
        position = new Position(xPos, yPos);
    }

    public void draw(Batch batch){
        currentAnimation.draw(batch, position.getX(), position.getY(), timeSinceAnimationStart);
    }

    public abstract void update(float deltaTime);

}
