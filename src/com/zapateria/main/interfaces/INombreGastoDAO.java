/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.NombreGastoDTO;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public interface INombreGastoDAO extends java.io.Serializable{
    
     public boolean registrar(String nombre);
     public NombreGastoDTO consultar(long id) ;
     public NombreGastoDTO consultarNombre(String nombre);
     public ArrayList<NombreGastoDTO> listar();
     public boolean editar(long id, String nombre);
     public boolean eliminar(long id);
    
}
