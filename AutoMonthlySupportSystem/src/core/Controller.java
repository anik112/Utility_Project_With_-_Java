/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;
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
        databackupSizeInMb = dataBackupSize;
    }

    @Override
    public void updateTableSpace(Connection connection, int databackupSizeInMb) {
        tableSpaceList = data.getTableSpaceInfo(connection);
        // execute loop
        tableSpaceList.forEach((tableSpaceInfo) -> {
            // execute Alter SQL
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

    /**
     * This function make sure table space total size.
     *
     * @param tableSpaceFileName
     * @param usedSize
     * @param freeSize
     * @param backupSize
     * @return
     */
    private int updateFinalSizeOfTableSpace(String tableSpaceFileName, int usedSize, int freeSize, int backupSize) {
        //System.out.println("> " + tableSpaceName);
        int updateSize = 0;
        if (lList.TBS_USERS.equals(tableSpaceFileName)) { // This for USERS01 table space
            int[] siz = lList.TABLESPACE_SIZES.get(lList.TBS_USERS);
            if (backupSize > 0 && backupSize < 501) {
                return (usedSize + siz[0]);
            } else if (backupSize > 500 && backupSize < 800) {
                return (usedSize + siz[1]);
            } else if (backupSize > 799 && backupSize < 1024) {
                return (usedSize + siz[2]);
            } else if (backupSize > 1023 && backupSize < 1536) {
                return usedSize + siz[3];
            } else if (backupSize > 1535 && backupSize < 2048) {
                return usedSize + siz[4];
            } else if (backupSize > 2047 && backupSize < 2867) {
                return usedSize + siz[5];
            } else if (backupSize > 2867 && backupSize < 3072) {
                return usedSize + siz[6];
            } else if (backupSize > 3071 && backupSize < 4096) {
                return usedSize + siz[7];
            }
        } else if (lList.TBS_USERS02.equals(tableSpaceFileName)) { // This for USER02 table space
            int[] siz = lList.TABLESPACE_SIZES.get(lList.TBS_USERS);
            if (backupSize > 0 && backupSize < 501) {
                return (usedSize + siz[0]);
            } else if (backupSize > 500 && backupSize < 800) {
                return (usedSize + siz[1]);
            } else if (backupSize > 799 && backupSize < 1024) {
                return (usedSize + siz[2]);
            } else if (backupSize > 1023 && backupSize < 1536) {
                return usedSize + siz[3];
            } else if (backupSize > 1535 && backupSize < 2048) {
                return usedSize + siz[4];
            } else if (backupSize > 2047 && backupSize < 2867) {
                return usedSize + siz[5];
            } else if (backupSize > 2867 && backupSize < 3072) {
                return usedSize + siz[6];
            } else if (backupSize > 3071 && backupSize < 4096) {
                return usedSize + siz[7];
            }
        } else if (lList.TBS_SYSTEM.equals(tableSpaceFileName)) { // This for SYSTEM01 table space
            int[] siz = lList.TABLESPACE_SIZES.get(lList.TBS_SYSTEM);
            return (usedSize + siz[0]);
        } else if (lList.TBS_SYSTEM02.equals(tableSpaceFileName)) { // This for SYSTEM02 table space
            int[] siz = lList.TABLESPACE_SIZES.get(lList.TBS_SYSTEM02);
            return (usedSize + siz[0]);
        } else if (lList.TBS_SYSAUX.equals(tableSpaceFileName)) { // This for SYSAUX table space
            int[] siz = lList.TABLESPACE_SIZES.get(lList.TBS_SYSAUX);
            return (usedSize + siz[0]);
        } else if (lList.TBS_UNDOTBS1.equals(tableSpaceFileName)) { // This for UNDOTTBS1 table space
            int[] siz = lList.TABLESPACE_SIZES.get(lList.TBS_UNDOTBS1);
            return (usedSize + siz[0]);
        } else if (lList.TBS_USRPICTURE.equals(tableSpaceFileName)) { // This for USRPICTURE table space
            int[] siz = lList.TABLESPACE_SIZES.get(lList.TBS_USRPICTURE);
            return (usedSize + siz[0]);
        }

        return updateSize + freeSize;
    }

    @Override
    public List<String> getTableList(Connection connection) {
        List<String> list = new ArrayList<>();
        try {
            // get table name and store in array
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
            // get index name ad store in array
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
            // get table Script from database
            ResultSet set = data.getDataFromSQL(connection, lList.SELECT_INDEX_UPTO_16_WITH_TABLESPACE);
            boolean check = true;
            while (set.next()) {
                // get index alter sql
                String sql = lList.getAlterIndexScriptForResize(set.getString(1), set.getString(4), 16);
                data.updateSQL(connection, sql); // run sql
                firePropertyChange("writeConsole", null, "\n> INEX == " + set.getString(4) + " == [ Update ]");
                check = false;
            }
            if (check) {
                firePropertyChange("writeConsole", null, "\n> All index script are 16k --!\n");
            }
        } catch (SQLException ex) {
            firePropertyChange("writeConsole", null, "# updateTableIndex() function dose not work.\n");
        }

    }

    @Override
    public List<String> getAllIndexScript(Connection connection) {
        // get index name from database
        List<String> indexNames = getIndexList(connection);
        for (String indexName : indexNames) {
            System.out.println("\n> INEX == " + indexName);
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

    /**
     * This function work in backround by using Thred when main window runing.
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Void doInBackground() throws Exception {
        firePropertyChange("writeConsole", null, "\n> Start Database Backup Processing ...\n");
        Process process = Runtime.getRuntime().exec("exp payroll/payroll@payroll file=D:\\" + System.currentTimeMillis() + ".dmp");
        
        firePropertyChange("writeConsole", null, "\n> Database Backup Success. !\n");
//        firePropertyChange("writeConsole", null, "\n> Start TABLESPACE Processing ...\n");
//        updateTableSpace(OraDbConnection.connection(), databackupSizeInMb);
//        firePropertyChange("writeConsole", null, "> TABLESPACE Update ...\n");
//        updateTableIndex(OraDbConnection.connection());
//        firePropertyChange("writeConsole", null, "> All INDEX Update ...\n");
//        rebuildTableScript(OraDbConnection.connection());
//        firePropertyChange("writeConsole", null, "> All TABLE Update ...\n");
        return null;
    }

}
