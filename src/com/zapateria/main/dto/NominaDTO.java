/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;

/**
 *
 * @author DELL
 */
public class NominaDTO implements java.io.Serializable {

    private static final long serialVersionUID = 2812808220897955679L;

    private long id;
    private long usuario,horasE;
    private String nombre, apellido,documento,nombreUsuario,rol;
    private double salario, prestamo, pago_total, otros;
    private Date fecha_inicio; 
    private Timestamp  fecha_pago;
    private UsuarioDTO usuarioDTO;
    private int horasTrabajadas,dias_trabajados;
    private double abono;
    DecimalFormat formateador = new DecimalFormat("###,###.###");

    public NominaDTO( long usuario,double salario ,double pago_total,
            Date fecha_inicio, Timestamp fecha_pago, int horasTrabajadas, int dias_trabajados) {
        this.usuario = usuario;
        this.pago_total = pago_total;
        this.fecha_inicio = fecha_inicio;
        this.fecha_pago = fecha_pago;
        this.horasTrabajadas = horasTrabajadas;
        this.dias_trabajados = dias_trabajados;
        this.salario=salario;
        
    }
    

    public long getHorasE() {
        return horasE;
    }

    public void setHorasE(long horasE) {
        this.horasE = horasE;
    }

    public double getAbono() {
        return abono;
    }

    public void setAbono(double abono) {
        this.abono = abono;
    }
      public NominaDTO( long id,long usuario,double salario ,
              double pago_total, Date fecha_inicio, Timestamp fecha_pago, int horasTrabajadas,
              int dias_trabajados,double abon,long horas) {
        this.id=id;
          this.usuario = usuario;
        this.pago_total = pago_total;
        this.fecha_inicio = fecha_inicio;
        this.fecha_pago = fecha_pago;
        this.horasTrabajadas = horasTrabajadas;
        this.dias_trabajados = dias_trabajados;
        this.salario=salario;
        this.abono=abon;
        this.horasE=horas;
        
    }
       public NominaDTO( long id,long usuario,double salario ,double pago_total, Date fecha_inicio, 
               Timestamp fecha_pago, int horasTrabajadas, int dias_trabajados) {
        this.id=id;
          this.usuario = usuario;
        this.pago_total = pago_total;
        this.fecha_inicio = fecha_inicio;
        this.fecha_pago = fecha_pago;
        this.horasTrabajadas = horasTrabajadas;
        this.dias_trabajados = dias_trabajados;
        this.salario=salario;
      
        
    }
       public NominaDTO( long id,long usuario,double salario ,double pago_total, Date fecha_inicio, 
               Timestamp fecha_pago, int horasTrabajadas, int dias_trabajados
               ,String nombre,String Apellido, String documento,String nombre_usuario,String rol) {
        this.id=id;
          this.usuario = usuario;
        this.pago_total = pago_total;
        this.fecha_inicio = fecha_inicio;
        this.fecha_pago = fecha_pago;
        this.horasTrabajadas = horasTrabajadas;
        this.dias_trabajados = dias_trabajados;
        this.salario=salario;
        this.nombre=nombre;
        this.apellido=Apellido;
        this.documento=documento;
        this.nombreUsuario=nombre_usuario;
        this.rol=rol;
      
        
    }
       public NominaDTO( long id,long usuario,double salario ,double pago_total, Date fecha_inicio, 
               Timestamp fecha_pago, int horasTrabajadas, int dias_trabajados
               ,String nombre,String Apellido, String documento,String nombre_usuario,String rol,long horasExtras) {
        this.id=id;
          this.usuario = usuario;
        this.pago_total = pago_total;
        this.fecha_inicio = fecha_inicio;
        this.fecha_pago = fecha_pago;
        this.horasTrabajadas = horasTrabajadas;
        this.dias_trabajados = dias_trabajados;
        this.salario=salario;
        this.nombre=nombre;
        this.apellido=Apellido;
        this.documento=documento;
        this.nombreUsuario=nombre_usuario;
        this.rol=rol;
        this.horasE=horasExtras;
      
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public DecimalFormat getFormateador() {
        return formateador;
    }

    public void setFormateador(DecimalFormat formateador) {
        this.formateador = formateador;
    }
    
    
    

    public NominaDTO(long id, long usuario, double salario, double prestamo,
            double pago_total, double otros, Date fecha_inicio, Timestamp fecha_pago ,int dias_ausentes,int dias_trabajados,long horas) {
        this.id = id;
        this.usuario = usuario;
        this.salario = salario;
        this.prestamo = prestamo;
        this.pago_total = pago_total;
        this.otros = otros;
        this.fecha_inicio = fecha_inicio;
        this.fecha_pago = fecha_pago;
        this.usuarioDTO = usuarioDTO;
        this.horasTrabajadas=horasTrabajadas;
        this.dias_trabajados=dias_trabajados;
    }

    public NominaDTO() {

    }

    public long getId() {
        return id;
    }

    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(int horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

  

    public int getDias_trabajados() {
        return dias_trabajados;
    }

    public void setDias_trabajados(int dias_trabajados) {
        this.dias_trabajados = dias_trabajados;
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

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public double getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(double prestamo) {
        this.prestamo = prestamo;
    }

    public double getPago_total() {
        return pago_total;
    }

    public void setPago_total(double pago_total) {
        this.pago_total = pago_total;
    }

    public double getOtros() {
        return otros;
    }

    public void setOtros(double otros) {
        this.otros = otros;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Timestamp getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Timestamp fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public UsuarioDTO getUsuarioDTO() {
        return usuarioDTO;
    }

    public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
        this.usuarioDTO = usuarioDTO;
    }
    
    public Object[] toArray() {
        return new Object[]{
            usuario,fecha_inicio,salario, fecha_pago,dias_trabajados,horasTrabajadas,formateador.format(pago_total)
             

        };
    }
    
    

}
