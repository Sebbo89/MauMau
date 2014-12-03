
import java.rmi.Remote;
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
    public void handNehmen(ArrayList<Card> kartendeck, int anzahlKarten);
    public void handAusgeben();
    public String getBenutzername();
}
