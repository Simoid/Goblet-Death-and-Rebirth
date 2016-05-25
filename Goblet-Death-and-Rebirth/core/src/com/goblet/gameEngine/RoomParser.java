package com.goblet.gameEngine;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.goblet.level.Room;
import com.goblet.level.SpawnPoint;
import com.goblet.level.Tile;
import com.goblet.level.TileType;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * Created by Simoido on 2016-05-19.
 */
public class RoomParser {
    Reader reader;
    JsonElement je;
    private int tilesHeight;
    private int tilesWidth;
    private SpriteBatch batch;
    private Camera camera;
    private Viewport viewPort;
    private EnemyConstructor enemyConstructor;



    public RoomParser(String fileName){
        enemyConstructor = new EnemyConstructor();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewPort = new FitViewport(480, 270, camera);
        viewPort.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
        batch.setProjectionMatrix(camera.combined);

        tilesHeight = 16;
        tilesWidth = 26;
        try {
            reader = new FileReader("assets/jsonFiles/" + fileName);
            je = new JsonParser().parse(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Room createRoom(float bottomLeftX, float bottomLeftY, float topRightX, float topRightY, String roomName){
        //TODO
        Room room = new Room(-camera.viewportWidth/2, -camera.viewportHeight/2, camera.viewportWidth/2, camera.viewportHeight/2, null, null);
        TileType[][] roomTiles = getRoomTiles(roomName);
        SpawnPoint[][] spawnPoints = getSpawns(roomName);
        for(int i = 0 ; i < tilesHeight ; i++){
            for(int j = 0 ; j < tilesWidth ; j++){
                if( roomTiles[i][j] == TileType.STONE){
                    //Put stone on position vertical = i, horizontal  = j;

                }
            }
        }

        for(int i = 0 ; i < tilesHeight ; i++){
            for(int j = 0 ; j < tilesWidth ; j++){
                    //Put enemies on position vertical = i, horizontal  = j;
                switch (spawnPoints[i][j]){
                    case KING:
                        room.addEnemy(enemyConstructor.createEnemy("king",i*15,j*15));
                        break;
                    case BAT:
                        room.addEnemy(enemyConstructor.createEnemy("bat",i*15,j*15));
                        break;
                    case DATBOI:
                        room.addEnemy(enemyConstructor.createEnemy("datboi",i*15,j*15));
                        break;
                    case MASK:
                        room.addEnemy(enemyConstructor.createEnemy("mask",i*15,j*15));
                        break;
                    case SPIDER:
                        room.addEnemy(enemyConstructor.createEnemy("spider",i*15,j*15));
                        break;
                    case PRISM:
                        room.addEnemy(enemyConstructor.createEnemy("prism",i*15,j*15));
                        break;
                    case EYE:
                        room.addEnemy(enemyConstructor.createEnemy("eye",i*15,j*15));
                        break;
                }
            }
        }
        return null;
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



    /*
    public static void main(String[]arg){
        RoomParser rp = new RoomParser("room1","rooms.json");
        String[][] array = rp.getRoom("room1");
        for(int i = 0; i < 16 ; i++){
            for(int j = 0 ; j < 26 ; j++){
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }
    */
}
