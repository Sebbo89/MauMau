
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

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
    public void readyListeChecken() throws RemoteException;
    public void broadcastMessage(String nextLine) throws RemoteException;
}
