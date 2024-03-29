/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlrunnerinoracle.core;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import sqlrunnerinoracle.connection.DBConnection;
import sqlrunnerinoracle.model.GetSQLDataFromDatabase;

/**
 *
 * @author VSI SERVER
 */
public class ProcessToMakeXlsFile extends SwingWorker<Boolean, Integer> {

    private String sql = "";
    private String savingLoc = "";

    public ProcessToMakeXlsFile(String sql, String svingLoc) {
        this.sql = sql;
        this.savingLoc = svingLoc;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        Connection connection = DBConnection.getConnection(); // get database connection
        // make result set from get data fomr databse
        ResultSet dataSet = new GetSQLDataFromDatabase().getSqlDataFromDatabase(connection, sql);
        int rsSize = 400;

        // call Workbook class for make xls sheet
        /*
        Procedure of make xls file
        1. Create Workbook
        2. Create Sheet
        3. Create Row by sheet.createRow() method
        4. Create Col by row.createCell() method
        5. Write using FOS and workbook.write(fos) method
        
        // XSSFWorkbook for xlsx, HSSFWorkbook for xls format.
         */
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(); // create sheet

        try {
            // Get resultset meta data for find out column name
            ResultSetMetaData metaData = dataSet.getMetaData();
            int rowIndex = 0; // make row index
            Row row = sheet.createRow(rowIndex); // make Header row
            // Make header cell list like
            // Cardno, EmpName, Disc, Salary ect
            Cell cellHeader[] = new Cell[metaData.getColumnCount()];
            int cellIndex = 0; // call cell index
            // Run loop to find out how many column in this sql
            while (cellIndex != metaData.getColumnCount()) {
                cellHeader[cellIndex] = row.createCell(cellIndex);// Make cell header
                cellHeader[cellIndex].setCellValue(metaData.getColumnName(cellIndex + 1)); // Set value
                cellIndex++;
            }
            // Run loop to find out how many row execute in this sql
            while (dataSet.next()) {
                rowIndex++; // increment row index until last row of this set
                Row rowCh = sheet.createRow(rowIndex); // make row
                Cell cell[] = new Cell[metaData.getColumnCount()]; // make cell
                cellIndex = 0; // call cell index
                // Run loop to write column data from this sql
                while (cellIndex != metaData.getColumnCount()) {
                    cell[cellIndex] = rowCh.createCell(cellIndex);
                    String data=dataSet.getString(cellIndex + 1);
                    if(metaData.getColumnType(cellIndex)==91){
                        System.out.println("-> "+data);
                        //String date=data.substring(8, 10)+"/"+data.substring(5, 7)+"/"+data.substring(0, 4);
                    }
                    cell[cellIndex].setCellValue(data);
                    cellIndex++;
                }
//                System.out.println(rowIndex + " -- " + dataSet.getString(1)
//                        + " ---- " + dataSet.getString(2)
//                        + " ---- " + dataSet.getString(3));
                firePropertyChange("writeXLS", 0, (rowIndex / rsSize) * 100);
                System.out.println("==> create new row.");
            }
            //System.out.println("Total Row Patch: " + rowIndex);

            FileOutputStream fos = new FileOutputStream(savingLoc); // Open file by FOS
            workbook.write(fos); // write data in xls file
            JOptionPane.showMessageDialog(
                    null, "Save Data In \n" + savingLoc,
                    "Total Export Data: " + rowIndex, JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(CoreSQLEditeor.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(
                    null, "FN: [btnExportInXlsClick] \n" + ex.getMessage(),
                    ":: Function Error-101 :: ", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

}
