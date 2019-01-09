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
public class PermisoDTO implements java.io.Serializable {

    private static final long serialVersionUID = -6626894140208669733L;

    private long id, rol, vista;
    private RolDTO rolDTO;
    private VistaDTO vistaDTO;

    private static final String MATERIALES = "MATERIALES", PROVEEDORES = "PROVEEDORES", COMPRAS = "COMPRAS";

    public PermisoDTO(long id, long rol, long vista) {
        this.id = id;
        this.rol = rol;
        this.vista = vista;
    }

    public PermisoDTO() {

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
     * @return the rol
     */
    public long getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(long rol) {
        this.rol = rol;
    }

    /**
     * @return the vista
     */
    public long getVista() {
        return vista;
    }

    /**
     * @param vista the vista to set
     */
    public void setVista(long vista) {
        this.vista = vista;
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

    /**
     * @return the vistaDTO
     */
    public VistaDTO getVistaDTO() {
        return vistaDTO;
    }

    /**
     * @param vistaDTO the vistaDTO to set
     */
    public void setVistaDTO(VistaDTO vistaDTO) {
        this.vistaDTO = vistaDTO;
    }

    public Object[] toArray() {
        return new Object[]{
            this.id, this.rolDTO, this.vistaDTO
        };
    }
}
