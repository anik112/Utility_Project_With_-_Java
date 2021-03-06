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

/**
 *
 * @author Anik
 */
public class GetUtilityData {

    private SQLList lList = new SQLList();
    private ErrorList errorList = new ErrorList();

    public ResultSet getAllIndexName(Connection con) {
        ResultSet set = null;
        try {
            PreparedStatement statement = con.prepareStatement(lList.SELECT_ALL_INDEX);
            set = statement.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, errorList.GET_INDX_NM_ERROR + e.getMessage(),
                    "GET_INDX_NM_ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return set;
    }

    public List<TableSpaceInfo> getTableSpaceInfo(Connection con) {
        List<TableSpaceInfo> tableSpaceList = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement(lList.GET_ALL_TABLESPACE);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                TableSpaceInfo spaceInfo = new TableSpaceInfo();
                spaceInfo.setTableSpaceName(rs.getString(1));
                spaceInfo.setFileName(rs.getString(2));
                spaceInfo.setTotalSpace(rs.getFloat(3));
                spaceInfo.setFreeSpace(rs.getFloat(4));

                tableSpaceList.add(spaceInfo);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, errorList.GET_TBLSPACE_INFO + e.getMessage(),
                    "GET_TBLSPACE_INFO", JOptionPane.ERROR_MESSAGE);
        }
        return tableSpaceList;
    }

    public String getIndexScript(Connection con, String indexName) {
        String script = "";
        try {
            PreparedStatement statement = con.prepareStatement(lList.getIndexScript(indexName));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                script = rs.getString(1);
                return script;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "GET_INDX_SCRIPT: " + e.getMessage(),
                    "GET_INDX_SCRIPT", JOptionPane.ERROR_MESSAGE);
        }
        return script;
    }

    public String getTableScript(Connection con, String tableName) {
        String script = "";
        try {
            PreparedStatement statement = con.prepareStatement(lList.getTableScript(tableName));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                script = rs.getString(1);
                return script;
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "GET_TBL_SCRIPT: " + e.getMessage(),
                    "GET_TBL_SCRIPT", JOptionPane.ERROR_MESSAGE);
        }
        return script;
    }

    public String getIndexScriptByTableName(Connection con, String tableName) {
        String script = "";
        String indexName = "";
        try {
            PreparedStatement statementIndex = con.prepareStatement(lList.getIndexNameByTableScript(tableName));
            ResultSet indxRs = statementIndex.executeQuery();
            while (indxRs.next()) {
                indexName = indxRs.getString(1);
            }
            if (indexName.length() > 1) {
                PreparedStatement statement = con.prepareStatement(lList.getIndexScript(indexName));
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    script = rs.getString(1);
                    return script;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "GET_INDX_SCRIPT: " + e.getMessage(),
                    "GET_INDX_SCRIPT_BY_TABLE_NAME", JOptionPane.ERROR_MESSAGE);
        }
        return script;
    }

    public boolean updateSQL(Connection con, String sql) {
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "<html><body><p style='width: 200px;'>" + sql + "\n> " + e.getMessage(),
                    "UPDATE_SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean insertSQL(Connection con, String sql) {
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "<html><body><p style='width: 200px;'>" + sql + "\n> " + e.getMessage() + "</p></body></html>",
                    "INSERT_SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public ResultSet getDataFromSQL(Connection con, String sql) {
        ResultSet rs = null;
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            rs = statement.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "<html><body><p style='width: 200px;'>" + sql + "\n> " + e.getMessage() + "</p></body></html>",
                    "GET_DATA_FROM_SQL", JOptionPane.ERROR_MESSAGE);
        }
        return rs;
    }

}
