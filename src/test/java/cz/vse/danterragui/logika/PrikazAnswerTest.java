package cz.vse.danterragui.logika;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class PrikazAnswerTest {
    private Hra hra;
    private Inventory inventory = new Inventory(new HashMap<>());
    private PrikazAnswer answer;
    private Prostor testRoom = new Prostor("testRoom", "test",false,null);
    private Thing reward = new Thing("reward", true,false, false);
    private Thing hint = new Thing("hint", true, true, false);
    private Riddle riddle = new Riddle("Hello", "World", reward, hint);

    @BeforeEach
    public void setUp() {
        hra = new Hra();
        answer = new PrikazAnswer(hra.getHerniPlan(), inventory);
        hra.getHerniPlan().setAktualniProstor(testRoom);
        //inventory.addThing(hint);
    }
    @Test
    public void testPrikazAnswer(){
        assertEquals("Please answer", answer.provedPrikaz());
        assertEquals("What am I thinking?", answer.provedPrikaz("yes","no"));
        assertEquals("There's no riddle in this location.",answer.provedPrikaz("world"));
        testRoom.setRiddle(riddle);
        testRoom.setHasRiddle(true);
        assertTrue(testRoom.isHasRiddle());
        assertEquals("Wrong answer, doom awaits you. Just kidding, try answering again", answer.provedPrikaz("wrong answer"));
        assertEquals("You solved the riddle and received " + riddle.getReward(), answer.provedPrikaz("World"));
        assertFalse(testRoom.isHasRiddle());
    }
}
