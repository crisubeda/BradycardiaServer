/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverbradycardia;

import Pojos.Patient;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author drijc
 */
public class ServerThreadsClient implements Runnable {

    int byteRead;
    public static Socket socket;

    public ServerThreadsClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        boolean stop = true;
        try {
            while (stop) {
                inputStream = socket.getInputStream();
                stop = Utilities.ConnectionClient.getData(inputStream);
            }
            /*while ((byteRead = inputStream.read()) != -1) {
                char caracter = (char) byteRead;
                System.out.print(caracter);
            }*/
        } catch (IOException ex) {
            Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*finally {
            releaseResourcesClient(inputStream, socket);
        }*/

    }

    public static void sendPatient(Patient patient) {
        int i = 0;
        OutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(patient);
            System.out.println("Se ha mandado ya!");
            outputStream.flush();
            objectOutputStream.flush();
        } catch (IOException ex) {
            System.out.println("ERROR");
        }
    }

    private static void releaseResourcesClient(InputStream inputStream, Socket socket) {
        try {
            inputStream.close();

        } catch (IOException ex) {
            Logger.getLogger(ServerThreads.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        try {
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(ServerThreads.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
