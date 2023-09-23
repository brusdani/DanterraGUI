package cz.vse.danterragui.logika;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrikazGiveItemTest {
    private Hra hra;
    private Inventory inventory = new Inventory();
    private Thing broom = new Thing("broom", true,false,false);
    private Thing wrongItem = new Thing ("wrongItem", true, false, false);
    private Thing reward = new Thing("reward", true, false, false);
    private Quest npcQuest = new Quest("solve it", broom, reward);
    private Npc npcWQuest = new Npc("WQuest", "has quest", "yo",npcQuest );
    private Npc npcWOQuest = new Npc ("WOQuest", "doesn't have quest", "oi", null);
    private PrikazGiveItem giveItem;
    private Prostor testRoom = new Prostor("testRoom", "test",false,null);

    @BeforeEach
    public void setup(){
        hra = new Hra();
        giveItem = new PrikazGiveItem(hra.getHerniPlan(), inventory);
        hra.getHerniPlan().setAktualniProstor(testRoom);
        inventory.addThing(wrongItem);
        testRoom.addNpc(npcWOQuest);
        testRoom.addNpc(npcWQuest);

    }
    @Test
    public void testPrikazGiveItem(){
        assertEquals("Which item am I supposed to give away", giveItem.provedPrikaz());
        assertEquals("Please specify both ITEM and CHARACTER in that order", giveItem.provedPrikaz("hello"));
        assertEquals("I don't have this item in my backpack", giveItem.provedPrikaz("broom", "WQuest"));
        assertEquals("No one with this name is here", giveItem.provedPrikaz("wrongItem", "NonexistenNPC"));
        assertEquals("This character doesn't have quest", giveItem.provedPrikaz("wrongItem", "WOQuest"));
        assertEquals("That's not the correct item", giveItem.provedPrikaz("wrongItem", "WQuest"));
        inventory.addThing(broom);
        assertEquals("You received reward " + reward.getName(), giveItem.provedPrikaz("broom", "WQuest"));
        inventory.addThing(broom);
        assertEquals("This character doesn't have quest", giveItem.provedPrikaz("broom", "WQuest"));
    }
}
