/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.test;

import com.zapateria.test.view.Marco;
import javax.swing.JFrame;

/**
 *
 * @author diego
 */
public class Zapateria {

    public Zapateria() {

    }

    public void pruebas() {
        JFrame marco = new Marco();

        marco.setVisible(true);
        marco.setResizable(true);
        
        marco.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        

    }

    public static void main(String[] args) {
        new Zapateria().pruebas();
    }

}
