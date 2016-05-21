package com.goblet.level;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.goblet.entities.Direction;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by johan on 5/21/16.
 */
public class Room {
    private HashMap<Direction, Point> doorLocations;
    private Sprite[][] tiles;

    public void draw(Batch batch){
        for (Sprite[] tileRow : tiles){
            for (Sprite currentTile : tileRow){
                currentTile.draw(batch);
            }
        }
    }
}
