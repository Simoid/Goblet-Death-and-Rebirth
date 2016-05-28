package com.goblet.level;

import com.goblet.entities.Direction;
import com.goblet.gameEngine.RoomParser;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Johan on 2016-05-28.
 */
public class FloorNode {

    private int xPos;
    private int yPos;
    private RoomParser roomParser;
    private HashMap<Direction, FloorNode> neighbours;
    private Room room;

    public FloorNode(int xPos, int yPos, RoomParser roomParser, boolean random){
        this.xPos = xPos;
        this.yPos = yPos;
        this.roomParser = roomParser;
        neighbours = new HashMap<Direction, FloorNode>();
        if (!random) {
            room = roomParser.createRoom("firstRoom");
        } else {
            room = roomParser.createRandom();
        }
    }

    public void setConnection(Direction dir, FloorNode connectedNode){
        neighbours.put(dir, connectedNode);
        room.addDoor(dir);
    }

    public boolean isNeighbourWith(FloorNode otherNode){
        return Math.abs(otherNode.getX() - xPos) + Math.abs(otherNode.getY() - yPos) == 1;
    }

    public int getX(){
        return xPos;
    }

    public int getY(){
        return yPos;
    }

    public Collection<FloorNode> getNeighbours(){
        return neighbours.values();
    }

    public Collection<FloorNode> getAllNeighbours(){
        return null;
    }

    public FloorNode createNeighbour(int x, int y){
        FloorNode newNode = new FloorNode(x, y, roomParser, true);
        if (x - xPos == 1){
            newNode.setConnection(Direction.RIGHT, this);
            this.setConnection(Direction.LEFT, newNode);
        } else if (xPos - x == 1) {
            newNode.setConnection(Direction.LEFT, this);
            this.setConnection(Direction.RIGHT, newNode);
        } else if (y - yPos == 1){
            newNode.setConnection(Direction.UP, this);
            this.setConnection(Direction.DOWN, newNode);
        } else if (yPos - y == 1) {
            newNode.setConnection(Direction.DOWN, this);
            this.setConnection(Direction.UP, newNode);
        }
        return newNode;
    }

    public FloorNode getConnection(Direction dir){
        return neighbours.get(dir);
    }

    public Room getRoom(){
        return room;
    }


}
