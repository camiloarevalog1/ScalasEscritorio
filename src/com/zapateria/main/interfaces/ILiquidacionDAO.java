/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.LiquidacionDTO;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public interface ILiquidacionDAO extends java.io.Serializable {

    public boolean registrarLiquidacion(long id_usuario, Date fecha_salida, int horas_trabajadas, int dias_trabajados,
            Date fecha_ingreso, double sueldo_basico,
            double cesantias, double intereses_cesantias, double prima, double vacaciones, double valor_liquidacion, double bonificacion);

    public ArrayList<LiquidacionDTO> buscarLiquidacionEmpleado(long cedula);

    public ArrayList<LiquidacionDTO> listarLiquidaciones();
    public ArrayList<LiquidacionDTO> listarLiquida();

    public int cantidadliqidacion();
    public ArrayList<LiquidacionDTO> listarFechaLiquidacion(Date fechaD, Date fechaH);
    public double  listarLiquidacionTotal(Date fecha);
    public boolean BorrarLiquidaciones(long id);
}
