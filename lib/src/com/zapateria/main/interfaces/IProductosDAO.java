/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.ProductoDTO;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public interface IProductosDAO extends java.io.Serializable {

    public boolean registrar(String nombre, double precioUnitario);

    public ProductoDTO consultar(long id);

    public ArrayList<ProductoDTO> listar();

    public ArrayList<ProductoDTO> buscar(String busqueda);

    public boolean editar(long id, String nombre, double precioUnitario);

    public boolean eliminar(long id);

}
