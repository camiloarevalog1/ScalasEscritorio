/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.view;

import com.zapateria.main.dto.RolDTO;
import com.zapateria.main.dto.VistaDTO;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Diego
 */
public class ifrmPermisos extends MarcoInternoPersonalizado {

    private static final long serialVersionUID = 5390503826701992127L;

    private DefaultTableModel dtblmPermisos;

    /**
     * Creates new form ifrmPermisos
     */
    public ifrmPermisos() {
        initComponents();
    }

    private void llenarTablaPermisos() {

        final String[] camposPermisos = {"Codigo Permiso", "Rol", "Ventana"};

        // Configurando los eventos de edicion de la tabla
        dtblmPermisos = new DefaultTableModel(null, camposPermisos) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0;
            }
        };
        // Llenando la tabla
        servicio.listarPermisos().forEach((permiso) -> {
            dtblmPermisos.addRow(permiso.toArray());
        });
        //Agregando el modelo de la tabla
        tblPermisos.setModel(dtblmPermisos);

        dtblmPermisos.addTableModelListener((TableModelEvent e) -> {
            long idRol = 0;
            long id = 0;
            long idVista = 0;
            RolDTO rolDTO = null;
            VistaDTO vistaDTO = null;

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tblPermisos.getValueAt(e.getFirstRow(), 0);

                Object objRol = tblPermisos.getValueAt(e.getFirstRow(), 1);
                Object objVista = tblPermisos.getValueAt(e.getFirstRow(), 2);

                if (objVista == null || objRol == null) {
                    return;
                }

                if (objId != null) {
                    id = Long.parseLong(String.valueOf(objId));
                    rolDTO = (RolDTO) objRol;
                    idRol = rolDTO.getId();
                    vistaDTO = (VistaDTO) objVista;
                    idVista = vistaDTO.getId();

                    if (servicio.editarPermiso(id, idRol, idVista)) {
                        actualizarTablaPermisos();
                        mostrarMensaje("Se ha editado el permiso satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo editar el permiso");
                    }
                } else {
                    rolDTO = (RolDTO) objRol;
                    idRol = rolDTO.getId();
                    vistaDTO = (VistaDTO) objVista;
                    idVista = vistaDTO.getId();
                    if (servicio.registrarPermiso(idRol, idVista)) {
                        actualizarTablaPermisos();
                        mostrarMensaje("Se ha registrado el permiso satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo registrar el permiso");
                    }
                }

            }
        });

        // Agregando combo para las celdas necesarias
        agregarJComboBoxAColumna(tblPermisos, 1, servicio.listarRoles().toArray());
        agregarJComboBoxAColumna(tblPermisos, 2, servicio.listarVistas().toArray());

        aplicarEventosPersonalizados(tblPermisos);
    }

    private void actualizarTablaPermisos() {
        tblPermisos.removeAll();
        dtblmPermisos = null;
        llenarTablaPermisos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPermisos = new javax.swing.JPanel();
        spPermisos = new javax.swing.JScrollPane();
        tblPermisos = new TablaPersonalizada();
        btnEliminar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Administrar permisos");
        setBackground(new java.awt.Color(255, 255, 255));
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/icon2.png"))); // NOI18N
        setMaximumSize(new java.awt.Dimension(400, 317));
        setMinimumSize(new java.awt.Dimension(400, 317));
        setPreferredSize(new java.awt.Dimension(400, 317));
        setVisible(false);
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

        panelPermisos.setBorder(javax.swing.BorderFactory.createTitledBorder("Permisos"));

        tblPermisos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        spPermisos.setViewportView(tblPermisos);

        javax.swing.GroupLayout panelPermisosLayout = new javax.swing.GroupLayout(panelPermisos);
        panelPermisos.setLayout(panelPermisosLayout);
        panelPermisosLayout.setHorizontalGroup(
            panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spPermisos, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panelPermisosLayout.setVerticalGroup(
            panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPermisosLayout.createSequentialGroup()
                .addComponent(spPermisos, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Eliminar.png"))); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/agregar.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/SALIR.png"))); // NOI18N
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPermisos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPermisos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:;
        llenarTablaPermisos();
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:

        int filaSeleccionada = tblPermisos.getSelectedRow();

        if (filaSeleccionada < 0) {
            mostrarMensaje("Seleccione un permiso de la tabla");
            return;
        }

        if (!mostrarDecision("¿Desea eliminar este permiso?")) {
            return;
        }

        Object idObj = tblPermisos.getValueAt(filaSeleccionada, 0);

        if (idObj == null) {
            dtblmPermisos.removeRow(filaSeleccionada);
            return;
        }

        long id = Long.parseLong(String.valueOf(idObj));

        if (servicio.eliminarPermiso(id)) {
            actualizarTablaPermisos();
            mostrarMensaje("Permiso eliminado satisfactoriamente");
        } else {
            mostrarMensaje("No se pudo eliminar el permiso");
        }

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        cerrar();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

        dtblmPermisos.addRow(new Object[]{});

    }//GEN-LAST:event_btnNuevoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JPanel panelPermisos;
    private javax.swing.JScrollPane spPermisos;
    private javax.swing.JTable tblPermisos;
    // End of variables declaration//GEN-END:variables
}
