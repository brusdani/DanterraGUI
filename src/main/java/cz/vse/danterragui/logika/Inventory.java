package cz.vse.danterragui.logika;

import cz.vse.danterragui.main.Pozorovatel;
import cz.vse.danterragui.main.PredmetPozorovani;
import cz.vse.danterragui.main.ZmenaHry;

import java.util.*;

/**
 * Class Inventory - Represents player's inventory
 * Allows players to carry Things with them
 * @author Daniel Brus
 * @version 0.9, October 2023
 */

public class Inventory implements PredmetPozorovani {
    private HashMap<String, Thing> items = new HashMap<>();

    private Map<ZmenaHry, Set<Pozorovatel>> seznamPozorovatelu = new HashMap<>();
    private int capacity = 10;

    /**
     * Constructor
     * @param items HashMap key - string, value - Thing object
     */
    public Inventory(HashMap<String, Thing> items) {
        this.items = items;
        for(ZmenaHry zmenaHry : ZmenaHry.values()){
            seznamPozorovatelu.put(zmenaHry,new HashSet<>());
        }
    }

    /**
     * Adds thing into inventory
     * @param item
     * @return Map of items (inventory)
     */
    public HashMap<String,Thing> addThing(Thing item) {
        items.put(item.getName(), item);
        System.out.println("We added the item: " + item.getName());
        upozorniPozorovatele(ZmenaHry.STAV_INVENTARE);
        return items;
    }

    /**
     * Removes item from the inventory
     * @param nameThing
     * @return Map of items (inventory)
     */

    public HashMap<String, Thing> removeItem(String nameThing) {
        items.remove(nameThing);
        upozorniPozorovatele(ZmenaHry.STAV_INVENTARE);
        return items;
    }

    /**
     *Checks if inventory reached its capacity (is full)
     * @return Returns true if inventory is at max capacity(full), otherwise false
     */
    public boolean isFull () {
        if (items.size() >= capacity) {
            return true;
        }
        return false;
    }

    /**
     * Takes string input from user and converts it into Thing class in the map for further work with the object
     * @param nameThing
     * @return Returns Thing object
     */

    public Thing returnThing(String nameThing) {
        return items.get(nameThing);
    }

    /**
     * Used to display things in the inventory as a Set of strings
     * @return Returns Set of keys from the inventory
     */
    public Set displayItems(){
        return items.keySet();
    }


    public List<Thing> getItemsList() {
        List<Thing> itemList = new ArrayList<>();
        itemList.addAll(items.values());
        return itemList;
    }

    /**
     * Checks if player has certain item in the inventory
     * @param item
     * @return Returns true if players has the item/thing, otherwise false
     */
    public boolean containsItem(String item){
        return items.containsKey(item);
    }

    /**
     * Allows to change capacity of the inventory (so far only used in tests), but it could be used
     * in further updates (eg. player picks up an item that expands the inventory)
     * @param capacity
     */

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Same as containsItem, but when it is more convenient to work with Thing class object
     * @param item
     * @return True if inventory contains said Item, otherwise false
     */

    public boolean cointainsRealItem (Thing item){
        return items.containsValue(item);
    }

    /**
     * Same as removeItem, used in situations where it is more convenient to work with Thing class object
     * @param item
     * @return Returns map of items(inventory) with a Thing removed
     */
    public HashMap<String, Thing> removeRealItem(Thing item) {
        //System.out.println("item was removed " + item);
        items.values().remove(item);
        upozorniPozorovatele(ZmenaHry.STAV_INVENTARE);
        return items;
    }

    /**
     * Only used in tests to see if inventory had expected size
     * @return Returns size of the inventory
     */
    public int inventorySize(){
        return items.size();
    }

    @Override
    public void registruj(ZmenaHry zmenaHry, Pozorovatel pozorovatel){
        seznamPozorovatelu.get(zmenaHry).add(pozorovatel);
    }
    /**
     * * Registers an observer (Pozorovatel) for a specific game state change (ZmenaHry).
     * @param zmenaHry The type of game change
     */
    private void upozorniPozorovatele(ZmenaHry zmenaHry){
        for(Pozorovatel pozorovatel: seznamPozorovatelu.get(zmenaHry)){
            pozorovatel.aktualizuj();
        }
    }
}

