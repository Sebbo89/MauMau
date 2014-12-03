
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sebbo
 */
public class ServerImpl implements IServer {
    public static ArrayList<IClient> spielerliste = new ArrayList();
    private ArrayList benutzernamen = new ArrayList();
    private ServerFenster serverfenster;
    private int anzahlKarten = 7;
    
    /*
    public ServerImpl(ServerFenster serverFenster) {
        this.serverfenster = serverFenster;
    }
    */
    
    public ServerImpl() {
        
    }
    
    @Override
    public void clientAnmelden(String benutzername, IClient client) throws RemoteException{

        System.out.println(benutzername + " beim Spiel angemeldet!");
        benutzernamen.add(benutzername);
        for (int i = 0; i < benutzernamen.size(); i++ ) {
            System.out.println(benutzernamen.get(i).toString());
        }
        spielerliste.add(client);
        //serverfenster.jPanelHinzufuegen(benutzername);
    } 
    
    @Override
    public void spielStarten(ArrayList<IClient> spielerliste) throws RemoteException {
        ArrayList<Card> kartendeck = Card.kartendeckErzeugen();
        for ( int i = 0; i < spielerliste.size(); i++  ) {
            spielerliste.get(i).handNehmen(kartendeck, anzahlKarten);
            spielerliste.get(i).handAusgeben();
        }    
    }
    
    @Override
    public void spielerlisteAnzahlAusgeben() throws RemoteException {
        System.out.println(this.spielerliste.size());
    }
}
