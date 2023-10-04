package cz.vse.danterragui.logika;
/**
 * Class PrikazTalkTo - Implements talkTo command for the game
 * talks to NPC - shows NPC dialogue
 * @author Daniel Brus
 * @version 0.9, May 2023
 */

public class PrikazTalkTo implements IPrikaz {

    public static final String NAZEV = "talkTo";

    private final HerniPlan herniPlan;
    private Npc npc;
    private Hra hra;

    /**
     * Constructor
     * @param hra Talking to Keeper ends the game
     * @param herniPlan gameplan - has all the rooms and its contents
     * @param npc Not needed at the moment, because "keeper" is hardcoded
     */

    public PrikazTalkTo(HerniPlan herniPlan, Npc npc, Hra hra) {
        this.herniPlan = herniPlan;
        this.npc = npc;
        this.hra=hra;
    }
    /**
     * Executes command talkTo
     * Checks if input is valid.
     * Checks if npc with that name exists in the room. If the npc is keeper, the game ends.
     * Checks if npc's class is MovingNPC - if so methong move()is executed.
     * Returns NPC's dialogue as a message for the player.
     * @param parametry number of parameters vary based on command.
     * @return Message for the player
     */

    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Am I supposed to talk to myself";
        } else if (parametry.length != 1){
            return "I can only hold one conversation at a time.";
        };
        String npcName = parametry[0];
        Prostor aktualniProstor = herniPlan.getAktualniProstor();
        Npc npc = aktualniProstor.returnNpc(npcName);

        if (npc == null) {
            return "Nikdo takový tu není";
        }
        else if(npc.getName().equals("keeper")){
            hra.setKonecHry(true);
            return "Victor shall write the tale and vanquished become its villain";
        }
        else if(npc.getClass().equals(MovingNPC.class)){
            ((MovingNPC) npc).move();
        }
        return npc.getDialogue();

    }
    /**
     * @return Returns name of the command
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}


