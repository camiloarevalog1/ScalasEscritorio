/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Diego
 */
public class TablaPersonalizada extends JTable {

    private static final long serialVersionUID = 5258196081126626243L;

    public TablaPersonalizada() {
        super();
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        //  Alternando color de la fila
        if (!isRowSelected(row)) {
            c.setBackground(row % 2 == 0 ? getBackground() : new Color(248, 248, 248));
        }
        return c;
    }

}
