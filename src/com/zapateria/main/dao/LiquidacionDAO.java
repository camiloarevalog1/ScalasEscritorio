/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.AbonoDTO;
import com.zapateria.main.dto.FacturaDTO;
import com.zapateria.main.dto.LiquidacionDTO;
import com.zapateria.main.dto.NominaDTO;
import com.zapateria.main.dto.RegistroDTO;
import com.zapateria.main.dto.UsuarioDTO;
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
 * @author DELL
 */
public class LiquidacionDAO extends ConexionDAO implements com.zapateria.main.interfaces.ILiquidacionDAO {

    private static final long serialVersionUID = -4298378340877167850L;

    public LiquidacionDAO() {
        super();
    }

    @Override
    public boolean registrarLiquidacion(long id_usuario, Date fecha_salida, int horas_trabajadas, int dias_trabajados,
            Date fecha_ingreso, double sueldo_basico,
            double cesantias, double intereses_cesantias, double prima, double vacaciones, double valor_liquidacion, double bonificacion) {
       Connection link = null;
        try {

            String sql = "INSERT INTO LIQUIDACION (ID_USUARIO,FECHA_SALIDA,HORAS_TRABAJADAS,DIAS_TRABAJADOS,FECHA_INGRESO,"
                    + "SUELDO_BASICO,CESANTIAS,INTERESES_CESANTIAS,PRIMA_SERVICIO,VACACIONES,VALOR_LIQUIDACION,BONIFICACION"
                    + ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id_usuario);
            str.setDate(2, fecha_salida);
            str.setInt(3, horas_trabajadas);
            str.setInt(4, dias_trabajados);
            str.setDate(5, fecha_ingreso);
            str.setDouble(6, sueldo_basico);
            str.setDouble(7, cesantias);
            str.setDouble(8, intereses_cesantias);
            str.setDouble(9, prima);
            str.setDouble(10, vacaciones);
            str.setDouble(11, valor_liquidacion);
            str.setDouble(12, bonificacion);

            return str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LiquidacionDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    @Override
    public ArrayList<LiquidacionDTO> listarLiquidaciones() {
        lista = new ArrayList();
        try {
            String sql = "SELECT ID_USUARIO,FECHA_SALIDA,HORAS_TRABAJADAS,DIAS_TRABAJADOS,FECHA_INGRESO,"
                    + "SUELDO_BASICO,CESANTIAS,INTERESES_CESANTIAS,PRIMA_SERVICIO,VACACIONES,VALOR_LIQUIDACION,BONIFICACION"
                    + " FROM LIQUIDACION ";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                LiquidacionDTO liquidacion = new LiquidacionDTO(
                        rs.getLong(1),
                        rs.getDate(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getDate(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getDouble(9),
                        rs.getDouble(10),
                        rs.getDouble(11),
                        rs.getDouble(12)
                );

                lista.add(liquidacion);
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

    public ArrayList<LiquidacionDTO> listarLiquida() {
        lista = new ArrayList();
        Connection link = null;
        try {
            String sql = "SELECT ID,ID_USUARIO,FECHA_SALIDA,HORAS_TRABAJADAS,DIAS_TRABAJADOS,FECHA_INGRESO,"
                    + "SUELDO_BASICO,CESANTIAS,INTERESES_CESANTIAS,PRIMA_SERVICIO,VACACIONES,VALOR_LIQUIDACION,BONIFICACION"
                    + " FROM liquidacion ";

             link = Conexion.getInstance().dataSource.getConnection();
             PreparedStatement str;
            str = link.prepareStatement(sql);
             ResultSet rs = str.executeQuery();
            while (rs.next()) {

                LiquidacionDTO liquidacion = new LiquidacionDTO(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getDate(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getDate(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getDouble(9),
                        rs.getDouble(10),
                        rs.getDouble(11),
                        rs.getDouble(12),
                        rs.getDouble(13)
                );

                lista.add(liquidacion);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LiquidacionDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }

    @Override
    public ArrayList<LiquidacionDTO> buscarLiquidacionEmpleado(long cedula) {
        lista = new ArrayList();
        Connection link = null;
        String fecha1 = "";
        String fecha2 = "";
        try {

            //String f= fecha2+"00:00:00";
            String sql = "SELECT L.ID,L.FECHA_SALIDA,L.DIAS_TRABAJADOS,L.FECHA_INGRESO,L.SUELDO_BASICO"
                    + ",L.CESANTIAS,L.INTERESES_CESANTIAS,L.PRIMA_SERVICIO,L.VACACIONES,L.VALOR_LIQUIDACION,L.BONIFICACION "
                    + "FROM   liquidacion L, usuarios U"
                    + " WHERE L.ID_USUARIO=U.ID AND U.ID=? ";

            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, cedula);

            ResultSet rs = str.executeQuery();

            while (rs.next()) {

                LiquidacionDTO liquidacion = new LiquidacionDTO(
                        rs.getLong(1),
                        rs.getDate(2),
                        rs.getInt(3),
                        rs.getDate(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getDouble(9),
                        rs.getDouble(10),
                        rs.getDouble(11)
                );

                lista.add(liquidacion);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
           if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LiquidacionDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }

    public int cantidadliqidacion() {
        int total = 0;
        lista = new ArrayList();
        String fecha1 = "";
        String fecha2 = "";
        try {

            //String f= fecha2+"00:00:00";
            String sql = "SELECT COUNT(ID) FROM LIQUIDACION ";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

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

    public ArrayList<LiquidacionDTO> listarLiquidacion() {
        lista = new ArrayList();
        try {
            String sql = "SELECT ID_USUARIO,FECHA_SALIDA,HORAS_TRABAJADAS,DIAS_TRABAJADOS,FECHA_INGRESO,"
                    + "SUELDO_BASICO,CESANTIAS,INTERESES_CESANTIAS,PRIMA_SERVICIO,VACACIONES,VALOR_LIQUIDACION,BONIFICACION"
                    + " FROM LIQUIDACION ";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                NominaDTO nomina = new NominaDTO(
                        rs.getLong(1),
                        rs.getDouble(2),
                        rs.getDouble(3),
                        rs.getDate(4),
                        rs.getTimestamp(5),
                        rs.getInt(6),
                        rs.getInt(7)
                );

                lista.add(nomina);
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

        return new SimpleDateFormat("yyyy-MM-dd").format(date);

    }

    public String convertirFechaStri(Date date) {

        return new SimpleDateFormat("yyyy-MM-dd").format(date);

    }

    public ArrayList<LiquidacionDTO> listarFechaLiquidacion(Date fechaD, Date fechaH) {
        lista = new ArrayList();
         Connection link = null;
        String fecha1 = "";
        String fecha2 = "";
        try {
            fecha1 = convertirFechaString(fechaD);
            fecha2 = convertirFechaStri(fechaH);
            //String f= fecha2+"00:00:00";
            System.out.println(fecha2);
            System.out.println(fecha1);
            String sql = "SELECT DISTINCT( L.ID),ID_USUARIO,L.FECHA_SALIDA,L.DIAS_TRABAJADOS,L.FECHA_INGRESO,L.SUELDO_BASICO,L.CESANTIAS,L.INTERESES_CESANTIAS,L.PRIMA_SERVICIO,L.VACACIONES,L.VALOR_LIQUIDACION,L.BONIFICACION "
                    + "FROM   LIQUIDACION L, USUARIOS U"
                    + " WHERE L.ID_USUARIO=U.ID AND L.FECHA_SALIDA BETWEEN ? AND ? GROUP BY (L.ID) ";

           link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, fecha1);
            str.setString(2, fecha2);

             ResultSet rs = str.executeQuery();

            while (rs.next()) {

                LiquidacionDTO liquidacion = new LiquidacionDTO(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getDate(3),
                        rs.getInt(4),
                        rs.getDate(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getDouble(9),
                        rs.getDouble(10),
                        rs.getDouble(11),
                        rs.getDouble(12)
                );

                lista.add(liquidacion);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
              if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LiquidacionDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;

    }
    
    public double  listarLiquidacionTotal(Date fecha) {
        lista = new ArrayList();
        Connection link = null;
        System.out.println("entro listar");
        String fecha1 = "";
        String fecha2 = "";
        double total=0;
        try {
            fecha1 = convertirFechaString(fecha);
            fecha2 = convertirFechaStri(fecha);
            String sql = "SELECT SUM(VALOR_LIQUIDACION) FROM liquidacion  WHERE FECHA_SALIDA>= ? AND FECHA_SALIDA <= ?";
            
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
                    Logger.getLogger(LiquidacionDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return total;
    }
    public boolean BorrarLiquidaciones(long id) {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM LIQUIDACION WHERE ID_USUARIO=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            if (pst.executeUpdate() == 1) {

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

}
