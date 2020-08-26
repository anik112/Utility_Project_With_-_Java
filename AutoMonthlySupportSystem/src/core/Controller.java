/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import model.GetUtilityData;
import model.OraDbConnection;
import model.SQLList;
import model.TableSpaceInfo;

/**
 *
 * @author Anik
 */
public class Controller extends SwingWorker<Void, String> implements Core {

    private List<TableSpaceInfo> tableSpaceList = new ArrayList<>();
    private final SQLList lList = new SQLList();
    private int databackupSizeInMb = 300;
    private final GetUtilityData data = new GetUtilityData();

    public Controller(int dataBackupSize) {
        databackupSizeInMb=dataBackupSize;
    }
    
    @Override
    public void updateTableSpace(Connection connection, int databackupSizeInMb) {
        tableSpaceList = data.getTableSpaceInfo(connection);

        tableSpaceList.forEach((tableSpaceInfo) -> {
            data.updateSQL(connection,
                    lList.updateTablespace(tableSpaceInfo.getFileName(),
                            updateFinalSizeOfTableSpace(
                                    tableSpaceInfo.getFileName(),
                                    Math.round(tableSpaceInfo.getUserdSpace()),
                                    (int) tableSpaceInfo.getFreeSpace(),
                                    databackupSizeInMb))
            );
        });
    }

    private int updateFinalSizeOfTableSpace(String tableSpaceFileName, int usedSize, int freeSize, int backupSize) {
        //System.out.println("> " + tableSpaceName);
        int updateSize = 0;
        if (lList.TBS_USERS.equals(tableSpaceFileName)) {
            int[] siz = lList.TABLESPACE_SIZES.get(lList.TBS_USERS);
            if (backupSize > 0 && backupSize < 501) {
                return (usedSize + siz[0]);
            } else if (backupSize > 500 && backupSize < 800) {
                return (usedSize + siz[1]);
            } else if (backupSize > 799 && backupSize < 1024) {
                return (usedSize + siz[2]);
            }else if(backupSize>1023 && backupSize<1536){
                return usedSize+siz[3];
            }else if(backupSize>1535 && backupSize<2048){
                return usedSize+siz[4];
            }else if(backupSize>2047 && backupSize<2867){
                return usedSize+siz[5];
            }else if(backupSize>2867 && backupSize<3072){
                return usedSize+siz[6];
            }else if(backupSize>3071 && backupSize<4096){
                return usedSize+siz[7];
            }
        } else if(lList.TBS_USERS02.equals(tableSpaceFileName)){
            int[] siz = lList.TABLESPACE_SIZES.get(lList.TBS_USERS);
            if (backupSize > 0 && backupSize < 501) {
                return (usedSize + siz[0]);
            } else if (backupSize > 500 && backupSize < 800) {
                return (usedSize + siz[1]);
            } else if (backupSize > 799 && backupSize < 1024) {
                return (usedSize + siz[2]);
            }else if(backupSize>1023 && backupSize<1536){
                return usedSize+siz[3];
            }else if(backupSize>1535 && backupSize<2048){
                return usedSize+siz[4];
            }else if(backupSize>2047 && backupSize<2867){
                return usedSize+siz[5];
            }else if(backupSize>2867 && backupSize<3072){
                return usedSize+siz[6];
            }else if(backupSize>3071 && backupSize<4096){
                return usedSize+siz[7];
            }
        }
        else if (lList.TBS_SYSTEM.equals(tableSpaceFileName)) {
            int[] siz = lList.TABLESPACE_SIZES.get(lList.TBS_SYSTEM);
            return (usedSize + siz[0]);
        } else if (lList.TBS_SYSTEM02.equals(tableSpaceFileName)) {
            int[] siz = lList.TABLESPACE_SIZES.get(lList.TBS_SYSTEM02);
            return (usedSize + siz[0]);
        }else if (lList.TBS_SYSAUX.equals(tableSpaceFileName)) {
            int[] siz = lList.TABLESPACE_SIZES.get(lList.TBS_SYSAUX);
            return (usedSize + siz[0]);
        } else if (lList.TBS_UNDOTBS1.equals(tableSpaceFileName)) {
            int[] siz = lList.TABLESPACE_SIZES.get(lList.TBS_UNDOTBS1);
            return (usedSize + siz[0]);
        }else if (lList.TBS_USRPICTURE.equals(tableSpaceFileName)) {
            int[] siz = lList.TABLESPACE_SIZES.get(lList.TBS_USRPICTURE);
            return (usedSize + siz[0]);
        }
        
        return updateSize+freeSize;
    }

