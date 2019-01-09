/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.VistaDTO;
import com.zapateria.main.interfaces.IVistasDAO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class VistasDAO extends ConexionDAO implements IVistasDAO {

    private static final long serialVersionUID = 2188379338620699688L;

    public VistasDAO() {
        super();
    }

    @Override
    public boolean registrar(String vista) {
        try {
            String sql = "INSERT INTO VISTAS (VISTA) VALUES(?)";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, vista);
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
    public VistaDTO consultar(long id) {
        try {
            String sql = "SELECT ID,VISTA FROM VISTAS WHERE ID=?";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {
                return new VistaDTO(rs.getInt(1), rs.getString(2));
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
    public boolean editar(long id, String vista) {
        try {
            String sql = "UPDATE VISTAS SET VISTA=? WHERE ID=?";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, vista);
            pst.setLong(2, id);
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
    public ArrayList<VistaDTO> listar() {
        lista = new ArrayList<>();
        try {
            String sql = "SELECT ID,VISTA FROM VISTAS";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                lista.add(new VistaDTO(
                        rs.getLong(1),
                        rs.getString(2)
                ));
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
    public ArrayList<VistaDTO> listar(long rol) {
        lista = new ArrayList<>();
        try {
            String sql = "SELECT V.ID,V.VISTA FROM VISTAS V, PERMISOS P WHERE"
                    + "\nV.ID=P.VISTAS_ID AND P.ROLES_ID=?";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, rol);
            rs = pst.executeQuery();
            while (rs.next()) {
                lista.add(new VistaDTO(
                        rs.getLong(1),
                        rs.getString(2)
                ));
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
    public boolean eliminar(long id) {
        try {
            String sql = "DELETE FROM VISTAS WHERE ID=?";
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
