/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.MaterialDTO;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public interface IMaterialesDAO extends java.io.Serializable {

    public boolean registrar(String nombre);

    public MaterialDTO consultar(long id);

    public ArrayList<MaterialDTO> listar();

    public boolean editar(long id, String nombre);

    public boolean editar(long id, long cantidad);

    public boolean eliminar(long id);
}
