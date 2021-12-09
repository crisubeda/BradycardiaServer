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

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            boolean stop = true;
            boolean exit = false;

            while (!exit) {
                String line = bufferedReader.readLine();
                if (line.equals("patient-login")) {
                    boolean a = true;
                    System.out.println("Vamos a login");
                    line = bufferedReader.readLine();
                    String uno = Character.toString(line.charAt(0));
                    String dos = Character.toString(line.charAt(1));
                    String head = uno.concat(dos);
                    if (head.equals("p#")) {
                        //Cogemos la información del paciente de la base de datos
                        patient = ConnectionClient.getData(line, patient, patientManager);
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
                                    if (line.equals("Introducebitalino")) {
                                        line = bufferedReader.readLine();
                                        if (!line.equals("back")) {
                                            patient.setMacBitalino(line);
                                            patientManager.modifyMac(patient);
                                            sendPatient(patient);
                                        }
                                    } else if (line.equals("GetData")) {
                                        line = bufferedReader.readLine();
                                        if (!line.equals("back")) {
                                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("_uuuu-MM-dd_HH-mm-ss");
                                            LocalDateTime date = LocalDateTime.now();
                                            String name = patient.getUsername().concat(dtf.format(date));
                                            file = new File("files/fileBit_" + name + ".txt");
                                            patient.addFile(file);
                                            FileWriter myWriter = new FileWriter(file);
                                            while ((line = bufferedReader.readLine()) != null && !line.equals("back")) {
                                                myWriter.write(line);
                                            }
                                            myWriter.close();
                                            //meter en base de datos
                                            filesManager.insertFile(file, patient);
                                        }
                                    } else if (line.equals("diagnosis")) {
                                        line = bufferedReader.readLine();
                                        if (line.equals("sendDiagnosis")) {
                                            line = bufferedReader.readLine();
                                            patient.setDiagnosis(line);
                                            patientManager.modifyDiagnosis(patient);
                                        }

                                    } else if (line.equals("release")) {
                                        exit = true;
                                        break;
                                    }
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
                    if (head.equals("d#")) {
                        doctor = ConnectionClient.getDataDoctor(line, doctor, doctorManager);
                        if (doctor == null) {
                            PrintWriter printWriter2 = new PrintWriter(socket.getOutputStream(), true);
                            printWriter2.println(doctor.toString());
                        } else {
                            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                            printWriter.println(doctor.toString());
                            while (true) {
                                line = bufferedReader.readLine();
                                String uno1 = Character.toString(line.charAt(0));
                                String dos2 = Character.toString(line.charAt(1));
                                String head1 = uno1.concat(dos2);
                                if (head1.equals("s#")) { //s de search
                                    String[] ListNames = new String[100];
                                    ListNames = doctorManager.getNameByName(line.substring(2, line.length()));
                                    StringBuilder strBuilder = new StringBuilder();
                                    for (int i = 0; i < ListNames.length; i++) {
                                        strBuilder.append(ListNames[i] + ";");
                                    }

                                    String messageNames = strBuilder.toString();
                                    PrintWriter printWriter2 = new PrintWriter(socket.getOutputStream(), true);
                                    printWriter2.println(messageNames);//obtener nombres con receiveData
                                } else if (head1.equals("g#")) { //g de go
                                    patient = doctorManager.getPatientByFullname(line.substring(2, line.length()));
                                    sendPatient(patient);
                                    line = bufferedReader.readLine();
                                    if (line.equals("files")) {
                                        String[] ListNamesFiles = filesManager.getNameFilesById(patient.getID());
                                        StringBuilder strBuilderFiles = new StringBuilder();
                                        for (int i = 0; i < ListNamesFiles.length; i++) {
                                            strBuilderFiles.append(ListNamesFiles[i] + ";");
                                        }
                                        String filesNames = strBuilderFiles.toString();
                                        PrintWriter printWriter3 = new PrintWriter(socket.getOutputStream(), true);
                                        printWriter3.println(filesNames);//mandamos todos los ficheros de ese paciente al doctor
                                        line = bufferedReader.readLine(); //llega el file que ha seleccionado el doctor
                                        //enseñamos el fichero que pide
                                        uno1 = Character.toString(line.charAt(0));
                                        dos2 = Character.toString(line.charAt(1));
                                        head1 = uno1.concat(dos2);
                                        if (head1.equals("s#")) { //s de search
                                            File file = filesManager.getFileByName(line.substring(2, line.length()), printWriter3);

                                        } else if (head1.equals("b#")) { //b de back

                                        }

                                    } else if (line.equals("ex")) {
                                        exit = true;
                                        break;
                                    }
                                } else if (head1.equals("re")) {
                                    exit = true;
                                    break;
                                }
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
