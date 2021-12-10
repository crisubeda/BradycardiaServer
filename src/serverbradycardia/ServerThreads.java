/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverbradycardia;

import interf.Window;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author drijc
 */
public class ServerThreads {
    //como en este caso hay varios threads se va a escribir lo que llegue en el momento
    //ahora no espero a que se termine uno

    public static Socket socket;
    public static ServerSocket serverSocket;
    public static ServerThreadsClient serverTC;
    public static Thread[] listThreads;
    public static void main(String[] args) throws IOException {
        Window.main(args);
       //Create a service that is waiting in port 9000
        String[] datos = Utilities.ConnectionClient.getDataFromFile();
        int ip = Utilities.Exceptions.convertInt(datos[1]);
        serverSocket = new ServerSocket(ip);
        listThreads= new Thread[100];
        int i=0;
        try {
            while (true) {
                if(Window.contin){
                    //This executes when we have a client
                    socket = serverSocket.accept();
                    serverTC = new ServerThreadsClient(socket);
                    //Cada vez que recibo una conexion creo un nuevo thread con el scoket
                    Thread a=new Thread(serverTC);
                    a.start();
                    listThreads[i]=a;
                    i++;
                }
                else{
                    releaseResourcesServer(serverSocket);
                }
            }
        } finally {
            //releaseResourcesServer(serverSocket);
        }
    }

    public static void releaseResourcesServer(ServerSocket serverSocket) {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerThreads.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}
