package cz.vse.danterragui.logika;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class PrikazUnlockTest {
    private Hra hra;
    private Inventory inventory = new Inventory(new HashMap<>());
    private Thing keys = new Thing("keys", true, false, true);

    private Prostor lockedRoom = new Prostor("lockedRoom", "locked", true, keys);
    private Prostor testRoom = new Prostor("testRoom", "test", false, null);
    private PrikazUnlock unlock;

    @BeforeEach
    public void setUp() {
        hra = new Hra();
        unlock = new PrikazUnlock(hra.getHerniPlan(),inventory);
        lockedRoom.setVychod(testRoom);
        testRoom.setVychod(lockedRoom);
        hra.getHerniPlan().setAktualniProstor(testRoom);
    }
    @Test
    public void testPrikazUnlock(){
        assertEquals("Odsud nevedou dveře do místnosti les !",unlock.provedPrikaz("les"));
        assertEquals("To unlock the path you need keys",unlock.provedPrikaz("lockedRoom"));
        inventory.addThing(keys);
        assertEquals("Path to lockedRoom is now clear",unlock.provedPrikaz("lockedRoom"));
        assertEquals("The path to lockedRoom is already unlocked!",unlock.provedPrikaz("lockedRoom"));
    }
}
