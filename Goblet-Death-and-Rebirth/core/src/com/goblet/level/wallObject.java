package com.goblet.level;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.goblet.entities.Direction;
import com.goblet.level.Position;

/**
 * Created by Johan on 2016-05-23.
 */
public class WallObject {

    private TextureRegion region;
    private Direction dir;
    private Position position;
    private Position bottomLeft;
    private Position topRight;

    public WallObject(TextureRegion region, TextureRegion regionWithDoor, Direction dir, Position bottomLeft, Position topRight, boolean doorOnWall){
        //Se vilken TextureRegion som ska användas i WallObject
        if(doorOnWall){
            this.region = regionWithDoor;
        }else{
            this.region = region;
        }

        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
        this.dir = dir;
        position = new Position(0, 0);
        setPosition();
    }

    private void setPosition(){
        switch(dir){
            case DOWN:
                position.setPosition(-region.getRegionWidth()/2, bottomLeft.getY());
                break;
            case UP:
                position.setPosition(-region.getRegionWidth()/2,topRight.getY() - region.getRegionHeight());
                break;
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
