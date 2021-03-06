/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.view;

import com.zapateria.main.dto.UsuarioDTO;
import com.zapateria.main.util.BuscadorCarpeta;
import com.zapateria.main.util.Sesion;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class ConfirmarUsuario extends MarcoInternoPersonalizado {

    /**
     * Creates new form ConfirmarUsuario
     */
    public ConfirmarUsuario() {
        initComponents();
        panelConfirmacion.setLocation(1000, 1000);
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);
        ImageIcon ImageIcon = new ImageIcon(c + "//icon2.png");
        Image Image = ImageIcon.getImage();
        //this.setIconImage(Image);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelConfirmacion = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtUsuarioConfirmar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        txtContrasenaConfirmar = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/icon2.png"))); // NOI18N

        panelConfirmacion.setBackground(new java.awt.Color(255, 255, 255));
        panelConfirmacion.setBorder(javax.swing.BorderFactory.createTitledBorder("Confirmacion"));
        panelConfirmacion.setAlignmentX(1.0F);
        panelConfirmacion.setAlignmentY(1.0F);
        panelConfirmacion.setAutoscrolls(true);

        jLabel1.setText("Usuario");

        jLabel2.setText("Contraseña");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Aceptar_opt.png"))); // NOI18N
        jButton1.setText("Confirmar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtContrasenaConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContrasenaConfirmarActionPerformed(evt);
            }
        });
        txtContrasenaConfirmar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContrasenaConfirmarKeyPressed(evt);
            }
        });

        jLabel3.setText("Nota:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel4.setText("Se borraran las facturas y las nominas,liquidaciones, deudas, abonos de un empleado");

        javax.swing.GroupLayout panelConfirmacionLayout = new javax.swing.GroupLayout(panelConfirmacion);
        panelConfirmacion.setLayout(panelConfirmacionLayout);
        panelConfirmacionLayout.setHorizontalGroup(
            panelConfirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelConfirmacionLayout.createSequentialGroup()
                .addContainerGap(147, Short.MAX_VALUE)
                .addGroup(panelConfirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtUsuarioConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelConfirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton1)
                        .addGroup(panelConfirmacionLayout.createSequentialGroup()
                            .addGap(37, 37, 37)
                            .addComponent(jLabel2))
                        .addGroup(panelConfirmacionLayout.createSequentialGroup()
                            .addGap(49, 49, 49)
                            .addComponent(jLabel1))))
                .addGap(144, 144, 144))
            .addGroup(panelConfirmacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelConfirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelConfirmacionLayout.createSequentialGroup()
                    .addGap(175, 175, 175)
                    .addComponent(txtContrasenaConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(142, Short.MAX_VALUE)))
        );
        panelConfirmacionLayout.setVerticalGroup(
            panelConfirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfirmacionLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel1)
                .addGap(36, 36, 36)
                .addComponent(txtUsuarioConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addGap(100, 100, 100)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(panelConfirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap())
            .addGroup(panelConfirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelConfirmacionLayout.createSequentialGroup()
                    .addGap(189, 189, 189)
                    .addComponent(txtContrasenaConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(189, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(482, 482, 482)
                .addComponent(panelConfirmacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(panelConfirmacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
public void crearAbonoPrestamo() {
        ArrayList<UsuarioDTO> list = servicio.listarUsuarios();
        for (UsuarioDTO u : list) {
            long id = u.getId();
            servicio.crearAbonoUsuario(id, 0, 0, 0);
        }
    }

    private boolean iniciarSesion(String usuario, String contrasena) {

        System.out.println(usuario + contrasena);
        if (Sesion.iniciarSesion(usuario, contrasena)) {
            System.out.println(usuario + contrasena);
            this.setVisible(false);

            Escritorio escritorio = new Escritorio();
            escritorio.setExtendedState(JFrame.MAXIMIZED_BOTH);
            escritorio.setVisible(true);
            return true;
        } else {

            return false;
        }

    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String nombre = txtUsuarioConfirmar.getText().toString();
        String contrasen = txtContrasenaConfirmar.getText().toString();
        boolean x = servicio.validar(nombre, contrasen);
        boolean c = false;
        if (x) {
            mostrarMensaje("Usuario correcto");
            servicio.crearSQL();
            boolean v = servicio.LimpiarBase();
            boolean y = servicio.LimpiarAbonoUsuario();
            boolean z = servicio.LimpiarFactura();
            boolean a = servicio.LimpiarLiquidacion();
            boolean b = servicio.LimpiarNominaUsuario();
            boolean d = servicio.LimpiarPagos();
            c = servicio.LimpiarPrestamoUsuario();

            servicio.LimpiarVentas2();

            servicio.LimpiarFacturas2();

            servicio.LimpiarFacturas();

            servicio.LimpiarRegistro();

            servicio.LimpiarRegistro2();

            servicio.LimpiarVentas();
            servicio.LimpiarIngresos();
            servicio.LimpiarCompras();
            servicio.LimpiarCierres();
            servicio.LimpiarLibro();
            servicio.LimpiarComprasP();
            servicio.LimpiarPagosP();

            crearAbonoPrestamo();
        }

        if (c) {

            mostrarMensaje("La base de datos se ha limpiado");
        } else {
            mostrarMensaje("La base de datos  se ha limpiado");
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtContrasenaConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContrasenaConfirmarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContrasenaConfirmarActionPerformed

    private void txtContrasenaConfirmarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContrasenaConfirmarKeyPressed
        // TODO add your handling code here:

        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {

        }

    }//GEN-LAST:event_txtContrasenaConfirmarKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel panelConfirmacion;
    private javax.swing.JPasswordField txtContrasenaConfirmar;
    private javax.swing.JTextField txtUsuarioConfirmar;
    // End of variables declaration//GEN-END:variables
}
