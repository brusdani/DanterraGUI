package cz.vse.danterragui.logika;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrikazKonecTest {
    private Hra hra;
    private PrikazKonec prikazKonec;

    @BeforeEach
    public void setup(){
        hra = new Hra();
        prikazKonec = new PrikazKonec(hra);
    }
    @Test
    public void testPrikazKonec(){
        assertEquals("Ukončit co? Nechápu, proč jste zadal druhé slovo.", prikazKonec.provedPrikaz("Hello"));
        assertEquals("hra ukončena příkazem konec", prikazKonec.provedPrikaz());
        assertTrue(hra.konecHry());


    }
}
