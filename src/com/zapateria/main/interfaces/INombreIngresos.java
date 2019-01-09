/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.NombreIngresosDTO;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public interface INombreIngresos extends java.io.Serializable {
    
    public boolean registrar(Timestamp fecha,String nombre,long id,double pago);
    public boolean registrar2(String nombre,long id_n ,double pago,Date fecha);
    public NombreIngresosDTO consultar(long id);
     public ArrayList<NombreIngresosDTO> listar();
     public boolean editar(long id,long nombreg ,String nombre ,double pago);
     public boolean eliminar(long id);
    public NombreIngresosDTO consultarNombre(String nombre);
    public ArrayList<NombreIngresosDTO> listar(Date fecha);
    public boolean editarPrecio(long id, double pago);
    public ArrayList<NombreIngresosDTO> listar(long id);
    
}
