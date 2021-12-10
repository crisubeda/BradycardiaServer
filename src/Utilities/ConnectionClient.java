/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Pojos.Doctor;
import Pojos.Patient;
import db.interfaces.DBManager;
import db.interfaces.DoctorManager;
import db.interfaces.PatientManager;
import java.io.BufferedReader;
import java.io.*;
import java.net.Socket;
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
        String[] datos = new String[5];
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
        return datos;
    }

    public static Patient getData(String line, Patient patient, PatientManager patientManager) {
        boolean stop = false;
        Patient p = new Patient();
        if (line.charAt(0) == 'p') {
            String[] data = Reads.ReadDatos(line);
            p= patientManager.getPatientByUsername(data[0]);
            if(p==null){
                p= new Patient();
                p.setEmail("null");
            }
           else if(!p.getPassword().equals(data[1])){
                p= new Patient();
                p.setEmail("null");
            }
        } 

        return p;
    }

    public static Doctor getDataDoctor(String line, Doctor doctor, DoctorManager doctorManager) {
        boolean stop = false;
        Doctor doc = new Doctor();
        if (line.charAt(0) == 'd') {
            String[] data = Reads.ReadDatos(line);
            doc = doctorManager.getDoctorByUsername(data[0]);
            if(doc==null ){
                doc=new Doctor();
                doc.setEmail("null");
            } else if(!doc.getPassword().equals(data[1])){
                doc= new Doctor();
                doc.setEmail("null");
            }
        }
        return doc;
    }

    public static void sendPatient(Patient p, ServerThreadsClient serverThreadsClient) {
        //Patient p = new Patient(1, "Cristina", "CrisMola", "Calle baloncesto", "68970896979", "c@ceu.es", "nada super sana", 2, "98:D3:91:FD:69:49");
        serverThreadsClient.sendPatient(p);
    }
    
    public static void copyInputStreamToFile(InputStream inputStream, File file)
            throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[10000];
            while ((read = inputStream.read(bytes)) != -1) {
                //estamos mandando todos los bytes del fichero en forma de char
                outputStream.write(bytes,0, read);
            }
        }
    }
}
