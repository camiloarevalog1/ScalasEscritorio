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
public class VentaDTO implements java.io.Serializable {

    private static final long serialVersionUID = -1942518992912940408L;

    private long id, cantidad;
    private String comentario;
    private long factura, producto;
    private FacturaDTO facturaDTO;
    private ProductoDTO productoDTO;

    public VentaDTO(long id, long cantidad, String comentario, long factura, long producto) {
        this.id = id;

        this.cantidad = cantidad;
        this.comentario = comentario;
        this.factura = factura;
        this.producto = producto;
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public long getFactura() {
        return factura;
    }

    public void setFactura(long factura) {
        this.factura = factura;
    }

    public long getProducto() {
        return producto;
    }

    public void setProducto(long producto) {
        this.producto = producto;
    }

    public FacturaDTO getFacturaDTO() {
        return facturaDTO;
    }

    public void setFacturaDTO(FacturaDTO facturaDTO) {
        this.facturaDTO = facturaDTO;
    }

    public ProductoDTO getProductoDTO() {
        return productoDTO;
    }

    public void setProductoDTO(ProductoDTO productoDTO) {
        this.productoDTO = productoDTO;
    }

    @Override
    public String toString() {
        return "" + id;
    }

    public Object[] toArray() {
        return new Object[]{
            id,
            productoDTO,
            cantidad,
            productoDTO.getPrecioUnitario(),
            comentario
        };
    }

}
