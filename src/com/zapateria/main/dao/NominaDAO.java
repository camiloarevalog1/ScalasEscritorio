/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.ClienteDTO;
import com.zapateria.main.dto.FacturaDTO;
import com.zapateria.main.dto.LiquidacionDTO;
import com.zapateria.main.dto.NominaDTO;
import com.zapateria.main.dto.PagoDTO;
import com.zapateria.main.dto.RolDTO;
import com.zapateria.main.dto.UsuarioDTO;
import com.zapateria.main.interfaces.IFacturasDAO;
import com.zapateria.main.interfaces.INominaDAO;
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
public class NominaDAO extends ConexionDAO implements INominaDAO {

    @Override
    public boolean registrarNomina(long id, double valorHora, double pagoTot,
            Date fechaInicio, Date fechaFin, int horasTrabajadas, int diasTrabajados,long horas,double abonado) {
        System.out.println("3");
        Connection link = null;
        try {

            String sql = "INSERT INTO nomina_usuario(ID_USUARIO,SALARIO_HORA,PAGO_TOTAL,"
                    + "FECHA_INICIO,FECHA_FIN,HORAS_TRABAJADAS,DIAS_TRABAJADOS,DIAS_EXTRAS,ABONADO) VALUES(?,?,?,?,?,?,?,?,?)";

            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);
            str.setDouble(2, valorHora);

            str.setDouble(3, pagoTot);

            str.setDate(4, fechaInicio);
            str.setDate(5, fechaFin);
            str.setInt(6, horasTrabajadas);
            str.setInt(7, diasTrabajados);
            str.setLong(8, horas);
            str.setDouble(9, abonado);
            System.out.println("guardo");
            return str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NominaDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }
    
