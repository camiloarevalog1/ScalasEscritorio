/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.ProveedorDTO;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public interface IProveedoresDAO extends java.io.Serializable {

    public boolean registrar(String nombre, String nit, String direccion, String telefono);

    public ProveedorDTO consultar(long id);

    public ArrayList<ProveedorDTO> listar();

    public boolean editar(long id, String nombre, String nit, String direccion,
            String telefono);

    public boolean eliminar(long id);
     public ProveedorDTO consultarNombre(String nombre);
}
