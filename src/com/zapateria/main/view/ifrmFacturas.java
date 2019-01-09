/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.view;

/**
 *
 * @author JHON
 */
public class ifrmFacturas extends MarcoInternoPersonalizado {

    private static final String FACTURAS = "FACTURAS";
    private static final long serialVersionUID = -1466221196316245329L;

    /**
     * Creates new form ifrmFacturas
     */
    public ifrmFacturas() {
        initComponents();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        panelFacturas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbFacturas = new TablaPersonalizada();
        jLabel1 = new javax.swing.JLabel();
        dateFiltroFechaA = new com.github.lgooddatepicker.components.DatePicker();
        datePFiltroFechaB = new com.github.lgooddatepicker.components.DatePicker();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        comboFiltroEstado = new javax.swing.JComboBox<>();
        lbFiltroFormaDePago = new javax.swing.JLabel();
        comboFiltroFormaDePago = new javax.swing.JComboBox<>();
        lbFiltroCliente = new javax.swing.JLabel();
        comboFiltroBuscarCliente = new javax.swing.JComboBox<>();
        btnImprimirFactura = new javax.swing.JButton();
        btnVerProductosFactura = new javax.swing.JButton();

        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/icon2.png"))); // NOI18N

        tbFacturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Forma de pago", "Estado", "Fecha", "IVA", "Total a pagar", "Total pagado", "Fecha entrega", "Cliente", "Usuario"
            }
        ));
        jScrollPane1.setViewportView(tbFacturas);

        jLabel2.setText("Fecha realizacion:");

        jLabel3.setText("Fecha entrega:");

        jLabel4.setText("Estado:");

        comboFiltroEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DEPOSITO", "ENTREGADO", "PROCESO" }));

        lbFiltroFormaDePago.setText("Forma de pago:");

        comboFiltroFormaDePago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CONTADO", "CUOTAS" }));

        lbFiltroCliente.setText("Cliente:");

        comboFiltroBuscarCliente.setEditable(true);

        btnImprimirFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        btnImprimirFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirFacturaActionPerformed(evt);
            }
        });

        btnVerProductosFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA3.png"))); // NOI18N
        btnVerProductosFactura.setText("Productos");
        btnVerProductosFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerProductosFacturaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFacturasLayout = new javax.swing.GroupLayout(panelFacturas);
        panelFacturas.setLayout(panelFacturasLayout);
        panelFacturasLayout.setHorizontalGroup(
            panelFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFacturasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFacturasLayout.createSequentialGroup()
                        .addComponent(lbFiltroFormaDePago)
                        .addGap(18, 18, 18)
                        .addComponent(comboFiltroFormaDePago, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbFiltroCliente)
                        .addGap(18, 18, 18)
                        .addComponent(comboFiltroBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelFacturasLayout.createSequentialGroup()
                        .addGroup(panelFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFacturasLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1))
                            .addGroup(panelFacturasLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dateFiltroFechaA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel3)
                                .addGap(10, 10, 10)
                                .addComponent(datePFiltroFechaB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboFiltroEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelFacturasLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addGroup(panelFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelFacturasLayout.createSequentialGroup()
                                        .addComponent(btnImprimirFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(btnVerProductosFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap())))
        );
        panelFacturasLayout.setVerticalGroup(
            panelFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFacturasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateFiltroFechaA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(datePFiltroFechaB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(comboFiltroEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbFiltroFormaDePago)
                    .addComponent(comboFiltroFormaDePago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbFiltroCliente)
                    .addComponent(comboFiltroBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jLabel1)
                .addGap(10, 10, 10)
                .addGroup(panelFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                    .addGroup(panelFacturasLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(btnVerProductosFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnImprimirFactura)
                        .addGap(0, 135, Short.MAX_VALUE)))
                .addContainerGap())
        );

        if (SESION_USUARIO.isVistaPermitida(FACTURAS)) {

            jTabbedPane2.addTab("Facturas", panelFacturas);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnImprimirFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirFacturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnImprimirFacturaActionPerformed

    private void btnVerProductosFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerProductosFacturaActionPerformed

        int filaSeleccionada = tbFacturas.getSelectedRow();

        if (filaSeleccionada < 0) {
            mostrarMensaje("Seleccione una factura de la tabla");
            return;
        }

        Object idObj = tbFacturas.getValueAt(filaSeleccionada, 0);

        long id = Long.parseLong(String.valueOf(idObj));

        ifrmProductosPorFactura vistaProductosPorFactura = new ifrmProductosPorFactura(id);
        getDesktopPane().add(vistaProductosPorFactura);
        vistaProductosPorFactura.setVisible(true);
        vistaProductosPorFactura.setSize(471, 337);
    }//GEN-LAST:event_btnVerProductosFacturaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImprimirFactura;
    private javax.swing.JButton btnVerProductosFactura;
    private javax.swing.JComboBox<String> comboFiltroBuscarCliente;
    private javax.swing.JComboBox<String> comboFiltroEstado;
    private javax.swing.JComboBox<String> comboFiltroFormaDePago;
    private com.github.lgooddatepicker.components.DatePicker dateFiltroFechaA;
    private com.github.lgooddatepicker.components.DatePicker datePFiltroFechaB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lbFiltroCliente;
    private javax.swing.JLabel lbFiltroFormaDePago;
    private javax.swing.JPanel panelFacturas;
    private javax.swing.JTable tbFacturas;
    // End of variables declaration//GEN-END:variables
}
