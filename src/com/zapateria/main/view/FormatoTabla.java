/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author DELL
 */
public class FormatoTabla extends DefaultTableCellRenderer {

    private int hex(String color_hex) {
        return Integer.parseInt(color_hex, 16);
    }

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSelected, boolean hasFocus, int row, int column) {

        String ve = String.valueOf(jtable.getValueAt(row, 6));

        if (ve.equalsIgnoreCase("ENTREGADO")) {
            Color color = new Color(hex("45B40A"));
            setForeground(color);
            System.out.println(Integer.parseInt(String.valueOf(jtable.getValueAt(row, 0))) + "celda");
        } else if (ve.equalsIgnoreCase("DEPOSITO")) {
            Color color = new Color(hex("255ABD"));
            setForeground(color);
            System.out.println("red");
            System.out.println(Integer.parseInt(String.valueOf(jtable.getValueAt(row, 0))) + "celda");
        } else if (ve.equalsIgnoreCase("PROCESO")) {
            setForeground(Color.red);
        }

        return super.getTableCellRendererComponent(jtable, o, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
    }

}
