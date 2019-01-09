/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class ConexionDAO implements java.io.Serializable {

    private static final long serialVersionUID = -6228726714823310751L;

    protected ResultSet rs;
    protected Conexion conexion;
    protected Connection con;
    protected PreparedStatement pst;
    protected ArrayList lista;

    public ConexionDAO() {
        conexion = Conexion.getInstance();
        con = null;
        pst = null;
        rs = null;
        lista = null;
    }
}
