package com.goblet.gameEngine;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.goblet.entities.Direction;
import com.goblet.entities.Enemy;
import com.goblet.entities.Entity;
import com.goblet.entities.Player;
import com.goblet.graphics.Map;
import com.goblet.graphics.Score;
import com.goblet.graphics.UserInterface;
import com.goblet.level.Floor;
import com.goblet.level.Room;
import com.goblet.level.Position;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Huvudklassen för spelet, här ligger spel-loopen.
 *
 * Den här klassen innehåller de flesta entity-objekten och room-objekten, och ritar ut allt sam uppdaterar allt.
 *
 */
public class Engine implements ApplicationListener, InputProcessor {

    private Floor floor;
	private float timeBetweenUpdates = 1/120f;
	private float timeCounter = 0f;
	private SpriteBatch batch;
    private BitmapFont font;
	private Camera camera;
    private Viewport viewPort;
    private ArrayList<Entity> enemies;
	private RoomParser roomParser;
    private Player player;
    private Room currentRoom;
	private EnemyParser enemyParser;
    private UserInterface ui;
    private boolean gameover = false;
    private int changeRoom = -100;
	private Map map;
	private Sound music;
    //private Enemy testEnemy;

    /**
     * Skapar grafiken för spelet, och sedan de objekt som ska finnas från början: spelaren, och ett rum (+testfiender).
     */
	@Override
	public void create () {
		music = Gdx.audio.newSound(Gdx.files.internal("assets/audio/wraiths_waltz.wav"));
		music.loop();
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
		camera = new OrthographicCamera();
        viewPort = new FitViewport(480, 270, camera);
        viewPort.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
        batch.setProjectionMatrix(camera.combined);


        Position bottomLeft = new Position(-camera.viewportWidth/2, -camera.viewportHeight/2);
        Position topRight = new Position(camera.viewportWidth/2, camera.viewportHeight/2);



		enemyParser = new EnemyParser("enemies.json");
        roomParser = new RoomParser("rooms.json", bottomLeft, topRight, 20);
        floor = new Floor(15, roomParser);
        currentRoom = floor.getFirstRoom();

		player = new Player(0, 0,100f);
        ui = new UserInterface(bottomLeft, topRight,floor);

		//setCurrentRoom("room1");
		//currentRoom = new Room(bottomLeft, topRight);

        enemies = new ArrayList<Entity>();
        //enemies.add(testEnemy);

		map = new Map(topRight,floor);


		Gdx.input.setInputProcessor(this);
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
	}

	@Override
	public void resize(int width, int height) {
        viewPort.update(width, height);
	}

    /**
     * Tar bort de objekt som inte kan hanteras av javas GC.
     */
	@Override
	public void dispose(){
        player.dispose();
        batch.dispose();
        currentRoom.dispose();
	}

    /**
     * Uppdaterar objekten.
     * @param deltaTime
     */
	public void update(float deltaTime){
        currentRoom.updateEntities(deltaTime, player, ui.getScoreObject());}

    /**
     * Uppdaterar objekten och ritar ut dem.
     * Objekten ritas ut så ofta som möjligt, men uppdateras högst 120 gånger per sekund.
     * Detta eftersom spelet inte gynnas av att uppdatera överdrivet ofta, medan extra fps aldrig skadar.
     */
	@Override
	public void render () {
        // Uppdatera om det har gått tillräckligt lång tid sen senaste uppdateringen.
		timeCounter += Gdx.graphics.getDeltaTime();
		if (timeCounter > timeBetweenUpdates && !gameover){
			if (currentRoom.nextRoom() != null && changeRoom <= -100){
                changeRoom = 100;
            }
            if (changeRoom <= -100) {
                update(timeCounter);
            } else if (changeRoom == 0){
                Direction dir = currentRoom.nextRoom();
                Room lastRoom = currentRoom;
                currentRoom = floor.getNextRoom(currentRoom.nextRoom());
                currentRoom.playerEnter(dir, player);
                lastRoom.clearNextRoom();
                changeRoom -= 5;
                map.update();
            } else {
                changeRoom -= 5;
            }

			timeCounter = 0f;
            if (player.getHP() <= 0){
                gameover = true;
                timeCounter = 1f;
                pause();
            }
		}

        camera.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        if (gameover){
            timeCounter -= Gdx.graphics.getDeltaTime()*2;
            if (timeCounter <= 0){
                timeCounter = 0;
            }
            batch.setColor(1, 1, 1, timeCounter);
        }
		if (!gameover) {
			batch.setColor(1, 1, 1, Math.abs((float) changeRoom / 100f));
		}
        currentRoom.draw(batch, player);
        ui.draw(batch, player);
		map.draw(batch);
		batch.end();
		//System.out.println("FPS: " + Gdx.graphics.getFramesPerSecond());
	}


    /**
     *
     * Tells the player object that a key has been pressed.
     * The player object handles what to do for specific keys.
     * The method checks for the escape key before sending the keycode to the player object,
     * since the Engine class is the one that can exit the game.
     * @param keycode The code of the key that was pressed.
     */
	@Override
	public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE){
            quit();
        }
		player.keyPressed(keycode);
		return true;
	}

	/**
	 * Tells the player object that a key has been released.
	 * The player object handles what to do for specific keys.
	 * @param keycode The code of the key that was released.
     */
	@Override
	public boolean keyUp(int keycode) {
		player.keyReleased(keycode);
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

	/**
	 * Exits the game.
	 */
    private void quit(){
        Gdx.app.exit();
    }

	public void setCurrentRoom(String roomName){
		currentRoom = roomParser.createRoom(roomName) ;
	}

}
