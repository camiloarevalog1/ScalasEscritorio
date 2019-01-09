package com.zapateria.main.dao;

import com.zapateria.main.dto.ClienteDTO;
import com.zapateria.main.dto.FacturaDTO;
import com.zapateria.main.dto.MaterialDTO;
import com.zapateria.main.dto.ProductoDTO;
import com.zapateria.main.dto.RegistroDTO;
import com.zapateria.main.dto.UsuarioDTO;
import com.zapateria.main.dto.VentaDTO;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class VentasDAO extends ConexionDAO implements com.zapateria.main.interfaces.IVentasDAO {

    private static final long serialVersionUID = 3876231646055255539L;

    public VentasDAO() {
        super();
    }

    public ArrayList<VentaDTO> listar(long id) {
        lista = new ArrayList();
        try {
            String sql = "SELECT DISTINCT F.ID,F.FECHA_REGISTRO,F.ESTADO,\n"
                    + "                   F.FORMA_DE_PAGO,F.CLIENTES_ID,F.USUARIOS_ID,F.CANTIDAD,\n"
                    + "                    F.MATERIAL_ID,F.COMENTARIO,F.PRECIO,F.NUMERO_CUOTAS,F.CUOTAS_PAGADAS,F.FECHA_ENTREGA,F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO,F.REGISTRO_ID\n"
                    + "                    ,C.DOCUMENTO,C.NOMBRES,\n"
                    + "                    C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,\n"
                    + "                    U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID,M.NOMBRE,M.PATRON\n"
                    + "                    FROM VENTAS2 F,CLIENTES C,USUARIOS U,MATERIALES M ,FACTURAS2 A WHERE F.CLIENTES_ID=C.ID\n"
                    + "                    AND F.USUARIOS_ID=U.ID AND F.MATERIAL_ID=M.ID AND  F.FACTURAS_ID=? ";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {

                VentaDTO factura = new VentaDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getLong(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getLong(8),
                        rs.getString(9),
                        rs.getDouble(10),
                        rs.getLong(11),
                        rs.getLong(12),
                        rs.getDate(13),
                        rs.getDouble(14),
                        rs.getDouble(15),
                        rs.getDouble(16),
                        rs.getLong(17)
                );

                factura.setCliente(new ClienteDTO(
                        factura.getId_cliente(),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getString(20),
                        rs.getString(21),
                        rs.getString(22),
                        rs.getTimestamp(23))
                );

                factura.setUsuario(new UsuarioDTO(
                        factura.getId_usuario(),
                        rs.getString(24),
                        rs.getString(25),
                        rs.getString(26),
                        rs.getString(27),
                        rs.getLong(28))
                );
                factura.setMaterial(new MaterialDTO(
                        factura.getId_material(),
                        rs.getString(29),
                        rs.getString(30))
                );

                lista.add(factura);
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
    
     public String convertirFechaString(Date date) {

        return new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(date);

    }

    public String convertirFechaStri(Date date) {

        return new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(date);

    }
      
      public ArrayList<VentaDTO> listarIngresos(Date FechaD, Date FechaH) {
        lista = new ArrayList();
        String fecha1 = "";
        String fecha2 = "";
        try {
            fecha1 = convertirFechaString(FechaD);
            fecha2 = convertirFechaStri(FechaH);
            String sql = "SELECT DISTINCT F.ID ,F.FECHA_REGISTRO,F.ESTADO,\n"
                    + "                    F.FORMA_DE_PAGO,F.CLIENTES_ID,F.USUARIOS_ID,F.CANTIDAD,\n"
                    + "                    F.MATERIAL_ID,F.COMENTARIO,F.PRECIO,F.NUMERO_CUOTAS,F.CUOTAS_PAGADAS,F.FECHA_ENTREGA,F.IVA,F.TOTAL_A_PAGAR,F.TOTAL_PAGADO\n"
                    + "                    ,C.DOCUMENTO,C.NOMBRES,\n"
                    + "                    C.APELLIDOS,C.DIRECCION,C.TELEFONO,C.FECHA_REGISTRO,\n"
                    + "           U.NOMBRES,U.APELLIDOS,U.DOCUMENTO,U.NOMBRE_USUARIO,U.ROLES_ID,M.NOMBRE,M.PATRON\n"
                    + "                    FROM VENTAS2 F INNER JOIN CLIENTES C ON C.ID =F.CLIENTES_ID,USUARIOS U,MATERIALES M WHERE "
                    + "                        F.USUARIOS_ID=U.ID AND F.MATERIAL_ID=M.ID AND F.FECHA_REGISTRO>=? AND F.FECHA_REGISTRO<=? AND F.FORMA_DE_PAGO='CONTADO' ORDER BY F.ID ASC";

            con = conexion.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, fecha1);
            pst.setString(2, fecha2);
            rs = pst.executeQuery();

            while (rs.next()) {

                VentaDTO factura = new VentaDTO(
                        rs.getLong(1),
                        rs.getTimestamp(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getLong(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        rs.getLong(8),
                        rs.getString(9),
                        rs.getDouble(10),
                        rs.getLong(11),
                        rs.getLong(12),
                        rs.getDate(13),
                        rs.getDouble(14),
                        rs.getDouble(15),
                        rs.getDouble(16)
                );

                factura.setCliente(new ClienteDTO(
                        factura.getId_cliente(),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getString(20),
                        rs.getString(21),
                        rs.getTimestamp(22))
                );

                factura.setUsuario(new UsuarioDTO(
                        factura.getId_usuario(),
                        rs.getString(23),
                        rs.getString(24),
                        rs.getString(25),
                        rs.getString(26),
                        rs.getLong(27))
                );
                factura.setMaterial(new MaterialDTO(
                        factura.getId_material(),
                        rs.getString(28),
                        rs.getString(29))
                );

                lista.add(factura);
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
