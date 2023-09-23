package cz.vse.danterragui.logika;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


public class PrikazTalkToTest {
    private Hra hra;
    private Npc gandalf = new Npc("gandalf", "Legend", "You shall not pass", null);
    private Npc keeper = new Npc("keeper","yep","no need", null);
    private Prostor testRoom = new Prostor("testRoom", "test",false,null);
    private PrikazTalkTo talkto;

    @BeforeEach
    public void setUp() {
        hra = new Hra();
        talkto= new PrikazTalkTo(hra.getHerniPlan(),gandalf,hra);
        testRoom.addNpc(gandalf);
        hra.getHerniPlan().setAktualniProstor(testRoom);

    }
    @Test
    public void testTalkToNpc(){
        assertEquals("You shall not pass", talkto.provedPrikaz("gandalf"));
        assertEquals("Nikdo takový tu není", talkto.provedPrikaz("yoda"));
        assertEquals("Am I supposed to talk to myself", talkto.provedPrikaz());
        assertEquals("Nikdo takový tu není", talkto.provedPrikaz("keeper"));
        testRoom.addNpc(keeper);
        assertEquals("Victor shall write the tale and vanquished become its villain", talkto.provedPrikaz("keeper"));

    }
}
