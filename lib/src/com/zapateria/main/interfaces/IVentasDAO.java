/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.VentaDTO;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public interface IVentasDAO extends java.io.Serializable {

    public boolean registrar(long cantidad, String comentario, long factura, long producto);

    public VentaDTO consultar(long id);

    public ArrayList<VentaDTO> listar();

    public ArrayList<VentaDTO> listar(long factura);

    public boolean editar(long id, long cantidad, String comentario, long factura, long producto);

    public boolean editar(long id, long cantidad);

    public boolean eliminar(long id);

}
