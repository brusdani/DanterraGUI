package cz.vse.danterragui.logika;

    /**
     * Class PrikazDrop - Implements drop command for the game
     * Removes the item from player's inventory and places it to the room
     * @author Daniel Brus
     * @version 0.9, May 2023
     * */


public class PrikazDrop implements  IPrikaz{
    private static final String NAZEV = "drop";

    //public Inventory inventory = new Inventory();
    private final HerniPlan herniPlan;
    private Inventory inventory;

    /**
     * Constructor
     * @param inventory item is being removed from inventory after being dropped
     * @param herniPlan gameplan - has all the rooms and its contents
     */
    public PrikazDrop(Inventory inventory, HerniPlan herniPlan){
        this.inventory=inventory;
        this.herniPlan=herniPlan;
    }

    /**
     * Executes command drop
     * Checks if input is valid.
     * Checks if the item player wants to drop is in the inventory
     * If the item is key_item(required to complete the game) it's not possible to drop it
     * Removes the item from inventory and places it into the current room.
     * @param parametry number of parameters vary based on command.
     * @return Message for the player
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Don't know which item to drop.";
        } else if (parametry.length != 1){
            return "I can only drop one thing at a time.";
        }

        String nameThing = parametry[0];
        Prostor aktualniProstor = herniPlan.getAktualniProstor();
        Thing item = inventory.returnThing(nameThing);
        if (!inventory.containsItem(nameThing)) {
            return "I don't have this item in the inventory";
        } if (item.isKeyItem()){
            return "This item might be important, I should keep it";
        }


        inventory.displayItems();
        inventory.removeItem(nameThing);
        inventory.displayItems();
        aktualniProstor.addThing(item);
        return "You throw away "+ nameThing;
    }

    /**
     * @return Returns name of the command
     */

    @Override
    public String getNazev() {
        return NAZEV;
    }
}

