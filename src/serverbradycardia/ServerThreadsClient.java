/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverbradycardia;

import Pojos.Doctor;
import Pojos.Patient;
import Utilities.ConnectionClient;
import Utilities.PatientUtilities;
import db.interfaces.DBManager;
import db.interfaces.DoctorManager;
import db.interfaces.PatientManager;
import db.sql.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
    public static PatientManager patientManager;
    public static DoctorManager doctorManager;
    public static Patient patient;
    public static DBManager dbManager;
    public static SQLManager sqlManager;
    public static Doctor doctor;
    //public static PrintWriter printWriter;

    public ServerThreadsClient(Socket socket) {
        this.socket = socket;
        /*try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            System.out.println("Error, no se ha podido enviar datos");
            //Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    @Override
    public void run() {

        BufferedReader bufferedReader;
        //Utilities.ConnectionClient.initialiceAll(dbManager, patientManager, patient);
        patient = new Patient();
        doctor = new Doctor();
        /*dbManager = new SQLManager();
        dbManager.connect();
        doctorManager= dbManager.getDoctorManager();
        patientManager = dbManager.getPatientManager();*/
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            boolean stop = true;
            while (true) {
                while (bufferedReader.readLine() != null) {
                    String line = bufferedReader.readLine();
                    if (line.equals("patient-login")) {
                        System.out.println("Vamos a login");
                        line = bufferedReader.readLine();
                        String uno = Character.toString(line.charAt(0));
                        String dos = Character.toString(line.charAt(1));
                        String head = uno.concat(dos);
                        if (head.equals("p#")) {
                            //Como lo que llega es un paciente, vamos a coger toda su información de la base
                            //de datos
                            patient = ConnectionClient.getData(line, patient, patientManager);
                            System.out.println("Ya he cogido toda la información del patient");
                            // if (patient.getFullName().equals(patient)) {
                            sendPatient(patient);
                            System.out.println("Patient toString: " + patient.toString());
                            line = bufferedReader.readLine();
                            System.out.println("linea leida: " + line);
                            if (line.equals("again")) {
                                System.out.println("Se entra en again");
                                line = bufferedReader.readLine();
                                // patient = ConnectionClient.getData(line, patient, patientManager);
                                patient = new Patient(1, "Cristina", "CrisMola", "Calle baloncesto", "68970896979", "c@usp.ceu.es", "nada super sana", 2, "98:D3:91:FD:69:49");
                                sendPatient(patient);
                                System.out.println("patient send: " + patient.toString());
                            } else if (line.equals("done")) {
                                //lo siguiente que haya que hacer si se ha login
                            }
                        }
                    } else if (line.equals("patient-register")) {
                        System.out.println("Vamos a register new patient");
                        line = bufferedReader.readLine();
                        boolean done = PatientUtilities.regiterNewPatient(line, patient, patientManager);
                        if (done) {
                            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                            printWriter.println("done");
                        } else {
                            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                            printWriter.println("notpossible");
                        }
                    } //}
                    /*else {
                            System.out.println("No se ha encontrado");
                        }*/ else {
                        String uno = Character.toString(line.charAt(0));
                        String dos = Character.toString(line.charAt(1));
                        String head = uno.concat(dos);
                        if (head.equals("d#")) {
                            //Como lo que llega es un paciente, vamos a coger toda su información de la base
                            //de datos
                            doctor = ConnectionClient.getDataDoctor(line, doctor, doctorManager);
                            if (doctor == null) {
                                System.out.println("El doctor es null");
                                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                                printWriter.println("error");
                            } else {
                                System.out.println("Doctor toString: " + doctor.toString());
                                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                                printWriter.println(doctor.toString());
                            }
                        }
                    }
                }
                //stop = Utilities.ConnectionClient.getData(introd);
            }
        } catch (IOException ex) {
            // System.out.println("Error en run de serverThreadsClient");
            Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
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
            System.out.println("Error en send de serverThreadsClient");
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
