package com.goblet.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.goblet.entities.Direction;
import com.goblet.entities.Enemy;
import com.goblet.entities.Entity;
import com.goblet.entities.Player;
import com.goblet.gameEngine.Box;
import com.goblet.graphics.Score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
    private ArrayList<GObstacles> gObstacles;
    private TextureAtlas atlas;
    private Position bottomLeft;
    private Position topRight;
    private boolean doorsOpen;
    private Direction nextRoom = null;
    private Sound openDoorSound;


    /**
     * Ritar ut allt i rummet.
     * Sakerna ritas ut i en logisk ordning: Först golvet och väggarna, sedan alla entities, och sist taket.
     * @param batch Batchen som ska ritas på.
     * @param player Spelaren som ska ritas ut.
     */
    public void draw(Batch batch, Player player){
        drawFloorAndWalls(batch);
        drawEntitites(batch, player);
        drawRoof(batch);
    }

    public void addGObstacles(GObstacles GOb){
        gObstacles.add(GOb);
    }

    /**
     * Ritar ut golvet, väggarna och GObstacles
     * @param batch Batchen som ska ritas på.
     */
    private void drawFloorAndWalls(Batch batch){
        batch.draw(floorTextureRegion, -floorTextureRegion.getRegionWidth()/2, bottomLeft.getY(), floorTextureRegion.getRegionWidth(), floorTextureRegion.getRegionHeight());
        for (WallObject currentWall : wallMap.values()){
            currentWall.draw(batch);
        }


        for (GObstacles currentGObstacle : gObstacles){
            currentGObstacle.draw(batch);
        }
    }


    /**
     * Ritar ut alla enemies, och spelaren.
     * @param batch Batchen som ska ritas på.
     * @param player Spelaren som ska ritas ut.
     */
    private void drawEntitites(Batch batch, Player player){
        for (Enemy currentEnemy : enemies){
            currentEnemy.draw(batch);
        }
        player.draw(batch);
    }

    /**
     * Ritar ut taket ovanför dörrarna till vänster och höger.
     * @param batch Batchen som ska ritas på.
     */
    private void drawRoof(Batch batch){
        for (RoofObject currentRoof : roofMap.values()){
            if (wallMap.get(currentRoof.getDirection()).hasDoor()) {
                currentRoof.draw(batch);
            }
        }
    }

    /**
     * Konstruktorn för rummet.
     * @param bottomLeft Nedre vänstra hörnet på skärmen.
     * @param topRight Högre högra hörnet på skärmen.
     */
    public Room(Position bottomLeft, Position topRight){
        this.bottomLeft = new Position(bottomLeft);
        this.topRight = new Position(topRight);

        gObstacles = new ArrayList<GObstacles>();
        wallMap = new HashMap<Direction, WallObject>();
        roofMap = new HashMap<Direction, RoofObject>();
        tiles = new Tile[16][26];

        enemies = new ArrayList<Enemy>();
        //enemies.add(new Enemy(new Position(150, 150), "king/king", 3, 9, 50f, 100, 2, "move", "melee", new Box(new Position(150, 150), 75, 49),1/5f,1/9f));

        atlas = new TextureAtlas(Gdx.files.internal(atlasLocation));

        wallMap.put(Direction.UP, new WallObject(atlas.findRegion("spr_upper_wall"), atlas.findRegion("spr_upper_wall_path"), atlas.findRegion("spr_upper_door"), Direction.UP, bottomLeft, topRight, false));
        wallMap.put(Direction.DOWN, new WallObject(atlas.findRegion("spr_lower_wall"), atlas.findRegion("spr_lower_wall_path"), atlas.findRegion("spr_lower_door"), Direction.DOWN, bottomLeft, topRight, false));
        wallMap.put(Direction.LEFT, new WallObject(atlas.findRegion("spr_left_wall"), atlas.findRegion("spr_left_wall_path"), atlas.findRegion("spr_left_door"), Direction.LEFT, bottomLeft, topRight, false));
        wallMap.put(Direction.RIGHT, new WallObject(atlas.findRegion("spr_right_wall"), atlas.findRegion("spr_right_wall_path"), atlas.findRegion("spr_right_door"), Direction.RIGHT, bottomLeft, topRight, false));

        roofMap.put(Direction.LEFT, new RoofObject(atlas.findRegion("spr_left_wall_path_roof"), Direction.LEFT, bottomLeft, topRight));
        roofMap.put(Direction.RIGHT, new RoofObject(atlas.findRegion("spr_right_wall_path_roof"), Direction.RIGHT, bottomLeft, topRight));

        floorTextureRegion = atlas.findRegion("spr_floor");
        openDoorSound = Gdx.audio.newSound(Gdx.files.internal("assets/audio/door_open.mp3"));
    }

    public void openDoors(){
        for (WallObject currentWall : wallMap.values()){
            currentWall.openDoor();
            doorsOpen = true;
        }

        openDoorSound.play();
    }

    public void addEnemy(Enemy enemy){
        enemies.add(enemy);
    }

    public void closeDoors(){
        for (WallObject currentWall : wallMap.values()){
            currentWall.closeDoor();
            doorsOpen = false;
        }
    }

    public void addDoor(Direction dir){
        wallMap.get(dir).addDoor();
    }

    public void playerEnter(Direction dir, Player player){
        dir = Direction.opposite(dir);
        player.setPosition(new Position(wallMap.get(dir).getEnterPosition()));
        player.setInvincible();
    }

    public void updateEntities(float deltaTime, Player player, Score score){
        Iterator<Enemy> enemyIterator = enemies.iterator();
        Enemy currentEnemy;
        while (enemyIterator.hasNext()){
            currentEnemy = enemyIterator.next();
            currentEnemy.updateTowardsPlayer(player, deltaTime);
            for (Enemy otherEnemy : enemies){
                if (otherEnemy != currentEnemy && otherEnemy.getHitbox().collides(currentEnemy.getHitbox())) {
                    fixMovementEntity(currentEnemy, otherEnemy);
                }
            }
            if (player.getHitbox().collides(currentEnemy.getHitbox())){
                fixMovementEntity(currentEnemy, player);
            }
            for (WallObject wall : wallMap.values()){
                if (wall.getNoEntititesZone().collides(currentEnemy.getHitbox())){
                    fixMovementWall(wall, currentEnemy);
                }
            }
            for (GObstacles obstacle : gObstacles){
                if (!currentEnemy.canFly() && obstacle.getHitbox().collides(currentEnemy.getHitbox())){
                    fixMovementGObstacle(obstacle, currentEnemy);
                }
            }
            checkPosition(currentEnemy.getPosition());
            if (currentEnemy.isAttacking() && player.getHitbox().getMiddlePos().distance(currentEnemy.getHitbox().getMiddlePos()) < currentEnemy.getAttackRange()){
                player.takeDamage();
            }
            if (player.isAttacking() && player.getAttackHitbox().collides(currentEnemy.getHitbox())){
                currentEnemy.takeDamage();
                if (currentEnemy.isDead()) {
                    System.out.println(currentEnemy.getScoreGain());
                    score.increaseScore(currentEnemy.getScoreGain());
                    enemyIterator.remove();
                }
            }
        }
        player.update(deltaTime);
        for (WallObject wall : wallMap.values()){
            if (wall.getNoEntititesZone().collides(player.getHitbox()) && (!wall.hasDoor() || (wall.getDoorZone() != null && !wall.getDoorZone().collides(player.getHitbox())) || wall.doorIsClosed())){
                fixMovementWall(wall, player);
            } else if (wall.getNextRoomZone() != null && wall.getNextRoomZone().collides(player.getHitbox())){
                nextRoom = wall.getDir();
            }
        }
        for (GObstacles obstacle : gObstacles){
            if (obstacle.getHitbox().collides(player.getHitbox())){
                fixMovementGObstacle(obstacle, player);
            }
        }
        checkPosition(player.getPosition());
        if (enemies.size() == 0 && !doorsOpen){
            openDoors();
        }
    }

    public Direction nextRoom(){
        return nextRoom;
    }

    public void clearNextRoom(){
        nextRoom = null;
    }

    private void fixMovementWall(WallObject wall, Entity entity){
        Direction dir = wall.getDir();
        switch (dir){
            case LEFT:
                entity.setPosition(new Position(wall.getNoEntititesZone().getX() + wall.getNoEntititesZone().getWidth() + entity.getHitbox().getWidth()/2 - entity.getHitbox().getOffsetX(), entity.getPosition().getY()));
                break;
            case RIGHT:
                entity.setPosition(new Position(wall.getNoEntititesZone().getX() - entity.getHitbox().getWidth()/2 - entity.getHitbox().getOffsetX(), entity.getPosition().getY()));
                break;
            case UP:
                entity.setPosition(new Position(entity.getPosition().getX(), wall.getNoEntititesZone().getY() - entity.getHitbox().getHeight()/2 - entity.getHitbox().getOffsetY()));
                break;
            case DOWN:
                entity.setPosition(new Position(entity.getPosition().getX(), wall.getNoEntititesZone().getY() + wall.getNoEntititesZone().getHeight() + entity.getHitbox().getHeight()/2 - entity.getHitbox().getOffsetY()));
                break;

        }
    }

    private void fixMovementEntity(Entity first, Entity second){
        switch (first.getHitbox().getBiggestIntersectionDirection(second.getHitbox())){
            case LEFT:
                if (first.canMove()) {
                    first.setPosition(second.getHitbox().getX() + second.getHitbox().getWidth() + first.getHitbox().getWidth() / 2 - first.getHitbox().getOffsetX(), first.getPosition().getY());
                }
                break;
            case RIGHT:
                if (first.canMove()){
                    first.setPosition(second.getHitbox().getX() - first.getHitbox().getWidth()/2 - first.getHitbox().getOffsetX(), first.getPosition().getY());
                }
                break;
            case UP:
                if (first.canMove()){
                    first.setPosition(first.getPosition().getX(), second.getHitbox().getY() - first.getHitbox().getHeight()/2 - first.getHitbox().getOffsetY());
                }
                break;
            case DOWN:
                if (first.canMove()){
                    first.setPosition(first.getPosition().getX(), second.getHitbox().getY() + second.getHitbox().getHeight() + first.getHitbox().getHeight()/2 - first.getHitbox().getOffsetY());
                }
                break;
        }
    }

    /**
     * Metoden tittar på en entity och en GObstacle som har kolliderat, och räknar ut var entityn ska placeras.
     * @param obstacle GObstaclet som kolliderade.
     * @param entity Entityn som kolliderade.
     */
    private void fixMovementGObstacle(GObstacles obstacle, Entity entity){
        switch (entity.getHitbox().getBiggestIntersectionDirection(obstacle.getHitbox())){
            case LEFT:
                entity.setPosition(obstacle.getHitbox().getX() + obstacle.getHitbox().getWidth() + entity.getHitbox().getWidth()/2 - entity.getHitbox().getOffsetX(), entity.getPosition().getY());
                break;
            case RIGHT:
                entity.setPosition(obstacle.getHitbox().getX() - entity.getHitbox().getWidth()/2 - entity.getHitbox().getOffsetX(), entity.getPosition().getY());
                break;
            case UP:
                entity.setPosition(entity.getPosition().getX(), obstacle.getHitbox().getY() - entity.getHitbox().getHeight()/2 - entity.getHitbox().getOffsetY());
                break;
            case DOWN:
                entity.setPosition(entity.getPosition().getX(), obstacle.getHitbox().getY() + obstacle.getHitbox().getHeight() + entity.getHitbox().getHeight()/2 - entity.getHitbox().getOffsetY()/2);
                break;
        }
    }

    private void checkPosition(Position position){

    }

    public boolean doorsAreOpen(){
        return doorsOpen;
    }

    public void dispose(){
        for (Enemy enemy : enemies){
            enemy.dispose();
        }
    }



}

