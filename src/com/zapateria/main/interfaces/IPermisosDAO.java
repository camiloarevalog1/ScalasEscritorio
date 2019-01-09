/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.PermisoDTO;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public interface IPermisosDAO extends java.io.Serializable {

    public ArrayList<PermisoDTO> listar();

    public boolean editar(long id, long rol, long vista);

    public boolean eliminar(long id);

    public boolean registrar(long rol, long vista);

}
