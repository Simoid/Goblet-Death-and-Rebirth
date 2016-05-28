package com.goblet.level;

import com.goblet.entities.Direction;
import com.goblet.gameEngine.RoomParser;

import java.util.Random;

/**
 * Created by Johan on 2016-05-28.
 */
public class Floor {

    private int numberOfRooms;
    private FloorNode startNode;
    private RoomParser roomParser;
    private FloorNode currentNode;

    public Room getNextRoom(Direction dir){
        currentNode = currentNode.getConnection(dir);
        return currentNode.getRoom();
    }

    public Floor(int numberOfRooms, RoomParser roomParser){
        this.roomParser = roomParser;
        this.numberOfRooms = numberOfRooms;
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
        FloorNode lastNode = startNode;
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
            System.out.println(positionBusy(x, y, lastNode));
            System.out.println("x: " + x + ", y: " + y);
            if (positionBusy(x, y, lastNode)){
                rooms--;
            } else {
                //System.out.println("x: " + x + ", y: " + y);
                lastNode = lastNode.createNeighbour(x, y);
            }

        }
    }

    private boolean positionBusy(int x, int  y, FloorNode lastNode){
        for (FloorNode neighbour : lastNode.getNeighbours()){
            if (neighbour.getX() == x && neighbour.getX() == y){
                return true;
            }
        }
        return false;
    }


}
