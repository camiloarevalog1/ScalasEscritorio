/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.interfaces;

/**
 *
 * @author DELL
 */
public interface IBasesDAO extends java.io.Serializable {
    public boolean crearSQL();
    public boolean LimpiarBase();
    public boolean LimpiarAbonoUsuario() ;
    public boolean LimpiarFactura();
    public boolean LimpiarLiquidacion() ;
    public boolean LimpiarNominaUsuario() ;
    public boolean LimpiarPrestamoUsuario();
    public boolean LimpiarPagos();
    public boolean LimpiarVentas2();
    public boolean LimpiarFacturas2();
    public boolean LimpiarFacturas();
    public boolean LimpiarRegistro();
    public boolean LimpiarRegistro2();
    public boolean LimpiarVentas();
    public boolean LimpiarIngresos();
    public boolean LimpiarCompras();
    public boolean LimpiarCierres();
    public boolean LimpiarLibro();
    public boolean LimpiarComprasP();
    public boolean LimpiarPagosP();
    public boolean EliminarSquemaSQL();
    
}
