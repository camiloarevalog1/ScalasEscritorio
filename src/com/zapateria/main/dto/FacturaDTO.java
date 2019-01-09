package com.zapateria.main.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Diego
 */
public class FacturaDTO implements java.io.Serializable {

    private static final long serialVersionUID = 2791904450218187774L;
    public static final String ESTADO_DEPOSITO = "DEPOSITO", ESTADO_ENTREGADO = "ENTREGADO",
            ESTADO_PROCESO = "PROCESO";

    public static final String FORMA_PAGO_CONTADO = "CONTADO", FORMA_PAGO_CUOTAS = "CUOTAS";

    private long id;
    private String formaDePago, estado,factura;
    private Timestamp fechaFactura;
    private double iva, totalPagar, totalPagado;
    private Date fechaEntrega;
    private long cliente, usuario;
    private ClienteDTO clienteDTO;
    private UsuarioDTO usuarioDTO;
    private long numeroCuotas, cuotas_pagadas;
    DecimalFormat formateador = new DecimalFormat("###,###.###");

    public FacturaDTO(long id, Timestamp fechaFactura, double iva, double totalPagar, double totalPagado) {
        this.id = id;
        this.fechaFactura = fechaFactura;
        this.iva = iva;
        this.totalPagar = totalPagar;
        this.totalPagado = totalPagado;
    }

    public FacturaDTO(long id, String formaDePago, Timestamp fechaFactura,
            double iva, double totalPagar, double totalPagado, Date fechaEntrega, long cliente, long usuario,
            long numeroCuotas, long cuotas_pagadas) {

        this.id = id;
        this.formaDePago=formaDePago;
        this.fechaFactura = fechaFactura;
        this.iva = iva;
        this.totalPagar = totalPagar;
        this.totalPagado = totalPagado;
        this.fechaEntrega = fechaEntrega;
        this.cliente = cliente;
        this.usuario = usuario;
        this.numeroCuotas = numeroCuotas;
        this.cuotas_pagadas = cuotas_pagadas;
    }

    public FacturaDTO(long id,Timestamp fechaFactura, double iva, double totalPagar, double totalPagado, long cliente, long usuario,String factura,String forma) {
        this.id=id;
        this.fechaFactura = fechaFactura;
        this.iva = iva;
        this.totalPagar = totalPagar;
        this.totalPagado = totalPagado;
        this.cliente = cliente;
        this.usuario = usuario;
        this.factura=factura;
        this.formaDePago=forma;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }
    
    

    public long getNumeroCuotas() {
        return numeroCuotas;
    }

    public long getCuotas_pagadas() {
        return cuotas_pagadas;
    }

    public void setCuotas_pagadas(long cuotas_pagadas) {
        this.cuotas_pagadas = cuotas_pagadas;
    }

    public void setNumeroCuotas(long numeroCuotas) {
        this.numeroCuotas = numeroCuotas;
    }

    public FacturaDTO() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFormaDePago() {
        return formaDePago;
    }

    public void setFormaDePago(String formaDePago) {
        this.formaDePago = formaDePago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Timestamp fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(double totalPagar) {
        this.totalPagar = totalPagar;
    }

    public double getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(double totalPagado) {
        this.totalPagado = totalPagado;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public long getCliente() {
        return cliente;
    }

    public void setCliente(long cliente) {
        this.cliente = cliente;
    }

    public long getUsuario() {
        return usuario;
    }

    public void setUsuario(long usuario) {
        this.usuario = usuario;
    }

    public ClienteDTO getClienteDTO() {
        return clienteDTO;
    }

    public void setClienteDTO(ClienteDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
    }

    public UsuarioDTO getUsuarioDTO() {
        return usuarioDTO;
    }

    public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
        this.usuarioDTO = usuarioDTO;
    }

    @Override
    public String toString() {
        return String.format("%04d", id);
    }

    public Object[] toArray() {
         Timestamp stamp = fechaFactura;
             Date date = new Date(stamp.getTime());
             String S = new SimpleDateFormat("dd/MM/yyyy").format(date);
        double Saldo = 0;
       
                Saldo = totalPagar - totalPagado;
       
        return new Object[]{
            id,S, formateador.format(iva), formateador.format(totalPagar), formateador.format(totalPagado),
             clienteDTO, usuarioDTO, formateador.format(Saldo),formaDePago,factura

        };
    }

}
