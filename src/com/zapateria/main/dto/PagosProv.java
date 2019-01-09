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
public class PagosProv implements java.io.Serializable{
    
     private static final long serialVersionUID = -2685646308629990809L;

    private long id;
    private double abono;
    private Timestamp fecha;
    private long compra;
    private CompraDTO CompraDTO;

    public PagosProv(long id, double abono, Timestamp fecha, long compra) {
        this.id = id;
        this.abono = abono;
        this.fecha = fecha;
        this.compra = compra;
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

    public long getCompra() {
        return compra;
    }

    public void setCompra(long compra) {
        this.compra = compra;
    }

    public CompraDTO getCompraDTO() {
        return CompraDTO;
    }

    public void setCompraDTO(CompraDTO CompraDTO) {
        this.CompraDTO = CompraDTO;
    }
    
    public String toString() {
        return "" + fecha;
    }
DecimalFormat formateador = new DecimalFormat("###,###.###");
    public Object[] toArray() {
        return new Object[]{
            id,
            CompraDTO,
            formateador.format(abono),
            fecha
            
        };
    }
    
    
    
    
}
