
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
        System.out.println(readyListe.get(spielerAmZugIndex).getBenutzername());
        
        
        // Chatfenster bei Clients ausblenden und Spielfenster öffnen
        for (int i = 0; i < readyListe.size(); i++) {
            readyListe.get(i).clientFensterAusblenden();
            readyliste.get(i).spielFensterOeffnen();
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
        System.out.println(message);
        for(int i = 0; i<spielerliste.size(); i++) {
            spielerliste.get(i).nachrichtEmpfangen(message);
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
    public void spielerUeberspringen() {
        for (int i = 0;  i<readyListe.size(); i++) {
            
            try {
                // Spieler am Zug finden und prüfen, dass er nicht an letzter Stelle in der ReadyListe steht
                if (readyListe.get(i).getSpielerAmZug() && (i != (readyListe.size()-2) ) ) {
                    // Aktiven Spieler am Zug auf false setzen
                    readyListe.get(i).setSpielerAmZugFalse();
                    // Nächsten Spieler am Zug auf true setzen
                    readyListe.get(i+1).setSpielerAmZugTrue();
                }
                
                // Spieler am Zug und an letzter Stelle in der ReadyListe finden
                else if (readyListe.get(i).getSpielerAmZug() && (i == (readyListe.size()-2) ) ) {
                    // Aktiven Spieler am Zug auf false setzen
                    readyListe.get(i).setSpielerAmZugFalse();
                    // Ersten Spieler der ReadyListe am Zug auf true setzen
                    readyListe.get(0).setSpielerAmZugTrue();
                }
                // Ausgabe im Spielerfenster, dass Spieler nicht am Zug
                else {
                    System.out.println("Du bist nicht am Zug, Junge!");
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void setTopcard(Card karte) throws RemoteException {
        topCard = karte;
    }

    @Override
    public void spieleKarte(int selectedCardID) throws RemoteException {
        System.out.println(selectedCardID);
    }
    
}
