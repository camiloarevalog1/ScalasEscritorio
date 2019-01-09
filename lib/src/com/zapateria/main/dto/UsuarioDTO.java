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
public class UsuarioDTO implements java.io.Serializable {

    private static final long serialVersionUID = 895938318082279065L;

    private long id;
    private String nombres, apellidos;
    private String documento;
    private String nombreUsuario;
    private long rol;
    private RolDTO rolDTO;

    public UsuarioDTO(long id, String nombres, String apellidos, String documento, String nombreUsuario, long rol) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
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
        return new Object[]{
            id, nombres, apellidos, documento, nombreUsuario, rolDTO
        };
    }

}
