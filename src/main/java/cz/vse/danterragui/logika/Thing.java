package cz.vse.danterragui.logika;

/**
 * Class Thing - Stores all the information regarding Things.
 * Players can collect and carry certain things during their playthrough.
 * @author Daniel Brus
 * @version 0.9, May 2023
 */

public class Thing {
    private final String name;
    private boolean collectable;
    private boolean useable;
    private boolean keyItem;
    private String specialEffect;
    private int weight;

    /**
     * Constructor
     * @param name Name of the Thing
     * @param collectable Boolean - only certain things can be collected
     * @param useable - Boolean - only certain things can be used
     * @param keyItem - Boolean - only certain things are considered key items
     */

    public Thing(String name, boolean collectable, boolean useable, boolean keyItem) {
        this.name = name;
        this.collectable = collectable;
        this.useable = useable;
        this.keyItem = keyItem;
    }

    /**
     * Getter for name
     * @return String name
     */

    public String getName() {
        return name;
    }

    /**
     * Getter for collectable
     * @return true if item is collectable
     */

    public boolean isCollectable() {
        return collectable;
    }

    /**
     * Getter for keyItem
     * @return True if item is keyItem
     */

    public boolean isKeyItem(){
        return keyItem;
    }

    /**
     * Getter for useable
     * @return True if item can be used, otherwise false
     */

    public boolean isUseable() {
        return useable;
    }

    /**
     * Getter for special effect
     * @return String special effect (At this stage special effects are Strings of text)
     */
    public String getSpecialEffect() {
        return specialEffect;
    }

    /**
     * Setter to change collectibility. If a player fulfills certain conditions, item can become collectable
     * @param collectable
     */

    public void setCollectable(boolean collectable) {
        this.collectable = collectable;
    }

    /**
     * Gives an item special effect
     * @param specialEffect
     */

    public void setSpecialEffect(String specialEffect) {
        this.specialEffect = specialEffect;
    }

    /**
     * Gives information to the player that item has been used
     * @return String special effect
     */

    public String useItem(){
        //System.out.println("You used: " + getName());
        return getSpecialEffect();
    }

    /**
     * @return String name
     */
    @Override
    public String toString(){
        return name;
    }


}
