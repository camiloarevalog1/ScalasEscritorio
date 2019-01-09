/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.ProductoDTO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class ProductosDAO extends ConexionDAO implements com.zapateria.main.interfaces.IProductosDAO {

    private static final long serialVersionUID = -5508698845154027336L;

    public ProductosDAO() {
        super();
    }

    @Override
    public boolean registrar(String nombre, double precioUnitario) {
        try {
            String sql = "INSERT INTO PRODUCTOS(NOMBRE,PRECIO_UNITARIO) VALUES"
                    + "(?,?)";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, nombre);
            pst.setDouble(2, precioUnitario);
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
    public ProductoDTO consultar(long id) {
        try {
            String sql = "SELECT ID,NOMBRE,PRECIO_UNITARIO FROM PRODUCTOS WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {
                return new ProductoDTO(
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
    public ArrayList<ProductoDTO> listar() {
        lista = new ArrayList();
        try {
            String sql = "SELECT ID,NOMBRE,PRECIO_UNITARIO FROM PRODUCTOS";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                lista.add(new ProductoDTO(
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
    public ArrayList<ProductoDTO> buscar(String busqueda) {
        lista = new ArrayList();
        try {
            String sql = "SELECT P.ID,P.NOMBRE,P.PRECIO_UNITARIO FROM FT_SEARCH_DATA(?, 0, 0) FT,"
                    + " PRODUCTOS P WHERE FT.TABLE='PRODUCTOS' AND P.ID=FT.KEYS[0]";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, busqueda);

            rs = pst.executeQuery();

            while (rs.next()) {
                lista.add(new ProductoDTO(
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
    public boolean editar(long id, String nombre, double precioUnitario) {
        try {
            String sql = "UPDATE PRODUCTOS SET NOMBRE=?,PRECIO_UNITARIO=? WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, nombre);
            pst.setDouble(2, precioUnitario);
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
            String sql = "DELETE FROM PRODUCTOS WHERE ID=?";

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
