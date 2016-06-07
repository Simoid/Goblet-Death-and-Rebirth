package com.goblet.level;

import com.goblet.entities.Direction;
import com.goblet.gameEngine.RoomParser;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    private boolean visited;

    public FloorNode(int xPos, int yPos, RoomParser roomParser, boolean random){
        this.xPos = xPos;
        this.yPos = yPos;
        this.roomParser = roomParser;
        visited = false;
        neighbours = new HashMap<Direction, FloorNode>();
        if (!random) {
            room = roomParser.createRoom("firstRoom");
        } else {
            room = roomParser.createRandom();
        }
    }

    public boolean visited(){
        return visited;
    }

    public void visit(){
        visited = true;
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

    public ArrayList<FloorNode> getAllNodes(){
        ArrayList<FloorNode> returnCollection = new ArrayList<FloorNode>();
        returnCollection.add(this);
        for (FloorNode node : neighbours.values()){
            if (node != null) {
                returnCollection.add(node);
                node.getAllNeighboursExcluding(returnCollection, -1);
            }
        }
        return returnCollection;
    }

    public ArrayList<FloorNode> getNodesSteps(int steps){
        ArrayList<FloorNode> returnCollection = new ArrayList<FloorNode>();
        returnCollection.add(this);
        for (FloorNode node : neighbours.values()){
            if (node != null){
                if (!returnCollection.contains(node)) {
                    returnCollection.add(node);
                }
                if (steps != 0){
                    //System.out.println("Nu kör vi:");
                    getAllNeighboursExcluding(returnCollection, steps-1);
                }
            }
        }
        returnCollection.remove(this);
        return returnCollection;
    }

    private void getAllNeighboursExcluding(ArrayList<FloorNode> excludeCollection, int steps){

        for (FloorNode node : neighbours.values()){
            if (node != null && (!excludeCollection.contains(node) || steps >= 0)) {
                if (!excludeCollection.contains(node)) {
                    excludeCollection.add(node);
                }
                //System.out.println(steps);
                if (steps != 0) {
                    node.getAllNeighboursExcluding(excludeCollection, steps - 1);
                }
            }

        }
    }

    public void addNeighbour(FloorNode otherNode){
        if (otherNode.getX() - xPos == 1){
            otherNode.setConnection(Direction.RIGHT, this);
            this.setConnection(Direction.LEFT, otherNode);
        } else if (xPos - otherNode.getX() == 1) {
            otherNode.setConnection(Direction.LEFT, this);
            this.setConnection(Direction.RIGHT, otherNode);
        } else if (otherNode.getY() - yPos == 1){
            otherNode.setConnection(Direction.UP, this);
            this.setConnection(Direction.DOWN, otherNode);
        } else if (yPos - otherNode.getY() == 1) {
            otherNode.setConnection(Direction.DOWN, this);
            this.setConnection(Direction.UP, otherNode);
        }
    }

    public FloorNode getConnection(Direction dir){
        return neighbours.get(dir);
    }

    public Room getRoom(){
        return room;
    }

    @Override
    public String toString(){
        return "x: " + xPos + ", y: " + yPos;
    }


}
