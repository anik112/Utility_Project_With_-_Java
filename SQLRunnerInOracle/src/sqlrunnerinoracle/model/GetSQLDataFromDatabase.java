/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlrunnerinoracle.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author VSI SERVER
 */
public class GetSQLDataFromDatabase {

    public ResultSet getSqlDataFromDatabase(Connection connection, String sql) {

        PreparedStatement statement;
        ResultSet rs = null;

        try {
            statement = connection.prepareCall(sql);
            rs = statement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(GetSQLDataFromDatabase.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(
                    null, ex.getMessage(),
                    ":: SQL Error-101 :: ", JOptionPane.ERROR_MESSAGE);
        }
        return rs;
    }

}
