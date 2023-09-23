package cz.vse.danterragui.logika;

import cz.vse.danterragui.uiText.TextoveRozhrani;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EndingTest {
    private Hra hra;
    private Inventory inventory;
    private Ending ending;
    private PrikazTalkTo talkTo;
    private Npc npc = new Npc("keeper", "keeper","end",null);
    private Thing moonstone = new Thing("moonstone",true,false,true);
    private Thing sunstone = new Thing("sunstone", true, false, true);
    private Prostor testRoom = new Prostor("testRoom", "test", false, null);

    @BeforeEach
    public void setup(){
        hra = new Hra();
        inventory = new Inventory();
        talkTo = new PrikazTalkTo(hra.getHerniPlan(),npc,hra);
        ending = new Ending(inventory);
        testRoom.addNpc(npc);
        hra.getHerniPlan().setAktualniProstor(testRoom);
    }
    @Test
    public void testEnding(){
        assertEquals("Victor shall write the tale and vanquished become its villain", talkTo.provedPrikaz("keeper"));
        assertTrue(hra.konecHry());
        assertEquals(ending.playerEnding(),ending.getBadEnd());
        inventory.addThing(moonstone);
        inventory.addThing(sunstone);
        assertEquals(ending.playerEnding(),ending.getGoodEnd());
    }
}
