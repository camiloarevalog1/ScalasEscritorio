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
public class MaterialUsadoDTO implements java.io.Serializable {

    private static final long serialVersionUID = 8957974376945774980L;

    private long id;
    private long cantidad;
    private Timestamp fecha;
    private long material;
    private MaterialDTO materialDTO;

    public MaterialUsadoDTO(long id, long cantidad, Timestamp fecha, long material) {
        this.id = id;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.material = material;
    }

    public MaterialUsadoDTO() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public long getMaterial() {
        return material;
    }

    public void setMaterial(long material) {
        this.material = material;
    }

    public MaterialDTO getMaterialDTO() {
        return materialDTO;
    }

    public void setMaterialDTO(MaterialDTO materialDTO) {
        this.materialDTO = materialDTO;
    }

    @Override
    public String toString() {
        return fecha.toString();
    }

    public Object[] toArray() {
        return new Object[]{
            id,
            cantidad,
            fecha,
            materialDTO
        };
    }

}
