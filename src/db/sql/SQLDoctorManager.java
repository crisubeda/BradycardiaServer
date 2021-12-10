/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.sql;

import Pojos.Doctor;
import Pojos.Patient;
import db.interfaces.DoctorManager;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristina
 */
public class SQLDoctorManager implements DoctorManager{
    
    private Connection c;
    public SQLDoctorManager(Connection c){
        this.c =c;
    }
   
    
    public Doctor getDoctorByUsername(String username) {
        Doctor doctor = new Doctor();
        try {
            String sqldoctor = "SELECT * FROM Doctor WHERE username LIKE ?";
            PreparedStatement stm = c.prepareStatement(sqldoctor);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Integer docID = rs.getInt("idDoctor");
                String name = rs.getString("fullname");
                String email = rs.getString("email");
                String pass= rs.getString("pwd");
                System.out.println("name: "+ name);
                doctor = new Doctor(docID, name, username,email,pass);
                System.out.println(doctor.getFullName());
            }
        } catch (SQLException e) {
            doctor = null;
            System.out.println("Algo ha ido mal");
            //e.printStackTrace();
        }
        return doctor;
    }
    
    
    
    @Override
    public String[] getNameByName(String name) {
        String[] listNames=new String[100];
        int position=0;
        String sqlpatient = "SELECT * FROM Patient WHERE fullname LIKE ?";
        try {
            PreparedStatement stm = c.prepareStatement(sqlpatient);
            stm.setString(1, name+"%");
            ResultSet rs = stm.executeQuery();
        
            while (rs.next()) {
                String fullname = rs.getString("fullname");
                listNames[position] = fullname;
                position++;
            }
            rs.close();
            stm.close();
        } catch (SQLException ex) {
            listNames = null;
            Logger.getLogger(SQLPatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listNames;
    }
    
    public Patient getPatientByFullname(String fullname) {
        Patient patient = new Patient();
        try {
            String sqlpatient = "SELECT * FROM Patient WHERE fullname LIKE ?";
            PreparedStatement stm = c.prepareStatement(sqlpatient);
            stm.setString(1, fullname);
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
            //e.printStackTrace();
        }
        return patient;
    }
    
    }

