package cz.vse.danterragui.logika;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class PrikazShowRiddleTest {
    private Hra hra;
    private Thing reward = new Thing("reward",true,false,false);
    private Thing hint = new Thing("hint", true, true, false);
    private Riddle riddle = new Riddle("Random riddle","YEPtree",reward,hint);
    private PrikazShowRiddle  showRiddle;
    private Prostor testRoom = new Prostor("withRiddle", "test", false, null);
    private Prostor testRoom2 = new Prostor("withoutRiddle", "test", false, null);

    @BeforeEach
    public void setUp() {
        hra = new Hra();
        testRoom.setRiddle(riddle);
        testRoom.setHasRiddle(true);
        showRiddle = new PrikazShowRiddle(hra.getHerniPlan(),riddle);
    }
    @Test
    public void testPrikazShowRiddle(){
        hra.getHerniPlan().setAktualniProstor(testRoom);
        assertEquals("Random riddle", showRiddle.provedPrikaz());
        hra.getHerniPlan().setAktualniProstor(testRoom2);
        assertEquals("There isn't any riddle in the room", showRiddle.provedPrikaz());
    }
}
