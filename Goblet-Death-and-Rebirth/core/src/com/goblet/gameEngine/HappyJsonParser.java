package com.goblet.gameEngine;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
/**
 *En klass som kan läsa information från json filer och returnera dem
 * Created by Simoido on 2016-05-19.
 */
public class HappyJsonParser {
    Gson gson = new Gson();
    Reader reader;

    public String getName(String fileName) throws FileNotFoundException{
        reader = new FileReader("core/assets/jsonFiles/" + fileName);
        JsonElement je = new JsonParser().parse(reader);
        String name = je.getAsJsonObject().get("name").getAsString();
        return name;
    }
}
