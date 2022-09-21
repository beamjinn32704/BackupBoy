package com.mindblown.backupper;




import java.io.File;
import java.util.ArrayList;
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
public class DrivesTableModel implements TableModel {
    private ArrayList<Drive> drives = new ArrayList<>();
    private Register register;
    private String[] columnTemplate = new String[]{"Drives"};

    public DrivesTableModel(Register register) {
        this.register = register;
    }
    
    public void addDrive(Drive drive){
        Util.add(drives, drive);
    }
    
    public void removeDrive(Drive drive){
        drives.remove(drive);
    }
    
    public Drive getDrive(int ind){
        if(ind >= drives.size() || ind < 0){
            return null;
        }
        return drives.get(ind);
    }

    @Override
    public int getRowCount() {
        return drives.size();
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
        Drive drive = drives.get(rowIndex);
        boolean registered = register.registered(drive);
        if(registered){
            return drive + " - Registered";
        } else {
            return drive + " - UnRegistered";
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(!(aValue instanceof Drive || aValue instanceof String)){
            return;
        }
        if(aValue instanceof Drive){
            drives.set(rowIndex, (Drive) aValue);
        } else {
            drives.set(rowIndex, new Drive(new File((String) aValue)));
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        
    }
    
}
