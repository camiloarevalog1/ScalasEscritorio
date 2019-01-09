/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.RegistroDTO;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public interface IRegistroDAO extends java.io.Serializable {
    
    public boolean registrar( long id_usuario, long id_cliente, long id_material, double precio, long cantidad, String comentario,
            String estado, Date fecha_entrega,double iva,double total_pagar);
     public ArrayList<RegistroDTO> listarVentasCliente(long id) ;
    public boolean cambiarEstado(long id, String estado);
    public boolean cambiarImpresion(long id, long estado);
    public boolean cambiarIdFactura(long id, long factura);
    public ArrayList<RegistroDTO> listar();
    public ArrayList<RegistroDTO> listarRegistroFecha(Date fechaD, Date fechaH);
    public boolean cambiarFormaDePago(long id, String formaDePago);
     public boolean editarFechaRegistro(long id, Date fecha) ;
     public ArrayList<RegistroDTO> listarRegistro(long id);
     public boolean registrarContado(long id_usuario, long id_cliente, long id_material, double precio, long cantidad, String comentario,
             String estado, String forma_pago, long numero_cuotas, long cuotas_pagadas, Date fecha_entrega, double iva, double total_pagar,double total_pagado);
     public boolean registrarRegistroRemision(long id_usuario, long id_cliente, long id_material, double precio, long cantidad, String comentario,
            String estado, String forma_pago, long numero_cuotas, long cuotas_pagadas, Date fecha_entrega, double iva, double total_pagar,long id_factura,double totalPagado,long id_re);
     public ArrayList<RegistroDTO> listarRegistrosEstado(String estado);
     public ArrayList<RegistroDTO> listarRegistroMaterial(long id);
     public ArrayList<RegistroDTO> listarRegistroMaterialFecha(long id,Date fecha,Date hasta);
//      public ArrayList<RegistroDTO> listarRegistrosPago(String estado);
       public ArrayList<RegistroDTO> listarRegistrosMaterial(long id);
       public ArrayList<RegistroDTO> listarIngresos(Date FechaD, Date FechaH);
       public boolean registrarCuotasPagadas(long id, long cuota);
       public ArrayList<RegistroDTO> listar(long id);
       public double itemTotalFacturas(long id);
       public double IvaTotalFacturas(long id);
       public boolean eliminarRegistro(long id) ;
       public boolean cambiarIdFacturasCero(long id, long factura);
       public boolean eliminarRegistros(long id) ;
        public boolean cambiarComentario(long id, String estado);
        public boolean Material(long id, long fecha);
        public boolean editarPrecio(long id, double precio,double total,double iva,long cantidad);
        public ArrayList<RegistroDTO> listarVentasClienteRemision(long id);

}
