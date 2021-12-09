/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverbradycardia;

import java.time.*;
import Pojos.Doctor;
import Pojos.Patient;
import Utilities.ConnectionClient;
import Utilities.PatientUtilities;
import db.interfaces.DBManager;
import db.interfaces.DoctorManager;
import db.interfaces.FilesManager;
import db.interfaces.PatientManager;
import db.sql.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
        dbManager = new SQLManager();
        dbManager.connect();
        doctorManager= dbManager.getDoctorManager();
        patientManager = dbManager.getPatientManager();
        filesManager = dbManager.getFilesManager();
       // file= new File(".");
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
                            //Como lo que llega es un paciente, vamos a coger toda su información de la base
                            //de datos
                            patient = ConnectionClient.getData(line, patient, patientManager);
                            System.out.println("Ya he cogido toda la información del patient");
                            // if (patient.getFullName().equals(patient)) {
                            sendPatient(patient);
                            System.out.println("Patient toString: " + patient.toString());
                            while(a){
                                line = bufferedReader.readLine();
                                System.out.println("linea leida: " + line);
                                if (line.equals("again")) {
                                    System.out.println("Se entra en again");
                                    line = bufferedReader.readLine();
                                    patient = ConnectionClient.getData(line, patient, patientManager);
                                    //patient = new Patient(1, "Cristina", "CrisMola", "Calle baloncesto", "68970896979", "c@usp.ceu.es", "nada super sana", 2, "98:D3:91:FD:69:49");
                                    sendPatient(patient);
                                    System.out.println("patient send: " + patient.toString());
                                } else if (line.equals("done")) {
                                    System.out.println("estoy doneeee");
                                    //lo siguiente que haya que hacer si se ha login
                                    a = false;
                                    while(true){
                                        line = bufferedReader.readLine();
                                        System.out.println("Line leida en while: "+ line);
                                        if(line.equals("Introducebitalino")){
                                            System.out.println("estamos en introducebitalino");
                                            line = bufferedReader.readLine();
                                            if(!line.equals("back")){
                                            patient.setMacBitalino(line);
                                            System.out.println(patient.toString());
                                            patientManager.modifyMac(patient);
                                            sendPatient(patient);
                                            }

                                        }else if(line.equals("GetData")){
                                             line = bufferedReader.readLine();
                                            if(!line.equals("back")){
                                              DateTimeFormatter dtf = DateTimeFormatter.ofPattern("_uuuu-MM-dd_HH-mm-ss");
                                              LocalDateTime date = LocalDateTime.now(); 
                                              String name = patient.getUsername().concat(dtf.format(date));
                                              file= new File("files/fileBit_"+name+".txt");
                                              System.out.println(file.getAbsolutePath());
                                              patient.addFile(file);
                                              FileWriter myWriter = new FileWriter(file);
                                            while((line=bufferedReader.readLine()) != null && !line.equals("back")){  
                                                myWriter.write(line);
                                                System.out.println("line: "+line);
                                            }
                                            myWriter.close();
                                            //meter en base de datos
                                            System.out.println("Vamos a meter el archivo en la base de datos");
                                            filesManager.insertFile(file,patient);
                                            System.out.println("Se ha introducido en la base de datos");
                                            }
                                            
                                        }else if (line.equals("release")){
                                            exit=true;
                                            break;
                                        }
                                    }
                                    
                                }else if (line.equals("back") || line.equals("release")){
                                    a =false;                              
                                }                           
                            }
                        }
                    } else if (line.equals("patient-register")) {
                        System.out.println("Vamos a register new patient");
                        boolean b = true;
                        while(b){
                        line = bufferedReader.readLine();
                        boolean done = PatientUtilities.regiterNewPatient(line, patient, patientManager);
                        if (done) {
                            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                            printWriter.println("done");
                            System.out.println("se mete en done");
                            b = false;
                        } else {
                            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                            printWriter.println("notpossible");
                            System.out.println("se mete en el notpossible");
                        }
                    } //}
                    }
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
                            System.out.println(doctor.toString());
                            if (doctor == null) {
                                System.out.println("El doctor es null");
                                PrintWriter printWriter2 = new PrintWriter(socket.getOutputStream(), true);
                                printWriter2.println(doctor.toString());
                            } else {
                                System.out.println("Doctor toString: " + doctor.toString());
                                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                                printWriter.println(doctor.toString());
                                while(true){
                                    line = bufferedReader.readLine();
                                    System.out.println("Soy el servidor y me llega: " +line);
                                    String uno1 = Character.toString(line.charAt(0));
                                    String dos2 = Character.toString(line.charAt(1));
                                    String head1 = uno1.concat(dos2);
                                    if (head1.equals("s#")) {
                                        System.out.println("Estoy dentro de s");
                                        String[] ListNames = new String[100];
                                        System.out.println("Quiero buscar este nombre:" +line.substring(2, line.length()));
                                        ListNames= doctorManager.getNameByName(line.substring(2, line.length())); 
                                        System.out.println("La lista de nombres es: " +ListNames[0]);
                                        System.out.println("Ya he entrado en la base de datos");
                                        //System.out.println(Arrays.toString(ListNames));
                                        //String messageNames ="";
                                        
                                        StringBuilder strBuilder = new StringBuilder();
                                        for (int i = 0; i < ListNames.length; i++) {
                                           strBuilder.append(ListNames[i] + ";");
                                        }
                                        String messageNames = strBuilder.toString();
                                        /*for(int i=0;i<ListNames.length;i++){
                                            messageNames.concat(ListNames[i]+ ", ");
                                        }*/
                                        System.out.println("los nombres concatenados son: " + messageNames);
                                        PrintWriter printWriter2 = new PrintWriter(socket.getOutputStream(), true);
                                        printWriter2.println(messageNames);//obtener nombres con receiveData
                                        System.out.println("He mandado el mensaje correctamente: " +messageNames);
                                    }else if(head1.equals("n#")){
                                        
                                    }
                                }
                            }
                        }
                    }
                }
                //stop = Utilities.ConnectionClient.getData(introd);
            System.out.println("fuera de todo");
            releaseResourcesClient(bufferedReader, socket);
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

      private static void releaseResourcesClient(BufferedReader bufferedReader, Socket socket) {
        try {
            System.out.println("se han release los sources");
            bufferedReader.close();

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
