package model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Anik
 */
public class SQLList {
    
    public final String TBS_USERS="D:\\ORACLE\\PRODUCT\\10.2.0\\ORADATA\\PAYROLL\\USERS01.DBF";
    public final String TBS_SYSTEM="D:\\ORACLE\\PRODUCT\\10.2.0\\ORADATA\\PAYROLL\\SYSTEM01.DBF";
    public final String TBS_SYSAUX="D:\\ORACLE\\PRODUCT\\10.2.0\\ORADATA\\PAYROLL\\SYSAUX01.DBF";
    public final String TBS_UNDOTBS1="D:\\ORACLE\\PRODUCT\\10.2.0\\ORADATA\\PAYROLL\\UNDOTBS01.DBF";
    public final String TBS_SYSTEM02="D:\\ORACLE\\PRODUCT\\10.2.0\\ORADATA\\PAYROLL\\SYSTEM02.DBF";
    public final String TBS_USERS02="D:\\ORACLE\\PRODUCT\\10.2.0\\ORADATA\\PAYROLL\\USERS02.DBF";
    public final String TBS_USRPICTURE="D:\\ORACLE\\PRODUCT\\10.2.0\\ORADATA\\PAYROLL\\USRPICTURE01.DBF";
    public final String TBS_TEMP="D:\\ORACLE\\PRODUCT\\10.2.0\\ORADATA\\PAYROLL\\TEMP01.DBF";
    public final String TBS_INDX="D:\\ORACLE\\PRODUCT\\10.2.0\\ORADATA\\PAYROLL\\INDX01.DBF";
    public final String TBS_EXAMPLE="D:\\ORACLE\\PRODUCT\\10.2.0\\ORADATA\\PAYROLL\\EXAMPLE01.DBF";
    
    
    public Map<String,int[]> TABLESPACE_SIZES=new HashMap<>();
    
    public final String SELECT_INDEX_UPTO_16="SELECT INDEX_NAME,TABLE_NAME,(INITIAL_EXTENT/1024) FROM ALL_INDEXES WHERE OWNER='PAYROLL' AND INITIAL_EXTENT>16999";
    public final String SELECT_INDEX_UPTO_16_WITH_TABLESPACE="SELECT INDEX_NAME,TABLE_NAME,(INITIAL_EXTENT/1024),TABLESPACE_NAME FROM ALL_INDEXES WHERE OWNER='PAYROLL' AND INITIAL_EXTENT>16999";
    public final String SELECT_ALL_TABLE="SELECT TABLE_NAME FROM ALL_TABLES WHERE OWNER='PAYROLL'";
    public final String SELECT_TABLE_UPTO_16="SELECT TABLE_NAME FROM ALL_TABLES WHERE OWNER='PAYROLL' AND INITIAL_EXTENT>16999";
    public final String SELECT_ALL_INDEX="SELECT INDEX_NAME FROM ALL_INDEXES WHERE OWNER='PAYROLL'";
    public final String GET_ALL_TABLESPACE="SELECT DATA_FILES.TABLESPACE_NAME,DATA_FILES.FILE_NAME, DATA_FILES.BYTES/1024/1024 AS TOTAL_SIZE,\n" +
                                            "SUM(FREE_SPACE.BYTES)/1024/1024 AS FREE_SPACE \n" +
                                            "FROM DBA_DATA_FILES DATA_FILES, DBA_FREE_SPACE FREE_SPACE \n" +
                                            "WHERE DATA_FILES.FILE_ID=FREE_SPACE.FILE_ID \n" +
                                            "GROUP BY DATA_FILES.TABLESPACE_NAME,DATA_FILES.FILE_NAME, DATA_FILES.BYTES";
    public final String SET_LINE_SIZE="SET LINESIZE 200";
    public final String RESIZE_CURSOR_SIZE="alter system set open_cursors=3000 scope=both";
    
    public SQLList() {
        setAllSpaceSize();
    }

    public String updateTablespace(String dataFile, int size){
        return "ALTER DATABASE DATAFILE '"+dataFile+"' RESIZE "+size+"M";
    }
    
    public String updateTempSpace(String dataFile, int size){
        return "ALTER DATABASE TEMPFILE '"+dataFile+"' RESIZE "+size+"M";
    }
    
