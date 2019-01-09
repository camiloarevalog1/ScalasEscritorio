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
public class ClienteDTO implements java.io.Serializable {

    private static final long serialVersionUID = 2812808220897956498L;

    private long id;
    private String documento, nombres, apellidos, direccion, telefono;
    private Timestamp fechaRegistro;

    public ClienteDTO() {
    }

    public ClienteDTO(long id, String documento, String nombres, String apellidos,
            String direccion, String telefono, Timestamp fechaRegistro) {
        this.id = id;
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return nombres.split(" ")[0] + " " + apellidos.split(" ")[0];
    }

    public Object[] toArray() {
        return new Object[]{
            id,
            documento,
            nombres,
            apellidos,
            direccion,
            telefono,
            fechaRegistro
        };
    }

}
