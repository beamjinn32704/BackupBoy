package com.mindblown.backupper;


import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author beamj
 */
public class RegistryFilesTableModel implements TableModel{
    
    private Registry registry;
    private String[] columnTemplate = new String[]{"From", "To"};

    public RegistryFilesTableModel(Registry registry) {
        this.registry = registry;
    }
    
    public void removeFileAction(FileAction fa){
        registry.removeFileAction(fa);
    }

    @Override
    public int getRowCount() {
        return registry.getFileActions().size();
    }

    @Override
    public int getColumnCount() {
        return columnTemplate.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnTemplate[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FileAction a = registry.getFileActions().get(rowIndex);
        if(columnIndex == 0){
            String name = a.getFile1().getName();
            if(Util.isBlank(name) || name.isEmpty()){
                return a.getFile1().toString();
            } else {
                return name;
            }
        } else {
            String name = a.getFile2().getName();
            if(Util.isBlank(name) || name.isEmpty()){
                return a.getFile2().toString(); //CLEAR REGISTER CACHE
            } else {
                return name;
            }
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        
    }
    
}
