/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dao.Abono_PrestamoDAO;
import com.zapateria.main.dto.Abono_PrestamoDTO;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public interface IAbonoPrestamoDAO extends java.io.Serializable {

    public double Buscarvalor(long id);

    public boolean crearAbonoUsuario(long id, double total, double total_debe, double total_abono);

    public Abono_PrestamoDTO buscarAbonoPrestamo(long id);
    
    public boolean actualizarAbonoPrestamo(long abo,double prestamo,double total_debe);
    
   public boolean actualizarAbonoPresta(long id,double total_abonado,double total_debe);
   
   public int cantidadAbono_Prestamo();
   public ArrayList<Abono_PrestamoDTO> listarDebeCedula(long id);
   
   public ArrayList<Abono_PrestamoDTO>listarDebe();
   public boolean BorrarAbono_Prestamo(long id);
}
