/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.view;

import com.zapateria.main.dto.FacturaDTO;
import com.zapateria.main.dto.MaterialDTO;
import com.zapateria.main.dto.RegistroDTO;
import com.zapateria.main.dto.VentaDTO;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Diego
 */
public class ifrmProductosPorFactura extends MarcoInternoPersonalizado {

    private static final long serialVersionUID = 1024653378505981710L;

    private DefaultTableModel dtbmVentas;
    private long idFactura = 0;
    FormatoTabla f= new FormatoTabla();

    /**
     * Creates new form ifrmVentasPorFactura
     *
     * @param idFactura
     */
    public ifrmProductosPorFactura(long idFactura) {

        this.idFactura = idFactura;

        initComponents();
    }

    public ifrmProductosPorFactura() {
        initComponents();
    }
    DecimalFormat formateador = new DecimalFormat("###,###.###");

    private void actualizarTablaVentas() {
        tbVentas.removeAll();
        dtbmVentas = null;
        llenarTablaVentas();

    }

    private void llenarTablaVentas() {
        final String[] columnas = {"N° Item", "Cliente", "Fecha","Total a pagar",
             "Fecha Entrega","Material","Estado","Codigo Remision","Comentario"};

        // Configurando los eventos de edicion de la tabla
        dtbmVentas = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            public boolean isCellEditable(int row, int column) {
                return column == 6 || column==8 || column==5;
            }
        };
        // Llenando la tabla
        servicio.listar(idFactura).forEach((RegistroDTO venta) -> {
                dtbmVentas.addRow(venta.toArray());
                tbVentas.setDefaultRenderer(Object.class, f);
        });
        //Agregando el modelo de la tabla
        tbVentas.setModel(dtbmVentas);
        
        tbVentas.getColumnModel().getColumn(0).setMaxWidth(60);
        tbVentas.getColumnModel().getColumn(1).setMaxWidth(100);
        tbVentas.getColumnModel().getColumn(2).setMaxWidth(90);
        tbVentas.getColumnModel().getColumn(3).setMaxWidth(50);
        tbVentas.getColumnModel().getColumn(4).setMaxWidth(80);
        tbVentas.getColumnModel().getColumn(5).setMaxWidth(90);
        tbVentas.getColumnModel().getColumn(6).setMaxWidth(90);
        tbVentas.getColumnModel().getColumn(7).setMaxWidth(60);
        
        tbVentas.getColumnModel().getColumn(8).setMaxWidth(470);

        dtbmVentas.addTableModelListener((TableModelEvent e) -> {

            long id;
//            long cantidad;

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tbVentas.getValueAt(e.getFirstRow(), 0);
                String formaDePago = String.valueOf(tbVentas.getValueAt(e.getFirstRow(), 1));

                String estado = String.valueOf(tbVentas.getValueAt(e.getFirstRow(), 6));
                String material = String.valueOf(tbVentas.getValueAt(e.getFirstRow(), 5));
                String Comentario = String.valueOf(tbVentas.getValueAt(e.getFirstRow(), 8));

                if (objId != null) {
                    id = Long.parseLong(String.valueOf(objId));
                    MaterialDTO mate=servicio.consultarMaterialNombre(material);
                    servicio.Material(id, mate.getId());
                    if(servicio.cambiarComentario(id, Comentario)){
                        mostrarMensaje("Se ha edito");
                        actualizarTablaVentas();
                    }
                    if (servicio.cambiarEstado(id, estado)) {

//                        mostrarMensaje("La factura se ha editado satisfactoriamente.");
                        actualizarTablaVentas();
                        // actualizarTablaFacturas();
//                    calcularTotales();
                    } else {
                        mostrarMensaje("No se pudo editar la factura.");
                    }
                } else {
//                    if (servicio.registrarProducto(nombre, precioUnitario)) {
//                        actualizarTablaProductos();
//                        mostrarMensaje("Se ha registrado la producto satisfactoriamente");
//                    } else {
//                        mostrarMensaje("No se pudo registrar la producto");
//                    }
                }

            }
        });
        ArrayList<String> a = new ArrayList<>();
        a.add("DEPOSITO");
        a.add("ENTREGADO");
        a.add("PROCESO");
        ArrayList<MaterialDTO>m=servicio.listarMateriales();
        agregarJComboBoxAColumna(tbVentas, 6, a.toArray());
        agregarJComboBoxAColumna(tbVentas, 5, m.toArray());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCerrar = new javax.swing.JButton();
        panelVentas = new javax.swing.JPanel();
        spVentas = new javax.swing.JScrollPane();
        tbVentas = new TablaPersonalizada();

        setClosable(true);
        setTitle("Ventas por factura");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/icon2.png"))); // NOI18N
        setMaximumSize(new java.awt.Dimension(312, 151));
        setMinimumSize(new java.awt.Dimension(471, 337));
        setPreferredSize(new java.awt.Dimension(471, 337));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        panelVentas.setBorder(javax.swing.BorderFactory.createTitledBorder("Ventas de la factura"));

        tbVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Producto", "Cantidad", "Comentario", "Estado"
            }
        ));
        spVentas.setViewportView(tbVentas);

        javax.swing.GroupLayout panelVentasLayout = new javax.swing.GroupLayout(panelVentas);
        panelVentas.setLayout(panelVentasLayout);
        panelVentasLayout.setHorizontalGroup(
            panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVentasLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(spVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        panelVentasLayout.setVerticalGroup(
            panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVentasLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(spVentas, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelVentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 917, Short.MAX_VALUE)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(panelVentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(btnCerrar)
                .addGap(10, 10, 10))
        );

        getAccessibleContext().setAccessibleName("Usar materiales");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        cerrar();
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        llenarTablaVentas();
    }//GEN-LAST:event_formInternalFrameOpened


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JPanel panelVentas;
    private javax.swing.JScrollPane spVentas;
    private javax.swing.JTable tbVentas;
    // End of variables declaration//GEN-END:variables
}
