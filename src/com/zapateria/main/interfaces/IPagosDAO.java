/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.PagoDTO;
import com.zapateria.main.dto.PagoFacDTO;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public interface IPagosDAO extends java.io.Serializable {

    public boolean registrar(double abono, long factura);
    
    public boolean actualizarPago(long factura, double abono);

    public PagoDTO consultar(long id);

    public ArrayList<PagoFacDTO> listar();
    public ArrayList<PagoFacDTO> listarNombrePagos(long id);

    public boolean editar(long id, double abono, long factura,long cuota);

    public boolean eliminar(long id);
    public boolean eliminarPagos(long id);
     public long consultaTotalPagos(long id);
     public ArrayList<PagoDTO> listarFechaAbo(Date fechaD, Date fechaH);
     public ArrayList<PagoDTO> listarNombre(long id);
     public double  listarPagosTotal(Date fecha);
}
