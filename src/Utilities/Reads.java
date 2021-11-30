/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carmen
 */
public class Reads {

    public static String[] ReadClient(InputStream inputStream) {
        int byteRead;
        int i = 0;
        String string = "";
        String[] datos = {"a", "a"};
        try {
            while ((byteRead = inputStream.read()) != -1) {
                char a = (char) byteRead;
                while (a != ';' || byteRead != -1) {
                    string = string + a;
                    byteRead = inputStream.read();
                    a = (char) byteRead;
                }
                if (i != 0) {
                    datos[i] = string;
                    string = "";
                    i++;
                } else {
                    string = "";
                }
            }

        } catch (IOException ex) {
            System.out.println("Error en 42 de reads");
            //Logger.getLogger(Reads.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datos;
    }
}
