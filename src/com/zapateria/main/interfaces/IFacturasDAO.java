/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

import com.zapateria.main.dto.FacturaDTO;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public interface IFacturasDAO extends java.io.Serializable {

    public boolean registrar(String formaDePago, double iva,
            double totalPagar, double totalPagado,
            Date fechaEntrega, long cliente, long usuario,long cuota);
    public boolean registrarFa(String formaDePago, double iva,
            double totalPagar, double totalPagado,
            Date fechaEntrega, long cliente, long usuario,long cuota);

    public FacturaDTO consultar(long id);

//    public ArrayList<FacturaDTO> listar();

    public ArrayList<FacturaDTO> buscar(String busqueda);

    public boolean editar(long id, String formaDePago, double iva,
            double totalPagar, double totalPagado,
            Date fechaEntrega, long cliente, long usuario);

    public boolean cambiarEstado(long id, String estado);

    public boolean cambiarFormaDePago(long id, String estado);

    public boolean editar(long id, String formaDePago, String estado);

    public boolean eliminar(long id);

//    public long consultarIncremento();
    
    public ArrayList<FacturaDTO> listarUsuarioFactura(long cedula);
    
    public ArrayList<FacturaDTO>listarEstadoFactura(String estado);
    public ArrayList<FacturaDTO>listarPagoFactura(String estado);
    
    public ArrayList<FacturaDTO>listarFechasFac(Date fechaD,Date fechaH);
    
    public ArrayList<FacturaDTO>listarFactura(long id);
    public boolean editarRemision(long id, double total, double iva);
    
    public ArrayList<FacturaDTO>listarFechasFact(Date fecha);
     public boolean registrarCuotasPagadas(long id, long cuota);
     
     public boolean editarFechaFactura(long id, Date fecha);
     public ArrayList<FacturaDTO> filtroMateriales(long id);
     public ArrayList<FacturaDTO> listarFechaNomi(Date fechaD, Date fechaH);
     public long consultarUltimaFactura() ;
     
     
//     public boolean registrar2(double iva,
//            double totalPagar, double totalPagado,
//            long cliente, long usuario);
     
     public boolean registrarRemision(double iva,double totalPagado, double totalPagar, long cliente, long usuario,String forma);
     public long consultarIncrementoRemision();
      public boolean registrarRemisionIva(double iva, double totalPagado, double totalPagar, long cliente, long usuario,String forma,String factura);
      public ArrayList<FacturaDTO> listarEstadosFactura(String estado);
      public ArrayList<FacturaDTO> listarMaterialFactura(long id);
      public double consultaTotalPagarPagado(long id);
       public double  listarFacturaTotal(Date fecha);
       public boolean ActualizarPago(long id, double pago);
       public ArrayList<FacturaDTO> listarIngresos(Date FechaD, Date FechaH);
       public long consultaTotalProductos(long id);
       public boolean editarIvaFactura(long id, String idF);
}
