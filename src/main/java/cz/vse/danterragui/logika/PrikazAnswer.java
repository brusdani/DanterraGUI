package cz.vse.danterragui.logika;
/**
 * Class PrikazAnswer - Implements answer command for the game
 * Allows player to answer the riddle in the room
 * If the riddle was answered correctly player receives reward
 * @author Daniel Brus
 * @version 0.9, May 2023
 */

public class PrikazAnswer implements IPrikaz {

    private static final String NAZEV = "answer";

    private final HerniPlan herniPlan;
    private Riddle riddle;
    private Inventory inventory;
    private Riddle thievesRiddle;

    /**
     * Constructor
     * @param herniPlan gameplan - has all the rooms and its contents
     * @param inventory reward is put inside the inventory for the correct answer
     */

    public PrikazAnswer(HerniPlan herniPlan, Inventory inventory) {
        this.herniPlan = herniPlan;
        this.inventory = inventory;
    }

    /**
     * Executes command answer
     * Checks if input is valid. Checks if there is unsolved riddle in the location.
     * Checks if player's answer was correct. Checks if player has space in the inventory.
     * If the riddle was solved correctly and players has space. Sets room's riddle status to "False" and gives player a reward
     * @param parametry počet parametrů závisí na konkrétním příkazu.
     * @return Message for the player
     */

    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Please answer";
        }
        else if (parametry.length != 1){
            return "What am I thinking?";
        }
        String userAnswer = parametry[0];
        Prostor location = herniPlan.getAktualniProstor();
        riddle = location.getRiddle();
        if (!location.isHasRiddle()){
            return "There's no riddle in this location.";
        }
        if (!userAnswer.equals(riddle.getAnswer())){
            return "Wrong answer, doom awaits you. Just kidding, try answering again";
        }
        if (inventory.isFull()) {
            return "Inventory is full. Drop an item before completing the riddle";
        }
        thievesRiddle = herniPlan.getThievesRiddle();
        if (location.getRiddle().equals(thievesRiddle)){
            Npc roy = herniPlan.getRoy();
            Npc ravi = herniPlan.getRavi();
            ravi.setDialogue("We're sorry :(");
            roy.setDialogue("We're sorry :(");
        }
        inventory.removeRealItem(riddle.getHint());
        inventory.addThing(riddle.getReward());
        location.setRiddle(null);
        location.setHasRiddle(false);
        return "You solved the riddle and received " + riddle.getReward();

    }
    /**
     * @return Returns name of the command
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}

