/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dto;

import java.sql.Timestamp;
import java.text.DecimalFormat;

/**
 *
 * @author DELL
 */
public class NombreIngresosDTO implements java.io.Serializable {

    private static final long serialVersionUID = -6277076094769109526L;

    private long id, nombre_G;
    private String nombre;
    private double pago;
    private Timestamp fecha;
    private String nombreGasto;

    public String getNombreGasto() {
        return nombreGasto;
    }

    public void setNombreGasto(String nombreGasto) {
        this.nombreGasto = nombreGasto;
    }
    private NombreGastoDTO nombreG;
    DecimalFormat formateador = new DecimalFormat("###,###.###");

    public NombreIngresosDTO(long id, String nombre, double pago, Timestamp fecha, long nombre_G) {
        this.nombre = nombre;
        this.id = id;
        this.pago = pago;
        this.fecha = fecha;
        this.nombre_G = nombre_G;
    }
    
     public NombreIngresosDTO(long id, String nombre, double pago, Timestamp fecha, long nombre_G,String nombreGasto) {
        this.nombre = nombre;
        this.id = id;
        this.pago = pago;
        this.fecha = fecha;
        this.nombre_G = nombre_G;
        this.nombreGasto=nombreGasto;
    }

    public double getPago() {
        return pago;
    }

    public long getNombre_G() {
        return nombre_G;
    }

    public void setNombre_G(long nombre_G) {
        this.nombre_G = nombre_G;
    }

    public NombreGastoDTO getNombreG() {
        return nombreG;
    }

    public void setNombreG(NombreGastoDTO nombreG) {
        this.nombreG = nombreG;
    }

    public void setPago(double pago) {
        this.pago = pago;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public NombreIngresosDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    public Object[] toArray() {
        return new Object[]{
            this.id,
             this.nombre_G,
            this.nombre,
           
            formateador.format(this.pago),
            this.fecha

        };
    }

}
