package cz.vse.danterragui.main;
/**
 * interface for observable objects
 * influencer, observable
 * @author Daniel Brus
 * @version 1.0, October 2023
 */
public interface PredmetPozorovani {
   void registruj(ZmenaHry zmenaHry, Pozorovatel pozorovatel);
}
