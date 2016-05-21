package com.goblet.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;


/**
 * En klass som innehåller en texture och en position för att rita ut en tile.
 * Created by johan on 5/21/16.
 */
public class Tile {
    private Texture texture;
    private Position position;

    public Tile(String spriteLocation, Position position){
        texture = new Texture(Gdx.files.internal(spriteLocation));
        this.position = new Position(position);
    }

    public void draw(Batch batch){
        batch.draw(texture, position.getX(), position.getY());
    }

}
