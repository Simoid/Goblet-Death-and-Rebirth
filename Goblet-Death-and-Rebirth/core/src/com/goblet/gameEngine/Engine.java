package com.goblet.gameEngine;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.goblet.entities.Enemy;
import com.goblet.entities.Entity;
import com.goblet.entities.Player;
import com.goblet.level.Room;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Huvudklassen för spelet, här ligger spel-loopen.
 *
 * Den här klassen innehåller de flesta entity-objekten och room-objekten, och ritar ut allt sam uppdaterar allt.
 *
 */
public class Engine implements ApplicationListener, InputProcessor {

	private float timeBetweenUpdates = 1/120f;
	private float timeCounter = 0f;
	private SpriteBatch batch;
	private Camera camera;
    private Viewport viewPort;
    private ArrayList<Entity> enemies;

    private Player player;
    private Room startRoom;
	private EnemyParser enemyParser;
    private Enemy testEnemy;

    /**
     * Skapar grafiken för spelet, och sedan de objekt som ska finnas från början: spelaren, och ett rum (+testfiender).
     */
	@Override
	public void create () {
		enemyParser = new EnemyParser("enemies.json");


		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
        viewPort = new FitViewport(480, 270, camera);
        viewPort.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		batch.setProjectionMatrix(camera.combined);



		player = new Player(0, 0,100f);
		startRoom = new Room(-camera.viewportWidth/2, -camera.viewportHeight/2, camera.viewportWidth/2, camera.viewportHeight/2);
        testEnemy = new Enemy(50, 0, "king/king", 2, 3, 3,50f);

        enemies = new ArrayList<Entity>();
        enemies.add(testEnemy);

		Gdx.input.setInputProcessor(this);
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		try {
			System.out.println(enemyParser.getName("bat"));
		}catch (FileNotFoundException ex){

		}
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
        for (Entity entity : enemies){
            entity.dispose();
        }
	}

    /**
     * Uppdaterar objekten.
     * @param deltaTime
     */
	public void update(float deltaTime){
        testEnemy.updateTowardsPlayer(player, deltaTime);
		player.update(deltaTime);
	}

    /**
     * Uppdaterar objekten och ritar ut dem.
     * Objekten ritas ut så ofta som möjligt, men uppdateras högst 120 gånger per sekund.
     * Detta eftersom spelet inte gynnas av att uppdatera överdrivet ofta, medan extra fps aldrig skadar.
     */
	@Override
	public void render () {
        // Uppdatera om det har gått tillräckligt lång tid sen senaste uppdateringen.
		timeCounter += Gdx.graphics.getDeltaTime();
		if (timeCounter > timeBetweenUpdates){
			update(timeCounter);
			timeCounter = 0f;
		}

        camera.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        startRoom.draw(batch);
        testEnemy.draw(batch);
		player.draw(batch);
		batch.end();
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
        } else if (keycode == Input.Keys.SPACE) {
            if (!startRoom.doorsAreOpen()) {
                startRoom.openDoors();
            } else {
                startRoom.closeDoors();
            }
        } else if (keycode == Input.Keys.CONTROL_LEFT){
            player.shouldDrawHitbox();
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

}
