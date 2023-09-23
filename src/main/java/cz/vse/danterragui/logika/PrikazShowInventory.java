package cz.vse.danterragui.logika;

/**
 * Class PrikazShowInventory - Implements showInventory command for the game
 * Displays contents of the inventory to the player
 * @author Daniel Brus
 * @version 0.9, May 2023
 */


public class PrikazShowInventory implements  IPrikaz{
    private static final String NAZEV = "showInventory";
    private Inventory inventory;

    /**
     * Constructor
     * @param inventory To display the inventory... surprisingly inventory is needed
     */
    public PrikazShowInventory(Inventory inventory){
        this.inventory=inventory;
    }
    /**
     * Executes command showInventory
     * Checks if input is valid and shows the player contents of their inventory.
     * @param parametry number of parameters vary based on command.
     * @return Message for the player
     */

    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length != 0) {
            return "Zadej prikaz bez parametru";
        }
        return "Your inventory: " + inventory.displayItems();
    }
    /**
     * @return Returns name of the command
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
