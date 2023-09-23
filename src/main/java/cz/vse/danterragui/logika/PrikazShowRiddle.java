package cz.vse.danterragui.logika;

/**
 * Class PrikazShowRiddle - Implements showRiddle command for the game
 * Shows the riddle (text) in the room if there is any
 * @author Daniel Brus
 * @version 0.9, May 2023
 */

public class PrikazShowRiddle implements IPrikaz {
    private static final String NAZEV = "showRiddle";

    private final HerniPlan herniPlan;
    private Riddle riddle;

    /**
     * Constructor
     * @param riddle Shows if there's riddle in the room
     * @param herniPlan gameplan - has all the rooms and its contents
     */

    public PrikazShowRiddle(HerniPlan herniPlan, Riddle riddle){
        this.herniPlan=herniPlan;
        this.riddle=riddle;
    }
    /**
     * Executes command showRiddle
     * Checks if input is valid
     * Checks if there's riddle in the room. If yes, returns riddle, if not returns
     * message that there's no riddle.
     * @param parametry number of parameters vary based on command.
     * @return Message for the player
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length!=0) {
            return "Zadej prikaz bez parametru";
        }
        Prostor location = herniPlan.getAktualniProstor();
        if (location.isHasRiddle()) {
            Riddle riddle = herniPlan.getAktualniProstor().getRiddle();
            return riddle.getRiddle();
        }
        return "There isn't any riddle in the room";
    }
    /**
     * @return Returns name of the command
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}

