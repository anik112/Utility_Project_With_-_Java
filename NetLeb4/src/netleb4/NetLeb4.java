/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netleb4;

import java.io.InputStream;
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
            // Frist we get 
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            
            con.setRequestMethod("GET");
            if(con.getResponseCode()==200){
                System.out.println(":: Connected ::");
                
                System.out.println(con.getResponseMessage());
                
                InputStream stream=con.getInputStream();
                System.out.println(stream);
            }
            System.out.println(con.getResponseCode());
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
       
    }
    
}
