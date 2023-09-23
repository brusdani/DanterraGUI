package cz.vse.danterragui.logika;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrikazDropTest {
    private Hra hra;
    private Inventory inventory = new Inventory();
    private Thing broom = new Thing("broom", true,false, false);
    private Thing keys = new Thing("keys", true,false, true);
    private Prostor testRoom = new Prostor("testRoom", "test",false,null);
    private PrikazDrop drop;

    @BeforeEach
    public void setUp() {
        hra = new Hra();
        drop = new PrikazDrop(inventory, hra.getHerniPlan());
        hra.getHerniPlan().setAktualniProstor(testRoom);
        inventory.addThing(broom);
        inventory.addThing(keys);
    }

    @Test
    public void testPrikazDrop(){
        assertEquals("Don't know which item to drop.",drop.provedPrikaz());
        assertEquals("I can only drop one thing at a time.",drop.provedPrikaz("hello","World"));
        assertEquals("I don't have this item in the inventory",drop.provedPrikaz("HolyGrail"));
        assertEquals("This item might be important, I should keep it",drop.provedPrikaz("keys"));
        assertEquals(0, testRoom.countThings());
        assertEquals(2, inventory.inventorySize());
        assertEquals("You throw away " + broom.getName(), drop.provedPrikaz("broom"));
        assertEquals(1, testRoom.countThings());
        assertEquals(1, inventory.inventorySize());

    }

}
