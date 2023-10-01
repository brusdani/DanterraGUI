package cz.vse.danterragui.logika;

import cz.vse.danterragui.main.Pozorovatel;
import cz.vse.danterragui.main.PredmetPozorovani;
import cz.vse.danterragui.main.ZmenaHry;

import java.util.*;

/**
 *  Třída Hra - třída představující logiku adventury.
 *
 *  Toto je hlavní třída  logiky aplikace.  Tato třída vytváří instanci třídy HerniPlan, která inicializuje mistnosti hry
 *  a vytváří seznam platných příkazů a instance tříd provádějící jednotlivé příkazy.
 *  Vypisuje uvítací a ukončovací text hry.
 *  Také vyhodnocuje jednotlivé příkazy zadané uživatelem.
 *
 *@author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 *@version    pro školní rok 2016/2017
 */

public class Hra implements IHra {
    private SeznamPrikazu platnePrikazy;    // obsahuje seznam přípustných příkazů
    private HerniPlan herniPlan;
    private Inventory inventory;

    private Map<ZmenaHry, Set<Pozorovatel>> seznamPozorovatelu = new HashMap<>();
    private boolean konecHry = false;
    private String username = "adventurer"; //getUsername();
    private  Aiba aiba;
    private Npc npc;
    private Quest quest;
    private Riddle riddle;
    private Ending ending;

    /**
     *  Vytváří hru a inicializuje místnosti (prostřednictvím třídy HerniPlan) a seznam platných příkazů.
     */
    public Hra() {
        herniPlan = new HerniPlan();
        inventory = new Inventory();
        aiba = new Aiba(false);
        ending = new Ending(inventory);
        platnePrikazy = new SeznamPrikazu();
        platnePrikazy.vlozPrikaz(new PrikazNapoveda(platnePrikazy));
        platnePrikazy.vlozPrikaz(new PrikazJdi(herniPlan, aiba));
        platnePrikazy.vlozPrikaz(new PrikazKonec(this));
        platnePrikazy.vlozPrikaz(new PrikazPickup(herniPlan, inventory));
        platnePrikazy.vlozPrikaz(new PrikazWhereami(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazCallAiba(aiba));
        platnePrikazy.vlozPrikaz(new PrikazDrop(inventory, herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazAibaScan(aiba, herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazTalkTo(herniPlan, npc, this));
        platnePrikazy.vlozPrikaz(new PrikazGiveItem(herniPlan, inventory));
        platnePrikazy.vlozPrikaz(new PrikazUnlock(herniPlan, inventory));
        platnePrikazy.vlozPrikaz(new PrikazUse(inventory));
        platnePrikazy.vlozPrikaz(new PrikazAnswer(herniPlan,inventory));
        platnePrikazy.vlozPrikaz(new PrikazShowRiddle(herniPlan, riddle));
        platnePrikazy.vlozPrikaz(new PrikazSail(inventory,herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazFireIV(inventory,herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazShowInventory(inventory));
        for(ZmenaHry zmenaHry: ZmenaHry.values()){
            seznamPozorovatelu.put(zmenaHry, new HashSet<>());
        }
    }

    /**
     * Gets name from user
     * @return Returning String entered by user
     */

    public String getUsername() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter character's name: ");
        System.out.print("> ");
        return scan.nextLine();
  }
    /**
     *  Vrátí úvodní zprávu pro hráče.
     */
    public String vratUvitani() {
        return  "Welcome to the world of Danterra " + username + "!\n" +
                "Napište 'nápověda', pokud si nevíte rady, jak hrát dál.\n";
        //herniPlan.getAktualniProstor().dlouhyPopis();
    }

    /**
     * Short poem at the beginning of the game
     * @return poem
     *
     */
    public String poem(){
        return  "In the world full of darkness and fears \n" +
                "The light has lain dormant for many years \n" +
                "From cries and pleas of a great scope \n"+
                "Souls lit up the sparks of hope \n";
    }

    /**
     * Introduction to the game
     * @return Returns intro text
     */
    public String intro(){
        return "You can hear a very loud voice. It seems to be talking specifically to you to you \n" +
                username + " Come on, wake up. We need to save this world and we can't do that from here! \n" +
                "Its name is Aiba.. but that's all you can remember. Your vision is still blurry \n" +
                "Use \"callAiba\" to call it ";
    }

    /**
     * Plays out ending when player completes the game
     * @return Returns text from Ending class
     */
    public String ending(){
        return ending.ending();
    }

    /**
     * Returns the ending player has achieved - more text after shared part of the ending is over
     * @return Returns text from ending player has earned
     */
    public String playerEnding(){
        return ending.playerEnding();
    }

    /**
     *  Vrátí závěrečnou zprávu pro hráče.
     */
    public String vratEpilog() {

        return "Thank you for playing the game";
    }

    /**
     * Vrací true, pokud hra skončila.
     */
    public boolean konecHry() {
        return konecHry;
    }

    /**
     *  Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo příkazu a další parametry.
     *  Pak otestuje zda příkaz je klíčovým slovem  např. jdi.
     *  Pokud ano spustí samotné provádění příkazu.
     *
     *@param  radek  text, který zadal uživatel jako příkaz do hry.
     *@return          vrací se řetězec, který se má vypsat na obrazovku
     */
    public String zpracujPrikaz(String radek) {
        String [] slova = radek.split("[ \t]+");
        String slovoPrikazu = slova[0];
        String []parametry = new String[slova.length-1];
        for(int i=0 ;i<parametry.length;i++){
            parametry[i]= slova[i+1];
        }
        String textKVypsani=" .... ";
        if (platnePrikazy.jePlatnyPrikaz(slovoPrikazu)) {
            IPrikaz prikaz = platnePrikazy.vratPrikaz(slovoPrikazu);
            textKVypsani = prikaz.provedPrikaz(parametry);
        }
        else {
            textKVypsani="Nevím co tím myslíš? Tento příkaz neznám. ";
        }
        return textKVypsani;
    }

    public String pleaseCallAiba(){
        return "Please call Aiba to continue the game";
    }


    /**
     *  Nastaví, že je konec hry, metodu využívá třída PrikazKonec,
     *  mohou ji použít i další implementace rozhraní Prikaz.
     *
     *  @param  konecHry  hodnota false= konec hry, true = hra pokračuje
     */
    void setKonecHry(boolean konecHry) {
        this.konecHry = konecHry;
        upozorniPozorovatele(ZmenaHry.KONEC_HRY);
    }

    /**
     *  Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
     *  kde se jejím prostřednictvím získává aktualní místnost hry.
     *
     *  @return     odkaz na herní plán
     */
    public HerniPlan getHerniPlan(){
        return herniPlan;
    }

    /**
     * Getter for Aiba
     * @return returns Aiba
     */
    public Aiba getAiba() {
        return aiba;
    }

    @Override
    public void registruj(ZmenaHry zmenaHry, Pozorovatel pozorovatel) {
        seznamPozorovatelu.get(zmenaHry).add(pozorovatel);
    }
    private void upozorniPozorovatele(ZmenaHry zmenaHry){
        for (Pozorovatel pozorovatel : seznamPozorovatelu.get(zmenaHry)) {
            pozorovatel.aktualizuj();
        }
    }

    public Inventory getInventory() {
        return inventory;
    }
}


