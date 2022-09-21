package com.mindblown.backupper;


import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author beamj
 */
public class MainFrame extends javax.swing.JFrame {
    
    private DrivesTableModel tableModel;
    private Register register;

    /**
     * Creates new form MainFrame
     * @param register
     */
    public MainFrame(Register register) {
        tableModel = new DrivesTableModel(register);
        initComponents();
        this.register = register;
    }
    
    public void start(){
        setVisible(true);
        Watcher watcher = new Watcher(register);
        watcher.watch();
    }
    
    public void addDrive(Drive drive){
        tableModel.addDrive(drive);
        updateTable();
    }
    
    public void cacheRegister(){
        Cacheable.cache(register, new File(Main.registryName));
    }
    
    public void removeDrive(Drive drive){
        tableModel.removeDrive(drive);
        updateTable();
    }
    
    public void updateTable(){
        drivesTable.revalidate();
        repaint();
    }
    
    public static boolean isDoubleClick(MouseEvent evt) {
        return evt.getClickCount() == 2;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        drivesTable = new javax.swing.JTable();
        backupAllButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        drivesTable.setModel(tableModel);
        drivesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                drivesTableMouseClicked(evt);
            }
        });
        drivesTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                drivesTableKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(drivesTable);

        backupAllButton.setText("Backup All");
        backupAllButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backupAllButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backupAllButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(backupAllButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void drivesTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drivesTableMouseClicked
        if(!isDoubleClick(evt)){
            return;
        }
        openDrivePanel();
    }//GEN-LAST:event_drivesTableMouseClicked

    private void drivesTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_drivesTableKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            System.out.println("ENTER");
            openDrivePanel();
        }
    }//GEN-LAST:event_drivesTableKeyPressed

    private void backupAllButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backupAllButtonMouseClicked
        register.backupAll();
    }//GEN-LAST:event_backupAllButtonMouseClicked


    private void openDrivePanel(){
        int row = drivesTable.getSelectedRow();
        Drive drive = tableModel.getDrive(row);
        DrivePanel drivePanel = new DrivePanel(drive, register);
        Util.message(this, drivePanel, drive.getRoot());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backupAllButton;
    private javax.swing.JTable drivesTable;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
