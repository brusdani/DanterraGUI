package cz.vse.danterragui.logika;

/**
 * Class PrikazSail - Implements sail command for the game
 * Sails player to Mare Lamentorum. Previous locations become inaccessible
 * Only once per playthrough
 * @author Daniel Brus
 * @version 0.9, May 2023
 */

public class PrikazSail implements IPrikaz {
    private static final String NAZEV = "sail";

    private final HerniPlan herniPlan;
    private Inventory inventory;
    private Prostor nextLocation;

    /**
     * Constructor
     * @param inventory Certain item is required to execute command Sail
     * @param herniPlan gameplan - has all the rooms and its contents
     */

    public PrikazSail(Inventory inventory, HerniPlan herniPlan){
        this.inventory=inventory;
        this.herniPlan=herniPlan;
    }

    /**
     * Executes command Sail
     * Checks if input is valid.
     * Checks if the player has "ticket" in the inventory and if currect location is "pub"
     * Current location is set to Mare Lamentorum, ticket's removed from the inventory, player obtains moonstone
     * and transition text is shown. (pretty much text cutscene)
     * @param parametry number of parameters vary based on command.
     * @return Message for the player
     */

    public String provedPrikaz (String...parametry){
        if (parametry.length != 0) {
            return "sail doesn't have parameters";
        }
        Prostor aktualniProstor = herniPlan.getAktualniProstor();
        //System.out.println(aktualniProstor);
        if (!inventory.containsItem("ticket"))
            return "you need ticket to be able to sail";
        if (!aktualniProstor.getNazev().equals("pub")){
            return "You can only sail from pub";
        }
        Prostor destination = herniPlan.getTeleport();
        //System.out.println(destination);
        inventory.removeItem("ticket");
        Thing moonstone = new Thing("moonstone", true,false,true);
        herniPlan.setAktualniProstor(destination);
        inventory.addThing(moonstone);
        return transitionText();

    }
    public String transitionText() {
        return "You started boarding the ship. You can see Ash running towards you. \"If you're going to the other side\" \n" +
                "Look for my sister Kira. She crossed the sea months ago but never came back. This moonstone might help you \n" +
                "find her. You boarded the ship. You're heading towards the infamous city Mare Lamentorum. People are known\n" +
                "to never return from there. But if you want to banish the darkness, you need to go there.\n"+
                "You succefully docked in the city's port. \"The ship isn't in state for another journey, says the captain\"\n"+
                "There is no way back now";
    }
    /**
     * @return Returns name of the command
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}

