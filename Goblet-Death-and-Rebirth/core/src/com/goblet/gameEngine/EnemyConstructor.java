package com.goblet.gameEngine;

import com.goblet.entities.Enemy;
import com.goblet.level.Position;
import com.goblet.level.SpawnPoint;

import java.io.FileNotFoundException;

/**
 * En klass som används för att skapa Enemy-objekt genom att läsa info från enemies.json.
 *
 * ( enemies.json ligger i assets/jsonFiles/ )
 *
 * Created by Simoido on 2016-05-23.
 */
public class EnemyConstructor {
    EnemyParser ep;

    /**
     * Konstruktorn för klassen. Skapar ett EnemyParser-objekt.
     */
    public EnemyConstructor(){
        ep = new EnemyParser("enemies.json");
    }

    /**
     * Skapar en fiendeobjekt utifrån enemies.json.
     * @param name Namnet på fienden som ska skapas.
     * @param xPos x-positionen för fienden.
     * @param yPos y-positionen för fienden.
     * @return
     */
    public Enemy createEnemy(String name, float xPos, float yPos){
        String atlasLocation = name +"/"+ name;
        Enemy enemy;
        Position position = new Position(xPos,yPos);
        Box box;

        try{
            box =  new Box(position,ep.getHitboxWidth(name),ep.getHitboxHeight(name),ep.getHitboxOffsetX(name),ep.getHitboxOffsetY(name));
        }catch (FileNotFoundException ex){
            box = null;
        }

        try{
            enemy = new Enemy(position,atlasLocation,ep.getMoveFrames(name),ep.getAttackFrames(name),(float) ep.getMoveSpeed(name)
                    ,ep.getAttackRange(name),ep.getAttackType(name),box,(float) ep.getMoveAnimationSpeed(name),
                    (float) ep.getAttackAnimationSpeed(name), ep.getMaxHealth(name), ep.getDamageTaken(name), ep.getFlight(name));
        }catch (FileNotFoundException ex){
            return null;
        }

        return enemy;
    }
}
