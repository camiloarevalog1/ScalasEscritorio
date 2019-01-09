/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.Ingresos_EgresosDTO;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public interface IIngresos_Egresos  extends java.io.Serializable{
    public boolean eliminar(long id);
//        public boolean editar(long id,String descripcion, double credito, double debito, long id_n);
        public ArrayList<Ingresos_EgresosDTO> listar();
         public boolean registrar( Timestamp fecha,double deudas, double abonado, double liquidacion, double nominas, double salio, double entro, double caja);
         public Ingresos_EgresosDTO consultar(long id);
         public ArrayList<Ingresos_EgresosDTO> listarIngreso(long id);
         public boolean editarCaja(long id,double caja);
         public long consultarUltimaCaja();
         

    
}
