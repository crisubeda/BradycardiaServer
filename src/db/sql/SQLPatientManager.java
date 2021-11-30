/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.sql;

import Pojos.Patient;
import db.interfaces.PatientManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristina
 */
public class SQLPatientManager implements PatientManager {

    private Connection c;

    public SQLPatientManager(Connection c) {
        this.c = c;
    }

    public void createPatient(Patient pat) {
        String sqlpatient = "INSERT INTO Patient (idPatient, nombre, username, address, phoneNumber, email, diagnosis, doctorId, macBitalino)"
                + "VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stm = c.prepareStatement(sqlpatient);
            stm.setString(2, pat.getFullName());
            stm.setString(3, pat.getUsername());
            stm.setString(4, pat.getAddress());
            stm.setString(5, pat.getPhonenumber());
            stm.setString(6, pat.getEmail());
            stm.setString(7, pat.getDiagnosis());
            stm.setInt(3, pat.getDocId());
            stm.setString(9, pat.getMacBitalino());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Logger.getLogger(SQLPatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deletePatient(Integer id) {

        String sqlpatient = "DELETE FROM Patient WHERE idPatient=?";
        try {
            PreparedStatement stm = c.prepareStatement(sqlpatient);
            stm.setInt(1, id);
            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Logger.getLogger(SQLPatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void modifyPatient(Patient pat) {

        String sqlpatient = "UPDATE Patient SET fullname=?, username=?, address=?, phoneNumber=?, email=?, idDoctor=?, macBitalino=? WHERE id=?";
        try {
            PreparedStatement stm = c.prepareStatement(sqlpatient);
            stm.setString(1, pat.getFullName());
            stm.setString(2, pat.getUsername());
            stm.setString(3, pat.getAddress());
            stm.setString(4, pat.getPhonenumber());
            stm.setString(5, pat.getEmail());
            stm.setInt(6, pat.getDocId());
            stm.setString(7, pat.getMacBitalino());
            stm.setInt(8, pat.getID());
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLPatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Patient getPatientById(int id) {

        String sqlpatient = "SELECT * FROM Patient WHERE id=?";
        Patient patient = new Patient();
        try {
            PreparedStatement stm = c.prepareStatement(sqlpatient);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Integer patID = rs.getInt("ID");
                String name = rs.getString("Name");
                String username = rs.getString("Username");
                String address = rs.getString("Address");
                String phoneNumber = rs.getString("PhoneNumber");
                String email = rs.getString("Email");
                String diagnosis = rs.getString("Diagnosis");
                String macBitalino = rs.getString("macBitalino");
                //obtener nombre del doctor
               // patient = new Patient(patID, name, username, address, phoneNumber, email, diagnosis, macBitalino);

            }
            rs.close();
            stm.close();
        } catch (SQLException ex) {
            patient = null;
            Logger.getLogger(SQLPatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return patient;
    }

    public Patient getPatientByUsername(String username) {
        Patient patient = new Patient();
        try {
            String sqlpatient = "SELECT * FROM Patient WHERE username=?";

            PreparedStatement stm = c.prepareStatement(sqlpatient);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Integer patID = rs.getInt("id");
                String name = rs.getString("fullname");
                String username2 = rs.getString("username");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phoneNumber");
                String email = rs.getString("email");
                Integer docID = rs.getInt("idDoctor");
                String diagnosis = rs.getString("diagnosis");
                String macBitalino = rs.getString("macBitalino");
                // meter contraseña
                System.out.println("name: "+ name);
                patient = new Patient(patID, name, username2, address, phoneNumber, email, diagnosis, docID, macBitalino);
                System.out.println(patient.getFullName());
            }

        } catch (SQLException e) {
            patient = null;
            e.printStackTrace();
        }

        return patient;
    }
}
