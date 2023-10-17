package cz.vse.danterragui.logika;

/**
 * Class PrikazFireIV - Implements fireIV command for the game
 * Player gets to cast fireball to break the seal
 * Only once per playthrough
 * @author Daniel Brus
 * @version 0.9, May 2023
 */


public class PrikazFireIV implements  IPrikaz{
    public static final String NAZEV = "fireIV";

    private final HerniPlan herniPlan;
    private Inventory inventory;
    private Thing chakram;
    public static final String FIREIV_TOOL = "magicRod_u";

    /**
     * Constructor
     * @param inventory Certain item is required to execute command fireIV
     * @param herniPlan gameplan - has all the rooms and its contents
     */

    public PrikazFireIV(Inventory inventory, HerniPlan herniPlan){
        this.herniPlan=herniPlan;
        this.inventory=inventory;
    }
    /**
     * Executes command FireIV
     * Checks if input is valid.
     * Checks if the player has magic_rod in the inventory and if currect location is "monaxia"
     * Chakrams become collectable, the rod is destroyed and transition text is shown. (pretty much text cutscene)
     * @param parametry number of parameters vary based on command.
     * @return Message for the player
     */
    public String provedPrikaz (String...parametry) {
        if (parametry.length != 0) {
            return "Explosions don't need parameters";
        }
        Prostor aktualniProstor = herniPlan.getAktualniProstor();
        if (!inventory.containsItem(FIREIV_TOOL))
            return "you need a rod to cast the mighty spell";
        if (!aktualniProstor.getNazev().equals("monaxia")) {
            return "I don't want to hurt anyone. Only safe place to use the rod is in monaxia";
        }
        Thing chakrams = herniPlan.getChakram();
        inventory.removeItem(FIREIV_TOOL);
        chakrams.setCollectable(true);
        return transitionText();
    }
    public String transitionText() {
        return "You take a deep breath. Inhale and exhale. You can feel strange energy pulsating through your body. \"Come on, you can do it\", Aiba says \n" +
                "You close your eyes and focus all your energy into the magical rod you're holding. \n" +
                "The air is getting warmer. You've done it. You summoned a fireball of about the same size as a golf ball. \n" +
                "However, it was still enough to break the magic seal.... and the rod too.";
                }

    /**
     * @return Returns name of the command
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

}
