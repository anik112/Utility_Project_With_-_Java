/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javax.swing.SwingWorker;
import model.OraDbConnection;

/**
 *
 * @author Anik
 */
public class ProcessMainWindow extends SwingWorker<Void, String> {
    
    private int key=0;

    public ProcessMainWindow(int key) {
        this.key=key;
    }
    
    
    @Override
    protected Void doInBackground() throws Exception {
        
        firePropertyChange("writeConsole", null, "\n> Start TABLESPACE Processing ... ");
        new Controller().updateTableSpace(OraDbConnection.connection());
        firePropertyChange("writeConsole", null, "\n> Update TABLESPACE Success !");
        
        
       
        for(int i=0;i<5;i++){
            
            
            Thread.sleep(200);
        }
        
        return null;
    }
    
}
