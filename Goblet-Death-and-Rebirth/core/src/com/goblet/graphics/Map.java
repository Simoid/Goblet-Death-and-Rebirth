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


    public Map(Position topRight, Floor floor){
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
        drawMiddlePos(batch, mapFrameTexture);
        drawMiddlePos(batch, currentRoomTexture);
        drawVisitedRooms(batch);
    }

    private void drawBackground(Batch batch){
        batch.setColor(1.0f,1.0f,1.0f,0.4f);
        batch.draw(mapBackgroundTexture, middlePos.getX() - mapBackgroundTexture.getRegionWidth()/2,middlePos.getY() - mapBackgroundTexture.getRegionHeight()/2);
        batch.setColor(1.0f,1.0f,1.0f,1.0f);
    }

    private void drawVisitedRooms(Batch batch){
        FloorNode currentNode = floor.getCurrentNode();
        for (FloorNode neighbour : currentNode.getNodesSteps(5)) {
            if (neighbour.visited()) {
                batch.draw(visitedRoomTexture, middlePos.getX() - currentRoomTexture.getRegionWidth()/2 + (currentNode.getX() - neighbour.getX())*visitedRoomTexture.getRegionWidth(), middlePos.getY() - currentRoomTexture.getRegionHeight()/2 + (currentNode.getY() - neighbour.getY())*visitedRoomTexture.getRegionHeight());
            }
        }

    }

    private void drawMiddlePos(Batch batch, TextureRegion regionToDraw){
        batch.draw(regionToDraw, middlePos.getX() - regionToDraw.getRegionWidth()/2, middlePos.getY() - regionToDraw.getRegionHeight()/2);
    }

}
