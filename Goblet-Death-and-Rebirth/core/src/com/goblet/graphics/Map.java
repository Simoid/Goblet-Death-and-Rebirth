package com.goblet.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.goblet.entities.Direction;
import com.goblet.level.Floor;
import com.goblet.level.FloorNode;
import com.goblet.level.Position;

import java.util.ArrayList;

/**
 * Created by johan on 6/5/16.
 */
public class Map {

    private TextureRegion visitedRoomTexture;
    private TextureRegion seenRoomTexture;
    private TextureRegion currentRoomTexture;
    private TextureRegion mapFrameTexture;
    private TextureRegion mapBackgroundTexture;
    private Position middlePos;
    private ArrayList<FloorNode> visitedRooms;
    private ArrayList<FloorNode> seenRooms;
    private Floor floor;


    public Map(Position topRight, Floor floor){
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("assets/sprites/ui/map/Map.pack"));
        visitedRoomTexture = atlas.findRegion("map_visited_room");
        seenRoomTexture = atlas.findRegion("map_seen_room");
        currentRoomTexture = atlas.findRegion("map_current_room");
        mapBackgroundTexture = atlas.findRegion("map_background_large");
        mapFrameTexture = atlas.findRegion("map_frame_large");
        this.middlePos = new Position(topRight.getX() - mapFrameTexture.getRegionWidth()/2 - 1 , topRight.getY() - mapFrameTexture.getRegionHeight()/2 - 1 );
        this.floor = floor;
        visitedRooms = new ArrayList<FloorNode>();
        seenRooms = new ArrayList<FloorNode>();
        update();
    }

    public void draw(Batch batch){
        drawBackground(batch);
        drawMiddlePos(batch, mapFrameTexture);
        drawMiddlePos(batch, currentRoomTexture);
        drawVisitedRooms(batch);
        drawSeenRooms(batch);
    }

    private void drawBackground(Batch batch){
        batch.setColor(1.0f,1.0f,1.0f,0.4f);
        batch.draw(mapBackgroundTexture, middlePos.getX() - mapBackgroundTexture.getRegionWidth()/2,middlePos.getY() - mapBackgroundTexture.getRegionHeight()/2);
        batch.setColor(1.0f,1.0f,1.0f,1.0f);
    }

    private void drawVisitedRooms(Batch batch){
        FloorNode currentNode = floor.getCurrentNode();
        for (FloorNode neighbour : visitedRooms) {
            if (neighbour.visited()) {
                drawRooms(batch, currentNode, neighbour, visitedRoomTexture);
            }
        }
    }
    private void drawSeenRooms(Batch batch){
        FloorNode currentNode = floor.getCurrentNode();
        for (FloorNode neighbour : seenRooms) {
            drawRooms(batch, currentNode, neighbour, seenRoomTexture);
        }
    }

    private void drawRooms(Batch batch, FloorNode centerNode, FloorNode nodeToDraw, TextureRegion texture){
        if (-currentRoomTexture.getRegionWidth()/2 + (centerNode.getX() - nodeToDraw.getX())*texture.getRegionWidth() < -mapBackgroundTexture.getRegionWidth()/2
                || -currentRoomTexture.getRegionWidth()/2 + (centerNode.getX() - nodeToDraw.getX() + 1)*texture.getRegionWidth() > mapBackgroundTexture.getRegionWidth()/2
                || -currentRoomTexture.getRegionHeight()/2 + (centerNode.getY() - nodeToDraw.getY())*texture.getRegionHeight() < -mapBackgroundTexture.getRegionHeight()/2
                || -currentRoomTexture.getRegionHeight()/2 + (centerNode.getY() - nodeToDraw.getY() + 1)*texture.getRegionHeight() > mapBackgroundTexture.getRegionHeight()/2
                || centerNode.equals(nodeToDraw)){
           return;
        } else {
            batch.draw(texture, middlePos.getX() - currentRoomTexture.getRegionWidth()/2 + (centerNode.getX() - nodeToDraw.getX())*texture.getRegionWidth(), middlePos.getY() - currentRoomTexture.getRegionHeight()/2 + (centerNode.getY() - nodeToDraw.getY())*texture.getRegionHeight());
        }
    }

    public void update(){
        if (!visitedRooms.contains(floor.getCurrentNode())){
            visitedRooms.add(floor.getCurrentNode());
        }
        seenRooms.remove(floor.getCurrentNode());
        for (FloorNode node : floor.getCurrentNode().getNeighbours()){
            if (!node.visited() && !seenRooms.contains(node)){
                seenRooms.add(node);
            }
        }
        System.out.println(seenRooms);
    }

    private void drawMiddlePos(Batch batch, TextureRegion regionToDraw){
        batch.draw(regionToDraw, middlePos.getX() - regionToDraw.getRegionWidth()/2, middlePos.getY() - regionToDraw.getRegionHeight()/2);
    }

}
