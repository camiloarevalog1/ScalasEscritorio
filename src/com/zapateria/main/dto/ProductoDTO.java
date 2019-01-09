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
public class ProductoDTO implements java.io.Serializable {

    private static final long serialVersionUID = -6912985852614844328L;

    private long id;
    private String nombre;
    private double precioUnitario;

    public ProductoDTO(long id, String nombre, double precioUnitario) {
        this.id = id;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
    }
    
    public ProductoDTO(String nombre, double precioUnitario) {
       
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
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

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public Object[] toArray() {
        return new Object[]{
            id, nombre, precioUnitario
        };
    }

}
