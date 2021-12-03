/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverbradycardia;

import Pojos.Patient;
import Utilities.ConnectionClient;
import db.interfaces.DBManager;
import db.interfaces.PatientManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author drijc
 */
public class ServerThreadsClient implements Runnable {

    int byteRead;
    public static Socket socket;
    public static PatientManager patientManager;
    public static Patient patient;
    public static DBManager dbManager;

    public ServerThreadsClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        BufferedReader bufferedReader;
        Utilities.ConnectionClient.initialiceAll(dbManager, patientManager, patient);
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            boolean stop = true;
            while (true) {
                while (bufferedReader.readLine() != null) {
                    String introd = bufferedReader.readLine();
                    System.out.println("lo que llega: " + introd);
                    String uno = Character.toString(introd.charAt(0));
                    String dos = Character.toString(introd.charAt(1));
                    String head = uno.concat(dos);
                    System.out.println("head: " + head);
                    if (head.equals("p#")) {
                        System.out.println("si es un patient lo que ha llegado: " + introd.charAt(0));
                        Patient p = ConnectionClient.getData(introd, patient, patientManager);
                        if (p.getFullName().equals("")) {
                            sendPatient(p);
                        } else {
                            System.out.println("No se ha encontrado");
                        }
                    }
                }

//stop = Utilities.ConnectionClient.getData(introd);
            }
        } catch (IOException ex) {
            System.out.println("Error en run de serverThreadsClient");
            // Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*while ((byteRead = inputStream.read()) != -1) {
                char caracter = (char) byteRead;
                System.out.print(caracter);
            }*/

 /*finally {
            releaseResourcesClient(inputStream, socket);
        }*/
    }

    public void sendPatient(Patient patient) {

        String mes = patient.toString();
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(mes);

            /*int i = 0;
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
            }*/
        } catch (IOException ex) {
            System.out.println("Error en sendP de serverThreadsClient");
            // Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*  private static void releaseResourcesClient(InputStream inputStream, Socket socket) {
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
    }*/
}
