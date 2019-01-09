/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.PrestamoDTO;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public interface IPrestamoDAO extends java.io.Serializable{
    
    public boolean registrarAdelanto(long usuario, double valor, Date fecha, String concepto);
    public ArrayList<PrestamoDTO>buscarCedulaAdelanto(long cedula);
    public int cantidadPrestamo();
    public double  listarDeudaFecha(Date fecha);
    public boolean eliminar(long id);
    
}
