/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.CompraDTO;
import com.zapateria.main.dto.FacturaDTO;
import com.zapateria.main.dto.PagoDTO;
import com.zapateria.main.dto.PagosProv;
import com.zapateria.main.interfaces.IComprasDAO;
import com.zapateria.main.interfaces.IFacturasDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class PagosProvDAO extends ConexionDAO implements com.zapateria.main.interfaces.IPagosProvDAO {

    private static final long serialVersionUID = -2689205908981839033L;

    private IFacturasDAO facturasDAO = new FacturasDAO();
    private IComprasDAO comprasDAO = new ComprasDAO();

    public PagosProvDAO() {
        super();

    }

    @Override
    public boolean registrar(double abono, long factura) {
        try {
            String sql = "INSERT INTO PAGOSPROV(ABONO,COMPRA_ID) VALUES(?,?)";
            System.out.println(abono + "" + factura);
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setDouble(1, abono);
            pst.setLong(2, factura);

            return pst.executeUpdate() == 1;
//            if (pst.executeUpdate() == 1) {
//                return sumarPagoFactura(factura, abono);
//            }

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
    public PagosProv consultar(long id) {

        try {
            String sql = "SELECT P.ID,P.ABONO,P.FECHA,P.COMPRA_ID,F.CANTIDAD,"
                    + "F.VALOR_UNITARIO,F.VALOR_TOTAL,F.FECHA,F.PROVEEDORES_ID,"
                    + "F.MATERIALES_ID,F.ABONADO FROM pagosprov P,compras F WHERE P.COMPRA_ID=F.ID AND P.ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {
                PagosProv pago = new PagosProv(
                        rs.getLong(1),
                        rs.getDouble(2),
                        rs.getTimestamp(3),
                        rs.getLong(4)
                );

                pago.setCompraDTO(new CompraDTO(
                        pago.getCompra(),
                        rs.getLong(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getTimestamp(8),
                        rs.getLong(9),
                        rs.getLong(10),
                        rs.getDouble(11)
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

    public boolean actualizarPago(long factura, double abono) {
        try {
            String sql = "UPDATE COMPRAS SET ABONADO=?  WHERE ID=?";

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

//    private boolean sumarPagoFactura(long factura, double abono) {
//
//        try {
//            String sql = "UPDATE FACTURAS SET TOTAL_PAGADO=+? WHERE ID=?";
//            con = conexion.getConnection();
//            pst = con.prepareStatement(sql);
//            pst.setDouble(1, abono);
//            pst.setLong(2, factura);
//            return pst.executeUpdate() == 1;
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        } finally {
//            try {
//                if (pst != null) {
//                    pst.close();
//                }
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return false;
//    }
//    private boolean restarPagoFactura(long factura, double abono) {
//
//        double totalPagado = facturasDAO.consultar(factura).getTotalPagado();
//
//        totalPagado = totalPagado - abono;
//
//        try {
//            String sql = "UPDATE FACTURAS SET TOTAL_PAGADO=? WHERE ID=?";
//            con = conexion.getConnection();
//            pst = con.prepareStatement(sql);
//            pst.setDouble(1, totalPagado);
//            pst.setLong(2, factura);
//            return pst.executeUpdate() == 1;
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        } finally {
//            try {
//                if (pst != null) {
//                    pst.close();
//                }
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return false;
//    }
    @Override
    public ArrayList<PagosProv> listar() {
        lista = new ArrayList();
        try {

            String sql = "SELECT P.ID,P.ABONO,P.FECHA,P.COMPRA_ID,F.CANTIDAD,"
                    + "F.VALOR_UNITARIO,F.VALOR_TOTAL,F.FECHA,F.PROVEEDORES_ID,"
                    + "F.MATERIALES_ID,F.ABONADO FROM pagosprov P,compras F WHERE P.COMPRA_ID=F.ID";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                PagosProv pago = new PagosProv(
                        rs.getLong(1),
                        rs.getDouble(2),
                        rs.getTimestamp(3),
                        rs.getLong(4)
                );

                pago.setCompraDTO(new CompraDTO(
                        pago.getCompra(),
                        rs.getLong(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getTimestamp(8),
                        rs.getLong(9),
                        rs.getLong(10),
                        rs.getDouble(11)
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

        //facturasDAO = new FacturasDAO();
        comprasDAO= new ComprasDAO();

        //FacturaDTO facturaDTO = facturasDAO.consultar(factura);
        CompraDTO compraDTO = comprasDAO.consultar(factura);
        PagosProv PagosProv = consultar(id);

        if (compraDTO == null || PagosProv == null) {
            return false;
        }

        try {

            String sql = "UPDATE PAGOSPROV SET ABONO=?,COMPRA_ID=? WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setDouble(1, abono);
            pst.setLong(2, factura);
            pst.setLong(3, id);

            if (pst.executeUpdate() == 1) {

//                double totalPagado = facturaDTO.getTotalPagado();
//                double nuevoAbono = totalPagado + (pagoDTO.getAbono() - abono);
//
//                try {
//                    actualizarPagoFactura(factura, nuevoAbono);
//                } catch (Exception e) {
//                    JOptionPane.showMessageDialog(null, "Error desconocido");
//                    e.printStackTrace();
//                }
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

        PagosProv PagosProv = consultar(id);

        if (PagosProv == null) {
            return false;
        }

        try {
            String sql = "DELETE FROM PAGOSPROV WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            return pst.executeUpdate() == 1;

            //  return restarPagoFactura(pago.getFactura(), pago.getAbono());
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

//    public long consultaTotalPagos(long id) {
//        long total = 0;
//        try {
//            String sql = "SELECT SUM (PAGO) FROM ZAPATERIA.PAGOS WHERE FACTURAS_ID=?";
//
//            con = conexion.getConnection();
//            pst = con.prepareStatement(sql);
//            pst.setLong(1, id);
//            rs = pst.executeQuery();
//
//            if (rs.next() && rs.absolute(1)) {
//
//                total = rs.getLong(1);
//            }
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (pst != null) {
//                    pst.close();
//                }
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return total;
//    }
    
    public ArrayList<PagosProv> listarProveedor(long id) {
        lista = new ArrayList();
        
        try {

            String sql = "SELECT P.ID,P.ABONO,P.FECHA,P.COMPRA_ID,F.CANTIDAD,"
                    + "F.VALOR_UNITARIO,F.VALOR_TOTAL,F.FECHA,F.PROVEEDORES_ID,"
                    + "F.MATERIALES_ID,F.ABONADO FROM pagosprov P,compras F WHERE P.COMPRA_ID=F.ID AND F.PROVEEDORES_ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                PagosProv pago = new PagosProv(
                        rs.getLong(1),
                        rs.getDouble(2),
                        rs.getTimestamp(3),
                        rs.getLong(4)
                );

                pago.setCompraDTO(new CompraDTO(
                        pago.getCompra(),
                        rs.getLong(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getTimestamp(8),
                        rs.getLong(9),
                        rs.getLong(10),
                        rs.getDouble(11)
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
    
    public double total(long id) {
        double totalAb=0;
        
        try {

            String sql = "SELECT SUM (P.ABONO) FROM PAGOSPROV P,COMPRAS F WHERE P.COMPRA_ID=F.ID AND F.ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {

                totalAb= rs.getInt(1) ;
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
        return totalAb;
    }
}
