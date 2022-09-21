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
public class Drive implements Comparable<Drive>{
    private String root;
    private File file;

    public Drive(String root, File file) {
        this.root = root;
        this.file = file;
    }

    public Drive(File file) {
        this.file = file;
        root = file.toString();
    }
    
    public boolean exists(){
        return file.exists();
    }

    public String getRoot() {
        return root;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return root;
    }

    @Override
    public int compareTo(Drive o) {
        return root.compareTo(o.root);
    }
    
}
