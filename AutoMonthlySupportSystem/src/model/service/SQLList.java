/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Anik
 */
public class SQLList {
    
    public Map<String,Boolean> TABLESPACE_NAMES=new HashMap<>();
    public final String SELECT_ALL_TABLE="SELECT TABLE_NAME FROM ALL_TABLE WHERE OWNER='PAYROLL'";
    public final String GET_ALL_TABLESPACE="SELECT DATA_FILES.TABLESPACE_NAME,DATA_FILES.FILE_NAME, DATA_FILES.BYTES/1024/1024 AS TOTAL_SIZE,\n" +
                                            "SUM(FREE_SPACE.BYTES)/1024/1024 AS FREE_SPACE \n" +
                                            "FROM DBA_DATA_FILES DATA_FILES, DBA_FREE_SPACE FREE_SPACE \n" +
                                            "WHERE DATA_FILES.FILE_ID=FREE_SPACE.FILE_ID \n" +
                                            "GROUP BY DATA_FILES.TABLESPACE_NAME,DATA_FILES.FILE_NAME, DATA_FILES.BYTES";

    public SQLList() {
        TABLESPACE_NAMES.put("USERS", true);
        TABLESPACE_NAMES.put("SYSTEM", true);
        TABLESPACE_NAMES.put("SYSAUX", true);
        TABLESPACE_NAMES.put("UNDOTBS1", true);
    }

    public String updateTablespace(String tableSpaceName){
        return "ALTER DATABASE DATAFILE '"+tableSpaceName+"' RESIZE "+0+"M";
    }
    
    
    
}
