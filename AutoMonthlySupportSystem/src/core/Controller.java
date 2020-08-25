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
    private int databackupSizeInMb = 400;
    private final GetUtilityData data = new GetUtilityData();

    @Override
    public void updateTableSpace(Connection connection) {
        tableSpaceList = data.getTableSpaceInfo(connection);

        tableSpaceList.forEach((tableSpaceInfo) -> {
            data.updateSQL(connection,
                    lList.updateTablespace(tableSpaceInfo.getFileName(),
                            updateFinalSizeOfTableSpace(
                                    tableSpaceInfo.getTableSpaceName(),
                                    Math.round(tableSpaceInfo.getUserdSpace()),
                                    databackupSizeInMb))
            );
        });
    }

    private int updateFinalSizeOfTableSpace(String tableSpaceName, int usedSize, int size) {
        //System.out.println("> " + tableSpaceName);
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
            ResultSet rs = data.getDataFromSQL(connection, lList.SELECT_ALL_TABLE);
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
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public List<String> getAllIndexScript(Connection connection) {
        List<String> indexNames = new Controller().getIndexList(connection);
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
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<String> getAllTableScript(Connection connection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Void doInBackground() throws Exception {
        firePropertyChange("writeConsole", null, "\n> Start TABLESPACE Processing ...\n");
        //updateTableSpace(OraDbConnection.connection());
        firePropertyChange("writeConsole", null, "> TABLESPACE Update ...\n");
        updateTableIndex(OraDbConnection.connection());
        firePropertyChange("writeConsole", null, "> All INDEX Update ...\n");
        rebuildTableScript(OraDbConnection.connection());
        firePropertyChange("writeConsole", null, "> All TABLE Update ...\n");
        return null;
    }

}
