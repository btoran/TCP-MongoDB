/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servertcp;

/**
 *
 * @author Borja
 */

//import java.net library
import java.net.*;

//import java.io library
import java.io.*;

public class ServerTCP {

    
    static String message; //Variable to store the client´s messages
    
    public static void main(String[] args) {
        
        
            ServerSocket socket; // ServerSocket object for communication
        
        
           
            try {

           //We indicate the communication port

            socket = new ServerSocket(6000);

            do{ 
                
                
            /*We create a socket_client to put the content of the socket object after execute the "accept" function
             to get the client connections*/
                
                
            Socket socket_client = socket.accept();

           

            //"in" is a DataInputStream object that will serve us to receive data from the client

            DataInputStream in =
            new DataInputStream(socket_client.getInputStream());

       
            message="";
            message = in.readUTF(); //we send encripted message
            System.out.println(message);


             }while (!"fin".equals(message));       
            } 
          
            catch (Exception e) {

       
            System.err.println(e.getMessage());
            
            }


            }

               }
    

