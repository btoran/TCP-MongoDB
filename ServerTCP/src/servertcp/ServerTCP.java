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

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerTCP {

    
        static String message; //Variable to store the client´s messages
        static String idHx = ""; // Id Hex (I leave it so because he is not specified to be otherwise)
        static String temperatureHx = ""; // Temperatue Hex
        static int temperatureDegree = 0; // Temperature decimal
        static String temperatureDegreeString = ""; //Temperature string
        
        
        
        
        //function that converts hex to decimal
        
    public static int hex2decimal(String s) {
             String digits = "0123456789ABCDEF";
             s = s.toUpperCase();
             int val = 0;
             for (int i = 0; i < s.length(); i++) {
                 char c = s.charAt(i);
                 int d = digits.indexOf(c);
                 val = 16*val + d;
             }
             return val;
         }
    
    public static void main(String[] args) {
        
        
            DB db;
            DBCollection collection = null;
        
            
           //We initialize local MongoDB
            
            try {
                
                 Mongo mongo = new Mongo("localhost",27017);
                 db=mongo.getDB("TCPMongoDB"); 
                 collection = db.getCollection("tiles");
            } catch (UnknownHostException ex) {
                Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        
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
                            message = in.readUTF(); //we recieve the message


                            //The temperature Hex is the last four digits of the message

                            temperatureHx = message.substring(32, 36);

                            //The Id Hex ate the first 32 digits of the message
                            idHx = message.substring(0,32);

                            //We changne the bytes order of the TemperatureHx beacuse it is LittleEndian
                            String aux = temperatureHx.substring(0,2);
                            String aux2 = temperatureHx.substring(2,4);
                            temperatureHx = aux2 + aux;

                            //We convert TemperatureHx in Decimal
                            temperatureDegree = hex2decimal(temperatureHx);
                            System.out.println("La temperatura es: " + temperatureDegree);

                            //We insert the Id and Temperature in MongoDB. 
                            BasicDBObject document = new BasicDBObject();
                            document.put("ID_Hx", "'" +idHx + "'");
                            document.put("Temperature", temperatureDegree);


                            collection.insert(document);


                       }while (!"fin".equals(message));       
                   } 
          
            catch (Exception e) {

       
            System.err.println(e.getMessage());
            
            }


            }

               }
    

