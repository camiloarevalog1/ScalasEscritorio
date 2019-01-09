/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.NominaDTO;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public interface INominaDAO extends java.io.Serializable {

    public boolean registrarNomina(long id,double valorHora,double pagoTot,Date fechaInicio,Date fechaFin,int horasTrabajadas,int diasTrabajados,long horas,double abonado);
public long consultarUltimaNomina();
    public int diasTrabajados(long id_us);
    public int HorasTrabajadas(long id_us);

    public int AusenteDias(long id_us);

    public ArrayList<NominaDTO> listarFechaNomi(Date fechaD, Date fechaH);
    public ArrayList<NominaDTO> listarCedulaNomi(long cedula);
     public ArrayList<NominaDTO> listarCedulaNomin(String cedula) ;
    
    public int cantidadNomina();
    public ArrayList<NominaDTO>listarNominas();
    public ArrayList<NominaDTO> listarNomin();
    public double  totalNomina(long cedula) ;
    public double  listarNominaFecha(Date fecha);
 public boolean BorrarNominas(long id);
 public int horasTrabajados(long id_us);
}
