/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dto;

import java.sql.Date;
import java.text.DecimalFormat;

/**
 *
 * @author DELL
 */
public class PrestamoDTO implements java.io.Serializable {

    private static final long serialVersionUID = -6626894140208669771L;

    private long id;
    private long id_deuda;
    private Abono_PrestamoDTO abonoPrestamoDTO;
    private double valor;
    private Date fecha;
    private String concepto;
DecimalFormat formateador = new DecimalFormat("###,###.###");
    public PrestamoDTO() {
        
    }

    public PrestamoDTO(double valor, Date fecha, String concepto) {
        this.valor = valor;
        this.fecha = fecha;
        this.concepto = concepto;
    }

    public PrestamoDTO(long id, double valor, Date fecha, String concepto) {
        this.id = id;
        this.valor = valor;
        this.fecha = fecha;
        this.concepto = concepto;
    }

    public PrestamoDTO(long id, long id_deuda, double valor, Date fecha, String concepto) {
        this.id = id;
        this.id_deuda = id_deuda;
        this.valor = valor;
        this.fecha = fecha;
        this.concepto = concepto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_deuda() {
        return id_deuda;
    }

    public void setId_deuda(long id_deuda) {
        this.id_deuda = id_deuda;
    }

    public Abono_PrestamoDTO getAbonoPrestamoDTO() {
        return abonoPrestamoDTO;
    }

    public void setAbonoPrestamoDTO(Abono_PrestamoDTO abonoPrestamoDTO) {
        this.abonoPrestamoDTO = abonoPrestamoDTO;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
 
    public Object[] toArray() {
        return new Object[]{
            id,formateador.format(valor), fecha, concepto

        };
    }
}
