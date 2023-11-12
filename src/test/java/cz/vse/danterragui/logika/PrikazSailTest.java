package cz.vse.danterragui.logika;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class PrikazSailTest {
    private Hra hra;
    private Inventory inventory = new Inventory(new HashMap<>());
    private PrikazSail sail;
    private Prostor testRoom = new Prostor("testRoom", "test",false,null);
    private Thing ticket = new Thing("ticket", true, false, true);
    private Thing moonstone = new Thing("moonstone", true, false, true);
    private Prostor pub = new Prostor("pub", "test", false, null);
    private PrikazWhereami whereami;
    private PrikazShowInventory showInventory;

    @BeforeEach
    public void setUp() {
        hra = new Hra();
        sail = new PrikazSail(inventory, hra.getHerniPlan());
        hra.getHerniPlan().setAktualniProstor(testRoom);
        whereami = new PrikazWhereami(hra.getHerniPlan());
        showInventory = new PrikazShowInventory(inventory);
    }
    @Test
    public void testPrikazSail(){
        assertEquals("sail doesn't have parameters",sail.provedPrikaz("Hello"));
        assertEquals("you need ticket to be able to sail",sail.provedPrikaz());
        inventory.addThing(ticket);
        assertEquals("You can only sail from pub",sail.provedPrikaz());
        hra.getHerniPlan().setAktualniProstor(pub);
        assertEquals(sail.transitionText(),sail.provedPrikaz());
        System.out.println(whereami.provedPrikaz());
        System.out.println(showInventory.provedPrikaz());
    }
}
