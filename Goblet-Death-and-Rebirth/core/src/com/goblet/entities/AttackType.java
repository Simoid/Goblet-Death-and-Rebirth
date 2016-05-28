package com.goblet.entities;

/**
 * En enum som beskriver vilken typ av attack en fiende har.
 * Created by Johan on 2016-05-27.
 */
public enum AttackType {
    MELEEAREA, MOVEMENT;

    public static AttackType parse(String type){
        if (type.toLowerCase().equals("meleearea")){
            System.out.println("King CREATE");
            return MELEEAREA;
        } else {
            return MOVEMENT;
        }
    }
}
