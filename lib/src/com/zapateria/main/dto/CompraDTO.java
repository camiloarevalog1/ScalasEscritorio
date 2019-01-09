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
public class CompraDTO implements java.io.Serializable {

    private static final long serialVersionUID = -5423960839283355727L;

    private long id, cantidad;
    private double valorUnitario, valorTotal;
    private Timestamp fecha;
    private long proveedor, material;
    private ProveedorDTO proveedorDTO;
    private MaterialDTO matrialDTO;

    public CompraDTO(long id, long cantidad, double valorUnitario,
            double valorTotal, Timestamp fecha, long proveedor, long material) {
        this.id = id;
        this.cantidad = cantidad;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.material = material;
    }

    public CompraDTO() {

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

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public long getProveedor() {
        return proveedor;
    }

    public void setProveedor(long proveedor) {
        this.proveedor = proveedor;
    }

    public long getMaterial() {
        return material;
    }

    public void setMaterial(long material) {
        this.material = material;
    }

    public ProveedorDTO getProveedorDTO() {
        return proveedorDTO;
    }

    public void setProveedorDTO(ProveedorDTO proveedorDTO) {
        this.proveedorDTO = proveedorDTO;
    }

    public MaterialDTO getMatrialDTO() {
        return matrialDTO;
    }

    public void setMaterialDTO(MaterialDTO matrialDTO) {
        this.matrialDTO = matrialDTO;
    }

    @Override
    public String toString() {
        return this.id + "";
    }

    public Object[] toArray() {
        return new Object[]{
            this.id,
            this.cantidad,
            this.valorUnitario,
            this.valorTotal,
            this.fecha,
            this.proveedorDTO,
            this.matrialDTO
        };
    }
}
