/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.ClienteDTO;
import com.zapateria.main.dto.NominaDTO;
import com.zapateria.main.dto.RolDTO;
import com.zapateria.main.dto.SesionDTO;
import com.zapateria.main.dto.UsuarioDTO;
import com.zapateria.main.dto.VistaDTO;
import com.zapateria.main.interfaces.IVistasDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import com.zapateria.main.interfaces.IUsuariosDAO;
import com.zapateria.main.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class UsuariosDAO extends ConexionDAO implements IUsuariosDAO {

    private static final long serialVersionUID = -2421338744313463847L;

    private IVistasDAO vistasDAO;

    public UsuariosDAO() {
        super();
    }

     public SesionDTO iniciarSesion(String nombreUsuario, String contrasena) {
         Connection link = null;
        SesionDTO sUsuario = null;
        ArrayList<VistaDTO> vistas = null;
        System.out.println(contrasena);

//        String sql = "SELECT ID,NOMBRES,APELLIDOS,DOCUMENTO,NOMBRE_USUARIO,ROLES_ID\n"
//                + "FROM USUARIOS WHERE NOMBRE_USUARIO=? AND CONTRASENA=?";
 String sql = "SELECT ID,NOMBRES,APELLIDOS,DOCUMENTO,NOMBRE_USUARIO,ROLES_ID\n"
                + "FROM usuarios WHERE NOMBRE_USUARIO=? AND CONTRASENA=?";

        try {
               link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setString(1, nombreUsuario);
            str.setString(2, contrasena);
            ResultSet rs = str.executeQuery();
            
            

            if (rs.next() && rs.absolute(1)) {
                sUsuario = new SesionDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getLong(6)
                );

                vistasDAO = new VistasDAO();

                vistas = vistasDAO.listar(sUsuario.getRol());

                if (vistas != null) {
                    sUsuario.setVistas(vistas);
                } else {
                    return null;
                }
            }
            return sUsuario;

        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
           if (link != null) {
                try {
                    link.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDTO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
    
    //@Override
    public SesionDTO iniciarSesion2(String nombreUsuario, String contrasena) {

        SesionDTO sUsuario = null;
        ArrayList<VistaDTO> vistas = null;
        System.out.println(contrasena);

//        String sql = "SELECT ID,NOMBRES,APELLIDOS,DOCUMENTO,NOMBRE_USUARIO,ROLES_ID\n"
//                + "FROM USUARIOS WHERE NOMBRE_USUARIO=? AND CONTRASENA=?";
 String sql = "SELECT ID,NOMBRES,APELLIDOS,DOCUMENTO,NOMBRE_USUARIO,ROLES_ID\n"
                + "FROM usuarios WHERE NOMBRE_USUARIO=? AND CONTRASENA=?";

        try {
            
            con = conexion.getConnection();
            
            pst = con.prepareStatement(sql);
            pst.setString(1, nombreUsuario);
            pst.setString(2, contrasena);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {
                sUsuario = new SesionDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getLong(6)
                );

                vistasDAO = new VistasDAO();

                vistas = vistasDAO.listar(sUsuario.getRol());

                if (vistas != null) {
                    sUsuario.setVistas(vistas);
                } else {
                    return null;
                }
            }
            return sUsuario;

        } catch (SQLException ex) {
            System.err.println(ex);
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
                System.err.println(ex);
            }
        }
        return null;
    }

    @Override
    public UsuarioDTO consultar(long id) {
         Connection link = null;
        try {
            String sql = "SELECT U.ID,U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,"
                    + "U.ROLES_ID,U.TELEFONO,U.DIRECCION,U.FECHA_INGRESO FROM usuarios U WHERE U.ID=? LIMIT 1";
             link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
            str.setLong(1, id);
           ResultSet rs = str.executeQuery();
            if (rs.next()) {
                return new UsuarioDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getLong(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getDate(9));
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
        return null;
    }
    
    public boolean clave( String password) {
        try {
            String sql = "SELECT ID FROM clave WHERE PASSWORD=?";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            
            pst.setString(1, password);
            rs = pst.executeQuery();
            if (rs.next() && rs.absolute(1)) {

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

    public boolean validar(String usuario, String contrasena) {
        try {
            String sql = "SELECT ID,NOMBRES,APELLIDOS,DOCUMENTO,NOMBRE_USUARIO,ROLES_ID\n"
                    + "FROM usuarios WHERE NOMBRE_USUARIO=? AND CONTRASENA=HASH('SHA256',STRINGTOUTF8(?),1)";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, usuario);
            pst.setString(2, contrasena);
            rs = pst.executeQuery();
            if (rs.next() && rs.absolute(1)) {

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

    public UsuarioDTO buscarFechaIngreso(long  cedula) {
        try {
            String sql = "SELECT ID,NOMBRES,APELLIDOS,DOCUMENTO,NOMBRE_USUARIO,"
                    + "ROLES_ID , FECHA_INGRESO FROM usuarios WHERE ID=? ";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, cedula);
            rs = pst.executeQuery();
            if (rs.next()) {
                return new UsuarioDTO(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getDate(7));
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

    @Override
    public UsuarioDTO buscarUsuarioCedula(String cedula) {
        try {
            String sql = "SELECT U.ID,U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,"
                    + "U.ROLES_ID,U.TELEFONO,U.DIRECCION,U.FECHA_INGRESO FROM usuarios U WHERE DOCUMENTO=? ";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, cedula);
            rs = pst.executeQuery();
            if (rs.next()) {
                return new UsuarioDTO(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6));
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

    @Override
    public ArrayList<UsuarioDTO> listar() {
        lista = new ArrayList();
          Connection link = null;
        try {
            String sql = "SELECT U.ID,U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,"
                    + "U.ROLES_ID,U.TELEFONO,U.DIRECCION,U.FECHA_INGRESO,R.ROL FROM usuarios U, roles R WHERE U.ROLES_ID=R.ID";

            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
           ResultSet rs = str.executeQuery();
            while (rs.next()) {

                UsuarioDTO usuario = new UsuarioDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getLong(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getDate(9)
                );

                usuario.setRolDTO(new RolDTO(
                        usuario.getRol(),
                        rs.getString(10))
                );

                lista.add(usuario);
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
        return lista;
    }

    @Override
    public ArrayList<UsuarioDTO> listarUsuariosNomina() {
        lista = new ArrayList();
         Connection link = null;
        try {
            String sql = "SELECT U.ID,NOMBRES,APELLIDOS,DOCUMENTO,NOMBRE_USUARIO,"
                    + "ROLES_ID,R.ROL,R.VALOR FROM usuarios U, roles R WHERE U.ROLES_ID=R.ID";

           
            link = Conexion.getInstance().dataSource.getConnection();
          
              PreparedStatement str;
            str = link.prepareStatement(sql);
          ResultSet rs = str.executeQuery();
            while (rs.next()) {

                UsuarioDTO usuario = new UsuarioDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getLong(6)
                );

                usuario.setRolDTO(new RolDTO(
                        usuario.getRol(),
                        rs.getString(7),
                        rs.getDouble(8))
                );

                lista.add(usuario);
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
        return lista;
    }

    @Override
    public boolean registrar(String nombres, String apellidos, String documento, String nombreUsuario, String contrasena, long rol,String telefono,String direccion) {
        try {

            String sql = "INSERT INTO usuarios (NOMBRES,APELLIDOS,DOCUMENTO,NOMBRE_USUARIO,CONTRASENA,ROLES_ID,TELEFONO,DIRECCION)"
                    + "VALUES(?,?,?,?,HASH('SHA256',STRINGTOUTF8(?),1),?,?,?)";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, nombres);
            pst.setString(2, apellidos);
            pst.setString(3, documento);
            pst.setString(4, nombreUsuario);
            pst.setString(5, contrasena);
            pst.setLong(6, rol);
            pst.setString(7, telefono);
            pst.setString(8, direccion);
            return pst.executeUpdate() > 0;

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

    public boolean registrar2(String nombres, String apellidos, String documento, String nombreUsuario, String contrasena, long rol) {
        try {

            String sql = "INSERT INTO usuarios (NOMBRES,APELLIDOS,DOCUMENTO,NOMBRE_USUARIO,CONTRASENA,ROLES_ID)"
                    + "VALUES(?,?,?,?,HASH('SHA256',STRINGTOUTF8(?),1),?)";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, nombres);
            pst.setString(2, apellidos);
            pst.setString(3, documento);
            pst.setString(4, nombreUsuario);
            pst.setString(5, contrasena);
            pst.setLong(6, rol);
            return pst.executeUpdate() > 0;

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

    @Override
    public boolean editar(long id, String nombres, String apellidos, String documento,
            String nombreUsuario, long rol,String tel,String dir) {
        try {
            String sql = "UPDATE usuarios SET NOMBRES=?,APELLIDOS=?,DOCUMENTO=?,"
                    + "NOMBRE_USUARIO=?,ROLES_ID=?, TELEFONO=? , DIRECCION=? WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, nombres);
            pst.setString(2, apellidos);
            pst.setString(3, documento);
            pst.setString(4, nombreUsuario);
            pst.setLong(5, rol);
            pst.setString(6,tel );
            pst.setString(7, dir);
            pst.setLong(8, id);
            return pst.executeUpdate() > 0;

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

    @Override
    public boolean editar(long id, String contrasena) {
        try {
            String sql = "UPDATE usuarios SET CONTRASENA=HASH('SHA256',STRINGTOUTF8(?),1) WHERE ID=?";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, contrasena);
            pst.setLong(2, id);
            return pst.executeUpdate() > 0;

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

    @Override
    public boolean eliminar(long id) {
        try {
            String sql = "DELETE FROM usuarios WHERE ID=?";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            return pst.executeUpdate() == 1;
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

    @Override
    public boolean validarCedula(String cedula) {
        try {
            String sql = "SELECT DOCUMENTO FROM usuarios WHERE DOCUMENTO=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, cedula);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {

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

    public boolean validarUsuario(String usuario) {
        try {
            String sql = "SELECT NOMBRE_USUARIO FROM usuarios WHERE NOMBRE_USUARIO=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, usuario);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {

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

    public ArrayList<UsuarioDTO> buscar(String busqueda) {

        lista = new ArrayList();

        try {
            String sql = "SELECT C.ID,C.DOCUMENTO,C.NOMBRES,C.APELLIDOS,C.ROLES_ID FROM FT_SEARCH_DATA(?, 0, 0) FT,"
                    + "USUARIOS C WHERE FT.TABLE='USUARIOS' AND C.ID=FT.KEYS[0]";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, busqueda);

            rs = pst.executeQuery();

            while (rs.next()) {

                lista.add(new UsuarioDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getLong(5))
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
        return lista;
    }
    
    public ArrayList<UsuarioDTO> buscarNombre(String busqueda) {

        lista = new ArrayList();

        try {
            String sql = "SELECT * FROM usuarios WHERE nombres like '%DIEGO%' ";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
//            pst.setString(1, busqueda);

            rs = pst.executeQuery();

            while (rs.next()) {

                lista.add(new UsuarioDTO(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getLong(5))
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
        return lista;
    }
    
    public long consultarUltimoUsuario() {
        try {
            String sql = "SELECT MAX(ID) FROM usuarios";

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
    
    
}
