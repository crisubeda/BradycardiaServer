/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Pojos.Patient;
import db.interfaces.DBManager;
import db.interfaces.PatientManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import serverbradycardia.*;

/**
 *
 * @author carmen
 */
public class ConnectionClient {

    public static void initialiceAll(DBManager dbManager, PatientManager patientManager, Patient patient) {
        //patient = new Patient();
        //dbManager = new SQLManager();
        //dbManager.connect();
        //patientManager = dbManager.getPatientManager();
    }

    public static String[] getDataFromFile() throws IOException {
        File file2 = new File(".");
        String path = file2.getAbsolutePath();
        String goodpath = file2.getAbsolutePath().substring(0, path.length() - 2).concat("/files/DataConnection.txt");
        FileReader br = null;
        String[] datos = {"a", "a", "a", "a"};
        try {
            br = new FileReader(goodpath);

            int caract;
            int i = 0;
            char a;
            String dt = "";
            while ((caract = br.read()) != -1) {
                a = (char) caract;
                if (a != '#') {
                    dt = dt + a;
                } else {
                    datos[i] = dt;
                    i++;
                    dt = "";
                    while (a != ';' || caract == -1) {
                        caract = br.read();
                        a = (char) caract;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            throw new IOException("File not found");
        } catch (IOException e) {
            throw new IOException("Error");
        }
        //datos[0] ---> IP SERVER SOCKET
        //datos[1] ---> ServerSocket
        //datos[2] ---> IP DB SERVER
        //datos[3] ---> PORT DB SERVER
        System.out.println(datos[1]);
        System.out.println(Utilities.Exceptions.checkFloat(datos[1]));
        return datos;
    }

    public static Patient getData(String line, Patient patient, PatientManager patientManager) {
        boolean stop = false;
        Patient p = new Patient();
        if (line.charAt(0) == 'p') {
            String[] data = Reads.ReadDatos(line);
            System.out.println("username: " + data[0]);
            System.out.println("pass: " + data[1]);
            System.out.println("Estoy antes del get PatientByUsername");
            patient = patientManager.getPatientByUsername(data[0]);
            //p = new Patient(1, "Cristina", "CrisMola", "Calle baloncesto", "68970896979", "c@ceu.es", "nada super sana", 2, "98:D3:91:FD:69:49");
            // System.out.println("Name: " + patient.getFullName());
            //meter tambien el password cuanod lo tengamos
        } else if (line.charAt(0) == 'p') {
            //Reads.ReadA(introd);//bitalino....
        }

        return patient;
    }

    public static void sendPatient(Patient p, ServerThreadsClient serverThreadsClient) {
        //Patient p = new Patient(1, "Cristina", "CrisMola", "Calle baloncesto", "68970896979", "c@ceu.es", "nada super sana", 2, "98:D3:91:FD:69:49");
        serverThreadsClient.sendPatient(p);
    }

}
