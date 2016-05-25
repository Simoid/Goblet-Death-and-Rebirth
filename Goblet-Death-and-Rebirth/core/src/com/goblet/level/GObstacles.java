package com.goblet.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.goblet.gameEngine.Box;

/**
 * Created by Johan on 2016-05-25.
 */
public abstract class GObstacles {

    protected Position position;
    protected float width;
    protected float height;
    protected Box hitbox;
    protected TextureAtlas atlas;
    protected TextureRegion region;

    public GObstacles(float posX, float posY, float boxWidth, float boxHeight, String atlasLocation){
        this.position = new Position(posX, posY);
        this.width = boxWidth;
        this.height = boxHeight;
        this.hitbox = new Box(new Position(position.getX() + boxWidth/2, position.getY() + boxHeight/2), boxWidth, boxHeight, 0, 0);
        atlas = new TextureAtlas(Gdx.files.internal(atlasLocation));
    }

    public Position getPosition(){
        return position;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public Box getHitbox(){
        return hitbox;
    }

    public void draw(Batch batch){
        batch.draw(region, position.getX(), position.getY());
        //hitbox.draw(batch);
    }

}
