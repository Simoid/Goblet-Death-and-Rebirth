package com.goblet.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;

/**
 * Created by johan on 5/14/16.
 */
public class SpriteAnimation {
    private TextureRegion[] regions;
    private float scale;
    private Animation animation;

    public SpriteAnimation(String atlasLocation, int numberOfSprites, float scale, float animationTime){
        this.scale = scale;
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(atlasLocation));
        regions = new TextureRegion[numberOfSprites];
        for (int i = 0; i < numberOfSprites; i++){
            regions[i] = atlas.findRegion(String.format("%04d", i));
        }
        animation = new Animation(animationTime, regions);
    }

    public void draw(Batch batch, float x, float y, float time){
        TextureRegion region = animation.getKeyFrame(time, true);
        batch.draw(region, x, y, region.getRegionWidth()*scale, region.getRegionHeight()*scale);
    }
}