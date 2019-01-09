/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main;

import com.zapateria.main.view.frmLogin;

/**
 *
 * @author Usuario
 */
public class Zapateria implements java.io.Serializable {

    private static frmLogin login = null;
    private static final long serialVersionUID = -6840678506209422204L;

    private static void instanciarLogin() {

        if (login == null) {
            login = new frmLogin();
        }

    }

    public static void main(String[] args) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        instanciarLogin();
        if (!login.isShowing()) {
            login.setVisible(true);
            login.setLocationRelativeTo(null);
        }

    }

}
