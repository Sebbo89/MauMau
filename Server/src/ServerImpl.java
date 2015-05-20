
import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sebbo
 */
public class ServerImpl implements IServer, Serializable {
    public static ArrayList<IClient> spielerliste = new ArrayList();
    public static ArrayList<IClient> readyListe = new ArrayList();
    private ArrayList benutzernamen = new ArrayList();
    private ServerFenster serverfenster;
    private int anzahlKarten = 7;
    private static ArrayList<Card> kartendeck;
    private Card topCard = null;
    private int zugCounter = 1;
    private int siebenerCounter = 0;
    /*
    public ServerImpl(ServerFenster serverFenster) {
        this.serverfenster = serverFenster;
    }
    */
    
    public ServerImpl() {
        
    }
    
    @Override
    public void clientAnmelden(String benutzername, String clientIndex) throws NotBoundException, RemoteException, AccessException{

        System.out.println(benutzername + " beim Spiel angemeldet!");
        benutzernamen.add(benutzername);
        for (int i = 0; i < benutzernamen.size(); i++ ) {
            System.out.println(benutzernamen.get(i).toString());
        }
        
        //Find Client
        Registry registry = LocateRegistry.getRegistry();
        IClient tmpClient = (IClient) registry.lookup( clientIndex );
        
        spielerliste.add(tmpClient);
        //serverfenster.jPanelHinzufuegen(benutzername);
    } 
    
    public void spielStarten(ArrayList<IClient> readyliste) throws RemoteException {
        kartendeck = Card.kartendeckErzeugen();
        // Karte als topCard deklarieren und aus Deck entfernen
        topCard = kartendeck.get(0);
        this.entferneKarteAusDeck(0);
        
        this.kartenAnSpielerVerteilen(readyliste);
        
        // Zufälligen Spieler starten lassen, "Zufällige Zahl" mit Anzahl der Spieler multiplizieren
        int spielerAmZugIndex = (int) (Math.random() * (readyListe.size()-1)); 
        readyListe.get(spielerAmZugIndex).setSpielerAmZugTrue();
        
        // Chatfenster bei Clients ausblenden und Spielfenster öffnen
        for (int i = 0; i < readyListe.size(); i++) {
            readyListe.get(i).clientFensterAusblenden();
            readyliste.get(i).spielFensterOeffnen();
            readyListe.get(i).nachrichtEmpfangen(readyListe.get(spielerAmZugIndex).getBenutzername() + " beginnt das Spiel! Viel Erfolg allen Kätzinnen und Katern!");
        }
                
        System.out.println(topCard.getFarbe() + " - " + topCard.getWert() + " - " + topCard.getID());
    }
    
    @Override
    public void spielerlisteAnzahlAusgeben() throws RemoteException {
        System.out.println(this.spielerliste.size());
    }
    
    @Override
    public ArrayList<IClient> spielerlisteAusgeben() throws RemoteException {
        return this.spielerliste;
    }
    
    /**
     *
     * @param IClient
     */
    public void kartenAnSpielerVerteilen(ArrayList<IClient> readyliste) throws RemoteException {
        for (int i = 0; i < readyliste.size(); i++) {
            readyliste.get(i).handNehmen(anzahlKarten);
            readyliste.get(i).handAusgeben();
            System.out.println(anzahlKarten + " Karten an " + readyliste.get(i).getBenutzername() + " ausgeteilt.");
        }
    }

