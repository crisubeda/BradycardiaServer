/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import Pojos.Patient;

/**
 *
 * @author Cristina
 */
public interface PatientManager {

    public void createPatient(Patient pat);

    public Patient getPatientById(int id);

    public Patient getPatientByUsername(String Username);

    public void modifyMac(Patient pat);

    public void modifyDiagnosis(Patient pat);

}
