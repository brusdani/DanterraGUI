package cz.vse.danterragui.logika;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PrikazFireIVTest {
    private Hra hra;
    private Inventory inventory = new Inventory();
    private PrikazFireIV fireIV;
    private Prostor testRoom = new Prostor("testRoom", "test",false,null);
    private Prostor monaxia = new Prostor("monaxia", "monaxia", false, null);
    private Thing magicRod = new Thing("magicRod_u", true,true,true);
    private PrikazPickup pickup;
    private PrikazShowInventory showInventory;

    @BeforeEach
    public void setUp() {
        hra = new Hra();
        fireIV = new PrikazFireIV(inventory, hra.getHerniPlan());
        hra.getHerniPlan().setAktualniProstor(testRoom);
        Thing chakram = hra.getHerniPlan().getChakram();
        monaxia.addThing(chakram);
        pickup = new PrikazPickup(hra.getHerniPlan(), inventory);
        showInventory = new PrikazShowInventory(inventory);
    }
    @Test
    public void testPrikazFireIV(){
        assertEquals("Explosions don't need parameters", fireIV.provedPrikaz("parametrNavic"));
        assertEquals("you need a rod to cast the mighty spell", fireIV.provedPrikaz());
        inventory.addThing(magicRod);
        assertEquals("I don't want to hurt anyone. Only safe place to use the rod is in monaxia",fireIV.provedPrikaz());
        hra.getHerniPlan().setAktualniProstor(monaxia);
        assertEquals("Tuto vec nelze prenest",pickup.provedPrikaz("chakrams"));
        System.out.println(showInventory.provedPrikaz());
        assertEquals(fireIV.transitionText(),fireIV.provedPrikaz());
        System.out.println(showInventory.provedPrikaz());
        assertEquals("věc byla sebrána",pickup.provedPrikaz("chakrams"));
        System.out.println(showInventory.provedPrikaz());
    }

}
