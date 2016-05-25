package com.goblet.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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

    public GObstacles(float posX, float posY, float width, float height, String atlasLocation){
        this.position = new Position(posX, posY);
        this.width = width;
        this.height = height;
        this.hitbox = new Box(position, width, height, 0, 0);
        atlas = new TextureAtlas(Gdx.files.internal(atlasLocation));
    }

    public Box getHitbox(){
        return hitbox;
    }

    public void draw(Batch batch){
        batch.draw(region, position.getX(), position.getY());
    }

}
