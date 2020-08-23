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
    
    public void updateTableSpace(Connection connection);
    public List<String> getTableList(Connection connection);
    public List<String> getIndexList(Connection connection);
    public void updateTableIndex(Connection connection);
    public List<String> getAllIndexScript(Connection connection);
    public void rebuildTableScript(Connection connection);
    public List<String> getAllTableScript(Connection connection);
    
    
}
