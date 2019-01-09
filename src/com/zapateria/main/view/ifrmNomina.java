/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.view;

import com.zapateria.main.dao.Abono_PrestamoDAO;
import com.zapateria.main.dto.AbonoDTO;
import com.zapateria.main.dto.Abono_PrestamoDTO;
import com.zapateria.main.dto.ClienteDTO;
import com.zapateria.main.dto.FacturaDTO;
import com.zapateria.main.dto.LiquidacionDTO;
import com.zapateria.main.dto.MaterialDTO;
import com.zapateria.main.dto.NominaDTO;
import com.zapateria.main.dto.PrestamoDTO;
import com.zapateria.main.dto.ProductoDTO;
import com.zapateria.main.dto.RolDTO;
import com.zapateria.main.dto.UsuarioDTO;

import com.zapateria.main.interfaces.IAbonoPrestamoDAO;
import com.zapateria.main.util.BuscadorCarpeta;
import com.zapateria.main.util.Conexion;
import static com.zapateria.main.view.MarcoInternoPersonalizado.REGEX_ENTERO_POSITIVO;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Diego
 */
public class ifrmNomina extends MarcoInternoPersonalizado {

    private static final long serialVersionUID = -4521190713135285108L;

    /**
     * Creates new form ifrmNomina
     */
    Date fechaDesde;
    int dias;
    private static final String NOMINA = "NOMINA", LIQUIDACION = "LIQUIDACION",
            ADMINNOMINAS = "ADMINNOMINAS", ADELANTOS = "ADELANTOS ";

    private final static String[] NOMBRES_COLUMNAS = {"ID", "NOMBRE", "APELLIDOS", "CEDULA", "NOMBRE USUARIO", "ROL", "VALOR HORA", "DIAS TRABAJADOS", "DIAS AUSENTES", "HORAS TRABAJADAS",
        "TOTAL PAGAR"};

    private DefaultTableModel dtblNomina, dtblNomina2;

    private UsuarioDTO UsuarioDTO = new UsuarioDTO();

    public ifrmNomina() {
        initComponents();
        //llenarTablaUsuarios();
        //llenarTablaUsuariosLiquidacion();
        PanelDatosN.setVisible(false);
        PanelDatosU.setVisible(false);
//        llenarComboMaterial();
        System.out.println("entro a nomina");
        btnGuardarPago.setVisible(false);
        comboBuscarEmpleado.setVisible(true);
//        comboBuscarEmpleado.setEditable(true);
        comboBuscarEmpleado.setEnabled(true);

        comboBuscarEmpleado1.setVisible(true);
//        comboBuscarEmpleado1.setEditable(true);
        comboBuscarEmpleado1.setEnabled(true);

        comboBuscarEmpleado2.setVisible(true);
//        comboBuscarEmpleado2.setEditable(true);
        comboBuscarEmpleado2.setEnabled(true);

        comboBuscarEmpleado3.setVisible(true);
        comboBuscarEmpleado3.setEnabled(true);
//        comboBuscarEmpleado2.setEditable(true);
        comboBuscarEmpleado4.setVisible(true);
        comboBuscarEmpleado4.setEnabled(true);

        comboBuscarEmpleado5.setVisible(true);
        comboBuscarEmpleado5.setEnabled(true);

        comboBuscarEmpleado6.setVisible(true);
        comboBuscarEmpleado6.setEnabled(true);

        comboBuscarEmpleado7.setVisible(true);
        comboBuscarEmpleado7.setEnabled(true);

//        comboBuscarCliente.setVisible(true);
//        comboBuscarCliente.setEditable(true);
//        comboBuscarCliente.setEnabled(true);
    }

    private void crearComboEditable(JComboBox<Object> combo, String tabla) {

        combo.setEditable(true);
        JTextField editor = (JTextField) combo.getEditor().getEditorComponent();
        editor.setText("");

        editor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getComponent();
                String texto = textField.getText();

                switch (tabla) {
                    case "PRODUCTOS":
                        editarCombo(combo, servicio.buscar(texto).toArray());
                        System.out.println("si entro");
                        break;
                    case "USUARIOS":
                        //Busca el cliente por nombre
                        editarCombo(combo, servicio.buscarUsuario(texto).toArray());
                        System.out.println(texto);
                        break;
                    case "FACTURAS":
                        editarCombo(combo, servicio.buscarFactura(texto).toArray());
                        break;
                    default:
                        break;
                }

            }
        });

        combo.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Object seleccion = combo.getSelectedItem();
                if (seleccion == null) {
                    return;
                }
                switch (tabla) {
                    case "PRODUCTOS":

                        MaterialDTO producto = null;

                        producto = (MaterialDTO) seleccion;

                        //mostrarMensaje(producto.getPrecioUnitario()+"");
                        break;
                    case "USUARIOS":

                        UsuarioDTO cliente = (UsuarioDTO) seleccion;
//                        txtCedulaEmpleado.setText(cliente.getDocumento());
//                        buscarEmpleado(cliente.getDocumento());
//                        textCodigoCliente.setText(cliente.getId() + "");
//                       txtCedulaEmpleado.setText(cliente.getDocumento() + "");
//                        txtNombreEmpleado.setText(cliente.getNombres());
//                        textApellidos.setText(cliente.getApellidos());
//                        textTelefono.setText(cliente.getTelefono() + "");
//                        textDireccion.setText(cliente.getDireccion());

                        break;

                    case "FACTURAS":
                        FacturaDTO factura = (FacturaDTO) seleccion;
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void editarCombo(JComboBox<Object> combo, Object[] array) {

        if (array.length <= 0) {
            combo.setPopupVisible(false);
            return;
        } else {
            combo.removeAllItems();

            for (Object o : array) {
                combo.addItem(o);
            }
            combo.setPopupVisible(true);
        }
    }

    public void actualizarTablaUsuarios() {
        tblNomina.removeAll();
        dtblNomina = null;
        llenarTablaUsuarios();

    }

    public void llenarComboMaterial() {

        comboBuscarEmpleado.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<UsuarioDTO> listar = servicio.listarUsuarios();

        for (UsuarioDTO n : listar) {
            comboBuscarEmpleado.addItem(n.getId() + "-" + n.getNombres());
        }
    }

    public void llenarComboMaterial2() {

        comboBuscarEmpleado1.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<UsuarioDTO> listar = servicio.listarUsuarios();

        for (UsuarioDTO n : listar) {
            comboBuscarEmpleado1.addItem(n.getId() + "-" + n.getNombres());
        }
    }

    public void llenarComboMaterial3() {

        comboBuscarEmpleado2.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<UsuarioDTO> listar = servicio.listarUsuarios();

        for (UsuarioDTO n : listar) {
            comboBuscarEmpleado2.addItem(n.getId() + "-" + n.getNombres());
        }
    }

    public void llenarComboMaterial4() {

        comboBuscarEmpleado3.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<UsuarioDTO> listar = servicio.listarUsuarios();

        for (UsuarioDTO n : listar) {
            comboBuscarEmpleado3.addItem(n.getId() + "-" + n.getNombres());
        }
    }

    public void llenarComboMaterial5() {

        comboBuscarEmpleado4.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<UsuarioDTO> listar = servicio.listarUsuarios();

        for (UsuarioDTO n : listar) {
            comboBuscarEmpleado4.addItem(n.getId() + "-" + n.getNombres());
        }
    }

    public void llenarComboMaterial6() {

        comboBuscarEmpleado5.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<UsuarioDTO> listar = servicio.listarUsuarios();

        for (UsuarioDTO n : listar) {
            comboBuscarEmpleado5.addItem(n.getId() + "-" + n.getNombres());
        }
    }

    public void llenarComboMaterial7() {

        comboBuscarEmpleado6.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<UsuarioDTO> listar = servicio.listarUsuarios();

        for (UsuarioDTO n : listar) {
            comboBuscarEmpleado6.addItem(n.getId() + "-" + n.getNombres());
        }
    }

    public void llenarComboMaterial8() {

        comboBuscarEmpleado7.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<UsuarioDTO> listar = servicio.listarUsuarios();

        for (UsuarioDTO n : listar) {
            comboBuscarEmpleado7.addItem(n.getId() + "-" + n.getNombres());
        }
    }

    public void actualizarTablaLiquidacion() {
        tblLiquidacion1.removeAll();
        dtblNomina = null;
        llenarTablaUsuariosLiquidacion();

    }

    private void buscarFechaNomina(Date FechaD, Date FechaH) {

        final String[] columnas = {"CODIGO NOMINA", "NOMBRE", "APELLIDOS", "CEDULA", "NOMBRE USUARIO", "ROL", "NOMINA DESDE", "NOMINA HASTA", "VALOR HORA", "DIAS TRABAJADOS", "HORAS TRABAJADAS",
            "HORAS EXTRAS", "ABONADO", "TOTAL PAGADO"};
        //configurando los eventos de la tabla
        dtblNomina = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2;
            }
        };
        Object[] object = new Object[14];
        ArrayList<NominaDTO> n = servicio.listarFechaNomi(FechaD, FechaH);
        System.out.println(n.size());
        for (NominaDTO no : n) {
            UsuarioDTO u = servicio.consultarUsuario(no.getUsuario());
            RolDTO rol = servicio.consultarRol(u.getRol());
            object[0] = no.getId();
            object[1] = u.getNombres();
            object[2] = u.getApellidos();
            object[3] = u.getDocumento();
            object[4] = u.getNombreUsuario();
            object[5] = rol.getRol();
            object[6] = no.getFecha_inicio();
            object[7] = no.getFecha_pago();
            object[8] = formateador.format(no.getSalario());
            object[9] = no.getDias_trabajados();
            object[10] = no.getHorasTrabajadas();
            object[11] = no.getHorasE();
            object[12] = formateador.format(no.getAbono());
            object[13] = formateador.format(no.getPago_total());

            dtblNomina.addRow(object);

        }
        tblBuscarNominaFecha.setModel(dtblNomina);

        dtblNomina.addTableModelListener((TableModelEvent e) -> {

            long id;
        });
    }

    private void buscarFechaLiquidaciones(Date FechaD, Date FechaH) {

        final String[] columnas = {"CODIGO LIQUIDACION", "NOMBRE", "FECHA INGRESO", "FECHA RETIRO", "DIAS TRABAJADOS", "SUELDO BASICO ", "CESANTIAS", "INTERESES CESANTIAS",
            "PRIMA", "VACACIONES", "BONIFICACION", "TOTAL LIQUIDACION"};
        //configurando los eventos de la tabla
        dtblNomina = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2;
            }
        };
        Object[] object = new Object[14];
        ArrayList<LiquidacionDTO> n = servicio.listarFechaLiquidacion(FechaD, FechaH);
        System.out.println(n.size());
        for (LiquidacionDTO no : n) {
            UsuarioDTO u = servicio.consultarUsuario(no.getUsuario());
//            RolDTO rol = servicio.consultarRol(u.getRol());
            object[0] = no.getId();
            object[1] = u.getNombres();
            object[2] = no.getFecha_entrada();
            object[3] = no.getFecha_salida();
            object[4] = no.getDias_trabajados();
            object[5] = formateador.format(no.getSueldo_basico());
            object[6] = formateador.format(no.getCesantias());
            object[7] = formateador.format(no.getIntereses_cesantias());
            object[8] = formateador.format(no.getPrima_servicio_());
            object[9] = formateador.format(no.getVacaciones());
            object[10] = formateador.format(no.getBonificacion());
            object[11] = formateador.format(no.getValor_liquidacion());

            dtblNomina.addRow(object);

        }
        tblBuscarNominaFecha1.setModel(dtblNomina);

        dtblNomina.addTableModelListener((TableModelEvent e) -> {

            long id;
        });
    }

    //Busca las facturas en una fecha correspondiente
    public static Long convertToLong(Object o) {
        String stringToConvert = String.valueOf(o);
        Long convertedLong = Long.parseLong(stringToConvert);
        return convertedLong;

    }

    public static Integer convertToInt(Object o) {
        String stringToConvert = String.valueOf(o);
        Integer convertedInt = Integer.parseInt(stringToConvert);
        return convertedInt;

    }

    public static Double convertToDouble(Object o) {
        String stringToConvert = String.valueOf(o);
        Double convertedInt = Double.parseDouble(stringToConvert);
        return convertedInt;
    }

    public void llenarValorPagar() {
        int filas = tblNomina.getRowCount();
        int count = 0;
        while (count < filas) {
            Object objid = tblNomina.getValueAt(count, 0);
            Object diasTrabajados = tblNomina.getValueAt(count, 7);
            Object diasFaltantes = tblNomina.getValueAt(count, 8);
            //Object valor=tblNomina.getValueAt(dias, dias);
            long id = convertToLong(objid);
            int diastrabajar = convertToInt(diasTrabajados);
            // int diasFal=convertToInt(diasFaltantes);
            double valor = 0.0;
            UsuarioDTO usuario = servicio.consultarUsuario(1);
            RolDTO rol = servicio.consultarRol(usuario.getRol());

            valor = diastrabajar * rol.getValor();
            System.out.println(valor);

        }

    }

    private void llenarTablaUsuariosLiquidacion() {

        final String[] COLUMNAS = {"ID", "NOMBRE", "FECHA INGRESO", "FECHA RETIRO", "DIAS TRABAJADOS", "SUELDO BASICO ", "CESANTIAS", "INTERESES CESANTIAS",
            "PRIMA", "VACACIONES", "BONIFICACION", "TOTAL LIQUIDACION"};
        // Configurando los eventos de edicion de la tabla
        dtblNomina = new DefaultTableModel(null, COLUMNAS) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0;
            }
        };
        // Llenando la tabla usuarios

        Object[] object = new Object[14];
        //servicio.listarUsuariosNomina()
        //      .forEach((UsuarioDTO usuario) -> {
        //        dtblNomina.addRow(usuario.toArray());
        //      

        for (UsuarioDTO u : servicio.listarUsuariosNomina()) {
            double debe = 0;
            Abono_PrestamoDTO abono = servicio.buscarAbonoPrestamo(u.getId());
            // double debe = abono.getTotal_debe();
            if (abono != null) {
                debe = abono.getTotal_debe();
            }
            UsuarioDTO usuarioFecha = servicio.buscarFechaIngreso(u.getId());

            object[0] = u.getId();
            object[1] = u.getNombres();
            object[2] = usuarioFecha.getFecha_ingreso();
            object[3] = "0";
            object[4] = "0";
            object[5] = "0";
            object[6] = "0";
            object[7] = "0";
            object[8] = "0";
            object[9] = "0";
            object[10] = "0";
            object[11] = "0";

            dtblNomina.addRow(object);

        }

        //});
        //Agregando el modelo de la tabla usuarios
        tblLiquidacion1.setModel(dtblNomina);

        dtblNomina.addTableModelListener(
                (TableModelEvent e) -> {

                    //Obteniendo el objeto almacenado en la celda seleccionada
                    if (e.getType() == TableModelEvent.UPDATE) {

                        /*
                         * Indexado de columnas y sus campos en la base de datos
                         * 0 - ID, 1 - NOMBRES, 2 - APELLIDOS, 3 - DOCUMENTO, 4
                         * - NOMBRE_USUARIO, 5 - ROL
                         */
                        // Capturando los datos que contiene las celdas a editar.
                        long id = Long.parseLong(String.valueOf(tblLiquidacion1.getValueAt(e.getFirstRow(), 0)));
                        String nombres = String.valueOf(tblLiquidacion1.getValueAt(e.getFirstRow(), 1));
                        String documento = String.valueOf(tblLiquidacion1.getValueAt(e.getFirstRow(), 2));
                        String valor = String.valueOf(tblLiquidacion1.getValueAt(e.getFirstRow(), 4));
                        String nombreUsuario = String.valueOf(tblLiquidacion1.getValueAt(e.getFirstRow(), 4));

                        //
                    }
                }
        );

        agregarJComboBoxAColumna(tblLiquidacion1, 5, servicio.listarRoles().toArray());

        aplicarEventosPersonalizados(tblLiquidacion1);
    }

    private void llenarTablaUsuarios() {

        final String[] COLUMNAS = {"ID", "NOMBRE", "APELLIDOS", "CEDULA", "VALOR HORA", "DIAS TRABAJADOS",
            "HORAS AUSENTES", "HORAS TRABAJADAS", "HORAS EXTRAS", "DEBE", "ABONAR", "TOTAL PAGAR", "OK"};
        // Configurando los eventos de edicion de la tabla
        dtblNomina2 = new DefaultTableModel(null, COLUMNAS) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0 || column == 12;
            }

            @Override
            public Class getColumnClass(int columna) {
                if (columna == 12) {
                    return Boolean.class;
                }

                return Object.class;
            }
        };
        // Llenando la tabla usuarios

        Object[] object = new Object[14];
        //servicio.listarUsuariosNomina()
        //      .forEach((UsuarioDTO usuario) -> {
        //        dtblNomina.addRow(usuario.toArray());
        //      

        for (UsuarioDTO u : servicio.listarUsuariosNomina()) {
            double debe = 0;
            Abono_PrestamoDTO abono = servicio.buscarAbonoPrestamo(u.getId());
            // double debe = abono.getTotal_debe();
            if (abono != null) {
                debe = abono.getTotal_debe();
            }

            object[0] = u.getId();
            object[1] = u.getNombres();
            object[2] = u.getApellidos();
            object[3] = u.getDocumento();
            object[4] = formateador.format(u.getRolDTO().getValor());
            object[5] = "0";
            object[6] = "0";
            object[7] = "0";
            object[8] = "0";

            object[9] = formateador.format(debe);
            object[10] = "0";
            object[11] = "0";
            object[12] = true;

            dtblNomina2.addRow(object);

        }

        //});
        //Agregando el modelo de la tabla usuarios
        tblNomina.setModel(dtblNomina2);
        tblNomina.getColumnModel().getColumn(0).setMaxWidth(40);
        tblNomina.getColumnModel().getColumn(1).setMaxWidth(400);
        tblNomina.getColumnModel().getColumn(2).setMaxWidth(400);
        tblNomina.getColumnModel().getColumn(3).setMaxWidth(80);
        tblNomina.getColumnModel().getColumn(4).setMaxWidth(80);
        tblNomina.getColumnModel().getColumn(5).setMaxWidth(80);
        tblNomina.getColumnModel().getColumn(6).setMaxWidth(80);
        tblNomina.getColumnModel().getColumn(7).setMaxWidth(80);
        tblNomina.getColumnModel().getColumn(8).setMaxWidth(80);
        tblNomina.getColumnModel().getColumn(9).setMaxWidth(80);
        tblNomina.getColumnModel().getColumn(10).setMaxWidth(80);
        tblNomina.getColumnModel().getColumn(11).setMaxWidth(80);
        tblNomina.getColumnModel().getColumn(12).setMaxWidth(50);
        dtblNomina2.addTableModelListener(
                (TableModelEvent e) -> {

                    //Obteniendo el objeto almacenado en la celda seleccionada
                    if (e.getType() == TableModelEvent.UPDATE) {

                        /*
                         * Indexado de columnas y sus campos en la base de datos
                         * 0 - ID, 1 - NOMBRES, 2 - APELLIDOS, 3 - DOCUMENTO, 4
                         * - NOMBRE_USUARIO, 5 - ROL
                         */
                        // Capturando los datos que contiene las celdas a editar.
                        long id = Long.parseLong(String.valueOf(tblNomina.getValueAt(e.getFirstRow(), 0)));
                        String nombres = String.valueOf(tblNomina.getValueAt(e.getFirstRow(), 1));
                        String documento = String.valueOf(tblNomina.getValueAt(e.getFirstRow(), 2));
                        String valor = String.valueOf(tblNomina.getValueAt(e.getFirstRow(), 4));
                        String nombreUsuario = String.valueOf(tblNomina.getValueAt(e.getFirstRow(), 4));

                        //
                    }
                }
        );

