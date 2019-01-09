/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.CompraDTO;
import com.zapateria.main.dto.Ingresos_EgresosDTO;
import com.zapateria.main.dto.LiquidacionDTO;
import com.zapateria.main.dto.MaterialDTO;
import com.zapateria.main.dto.NombreIngresosDTO;
import com.zapateria.main.dto.ProveedorDTO;
import com.zapateria.main.dto.UsuarioDTO;
import com.zapateria.main.interfaces.IComprasDAO;
import com.zapateria.main.interfaces.IIngresos_Egresos;
import com.zapateria.main.interfaces.IMaterialesDAO;
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
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class Ingresos_EgresosDAO extends ConexionDAO implements IIngresos_Egresos {

    private final NombreIngresosDAO INombreIngresos = new NombreIngresosDAO();

    public Ingresos_EgresosDAO() {
        super();
    }

    public boolean registrar(Timestamp fecha,double deudas, double abonado, double liquidacion, double nominas, double salio, double entro, double caja) {
         Connection link = null;
        try {
            String sql = "INSERT INTO ingresos_egresos (FECHA,DEUDAS,ABONOS,LIQUIDACIONES,NOMINAS,"
                    + "TOTAL_SALIO,TOTAL_ENTRO,CAJA) VALUES(?,?,?,?,?,?,?,?)";

            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setTimestamp(1, fecha);
            str.setDouble(2, deudas);
            str.setDouble(3, abonado);
            str.setDouble(4, liquidacion);
            str.setDouble(5, nominas);
            str.setDouble(6, salio);
            str.setDouble(7, entro);
            str.setDouble(8, caja);

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
                    Logger.getLogger(Ingresos_EgresosDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

//    public Ingresos_EgresosDTO consultar(long id) {
//        try {
//            String sql = "SELECT ID,DESCRIPCION,CREDITO,DEBITO,FECHA,"
//                    + "NOMBRE_ID FROM INGRESOS_EGRESOS WHERE ID=?";
//            con = conexion.getConnection();
//            pst = con.prepareStatement(sql);
//            pst.setLong(1, id);
//            rs = pst.executeQuery();
//
//            if (rs.next() && rs.absolute(1)) {
//
//                return new Ingresos_EgresosDTO(
//                        rs.getLong(1),
//                        rs.getString(2),
//                        rs.getDouble(3),
//                        rs.getDouble(4),
//                        rs.getTimestamp(5),
//                        rs.getLong(6)
//                );
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
//        return null;
//    }
    @Override
    public ArrayList<Ingresos_EgresosDTO> listar() {

        lista = new ArrayList();
Connection link = null;
        try {
            String sql = "SELECT I.ID,I.FECHA,I.ABONOS,I.DEUDAS,I.LIQUIDACIONES,I.NOMINAS,I.TOTAL_SALIO,"
                    + "I.TOTAL_ENTRO,I.CAJA FROM ingresos_egresos I ORDER BY I.FECHA ASC";

            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            ResultSet rs = str.executeQuery();

            while (rs.next()) {

                Ingresos_EgresosDTO ingreso = new Ingresos_EgresosDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getDouble(9)
                );

                lista.add(ingreso);
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

    public ArrayList<Ingresos_EgresosDTO> listarIngreso(long id) {

        lista = new ArrayList();
        System.out.println(id + "metodo");

        try {
            String sql = "SELECT I.ID,I.FECHA,I.ABONOS,I.DEUDAS,I.LIQUIDACIONES,I.NOMINAS,I.TOTAL_SALIO,I.TOTAL_ENTRO,I.CAJA FROM INGRESOS_EGRESOS I WHERE I.ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Ingresos_EgresosDTO ingreso = new Ingresos_EgresosDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getDouble(9)
                );

                lista.add(ingreso);
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

    public ArrayList<Ingresos_EgresosDTO> listarComprasDiarias(Date fechaD) {

        lista = new ArrayList();

        try {
            String sql = "SELECT I.ID,I.DESCRIPCION,I.CREDITO,I.DEBITO,I.FECHA,"
                    + "N.ID,N.NOMBRE FROM INGRESOS_EGRESOS I, NOMBRE_INGRESOS N WHERE I.NOMBRE_ID=N.ID AND N.ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {

                Ingresos_EgresosDTO ingreso = new Ingresos_EgresosDTO(
                        rs.getLong(1),
                        rs.getTimestamp(5),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getDouble(4),
                        rs.getDouble(4),
                        rs.getDouble(4),
                        rs.getDouble(4),
                        rs.getDouble(4)
                );

                ingreso.setIngresos(new NombreIngresosDTO(
                        ingreso.getNombre_id(),
                        rs.getString(7),
                        rs.getDouble(7),
                        rs.getTimestamp(7),
                        rs.getLong(7)
                )
                );

                lista.add(ingreso);
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

    public long consultarUltimaCaja() {
        try {
            String sql = "SELECT MAX(ID) FROM INGRESOS_EGRESOS";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {

                return rs.getLong(1);
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

    public boolean editarCaja(long id, double caja) {
//

        try {
            String sql = "UPDATE INGRESOS_EGRESOS SET CAJA=? WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

            pst.setDouble(1, caja);
            pst.setLong(2, id);

            // if (pst.executeUpdate() == 1) {
            //long aux = cantidad - compraDTO.getCantidad();
            // return materialesDAO.editar(material, 0 + aux);
            return pst.executeUpdate() == 1;
            //}

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

//    @Override
//    public boolean editar(long id,double abonado,double liquidacion,double nominas,) {
//
//        Ingresos_EgresosDTO compraDTO = consultar(id);
//        NombreIngresosDTO materialDTO = INombreIngresos.consultar(id_n);
//
//        if (compraDTO == null || materialDTO == null) {
//            return false;
//        }
//
//        try {
//            String sql = "UPDATE INGRESOS_EGRESOS SET DESCRIPCION=?,CREDITO=?,"
//                    + "DEBITO=?,NOMBRE_ID=? WHERE ID=?";
//
//            con = conexion.getConnection();
//            pst = con.prepareStatement(sql);
//
//            pst.setString(1, descripcion);
//            pst.setDouble(2, credito);
//            pst.setDouble(3, debito);
//            pst.setLong(4, id_n);
//            pst.setLong(5, id);
//            
//           // if (pst.executeUpdate() == 1) {
//
//                //long aux = cantidad - compraDTO.getCantidad();
//                // return materialesDAO.editar(material, 0 + aux);
//                return pst.executeUpdate() == 1;
//            //}
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
//    @Override
//    public boolean eliminar(long id) {
//
////        Ingresos_EgresosDTO compraDTO = consultar(id);
//        NombreIngresosDTO materialDTO = null;
//
//        if (compraDTO != null) {
//            materialDTO = INombreIngresos.consultar(compraDTO.getNombre_id());
//        }
//
//        if (materialDTO == null) {
//            return false;
//        }
//
//        try {
//            String sql = "DELETE FROM INGRESOS_EGRESOS WHERE ID=?";
//
//            con = conexion.getConnection();
//            pst = con.prepareStatement(sql);
//
//            pst.setLong(1, id);
//
//            if (pst.executeUpdate() == 1) {
//                //  return materialesDAO.editar(materialDTO.getId(), 0 -0);
//                return true;
//            }
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
    @Override
    public boolean eliminar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Ingresos_EgresosDTO consultar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
