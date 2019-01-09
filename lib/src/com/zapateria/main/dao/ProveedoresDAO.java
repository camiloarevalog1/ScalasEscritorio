package com.zapateria.main.dao;

import com.zapateria.main.dto.ProveedorDTO;
import com.zapateria.main.interfaces.IProveedoresDAO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class ProveedoresDAO extends ConexionDAO implements IProveedoresDAO {

    private static final long serialVersionUID = -1696906684468261389L;

    public ProveedoresDAO() {
        super();
    }

    @Override
    public boolean registrar(String nombre, String nit, String direccion, String telefono) {
        try {
            String sql = "INSERT INTO PROVEEDORES (NOMBRE,NIT,DIRECCION,TELEFONO)\n"
                    + "VALUES(?,?,?,?)";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, nombre);
            pst.setString(2, nit);
            pst.setString(3, direccion);
            pst.setString(4, telefono);

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
    public ProveedorDTO consultar(long id) {
        try {
            String sql = "SELECT ID,NOMBRE,NIT,DIRECCION,TELEFONO,FECHA\n"
                    + "FROM PROVEEDORES WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {
                return new ProveedorDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getTimestamp(6)
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
    public ArrayList<ProveedorDTO> listar() {
        lista = new ArrayList();

        try {
            String sql = "SELECT ID,NOMBRE,NIT,DIRECCION,TELEFONO,FECHA FROM PROVEEDORES";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                lista.add(new ProveedorDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getTimestamp(6)
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
    public boolean editar(long id, String nombre, String nit, String direccion, String telefono) {
        try {
            String sql = "UPDATE PROVEEDORES SET NOMBRE=?,NIT=?,DIRECCION=?,TELEFONO=? WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, nombre);
            pst.setString(2, nit);
            pst.setString(3, direccion);
            pst.setString(4, telefono);
            pst.setLong(5, id);
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
            String sql = "DELETE FROM PROVEEDORES WHERE ID=?";

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
