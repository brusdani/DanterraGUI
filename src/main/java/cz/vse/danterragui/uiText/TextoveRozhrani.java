package cz.vse.danterragui.uiText;


import java.io.*;
import java.util.Scanner;
import cz.vse.danterragui.logika.IHra;
/**
 *  Class TextoveRozhrani
 * 
 *  Toto je uživatelského rozhraní aplikace Adventura
 *  Tato třída vytváří instanci třídy Hra, která představuje logiku aplikace.
 *  Čte vstup zadaný uživatelem a předává tento řetězec logice a vypisuje odpověď logiky na konzoli.
 *  
 *  
 *
 *@author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 *@version    pro školní rok 2016/2017
 */

public class TextoveRozhrani {
    private IHra hra;
    private boolean ukonceniPrikazemKonec;

    /**
     *  Vytváří hru.
     */
    public TextoveRozhrani(IHra hra) {
        this.hra = hra;
    }

    /**
     *  Hlavní metoda hry. Vypíše úvodní text a pak opakuje čtení a zpracování
     *  příkazu od hráče do konce hry (dokud metoda konecHry() z logiky nevrátí
     *  hodnotu true). Nakonec vypíše text epilogu.
     */
    public void hraj() {
        System.out.println(hra.vratUvitani());
        System.out.println(hra.poem());
        System.out.println(hra.intro());

        // základní cyklus programu - opakovaně se čtou příkazy a poté
        // se provádějí do konce hry.
        while (!hra.konecHry()) {
            String radek = prectiString();
            System.out.println(hra.zpracujPrikaz(radek));
            if (radek.equals("konec")){
                ukonceniPrikazemKonec = true;
            }
        }
        if (ukonceniPrikazemKonec){
            System.out.println(hra.vratEpilog());
        }
        else {
            System.out.println(hra.ending());
            System.out.println(hra.playerEnding());
            System.out.println(hra.vratEpilog());
        }
    }

    /**
     *  Metoda přečte příkaz z příkazového řádku
     *
     *@return    Vrací přečtený příkaz jako instanci třídy String
     */
    private String prectiString() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        return scanner.nextLine();
    }
    public void hrajZeSouboru(String jmenoSouboru){
        try (BufferedReader cteni = new BufferedReader(new FileReader(jmenoSouboru));
             PrintWriter zapis = new PrintWriter(new FileWriter("vystup.txt"))){
            System.out.println(hra.vratUvitani());
            zapis.println(hra.vratUvitani());

            String radek = cteni.readLine();

            while (radek != null && !hra.konecHry()) {
                System.out.println("> " + radek);
                zapis.println("> " + radek);

                String vracenyText = hra.zpracujPrikaz(radek);
                System.out.println(vracenyText);
                zapis.println(vracenyText);

                radek = cteni.readLine();

            }
            System.out.println(hra.vratEpilog());

        } catch(FileNotFoundException e){
            File file = new File(jmenoSouboru);
            System.out.println("Soubor " + jmenoSouboru + " nebyl nalezen!");
            System.out.println("Soubor byl na cestě: " +file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Finally cast");
        }
    }

}
