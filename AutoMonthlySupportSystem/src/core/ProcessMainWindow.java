/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javax.swing.SwingWorker;

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
        
        for(int i=0;i<5;i++){
            firePropertyChange("writeConsole", null, "> Update file "+i);
            System.out.println("----------- "+i);
            Thread.sleep(10);
        }
        
        return null;
    }
    
}
