package com.goblet.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.goblet.level.Floor;
import com.goblet.level.Position;

/**
 * Created by johan on 6/5/16.
 */
public class Map {

    private Texture visitedRoomTexture;
    private Texture currentRoomTexture;
    private Texture mapFrameTexture;
    private Texture mapBackgroundTexture;
    private Position topRight;
    private Floor floor;


    public Map(Position topRight, Floor floor){
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("assets/sprites/ui/map/Map.pack"));
        visitedRoomTexture = atlas.findRegion("map_visited_room").getTexture();
        currentRoomTexture = atlas.findRegion("map_current_room").getTexture();
        mapBackgroundTexture = atlas.findRegion("map_background").getTexture();
        mapFrameTexture = atlas.findRegion("map_frame").getTexture();
        this.topRight = new Position(topRight);
        this.floor = floor;
    }

    public void draw(Batch batch){
        drawBackground(batch);
        drawFrame(batch);
        drawRooms(batch);
    }

    private void drawBackground(Batch batch){

    }

    private void drawFrame(Batch batch){

    }

    private void drawRooms(Batch batch){

    }

}
