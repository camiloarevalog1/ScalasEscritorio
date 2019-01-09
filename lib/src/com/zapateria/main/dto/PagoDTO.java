/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dto;

import java.sql.Timestamp;

/**
 *
 * @author Diego
 */
public class PagoDTO implements java.io.Serializable {

    private static final long serialVersionUID = -2385646308629990809L;

    private long id;
    private double abono;
    private Timestamp fecha;
    private long factura;
    private FacturaDTO facturaDTO;

    public PagoDTO(long id, double abono, Timestamp fecha, long factura) {
        this.id = id;
        this.abono = abono;
        this.fecha = fecha;
        this.factura = factura;
    }

    public PagoDTO() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAbono() {
        return abono;
    }

    public void setAbono(double abono) {
        this.abono = abono;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public long getFactura() {
        return factura;
    }

    public void setFactura(long factura) {
        this.factura = factura;
    }

    public FacturaDTO getFacturaDTO() {
        return facturaDTO;
    }

    public void setFacturaDTO(FacturaDTO facturaDTO) {
        this.facturaDTO = facturaDTO;
    }

    @Override
    public String toString() {
        return "" + fecha;
    }

    public Object[] toArray() {
        return new Object[]{
            id,
            facturaDTO,
            abono,
            fecha
        };
    }

}
