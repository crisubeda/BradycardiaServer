/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Pojos.Patient;
import db.interfaces.PatientManager;

/**
 *
 * @author carmen
 */
public class PatientUtilities {

    public static boolean regiterNewPatient(String line, Patient patient, PatientManager patientManager) {
        boolean nuevo = true;
        String[] datos = new String[100];
        int i = 0;
        int contador = 0;
        String data = "";
        switch (line.charAt(0)) {
            case 'p':
                while (line.charAt(i + 2) != '#') {
                    while (line.charAt(i + 2) != ';') {
                        data = data.concat(Character.toString(line.charAt(i + 2)));
                        i++;
                    }
                    //System.out.println("Con es:" + con);
                    datos[contador] = data;
                    i++;
                    contador++;
                    data = "";
                }
                // Patient patient2 = patientManager.getPatientByUsername(data[0]);
                //si esta devolver un no vale o algo asi
                patient.setID(Exceptions.convertInt(datos[0]));
                patient.setFullName(datos[1]);
                patient.setUsername(datos[2]);
                patient.setAddress(datos[3]);
                patient.setPhonenumber(datos[4]);
                patient.setEmail(datos[5]);
                patient.setDiagnosis(datos[6]);
                patient.setDocId(Exceptions.convertInt(datos[7]));
                //patient.setPassword(password);
                patient.setMacBitalino(datos[9]);
                patient.setNewBitalino();
                //patientManager.createPatient(patient); //meter en base de datos
                break;
        }

        return nuevo;
    }
}
