/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverbradycardia;
import java.net.*;
import java.io.*; //permiten establecer conexiones
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Cristina
 */
public class conector {
    
    private static byte[] buffer;
    private static byte[] data;
    
    Socket socket;
    int puerto = 9000;//puerto que hace referencia al puerto que utilizamos
    DataOutputStream salida; //para enviar datos
    BufferedReader entrada;//buffer donde guardamos info que viene del exterior
    
    public void iniciar(){
        DatagramSocket socket = null;
       
        try{
            socket = new DatagramSocket(puerto);//instanciamos servidos
                    //puerto es el parámetro principal que me dice por donde se va a realizar esa conexión
            buffer = new byte[1024]; //se necesita utilizar un buffer
            while(true){
                DatagramPacket datagram = new DatagramPacket(buffer, buffer.length);
                socket.receive(datagram);
                data = datagram.getData();
            }
        }catch (SocketException ex) {
            Logger.getLogger(conector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(conector.class.getName()).log(Level.SEVERE, null, ex);
        }  finally {
            if (socket != null) {
                socket.close();
            }
        }
    
    }
}