    @Override
    public List<String> getTableList(Connection connection) {
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = data.getDataFromSQL(connection, lList.SELECT_ALL_TABLE);
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            firePropertyChange("writeConsole", null, "# getTableList() function dose not work.\n");
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
            firePropertyChange("writeConsole", null, "# getIndexList() function dose not work.\n");
        }
        return list;
    }

    @Override
    public void updateTableIndex(Connection connection) {
        try {
            ResultSet set = data.getDataFromSQL(connection, lList.SELECT_INDEX_UPTO_16_WITH_TABLESPACE);
            boolean check = true;
            while (set.next()) {
                String sql = lList.getAlterIndexScriptForResize(set.getString(1), set.getString(4), 16);
                System.out.println(sql);
                data.updateSQL(connection, sql);
                firePropertyChange("writeConsole", null, "\n> INEX == " + set.getString(4) + " == [ Update ]");
                check = false;
            }
            if (check) {
                firePropertyChange("writeConsole", null, "> All index script are 16k --!\n");
            }
        } catch (SQLException ex) {
            firePropertyChange("writeConsole", null, "# updateTableIndex() function dose not work.\n");
        }

    }

    @Override
    public List<String> getAllIndexScript(Connection connection) {
        List<String> indexNames = getIndexList(connection);
        for (String indexName : indexNames) {
            System.out.println("\n> INEX == " + indexName + " == [ Update ]");
            String indexScrept = data.getIndexScript(connection, indexName);
            int loc = indexScrept.indexOf("INITIAL") + 8;
            int numberLength = 5;
            String tempStr = indexScrept.substring(loc, (loc + numberLength));
            indexScrept = indexScrept.replace(tempStr, "16000");
        }
        return indexNames;
    }

    @Override
    public void rebuildTableScript(Connection con) {
        try {
            boolean check = true;
            ResultSet set = data.getDataFromSQL(con, lList.SELECT_TABLE_UPTO_16);
            while (set.next()) {
                Connection connection = con;
                String tableName = set.getString(1);
                ResultSet rs = data.getDataFromSQL(connection, lList.getTableScript(tableName));
                while (rs.next()) {
                    firePropertyChange("writeConsole", null, "> TABLE == " + tableName + "\n");
                    String tableScript = data.getTableScript(connection, tableName);
                    if (!tableScript.contains("BLOB")) {
                        int loc = tableScript.indexOf("INITIAL") + 8;
                        int endLoc = tableScript.indexOf("NEXT");
                        String tempStr = tableScript.substring(loc, (endLoc - 1));
                        tableScript = tableScript.replace(tempStr, "16000");
                        // Start script rebuild
                        if (data.updateSQL(connection, lList.getTableRenameScript(tableName))) {
                            firePropertyChange("writeConsole", null, "> Table name change to " + tableName + "_X\n");
                            if (data.updateSQL(connection, tableScript)) {
                                firePropertyChange("writeConsole", null, "> Carete new table --!\n");
                                if (data.updateSQL(connection, lList.getInsertOneTblToAnotherScript(tableName, tableName + "_X"))) {
                                    firePropertyChange("writeConsole", null, "> Update data one table to another.\n");
                                    if (data.updateSQL(connection, lList.getDeleteTableScript(tableName + "_X"))) {
                                        System.out.println("> Table deleted " + tableName + "_X");
                                        firePropertyChange("writeConsole", null, "> Table deleted " + tableName + "_X\n\n");
                                    }
                                }
                            }
                        }

                    }
                    firePropertyChange("writeConsole", null, "$ This table work with Image.\n");
                }
                check = false;
            }
            if (check) {
                firePropertyChange("writeConsole", null, "> All Table are 16k --!\n");
            }
        } catch (SQLException ex) {
            firePropertyChange("writeConsole", null, "# rebuildTableScript() function dose not work.\n");
        }
    }

    @Override
    public List<String> getAllTableScript(Connection connection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Void doInBackground() throws Exception {
        firePropertyChange("writeConsole", null, "\n> Start TABLESPACE Processing ...\n");
        updateTableSpace(OraDbConnection.connection(),databackupSizeInMb);
        firePropertyChange("writeConsole", null, "> TABLESPACE Update ...\n");
        updateTableIndex(OraDbConnection.connection());
        firePropertyChange("writeConsole", null, "> All INDEX Update ...\n");
        rebuildTableScript(OraDbConnection.connection());
        firePropertyChange("writeConsole", null, "> All TABLE Update ...\n");
        return null;
    }

}
