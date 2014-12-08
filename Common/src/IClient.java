
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
public interface IClient extends Remote {
    public void handNehmen(ArrayList<Card> kartendeck, int anzahlKarten) throws RemoteException;
    public void handAusgeben() throws RemoteException;
    public String getBenutzername() throws RemoteException;
    public boolean getSpielerstatus() throws RemoteException;
}
