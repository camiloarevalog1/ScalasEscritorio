/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.MaterialUsadoDTO;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public interface IMaterialesUsadosDAO extends java.io.Serializable {

    public boolean registrar(long cantidad, long material);

    public MaterialUsadoDTO consultar(long id);

    public ArrayList<MaterialUsadoDTO> listar();

    public boolean eliminar(long id);
}
