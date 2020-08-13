/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sqlrunnerinoracle.connection.DBConnection;
import java.sql.Types;

/**
 *
 * @author VSI SERVER
 */
public class Test {
    
    public static void main(String args[]){
     
        Connection connection=DBConnection.getConnection();
              
        try {
            PreparedStatement statement=connection.prepareCall("select per.cardno, per.empname, per.designation,per .sectionnm,per.lineno,per. salary_grade, per.joining_date,per.grosssalary ,other.PERFORMANCE\n" +
"from TB_PERSONAL_INFO_OTHER other, TB_PERSONAL_INFO per\n" +
"where per.COMPANY = other.COMPANY\n" +
"and   per.cardno  = other.cardno\n" +
"and   per.active  = 0\n" +
"order by per.cardno asc");
            ResultSet rs=statement.executeQuery();
            ResultSetMetaData metaData=rs.getMetaData();
            do{
                System.out.println(metaData.getColumnName(1));
                System.out.println("=>"+metaData.getColumnCount());
                if(metaData.getColumnType(2)==Types.VARCHAR){
                    System.out.println("==> Straig");
                }
            }while (false);
            int i=1;
            while (rs.next()) {                
                System.out.println(rs.getString(1));
                i++;
            }
            System.out.println("Total data: "+i);
        } catch (SQLException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
}
