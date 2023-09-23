package cz.vse.danterragui.logika;

/**
 * Class PrikazUnlock - Implements unlock command for the game
 * unlocks door that has been previously locked
 * @author Daniel Brus
 * @version 0.9, May 2023
 */

public class PrikazUnlock implements  IPrikaz {

    private static final String NAZEV = "unlock";
    private final HerniPlan herniPlan;
    private Inventory inventory;

    /**
     * Constructor
     * @param inventory To see if player has the required key
     * @param herniPlan gameplan - has all the rooms and its contents
     */


    public PrikazUnlock(HerniPlan herniPlan, Inventory inventory) {
        this.herniPlan = herniPlan;
        this.inventory = inventory;
    }
    /**
     * Executes command unlock
     * Checks if input is valid and if the room is next to current location (path between them)
     * Checks if the player has the required key. Unlocks the room and gets rid of the key as it's no longer needed.
     * If the door was already unlocked it returns a message saying exactly that.
     * @param parametry number of parameters vary based on command.
     * @return Message for the player
     */
    public String provedPrikaz(String... parametry) {
        if (parametry.length != 1) {
            // pokud chybí druhé slovo (sousední místnost), tak ....
            return "Co mám odemknout? Musíš zadat jméno místnosti";
        }

        String room = parametry[0];

        Prostor sousedniProstor = herniPlan.getAktualniProstor().vratSousedniProstor(room);

        if (sousedniProstor == null) {
            return "Odsud nevedou dveře do místnosti "+room+" !";
        }
        else {
            if (sousedniProstor.isLocked()) {
                if (inventory.cointainsRealItem(sousedniProstor.getKey())) {
                    sousedniProstor.setLock(false);
                    inventory.removeRealItem(sousedniProstor.getKey());
                    return "Path to "
                            + sousedniProstor + " is now clear";
                }
                else {
                    return "To unlock the path you need " + sousedniProstor.getKey();
                }
            }
            else {
                return "The path to " + sousedniProstor + " is already unlocked!";
            }
        }
    }
    /**
     * @return Returns name of the command
     */
    public String getNazev() {
        return NAZEV;
    }

}

