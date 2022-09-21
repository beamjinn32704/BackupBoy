package com.mindblown.backupper;


import java.io.File;
import java.io.FilenameFilter;
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
public class Register implements Cacheable<Register> {
    
    private static CacheScanner<Register> regScan = new CacheScanner<Register>() {
        
        private ArrayList<Registry> registries = new ArrayList<>();
        private String list;
        
        @Override
        public Register next() {
            Register reg = new Register(registries);
            return reg;
        }

        @Override
        public void analyze() {
            registries = Cacheable.convert(new Object[]{list}, Registry.getScanner());
        }

        @Override
        public ArrayList<Register> scanned() {
            System.err.println("WARNING! REGISTER CACHE SCANNER SCANNED METHOD CALLED! RETURN NULL");
            return null;
        }

        @Override
        public void setParams(Object[] p) {
            list = (String)p[0];
        }
    };
    private ArrayList<Registry> registries;

    public Register() {
        registries = new ArrayList<>();
    }

    public Register(ArrayList<Registry> registries) {
        this.registries = registries;
    }
    
    public static Register genRegister(File cache){
        if(!cache.isFile()){
            return new Register();
        }
        String list = Util.strip(Util.getText(cache));
        regScan.setParams(new Object[]{list});
        regScan.analyze();
        return regScan.next();
    }

    @Override
    public String cache() {
        String print = "";
        for(Registry r : registries){
            print += r.cache() + "\n";
        }
        return print;
    }
    
    public static RegistryRef getRegistryRef(Drive drive){
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.equals(Main.regFileName);
            }
        };
        File[] files = drive.getFile().listFiles(filter);
        if(files.length == 0){
            return null;
        }
        CacheScanner<RegistryRef> scan = RegistryRef.scanner();
        scan.setParams(new Object[]{Util.getText(files[0])});
        RegistryRef r = scan.next();
        if(r == null){
            return null;
        }
        return r;
    }
    
    public Registry getRegistry(Drive drive){
        RegistryRef r = getRegistryRef(drive);
        if(r == null || !has(r)){
            return null;
        }
        Registry reg = registries.get(Util.indexOf(registries, r));
        return reg;
    }
    
    public boolean has(RegistryRef ref){
        if(registries.isEmpty()){
            return false;
        }
        return Util.has(registries, ref);
    }
    
    public boolean registered(Drive drive){
        RegistryRef ref = Register.getRegistryRef(drive);
        if(ref == null){
            return false;
        }
        return has(ref);
    }
    
    public static boolean hasRegistry(Drive drive){
        return Register.getRegistryRef(drive) != null;
    }
    
    public void newDrive(Drive drive){
        Registry reg = getRegistry(drive);
        if(reg == null){
            System.out.println(drive + " isn't registered!");
            return;
        }
        ArrayList<FileAction> fileActions = reg.getFileActions();
        if(fileActions.isEmpty()){
            return;
        }
        int res = Util.confirm(null, "Would you like to do " + drive + " backups?", "Backup?");
        if(res == Util.yes){
            reg.backupAll();
        }
    }
    
    public void backupAll(){
        for(Registry reg : registries){
            ArrayList<FileAction> fileActions = reg.getFileActions();
            for(FileAction fa : fileActions){
                Backupper.backup(fa.getFile1(), fa.getFile2());
            }
        }
    }
    
    public void addRegistry(Drive drive){
        RegistryRef ref = getRegistryRef(drive);
        if(ref == null){
            System.out.println("DRIVE DOESN'T HAVE REGISTRY, Returning (Method: Register's addRegistry)");
            return;
        }
        Util.add(registries, new Registry(ref.getCode()));
        Cacheable.cache(this, new File(Main.registryName));
        System.out.println("REGISTERED!");
        Main.main.updateTable();
    }
    
    public void register(Drive drive){
        boolean go = true;
        RegistryRef reg = null;
        System.out.println("REGISTERING!");
        while(go){
            String code = Registry.genCode();
            reg = new RegistryRef(code);
            if(!has(reg)){
                go = false;
            }
        }
        File ref = new File(drive.getFile(), Main.regFileName);
        Cacheable.cache(reg, ref);
        Util.add(registries, new Registry(reg.getCode()));
        Cacheable.cache(this, new File(Main.registryName));
        System.out.println("REGISTERED!");
        Main.main.updateTable();
    }
    
    public void deregister(Drive drive){
        if(!registered(drive)){
            return;
        }
        File ref = new File(drive.getFile(), Main.regFileName);
        registries.remove(getRegistry(drive));
        if(!ref.delete()){
            System.out.println("UNSUCCESSFULLY DEREGISTERED " + drive.getRoot());
        } else {
            Cacheable.cache(this, new File(Main.registryName));
            System.out.println("DEREGISTERED!");
            Main.main.updateTable();
        }
    }
}