/*,
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.view;

import com.zapateria.main.dto.RolDTO;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class ifrmRegistrarUsuario extends MarcoInternoPersonalizado {

    private static final long serialVersionUID = 7028967453495976775L;

    private final ifrmUsuarios vistaUsuarios;

    /**
     * Creates new form RegistrarUsuario
     *
     *
     * @param vistaUsuarios
     */
    public ifrmRegistrarUsuario(ifrmUsuarios vistaUsuarios) {
        this.vistaUsuarios = vistaUsuarios;
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

        panelDatosUsuario = new javax.swing.JPanel();
        lbNombreUsuario = new javax.swing.JLabel();
        lbContrasena = new javax.swing.JLabel();
        lbConfirmarContrasena = new javax.swing.JLabel();
        lbRol = new javax.swing.JLabel();
        comboRoles = new javax.swing.JComboBox<>();
        textNombreUsuario = new javax.swing.JTextField();
        passContrasena = new javax.swing.JPasswordField();
        passConfirmarContrasena = new javax.swing.JPasswordField();
        panelDatosPersonales = new javax.swing.JPanel();
        lbDocumento1 = new javax.swing.JLabel();
        lbApellidos1 = new javax.swing.JLabel();
        lbNombres1 = new javax.swing.JLabel();
        textNombres = new javax.swing.JTextField();
        textApellidos = new javax.swing.JTextField();
        textDocumento = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setClosable(true);
        setTitle("Registrar usuario nuevo");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(391, 355));
        setMinimumSize(new java.awt.Dimension(391, 355));
        setName("Registrar usuario nuevo"); // NOI18N
        setPreferredSize(new java.awt.Dimension(391, 355));
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

        panelDatosUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder("Información del usuario"));

        lbNombreUsuario.setText("Nombre de usuario:");
        lbNombreUsuario.setToolTipText("");

        lbContrasena.setText("Contraseña:");
        lbContrasena.setToolTipText("");

        lbConfirmarContrasena.setText("Confirmar contraseña:");
        lbConfirmarContrasena.setToolTipText("");

        lbRol.setText("Rol:");
        lbRol.setToolTipText("");

        comboRoles.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione un rol" }));
        comboRoles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboRolesActionPerformed(evt);
            }
        });

        textNombreUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textNombreUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDatosUsuarioLayout = new javax.swing.GroupLayout(panelDatosUsuario);
        panelDatosUsuario.setLayout(panelDatosUsuarioLayout);
        panelDatosUsuarioLayout.setHorizontalGroup(
            panelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbConfirmarContrasena)
                    .addComponent(lbRol)
                    .addComponent(lbNombreUsuario)
                    .addComponent(lbContrasena))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(passContrasena, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(passConfirmarContrasena)
                    .addComponent(textNombreUsuario, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboRoles, 0, 215, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDatosUsuarioLayout.setVerticalGroup(
            panelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbRol)
                    .addComponent(comboRoles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNombreUsuario)
                    .addComponent(textNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbContrasena)
                    .addComponent(passContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbConfirmarContrasena)
                    .addComponent(passConfirmarContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelDatosPersonales.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos personales"));

        lbDocumento1.setText("Documento:");
        lbDocumento1.setToolTipText("");

        lbApellidos1.setText("Apellidos:");

        lbNombres1.setText("Nombres:");

        textNombres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textNombresActionPerformed(evt);
            }
        });

        textApellidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textApellidosActionPerformed(evt);
            }
        });

        textDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textDocumentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDatosPersonalesLayout = new javax.swing.GroupLayout(panelDatosPersonales);
        panelDatosPersonales.setLayout(panelDatosPersonalesLayout);
        panelDatosPersonalesLayout.setHorizontalGroup(
            panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDatosPersonalesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelDatosPersonalesLayout.createSequentialGroup()
                        .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbDocumento1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDatosPersonalesLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbNombres1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(textDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelDatosPersonalesLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lbApellidos1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textApellidos)))
                .addContainerGap())
        );
        panelDatosPersonalesLayout.setVerticalGroup(
            panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNombres1)
                    .addComponent(textNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbApellidos1)
                    .addComponent(textApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbDocumento1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDatosUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDatosPersonales, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRegistrar)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDatosPersonales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelDatosUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void llenarComboRoles() {

        // Agregando los elementos del JComboBox de los roles
        ArrayList<RolDTO> listaRoles = servicio.listarRoles();

        listaRoles.forEach((rol) -> {
            comboRoles.addItem(rol);
        });
    }

    private void textNombresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNombresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textNombresActionPerformed

    private void textApellidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textApellidosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textApellidosActionPerformed

    private void textDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textDocumentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textDocumentoActionPerformed

    private void textNombreUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNombreUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textNombreUsuarioActionPerformed

    private void comboRolesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboRolesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboRolesActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        cerrar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        // Registrando usuario
        String nombres = textNombres.getText();
        String apellidos = textApellidos.getText();
        String documento = "";

        long rol = 0;

        String nombreUsuario = null;
        String contrasena = null;
        String confirmarContrasena = null;

        nombreUsuario = textNombreUsuario.getText();
        contrasena = String.copyValueOf(passContrasena.getPassword());
        confirmarContrasena = String.copyValueOf(passConfirmarContrasena.getPassword());

        if (!(nombres.length() > 2 && apellidos.length() > 2 && contrasena.length() > 2 && confirmarContrasena.length() > 2 && nombreUsuario.length() > 2)) {
            mostrarMensaje("Falta uno o más datos por ingresar");
            return;
        }

        if (!textDocumento.getText().matches(REGEX_ENTERO_POSITIVO)) {
            mostrarMensaje("Solo se permiten números para el documento");
            textDocumento.requestFocus();
            return;
        } else {
            documento = textDocumento.getText();
        }

        //Extrayendo el ID desde el objeto almacenado en el item
        if (!String.valueOf(comboRoles.getSelectedItem()).equals("Seleccione un rol")) {
            RolDTO rolDTO = (RolDTO) comboRoles.getSelectedItem();
            rol = rolDTO.getId();
        } else {
            mostrarMensaje("Seleccione un rol");
            comboRoles.requestFocus();
            return;
        }

        if (contrasena.equals(confirmarContrasena)) {

            if (servicio.registrarUsuario(nombres, apellidos, documento, nombreUsuario, contrasena, rol)) {
                mostrarMensaje("Usuario registrado satisfactoriamente");

                vistaUsuarios.actualizarTablaUsuarios();
                this.dispose();
            } else {
                mostrarMensaje("No se pudo registrar el usuario");
            }

        } else {
            mostrarMensaje("Las contraseñas no coinciden");
        }


    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:

        llenarComboRoles();
    }//GEN-LAST:event_formInternalFrameOpened


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<Object> comboRoles;
    private javax.swing.JLabel lbApellidos1;
    private javax.swing.JLabel lbConfirmarContrasena;
    private javax.swing.JLabel lbContrasena;
    private javax.swing.JLabel lbDocumento1;
    private javax.swing.JLabel lbNombreUsuario;
    private javax.swing.JLabel lbNombres1;
    private javax.swing.JLabel lbRol;
    private javax.swing.JPanel panelDatosPersonales;
    private javax.swing.JPanel panelDatosUsuario;
    private javax.swing.JPasswordField passConfirmarContrasena;
    private javax.swing.JPasswordField passContrasena;
    private javax.swing.JTextField textApellidos;
    private javax.swing.JTextField textDocumento;
    private javax.swing.JTextField textNombreUsuario;
    private javax.swing.JTextField textNombres;
    // End of variables declaration//GEN-END:variables

}
