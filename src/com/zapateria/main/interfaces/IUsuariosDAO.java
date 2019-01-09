/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.SesionDTO;
import com.zapateria.main.dto.UsuarioDTO;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public interface IUsuariosDAO extends java.io.Serializable {

    public boolean validar(String usuario, String contrasena);

    public UsuarioDTO consultar(long id);

    public boolean registrar(String nombres, String apellidos, String documento, String nombreUsuario, String contrasena, long rol,String telefono,String direccion);

    public boolean editar(long id, String nombres, String apellidos, String documento, String nombreUsuario, long rol,String telefono,String direccion);

    public boolean editar(long id, String contrasena);

    public boolean eliminar(long id);

    public ArrayList<UsuarioDTO> listar();
    
        public boolean clave( String password) ;

    public ArrayList<UsuarioDTO> listarUsuariosNomina();

    public boolean validarCedula(String cedula);

    public boolean validarUsuario(String usuario);

    public SesionDTO iniciarSesion(String nombreUsuario, String contrasena);

    public UsuarioDTO buscarUsuarioCedula(String cedula);

    public UsuarioDTO buscarFechaIngreso(long cedula);
    public ArrayList<UsuarioDTO> buscar(String busqueda);
    public ArrayList<UsuarioDTO> buscarNombre(String busqueda);
     public long consultarUltimoUsuario();
}
