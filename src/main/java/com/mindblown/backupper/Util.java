package com.mindblown.backupper;


import java.awt.Component;
import java.awt.Desktop;
import java.awt.Image;
import java.io.File;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

/**
 *
 * @author beamj
 */
public class Util {
    
    public static int filesOnly = JFileChooser.FILES_ONLY;
    public static int dirsOnly = JFileChooser.DIRECTORIES_ONLY;
    public static int filesAndDirs = JFileChooser.FILES_AND_DIRECTORIES;
    public static int yes = JOptionPane.YES_OPTION;
    public static int no = JOptionPane.NO_OPTION;
    
    public static String currentWriterFileName = "";
    
    
    
    
    public static <T> Object[] toArray(ArrayList<T> list){
        return list.toArray();
    }
    
    public static int[] toIntArray(ArrayList<Integer> list){
        int[] a = new int[list.size()];
        for(int i = 0; i < a.length; i++){
            a[i] = list.get(i);
        }
        return a;
    }
    
    public static void runBatch(File batch) throws Exception {
        Runtime rt = Runtime.getRuntime();
        rt.exec("cmd /c start " + batch);
    }
    
    public static File getFile(int selectionMode, String title) {
        return getFile(selectionMode, title, null);
    }
    
    public static File getFile(int selectionMode, String title, File f) {
        return getFile(selectionMode, title, null, f);
    }
    
    public static File getFile(int selectionMode, String title, FileFilter filter, File f) {
        File file = null;
        JFileChooser chooser = new JFileChooser();
        if(filter != null){
            chooser.setFileFilter(filter);
        }
        chooser.setFileSelectionMode(selectionMode);
        chooser.setDialogTitle(title);
        if(f != null){
            chooser.setSelectedFile(f);
        }
        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }
        return file;
    }
    
    public static boolean isImg(File img){
        try {
            Image image = ImageIO.read(img);
            return image != null;
        } catch(Exception e) {
            return false;
        }
    }
    
    public static int objectExists(Object[] o, Object obj){
        for(int i = 0; i < o.length; i++){
            if(o[i] == obj){
                return i;
            }
        }
        return -1;
    }
    
    public static int objectExists(Object[] o, Object obj, boolean rel){
        for(int i = 0; i < o.length; i++){
            if(rel){
                if(o[i].equals(obj)){
                    return i;
                }
            } else {
                if(o[i] == obj){
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static String nextAlpha(String text){
        return nextAlpha(text, 0);
    }
    
    public static String nextAlpha(String text, int start){
        for(int i = start ; i < text.length(); i++){
            char c = text.charAt(i);
            if(Character.isAlphabetic(c)){
                return c + "";
            }
        }
        return null;
    }
    
    public static String getText(File file){
        if(!file.isFile()){
            return "";
        }
        String text = "";
        try(Scanner in = new Scanner(file)){
            while(in.hasNextLine()){
                text += in.nextLine();
                text += "\n";
            }
        } catch (Exception ex) {
            return null;
        }
        return text;
    }
    
    public static String removeLines(String s){
        String string = Util.strip(s);
        String text = "";
        for(int i = 0; i < string.length(); i++){
            String str = string.substring(i, i+1);
            if(!(Util.isBlank(str) && !str.equals(" "))){
                text += str;
            }
        }
        return text;
    }
    
    public static void print(ArrayList<Object> list){
        for(Object o : list){
            System.out.println(o);
        }
    }
    
    public static <T extends Comparable> int indexOf(ArrayList<? extends Comparable<? super T>> list, T key){
        return Collections.binarySearch(list, key);
    }
    
    public static <T extends Comparable> boolean has(ArrayList<? extends Comparable<? super T>> list, T key){
        return indexOf(list, key) > -1;
    }
    
    public static <T extends Comparable> int indexOf(T[] list, T key){
        return Arrays.binarySearch(list, key);
    }
    
    public static <T extends Comparable> boolean has(T[] list, T key){
        return indexOf(list, key) > -1;
    }
    
    public static <T extends Comparable<? super T>> boolean add(ArrayList<T> list, T key){
        int ind = Util.indexOf(list, key);
        if(ind >= 0){
            return false;
        } else {
            ind = -1 * (ind + 1);
            list.add(ind, key);
            return true;
        }
    }
    
    public static boolean isBlank(String str){
        return Util.strip(str).length() == 0;
    }
    
    public static String strip(String s){
        return s.trim();
    }
    
    public static <T extends Comparable> ArrayList<T> diffSortElements(T[] old, T[] newer){
        ArrayList<T> ts = new ArrayList<>();
        for(int i = 0; i < newer.length; i++){
            T t = newer[i];
            if(!has(old, t)){
                ts.add(t);
            }
        }
        return ts;
    }
    
    public static void message(Component frame, Object message, String title){
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.PLAIN_MESSAGE);
    }
    
    public static int confirm(Component frame, Object message, String title){
        return JOptionPane.showConfirmDialog(frame, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
    }
    
    
    public static JDialog showPan(JPanel pan, String title){
        return showPan(pan, title, JDialog.DISPOSE_ON_CLOSE);
    }
    
    public static JDialog showPan(JPanel pan, String title, int close){
        JOptionPane pane = new JOptionPane(pan, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{});
        JDialog dial = new JDialog();
        dial.setTitle(title);
        dial.setContentPane(pane);
        dial.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
        dial.setDefaultCloseOperation(close);
        dial.pack();
        return dial;
    }
    
    public static boolean open(File file){
        try {
            Desktop.getDesktop().open(file);
        } catch (Exception e){
            try {
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", file.toString()});
            } catch(Exception ex){
                return false;
            }
        }
        return true;
    }
    
    public static boolean open(String url){
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e){
            try {
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", url});
            } catch(Exception ex){
                return false;
            }
        }
        return true;
    }
}