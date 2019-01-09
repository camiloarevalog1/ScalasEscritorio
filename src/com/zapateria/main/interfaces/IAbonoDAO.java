/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.AbonoDTO;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public interface IAbonoDAO extends java.io.Serializable {

    public boolean registrarAbono(long usuario, double valor, Date fecha);
    
    public ArrayList<AbonoDTO>buscarCedulaAbono(long cedula);
     public int cantidadAbono();
     
     public ArrayList<AbonoDTO> listarFechaAbon(Date fechaD, Date fechaH) ;
     public double  listarAbonoFecha(Date fecha);
}
