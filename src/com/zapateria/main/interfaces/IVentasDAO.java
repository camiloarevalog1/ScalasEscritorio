/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.VentaDTO;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public interface IVentasDAO extends java.io.Serializable {

   public ArrayList<VentaDTO> listar(long id);
    public ArrayList<VentaDTO> listarIngresos(Date FechaD, Date FechaH);

}
