/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.ClienteDTO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class ClientesDAO extends ConexionDAO implements com.zapateria.main.interfaces.IClientesDAO {

//    private static final long serialVersionUID = 3117878119281705244L;
    public ClientesDAO() {
        super();
    }

    @Override
    public long registrarCliente(String documento, String nombres, String apellidos,
            String direccion, String telefono) {
        long x = 0;
        try {
            String sql = "INSERT INTO CLIENTES (DOCUMENTO,NOMBRES,APELLIDOS,DIRECCION,TELEFONO)\n"
                    + "VALUES(?,?,?,?,?)";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, documento);
            pst.setString(2, nombres);
            pst.setString(3, apellidos);
            pst.setString(4, direccion);
            pst.setString(5, telefono);
            pst.executeUpdate();

            String sq = "SELECT MAX(ID) FROM CLIENTES ";

            pst = con.prepareStatement(sq);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {
                return rs.getLong(1);
            }

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
        return x;
    }

    @Override
    public boolean validarCedula(String cedula) {
        try {
            String sql = "SELECT DOCUMENTO FROM CLIENTES WHERE DOCUMENTO=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, cedula);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {

                return true;
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
        return false;
    }

    @Override
    public boolean registrar(String documento, String nombres, String apellidos,
            String direccion, String telefono) {

        try {
            String sql = "INSERT INTO CLIENTES (DOCUMENTO,NOMBRES,APELLIDOS,DIRECCION,TELEFONO)\n"
                    + "VALUES(?,?,?,?,?)";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, documento);
            pst.setString(2, nombres);
            pst.setString(3, apellidos);
            pst.setString(4, direccion);
            pst.setString(5, telefono);
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
    public boolean registrar(String documento, String nombres, String apellidos) {
        try {
            String sql = "INSERT INTO CLIENTES (DOCUMENTO,NOMBRES,APELLIDOS)\n"
                    + "VALUES(?,?,?)";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, documento);
            pst.setString(2, nombres);
            pst.setString(3, apellidos);
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
    public ClienteDTO consultar(long id) {

        try {
            String sql = "SELECT ID,DOCUMENTO,NOMBRES,APELLIDOS,DIRECCION,"
                    + "TELEFONO,FECHA_REGISTRO FROM CLIENTES WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {

                return new ClienteDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getTimestamp(7));
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
    public boolean editar(long id, String documento, String nombres, String apellidos,
            String direccion, String telefono) {
        try {
            String sql = "UPDATE CLIENTES SET DOCUMENTO=?,NOMBRES=?,APELLIDOS=?,"
                    + "DIRECCION=?,TELEFONO=? WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, documento);
            pst.setString(2, nombres);
            pst.setString(3, apellidos);
            pst.setString(4, direccion);
            pst.setString(5, telefono);
            pst.setLong(6, id);
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
    public boolean editar(long id, String documento, String nombres, String apellidos) {
        try {
            String sql = "UPDATE CLIENTES SET DOCUMENTO=?,NOMBRES=?,APELLIDOS=? WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, documento);
            pst.setString(2, nombres);
            pst.setString(3, apellidos);
            pst.setLong(4, id);
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
    public ArrayList<ClienteDTO> listar() {

        lista = new ArrayList();

        try {
            String sql = "SELECT ID,DOCUMENTO,NOMBRES,APELLIDOS,DIRECCION,"
                    + "TELEFONO,FECHA_REGISTRO FROM CLIENTES";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                lista.add(new ClienteDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getTimestamp(7))
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
    public ArrayList<ClienteDTO> buscar(String busqueda) {

        lista = new ArrayList();

        try {
            String sql = "SELECT C.ID,C.DOCUMENTO,C.NOMBRES,C.APELLIDOS,C.DIRECCION,"
                    + "C.TELEFONO,C.FECHA_REGISTRO FROM FT_SEARCH_DATA(?, 0, 0) FT,"
                    + "CLIENTES C WHERE FT.TABLE='CLIENTES' AND C.ID=FT.KEYS[0];";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, busqueda);

            rs = pst.executeQuery();

            while (rs.next()) {

                lista.add(new ClienteDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getTimestamp(7))
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
    public boolean eliminar(long id) {
        try {
            String sql = "DELETE FROM CLIENTES WHERE ID=?";

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
