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
        switch (enemy){
            case KING:
                break;
            case DATBOI:
                break;
            case BAT:
                break;
            case MASK:
                break;
            case SPIDER:
                break;
            case PRISM:
                break;
        }
    }
}
