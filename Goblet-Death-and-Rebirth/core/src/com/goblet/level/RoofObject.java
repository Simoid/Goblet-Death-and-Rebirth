package com.goblet.level;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.goblet.entities.Direction;
import com.sun.glass.ui.SystemClipboard;

/**
 * Created by Johan on 2016-05-24.
 */
public class RoofObject {

    private TextureRegion region;
    private Direction dir;
    private Position position;
    private Position bottomLeft;
    private Position topRight;

    public RoofObject(TextureRegion region, Direction dir, Position bottomLeft, Position topRight){
        //Se vilken TextureRegion som ska användas i WallObject
        this.region = region;
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
        this.dir = dir;
        position = new Position(0, 0);
        setPosition();
    }

    private void setPosition(){
        switch(dir){
            case LEFT:
                position.setPosition(bottomLeft.getX(), bottomLeft.getY());
                break;
            case RIGHT:
                position.setPosition(topRight.getX() - region.getRegionWidth(), bottomLeft.getY());
                break;
        }
    }

    public void draw(Batch batch){
        batch.draw(region, position.getX(), position.getY(), region.getRegionWidth(), region.getRegionHeight());
    }

}
