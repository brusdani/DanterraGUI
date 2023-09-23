package cz.vse.danterragui.logika;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class PrikazPickupTest {
    private Hra hra;
    private Inventory inventory = new Inventory();
    private Thing keys = new Thing("keys", true,false,true);
    private Thing broom = new Thing("broom", true,false, false);
    private Thing piano = new Thing("piano", false, true, false);
    private Prostor testRoom = new Prostor("testRoom", "test",false,null);
    private PrikazPickup pickup;

    @BeforeEach
    public void setUp() {
        hra = new Hra();
        pickup = new PrikazPickup(hra.getHerniPlan(), inventory);
        hra.getHerniPlan().setAktualniProstor(testRoom);
        testRoom.addThing(piano);
        testRoom.addThing(broom);
        testRoom.addThing(keys);
    }

    @Test
    public void testPickupThing(){
        assertEquals("věc byla sebrána", pickup.provedPrikaz("broom"));
        assertEquals("Tuto vec nelze prenest",pickup.provedPrikaz("piano"));
        assertEquals("Takova vec se v prostoru nenachazi", pickup.provedPrikaz("laser"));
        inventory.setCapacity(1);
        System.out.println(inventory.displayItems());
        assertEquals("Backpack is too heavy. I can't carry more items", pickup.provedPrikaz("keys"));
    }
}
