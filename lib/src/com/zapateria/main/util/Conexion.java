/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.h2.jdbcx.JdbcConnectionPool;

/**
 *
 * @author Usuario
 */
public class Conexion implements java.io.Serializable {

    private static final long serialVersionUID = -2145318330355997647L;
    private static Conexion INSTANCIA;
    private Connection con;
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "12345";
    private static final String ARCHIVO = "zapateria";
    private static final String CARPETA = "db";
    private static final String SCHEMA = "ZAPATERIA";
    private final JdbcConnectionPool cp;

    private static final String URL = "jdbc:h2:file:" + BuscadorCarpeta.buscar(CARPETA) + ARCHIVO + ";SCHEMA=" + SCHEMA + ";AUTO_SERVER=FALSE";

    private Conexion() {
        cp = JdbcConnectionPool.create(URL, USUARIO, CONTRASENA);
        cp.setMaxConnections(2);
    }

    public static Conexion getInstance() {
        if (INSTANCIA == null) {
            INSTANCIA = new Conexion();
        }
        return INSTANCIA;
    }

    public Connection getConnection() {
        try {
            con = cp.getConnection();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos,"
                    + " puede que ya esté siendo utilizada.");
            try {
                if (con != null) {
                    con.close();
                }
                System.exit(0);
            } catch (SQLException e) {
                ex.printStackTrace();
            }
        }
        return con;
    }

    public void dispose() {
        cp.dispose();
    }
}
