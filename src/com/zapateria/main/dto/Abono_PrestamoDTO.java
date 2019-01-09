/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dto;

import java.sql.Timestamp;
import java.text.DecimalFormat;

/**
 *
 * @author DELL
 */
public class Abono_PrestamoDTO implements java.io.Serializable {

    private static final long serialVersionUID = -6626894140208669799L;

    private long id;
    private long usuario;
    private UsuarioDTO usuarioDTO;
    private double total_prestamo;
    private double total_debe;
    private double total_abonado;
    
    public Abono_PrestamoDTO(){
        
    }

    public Abono_PrestamoDTO(long usuario, double total_prestamo, double total_debe, double total_abonado) {
        this.usuario = usuario;
        this.total_prestamo = total_prestamo;
        this.total_debe = total_debe;
        this.total_abonado = total_abonado;
    }

    public Abono_PrestamoDTO(double valor) {
        this.total_debe = valor;
    }

    public Abono_PrestamoDTO(long id, long usuario, double total_prestamo, double total_debe,double total_abonado) {
        this.id = id;
        this.usuario = usuario;
        this.total_prestamo = total_prestamo;
        this.total_debe = total_debe;
        this.total_abonado=total_abonado;
    }

    public long getId() {
        return id;
    }

    public double getTotal_abonado() {
        return total_abonado;
    }

    public void setTotal_abonado(double total_abonado) {
        this.total_abonado = total_abonado;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUsuario() {
        return usuario;
    }

    public void setUsuario(long usuario) {
        this.usuario = usuario;
    }

    public UsuarioDTO getUsuarioDTO() {
        return usuarioDTO;
    }

    public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
        this.usuarioDTO = usuarioDTO;
    }

    public double getTotal_prestamo() {
        return total_prestamo;
    }

    public void setTotal_prestamo(double total_prestamo) {
        this.total_prestamo = total_prestamo;
    }

    public double getTotal_debe() {
        return total_debe;
    }

    public void setTotal_debe(double total_debe) {
        this.total_debe = total_debe;
    }
    DecimalFormat formateador = new DecimalFormat("###,###.###");
    public Object[] toArray() {
        return new Object[]{
           total_prestamo,formateador.format(total_abonado),formateador.format(total_debe)
        };

    }

}
