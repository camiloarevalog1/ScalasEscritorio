package com.zapateria.main.dao;

import com.zapateria.main.dto.FacturaDTO;
import com.zapateria.main.dto.ProductoDTO;
import com.zapateria.main.dto.VentaDTO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class VentasDAO extends ConexionDAO implements com.zapateria.main.interfaces.IVentasDAO {

    private static final long serialVersionUID = 3876231646055255539L;

    public VentasDAO() {
        super();
    }

    @Override
    public boolean registrar(long cantidad, String comentario, long factura, long producto) {
        try {
            String sql = "INSERT INTO VENTAS(CANTIDAD,COMENTARIO,FACTURAS_ID,PRODUCTOS_ID) VALUES(?,?,?,?)";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, cantidad);
            pst.setString(2, comentario);
            pst.setLong(3, factura);
            pst.setLong(4, producto);

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
    public VentaDTO consultar(long id) {

        try {
            String sql = "SELECT V.ID,V.CANTIDAD,V.COMENTARIO,V.FACTURAS_ID,V.PRODUCTOS_ID,"
                    + "F.FORMA_DE_PAGO,F.ESTADO,F.FECHA_FACTURA,F.IVA,F.TOTAL_A_PAGAR,"
                    + "F.TOTAL_PAGADO,F.FECHA_ENTREGA,F.CLIENTES_ID,F.USUARIOS_ID,P.NOMBRE,"
                    + "P.PRECIO_UNITARIO FROM VENTAS V,FACTURAS F,PRODUCTOS P\n"
                    + "WHERE V.FACTURAS_ID=F.ID AND V.PRODUCTOS_ID=P.ID AND ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {

                VentaDTO venta = new VentaDTO(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getString(3),
                        rs.getLong(4),
                        rs.getLong(5)
                );

                venta.setFacturaDTO(new FacturaDTO(
                        venta.getFactura(),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getTimestamp(8),
                        rs.getDouble(9),
                        rs.getDouble(10),
                        rs.getDouble(11),
                        rs.getDate(12),
                        rs.getLong(13),
                        rs.getLong(14)
                ));

                venta.setProductoDTO(new ProductoDTO(
                        venta.getProducto(),
                        rs.getString(15),
                        rs.getDouble(16)
                ));

                return venta;
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
    public ArrayList<VentaDTO> listar() {
        lista = new ArrayList();
        try {
            String sql = "SELECT V.ID,V.CANTIDAD,V.COMENTARIO,V.FACTURAS_ID,V.PRODUCTOS_ID,"
                    + "F.FORMA_DE_PAGO,F.ESTADO,F.FECHA_FACTURA,F.IVA,F.TOTAL_A_PAGAR,"
                    + "F.TOTAL_PAGADO,F.FECHA_ENTREGA,F.CLIENTES_ID,F.USUARIOS_ID,P.NOMBRE,"
                    + "P.PRECIO_UNITARIO FROM VENTAS V,FACTURAS F,PRODUCTOS P\n"
                    + "WHERE V.FACTURAS_ID=F.ID AND V.PRODUCTOS_ID=P.ID";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                VentaDTO venta = new VentaDTO(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getString(3),
                        rs.getLong(4),
                        rs.getLong(5)
                );

                venta.setFacturaDTO(new FacturaDTO(
                        venta.getFactura(),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getTimestamp(8),
                        rs.getDouble(9),
                        rs.getDouble(10),
                        rs.getDouble(11),
                        rs.getDate(12),
                        rs.getLong(13),
                        rs.getLong(14)
                ));

                venta.setProductoDTO(new ProductoDTO(
                        venta.getProducto(),
                        rs.getString(15),
                        rs.getDouble(16)
                ));

                lista.add(venta);
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
    public ArrayList<VentaDTO> listar(long factura) {
        lista = new ArrayList();
        try {
            String sql = "SELECT V.ID,V.CANTIDAD,V.COMENTARIO,V.FACTURAS_ID,V.PRODUCTOS_ID,"
                    + "F.FORMA_DE_PAGO,F.ESTADO,F.FECHA_FACTURA,F.IVA,F.TOTAL_A_PAGAR,"
                    + "F.TOTAL_PAGADO,F.FECHA_ENTREGA,F.CLIENTES_ID,F.USUARIOS_ID,P.NOMBRE,"
                    + "P.PRECIO_UNITARIO FROM VENTAS V,FACTURAS F,PRODUCTOS P\n"
                    + "WHERE V.FACTURAS_ID=F.ID AND V.PRODUCTOS_ID=P.ID AND V.FACTURAS_ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, factura);
            rs = pst.executeQuery();

            while (rs.next()) {

                VentaDTO venta = new VentaDTO(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getString(3),
                        rs.getLong(4),
                        rs.getLong(5)
                );

                venta.setFacturaDTO(new FacturaDTO(
                        venta.getFactura(),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getTimestamp(8),
                        rs.getDouble(9),
                        rs.getDouble(10),
                        rs.getDouble(11),
                        rs.getDate(12),
                        rs.getLong(13),
                        rs.getLong(14)
                ));

                venta.setProductoDTO(new ProductoDTO(
                        venta.getProducto(),
                        rs.getString(15),
                        rs.getDouble(16)
                ));

                lista.add(venta);
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
    public boolean editar(long id, long cantidad, String comentario, long factura, long producto) {
        try {
            String sql = "UPDATE VENTAS SET CANTIDAD=?,COMENTARIO=?,FACTURAS_ID=?,PRODUCTOS_ID=? WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, cantidad);
            pst.setString(2, comentario);
            pst.setLong(3, factura);
            pst.setLong(4, producto);
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
    public boolean editar(long id, long cantidad) {
        try {
            String sql = "UPDATE VENTAS SET CANTIDAD=? WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, cantidad);
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
    public boolean eliminar(long id) {
        try {
            String sql = "DELETE FROM VENTAS WHERE ID=?";

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