    @Override
    public void listenChecken() throws RemoteException {
        // spielerliste durchgehen und Spieler, die bereit sind, in ReadyListe verschieben
        for (int i = 0; i < spielerliste.size(); i++) {
            if (spielerliste.get(i).getSpielerstatus()) {
                IClient tmpClient = spielerliste.get(i);
                spielerliste.remove(spielerliste.get(i));
                readyListe.add(tmpClient);
            }
        }
        // readyListe durchgehen und Spieler, die nicht mehr bereit sind, in Spielerliste verschieben
        for (int i = 0; i < readyListe.size(); i++) {
            if (!readyListe.get(i).getSpielerstatus()) {
                IClient tmpClient = readyListe.get(i);
                readyListe.remove(readyListe.get(i));
                spielerliste.add(tmpClient);
            }
        }
        
        int anzahlBereiterSpieler = 0;
        for (int i = 0; i < readyListe.size(); i++) {
            if (readyListe.get(i).getSpielerstatus()) {
                anzahlBereiterSpieler++;
            }
        }
        
        System.out.println("Anzahl bereiter Spieler: " + anzahlBereiterSpieler);
        anzahlBereiterSpieler = 0;
    }

    public void broadcastMessage(String message) throws RemoteException {
        //System.out.println(message);
        
        if (spielerliste != null) {
            for(int i = 0; i < spielerliste.size(); i++) {
            spielerliste.get(i).nachrichtEmpfangen(message);
            }
        }
        
        if (readyListe != null) {
            for(int i = 0; i < readyListe.size(); i++) {
            readyListe.get(i).nachrichtEmpfangen(message);
            }
        }
        
    }
    
    public ArrayList<Card> getKartendeck() {
        return this.kartendeck;
    }
    
    @Override
    public Card entferneKarteAusDeck(int index){ 
        return getKartendeck().remove(index);
    }
    
    @Override
    public int getTopCardID() {
        return topCard.getID();
    }
    
    public int anzahlReadyListeAusgeben() {
        return readyListe.size();
    }

    @Override
    public void spielerWechseln() throws RemoteException {
        for (int i = 0; i < readyListe.size(); i++) {
            // Prüfen, ob Spieler am Zug ist
            if (readyListe.get(i).getSpielerAmZug()) {
                if ( i == readyListe.size()-1) {
                    readyListe.get(i).setSpielerAmZugFalse();
                    readyListe.get(0).setSpielerAmZugTrue();
                    break;
                } else {
                    readyListe.get(i).setSpielerAmZugFalse();
                    readyListe.get(i+1).setSpielerAmZugTrue();
                    break;
                }
            }
        }
    }

    @Override
    public void setTopcard(Card karte) throws RemoteException {
        topCard = karte;
    }
    
    

