package com.mindblown.backupper;




import java.io.File;
import javax.swing.JDialog;
import javax.swing.JOptionPane;



public class Backupper {
    public static void backup(File from, File to){
        if(!from.exists() || !to.isDirectory()){
            System.out.println("BACKUP FILES DON'T EXIST");
            return;
        }
        String toString = to.getName();
        if(Util.isBlank(toString) || toString.isEmpty()){
            toString = to.toString();
        }
        CopyPanel pan = new CopyPanel(from.getName(), toString);
        JOptionPane pane = new JOptionPane(pan, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{});
        JDialog dial = new JDialog();
        dial.setTitle("Backing up...");
        dial.setContentPane(pane);
        dial.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
        dial.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dial.pack();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Copier.copy(from, to, true, pan);
                dial.dispose();
            }
        }).start();
        dial.setVisible(true);
    }
}