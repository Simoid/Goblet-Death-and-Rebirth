package com.goblet.gameEngine;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
/**
 * Created by Simoido on 2016-05-19.
 */
public class HappyJasonParser {
    Gson gson = new Gson();
    Reader reader;

    public String getName() throws FileNotFoundException{
        reader = new FileReader("core/assets/jsonFiles/testfile.json");
        JsonElement je = new JsonParser().parse(reader);
        String name = je.getAsJsonObject().get("name").getAsString();
        return name;
    }
}
