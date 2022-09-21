package com.mindblown.backupper;




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
public class FileSetCreator extends javax.swing.JPanel {
    
    private FileAction fa;

    /**
     * Creates new form FileSetCreator
     * @param fa
     */
    public FileSetCreator(FileAction fa) {
        this.fa = fa;
        initComponents();
        file1Label.setText(getFile1Text());
        file2Label.setText(getFile2Text());
    }
    
    public FileAction getFileAction(){
        return fa;
    }
    
    private String getFile1Text(){
        if(fa.getFile1() == null){
            return "Current File: None";
        } else {
            File f = fa.getFile1();
            if(Util.isBlank(f.getName()) || f.getName().isEmpty()){
                return "Current File: " + f;
            } else {
                return "Current File: " + f.getName();
            }
        }
    }
    
    private String getFile2Text(){
        if(fa.getFile2() == null){
            return "Current File: None";
        } else {
            File f = fa.getFile2();
            if(Util.isBlank(f.getName())){
                return "Current File: " + f;
            } else {
                return "Current File: " + f.getName();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        file1Label = new javax.swing.JLabel();
        file1Button = new javax.swing.JButton();
        file2Label = new javax.swing.JLabel();
        file2Button = new javax.swing.JButton();

        setLayout(new java.awt.GridLayout(2, 2));

        file1Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        file1Label.setText("Current File: ");
        add(file1Label);

        file1Button.setText("Choose File to Copy");
        file1Button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                file1ButtonMouseClicked(evt);
            }
        });
        add(file1Button);

        file2Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        file2Label.setText("Current File: ");
        add(file2Label);

        file2Button.setText("Choose Where to Copy To");
        file2Button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                file2ButtonMouseClicked(evt);
            }
        });
        add(file2Button);
    }// </editor-fold>//GEN-END:initComponents

    private void file1ButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_file1ButtonMouseClicked
        fa.setFile1(Util.getFile(Util.filesAndDirs, "Choose File to Copy", fa.getFile1()));
        file1Label.setText(getFile1Text());
    }//GEN-LAST:event_file1ButtonMouseClicked

    private void file2ButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_file2ButtonMouseClicked
        fa.setFile2(Util.getFile(Util.dirsOnly, "Choose Where to Copy To", fa.getFile2()));
        file2Label.setText(getFile2Text());
    }//GEN-LAST:event_file2ButtonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton file1Button;
    private javax.swing.JLabel file1Label;
    private javax.swing.JButton file2Button;
    private javax.swing.JLabel file2Label;
    // End of variables declaration//GEN-END:variables
}
