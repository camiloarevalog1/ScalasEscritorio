/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.FacturaDTO;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public interface IFacturasDAO extends java.io.Serializable {

    public boolean registrar(String formaDePago, String estado, double iva,
            double totalPagar, double totalPagado,
            Date fechaEntrega, long cliente, long usuario);

    public FacturaDTO consultar(long id);

    public ArrayList<FacturaDTO> listar();

    public ArrayList<FacturaDTO> buscar(String busqueda);

    public boolean editar(long id, String formaDePago, String estado, double iva,
            double totalPagar, double totalPagado,
            Date fechaEntrega, long cliente, long usuario);

    public boolean cambiarEstado(long id, String estado);

    public boolean cambiarFormaDePago(long id, String estado);

    public boolean editar(long id, String formaDePago, String estado);

    public boolean eliminar(long id);

    public long consultarIncremento();

}
