/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.ClienteDTO;
import com.zapateria.main.dto.UsuarioDTO;
import com.zapateria.main.dto.FacturaDTO;
import com.zapateria.main.dto.MaterialDTO;
import com.zapateria.main.dto.NominaDTO;
import com.zapateria.main.dto.PagoDTO;
import com.zapateria.main.dto.RegistroDTO;
import com.zapateria.main.dto.VentaDTO;
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
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

/**
 *
 * @author Diego
 */
public class FacturasDAO extends ConexionDAO implements com.zapateria.main.interfaces.IFacturasDAO {

    private static final long serialVersionUID = -4298378340877167853L;
    Servicio servicio;

    public FacturasDAO() {
        super();
    }

    @Override
    public boolean registrar(String formaDePago, double iva,
            double totalPagar, double totalPagado,
            Date fechaEntrega, long cliente, long usuario, long cuota) {
        try {

            String sql = "INSERT INTO FACTURAS (FORMA_DE_PAGO,IVA,TOTAL_A_PAGAR,"
                    + "TOTAL_PAGADO,CLIENTES_ID,USUARIOS_ID) VALUES(?,?,?,?,?,?,?,?)";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, formaDePago);
            pst.setDouble(2, iva);
            pst.setDouble(3, totalPagar);
            pst.setDouble(4, totalPagado);
            pst.setDate(5, fechaEntrega);
            pst.setLong(6, cliente);
            pst.setLong(7, usuario);
            pst.setLong(8, cuota);

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

    public boolean registrarRemisionIva(double iva, double totalPagado, double totalPagar, long cliente, long usuario, String forma, String factura) {
        Connection link =null;
        try {

            String sql = "INSERT INTO facturas3 (IVA,TOTAL_A_PAGAR,"
                    + "TOTAL_PAGADO,CLIENTES_ID,USUARIOS_ID,FORMA_DE_PAGO,FACTURA) VALUES(?,?,?,?,?,?,?)";

           link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);

            str.setDouble(1, iva);

            str.setDouble(2, totalPagar);
            str.setDouble(3, totalPagado);
            str.setLong(4, cliente);
            str.setLong(5, usuario);
            str.setString(6, forma);
            str.setString(7, factura);

            return str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FacturaDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;

    }

    public boolean registrarRemision(double iva, double totalPagado, double totalPagar, long cliente, long usuario, String forma) {
         Connection link = null;
        try {
            

            String sql = "INSERT INTO facturas3 (IVA,TOTAL_A_PAGAR,"
                    + "TOTAL_PAGADO,CLIENTES_ID,USUARIOS_ID,FORMA_DE_PAGO) VALUES(?,?,?,?,?,?)";

             link = Conexion.getInstance().dataSource.getConnection();
             PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setDouble(1, iva);

            str.setDouble(2, totalPagar);
            str.setDouble(3, totalPagado);
            str.setLong(4, cliente);
            str.setLong(5, usuario);
            str.setString(6, forma);

            return str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FacturaDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;

    }

//    public boolean registrar2(double iva,
//            double totalPagar, double totalPagado,
//            long cliente, long usuario) {
//        try {
//
//            String sql = "INSERT INTO FACTURAS (IVA,TOTAL_A_PAGAR,"
//                    + "TOTAL_PAGADO,CLIENTES_ID,USUARIOS_ID) VALUES(?,?,?,?,?)";
//
//            con = conexion.getConnection();
//            pst = con.prepareStatement(sql);
//
//            pst.setDouble(1, iva);
//            pst.setDouble(2, totalPagar);
//            pst.setDouble(3, totalPagado);
//
//            pst.setLong(4, cliente);
//            pst.setLong(5, usuario);
//
//            return pst.executeUpdate() == 1;
//
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

    public boolean registrarFa(String formaDePago, double iva,
            double totalPagar, double totalPagado,
            Date fechaEntrega, long cliente, long usuario, long cuota) {
        try {

            String sql = "INSERT INTO FACTURAS2 (FORMA_DE_PAGO,IVA,TOTAL_A_PAGAR,"
                    + "TOTAL_PAGADO,FECHA_ENTREGA,CLIENTES_ID,USUARIOS_ID,NUMERO_CUOTAS) VALUES(?,?,?,?,?,?,?,?)";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, formaDePago);
            pst.setDouble(2, iva);
            pst.setDouble(3, totalPagar);
            pst.setDouble(4, totalPagado);
            pst.setDate(5, fechaEntrega);
            pst.setLong(6, cliente);
            pst.setLong(7, usuario);
            pst.setLong(8, cuota);

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

    public boolean registrarCuotasPagadas(long id, long cuota) {
        try {

            String sql = "UPDATE FACTURAS SET CUOTAS_PAGADAS=? WHERE ID=? ";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, cuota);
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
    public FacturaDTO consultar(long id) {
        try {
            String sql = "SELECT F.ID,F.FORMA_DE_PAGO,F.FECHA_FACTURA,"
                    + "F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,F.FECHA_ENTREGA,"
                    + "F.CLIENTES_ID,F.USUARIOS_ID,F.NUMERO_CUOTAS,F.CUOTAS_PAGADAS,C.DOCUMENTO,C.NOMBRES,"
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
                        rs.getTimestamp(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDate(7),
                        rs.getLong(8),
                        rs.getLong(9),
                        rs.getLong(10),
                        rs.getLong(11)
                );

                factura.setClienteDTO(new ClienteDTO(
                        factura.getCliente(),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getTimestamp(17))
                );

                factura.setUsuarioDTO(new UsuarioDTO(
                        factura.getUsuario(),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getString(20),
                        rs.getString(21),
                        rs.getLong(22))
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

    //listar ingresos 
    public ArrayList<FacturaDTO> listarFechaNomi(Date fechaD, Date fechaH) {
        lista = new ArrayList();
        String fecha1 = "";
        String fecha2 = "";
        try {
            fecha1 = convertirFechaString(fechaD);
            fecha2 = convertirFechaStri(fechaH);
            //String f= fecha2+"00:00:00";
            System.out.println(fecha2);
            System.out.println(fecha1);
            String sql = "SELECT F.ID,F.FORMA_DE_PAGO,F.FECHA_FACTURA,"
                    + "F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,F.FECHA_ENTREGA,"
                    + "F.CLIENTES_ID,F.USUARIOS_ID,F.NUMERO_CUOTAS,F.CUOTAS_PAGADAS\n "
                    + "FROM FACTURAS F WHERE  F.FECHA_FACTURA>=? AND F.FECHA_FACTURA<=? AND TOTAL_PAGADO>0 AND FORMA_DE_PAGO='CONTADO'";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
//            pst.setString(0, 'CONTADO');
            pst.setString(1, fecha1);
            pst.setString(2, fecha2);
            rs = pst.executeQuery();

            while (rs.next()) {

                FacturaDTO factura = new FacturaDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getTimestamp(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDate(7),
                        rs.getLong(8),
                        rs.getLong(9),
                        rs.getLong(10),
                        rs.getLong(11)
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

    public ArrayList<FacturaDTO> filtroMateriales(long id) {
        lista = new ArrayList();
        try {
            String sql = "SELECT DISTINCT ( F.ID),F.FORMA_DE_PAGO,"
                    + "F.FECHA_FACTURA,F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,F.FECHA_ENTREGA, "
                    + " F.CLIENTES_ID,F.USUARIOS_ID,F.NUMERO_CUOTAS,F.CUOTAS_PAGADAS   "
                    + " FROM FACTURAS F,CLIENTES C,USUARIOS U,VENTAS V, MATERIALES P WHERE "
                    + " V.FACTURAS_ID=F.ID AND V.MATERIAL_ID=P.ID AND P.ID =? ";

//            String sql= "SELECT V.ID,V.CANTIDAD,V.COMENTARIO,V.FACTURAS_ID,V.MATERIAL_ID,V.PRECIO,"
//                    + "F.FORMA_DE_PAGO,F.ESTADO,F.FECHA_FACTURA,F.IVA,F.TOTAL_A_PAGAR,"
//                    + "F.TOTAL_PAGADO,F.FECHA_ENTREGA,F.CLIENTES_ID,F.USUARIOS_ID,F.NUMERO_CUOTAS"
//                    + ",F.CUOTAS_PAGADAS,P.NOMBRE,"
//                    + "P.PATRON FROM VENTAS V INNER JOIN FACTURAS F ON V.FACTURAS_ID=F.ID INNER JOIN"
//                    + "MATERIALES P ON V.MATERIAL_ID=P.ID WHERE P.ID=? "
//                    + " ,MATERIALES P\n"
//                    + "WHERE   AND P.ID=? ";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {

                FacturaDTO factura = new FacturaDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getTimestamp(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDate(7),
                        rs.getLong(8),
                        rs.getLong(9),
                        rs.getLong(10),
                        rs.getLong(11)
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

//    @Override
//    public long consultarIncremento() {
//        try {
//            String sql = "SELECT ID FROM FACTURAS ORDER BY ID DESC LIMIT 1";
//
//            con = conexion.getConnection();
//            pst = con.prepareStatement(sql);
//            rs = pst.executeQuery();
//
//            if (rs.next() && rs.absolute(1)) {
//
//                return rs.getLong(1) + 1;
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
//        return 1;
//    }

    public long consultarIncrementoRemision() {
        try {
            String sql = "SELECT ID FROM facturas3 ORDER BY ID DESC LIMIT 1";

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

    public long consultarUltimaFactura() {
        Connection link =null;
        try {
            String sql = "SELECT MAX(ID) FROM facturas3";

           link = Conexion.getInstance().dataSource.getConnection();
             PreparedStatement str;
            str = link.prepareStatement(sql);
             ResultSet rs = str.executeQuery();

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
                    Logger.getLogger(FacturaDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return 1;
    }

    @Override
    public ArrayList<FacturaDTO> listarFactura(long id) {
        lista = new ArrayList();
        Connection link = null;

        try {

            String sql = "SELECT F.ID,F.FECHA_FACTURA,"
                    + "F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,"
                    + "F.CLIENTES_ID,F.USUARIOS_ID,F.FACTURA,F.FORMA_DE_PAGO,C.DOCUMENTO,C.NOMBRES,"
                    + "C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,"
                    + "U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID\n"
                    + "FROM facturas3 F,clientes C,usuarios U WHERE F.CLIENTES_ID=C.ID\n"
                    + "AND F.USUARIOS_ID=U.ID AND F.ID=? ";

         link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);

            
            str.setLong(1, id);

           ResultSet rs = str.executeQuery();

            while (rs.next()) {

                FacturaDTO factura = new FacturaDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getString(8),
                        rs.getString(9)
                );

                factura.setClienteDTO(new ClienteDTO(
                        factura.getCliente(),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getTimestamp(15))
                );

                factura.setUsuarioDTO(new UsuarioDTO(
                        factura.getUsuario(),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getLong(20))
                );

                lista.add(factura);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
        if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FacturasDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }

    public ArrayList<FacturaDTO> listarEstadosFactura(String estado) {
        lista = new ArrayList();
        Connection link=null;

        try {

            String sql = "SELECT F.ID,F.FECHA_FACTURA,"
                    + "F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,"
                    + "F.CLIENTES_ID,F.USUARIOS_ID,F.FACTURA,F.FORMA_DE_PAGO,C.DOCUMENTO,C.NOMBRES,"
                    + "C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,"
                    + "U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID\n"
                    + "FROM facturas3 F,clientes C,usuarios U WHERE F.CLIENTES_ID=C.ID\n"
                    + "AND F.USUARIOS_ID=U.ID AND F.FORMA_DE_PAGO=? ";

            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, estado);

             ResultSet rs = str.executeQuery();

            while (rs.next()) {

                FacturaDTO factura = new FacturaDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getString(8),
                        rs.getString(9)
                );

                factura.setClienteDTO(new ClienteDTO(
                        factura.getCliente(),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getTimestamp(15))
                );

                factura.setUsuarioDTO(new UsuarioDTO(
                        factura.getUsuario(),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getLong(20))
                );

                lista.add(factura);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FacturaDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }
    

    @Override
    public ArrayList<FacturaDTO> listarFechasFac(Date fechaD, Date fechaH) {
        lista = new ArrayList();
        Connection link=null;
        String fecha1 = "";
        String fecha2 = "";
        try {
            fecha1 = convertirFechaString(fechaD);
            fecha2 = convertirFechaStri(fechaH);
            //String f= fecha2+"00:00:00";
            System.out.println(fecha2);
            System.out.println(fecha1);
            String sql = "SELECT F.ID,F.FECHA_FACTURA,"
                    + "F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,"
                    + "F.CLIENTES_ID,F.USUARIOS_ID,F.FACTURA,F.FORMA_DE_PAGO,C.DOCUMENTO,C.NOMBRES,"
                    + "C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,"
                    + "U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID\n"
                    + "FROM facturas3 F,clientes C,usuarios U WHERE F.CLIENTES_ID=C.ID\n"
                    + "AND F.USUARIOS_ID=U.ID AND F.FECHA_FACTURA >= ?   AND  F.FECHA_FACTURA <= ?  ";

           link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, fecha1);
            str.setString(2, fecha2);
            ResultSet rs = str.executeQuery();

            while (rs.next()) {

                FacturaDTO factura = new FacturaDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getString(8),
                        rs.getString(9)
                );

                factura.setClienteDTO(new ClienteDTO(
                        factura.getCliente(),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getTimestamp(15))
                );

                factura.setUsuarioDTO(new UsuarioDTO(
                        factura.getUsuario(),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getLong(20))
                );

                lista.add(factura);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FacturaDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }

    public String convertirFechaString(Date date) {

        return new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(date);

    }

    public String convertirFechaStri(Date date) {

        return new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(date);

    }

    //listar facturas por una fecha
    @Override
    public ArrayList<FacturaDTO> listarFechasFact(Date fecha) {
        lista = new ArrayList();
        try {
            String sql = "SELECT F.ID,F.FORMA_DE_PAGO,F.ESTADO,F.FECHA_FACTURA,"
                    + "F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,F.FECHA_ENTREGA,"
                    + "F.CLIENTES_ID,F.USUARIOS_ID,F.NUMERO_CUOTAS,F.CUOTAS_PAGADAS,C.DOCUMENTO,C.NOMBRES,"
                    + "C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,"
                    + "U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID\n"
                    + "FROM FACTURAS F,CLIENTES C,USUARIOS U WHERE F.CLIENTES_ID=C.ID\n"
                    + "AND F.USUARIOS_ID=U.ID AND F.FECHA_FACTURA = ?  ";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setDate(1, fecha);
            rs = pst.executeQuery();

            while (rs.next()) {

                FacturaDTO factura = new FacturaDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getTimestamp(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDate(7),
                        rs.getLong(8),
                        rs.getLong(9),
                        rs.getLong(10),
                        rs.getLong(11)
                );

                factura.setClienteDTO(new ClienteDTO(
                        factura.getCliente(),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getTimestamp(17))
                );

                factura.setUsuarioDTO(new UsuarioDTO(
                        factura.getUsuario(),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getString(20),
                        rs.getString(21),
                        rs.getLong(22))
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
    public ArrayList<FacturaDTO> listarEstadoFactura(String estado) {
        lista = new ArrayList();
        try {
            String sql = "SELECT DISTINCT F.ID,F.FORMA_DE_PAGO,F.FECHA_FACTURA,"
                    + "F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,F.FECHA_ENTREGA,"
                    + "F.CLIENTES_ID,F.USUARIOS_ID,F.NUMERO_CUOTAS,F.CUOTAS_PAGADAS,C.DOCUMENTO,C.NOMBRES,"
                    + "C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,"
                    + "U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID\n"
                    + "FROM FACTURAS F,CLIENTES C,USUARIOS U,VENTAS V WHERE F.CLIENTES_ID=C.ID\n"
                    + "AND F.USUARIOS_ID=U.ID AND V.FACTURAS_ID = F.ID AND V.ESTADO=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, estado);
            rs = pst.executeQuery();

            while (rs.next()) {

                FacturaDTO factura = new FacturaDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getTimestamp(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDate(7),
                        rs.getLong(8),
                        rs.getLong(9),
                        rs.getLong(10),
                        rs.getLong(11)
                );

                factura.setClienteDTO(new ClienteDTO(
                        factura.getCliente(),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getTimestamp(17))
                );

                factura.setUsuarioDTO(new UsuarioDTO(
                        factura.getUsuario(),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getString(20),
                        rs.getString(21),
                        rs.getLong(22))
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

    public ArrayList<FacturaDTO> listarPagoFactura(String estado) {
        lista = new ArrayList();
        try {
            String sql = "SELECT F.ID,F.FORMA_DE_PAGO,F.FECHA_FACTURA,"
                    + "F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,F.FECHA_ENTREGA,"
                    + "F.CLIENTES_ID,F.USUARIOS_ID,F.NUMERO_CUOTAS,F.CUOTAS_PAGADAS,C.DOCUMENTO,C.NOMBRES,"
                    + "C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,"
                    + "U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID\n"
                    + "FROM FACTURAS F,CLIENTES C,USUARIOS U WHERE F.CLIENTES_ID=C.ID\n"
                    + "AND F.USUARIOS_ID=U.ID AND F.FORMA_DE_PAGO = ?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, estado);
            rs = pst.executeQuery();

            while (rs.next()) {

                FacturaDTO factura = new FacturaDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getTimestamp(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDate(7),
                        rs.getLong(8),
                        rs.getLong(9),
                        rs.getLong(10),
                        rs.getLong(11)
                );

                factura.setClienteDTO(new ClienteDTO(
                        factura.getCliente(),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getTimestamp(17))
                );

                factura.setUsuarioDTO(new UsuarioDTO(
                        factura.getUsuario(),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getString(20),
                        rs.getString(21),
                        rs.getLong(22))
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
    public ArrayList<FacturaDTO> listarUsuarioFactura(long cedula) {
        Connection link =null;
        lista = new ArrayList();
        try {
            String sql = "SELECT F.ID,F.FECHA_FACTURA,"
                    + "F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,"
                    + "F.CLIENTES_ID,F.USUARIOS_ID,F.FACTURA,F.FORMA_DE_PAGO,C.DOCUMENTO,C.NOMBRES,"
                    + "C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,"
                    + "U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID\n"
                    + "FROM facturas3 F,clientes C,usuarios U WHERE F.CLIENTES_ID=C.ID\n"
                    + "AND F.USUARIOS_ID=U.ID AND C.ID = ?";

            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, cedula);
            ResultSet rs = str.executeQuery();

            while (rs.next()) {

                FacturaDTO factura = new FacturaDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getString(8),
                        rs.getString(9)
                );

                factura.setClienteDTO(new ClienteDTO(
                        factura.getCliente(),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getTimestamp(15))
                );

                factura.setUsuarioDTO(new UsuarioDTO(
                        factura.getUsuario(),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getLong(20))
                );

                lista.add(factura);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FacturaDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }

//    @Override
//    public ArrayList<FacturaDTO> listar() {
//        lista = new ArrayList();
//        try {
//            String sql = "SELECT F.ID,F.FORMA_DE_PAGO,F.FECHA_FACTURA,"
//                    + "F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,F.FECHA_ENTREGA,"
//                    + "F.CLIENTES_ID,F.USUARIOS_ID,F.NUMERO_CUOTAS,F.CUOTAS_PAGADAS,C.DOCUMENTO,C.NOMBRES,"
//                    + "C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,"
//                    + "U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID\n"
//                    + "FROM FACTURAS F,CLIENTES C,USUARIOS U WHERE F.CLIENTES_ID=C.ID\n"
//                    + "AND F.USUARIOS_ID=U.ID";
//
//            con = conexion.getConnection();
//            pst = con.prepareStatement(sql);
//
//            rs = pst.executeQuery();
//
//            while (rs.next()) {
//
//                FacturaDTO factura = new FacturaDTO(
//                        rs.getLong(1),
//                        rs.getString(2),
//                        rs.getTimestamp(3),
//                        rs.getDouble(4),
//                        rs.getDouble(5),
//                        rs.getDouble(6),
//                        rs.getDate(7),
//                        rs.getLong(8),
//                        rs.getLong(9),
//                        rs.getLong(10),
//                        rs.getLong(11)
//                );
//
//                factura.setClienteDTO(new ClienteDTO(
//                        factura.getCliente(),
//                        rs.getString(12),
//                        rs.getString(13),
//                        rs.getString(14),
//                        rs.getString(15),
//                        rs.getString(16),
//                        rs.getTimestamp(17))
//                );
//
//                factura.setUsuarioDTO(new UsuarioDTO(
//                        factura.getUsuario(),
//                        rs.getString(18),
//                        rs.getString(19),
//                        rs.getString(20),
//                        rs.getString(21),
//                        rs.getLong(22))
//                );
//
//                lista.add(factura);
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
//        return lista;
//    }

    @Override
    public ArrayList<FacturaDTO> buscar(String busqueda) {
        lista = new ArrayList();
        try {
            String sql = "SELECT F.ID,F.FORMA_DE_PAGO,F.FECHA_FACTURA,"
                    + "F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,F.FECHA_ENTREGA,"
                    + "F.CLIENTES_ID,F.USUARIOS_ID,F.NUMERO_CUOTAS,F.CUOTAS_PAGADAS,C.DOCUMENTO,C.NOMBRES,"
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
                        rs.getTimestamp(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDate(7),
                        rs.getLong(8),
                        rs.getLong(9),
                        rs.getLong(10),
                        rs.getLong(11)
                );

                factura.setClienteDTO(new ClienteDTO(
                        factura.getCliente(),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getTimestamp(17))
                );

                factura.setUsuarioDTO(new UsuarioDTO(
                        factura.getUsuario(),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getString(20),
                        rs.getString(21),
                        rs.getLong(22))
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

    public boolean editarFechaFactura(long id, Date fecha) {
        boolean exito = false;

        try {
            String sql = "UPDATE FACTURAS SET FECHA_ENTREGA=? WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setDate(1, fecha);
            pst.setLong(2, id);

            exito = pst.executeUpdate() == 1;

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

        return exito;
    }
    
    public boolean editarRemision(long id, double total, double iva) {
        boolean exito = false;
      Connection link = null;

        try {
            String sql = "UPDATE facturas3 SET TOTAL_A_PAGAR=? , IVA=? WHERE ID=?";

            link = Conexion.getInstance().dataSource.getConnection();
             PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setDouble(1, total);
            str.setDouble(2, iva);
            str.setLong(3, id);

            exito = str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FacturaDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return exito;
    }

    @Override
    public boolean editar(long id, String formaDePago, double iva,
            double totalPagar, double totalPagado,
            Date fechaEntrega, long cliente, long usuario) {
        try {
            String sql = "UPDATE FACTURAS SET FORMA_DE_PAGO=?,IVA=?,"
                    + "TOTAL_A_PAGAR=?,TOTAL_PAGADO=?,FECHA_ENTREGA=?,"
                    + "CLIENTES_ID=?,USUARIOS_ID=? WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, formaDePago);

            pst.setDouble(2, iva);
            pst.setDouble(3, totalPagar);
            pst.setDouble(4, totalPagado);
            pst.setDate(5, fechaEntrega);
            pst.setLong(6, cliente);
            pst.setLong(7, usuario);
            pst.setLong(8, id);

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
            String sql = "UPDATE FACTURAS SET FORMA_DE_PAGO=? WHERE ID=?";

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
    
    public boolean ActualizarPago(long id, double pago) {
        Connection link=null;
        try {
            String sql = "UPDATE facturas3 SET TOTAL_PAGADO=? WHERE ID=?";

           link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setDouble(1, pago);
            str.setLong(2, id);

            return str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FacturaDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
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
        return cambiarFormaDePago(id, formaDePago);
    }

    @Override
    public boolean eliminar(long id) {
        Connection link=null;
        try {
            String sql = "DELETE FROM facturas3 WHERE ID=?";

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
                    Logger.getLogger(FacturaDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    public ArrayList<FacturaDTO> listarMaterialFactura(long id) {
        lista = new ArrayList();
        Connection link =null;

        try {

            String sql = "SELECT DISTINCT F.ID,F.FECHA_FACTURA,\n"
                    + "                    F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,\n"
                    + "                    F.CLIENTES_ID,F.USUARIOS_ID,F.FACTURA"
                    + ",F.FORMA_DE_PAGO,C.DOCUMENTO,C.NOMBRES,\n"
                    + "                    C.APELLIDOS,C.DIRECCION,C.TELEFONO,"
                    + "C.FECHA_REGISTRO FROM facturas3 F INNER JOIN clientes C "
                    + "ON C.ID =F.CLIENTES_ID,usuarios U,materiales M,"
                    + "registro R WHERE \n"
                    + "                      R.FACTURAS_ID=F.ID AND R.MATERIAL_ID=? ";

            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);

            ResultSet rs = str.executeQuery();

            while (rs.next()) {
                
                FacturaDTO factura = new FacturaDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getString(8),
                        rs.getString(9)
                );

                factura.setClienteDTO(new ClienteDTO(
                        factura.getCliente(),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getTimestamp(15))
                );

             

                lista.add(factura);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
           if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FacturaDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }
    public double consultaTotalPagarPagado(long id) {
        double total = 0;
        try {
            String sql = "SELECT (SUM(TOTAL_A_PAGAR)-SUM(TOTAL_PAGADO) ) FROM facturas3 WHERE CLIENTES_ID=?";

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
    
    public long consultaTotalPagado(long id) {
        long total = 0;
        try {
            String sql = "SELECT SUM (PAGO) FROM ZAPATERIA.PAGOS WHERE FACTURAS_ID=?";

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
    public double  listarFacturaTotal(Date fecha) {
        lista = new ArrayList();
        Connection link = null;
        System.out.println("entro listar");
        String fecha1 = "";
        String fecha2 = "";
        double total=0;
        try {
            fecha1 = convertirFechaString(fecha);
            fecha2 = convertirFechaStri(fecha);
            String sql = "SELECT SUM(TOTAL_PAGADO) FROM facturas3  "
                    + "WHERE FECHA_FACTURA>= ? AND FECHA_FACTURA <= ? AND FORMA_DE_PAGO='CONTADO'";
            
             link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, fecha1);
            str.setString(2, fecha2);
          ResultSet rs = str.executeQuery();

            if (rs.next() && rs.absolute(1)) {

                total= rs.getInt(1) ;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FacturaDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return total;
    }
    
    public ArrayList<FacturaDTO> listarIngresos(Date FechaD, Date FechaH) {
        lista = new ArrayList();
        Connection link = null;
        String fecha1 = "";
        String fecha2 = "";
        
        try {
            fecha1 = convertirFechaString(FechaD);
            fecha2 = convertirFechaStri(FechaH);
            String sql = "SELECT F.ID,F.FECHA_FACTURA,"
                    + "F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,"
                    + "F.CLIENTES_ID,F.USUARIOS_ID,F.FACTURA,F.FORMA_DE_PAGO,C.DOCUMENTO,C.NOMBRES,"
                    + "C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,"
                    + "U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID\n"
                    + "FROM facturas3 F,clientes C,usuarios U WHERE F.CLIENTES_ID=C.ID\n"
                    + "AND F.USUARIOS_ID=U.ID AND F.FECHA_FACTURA>=? AND F.FECHA_FACTURA<=? AND F.FORMA_DE_PAGO='CONTADO' ORDER BY F.ID ASC";

             link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, fecha1);
            str.setString(2, fecha2);
             ResultSet rs = str.executeQuery();

            while (rs.next()) {

                FacturaDTO factura = new FacturaDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getString(8),
                        rs.getString(9)
                );

                factura.setClienteDTO(new ClienteDTO(
                        factura.getCliente(),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getTimestamp(15))
                );

                factura.setUsuarioDTO(new UsuarioDTO(
                        factura.getUsuario(),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getLong(20))
                );
               

                lista.add(factura);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FacturaDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;

    }
    public long consultaTotalProductos(long id) {
        long total = 0;
        try {
            String sql = "SELECT COUNT(R.ID) FROM facturas3 F, REGISTRO R WHERE R.FACTURAS_ID=F.ID AND F.ID=?";

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
    public boolean editarIvaFactura(long id, String idF) {
        boolean exito = false;
Connection link=null;
        try {
            String sql = "UPDATE facturas3 SET FACTURA=? WHERE ID=?";

            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, idF);
            str.setLong(2, id);

            exito = str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FacturaDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return exito;
    }
}
