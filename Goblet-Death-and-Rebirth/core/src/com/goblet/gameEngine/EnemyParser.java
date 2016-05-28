package com.goblet.gameEngine;


import com.goblet.entities.AttackType;
import com.goblet.entities.Enemy;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

/**
 *En klass som kan läsa information från json filer som innehåller information om fiender och returnera värdena.
 * Created by Simoido on 2016-05-19.
 */
public class EnemyParser {
    private Reader reader;
    private JsonElement je;

    public EnemyParser(String filename){
        try{
            reader = new FileReader("assets/jsonFiles/" + filename);
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

    public float getAttackRange(String enemy) throws FileNotFoundException{
        float dmg = je.getAsJsonObject().get(enemy).getAsJsonObject().get("attackrange").getAsFloat();
        return dmg;
    }

    public int getHitboxWidth(String enemy) throws FileNotFoundException{
        int width = je.getAsJsonObject().get(enemy).getAsJsonObject().get("hitboxWidth").getAsInt();
        return width;
    }

    public int getHitboxHeight(String enemy) throws FileNotFoundException{
        int height = je.getAsJsonObject().get(enemy).getAsJsonObject().get("hitboxHeight").getAsInt();
        return height;
    }

    public int getHiboxOrigin(String enemy) throws FileNotFoundException{
        int hitboxOrigin = je.getAsJsonObject().get(enemy).getAsJsonObject().get("hitboxOrigin").getAsInt();
        return hitboxOrigin;
    }

    public AttackType getAttackType(String enemy) throws FileNotFoundException{
        String attackType = je.getAsJsonObject().get(enemy).getAsJsonObject().get("attackType").getAsString();
        return AttackType.parse(attackType);
    }

    public String getMoveType(String enemy) throws FileNotFoundException{
        String moveType = je.getAsJsonObject().get(enemy).getAsJsonObject().get("moveType").getAsString();
        return moveType;
    }

    public int getMoveSpeed(String enemy) throws FileNotFoundException{
        int moveSpeed = je.getAsJsonObject().get(enemy).getAsJsonObject().get("moveSpeed").getAsInt();
        return moveSpeed;
    }

    public int getMoveFrames(String enemy) throws FileNotFoundException{
        int moveFrames = je.getAsJsonObject().get(enemy).getAsJsonObject().get("moveFrames").getAsInt();
        return moveFrames;
    }

    public int getAttackFrames(String enemy) throws FileNotFoundException{
        int attackFrames = je.getAsJsonObject().get(enemy).getAsJsonObject().get("attackFrames").getAsInt();
        return attackFrames;
    }

    public int getHitboxOffsetX(String enemy) throws FileNotFoundException{
        int offX = je.getAsJsonObject().get(enemy).getAsJsonObject().get("hitboxOffsetX").getAsInt();
        return offX;
    }

    public int getHitboxOffsetY(String enemy) throws FileNotFoundException{
        int offY = je.getAsJsonObject().get(enemy).getAsJsonObject().get("hitboxOffsetY").getAsInt();
        return offY;
    }

    public double getMoveAnimationSpeed(String enemy) throws FileNotFoundException{
        double speed = je.getAsJsonObject().get(enemy).getAsJsonObject().get("moveAnimationSpeed").getAsDouble();
        return speed;
    }

    public double getAttackAnimationSpeed(String enemy) throws FileNotFoundException{
        double speed = je.getAsJsonObject().get(enemy).getAsJsonObject().get("attackAnimationSpeed").getAsDouble();
        return speed;
    }

    public boolean getFlight(String enemy){
        return je.getAsJsonObject().get(enemy).getAsJsonObject().get("flight").getAsBoolean();
    }

    public float getMaxHealth(String enemy) throws FileNotFoundException{
        float maxHealth = je.getAsJsonObject().get(enemy).getAsJsonObject().get("maxHealth").getAsFloat();
        return maxHealth;
    }

    public float getDamageTaken(String enemy) throws FileNotFoundException{
        float damageTaken = je.getAsJsonObject().get(enemy).getAsJsonObject().get("damageTaken").getAsFloat();
        return damageTaken;
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