/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.AbonoDTO;
import com.zapateria.main.dto.PagoDTO;
import com.zapateria.main.dto.FacturaDTO;
import com.zapateria.main.dto.PagoFacDTO;
import com.zapateria.main.dto.RegistroDTO;
import com.zapateria.main.dto.UsuarioDTO;
import com.zapateria.main.interfaces.IFacturasDAO;
import com.zapateria.main.interfaces.IPagosDAO;
import com.zapateria.main.util.Conexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        Connection link=null;
        try {
            String sql = "INSERT INTO pagos(ABONO,FACTURAS_ID) VALUES(?,?)";
            System.out.println(abono + "" + factura);
           link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setDouble(1, abono);
            str.setLong(2, factura);
            
            return str.executeUpdate() == 1;
//            if (pst.executeUpdate() == 1) {
//                return sumarPagoFactura(factura, abono);
//            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PagoDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }
    
    @Override
    public PagoDTO consultar(long id) {
        
        try {
            String sql = "SELECT P.ID,P.ABONO,P.FECHA,P.FACTURAS_ID FROM pagos P WHERE  P.ID=?";
            
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();
            
            if (rs.next() && rs.absolute(1)) {
                PagoDTO pago = new PagoDTO(
                        rs.getLong(1),
                        rs.getDouble(2),
                        rs.getTimestamp(3),
                        rs.getLong(4)
                );
                
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
            String sql = "UPDATE REGISTRO SET TOTAL_PAGADO=?  WHERE ID=?";
            
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
    
    public String convertirFechaString(Date date) {
        
        return new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(date);
        
    }
    
    public String convertirFechaStri(Date date) {
        
        return new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(date);
        
    }
    
    public ArrayList<PagoDTO> listarFechaAbo(Date fechaD, Date fechaH) {
        lista = new ArrayList();
        String fecha1 = "";
        String fecha2 = "";
        try {
            fecha1 = convertirFechaString(fechaD);
            fecha2 = convertirFechaStri(fechaH);
            //String f= fecha2+"00:00:00";
            System.out.println(fecha2);
            System.out.println(fecha1);
            String sql = "SELECT DISTINCT (ID),ABONO,FECHA,FACTURAS_ID \n "
                    + "FROM pagos   WHERE  FECHA>=? AND FECHA<=?  ";
            
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
//            pst.setString(0, 'CONTADO');
            pst.setString(1, fecha1);
            pst.setString(2, fecha2);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                
                PagoDTO abono = new PagoDTO(
                        rs.getLong(1),
                        rs.getDouble(2),
                        rs.getTimestamp(3),
                        rs.getLong(4)
                );
                
                lista.add(abono);
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
    
    public ArrayList<PagoDTO> listar2() {
        lista = new ArrayList();
        Connection link = null;
        try {
            String sql = "SELECT P.ID,P.ABONO,P.FECHA,P.FACTURAS_ID,"
                    + "F.FECHA_FACTURA,F.IVA,F.TOTAL_A_PAGAR,"
                    + "F.TOTAL_PAGADO,F.CLIENTES_ID, F.USUARIOS_ID,F.FACTURA,F.FORMA_DE_PAGO FROM pagos P,"
                    + "facturas3 F WHERE P.FACTURAS_ID=F.ID";
            
             link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);

            ResultSet rs = str.executeQuery();
            
            while (rs.next()) {
                PagoDTO pago = new PagoDTO(
                        rs.getLong(1),
                        rs.getDouble(2),
                        rs.getTimestamp(3),
                        rs.getLong(4)
                );
                
                pago.setFacturaDTO(new FacturaDTO(
                        pago.getFactura(),
                        rs.getTimestamp(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getLong(9),
                        rs.getLong(10),
                        rs.getString(11),
                        rs.getString(12)
                ));
                
                lista.add(pago);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
       if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PagosDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }
    
    //Listar pagos con nombre del cliente
 
     public ArrayList<PagoFacDTO> listar() {
        lista = new ArrayList();
        Connection link = null;
        try {
            String sql = "select p.ID,p.FECHA,p.FACTURAS_ID,p.ABONO,c.NOMBRES,c.APELLIDOS from pagos p,"
                    + " clientes c, facturas3 f where p.FACTURAS_ID=f.ID and f.CLIENTES_ID=c.ID";
            
             link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);

            ResultSet rs = str.executeQuery();
            
            while (rs.next()) {
                PagoFacDTO pago = new PagoFacDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getLong(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        rs.getString(6)
                     
                );
                
               
                
                lista.add(pago);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
       if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PagosDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }
    
    public ArrayList<PagoDTO> listarNombre(long id) {
        lista = new ArrayList();
        Connection link = null;
        try {
            String sql = "SELECT P.ID,P.ABONO,P.FECHA,P.FACTURAS_ID,"
                    + "F.FECHA_FACTURA,F.IVA,F.TOTAL_A_PAGAR,"
                    + "F.TOTAL_PAGADO,F.CLIENTES_ID, F.USUARIOS_ID,F.FACTURA,F.FORMA_DE_PAGO FROM pagos P,"
                    + "facturas3 F WHERE P.FACTURAS_ID=F.ID AND F.CLIENTES_ID=?";
            
       link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);
           ResultSet rs = str.executeQuery();
            
            while (rs.next()) {
                PagoDTO pago = new PagoDTO(
                        rs.getLong(1),
                        rs.getDouble(2),
                        rs.getTimestamp(3),
                        rs.getLong(4)
                );
                
                pago.setFacturaDTO(new FacturaDTO(
                        pago.getFactura(),
                        rs.getTimestamp(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getLong(9),
                        rs.getLong(10),
                        rs.getString(11),
                        rs.getString(12)
                ));
                
                lista.add(pago);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
       if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PagosDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }
    
    //LISTAR CON NUEVA CONEXION PAGOS
    public ArrayList<PagoFacDTO> listarNombrePagos(long id) {
        lista = new ArrayList();
        Connection link = null;
        try {
            String sql = "select p.ID,p.FECHA,p.FACTURAS_ID,p.ABONO,c.NOMBRES,c.APELLIDOS from pagos p,"
                    + " clientes c, facturas3 f where p.FACTURAS_ID=f.ID and f.CLIENTES_ID=c.ID and c.id=?";
            
       link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);
           ResultSet rs = str.executeQuery();
            
            while (rs.next()) {
               PagoFacDTO pago = new PagoFacDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getLong(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        rs.getString(6)
                     
                );
                
               
                
                lista.add(pago);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
       if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PagosDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }
    
    @Override
    public boolean editar(long id, double abono, long factura, long cuota) {

//        facturasDAO = new FacturasDAO();
//
//        FacturaDTO facturaDTO = facturasDAO.consultar(factura);
//        PagoDTO pagoDTO = consultar(id);
//
//        if (facturaDTO == null || pagoDTO == null) {
//            return false;
//        }
        try {
            
            String sql = "UPDATE pagos SET ABONO=?,FACTURAS_ID=?,PAGO=? WHERE ID=?";
            
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setDouble(1, abono);
            pst.setLong(2, factura);
            pst.setLong(3, cuota);
            pst.setLong(4, id);
            
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
        
        PagoDTO pago = consultar(id);
        
        if (pago == null) {
            return false;
        }
        
        try {
            String sql = "DELETE FROM pagos WHERE ID=?";
            
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
    
    public boolean eliminarPagos(long id) {
        
//        JOptionPane.showMessageDialog(null, id);
        Connection link=null;
        try {
            String sql = "DELETE FROM pagos WHERE FACTURAS_ID=?";
            
            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);
            return str.executeUpdate() == 1;

            //  return restarPagoFactura(pago.getFactura(), pago.getAbono());
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
           if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PagoDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }
    
    public long consultaTotalPagos(long id) {
        long total = 0;
        try {
            String sql = "SELECT SUM (PAGO) FROM pagos WHERE FACTURAS_ID=?";
            
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();
            
            if (rs.next() && rs.absolute(1)) {
                
                total = rs.getLong(1);
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
        return total;
    }

    public double listarPagosTotal(Date fecha) {
        lista = new ArrayList();
        Connection link = null;
        System.out.println("entro listar");
        String fecha1 = "";
        String fecha2 = "";
        double total = 0;
        try {
            fecha1 = convertirFechaString(fecha);
            fecha2 = convertirFechaStri(fecha);
            String sql = "SELECT SUM(ABONO) FROM pagos  WHERE FECHA>= ? AND FECHA <= ?";
            JOptionPane.showMessageDialog(null, "La caja se abrio el: " + fecha1 + "\n Cierre de día: " + fecha2);
            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, fecha1);
            str.setString(2, fecha2);
            ResultSet rs = str.executeQuery();
            
            if (rs.next() && rs.absolute(1)) {
                
                total = rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
           if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PagoDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return total;
    }
    
    private void pruebas() {

        //      System.out.println(editar(12, 26, 1));
    }
    
    public static void main(String[] args) {
        new PagosDAO().pruebas();
    }
    
}
