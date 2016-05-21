package com.goblet.gameEngine;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.goblet.entities.Player;
import com.goblet.graphics.SpriteAnimation;

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
	private HappyJasonParser happyJasonParser;

	@Override
	public void create () {
		happyJasonParser = new HappyJasonParser();
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.GREEN);
        xScale = Gdx.graphics.getWidth() / 640;
        yScale = Gdx.graphics.getHeight() / 360;
		player = new Player(50, 50, xScale, yScale);
		Gdx.input.setInputProcessor(this);
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		try {
			System.out.println(happyJasonParser.getName());
		} catch (FileNotFoundException exception){

		}
	}

	@Override
	public void resize(int width, int height) {
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
