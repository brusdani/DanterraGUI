package cz.vse.danterragui.logika;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

public class PrikazShowInventoryTest {
    private Hra hra;
    private Inventory inventory = new Inventory();
    private Thing keys = new Thing("keys", true,false,true);

    private PrikazShowInventory showInventory;

    @BeforeEach
    public void setUp() {
        hra = new Hra();
        showInventory = new PrikazShowInventory(inventory);
        inventory.addThing(keys);
    }
    @Test
    public void testPrikazShowInventory(){
        Set expected = new HashSet<>();
        expected.add(keys);
        assertEquals("Your inventory: " + expected, showInventory.provedPrikaz());
        assertEquals("Zadej prikaz bez parametru", showInventory.provedPrikaz("hello"));
    }

}
