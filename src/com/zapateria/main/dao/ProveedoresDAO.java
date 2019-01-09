package com.zapateria.main.dao;

import com.zapateria.main.dto.ProveedorDTO;
import com.zapateria.main.interfaces.IProveedoresDAO;
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
public class ProveedoresDAO extends ConexionDAO implements IProveedoresDAO {

    private static final long serialVersionUID = -1696906684468261389L;

    public ProveedoresDAO() {
        super();
    }

    @Override
    public boolean registrar(String nombre, String nit, String direccion, String telefono) {
        try {
            String sql = "INSERT INTO proveedores (NOMBRE,NIT,DIRECCION,TELEFONO)\n"
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
                    + "FROM proveedores WHERE ID=?";

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

    public ProveedorDTO consultarNombre(String nombre) {
        try {
            String sql = "SELECT ID,NOMBRE,NIT,DIRECCION,TELEFONO,FECHA\n"
                    + "FROM proveedores WHERE NOMBRE=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, nombre);
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
       Connection link = null;
        lista = new ArrayList();

        try {
            String sql = "SELECT ID,NOMBRE,NIT,DIRECCION,TELEFONO,FECHA FROM proveedores";

          link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);

            ResultSet rs = str.executeQuery();
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
          if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProveedoresDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }

    @Override
    public boolean editar(long id, String nombre, String nit, String direccion, String telefono) {
        try {
            String sql = "UPDATE proveedores SET NOMBRE=?,NIT=?,DIRECCION=?,TELEFONO=? WHERE ID=?";

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
            String sql = "DELETE FROM proveedores WHERE ID=?";

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
