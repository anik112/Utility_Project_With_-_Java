/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.GetUtilityData;
import model.OraDbConnection;
import model.service.IndexName;
import model.service.SQLList;
import model.service.TableName;
import model.service.TableSpaceInfo;

/**
 *
 * @author Anik
 */
public class Controller implements Core {

    private List<TableSpaceInfo> tableSpaceList = new ArrayList<>();
    private SQLList lList = new SQLList();
    private int databackupSizeInMb = 400;
    private GetUtilityData data = new GetUtilityData();

    @Override
    public void updateTableSpace(Connection connection) {
        tableSpaceList = data.getTableSpaceInfo(connection);

        for (TableSpaceInfo tableSpaceInfo : tableSpaceList) {
            data.updateSQL(connection,
                    lList.updateTablespace(tableSpaceInfo.getFileName(),
                            updateFinalSizeOfTableSpace(
                                    tableSpaceInfo.getTableSpaceName(),
                                    Math.round(tableSpaceInfo.getUserdSpace()),
                                    databackupSizeInMb))
            );

        }
    }

    private int updateFinalSizeOfTableSpace(String tableSpaceName, int usedSize, int size) {
        System.out.println("> " + tableSpaceName);
        int updateSize = 0;
        if (lList.TBS_USERS.equals(tableSpaceName)) {
            int[] siz = lList.TABLESPACE_SIZES.get(lList.TBS_USERS);
            if (size > 0 && size < 501) {
                return (usedSize + siz[0]);
            } else if (size > 500 && size < 801) {
                return (usedSize + siz[1]);
            } else if (size > 800 && size < 1024) {
                return (usedSize + siz[2]);
            }
        } else if (lList.TBS_SYSTEM.equals(tableSpaceName)) {
            int[] siz = lList.TABLESPACE_SIZES.get(lList.TBS_SYSTEM);
            return (usedSize + siz[0]);
        } else if (lList.TBS_SYSAUX.equals(tableSpaceName)) {
            int[] siz = lList.TABLESPACE_SIZES.get(lList.TBS_SYSAUX);
            return (usedSize + siz[0]);
        } else if (lList.TBS_UNDOTBS1.equals(tableSpaceName)) {
            int[] siz = lList.TABLESPACE_SIZES.get(lList.TBS_UNDOTBS1);
            return (usedSize + siz[0]);
        }
        return updateSize;
    }

    @Override
    public List<String> getTableList(Connection connection) {
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = data.getAllTableName(connection);
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public List<String> getIndexList(Connection connection) {
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = data.getAllIndexName(connection);
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public void updateTableIndex(Connection connection) {
        try {
            ResultSet set=data.getDataFromSQL(connection, lList.SELECT_INDEX_UPTO_16_WITH_TABLESPACE);
            boolean check=true;
            while(set.next()){
                String sql=lList.getAlterIndexScriptForResize(set.getString(1),set.getString(3), 16);
                System.out.println(sql);
                data.updateSQL(connection, sql);
                System.out.println("====== UPDATE ========\n");
                check=false;
            }
            if(check){
                System.out.println("All index are 16k");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public List<String> getAllIndexScript(Connection connection) {
        List<String> indexNames = new Controller().getIndexList(connection);
        for (String indexName : indexNames) {
            System.out.println("--INEX--"+indexName+"---\n");
            String indexScrept = data.getIndexScript(connection, indexName);
            int loc = indexScrept.indexOf("INITIAL");
            loc += 8;
            System.out.println("Loc of INITIAL: " + loc);
            String tempStr = indexScrept.substring(loc, loc + 5);
            System.out.println(tempStr);
            indexScrept = indexScrept.replace(tempStr, "16000");
            System.out.println(indexScrept);
            System.out.println("=============================");
        }
        return indexNames;
    }

}
