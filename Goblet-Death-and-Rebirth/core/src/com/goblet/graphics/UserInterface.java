package com.goblet.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.goblet.entities.Player;
import com.goblet.level.Position;

/**
 * Created by Johan on 2016-05-25.
 */
public class UserInterface {

    private TextureRegion heartFull;
    private TextureRegion heartEmpty;
    private Position position;

    public UserInterface(Position bottomLeft, Position topRight){
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("assets/sprites/ui/heart.pack"));
        heartFull = atlas.findRegion("full");
        heartEmpty = atlas.findRegion("empty");
        this.position = new Position(bottomLeft.getX(), topRight.getY()  - heartFull.getRegionHeight() * 2);
    }

    public void draw(Batch batch, Player player){
        for (int i = 0; i < player.getHP(); i++) {
            batch.draw(heartFull, position.getX() + (i + 1) * (heartFull.getRegionWidth() + 3), position.getY());
        }
        for (int i = player.getHP(); i < player.getMaxHP(); i ++){
            batch.draw(heartEmpty, position.getX() + (i + 1) * (heartFull.getRegionWidth() + 3), position.getY());
        }
    }

}
