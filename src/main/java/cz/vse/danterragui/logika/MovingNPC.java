package cz.vse.danterragui.logika;

import java.util.Random;

/**
 * Class MovingNPC - The latest addition allows NPCs that can move around the map
 * Child class of NPC class
 * @author Daniel Brus
 * @version 0.9, May 2023
 */

public class MovingNPC extends Npc {

    private final HerniPlan herniPlan;
    private Prostor currentProstor;
    private String name;

    /**
     * Constructor
     * @param name name
     * @param description description
     * @param dialogue npc dialogue
     * @param npcQuest npc quest
     * @param herniPlan game plan
     * @param currentProstor current location
     */
    public MovingNPC(String name, String description, String dialogue, Quest npcQuest, HerniPlan herniPlan, Prostor currentProstor) {
        super(name, description, dialogue, npcQuest);
        this.herniPlan=herniPlan;
        this.currentProstor = currentProstor;
    }

    /**
     * Makes npc move to a randomly selected location making sure it doesn't clone them
     * althought ocassionally it can happen that randomly selected location is the current one
     */
    public void move(){
        //System.out.println(herniPlan.getProstorMap());
        Random generator = new Random();
        Object[] values = herniPlan.getProstorMap().values().toArray();
        Prostor randomProstor = (Prostor) values[generator.nextInt(values.length)];
        herniPlan.getAktualniProstor().removeNpc(super.getName());
        herniPlan.setNpcProstor(randomProstor);
        //System.out.println(randomProstor);
        herniPlan.getNpcProstor().addNpc(this);

    }
}
