/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

/**
 *
 * @author carmen
 */
public class Reads {

    public static String[] ReadDatos(String line) {
        int byteRead;
        int i = 0;
        String string = "";
        String[] datos = new String[2];
        int contador = 2;
        while (contador <= line.length() - 1) {
            char a = line.charAt(contador);
            while ((char) a != ';' && (contador != (line.length()))) {
                a = line.charAt(contador);
                if (a != ';') {
                    string = string + a;
                    contador++;
                }
            }
            contador++;
            datos[i] = string;
            string = "";
            i++;
        }
        return datos;
    }
}
