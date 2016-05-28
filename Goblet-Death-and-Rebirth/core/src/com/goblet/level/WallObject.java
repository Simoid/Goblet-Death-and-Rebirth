package com.goblet.level;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.goblet.entities.Direction;
import com.goblet.gameEngine.Box;
import com.goblet.level.Position;

/**
 * Created by Johan on 2016-05-23.
 */
public class WallObject {

    private DoorObject door;
    private TextureRegion region;
    private Direction dir;
    private Position position;
    private Position bottomLeft;
    private Position topRight;
    private Boolean hasDoor;
    private Boolean doorOpened;
    private TextureRegion regionWithDoor;
    private TextureRegion doorTexture;
    private Box noEntititesZone;
    private Position enterPosition;

    public WallObject(TextureRegion region, TextureRegion regionWithDoor, TextureRegion doorTexture, Direction dir, Position bottomLeft, Position topRight, boolean hasDoor) {
        //Se vilken TextureRegion som ska användas i WallObject
        this.hasDoor = hasDoor;
        this.regionWithDoor = regionWithDoor;
        this.doorTexture = doorTexture;
        doorOpened = false;
        if (hasDoor) {
            this.region = regionWithDoor;
        } else {
            this.region = region;
        }
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
        this.dir = dir;
        position = new Position(0, 0);
        setPosition();
        setNoEntititesZone(dir);
        setEnterPosition(dir);
        if (hasDoor) {
            door = new DoorObject(doorTexture, dir, bottomLeft, topRight, position);
        }
    }

    public Box getNoEntititesZone(){
        return noEntititesZone;
    }

    public Direction getDir(){
        return dir;
    }

    private void setNoEntititesZone(Direction dir){
        float offsetX = 0;
        float offsetY = 0;
        switch(dir){
            case DOWN:
                offsetY = -1;
                break;
            case LEFT:
                offsetX = -1;
                break;
            case UP:
                offsetY = 2;
                break;
            case RIGHT:
                offsetX = 2;
                break;
        }
        noEntititesZone = new Box(new Position(position.getX() + region.getRegionWidth() / 2, position.getY() + region.getRegionHeight() / 2), region.getRegionWidth(), region.getRegionHeight(), offsetX, offsetY);
    }

    private void setEnterPosition(Direction dir) {
        switch (dir) {
            case DOWN:
                enterPosition = new Position(0, bottomLeft.getY() + region.getRegionHeight() + 60);
            case LEFT:
                enterPosition = new Position(bottomLeft.getX() + region.getRegionWidth() + 60, 0);
                break;
            case UP:
                enterPosition = new Position(0, topRight.getY() - region.getRegionHeight() - 60);
                break;
            case RIGHT:
                enterPosition = new Position(topRight.getX() - region.getRegionWidth() - 60, 0);
                break;
        }
    }

    public Position getEnterPosition(){
        return enterPosition;
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
        if (hasDoor && !doorOpened){
            door.draw(batch);
        }
        //noEntititesZone.draw(batch);
    }

    public Box getNextRoomZone(){
        if (door == null){
            return null;
        }
        return door.getNextRoomZone();
    }

    public void addDoor(){
        hasDoor = true;
        this.region = regionWithDoor;
        door = new DoorObject(doorTexture, dir, bottomLeft, topRight, position);
        setNoEntititesZone(dir);
    }

    public Box getDoorZone(){
        if (door == null){
            return null;
        }
        return door.getAllowedZone();
    }

    public boolean doorIsClosed(){
        return !doorOpened;
    }

    public void closeDoor(){
        doorOpened = false;
    }

    public void openDoor(){
        doorOpened = true;
    }

}
