
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sebbo
 */

public class Client {
  private static IClient client;
  
  static int playerIndex = 0;
  public static void main( String[] args ) throws  RemoteException, NotBoundException, Exception{
    
    
    Registry registry = LocateRegistry.getRegistry();
    IServer server = (IServer) registry.lookup( "Server" );
    
    // Hier kann der Benutzername eingegeben werden
    /*String benutzername = ""; 
    switch(playerIndex++){
        case 0: benutzername = "Sebbo"; break;
        case 1: benutzername = "Paul"; break;
        case 2: benutzername = "Peter"; break;
        case 3: benutzername = "Daiana"; break;
    }*/
    
    
    ClientImpl clientImpl1 = new ClientImpl(server, "Sebbo");
    ClientImpl clientImpl2 = new ClientImpl(server, "Tim");
    ClientImpl clientImpl3 = new ClientImpl(server, "Timä");
    ClientImpl clientImpl4 = new ClientImpl(server, "Tom");
   
  }

    /*public Client() {
        this.client = (IClient) new Client();
    }*/
}
