package com.goblet.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;

/**
 * En klass som innehåller en animation och kan rita ut den större eller mindre. Klassen kräver en spritesheet
 * och en .atlas-fil som beskriver spritesheetet, samt att alla bilder i atlas-filen är döpta från 0000.png och
 * uppåt.
 * Created by johan on 5/14/16.
 */
public class SpriteAnimation {
    private TextureRegion[] regions;
    private float xScale;
    private float yScale;
    private Animation animation;
    private TextureAtlas atlas;

    /**
     * Konstruktur för klassen.
     * @param atlasLocation Filvägen till atlas filen (oftast "assets/sprites/***.pack")
     * @param numberOfSprites Hur många bilder som ingår i animationen.
     * @param xScale Skalan på bilden i x-led.
     * @param yScale Skalan på bilden i y-led.
     * @param animationTime Tidsskillnaden mellan bildbyte.
     */
    public SpriteAnimation(String atlasLocation, int numberOfSprites, float xScale, float yScale, float animationTime){
        this.xScale = xScale;
        this.yScale = yScale;
        atlas = new TextureAtlas(Gdx.files.internal(atlasLocation));
        regions = new TextureRegion[numberOfSprites];
        for (int i = 0; i < numberOfSprites; i++){
            regions[i] = atlas.findRegion(String.format("%04d", i));
        }
        animation = new Animation(animationTime, regions);
    }

    /**
     * Funktion för att sätta storleken på bilden som ritas ut.
     * Parametern är en int som sedan görs om till float, detta för att se till att alla pixlar på karaktären
     * ritas ut. Om en float kunde skickas in och t.ex. 2.5f skickas in kommer inte bilden att se symmetrisk
     * ut, därför får bilden endast skaleras med en int.
     * @param scale Hur mycket bilden skaleras.
     */
    public void changeScale(float scale){
        xScale *= scale;
        yScale *= scale;
    }

    /**
     * Rita ut animationen.
     * @param batch Batchen som animationen ska ritas ut på.
     * @param x X-koordinaten för animationen.
     * @param y Y-koordinaten för animationen.
     * @param time Tiden som har passerat sen animationen började.
     */
    public void draw(Batch batch, float x, float y, float time){
        TextureRegion region = animation.getKeyFrame(time, true);
        batch.draw(region, x, y, region.getRegionWidth()*xScale, region.getRegionHeight()*yScale);
    }

    /**
     * Tar bort de objekt som javas GC inte tar hand om från minnet.
     */
    public void dispose(){
        atlas.dispose();
    }
}