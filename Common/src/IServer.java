
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
    
    public void clientAnmelden(String benutzername, IClient client) throws RemoteException;
    public void spielStarten(ArrayList<IClient> spielerliste) throws RemoteException;
}
