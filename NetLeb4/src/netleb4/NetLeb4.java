/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netleb4;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Anik
 */
public class NetLeb4 {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try{
            // Set server URL
            String svUrl="http://webcode.me/";
            // URL object for open HTTP connection in server
            URL url=new URL(svUrl);
            // Make HTTP Connection for using GET & POST method
            // Frist we get url connection then cust in Https Connection
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            // Set the communication method
            con.setRequestMethod("GET");
            // Check connetion is ok or not
            // If it get 200 that mean connection successfull or it get 404
            // that's mean connection is unsuccessfull
            if(con.getResponseCode()==200){
                // Print status
                System.out.println(":: Connected ::");
                // print response massage
                System.out.println(con.getResponseMessage());
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
       
    }
    
}
