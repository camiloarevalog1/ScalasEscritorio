/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.ClienteDTO;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public interface IClientesDAO extends java.io.Serializable {

    public boolean registrar(String documento, String nombres, String apellidos, String direccion, String telefono);

    public boolean registrar(String documento, String nombres, String apellidos);

    public ClienteDTO consultar(long id);

    public boolean editar(long id, String documento, String nombres, String apellidos, String direccion, String telefono);

    public boolean editar(long id, String documento, String nombres, String apellidos);

    public ArrayList<ClienteDTO> buscar(String busqueda);

    public ArrayList<ClienteDTO> listar();

    public boolean eliminar(long id);

    public long registrarCliente(String documento, String nombres, String apellidos,
            String direccion, String telefono);
    
    public boolean validarCedula(String cedula);
}
