package cz.vse.danterragui.logika;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Aiba - companion of the player,
 * is supposed to help the player complete the game
 * @author Daniel Brus
 * @version 0.9, May 2023
 */
public class Aiba {
    private boolean summoned;
    private List<IPrikaz> abilities;
    private String mood;
    private PrikazAibaScan prikazAibaScan;


    public String aibaHelp(){
        return "";
    }

    public String aibaIntroduction(){
        return "I'm Aiba and I'm here to help you fulfill your destiny \n" +
                "One of my abilities is \"aibaScan\", which will scan through the room and give you all necessary information\n"
                +"Let's find our way to Mare Lamentorum and free the light";
    }

    /**
     * Constructor
     * Creates Aiba - Aiba is defaultly not summoned
     * @param summoned
     */
    public Aiba(boolean summoned) {
        this.summoned = summoned;
    }

    /**
     * Checks if Aiba is currently summoned
     * @return True if Aiba is summoned, otherwise false
     */

    public boolean isSummoned() {
        return summoned;
    }

    /**
     * Summons Aiba (could be possibly used to send Aiba away in the future versions)
     * @param summoned
     */
    public void setSummoned(boolean summoned) {
        this.summoned = summoned;
    }

    /**
     *Just some ideas for the future
     */
    public void learnAbility() {
        abilities.add(prikazAibaScan);
    }

    public List<IPrikaz> getAbilities() {
        return abilities;
    }
}

