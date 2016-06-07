package com.goblet.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.goblet.level.Floor;
import com.goblet.level.FloorNode;
import com.goblet.level.Position;

import java.util.ArrayList;

/**
 * Created by johan on 6/5/16.
 */
public class Map {

    private TextureRegion visitedRoomTexture;
    private TextureRegion currentRoomTexture;
    private TextureRegion mapFrameTexture;
    private TextureRegion mapBackgroundTexture;
    private Position middlePos;
    private Floor floor;
    private ArrayList<FloorNode> visitedNodes;


    public Map(Position topRight, Floor floor){
        visitedNodes = new ArrayList<FloorNode>();
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("assets/sprites/ui/map/Map.pack"));
        visitedRoomTexture = atlas.findRegion("map_visited_room");
        currentRoomTexture = atlas.findRegion("map_current_room");
        mapBackgroundTexture = atlas.findRegion("map_background");
        mapFrameTexture = atlas.findRegion("map_frame");
        this.middlePos = new Position(topRight.getX() - mapFrameTexture.getRegionWidth()/2 - 1 , topRight.getY() - mapFrameTexture.getRegionHeight()/2 - 1 );
        this.floor = floor;
    }

    public void draw(Batch batch){
        drawBackground(batch);
        drawFrame(batch);
        drawRooms(batch);
    }

    private void drawBackground(Batch batch){
        batch.setColor(1.0f,1.0f,1.0f,0.4f);
        batch.draw(mapBackgroundTexture, middlePos.getX() - mapBackgroundTexture.getRegionWidth()/2,middlePos.getY() - mapBackgroundTexture.getRegionHeight()/2);
        batch.setColor(1.0f,1.0f,1.0f,1.0f);
    }

    private void drawFrame(Batch batch){
        batch.draw(mapFrameTexture, middlePos.getX() - mapFrameTexture.getRegionWidth()/2,middlePos.getY() - mapFrameTexture.getRegionHeight()/2);
    }

    private void drawRooms(Batch batch){

    }

}
