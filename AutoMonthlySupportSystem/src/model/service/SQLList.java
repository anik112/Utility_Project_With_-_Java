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
    
    public final String TBS_USERS="USERS";
    public final String TBS_SYSTEM="SYSTEM";
    public final String TBS_SYSAUX="SYSAUX";
    public final String TBS_UNDOTBS1="UNDOTBS1";
    public final String TBS_SYSTEM02="SYSTEM";
    public final String TBS_USERS02="USERS";
    public final String USRPICTURE="USRPICTURE";
    public final String TEMP="TEMP";
    public final String INDX="INDX";
    public final String EXAMPLE="EXAMPLE";
    public String INDX_NAME="";
    public String TABLE_NAME="";
    
    public Map<String,int[]> TABLESPACE_SIZES=new HashMap<>();
    
    public final String SELECT_ALL_TABLE="SELECT TABLE_NAME FROM ALL_TABLES WHERE OWNER='PAYROLL'";
    public final String SELECT_ALL_INDEX="SELECT INDEX_NAME FROM ALL_INDEXES WHERE OWNER='PAYROLL'";
    public final String GET_ALL_TABLESPACE="SELECT DATA_FILES.TABLESPACE_NAME,DATA_FILES.FILE_NAME, DATA_FILES.BYTES/1024/1024 AS TOTAL_SIZE,\n" +
                                            "SUM(FREE_SPACE.BYTES)/1024/1024 AS FREE_SPACE \n" +
                                            "FROM DBA_DATA_FILES DATA_FILES, DBA_FREE_SPACE FREE_SPACE \n" +
                                            "WHERE DATA_FILES.FILE_ID=FREE_SPACE.FILE_ID \n" +
                                            "GROUP BY DATA_FILES.TABLESPACE_NAME,DATA_FILES.FILE_NAME, DATA_FILES.BYTES";
    public final String GET_TABLE_SCRIPT="SELECT DBMS_METADATA.GET_DDL('TABLE','"+TABLE_NAME+"') AS TABLE_SCR FROM DUAL";
    public String GRT_INDEX_SCRIPT="SELECT DBMS_METADATA.GET_DDL('INDEX','IDX_DATA_OUT') AS INDEX_SCR FROM DUAL";;
    
    
    public SQLList() {
        setAllSpaceSize();
    }

    public String updateTablespace(String dataFile, int size){
        return "ALTER DATABASE DATAFILE '"+dataFile+"' RESIZE "+size+"M";
    }
    
    private void setAllSpaceSize(){
        int[] sizeForUsers={60,65,70,75};
        int[] sizeForSystem={250};
        int[] sizeForSysax={68};
        int[] sizeForUndotbs1={80};
        
        TABLESPACE_SIZES.put(TBS_USERS, sizeForUsers);
        TABLESPACE_SIZES.put(TBS_SYSAUX, sizeForSysax);
        TABLESPACE_SIZES.put(TBS_SYSTEM, sizeForSystem);
        TABLESPACE_SIZES.put(TBS_UNDOTBS1, sizeForUndotbs1);
    }
    
}


//D:\ORACLE\PRODUCT\10.2.0\ORADATA\PAYROLL\EXAMPLE01.DBF
//D:\ORACLE\PRODUCT\10.2.0\ORADATA\PAYROLL\INDX01.DBF
//D:\ORACLE\PRODUCT\10.2.0\ORADATA\PAYROLL\SYSAUX01.DBF
//D:\ORACLE\PRODUCT\10.2.0\ORADATA\PAYROLL\SYSTEM01.DBF
//D:\ORACLE\PRODUCT\10.2.0\ORADATA\PAYROLL\SYSTEM02.DBF
//D:\ORACLE\PRODUCT\10.2.0\ORADATA\PAYROLL\TEMP01.DBF
//D:\ORACLE\PRODUCT\10.2.0\ORADATA\PAYROLL\UNDOTBS01.DBF
//D:\ORACLE\PRODUCT\10.2.0\ORADATA\PAYROLL\USERS01.DBF
//D:\ORACLE\PRODUCT\10.2.0\ORADATA\PAYROLL\USERS02.DBF
//D:\ORACLE\PRODUCT\10.2.0\ORADATA\PAYROLL\USRPICTURE01.DBF
