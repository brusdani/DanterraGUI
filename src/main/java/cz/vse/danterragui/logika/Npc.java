package cz.vse.danterragui.logika;

/**
 * Class NPC - NPCs players can talk to, do their quest and after completing quest receiving rewards from them
 * Parent class of MovingNPC class
 * @author Daniel Brus
 * @version 0.9, May 2023
 */

public class Npc {
    private String name;
    private String description;
    private String dialogue;
    private Quest npcQuest;
    private String dialogueAfter;

    /**
     * Constructor
     * @param name name
     * @param description description
     * @param dialogue npc dialogue
     * @param npcQuest quest from the npc
     */

    public Npc(String name, String description, String dialogue, Quest npcQuest) {
        this.name = name;
        this.description = description;
        this.dialogue = dialogue;
        this.npcQuest = npcQuest;
    }

    /**
     * Getter for NPC name
     * @return Returns NPC name
     */

    public String getName() {
        return name;
    }

    /**
     * Getter for description, as of version 0.9 not used yet
     * @return Returns description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for dialogue
     * @return Returns specific npc dialogue
     */
    public String getDialogue() {
        return dialogue;
    }

    /**
     * Getter for quest
     * @return Returns npc quest
     */

    public Quest getNpcQuest() {
        return npcQuest;
    }

    /**
     * Setter for npc dialogue - allows dialogue to be changed after quest is completed
     * @param dialogue String with a dialogue
     * Returns new dialogue for npc
     */


    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }

    /**
     * Setter for npc quest - allows quest to be changed after quest is completed
     * @param npcQuest NPC's quest
     * Returns new Quest for npc
     */

    public void setNpcQuest(Quest npcQuest) {
        this.npcQuest = npcQuest;
    }
}