    private void setAllSpaceSize(){
        int[] sizeForUsers={61,76,86,96,106,111,121,131,136};
        int[] sizeForSystem={251};
        int[] sizeForSysax={69};
        int[] sizeForUndotbs1={81};
        int[] sizeForUsers02={51,61,66,81,86,91,96,101};
        int[] sizeForSystem02={201};
        int[] sizeForIndex={61,66,71,76,81,91,96,101,106,111};
        int[] sizeForUsrPicture={61};
        int[] sizeForTemp={951};
        
        TABLESPACE_SIZES.put(TBS_USERS, sizeForUsers);
        TABLESPACE_SIZES.put(TBS_USERS02, sizeForUsers02);
        TABLESPACE_SIZES.put(TBS_SYSAUX, sizeForSysax);
        TABLESPACE_SIZES.put(TBS_SYSTEM, sizeForSystem);
        TABLESPACE_SIZES.put(TBS_SYSTEM02, sizeForSystem02);
        TABLESPACE_SIZES.put(TBS_UNDOTBS1, sizeForUndotbs1);
        TABLESPACE_SIZES.put(TBS_INDX, sizeForIndex);
        TABLESPACE_SIZES.put(TBS_TEMP, sizeForTemp);
        TABLESPACE_SIZES.put(TBS_USRPICTURE, sizeForUsrPicture);
    }
    
    public String getIndexScript(String indexName){
        return "SELECT DBMS_METADATA.GET_DDL('INDEX','"+indexName+"') AS INDEX_SCR FROM DUAL";
    }
    public String getTableScript(String tableName){
        return "SELECT DBMS_METADATA.GET_DDL('TABLE','"+tableName+"') AS TABLE_SCR FROM DUAL";
    }
    
    public String getAlterIndexScriptForResize(String indexName,String tableSpace,int size){
        return "ALTER INDEX PAYROLL."+indexName
                + " REBUILD"
                + " NOCOMPRESS"
                + " NOPARALLEL"
                + " TABLESPACE "+tableSpace
                + " STORAGE (INITIAL "+size+"K)";
    }
    
    public String getTableRenameScript(String tableName){
        return "ALTER TABLE PAYROLL."+tableName+" RENAME TO "+tableName+"_X";
    }
    
    public String getInsertOneTblToAnotherScript(String newTable, String oldTable){
        return "INSERT INTO "+newTable+" SELECT * FROM "+oldTable;
    }
    
    public String getDeleteTableScript(String tableName){
        return "DROP TABLE "+tableName;
    }
    
    
   public String getIndexNameByTableScript(String tableName){
       return "SELECT INDEX_NAME FROM ALL_INDEXES WHERE OWNER='PAYROLL' AND TABLE_NAME='"+tableName+"'";
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
//SELECT INDEX_NAME,(INITIAL_EXTENT/1024) FROM ALL_INDEXES WHERE OWNER='PAYROLL'

/*INSERT 
INTO PAYROLL.SW_PARTS_RATE INS_TBL
(SERIAL_NO, BUYER_NAME, STYLE_NAME, SECTIONNM, 
GAUGE_NO, PARTS_NAME, PARTS_RATE, USENESS, 
USENESS_TM, USENESS_PT, USELESS, USELESS_TM, 
USELESS_PT, PROCESSBY, PROCESSDATE, REMARKS)
SELECT 
SERIAL_NO, BUYER_NAME, STYLE_NAME, SECTIONNM, 
GAUGE_NO, PARTS_NAME, PARTS_RATE, USENESS, 
USENESS_TM, USENESS_PT, USELESS, USELESS_TM, 
USELESS_PT, PROCESSBY, PROCESSDATE, REMARKS
FROM PAYROLL.SW_PARTS_RATE_X SEL_TBL ;*/

/*alter database datafile 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\PAYROLL\SYSTEM01.DBF' resize 600M
alter tablespace SYSTEM add datafile 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\PAYROLL\SYSTEM02.DBF' size 200M
alter database datafile 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\PAYROLL\USERS01.DBF' resize 200M
alter tablespace USERS add datafile 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\PAYROLL\USERS02.DBF' size 150M
alter database tempfile 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\PAYROLL\TEMP01.DBF' resize 950M
create tablespace INDX datafile 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\PAYROLL\INDX01.DBF' size 100M
create tablespace USRPICTURE datafile 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\PAYROLL\USRPICTURE01.DBF' size 100M
create user payroll identified by payroll
grant dba to payroll*/