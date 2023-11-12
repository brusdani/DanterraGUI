package cz.vse.danterragui.logika;

import java.util.concurrent.TimeUnit;

/**
 * Class Ending - Covers all the text for player ending. To make it easier to read and avoid clutter in "hra" class.
 * @author Daniel Brus
 * @version 0.9, May 2023
 */

public class Ending {
    private Inventory inventory;

    /**
     * Constructor
     * @param inventory - Player needs to have certain items in order to get good end
     */

    public Ending(Inventory inventory) {
        this.inventory = inventory;
    }

    private String badEnd = "You can hear a deep voice. Babel can not be without its keeper.\n"
            +"When one is defeated a new keeper has to take place.\n"
            + "Darkness is slowly overpowering you. The last thing you remember is hearing Aiba scream.\n";
    private String goodEnd = "You can hear a deep voice. Babel can not be without its keeper.\n"
            +"When one is defeated a new keeper has to take its place\n"
            +"You can hear Aiba screaming. The moonstone and sunstone united in Aiba's hands.\n"
            +"You've never seen anything this bright. The light has returned, you think to yourself.\n"
            +"But why is it shaped exactly like Aiba. You checked again... Aiba is glowing\n"
            +"With the newly acquired power, Aiba has banished the darkness \n"
            +"and sunrise has returned to the lands of Danterra once again.\n";

    public String ending() {
        return "A fearsome battle has begun. You and the Keeper were exchanging blows, one after another \n"
                +"With Aiba's and Kira's help you managed to defeat the Keeper\n"
                +"Is this really it?, you're asking yourself. Have we won?\n"
                +"Suddenly an unknown force started dragging you towards the throne\n";
    }

    /**
     * Decides if player earned bad or good end
     * @return Returns ending that the player has earned.
     */
    public String playerEnding(){
        if (inventory.containsItem("moonstone") && inventory.containsItem("sunstone")) {
            return goodEnd;
        }
        return badEnd;
    }

    /**
     * Getters for good and bad end for testing purposes
     * @return
     */

    public String getBadEnd() {
        return badEnd;
    }

    public String getGoodEnd() {
        return goodEnd;
    }
}

