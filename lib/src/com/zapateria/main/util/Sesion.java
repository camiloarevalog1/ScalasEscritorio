/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.util;

import com.zapateria.main.dao.UsuariosDAO;
import com.zapateria.main.dto.SesionDTO;
import com.zapateria.main.interfaces.IUsuariosDAO;

/**
 *
 * @author Usuario
 */
public class Sesion implements java.io.Serializable {

    private static final long serialVersionUID = -737057400883349732L;

    private static SesionDTO sesionDTO = null;
    private static Sesion INSTANCIA = null;
    private static IUsuariosDAO dao = null;

    private Sesion(String nombre, String contrasena) {
        dao = new UsuariosDAO();
        sesionDTO = dao.iniciarSesion(nombre, contrasena);
    }

    public static boolean iniciarSesion(String nombre, String contrasena) {

        if (INSTANCIA == null) {
            INSTANCIA = new Sesion(nombre, contrasena);
        }
        if (sesionDTO == null) {
            INSTANCIA = null;
            return false;
        }
        return sesionDTO != null;
    }

    public static SesionDTO obtenerSesion() {
        return sesionDTO;
    }
}