    public long consultarUltimaNomina() {
        Connection link = null;
        try {
            String sql = "SELECT MAX(ID) FROM nomina_usuario";

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
                    Logger.getLogger(NominaDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return 1;
    }

    @Override
    public int AusenteDias(long id_us) {
        int d = 0;
        try {
            String sql = "SELECT SUM(DIAS_AUSENTE)"
                    + " FROM NOMINA_USUARIO WHERE ID_USUARIO=? ";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id_us);
            rs = pst.executeQuery();
            if (rs.next()) {
                d = rs.getInt(1);

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
        return d;
    }

    public int HorasTrabajadas(long id_us) {
        int d = 0;
        try {
            String sql = "SELECT SUM(HORAS_TRABAJADAS)"
                    + " FROM NOMINA_USUARIO WHERE ID_USUARIO=? ";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id_us);
            rs = pst.executeQuery();
            if (rs.next()) {
                d = rs.getInt(1);

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
        return d;
    }

    @Override
    public int diasTrabajados(long id_us) {
        int d = 0;
        try {
            String sql = "SELECT SUM(DIAS_TRABAJADOS)"
                    + " FROM NOMINA_USUARIO WHERE ID_USUARIO=? ";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id_us);
            rs = pst.executeQuery();
            if (rs.next()) {
                d = rs.getInt(1);

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
        return d;
    }
    public int horasTrabajados(long id_us) {
        int d = 0;
        try {
            String sql = "SELECT SUM(HORAS_TRABAJADAS)"
                    + " FROM NOMINA_USUARIO WHERE ID_USUARIO=? ";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id_us);
            rs = pst.executeQuery();
            if (rs.next()) {
                d = rs.getInt(1);

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
        return d;
    }

    public String convertirFechaString(Date date) {

        return new SimpleDateFormat("yyyy-MM-dd").format(date);

    }

    public String convertirFechaStri(Date date) {

        return new SimpleDateFormat("yyyy-MM-dd").format(date);

    }

    @Override
    public ArrayList<NominaDTO> listarFechaNomi(Date fechaD, Date fechaH) {
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
            String sql = "SELECT DISTINCT (N.ID) ,N.ID_USUARIO,N.SALARIO_HORA,N.PAGO_TOTAL,"
                    + "N.FECHA_INICIO,N.FECHA_FIN,N.HORAS_TRABAJADAS,N.DIAS_TRABAJADOS,ABONADO,DIAS_EXTRAS "
                    + "FROM nomina_usuario N WHERE N.FECHA_FIN BETWEEN ? AND ? GROUP BY (N.ID) ";

          link = Conexion.getInstance().dataSource.getConnection();
             PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, fecha1);
            str.setString(2, fecha2);
           ResultSet rs = str.executeQuery();

            while (rs.next()) {

                 NominaDTO nomina = new NominaDTO(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getDate(5),
                        rs.getTimestamp(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getDouble(9),
                        rs.getLong(10)
                );

                

                lista.add(nomina);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
           if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NominaDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;

    }

    
    @Override
    public ArrayList<NominaDTO> listarCedulaNomin(String cedula) {
        lista = new ArrayList();
        String fecha1 = "";
        String fecha2 = "";
        try {

            //String f= fecha2+"00:00:00";
            String sql = "SELECT N.ID,N.SALARIO_HORA,N.PAGO_TOTAL,N.FECHA_INICIO,N.FECHA_FIN,N.HORAS_TRABAJADAS,N.DIAS_TRABAJADOS\n"
                    + "                    FROM ZAPATERIA.NOMINA_USUARIO N,ZAPATERIA.USUARIOS U WHERE N.ID_USUARIO=U.ID AND U.DOCUMENTO=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, cedula);

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
    
    @Override
    public double  totalNomina(long cedula) {
       double x=0;
        try {

            //String f= fecha2+"00:00:00";
            String sql = "SELECT N.ID,N.SALARIO_HORA,N.PAGO_TOTAL,N.FECHA_INICIO,N.FECHA_FIN,N.HORAS_TRABAJADAS,N.DIAS_TRABAJADOS\n"
                    + "                    FROM ZAPATERIA.NOMINA_USUARIO N,ZAPATERIA.USUARIOS U WHERE N.ID_USUARIO=U.ID AND U.ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, cedula);

            rs = pst.executeQuery();

            while (rs.next()) {

                NominaDTO nomina = new NominaDTO(
                        rs.getLong(1),
                        rs.getDouble(2),
                       x+=rs.getDouble(3),
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
        return x;

    }

    public int cantidadNomina() {
        int total = 0;
        lista = new ArrayList();
        String fecha1 = "";
        String fecha2 = "";
        try {

            //String f= fecha2+"00:00:00";
            String sql = "SELECT COUNT(ID) FROM NOMINA_USUARIO  ";

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

    @Override
    public ArrayList<NominaDTO> listarNominas() {
        lista = new ArrayList();
        try {
            String sql = "SELECT ID_USUARIO,SALARIO_HORA,PAGO_TOTAL,FECHA_INICIO,FECHA_FIN,"
                    + "HORAS_TRABAJADAS,DIAS_TRABAJADOS FROM NOMINA_USUARIO ";

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
    
    @Override
    //Listar la nomina por id
    public ArrayList<NominaDTO> listarCedulaNomi(long cedula) {
        lista = new ArrayList();
                 Connection link = null;

        String fecha1 = "";
        String fecha2 = "";
        try {

            //String f= fecha2+"00:00:00";
            String sql = "SELECT N.ID, N.ID_USUARIO,N.SALARIO_HORA,N.PAGO_TOTAL,"
                    + "N.FECHA_INICIO,N.FECHA_FIN,N.HORAS_TRABAJADAS,N.DIAS_TRABAJADOS,"
                    + "U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,R.ROL FROM "
                    + "nomina_usuario N,usuarios U,roles R WHERE N.ID_USUARIO=U.ID AND"
                    + " U.ROLES_ID=R.ID AND U.ID=?";

              link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, cedula);

                       ResultSet rs = str.executeQuery();


            while (rs.next()) {

                NominaDTO nomina = new NominaDTO(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getDate(5),
                        rs.getTimestamp(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13)
                );

                lista.add(nomina);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NominaDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;

    }


    @Override
    //Listar nomina general
    public ArrayList<NominaDTO> listarNomin() {
        Connection link = null;
        lista = new ArrayList();
        try {
            String sql = "SELECT N.ID, N.ID_USUARIO,N.SALARIO_HORA,N.PAGO_TOTAL,"
                    + "N.FECHA_INICIO,N.FECHA_FIN,N.HORAS_TRABAJADAS,N.DIAS_TRABAJADOS,"
                    + "U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,R.ROL,N.DIAS_EXTRAS FROM "
                    + "nomina_usuario N,usuarios U,roles R WHERE N.ID_USUARIO=U.ID AND"
                    + " U.ROLES_ID=R.ID ";

            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
          ResultSet rs = str.executeQuery();
            while (rs.next()) {

                NominaDTO nomina = new NominaDTO(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getDate(5),
                        rs.getTimestamp(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getLong(14)
                );

                lista.add(nomina);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
           if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NominaDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }
    
    public double  listarNominaFecha(Date fecha) {
        lista = new ArrayList();
        Connection link = null;
        System.out.println("entro listar");
        String fecha1 = "";
        String fecha2 = "";
        double total=0;
        try {
            fecha1 = convertirFechaString(fecha);
            fecha2 = convertirFechaStri(fecha);
            String sql = "SELECT (SUM(PAGO_TOTAL)-SUM(ABONADO)) FROM nomina_usuario  WHERE FECHA_FIN>= ? AND FECHA_FIN <= ?";
            
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
                    Logger.getLogger(NominaDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return total;
    }
    public boolean BorrarNominas(long id) {
        //Calendar fecha =Calendar.getInstance();
Connection link = null;
        try {
            String sql = "DELETE FROM NOMINA_USUARIO WHERE ID=?";

            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);
            if (str.executeUpdate() == 1) {

                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NominaDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }
    
    public boolean eliminarNomina(long id) {

        

        try {
            String sql = "DELETE FROM PAGOS WHERE ID=?";

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

}
