package cz.vse.danterragui.logika;

/**
 * Class PrikazGiveItem - Implements giveItem command for the game
 * Gives item to npc to complete their quests.
 * Reward is handed over to the player
 * @author Daniel Brus
 * @version 0.9, May 2023
 */

public class PrikazGiveItem implements IPrikaz {

    private static final String NAZEV = "giveItem";

    private final HerniPlan herniPlan;
    private Inventory inventory;

    /**
     * Constructor
     * @param inventory Player must have desired item in the inventory, also receives reward
     * @param herniPlan gameplan - has all the rooms and its contents
     */

    public PrikazGiveItem(HerniPlan herniPlan, Inventory inventory) {
        this.herniPlan = herniPlan;
        this.inventory = inventory;
    }

    /**
     * Executes command giveItem
     * Checks if input is valid and item/npc in correct order.
     * Checks if the players has the item they want to give to npc. Checks if npc exists in the room.
     * Checks if specified npc even has a quest and if the item given to them is their desired.
     * Removes the desired item and receives reward from npc (exchange). Npc quest is set to "completed".
     * Displays current inventory with the reward included
     * @param parametry number of parameters vary based on command.
     * @return Message for the player
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Which item am I supposed to give away";
        } else if (parametry.length != 2){
            return "Please specify both ITEM and CHARACTER in that order";
        };
        String itemName = parametry[0];
        String npcName = parametry[1];


        Prostor aktualniProstor = herniPlan.getAktualniProstor();
        Npc npc = aktualniProstor.returnNpc(npcName);
        Thing item = inventory.returnThing(itemName);

        if (!inventory.containsItem(itemName)){
            return "I don't have this item in my backpack";
        }
        else if (npc == null) {
            return "No one with this name is here";
        }
        else if (npc.getNpcQuest()==null){
            return "This character doesn't have quest";
        }
        Quest quest = npc.getNpcQuest();
        if (!item.equals(quest.getRequestedItem())){
            return "That's not the correct item";
        }
        Thing reward = quest.getReward();
        inventory.removeItem(itemName);
        npc.setDialogue("Thank you for your help");
        quest.setCompleted(true);
        npc.setNpcQuest(null);
        inventory.addThing(reward);
        inventory.displayItems();
        return "You received reward " + reward.getName();


    }
    /**
     * @return Returns name of the command
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}

