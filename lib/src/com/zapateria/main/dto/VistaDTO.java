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
public class VistaDTO implements java.io.Serializable {

    private static final long serialVersionUID = -5636987373946031986L;

    private long id;
    private String vista;

    public VistaDTO(long id, String vista) {
        this.id = id;
        this.vista = vista;
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
     * @return the vista
     */
    public String getVista() {
        return vista;
    }

    /**
     * @param vista the vista to set
     */
    public void setVista(String vista) {
        this.vista = vista;
    }

    @Override
    public String toString() {
        return this.vista;
    }

    public Object[] toArray() {
        return new Object[]{
            this.id, this.vista
        };
    }

}
