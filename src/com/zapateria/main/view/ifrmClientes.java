/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.view;

import com.zapateria.main.util.BuscadorCarpeta;
import com.zapateria.main.util.Conexion;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Diego
 */
public class ifrmClientes extends MarcoInternoPersonalizado {

    private static final long serialVersionUID = 2258734246131279227L;

    private DefaultTableModel dtblmClientes;

    /**
     * Creates new form ifrmNomina
     */
    public ifrmClientes() {
        initComponents();
        actualizarTablaClientes();
        this.setSize(600, 600);
        System.out.println("clienteeeees");
    }

    private void llenarTablaClientes() {
        final String[] camposPermisos = {"Codigo", "Documento", "Nombres", "Apellidos", "Dirección", "Teléfono", "Fecha registro"};

        // Configurando los eventos de edicion de la tabla
        dtblmClientes = new DefaultTableModel(null, camposPermisos) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0 && column < 6;
            }
        };
        // Llenando la tabla
        servicio.listarClientes().forEach((cliente) -> {
            dtblmClientes.addRow(cliente.toArray());
        });
        //Agregando el modelo de la tabla
        tblClientes.setModel(dtblmClientes);
        
        tblClientes.getColumnModel().getColumn(0).setMaxWidth(60);
        tblClientes.getColumnModel().getColumn(1).setMaxWidth(90);
        tblClientes.getColumnModel().getColumn(2).setMaxWidth(170);
        tblClientes.getColumnModel().getColumn(3).setMaxWidth(170);
        tblClientes.getColumnModel().getColumn(4).setMaxWidth(560);
        tblClientes.getColumnModel().getColumn(5).setMaxWidth(170);
        tblClientes.getColumnModel().getColumn(6).setMaxWidth(100);

        dtblmClientes.addTableModelListener((TableModelEvent e) -> {
            long id = 0;
            String documento = "", nombres = "", apellidos = "", direccion = "";
            String telefono = "";

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tblClientes.getValueAt(e.getFirstRow(), 0);
                Object objDocumento = tblClientes.getValueAt(e.getFirstRow(), 1);
                Object objNombres = tblClientes.getValueAt(e.getFirstRow(), 2);
                Object objApellidos = tblClientes.getValueAt(e.getFirstRow(), 3);
                Object objDireccion = tblClientes.getValueAt(e.getFirstRow(), 4);
                Object objTelefono = tblClientes.getValueAt(e.getFirstRow(), 5);

                if (
                         objNombres == null
                        || objApellidos == null
                        
                                    ) {
                    return;
                }
                nombres = String.valueOf(objNombres);
                apellidos = String.valueOf(objApellidos);
                direccion = String.valueOf(objDireccion);
                documento = String.valueOf(objDocumento);
                telefono=String.valueOf(objTelefono);
             

                if (objId != null) {
                    id = Long.parseLong(String.valueOf(objId));

                    if (servicio.editarCliente(id, documento, nombres, apellidos, direccion, telefono)) {
                        actualizarTablaClientes();
                        System.out.println("Se ha editado el cliente satisfactoriamente");
                    } else {
                        System.out.println("No se pudo editar el cliente");
                    }
                } else  {
//                    if (!servicio.validarCedula(documento)) {
                        if (servicio.registrarCliente(documento, nombres, apellidos, direccion, telefono)) {
                            actualizarTablaClientes();
                            mostrarMensaje("Se ha registrado el cliente satisfactoriamente");
                        }
//                    } else {
//                        mostrarMensaje("El cliente ya existe");
//                    }
                }

            }
        });

        aplicarEventosPersonalizados(tblClientes);
    }

    private void actualizarTablaClientes() {
        tblClientes.removeAll();
        dtblmClientes = null;
        llenarTablaClientes();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelClientes = new javax.swing.JPanel();
        spClientes = new javax.swing.JScrollPane();
        tblClientes = new TablaPersonalizada();
        btnNuevo = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setBackground(new java.awt.Color(255, 255, 255));
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/icon2.png"))); // NOI18N
        setMaximumSize(new java.awt.Dimension(583, 335));
        setMinimumSize(new java.awt.Dimension(583, 335));
        setPreferredSize(new java.awt.Dimension(583, 335));
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

        panelClientes.setBackground(new java.awt.Color(255, 255, 255));
        panelClientes.setBorder(javax.swing.BorderFactory.createTitledBorder("Clientes"));

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spClientes.setViewportView(tblClientes);

        javax.swing.GroupLayout panelClientesLayout = new javax.swing.GroupLayout(panelClientes);
        panelClientes.setLayout(panelClientesLayout);
        panelClientesLayout.setHorizontalGroup(
            panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
        );
        panelClientesLayout.setVerticalGroup(
            panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientesLayout.createSequentialGroup()
                .addComponent(spClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/agregarCli.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/eliminarCli.png"))); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/SALIR.png"))); // NOI18N
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/amistad-amigos-amigas-046.png"))); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(97, 97, 97))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        cerrar();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = tblClientes.getSelectedRow();

        if (filaSeleccionada < 0) {
            mostrarMensaje("Seleccione un cliente de la tabla");
            return;
        }

        if (!mostrarDecision("¿Desea eliminar este cliente?")) {
            return;
        }

        Object idObj = tblClientes.getValueAt(filaSeleccionada, 0);

        if (idObj == null) {
            dtblmClientes.removeRow(filaSeleccionada);
            return;
        }

        int id = Integer.valueOf(String.valueOf(idObj));
        
        if (servicio.eliminarCliente(id)) {
            actualizarTablaClientes();
            mostrarMensaje("Cliente eliminado satisfactoriamente");
        } else {
            mostrarMensaje("No se pudo eliminar el cliente");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        dtblmClientes.addRow(new Object[]{});
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        llenarTablaClientes();
    }//GEN-LAST:event_formInternalFrameOpened

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
              String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String co = b.buscar(CARPETA);
        int cantidad= tblClientes.getRowCount();
        int c=0;
        while(c<cantidad){
            Object objectId = tblClientes.getValueAt(c, 0);
            int numero = Integer.parseInt(String.valueOf(objectId));
            cadena += numero + ","+ 0;
            c++;
        }
        System.out.println(cadena);

        //Object o = tblUsuario.getValueAt(0, 0);
        //String id = String.valueOf(o);
        String imagen = co+"\\logo.jpeg";
        String archivo = co+"\\Clientes.jasper";
        Conexion cn = new Conexion();
        JasperReport jr = null;
        try {
            Map parametro = new HashMap();
            parametro.put("IDS",cadena);
            parametro.put("imagen",imagen);
            jr = (JasperReport) JRLoader.loadObjectFromFile(archivo);
            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, cn.getConnection());
             JasperExportManager.exportReportToPdfFile(jp,"D:\\Reporte.pdf");
//            JasperViewer jv = new JasperViewer(jp, false);
            String file = new String("D:\\Reporte.pdf");
                  try {
                      Runtime.getRuntime().exec("cmd /c start "+file);
                  } catch (IOException ex) {
                      Logger.getLogger(ifrmClientes.class.getName()).log(Level.SEVERE, null, ex);
                  }
//            jv.setVisible(true);
//            jv.setTitle("FACTURA");
        } catch (JRException ex) {
            Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel panelClientes;
    private javax.swing.JScrollPane spClientes;
    private javax.swing.JTable tblClientes;
    // End of variables declaration//GEN-END:variables
}
