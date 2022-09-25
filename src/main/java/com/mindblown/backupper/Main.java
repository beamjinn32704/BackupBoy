package com.mindblown.backupper;




import java.io.File;


public class Main {
    public static String regFileName = "reg.backup-reg";
    public static String registryName = "registry.backup-register";
    public static MainFrame main;
    
    public static void main(String[] args) {
        File regFile = new File(Main.registryName);
        Register register = Register.genRegister(regFile);
        main = new MainFrame(register);
        main.start();
    }
}
