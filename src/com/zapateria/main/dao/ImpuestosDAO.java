/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.ImpuestoDTO;
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
 * @author Diego
 */
public class ImpuestosDAO extends ConexionDAO implements com.zapateria.main.interfaces.IImpuestosDAO {

    private static final long serialVersionUID = -7213218500103021691L;

    @Override
    public boolean registrar(String nombre, double valor) {
        try {
            String sql = "INSERT INTO impuestos(NOMBRE,VALOR) VALUES(?,?)";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, nombre);
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
    public ImpuestoDTO consultar(long id) {
        Connection link = null;
        try {
            String sql = "SELECT ID,NOMBRE,VALOR FROM impuestos WHERE ID=?";

           link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);

            
            str.setLong(1, id);
            ResultSet rs = str.executeQuery();

            if (rs.next() && rs.absolute(1)) {
                return new ImpuestoDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getDouble(3));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
          if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ImpuestosDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public ArrayList<ImpuestoDTO> listar() {
        lista = new ArrayList();
        try {
            String sql = "SELECT ID,NOMBRE,VALOR FROM impuestos";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                lista.add(new ImpuestoDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getDouble(3))
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
        return lista;
    }

    @Override
    public boolean editar(long id, String nombre, double valor) {
        try {
            String sql = "UPDATE impeustos SET NOMBRE=?,VALOR=? WHERE ID=?";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, nombre);
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
    public boolean eliminar(long id) {
        try {
            String sql = "DELETE FROM impuestos WHERE ID=? AND NOMBRE!='IVA'";
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
