/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.sql.Connection;
import java.util.List;
import model.service.IndexName;
import model.service.TableName;

/**
 *
 * @author Anik
 */
public interface Core {
    
    public void updateTableSpace(Connection connection);
    public List<String> getTableList(Connection connection);
    public List<String> getIndexList(Connection connection);
    public void updateTableIndex(Connection connection);
    
}
