package cz.vse.danterragui.logika;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class PrikazUseTest {
    private Hra hra;
    private Inventory inventory = new Inventory(new HashMap<>());
    private Thing broom = new Thing("broom", true, true, false);
    private Thing socks = new Thing("socks", true, false, false);
    private PrikazUse use;

    @BeforeEach
    public void setUp() {
        hra = new Hra();
        use = new PrikazUse(inventory);
        broom.setSpecialEffect("vroom");
        inventory.addThing(broom);
        inventory.addThing(socks);
    }
    @Test
    public void testPrikazUse(){
        assertEquals("Don't know which item to use", use.provedPrikaz());
        assertEquals("I can only use one item at a time.", use.provedPrikaz("broom","socks"));
        assertEquals("vroom", use.provedPrikaz("broom"));
        assertEquals("I can't use the item if I don't have it", use.provedPrikaz("hello"));
        assertEquals("This item isn't usable", use.provedPrikaz("socks"));
    }
}
