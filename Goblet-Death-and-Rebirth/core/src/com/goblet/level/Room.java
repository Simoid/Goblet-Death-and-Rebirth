package com.goblet.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.goblet.entities.Direction;
import com.goblet.entities.Enemy;
import com.goblet.entities.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by johan on 5/21/16.
 */
public class Room {

    private Tile[][] tiles;
    private String atlasLocation = "assets/tiles/room/room.pack";
    private TextureRegion floorTextureRegion;
    private Map<Direction, WallObject> wallMap;
    private Map<Direction, RoofObject> roofMap;
    private ArrayList<Enemy> enemies;
    private TextureAtlas atlas;
    private Position bottomLeft;
    private Position topRight;
    private boolean doorsOpen;



    public void draw(Batch batch, Player player){
        drawFloorAndWalls(batch);
        drawEntitites(batch, player);
        drawRoof(batch);
    }

    private void drawFloorAndWalls(Batch batch){
        drawFloor(batch);
        for (WallObject currentWall : wallMap.values()){
            currentWall.draw(batch);
        }
    }

    private void drawFloor(Batch batch){
        batch.draw(floorTextureRegion, -floorTextureRegion.getRegionWidth()/2, bottomLeft.getY(), floorTextureRegion.getRegionWidth(), floorTextureRegion.getRegionHeight());
    }

    private void drawEntitites(Batch batch, Player player){
        for (Enemy currentEnemy : enemies){
            currentEnemy.draw(batch);
        }
        player.draw(batch);
    }

    private void drawRoof(Batch batch){
        for (RoofObject currentRoof : roofMap.values()){
            currentRoof.draw(batch);
        }
    }

    public Room(float bottomLeftX, float bottomLeftY, float topRightX, float topRightY, TileType[][] tileTypes, SpawnPoint[][] spawnPoints){
        bottomLeft = new Position(bottomLeftX, bottomLeftY);
        topRight = new Position(topRightX, topRightY);

        wallMap = new HashMap<Direction, WallObject>();
        roofMap = new HashMap<Direction, RoofObject>();
        tiles = new Tile[16][26];

        //allowedArea = new Box(new Position())

        enemies = new ArrayList<Enemy>();
        //enemies.add(new Enemy(50, 0, "king/king", 2, 3, 3,50f));


        atlas = new TextureAtlas(Gdx.files.internal(atlasLocation));

        wallMap.put(Direction.UP, new WallObject(atlas.findRegion("spr_upper_wall"), atlas.findRegion("spr_upper_wall_path"), atlas.findRegion("spr_upper_door"), Direction.UP, bottomLeft, topRight, true));
        wallMap.put(Direction.DOWN, new WallObject(atlas.findRegion("spr_lower_wall"), atlas.findRegion("spr_lower_wall_path"), atlas.findRegion("spr_lower_door"), Direction.DOWN, bottomLeft, topRight, true));
        wallMap.put(Direction.LEFT, new WallObject(atlas.findRegion("spr_left_wall"), atlas.findRegion("spr_left_wall_path"), atlas.findRegion("spr_left_door"), Direction.LEFT, bottomLeft, topRight, true));
        wallMap.put(Direction.RIGHT, new WallObject(atlas.findRegion("spr_right_wall"), atlas.findRegion("spr_right_wall_path"), atlas.findRegion("spr_right_door"), Direction.RIGHT, bottomLeft, topRight, true));

        roofMap.put(Direction.LEFT, new RoofObject(atlas.findRegion("spr_left_wall_path_roof"), Direction.LEFT, bottomLeft, topRight));
        roofMap.put(Direction.RIGHT, new RoofObject(atlas.findRegion("spr_right_wall_path_roof"), Direction.RIGHT, bottomLeft, topRight));

        floorTextureRegion = atlas.findRegion("spr_floor");
    }

    public void openDoors(){
        for (WallObject currentWall : wallMap.values()){
            currentWall.openDoor();
            doorsOpen = true;
        }
    }

    public void closeDoors(){
        for (WallObject currentWall : wallMap.values()){
            currentWall.closeDoor();
            doorsOpen = false;
        }
    }

    public void updateEntities(float deltaTime, Player player){
        for (Enemy currentEnemy : enemies){
            currentEnemy.updateTowardsPlayer(player, deltaTime);
            checkPosition(currentEnemy.getPosition());
        }
        player.update(deltaTime);
        checkPosition(player.getPosition());
    }

    private void checkPosition(Position position){

    }

    public boolean doorsAreOpen(){
        return doorsOpen;
    }


}

