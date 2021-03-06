/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlrunnerinoracle.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Anik
 */
public class DBConnection {

    private static String basedUrl = ""; //jdbc:oracle:thin:
    private static String host = "";//"192.168.1.204"//"DESKTOP-ICON0GA"//DESKTOP-KTD50PC;
    private static String port = "";//1521
    private static String dbName = "";//payroll
    private static String userName = "";//report_generator
    private static String password = "";//report_generator
    private final static ConfigKeyList KEY_LIST = new ConfigKeyList();

    private static String url = "";
    private static java.sql.Connection connection = null;

    public static void setupUtility() {
        getHostName(); // call function for get host name
        
        // check file is exists
        try (InputStream input = new FileInputStream("setup\\ConnectionSetup.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            basedUrl = properties.getProperty(KEY_LIST.KEY_ORACLE_BASE_URL);
            //host = properties.getProperty(KEY_LIST.KEY_SERVER_NAME);
            port = properties.getProperty(KEY_LIST.KEY_PORT_NUMBER);
            dbName = properties.getProperty(KEY_LIST.KEY_DATABSE_NAME);
            userName = properties.getProperty(KEY_LIST.KEY_USER_NAME);
            password = properties.getProperty(KEY_LIST.KEY_USER_PASSWORD);

            properties.clear();

            url = basedUrl + "@" + host + ":" + port + ":" + dbName;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null, "Check File Exists? " + e.getMessage(),
                    ":: File Not Found :: ", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void getHostName() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            host = address.getHostName();
            System.out.println("Host: " + host);
        } catch (UnknownHostException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(
                    null, ex.getMessage(),
                    ":: Database Error-101 :: ", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static java.sql.Connection getConnection() {

        setupUtility();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("::Connected::");
            return connection;
        } catch (Exception /*| ClassNotFoundException */ e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    ":: Error- DB 01 :: ", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

}
