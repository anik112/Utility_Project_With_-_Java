/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Anik
 */
public interface Core {
    /**
     * This function update table space size. This function first get all table
     * space and execute loop. Each loop run alter sql.
     * @param connection
     * @param databackupSizeInMb 
     */
    public void updateTableSpace(Connection connection, int databackupSizeInMb);
    /**
     * This function get all table name.
     * @param connection
     * @return 
     */
    public List<String> getTableList(Connection connection);
    /**
     * This function get all index name.
     * @param connection
     * @return 
     */
    public List<String> getIndexList(Connection connection);
    /**
     * This function update table index. first get all index name &
     * execute loop. Each loop run alter index sql.
     * @param connection 
     */
    public void updateTableIndex(Connection connection);
    /**
     * This function get all index script from database DLL()
     * @param connection
     * @return 
     */
    public List<String> getAllIndexScript(Connection connection);
    /**
     * This function update all table script. This function first get table script
     * then update initial size then execute sql.
     * @param connection 
     */
    public void rebuildTableScript(Connection connection);
    /**
     * This function get all table script from database DLL()
     * @param connection
     * @return 
     */
    public List<String> getAllTableScript(Connection connection);
    
    
}
