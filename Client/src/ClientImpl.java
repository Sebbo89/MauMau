
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
public class ClientImpl implements IClient {
    
    private static final long serialVersionUID = 1L;
    //private ArrayList<Card> cards;
    private IServer server;
    private IClient client;
    private String benutzername;
    private ArrayList<Card> hand;
    
	
	public ClientImpl(IServer server, IClient client, String benutzername) throws Exception{
		this.benutzername = benutzername;
                this.server = server;
		this.client = client;
                this.server.clientAnmelden(benutzername, client);
	}
        
        @Override
        public void handNehmen(ArrayList<Card> kartendeck, int anzahlKarten) {
            for (int i = 0; i < anzahlKarten; i++) {
                this.hand.add(kartendeck.get(0));
                kartendeck.remove(kartendeck.get(0));
            }
        }
        
        @Override
        public void handAusgeben() {
            for (Card karte : hand) {
                System.out.println(karte.getFarbe() + " - " + karte.getWert() + " - " + karte.getID());
            }
        }
        
    @Override
        public String getBenutzername() {
            return this.benutzername;
        }
}