//        agregarJComboBoxAColumna(tblNomina, 5, servicio.listarRoles().toArray());
//        aplicarEventosPersonalizados(tblNomina);
    }

    public void listarUsuarios() {

    }

    private void BuscarValor(long id) {
        double valor = servicio.BuscarValor(id);

    }

    private void buscarEmpleadoLiquidacion(long cedula) {
        UsuarioDTO usuario = servicio.consultarUsuario(cedula);
        //long rol = usuario.getRol();
        if (usuario != null) {
            RolDTO rol = servicio.consultarRol(usuario.getRol());
            llenarTablaLiquidacion(cedula);

            txtIdLiquidacion.setText(usuario.getId() + "");
        } else {
            JOptionPane.showMessageDialog(null, "Cedula erronea");
        }
    }

    private void buscarEmpleado(long cedula) {
        UsuarioDTO usuario = servicio.consultarUsuario(cedula);
        //long rol = usuario.getRol()
        if (usuario != null) {
            RolDTO rol = servicio.consultarRol(usuario.getRol());
            txtNombreEmpleado.setText(usuario.getNombres() + usuario.getApellidos());
            txtRol.setText(rol.getRol());
            txtValorHora.setText(formateador.format(rol.getValor()));
            BuscarValor(usuario.getId());
            Abono_PrestamoDTO abono = servicio.buscarAbonoPrestamo(usuario.getId());

            double debe = abono.getTotal_debe();
            if (debe != 0) {
                txtDebe.setVisible(true);
                txtDebe.setText(formateador.format(debe));

                jLabel2.setVisible(true);
            } else {

                txtDebe.setVisible(false);

                jLabel2.setVisible(false);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Cedula no encontrada");
        }

    }

    private void llenarTablaLiquidacion(long cedula) {

        // Configurando los eventos de edicion de la tabla
        dtblNomina = new DefaultTableModel(null, NOMBRES_COLUMNAS) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0;
            }
        };
        // Llenando la tabla usuarios
        UsuarioDTO usuario = servicio.consultarUsuario(cedula);
        Object[] object = new Object[13];
        //servicio.listarUsuariosNomina()
        //      .forEach((UsuarioDTO usuario) -> {
        //        dtblNomina.addRow(usuario.toArray());
        //      
        UsuarioDTO usuarioFecha = servicio.buscarFechaIngreso(cedula);

        tblLiquidacion.setValueAt(usuario.getNombres(), 0, 0);
        tblLiquidacion.setValueAt(usuarioFecha.getFecha_ingreso(), 0, 1);

        //dtblNomina.addRow(object);
        //});
        //Agregando el modelo de la tabla usuarios
        // tblLiquidacion.setModel(dtblNomina);
        dtblNomina.addTableModelListener(
                (TableModelEvent e) -> {

                    //Obteniendo el objeto almacenado en la celda seleccionada
                    if (e.getType() == TableModelEvent.UPDATE) {

                        /*
                         * Indexado de columnas y sus campos en la base de datos
                         * 0 - ID, 1 - NOMBRES, 2 - APELLIDOS, 3 - DOCUMENTO, 4
                         * - NOMBRE_USUARIO, 5 - ROL
                         */
                        // Capturando los datos que contiene las celdas a editar.
                        long id = Long.parseLong(String.valueOf(tblNomina.getValueAt(e.getFirstRow(), 0)));
                        String nombres = String.valueOf(tblNomina.getValueAt(e.getFirstRow(), 1));
                        String documento = String.valueOf(tblNomina.getValueAt(e.getFirstRow(), 2));
                        String valor = String.valueOf(tblNomina.getValueAt(e.getFirstRow(), 4));
                        String nombreUsuario = String.valueOf(tblNomina.getValueAt(e.getFirstRow(), 4));

                        //
                    }
                }
        );

        agregarJComboBoxAColumna(tblNomina, 5, servicio.listarRoles().toArray());

        aplicarEventosPersonalizados(tblNomina);
    }

    private void buscarEmpleadoNomina(long cedula) {
        UsuarioDTO usuario = servicio.consultarUsuario(cedula);
        if (usuario != null) {
            txtNombreAdelanto.setText(usuario.getNombres() + usuario.getApellidos());
            txtId.setText(usuario.getId() + "");
        } else {
            JOptionPane.showMessageDialog(null, "Cedula no encontrada");
        }
    }

    private void registrarAdelanto(long id, double valor, Date fecha, String concepto) {

        servicio.registrarAdelanto(id, valor, fecha, concepto);

    }

    public void diasTrabajados(int dias) {

        int filas = tblNomina.getRowCount();
        int cont = 0;

        while (cont < filas) {
            tblNomina.setValueAt(dias, cont, 5);
            cont++;
        }

    }

    private void buscarCedulaNomina(long cedula) {

        final String[] columnas = {"CODIGO NOMINA", "NOMBRE", "APELLIDOS", "CEDULA", "NOMBRE USUARIO", "ROL", "NOMINA DESDE", "NOMINA HASTA", "VALOR HORA", "DIAS TRABAJADOS", "HORAS TRABAJADAS",
            "HORAS EXTRAS", "ABONADO", "TOTAL PAGADO"};
        //configurando los eventos de la tabla
        dtblNomina = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2;
            }
        };
        //Llenando la tabla
        Object[] object = new Object[14];
        ArrayList<NominaDTO> n = servicio.buscarCedulaNomina(cedula);
        System.out.println(n.size());
        for (NominaDTO no : n) {
            UsuarioDTO u = servicio.consultarUsuario(no.getUsuario());
            RolDTO rol = servicio.consultarRol(u.getRol());
            object[0] = no.getUsuario();
            object[1] = u.getNombres();
            object[2] = u.getApellidos();
            object[3] = u.getDocumento();
            object[4] = u.getNombreUsuario();
            object[5] = rol.getRol();
            object[6] = no.getFecha_inicio();
            object[7] = no.getFecha_pago();
            object[8] = no.getSalario();
            object[9] = no.getDias_trabajados();
            object[10] = no.getHorasTrabajadas();
            object[11] = no.getHorasE();
            object[12] = no.getAbono();
            object[13] = no.getPago_total();

            dtblNomina.addRow(object);

            tblNominaCedula.setModel(dtblNomina);

            dtblNomina.addTableModelListener((TableModelEvent e) -> {

                long id;
            });
        }
    }

    private void buscarCedulaAbono(long cedula) {

        final String[] columnas = {"CODIGO ABONADO", "CODIGO USUARIO", "VALOR", "FECHA"};
        //configurando los eventos de la tabla
        dtblNomina = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2;
            }
        };
        //Llenando la tabla
        servicio.buscarCedulaAbono(cedula).forEach((AbonoDTO abono) -> {
            dtblNomina.addRow(abono.toArray());
        });
        tblAbonado.setModel(dtblNomina);

        dtblNomina.addTableModelListener((TableModelEvent e) -> {

            long id;
        });
    }

    private void buscarLiquidacionEmpleado(long cedula) {

        final String[] columnas = {"Codigo Liquidacion", "sueldo_basico", "fecha_entrada", "fecha_salida", "dias_trabajados", "cesantias",
            "intereses_cesantias", "prima_servicio_", "vacaciones", "bonificacion",
            "valor_liquidacion"};
        //configurando los eventos de la tabla
        dtblNomina = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2;
            }
        };
        //Llenando la tabla
        servicio.buscarLiquidacionEmpleado(cedula).forEach((LiquidacionDTO liquidacion) -> {
            dtblNomina.addRow(liquidacion.toArray());
        });
        tblLiquidacionE.setModel(dtblNomina);

        dtblNomina.addTableModelListener((TableModelEvent e) -> {

            long id;
        });
    }

    private void buscarCedulaAdelanto(long cedula) {

        final String[] columnas = {"CODIGO ADELANTO", "VALOR", "FECHA", "CONCEPTO"};
        //configurando los eventos de la tabla
        dtblNomina = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2;
            }
        };
        //Llenando la tabla
        servicio.buscarCedulaAdelanto(cedula).forEach((PrestamoDTO prestamo) -> {
            dtblNomina.addRow(prestamo.toArray());
        });
        tblAdelanto.setModel(dtblNomina);

        dtblNomina.addTableModelListener((TableModelEvent e) -> {

            long id;
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tpPestana = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        btnGuardarPago = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        comboBuscarEmpleado = new javax.swing.JComboBox<>();
        btnActualizar = new javax.swing.JButton();
        PanelDatosU = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNombreEmpleado = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtRol = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtValorHora = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtDebe = new javax.swing.JTextField();
        PanelDatosN = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        dpIniNomina = new com.github.lgooddatepicker.components.DatePicker();
        jLabel5 = new javax.swing.JLabel();
        dpFinNomina = new com.github.lgooddatepicker.components.DatePicker();
        txtTotal = new javax.swing.JTextField();
        labelTotal = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        labelTotal1 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        labelTotal2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnGenerar = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        textAreaConcepto = new javax.swing.JTextArea();
        jPanel12 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        comboBuscarEmpleado1 = new javax.swing.JComboBox<>();
        btnActualizar1 = new javax.swing.JButton();
        panelDatosAdelantos = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtNombreAdelanto = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        txtAnticipo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        dpFecha = new com.github.lgooddatepicker.components.DatePicker();
        jPanel3 = new javax.swing.JPanel();
        btnAbonar = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        comboBuscarEmpleado2 = new javax.swing.JComboBox<>();
        btnActualizar2 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        txtIdAbonar = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtNombeAbonar = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        dpFechaAbonar = new com.github.lgooddatepicker.components.DatePicker();
        jLabel25 = new javax.swing.JLabel();
        txtDebeAbonar = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtAbono = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLiquidacion = new javax.swing.JTable();
        btnCalcularLiquidacion = new javax.swing.JButton();
        btnLiquidar = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        comboBuscarEmpleado3 = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();
        btnActualizar3 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        txtIdLiquidacion = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        dpFechaLiquidacion = new com.github.lgooddatepicker.components.DatePicker();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblNominaCedula = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        txtCedulaNomina = new javax.swing.JTextField();
        btnBuscarUsuario1 = new javax.swing.JButton();
        comboBuscarEmpleado4 = new javax.swing.JComboBox<>();
        btnActualizar4 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblBuscarNominaFecha = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        dpDesdeN = new com.github.lgooddatepicker.components.DatePicker();
        jLabel34 = new javax.swing.JLabel();
        dpHastaN = new com.github.lgooddatepicker.components.DatePicker();
        jLabel33 = new javax.swing.JLabel();
        btnBuscarFechaNomina = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblAdelanto = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        btnBuscarUsuarioAdelanto = new javax.swing.JButton();
        comboBuscarEmpleado7 = new javax.swing.JComboBox<>();
        btnActualizar5 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblAbonado = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        btnBuscarUsuario3 = new javax.swing.JButton();
        comboBuscarEmpleado6 = new javax.swing.JComboBox<>();
        btnActualizar6 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblLiquidacionE = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        btnBuscarLiquidacionE = new javax.swing.JButton();
        comboBuscarEmpleado5 = new javax.swing.JComboBox<>();
        btnActualizar7 = new javax.swing.JButton();
        panelNominaGrupo = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblNomina = new javax.swing.JTable();
        guardarNomina = new javax.swing.JButton();
        btnAceptarNomina = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnEliminarEmpleado1 = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        dpInicio = new com.github.lgooddatepicker.components.DatePicker();
        jLabel16 = new javax.swing.JLabel();
        dpFin = new com.github.lgooddatepicker.components.DatePicker();
        jLabel17 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtDias = new javax.swing.JTextField();
        btnCalcular = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblLiquidacion1 = new javax.swing.JTable();
        btnCalcularLiquidacion1 = new javax.swing.JButton();
        btnLiquidar1 = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        dpFechaLiquidacion1 = new com.github.lgooddatepicker.components.DatePicker();
        btnEliminarEmpleado2 = new javax.swing.JButton();
        jPanel26 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tblBuscarNominaFecha1 = new javax.swing.JTable();
        jPanel29 = new javax.swing.JPanel();
        dpDesdeN1 = new com.github.lgooddatepicker.components.DatePicker();
        jLabel35 = new javax.swing.JLabel();
        dpHastaN1 = new com.github.lgooddatepicker.components.DatePicker();
        jLabel37 = new javax.swing.JLabel();
        btnBuscarFechaNomina1 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Nóminas");
        setBackground(new java.awt.Color(255, 255, 255));
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/icon2.png"))); // NOI18N
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        tpPestana.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tpPestanaMouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnGuardarPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/guardar.png"))); // NOI18N
        btnGuardarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarPagoActionPerformed(evt);
            }
        });

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtro Cedula"));

        jButton2.setText("Buscar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        comboBuscarEmpleado.setEnabled(false);
        comboBuscarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBuscarEmpleadoActionPerformed(evt);
            }
        });

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(comboBuscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(btnActualizar)
                .addContainerGap(557, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(comboBuscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        PanelDatosU.setBackground(new java.awt.Color(255, 255, 255));
        PanelDatosU.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Usuario"));

        jLabel1.setText("Nombre Empleado");

        txtNombreEmpleado.setEditable(false);
        txtNombreEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreEmpleadoActionPerformed(evt);
            }
        });

        jLabel15.setText("Rol");

        txtRol.setEditable(false);

        jLabel22.setText("Valor Hora");

        txtValorHora.setEditable(false);
        txtValorHora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorHoraActionPerformed(evt);
            }
        });

        jLabel2.setText("Debe");

        txtDebe.setEditable(false);

        javax.swing.GroupLayout PanelDatosULayout = new javax.swing.GroupLayout(PanelDatosU);
        PanelDatosU.setLayout(PanelDatosULayout);
        PanelDatosULayout.setHorizontalGroup(
            PanelDatosULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDatosULayout.createSequentialGroup()
                .addGap(0, 2, Short.MAX_VALUE)
                .addGroup(PanelDatosULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addGroup(PanelDatosULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(PanelDatosULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(PanelDatosULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDatosULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtValorHora, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDatosULayout.createSequentialGroup()
                            .addComponent(txtNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap()))
                    .addGroup(PanelDatosULayout.createSequentialGroup()
                        .addGroup(PanelDatosULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRol, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDebe, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        PanelDatosULayout.setVerticalGroup(
            PanelDatosULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDatosULayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(PanelDatosULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDatosULayout.createSequentialGroup()
                        .addGroup(PanelDatosULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addGroup(PanelDatosULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelDatosULayout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addGroup(PanelDatosULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtValorHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelDatosULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtDebe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelDatosN.setBackground(new java.awt.Color(255, 255, 255));
        PanelDatosN.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Nomina"));

        jLabel3.setText("Trabajado desde");

        jLabel5.setText("Trabajado hasta");

        txtTotal.setEditable(false);
        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });

        labelTotal.setText("Total pagar");

        jButton3.setText("Calcular Total ");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField2.setText("0");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        labelTotal1.setText("Porcentaje");

        jTextField3.setText("0");
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        labelTotal2.setText("Total Hecho");

        javax.swing.GroupLayout PanelDatosNLayout = new javax.swing.GroupLayout(PanelDatosN);
        PanelDatosN.setLayout(PanelDatosNLayout);
        PanelDatosNLayout.setHorizontalGroup(
            PanelDatosNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDatosNLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDatosNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDatosNLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(26, 26, 26)
                        .addComponent(dpIniNomina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(dpFinNomina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))
                    .addGroup(PanelDatosNLayout.createSequentialGroup()
                        .addGroup(PanelDatosNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTotal)
                            .addGroup(PanelDatosNLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(labelTotal1)))
                        .addGap(47, 47, 47)
                        .addGroup(PanelDatosNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(PanelDatosNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelDatosNLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3)
                                .addGap(69, 69, 69))
                            .addGroup(PanelDatosNLayout.createSequentialGroup()
                                .addGap(143, 143, 143)
                                .addComponent(labelTotal2)
                                .addGap(47, 47, 47)
                                .addComponent(jTextField3)
                                .addContainerGap())))))
        );
        PanelDatosNLayout.setVerticalGroup(
            PanelDatosNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDatosNLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(PanelDatosNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(dpIniNomina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(dpFinNomina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(PanelDatosNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDatosNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelTotal2))
                    .addGroup(PanelDatosNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelTotal1)))
                .addGap(65, 65, 65)
                .addGroup(PanelDatosNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTotal)
                    .addComponent(jButton3))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/SALIR.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(PanelDatosU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PanelDatosN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(32, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGuardarPago)
                        .addGap(444, 444, 444)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelDatosU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelDatosN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnGuardarPago)
                        .addGap(44, 44, 44))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        tpPestana.addTab("Pago Nomina/Empleado", jPanel1);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        btnGenerar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/guardar.png"))); // NOI18N
        btnGenerar.setText("Adelanto");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

        textAreaConcepto.setColumns(20);
        textAreaConcepto.setRows(5);
        jScrollPane5.setViewportView(textAreaConcepto);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Busqueda"));

        jButton4.setText("Buscar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        comboBuscarEmpleado1.setEnabled(false);
        comboBuscarEmpleado1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBuscarEmpleado1ActionPerformed(evt);
            }
        });

        btnActualizar1.setText("Actualizar");
        btnActualizar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(comboBuscarEmpleado1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addGap(38, 38, 38)
                .addComponent(btnActualizar1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(comboBuscarEmpleado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar1))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        panelDatosAdelantos.setBackground(new java.awt.Color(255, 255, 255));
        panelDatosAdelantos.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos usuario"));

        jLabel13.setText("Codigo Usuario");

        txtId.setEditable(false);

        txtNombreAdelanto.setEditable(false);
        txtNombreAdelanto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreAdelantoActionPerformed(evt);
            }
        });

        jLabel11.setText("Nombre Empleado");

        javax.swing.GroupLayout panelDatosAdelantosLayout = new javax.swing.GroupLayout(panelDatosAdelantos);
        panelDatosAdelantos.setLayout(panelDatosAdelantosLayout);
        panelDatosAdelantosLayout.setHorizontalGroup(
            panelDatosAdelantosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosAdelantosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosAdelantosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(panelDatosAdelantosLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(41, 41, 41)
                        .addGroup(panelDatosAdelantosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreAdelanto, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        panelDatosAdelantosLayout.setVerticalGroup(
            panelDatosAdelantosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosAdelantosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosAdelantosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDatosAdelantosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtNombreAdelanto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Prestamo"));

        jLabel7.setText("Anticipo");

        jLabel12.setText("Fecha");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel7))
                .addGap(25, 25, 25)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAnticipo, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dpFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(322, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(dpFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtAnticipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(427, 427, 427)
                        .addComponent(btnGenerar))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(panelDatosAdelantos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5)))
                .addGap(0, 72, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDatosAdelantos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGenerar)
                .addContainerGap())
        );

        tpPestana.addTab("Adelantos", jPanel6);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        btnAbonar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/guardar.png"))); // NOI18N
        btnAbonar.setText("Abonado");
        btnAbonar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbonarActionPerformed(evt);
            }
        });

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("Busqueda"));

        jButton5.setText("Buscar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        comboBuscarEmpleado2.setEnabled(false);
        comboBuscarEmpleado2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBuscarEmpleado2ActionPerformed(evt);
            }
        });

        btnActualizar2.setText("Actualizar");
        btnActualizar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizar2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(comboBuscarEmpleado2, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addGap(69, 69, 69)
                .addComponent(btnActualizar2)
                .addContainerGap(605, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(comboBuscarEmpleado2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar2))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Empleado"));

        txtIdAbonar.setEditable(false);

        jLabel26.setText("Codigo Empleado");

        jLabel24.setText("Nombre Empleado");

        txtNombeAbonar.setEditable(false);
        txtNombeAbonar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombeAbonarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(84, 84, 84)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombeAbonar, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                            .addComponent(txtIdAbonar)))
                    .addComponent(jLabel24))
                .addContainerGap(154, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtIdAbonar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtNombeAbonar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos abonado"));

        jLabel25.setText("Fecha");

        txtDebeAbonar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDebeAbonarActionPerformed(evt);
            }
        });

        jLabel23.setText("Debe");

        jLabel27.setText("Abono");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(jLabel23)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dpFechaAbonar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDebeAbonar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAbono, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(143, 143, 143))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dpFechaAbonar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addGap(42, 42, 42)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAbono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDebeAbonar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(66, 66, 66))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAbonar)
                .addGap(472, 472, 472))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 186, Short.MAX_VALUE)
                .addComponent(btnAbonar)
                .addGap(45, 45, 45))
        );

        tpPestana.addTab("Abonar", jPanel3);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tblLiquidacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre Empleado", "Fecha Ingreso", "Fecha Retiro", "Dias Trabajados", "Sueldo basico", "Cesantias", "Intereses Cesantias", "Prima", "Vacaciones", "Bonificacion", "Total Liquidacion"
            }
        ));
        jScrollPane3.setViewportView(tblLiquidacion);

        btnCalcularLiquidacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/calcular_opt.png"))); // NOI18N
        btnCalcularLiquidacion.setText("Liquidacion");
        btnCalcularLiquidacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularLiquidacionActionPerformed(evt);
            }
        });

        btnLiquidar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/guardar.png"))); // NOI18N
        btnLiquidar.setText("Liquidacion");
        btnLiquidar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiquidarActionPerformed(evt);
            }
        });

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("Busqueda "));

        comboBuscarEmpleado3.setEnabled(false);
        comboBuscarEmpleado3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBuscarEmpleado3ActionPerformed(evt);
            }
        });

        jButton6.setText("BUSCAR");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        btnActualizar3.setText("Actualizar");
        btnActualizar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizar3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(comboBuscarEmpleado3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(btnActualizar3)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBuscarEmpleado3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6)
                    .addComponent(btnActualizar3))
                .addContainerGap())
        );

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Liquidacion"));

        jLabel28.setText("Codigo Usuario");

        txtIdLiquidacion.setEditable(false);

        jLabel8.setText("Fecha retiro");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtIdLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(27, 27, 27)
                .addComponent(dpFechaLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtIdLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(dpFechaLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(236, 236, 236)
                .addComponent(btnCalcularLiquidacion)
                .addGap(150, 150, 150)
                .addComponent(btnLiquidar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCalcularLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLiquidar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(52, 52, 52))
        );

        tpPestana.addTab("Liquidacion", jPanel2);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        tblNominaCedula.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "Cedula", "Rol", "Valor Dia", "Fecha Inicio", "Fecha Fin", "Dias trabajados", "Dias ausente", "Prestamos", "Total pagar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane7.setViewportView(tblNominaCedula);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder("Busqueda"));

        jLabel29.setText("Cedula");

        txtCedulaNomina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedulaNominaActionPerformed(evt);
            }
        });

        btnBuscarUsuario1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnBuscarUsuario1.setText("Buscar");
        btnBuscarUsuario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarUsuario1ActionPerformed(evt);
            }
        });

        comboBuscarEmpleado4.setEnabled(false);
        comboBuscarEmpleado4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBuscarEmpleado4ActionPerformed(evt);
            }
        });

        btnActualizar4.setText("Actualizar");
        btnActualizar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizar4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29)
                .addGap(32, 32, 32)
                .addComponent(txtCedulaNomina, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(comboBuscarEmpleado4, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBuscarUsuario1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnActualizar4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(395, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCedulaNomina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(btnBuscarUsuario1)
                    .addComponent(comboBuscarEmpleado4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1099, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );

        tpPestana.addTab("Buscar Nomina/Empleado", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        tblBuscarNominaFecha.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "Cedula", "Rol", "Valor Dia", "Fecha Inicio", "Fecha Fin", "Dias trabajados", "Dias ausente", "Prestamos", "Total pagar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane12.setViewportView(tblBuscarNominaFecha);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder("Busqueda"));

        jLabel34.setText("Desde");

        jLabel33.setText("Hasta");

        btnBuscarFechaNomina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnBuscarFechaNomina.setText("Buscar");
        btnBuscarFechaNomina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFechaNominaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel34)
                .addGap(51, 51, 51)
                .addComponent(dpDesdeN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94)
                .addComponent(jLabel33)
                .addGap(27, 27, 27)
                .addComponent(dpHastaN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(btnBuscarFechaNomina, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jLabel33)
                    .addComponent(dpDesdeN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dpHastaN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarFechaNomina))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 1099, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        tpPestana.addTab("Buscar Nominas/Fechas", jPanel5);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        tblAdelanto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo Adelanto", "Nombre", "Cedula", "Rol", "Valor Dia", "Fecha Inicio", "Fecha Fin", "Dias trabajados", "Dias ausente", "Prestamos", "Total pagar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane11.setViewportView(tblAdelanto);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder("Busqueda"));

        btnBuscarUsuarioAdelanto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnBuscarUsuarioAdelanto.setText("Buscar");
        btnBuscarUsuarioAdelanto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarUsuarioAdelantoActionPerformed(evt);
            }
        });

        comboBuscarEmpleado7.setEnabled(false);
        comboBuscarEmpleado7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBuscarEmpleado7ActionPerformed(evt);
            }
        });

        btnActualizar5.setText("Actualizar");
        btnActualizar5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizar5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(comboBuscarEmpleado7, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91)
                .addComponent(btnBuscarUsuarioAdelanto, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnActualizar5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(367, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBuscarUsuarioAdelanto)
                        .addComponent(btnActualizar5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(comboBuscarEmpleado7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 1099, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                .addGap(28, 28, 28))
        );

        tpPestana.addTab("Buscar Adelanto", jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        tblAbonado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "Cedula", "Rol", "Valor Dia", "Fecha Inicio", "Fecha Fin", "Dias trabajados", "Dias ausente", "Prestamos", "Total pagar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane10.setViewportView(tblAbonado);

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder("Busqueda"));

        btnBuscarUsuario3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnBuscarUsuario3.setText("Buscar");
        btnBuscarUsuario3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarUsuario3ActionPerformed(evt);
            }
        });

        comboBuscarEmpleado6.setEnabled(false);
        comboBuscarEmpleado6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBuscarEmpleado6ActionPerformed(evt);
            }
        });

        btnActualizar6.setText("Actualizar");
        btnActualizar6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizar6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(comboBuscarEmpleado6, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(btnBuscarUsuario3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(btnActualizar6, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(482, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarUsuario3)
                    .addComponent(comboBuscarEmpleado6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar6, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 1099, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                .addContainerGap())
        );

        tpPestana.addTab("Buscar Abonado", jPanel9);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        tblLiquidacionE.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "Cedula", "Rol", "Valor Dia", "Fecha Inicio", "Fecha Fin", "Dias trabajados", "Dias ausente", "Prestamos", "Total pagar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane9.setViewportView(tblLiquidacionE);

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder("Busqueda"));

        btnBuscarLiquidacionE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnBuscarLiquidacionE.setText("Buscar");
        btnBuscarLiquidacionE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarLiquidacionEActionPerformed(evt);
            }
        });

        comboBuscarEmpleado5.setEnabled(false);
        comboBuscarEmpleado5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBuscarEmpleado5ActionPerformed(evt);
            }
        });

        btnActualizar7.setText("Actualizar");
        btnActualizar7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizar7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(comboBuscarEmpleado5, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(btnBuscarLiquidacionE, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btnActualizar7, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(485, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarLiquidacionE)
                    .addComponent(comboBuscarEmpleado5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 1099, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                .addContainerGap())
        );

        tpPestana.addTab("Buscar Liquidacion/Empleado", jPanel10);

        panelNominaGrupo.setBackground(new java.awt.Color(255, 255, 255));

        tblNomina.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo Usuario", "Nombre", "Cedula", "Rol", "Valor Dia", "Fecha Inicio", "Fecha Fin", "Dias trabajados", "Dias ausente", "Total pagar", "null", "null"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tblNomina);

        guardarNomina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/guardar.png"))); // NOI18N
        guardarNomina.setText(" Nomina");
        guardarNomina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarNominaActionPerformed(evt);
            }
        });

        btnAceptarNomina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Aceptar_opt.png"))); // NOI18N
        btnAceptarNomina.setText("Aceptar");
        btnAceptarNomina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarNominaActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Eliminar.png"))); // NOI18N
        btnEliminar.setText(" Empleado");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnEliminarEmpleado1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Actualizar_opt.png"))); // NOI18N
        btnEliminarEmpleado1.setText("Actualizar");
        btnEliminarEmpleado1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEmpleado1ActionPerformed(evt);
            }
        });

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder("Fecha Nomina"));

        dpInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dpInicioMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                dpInicioMousePressed(evt);
            }
        });

        jLabel16.setText("Fecha Inicio");

        jLabel17.setText("Fecha fin");

        jLabel21.setText("Dias trabajados");

        btnCalcular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/calcular_opt.png"))); // NOI18N
        btnCalcular.setText("Calcular Dias");
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(12, 12, 12)
                .addComponent(dpInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dpFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDias, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                .addComponent(btnCalcular)
                .addGap(124, 124, 124))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dpInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(dpFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(txtDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        jLabel38.setText("TOTAL A PAGAR NOMINA");

        javax.swing.GroupLayout panelNominaGrupoLayout = new javax.swing.GroupLayout(panelNominaGrupo);
        panelNominaGrupo.setLayout(panelNominaGrupoLayout);
        panelNominaGrupoLayout.setHorizontalGroup(
            panelNominaGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNominaGrupoLayout.createSequentialGroup()
                .addGroup(panelNominaGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelNominaGrupoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane6))
                    .addGroup(panelNominaGrupoLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(btnAceptarNomina)
                        .addGap(61, 61, 61)
                        .addComponent(guardarNomina, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(btnEliminarEmpleado1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel38)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53))
                    .addComponent(jPanel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelNominaGrupoLayout.setVerticalGroup(
            panelNominaGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNominaGrupoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelNominaGrupoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(guardarNomina)
                    .addComponent(btnAceptarNomina)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarEmpleado1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addGap(29, 29, 29))
        );

        tpPestana.addTab("Pago Nomina/Grupo", panelNominaGrupo);

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));

        tblLiquidacion1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre Empleado", "Fecha Ingreso", "Fecha Retiro", "Dias Trabajados", "Sueldo basico", "Cesantias", "Intereses Cesantias", "Prima", "Vacaciones", "Bonificacion", "Total Liquidacion"
            }
        ));
        jScrollPane4.setViewportView(tblLiquidacion1);

        btnCalcularLiquidacion1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/calcular_opt.png"))); // NOI18N
        btnCalcularLiquidacion1.setText("Liquidacion");
        btnCalcularLiquidacion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularLiquidacion1ActionPerformed(evt);
            }
        });

        btnLiquidar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/guardar.png"))); // NOI18N
        btnLiquidar1.setText("Liquidacion");
        btnLiquidar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiquidar1ActionPerformed(evt);
            }
        });

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Liquidacion"));

        jLabel36.setText("Fecha retiro");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel36)
                .addGap(27, 27, 27)
                .addComponent(dpFechaLiquidacion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(774, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(dpFechaLiquidacion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        btnEliminarEmpleado2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Actualizar_opt.png"))); // NOI18N
        btnEliminarEmpleado2.setText("Actualizar");
        btnEliminarEmpleado2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEmpleado2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGap(236, 236, 236)
                                .addComponent(btnCalcularLiquidacion1)
                                .addGap(150, 150, 150)
                                .addComponent(btnLiquidar1)
                                .addGap(63, 63, 63)
                                .addComponent(btnEliminarEmpleado2))
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCalcularLiquidacion1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLiquidar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminarEmpleado2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1119, Short.MAX_VALUE)
            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 623, Short.MAX_VALUE)
            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel24Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        tpPestana.addTab("Pago Liquidacion/Grupo", jPanel24);

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));

        tblBuscarNominaFecha1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "Cedula", "Rol", "Valor Dia", "Fecha Inicio", "Fecha Fin", "Dias trabajados", "Dias ausente", "Prestamos", "Total pagar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane13.setViewportView(tblBuscarNominaFecha1);

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));
        jPanel29.setBorder(javax.swing.BorderFactory.createTitledBorder("Busqueda"));

        jLabel35.setText("Desde");

        jLabel37.setText("Hasta");

        btnBuscarFechaNomina1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnBuscarFechaNomina1.setText("Buscar");
        btnBuscarFechaNomina1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFechaNomina1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel35)
                .addGap(51, 51, 51)
                .addComponent(dpDesdeN1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94)
                .addComponent(jLabel37)
                .addGap(27, 27, 27)
                .addComponent(dpHastaN1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(btnBuscarFechaNomina1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jLabel37)
                    .addComponent(dpDesdeN1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dpHastaN1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarFechaNomina1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 1079, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1119, Short.MAX_VALUE)
            .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel26Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 623, Short.MAX_VALUE)
            .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel26Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        tpPestana.addTab("Buscar Liquidaciones/Fechas", jPanel26);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(tpPestana, javax.swing.GroupLayout.PREFERRED_SIZE, 1104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(tpPestana, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCalcularLiquidacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularLiquidacionActionPerformed
        String te = comboBuscarEmpleado3.getSelectedItem().toString();
        String[] parts = te.split("-");
        String part1 = parts[0]; // 123
        String part2 = parts[1];
        UsuarioDTO DTO = servicio.consultarUsuario(Long.parseLong(part1));

        Date fechaInicio = Date.valueOf(dpFechaLiquidacion.getDateStringOrSuppliedString(LocalDate.now().toString()));
        if (DTO != null && fechaInicio != null) {
            Object cesan = tblLiquidacion.getValueAt(0, 6);
            Object inte = tblLiquidacion.getValueAt(0, 7);
            Object pri = tblLiquidacion.getValueAt(0, 8);
            Object vaca = tblLiquidacion.getValueAt(0, 9);
            Object Boni = tblLiquidacion.getValueAt(0, 9);
//            
//            String cedula = txtCedulaLiquidacion.getText();
            ArrayList<NominaDTO> nomina = servicio.buscarCedulaNomina(DTO.getId());

            Date d = null;
            UsuarioDTO usuario = servicio.consultarUsuario(DTO.getId());

            RolDTO rol = servicio.consultarRol(usuario.getRol());

            System.out.println(servicio.totalNomina(DTO.getId()) + "fecha inicio");
            dias = (int) ((fechaInicio.getTime() - usuario.getFecha_ingreso().getTime()) / 86400000);

            Object horas = tblLiquidacion.getValueAt(0, 4);
//            int hora = convertToInt(horas);

            double valor = rol.getValor();

            double total_nomina = servicio.totalNomina(DTO.getId());
            dias = servicio.HorasTrabajadas(DTO.getId()) / 8;
            double meses_trabajados = (double) dias / 30;
//            mostrarMensaje(total_nomina+"-"+meses_trabajados);
            double total_sueldo = total_nomina / meses_trabajados;

            double cesantia = (total_sueldo * dias) / 360;
            double cesantias_intere = (cesantia * 0.12 * dias) / 360;
            double prim = (total_sueldo * dias) / 360;
            double vacacion = (dias * (total_sueldo / 2)) / 360;
            double bonificacion = convertToDouble(Boni);

            //int dias = servicio.diasTrabajados(usuario.getId());
            double total_liquidacion = cesantia + cesantias_intere + prim + vacacion + bonificacion;
            tblLiquidacion.setValueAt(dias, 0, 3);
            //tblLiquidacion.setValueAt(hora, 0, 4);
            tblLiquidacion.setValueAt(formateador.format(total_sueldo), 0, 4);
            tblLiquidacion.setValueAt(formateador.format(cesantia), 0, 5);
            tblLiquidacion.setValueAt(formateador.format(cesantias_intere), 0, 6);
            tblLiquidacion.setValueAt(formateador.format(prim), 0, 7);
            tblLiquidacion.setValueAt(formateador.format(vacacion), 0, 8);
            tblLiquidacion.setValueAt(formateador.format(total_liquidacion), 0, 10);
            tblLiquidacion.setValueAt(fechaInicio, 0, 2);

            System.out.println(fechaInicio);

        } else {
            JOptionPane.showMessageDialog(null, "Digite cedula y seleccione fecha");
        }

    }//GEN-LAST:event_btnCalcularLiquidacionActionPerformed

    private void txtNombreEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreEmpleadoActionPerformed

    private void txtNombreAdelantoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreAdelantoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreAdelantoActionPerformed

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        String id_usuario = txtId.getText();
        String val = txtAnticipo.getText();
        Date fechaEntrega = Date.valueOf(dpFecha.getDateStringOrSuppliedString(LocalDate.now().toString()));
        if (!val.equalsIgnoreCase("") && !id_usuario.equalsIgnoreCase("")) {
            long id_u = Long.parseLong(id_usuario);
            val = val.replace(",", ".");
            val = val.replace(".", "");

            double va = Double.parseDouble(val);
//            Date fechaEntrega = Date.valueOf(dpFecha.getDateStringOrSuppliedString(LocalDate.now().toString()));
            String concepto = textAreaConcepto.getText();
            Abono_PrestamoDTO abono = servicio.buscarAbonoPrestamo(id_u);
            double prestamo = abono.getTotal_prestamo() + va;
            double total_debe = abono.getTotal_debe() + va;

            long abo = abono.getId();
            boolean x = servicio.actualizarAbonoPrestamo(abo, prestamo, total_debe);
            registrarAdelanto(abo, va, fechaEntrega, concepto);
            if (x) {
                String pago = txtAnticipo.getText();
                String nombre = txtNombreAdelanto.getText();
                pago = pago.replace(",", ".");
                pago = pago.replace(".", "");
                double pa = Double.parseDouble(pago);
                boolean z = servicio.registrar2("Adelanto  " + nombre, 6, pa, fechaEntrega);
                mostrarMensaje("Se registro el Prestamo exitosamente");
            } else {
                mostrarMensaje("No se pudo registrar el prestamo");
            }
//            txtCedulaAdelanto.setText("");
            txtId.setText("");
            txtNombreAdelanto.setText("");
            txtAnticipo.setText("");
            textAreaConcepto.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "todos los campos deben estar llenos");
        }
    }//GEN-LAST:event_btnGenerarActionPerformed

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void btnAceptarNominaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarNominaActionPerformed
        fechaDesde = Date.valueOf(dpInicio.getDateStringOrSuppliedString(LocalDate.now().toString()));

        Date fechaInicio = Date.valueOf(dpInicio.getDateStringOrSuppliedString(LocalDate.now().toString()));
        Date fechaFin = Date.valueOf(dpFin.getDateStringOrSuppliedString(LocalDate.now().toString()));
        int dias2 = 0;
        dias2 = (int) ((fechaFin.getTime() - fechaInicio.getTime()) / 86400000) + 1;
        int filas = tblNomina.getRowCount();
        int count = 0;
        double total_pagar = 0;
        // llenarValorPagar();
        //  while (count < filas) {
        //    Object objid = tblNomina.getValueAt(count, 0);
        //  Object diasTrabajados = tblNomina.getValueAt(count, 7);
        // Object diasFaltantes = tblNomina.getValueAt(count, 8);

        //Object valor=tblNomina.getValueAt(dias, dias);
        // long id = convertToLong(objid);
        //int diastrabajar = convertToInt(diasTrabajados);
        // int diasFal=convertToInt(diasFaltantes);
        // double valor = 0.0;
        //UsuarioDTO usuario = servicio.consultarUsuario(id);
        //valor = diastrabajar * usuario.getRolDTO().getValor();
        //objid=null;
        //count++;
        //System.out.println(id);
        //}
        UsuarioDTO usuario = null;
        while (count < filas) {

            Object valor = tblNomina.getValueAt(count, 4);
            String v = String.valueOf(valor);
            v = v.replace(",", ".");
            v = v.replace(".", "");

            Object dia = tblNomina.getValueAt(count, 5);
            String di = String.valueOf(dia);
            Integer da = Integer.parseInt(di);

            Object abonar = tblNomina.getValueAt(count, 10);
            String a = String.valueOf(abonar);
            a = a.replace(",", ".");
            a = a.replace(".", "");

            Object horasTra = tblNomina.getValueAt(count, 7);
            double val = Double.parseDouble(v);
            double hora = convertToDouble(horasTra);
            double abon = Double.parseDouble(a);

            Object diasA = tblNomina.getValueAt(count, 6);
            String d = String.valueOf(diasA);
            int dias = Integer.parseInt(d);
            double res = dias * hora;

            Object tota = tblNomina.getValueAt(count, 11);
            String to = String.valueOf(tota);
            to = to.replace(",", ".");
            to = to.replace(".", "");
            double totalp = Double.parseDouble(to);

            Object horaseE = tblNomina.getValueAt(count, 8);
            String h = String.valueOf(horaseE);
            h = h.replace(",", ".");
            h = h.replace(".", "");
            int hor = Integer.parseInt(h);
            double totalE = ((val * 48) * 0.026) * hor;
            int t_dias = (int) ((dias2 * 8) - dias);
            double total = ((t_dias * val) + totalE) - abon;

            System.out.println(total);
            tblNomina.setValueAt(t_dias, count, 7);
            tblNomina.setValueAt(formateador.format(total), count, 11);

            Object check = tblNomina.getValueAt(count, 12);
            boolean ch = new Boolean(String.valueOf(check));
            if (ch) {

                total_pagar = total_pagar + total;
            }
            count++;
        }
        jTextField1.setText(formateador.format(total_pagar) + "");
    }//GEN-LAST:event_btnAceptarNominaActionPerformed

    private void dpInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dpInicioMouseClicked

    }//GEN-LAST:event_dpInicioMouseClicked

    private void dpInicioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dpInicioMousePressed


    }//GEN-LAST:event_dpInicioMousePressed

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        Date fechaInicio = Date.valueOf(dpInicio.getDateStringOrSuppliedString(LocalDate.now().toString()));
        Date fechaFin = Date.valueOf(dpFin.getDateStringOrSuppliedString(LocalDate.now().toString()));

        dias = (int) ((fechaFin.getTime() - fechaInicio.getTime()) / 86400000) + 1;
        txtDias.setText(dias + "");

        diasTrabajados(dias);

        int filas = tblNomina.getRowCount();
        int count = 0;

        while (count < filas) {

            Object objid = tblNomina.getValueAt(count, 0);
            Object diasTrabajados = tblNomina.getValueAt(count, 5);
            Object diasFaltantes = tblNomina.getValueAt(count, 6);
            Object valor = tblNomina.getValueAt(count, 4);
            String v = String.valueOf(valor);
            v = v.replace(",", ".");
            v = v.replace(".", "");

            long us = convertToLong(objid);
            int dias = convertToInt(diasTrabajados);
            int faltantes = convertToInt(diasFaltantes);
            int diasTotales = dias - faltantes;
            double totalHoras = diasTotales * 8;
            double valo = Double.parseDouble(v);
            double total = totalHoras * valo;
            tblNomina.setValueAt(totalHoras, count, 7);
            tblNomina.setValueAt(formateador.format(total), count, 11);

            System.out.println(diasTotales * valo);
            count++;
        }

    }//GEN-LAST:event_btnCalcularActionPerformed

    private void guardarNominaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarNominaActionPerformed
        String dias_T = txtDias.getText();
        boolean x = false;
        boolean za = mostrarDecision("DESEA PAGAR NOMINA?");
        if (za) {
            if (!dias_T.equalsIgnoreCase("")) {
                int d = Integer.parseInt(dias_T);
                if (d > 0) {
                    int filas = tblNomina.getRowCount();
                    System.out.println(filas);
                    int count = 0;
                    while (count < filas) {
                        Object obdId = tblNomina.getValueAt(count, 0);
                        Object Nombre = tblNomina.getValueAt(count, 1);

                        Object Apellidos = tblNomina.getValueAt(count, 2);

                        Object Cedula = tblNomina.getValueAt(count, 3);

                        Object ValorDia = tblNomina.getValueAt(count, 4);
                        String v = String.valueOf(ValorDia);
                        v = v.replace(",", ".");
                        v = v.replace(".", "");
                        Object DiasTrabajados = tblNomina.getValueAt(count, 5);

                        Object DiasAsuentes = tblNomina.getValueAt(count, 6);

                        Object horas = tblNomina.getValueAt(count, 7);
                        Object abonar = tblNomina.getValueAt(count, 10);
                        String a = String.valueOf(abonar);
                        a = a.replace(",", ".");
                        a = a.replace(".", "");
                        Object TotalPagar = tblNomina.getValueAt(count, 11);
                        String t = String.valueOf(TotalPagar);
                        t = t.replace(",", ".");
                        t = t.replace(".", "");

                        Object ho = tblNomina.getValueAt(count, 8);
                        String h = String.valueOf(ho);
                        h = h.replace(",", ".");
                        h = h.replace(".", "");
                        long he = Long.parseLong(h);

                        long id = convertToLong(obdId);
                        double valorHora = Double.parseDouble(v);
                        double horasT = convertToDouble(horas);
                        int horasTrabajadas = (int) horasT;
                        double pagoTot = Double.parseDouble(t);
                        Date fechaInicio = Date.valueOf(dpInicio.getDateStringOrSuppliedString(LocalDate.now().toString()));
                        Date fechaFin = Date.valueOf(dpFin.getDateStringOrSuppliedString(LocalDate.now().toString()));
                        int DiasAuse = convertToInt(DiasAsuentes);
                        int dias_traba = convertToInt(DiasTrabajados);
                        int diasTrabajados = dias_traba - (DiasAuse / 8);

                        double abona = Double.parseDouble(a);
                        Object check = tblNomina.getValueAt(count, 12);
                        boolean ch = new Boolean(String.valueOf(check));
                        if (ch) {
                            x = servicio.registrarNomina(id, valorHora, pagoTot, fechaInicio, fechaFin, horasTrabajadas, diasTrabajados, he, abona);

                            String aq = new String(fechaInicio + "-" + fechaFin);
                            Timestamp te = new Timestamp(id);
                            long per = 8;

                            //actualizar abonado
                        }

                        Abono_PrestamoDTO abono = servicio.buscarAbonoPrestamo(id);
                        double total_abonado = abono.getTotal_abonado() + abona;
                        Date fecha = Date.valueOf(dpFin.getDateStringOrSuppliedString(LocalDate.now().toString()));
                        if (abona != 0) {
                            boolean s = servicio.registrarAbono(abono.getId(), abona, fecha);

                            Abono_PrestamoDTO abono2 = servicio.buscarAbonoPrestamo(id);
                            double total_debe = abono.getTotal_prestamo() - total_abonado;

                            System.out.println("abonado 2" + abono2.getTotal_prestamo());
                            servicio.actualizarAbonoPresta(abono2.getId(), total_abonado, total_debe);
                        }
//            System.out.println(id + "" + valorD + prestamo + pagoTotal + o + fechaInicio + fechaFin + DiasAuse);
                        count++;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccionar rango de dias");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccionar rango de dias");
            }
            if (x) {
                JOptionPane.showMessageDialog(null, "Nominas Registradas");
            } else {
                JOptionPane.showMessageDialog(null, "Las nominas no fueron guardadas");
            }
            Date fechaInicio = Date.valueOf(dpInicio.getDateStringOrSuppliedString(LocalDate.now().toString()));
            Date fechaFin = Date.valueOf(dpFin.getDateStringOrSuppliedString(LocalDate.now().toString()));
            String pago = jTextField1.getText();
            pago = pago.replace(",", ".");
            pago = pago.replace(".", "");
            double pa = Double.parseDouble(pago);
            boolean z = servicio.registrar2(fechaInicio + " - " + fechaFin, 8, pa, fechaFin);
            ArrayList<NominaDTO> nomina = servicio.listarFechaNomi(fechaInicio, fechaFin);
            String cadena = "";
            String CARPETA = "db";
            BuscadorCarpeta b = null;
            String c = b.buscar(CARPETA);

            for (NominaDTO n : nomina) {

                cadena += n.getId() + ",";

            }
            cadena = cadena.substring(0, cadena.length() - 1);
            System.out.println(cadena);

            String archivo = c + "\\NominasFechas.jasper";
            String imagen = c + "\\logo.jpeg";
            Conexion cn = new Conexion();
            JasperReport jr = null;
            try {
                Map parametro = new HashMap();
                parametro.put("ID", cadena);
                parametro.put("imagen", imagen);
                parametro.put("FEHA_INICIO", fechaInicio);
                parametro.put("FECHA_FIN", fechaFin);
                jr = (JasperReport) JRLoader.loadObjectFromFile(archivo);
                JasperPrint jp = JasperFillManager.fillReport(jr, parametro, cn.getConnection());
                JasperExportManager.exportReportToPdfFile(jp, "D:\\ReporteN.pdf");
                String file = new String("D:\\ReporteN.pdf");
                try {
                    Runtime.getRuntime().exec("cmd /c start " + file);
                } catch (IOException ex) {
                    Logger.getLogger(ifrmVentas3.class.getName()).log(Level.SEVERE, null, ex);
                }
//            JasperViewer jv = new JasperViewer(jp, false);
//            jv.setVisible(true);
//            jv.setTitle("NOMINA");
            } catch (JRException ex) {
                Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_guardarNominaActionPerformed

    private void txtValorHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorHoraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorHoraActionPerformed

    private void btnGuardarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarPagoActionPerformed
        int diasAu = 0;
        String te = comboBuscarEmpleado.getSelectedItem().toString();
        String[] parts = te.split("-");
        String part1 = parts[0]; // 123
        String part2 = parts[1];
        double pagoTot = 0;
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);

        UsuarioDTO DTO = servicio.consultarUsuario(Long.parseLong(part1));
        if (DTO != null) {

            Date fechaInicio = Date.valueOf(dpIniNomina.getDateStringOrSuppliedString(LocalDate.now().toString()));
            Date fechaFin = Date.valueOf(dpFinNomina.getDateStringOrSuppliedString(LocalDate.now().toString()));

            dias = (int) ((fechaFin.getTime() - fechaInicio.getTime()) / 86400000) + 1;
            UsuarioDTO usuario = servicio.consultarUsuario(DTO.getId());
            //long rol = usuario.getRol();
            long id = usuario.getId();
            String valor = txtValorHora.getText();
            valor = valor.replace(",", ".");
            valor = valor.replace(".", "");

            String pagoTotal = txtTotal.getText();
            pagoTotal = pagoTotal.replace(",", ".");
            pagoTotal = pagoTotal.replace(".", "");

            double valorHora = Double.parseDouble(valor);

            if (!pagoTotal.equalsIgnoreCase("")) {
                pagoTot = Double.parseDouble(pagoTotal);

                int diasTrabajados = (dias - diasAu);

                boolean x = servicio.registrarNomina(id, 0, pagoTot, fechaInicio, fechaFin, 0, 0, 0, 0);
                cadena = servicio.consultarUltimaNomina() + "";

                //actualizar abonado
                if (x) {
                    mostrarMensaje("La Nomina Se Registro");
                } else {
                    mostrarMensaje("La Nomina No Se Registro");
                }

                //Abono_PrestamoDTO abono = servicio.buscarAbonoPrestamo(usuario.getId());

                Date fecha = Date.valueOf(dpFinNomina.getDateStringOrSuppliedString(LocalDate.now().toString()));

//                txtCedulaEmpleado.setText("");
                txtNombreEmpleado.setText("");
                txtRol.setText("");
                txtValorHora.setText("");

                txtTotal.setText("");

                if (x) {
                    mostrarMensaje("Se registro la Nomina Exitosamente");
                  boolean z = servicio.registrar2(fechaInicio + " - " + fechaFin + "Diseñador", 8, pagoTot, fechaFin);

                } else {
                    mostrarMensaje("No se pudo registrar la Nomina");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Todos los campos deben estar llenos");
            }

            String archivo = c + "\\NominaUsuario.jasper";
            String imagen = c + "\\logo.jpeg";
            Conexion cn = new Conexion();
            JasperReport jr = null;
            try {
                Map parametro = new HashMap();
                parametro.put("ID", cadena);
                parametro.put("imagen", imagen);
                jr = (JasperReport) JRLoader.loadObjectFromFile(archivo);
                JasperPrint jp = JasperFillManager.fillReport(jr, parametro, cn.getConnection());
                JasperExportManager.exportReportToPdfFile(jp, "D:\\Reporte.pdf");
                String file = new String("D:\\Reporte.pdf");
                try {
                    Runtime.getRuntime().exec("cmd /c start " + file);
                } catch (IOException ex) {
                    Logger.getLogger(ifrmVentas3.class.getName()).log(Level.SEVERE, null, ex);
                }
//                JasperViewer jv = new JasperViewer(jp, false);
//                jv.setVisible(true);
//                jv.setTitle("NOMINA");
            } catch (JRException ex) {
                Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
            }

            String pago = txtTotal.getText();
            pago = pago.replace(",", ".");
            pago = pago.replace(".", "");
//            double pa = Double.parseDouble(pago);
            PanelDatosU.setVisible(false);
            PanelDatosN.setVisible(false);

        }
    }//GEN-LAST:event_btnGuardarPagoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (panelNominaGrupo.isShowing()) {
            int filaSeleccionada = tblNomina.getSelectedRow();

            if (filaSeleccionada < 0) {
                mostrarMensaje("Seleccione un empleado de la tabla");
                return;
            }

            if (!mostrarDecision("¿Desea eliminar este empleado?")) {
                return;
            }

            Object idObj = tblNomina.getValueAt(filaSeleccionada, 0);
//            mostrarMensaje(filaSeleccionada + "");

            if (idObj != null) {
                dtblNomina2.removeRow(filaSeleccionada);
                tblNomina.setModel(dtblNomina2);
                return;
            }

            //long id = Long.parseLong(String.valueOf(idObj));
            //if (servicio.eliminarProducto(id)) {
            //  actualizarTablaProductos();
            //mostrarMensaje("Producto eliminado satisfactoriamente");
            //} else {
            //  mostrarMensaje("No se pudo eliminar el producto");
            //}
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnEliminarEmpleado1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEmpleado1ActionPerformed
        llenarTablaUsuarios();
    }//GEN-LAST:event_btnEliminarEmpleado1ActionPerformed

    private void btnAbonarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbonarActionPerformed
        if (!txtAbono.getText().equalsIgnoreCase("")) {
            String id = txtIdAbonar.getText();
            long id_u = Long.parseLong(id);
            String abonar = txtAbono.getText();
            abonar = abonar.replace(",", ".");
            abonar = abonar.replace(".", "");
            double abona = Double.parseDouble(abonar);
            Date fecha = Date.valueOf(dpFechaAbonar.getDateStringOrSuppliedString(LocalDate.now().toString()));
            Abono_PrestamoDTO abono = servicio.buscarAbonoPrestamo(id_u);
            double total_abonado = abono.getTotal_abonado() + abona;
            boolean x = servicio.registrarAbono(abono.getId(), abona, fecha);

            Abono_PrestamoDTO abono2 = servicio.buscarAbonoPrestamo(id_u);
            double total_debe = abono.getTotal_prestamo() - total_abonado;
            System.out.println("abonado 2" + abono2.getTotal_prestamo());
            servicio.actualizarAbonoPresta(abono2.getId(), total_abonado, total_debe);

            if (x) {
                mostrarMensaje("El abono se registro con exito");
            } else {
                mostrarMensaje("el abono no se pudo registrar");
            }
//            txtCedulaAbonar.setText("");
            txtIdAbonar.setText("");
            txtNombeAbonar.setText("");
            txtAbono.setText("");
            txtDebeAbonar.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Debe elegir cuanto va abonar");
        }
    }//GEN-LAST:event_btnAbonarActionPerformed

    private void btnLiquidarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiquidarActionPerformed

        Object total = tblLiquidacion.getValueAt(0, 10);

        if (total != null) {
            String t = String.valueOf(total);
            t = t.replace(",", ".");
            t = t.replace(".", "");
            String id_usuario = txtIdLiquidacion.getText();
            long id = Long.parseLong(id_usuario);
            Date fechaSalida = Date.valueOf(dpFechaLiquidacion.getDateStringOrSuppliedString(LocalDate.now().toString()));
            double total_liquidacion = Double.parseDouble(t);
            Object obj = tblLiquidacion.getValueAt(0, 2);
            Object fech = tblLiquidacion.getValueAt(0, 1);
            Date fechaEntrada = (Date) fech;

            Object dias_trabajados = tblLiquidacion.getValueAt(0, 3);
            int dias = convertToInt(dias_trabajados);

//            Object horas_trabajadas = tblLiquidacion.getValueAt(0, 4);
//            int hora = convertToInt(horas_trabajadas);
            Object sueldo = tblLiquidacion.getValueAt(0, 4);
            String s = String.valueOf(sueldo);
            s = s.replace(",", ".");
            s = s.replace(".", "");
            double sueldo_basico = Double.parseDouble(s);

            Object cesantias = tblLiquidacion.getValueAt(0, 5);
            String c = String.valueOf(cesantias);
            c = c.replace(",", ".");
            c = c.replace(".", "");
            double cesan = Double.parseDouble(c);

            Object intereses = tblLiquidacion.getValueAt(0, 6);
            String i = String.valueOf(intereses);
            i = i.replace(".", "");
            i = i.replace(",", ".");
            double intere = Double.parseDouble(i);

            Object prima = tblLiquidacion.getValueAt(0, 7);
            String p = String.valueOf(prima);
            p = p.replace(".", "");
            p = p.replace(",", ".");
            double pri = Double.parseDouble(p);

            Object vacaciones = tblLiquidacion.getValueAt(0, 8);
            String v = String.valueOf(vacaciones);
            v = v.replace(",", ".");
            v = v.replace(".", "");
            double vaca = Double.parseDouble(v);

            Object bonificacion = tblLiquidacion.getValueAt(0, 9);
            String b = String.valueOf(bonificacion);
            b = b.replace(",", ".");
            b = b.replace(".", "");
            double boni = Double.parseDouble(b);
            //System.out.println(boni);

            System.out.println(dias);
            String pago = txtAnticipo.getText();
            pago = pago.replace(",", ".");
            pago = pago.replace(".", "");
//            double pa = Double.parseDouble(pago);
            boolean z = servicio.registrar2("Liquidacion" + fechaSalida, 5, total_liquidacion, fechaSalida);
            boolean x = servicio.registrarLiquidacion(id, fechaSalida, 0, dias, fechaEntrada, sueldo_basico, cesan, intere, pri,
                    vaca, total_liquidacion, boni);
            if (x) {

                mostrarMensaje("Liquidacion registrada con Exito");
            } else {
                mostrarMensaje("No se pudo registrar la liquidacion");
            }
        } else {
            mostrarMensaje("Primero tiene que calcular la liquidación,luego de haber seleccionado una cedula y fecha");
        }
    }//GEN-LAST:event_btnLiquidarActionPerformed

    private void btnBuscarUsuario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarUsuario1ActionPerformed
