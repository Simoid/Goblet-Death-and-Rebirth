package com.goblet.level;

/**
 * Created by Johan on 2016-05-24.
 */
public enum SpawnPoint {
    KING, DATBOI, BAT, MASK, SPIDER, PRISM, EYE;

    /**
     * Översätter en string till motsvarande fiendetyp.
     * Denna metod använder sig av en lång if-else sats i stället för switch, då jag fick error när jag
     * försökte använda en switch på String, trots att det står i Javas API att det funkar för java 1.7 och senare.
     * (Projektet använder Java 1.8).
     * @param s Inputsträngen.
     * @return Inputsträngen översatt till en SpawnPoint.
     */
    public static SpawnPoint translate(String s){
        if (s.toLowerCase().equals("k")) {return KING;}
        else if (s.toLowerCase().equals("d")){return DATBOI;}
        else if (s.toLowerCase().equals("b")){return BAT;}
        else if (s.toLowerCase().equals("m")){return MASK;}
        else if (s.toLowerCase().equals("s")){return SPIDER;}
        else if (s.toLowerCase().equals("p")){return PRISM;}
        else if (s.toLowerCase().equals("e")){return EYE;}
        else return null;
    }
}

