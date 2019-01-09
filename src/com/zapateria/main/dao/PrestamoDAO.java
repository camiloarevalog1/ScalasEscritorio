/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.AbonoDTO;
import com.zapateria.main.dto.FacturaDTO;
import com.zapateria.main.dto.NombreIngresosDTO;
import com.zapateria.main.dto.NominaDTO;
import com.zapateria.main.dto.PagoDTO;
import com.zapateria.main.dto.PrestamoDTO;
import com.zapateria.main.dto.RegistroDTO;
import com.zapateria.main.interfaces.IPrestamoDAO;
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
public class PrestamoDAO extends ConexionDAO implements IPrestamoDAO {

    @Override
    public boolean registrarAdelanto(long usuario, double valor, Date fecha, String concepto) {
        Connection link = null;
        try {

            String sql = "INSERT INTO prestamo_usuario (ID_DEUDA,VALOR,FECHA,"
                    + "CONCEPTO) VALUES(?,?,?,?)";

            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, usuario);
            str.setDouble(2, valor);
            str.setDate(3, fecha);
            str.setString(4, concepto);

            return str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PrestamoDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
             }
        }
        return false;

    }

    public int cantidadPrestamo() {
        int total = 0;
        Connection link = null;
        lista = new ArrayList();
        String fecha1 = "";
        String fecha2 = "";
        try {

            //String f= fecha2+"00:00:00";
            String sql = "SELECT COUNT(ID) FROM PRESTAMO_USUARIO ";
  link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);

            rs = pst.executeQuery();
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
                    Logger.getLogger(PrestamoDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
             }
        }
        return total;

    }

    @Override
    public ArrayList<PrestamoDTO> buscarCedulaAdelanto(long cedula) {
        lista = new ArrayList();
        Connection link = null;
        String fecha1 = "";
        String fecha2 = "";
        try {

            //String f= fecha2+"00:00:00";
            String sql = "SELECT A.ID,A.VALOR,A.FECHA,A.CONCEPTO FROM prestamo_usuario A , abono_prestamo AP, usuarios U"
                    + " WHERE A.ID_DEUDA=AP.ID AND AP.ID_USUARIO=U.ID AND U.ID=? ";
            link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, cedula);

            ResultSet rs = str.executeQuery();
            while (rs.next()) {

                PrestamoDTO prestamo = new PrestamoDTO(
                        rs.getLong(1),
                        rs.getDouble(2),
                        rs.getDate(3),
                        rs.getString(4)
                );

                lista.add(prestamo);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PrestamoDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
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

    public double listarDeudaFecha(Date fecha) {
        lista = new ArrayList();
        Connection link = null;
        System.out.println("entro listar");
        String fecha1 = "";
        String fecha2 = "";
        double total = 0;
        try {
            fecha1 = convertirFechaString(fecha);
            fecha2 = convertirFechaStri(fecha);
            String sql = "SELECT SUM(VALOR) FROM prestamo_usuario  WHERE FECHA>= ? AND FECHA <= ?";

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
                    Logger.getLogger(PrestamoDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return total;
    }

    public boolean eliminar(long id) {
   Connection link = null;
        try {
            String sql = "DELETE FROM PRESTAMO_USUARIO WHERE ID=?";

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

}
