package com.goblet.entities;

import java.util.HashMap;

/**
 * En klass som hanterar en entitites rörelser.
 *
 * Klassen använder sig av tre olika Hashmaps:
 * En hashmap som håller booleans i riktningarna ned, upp, vänster och höger som beskriver i fall
 * karaktären rör sig ditåt.
 * En hashmap som innehåller hastigheten som håller hastigheterna i x-led för de olika hållen
 * (ned och upp har hastigheten 0 här).
 * En hashmap som innehåller hastigheten som håller hastigheterna i y-led för de olika hållen
 * (vänster och höger har hastigheten 0 här).
 *
 * Klassen hanterar sedan all matematisk beräkning och returnerar bara ett värde för rörelse
 * i x-led och ett för rörelse i y-led som kan användas av objekt som har ett movementobjekt
 * för att lätt flytta sig i x- och y-led.
 *
 * Created by johan on 5/16/16.
 */
public class Movement {

    private HashMap<Direction, Boolean> movementBools = new HashMap<Direction, Boolean>();
    private HashMap<Direction, Float> xMovement = new HashMap<Direction, Float>();
    private HashMap<Direction, Float> yMovement = new HashMap<Direction, Float>();
    private float currentMovementX = 0f;
    private float currentMovementY = 0f;

    /**
     * Konstruktorn för klassen, moveSpeed sätts från början men kan ändras senare vid behov.
     * @param moveSpeed Rörelsehastigheten för objektet som har Movement-objektet.
     */
    public Movement(float moveSpeed){

        movementBools.put(Direction.DOWN, false);
        movementBools.put(Direction.UP, false);
        movementBools.put(Direction.LEFT, false);
        movementBools.put(Direction.RIGHT, false);

        xMovement.put(Direction.DOWN, 0f);
        xMovement.put(Direction.UP, 0f);
        xMovement.put(Direction.LEFT, -moveSpeed);
        xMovement.put(Direction.RIGHT, moveSpeed);

        yMovement.put(Direction.DOWN, -moveSpeed);
        yMovement.put(Direction.UP, moveSpeed);
        yMovement.put(Direction.LEFT, 0f);
        yMovement.put(Direction.RIGHT, 0f);

    }

    /**
     * Ändra rörelsehastigheten för objeketet.
     * @param newSpeed Den nya rörelsehastigheten för objektet.
     */
    public void setMoveSpeed(float newSpeed){
        xMovement.put(Direction.LEFT, -newSpeed);
        xMovement.put(Direction.RIGHT, newSpeed);
        yMovement.put(Direction.DOWN, -newSpeed);
        yMovement.put(Direction.UP, newSpeed);
    }

    /**
     * Berättar i fall karaktären rör sig åt ett håll eller inte.
     * @param dir Hållet som frågas om.
     * @return true om karaktären rör sig åt 'dir', false annars.
     */
    public boolean getMoveFlag(Direction dir){
        return movementBools.get(dir);
    }

    /**
     * Returnerar hur långt karaktären ska gå i nästa update i y-axel (ska sedan multipliceras med deltatid).
     * @return Hur långt karaktären ska gå i y-axel.
     */
    public float getMovementY() {
        if (currentMovementY == 0){
            return 0;
        }
        double angle = Math.atan2(currentMovementY, currentMovementX);
        System.out.println(currentMovementY / Math.abs(currentMovementY));
        return (float) (currentMovementY / Math.abs(currentMovementY) * Math.sin(angle) * currentMovementY);
    }

    /**
     * Returnerar hur långt karaktären ska gå i nästa update i x-axel (ska sedan multipliceras med deltatid).
     * @return Hur långt karaktären ska gå i x-axel.
     */
    public float getMovementX(){
        if (currentMovementX == 0){
            return 0;
        }
        double angle = Math.atan2(currentMovementY, currentMovementX);
        return (float) ((currentMovementX / Math.abs(currentMovementX) * Math.cos(angle) * currentMovementX));
    }

    /**
     * Sätter flaggan för att karaktären rör sig åt ett visst håll (eller inte rör sig åt det hållet).
     * @param dir Hållet som flaggan ska sättas för.
     * @param flag true om karaktären rör sig, false annars.
     */
    public void setMoveFlag(Direction dir, boolean flag){
        if (flag && !movementBools.get(dir)) {
            movementBools.put(dir, flag);
            addMovementX(dir);
            addMovementY(dir);
        } else if (!flag && movementBools.get(dir)) {
            movementBools.put(dir, flag);
            subMovementX(dir);
            subMovementY(dir);
        }
    }

    /**
     * Öka hastigheten i x-axeln.
     * @param dir Hållet som hastigheten ska ökas till.
     */
    private void addMovementX(Direction dir){
        currentMovementX += xMovement.get(dir);
    }

    /**
     * Öka hastigheten i y-axeln.
     * @param dir Hållet som hastigheten ska ökas till.
     */
    private void addMovementY(Direction dir){
        currentMovementY += yMovement.get(dir);
    }

    /**
     * Minska hastigheten i x-axeln.
     * @param dir Hållet som hastigheten ska ökas till.
     */
    private void subMovementX(Direction dir){
        currentMovementX -= xMovement.get(dir);
    }

    /**
     * Minska hastigheten i y-axeln.
     * @param dir Hållet som hastigheten ska ökas till.
     */
    private void subMovementY(Direction dir){
        currentMovementY -= yMovement.get(dir);
    }

}
