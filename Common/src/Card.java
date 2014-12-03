
import java.util.ArrayList;
import java.util.Collections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sebbo
 */
public class Card {
    private String farbe = null;
    private String wert = null;
    private int kartenID ;
    private static ArrayList<Card> kartendeck = new ArrayList();
    private enum Farbe {
        HERZ, KARO, PIK, KREUZ
    }
    private enum Wert {
        SIEBEN, ACHT, NEUN, BUBE, DAME, KOENIG, ZEHN, ASS
    }
    
    public Card(String farbe, String wert, int counter) {
        this.farbe = farbe;
        this.wert = wert;
        this.kartenID = counter;
    }
    
    public static ArrayList kartendeckErzeugen() {
        int counter = 1;
        for (Farbe farbe : Farbe.values()) {
            for (Wert wert : Wert.values()) {
                kartendeck.add(new Card(farbe.name(), wert.name(), counter));
                counter++;
            }
        }
        // Liste mischen
        Collections.shuffle(kartendeck);
        return kartendeck;
    }
    
    public static ArrayList kartendeckMischen(ArrayList<Card> kartendeck) {
        // Liste mischen
        Collections.shuffle(kartendeck);
        return kartendeck;
    }
    
    public static void kartendeckAusgeben(ArrayList<Card> kartendeck) {
        for (Card karte : kartendeck) {
            System.out.println(karte.getFarbe() + " - " + karte.getWert() + " - " + karte.getID());
        }
    }
    
    public String getFarbe() {
        return this.farbe;
    }
    
    public String getWert() {
        return this.wert;
    }
    
    public int getID() {
        return this.kartenID;
    }
}
