/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.ClienteDTO;
import com.zapateria.main.dto.UsuarioDTO;
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
public class ClientesDAO extends ConexionDAO implements com.zapateria.main.interfaces.IClientesDAO {

//    private static final long serialVersionUID = 3117878119281705244L;
    public ClientesDAO() {
        super();
    }

    @Override
    public long registrarCliente(String documento, String nombres, String apellidos,
            String direccion, String telefono) {
        long x = 0;
        ResultSet rs = null;
        PreparedStatement pst = null;
        Connection link =null;
        try {
            String sql = "INSERT INTO clientes (DOCUMENTO,NOMBRES,APELLIDOS,DIRECCION,TELEFONO)\n"
                    + "VALUES(?,?,?,?,?)";

            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, documento);
            str.setString(2, nombres);
            str.setString(3, apellidos);
            str.setString(4, direccion);
            str.setString(5, telefono);
            str.executeUpdate();

            String sq = "SELECT MAX(ID) FROM clientes ";

            pst = con.prepareStatement(sq);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {
                return rs.getLong(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return x;
    }

    @Override
    public boolean validarCedula(String cedula) {
        try {
            String sql = "SELECT DOCUMENTO FROM clientes WHERE DOCUMENTO=?";

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
            String sql = "INSERT INTO clientes (DOCUMENTO,NOMBRES,APELLIDOS,DIRECCION,TELEFONO)\n"
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
            String sql = "INSERT INTO clientes (DOCUMENTO,NOMBRES,APELLIDOS)\n"
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
        Connection link = null;
        try {
            String sql = "SELECT ID,DOCUMENTO,NOMBRES,APELLIDOS,DIRECCION,"
                    + "TELEFONO,FECHA_REGISTRO FROM clientes WHERE ID=?";

            link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);

            str.setLong(1, id);
            ResultSet rs = str.executeQuery();

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
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public ClienteDTO consultarCliendeDeuda(long id) {
        Connection link = null;
        try {
            String sql = "SELECT c.ID,c.DOCUMENTO,c.NOMBRES,c.APELLIDOS,c.TELEFONO,c.DIRECCION,"
                    + "(SUM(f.TOTAL_A_PAGAR)-SUM(f.TOTAL_PAGADO) ) as debe FROM facturas3 f ,"
                    + " clientes c WHERE f.CLIENTES_ID=? and f.CLIENTES_ID=c.ID ";

            link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);

            str.setLong(1, id);
            ResultSet rs = str.executeQuery();

            if (rs.next() && rs.absolute(1)) {

                return new ClienteDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),                        
                        rs.getDouble(7));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public boolean editar(long id, String documento, String nombres, String apellidos,
            String direccion, String telefono) {
         Connection link = null;
        try {
            
            String sql = "UPDATE clientes SET DOCUMENTO=?,NOMBRES=?,APELLIDOS=?,"
                    + "DIRECCION=?,TELEFONO=? WHERE ID=?";

            link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, documento);
            str.setString(2, nombres);
            str.setString(3, apellidos);
            str.setString(4, direccion);
            str.setString(5, telefono);
            str.setLong(6, id);
            return str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    @Override
    public boolean editar(long id, String documento, String nombres, String apellidos) {
        Connection link = null;
        try {
            String sql = "UPDATE clientes SET DOCUMENTO=?,NOMBRES=?,APELLIDOS=? WHERE ID=?";

           link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, documento);
            str.setString(2, nombres);
            str.setString(3, apellidos);
            str.setLong(4, id);
            return str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClienteDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    @Override
    public ArrayList<ClienteDTO> listar() {
        Connection link = null;
        lista = new ArrayList();

        try {
            String sql = "SELECT ID,DOCUMENTO,NOMBRES,APELLIDOS,DIRECCION,"
                    + "TELEFONO,FECHA_REGISTRO FROM clientes ORDER BY NOMBRES ASC";

            link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);

            ResultSet rs = str.executeQuery();

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
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }

    public ClienteDTO buscarClienteCedula(String cedula) {
        Connection link =null;
        try {
            String sql = "SELECT C.ID,C.DOCUMENTO,C.NOMBRES,C.APELLIDOS,C.DIRECCION,"
                    + "C.TELEFONO,C.FECHA_REGISTRO FROM clientes C WHERE C.DOCUMENTO=? ";
            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, cedula);
             ResultSet rs = str.executeQuery();
            if (rs.next()) {
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
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClienteDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public ArrayList<ClienteDTO> buscar(String busqueda) {
Connection link =null;
        lista = new ArrayList();

        try {
            String sql = "SELECT C.ID,C.DOCUMENTO,C.NOMBRES,C.APELLIDOS,C.DIRECCION,"
                    + "C.TELEFONO,C.FECHA_REGISTRO FROM FT_SEARCH_DATA(?, 0, 0) FT,"
                    + "clientes C WHERE FT.TABLE='clientes' AND C.ID=FT.KEYS[0];";

          link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, busqueda);

             ResultSet rs = str.executeQuery();

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
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClienteDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }

    @Override
    public boolean eliminar(long id) {
        Connection link =null;
        try {
            String sql = "DELETE FROM clientes WHERE ID=?";
 link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);
            return str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
           if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClienteDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

}
