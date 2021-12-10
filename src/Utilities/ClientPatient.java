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
public class ClientPatient {
    public static Patient again(BufferedReader bufferedReader){
        Patient patient= new Patient();
         String line="";
        try {
            line = bufferedReader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ClientPatient.class.getName()).log(Level.SEVERE, null, ex);
        }
         patient = ConnectionClient.getData(line, patient, patientManager);
         return patient;
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
            Logger.getLogger(ClientPatient.class.getName()).log(Level.SEVERE, null, ex);
        }
        filesManager.insertFile(file, patient);
                                                                                
}

}
