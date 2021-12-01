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
                System.out.println("char a es : " + a);
                while (a != ';' || byteRead != -1) {
                    string = string + a;
                    System.out.println("string es: " + string);
                    byteRead = inputStream.read();
                    a = (char) byteRead;  
                    System.out.println("a ahora es: "+ a);
                }
                i++;//añadido
                if (i != 0) {//no se mete en este if
                    datos[i] = string;
                    string = "";
                    System.out.println("datos va a ser:" +  datos[i]);
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
