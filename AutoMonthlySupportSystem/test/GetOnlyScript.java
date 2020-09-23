
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.GetUtilityData;
import model.OraDbConnection;
import model.SQLList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author VSI-ANIK
 */
public class GetOnlyScript {
    
    public void getScriptFormDatabase(){
        try {
            GetUtilityData data=new GetUtilityData();
            SQLList lList=new SQLList();
            
            ResultSet set = data.getDataFromSQL(OraDbConnection.connection(), "SELECT INDEX_NAME,TABLE_NAME,(INITIAL_EXTENT/1024) FROM ALL_INDEXES WHERE OWNER='PAYROLL'");            
            while(set.next()){
                System.out.println("\n");
                System.out.println(data.getIndexScript(OraDbConnection.connection(), set.getString(1))+";");
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetOnlyScript.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
