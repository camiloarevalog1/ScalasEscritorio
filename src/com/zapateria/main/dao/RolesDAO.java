/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.RolDTO;
import com.zapateria.main.dto.UsuarioDTO;
import com.zapateria.main.interfaces.IRolesDAO;
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
public class RolesDAO extends ConexionDAO implements IRolesDAO {

    private static final long serialVersionUID = 3290965084077494179L;

    public RolesDAO() {
        super();
    }

    @Override
    public boolean registrar(String rol, double valor) {
        try {
            String sql = "INSERT INTO roles (ROL,VALOR) VALUES(?,?)";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, rol);
            pst.setDouble(2, valor);
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
    public boolean editar(long id, String rol, double valor) {
        try {
            String sql = "UPDATE roles SET ROL=?, VALOR=?  WHERE ID=? ";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, rol);

            pst.setDouble(2, valor);
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
    public ArrayList<RolDTO> listar() {
        lista = new ArrayList<>();
        Connection link = null;
        try {

            String sql = "SELECT ID,ROL,VALOR FROM roles";

            link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);

            ResultSet rs = str.executeQuery();

            while (rs.next()) {
                lista.add(new RolDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getDouble(3)
                ));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RolesDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println("retornando consulta");
        return lista;
    }

    @Override
    public RolDTO consultarNombre(String nombre) {
        try {
            String sql = "SELECT ID,ROL,VALOR FROM roles WHERE ROL=?";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, nombre);
            rs = pst.executeQuery();
            if (rs.next() && rs.absolute(1)) {
                return new RolDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getDouble(3)
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
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
        return null;

    }

    @Override
    public RolDTO consultar(long id) {
        Connection link = null;
        try {
            String sql = "SELECT ID,ROL,VALOR FROM roles WHERE ID=?";
            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);
           ResultSet rs = str.executeQuery();
            if (rs.next() && rs.absolute(1)) {
                return new RolDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getDouble(3)
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RolDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public boolean eliminar(long id) {
        try {
            String sql = "DELETE FROM roles WHERE ID=?";
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
}
