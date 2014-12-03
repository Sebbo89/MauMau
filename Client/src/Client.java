
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
  
  
  public static void main( String[] args ) throws  RemoteException, NotBoundException, Exception{
    
    
    Registry registry = LocateRegistry.getRegistry();
    IServer server = (IServer) registry.lookup( "Server" );
    
    // Hier kann der Benutzername eingegeben werden
    String benutzername = "Sebbo";
    
    ClientImpl clientImpl = null;
    clientImpl = new ClientImpl(server, client, benutzername);
  }

    public Client() {
        this.client = (IClient) new Client();
    }
}
