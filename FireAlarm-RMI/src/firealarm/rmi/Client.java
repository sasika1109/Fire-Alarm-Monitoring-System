/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firealarm.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import java.net.MalformedURLException;
import java.rmi.Naming;

/**
 *
 * @author Sasika
 */
public class Client 
{
 public static void main(String[] args) throws RemoteException 
 {
// {
//     Client c = new Client();
//     c.connectRemote();
// }
// private void connectRemote() throws RemoteException
// {
//     try
//     {
//         Scanner sc = new Scanner(System.in);
//         Registry reg = LocateRegistry.getRegistry("localhost",9999);
//         Sensor s =(Sensor)reg.lookup("hi server");
//         System.out.println("enter two number");
//         int a = sc.nextInt();
//         int b = sc.nextInt();
//         System.out.println("Addition is "+s.add(a,b));    
//     }
//     catch(NotBoundException e)
//     {
//         System.err.println("exception"+e);
//     }
     
       System.setProperty("java.security.policy", "file:allowall.policy");

        Sensor sensor = null;
        try {
            sensor = (Sensor) Naming.lookup("//localhost/CalculatorService");


            System.out.println  ("Add : " + sensor.add(2,2));
 
        } catch (NotBoundException ex) {
            System.err.println(ex.getMessage());
        } catch (MalformedURLException ex) {
            System.err.println(ex.getMessage());
        } catch (RemoteException ex) {
            System.err.println(ex.getMessage());
        }
    }
 }

