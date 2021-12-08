/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.sql;

import Pojos.Patient;
import db.interfaces.FilesManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
        String sqlpatient = "INSERT INTO FILES(files,pati_id) VALUES(?,?)";
        try {
            PreparedStatement stm = c.prepareStatement(sqlpatient);
            stm.setBinaryStream(1, input);
            stm.setInt(2, pat.getID());
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLPatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
