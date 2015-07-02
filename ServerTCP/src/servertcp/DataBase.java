/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servertcp;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Borja
 */
public class DataBase {
    
        static DB db = null;
        static DBCollection collection = null;
        
        
        //Function that init MongoDB
    
    public static void initMongoDB(String ip, int port,String DBname, String CollectionName) throws UnknownHostException {
        

                
                 Mongo mongo = new Mongo(ip,port);
                 db=mongo.getDB(DBname); 
                 collection = db.getCollection(CollectionName);
            
           
        
         }
    
    
         //Function that insert data in MongoDB
    
    public static void insertData(String id, int temperature, String currentDate) {
        
        //We insert the Id and Temperature in MongoDB. 
                            BasicDBObject document = new BasicDBObject();
                            document.put("ID_Hx", "'" +id + "'");
                            document.put("Temperature", temperature);
                            document.put("Date", "'" +currentDate + "'");


                            collection.insert(document);
    }
        
        
    
    
    
}
