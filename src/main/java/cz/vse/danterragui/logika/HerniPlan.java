package cz.vse.danterragui.logika;

import cz.vse.danterragui.main.Pozorovatel;
import cz.vse.danterragui.main.PredmetPozorovani;
import cz.vse.danterragui.main.ZmenaHry;

import java.util.*;

/**
 *  Class HerniPlan - třída představující mapu a stav adventury.
 *
 *  Tato třída inicializuje prvky ze kterých se hra skládá:
 *  vytváří všechny prostory,
 *  propojuje je vzájemně pomocí východů
 *  a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *
 *@author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 *@version    pro školní rok 2016/2017
 */
public class HerniPlan implements PredmetPozorovani {

    private Prostor aktualniProstor;

    private Map<ZmenaHry, Set<Pozorovatel>> seznamPozorovatelu = new HashMap<>();
    private Prostor teleport;
    private Prostor winRoom;
    private Thing chakram;
    private Map<String, Prostor> prostorMap = new HashMap<>();
    private Prostor npcProstor;
    private Riddle thievesRiddle;
    private Npc thiefRoy;
    private Npc thiefRavi;
    private Thing magicRod;
    public static final String WINNING_OBJECT1 = "moonstone";
    public static final String WINNING_OBJECT2 = "sunstone";

