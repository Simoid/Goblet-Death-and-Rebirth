package com.goblet.level;

import com.goblet.entities.Direction;
import com.goblet.gameEngine.RoomParser;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Johan on 2016-05-28.
 */
public class Floor {

    private int numberOfRooms;
    private FloorNode startNode;
    private RoomParser roomParser;
    private FloorNode currentNode;
    private ArrayList<FloorNode> nodes;

    public Room getNextRoom(Direction dir){
        currentNode = currentNode.getConnection(dir);
        return currentNode.getRoom();
    }

    public Floor(int numberOfRooms, RoomParser roomParser){
        this.roomParser = roomParser;
        this.numberOfRooms = numberOfRooms;
        nodes = new ArrayList<FloorNode>();
        generateRooms();
    }

    public Room getFirstRoom(){
        return startNode.getRoom();
    }

    private void generateRooms(){
        int x = 0;
        int y = 0;
        startNode = new FloorNode(x, y, roomParser, false);
        currentNode = startNode;
        Random randomizer = new Random();
        nodes.add(startNode);
        for (int rooms  = 1; rooms <= numberOfRooms; rooms++){
            switch(randomizer.nextInt(4)) {
                case 0:
                    x++;
                    break;
                case 1:
                    x--;
                    break;
                case 2:
                    y--;
                    break;
                case 3:
                    y++;
                    break;
            }
            if (positionBusy(x, y)){
                rooms--;
            } else {
                createNode(x, y);
            }

        }
    }

    private boolean positionBusy(int x, int  y){
        for (FloorNode node : startNode.getAllNodes()){
            if (node.getX() == x && node.getY() == y){
                return true;
            }
        }
        return false;
    }

    private void createNode(int x, int y){
        FloorNode newNode = new FloorNode(x, y, roomParser, true);
        for (FloorNode node : nodes){
            if (node.isNeighbourWith(newNode)){
                node.addNeighbour(newNode);
                newNode.addNeighbour(node);
            }
        }
        nodes.add(newNode);
    }

}
