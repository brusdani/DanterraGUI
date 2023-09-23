package cz.vse.danterragui.logika;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PrikazAibaScanTest {
    private Hra hra;
    private PrikazAibaScan aibaScan;
    private Aiba aiba;
    private Prostor testRoom = new Prostor("testRoom", "test", false, null);



    @BeforeEach
    public void setUp() {
        hra = new Hra();
        aiba = new Aiba(false);
        aibaScan = new PrikazAibaScan(aiba, hra.getHerniPlan());
        hra.getHerniPlan().setAktualniProstor(testRoom);

    }
    @Test
    public void testPrikazAibaScan(){
        assertEquals("aibaScan doesn't have parameters", aibaScan.provedPrikaz("nejakyParametr"));
        assertEquals("Summon Aiba to perform the scan", aibaScan.provedPrikaz());
        aiba.setSummoned(true);
        assertFalse(testRoom.isWasScanned());
        assertEquals("Scanning the rooooom \n" + hra.getHerniPlan().getAktualniProstor().aibaScan(), aibaScan.provedPrikaz());
        assertTrue(testRoom.isWasScanned());
    }


}
