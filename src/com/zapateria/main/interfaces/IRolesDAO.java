/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.RolDTO;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public interface IRolesDAO extends java.io.Serializable {

    public boolean registrar(String rol,double valor);

    public RolDTO consultar(long id);

    public boolean editar(long id, String rol,double valor);

    public ArrayList<RolDTO> listar();

    public boolean eliminar(long id);
    
    public RolDTO consultarNombre(String nombre);
}
