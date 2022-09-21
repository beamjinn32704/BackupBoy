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
 * @param <T>
 */
public interface CacheScanner<T> {
    T next();
    void analyze();
    ArrayList<T> scanned();
    void setParams(Object[] p);
}
