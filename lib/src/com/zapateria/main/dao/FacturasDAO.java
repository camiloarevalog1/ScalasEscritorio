/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.ClienteDTO;
import com.zapateria.main.dto.UsuarioDTO;
import com.zapateria.main.dto.FacturaDTO;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class FacturasDAO extends ConexionDAO implements com.zapateria.main.interfaces.IFacturasDAO {

    private static final long serialVersionUID = -4298378340877167853L;

    public FacturasDAO() {
        super();
    }

    @Override
    public boolean registrar(String formaDePago, String estado, double iva,
            double totalPagar, double totalPagado,
            Date fechaEntrega, long cliente, long usuario) {
        try {

            String sql = "INSERT INTO FACTURAS (FORMA_DE_PAGO,ESTADO,IVA,TOTAL_A_PAGAR,"
                    + "TOTAL_PAGADO,FECHA_ENTREGA,CLIENTES_ID,USUARIOS_ID) VALUES(?,?,?,?,?,?,?,?)";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, formaDePago);
            pst.setString(2, estado);
            pst.setDouble(3, iva);
            pst.setDouble(4, totalPagar);
            pst.setDouble(5, totalPagado);
            pst.setDate(6, fechaEntrega);
            pst.setLong(7, cliente);
            pst.setLong(8, usuario);

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
    public FacturaDTO consultar(long id) {
        try {
            String sql = "SELECT F.ID,F.FORMA_DE_PAGO,F.ESTADO,F.FECHA_FACTURA,"
                    + "F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,F.FECHA_ENTREGA,"
                    + "F.CLIENTES_ID,F.USUARIOS_ID,C.DOCUMENTO,C.NOMBRES,"
                    + "C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,"
                    + "U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID\n"
                    + "FROM FACTURAS F,CLIENTES C,USUARIOS U WHERE F.CLIENTES_ID=C.ID\n"
                    + "AND F.USUARIOS_ID=U.ID AND F.ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {

                FacturaDTO factura = new FacturaDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getTimestamp(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getDate(8),
                        rs.getLong(9),
                        rs.getLong(10)
                );

                factura.setClienteDTO(new ClienteDTO(
                        factura.getCliente(),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getTimestamp(16))
                );

                factura.setUsuarioDTO(new UsuarioDTO(
                        factura.getUsuario(),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getString(20),
                        rs.getLong(21))
                );

                return factura;
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
    public long consultarIncremento() {
        try {
            String sql = "SELECT ID FROM FACTURAS ORDER BY ID DESC LIMIT 1";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {

                return rs.getLong(1) + 1;
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
        return 1;
    }

    @Override
    public ArrayList<FacturaDTO> listar() {
        lista = new ArrayList();
        try {
            String sql = "SELECT F.ID,F.FORMA_DE_PAGO,F.ESTADO,F.FECHA_FACTURA,"
                    + "F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,F.FECHA_ENTREGA,"
                    + "F.CLIENTES_ID,F.USUARIOS_ID,C.DOCUMENTO,C.NOMBRES,"
                    + "C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,"
                    + "U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID\n"
                    + "FROM FACTURAS F,CLIENTES C,USUARIOS U WHERE F.CLIENTES_ID=C.ID\n"
                    + "AND F.USUARIOS_ID=U.ID";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                FacturaDTO factura = new FacturaDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getTimestamp(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getDate(8),
                        rs.getLong(9),
                        rs.getLong(10)
                );

                factura.setClienteDTO(new ClienteDTO(
                        factura.getCliente(),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getTimestamp(16))
                );

                factura.setUsuarioDTO(new UsuarioDTO(
                        factura.getUsuario(),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getString(20),
                        rs.getLong(21))
                );

                lista.add(factura);
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
    public ArrayList<FacturaDTO> buscar(String busqueda) {
        lista = new ArrayList();
        try {
            String sql = "SELECT F.ID,F.FORMA_DE_PAGO,F.ESTADO,F.FECHA_FACTURA,"
                    + "F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,F.FECHA_ENTREGA,"
                    + "F.CLIENTES_ID,F.USUARIOS_ID,C.DOCUMENTO,C.NOMBRES,"
                    + "C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,U.NOMBRES,"
                    + "U.APELLIDOS,U.DOCUMENTO,"
                    + "U.NOMBRE_USUARIO,U.ROLES_ID FROM FT_SEARCH_DATA(?,0,0) FT,"
                    + "FACTURAS F,CLIENTES C,USUARIOS U WHERE F.CLIENTES_ID=C.ID\n"
                    + "AND F.USUARIOS_ID=U.ID AND F.ID=FT.KEYS[0]";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, busqueda);
            rs = pst.executeQuery();

            while (rs.next()) {

                FacturaDTO factura = new FacturaDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getTimestamp(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getDate(8),
                        rs.getLong(9),
                        rs.getLong(10)
                );

                factura.setClienteDTO(new ClienteDTO(
                        factura.getCliente(),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getTimestamp(16))
                );

                factura.setUsuarioDTO(new UsuarioDTO(
                        factura.getUsuario(),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getString(20),
                        rs.getLong(21))
                );

                lista.add(factura);
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
    public boolean editar(long id, String formaDePago, String estado, double iva,
            double totalPagar, double totalPagado,
            Date fechaEntrega, long cliente, long usuario) {
        try {
            String sql = "UPDATE FACTURAS SET FORMA_DE_PAGO=?,ESTADO=?,IVA=?,"
                    + "TOTAL_A_PAGAR=?,TOTAL_PAGADO=?,FECHA_ENTREGA=?,"
                    + "CLIENTES_ID=?,USUARIOS_ID=? WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, formaDePago);
            pst.setString(2, estado);
            pst.setDouble(3, iva);
            pst.setDouble(4, totalPagar);
            pst.setDouble(5, totalPagado);
            pst.setDate(6, fechaEntrega);
            pst.setLong(7, cliente);
            pst.setLong(8, usuario);
            pst.setLong(9, id);

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
    public boolean cambiarEstado(long id, String estado) {
        try {
            String sql = "UPDATE FACTURAS SET ESTADO=? WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, estado);
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
    public boolean cambiarFormaDePago(long id, String formaDePago) {
        try {
            String sql = "UPDATE FACTURAS SET FORMA_DE_PAGO=? WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, formaDePago);
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
    public boolean editar(long id, String formaDePago, String estado) {
        return cambiarEstado(id, estado) && cambiarFormaDePago(id, formaDePago);
    }

    @Override
    public boolean eliminar(long id) {
        try {
            String sql = "DELETE FROM FACTURAS WHERE ID=?";

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
