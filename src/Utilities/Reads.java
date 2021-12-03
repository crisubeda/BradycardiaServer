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

    public static String[] ReadClient(String introd) {
        int byteRead;
        int i = 0;
        String string = "";
        String[] datos = new String[2];
        int contador = 2;
        while (contador != introd.length()) {
            char a = introd.charAt(contador);
            System.out.println("char a es : " + a);
            while ((char) a != ';' || contador != introd.length()) {
                string = string + a;
                System.out.println("string es: " + string);
                contador++;
                a = introd.charAt(contador);
                System.out.println("a ahora es: " + a);
            }
            i++;//añadido
            if (i != 0) {//no se mete en este if
                datos[i] = string;
                string = "";
                System.out.println("datos va a ser:" + datos[i]);
                i++;

            } else {
                string = "";
            }
        }

        return datos;
    }
}
