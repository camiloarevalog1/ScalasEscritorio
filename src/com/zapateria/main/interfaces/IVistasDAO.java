/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.VistaDTO;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public interface IVistasDAO extends java.io.Serializable {

    public boolean registrar(String vista);

    public VistaDTO consultar(long id);

    public boolean editar(long id, String vista);

    public ArrayList<VistaDTO> listar(long rol);

    public ArrayList<VistaDTO> listar();

    public boolean eliminar(long id);
}
