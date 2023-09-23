package cz.vse.danterragui.logika;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrikazNapovedaTest {
    private PrikazNapoveda napoveda;
    private Hra hra;
    private SeznamPrikazu seznamPrikazu;


    @BeforeEach
    public void setup(){
        hra = new Hra();
        seznamPrikazu = new SeznamPrikazu();
        napoveda = new PrikazNapoveda(seznamPrikazu);

    }
    @Test
    public void testPrikazNapoveda(){
        assertEquals("Your task is to bring back light to Danterra\n"
                + "You can use following commands\n"
                + seznamPrikazu.vratNazvyPrikazu(), napoveda.provedPrikaz());
    }

}
