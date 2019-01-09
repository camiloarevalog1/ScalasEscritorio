/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.AbonoDTO;
import com.zapateria.main.dto.Abono_PrestamoDTO;
import com.zapateria.main.dto.FacturaDTO;
import com.zapateria.main.dto.NombreIngresosDTO;
import com.zapateria.main.dto.NominaDTO;
import com.zapateria.main.dto.PrestamoDTO;
import com.zapateria.main.dto.UsuarioDTO;
import com.zapateria.main.interfaces.IAbonoDAO;
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
public class AbonoDAO extends ConexionDAO implements IAbonoDAO {

    @Override
    public boolean registrarAbono(long usuario, double valor, Date fecha) {
       Connection link = null;
        try {

            String sql = "INSERT INTO abono_usuario (ID_DEUDA,VALOR,FECHA"
                    + ") VALUES(?,?,?)";

           link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, usuario);
            str.setDouble(2, valor);
            str.setDate(3, fecha);

            return str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
           if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Abono_PrestamoDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;

    }
     public String convertirFechaString(Date date) {

        return new SimpleDateFormat("yyyy-MM-dd").format(date);

    }

    public String convertirFechaStri(Date date) {

        return new SimpleDateFormat("yyyy-MM-dd").format(date);

    }

     public ArrayList<AbonoDTO> listarFechaAbon(Date fechaD, Date fechaH) {
        lista = new ArrayList();
        String fecha1 = "";
        String fecha2 = "";
        try {
            fecha1 = convertirFechaString(fechaD);
            fecha2 = convertirFechaStri(fechaH);
            //String f= fecha2+"00:00:00";
            System.out.println(fecha2);
            System.out.println(fecha1);
            String sql = "SELECT ID_DEUDA,VALOR,FECHA\n "
                    + "FROM ABONO_USUARIO  F WHERE  F.FECHA BETWEEN ? AND ?  ";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
//            pst.setString(0, 'CONTADO');
            pst.setString(1, fecha1);
            pst.setString(2, fecha2);
            rs = pst.executeQuery();

            while (rs.next()) {

                 AbonoDTO abono = new AbonoDTO(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getDouble(3),
                        rs.getDate(4)
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
    @Override
    public int cantidadAbono() {
        int total = 0;
        lista = new ArrayList();
        String fecha1 = "";
        String fecha2 = "";
        try {

            //String f= fecha2+"00:00:00";
            String sql = "SELECT COUNT(ID) FROM ABONO_USUARIO ";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

            rs = pst.executeQuery();
            if (rs.next() && rs.absolute(1)) {

                total= rs.getInt(1) ;
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
    
     public int cantidadAbonoCedula(String cedula) {
        int total = 0;
        lista = new ArrayList();
        String fecha1 = "";
        String fecha2 = "";
        try {

            //String f= fecha2+"00:00:00";
            String sql = "SELECT COUNT(ID) FROM ABONO_USUARIO WHERE ID=? ";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

            rs = pst.executeQuery();
            if (rs.next() && rs.absolute(1)) {

                total= rs.getInt(1) ;
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
    public ArrayList<AbonoDTO> buscarCedulaAbono(long cedula) {
        lista = new ArrayList();
        Connection link = null;
        String fecha1 = "";
        String fecha2 = "";
        try {

            //String f= fecha2+"00:00:00";
            String sql = "SELECT A.ID,A.ID_DEUDA,A.VALOR,A.FECHA   FROM abono_usuario A , abono_prestamo AP, usuarios U"
                    + " WHERE A.ID_DEUDA=AP.ID AND AP.ID_USUARIO=U.ID AND U.ID=? ";

          link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, cedula);

              ResultSet rs = str.executeQuery();

            while (rs.next()) {

                AbonoDTO abono = new AbonoDTO(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getDouble(3),
                        rs.getDate(4)
                );

                lista.add(abono);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AbonoDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;

    }
    public double  listarAbonoFecha(Date fecha) {
        lista = new ArrayList();
        Connection link = null;
        System.out.println("entro listar");
        String fecha1 = "";
        String fecha2 = "";
        double total=0;
        try {
            fecha1 = convertirFechaString(fecha);
            fecha2 = convertirFechaStri(fecha);
            String sql = "SELECT SUM(VALOR) FROM abono_usuario  WHERE FECHA>= ? AND FECHA <= ?";
            
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
                    Logger.getLogger(UsuarioDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return total;
    }
    
    public boolean BorrarNominas(long id) {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM ABONO_USUARIO WHERE ID_USUARIO=?";

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
