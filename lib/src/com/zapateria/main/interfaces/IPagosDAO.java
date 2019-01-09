/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.PagoDTO;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public interface IPagosDAO extends java.io.Serializable {

    public boolean registrar(double abono, long factura);

    public PagoDTO consultar(long id);

    public ArrayList<PagoDTO> listar();

    public boolean editar(long id, double abono, long factura);

    public boolean eliminar(long id);

}
