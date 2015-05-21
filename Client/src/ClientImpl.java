
import com.sun.java.accessibility.util.TopLevelWindowMulticaster;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
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
public class ClientImpl implements IClient, Serializable {
    
    private static final long serialVersionUID = 1L;
    //private ArrayList<Card> cards;
    private IServer server;
    private ArrayList<Card> hand;
    private IClient client;
    private ClientFenster clientFenster;
    private String benutzername;
    private boolean spielerBereit = false;
    private boolean spielerAmZug = false;
    private SpielFenster spielFenster = null;
    private boolean spielGewonnen = false;
    private String registryKey;
    private int ziehenCounter = 0;
    
    private static int clientIndex = 0;
	public ClientImpl(IServer server, String benutzername, ClientFenster clientFenster) throws Exception{
                this.clientFenster = clientFenster;
		this.benutzername = benutzername;
                this.server = server;
                this.server.clientAnmelden(this.benutzername, registerClient());
                
                // Nicknamefenster erstellen, um Nickname einzugeben
                
                NicknameFenster nicknameFenster = new NicknameFenster(this.client);
                nicknameFenster.setVisible(true);
                nicknameFenster.setAlwaysOnTop(true);
	}       
        
        int playerIndex;
        
        private void refreshRegistry() throws RemoteException{
            
            //Search in all registry keys
            //last clientXX +1
            
            
            client = (IClient) UnicastRemoteObject.exportObject( this, 0 );
            Registry registry = LocateRegistry.getRegistry();
            //int index = 0;
            
            registry.rebind(registryKey, client );
            
            
            
            
        }
        
        private String registerClient() throws RemoteException{
            
            //Search in all registry keys
            //last clientXX +1
            
            String key = "Client" + clientIndex++;
            this.registryKey = key;
            client = (IClient) UnicastRemoteObject.exportObject( this, 0 );
            Registry registry = LocateRegistry.getRegistry();
            int index = 0;
            
            registry.rebind(key, client );
            
            
            
            return key;
        }
        
        public IServer getServer() throws RemoteException {
            return this.server;
        }
        
    @Override
        public String getBenutzername() throws RemoteException{
            return this.benutzername;
        }
        
    @Override
        public boolean getSpielerstatus() throws RemoteException{
            return this.spielerBereit;
        }
        
    @Override
        public void bereitMelden() throws RemoteException{
            this.spielerBereit = true;
            this.server.listenChecken();
        }
        
    @Override
        public boolean getSpielerAmZug() throws RemoteException{
            return this.spielerAmZug;
        }

    @Override
    public void setSpielerAmZugTrue() throws RemoteException {
        this.spielerAmZug = true;
    }

    @Override
    public void setSpielerAmZugFalse() throws RemoteException {
        this.spielerAmZug = false;
    }
    
    public void setSpielerBereitTrue() throws RemoteException {
        this.spielerBereit = true;
    }
    
    public void setSpielerBereitFalse() throws RemoteException {
        this.spielerBereit = false;
    }
    
    /*
    Methode, die wartet, vom Server ausgelöst zu werden und dann eine Nachricht übermittelt und zurückgibt.
    */

    /**
     *
     * @param message
     * @throws RemoteException
     */
    public void individuellesPopupZeigen(String message) throws RemoteException {
        spielFenster.popupZeigen(message);
    }
    
    public void nachrichtEmpfangen(String message) throws RemoteException {
        // Code
        clientFenster.nachrichtInTextAreaEinfuegen(message);
        if (spielFenster != null) {
            spielFenster.nachrichtInTextAreaEinfuegen(message);
        }
    }

    public void handNehmen(int anzahlKarten) throws RemoteException {
        hand = new ArrayList();
        for (int i = 0; i < anzahlKarten; i++) {
            hand.add(getServer().getKartendeck().get(0));
            // Aufruf der Methode, um Karte aus Deck zu löschen
            this.getServer().entferneKarteAusDeck(0);
        }
    }

    @Override
    public void handAusgeben() throws RemoteException {
        for (int i = 0; i < hand.size(); i++) {
            System.out.println(hand.get(i).getFarbe() + " - " + hand.get(i).getWert() + " " + hand.get(i).getID());
        }
    }

    public void clientFensterAusblenden() throws RemoteException {
        this.clientFenster.setVisible(false);
    }
    
    public void spielFensterOeffnen() throws RemoteException {
        spielFenster = new SpielFenster(this.client, this.server);
        spielFenster.setVisible(true);
        spielFenster.setExtendedState(spielFenster.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

    @Override
    public void setBenutzername(String text) {
        this.benutzername = text;
    }

    @Override
    public ArrayList<Card> getHand() throws RemoteException {
        return this.hand;
    }
    
    public void karteGrafischEntfernen(int selectedCardID) throws RemoteException {
        // ID in String umwandeln
        String tmpString= Integer.toString(selectedCardID);
        
        for (int i = 0; i < this.spielFenster.JLabelListe.size(); i++) {
            
            // Überprüfen, ob String auf der Hand vorkommt und dann die Karte aus der ArrayList entfernen
            if (this.spielFenster.JLabelListe.get(i).getName().equals(tmpString)) {
                this.spielFenster.JLabelListe.remove(this.spielFenster.JLabelListe.get(i));
                
                
                for (int j=0; j < this.getHand().size(); j++) {
                    String tmpId = this.getHand().get(j).getID() + "";
                    if (tmpString.equals(tmpId)) {
                        this.getHand().remove(j);
                        //ArrayList<Card> tmpHand = client.getHand();
                        //this.geth
                        //Object t = null;
                    }
                }
            }
        }
        //refreshRegistry();
        ArrayList<Card> tmpHand = client.getHand();
    }
    
    public void spielFensterAktualisieren(int selectedCardID) throws RemoteException {
        this.spielFenster.jPanelLoeschen();
        this.spielFenster.topCardIconAendern(selectedCardID);
        this.spielFenster.spielerhandZeichnen();
        this.spielFenster.revalidate();   
    }
    
    public String getRegistryKey() throws RemoteException {
        return registryKey;
    }

    @Override
    public void karteZiehen(int anzahl) throws RemoteException {
        ArrayList<Card> tmpKartendeck = server.getKartendeck();
        for (int i = 0; i < anzahl; i++) {
            // Letzte Karte vom Kartendeck zwischenspeichern und der Hand hinzufügen
            Card tmpCard = tmpKartendeck.get(tmpKartendeck.size()-1);
            this.getHand().add(tmpCard);
            // Karte aus Deck entfernen
            tmpKartendeck.remove(tmpKartendeck.get(tmpKartendeck.size()-1));
            // Kartendeck mischen
            Card.kartendeckMischen(tmpKartendeck);
        }
    }

    @Override
    public int getZiehenCounter() throws RemoteException {
        return ziehenCounter;
    }

    @Override
    public void setZiehenCounter(int zustand) throws RemoteException {
        this.ziehenCounter = zustand;
    }

    @Override
    public void siebenerAbfragen() throws RemoteException {
        boolean siebenerAufHand = false;
        for (int i = 0; i < this.getHand().size(); i++) {
            if (this.getHand().get(i).getWert().equals("Sieben")) {
                siebenerAufHand = true;
                break;
            }
        }
        // wenn kein Siebener auf der Hand, Karten ziehen
        if (!siebenerAufHand) {
            this.karteZiehen(server.getSiebenerCounter());
            this.server.setSiebenerCounter(0);
            this.spielFensterAktualisieren(server.getTopCardID());
        } else {
            this.spielFenster.siebenerAbfragen();
        }
    }

}

