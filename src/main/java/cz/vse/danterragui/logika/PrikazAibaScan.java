package cz.vse.danterragui.logika;

/**
 * Class PrikazAibaScan - Implements aibaScan command for the game
 * Players scan the room. After room was scanned it shows all its contents
 * (Things, riddles, NPCs) in it.
 * @author Daniel Brus
 * @version 0.9, May 2023
 */

public class PrikazAibaScan implements IPrikaz {
    public static final String NAZEV = "aibaScan";

    private Aiba aiba;
    private final HerniPlan herniPlan;

    /**
     * Constructor
     * @param aiba - Aiba has to be summoned to be able to perform the scan
     * @param herniPlan gameplan - has all the rooms and its contents
     */

    public PrikazAibaScan(Aiba aiba, HerniPlan herniPlan){
        this.aiba = aiba;
        this.herniPlan = herniPlan;
    }

    /**
     * Executes command AibaScan
     * Checks if input is valid. Checks if Aiba is summoned.
     * Setting "WasScanned" to true - making sure players don't need to scan the same room more than once
     * @param parametry number of parameters vary based on command
     * @return Message for the player
     */
    public String provedPrikaz (String...parametry){
        if (parametry.length != 0) {
            return "aibaScan doesn't have parameters";
        }
        if (!aiba.isSummoned()){
            return "Summon Aiba to perform the scan";
        }
        Prostor location = herniPlan.getAktualniProstor();
        location.setWasScanned(true);
        return  "Scanning the rooooom \n" +
                herniPlan.getAktualniProstor().aibaScan();
    }

    /**
     * @return Returns name of the command
     */

    public String getNazev () {
        return NAZEV;
    }
}

