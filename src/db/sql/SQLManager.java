/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.sql;

import Utilities.ConnectionClient;
import db.interfaces.*;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristina
 */
public class SQLManager implements DBManager {

    private Connection c;
    private PatientManager patient;
    private DoctorManager doctor;
    private FilesManager file;

    public SQLManager() {
        super();
    }

    public Connection getConnection() {
        return c;
    }

    @Override
    public PatientManager getPatientManager() {
        return patient;
    }

    @Override
    public DoctorManager getDoctorManager() {
        return doctor;
    }
    
    public FilesManager getFilesManager() {
        return file;
    }

    @Override
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String ipFromConfigFile = "";
            try {
                String[] datos=ConnectionClient.getDataFromFile();
                this.c = DriverManager.getConnection("jdbc:mysql://" + ipFromConfigFile + "/dbbradycardia?user=root&password="+ datos[4]);
            } catch (IOException ex) {
                Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            patient = new SQLPatientManager(c);
            doctor = new SQLDoctorManager(c);
            file = new SQLFilesManager(c);
        } catch (ClassNotFoundException exc) {
            exc.printStackTrace();

        } catch (SQLException ex) {
            Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void disconnect() {
        try {
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
