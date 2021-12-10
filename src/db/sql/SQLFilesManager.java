/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.sql;

import Pojos.Patient;
import Utilities.ConnectionClient;
import db.interfaces.FilesManager;
import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author drijc
 */
public class SQLFilesManager implements FilesManager{
    
    private Connection c;

    public SQLFilesManager(Connection c) {
        this.c = c;
    }

    public void insertFile(File file, Patient pat){
        String sqlpatient = "INSERT INTO FILES(files,pati_id,fileName) VALUES(?,?,?)";
        try {
            String path=file.getAbsolutePath();
            PreparedStatement stm = c.prepareStatement(sqlpatient);
            stm.setString(1, path);
            stm.setInt(2, pat.getID());
            stm.setString(3, file.getName());
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLPatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String[] getNameFilesById(int id) {
        String sqlpatient = "SELECT fileName FROM FILES WHERE pati_id LIKE ?";
        String[] filesName = new String[100];
        int contador=0;
        try {
            PreparedStatement stm = c.prepareStatement(sqlpatient);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                filesName[contador] = rs.getString("fileName");
                contador++;
            }
            rs.close();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLPatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filesName;
    }
    
     public String getFileByName(String name) {
        System.out.println("Entramos en funcion el nombre es:"+ name);
        String sqlpatient = "SELECT files FROM FILES WHERE fileName LIKE ?";
        String path = "";
        int contador=0;
        try {
            PreparedStatement stm = c.prepareStatement(sqlpatient);
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                path = rs.getString("files");
            }
            rs.close();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLPatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return path;
    }
}