//        String cedula = txtCedulaNomina.getText();

        String te = comboBuscarEmpleado4.getSelectedItem().toString();
        String[] parts = te.split("-");
        String part1 = parts[0]; // 123
        String part2 = parts[1];
        UsuarioDTO DTO = servicio.consultarUsuario(Long.parseLong(part1));

        buscarCedulaNomina(DTO.getId());
    }//GEN-LAST:event_btnBuscarUsuario1ActionPerformed

    private void btnBuscarLiquidacionEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarLiquidacionEActionPerformed
//        String cedula = txtBuscarLiquidacionE.getText();
        String te = comboBuscarEmpleado5.getSelectedItem().toString();
        String[] parts = te.split("-");
        String part1 = parts[0]; // 123
        String part2 = parts[1];
        UsuarioDTO DTO = servicio.consultarUsuario(Long.parseLong(part1));

        buscarLiquidacionEmpleado(DTO.getId());
    }//GEN-LAST:event_btnBuscarLiquidacionEActionPerformed

    private void btnBuscarUsuario3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarUsuario3ActionPerformed
        String te = comboBuscarEmpleado6.getSelectedItem().toString();
        String[] parts = te.split("-");
        String part1 = parts[0]; // 123
        String part2 = parts[1];
        UsuarioDTO DTO = servicio.consultarUsuario(Long.parseLong(part1));
        buscarCedulaAbono(DTO.getId());
    }//GEN-LAST:event_btnBuscarUsuario3ActionPerformed

    private void btnBuscarUsuarioAdelantoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarUsuarioAdelantoActionPerformed
