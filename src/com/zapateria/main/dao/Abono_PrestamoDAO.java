/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.AbonoDTO;
import com.zapateria.main.dto.Abono_PrestamoDTO;
import com.zapateria.main.dto.LiquidacionDTO;
import com.zapateria.main.dto.NombreIngresosDTO;
import com.zapateria.main.dto.UsuarioDTO;
import com.zapateria.main.util.Conexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class Abono_PrestamoDAO extends ConexionDAO implements com.zapateria.main.interfaces.IAbonoPrestamoDAO {

    private static final long serialVersionUID = -4298378340877167884L;

    public Abono_PrestamoDAO() {
        super();
    }

    @Override
    public ArrayList<Abono_PrestamoDTO> listarDebe() {
        lista = new ArrayList();
        Connection link = null;
        try {
            String sql = "SELECT ID_USUARIO,TOTAL_PRESTAMO,TOTAL_DEBE,TOTAL_ABONADO"
                    + " FROM abono_prestamo ";

            link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);
            ResultSet rs = str.executeQuery();
            while (rs.next()) {

                Abono_PrestamoDTO abono = new Abono_PrestamoDTO(
                        rs.getLong(1),
                        rs.getDouble(2),
                        rs.getInt(3),
                        rs.getDouble(4)
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
                    Logger.getLogger(Abono_PrestamoDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }

    public ArrayList<Abono_PrestamoDTO> listarDebeCedula(long id) {
        lista = new ArrayList();
        Connection link = null;
        try {
            String sql = "SELECT ID_USUARIO,TOTAL_PRESTAMO,TOTAL_DEBE,TOTAL_ABONADO"
                    + " FROM abono_prestamo WHERE ID_USUARIO = ? ";

            link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);
            ResultSet rs = str.executeQuery();
            while (rs.next()) {

                Abono_PrestamoDTO abono = new Abono_PrestamoDTO(
                        rs.getLong(1),
                        rs.getDouble(2),
                        rs.getInt(3),
                        rs.getDouble(4)
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
                    Logger.getLogger(Abono_PrestamoDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }

    @Override
    public int cantidadAbono_Prestamo() {
        int total = 0;
         Connection link = null;
        lista = new ArrayList();
        String fecha1 = "";
        String fecha2 = "";
        try {

            //String f= fecha2+"00:00:00";
            String sql = "SELECT COUNT(ID) FROM abono_prestamo ";

            link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);
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
                    Logger.getLogger(Abono_PrestamoDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return total;

    }

    public boolean BorrarAbono_Prestamo(long id) {
        //Calendar fecha =Calendar.getInstance();
Connection link = null;
        try {
            String sql = "DELETE FROM abono_prestamo WHERE ID_USUARIO=?";

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
                    Logger.getLogger(Abono_PrestamoDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    @Override
    public Abono_PrestamoDTO buscarAbonoPrestamo(long id) {
        Connection link = null;
        try {
            String sql = "SELECT ID,ID_USUARIO,TOTAL_PRESTAMO,TOTAL_DEBE,TOTAL_ABONADO"
                    + " FROM abono_prestamo WHERE ID_USUARIO=? LIMIT 1";
            link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);
             ResultSet rs = str.executeQuery();
            if (rs.next()) {
                return new Abono_PrestamoDTO(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getDouble(5));

            }
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
        return null;
    }

    @Override
    public double Buscarvalor(long id) {
        double anticipo = 0;
         Connection link = null;

        try {
            String sql = "SELECT ID,ID_USUARIO,TOTAL_PRESTAMO,TOTAL_DEBE,TOTAL_ABONADO FROM abono_prestamo WHERE ID_USUARIO=?";

            link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);
            ResultSet rs = str.executeQuery();

            while (rs.next()) {

                Abono_PrestamoDTO prestamo = new Abono_PrestamoDTO(
                        rs.getLong(1),
                        rs.getLong(2),
                        anticipo += rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getDouble(5)
                );

            }

            System.out.println(anticipo);

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
        return anticipo;

    }

    @Override
    public boolean crearAbonoUsuario(long id, double total, double total_debe, double total_abono) {
        Connection link = null;
        String sql = "INSERT INTO abono_prestamo (ID_USUARIO,TOTAL_PRESTAMO,TOTAL_DEBE,TOTAL_ABONADO,"
                + ") VALUES(?,?,?,?)";
        try {
            link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);
            str.setDouble(2, total_debe);
            str.setDouble(3, total);
            str.setDouble(4, total_abono);
            System.out.println("SE CREO NOMINA");
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

    @Override
    public boolean actualizarAbonoPrestamo(long abo, double prestamo, double total_debe) {
        Connection link = null;
        try {
            String sql = "UPDATE abono_prestamo SET TOTAL_DEBE=?,TOTAL_PRESTAMO=?"
                    + "WHERE ID=?";

            link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setDouble(1, total_debe);
            str.setDouble(2, prestamo);
            str.setLong(3, abo);
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

    public boolean actualizarAbonoPresta(long id, double total_abonado, double total_debe) {
        Connection link = null;
        try {
            String sql = "UPDATE abono_prestamo SET TOTAL_ABONADO=?,TOTAL_DEBE=?"
                    + "WHERE ID=?";

            link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setDouble(1, total_abonado);
            str.setDouble(2, total_debe);
            str.setLong(3, id);
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

}
