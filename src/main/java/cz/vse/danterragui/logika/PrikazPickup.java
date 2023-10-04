package cz.vse.danterragui.logika;

/**
 * Class PrikazPickup - Implements pickup command for the game
 * Players pick up things inside the room and
 * put them in their inventory. Thing is removed from the room.
 * @author Daniel Brus
 * @version 0.9, May 2023
 */

public class PrikazPickup implements IPrikaz{

    public static final String NAZEV = "pickup";

    private final HerniPlan herniPlan;
    private Inventory inventory;

    /**
     * Constructor
     * @param inventory Item is stored in player's inventory
     * @param herniPlan gameplan - has all the rooms and its contents
     */

    public PrikazPickup(HerniPlan herniPlan, Inventory inventory) {
        this.herniPlan = herniPlan;
        this.inventory = inventory;
    }

    /**
     * Executes command Pickup
     * Checks if input is valid and if the thing exists in the room.
     * Checks if the thing is collectable and if the inventory is full.
     * Places thing in player's inventory and removes it from the room.
     * @param parametry number of parameters vary based on command.
     * @return Message for the player
     */

    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Don't know what to pick up.";
        } else if (parametry.length != 1){
            return "I can only pick up one thing at a time.";
        }

        String nameThing = parametry[0];
        Prostor aktualniProstor = herniPlan.getAktualniProstor();
        Thing thing = aktualniProstor.returnThing(nameThing);

        if (thing == null) {
            return  "Takova vec se v prostoru nenachazi";
        } else if (!thing.isCollectable()) {
            return "Tuto vec nelze prenest";
        } else if (inventory.isFull()) {
            return "Backpack is too heavy. I can't carry more items";
        }
        aktualniProstor.removeThing(nameThing);
        //TODO logika pro pridani inventare
        inventory.addThing(thing);
        inventory.displayItems();
        return "věc byla sebrána";
    }
    /**
     * @return Returns name of the command
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}