    @Override
    public void spieleKarte(int selectedCardID) throws RemoteException {
        
        IClient aktiverSpieler = getAktivenSpieler();
        ArrayList<Card> tmpHand = aktiverSpieler.getHand();
        String tmpKarteWert = null;
        String tmpKarteFarbe = null; 
        
        
        // Aktion, falls 7 zu Spielbeginn liegt
        if (topCard.getWert().equals("Sieben") && zugCounter==1) {
            siebenerCounter = siebenerCounter+2;
            for (int i = 0; i < aktiverSpieler.getHand().size(); i++) {
                if (aktiverSpieler.getHand().get(i).getWert().equals("Sieben")) {
                    // Wenn 7 auf der Hand, sicherstellen, dass diese gespielt wird
                } else {
                    aktiverSpieler.karteZiehen(siebenerCounter);
                    aktiverSpieler.spielFensterAktualisieren(topCard.getID());
                    siebenerCounter = 0;
                }
            }
        }
        
        // wenn Karte, ein Bube ist, dann lege ohne weitere Prüfung ab oder wenn TopCard Bube und Runde 1 ist
        if ((selectedCardID == 4 || selectedCardID == 12 || selectedCardID == 20 || selectedCardID == 28) || (zugCounter == 1 && topCard.getWert().equals("Bube"))) {
            // Hand des aktiven Spielers durchgehen und Karte auf Stapel legen
            for (int i = 0; i < tmpHand.size(); i++) {
                
                tmpKarteFarbe = tmpHand.get(i).getFarbe();
                tmpKarteWert = tmpHand.get(i).getWert();
                
                if (tmpHand.get(i).getID() == selectedCardID) {
                    getKartendeck().add(topCard);
                    // Kartendeck wieder mischen
                    kartendeck = Card.kartendeckMischen(kartendeck);
                    setTopcard(tmpHand.get(i));
                    tmpHand.remove(tmpHand.get(i));
                    aktiverSpieler.setZiehenCounter(0);
                    spielerWechseln();
                    zugCounter++;
                    
                    broadcastMessage("\n" + tmpKarteFarbe + " - " + tmpKarteWert + " wurde gespielt.");
                    // Spielerfenster nach Zug aktualisieren
                    for (int j = 0; j < readyListe.size(); j++) {
                        readyListe.get(j).karteGrafischEntfernen(selectedCardID);
                        readyListe.get(j).spielFensterAktualisieren(selectedCardID);
                    }
                    break;
                }
            }
        }
        // Rest abdecken, entweder muss Wert oder Farbe gleich sein
        else if (selectedCardID != 4 || selectedCardID != 12 || selectedCardID != 20 || selectedCardID != 28  ) {
          
            for (int i = 0; i < tmpHand.size(); i++) {
                tmpKarteFarbe = tmpHand.get(i).getFarbe();
                tmpKarteWert = tmpHand.get(i).getWert();
                
                // Prüfen, dass die selektierte Karte gewählt ist (anhand von ID) und dann prüfen ob Farbe oder Wert der TopCard gleich sind
                if ( selectedCardID == tmpHand.get(i).getID() && (topCard.getFarbe().equals(tmpKarteFarbe) || topCard.getWert().equals(tmpKarteWert))) {
                    getKartendeck().add(topCard);
                    // Kartendeck wieder mischen
                    kartendeck = Card.kartendeckMischen(kartendeck);
                    setTopcard(tmpHand.get(i));
                    // Falls Karte keine 8 ist, dann Spieler wechseln, ansonsten nicht!
                    if (!tmpHand.get(i).getWert().equals("Acht")) {
                        aktiverSpieler.setZiehenCounter(0);
                        spielerWechseln();
                    } else {
                        //aktiverSpieler.individuellesPopupZeigen("Du hast eine Acht abgelegt! Du darfst nochmal!");
                    }
                    // Karte aus Hand entfernen
                    tmpHand.remove(tmpHand.get(i));
                    // ZugCounter erhöhen
                    zugCounter++;
                    
                    broadcastMessage("\n" + tmpKarteFarbe + " - " + tmpKarteWert + " wurde gespielt.");
                    // Spielerfenster nach Zug aktualisieren
                    for (int j = 0; j < readyListe.size(); j++) {
                        readyListe.get(j).karteGrafischEntfernen(selectedCardID);
                        readyListe.get(j).spielFensterAktualisieren(selectedCardID);
                    }
                    break;
                } 
                else if (i == tmpHand.size()-1) {
                    aktiverSpieler.nachrichtEmpfangen("\n" +"Katzenmeister My Auz: Diese Karte darf nicht gespielt werden! Versuch eine andere!");
                }
                
                //Überprüfen, ob nur noch eine Hand vorhanden
                if (aktiverSpieler.getHand().size() == 1) {
                    broadcastMessage(aktiverSpieler.getBenutzername() + " hat nur noch eine Karte auf der Hand! Miau! Jetzt aber Vollgas!");
                }
            } 
        }
    }

    @Override
    public String getTopCardWert() throws RemoteException {
        return topCard.getWert();
    }

    @Override
    public String getTopCardFarbe() throws RemoteException {
       return topCard.getFarbe();
    }

    
    // aktiven Spieler zurückgeben
    private IClient getAktivenSpieler() throws RemoteException {
        IClient aktivenSpieler = null;
        for (int i = 0; i < readyListe.size(); i++) {
            if (readyListe.get(i).getSpielerAmZug()) {
                aktivenSpieler = readyListe.get(i);
                break;
            } else {
                aktivenSpieler = null;
            }
        }
        return aktivenSpieler;
    }    
}
