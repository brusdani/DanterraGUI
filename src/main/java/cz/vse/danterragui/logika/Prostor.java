package cz.vse.danterragui.logika;

import cz.vse.danterragui.main.Pozorovatel;
import cz.vse.danterragui.main.PredmetPozorovani;
import cz.vse.danterragui.main.ZmenaHry;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Trida Prostor - popisuje jednotlivé prostory (místnosti) hry
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * "Prostor" reprezentuje jedno místo (místnost, prostor, ..) ve scénáři hry.
 * Prostor může mít sousední prostory připojené přes východy. Pro každý východ
 * si prostor ukládá odkaz na sousedící prostor.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 * @version pro školní rok 2016/2017
 *
 * Updated in May 2023
 * @author Daniel Brus
 */
public class Prostor implements PredmetPozorovani {
    private Map<ZmenaHry, Set<Pozorovatel>> seznamPozorovatelu = new HashMap<>();

    private String nazev;
    private String aibaDescription;
    private boolean locked;
    private Riddle riddle;
    private Thing key;
    private boolean hasRiddle;
    private boolean wasScanned = false;
    private Set<Prostor> vychody;// obsahuje sousední místnosti
    private final Map<String, Thing> things;
    private final Map<String, Npc> npcs;

    /**
     * Vytvoření prostoru se zadaným popisem, např. "kuchyň", "hala", "trávník
     * před domem"
     *
     * @param nazev nazev prostoru, jednoznačný identifikátor, jedno slovo nebo
     * víceslovný název bez mezer.
     *      */
    public Prostor(String nazev, String aibaDescription, boolean locked, Thing key) {
        this.nazev = nazev;
        this.aibaDescription=aibaDescription;
        this.locked = locked;
        this.key = key;
        vychody = new HashSet<>();
        things = new HashMap<>();
        npcs = new HashMap<>();
        for(ZmenaHry zmenaHry : ZmenaHry.values()){
            seznamPozorovatelu.put(zmenaHry, new HashSet<>());
        }
    }

    /**
     * Locks/unlocks the room
     * @param locked
     */

    public void setLock(boolean locked) {
        this.locked = locked;
        notifyObserver(ZmenaHry.STAV_HRY);
    }

    /**
     * Checks if the room is locked or not
     * @return True if the room is locked
     */

    public boolean isLocked() {

        return locked;
    }

    /**
     * Getter for the keyItem if the room has lock
     * @return keyItem
     */

    public Thing getKey() {
        return key;
    }

    /**
     * Getter for riddle in case room has riddle
     * @return Riddle
     */
    public Riddle getRiddle() {
        return riddle;
    }

    /**
     * Puts riddle inside the room
     * @param riddle
     */
    public void setRiddle(Riddle riddle) {
        this.riddle = riddle;
    }

    /**
     * Checks if room has currently unsolved riddle
     * @return True if there's unsolved riddle, otherwise false
     */
    public boolean isHasRiddle() {
        return hasRiddle;
    }

    /**
     * Mostly used to change "hasRiddle" to false after player solves the riddle
     * @param hasRiddle
     */

    public void setHasRiddle(boolean hasRiddle) {
        this.hasRiddle = hasRiddle;
    }

    /**
     * Checks if the room was scanned (aibaScan performed)
     * @return True if room has been scanned, otherwise false
     */
    public boolean isWasScanned() {
        return wasScanned;
    }

    /**
     * Setter used to set "wasScanned" to true - to note that room was scanned
     * @param wasScanned
     */
    public void setWasScanned(boolean wasScanned) {
        //System.out.printf("Was scanned was called");
        this.wasScanned = wasScanned;
        notifyObserver(ZmenaHry.STAV_HRY);
    }

    private void notifyObserver(ZmenaHry zmenaHry) {
        System.out.println("Observer notified");
        for(Pozorovatel pozorovatel : seznamPozorovatelu.get(zmenaHry)) {
            pozorovatel.aktualizuj();
        }
    }

    /**
     * Definuje východ z prostoru (sousední/vedlejsi prostor). Vzhledem k tomu,
     * že je použit Set pro uložení východů, může být sousední prostor uveden
     * pouze jednou (tj. nelze mít dvoje dveře do stejné sousední místnosti).
     * Druhé zadání stejného prostoru tiše přepíše předchozí zadání (neobjeví se
     * žádné chybové hlášení). Lze zadat též cestu ze do sebe sama.
     *
     * @param vedlejsi prostor, který sousedi s aktualnim prostorem.
     *
     */
    public void setVychod(Prostor vedlejsi) {
        vychody.add(vedlejsi);
    }

    /**
     * adds thing into room
     * @param thing
     */

    public void addThing(Thing thing) {
        things.put(thing.getName(), thing);
    }

    /**
     * removes thing from the room
     * @param nameThing
     */
    public void removeThing(String nameThing) {
        things.remove(nameThing);
    }

    /**
     * Gets instance of a thing from String name (key)
     * @param nameThing
     * @return Thing
     */
    public Thing returnThing(String nameThing) {
        return things.get(nameThing);
    }

    /**
     *Adds npc into room
     * @param npc
     */
    public void addNpc(Npc npc){
        npcs.put(npc.getName(), npc);
    }

    /**
     * Removes npc from the room
     * @param npcName
     */
    public void removeNpc(String npcName) {
        npcs.remove(npcName);
    }

