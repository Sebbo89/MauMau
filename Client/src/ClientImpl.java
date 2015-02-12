
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
        private String registerClient() throws RemoteException{
            
            //Search in all registry keys
            //last clientXX +1
            
            String key = "Client" + clientIndex++;
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
    
    
    public void nachrichtEmpfangen(String message) throws RemoteException {
        // Code
        clientFenster.nachrichtInTextAreaEinfuegen(message);
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
        SpielFenster spielFenster = new SpielFenster(this.client, this.server);
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

}

