/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.sql;

import Pojos.Doctor;
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
    @Override
    public Integer searchByFullName(String fullname){
        Integer patID = null;
		try
		{
			String sqlfullname = "SELECT ID FROM patient WHERE fullname=?";
			PreparedStatement stm = c.prepareStatement(sqlfullname);
			stm.setString(1,fullname);
			ResultSet rs = stm.executeQuery();
			while(rs.next())
			{
				patID = rs.getInt("ID");
			}
			stm.close();
			rs.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return patID;
	}
    }

