package cz.vse.danterragui.logika;

/**
 * Class PrikazWhereami - Implements whereami command for the game
 * Reminds the player of the current location
 * @author Daniel Brus
 * @version 0.9, May 2023
 */

public class PrikazWhereami implements IPrikaz {

    private static final String NAZEV = "whereami";
    private final HerniPlan herniPlan;

    /**
     * Constructor
     * @param herniPlan gameplan - has all the rooms and its contents
     */
    public PrikazWhereami(HerniPlan herniPlan) {
        this.herniPlan = herniPlan;
    }

    /**
     * Executes command whereami
     * Checks if input is valid and if aibaScan was used in the room.
     * Only shows AibaDescription and name if room wasn't scanned. But all the information if it was.
     * @param parametry number of parameters vary based on command.
     * @return Message for the player
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length!=0) {
            return "Zadej prikaz bez parametru";
        }
        Prostor location = herniPlan.getAktualniProstor();
        if (location.isWasScanned()){
            return location.getNazev() + " \n" +
                    location.aibaDescription() + " \n" +
                    location.aibaScan();
        }
        return  location.getNazev() + " \n" +
                location.aibaDescription();

    }
    /**
     * @return Returns name of the command
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}

