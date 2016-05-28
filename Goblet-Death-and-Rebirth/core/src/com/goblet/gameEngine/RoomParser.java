package com.goblet.gameEngine;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.goblet.level.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Random;

/**
 * Created by Simoido on 2016-05-19.
 */
public class RoomParser {
    Reader reader;
    JsonElement je;
    private int tilesHeight;
    private int tilesWidth;
    private SpriteBatch batch;
    private EnemyConstructor enemyConstructor;
    private Position bottomLeft;
    private Position topRight;
    private int numberOfRoomsInJson;

    public RoomParser(String fileName, Position bottomLeft, Position topRight, int numberOfRoomsInJson){
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
        this.numberOfRoomsInJson = numberOfRoomsInJson;
        enemyConstructor = new EnemyConstructor();
        batch = new SpriteBatch();

        tilesHeight = 16;
        tilesWidth = 26;
        try {
            reader = new FileReader("assets/jsonFiles/" + fileName);
            je = new JsonParser().parse(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Room createRandom(){
        Random randomizer = new Random();
        String roomName = "room" + (randomizer.nextInt(numberOfRoomsInJson) + 1);
        return createRoom(roomName);
    }

    public Room createRoom(String roomName){
        //TODO

        Room room = new Room(bottomLeft, topRight);

        TileType[][] roomTiles = getRoomTiles(roomName);

        SpawnPoint[][] spawnPoints = getSpawns(roomName);
        for(int i = 0 ; i < tilesHeight ; i++){
            for(int j = 0 ; j < tilesWidth ; j++){
                if( roomTiles[i][j] == TileType.STONE){
                    //Put stone on position vertical = i, horizontal  = j;
                    room.addGObstacles(new Rock(bottomLeft.getX() + j*15 + 45,topRight.getY()-15*i-30, 15,15,"assets/tiles/rock/rock.pack"));
                }
            }
        }

        for(int i = 0 ; i < tilesHeight ; i++){
            for(int j = 0 ; j < tilesWidth ; j++){
                    //Put enemies on position vertical = i, horizontal  = j;
                if(spawnPoints[i][j] != null) {
                    switch (spawnPoints[i][j]) {
                        case KING:
                            room.addEnemy(enemyConstructor.createEnemy("king", bottomLeft.getX() + j*15 + 45, topRight.getY()-15*i-30));
                            break;
                        case BAT:
                            room.addEnemy(enemyConstructor.createEnemy("bat", bottomLeft.getX() + j*15 + 45, topRight.getY()-15*i-30));
                            break;
                        case DATBOI:
                            room.addEnemy(enemyConstructor.createEnemy("datboi", bottomLeft.getX() + j*15 + 45, topRight.getY()-15*i-30));
                            break;
                        case MASK:
                            room.addEnemy(enemyConstructor.createEnemy("mask", bottomLeft.getX() + j*15 + 45, topRight.getY()-15*i-30));
                            break;
                        case SPIDER:
                            room.addEnemy(enemyConstructor.createEnemy("spider", bottomLeft.getX() + j*15 + 45, topRight.getY()-15*i-30));
                            break;
                        case PRISM:
                            room.addEnemy(enemyConstructor.createEnemy("prism", bottomLeft.getX() + j*15 + 45, topRight.getY()-15*i-30));
                            break;
                        case EYE:
                            room.addEnemy(enemyConstructor.createEnemy("eye",bottomLeft.getX() + j*15 + 45, topRight.getY()-15*i-30));
                            break;
                    }
                }
            }
        }
        return room;
    }

    public TileType[][] getRoomTiles(String roomName){
        String[][] stringArray = new String[tilesHeight][tilesWidth];
        TileType[][] returnArray = new TileType[tilesHeight][tilesWidth];
        for(int i = 0 ; i < tilesHeight ; i++){
            for(int j = 0 ; j < tilesWidth ; j++){
                stringArray[i][j] = je.getAsJsonObject().get(roomName).getAsJsonArray().get(i).getAsJsonArray().get(j).getAsString();
            }
        }
        for (int i = 0; i < tilesHeight; i++) {
            for (int j = 0; j < tilesWidth; j++) {
                returnArray[i][j] = TileType.translate(stringArray[i][j]);
            }
        }
        return returnArray;
    }

    public SpawnPoint[][] getSpawns(String roomName){
        String[][] stringArray = new String[tilesHeight][tilesWidth];
        SpawnPoint[][] returnArray = new SpawnPoint[tilesHeight][tilesWidth];
        for(int i = 0 ; i < tilesHeight ; i++){
            for(int j = 0 ; j < tilesWidth ; j++){
                stringArray[i][j] = je.getAsJsonObject().get(roomName).getAsJsonArray().get(i).getAsJsonArray().get(j).getAsString();
            }
        }
        for (int i = 0; i < tilesHeight; i++) {
            for (int j = 0; j < tilesWidth; j++) {
                returnArray[i][j] = SpawnPoint.translate(stringArray[i][j]);
            }
        }
        return returnArray;
    }

}
