package cz.vse.danterragui.logika;

/**
 * Class PrikazUse - Implements use command for the game
 * Allows player to use certain items see their special effect (usually hint to riddle)
 * @author Daniel Brus
 * @version 0.9, May 2023
 */

public class PrikazUse implements  IPrikaz{

    public static final String NAZEV = "use";

    private Inventory inventory;

    /**
     * Constructor
     * @param inventory To see if the item with special effect is in the inventory
     */

    public PrikazUse(Inventory inventory) {

        this.inventory = inventory;
    }

    /**
     * Executes command use
     * Checks if input is valid.
     * Checks if the item player wants to use is in the inventory and if the item is "useable".
     * Uses the item and shows special effect of it.
     * @param parametry number of parameters vary based on command.
     * @return Message for the player
     */

    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Don't know which item to use";
        } else if (parametry.length != 1){
            return "I can only use one item at a time.";
        }

        String itemName = parametry[0];
        if (!inventory.containsItem(itemName)) {
            return  "I can't use the item if I don't have it";
        }
        Thing item = inventory.returnThing(itemName);
        if (!item.isUseable()) {
            return "This item isn't usable";
        }
        return item.useItem();

    }
    /**
     * @return Returns name of the command
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

}

