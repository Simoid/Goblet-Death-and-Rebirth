package com.goblet.gameEngine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.goblet.graphics.SpriteAnimation;

public class Engine extends ApplicationAdapter {
	//TEST COMMENT!
	private SpriteBatch batch;
	private BitmapFont font;
	private float elapsedTime = 0f;
	private SpriteAnimation mc_walk;
	private float mc_x = 0;
	private float mc_y = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.RED);
		mc_walk = new SpriteAnimation("assets/sprites/mc_frontwalk.pack", 4, 2, 1/5f);
	}

	@Override
	public void dispose(){
		batch.dispose();
		font.dispose();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
				mc_x -= 1f;
			else
				mc_x -= 3.0f;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
				mc_x += 1f;
			else
				mc_x += 3.0f;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
				mc_y -= 1f;
			else
				mc_y -= 3.0f;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
				mc_y += 1f;
			else
				mc_y += 3.0f;
		}


		batch.begin();
		font.draw(batch, "Hello world!", 200, 200);
		elapsedTime += Gdx.graphics.getDeltaTime();
		mc_walk.draw(batch, mc_x, mc_y, elapsedTime);
		batch.end();
	}

	@Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
