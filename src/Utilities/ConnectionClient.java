/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Pojos.Patient;
import db.interfaces.DBManager;
import db.interfaces.PatientManager;
import db.sql.SQLManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carmen
 */
public class ConnectionClient {

    public static PatientManager patientManager;
    public static Patient patient;
    public static DBManager dbManager;

    public static void initialiceAll() {
        patient = new Patient();
        dbManager = new SQLManager();
        dbManager.connect();
        PatientManager patientManager = dbManager.getPatientManager();
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

    public static void getData(InputStream inputStream) {
        int byteRead;
        try {
            byteRead = inputStream.read();
            char caracter = (char) byteRead;
            if (caracter == 'p') {
                String[] data = Reads.ReadClient(inputStream);
                patient = patientManager.getPatientByUsername(data[0]);
                System.out.println("Name: " + patient.getFullName());
                //meter tambien el password cuanod lo tengamos
            } else if (caracter == 'b') {
                Reads.ReadClient(inputStream);//bitalino....
            }
        } catch (IOException ex) {
            Logger.getLogger(ConnectionClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
