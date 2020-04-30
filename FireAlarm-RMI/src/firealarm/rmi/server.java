/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firealarm.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;

/**
 *
 * @author Sasika
 */
public class server extends UnicastRemoteObject implements Sensor
{
 public server() throws RemoteException 
 {
     super();
 }   

public int add(int a, int b) throws RemoteException {
    System.out.println("Adding " + a + " and " + b + " in the Server");
    return a + b;
}

public static void main(String[] args) throws RemoteException{
//    try{
//        Registry reg = LocateRegistry.createRegistry(9999);
//        reg.rebind("hi server", new server());
//        System.out.println("Server is ready");
//
//    }
//    catch(RemoteException e)
//    {
//                System.out.println("exception"+e);
//    }
    try{

            server svr = new server();
		 // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("CalculatorService", svr);

           
            System.out.println ("Service started....");
        }
        catch(RemoteException re){
            System.err.println(re.getMessage());
        }
        catch(AlreadyBoundException abe){
            System.err.println(abe.getMessage());
        }
}
}
