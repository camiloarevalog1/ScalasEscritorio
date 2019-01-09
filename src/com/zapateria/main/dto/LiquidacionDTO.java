/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dto;

import java.sql.Date;
import java.text.DecimalFormat;

/**
 *
 * @author DELL
 */
public class LiquidacionDTO implements java.io.Serializable {

    private static final long serialVersionUID = 2791904450218187765L;

    private long id;
    private long usuario;
    private Date fecha_salida;
    private int dias_trabajados,horas_trabajadas;
    private Date fecha_entrada;
    private double sueldo_basico, cesantias, intereses_cesantias, prima_servicio_, vacaciones, valor_liquidacion, bonificacion;
    private UsuarioDTO usuarioDTO;
    DecimalFormat formateador = new DecimalFormat("###,###.###");
    public LiquidacionDTO() {
        
    }

    public int getHoras_trabajadas() {
        return horas_trabajadas;
    }

    public void setHoras_trabajadas(int horas_trabajadas) {
        this.horas_trabajadas = horas_trabajadas;
    }

    public LiquidacionDTO(long usuario, Date fecha_salida,int horas_trabajadas ,int dias_trabajados, Date fecha_entrada, double sueldo_basico, double cesantias, double intereses_cesantias, double prima_servicio_, double vacaciones, double valor_liquidacion, double bonificacion) {
        this.usuario = usuario;
        this.fecha_salida = fecha_salida;
        this.dias_trabajados = dias_trabajados;
        this.fecha_entrada = fecha_entrada;
        this.sueldo_basico = sueldo_basico;
        this.cesantias = cesantias;
        this.intereses_cesantias = intereses_cesantias;
        this.prima_servicio_ = prima_servicio_;
        this.vacaciones = vacaciones;
        this.valor_liquidacion = valor_liquidacion;
        this.bonificacion = bonificacion;
        this.horas_trabajadas=horas_trabajadas;
    }
     public LiquidacionDTO(long id,long usuario, Date fecha_salida,int horas_trabajadas ,int dias_trabajados, Date fecha_entrada, double sueldo_basico, double cesantias, double intereses_cesantias, double prima_servicio_, double vacaciones, double valor_liquidacion, double bonificacion) {
        this.id=id;
        this.usuario = usuario;
        this.fecha_salida = fecha_salida;
        this.dias_trabajados = dias_trabajados;
        this.fecha_entrada = fecha_entrada;
        this.sueldo_basico = sueldo_basico;
        this.cesantias = cesantias;
        this.intereses_cesantias = intereses_cesantias;
        this.prima_servicio_ = prima_servicio_;
        this.vacaciones = vacaciones;
        this.valor_liquidacion = valor_liquidacion;
        this.bonificacion = bonificacion;
        this.horas_trabajadas=horas_trabajadas;
    }
    public LiquidacionDTO(long usuario, Date fecha_salida ,int dias_trabajados, Date fecha_entrada, double sueldo_basico, double cesantias, double intereses_cesantias, double prima_servicio_, double vacaciones, double valor_liquidacion, double bonificacion) {
        this.usuario = usuario;
        this.fecha_salida = fecha_salida;
        this.dias_trabajados = dias_trabajados;
        this.fecha_entrada = fecha_entrada;
        this.sueldo_basico = sueldo_basico;
        this.cesantias = cesantias;
        this.intereses_cesantias = intereses_cesantias;
        this.prima_servicio_ = prima_servicio_;
        this.vacaciones = vacaciones;
        this.valor_liquidacion = valor_liquidacion;
        this.bonificacion = bonificacion;
       
    }
    public LiquidacionDTO(long id,long usuario, Date fecha_salida ,int dias_trabajados, Date fecha_entrada, double sueldo_basico, double cesantias, double intereses_cesantias, double prima_servicio_, double vacaciones, double valor_liquidacion, double bonificacion) {
        this.id=id;
        this.usuario = usuario;
        this.fecha_salida = fecha_salida;
        this.dias_trabajados = dias_trabajados;
        this.fecha_entrada = fecha_entrada;
        this.sueldo_basico = sueldo_basico;
        this.cesantias = cesantias;
        this.intereses_cesantias = intereses_cesantias;
        this.prima_servicio_ = prima_servicio_;
        this.vacaciones = vacaciones;
        this.valor_liquidacion = valor_liquidacion;
        this.bonificacion = bonificacion;
       
    }
    
    

    public long getId() {
        return id;
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

    public Date getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(Date fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public int getDias_trabajados() {
        return dias_trabajados;
    }

    public void setDias_trabajados(int dias_trabajados) {
        this.dias_trabajados = dias_trabajados;
    }

    public Date getFecha_entrada() {
        return fecha_entrada;
    }

    public void setFecha_entrada(Date fecha_entrada) {
        this.fecha_entrada = fecha_entrada;
    }

    public double getSueldo_basico() {
        return sueldo_basico;
    }

    public void setSueldo_basico(double sueldo_basico) {
        this.sueldo_basico = sueldo_basico;
    }

    public double getCesantias() {
        return cesantias;
    }

    public void setCesantias(double cesantias) {
        this.cesantias = cesantias;
    }

    public double getIntereses_cesantias() {
        return intereses_cesantias;
    }

    public void setIntereses_cesantias(double intereses_cesantias) {
        this.intereses_cesantias = intereses_cesantias;
    }

    public double getPrima_servicio_() {
        return prima_servicio_;
    }

    public void setPrima_servicio_(double prima_servicio_) {
        this.prima_servicio_ = prima_servicio_;
    }

    public double getVacaciones() {
        return vacaciones;
    }

    public void setVacaciones(double vacaciones) {
        this.vacaciones = vacaciones;
    }

    public double getValor_liquidacion() {
        return valor_liquidacion;
    }

    public void setValor_liquidacion(double valor_liquidacion) {
        this.valor_liquidacion = valor_liquidacion;
    }

    public double getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(double bonificacion) {
        this.bonificacion = bonificacion;
    }

    public UsuarioDTO getUsuarioDTO() {
        return usuarioDTO;
    }

    public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
        this.usuarioDTO = usuarioDTO;
    }

    public Object[] toArray() {
        return new Object[]{
            usuario,formateador.format(sueldo_basico),fecha_entrada,fecha_salida,dias_trabajados,formateador.format(cesantias),
            formateador.format(intereses_cesantias),formateador.format(prima_servicio_),formateador.format(vacaciones),formateador.format(bonificacion),
            formateador.format(valor_liquidacion)

        };
    }

}
