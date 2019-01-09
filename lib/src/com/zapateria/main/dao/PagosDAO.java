/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.PagoDTO;
import com.zapateria.main.dto.FacturaDTO;
import com.zapateria.main.interfaces.IFacturasDAO;
import com.zapateria.main.interfaces.IPagosDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego
 */
public class PagosDAO extends ConexionDAO implements com.zapateria.main.interfaces.IPagosDAO {

    private static final long serialVersionUID = -2689205908981839033L;

    private IFacturasDAO facturasDAO = new FacturasDAO();

    public PagosDAO() {
        super();

    }

    @Override
    public boolean registrar(double abono, long factura) {
        try {
            String sql = "INSERT INTO PAGOS(ABONO,FACTURAS_ID) VALUES(?,?)";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setDouble(1, abono);
            pst.setLong(2, factura);

            if (pst.executeUpdate() == 1) {
                return sumarPagoFactura(factura, abono);
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
        return false;
    }

    @Override
    public PagoDTO consultar(long id) {

        try {
            String sql = "SELECT P.ID,P.ABONO,P.FECHA,P.FACTURAS_ID,F.FORMA_DE_PAGO,"
                    + "F.ESTADO,F.FECHA_FACTURA,F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,"
                    + "F.FECHA_ENTREGA,F.CLIENTES_ID,"
                    + "F.USUARIOS_ID FROM PAGOS P,FACTURAS F WHERE P.FACTURAS_ID=F.ID AND P.ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {
                PagoDTO pago = new PagoDTO(
                        rs.getLong(1),
                        rs.getDouble(2),
                        rs.getTimestamp(3),
                        rs.getLong(4));

                pago.setFacturaDTO(new FacturaDTO(
                        pago.getFactura(),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getTimestamp(7),
                        rs.getDouble(8),
                        rs.getDouble(9),
                        rs.getDouble(10),
                        rs.getDate(11),
                        rs.getLong(12),
                        rs.getLong(13)
                ));

                return pago;
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

    private boolean actualizarPagoFactura(long factura, double abono) {
        try {
            String sql = "UPDATE FACTURAS SET TOTAL_PAGADO=?  WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setDouble(1, abono);
            pst.setLong(2, factura);

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

    private boolean sumarPagoFactura(long factura, double abono) {

        try {
            String sql = "UPDATE FACTURAS SET TOTAL_PAGADO=+? WHERE ID=?";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setDouble(1, abono);
            pst.setLong(2, factura);
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

    private boolean restarPagoFactura(long factura, double abono) {

        double totalPagado = facturasDAO.consultar(factura).getTotalPagado();

        totalPagado = totalPagado - abono;

        try {
            String sql = "UPDATE FACTURAS SET TOTAL_PAGADO=? WHERE ID=?";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setDouble(1, totalPagado);
            pst.setLong(2, factura);
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
    public ArrayList<PagoDTO> listar() {
        lista = new ArrayList();
        try {
            String sql = "SELECT P.ID,P.ABONO,P.FECHA,P.FACTURAS_ID,F.FORMA_DE_PAGO,"
                    + "F.ESTADO,F.FECHA_FACTURA,F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,"
                    + "F.FECHA_ENTREGA,F.CLIENTES_ID, F.USUARIOS_ID FROM PAGOS P,"
                    + "FACTURAS F WHERE P.FACTURAS_ID=F.ID";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                PagoDTO pago = new PagoDTO(
                        rs.getLong(1),
                        rs.getDouble(2),
                        rs.getTimestamp(3),
                        rs.getLong(4));

                pago.setFacturaDTO(new FacturaDTO(
                        pago.getFactura(),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getTimestamp(7),
                        rs.getDouble(8),
                        rs.getDouble(9),
                        rs.getDouble(10),
                        rs.getDate(11),
                        rs.getLong(12),
                        rs.getLong(13)
                ));

                lista.add(pago);
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
    public boolean editar(long id, double abono, long factura) {

        facturasDAO = new FacturasDAO();

        FacturaDTO facturaDTO = facturasDAO.consultar(factura);
        PagoDTO pagoDTO = consultar(id);

        if (facturaDTO == null || pagoDTO == null) {
            return false;
        }

        try {

            String sql = "UPDATE PAGOS SET ABONO=?,FACTURAS_ID=? WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setDouble(1, abono);
            pst.setLong(2, factura);
            pst.setLong(3, id);

            if (pst.executeUpdate() == 1) {

                double totalPagado = facturaDTO.getTotalPagado();
                double nuevoAbono = totalPagado + (pagoDTO.getAbono() - abono);

                try {
                    actualizarPagoFactura(factura, nuevoAbono);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error desconocido");
                    e.printStackTrace();
                }

                return true;
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
        return false;
    }

    @Override
    public boolean eliminar(long id) {

        PagoDTO pago = consultar(id);

        if (pago == null) {
            return false;
        }

        try {
            String sql = "DELETE FROM PAGOS WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            if (pst.executeUpdate() == 1) {

                return restarPagoFactura(pago.getFactura(), pago.getAbono());
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
        return false;
    }

    private void pruebas() {

        System.out.println(editar(12,26, 1));

    }

    public static void main(String[] args) {
        new PagosDAO().pruebas();
    }

}
