package cz.vse.danterragui.logika;

/**
 * Class PrikazCallAiba - Implements callAiba command for the game
 * Summons companion
 * Aiba can now use abilities
 * @author Daniel Brus
 * @version 0.9, May 2023
 */


public class PrikazCallAiba implements IPrikaz {

    private static final String NAZEV = "callAiba";
    private Aiba aiba;

    /**
     * Constructor
     * @param aiba - Companion
     */
    public PrikazCallAiba(Aiba aiba) {
        this.aiba = aiba;
    }

    /**
     * Executes command callAiba
     * Sets status of summoned to true. Aiba will be able to execute AibaScan
     * @param parametry number of parameters vary based on command
     * @return Message for the player
     */

    public String provedPrikaz (String...parametry){
        if (parametry.length != 0) {
            return "callAiba doesn't have parameters";
        }
        if(aiba.isSummoned()){
            return "Aiba has been already summoned";
        }
        aiba.setSummoned(true);
        return "Aiba has been summoned \n"+ aiba.aibaIntroduction();
    }

    /**
     * @return Returns name of the command
     */
    public String getNazev () {
        return NAZEV;
    }
}

