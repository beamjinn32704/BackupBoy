package com.mindblown.backupper;


import java.io.File;
import java.util.ArrayList;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

/**
 *
 * @author beamj
 */
public class Watcher {
    
    private File[] oldListRoots;
    private Register register;
    
    public Watcher(Register register) {
        oldListRoots = File.listRoots();
        this.register = register;
    }
    
    public void watch(){
        ArrayList<Drive> drives = new ArrayList<>();
        String systemDrive = System.getenv("SystemDrive");
        systemDrive = systemDrive.substring(0, systemDrive.indexOf(":") + 1);
        systemDrive += File.separator;
        for(int i = 0; i < oldListRoots.length; i++){
            Drive drive = new Drive(oldListRoots[i]);
            if(drive.getRoot().equals(systemDrive)){
                
            } else {
                Util.add(drives, drive);
                Main.main.addDrive(drive);
                register.newDrive(drive);
            }   
        }
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (File.listRoots().length > oldListRoots.length) {
                Object[] newDrives = Util.toArray(Util.diffSortElements(oldListRoots, File.listRoots()));
                oldListRoots = File.listRoots();
                for(int i = 0; i < newDrives.length; i++){
                    Drive drive = new Drive((File) newDrives[i]);
                    System.out.println("Drive " + drive + " detected");
                    Util.add(drives, drive);
                    Main.main.addDrive(drive);
                    register.newDrive(drive);
                }
            } else if (File.listRoots().length < oldListRoots.length) {
                for(int i = 0; i < drives.size(); i++){
                    Drive d = drives.get(i);
                    if(!d.exists()){
                        drives.remove(i);
                        System.out.println(d + " ejected.");
                        Main.main.removeDrive(d);
                    }
                }
                oldListRoots = File.listRoots();
            }
            
        }
    }
}
