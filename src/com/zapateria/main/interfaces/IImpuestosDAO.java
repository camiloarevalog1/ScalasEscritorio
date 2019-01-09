/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.ImpuestoDTO;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public interface IImpuestosDAO extends java.io.Serializable {

    public boolean registrar(String nombre, double valor);

    public ImpuestoDTO consultar(long id);

    public ArrayList<ImpuestoDTO> listar();

    public boolean editar(long id, String nombre, double valor);

    public boolean eliminar(long id);
}
