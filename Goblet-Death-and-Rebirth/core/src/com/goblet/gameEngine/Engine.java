package com.goblet.gameEngine;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.goblet.entities.Player;
import com.goblet.graphics.SpriteAnimation;
import com.goblet.level.Room;
import com.sun.prism.image.ViewPort;

import java.io.FileNotFoundException;

public class Engine implements ApplicationListener, InputProcessor {

	private float timeBetweenUpdates = 1/120f;
	private float timeCounter = 0f;
	private SpriteBatch batch;
	private BitmapFont font;
	private float elapsedTime = 0f;
	private SpriteAnimation mc_walk;
	private Player player;
    private float xScale;
    private float yScale;
	private Camera camera;
    private Viewport viewPort;
	private Room startRoom;

	@Override
	public void create () {
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.GREEN);
		camera = new OrthographicCamera();
        viewPort = new FitViewport(480, 270, camera);
        viewPort.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		batch.setProjectionMatrix(camera.combined);
		xScale = 1.0f;
		yScale = 1.0f;
		System.out.println("Width: " + Gdx.graphics.getWidth() + ", Height: " + Gdx.graphics.getHeight() + ", xScale: " + xScale + ", yScale: " + yScale + ", 16/9 = " + 16/9 + ", Width/height = " + Gdx.graphics.getWidth()/Gdx.graphics.getHeight());
		player = new Player(50, 50, xScale, yScale);
		startRoom = new Room();
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void resize(int width, int height) {
        viewPort.update(width, height);
	}

	@Override
	public void dispose(){
		font.dispose();
        player.dispose();
        batch.dispose();
	}

	public void update(float deltaTime){
		player.update(deltaTime);
	}

	@Override
	public void render () {
        // Uppdatera om det har gått tillräckligt lång tid sen senaste uppdateringen.
		timeCounter += Gdx.graphics.getDeltaTime();
		if (timeCounter > timeBetweenUpdates){
			update(timeCounter);
			timeCounter = 0f;
		}

        camera.update();
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, Gdx.graphics.getHeight());
		elapsedTime += Gdx.graphics.getDeltaTime();
		//mc_walk.draw(batch, mc_x, mc_y, elapsedTime);
		player.draw(batch);
		batch.end();
	}


	@Override
	public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE){
            quit();
        } else if (keycode == Input.Keys.CONTROL_LEFT){
            player.increaseScale();
        } else if (keycode == Input.Keys.SHIFT_LEFT){
            player.decreaseScale();
        }
		player.keyPressed(keycode);
		return true;
	}

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

    private void quit(){
        Gdx.app.exit();
    }

}
