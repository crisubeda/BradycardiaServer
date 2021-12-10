/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverbradycardia;

import Pojos.*;
import Utilities.*;
import db.interfaces.*;
import db.sql.*;
import java.io.*;
import java.net.Socket;
import java.time.*;
import java.time.format.DateTimeFormatter;
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
    public static FilesManager filesManager;
    public static Patient patient;
    public static DBManager dbManager;
    public static SQLManager sqlManager;
    public static Doctor doctor;
    public static File file;

    public ServerThreadsClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader;
        patient = new Patient();
        doctor = new Doctor();
        dbManager = new SQLManager();
        dbManager.connect();
        doctorManager = dbManager.getDoctorManager();
        patientManager = dbManager.getPatientManager();
        filesManager = dbManager.getFilesManager();
        file= new File("files/.");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            boolean stop = true;
            boolean exit = false;

            while (!exit) {
                String line = bufferedReader.readLine();
                if (line.equals("patient-login")) {
                    boolean a = true;
                    line = bufferedReader.readLine();
                    String uno = Character.toString(line.charAt(0));
                    String dos = Character.toString(line.charAt(1));
                    String head = uno.concat(dos);
                    System.out.println("head:" + head);
                    if (head.equals("p#")) {
                        System.out.println("line: "+ line);
                        //Cogemos la información del paciente de la base de datos
                        patient = ConnectionClient.getData(line, patient, patientManager);
                        System.out.println("pat:"+ patient);
                        sendPatient(patient);
                        while (a) {
                            line = bufferedReader.readLine();
                            if (line.equals("again")) {
                                line = bufferedReader.readLine();
                                patient = ConnectionClient.getData(line, patient, patientManager);
                                sendPatient(patient);
                            } else if (line.equals("done")) {
                                a = false;
                                while (true) {
                                    line = bufferedReader.readLine();
                                    FunctionsWithPatients.insidePatient(line, bufferedReader);
                                }
                            } else if (line.equals("back") || line.equals("release")) {
                                a = false;
                            }
                        }
                    }
                } else if (line.equals("patient-register")) {
                    boolean b = true;
                    while (b) {
                        line = bufferedReader.readLine();
                        boolean done = PatientUtilities.regiterNewPatient(line, patient, patientManager);
                        if (done) {
                            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                            printWriter.println("done");
                            b = false;
                        } else {
                            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                            printWriter.println("notpossible");
                        }
                    }
                } else {
                    String uno = Character.toString(line.charAt(0));
                    String dos = Character.toString(line.charAt(1));
                    String head = uno.concat(dos);
                    boolean a= true;
                    if (head.equals("d#")) {
                        doctor = ConnectionClient.getDataDoctor(line, doctor, doctorManager);
                        PrintWriter printWriter2 = new PrintWriter(socket.getOutputStream(), true);
                        printWriter2.println(doctor.toString());
                        while (a) {
                            try {
                                line = bufferedReader.readLine();
                            } catch (IOException ex) {
                                Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                if (line.equals("again")) {
                                try {
                                    line = bufferedReader.readLine();
                                } catch (IOException ex) {
                                    Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                    doctor = ConnectionClient.getDataDoctor(line, doctor, doctorManager);
                                    printWriter2.println(doctor.toString());
                                    
                                } else if (line.equals("done")) {
                                    a = false;
                                } 
                             }
                            while (true) {
                                line = bufferedReader.readLine();
                                String uno1 = Character.toString(line.charAt(0));
                                String dos2 = Character.toString(line.charAt(1));
                                String head1 = uno1.concat(dos2);
                                if (head1.equals("s#")) { //s de search
                                    FunctionsWithPatients.seachPatients(line);
                                } else if (head1.equals("g#")) { //g de go
                                    patient = doctorManager.getPatientByFullname(line.substring(2, line.length()));
                                    sendPatient(patient);
                                while(true){
                                    line = bufferedReader.readLine();
                                    if (line.equals("files")) {
                                        FunctionsWithPatients.getFiles(bufferedReader);
                                       
                                    } else if (line.equals("ex")) {
                                        exit = true;
                                        break;
                                    }
                                    }
                                } else if (head1.equals("re")) {
                                    exit = true;
                                    break;
                                } else if (line.equals("ex")) {
                                        exit = true;
                                        break;
                                    }
                                
                            }
                        
                    } else if (head.equals("ex")) {
                        exit = true;
                    }
                }
            }
            releaseResourcesClient(bufferedReader, socket);
        } catch (IOException ex) {
            Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendPatient(Patient patient) {
        String patientToClient = patient.toString();
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(patientToClient);
        } catch (IOException ex) {
            System.out.println("Error en send de serverThreadsClient");
        }
    }

    private static void releaseResourcesClient(BufferedReader bufferedReader, Socket socket) {
        try {
            bufferedReader.close();

        } catch (IOException ex) {
            Logger.getLogger(ServerThreads.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerThreads.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
