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
public class TableName {
    
    private int id;
    private String tableName;
    private String tableOwner;
    private String tableScript;
    private String indexScript;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableOwner() {
        return tableOwner;
    }

    public void setTableOwner(String tableOwner) {
        this.tableOwner = tableOwner;
    }

    public String getTableScript() {
        return tableScript;
    }

    public void setTableScript(String tableScript) {
        this.tableScript = tableScript;
    }

    public String getIndexScript() {
        return indexScript;
    }

    public void setIndexScript(String indexScript) {
        this.indexScript = indexScript;
    }
    
    
    
    
}
