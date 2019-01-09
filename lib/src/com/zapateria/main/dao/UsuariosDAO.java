/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.RolDTO;
import com.zapateria.main.dto.SesionDTO;
import com.zapateria.main.dto.UsuarioDTO;
import com.zapateria.main.dto.VistaDTO;
import com.zapateria.main.interfaces.IVistasDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import com.zapateria.main.interfaces.IUsuariosDAO;

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

    @Override
    public SesionDTO iniciarSesion(String nombreUsuario, String contrasena) {

        SesionDTO sUsuario = null;
        ArrayList<VistaDTO> vistas = null;

        String sql = "SELECT ID,NOMBRES,APELLIDOS,DOCUMENTO,NOMBRE_USUARIO,ROLES_ID\n"
                + "FROM USUARIOS WHERE NOMBRE_USUARIO=? AND CONTRASENA=HASH('SHA256',STRINGTOUTF8(?),1)";

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
        try {
            String sql = "SELECT ID,NOMBRES,APELLIDOS,DOCUMENTO,NOMBRE_USUARIO,"
                    + "CONTRASENA,ROLES_ID FROM USUARIOS WHERE ID=? LIMIT 1";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
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
        try {
            String sql = "SELECT U.ID,NOMBRES,APELLIDOS,DOCUMENTO,NOMBRE_USUARIO,"
                    + "ROLES_ID,R.ROL FROM USUARIOS U, ROLES R WHERE U.ROLES_ID=R.ID";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
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
                        rs.getString(7))
                );

                lista.add(usuario);
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
    public boolean registrar(String nombres, String apellidos, String documento, String nombreUsuario, String contrasena, long rol) {
        try {
            String sql = "INSERT INTO USUARIOS (NOMBRES,APELLIDOS,DOCUMENTO,NOMBRE_USUARIO,CONTRASENA,ROLES_ID)"
                    + "VALUES(?,?,?,?,?,?)";
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
            String nombreUsuario, long rol) {
        try {
            String sql = "UPDATE USUARIOS SET NOMBRES=?,APELLIDOS=?,DOCUMENTO=?,"
                    + "NOMBRE_USUARIO=?,ROLES_ID=? WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, nombres);
            pst.setString(2, apellidos);
            pst.setString(3, documento);
            pst.setString(4, nombreUsuario);
            pst.setLong(5, rol);
            pst.setLong(6, id);
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
            String sql = "UPDATE USUARIOS SET CONTRASENA=HASH('SHA256',STRINGTOUTF8(?),1) WHERE ID=?";
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
            String sql = "DELETE FROM USUARIOS WHERE ID=?";
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
}
