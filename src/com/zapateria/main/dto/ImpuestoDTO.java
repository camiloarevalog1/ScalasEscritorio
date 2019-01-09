/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dto;

/**
 *
 * @author Diego
 */
public class ImpuestoDTO implements java.io.Serializable {

    private static final long serialVersionUID = -6580392540377117826L;

    private long id;
    private String nombre;
    private double valor;

    public ImpuestoDTO(long id, String nombre, double valor) {
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
    }

    public ImpuestoDTO() {

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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public Object[] toArray() {
        return new Object[]{
            id, nombre, valor
        };
    }

}