    /**
     * Gets instance of npc from a String name (map key)
     * @param npcName
     * @return Npc
     */
    public Npc returnNpc(String npcName) {
        return npcs.get(npcName);
    }




    /**
     * Metoda equals pro porovnání dvou prostorů. Překrývá se metoda equals ze
     * třídy Object. Dva prostory jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     *
     * Bližší popis metody equals je u třídy Object.
     *
     * @param o object, který se má porovnávat s aktuálním
     * @return hodnotu true, pokud má zadaný prostor stejný název, jinak false
     */

    @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr
        if (!(o instanceof Prostor)) {
            return false;    // pokud parametr není typu Prostor, vrátíme false
        }
        // přetypujeme parametr na typ Prostor
        Prostor druhy = (Prostor) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů.
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

        return (java.util.Objects.equals(this.nazev, druhy.nazev));
    }

    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object
     */
    @Override
    public int hashCode() {
        int vysledek = 3;
        int hashNazvu = java.util.Objects.hashCode(this.nazev);
        vysledek = 37 * vysledek + hashNazvu;
        return vysledek;
    }


    /**
     * Vrací název prostoru (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return název prostoru
     */
    public String getNazev() {
        return nazev;
    }

    /**
     * Vrací "dlouhý" popis prostoru, který může vypadat následovně: Jsi v
     * mistnosti/prostoru vstupni hala budovy VSE na Jiznim meste. vychody:
     * chodba bufet ucebna
     *
     * @return Dlouhý popis prostoru
     */
//    public String dlouhyPopis() {
//        return "Jsi v mistnosti/prostoru " + popis + ".\n"
//                + popisVychodu() + " \n";
//                //+ describeThings();
    //   }

    /**
     * Describes things, exits, npcs and riddles, all put into one method to make code easier to read
     * @return String as description of the current room
     */

    public String aibaScan () {
        return    popisVychodu() + " \n"
                + describeThings() + "\n"
                + describeNpcs() + "\n"
                +describeRiddle();
    }

    /**
     * Description of the room before it gets scanned
     * @return String description
     */
    public String aibaDescription() {
        return aibaDescription;
    }

    public void setAibaDescription(String aibaDescription) {
        this.aibaDescription = aibaDescription;
    }

    /**
     * Vrací textový řetězec, který popisuje sousední východy, například:
     * "vychody: hala ".
     *
     * @return Popis východů - názvů sousedních prostorů
     */
    private String popisVychodu() {
        String vracenyText = "východy:";
        for (Prostor sousedni : vychody) {
            vracenyText += " " + sousedni.getNazev();
            if (sousedni.isLocked()){
                vracenyText += "(locked)";
            }
        }
        return vracenyText;
    }

    /**
     * Describes Things in the room
     * @return String with all Things
     */

    private String describeThings() {
        String vracenyText = "things:";
        for (String thing : things.keySet()) {
            vracenyText += " " + thing;
        }
        return vracenyText;
    }

    /**
     * Describes Npcs in the room
     * @return String with all Npcs
     */
    private String describeNpcs() {
        String vracenyText = "NPCs:";
        for (String npc : npcs.keySet()) {
            vracenyText += " " + npc;
        }
        if (vracenyText.equals("NPCs:")){
            return "There's no one in this room";
        }
        return vracenyText;
    }

    /**
     * Says if there's riddle in the room or not
     * @return String that says if there is a riddle or not
     */

    private String describeRiddle() {
        String vracenyText = "Riddle: ";
        if (isHasRiddle()){
            return "There seems to be a riddle to solve!";
        }
        return "No riddle in this room";
    }
    @Override
    public String toString(){
        if (isLocked()){
            return nazev + "(Locked)";
        } else
            return nazev;
    }
    /**
     * Vrací prostor, který sousedí s aktuálním prostorem a jehož název je zadán
     * jako parametr. Pokud prostor s udaným jménem nesousedí s aktuálním
     * prostorem, vrací se hodnota null.
     *
     * @param nazevSouseda Jméno sousedního prostoru (východu)
     * @return Prostor, který se nachází za příslušným východem, nebo hodnota
     * null, pokud prostor zadaného jména není sousedem.
     */
    public Prostor vratSousedniProstor(String nazevSouseda) {
        List<Prostor>hledaneProstory =
                vychody.stream()
                        .filter(sousedni -> sousedni.getNazev().equals(nazevSouseda))
                        .collect(Collectors.toList());
        if(hledaneProstory.isEmpty()){
            return null;
        }
        else {
            return hledaneProstory.get(0);
        }
    }

    /**
     * Counts the number of things in the room. Serves testing purposes mostly
     * @return number
     */
    public int countThings(){
        return things.size();
    }

    /**
     * Vrací kolekci obsahující prostory, se kterými tento prostor sousedí.
     * Takto získaný seznam sousedních prostor nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Prostor.
     *
     * @return Nemodifikovatelná kolekce prostorů (východů), se kterými tento
     * prostor sousedí.
     */
    public Collection<Prostor> getVychody() {
        return Collections.unmodifiableCollection(vychody);
    }

    public List<Thing> getThings() {
        List<Thing> itemList = new ArrayList<>();
        itemList.addAll(things.values());
        return itemList;
    }

    @Override
    public void registruj(ZmenaHry zmenaHry, Pozorovatel pozorovatel) {
        seznamPozorovatelu.get(zmenaHry).add(pozorovatel);
    }
}
