
import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import javax.swing.JLabel;

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
    
    @Override
    public void spielStarten(ArrayList<IClient> spielerliste) throws RemoteException {
        ArrayList<Card> kartendeck = Card.kartendeckErzeugen();
        for ( int i = 0; i < spielerliste.size(); i++  ) {
            spielerliste.get(i).handNehmen(kartendeck, anzahlKarten);
            spielerliste.get(i).handAusgeben();
        }    
    }
    
    @Override
    public void spielerlisteAnzahlAusgeben() throws RemoteException {
        System.out.println(this.spielerliste.size());
    }
    
    @Override
    public ArrayList<IClient> spielerlisteAusgeben() throws RemoteException {
        return this.spielerliste;
    }

    @Override
    public void readyListeChecken() throws RemoteException {
        // spielerliste durchgehen und Spieler, die bereit sind, in ReadyListe verschieben
        for (int i = 0; i < spielerliste.size(); i++) {
            if (spielerliste.get(i).getSpielerstatus()) {
                IClient tmpClient = spielerliste.get(i);
                spielerliste.remove(spielerliste.get(i));
                readyListe.add(tmpClient);
            }
        }
        
        int anzahlBereiterSpieler = 0;
        for (int i = 0; i < readyListe.size(); i++) {
            if (readyListe.get(i).getSpielerstatus()) {
                anzahlBereiterSpieler++;
            }
        }
        
        if (anzahlBereiterSpieler < 2) {
            System.out.println("Anzahl bereiter Spieler: " + anzahlBereiterSpieler);
        } else {
            System.out.println("Anzahl bereiter Spieler: " + anzahlBereiterSpieler);
            System.out.println("Spiel wird gestartet.");
            spielStarten(this.spielerliste);
        }
        
        anzahlBereiterSpieler = 0;
    }
    
    
}
