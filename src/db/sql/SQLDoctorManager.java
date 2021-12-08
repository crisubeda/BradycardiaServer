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
        String sqldoctor= "INSERT INTO Doctor (idDoctor, fullname, username, email, pwd)"
					+  "VALUES (?,?,?)";
        try {
            PreparedStatement stm = c.prepareStatement(sqldoctor);
            stm.setString(2,doc.getFullName());
            stm.setString(3,doc.getUsername()); 
            stm.setString(4,doc.getEmail());
            stm.setString(5, doc.getPassword());
            
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
        
	String sqldoctor = "UPDATE Doctor SET fullname=?, username=?, email=? WHERE idDoctor=?";
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
        System.out.println("Se ha metido en getDoctorBy Username");
        Doctor doctor = new Doctor();
        try {
            String sqldoctor = "SELECT * FROM Doctor WHERE username LIKE ?";
            PreparedStatement stm = c.prepareStatement(sqldoctor);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Integer docID = rs.getInt("idDoctor");
                String name = rs.getString("fullname");
                String username2 = rs.getString("username");
                String email = rs.getString("email");
                // meter contraseña
                System.out.println("name: "+ name);
                doctor = new Doctor(docID, name, username2,email);
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
        System.out.println("Hemos entrado y name es: " +name);
        String[] listNames=new String[100];
        int position=0;
        String sqlpatient = "SELECT * FROM Patient WHERE fullname=?";
        try {
            PreparedStatement stm = c.prepareStatement(sqlpatient);
            stm.setString(1, name+"%");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String fullname = rs.getString("fullname");
                System.out.println("El fullname es: " +fullname);
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
    }

