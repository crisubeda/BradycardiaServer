/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.sql;

import Pojos.Patient;
import Utilities.ConnectionClient;
import db.interfaces.FilesManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        FileInputStream input = null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SQLPatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sqlpatient = "INSERT INTO FILES(files,pati_id,fileName) VALUES(?,?,?)";
        try {
            PreparedStatement stm = c.prepareStatement(sqlpatient);
            stm.setBinaryStream(1, input);
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
    
     public File getFileByName(String name, PrintWriter pw) {
        String sqlpatient = "SELECT files FROM FILES WHERE fileName LIKE ?";
        File file=new File(".");
        int contador=0;
        try {
            PreparedStatement stm = c.prepareStatement(sqlpatient);
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                InputStream input = rs.getBinaryStream("files");
                try {
                    ConnectionClient.copyInputStreamToFile(input,file,pw);
                } catch (IOException ex) {
                    Logger.getLogger(SQLFilesManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                contador++;
            }
            rs.close();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLPatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }
}
