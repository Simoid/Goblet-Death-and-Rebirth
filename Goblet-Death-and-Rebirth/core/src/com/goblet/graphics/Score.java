package com.goblet.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.goblet.level.Position;

/**
 * Created by Johan on 2016-05-28.
 */
public class Score {

    private Texture scoreBackground ;
    private Texture[] numbers = new Texture[10];
    private Position position;
    private int score;
    private int maxScore = 9999;

    private String numberToString(int n){
        switch (n){
            default:
            case 0:
                return "zero";
            case 1:
                return "one";
            case 2:
                return "two";
            case 3:
                return "three";
            case 4:
                return "four";
            case 5:
                return "five";
            case 6:
                return "six";
            case 7:
                return "seven";
            case 8:
                return "eight";
            case 9:
                return "nine";
        }
    }

    public Score(Position position){
        scoreBackground = new Texture(Gdx.files.internal("assets/sprites/ui/score.png"));
        this.position = position;
        for (int i = 0; i < 10; i++){
            numbers[i] = new Texture(Gdx.files.internal("assets/sprites/ui/numbers/" + numberToString(i) + ".png"));
        }
    }

    public void increaseScore(int scoreIncrease){
        score += scoreIncrease;
        if (score > maxScore) {
            score = maxScore;
        }
    }

    public void draw(Batch batch){
        batch.draw(scoreBackground, position.getX(), position.getY());
        batch.draw(numbers[(score / 1000)%10], position.getX() + 55, position.getY() + 1);
        batch.draw(numbers[(score / 100)%10], position.getX() + 65, position.getY() + 1);
        batch.draw(numbers[(score / 10)%10], position.getX() + 75, position.getY() + 1);
        batch.draw(numbers[score%10], position.getX() + 85, position.getY() + 1);
    }

}
