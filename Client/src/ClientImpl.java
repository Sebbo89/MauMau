
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;
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
    private IClient client;
    private ClientFenster clientFenster;
    private String benutzername;
    private ArrayList<Card> hand;
    private boolean spielerBereit = false;
    private boolean spielerAmZug = false;
    
    private static int clientIndex = 0;
	public ClientImpl(IServer server, String benutzername, ClientFenster clientFenster) throws Exception{
                this.clientFenster = clientFenster;
                this.hand = new ArrayList();
		this.benutzername = benutzername;
                this.server = server;
                this.server.clientAnmelden(this.benutzername, registerClient());
	}       
        
        public void handNehmen(ArrayList<Card> kartendeck, int anzahlKarten) {
            for (int i = 0; i < anzahlKarten; i++) {
                this.hand.add(kartendeck.get(0));
                kartendeck.remove(kartendeck.get(0));
            }
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
        
        private int getNumberOfPlayers(){
            String keyNumOfPlayers = "playerIndex";
            //Registry=> playerIndex:int
            
            return -1;
        }
        private void setNumberOfPlayers(){
           //Registry <= playerIndex:int 
        }
        @Override
        public void handAusgeben() throws RemoteException{
            for (Card karte : hand) {
                System.out.println(karte.getFarbe() + " - " + karte.getWert() + " - " + karte.getID());
            }
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
            this.server.readyListeChecken();
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

    public IServer getServer() {
        return this.server;
    }
}

