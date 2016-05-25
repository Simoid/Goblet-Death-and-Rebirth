package com.goblet.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

/**
 * Created by Johan on 2016-05-25.
 */
public class Rock extends GObstacles{

    public Rock(float posX, float posY, float boxWidth, float boxHeight, String atlasLocation){
        super(posX, posY, boxWidth, boxHeight, atlasLocation);
        Random randomizer = new Random();
        region = atlas.findRegion(String.format("%04d", randomizer.nextInt(9)));
    }

}
