/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dto;

import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class SesionDTO extends UsuarioDTO {

    private static final long serialVersionUID = 8782681512648397571L;

    private ArrayList<VistaDTO> vistas = null;

    public SesionDTO(long id, String nombres, String apellidos, String documento, String nombreUsuario, long rol) {
        super(id, nombres, apellidos, documento, nombreUsuario, rol);
    }

    public boolean isVistaPermitida(String vistaActual) {

        boolean esPermitida = false;

        for (VistaDTO pagina : getVistas()) {
            if (pagina.getVista().equals(vistaActual)) {
                esPermitida = true;
                break;
            }
        }
        return esPermitida;
    }

    /**
     * @return the vistas
     */
    public ArrayList<VistaDTO> getVistas() {
        return vistas;
    }

    /**
     * @param vistas the vistas to set
     */
    public void setVistas(ArrayList<VistaDTO> vistas) {
        this.vistas = vistas;
    }

}
