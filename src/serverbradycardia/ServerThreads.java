/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverbradycardia;

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

    public static void main(String args[]) throws IOException {
        //Create a service that is waiting in port 9000
        Utilities.ConnectionClient.initialiceAll();
        String[] datos = Utilities.ConnectionClient.getDataFromFile();
        int ip = Utilities.Exceptions.convertInt(datos[1]);
        ServerSocket serverSocket = new ServerSocket(ip);
        try {
            while (true) {
                //Thie executes when we have a client
                Socket socket = serverSocket.accept();
                //Cada vez que recibo una conexion creo un nuevo thread con el scoket
                new Thread(new ServerThreadsClient(socket)).start();
            }
        } finally {
            releaseResourcesServer(serverSocket);
        }
    }

    private static void releaseResourcesServer(ServerSocket serverSocket) {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerThreads.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
