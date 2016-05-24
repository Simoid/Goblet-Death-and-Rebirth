package com.goblet.gameEngine;

/**
 * Created by Simoido on 2016-05-23.
 */
public class EnemyConstructor {
    EnemyParser ep;
    public EnemyConstructor(){
        ep = new EnemyParser("enemies.json");
    }
}
