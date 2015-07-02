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

    
        static String message  = ""; //Variable to store the client´s messages
        static String idHx = ""; // Id Hex (I leave it so because he is not specified to be otherwise)
        static String temperatureHx = ""; // Temperatue Hex
        static int temperatureDegree = 0; // Temperature decimal
        static String temperatureDegreeString = ""; //Temperature string
        static String date; //Current Date
        static ServerSocket socket = null; // ServerSocket object for TCP communication 
        
        
        
        
        
       
        
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
    
    
         //function that recieve and decode message  
    
    public static void recieveData() throws IOException {
             
             /*We create a socket_client to put the content of the socket object after execute the "accept" function
             to get the client connections*/


            Socket socket_client = socket.accept();
           

            //"in" is a DataInputStream object that will serve us to receive data from the client

            DataInputStream in =
            new DataInputStream(socket_client.getInputStream());


            message="";
            message = in.readUTF(); //we recieve the message

            temperatureDegree = getTemperature(message);
            idHx = getId(message);
            date = getCurrentDate(message);
            
            

         }
    
    
    
    
    public static int getTemperature(String messageRecieved) {
        
            //The temperature Hex is the last four digits of the message

             String tempHex = messageRecieved.substring(32, 36);

            //We changne the bytes order of the TemperatureHx beacuse it is LittleEndian
            String aux = tempHex.substring(0,2);
            String aux2 = tempHex.substring(2,4);
            tempHex = aux2 + aux;

            //We convert TemperatureHx in Decimal
            int tempDegree = hex2decimal(tempHex);
            System.out.println("La temperatura es: " + tempDegree);
            
             
            
            return tempDegree;
             
         }
    
    
    public static String getId(String messageRecieved) {
        
            //The Id Hex ate the first 32 digits of the message
            String idHex = message.substring(0,32);

            
            return idHex;
            
            
             
         }
    
     public static String getCurrentDate(String messageRecieved) {
        
            String currentDate = message.substring(36, 55);
            return currentDate;
             
         }
    
    
    
    public static void main(String[] args) throws IOException {
        
        

            
            //We initialize local MongoDB (ip,port,DBname,CollectionName)
        
            DataBase.initMongoDB("localhost",27017,"TCPMongoDB", "tiles");

            //We indicate the communication port
                
            socket = new ServerSocket(6000);
            
       
            //We always listen to the client
            
             do{ 
                
                
                            recieveData();
                            DataBase.insertData(idHx, temperatureDegree,date);

                         

             }while (1>0);       

       
            }

            }
    

