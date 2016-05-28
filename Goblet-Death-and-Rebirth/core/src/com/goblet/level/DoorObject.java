package com.goblet.level;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.goblet.entities.Direction;
import com.goblet.gameEngine.Box;

/**
 * Created by Simoido on 2016-05-23.
 */
public class DoorObject {

    private TextureRegion region;
    private Direction dir;
    private Position position;
    private Position bottomLeft;
    private Position topRight;
    private Position wallPosition;
    private float doorShadowSize = 3.0f;
    private Box allowedZone;
    private Box nextRoomZone;

    public DoorObject(TextureRegion region, Direction dir, Position bottomLeft, Position topRight, Position wallPosition){
        this.region = region;
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
        this.wallPosition = wallPosition;
        this.dir = dir;
        position = new Position(0, 0);
        setPosition();
    }

    private void setPosition(){
        switch(dir){
            case DOWN:
                position.setPosition(wallPosition.getX() , bottomLeft.getY());
                allowedZone = new Box(new Position(0f, bottomLeft.getY()), 34, 30, 0, 0);
                nextRoomZone = new Box(new Position(0f, bottomLeft.getY() - 30), 34, 30, 0, 0);
                break;
            case UP:
                position.setPosition(-region.getRegionWidth()/2,topRight.getY() - region.getRegionHeight());
                allowedZone = new Box(new Position(0f, topRight.getY()), 34, 30, 0, 0);
                nextRoomZone = new Box(new Position(0f, topRight.getY() + 30), 34, 30, 0, 0);
                break;
            case LEFT:
                position.setPosition(wallPosition.getX(), wallPosition.getY());
                allowedZone = new Box(new Position(bottomLeft.getX() + region.getRegionWidth() - 15, 0), 30, 10, 0, 0);
                nextRoomZone = new Box(new Position(bottomLeft.getX() + region.getRegionWidth() - 15 - 30, 0), 30, 10, 0, 0);
                break;
            case RIGHT:
                position.setPosition(wallPosition.getX() - doorShadowSize, bottomLeft.getY());
                allowedZone = new Box(new Position(topRight.getX() - region.getRegionWidth() - 15, 0), 30, 10, 0, 0);
                nextRoomZone = new Box(new Position(topRight.getX() - region.getRegionWidth() - 15 + 30, 0), 30, 10, 0, 0);
                break;
        }
    }

    public Box getAllowedZone(){
        return allowedZone;
    }

    public Box getNextRoomZone(){
        return nextRoomZone;
    }

    public void draw(Batch batch){
        batch.draw(region, position.getX(), position.getY(), region.getRegionWidth(), region.getRegionHeight());
        allowedZone.draw(batch);
        nextRoomZone.draw(batch);
    }

}
