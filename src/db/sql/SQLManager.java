/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.sql;

import db.interfaces.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

    @Override
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String ipFromConfigFile = "";
            this.c = DriverManager.getConnection("jdbc:mysql://" + ipFromConfigFile + "/dbbradycardia?user=root&password=Cris.2102");
            patient = new SQLPatientManager(c);
            doctor = new SQLDoctorManager(c);
            //esto es para prueba:
            /*Statement stm = c.createStatement();
           ResultSet rs = stm.executeQuery("select * from patient");

           while(rs.next()){
           System.out.println(rs.getString("nombre"));}*/
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
