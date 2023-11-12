package cz.vse.danterragui.logika;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class PrikazJdiTest {
    private Hra hra;
    private PrikazJdi prikazJdi;
    private Aiba aiba;
    private Thing keys = new Thing("keys", true, false, true);
    private Prostor testRoom = new Prostor("testRoom", "test", false, null);
    private Prostor lockedRoom = new Prostor("lockedRoom", "locked", true, keys);
    private Inventory inventory;
    private PrikazUnlock unlock;


    @BeforeEach
    public void setup(){
        hra = new Hra();
        aiba = new Aiba(true);
        inventory = new Inventory(new HashMap<>());
        inventory.addThing(keys);
        prikazJdi = new PrikazJdi(hra.getHerniPlan(),aiba);
        hra.getHerniPlan().setAktualniProstor(testRoom);
        testRoom.setVychod(lockedRoom);
        lockedRoom.setVychod(testRoom);
        unlock = new PrikazUnlock(hra.getHerniPlan(),inventory);
    }
    @Test
    public void testPrikazJdi(){
        assertEquals("Kam mám jít? Musíš zadat jméno východu", prikazJdi.provedPrikaz());
        assertEquals("Tam se odsud jít nedá!", prikazJdi.provedPrikaz("moon"));
//        assertEquals("dveře do místnosti "+ hra.getHerniPlan().getAktualniProstor().
//                vratSousedniProstor("lockedRoom") +" jsou zamčené", prikazJdi.provedPrikaz("lockedRoom"));
        unlock.provedPrikaz("lockedRoom");
        assertEquals(lockedRoom.aibaDescription(), prikazJdi.provedPrikaz("lockedRoom"));
        testRoom.setWasScanned(true);
        assertEquals(testRoom.aibaScan(), prikazJdi.provedPrikaz("testRoom"));


    }

}
