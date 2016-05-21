package com.goblet.gameEngine;


import com.google.gson.Gson;
import java.io.*;
/**
 * Created by Simoido on 2016-05-19.
 */
public class JsonParser {
    Gson gson = new Gson();
    Reader reader;

    public String getName() throws FileNotFoundException{
        reader = new FileReader("core/assets/jsonFiles/testfile.json");
        String name = gson.fromJson(reader,String.class);
        return name;
    }
}
