package com.goblet.gameEngine;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.*;
/**
 *En klass som kan läsa information från json filer som innehåller information om fiender och returnera värdena
 * Created by Simoido on 2016-05-19.
 */
public class EnemyParser {
    Reader reader;
    JsonElement je;

    public EnemyParser(String filename){
        try{
            reader = new FileReader("core/assets/jsonFiles/" + filename);
            je = new JsonParser().parse(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getName() throws FileNotFoundException{
        String name = je.getAsJsonObject().get("name").getAsString();
        return name;
    }

    public int getHealth() throws FileNotFoundException{
        int health = je.getAsJsonObject().get("health").getAsInt();
        return health;
    }

    public int getDamage() throws FileNotFoundException{
        int dmg = je.getAsJsonObject().get("damage").getAsInt();
        return dmg;
    }

    public int getHitboxSize() throws FileNotFoundException{
        int hitbox = je.getAsJsonObject().get("hitbox").getAsInt();
        return hitbox;
    }

    public int getHiboxOrigin() throws FileNotFoundException{
        int hitboxOrigin = je.getAsJsonObject().get("hitboxOrigin").getAsInt();
        return hitboxOrigin;
    }

    public String getAttackType() throws FileNotFoundException{
        String attackType = je.getAsJsonObject().get("attackType").getAsString();
        return attackType;
    }

    public String getMoveType() throws FileNotFoundException{
        String moveType = je.getAsJsonObject().get("moveType").getAsString();
        return moveType;
    }
}

/*
ska kunna läsa:
name
health
damage
hitboxSize
hitboxOrigin
attackType
moveType
 */