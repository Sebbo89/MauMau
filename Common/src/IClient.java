
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
public interface IClient extends Remote {
    public void handNehmen(int anzahlKarten) throws RemoteException;
    public void handAusgeben() throws RemoteException;
    public String getBenutzername() throws RemoteException;
    public boolean getSpielerstatus() throws RemoteException;
    public void nachrichtEmpfangen(String message) throws RemoteException;
    public void bereitMelden() throws RemoteException;
    public boolean getSpielerAmZug() throws RemoteException;
    public void setSpielerAmZugTrue() throws RemoteException;
    public void setSpielerAmZugFalse() throws RemoteException;
    public void clientFensterAusblenden() throws RemoteException;
    public void spielFensterOeffnen() throws RemoteException;
    public void setBenutzername(String text) throws RemoteException;
    public ArrayList<Card> getHand() throws RemoteException;
    public void spielFensterAktualisieren(int selectedCardID) throws RemoteException;
    public void karteGrafischEntfernen(int selectedCardID) throws RemoteException;
    public String getRegistryKey() throws RemoteException;
    public void individuellesPopupZeigen(String message) throws RemoteException;
    public void karteZiehen(int anzahl) throws RemoteException;
    public int getZiehenCounter() throws RemoteException;
    public void setZiehenCounter(int zustand) throws RemoteException;
}
