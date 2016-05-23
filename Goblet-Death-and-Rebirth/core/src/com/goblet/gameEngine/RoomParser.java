package com.goblet.gameEngine;

import com.goblet.level.Tile;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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

    public RoomParser(String roomName, String fileName, Tile[][] tiles){
        try {
            reader = new FileReader(fileName);
            je = new JsonParser().parse(reader);
            JsonObject roomObject = je.getAsJsonObject().get(roomName).getAsJsonObject();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
