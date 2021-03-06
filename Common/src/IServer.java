
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
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
public interface IServer extends Remote {
    /**
     *
     * @param benutzername
     * @param client
     * @throws RemoteException
     */
    
    public void clientAnmelden(String benutzername, String clientIndex) throws RemoteException, NotBoundException;
    
    /**
     * 
     * @param spielerliste
     * @throws RemoteException 
     */
    
    public void spielStarten(ArrayList<IClient> spielerliste) throws RemoteException;
    public void spielerlisteAnzahlAusgeben() throws RemoteException;
    public ArrayList<IClient> spielerlisteAusgeben() throws RemoteException;
    public void listenChecken() throws RemoteException;
    public ArrayList<Card> getKartendeck() throws RemoteException;
    public Card entferneKarteAusDeck(int index) throws RemoteException;
    public void broadcastMessage(String message) throws RemoteException;
    public int getTopCardID() throws RemoteException;
    public String getTopCardWert() throws RemoteException;
    public String getTopCardFarbe() throws RemoteException;
    public int anzahlReadyListeAusgeben() throws RemoteException;
    public void spielerWechseln() throws RemoteException;
    public void setTopcard(Card karte) throws RemoteException;
    public void spieleKarte(int selectedCardID) throws RemoteException;
    public int getSiebenerCounter() throws RemoteException;
    public IClient getNaechstenSpieler() throws RemoteException;
    public void setSiebenerCounter(int i) throws RemoteException;
    public void miauMiauPruefen(IClient client) throws RemoteException;
    public void skipNaechstenSpieler() throws RemoteException;
    public void alleSpielerFensterAktualisieren(int kartenid) throws RemoteException;
    public void setTopcardFarbe(String farbe) throws RemoteException;
    public void setTopcardID(int i) throws RemoteException;
    public void swapTopcardWithDummy() throws RemoteException;
    public String getSpielerKey() throws RemoteException;

}
