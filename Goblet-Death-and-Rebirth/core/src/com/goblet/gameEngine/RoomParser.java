package com.goblet.gameEngine;

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


    public RoomParser(String fileName/* Tile[][] tiles*/){
        try {
            reader = new FileReader("assets/jsonFiles/" + fileName);
            je = new JsonParser().parse(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public String[][] getRoom(String roomName){
        String[][] array = new String[16][24];
        for(int i = 0 ; i < 16 ; i++){
            for(int j = 0 ; j < 24 ; j++){
                array[i][j] = je.getAsJsonObject().get(roomName).getAsJsonArray().get(i).getAsJsonArray().get(j).getAsString();
            }
        }
        return  array;
    }

    /*
    public static void main(String[]arg){
        RoomParser rp = new RoomParser("room1","rooms.json");
        String[][] array = rp.getRoom("room1");
        for(int i = 0; i < 16 ; i++){
            for(int j = 0 ; j < 24 ; j++){
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }
    */
}
