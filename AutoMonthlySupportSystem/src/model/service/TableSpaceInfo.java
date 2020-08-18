/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.service;

/**
 *
 * @author Anik
 */
public class TableSpaceInfo {
    
    private String tableSpaceName;
    private String fileName;
    private float totalSpace=0;
    private float freeSpace=0;
    private float userdSpace=0;

    public String getTableSpaceName() {
        return tableSpaceName;
    }

    public void setTableSpaceName(String tableSpaceName) {
        this.tableSpaceName = tableSpaceName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public float getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(float totalSpace) {
        this.totalSpace = totalSpace;
    }

    public float getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(float freeSpace) {
        this.freeSpace = freeSpace;
    }

    public float getUserdSpace() {
        return this.totalSpace-this.freeSpace;
    }
    
    
}
