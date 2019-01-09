/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diego
 */
public class Convertir {
    
    
    public static List<Object> toList(Object[] array) {
       List<Object> list = new ArrayList<>();
       
       for (Object o : array) {
           list.add(o);
       }
        
       return list; 
    } 
    
}
