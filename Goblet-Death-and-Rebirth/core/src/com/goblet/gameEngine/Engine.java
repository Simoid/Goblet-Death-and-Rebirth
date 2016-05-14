package com.goblet.gameEngine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;

public class Engine extends ApplicationAdapter {
	//TEST COMMENT!
	private SpriteBatch batch;
	private BitmapFont font;
	private TextureAtlas atlas;
	private Animation animation;
	private float elapsedTime = 0f;
	private TextureRegion[] frontwalk = new TextureRegion[4];
	private float mc_x = 0;
	private float mc_y = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.RED);
		atlas = new TextureAtlas(Gdx.files.internal("assets/sprites/mc_frontwalk.pack"));
		frontwalk[0] = (atlas.findRegion("spr_mc_frontwalk00"));
		frontwalk[1] = (atlas.findRegion("spr_mc_frontwalk01"));
		frontwalk[2] = (atlas.findRegion("spr_mc_frontwalk02"));
		frontwalk[3] = (atlas.findRegion("spr_mc_frontwalk03"));
		animation = new Animation(1/8f, frontwalk);
	}

	@Override
	public void dispose(){
		batch.dispose();
		font.dispose();
		atlas.dispose();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
				mc_x -= 1f;
			else
				mc_x -= 5.0f;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
				mc_x += 1f;
			else
				mc_x += 5.0f;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
				mc_y -= 1f;
			else
				mc_y -= 5.0f;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
				mc_y += 1f;
			else
				mc_y += 5.0f;
		}


		batch.begin();
		font.draw(batch, "Hello world!", 200, 200);
		elapsedTime += Gdx.graphics.getDeltaTime();
		batch.draw(animation.getKeyFrame(elapsedTime, true), mc_x, mc_y);
		batch.end();
	}

	@Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
