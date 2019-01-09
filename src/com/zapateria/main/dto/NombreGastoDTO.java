/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dto;

import java.sql.Timestamp;

/**
 *
 * @author DELL
 */
public class NombreGastoDTO implements java.io.Serializable{
     private long id;
     private String nombre;
     private Timestamp fecha;

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

    public NombreGastoDTO(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
     
     @Override
    public String toString() {
        return this.nombre;
    }

    public Object[] toArray() {
        return new Object[]{
            this.id,
            this.nombre
           
        };
    }
    
}
