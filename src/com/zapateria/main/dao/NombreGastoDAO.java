/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.NombreGastoDTO;
import com.zapateria.main.dto.NombreIngresosDTO;
import com.zapateria.main.dto.UsuarioDTO;
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
 * @author DELL
 */
public class NombreGastoDAO extends ConexionDAO implements com.zapateria.main.interfaces.INombreGastoDAO {

    public NombreGastoDAO() {
        super();
    }
    
    public boolean registrar(String nombre) {
         Connection link = null;
        try {
            String sql = "INSERT INTO nombre_gastos (NOMBRE)\n"
                    + "VALUES(?)";

             link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, nombre);
            

            return str.executeUpdate() == 1;

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
        return false;
    }

    public NombreGastoDTO consultar(long id) {
         Connection link = null;
        try {
            String sql = "SELECT ID,NOMBRE\n"
                    + "FROM nombre_gastos WHERE ID=?";

            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);
            ResultSet rs = str.executeQuery();

            if (rs.next() && rs.absolute(1)) {
                return new NombreGastoDTO(
                        rs.getLong(1),
                        rs.getString(2)
                );
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
        return null;
    }

    public NombreGastoDTO consultarNombre(String nombre) {
        Connection link = null;
        try {
            String sql = "SELECT ID,NOMBRE\n"
                    + "FROM nombre_gastos WHERE NOMBRE=?";

             link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, nombre);
           ResultSet rs = str.executeQuery();

            if (rs.next() && rs.absolute(1)) {
                return new NombreGastoDTO(
                        rs.getLong(1),
                        rs.getString(2)
                );
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
        return null;
    }

    public ArrayList<NombreGastoDTO> listar() {
        lista = new ArrayList();
        Connection link = null;

        try {
            String sql = "SELECT ID,NOMBRE FROM nombre_gastos";

             link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
             ResultSet rs = str.executeQuery();

            while (rs.next()) {
                lista.add(new NombreGastoDTO(
                        rs.getLong(1),
                        rs.getString(2)
                        
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

    
    public boolean editar(long id, String nombre) {
        Connection link = null;
        try {
            String sql = "UPDATE nombre_gastos SET NOMBRE=? WHERE ID=?";

           
          link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, nombre);

            str.setLong(2, id);
            return str.executeUpdate() == 1;

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
        return false;
    }
    public boolean eliminar(long id) {
        Connection link = null;
        try {
            String sql = "DELETE FROM nombre_gastos WHERE ID=?";

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
                    Logger.getLogger(NombreGastoDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    
    
    
}
