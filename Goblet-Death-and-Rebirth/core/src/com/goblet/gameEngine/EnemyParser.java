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

    public String getName(String enemy) throws FileNotFoundException{
        String name = je.getAsJsonObject().get(enemy).getAsJsonObject().get("name").getAsString();
        return name;
    }

    public int getHealth(String enemy) throws FileNotFoundException{
        int health = je.getAsJsonObject().get(enemy).getAsJsonObject().get("health").getAsInt();
        return health;
    }

    public int getDamage(String enemy) throws FileNotFoundException{
        int dmg = je.getAsJsonObject().get(enemy).getAsJsonObject().get("damage").getAsInt();
        return dmg;
    }

    public int getHitboxSize(String enemy) throws FileNotFoundException{
        int hitbox = je.getAsJsonObject().get(enemy).getAsJsonObject().get("hitbox").getAsInt();
        return hitbox;
    }

    public int getHiboxOrigin(String enemy) throws FileNotFoundException{
        int hitboxOrigin = je.getAsJsonObject().get(enemy).getAsJsonObject().get("hitboxOrigin").getAsInt();
        return hitboxOrigin;
    }

    public String getAttackType(String enemy) throws FileNotFoundException{
        String attackType = je.getAsJsonObject().get(enemy).getAsJsonObject().get("attackType").getAsString();
        return attackType;
    }

    public String getMoveType(String enemy) throws FileNotFoundException{
        String moveType = je.getAsJsonObject().get(enemy).getAsJsonObject().get("moveType").getAsString();
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