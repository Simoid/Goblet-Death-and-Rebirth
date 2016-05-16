package com.goblet.gameEngine;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.goblet.entities.Player;
import com.goblet.graphics.SpriteAnimation;

public class Engine implements ApplicationListener, InputProcessor {

	private float timeBetweenUpdates = 1/120f;
	private float timeCounter = 0f;
	private SpriteBatch batch;
	private BitmapFont font;
	private float elapsedTime = 0f;
	private SpriteAnimation mc_walk;
	private Player player;
	// private float mc_x = 0f;
	// private float mc_y = 0f;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.RED);
		mc_walk = new SpriteAnimation("assets/sprites/mc_frontwalk.pack", 4, 2, 1/5f);
		player = new Player(50, 50);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void dispose(){
		batch.dispose();
		font.dispose();
	}

	public void update(float deltaTime){
		player.update(deltaTime);
	}

	@Override
	public void render () {
		timeCounter += Gdx.graphics.getDeltaTime();
		if (timeCounter > timeBetweenUpdates){
			update(timeCounter);
			timeCounter = 0f;
		}
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		font.draw(batch, "Hello world!", 200, 200);
		elapsedTime += Gdx.graphics.getDeltaTime();
		//mc_walk.draw(batch, mc_x, mc_y, elapsedTime);
		player.draw(batch);
		batch.end();
	}

	@Override
	public boolean keyDown(int keycode) {
		player.startMove(keycode);
		/*float moveAmount = 3.0f;
		if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
			moveAmount = 6.0f;
		}

		if (keycode == Input.Keys.LEFT)
			mc_x -= moveAmount;
		if (keycode == Input.Keys.RIGHT)
			mc_x += moveAmount;
		if (keycode == Input.Keys.UP)
			mc_y += moveAmount;
		if (keycode == Input.Keys.DOWN)
			mc_y -= moveAmount;*/



		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		player.stopMove(keycode);
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
}
