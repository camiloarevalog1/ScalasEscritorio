/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.CompraDTO;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public interface IComprasDAO extends java.io.Serializable {

    public boolean registrar(long cantidad, double valorUnitario, double valorTotal,
            long proveedor, long material);

    public CompraDTO consultar(long id);

    public ArrayList<CompraDTO> listar();

    public boolean editar(long id, long cantidad, double valorUnitario, double valorTotal,
            long proveedor, long material);

    public boolean eliminar(long id);

}
