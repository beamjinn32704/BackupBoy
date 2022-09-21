package com.mindblown.backupper;




import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author beamj
 * @param <T>
 */
public interface Cacheable<T> {
    String cache();
    
    public static <T extends Cacheable> void cache(ArrayList<T> list, File to){
        String print = "";
        boolean first = true;
        for(T t : list){
            if(!first){
                print += " "; 
            } else {
                first = false;
            }
            print += t.cache();
        }
        cache(print, to);
    }
    
    public static <T extends Cacheable> void cache(T t, File to){
        cache(t.cache(), to);
    }
    
    public static void cache(String print, File to){
        try(PrintWriter writer = new PrintWriter(to)){
            writer.println(print);
        } catch (Exception ex) {
            System.out.println("UNABLE TO CACHE!");
        }
    }
    
    public static <T extends Cacheable> ArrayList<T> convert(Object[] params, CacheScanner<T> scanner){
        ArrayList<T> converted = new ArrayList<>();
        scanner.setParams(params);
        boolean go = true;
        while(go){
            T next = scanner.next();
            if(next == null){
                return converted;
            } else {
                converted.add(next);
            }
        }
        return converted;
    }
    
}
