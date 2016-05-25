package com.goblet.level;

/**
 * Created by johan on 5/21/16.
 */
public enum TileType {
    FLOOR, STONE, HOLE;
    public static TileType translate(String s){
        if (s.toLowerCase().equals("0")){return FLOOR;}
        else if (s.toLowerCase().equals("x")){return STONE;}
        else if (s.toLowerCase().equals("h")){return HOLE;}
        else return FLOOR;
    }

}
