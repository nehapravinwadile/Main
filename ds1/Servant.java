import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
public class Servant extends UnicastRemoteObject implements ServerInterface {
 protected Servant() throws RemoteException {
 super();
 }
 @Override
 public String concat(String a, String b) throws RemoteException {
    return a + b;
 }
}
//sudo apt update 
//sudo apt install openjdk-8-jdk

//javac *.java will compile all files 
//

//run servant,server,client 

//cd ds1 
//rmic Servant 
//rmiregistry
//java server 
//java client


