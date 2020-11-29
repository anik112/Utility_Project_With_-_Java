/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netleb4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 *
 * @author Anik
 */
public class PostRequest {

    public static void main(String[] arg) {
        String posURL = "https://jsonplaceholder.typicode.com/posts/";
        try {
            HashMap<String, String> values = new HashMap<String, String>();
            values.put("name", "anik");
            values.put("name", "maruf");
            values.put("name", "shuvo");

            // URL object for open HTTP connection in server
            URL url = new URL(posURL);
            // Frist we get url connection then cust in Https Connection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            // Set the communication method
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            StringBuilder sendingData = new StringBuilder();
            for (String object : (values.keySet())) {
                if (sendingData.length() != 0) {
                    sendingData.append('&');
                }
                sendingData.append(URLEncoder.encode(object.toString(), "UTF-8"));
                sendingData.append("=");
                sendingData.append(URLEncoder.encode(values.get(object).toString(), "UTF-8"));

                byte[] postDataBytes = sendingData.toString().getBytes("UTF-8");

                con.getOutputStream().write(postDataBytes);
                
                Reader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                BufferedReader bufIn = new BufferedReader(in);
                String line = null;
                while ((line = bufIn.readLine()) != null) {
                    System.out.println(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