    /**
     *  Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví halu.
     */
    public HerniPlan() {
        zalozProstoryHry();
        for(ZmenaHry zmenaHry : ZmenaHry.values()){
            seznamPozorovatelu.put(zmenaHry, new HashSet<>());
        }
    }
    /**
     *  Vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví domeček.
     */
    private void zalozProstoryHry() {
        //vytvareni jednotlivých vecí
        Thing lantern = new Thing("lantern", true, false, false);
        Thing backpack = new Thing("backpack", true, false, false);
        Thing sword = new Thing("sword", true, false, false);
        Thing altar = new Thing("altar", false, false, false);
        Thing bookcase = new Thing("bookcase",false, false, false);
        Thing treasureRoomKey = new Thing("treasureRoom_key", true, false, true);
        Thing note = new Thing("note_u", true, true, false);
        note.setSpecialEffect("Capten lykes his wyne red");
        Thing moonstone = new Thing("moonstone", true, false,true);
        Thing gateKey = new Thing("gate_key", true, false, true);
        Thing chain = new Thing("chain", true, false, false);
        Thing ring = new Thing("ring", true, false, true);
        Thing necklace = new Thing("necklace", true, false, true);
        Thing ticket = new Thing("ticket", true, false, true);
        ticket.setSpecialEffect("You can now use the \"sail\" command");
        Thing sunstone = new Thing("sunstone", true, false, true);
        Thing advice = new Thing("advice_u", true,true,false);
        advice.setSpecialEffect("One of them is always lying and the other always telling the truth. Aiba, over and out");
        Thing magicRod = new Thing("magicRod_u", true,true,true );
        magicRod.setSpecialEffect("So... I've heard you like explosions. Send some fireballs on that seal");
        Thing chakrams = new Thing("chakrams",false,false,true);
        Thing fountain = new Thing ("fountain", false, false, false);
        Thing throne = new Thing ("throne", false, false, false);
        Thing memberberries = new Thing("memberberries", true, false, false);
        Thing stick = new Thing("stick", true, false, false);
        Thing pillar = new Thing("pillar", false, false, false);


        Riddle lockedRoomRiddle = new Riddle("There are 3 buttons, green, red and blue. Which button should I press?","red", moonstone, note );
        Riddle treasureRoomRiddle = new Riddle("There are 3 buttons, green, red and blue. Which button should I press?","red", gateKey, note);
        Riddle cliffsRiddle = new Riddle("Which one of the two stole the necklace?", "ravi",necklace,advice);

        // vytvářejí se jednotlivé prostory
        Prostor startingRoom = new Prostor("Starting room","What are you staring at, there's nothing in this room, let's get going", false, null);
        Prostor lockedRoom = new Prostor("Locked_room", "Eeeeew, cold", true, treasureRoomKey);
        Prostor cellar = new Prostor("cellar","There's nothing here. Let's get going",false,null);
        Prostor hall = new Prostor("hall", "This place is huge, there's 2 ways we can take from here",false,null);
        Prostor tower = new Prostor("tower","Too many stairs. Fortunately, I can fly... unlike you.",false, null);
        Prostor treasureRoom = new Prostor("treasure_room", "Daaamn, someone looted it before us :( but what do these buttons do?",true, treasureRoomKey);
        treasureRoom.setRiddle(treasureRoomRiddle);
        treasureRoom.setHasRiddle(true);
        Prostor gate = new Prostor("gate", "Finally we're out of that creepy old castle... But it's not looking any better outside. Let's find our way to Mare Lamentorum",true, gateKey);
        Prostor village = new Prostor("village", "It seems empty. Nobody's outside. I wonder where people could be.", false, null);
        Prostor pub = new Prostor("pub", "Local pub. We should ask some of the customers here. They might be able to help us", false,null);
        Prostor forest = new Prostor("forest", "Deep dark forrest. You're definitely not getting any signal here", false, null);
        Prostor cliffs = new Prostor("cliffs", "I wonder how pretty this place could be if we managed to bring light back", false, null);
        cliffs.setRiddle(cliffsRiddle);
        cliffs.setHasRiddle(true);
        Prostor mareLamentorum = new Prostor("mare_lamentorum", "We made it to the city of the lost souls.", false, null);
        Prostor ruins = new Prostor("ruins","This city once used to be full of life", false,null);
        Prostor monaxia = new Prostor("monaxia","This place looks really scary. Be careful and lead the way.",false,null);
        //Prostor sewers = new Prostor ("sewers","bleh, let's find out what we need and get outta here, QUICK!", true, magicRod);
        Prostor babel = new Prostor("babel","If you have both moonstone and sunstone, you are ready to face Keeper. One way or another... it ends here", false,null);
        startingRoom.setRiddle(lockedRoomRiddle);
        startingRoom.setHasRiddle(true);
        Prostor keep = new Prostor("keep", "You won the game", false, null);

        prostorMap.put(hall.getNazev(), hall);
        prostorMap.put(tower.getNazev(), tower);
        prostorMap.put(treasureRoom.getNazev(), treasureRoom);
        prostorMap.put(gate.getNazev(), gate);
        prostorMap.put(village.getNazev(), village);
        prostorMap.put(pub.getNazev(), pub);
        prostorMap.put(forest.getNazev(), forest);
        prostorMap.put(mareLamentorum.getNazev(), mareLamentorum);


        Quest guardQuest = new Quest("Help him find the lantern", lantern, treasureRoomKey) ;
        Quest ashQuest = new Quest("Help him retrieve the item from the thieves",necklace, ring );
        Quest nemoQuest = new Quest("Find Nemo's ring", ring, ticket);
        Quest kiraQuest = new Quest("Get Kira out of here", chakrams ,sunstone);

        //creating npcs
        Npc guard = new Npc("guard", "Distressed guard", "Please help me find my lantern. I want to get out of here", guardQuest);
        Npc ash = new Npc("ash","Young boy crying for help", "Two thieves stole my necklace. It was a gift from my sister. Please help me get it back", ashQuest);
        Npc nemo = new Npc("nemo", "Captain of the Brigantine", "I lost a ring. If you'll find it and bring it back to me, I'll take you to Mare Lamentorum", nemoQuest);
        Npc monk = new Npc("monk", "An old monk", "Do not try to face keeper in the tower of Babel without both moonstone and sunstone",null);
        Npc ravi = new Npc("ravi", "ugly thief","We're both liars. Roy stole the necklace", null);
        Npc roy = new Npc("roy","ugly thief","I tell the truth. Ravi stole the necklace",null);
        Npc kira = new Npc("kira","Frightened woman", "Please help me escape. Break this magic seal. My chakrams are sealed behind it", kiraQuest);
        Npc keeper = new Npc("keeper", "Keeper of the light", "",null);
        MovingNPC ghost = new MovingNPC("ghost","who you're gonna call?", "Who you gonna call? *vanishes*",null, this, npcProstor);
        //přiřazujeme veci do prostoru

        startingRoom.addThing(lantern);
        startingRoom.addThing(sword);
        startingRoom.addThing(note);
        startingRoom.addThing(ring);
        startingRoom.addThing(ticket);
        startingRoom.addThing(moonstone);
        startingRoom.addThing(sunstone);
        cellar.addThing(chain);
        hall.addThing(lantern);
        hall.addThing(altar);
        hall.addThing(bookcase);
        treasureRoom.addThing(note);
        cliffs.addThing(advice);
        monaxia.addThing(magicRod);
        monaxia.addThing(chakrams);
        village.addThing(fountain);
        babel.addThing(throne);
        forest.addThing(memberberries);
        forest.addThing(stick);
        gate.addThing(pillar);


        //adding npcs into rooms
        startingRoom.addNpc(nemo);
        startingRoom.addNpc(keeper);
        startingRoom.addNpc(ghost);
        cellar.addNpc(ghost);
        tower.addNpc(guard);
        startingRoom.addNpc(monk);
        ruins.addNpc(monk);
        pub.addNpc(nemo);
        forest.addNpc(ash);
        cliffs.addNpc(roy);
        cliffs.addNpc(ravi);
        monaxia.addNpc(kira);
        babel.addNpc(keeper);
        cellar.addNpc(keeper);

        // přiřazují se průchody mezi prostory (sousedící prostory)
        startingRoom.setVychod(lockedRoom);
        cellar.setVychod(hall);
        hall.setVychod(cellar);
        hall.setVychod(tower);
        hall.setVychod(gate);
        tower.setVychod(hall);
        tower.setVychod(treasureRoom);
        treasureRoom.setVychod(tower);
        gate.setVychod(hall);
        gate.setVychod(village);
        gate.setVychod(forest);
        forest.setVychod(gate);
        forest.setVychod(cliffs);
        cliffs.setVychod(forest);
        village.setVychod(pub);
        village.setVychod(gate);
        pub.setVychod(village);
        mareLamentorum.setVychod(ruins);
        ruins.setVychod(mareLamentorum);
        ruins.setVychod(monaxia);
        ruins.setVychod(babel);
        monaxia.setVychod(ruins);

        startingRoom.setVychod(keep);

        aktualniProstor = cellar;  // game begins in cellar
        teleport = mareLamentorum;
        winRoom = babel;
        chakram = chakrams;
        thievesRiddle = cliffsRiddle;
        thiefRavi = ravi;
        thiefRoy = roy;
    }