//        String cedula = this.txtBuscarAdelanto.getText();
        String te = comboBuscarEmpleado7.getSelectedItem().toString();
        String[] parts = te.split("-");
        String part1 = parts[0]; // 123
        String part2 = parts[1];
        UsuarioDTO DTO = servicio.consultarUsuario(Long.parseLong(part1));

        buscarCedulaAdelanto(DTO.getId());

    }//GEN-LAST:event_btnBuscarUsuarioAdelantoActionPerformed

    private void btnBuscarFechaNominaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFechaNominaActionPerformed
        Date fechaDesde = Date.valueOf(this.dpDesdeN.getDateStringOrSuppliedString(LocalDate.now().toString()));
        Date fechaHasta = Date.valueOf(this.dpHastaN.getDateStringOrSuppliedString(LocalDate.now().toString()));

        if (fechaDesde.toString().equalsIgnoreCase("") || fechaHasta.toString().equalsIgnoreCase("")) {

            JOptionPane.showMessageDialog(null, "Seleccionar Fecha");
        } else {

            //String fecha= 
//           Date h= Date.valueOf(dpHasta.getDateStringOrEmptyString().toString());
            buscarFechaNomina(fechaDesde, fechaHasta);
            // buscarFechaFactura(fechaDesde);
        }
    }//GEN-LAST:event_btnBuscarFechaNominaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        txtCedulaNomina.setText("");
        txtNombreEmpleado.setText("");
        txtRol.setText("");
        txtValorHora.setText("");

        txtTotal.setText("");
        PanelDatosN.setVisible(false);
        PanelDatosU.setVisible(false);
        labelTotal.setVisible(false);
        txtTotal.setVisible(false);
        btnGuardarPago.setVisible(false);
        cerrar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
