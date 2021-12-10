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
                    datos[contador] = data;
                    i++;
                    contador++;
                    data = "";
                }
                
                Patient patient2 = patientManager.getPatientByUsername(datos[2]);
                if(!patient2.getUsername().equals(datos[2])){
                patient.setID(Exceptions.convertInt(datos[0]));
                patient.setFullName(datos[1]);
                patient.setUsername(datos[2]);
                patient.setAddress(datos[3]);
                patient.setPhonenumber(datos[4]);
                patient.setEmail(datos[5]);
                patient.setDiagnosis(datos[6]);
                patient.setPassword(datos[7]);
                patient.setMacBitalino(datos[8]);
                patient.setNewBitalino();
                patientManager.createPatient(patient); 
                } else {
                    nuevo = false;
                }
                break;
                
        }

        return nuevo;
    }
}
