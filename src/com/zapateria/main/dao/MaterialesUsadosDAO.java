/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.MaterialDTO;
import com.zapateria.main.dto.MaterialUsadoDTO;
import com.zapateria.main.interfaces.IMaterialesDAO;
import java.util.ArrayList;
import com.zapateria.main.interfaces.IMaterialesUsadosDAO;
import java.sql.SQLException;

/**
 *
 * @author Diego
 */
public class MaterialesUsadosDAO extends ConexionDAO implements IMaterialesUsadosDAO {

    private static final long serialVersionUID = 3486569985214507941L;

    private IMaterialesDAO materialesDAO = null;

    public MaterialesUsadosDAO() {
        super();
    }

    @Override
    public boolean registrar(long cantidad, long material) {
        materialesDAO = new MaterialesDAO();

        MaterialDTO materialDTO = materialesDAO.consultar(material);

        if (materialDTO == null) {
            return false;
        }

        try {
            String sql = "INSERT INTO MATERIALES_USADOS (CANTIDAD,MATERIALES_ID) VALUES(?,?)";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, cantidad);
            pst.setLong(2, material);

            if (pst.executeUpdate() == 1) {
              //  return materialesDAO.editar(material, 0- 0);
              return true;
            }

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
                materialesDAO = null;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public MaterialUsadoDTO consultar(long id) {

        try {
            String sql = "SELECT MU.ID,MU.CANTIDAD,MU.FECHA,MU.MATERIALES_ID,"
                    + "M.NOMBRE,M.PATRON FROM MATERIALES_USADOS MU,"
                    + "MATERIALES M WHERE MU.MATERIALES_ID=M.ID AND MU.ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();
            if (rs.next() && rs.absolute(1)) {
                MaterialUsadoDTO materialUsado = new MaterialUsadoDTO(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getTimestamp(3),
                        rs.getLong(4)
                );

                materialUsado.setMaterialDTO(new MaterialDTO(
                        materialUsado.getMaterial(),
                        rs.getString(5),
                        rs.getString(6))
                );
                return materialUsado;
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
    public ArrayList<MaterialUsadoDTO> listar() {
        lista = new ArrayList<>();
        try {
            String sql = "SELECT MU.ID,MU.CANTIDAD,MU.FECHA,MU.MATERIALES_ID,"
                    + "M.NOMBRE,M.PATRON FROM MATERIALES_USADOS MU,"
                    + "MATERIALES M WHERE MU.MATERIALES_ID=M.ID";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                MaterialUsadoDTO materialUsado = new MaterialUsadoDTO(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getTimestamp(3),
                        rs.getLong(4)
                );

                materialUsado.setMaterialDTO(new MaterialDTO(
                        materialUsado.getMaterial(),
                        rs.getString(5),
                        rs.getString(6))
                );

                lista.add(materialUsado);
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
    public boolean eliminar(long id) {
        try {
            String sql = "DELETE FROM MATERIALES_USADOS WHERE ID=?";

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