    /**
     * Getter for thievesRiddle
     * @return riddle
     */

    public Riddle getThievesRiddle() {
        return thievesRiddle;
    }

    /**
     * Getter for roy NPC
     * @return npc roy
     */


    public Npc getRoy() {
        return thiefRoy;
    }

    /**
     * getter for Ravi npc
     * @return npc ravi
     */

    public Npc getRavi() {
        return thiefRavi;
    }


    /**
     *  Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *
     *@return     aktuální prostor
     */

    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }

    /**
     *  Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory
     *
     *@param  prostor nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor) {
        aktualniProstor = prostor;
        notifyObserver(ZmenaHry.ZMENA_MISTNOSTI);
    }

    private void notifyObserver(ZmenaHry zmenaHry) {
        for(Pozorovatel pozorovatel : seznamPozorovatelu.get(zmenaHry)) {
            pozorovatel.aktualizuj();
        }
    }

    /**
     * Method sets new prostor for moving npc
     * @param prostor new prostor when moving npc is relocated to
     */
    public void setNpcProstor(Prostor prostor) {
        npcProstor = prostor;
    }

    /**
     * Method acquires current Prostor of moving npc
     * @return Returns current prostor
     */
    public Prostor getNpcProstor() {
        return npcProstor;
    }

    /**
     * Needed to reference mare_lamentorum location to sail command
     * @return Returns mareLamentorum
     */
    public Prostor getTeleport() { return teleport;}

    /**
     * Needed to reference chakrams and couldn't think of better way
     * @return Returns chakrams
     */

    public Thing getChakram() {
        return chakram;
    }

    /**
     *
     * @return Vrací mapu prostorů
     */

    public Map<String, Prostor> getProstorMap() {
        return prostorMap;
    }

    @Override
    public void registruj(ZmenaHry zmenaHry, Pozorovatel pozorovatel) {
        seznamPozorovatelu.get(zmenaHry).add(pozorovatel);
    }
}
