package cz.vse.danterragui.logika;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PrikazWhereamiTest {
    private Hra hra;
    private Prostor testRoom = new Prostor("testRoom", "test",false,null);
    private PrikazWhereami wherami;

    @BeforeEach
    public void setUp() {
        hra = new Hra();
        wherami = new PrikazWhereami(hra.getHerniPlan());
        hra.getHerniPlan().setAktualniProstor(testRoom);
    }
    @Test
    public void testPrikazWherami(){
        assertEquals("Zadej prikaz bez parametru",wherami.provedPrikaz("parametr navic"));
        assertEquals(testRoom.getNazev() + " \n" + testRoom.aibaDescription(), wherami.provedPrikaz());
        testRoom.setWasScanned(true);
        assertEquals(testRoom.getNazev() + " \n" +
                testRoom.aibaDescription() + " \n" + testRoom.aibaScan(), wherami.provedPrikaz());

    }

}