//        String cedula = txtCedulaEmpleado.getText();
//
//        UsuarioDTO DTO = servicio.buscarUsuarioCedula(cedula);
//        if (DTO != null) {
//            PanelDatosU.setVisible(true);
//            PanelDatosN.setVisible(true);
//            buscarEmpleado(cedula);
//            txtTotal.setVisible(true);
//        }

        String te = comboBuscarEmpleado.getSelectedItem().toString();
        String[] parts = te.split("-");
        String part1 = parts[0]; // 123
        String part2 = parts[1];
        //UsuarioDTO DTO = servicio.consultarUsuario(Long.parseLong(part1));
        //if (DTO != null) {
            PanelDatosU.setVisible(true);
            PanelDatosN.setVisible(true);
            buscarEmpleado(Long.parseLong(part1));
        //}


    }//GEN-LAST:event_jButton2ActionPerformed
    DecimalFormat formateador = new DecimalFormat("###,###");
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Date fechaInicio = Date.valueOf(dpIniNomina.getDateStringOrSuppliedString(LocalDate.now().toString()));
        Date fechaFin = Date.valueOf(dpFinNomina.getDateStringOrSuppliedString(LocalDate.now().toString()));

        int abonar = 0;

        dias = (int) ((fechaFin.getTime() - fechaInicio.getTime()) / 86400000) + 1;
        int d = Integer.parseInt(jTextField2.getText());
        int de = Integer.parseInt(jTextField3.getText());

        double a = (de - ((de * d) / 100.0));
        txtTotal.setVisible(true);
        txtTotal.setText(formateador.format(a) + "");
        btnGuardarPago.setVisible(true);


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        String te = comboBuscarEmpleado1.getSelectedItem().toString();
        String[] parts = te.split("-");
        String part1 = parts[0]; // 123
        String part2 = parts[1];
        UsuarioDTO DTO = servicio.consultarUsuario(Long.parseLong(part1));

        if (DTO != null) {

            buscarEmpleadoNomina(DTO.getId());
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtNombeAbonarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombeAbonarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombeAbonarActionPerformed

    private void txtDebeAbonarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDebeAbonarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDebeAbonarActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String te = comboBuscarEmpleado2.getSelectedItem().toString();
        String[] parts = te.split("-");
        String part1 = parts[0]; // 123
        String part2 = parts[1];
        UsuarioDTO DTO = servicio.consultarUsuario(Long.parseLong(part1));

        if (DTO != null) {
            txtIdAbonar.setText(DTO.getId() + "");
            txtNombeAbonar.setText(DTO.getNombres() + " " + DTO.getApellidos());

            Abono_PrestamoDTO abono = servicio.buscarAbonoPrestamo(DTO.getId());
            double debe = abono.getTotal_debe();
            txtDebeAbonar.setText(formateador.format(debe));
            buscarEmpleadoNomina(DTO.getId());
            System.out.println("debe" + debe);
        } else {
            JOptionPane.showMessageDialog(null, "Cedula  erronea");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtCedulaNominaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaNominaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCedulaNominaActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
//        crearComboEditable(comboBuscarEmpleado, "USUARIOS");
        llenarComboMaterial();
       // llenarComboMaterial2();
      //  llenarComboMaterial3();
      // llenarComboMaterial4();
       // llenarComboMaterial5();
        llenarComboMaterial6();
        llenarComboMaterial7();
        llenarComboMaterial8();
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnCalcularLiquidacion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularLiquidacion1ActionPerformed
        Date fechaInicio = Date.valueOf(dpFechaLiquidacion1.getDateStringOrSuppliedString(LocalDate.now().toString()));
        int filas = tblLiquidacion1.getRowCount();
        int count = 0;
        while (count < filas) {
            Object cesan = tblLiquidacion1.getValueAt(count, 6);
            Object inte = tblLiquidacion1.getValueAt(count, 7);
            Object pri = tblLiquidacion1.getValueAt(count, 8);
            Object vaca = tblLiquidacion1.getValueAt(count, 9);
            Object Boni = tblLiquidacion1.getValueAt(count, 10);
            Object id = tblLiquidacion1.getValueAt(count, 0);

            long i = Long.parseLong(String.valueOf(id));
//            
            UsuarioDTO u = servicio.consultarUsuario(i);
//            String cedula = u.getDocumento();
            ArrayList<NominaDTO> nomina = servicio.buscarCedulaNomina(u.getId());
            Date d = null;
            UsuarioDTO usuario = servicio.buscarFechaIngreso(u.getId());

            RolDTO rol = servicio.consultarRol(usuario.getRol());

//            System.out.println(servicio.totalNomina(cedula) + "fecha inicio");
            dias = (int) ((fechaInicio.getTime() - usuario.getFecha_ingreso().getTime()) / 86400000);

            Object horas = tblLiquidacion1.getValueAt(0, 4);
//            int hora = convertToInt(horas);

            double valor = rol.getValor();

            double meses_trabajados = (double) dias / 30;
            System.out.println(meses_trabajados + "meses");
            double total_nomina = servicio.totalNomina(u.getId());

            double total_sueldo = total_nomina / meses_trabajados;

            double cesantia = (total_sueldo * dias) / 360;
            double cesantias_intere = (cesantia * 0.12 * dias) / 360;
            double prim = (total_sueldo * dias) / 360;
            double vacacion = (dias * (total_sueldo / 2)) / 360;
            double bonificacion = convertToDouble(Boni);

            //int dias = servicio.diasTrabajados(usuario.getId());
            double total_liquidacion = cesantia + cesantias_intere + prim + vacacion + bonificacion;
            tblLiquidacion1.setValueAt(dias, count, 4);
            //tblLiquidacion.setValueAt(hora, 0, 4);
            tblLiquidacion1.setValueAt(formateador.format(total_sueldo), count, 5);
            tblLiquidacion1.setValueAt(formateador.format(cesantia), count, 6);
            tblLiquidacion1.setValueAt(formateador.format(cesantias_intere), count, 7);
            tblLiquidacion1.setValueAt(formateador.format(prim), count, 8);
            tblLiquidacion1.setValueAt(formateador.format(vacacion), count, 9);
            tblLiquidacion1.setValueAt(formateador.format(total_liquidacion), count, 11);
            tblLiquidacion1.setValueAt(fechaInicio, count, 3);

            System.out.println(fechaInicio);
            count++;
        }

    }//GEN-LAST:event_btnCalcularLiquidacion1ActionPerformed

    private void btnLiquidar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiquidar1ActionPerformed
        int filas = tblLiquidacion1.getRowCount();
        int count = 0;
        while (count < filas) {
            Object total = tblLiquidacion1.getValueAt(count, 11);
            Object is = tblLiquidacion1.getValueAt(count, 0);

            if (total != null) {
                String t = String.valueOf(total);
                t = t.replace(",", ".");
                t = t.replace(".", "");
                String id_usuario = String.valueOf(is);
                long id = Long.parseLong(id_usuario);
                Date fechaSalida = Date.valueOf(dpFechaLiquidacion1.getDateStringOrSuppliedString(LocalDate.now().toString()));
                double total_liquidacion = Double.parseDouble(t);
                Object obj = tblLiquidacion1.getValueAt(count, 3);
                Object fech = tblLiquidacion1.getValueAt(count, 2);
                Date fechaEntrada = (Date) fech;

                Object dias_trabajados = tblLiquidacion1.getValueAt(count, 4);
                int dias = convertToInt(dias_trabajados);

//            Object horas_trabajadas = tblLiquidacion.getValueAt(0, 4);
//            int hora = convertToInt(horas_trabajadas);
                Object sueldo = tblLiquidacion1.getValueAt(count, 5);
                String s = String.valueOf(sueldo);
                s = s.replace(",", ".");
                s = s.replace(".", "");
                double sueldo_basico = Double.parseDouble(s);

                Object cesantias = tblLiquidacion1.getValueAt(count, 6);
                String c = String.valueOf(cesantias);
                c = c.replace(",", ".");
                c = c.replace(".", "");
                double cesan = Double.parseDouble(c);

                Object intereses = tblLiquidacion1.getValueAt(count, 7);
                String i = String.valueOf(intereses);
                i = i.replace(",", ".");
                i = i.replace(".", "");
                double intere = Double.parseDouble(i);

                Object prima = tblLiquidacion1.getValueAt(count, 8);
                String p = String.valueOf(prima);
                p = p.replace(",", ".");
                p = p.replace(".", "");
                double pri = Double.parseDouble(p);

                Object vacaciones = tblLiquidacion1.getValueAt(count, 9);
                String v = String.valueOf(vacaciones);
                v = v.replace(",", ".");
                v = v.replace(".", "");
                double vaca = Double.parseDouble(v);

                Object bonificacion = tblLiquidacion1.getValueAt(count, 10);
                String b = String.valueOf(bonificacion);
                b = b.replace(",", ".");
                b = b.replace(".", "");
                double boni = Double.parseDouble(b);
                //System.out.println(boni);

                System.out.println(dias);
                boolean x = servicio.registrarLiquidacion(id, fechaSalida, 0, dias, fechaEntrada, sueldo_basico, cesan, intere, pri,
                        vaca, total_liquidacion, boni);
                if (x) {
                    mostrarMensaje("Liquidacion registrada con Exito");
                } else {
                    mostrarMensaje("No se pudo registrar la liquidacion");
                }
            } else {
                mostrarMensaje("Primero tiene que calcular la liquidación,luego de haber seleccionado una cedula y fecha");
            }
            count++;
        }
    }//GEN-LAST:event_btnLiquidar1ActionPerformed

    private void btnBuscarFechaNomina1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFechaNomina1ActionPerformed
        Date fechaDesde = Date.valueOf(this.dpDesdeN1.getDateStringOrSuppliedString(LocalDate.now().toString()));
        Date fechaHasta = Date.valueOf(this.dpHastaN1.getDateStringOrSuppliedString(LocalDate.now().toString()));

        if (fechaDesde.toString().equalsIgnoreCase("") || fechaHasta.toString().equalsIgnoreCase("")) {

            JOptionPane.showMessageDialog(null, "Seleccionar Fecha");
        } else {

            //String fecha= 
//           Date h= Date.valueOf(dpHasta.getDateStringOrEmptyString().toString());
            buscarFechaLiquidaciones(fechaDesde, fechaHasta);
            // buscarFechaFactura(fechaDesde);
        }
    }//GEN-LAST:event_btnBuscarFechaNomina1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void btnEliminarEmpleado2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEmpleado2ActionPerformed
        llenarTablaUsuariosLiquidacion();
    }//GEN-LAST:event_btnEliminarEmpleado2ActionPerformed

    private void comboBuscarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBuscarEmpleadoActionPerformed

