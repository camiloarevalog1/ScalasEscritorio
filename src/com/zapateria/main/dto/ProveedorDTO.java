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
public class ProveedorDTO implements java.io.Serializable {

    private static final long serialVersionUID = -6275076094769107526L;

    private long id;
    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;
    private Timestamp fecha;

    public ProveedorDTO(long id, String nombre, String nit, String direccion, String telefono, Timestamp fecha) {
        this.id = id;
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fecha = fecha;
    }

    public ProveedorDTO() {

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

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    public Object[] toArray() {
        return new Object[]{
            this.id,
            this.nombre,
            this.nit,
            this.direccion,
            this.telefono,
            this.fecha
        };
    }

}
