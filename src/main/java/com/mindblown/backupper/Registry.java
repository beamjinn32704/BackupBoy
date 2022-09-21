package com.mindblown.backupper;


import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

/**
 *
 * @author beamj
 */
public class Registry implements Cacheable<Registry>, Comparable<Object>{
    private ArrayList<FileAction> fileActions;
    private String code;
    
    public static int codeLength = 9;
    
    public Registry(ArrayList<FileAction> fileActions, String code) {
        this.fileActions = fileActions;
        this.code = code;
    }
    
    public Registry(ArrayList<FileAction> fileActions) {
        this.fileActions = fileActions;
        code = genCode();
    }

    public Registry(String code) {
        fileActions = new ArrayList<>();
        this.code = code;
    }

    public Registry() {
        fileActions = new ArrayList<>();
        code = genCode();
    }

    public ArrayList<FileAction> getFileActions() {
        return fileActions;
    }
    
    public void addFileAction(FileAction fa){
        fileActions.add(fa);
    }
    
    public void removeFileAction(FileAction fa){
        fileActions.remove(fa);
    }
    
    public static String genCode(){
        long seed = System.currentTimeMillis();
        Random gen = new Random(seed);
        String code = "";
        //START CHARACTER
        int startChar = 48;
        //END CHARACTER
        int endChar = 58;
        for(int i = 0; i < codeLength; i++){
            int num = gen.nextInt(endChar - startChar) + startChar;
            char c = (char)num;
            if(num == 58){
                c = '@';
            }
            code += c;
        }
        return code;
    }
    
    public void backupAll(){
        for(FileAction fa : fileActions){
            Backupper.backup(fa.getFile1(), fa.getFile2());
        }
    }
    
    public String getCode() {
        return code;
    }
    
    public boolean matches(RegistryRef r){
        return code.equals(r.getCode());
    }
    
    @Override
    public String cache() {
        String print = "{" + code + "\n";
        for(int i = 0; i < fileActions.size(); i++){
            print += fileActions.get(i) + "\n";
        }
        print = Util.strip(print);
        print += "}";
        return print;
    }
    
    public static CacheScanner<Registry> getScanner(){
        return new CacheScanner<Registry>() {
            
            private String string;
            
            @Override
            public Registry next() {
                int startInd = string.indexOf("{");
                int endInd = string.indexOf("}", startInd+1);
                if(startInd == -1 || endInd == -1){
                    return null;
                }
                String check = string.substring(startInd+1, endInd);
                check = Util.strip(check);
                try(Scanner in = new Scanner(check)){
                    String code = in.nextLine();
                    code = Util.strip(code);
                    ArrayList<FileAction> fileActions = new ArrayList<>();
                    while(in.hasNextLine()){
                        try{
                            File file1 = new File(Util.strip(in.nextLine()));
                            File file2 = new File(Util.strip(in.nextLine()));
                            fileActions.add(new FileAction(file1, file2));
                        } catch(Exception e){
                            return new Registry(fileActions, code);
                        }
                    }
                    string = string.substring(endInd+1);
                    return new Registry(fileActions, code);
                } catch(Exception e){
                    System.err.println("BACKUPPER CACHE SCANNER NEXT METHOD ERROR! RETURNING NULL");
                    return null;
                }
            }
            
            @Override
            public void analyze() {
                
            }
            
            @Override
            public ArrayList<Registry> scanned() {
                System.out.println("WARNING: ACCESSING CACHESCANNER SCANNED METHOD. THIS IS UNSET!");
                return null;
            }
            
            @Override
            public void setParams(Object[] p) {
                string = (String)p[0];
            }
        };
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Registry){
            return code.compareTo(((Registry) o).code);
        } else if(o instanceof RegistryRef){
            return code.compareTo(((RegistryRef) o).getCode());
        } else {
            return -1;
        }
    }
}