/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.UsuarioDTO;
import com.zapateria.main.util.BuscadorCarpeta;
import com.zapateria.main.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class BaseDAO extends ConexionDAO implements com.zapateria.main.interfaces.IBasesDAO{
    
    public boolean crearSQL() {
        //Calendar fecha =Calendar.getInstance();
        Date date = new Date();
         Connection link = null;
        String cadena = "";
        String CARPETA = "CopiasBaseDeDatos";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);
        
        DateFormat h = new SimpleDateFormat("HH-mm-ss");
        DateFormat dat = new SimpleDateFormat("dd-MM-yyyy");
        String p = dat.format(date);
        String a = h.format(date) + ".sql";

        String s = c + p + "\\"+"copia" + a + "";
        System.out.println(a);

        try {
            String sql = "SCRIPT SIMPLE TO ? SCHEMA ZAPATERIA";

            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, s);
            ResultSet rs = str.executeQuery();

            if (rs.next() && rs.absolute(1)) {
                   JOptionPane.showMessageDialog(null, "Se ha guardado la copia en:"+ s);
                return true;
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
        return false;
    }
    
    public boolean LimpiarBase() {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM ABONO_PRESTAMO";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

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
    
     public boolean LimpiarCompras() {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM NOMBRE_INGRESOS";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

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
     public boolean LimpiarVentas2() {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM VENTAS2";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

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
     public boolean LimpiarFacturas2() {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM FACTURAS3";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

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
      public boolean LimpiarIngresos() {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM INGRESOS_EGRESOS";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

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
     public boolean LimpiarFacturas() {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM FACTURAS";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

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
     public boolean LimpiarRegistro() {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM REGISTRO";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

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
     public boolean LimpiarRegistro2() {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM REGISTRO2";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

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
     public boolean LimpiarVentas() {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM VENTAS";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

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

    public boolean LimpiarAbonoUsuario() {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM ABONO_USUARIO";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            

            if (pst.executeUpdate()==1) {

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

    public boolean LimpiarFactura() {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM FACTURAS";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

            if (pst.executeUpdate()==1) {

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

    public boolean LimpiarLiquidacion() {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM LIQUIDACION";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            

            if (pst.executeUpdate()==1) {

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

    public boolean LimpiarNominaUsuario() {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM NOMINA_USUARIO";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            

            if (pst.executeUpdate()==1) {

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

    public boolean LimpiarPrestamoUsuario() {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM PRESTAMO_USUARIO";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            

            if (pst.executeUpdate()==1) {

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
    
    public boolean LimpiarPagos() {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM PAGOS";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            

            if (pst.executeUpdate()==1) {

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
    public boolean LimpiarCierres() {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM INGRESOS_EGRESOS";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            

            if (pst.executeUpdate()==1) {

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
    
    public boolean LimpiarLibro() {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM NOMBRE_INGRESOS";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            

            if (pst.executeUpdate()==1) {

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
    public boolean LimpiarComprasP() {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM COMPRAS";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            

            if (pst.executeUpdate()==1) {

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
    
    public boolean LimpiarPagosP() {
        //Calendar fecha =Calendar.getInstance();

        try {
            String sql = "DELETE FROM PAGOSPROV";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            

            if (pst.executeUpdate()==1) {

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
    
    public boolean EliminarSquemaSQL() {
        //Calendar fecha =Calendar.getInstance();
//        Date date = new Date();
//        String cadena = "";
//        String CARPETA = "CopiasBaseDeDatos";
//        BuscadorCarpeta b = null;
//        String c = b.buscar(CARPETA);
//        
//        DateFormat h = new SimpleDateFormat("HH-mm-ss");
//        DateFormat dat = new SimpleDateFormat("dd-MM-yyyy");
//        String p = dat.format(date);
//        String a = h.format(date) + ".sql";
//
//        String s = c + p + "\\"+"copia" + a + "";
//        System.out.println(a);
        JOptionPane.showMessageDialog(null, "SIRVE");
        try {
            String sql = "RUNSCRIPT FROM C:\\Users\\DELL\\Documents\\bitbucket\\zapateria\\CopiasBaseDeDatos\\10-01-2018\\copia16-33-50.sql";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
//            pst.setString(1, s);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {
                   JOptionPane.showMessageDialog(null, "Se ha guardado la copia en:");
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
