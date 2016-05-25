package com.goblet.gameEngine;

import com.goblet.level.Room;
import com.goblet.level.SpawnPoint;
import com.goblet.level.Tile;
import com.goblet.level.TileType;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * Created by Simoido on 2016-05-19.
 */
public class RoomParser {
    Reader reader;
    JsonElement je;
    private int tilesHeight;
    private int tilesWidth;


    public RoomParser(String fileName){
        tilesHeight = 16;
        tilesWidth = 26;
        try {
            reader = new FileReader("assets/jsonFiles/" + fileName);
            je = new JsonParser().parse(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Room createRoom(float bottomLeftX, float bottomLeftY, float topRightX, float topRightY){
        //TODO
        TileType[][] roomTiles = getRoomTiles("room1");
        for(int i = 0 ; i < tilesHeight ; i++){
            for(int j = 0 ; j < tilesWidth ; j++){
                //if ()
            }
        }
        return null;
    }

    public TileType[][] getRoomTiles(String roomName){
        String[][] stringArray = new String[tilesHeight][tilesWidth];
        TileType[][] returnArray = new TileType[tilesHeight][tilesWidth];
        for(int i = 0 ; i < tilesHeight ; i++){
            for(int j = 0 ; j < tilesWidth ; j++){
                stringArray[i][j] = je.getAsJsonObject().get(roomName).getAsJsonArray().get(i).getAsJsonArray().get(j).getAsString();
            }
        }
        for (int i = 0; i < tilesHeight; i++) {
            for (int j = 0; j < tilesWidth; j++) {
                returnArray[i][j] = TileType.translate(stringArray[i][j]);
            }
        }
        return returnArray;
    }

    public SpawnPoint[][] getSpawns(String roomName){
        String[][] stringArray = new String[tilesHeight][tilesWidth];
        SpawnPoint[][] returnArray = new SpawnPoint[tilesHeight][tilesWidth];
        for(int i = 0 ; i < tilesHeight ; i++){
            for(int j = 0 ; j < tilesWidth ; j++){
                stringArray[i][j] = je.getAsJsonObject().get(roomName).getAsJsonArray().get(i).getAsJsonArray().get(j).getAsString();
            }
        }
        for (int i = 0; i < tilesHeight; i++) {
            for (int j = 0; j < tilesWidth; j++) {
                returnArray[i][j] = SpawnPoint.translate(stringArray[i][j]);
            }
        }
        return returnArray;
    }



    /*
    public static void main(String[]arg){
        RoomParser rp = new RoomParser("room1","rooms.json");
        String[][] array = rp.getRoom("room1");
        for(int i = 0; i < 16 ; i++){
            for(int j = 0 ; j < 26 ; j++){
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }
    */
}
