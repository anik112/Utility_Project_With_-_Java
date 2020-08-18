/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import model.GetUtilityData;
import model.OraDbConnection;
import model.service.SQLList;
import model.service.TableSpaceInfo;

/**
 *
 * @author Anik
 */
public class Controller implements Core{

    private List<TableSpaceInfo> tableSpaceList=new ArrayList<>();
    private SQLList lList=new SQLList();
    private int SizeForUsers[]={60,65,70};
    
    
    
    @Override
    public void updateTableSpace(Connection connection) {
        Connection con=OraDbConnection.connection();
        GetUtilityData data=new GetUtilityData();
        tableSpaceList=data.getTableSpaceInfo(con);
        
        for (TableSpaceInfo tableSpaceInfo : tableSpaceList) {
            
        }
    }
    
    
    private int updateFinalSizeOfTableSpace(String tableSpaceName,float size){
        int updateSize=0;
        if(lList.TABLESPACE_NAMES.get(tableSpaceName)){
            
        }
        return updateSize;
    }
    
}
