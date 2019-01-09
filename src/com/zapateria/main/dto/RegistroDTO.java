/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author DELL
 */
public class RegistroDTO implements java.io.Serializable {

    private static final long serialVersionUID = -6275076094769107520L;
    private Timestamp fecha;
    private double iva, total_pagar, total_pagado;
    private Date fechaEntrega;
    private long id, id_usuario, id_cliente, id_material, impreso,id_factura;
   

    public long getImpreso() {
        return impreso;
    }

    public long getId_factura() {
        return id_factura;
    }

    public void setId_factura(long id_factura) {
        this.id_factura = id_factura;
    }

    public void setImpreso(long impreso) {
        this.impreso = impreso;
    }
    private ClienteDTO cliente;
    private UsuarioDTO usuario;
    private MaterialDTO material;
    private String comentario;
    private long cantidad;
    private double precio;
    private String formaDePago, estado;
    private long numeroCuotas, cuotas_pagadas;
    DecimalFormat formateador = new DecimalFormat("###,###.###");

    public RegistroDTO(long id, long id_usuario, long id_cliente, long id_material, double precio, long cantidad, String comentario, String estado, String formaPago) {
        this.fecha = fecha;
        this.id = id;
        this.id_usuario = id_usuario;
        this.id_cliente = id_cliente;
        this.id_material = id_material;
        this.precio = precio;
        this.cantidad = cantidad;
        this.comentario = comentario;
        this.estado = estado;
        this.formaDePago = formaPago;

    }

    public RegistroDTO(long id, String formaDePago, Timestamp fecha, double iva, double total_pagar, double total_pagado, Date fechaEntrega, long id_usuario, long id_cliente, long numeroCuotas, long cuotas_pagadas) {
        this.fecha = fecha;
        this.iva = iva;
        this.total_pagar = total_pagar;
        this.total_pagado = total_pagado;
        this.fechaEntrega = fechaEntrega;
        this.id = id;
        this.id_usuario = id_usuario;
        this.id_cliente = id_cliente;
        this.formaDePago = formaDePago;
        this.numeroCuotas = numeroCuotas;
        this.cuotas_pagadas = cuotas_pagadas;
    }

    public RegistroDTO(long id, Timestamp fecha, String estado, long id_cliente, long id_usuario, long cantidad, long id_material,
            String comentario, double precio, Date fechaEntrega, double iva, double total_pagar,long factura) {
        this.fecha = fecha;
        this.id = id;
        this.id_usuario = id_usuario;
        this.id_cliente = id_cliente;
        this.id_material = id_material;
        this.comentario = comentario;
        this.cantidad = cantidad;
        this.precio = precio;

        this.estado = estado;

        this.fechaEntrega = fechaEntrega;
        this.iva = iva;

        this.total_pagar = total_pagar;
        this.id_factura=factura;
    }
    public RegistroDTO(long id, Timestamp fecha, String estado, long id_cliente, long id_usuario, long cantidad, long id_material,
            String comentario, double precio, Date fechaEntrega, double iva, double total_pagar,long impreso,long factura) {
        this.fecha = fecha;
        this.id = id;
        this.id_usuario = id_usuario;
        this.id_cliente = id_cliente;
        this.id_material = id_material;
        this.comentario = comentario;
        this.cantidad = cantidad;
        this.precio = precio;
        this.estado = estado;
        this.fechaEntrega = fechaEntrega;
        this.iva = iva;
        this.total_pagar = total_pagar;
        this.impreso=impreso;
        this.id_factura=factura;
    }

    public RegistroDTO(long id, Timestamp fecha, String estado, String formaDePago, long id_cliente, long id_usuario, long cantidad, long id_material,
            String comentario, double precio, long numeroCuotas, long cuotas_pagadas, Date fechaEntrega, double iva, double total_pagar, double total_pagado, long impreso) {
        this.fecha = fecha;
        this.id = id;
        this.id_usuario = id_usuario;
        this.id_cliente = id_cliente;
        this.id_material = id_material;
        this.comentario = comentario;
        this.cantidad = cantidad;
        this.precio = precio;
        this.formaDePago = formaDePago;
        this.estado = estado;
        this.numeroCuotas = numeroCuotas;
        this.cuotas_pagadas = cuotas_pagadas;
        this.fechaEntrega = fechaEntrega;
        this.iva = iva;
        this.total_pagado = total_pagado;
        this.total_pagar = total_pagar;
        this.impreso = impreso;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public MaterialDTO getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDTO material) {
        this.material = material;
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

    public long getNumeroCuotas() {
        return numeroCuotas;
    }

    public void setNumeroCuotas(long numeroCuotas) {
        this.numeroCuotas = numeroCuotas;
    }

    public long getCuotas_pagadas() {
        return cuotas_pagadas;
    }

    public void setCuotas_pagadas(long cuotas_pagadas) {
        this.cuotas_pagadas = cuotas_pagadas;
    }

    public RegistroDTO(long id, long cantidad, String comentario, double precio, long id_material) {

        this.id = id;

        this.id_material = id_material;
        this.precio = precio;
        this.cantidad = cantidad;
        this.comentario = comentario;

    }

    public long getId_material() {
        return id_material;
    }

    public void setId_material(long id_material) {
        this.id_material = id_material;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal_pagar() {
        return total_pagar;
    }

    public void setTotal_pagar(double total_pagar) {
        this.total_pagar = total_pagar;
    }

    public double getTotal_pagado() {
        return total_pagado;
    }

    public void setTotal_pagado(double total_pagado) {
        this.total_pagado = total_pagado;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public Object[] toArray() {
//        ClienteDTO c= new ClienteDTO();
//       c=servicio.consultarCliente(facturaDTO.getCliente());
 Timestamp stamp = fecha;
             Date date = new Date(stamp.getTime());
             String S = new SimpleDateFormat("dd/MM/yyyy").format(date);

        return new Object[]{
            id, cliente, S , formateador.format(total_pagar),
            fechaEntrega, material, estado,id_factura,comentario
        };
    }

}
