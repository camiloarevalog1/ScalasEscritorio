/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.NombreGastoDTO;
import com.zapateria.main.dto.NombreIngresosDTO;
import com.zapateria.main.dto.ProveedorDTO;
import com.zapateria.main.dto.UsuarioDTO;
import com.zapateria.main.interfaces.INombreIngresos;
import com.zapateria.main.interfaces.IProveedoresDAO;
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
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class NombreIngresosDAO extends ConexionDAO implements INombreIngresos {

    private static final long serialVersionUID = -1996906684468261389L;

    public NombreIngresosDAO() {
        super();
    }

    public boolean registrar(Timestamp fecha,String nombre,long id_n ,double pago) {
        Connection link = null;
        try {
            String sql = "INSERT INTO nombre_ingresos (FECHA_REGISTRO,NOMBRE,NOMBRES_ID,PAGO)\n"
                    + "VALUES(?,?,?,?)";
 link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            
            str.setTimestamp(1, fecha);
            str.setString(2, nombre);
            str.setLong(3, id_n);
            str.setDouble(4, pago);

            return str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NombreIngresosDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }
    public boolean registrar2(String nombre,long id_n ,double pago,Date fecha) {
        Connection link = null;
        try {
            JOptionPane.showMessageDialog(null,nombre+id_n+pago+fecha );
            String sql = "INSERT INTO nombre_ingresos (NOMBRE,NOMBRES_ID,PAGO,FECHA_REGISTRO)\n"
                    + "VALUES(?,?,?,?)";

            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, nombre);
            str.setLong(2, id_n);
            str.setDouble(3, pago);
            str.setDate(4, fecha);

            return str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NombreIngresosDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
             }
        }
        return false;
    }

    public NombreIngresosDTO consultar(long id) {
        try {
            String sql = "SELECT ID,NOMBRE,PAGO,FECHA_REGISTRO,NOMBRES_ID\n"
                    + "FROM NOMBRE_INGRESOS WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {
                return new NombreIngresosDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getTimestamp(4),
                        rs.getLong(5)
                );
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

    public NombreIngresosDTO consultarNombre(String nombre) {
        try {
            String sql = "SELECT ID,NOMBRE,PAGO,FECHA,NOMBRES_ID\n"
                    + "FROM NOMBRE_INGRESOS WHERE NOMBRE=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, nombre);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {
                return new NombreIngresosDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getTimestamp(4),
                        rs.getLong(5)
                );
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

    public ArrayList<NombreIngresosDTO> listar() {
        lista = new ArrayList();
        Connection link = null;
        
        try {
            String sql = "SELECT a.ID,a.NOMBRE,a.PAGO,a.FECHA_REGISTRO,a.NOMBRES_ID,g.NOMBRE"
                    + " FROM nombre_ingresos a, nombre_gastos g WHERE a.NOMBRES_ID=g.ID ORDER BY FECHA_REGISTRO ASC";

          
               link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            ResultSet rs = str.executeQuery();

            while (rs.next()) {
                lista.add(new NombreIngresosDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getTimestamp(4),
                        rs.getLong(5),
                        rs.getString(6)
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NombreIngresosDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }

    @Override
    public boolean editar(long id,long nombreg ,String nombre ,double pago) {
       Connection link = null;
        try {
            
            String sql = "UPDATE nombre_ingresos SET NOMBRE=?,PAGO=?,NOMBRES_ID=? WHERE ID=?";

          link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, nombre);
            str.setDouble(2, pago);
            str.setLong(3, nombreg);

            str.setLong(4, id);
            return str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NombreIngresosDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }
    public boolean editarPrecio(long id, double pago) {
         Connection link = null;
        try {
            String sql = "UPDATE NOMBRE_INGRESOS SET PAGO=? WHERE ID=?";

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
                    Logger.getLogger(NombreIngresosDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    @Override
    public boolean eliminar(long id) {
        Connection link = null;
        try {
            String sql = "DELETE FROM NOMBRE_INGRESOS WHERE ID=?";
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
                    Logger.getLogger(NombreIngresosDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
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

    public ArrayList<NombreIngresosDTO> listar(Date fecha) {
        lista = new ArrayList();
        Connection link = null;
        System.out.println("entro listar");
        String fecha1 = "";
        String fecha2 = "";
        try {
            fecha1 = convertirFechaString(fecha);
            fecha2 = convertirFechaStri(fecha);
            String sql = "SELECT a.ID,a.NOMBRE,a.PAGO,a.FECHA_REGISTRO,a.NOMBRES_ID,g.NOMBRE"
                    + " FROM nombre_ingresos a, nombre_gastos g WHERE "
                    + "a.FECHA_REGISTRO>= ? AND a.FECHA_REGISTRO <= ? GROUP BY a.ID";
            
            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, fecha1);
            str.setString(2, fecha2);
             ResultSet rs = str.executeQuery();

            while (rs.next()) {
                lista.add(new NombreIngresosDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getTimestamp(4),
                        rs.getLong(5),
                        rs.getString(6)
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NombreIngresosDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }
    
    public ArrayList<NombreIngresosDTO> listar(long id) {
        lista = new ArrayList();
        Connection link = null;

        try {
            String sql = "SELECT ID,NOMBRE,PAGO,FECHA_REGISTRO,NOMBRES_ID FROM nombre_ingresos WHERE NOMBRES_ID=?";

            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);
           ResultSet rs = str.executeQuery();

            while (rs.next()) {
                lista.add(new NombreIngresosDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getTimestamp(4),
                        rs.getLong(5)
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NombreGastoDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }

}
