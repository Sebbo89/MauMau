
import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
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
    public void spielStarten(ArrayList<IClient> readyliste) throws RemoteException {
        kartendeck = Card.kartendeckErzeugen();
        this.kartenAnSpielerVerteilen(readyliste, kartendeck);
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
    public void kartenAnSpielerVerteilen(ArrayList<IClient> readyliste, ArrayList<Card> kartendeck) throws RemoteException {
        for (int i = 0; i < readyliste.size(); i++) {
            readyliste.get(i).handNehmen(kartendeck, anzahlKarten);
            readyliste.get(i).handAusgeben();
            System.out.println("Hand ausgeteilt.");
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
        for(int i =0; i<spielerliste.size(); i++) {
            spielerliste.get(i).nachrichtEmpfangen(message);
        }
    }
    
    
}
