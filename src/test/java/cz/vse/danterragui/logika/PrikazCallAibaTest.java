package cz.vse.danterragui.logika;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PrikazCallAibaTest {
    private Hra hra;
    private Aiba aiba;
    private PrikazCallAiba callAiba;

    @BeforeEach
    public void setUp() {
        hra = new Hra();
        aiba = new Aiba(false);
        callAiba = new PrikazCallAiba(aiba);
    }
    @Test
    public void testCallAiba(){
        assertEquals("callAiba doesn't have parameters", callAiba.provedPrikaz("parametr"));
        assertFalse(aiba.isSummoned());
        assertEquals("Aiba has been summoned \n"+ aiba.aibaIntroduction(),callAiba.provedPrikaz());
        assertTrue(aiba.isSummoned());
    }
}
