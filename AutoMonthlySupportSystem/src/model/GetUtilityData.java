/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import error.ErrorList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.service.SQLList;
import model.service.TableName;
import model.service.TableSpaceInfo;

/**
 *
 * @author Anik
 */
public class GetUtilityData {
    
    public List<TableName> getAllTableName(Connection con){
        
        List<TableName> nameList=new ArrayList<>();
        try{
            PreparedStatement statement=con.prepareStatement(new SQLList().SELECT_ALL_TABLE);
            ResultSet rs=statement.executeQuery();
            
            while(rs.next()){
                TableName tableName=new TableName();
                tableName.setTableName(rs.getString(1));
                nameList.add(tableName);
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, new ErrorList().GET_TBL_NM_ERROR+e.getMessage(),
                    "GET_TBL_NM_ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return nameList;
    }
    
    public List<TableSpaceInfo> getTableSpaceInfo(Connection con){
        List<TableSpaceInfo> tableSpaceList=new ArrayList<>();
        try{
            PreparedStatement statement=con.prepareStatement(new SQLList().GET_ALL_TABLESPACE);
            ResultSet rs=statement.executeQuery();
            
            while(rs.next()){
                TableSpaceInfo spaceInfo=new TableSpaceInfo();
                spaceInfo.setTableSpaceName(rs.getString(1));
                spaceInfo.setFileName(rs.getString(2));
                spaceInfo.setTotalSpace(rs.getFloat(3));
                spaceInfo.setFreeSpace(rs.getFloat(4));
                
                tableSpaceList.add(spaceInfo);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, new ErrorList().GET_TBLSPACE_INFO+e.getMessage(),
                    "GET_TBLSPACE_INFO", JOptionPane.ERROR_MESSAGE);
        }
        return tableSpaceList;
    }
    
    public void updateTableSpace(Connection con, String sql){
        try{
            PreparedStatement statement=con.prepareStatement(sql);
            statement.executeUpdate();
            con.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, new ErrorList().UPDATE_TABLESPACE+e.getMessage(),
                    "UPDATE_TABLESPACE", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
