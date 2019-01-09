/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.view;

import com.zapateria.main.dao.Servicio;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego
 */
public abstract class MarcoPersonalizado extends javax.swing.JFrame {
    
   
    private static final long serialVersionUID = -888205998328001485L;
    protected final Servicio servicio = new Servicio();

    protected void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    protected boolean mostrarDecision(String mensaje) {
        return JOptionPane.showConfirmDialog(null, mensaje) == JOptionPane.OK_OPTION;
    }
}
