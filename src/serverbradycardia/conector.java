/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverbradycardia;
import java.net.*;
import java.io.*; //permiten establecer conexiones
/**
 *
 * @author Cristina
 */
public class conector {
    
    ServerSocket server;
    Socket socket;
    int puerto = 9000;//puerto que hace referencia al puerto que utilizamos
    DataOutputStream salida; //para enviar datos
    BufferedReader entrada;//buffer donde guardamos info que viene del exterior
    
    public void iniciar(){
    
    try{
        server = new ServerSocket(puerto);//instanciamos servidos
                //puerto es el parámetro principal que me dice por donde se va a realizar esa conexión
        socket = new Socket();//instancia de socket
        socket = server.accept();//esta línea espera a que se mande una peticion de un programa exetrno, y una vez llega se estbalece conexión con el otro programa.
        
        //cinfiguro entrada:
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }catch(Exception e){}
    }
}
