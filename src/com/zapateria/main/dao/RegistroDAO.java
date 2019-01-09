/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.ClienteDTO;
import com.zapateria.main.dto.FacturaDTO;
import com.zapateria.main.dto.MaterialDTO;
import com.zapateria.main.dto.PagoDTO;
import com.zapateria.main.dto.RegistroDTO;
import com.zapateria.main.dto.UsuarioDTO;
import com.zapateria.main.dto.VentaDTO;
import com.zapateria.main.util.Conexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class RegistroDAO extends ConexionDAO implements com.zapateria.main.interfaces.IRegistroDAO {

    private static final long serialVersionUID = -4298378340877167853L;
    Servicio servicio;

    public RegistroDAO() {
        super();
    }

    @Override
    public boolean registrar(long id_usuario, long id_cliente, long id_material, double precio, long cantidad, String comentario,
            String estado, Date fecha_entrega, double iva, double total_pagar) {
        try {

            String sql = "INSERT INTO registro (CLIENTES_ID,USUARIOS_ID,MATERIAL_ID,PRECIO,CANTIDAD,COMENTARIO,ESTADO,"
                    + "FECHA_ENTREGA,TOTAL_A_PAGAR,IVA) VALUES(?,?,?,?,?,?,?,?,?,?)";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

            pst.setLong(1, id_cliente);
            pst.setLong(2, id_usuario);
            pst.setLong(3, id_material);
            pst.setDouble(4, precio);
            pst.setLong(5, cantidad);
            pst.setString(6, comentario);

            pst.setString(7, estado);

            pst.setDate(8, fecha_entrega);
            pst.setDouble(9, total_pagar);
            pst.setDouble(10, iva);

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

    public boolean registrar2(long id_usuario, long id_cliente, long id_material, double precio, long cantidad, String comentario,
            String estado, long numero_cuotas, long cuotas_pagadas, Date fecha_entrega, double iva, double total_pagar) {
        try {

            String sql = "INSERT INTO registro (CLIENTES_ID,USUARIOS_ID,MATERIAL_ID,PRECIO,CANTIDAD,COMENTARIO,ESTADO,"
                    + "FECHA_ENTREGA,TOTAL_A_PAGAR,IVA,FACTURAS_ID,IMPRESO) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

            pst.setLong(1, id_cliente);
            pst.setLong(2, id_usuario);
            pst.setLong(3, id_material);
            pst.setDouble(4, precio);
            pst.setLong(5, cantidad);
            pst.setString(6, comentario);

            pst.setString(7, estado);

            pst.setLong(9, numero_cuotas);
            pst.setLong(10, cuotas_pagadas);
            pst.setDate(11, fecha_entrega);
            pst.setDouble(12, total_pagar);
            pst.setDouble(13, iva);

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

    public boolean registrarRegistroRemision(long id_usuario, long id_cliente, long id_material, double precio, long cantidad, String comentario,
            String estado, String forma_pago, long numero_cuotas, long cuotas_pagadas, Date fecha_entrega, double iva, double total_pagar, long id_factura, double TotalPagado, long id_re) {
        try {

            String sql = "INSERT INTO ventas2 (CLIENTES_ID,USUARIOS_ID,MATERIAL_ID,PRECIO,CANTIDAD,COMENTARIO,ESTADO,"
                    + "FORMA_DE_PAGO,NUMERO_CUOTAS,CUOTAS_PAGADAS,FECHA_ENTREGA,TOTAL_A_PAGAR,IVA,FACTURAS_ID,TOTAL_PAGADO,REGISTRO_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

            pst.setLong(1, id_cliente);
            pst.setLong(2, id_usuario);
            pst.setLong(3, id_material);
            pst.setDouble(4, precio);
            pst.setLong(5, cantidad);
            pst.setString(6, comentario);

            pst.setString(7, estado);
            pst.setString(8, forma_pago);
            pst.setLong(9, numero_cuotas);
            pst.setLong(10, cuotas_pagadas);
            pst.setDate(11, fecha_entrega);
            pst.setDouble(12, total_pagar);
            pst.setDouble(13, iva);
            pst.setLong(14, id_factura);
            pst.setDouble(15, TotalPagado);
            pst.setLong(16, id_re);

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

    public boolean registrarContado(long id_usuario, long id_cliente, long id_material, double precio, long cantidad, String comentario,
            String estado, String forma_pago, long numero_cuotas, long cuotas_pagadas, Date fecha_entrega, double iva, double total_pagar, double total_pagado) {
        try {

            String sql = "INSERT INTO registro2 (CLIENTES_ID,USUARIOS_ID,MATERIAL_ID,PRECIO,CANTIDAD,COMENTARIO,ESTADO,"
                    + "FORMA_DE_PAGO,NUMERO_CUOTAS,CUOTAS_PAGADAS,FECHA_ENTREGA,TOTAL_A_PAGAR,IVA,TOTAL_PAGADO) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

            pst.setLong(1, id_cliente);
            pst.setLong(2, id_usuario);
            pst.setLong(3, id_material);
            pst.setDouble(4, precio);
            pst.setLong(5, cantidad);
            pst.setString(6, comentario);

            pst.setString(7, estado);
            pst.setString(8, forma_pago);
            pst.setLong(9, numero_cuotas);
            pst.setLong(10, cuotas_pagadas);
            pst.setDate(11, fecha_entrega);
            pst.setDouble(12, total_pagar);
            pst.setDouble(13, iva);
            pst.setDouble(14, total_pagado);

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

    public ArrayList<RegistroDTO> listar() {
        lista = new ArrayList();
        Connection link = null;
        try {
            String sql = "SELECT F.ID,F.FECHA_REGISTRO,F.ESTADO,"
                    + "F.CLIENTES_ID,F.USUARIOS_ID,F.CANTIDAD,"
                    + "F.MATERIAL_ID,F.COMENTARIO,F.PRECIO,F.FECHA_ENTREGA,F.IVA,F.TOTAL_A_PAGAR,F.FACTURAS_ID"
                    + ",C.DOCUMENTO,C.NOMBRES,"
                    + "C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,"
                    + "U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID,M.NOMBRE,M.PATRON\n"
                    + "FROM registro F,clientes C,usuarios U,materiales M WHERE F.CLIENTES_ID=C.ID\n"
                    + "AND F.USUARIOS_ID=U.ID AND F.MATERIAL_ID=M.ID ORDER BY F.ID DESC ";

            link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);
            ResultSet rs = str.executeQuery();

            while (rs.next()) {

                RegistroDTO factura = new RegistroDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getString(3),
                        rs.getLong(4),
                        rs.getLong(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getDate(10),
                        rs.getDouble(11),
                        rs.getDouble(12),
                        rs.getLong(13)
                );

                factura.setCliente(new ClienteDTO(
                        factura.getId_cliente(),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getTimestamp(19))
                );

                factura.setUsuario(new UsuarioDTO(
                        factura.getId_usuario(),
                        rs.getString(20),
                        rs.getString(21),
                        rs.getString(22),
                        rs.getString(23),
                        rs.getLong(24))
                );
                factura.setMaterial(new MaterialDTO(
                        factura.getId_material(),
                        rs.getString(25),
                        rs.getString(26))
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
                    Logger.getLogger(RegistroDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }
//Hola
    public ArrayList<RegistroDTO> listarRegistroFecha(Date fechaD, Date fechaH) {
        lista = new ArrayList();
        Connection link = null;
        String fecha1 = "";
        String fecha2 = "";
        try {
            fecha1 = convertirFechaString(fechaD);
            fecha2 = convertirFechaStri(fechaH);
            String sql = "SELECT F.ID,F.FECHA_REGISTRO,F.ESTADO,"
                    + "F.CLIENTES_ID,F.USUARIOS_ID,F.CANTIDAD,"
                    + "F.MATERIAL_ID,F.COMENTARIO,F.PRECIO,F.FECHA_ENTREGA,F.IVA,F.TOTAL_A_PAGAR,F.FACTURAS_ID"
                    + ",C.DOCUMENTO,C.NOMBRES,"
                    + "C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,"
                    + "U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID,M.NOMBRE,M.PATRON\n"
                    + "FROM registro F,clientes C,usuarios U,materiales M WHERE F.CLIENTES_ID=C.ID\n"
                    + "AND F.USUARIOS_ID=U.ID AND F.MATERIAL_ID=M.ID AND F.FECHA_REGISTRO >= ?   AND  F.FECHA_REGISTRO <= ? ORDER BY F.ID DESC ";

           
            link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);
            
            str.setString(1, fecha1);
            str.setString(2, fecha2);
            ResultSet rs = str.executeQuery();

            while (rs.next()) {

                RegistroDTO factura = new RegistroDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getString(3),
                        rs.getLong(4),
                        rs.getLong(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getDate(10),
                        rs.getDouble(11),
                        rs.getDouble(12),
                        rs.getLong(13)
                );

                factura.setCliente(new ClienteDTO(
                        factura.getId_cliente(),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getTimestamp(19))
                );

                factura.setUsuario(new UsuarioDTO(
                        factura.getId_usuario(),
                        rs.getString(20),
                        rs.getString(21),
                        rs.getString(22),
                        rs.getString(23),
                        rs.getLong(24))
                );
                factura.setMaterial(new MaterialDTO(
                        factura.getId_material(),
                        rs.getString(25),
                        rs.getString(26))
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
                    Logger.getLogger(RegistroDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }

    //LISTA REGISTROS DE UNA FACTURA
    public ArrayList<RegistroDTO> listar(long id) {
        Connection link =null;
        lista = new ArrayList();
        try {
            String sql = "SELECT DISTINCT F.ID,F.FECHA_REGISTRO,F.ESTADO,"
                    + "F.CLIENTES_ID,F.USUARIOS_ID,F.CANTIDAD,"
                    + "F.MATERIAL_ID,F.COMENTARIO,F.PRECIO,F.FECHA_ENTREGA,F.IVA,F.TOTAL_A_PAGAR,F.FACTURAS_ID\n"
                    + "                    ,C.DOCUMENTO,C.NOMBRES,\n"
                    + "                    C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,\n"
                    + "                    U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID,M.NOMBRE,M.PATRON\n"
                    + "                    FROM registro F,clientes C,usuarios U,materiales M ,facturas3 A WHERE F.CLIENTES_ID=C.ID\n"
                    + "                    AND F.USUARIOS_ID=U.ID AND F.MATERIAL_ID=M.ID AND  F.FACTURAS_ID=? ";

           
          link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);
           ResultSet rs = str.executeQuery();

            while (rs.next()) {

                RegistroDTO factura = new RegistroDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getString(3),
                        rs.getLong(4),
                        rs.getLong(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getDate(10),
                        rs.getDouble(11),
                        rs.getDouble(12),
                        rs.getLong(13)
                );

                factura.setCliente(new ClienteDTO(
                        factura.getId_cliente(),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getTimestamp(19))
                );

                factura.setUsuario(new UsuarioDTO(
                        factura.getId_usuario(),
                        rs.getString(20),
                        rs.getString(21),
                        rs.getString(22),
                        rs.getString(23),
                        rs.getLong(24))
                );
                factura.setMaterial(new MaterialDTO(
                        factura.getId_material(),
                        rs.getString(25),
                        rs.getString(26))
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
                    Logger.getLogger(RegistroDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }

    //total items remision
    public double itemTotalFacturas(long id) {
        lista = new ArrayList();
        double total = 0;
        try {
            String sql = "SELECT SUM(F.TOTAL_A_PAGAR)  FROM registro F WHERE  F.FACTURAS_ID=? ";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {

                total = rs.getInt(1);
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

    //iva total items remisiones
    public double IvaTotalFacturas(long id) {
        lista = new ArrayList();
        double total = 0;
        try {
            String sql = "SELECT SUM(F.IVA)  FROM registro F WHERE  F.FACTURAS_ID=? ";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {

                total = rs.getInt(1);
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

    public ArrayList<RegistroDTO> listarVentasCliente(long id) {
        lista = new ArrayList();
        Connection link = null;
        try {
            String sql = "SELECT DISTINCT (F.ID) ,F.FECHA_REGISTRO,F.ESTADO,\n"
                    + "                    F.CLIENTES_ID,F.USUARIOS_ID,F.CANTIDAD,\n"
                    + "                    F.MATERIAL_ID,F.COMENTARIO,F.PRECIO,F.FECHA_ENTREGA,F.IVA,F.TOTAL_A_PAGAR,F.IMPRESO,F.FACTURAS_ID\n"
                    + "                    ,C.DOCUMENTO,C.NOMBRES,\n"
                    + "                    C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,\n"
                    + "           U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID,M.NOMBRE,M.PATRON\n"
                    + "                    FROM registro F INNER JOIN clientes C ON C.ID =F.CLIENTES_ID,usuarios U,materiales M WHERE F.CLIENTES_ID=?\n"
                    + "                    AND F.USUARIOS_ID=U.ID AND F.MATERIAL_ID=M.ID ORDER BY F.FECHA_REGISTRO DESC";

             link = Conexion.getInstance().dataSource.getConnection();
             PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);
           ResultSet rs = str.executeQuery();

            while (rs.next()) {

                RegistroDTO factura = new RegistroDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getString(3),
                        rs.getLong(4),
                        rs.getLong(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getDate(10),
                        rs.getDouble(11),
                        rs.getDouble(12),
                        rs.getLong(13),
                        rs.getLong(14)
                );
                factura.setCliente(new ClienteDTO(
                        factura.getId_cliente(),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getTimestamp(20))
                );

                factura.setUsuario(new UsuarioDTO(
                        factura.getId_usuario(),
                        rs.getString(21),
                        rs.getString(22),
                        rs.getString(23),
                        rs.getString(24),
                        rs.getLong(25))
                );
                factura.setMaterial(new MaterialDTO(
                        factura.getId_material(),
                        rs.getString(26),
                        rs.getString(27))
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
                    Logger.getLogger(RegistroDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;

    }

    public ArrayList<RegistroDTO> listarVentasClienteRemision(long id) {
        lista = new ArrayList();
        try {
            String sql = "SELECT DISTINCT (F.ID) ,F.FECHA_REGISTRO,F.ESTADO,\n"
                    + "                    F.CLIENTES_ID,F.USUARIOS_ID,F.CANTIDAD,\n"
                    + "                    F.MATERIAL_ID,F.COMENTARIO,F.PRECIO,F.FECHA_ENTREGA,F.IVA,"
                    + "F.TOTAL_A_PAGAR,F.IMPRESO,F.FACTURAS_ID\n"
                    + "                    ,C.DOCUMENTO,C.NOMBRES,\n"
                    + "                    C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,\n"
                    + "           U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID,M.NOMBRE,M.PATRON\n"
                    + "                    FROM registro F INNER JOIN clientes C ON C.ID =F.CLIENTES_ID,usuarios U,"
                    + "materiales M,facturas3 FA WHERE F.CLIENTES_ID=?\n"
                    + "                    AND F.USUARIOS_ID=U.ID AND F.MATERIAL_ID=M.ID AND FA.ID=F.FACTURAS_ID AND FA.TOTAL_PAGADO<FA.TOTAL_A_PAGAR ORDER BY F.FECHA_REGISTRO DESC";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {

                RegistroDTO factura = new RegistroDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getString(3),
                        rs.getLong(4),
                        rs.getLong(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getDate(10),
                        rs.getDouble(11),
                        rs.getDouble(12),
                        rs.getLong(13),
                        rs.getLong(14)
                );
                factura.setCliente(new ClienteDTO(
                        factura.getId_cliente(),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getTimestamp(20))
                );

                factura.setUsuario(new UsuarioDTO(
                        factura.getId_usuario(),
                        rs.getString(21),
                        rs.getString(22),
                        rs.getString(23),
                        rs.getString(24),
                        rs.getLong(25))
                );
                factura.setMaterial(new MaterialDTO(
                        factura.getId_material(),
                        rs.getString(26),
                        rs.getString(27))
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

    public boolean editarFechaRegistro(long id, Date fecha) {
        boolean exito = false;
        Connection link = null;

        try {
            String sql = "UPDATE registro SET FECHA_ENTREGA=? WHERE ID=?";

           
               link = Conexion.getInstance().dataSource.getConnection();
             PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setDate(1, fecha);
            str.setLong(2, id);

            exito = str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return exito;
    }

    public boolean editarFechaRegistrosFactura(long id, String fa) {
        boolean exito = false;

        try {
            String sql = "UPDATE registro SET FECHA_ENTREGA=? WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, fa);
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

    public ArrayList<RegistroDTO> listarRegistro(long id) {
        lista = new ArrayList();
        Connection link = null;
        try {
            String sql = "SELECT F.ID ,F.FECHA_REGISTRO,F.ESTADO,\n"
                    + "     F.CLIENTES_ID,F.USUARIOS_ID,F.CANTIDAD,\n"
                    + "          F.MATERIAL_ID,F.COMENTARIO,F.PRECIO,F.FECHA_ENTREGA,F.IVA,F.TOTAL_A_PAGAR,F.FACTURAS_ID\n"
                    + "          ,C.DOCUMENTO,C.NOMBRES,\n"
                    + "       C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,\n"
                    + "           U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID,M.NOMBRE,M.PATRON\n"
                    + "              FROM registro F INNER JOIN clientes C ON C.ID =F.CLIENTES_ID,usuarios U,materiales M WHERE F.CLIENTES_ID=C.ID\n"
                    + "         AND F.USUARIOS_ID=U.ID AND F.MATERIAL_ID=M.ID AND F.ID=?";

               link = Conexion.getInstance().dataSource.getConnection();
             PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);
               ResultSet rs = str.executeQuery();

            while (rs.next()) {

                RegistroDTO factura = new RegistroDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getString(3),
                        rs.getLong(4),
                        rs.getLong(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getDate(10),
                        rs.getDouble(11),
                        rs.getDouble(12),
                        rs.getLong(13)
                );

                factura.setCliente(new ClienteDTO(
                        factura.getId_cliente(),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getTimestamp(19))
                );

                factura.setUsuario(new UsuarioDTO(
                        factura.getId_usuario(),
                        rs.getString(20),
                        rs.getString(21),
                        rs.getString(22),
                        rs.getString(23),
                        rs.getLong(24))
                );
                factura.setMaterial(new MaterialDTO(
                        factura.getId_material(),
                        rs.getString(25),
                        rs.getString(26))
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
                    Logger.getLogger(RegistroDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;

    }

    public boolean cambiarFormaDePago(long id, String formaDePago) {
        try {
            String sql = "UPDATE registro SET FORMA_DE_PAGO=? WHERE ID=?";

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

    public boolean cambiarIdFactura(long id, long factura) {
        System.out.println(id + factura);
        Connection link =null;
        try {
            String sql = "UPDATE registro SET FACTURAS_ID=?  WHERE ID=?  ";

            link = Conexion.getInstance().dataSource.getConnection();
             PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, factura);
            str.setLong(2, id);

            return str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RegistroDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    public boolean cambiarIdFacturasCero(long id, long factura) {
        System.out.println(id + factura);
        Connection link=null;
        try {
            String sql = "UPDATE registro SET FACTURAS_ID=?,IMPRESO=?  WHERE FACTURAS_ID=?  ";

            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, 0);
            str.setLong(2, 0);
            str.setLong(3, factura);

            return str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
           if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RegistroDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    @Override
    public boolean cambiarEstado(long id, String estado) {
        System.out.println(id + estado);
         Connection link = null;
        try {
            String sql = "UPDATE registro SET ESTADO=?  WHERE ID=?  ";
            
            link = Conexion.getInstance().dataSource.getConnection();

         PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, estado);
            str.setLong(2, id);

            return str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RegistroDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    public boolean cambiarImpresion(long id, long estado) {
        System.out.println(id + estado);
        Connection link = null;
        try {
            String sql = "UPDATE registro SET IMPRESO=?  WHERE ID=?  ";

             link = Conexion.getInstance().dataSource.getConnection();
             PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, estado);
            str.setLong(2, id);

            return str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RegistroDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    @Override
    public ArrayList<RegistroDTO> listarRegistrosEstado(String estado) {
        lista = new ArrayList();
        Connection link = null;
        try {
            String sql = "SELECT DISTINCT (F.ID) ,F.FECHA_REGISTRO,F.ESTADO,\n"
                    + "                    F.CLIENTES_ID,F.USUARIOS_ID,F.CANTIDAD,\n"
                    + "                    F.MATERIAL_ID,F.COMENTARIO,F.PRECIO,F.FECHA_ENTREGA,F.IVA,F.TOTAL_A_PAGAR,F.FACTURAS_ID\n"
                    + "                    ,C.DOCUMENTO,C.NOMBRES,\n"
                    + "                    C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,\n"
                    + "           U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID,M.NOMBRE,M.PATRON\n"
                    + "                    FROM registro F INNER JOIN clientes C ON C.ID =F.CLIENTES_ID,usuarios U,materiales M WHERE "
                    + "                        F.USUARIOS_ID=U.ID AND F.MATERIAL_ID=M.ID AND F.ESTADO=? ORDER BY F.FECHA_REGISTRO ASC";

            link = Conexion.getInstance().dataSource.getConnection();
            PreparedStatement str;
            str = link.prepareStatement(sql);

            str.setString(1, estado);
            ResultSet rs = str.executeQuery();

            while (rs.next()) {

                RegistroDTO factura = new RegistroDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getString(3),
                        rs.getLong(4),
                        rs.getLong(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getDate(10),
                        rs.getDouble(11),
                        rs.getDouble(12),
                        rs.getLong(13)
                );

                factura.setCliente(new ClienteDTO(
                        factura.getId_cliente(),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getTimestamp(19))
                );

                factura.setUsuario(new UsuarioDTO(
                        factura.getId_usuario(),
                        rs.getString(20),
                        rs.getString(21),
                        rs.getString(22),
                        rs.getString(23),
                        rs.getLong(24))
                );
                factura.setMaterial(new MaterialDTO(
                        factura.getId_material(),
                        rs.getString(25),
                        rs.getString(26))
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
                    Logger.getLogger(RegistroDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;

    }

    public ArrayList<RegistroDTO> listarRegistroMaterial(long id) {
        lista = new ArrayList();
        Connection link =null;
        try {
            String sql = "SELECT DISTINCT (F.ID) ,F.FECHA_REGISTRO,F.ESTADO,\n"
                    + "                    F.CLIENTES_ID,F.USUARIOS_ID,F.CANTIDAD,\n"
                    + "                    F.MATERIAL_ID,F.COMENTARIO,F.PRECIO,F.FECHA_ENTREGA,F.IVA,F.TOTAL_A_PAGAR,F.FACTURAS_ID\n"
                    + "                    ,C.DOCUMENTO,C.NOMBRES,\n"
                    + "                    C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,\n"
                    + "           U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID,M.NOMBRE,M.PATRON\n"
                    + "                    FROM registro F INNER JOIN clientes C ON C.ID =F.CLIENTES_ID,usuarios U,materiales M WHERE "
                    + "                        F.USUARIOS_ID=U.ID AND F.MATERIAL_ID=M.ID AND F.MATERIAL_ID=? ORDER BY F.FECHA_REGISTRO ASC";

           link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);
            ResultSet rs = str.executeQuery();

            while (rs.next()) {

                RegistroDTO factura = new RegistroDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getString(3),
                        rs.getLong(4),
                        rs.getLong(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getDate(10),
                        rs.getDouble(11),
                        rs.getDouble(12),
                        rs.getLong(13)
                );

                factura.setCliente(new ClienteDTO(
                        factura.getId_cliente(),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getTimestamp(19))
                );

                factura.setUsuario(new UsuarioDTO(
                        factura.getId_usuario(),
                        rs.getString(20),
                        rs.getString(21),
                        rs.getString(22),
                        rs.getString(23),
                        rs.getLong(24))
                );
                factura.setMaterial(new MaterialDTO(
                        factura.getId_material(),
                        rs.getString(25),
                        rs.getString(26))
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
                    Logger.getLogger(RegistroDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;

    }

    public ArrayList<RegistroDTO> listarRegistroMaterialFecha(long id, Date fecha, Date hasta) {
    Connection link=null;    
    lista = new ArrayList();
        String fecha1 = "";
        String fecha2 = "";
        try {

            fecha1 = convertirFechaString(fecha);
            fecha2 = convertirFechaStri(hasta);
            //String f= fecha2+"00:00:00";
            System.out.println(fecha2);
            System.out.println(fecha1);
            String sql = "SELECT DISTINCT (F.ID) ,F.FECHA_REGISTRO,F.ESTADO,\n"
                    + "                    F.CLIENTES_ID,F.USUARIOS_ID,F.CANTIDAD,\n"
                    + "                    F.MATERIAL_ID,F.COMENTARIO,F.PRECIO,F.FECHA_ENTREGA,F.IVA,F.TOTAL_A_PAGAR,F.FACTURAS_ID\n"
                    + "                    ,C.DOCUMENTO,C.NOMBRES,\n"
                    + "                    C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,\n"
                    + "           U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID,M.NOMBRE,M.PATRON\n"
                    + "                    FROM registro F INNER JOIN clientes C ON C.ID =F.CLIENTES_ID,USUARIOS U,MATERIALES M WHERE "
                    + "                        F.USUARIOS_ID=U.ID AND F.MATERIAL_ID=M.ID AND F.MATERIAL_ID=? AND "
                    + "F.FECHA_REGISTRO >= ?   AND  F.FECHA_REGISTRO <= ? ORDER BY F.FECHA_REGISTRO ASC";

            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);
            str.setString(2, fecha1);
            str.setString(3, fecha2);
             ResultSet rs = str.executeQuery();

            while (rs.next()) {

                RegistroDTO factura = new RegistroDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getString(3),
                        rs.getLong(4),
                        rs.getLong(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getDate(10),
                        rs.getDouble(11),
                        rs.getDouble(12),
                        rs.getLong(13)
                );

                factura.setCliente(new ClienteDTO(
                        factura.getId_cliente(),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getTimestamp(19))
                );

                factura.setUsuario(new UsuarioDTO(
                        factura.getId_usuario(),
                        rs.getString(20),
                        rs.getString(21),
                        rs.getString(22),
                        rs.getString(23),
                        rs.getLong(24))
                );
                factura.setMaterial(new MaterialDTO(
                        factura.getId_material(),
                        rs.getString(25),
                        rs.getString(26))
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
                    Logger.getLogger(RegistroDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;

    }

//    public ArrayList<RegistroDTO> listarRegistrosPago(String estado) {
//        lista = new ArrayList();
//        try {
//            String sql = "SELECT DISTINCT (F.ID) ,F.FECHA_REGISTRO,F.ESTADO,\n"
//                    + "                    F.CLIENTES_ID,F.USUARIOS_ID,F.CANTIDAD,\n"
//                    + "                    F.MATERIAL_ID,F.COMENTARIO,F.PRECIO,F.FECHA_ENTREGA,F.IVA,F.TOTAL_A_PAGAR\n"
//                    + "                    ,C.DOCUMENTO,C.NOMBRES,\n"
//                    + "                    C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,\n"
//                    + "           U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID,M.NOMBRE,M.PATRON\n"
//                    + "                    FROM REGISTRO F INNER JOIN CLIENTES C ON C.ID =F.CLIENTES_ID,USUARIOS U,MATERIALES M WHERE "
//                    + "                        F.USUARIOS_ID=U.ID AND F.MATERIAL_ID=M.ID AND F.FORMA_DE_PAGO=? ORDER BY F.ID ASC";
//
//            con = conexion.getConnection();
//            pst = con.prepareStatement(sql);
//            pst.setString(1, estado);
//            rs = pst.executeQuery();
//
//            while (rs.next()) {
//
//              RegistroDTO factura = new RegistroDTO(
//                        rs.getLong(1),
//                        rs.getTimestamp(2),
//                        rs.getString(3),
//                        rs.getLong(4),
//                        rs.getLong(5),
//                        rs.getLong(6),
//                        rs.getLong(7),
//                        rs.getString(8),
//                        rs.getDouble(9),
//                        rs.getDate(10),
//                        rs.getDouble(11),
//                        rs.getDouble(12)
//                );
//
//                factura.setCliente(new ClienteDTO(
//                        factura.getId_cliente(),
//                        rs.getString(13),
//                        rs.getString(14),
//                        rs.getString(15),
//                        rs.getString(16),
//                        rs.getString(17),
//                        rs.getTimestamp(18))
//                );
//
//                factura.setUsuario(new UsuarioDTO(
//                        factura.getId_usuario(),
//                        rs.getString(19),
//                        rs.getString(20),
//                        rs.getString(21),
//                        rs.getString(22),
//                        rs.getLong(23))
//                );
//                factura.setMaterial(new MaterialDTO(
//                        factura.getId_material(),
//                        rs.getString(24),
//                        rs.getString(25))
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
//
//    }
    public ArrayList<RegistroDTO> listarRegistrosMaterial(long id) {
        lista = new ArrayList();
        try {
            String sql = "SELECT DISTINCT (F.ID) ,F.FECHA_REGISTRO,F.ESTADO,\n"
                    + "                    F.CLIENTES_ID,F.USUARIOS_ID,F.CANTIDAD,\n"
                    + "                    F.MATERIAL_ID,F.COMENTARIO,F.PRECIO,F.FECHA_ENTREGA,F.IVA,F.TOTAL_PAGADO,F.FACTURAS_ID\n"
                    + "                    ,C.DOCUMENTO,C.NOMBRES,\n"
                    + "                    C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,\n"
                    + "           U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID,M.NOMBRE,M.PATRON\n"
                    + "                    FROM registro F INNER JOIN clientes C ON C.ID =F.CLIENTES_ID,USUARIOS U,MATERIALES M WHERE "
                    + "                        F.USUARIOS_ID=U.ID AND F.MATERIAL_ID=M.ID AND M.ID=? ORDER BY F.ID ASC";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {

                RegistroDTO factura = new RegistroDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getString(3),
                        rs.getLong(4),
                        rs.getLong(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getDate(10),
                        rs.getDouble(11),
                        rs.getDouble(12),
                        rs.getLong(13)
                );

                factura.setCliente(new ClienteDTO(
                        factura.getId_cliente(),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getTimestamp(19))
                );

                factura.setUsuario(new UsuarioDTO(
                        factura.getId_usuario(),
                        rs.getString(20),
                        rs.getString(21),
                        rs.getString(22),
                        rs.getString(23),
                        rs.getLong(24))
                );
                factura.setMaterial(new MaterialDTO(
                        factura.getId_material(),
                        rs.getString(25),
                        rs.getString(26))
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

    public String convertirFechaString(Date date) {

        return new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(date);

    }

    public String convertirFechaStri(Date date) {

        return new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(date);

    }

    public ArrayList<RegistroDTO> listarIngresos(Date FechaD, Date FechaH) {
        lista = new ArrayList();
        String fecha1 = "";
        String fecha2 = "";
        try {
            fecha1 = convertirFechaString(FechaD);
            fecha2 = convertirFechaStri(FechaH);
            String sql = "SELECT DISTINCT (F.ID) ,F.FECHA_REGISTRO,F.ESTADO,\n"
                    + "                    F.FORMA_DE_PAGO,F.CLIENTES_ID,F.USUARIOS_ID,F.CANTIDAD,\n"
                    + "                    F.MATERIAL_ID,F.COMENTARIO,F.PRECIO,F.NUMERO_CUOTAS,F.CUOTAS_PAGADAS,F.FECHA_ENTREGA,F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,F.FACTURAS_ID\n"
                    + "                    ,C.DOCUMENTO,C.NOMBRES,\n"
                    + "                    C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,\n"
                    + "           U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID,M.NOMBRE,M.PATRON\n"
                    + "                    FROM registro2 F INNER JOIN clientes C ON C.ID =F.CLIENTES_ID,usuarios U,materiales M WHERE "
                    + "                        F.USUARIOS_ID=U.ID AND F.MATERIAL_ID=M.ID AND F.FECHA_REGISTRO>=? AND F.FECHA_REGISTRO<=? ORDER BY F.ID ASC";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, fecha1);
            pst.setString(2, fecha2);
            rs = pst.executeQuery();

            while (rs.next()) {

                RegistroDTO factura = new RegistroDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getString(3),
                        rs.getLong(4),
                        rs.getLong(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getDate(10),
                        rs.getDouble(11),
                        rs.getDouble(12),
                        rs.getLong(13)
                );

                factura.setCliente(new ClienteDTO(
                        factura.getId_cliente(),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getTimestamp(19))
                );

                factura.setUsuario(new UsuarioDTO(
                        factura.getId_usuario(),
                        rs.getString(20),
                        rs.getString(21),
                        rs.getString(22),
                        rs.getString(23),
                        rs.getLong(24))
                );
                factura.setMaterial(new MaterialDTO(
                        factura.getId_material(),
                        rs.getString(25),
                        rs.getString(26))
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

    public boolean registrarCuotasPagadas(long id, long cuota) {
        try {

            String sql = "UPDATE registro SET CUOTAS_PAGADAS=? WHERE ID=? ";

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

    public boolean eliminarRegistro(long id) {
 Connection link = null;
        try {
            String sql = "DELETE FROM registro WHERE ID=?";

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
                    Logger.getLogger(RegistroDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    public boolean eliminarRegistros(long id) {

        try {
            String sql = "DELETE FROM registro WHERE FACTURAS_ID=?";

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

    public boolean cambiarComentario(long id, String estado) {
        System.out.println(id + estado);
        Connection link =null;
        try {
            String sql = "UPDATE registro SET COMENTARIO=?  WHERE ID=?  ";

             link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, estado);
            str.setLong(2, id);

            return str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RegistroDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    public boolean Material(long id, long fecha) {
        boolean exito = false;

        try {
            String sql = "UPDATE registro SET MATERIAL_ID=? WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, fecha);
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

    public boolean editarPrecio(long id, double precio, double total, double iva, long cantidad) {
        boolean exito = false;
        Connection link = null;

        try {
            String sql = "UPDATE registro SET PRECIO=?,TOTAL_A_PAGAR=?,IVA=?,CANTIDAD=? WHERE ID=?";
 link = Conexion.getInstance().dataSource.getConnection();
             PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setDouble(1, precio);
            str.setDouble(2, total);
            str.setDouble(3, iva);
            str.setLong(4, cantidad);
            str.setLong(5, id);

            exito = str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
           if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RegistroDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return exito;
    }

}
