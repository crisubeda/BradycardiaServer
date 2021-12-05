/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.sql;

import Pojos.Doctor;
import Pojos.Patient;
import db.interfaces.DoctorManager;
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
public class SQLDoctorManager implements DoctorManager{
    
    private Connection c;
    public SQLDoctorManager(Connection c){
        this.c =c;
    }
    public void createDoctor(Doctor doc) 
	{
        String sqldoctor= "INSERT INTO Doctor (idDoctor, nombre, username, email)"
					+  "VALUES (?,?,?)";
        try {
            PreparedStatement stm = c.prepareStatement(sqldoctor);
            stm.setString(2,doc.getFullName());
            stm.setString(3,doc.getUsername()); 
            stm.setString(4,doc.getEmail());
            
            stm.executeUpdate();
            stm.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(SQLPatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
    public void deleteDoctor(Integer id){
		
        String sqldoctor = "DELETE FROM Doctor WHERE idDoctor=?";
         try {
            PreparedStatement stm = c.prepareStatement(sqldoctor);
            stm.setInt(1,id);
            stm.executeUpdate();
            stm.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(SQLPatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void modifyDoctor(Doctor doc){
        
	String sqldoctor = "UPDATE Doctor SET name=?, username=?, email=? WHERE idDoctor=?";
         try {
            PreparedStatement stm = c.prepareStatement(sqldoctor);
            stm.setString(1, doc.getFullName());
            stm.setString(2, doc.getUsername());
            stm.setString(3, doc.getEmail());
            stm.setInt(7, doc.getID());
  
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLPatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public Doctor getDoctorByUsername(String username) {
        Doctor doctor = new Doctor();
        try {
            String sqlpatient = "SELECT * FROM Doctor WHERE username LIKE ?";
            PreparedStatement stm = c.prepareStatement(sqlpatient);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Integer patID = rs.getInt("id");
                String name = rs.getString("fullname");
                String username2 = rs.getString("username");
                String email = rs.getString("email");
                // meter contraseña
                System.out.println("name: "+ name);
                doctor = new Doctor(patID, name, username2,email);
                System.out.println(doctor.getFullName());
            }
        } catch (SQLException e) {
            doctor = null;
            //e.printStackTrace();
        }
        return doctor;
    }
    }

