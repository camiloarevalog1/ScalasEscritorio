/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.MaterialDTO;
import com.zapateria.main.dto.NombreIngresosDTO;
import com.zapateria.main.interfaces.IMaterialesDAO;
import com.zapateria.main.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego
 */
public class MaterialesDAO extends ConexionDAO implements IMaterialesDAO {

    private static final long serialVersionUID = 1664119091711995392L;

    public MaterialesDAO() {
        super();
    }

    @Override
    public boolean registrar(String nombre, String patron) {
Connection link = null;
        try {
            String sql = "INSERT INTO materiales (NOMBRE,PATRON) VALUES(?,?)";

            link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);

         
            str.setString(1, nombre);
            str.setString(2, patron);

            return str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MaterialesDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    @Override
    public MaterialDTO consultar(long id) {
       Connection link = null;
        try {
            String sql = "SELECT ID,NOMBRE,PATRON FROM materiales WHERE ID=?";

            link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);

            
            str.setLong(1, id);
           ResultSet rs = str.executeQuery();
            if (rs.next() && rs.absolute(1)) {
                return new MaterialDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3)
                );

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
          if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MaterialesDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
    
      public MaterialDTO consultarNombreM(String nombre) {
              Connection link = null;
        try {
            String sql = "SELECT ID,NOMBRE,PATRON FROM materiales WHERE NOMBRE=?";

           link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);

            
            str.setString(1, nombre);
            ResultSet rs = str.executeQuery();
            
            
            if (rs.next() && rs.absolute(1)) {
                return new MaterialDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3)
                );

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MaterialesDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public ArrayList<MaterialDTO> listar() {
        lista = new ArrayList();
        Connection link = null;
        try {
            String sql = "SELECT ID,NOMBRE,PATRON FROM materiales";
            link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);

            ResultSet rs = str.executeQuery();

            while (rs.next()) {
                lista.add(new MaterialDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3)
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MaterialesDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }
    
    


    @Override
    public boolean editar(long id, String nombre, String patron) {
        Connection link = null;
        try {
            String sql = "UPDATE materiales SET NOMBRE=?,PATRON=? WHERE ID=?";

             link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, nombre);
            str.setString(2, patron);
            str.setLong(3, id);
            
            return str.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MaterialesDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

//    @Override
//    public boolean editar(long id, long cantidad) {
//        try {
//            String sql = "UPDATE MATERIALES SET CANTIDAD=? WHERE ID=?";
//            con = conexion.getConnection();
//            pst = con.prepareStatement(sql);
//            pst.setLong(1, cantidad);
//            pst.setLong(2, id);
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
    @Override
    public boolean eliminar(long id) {
        Connection link = null;
        try {
            String sql = "DELETE FROM materiales WHERE ID=?";

             link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);

            return pst.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
           if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MaterialesDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    @Override
    public ArrayList<MaterialDTO> buscar(String busqueda) {
        lista = new ArrayList();
        Connection link = null;
        try {
            String sql = "SELECT P.ID,P.NOMBRE,P.PATRON FROM materiales P WHERE P.NOMBRE=?";
            System.out.println(busqueda);
            link = Conexion.getInstance().dataSource.getConnection();

            PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, busqueda);
ResultSet rs = str.executeQuery();
           

            while (rs.next()) {
                System.out.println(rs.getInt(1));
                System.out.println(rs.getString(2));
                lista.add(new MaterialDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3))
                );

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MaterialesDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lista;
    }

}
