/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dto;

/**
 *
 * @author Usuario
 */
public class RolDTO implements java.io.Serializable {

    private static final long serialVersionUID = 4506440672181551713L;

    private long id;
    private String rol;
    private double valor;

    public RolDTO(long id, String rol,double valor) {
        this.id = id;
        this.rol = rol;
        this.valor=valor;
    }
    public RolDTO(long id,String rol){
        this.id = id;
        this.rol = rol;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return this.rol;
    }

    public Object[] toArray() {
        return new Object[]{this.id, this.rol,this.valor};
    }
}
