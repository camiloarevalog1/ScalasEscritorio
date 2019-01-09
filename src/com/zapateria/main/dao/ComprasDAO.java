/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.CompraDTO;
import com.zapateria.main.dto.MaterialDTO;
import com.zapateria.main.dto.ProveedorDTO;
import com.zapateria.main.interfaces.IComprasDAO;
import com.zapateria.main.interfaces.IMaterialesDAO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class ComprasDAO extends ConexionDAO implements IComprasDAO {

    private static final long serialVersionUID = 4754745994785773158L;

    private final IMaterialesDAO materialesDAO = new MaterialesDAO();

    public ComprasDAO() {
        super();
    }
    

    @Override
    public boolean registrar(long cantidad, double valorUnitario, double valorTotal,
            long proveedor, long material) {

        MaterialDTO materialDTO = materialesDAO.consultar(material);

        if (materialDTO == null) {
            return false;
        }

        try {
            String sql = "INSERT INTO compras (CANTIDAD,VALOR_UNITARIO,VALOR_TOTAL,"
                    + "PROVEEDORES_ID,MATERIALES_ID) VALUES(?,?,?,?,?)";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, cantidad);
            pst.setDouble(2, valorUnitario);
            pst.setDouble(3, valorTotal);
            pst.setLong(4, proveedor);
            pst.setLong(5, material);
            if (pst.executeUpdate() == 1) {
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public CompraDTO consultar(long id) {
        try {
            String sql = "SELECT ID,CANTIDAD,VALOR_UNITARIO,VALOR_TOTAL,FECHA,"
                    + "PROVEEDORES_ID,MATERIALES_ID FROM compras WHERE ID=?";
            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();

            if (rs.next() && rs.absolute(1)) {

                return new CompraDTO(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getTimestamp(5),
                        rs.getLong(6),
                        rs.getLong(7)
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

    @Override
    public ArrayList<CompraDTO> listar() {

        lista = new ArrayList();

        try {
            String sql = "SELECT C.ID,C.CANTIDAD,C.VALOR_UNITARIO,C.VALOR_TOTAL,"
                    + "C.FECHA,C.PROVEEDORES_ID,C.MATERIALES_ID,C.ABONADO,P.NOMBRE,P.NIT,"
                    + "P.DIRECCION,P.TELEFONO,P.FECHA,M.NOMBRE,M.PATRON FROM compras C,proveedores P,"
                    + "materiales M WHERE C.PROVEEDORES_ID=P.ID AND C.MATERIALES_ID=M.ID";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                CompraDTO compra = new CompraDTO(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getTimestamp(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getDouble(8));

                compra.setProveedorDTO(new ProveedorDTO(
                        compra.getProveedor(),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getTimestamp(13))
                );

                compra.setMaterialDTO(new MaterialDTO(
                        compra.getMaterial(),
                        rs.getString(14),
                        rs.getString(15))
                );

                lista.add(compra);
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
    public boolean editar(long id, long cantidad, double valorUnitario,
            double valorTotal, long proveedor, long material) {

        CompraDTO compraDTO = consultar(id);
        MaterialDTO materialDTO = materialesDAO.consultar(material);

        if (compraDTO == null || materialDTO == null) {
            return false;
        }

        try {
            String sql = "UPDATE compras SET CANTIDAD=?,VALOR_UNITARIO=?,"
                    + "VALOR_TOTAL=?,PROVEEDORES_ID=?,MATERIALES_ID=? WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

            pst.setLong(1, cantidad);
            pst.setDouble(2, valorUnitario);
            pst.setDouble(3, valorTotal);
            pst.setLong(4, proveedor);
            pst.setLong(5, material);
            pst.setLong(6, id);

            if (pst.executeUpdate() == 1) {

                long aux = cantidad - compraDTO.getCantidad();
                // return materialesDAO.editar(material, 0 + aux);
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean eliminar(long id) {

        CompraDTO compraDTO = consultar(id);
        MaterialDTO materialDTO = null;

        if (compraDTO != null) {
            materialDTO = materialesDAO.consultar(compraDTO.getMaterial());
        }

        if (materialDTO == null) {
            return false;
        }

        try {
            String sql = "DELETE FROM compras WHERE ID=?";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);

            pst.setLong(1, id);

            if (pst.executeUpdate() == 1) {
                //  return materialesDAO.editar(materialDTO.getId(), 0 -0);
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }
    public ArrayList<CompraDTO> listarId(long id) {

        lista = new ArrayList();

        try {
            String sql = "SELECT C.ID,C.CANTIDAD,C.VALOR_UNITARIO,C.VALOR_TOTAL,"
                    + "C.FECHA,C.PROVEEDORES_ID,C.MATERIALES_ID,C.ABONADO,P.NOMBRE,P.NIT,"
                    + "P.DIRECCION,P.TELEFONO,P.FECHA,M.NOMBRE,M.PATRON FROM compras C,proveedores P,"
                    + "materiales M WHERE C.PROVEEDORES_ID=P.ID AND C.MATERIALES_ID=M.ID AND C.PROVEEDORES_ID=? ";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {

                CompraDTO compra = new CompraDTO(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getTimestamp(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getDouble(8));

                compra.setProveedorDTO(new ProveedorDTO(
                        compra.getProveedor(),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getTimestamp(13))
                );

                compra.setMaterialDTO(new MaterialDTO(
                        compra.getMaterial(),
                        rs.getString(14),
                        rs.getString(15))
                );

                lista.add(compra);
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
}
