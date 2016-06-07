package com.goblet.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.goblet.entities.Player;
import com.goblet.level.Floor;
import com.goblet.level.Position;

/**
 * Created by Johan on 2016-05-25.
 */
public class UserInterface {

    private TextureRegion heartFull;
    private TextureRegion heartEmpty;
    private Position position;
    private Score score;
    private Map map;

    public UserInterface(Position bottomLeft, Position topRight, Floor floor){
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("assets/sprites/ui/heart.pack"));
        heartFull = atlas.findRegion("full");
        heartEmpty = atlas.findRegion("empty");
        score = new Score(new Position(topRight.getX() - 130, bottomLeft.getY() + 15));
        this.position = new Position(bottomLeft.getX(), topRight.getY()  - heartFull.getRegionHeight() * 2);
        map = new Map(topRight, floor);
    }

    public Score getScoreObject(){
        return score;
    }

    public void draw(Batch batch, Player player){
        for (int i = 0; i < player.getHP(); i++) {
            batch.draw(heartFull, position.getX() + (i + 1) * (heartFull.getRegionWidth() + 3), position.getY());
        }
        for (int i = player.getHP(); i < player.getMaxHP(); i ++){
            batch.draw(heartEmpty, position.getX() + (i + 1) * (heartFull.getRegionWidth() + 3), position.getY());
        }
        score.draw(batch);
    }

}
