/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.PagoDTO;
import com.zapateria.main.dto.PagosProv;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public interface IPagosProvDAO extends java.io.Serializable {
     public boolean registrar(double abono, long factura);
    
    public boolean actualizarPago(long factura, double abono);

    public PagosProv consultar(long id);

    public ArrayList<PagosProv> listar();

    public boolean editar(long id, double abono, long factura);
    public double total(long id);

    public boolean eliminar(long id);
     //public long consultaTotalPagos(long id);
    public ArrayList<PagosProv> listarProveedor(long id);
    
}
