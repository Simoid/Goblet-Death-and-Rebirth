package com.goblet.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Timer;

public class Engine extends ApplicationAdapter {
	//TEST COMMENT!
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	private BitmapFont font;
	private TextureAtlas atlas;
	private int currentFrame = 1;
	private String currentAtlasKey = "spr_mc_frontwalk00";

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.RED);
		atlas = new TextureAtlas(Gdx.files.internal("assets/sprites/mc_frontwalk.pack"));
		TextureAtlas.AtlasRegion region = atlas.findRegion(currentAtlasKey);
		texture = new Texture(Gdx.files.internal("assets/sprites/goblet_MC_new.png"));
		sprite = new Sprite(region);
		sprite.setPosition(120, 100);
		sprite.scale(2.5f);
		Timer.schedule(new Timer.Task(){
						   @Override
						   public void run() {
							   currentFrame++;
							   if(currentFrame > 3)
								   currentFrame = 0;

							   // ATTENTION! String.format() doesnt work under GWT for god knows why...
							   currentAtlasKey = "spr_mc_frontwalk" + String.format("%02d", currentFrame);
							   sprite.setRegion(atlas.findRegion(currentAtlasKey));
						   }
					   }
				,0,1/5.0f);
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
