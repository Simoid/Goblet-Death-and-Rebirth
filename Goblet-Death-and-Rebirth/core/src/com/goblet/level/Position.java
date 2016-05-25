package com.goblet.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * En klass som håller en position med floats.
 * Created by johan on 5/21/16.
 */
public class Position {
    private float xPos;
    private float yPos;
    private Texture texture;

    /**
     * Konstruktor för klassen med x- och y-koordinaten.
     * @param xPos X-koordinaten.
     * @param yPos Y-koordinaten.
     */
    public Position(float xPos, float yPos){
        this.xPos = xPos;
        this.yPos = yPos;
        texture = new Texture(Gdx.files.internal("assets/sprites/hitbox/position.png"));
    }

    /**
     * Konstruktor för klassen som kopierar ett annat Position-objekt.
     * @param position Objektet som ska kopieras.
     */
    public Position(Position position){
        xPos = position.getX();
        yPos = position.getY();
        texture = new Texture(Gdx.files.internal("assets/sprites/hitbox/position.png"));
    }

    /**
     * Sätt en ny position med x- och y-koordinaten.
     * @param xPos X-koordinaten.
     * @param yPos Y-koordinaten.
     */
    public void setPosition(float xPos, float yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * Sätt en ny position med ett annat Position-objekt.
     * @param position Positionen vilkens värden ska kopieras.
     */
    public void setPosition(Position position){
        xPos = position.getX();
        yPos = position.getY();
    }

    /**
     * Returnerar x-koordinaten.
     * @return X-koordinaten.
     */
    public float getX(){
        return xPos;
    }

    /**
     * Returnerar y-koordinaten.
     * @return Y-koordinaten.
     */
    public float getY(){
        return yPos;
    }

    /**
     * Räknar ut sträckan mellan två punkter i planet med pythagoras sats.
     * @param position Den andra positionen.
     * @return Sträckan mellan denna position och inputargumentet.
     */
    public float distance(Position position){
        return (float) Math.sqrt(Math.pow(xPos - position.getX(), 2) + Math.pow(yPos - position.getY(), 2));
    }

    public float xDistance(float x){
        return (float) Math.abs(xPos - x);
    }

    public float yDistance(float y){
        return (float) Math.abs(yPos - y);
    }

    public void draw(Batch batch){
        batch.draw(texture, xPos, yPos, 1.0f, 1.0f);
    }

}
