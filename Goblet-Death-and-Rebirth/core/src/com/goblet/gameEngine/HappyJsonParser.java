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
    Reader reader;
    JsonElement je;

    public String getName(String fileName) throws FileNotFoundException{
        reader = new FileReader("core/assets/jsonFiles/" + fileName);
        je = new JsonParser().parse(reader);
        String name = je.getAsJsonObject().get("name").getAsString();
        return name;
    }

    public int getHealth(String fileName) throws FileNotFoundException{
        reader = new FileReader("core/assets/jsonFiles/" + fileName);
        je = new JsonParser().parse(reader);
        int health = je.getAsJsonObject().get("health").getAsInt();
        return health;
    }

    public int getDamge(String fileName) throws FileNotFoundException{
        reader = new FileReader("core/assets/jsonFiles/" + fileName);
        je = new JsonParser().parse(reader);
        int dmg = je.getAsJsonObject().get("damage").getAsInt();
        return dmg;
    }

    public int getHitbox(String fileName) throws FileNotFoundException{
        reader = new FileReader("core/assets/jsonFiles/" + fileName);
        je = new JsonParser().parse(reader);
        int hitbox = je.getAsJsonObject().get("hitbox").getAsInt();
        return hitbox;
    }
}
