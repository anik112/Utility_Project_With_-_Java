
import core.Controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.OraDbConnection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Anik
 */
public class TEST {

    public static void main(String[] arg) {

        Connection con = OraDbConnection.connection();

//        try {
//            CallableStatement callableStmt = con.prepareCall("{call dbms_output.enable(32000)}");
//            callableStmt.execute();
//
//            String sql = "BEGIN\n"
//                    + "   DBMS_OUTPUT.put_line (\n"
//                    + "   DBMS_METADATA.get_ddl ('TABLE', 'TB_COMPANY_INFO')||\n"
//                    + "	  DBMS_METADATA.GET_DDL('INDEX','IDX_COMPANY_INFO')\n"
//                    + "	  );\n"
//                    + "END;";
//            
//            PreparedStatement ps=con.prepareCall(sql);
//            ps.executeUpdate();
//            
//            callableStmt=con.prepareCall("{call dbms_output.get_line(?,?)}");
//            callableStmt.registerOutParameter(1, Types.VARCHAR);
//            List<String> list = new Controller(0).getTableList(con);
//            List<String> indexNames=new Controller(0).getIndexList(con);
//
//            for (String tableName : list) {
//                String sql1 = "select DBMS_METADATA.get_ddl ('TABLE', '"+tableName+"') as tbale_data from dual";
//                PreparedStatement ps = con.prepareStatement(sql1);
//                ResultSet rs = ps.executeQuery();
//                while (rs.next()) {
//                    String tableScript = rs.getString(1);
//                    System.out.println("-- TABLE ---\n" + tableScript);
//                    if (tableScript.contains("BLOB")) {
//                        System.out.println("==> That table contain Image File");
//                    }
//                }
//                System.out.println(" ======================================== ");
//            }
//            
//            for (String indexName : indexNames) {
//                String sql1 = "select DBMS_METADATA.GET_DDL('INDEX','"+indexName+"') as index_data from dual";
//                PreparedStatement ps = con.prepareStatement(sql1);
//                ResultSet rs = ps.executeQuery();
//                while (rs.next()) {
//                    String indexScrept = rs.getString(1);
//                    int loc = indexScrept.indexOf("INITIAL");
//                    loc += 8;
//                    System.out.println("Loc of INITIAL: " + loc);
//                    String tempStr = indexScrept.substring(loc, loc + 5);
//                    System.out.println(tempStr);
//                    indexScrept = indexScrept.replace(tempStr, "64000");
//                    System.out.println("-- INEX ---\n" + indexScrept);
//                }
//                System.out.println(" ======================================== ");
//            }
//            
//
//        } catch (SQLException ex) {
//            Logger.getLogger(TEST.class.getName()).log(Level.SEVERE, null, ex);
//        }
        System.out.println(Math.round(12.90));
        System.out.println(Math.round((int) 12.90));
    }

}
