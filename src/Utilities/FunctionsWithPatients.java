/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Pojos.Patient;
import db.interfaces.FilesManager;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import static serverbradycardia.ServerThreadsClient.*;

/**
 *
 * @author drijc
 */
public class FunctionsWithPatients {
    public static Patient again(BufferedReader bufferedReader){
        Patient patient= new Patient();
        String line="";
        try {
            line = bufferedReader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(FunctionsWithPatients.class.getName()).log(Level.SEVERE, null, ex);
        }
         patient = ConnectionClient.getData(line, patient, patientManager);
         System.out.println("Patient send:"+ patient);
         return patient;
    }
    public static void seachPatients(String line){
        String[] ListNames = new String[100];
        ListNames = doctorManager.getNameByName(line.substring(2, line.length()));
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < ListNames.length; i++) {
            strBuilder.append(ListNames[i] + ";");
        }
        String messageNames = strBuilder.toString();
        PrintWriter printWriter4;
        try {
            printWriter4 = new PrintWriter(socket.getOutputStream(), true);
            printWriter4.println(messageNames);
        } catch (IOException ex) {
            Logger.getLogger(FunctionsWithPatients.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static boolean insidePatient(String line, BufferedReader bufferedReader){
        boolean exit=false;
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
       
            System.out.println("line: "+line);
        if (line.equals("Introducebitalino")) {
            line = bufferedReader.readLine();
            if (!line.equals("back")) {
                patient.setMacBitalino(line);
                patientManager.modifyMac(patient);                              
                printWriter.println(patient);
            }
        } else if (line.equals("GetData")) {
                line = bufferedReader.readLine();
                if (!line.equals("back")) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("_uuuu-MM-dd_HH-mm-ss");
                    LocalDateTime date = LocalDateTime.now();
                    String name = patient.getUsername().concat(dtf.format(date));
                    file = new File("files/fileBit_" + name + ".txt");
                    FileWriter myWriter = new FileWriter(file);
                    while ((line = bufferedReader.readLine()) != null && !line.equals("back")) {
                        myWriter.write(line);
                    }
                    myWriter.close();
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
        }
         } catch (IOException ex) {
            Logger.getLogger(FunctionsWithPatients.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exit;
        }
    
    public static void getFiles(BufferedReader bufferedReader){
        String[] ListNamesFiles = filesManager.getNameFilesById(patient.getID());
        StringBuilder strBuilderFiles = new StringBuilder();
        for (int i = 0; i < ListNamesFiles.length; i++) {
            strBuilderFiles.append(ListNamesFiles[i] + ";");
        }
        String filesNames = strBuilderFiles.toString();
       
        try {
             PrintWriter printWriter3;
            printWriter3 = new PrintWriter(socket.getOutputStream(), true);
            printWriter3.println(filesNames);
        } catch (IOException ex) {
            Logger.getLogger(FunctionsWithPatients.class.getName()).log(Level.SEVERE, null, ex);
        }
        //mandamos todos los ficheros de ese paciente al doctor
        String line="";
        try {
            line = bufferedReader.readLine(); //llega el file que ha seleccionado el doctor
        } catch (IOException ex) {
            Logger.getLogger(FunctionsWithPatients.class.getName()).log(Level.SEVERE, null, ex);
        }
        //enseñamos el fichero que pide
        String uno1 = Character.toString(line.charAt(0));
        String dos2 = Character.toString(line.charAt(1));
        String head1 = uno1.concat(dos2);
        if (head1.equals("s#")) { //s de search
            String path;
            path = filesManager.getFileByName(line.substring(2, line.length()));
            File fileDoctor =new File(path);
            OutputStream outputstream;
            try {
                outputstream = socket.getOutputStream();
                 ObjectOutputStream obj=new ObjectOutputStream(outputstream);
                 obj.writeObject(fileDoctor);
            } catch (IOException ex) {
                Logger.getLogger(FunctionsWithPatients.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    
    
    
    
    
    public static void getData(BufferedReader bufferedReader, FilesManager filesManager){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("_uuuu-MM-dd_HH-mm-ss");
        LocalDateTime date = LocalDateTime.now();
        String name = patient.getUsername().concat(dtf.format(date));
        file = new File("files/fileBit_" + name + ".txt");
        FileWriter myWriter;
        try {
            myWriter = new FileWriter(file);
            String line = bufferedReader.readLine();
            while (( line = bufferedReader.readLine()) != null && !line.equals("back")) {
                myWriter.write(line);
            }
        myWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(FunctionsWithPatients.class.getName()).log(Level.SEVERE, null, ex);
        }
        filesManager.insertFile(file, patient);
                                                                                
}

}
