package com.goblet.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Engine extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	private BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.RED);
		texture = new Texture(Gdx.files.internal("assets/sprites/goblet_MC_new.png"));
		sprite = new Sprite(texture);
	}
	
	@Override
	public void dispose(){
		batch.dispose();
		font.dispose();
		f;
	}
	
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		font.draw(batch, "Hello world!", 200, 200);
		sprite.draw(batch);
		batch.end();
	}
	
	@Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
