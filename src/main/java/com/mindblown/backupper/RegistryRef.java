package com.mindblown.backupper;


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
public class RegistryRef implements Cacheable, Comparable<RegistryRef>{
    private String code;
    
    public RegistryRef() {
        code = Registry.genCode();
    }
    
    public RegistryRef(String code) {
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
    
    @Override
    public String cache() {
        return code;
    }
    
    public static CacheScanner<RegistryRef> scanner(){
        return new CacheScanner<RegistryRef>() {
            
            private String text;
            
            @Override
            public RegistryRef next() {
                text = Util.strip(text);
                int ind = text.indexOf("\n");
                String code;
                if(ind == -1){
                    code = text + "";
                } else {
                    code = text.substring(0, ind);
                    code = Util.strip(code);
                }
                if(code.isEmpty() || code.length() != Registry.codeLength){
                    text = "";
                    return null;
                } else {
                    text = text.substring(ind+1);
                    return new RegistryRef(code);
                }
            }
            
            @Override
            public void analyze() {
                
            }
            
            @Override
            public ArrayList<RegistryRef> scanned() {
                return null;
            }
            
            @Override
            public void setParams(Object[] p) {
                text = (String) p[0];
            }
        };
    }

    @Override
    public int compareTo(RegistryRef o) {
        return code.compareTo(o.code);
    }
}
