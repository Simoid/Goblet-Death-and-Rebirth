package com.goblet.gameEngine;

import com.goblet.entities.Enemy;
import com.goblet.level.Position;
import com.goblet.level.SpawnPoint;

/**
 * Created by Simoido on 2016-05-23.
 */
public class EnemyConstructor {
    SpawnPoint sp;
    EnemyParser ep;

    public EnemyConstructor(){
        ep = new EnemyParser("enemies.json");
    }

    public Enemy createEnemy(String name, int xPos, int yPos){
        SpawnPoint enemy = sp.translate(name);
        String atlasLocation = name +"/"+ name;
        Enemy newEnemy;
        switch (enemy){
            case KING:
                newEnemy = new Enemy(xPos,yPos,atlasLocation,2,3,3,50f);
                return newEnemy;
            case DATBOI:
                return newEnemy;
            case BAT:
                return newEnemy;
            case MASK:
                return newEnemy;
            case SPIDER:
                return newEnemy;
            case PRISM:
                return newEnemy;
            default:
                return null;
        }
    }
}
