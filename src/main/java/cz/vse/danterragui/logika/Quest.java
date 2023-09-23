package cz.vse.danterragui.logika;
/**
 * Class Quest - Holds all the information about quests in the game
 * @author Daniel Brus
 * @version 0.9, May 2023
 */

public class Quest {
    private String questDescription;
    private Thing requestedItem;
    private Thing reward;
    private boolean completed=false;

    public Quest(String questDescription, Thing requestedItem, Thing reward) {
        this.questDescription = questDescription;
        this.requestedItem = requestedItem;
        this.reward = reward;
    }

    /**
     * Getter for requested item to complete the quest
     * @return Thing requested item
     */

    public Thing getRequestedItem() {
        return requestedItem;
    }

    /**
     * Getter for Thing reward after completing the quest
     * @return
     */

    public Thing getReward() {
        return reward;
    }

    /**
     * Sets quest status to "completed"
     * @param completed
     */

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Method ended up not being used - might erase it
     * @param playersItem
     * @return
     */
    public Thing completeQuest(Thing playersItem) {
        if (playersItem.equals(requestedItem)){
            setCompleted(true);
            System.out.println("You've successfully completed quest");
            return reward;
        }
        System.out.println("That's not the right item");
        return null;
    }

}

