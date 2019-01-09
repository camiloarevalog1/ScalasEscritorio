/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dto;

import java.sql.Date;

/**
 *
 * @author Usuario
 */
public class UsuarioDTO implements java.io.Serializable {

    private static final long serialVersionUID = 895938318082279065L;

    private long id;
    private String nombres, apellidos,telefono;
    private String documento,direccion;
    private String nombreUsuario;
    private long rol;
    private RolDTO rolDTO;
    private double valor;

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
    private Date fecha_ingreso;

    public UsuarioDTO(long id, String nombres, String apellidos, String documento, String nombreUsuario, long rol, Date fecha_ingreso) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
        this.fecha_ingreso = fecha_ingreso;
    }
    public UsuarioDTO(long id, String nombres, String apellidos, String documento, long rol) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        
        this.rol = rol;
        
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public UsuarioDTO(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    
    
    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }
    
    
    
    

    public UsuarioDTO(long id, String nombres, String apellidos, String documento, String nombreUsuario, long rol) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
        
    }
    public UsuarioDTO(long id, String nombres, String apellidos, String documento, String nombreUsuario, long rol,String telefono,String direccion,Date fecha_ingreso) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
        this.telefono=telefono;
        this.direccion=direccion;
        this.fecha_ingreso=fecha_ingreso;
        
    }
    public UsuarioDTO(long id, String nombres, String apellidos, String documento, String nombreUsuario, long rol,double valor) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
        this.valor=valor;
        
    }
    
    

    public UsuarioDTO() {

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
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * @param documento the documento to set
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @return the rolsId
     */
    public long getRol() {
        return rol;
    }

    /**
     * @param rolsId the rolsId to set
     */
    public void setRol(long rolsId) {
        this.rol = rolsId;
    }

    /**
     * @return the rolDTO
     */
    public RolDTO getRolDTO() {
        return rolDTO;
    }

    /**
     * @param rolDTO the rolDTO to set
     */
    public void setRolDTO(RolDTO rolDTO) {
        this.rolDTO = rolDTO;
    }

    @Override
    public String toString() {
        return nombres.split(" ")[0] + " " + apellidos.split(" ")[0];
    }

    public Object[] toArray() {
        String telefo="";
        if(telefono.equalsIgnoreCase("null")){
            telefo="";
        }
        
        if(!telefono.equalsIgnoreCase("null")){
            telefo=telefono;
        }
        
        return new Object[]{
            id, nombres, apellidos, documento, nombreUsuario, rolDTO,telefo,direccion
        };
    }
    public Object[] Array() {
        return new Object[]{
            id, nombres, apellidos, documento, nombreUsuario, rolDTO,rolDTO.getValor()
        };
    }

}
