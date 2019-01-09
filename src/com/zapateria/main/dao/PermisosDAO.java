/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.PermisoDTO;
import com.zapateria.main.dto.RolDTO;
import com.zapateria.main.dto.VistaDTO;
import com.zapateria.main.interfaces.IPermisosDAO;
import com.zapateria.main.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class PermisosDAO extends ConexionDAO implements IPermisosDAO {

    private static final long serialVersionUID = 3160418420436125428L;

    public PermisosDAO() {
        super();
    }

    @Override
    public boolean registrar(long rol, long vista) {
        try {
            String sql = "INSERT INTO permisos (ROLES_ID,VISTAS_ID) VALUES(?,?)";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, rol);
            pst.setLong(2, vista);
            return pst.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean editar(long id, long rol, long vista) {
        try {
            String sql = "UPDATE permisos SET ROLES_ID=?, VISTAS_ID=? WHERE ID=?";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, rol);
            pst.setLong(2, vista);
            pst.setLong(3, id);
            return pst.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean eliminar(long id) {
        try {
            String sql = "DELETE FROM permisos WHERE ID=?";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            return pst.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public ArrayList<PermisoDTO> listar() {
        Connection link = null;
        lista = new ArrayList<>();
        try {
            String sql = "SELECT P.ID,P.ROLES_ID,P.VISTAS_ID,R.ROL,V.VISTA FROM\n"
                    + "permisos P,roles R,vistas V WHERE P.ROLES_ID=R.ID AND V.ID=P.VISTAS_ID";
            
           link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);

            ResultSet rs = str.executeQuery();
            
            while (rs.next()) {

                PermisoDTO permiso = new PermisoDTO(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getLong(3)
                );

                permiso.setRolDTO(new RolDTO(
                        permiso.getRol(),
                        rs.getString(4))
                );
                permiso.setVistaDTO(new VistaDTO(
                        permiso.getVista(),
                        rs.getString(5))
                );

                lista.add(permiso);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
          if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PermisosDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }
}
