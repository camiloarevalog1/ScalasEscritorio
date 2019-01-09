/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.view;

import com.zapateria.main.dao.Servicio;
import com.zapateria.main.dto.SesionDTO;
import com.zapateria.main.util.Sesion;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import static javax.swing.JInternalFrame.IS_CLOSED_PROPERTY;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author Diego
 */
public abstract class MarcoInternoPersonalizado extends JInternalFrame {

    protected static final SesionDTO SESION_USUARIO = Sesion.obtenerSesion();
    private static final long serialVersionUID = 4575518111619019589L;

    public MarcoInternoPersonalizado() {
        super();
    }

    protected static final String REGEX_DECIMAL = "-?(\\d+\\.\\d+|\\d+)", REGEX_ENTERO_POSITIVO = "\\d+";

    protected final Servicio servicio = new Servicio();

    protected void mostrarMensaje(String mensaje) {
        JOptionPane.showInternalMessageDialog(getParent().getComponent(0), mensaje);
    }

    protected boolean validarInt(String numero) {
        try {
            Integer.parseInt(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected boolean validarLong(String numero) {
        try {
            Long.parseLong(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected boolean validarDouble(String numero) {
        try {
            Double.parseDouble(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected boolean validarInt(Object numero) {
        return validarInt(String.valueOf(numero));
    }

    protected boolean validarLong(Object numero) {
        return validarLong(String.valueOf(numero));
    }

    protected boolean validarDouble(Object numero) {
        return validarDouble(String.valueOf(numero));
    }

    protected boolean mostrarDecision(String mensaje) {
        return JOptionPane.showInternalConfirmDialog(getDesktopPane().getComponent(0), mensaje) == JOptionPane.OK_OPTION;
    }

    protected void cerrar() {
        try {
            fireVetoableChange(IS_CLOSED_PROPERTY, Boolean.FALSE,
                    Boolean.TRUE);
            isClosed = true;
            setVisible(false);
            firePropertyChange(IS_CLOSED_PROPERTY, Boolean.FALSE,
                    Boolean.TRUE);
            dispose();
        } catch (PropertyVetoException pve) {
        }
    }

    protected void aplicarEventosPersonalizados(JTable tabla) {

        JViewport parent = (JViewport) tabla.getParent();
        JScrollPane scroll = (JScrollPane) parent.getParent();

        DefaultTableModel dtModel = (DefaultTableModel) tabla.getModel();

        tabla.setFocusable(true);

        tabla.setRowSelectionAllowed(true);

        // Agregando evento de ganancia de foco, para seleccionar la ultima fila de la misma
        tabla.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                //Seleccionando la última fila de la tabla
                if (tabla.getRowCount() > 0) {
                    tabla.setRowSelectionInterval(tabla.getRowCount() - 1, tabla.getRowCount() - 1);
                }

                // Bajando la barra del "scroll" de la tabla
                JScrollBar sb = scroll.getVerticalScrollBar();
                sb.setValue(sb.getMaximum());
            }

        });

        // Creando el "render" para la celda
        TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {
            private static final long serialVersionUID = -347184672129699492L;

            // Método de donde se obtiene las propiedades de la celda cuando se acciona el evento
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                // Obteniendo el objeto JLabel con lo cual se podrá cambiar las propiedades de la celda
                JLabel celda = (JLabel) super
                        .getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Aplicando colores de la celda que es selecionada y tiene foco
                if (isSelected == true
                        && row == table.getSelectedRow()
                        && column == table.getSelectedColumn()
                        && hasFocus) {

                    celda.setForeground(Color.WHITE);
                    celda.setBackground(new Color(000, 102, 204));

                } else {
                    // Aplicando colores de las celdas que son seleccionadas y sin foco
                    if (!hasFocus && isSelected) {
                        celda.setForeground(Color.WHITE);
                        celda.setBackground(new Color(051, 153, 255));
                    } else {
                        // Aplicando colores iniciales del resto de las celdas
                        celda.setBackground(Color.WHITE);
                        celda.setForeground(Color.BLACK);
                    }
                }
                return celda;
            }
        };

        // Aplicando el "render" a todas las columnas de la tabla
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.setDefaultRenderer(tabla.getColumnClass(i), tableCellRenderer);
        }

        // Agregando evento de tabulación
        tabla.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int filaSeleccionada = tabla.getSelectedRow();

                int codigoTecla = e.getExtendedKeyCode();
                // Si se oprime la tecla enter y es la última fila, crea una nueva fila vacía de la tabla
                if (dtModel.getRowCount() - 1 == filaSeleccionada && (codigoTecla == KeyEvent.VK_TAB || codigoTecla == KeyEvent.VK_ENTER)) {

                    Object obj = tabla.getValueAt(tabla.getRowCount() - 1, 0);
                    if (obj != null) {
                        tabla.setRowSelectionInterval(tabla.getRowCount() - 1, tabla.getRowCount() - 1);
                        tabla.setColumnSelectionInterval(0, 0);
                        dtModel.addRow(new String[]{});
                    }
                }
            }

        });

        if (tabla.getRowCount() > 0) {
            tabla.setRowSelectionInterval(tabla.getRowCount() - 1, tabla.getRowCount() - 1);
            tabla.setColumnSelectionInterval(0, 0);
        }

    }

    protected void agregarJComboBoxAColumna(JTable tabla, int numeroColumna, Object[] datos) {
        TableColumn columna = tabla.getColumnModel().getColumn(numeroColumna);
        JComboBox comboBox = new JComboBox(datos);
        DefaultCellEditor dce = new DefaultCellEditor(comboBox);
        dce.setClickCountToStart(2);
        columna.setCellEditor(dce);
    }

}