//        ArrayList<UsuarioDTO> a = servicio.buscarUsuario(te);

    }//GEN-LAST:event_comboBuscarEmpleadoActionPerformed

    private void comboBuscarEmpleado1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBuscarEmpleado1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBuscarEmpleado1ActionPerformed

    private void comboBuscarEmpleado2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBuscarEmpleado2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBuscarEmpleado2ActionPerformed

    private void comboBuscarEmpleado3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBuscarEmpleado3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBuscarEmpleado3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String te = comboBuscarEmpleado3.getSelectedItem().toString();
        String[] parts = te.split("-");
        String part1 = parts[0]; // 123
        String part2 = parts[1];
        UsuarioDTO DTO = servicio.consultarUsuario(Long.parseLong(part1));

        buscarEmpleadoLiquidacion(DTO.getId());

        UsuarioDTO usuario = servicio.consultarUsuario(DTO.getId());
        if (usuario != null) {
            String id = txtIdLiquidacion.getText();
            long i = Long.parseLong(id);

            int dias = servicio.diasTrabajados(i);
            //int aus = servicio.AusenteDias(i);
            int dias_total = dias;
            int horas_trabajadas = servicio.HorasTrabajadas(i);
            long idR = usuario.getRol();

            RolDTO rol = servicio.consultarRol(idR);
            double valor = rol.getValor();
            double total_sueldo = valor * 56;
            double cesantias = (total_sueldo * horas_trabajadas) / 360;
            double cesantias_interes = cesantias * 0.12;
            double prima = (total_sueldo * horas_trabajadas) / 360;
            double vacaciones = (total_sueldo * horas_trabajadas) / 720;

            // tblLiquidacion.setValueAt(dias, 0, 3);
            //tblLiquidacion.setValueAt(horas_trabajadas, 0, 4);
            //tblLiquidacion.setValueAt(total_sueldo, 0, 5);
            //tblLiquidacion.setValueAt(cesantias, 0, 6);
            //tblLiquidacion.setValueAt(cesantias_interes, 0, 7);
            //tblLiquidacion.setValueAt(prima, 0, 8);
            tblLiquidacion.setValueAt(0, 0, 9);
        }// TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void comboBuscarEmpleado4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBuscarEmpleado4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBuscarEmpleado4ActionPerformed

    private void comboBuscarEmpleado5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBuscarEmpleado5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBuscarEmpleado5ActionPerformed

    private void comboBuscarEmpleado6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBuscarEmpleado6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBuscarEmpleado6ActionPerformed

    private void comboBuscarEmpleado7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBuscarEmpleado7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBuscarEmpleado7ActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        llenarComboMaterial();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnActualizar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizar1ActionPerformed
        llenarComboMaterial2();
    }//GEN-LAST:event_btnActualizar1ActionPerformed

    private void btnActualizar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizar2ActionPerformed
        llenarComboMaterial3();
    }//GEN-LAST:event_btnActualizar2ActionPerformed

    private void btnActualizar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizar3ActionPerformed
        llenarComboMaterial4();
    }//GEN-LAST:event_btnActualizar3ActionPerformed

    private void btnActualizar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizar4ActionPerformed
        llenarComboMaterial5();
    }//GEN-LAST:event_btnActualizar4ActionPerformed

    private void btnActualizar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizar5ActionPerformed
        llenarComboMaterial8();
    }//GEN-LAST:event_btnActualizar5ActionPerformed

    private void btnActualizar6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizar6ActionPerformed
        llenarComboMaterial7();

    }//GEN-LAST:event_btnActualizar6ActionPerformed

    private void btnActualizar7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizar7ActionPerformed
        llenarComboMaterial6();
    }//GEN-LAST:event_btnActualizar7ActionPerformed

    private void tpPestanaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tpPestanaMouseClicked
        if(tpPestana.getSelectedIndex()==1){
            llenarComboMaterial2();
        }
        if(tpPestana.getSelectedIndex()==2){
            llenarComboMaterial3();
        }
        if(tpPestana.getSelectedIndex()==3){
            llenarComboMaterial4();
        }
        if(tpPestana.getSelectedIndex()==4){
            llenarComboMaterial5();
        }
       
        if(tpPestana.getSelectedIndex()==9){
          llenarTablaUsuarios();
        }
    }//GEN-LAST:event_tpPestanaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelDatosN;
    private javax.swing.JPanel PanelDatosU;
    private javax.swing.JButton btnAbonar;
    private javax.swing.JButton btnAceptarNomina;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnActualizar1;
    private javax.swing.JButton btnActualizar2;
    private javax.swing.JButton btnActualizar3;
    private javax.swing.JButton btnActualizar4;
    private javax.swing.JButton btnActualizar5;
    private javax.swing.JButton btnActualizar6;
    private javax.swing.JButton btnActualizar7;
    private javax.swing.JButton btnBuscarFechaNomina;
    private javax.swing.JButton btnBuscarFechaNomina1;
    private javax.swing.JButton btnBuscarLiquidacionE;
    private javax.swing.JButton btnBuscarUsuario1;
    private javax.swing.JButton btnBuscarUsuario3;
    private javax.swing.JButton btnBuscarUsuarioAdelanto;
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnCalcularLiquidacion;
    private javax.swing.JButton btnCalcularLiquidacion1;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarEmpleado1;
    private javax.swing.JButton btnEliminarEmpleado2;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnGuardarPago;
    private javax.swing.JButton btnLiquidar;
    private javax.swing.JButton btnLiquidar1;
    private javax.swing.JComboBox<Object> comboBuscarEmpleado;
    private javax.swing.JComboBox<Object> comboBuscarEmpleado1;
    private javax.swing.JComboBox<Object> comboBuscarEmpleado2;
    private javax.swing.JComboBox<Object> comboBuscarEmpleado3;
    private javax.swing.JComboBox<Object> comboBuscarEmpleado4;
    private javax.swing.JComboBox<Object> comboBuscarEmpleado5;
    private javax.swing.JComboBox<Object> comboBuscarEmpleado6;
    private javax.swing.JComboBox<Object> comboBuscarEmpleado7;
    private com.github.lgooddatepicker.components.DatePicker dpDesdeN;
    private com.github.lgooddatepicker.components.DatePicker dpDesdeN1;
    private com.github.lgooddatepicker.components.DatePicker dpFecha;
    private com.github.lgooddatepicker.components.DatePicker dpFechaAbonar;
    private com.github.lgooddatepicker.components.DatePicker dpFechaLiquidacion;
    private com.github.lgooddatepicker.components.DatePicker dpFechaLiquidacion1;
    private com.github.lgooddatepicker.components.DatePicker dpFin;
    private com.github.lgooddatepicker.components.DatePicker dpFinNomina;
    private com.github.lgooddatepicker.components.DatePicker dpHastaN;
    private com.github.lgooddatepicker.components.DatePicker dpHastaN1;
    private com.github.lgooddatepicker.components.DatePicker dpIniNomina;
    private com.github.lgooddatepicker.components.DatePicker dpInicio;
    private javax.swing.JButton guardarNomina;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JLabel labelTotal1;
    private javax.swing.JLabel labelTotal2;
    private javax.swing.JPanel panelDatosAdelantos;
    private javax.swing.JPanel panelNominaGrupo;
    private javax.swing.JTable tblAbonado;
    private javax.swing.JTable tblAdelanto;
    private javax.swing.JTable tblBuscarNominaFecha;
    private javax.swing.JTable tblBuscarNominaFecha1;
    private javax.swing.JTable tblLiquidacion;
    private javax.swing.JTable tblLiquidacion1;
    private javax.swing.JTable tblLiquidacionE;
    private javax.swing.JTable tblNomina;
    private javax.swing.JTable tblNominaCedula;
    private javax.swing.JTextArea textAreaConcepto;
    private javax.swing.JTabbedPane tpPestana;
    private javax.swing.JTextField txtAbono;
    private javax.swing.JTextField txtAnticipo;
    private javax.swing.JTextField txtCedulaNomina;
    private javax.swing.JTextField txtDebe;
    private javax.swing.JTextField txtDebeAbonar;
    private javax.swing.JTextField txtDias;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIdAbonar;
    private javax.swing.JTextField txtIdLiquidacion;
    private javax.swing.JTextField txtNombeAbonar;
    private javax.swing.JTextField txtNombreAdelanto;
    private javax.swing.JTextField txtNombreEmpleado;
    private javax.swing.JTextField txtRol;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtValorHora;
    // End of variables declaration//GEN-END:variables
}
