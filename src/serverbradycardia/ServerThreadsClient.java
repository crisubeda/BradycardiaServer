/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverbradycardia;

import Pojos.Patient;
import Utilities.ConnectionClient;
import db.sql.*;
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
    public static SQLManager sqlManager;

    public ServerThreadsClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        BufferedReader bufferedReader;
        //Utilities.ConnectionClient.initialiceAll(dbManager, patientManager, patient);
        patient = new Patient();
        System.out.println("Estoy antes del connect");
        sqlManager.connect();
        patientManager = dbManager.getPatientManager();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            boolean stop = true;
            while (true) {
                while (bufferedReader.readLine() != null) {
                    String line = bufferedReader.readLine();
                    String uno = Character.toString(line.charAt(0));
                    String dos = Character.toString(line.charAt(1));
                    String head = uno.concat(dos);
                    if (head.equals("p#")) {
                        //Como lo que llega es un paciente, vamos a coger toda su información de la base 
                        //de datos
                        patient = ConnectionClient.getData(line, patient, patientManager);
                        System.out.println("Ya he cogido toda la información del patient");
                        if (patient.getFullName().equals("")) {
                            sendPatient(patient);
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

        String patientToClient = patient.toString();
        try {

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(patientToClient);

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
