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
public class Ingresos_EgresosDTO  implements java.io.Serializable{
    
    private long id,Nombre_id;
    private String descripcion;
    private double credito,debito,deudas,abonado,liquidacion,nominas,salio,entro,caja;
    private Timestamp fecha;
    private NombreIngresosDTO ingresos;

    public Ingresos_EgresosDTO(long id, Timestamp fecha,double deudas,double abonado, double liquidacion, double nominas, double salio, double entro, double caja) {
        this.id = id;
        this.fecha = fecha;
        this.deudas=deudas;
        this.abonado=abonado;
        this.liquidacion=liquidacion;
        this.nominas=nominas;
        this.salio=salio;
        this.entro=entro;
        this.caja=caja;
    }

    public double getDeudas() {
        return deudas;
    }

    public void setDeudas(double deudas) {
        this.deudas = deudas;
    }

    public double getAbonado() {
        return abonado;
    }

    public void setAbonado(double abonado) {
        this.abonado = abonado;
    }

    public double getLiquidacion() {
        return liquidacion;
    }

    public void setLiquidacion(double liquidacion) {
        this.liquidacion = liquidacion;
    }

    public double getNominas() {
        return nominas;
    }

    public void setNominas(double nominas) {
        this.nominas = nominas;
    }

    public double getSalio() {
        return salio;
    }

    public void setSalio(double salio) {
        this.salio = salio;
    }

    public double getEntro() {
        return entro;
    }

    public void setEntro(double entro) {
        this.entro = entro;
    }

    public double getCaja() {
        return caja;
    }

    public void setCaja(double caja) {
        this.caja = caja;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNombre_id() {
        return Nombre_id;
    }

    public void setNombre_id(long Nombre_id) {
        this.Nombre_id = Nombre_id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCredito() {
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }

    public double getDebito() {
        return debito;
    }

    public void setDebito(double debito) {
        this.debito = debito;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public NombreIngresosDTO getIngresos() {
        return ingresos;
    }

    public void setIngresos(NombreIngresosDTO ingresos) {
        this.ingresos = ingresos;
    }
    
      @Override
    public String toString() {
        return this.id + "";
    }
DecimalFormat formateador = new DecimalFormat("###,###.###");
    public Object[] toArray() {
        return new Object[]{
            this.id,
            this.fecha,
            formateador.format(this.deudas),
            formateador.format(this.abonado),
            formateador.format(this.liquidacion),
            formateador.format(this.nominas),
            formateador.format(this.salio),
            formateador.format(this.entro),
            formateador.format(this.caja)
            
        };
    }
    
}
