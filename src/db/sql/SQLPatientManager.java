/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.sql;

import Pojos.Patient;
import db.interfaces.PatientManager;
import java.sql.*;
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
        String sqlpatient = "INSERT INTO Patient (fullname, username, address, phoneNumber, email, diagnosis, pwd, macBitalino)"
                + "VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stm = c.prepareStatement(sqlpatient);
            stm.setString(1, pat.getFullName());
            stm.setString(2, pat.getUsername());
            stm.setString(3, pat.getAddress());
            stm.setString(4, pat.getPhonenumber());
            stm.setString(5, pat.getEmail());
            stm.setString(6, pat.getDiagnosis());
            stm.setString(7, pat.getPassword());
            stm.setString(8, pat.getMacBitalino());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Logger.getLogger(SQLPatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    public void modifyMac(Patient pat) {
        String sqlpatient = "UPDATE Patient SET macBitalino=? WHERE username=?";
        try {
            PreparedStatement stm = c.prepareStatement(sqlpatient);
            stm.setString(1, pat.getMacBitalino());
            stm.setString(2, pat.getUsername());
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLPatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void modifyDiagnosis(Patient pat) {
        String sqlpatient = "UPDATE Patient SET diagnosis=? WHERE username=?";
        try {
            PreparedStatement stm = c.prepareStatement(sqlpatient);
            stm.setString(1, pat.getDiagnosis());
            stm.setString(2, pat.getUsername());
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLPatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Patient getPatientByUsername(String username) {
        Patient patient = new Patient();
        try {
            String sqlpatient = "SELECT * FROM Patient WHERE username LIKE ?";
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
                String diagnosis = rs.getString("diagnosis");
                String password = rs.getString("pwd");
                String macBitalino = rs.getString("macBitalino");
                patient = new Patient(patID, name, username2, address, phoneNumber, email, diagnosis, password, macBitalino);
            }
        } catch (SQLException e) {
            patient = null;
        }
        return patient;
    }

    @Override
    public Patient getPatientById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
