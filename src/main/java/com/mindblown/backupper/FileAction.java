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
public class FileAction {
    private File file1;
    private File file2;

    public FileAction(File file1, File file2) {
        this.file1 = file1;
        this.file2 = file2;
    }

    public File getFile1() {
        return file1;
    }
    
    public File getFile2() {
        return file2;
    }

    public void setFile1(File file1) {
        this.file1 = file1;
    }

    public void setFile2(File file2) {
        this.file2 = file2;
    }
    
    public boolean isReal(){
        return file1 != null && file2 != null;
    }

    @Override
    public String toString() {
        return file1.toString() + "\n" + file2.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof FileAction){
            FileAction fa = (FileAction) obj;
            return file1.equals(fa.file1) && file2.equals(file2);
        }
        return false;
    }
    
    
}
