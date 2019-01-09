/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.view;

import com.zapateria.main.dao.Abono_PrestamoDAO;
import com.zapateria.main.dao.ConexionDAO;
import com.zapateria.main.dto.AbonoDTO;
import com.zapateria.main.dto.Abono_PrestamoDTO;
import com.zapateria.main.dto.ClienteDTO;
import com.zapateria.main.dto.FacturaDTO;
import com.zapateria.main.dto.LiquidacionDTO;
import com.zapateria.main.dto.NominaDTO;
import com.zapateria.main.dto.PagoDTO;
import com.zapateria.main.dto.PrestamoDTO;
import com.zapateria.main.dto.ProductoDTO;
import com.zapateria.main.dto.RegistroDTO;
import com.zapateria.main.dto.RolDTO;
import com.zapateria.main.dto.UsuarioDTO;
import com.zapateria.main.dto.VentaDTO;

import com.zapateria.main.interfaces.IAbonoPrestamoDAO;
import com.zapateria.main.util.Conexion;
import com.zapateria.main.reportes.Reportes;
import com.zapateria.main.util.BuscadorCarpeta;
import static com.zapateria.main.view.MarcoInternoPersonalizado.REGEX_ENTERO_POSITIVO;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

import static org.eclipse.persistence.config.ParameterDelimiterType.Hash;

/**
 *
 * @author Diego
 */
public class ifrmReportes extends MarcoInternoPersonalizado {

    private static final long serialVersionUID = -4521190713135285108L;

    /**
     * Creates new form ifrmNomina
     */
    Date fechaDesde;
    int dias;
    private static final String NOMINA = "NOMINA", LIQUIDACION = "LIQUIDACION",
            ADMINNOMINAS = "ADMINNOMINAS", ADELANTOS = "ADELANTOS ";

    private final static String[] NOMBRES_COLUMNAS = {"CODIGO NOMINA", "NOMBRE", "APELLIDOS", "CEDULA", "NOMBRE USUARIO", "ROL", "NOMINA DESDE", "NOMINA HASTA", "VALOR HORA", "DIAS TRABAJADOS", "HORAS TRABAJADAS",
        "HORAS EXTRAS", "ABONADO", "TOTAL PAGADO"};

    private DefaultTableModel dtblNomina;

    private UsuarioDTO UsuarioDTO = new UsuarioDTO();

    public ifrmReportes() {
        initComponents();
        //llenarTablaUsuarios();
       // llenarTablaUsuarios();
        //llenarLiquidaciones();
        //llenarDeudas();

        setTitle("Reportes");
        System.out.println("entro a nomina");
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
        //Llenando la tabla
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
    DecimalFormat formateador = new DecimalFormat("###,###.###");

    public void llenarComboMaterial5() {

        comboBuscarEmpleado4.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<UsuarioDTO> listar = servicio.listarUsuarios();

        for (UsuarioDTO n : listar) {
            comboBuscarEmpleado4.addItem(n.getId() + "-" + n.getNombres() +" "+n.getApellidos());
        }
    }
     public void llenarComboMaterial6() {

        comboBuscarEmpleado5.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<UsuarioDTO> listar = servicio.listarUsuarios();

        for (UsuarioDTO n : listar) {
            comboBuscarEmpleado5.addItem(n.getId() + "-" + n.getNombres()+" "+n.getApellidos());
        }
    }
     public void llenarComboMaterial7() {

        comboBuscarEmpleado6.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<UsuarioDTO> listar = servicio.listarUsuarios();

        for (UsuarioDTO n : listar) {
            comboBuscarEmpleado6.addItem(n.getId() + "-" + n.getNombres()+" "+n.getApellidos());
        }
    }
     public void llenarComboMaterial8() {

        comboBuscarEmpleado7.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<UsuarioDTO> listar = servicio.listarUsuarios();

        for (UsuarioDTO n : listar) {
            comboBuscarEmpleado7.addItem(n.getId() + "-" + n.getNombres()+" "+n.getApellidos());
        }
    }
     
     public void llenarComboMaterial9() {

        comboBuscarEmpleado8.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<UsuarioDTO> listar = servicio.listarUsuarios();

        for (UsuarioDTO n : listar) {
            comboBuscarEmpleado8.addItem(n.getId() + "-" + n.getNombres()+" "+n.getApellidos());
        }
    }
    private void buscarFechaFact(Date FechaD, Date FechaH) {

        final String[] columnas = {"Codigo", "Fecha ", "Iva", "Total pagar", "Total  pagado",
            "Cliente", "Usuario", "Debe", "Forma de pago", "Factura Iva"};
        //configurando los eventos de la tabla
        dtblNomina = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2;
            }
        };

//        Llenando la tabla
        servicio.listarIngresosF(FechaD, FechaH).forEach((FacturaDTO factura) -> {
            dtblNomina.addRow(factura.toArray());
        });
        tblIngreso.setModel(dtblNomina);

        ArrayList<PagoDTO> abono = servicio.listarFechaAbo(FechaD, FechaH);
        System.out.println("abonoooooooo" + abono);
        System.out.println(abono.size() + "tamaño");
//        while(ab<abono.size()){
//         tblIngreso.addRowSelectionInterval(3, 8);

        for (PagoDTO a : abono) {
             ArrayList<FacturaDTO> r = servicio.buscarFactura(a.getFactura());
            String nombre = "";
            for (FacturaDTO re : r) {
                ClienteDTO c = servicio.consultarCliente(re.getCliente());
                nombre = c.getNombres() + " " + c.getApellidos();
            }
            System.out.println("factura" + a.getRegist());
            ArrayList<RegistroDTO> f = servicio.listarRegistro(a.getRegist());
            System.out.println("22" + f);
            long idC = 0, idU = 0, numeroCuots = 0, cuotasPagadas = 0;
            double iva = 0, totalPagar = 0, totalPagado = 0;
            Date d = null;
           
            ClienteDTO cliente = servicio.consultarCliente(idC);
            UsuarioDTO u = servicio.consultarUsuario(idU);
            System.out.println(a.getAbono());
            System.out.println(cliente + "" + u + "" + a.getId());
            dtblNomina.addRow(new Object[]{a.getId(),a.getFecha(),"","Factura:"+a.getFactura(),formateador.format(a.getAbono()),nombre, "" ,"","ABONO"});

//            }
        }

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

    public void listarUsuarios() {

    }

    private void BuscarValor(long id) {
        double valor = servicio.BuscarValor(id);

    }

    private void registrarAdelanto(long id, double valor, Date fecha, String concepto) {

        servicio.registrarAdelanto(id, valor, fecha, concepto);

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
        double totalPago=0;
        //Llenando la tabla
        Object[] object = new Object[14];
        ArrayList<NominaDTO> n = servicio.buscarCedulaNomina(cedula);
        System.out.println(n.size());
        for (NominaDTO no : n) {
            //UsuarioDTO u = servicio.consultarUsuario(no.getUsuario());
           // RolDTO rol = servicio.consultarRol(u.getRol());
            object[0] = no.getId();
            object[1] = no.getNombre();
            object[2] = no.getApellido();
            object[3] = no.getDocumento();
            object[4] = no.getNombreUsuario();
            object[5] = no.getRol();
            object[6] = no.getFecha_inicio();
            object[7] = no.getFecha_pago();
            object[8] = formateador.format(no.getSalario());
            object[9] = no.getDias_trabajados();
            object[10] = no.getHorasTrabajadas();
            object[11] = no.getHorasE();
            object[12] = formateador.format(no.getAbono());
            object[13] = formateador.format(no.getPago_total());
            totalPago=no.getPago_total()+totalPago;
            dtblNomina.addRow(object);
            tblNominaCedula.setModel(dtblNomina);

            dtblNomina.addTableModelListener((TableModelEvent e) -> {

                long id;
            });
        }
        txtTotalPagado.setText(formateador.format(totalPago));
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

        final String[] columnas = {"CODIGO PRESTAMO", "VALOR", "FECHA", "CONCEPTO"};
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

    public int cantidad() {
        int a = servicio.cantidadLiquidacion();
        int b = servicio.cantidadNomina();

        int total = a + b;
        return total;
    }

    public void actualizarTablaUsuarios() {
        tblGeneral.removeAll();
        dtblNomina = null;
        llenarTablaUsuarios();

    }

    private void llenarTablaUsuarios() {

        // Configurando los eventos de edicion de la tabla
        dtblNomina = new DefaultTableModel(null, NOMBRES_COLUMNAS) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0;
            }
        };
        // Llenando la tabla usuarios

        Object[] object = new Object[15];
        //servicio.listarUsuariosNomina()
        //      .forEach((UsuarioDTO usuario) -> {
        //        dtblNomina.addRow(usuario.toArray());
        //      

        for (NominaDTO n : servicio.listarNomin()) {
            System.out.println(n.getUsuario());
            //UsuarioDTO d = servicio.consultarUsuario(n.getUsuario());
//            System.out.println(d.getNombres()+n.getUsuario());
         //if(d!=null){   
           // RolDTO rol = servicio.consultarRol(d.getRol());
            String nombre = n.getNombre();
            String apellido = n.getApellido();
            String usuario = n.getNombreUsuario();
            String documento = n.getDocumento();
            object[0] = n.getId();
            object[1] = nombre;
            object[2] = apellido;
            object[3] = documento;
            object[4] = usuario;
            object[5] = n.getRol();
            object[6] = n.getFecha_inicio();
            object[7] = n.getFecha_pago();
            object[8] = formateador.format(n.getSalario());
            object[9] = n.getDias_trabajados();
            object[10] = n.getHorasTrabajadas();
            object[11] = n.getHorasE();
            object[12] = formateador.format(n.getAbono());
            object[13] = formateador.format(n.getPago_total());

            //  object[10] = "0";
            dtblNomina.addRow(object);
         //}
        }

        //});
        //Agregando el modelo de la tabla usuarios
        tblGeneral.setModel(dtblNomina);

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
                        long id = Long.parseLong(String.valueOf(tblGeneral.getValueAt(e.getFirstRow(), 0)));
                        String nombres = String.valueOf(tblGeneral.getValueAt(e.getFirstRow(), 1));
                        String documento = String.valueOf(tblGeneral.getValueAt(e.getFirstRow(), 2));
                        String valor = String.valueOf(tblGeneral.getValueAt(e.getFirstRow(), 4));
                        String nombreUsuario = String.valueOf(tblGeneral.getValueAt(e.getFirstRow(), 4));

                        //
                    }
                }
        );

        agregarJComboBoxAColumna(tblGeneral, 5, servicio.listarRoles().toArray());

        aplicarEventosPersonalizados(tblGeneral);
    }

    private void llenarLiquidaciones() {

        final String[] columnas = {"Codigo Liquidacion", "Nombre", "apellido", "sueldo_basico", "fecha_entrada", "fecha_salida", "dias_trabajados", "cesantias",
            "intereses_cesantias", "prima_servicio_", "vacaciones", "bonificacion",
            "valor_liquidacion"};
        // Configurando los eventos de edicion de la tabla
        dtblNomina = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0;
            }
        };
        // Llenando la tabla usuarios

        Object[] object = new Object[13];
        //servicio.listarUsuariosNomina()
        //      .forEach((UsuarioDTO usuario) -> {
        //        dtblNomina.addRow(usuario.toArray());
        //      

        for (LiquidacionDTO liquidacion : servicio.listarLiquida()) {
            UsuarioDTO usuario = servicio.consultarUsuario(liquidacion.getUsuario());
            //RolDTO rol=servicio.consultarRol(d.getRol());
            //String nombre = d.getNombreUsuario();
            //String apellido=d.getApellidos();
            //String usuario=d.getNombreUsuario();
            //String documento=d.getDocumento();
            if(usuario!=null){
            object[0] = liquidacion.getId();
            object[1] = usuario.getNombres();
            object[2] = usuario.getApellidos();
            object[3] = formateador.format(liquidacion.getSueldo_basico());
            object[4] = liquidacion.getFecha_entrada();
            object[5] = liquidacion.getFecha_salida();
            object[6] = liquidacion.getDias_trabajados();
            object[7] = formateador.format(liquidacion.getCesantias());
            object[8] = formateador.format(liquidacion.getIntereses_cesantias());
            object[9] = formateador.format(liquidacion.getPrima_servicio_());
            object[10] = formateador.format(liquidacion.getVacaciones());
            object[11] = formateador.format(liquidacion.getBonificacion());
            object[12] = formateador.format(liquidacion.getValor_liquidacion());

            //  object[10] = "0";
            dtblNomina.addRow(object);
            }
        }

        //});
        //Agregando el modelo de la tabla usuarios
        tblLiquidaciones.setModel(dtblNomina);

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
                        long id = Long.parseLong(String.valueOf(tblGeneral.getValueAt(e.getFirstRow(), 0)));
                        String nombres = String.valueOf(tblGeneral.getValueAt(e.getFirstRow(), 1));
                        String documento = String.valueOf(tblGeneral.getValueAt(e.getFirstRow(), 2));
                        String valor = String.valueOf(tblGeneral.getValueAt(e.getFirstRow(), 4));
                        String nombreUsuario = String.valueOf(tblGeneral.getValueAt(e.getFirstRow(), 4));

                        //
                    }
                }
        );

        agregarJComboBoxAColumna(tblGeneral, 5, servicio.listarRoles().toArray());

        aplicarEventosPersonalizados(tblGeneral);
    }

    public void llenarDeudasCedula(long cedula) {
        final String[] columnas = {"Prestado", "Abonado", "Debe"};

        //UsuarioDTO usuario = servicio.consultarUsuario(cedula);
        // Configurando los eventos de edicion de la tabla
        dtblNomina = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2;
            }
        };
        //Llenando la tabla
        servicio.listarDebeCedula(cedula).forEach((Abono_PrestamoDTO prestamo) -> {
            dtblNomina.addRow(prestamo.toArray());
        });
        tblDeudaEmpleadosCedula.setModel(dtblNomina);

        dtblNomina.addTableModelListener((TableModelEvent e) -> {

            long id;
        });

        agregarJComboBoxAColumna(tblDeudaEmpleados, 5, servicio.listarRoles().toArray());

        aplicarEventosPersonalizados(tblGeneral);
    }

    private void llenarDeudas() {

        final String[] columnas = {"Codigo Empleado", "Nombre", "apellido", "Prestado", "Abonado",
            "Debe"};
        // Configurando los eventos de edicion de la tabla
        dtblNomina = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0;
            }
        };
        // Llenando la tabla usuarios

        Object[] object = new Object[13];
        //servicio.listarUsuariosNomina()
        //      .forEach((UsuarioDTO usuario) -> {
        //        dtblNomina.addRow(usuario.toArray());
        //      

        for (Abono_PrestamoDTO abono_prestamo : servicio.listarDebe()) {
            UsuarioDTO usuario = servicio.consultarUsuario(abono_prestamo.getUsuario());
            //RolDTO rol=servicio.consultarRol(d.getRol());
            //String nombre = d.getNombreUsuario();
            //String apellido=d.getApellidos();
            //String usuario=d.getNombreUsuario();
            //String documento=d.getDocumento();
            object[0] = abono_prestamo.getUsuario();
            object[1] = usuario.getNombres();
            object[2] = usuario.getApellidos();
            object[3] = formateador.format(abono_prestamo.getTotal_prestamo());
            object[4] = formateador.format(abono_prestamo.getTotal_abonado());
            object[5] = formateador.format(abono_prestamo.getTotal_debe());

            //  object[10] = "0";
            dtblNomina.addRow(object);

        }

        //});
        //Agregando el modelo de la tabla usuarios
        tblDeudaEmpleados.setModel(dtblNomina);

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
                        long id = Long.parseLong(String.valueOf(tblGeneral.getValueAt(e.getFirstRow(), 0)));
                        String nombres = String.valueOf(tblGeneral.getValueAt(e.getFirstRow(), 1));
                        String documento = String.valueOf(tblGeneral.getValueAt(e.getFirstRow(), 2));
                        String valor = String.valueOf(tblGeneral.getValueAt(e.getFirstRow(), 4));
                        String nombreUsuario = String.valueOf(tblGeneral.getValueAt(e.getFirstRow(), 4));

                        //
                    }
                }
        );

        agregarJComboBoxAColumna(tblGeneral, 5, servicio.listarRoles().toArray());

        aplicarEventosPersonalizados(tblGeneral);
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
        jPanel4 = new javax.swing.JPanel();
        btnBuscarUsuario1 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblNominaCedula = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        comboBuscarEmpleado4 = new javax.swing.JComboBox<>();
        jButton13 = new javax.swing.JButton();
        txtTotalPagado = new javax.swing.JTextField();
        lbDocumento2 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblAdelanto = new javax.swing.JTable();
        btnBuscarUsuarioAdelanto = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        comboBuscarEmpleado5 = new javax.swing.JComboBox<>();
        jButton16 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblAbonado = new javax.swing.JTable();
        btnBuscarUsuario3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        comboBuscarEmpleado6 = new javax.swing.JComboBox<>();
        jButton17 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblLiquidacionE = new javax.swing.JTable();
        btnBuscarLiquidacionE = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        comboBuscarEmpleado7 = new javax.swing.JComboBox<>();
        jButton18 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tblGeneral = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        btnActualizarTNomi = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tblLiquidaciones = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        btnActualizarLiquidaciones = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tblDeudaEmpleados = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        btnActualizarTDeudas = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblBuscarNominaFecha = new javax.swing.JTable();
        dpDesdeN = new com.github.lgooddatepicker.components.DatePicker();
        dpHastaN = new com.github.lgooddatepicker.components.DatePicker();
        btnBuscarFechaNomina = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnBuscarFechaNomina3 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        tblDeudaEmpleadosCedula = new javax.swing.JTable();
        jButton15 = new javax.swing.JButton();
        comboBuscarEmpleado8 = new javax.swing.JComboBox<>();
        jButton19 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        tblIngreso = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        dpDesdeN1 = new com.github.lgooddatepicker.components.DatePicker();
        jLabel6 = new javax.swing.JLabel();
        dpHastaN1 = new com.github.lgooddatepicker.components.DatePicker();
        btnBuscarFechaNomina1 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        tblBuscarNominaFecha1 = new javax.swing.JTable();
        jPanel29 = new javax.swing.JPanel();
        dpDesdeN2 = new com.github.lgooddatepicker.components.DatePicker();
        jLabel35 = new javax.swing.JLabel();
        dpHastaN2 = new com.github.lgooddatepicker.components.DatePicker();
        jLabel37 = new javax.swing.JLabel();
        btnBuscarFechaNomina2 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Reportes");
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

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        btnBuscarUsuario1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnBuscarUsuario1.setText("Buscar");
        btnBuscarUsuario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarUsuario1ActionPerformed(evt);
            }
        });

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

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        jButton1.setText("Nominas Seleccionadas");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        jButton10.setText("Todas las Nominas");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        comboBuscarEmpleado4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBuscarEmpleado4ActionPerformed(evt);
            }
        });

        jButton13.setText("ACTUALIZAR");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        txtTotalPagado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalPagadoActionPerformed(evt);
            }
        });

        lbDocumento2.setText("Total Pagado");
        lbDocumento2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1173, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(comboBuscarEmpleado4, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscarUsuario1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(lbDocumento2)
                        .addGap(18, 18, 18)
                        .addComponent(txtTotalPagado, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(btnBuscarUsuario1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboBuscarEmpleado4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTotalPagado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbDocumento2))
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        tpPestana.addTab("Buscar Nomina/Empleado", jPanel4);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        tblAdelanto.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane11.setViewportView(tblAdelanto);

        btnBuscarUsuarioAdelanto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnBuscarUsuarioAdelanto.setText("Buscar");
        btnBuscarUsuarioAdelanto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarUsuarioAdelantoActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        jButton3.setText("Adelantos seleccionados");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        jButton11.setText("Todos los adelantos");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        comboBuscarEmpleado5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBuscarEmpleado5ActionPerformed(evt);
            }
        });

        jButton16.setText("ACTUALIZAR");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton20.setText("ELIMINAR");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 1173, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(comboBuscarEmpleado5, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btnBuscarUsuarioAdelanto, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(120, 120, 120))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                .addGap(31, 31, 31)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(btnBuscarUsuarioAdelanto, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboBuscarEmpleado5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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

        btnBuscarUsuario3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnBuscarUsuario3.setText("Buscar");
        btnBuscarUsuario3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarUsuario3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        jButton4.setText("Abonados seleccionados");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        jButton12.setText("Todos los abonados");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        comboBuscarEmpleado6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBuscarEmpleado6ActionPerformed(evt);
            }
        });

        jButton17.setText("ACTUALIZAR");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(comboBuscarEmpleado6, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBuscarUsuario3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jButton4)
                .addGap(31, 31, 31)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(186, 186, 186))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4)
                        .addComponent(btnBuscarUsuario3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboBuscarEmpleado6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        btnBuscarLiquidacionE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnBuscarLiquidacionE.setText("Buscar");
        btnBuscarLiquidacionE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarLiquidacionEActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        comboBuscarEmpleado7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBuscarEmpleado7ActionPerformed(evt);
            }
        });

        jButton18.setText("ACTUALIZAR");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(comboBuscarEmpleado7, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscarLiquidacionE, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 1173, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBuscarLiquidacionE, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboBuscarEmpleado7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tpPestana.addTab("Buscar Liquidacion/Empleado", jPanel10);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tblGeneral.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo Nomina", "Nombre", "Cedula", "Rol", "Valor Dia", "Fecha Inicio", "Fecha Fin", "Dias trabajados", "Dias ausente", "Prestamos", "Total pagar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane13.setViewportView(tblGeneral);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        jButton6.setText("Nominas Seleccionadas");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        btnActualizarTNomi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Actualizar_opt.png"))); // NOI18N
        btnActualizarTNomi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarTNomiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 1173, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(260, 260, 260)
                        .addComponent(btnActualizarTNomi, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addComponent(jButton6)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnActualizarTNomi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        tpPestana.addTab("Nominas", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tblLiquidaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Cedula", "Rol", "Valor Dia", "Fecha Inicio", "Fecha Fin", "Dias trabajados", "Dias ausente", "Prestamos", "Total pagar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane14.setViewportView(tblLiquidaciones);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        btnActualizarLiquidaciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Actualizar_opt.png"))); // NOI18N
        btnActualizarLiquidaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarLiquidacionesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 1173, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(263, 263, 263)
                .addComponent(btnActualizarLiquidaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnActualizarLiquidaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        tpPestana.addTab("Liquidaciones", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblDeudaEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Cedula", "Rol", "Valor Dia", "Fecha Inicio", "Fecha Fin", "Dias trabajados", "Dias ausente", "Prestamos", "Total pagar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane15.setViewportView(tblDeudaEmpleados);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        btnActualizarTDeudas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Actualizar_opt.png"))); // NOI18N
        btnActualizarTDeudas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarTDeudasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 1173, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(463, 463, 463)
                        .addComponent(btnActualizarTDeudas, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton8)
                    .addComponent(btnActualizarTDeudas, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        tpPestana.addTab("Deuda Empleados", jPanel3);

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

        btnBuscarFechaNomina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnBuscarFechaNomina.setText("Buscar");
        btnBuscarFechaNomina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFechaNominaActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        jButton2.setText("Nominas ");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Desde");

        jLabel3.setText("Hasta");

        btnBuscarFechaNomina3.setText("ELIMINAR NOMINA");
        btnBuscarFechaNomina3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFechaNomina3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(dpDesdeN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(dpHastaN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(btnBuscarFechaNomina, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(26, 26, 26)
                .addComponent(btnBuscarFechaNomina3)
                .addContainerGap(148, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dpDesdeN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dpHastaN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBuscarFechaNomina, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBuscarFechaNomina3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27))
        );

        tpPestana.addTab("Buscar Nominas/Fechas", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        tblDeudaEmpleadosCedula.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Cedula", "Rol", "Valor Dia", "Fecha Inicio", "Fecha Fin", "Dias trabajados", "Dias ausente", "Prestamos", "Total pagar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane16.setViewportView(tblDeudaEmpleadosCedula);

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA3.png"))); // NOI18N
        jButton15.setText("Buscar");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        comboBuscarEmpleado8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBuscarEmpleado8ActionPerformed(evt);
            }
        });

        jButton19.setText("ACTUALIZAR");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(439, 439, 439)
                .addComponent(comboBuscarEmpleado8, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 1173, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                .addGap(36, 36, 36)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBuscarEmpleado8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        tpPestana.addTab("Deuda Empleado / Filtro", jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        tblIngreso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Cedula", "Rol", "Valor Dia", "Fecha Inicio", "Fecha Fin", "Dias trabajados", "Dias ausente", "Prestamos", "Total pagar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane17.setViewportView(tblIngreso);

        jLabel5.setText("Desde");

        jLabel6.setText("Hasta");

        btnBuscarFechaNomina1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnBuscarFechaNomina1.setText("Buscar");
        btnBuscarFechaNomina1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFechaNomina1ActionPerformed(evt);
            }
        });

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        jButton14.setText("Ingresos");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 1193, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(dpDesdeN1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(dpHastaN1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btnBuscarFechaNomina1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dpDesdeN1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dpHastaN1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnBuscarFechaNomina1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton14))
                        .addContainerGap())))
        );

        tpPestana.addTab("Ingresos", jPanel7);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

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
        jScrollPane18.setViewportView(tblBuscarNominaFecha1);

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));
        jPanel29.setBorder(javax.swing.BorderFactory.createTitledBorder("Busqueda"));

        jLabel35.setText("Desde");

        jLabel37.setText("Hasta");

        btnBuscarFechaNomina2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnBuscarFechaNomina2.setText("Buscar");
        btnBuscarFechaNomina2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFechaNomina2ActionPerformed(evt);
            }
        });

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
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
                .addComponent(dpDesdeN2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94)
                .addComponent(jLabel37)
                .addGap(27, 27, 27)
                .addComponent(dpHastaN2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(btnBuscarFechaNomina2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35)
                            .addComponent(jLabel37)
                            .addComponent(dpDesdeN2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dpHastaN2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnBuscarFechaNomina2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 1079, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1193, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addGap(0, 47, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 47, Short.MAX_VALUE)))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 589, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        tpPestana.addTab("Buscar Liquidaciones/Fechas", jPanel11);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tpPestana)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(tpPestana)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarUsuario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarUsuario1ActionPerformed
        String te = comboBuscarEmpleado4.getSelectedItem().toString();
        String[] parts = te.split("-");
        String part1 = parts[0]; // 123
        String part2 = parts[1];
       // UsuarioDTO DTO = servicio.consultarUsuario(Long.parseLong(part1));
        buscarCedulaNomina(Long.parseLong(part1));
        
    }//GEN-LAST:event_btnBuscarUsuario1ActionPerformed

    private void btnBuscarLiquidacionEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarLiquidacionEActionPerformed
        
        String te = comboBuscarEmpleado7.getSelectedItem().toString();
        String[] parts = te.split("-");
        String part1 = parts[0]; // 123
        String part2 = parts[1];
        //UsuarioDTO DTO = servicio.consultarUsuario(Long.parseLong(part1));
        buscarLiquidacionEmpleado(Long.parseLong(part1));
        
        
    }//GEN-LAST:event_btnBuscarLiquidacionEActionPerformed

    private void btnBuscarUsuario3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarUsuario3ActionPerformed
        String te = comboBuscarEmpleado6.getSelectedItem().toString();
        String[] parts = te.split("-");
        String part1 = parts[0]; // 123
        String part2 = parts[1];
        //UsuarioDTO DTO = servicio.consultarUsuario(Long.parseLong(part1));
        

        buscarCedulaAbono(Long.parseLong(part1));
        
    }//GEN-LAST:event_btnBuscarUsuario3ActionPerformed

    private void btnBuscarUsuarioAdelantoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarUsuarioAdelantoActionPerformed
        String te = comboBuscarEmpleado5.getSelectedItem().toString();
        String[] parts = te.split("-");
        String part1 = parts[0]; // 123
        String part2 = parts[1];
       // UsuarioDTO DTO = servicio.consultarUsuario(Long.parseLong(part1));

        
            buscarCedulaAdelanto(Long.parseLong(part1));
            
        
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

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);
        int[] seleccion = tblGeneral.getSelectedRows();
        if (seleccion.length > 0) {
            for (int i = 0; i < seleccion.length; i++) {
                //System.out.println(i);
                //System.out.println(seleccion.length+"total filas");
                Object d = tblGeneral.getValueAt(seleccion[i], 0);
                String s = String.valueOf(d);
                if (i < seleccion.length - 1) {
                    cadena += s + ",";
                } else {
                    cadena += s;
                }

            }
            System.out.println(cadena);

            String archivo = c + "\\NominaGeneral.jasper";
            String imagen = c + "\\logo.jpeg";
            Conexion cn = new Conexion();
            JasperReport jr = null;
            try {
                Map parametro = new HashMap();
                parametro.put("ID", cadena);
                parametro.put("imagen", imagen);
                jr = (JasperReport) JRLoader.loadObjectFromFile(archivo);
                JasperPrint jp = JasperFillManager.fillReport(jr, parametro, cn.getConnection());
                JasperExportManager.exportReportToPdfFile(jp,"D:\\Reporte.pdf");
            String file = new String("D:\\Reporte.pdf");
            try {
                Runtime.getRuntime().exec("cmd /c start "+file);
            } catch (IOException ex) {
                Logger.getLogger(ifrmVentas3.class.getName()).log(Level.SEVERE, null, ex);
            }
//                JasperViewer jv = new JasperViewer(jp, false);
//                jv.setVisible(true);
//                jv.setTitle("NOMINA");
            } catch (JRException ex) {
                Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);
        int[] seleccion = tblLiquidaciones.getSelectedRows();
        if (seleccion.length > 0) {
            for (int i = 0; i < seleccion.length; i++) {
                //System.out.println(i);
                //System.out.println(seleccion.length+"total filas");
                Object d = tblLiquidaciones.getValueAt(seleccion[i], 0);
                String s = String.valueOf(d);
                if (i < seleccion.length - 1) {
                    cadena += s + ",";
                } else {
                    cadena += s;
                }

            }
            System.out.println(cadena);

            String archivo = c + "\\Liquidacion.jasper";
            String imagen = c + "\\logo.jpeg";
            String r = c;
            Conexion cn = new Conexion();
            JasperReport jr = null;
            try {
                Map parametro = new HashMap();
                parametro.put("IDS", cadena);
                parametro.put("imagen", imagen);

                parametro.put("RUTA", r);
                jr = (JasperReport) JRLoader.loadObjectFromFile(archivo);
                JasperPrint jp = JasperFillManager.fillReport(jr, parametro, cn.getConnection());
                JasperExportManager.exportReportToPdfFile(jp,"D:\\Reporte.pdf");
            String file = new String("D:\\Reporte.pdf");
            try {
                Runtime.getRuntime().exec("cmd /c start "+file);
            } catch (IOException ex) {
                Logger.getLogger(ifrmVentas3.class.getName()).log(Level.SEVERE, null, ex);
            }
//                JasperViewer jv = new JasperViewer(jp, false);
//                jv.setVisible(true);
//                jv.setTitle("NOMINA");
            } catch (JRException ex) {
                Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);
        int[] seleccion = tblNominaCedula.getSelectedRows();
        if (seleccion.length > 0) {
            for (int i = 0; i < seleccion.length; i++) {
                //System.out.println(i);
                //System.out.println(seleccion.length+"total filas");
                Object d = tblNominaCedula.getValueAt(seleccion[i], 0);
                String s = String.valueOf(d);
                if (i < seleccion.length - 1) {
                    cadena += s + ",";
                } else {
                    cadena += s;
                }

            }
            System.out.println(cadena);

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
                JasperExportManager.exportReportToPdfFile(jp,"D:\\Reporte.pdf");
            String file = new String("D:\\Reporte.pdf");
            try {
                Runtime.getRuntime().exec("cmd /c start "+file);
            } catch (IOException ex) {
                Logger.getLogger(ifrmVentas3.class.getName()).log(Level.SEVERE, null, ex);
            }
//                JasperViewer jv = new JasperViewer(jp, false);
//                jv.setVisible(true);
//                jv.setTitle("NOMINA");
            } catch (JRException ex) {
                Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
            } 
        } else {
            mostrarMensaje("seleccionar fila");
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);
        Date fechaDesde = Date.valueOf(this.dpDesdeN.getDateStringOrSuppliedString(LocalDate.now().toString()));
        Date fechaHasta = Date.valueOf(this.dpHastaN.getDateStringOrSuppliedString(LocalDate.now().toString()));
        int[] seleccion = tblBuscarNominaFecha.getSelectedRows();
        int cantidad = tblBuscarNominaFecha.getRowCount();
        int cant = 0;
        double totalDevengado=0;
        double totalP=0;
        
        while (cant < cantidad) {

            Object d = tblBuscarNominaFecha.getValueAt(cant, 0);
            String s = String.valueOf(d);
            
            Object totalDv = tblBuscarNominaFecha.getValueAt(cant, 12);
            String dv = String.valueOf(d);
            double dve=Double.parseDouble(dv);
            
            Object total = tblBuscarNominaFecha.getValueAt(cant, 13);
            String dt = String.valueOf(d);
            double dte=Double.parseDouble(dt);
            
            
            cadena += s + ",";
            totalP=totalP+dte;
            totalDevengado=totalDevengado+dte+dve;
            cant++;

        }
        if (cantidad > 0) {
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
                parametro.put("FEHA_INICIO", fechaDesde);
                parametro.put("FECHA_FIN", fechaHasta);
               
                parametro.put("TOTALD", totalDevengado);
                parametro.put("TOTALP", totalP);
                jr = (JasperReport) JRLoader.loadObjectFromFile(archivo);
                JasperPrint jp = JasperFillManager.fillReport(jr, parametro, cn.getConnection());
                JasperExportManager.exportReportToPdfFile(jp,"D:\\Reporte.pdf");
            String file = new String("D:\\Reporte.pdf");
            try {
                Runtime.getRuntime().exec("cmd /c start "+file);
            } catch (IOException ex) {
                Logger.getLogger(ifrmVentas3.class.getName()).log(Level.SEVERE, null, ex);
            }
//                JasperViewer jv = new JasperViewer(jp, false);
//                jv.setVisible(true);
//                jv.setTitle("NOMINA");
            } catch (JRException ex) {
                Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);
        int[] seleccion = tblAdelanto.getSelectedRows();
        if (seleccion.length > 0) {
            for (int i = 0; i < seleccion.length; i++) {
                //System.out.println(i);
                //System.out.println(seleccion.length+"total filas");
                Object d = tblAdelanto.getValueAt(seleccion[i], 0);
                String s = String.valueOf(d);
                if (i < seleccion.length - 1) {
                    cadena += s + ",";
                } else {
                    cadena += s;
                }

            }
            System.out.println(cadena);

            String archivo = c + "\\AdelantoEmpleados.jasper";
            String imagen = c + "\\logo.jpeg";
            Conexion cn = new Conexion();
            JasperReport jr = null;
            try {
                Map parametro = new HashMap();
                parametro.put("ID", cadena);
                parametro.put("imagen", imagen);
                jr = (JasperReport) JRLoader.loadObjectFromFile(archivo);
                JasperPrint jp = JasperFillManager.fillReport(jr, parametro, cn.getConnection());
                JasperExportManager.exportReportToPdfFile(jp,"D:\\Reporte.pdf");
            String file = new String("D:\\Reporte.pdf");
            try {
                Runtime.getRuntime().exec("cmd /c start "+file);
            } catch (IOException ex) {
                Logger.getLogger(ifrmVentas3.class.getName()).log(Level.SEVERE, null, ex);
            }
//                JasperViewer jv = new JasperViewer(jp, false);
//                jv.setVisible(true);
//                jv.setTitle("NOMINA");
            } catch (JRException ex) {
                Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);
        int[] seleccion = tblAbonado.getSelectedRows();
        if (seleccion.length > 0) {
            for (int i = 0; i < seleccion.length; i++) {
                //System.out.println(i);
                //System.out.println(seleccion.length+"total filas");
                Object d = tblAbonado.getValueAt(seleccion[i], 0);
                String s = String.valueOf(d);
                if (i < seleccion.length - 1) {
                    cadena += s + ",";
                } else {
                    cadena += s;
                }

            }
            System.out.println(cadena);

            String archivo = c + "\\AbonoNomina.jasper";
            String imagen = c + "\\logo.jpeg";
            Conexion cn = new Conexion();
            JasperReport jr = null;
            try {
                Map parametro = new HashMap();
                parametro.put("ID", cadena);
                parametro.put("imagen", imagen);
                jr = (JasperReport) JRLoader.loadObjectFromFile(archivo);
                JasperPrint jp = JasperFillManager.fillReport(jr, parametro, cn.getConnection());
                JasperExportManager.exportReportToPdfFile(jp,"D:\\Reporte.pdf");
            String file = new String("D:\\Reporte.pdf");
            try {
                Runtime.getRuntime().exec("cmd /c start "+file);
            } catch (IOException ex) {
                Logger.getLogger(ifrmVentas3.class.getName()).log(Level.SEVERE, null, ex);
            }
//                JasperViewer jv = new JasperViewer(jp, false);
//                jv.setVisible(true);
//                jv.setTitle("ABONO");
            } catch (JRException ex) {
                Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);
        int[] seleccion = tblLiquidacionE.getSelectedRows();
        if (seleccion.length > 0) {
            for (int i = 0; i < seleccion.length; i++) {
                //System.out.println(i);
                //System.out.println(seleccion.length+"total filas");
                Object d = tblLiquidacionE.getValueAt(seleccion[i], 0);
                String s = String.valueOf(d);
                if (i < seleccion.length - 1) {
                    cadena += s + ",";
                } else {
                    cadena += s;
                }

            }
            System.out.println(cadena);

            String archivo = c + "\\Liquidacion.jasper";
            String imagen = c + "\\logo.jpeg";
            String r = c;

            Conexion cn = new Conexion();
            JasperReport jr = null;
            try {
                Map parametro = new HashMap();
                parametro.put("IDS", cadena);
                parametro.put("imagen", imagen);
                parametro.put("RUTA", r);
                jr = (JasperReport) JRLoader.loadObjectFromFile(archivo);
                JasperPrint jp = JasperFillManager.fillReport(jr, parametro, cn.getConnection());
                JasperExportManager.exportReportToPdfFile(jp,"D:\\Reporte.pdf");
            String file = new String("D:\\Reporte.pdf");
            try {
                Runtime.getRuntime().exec("cmd /c start "+file);
            } catch (IOException ex) {
                Logger.getLogger(ifrmVentas3.class.getName()).log(Level.SEVERE, null, ex);
            }
//                JasperViewer jv = new JasperViewer(jp, false);
//                jv.setVisible(true);
//                jv.setTitle("LIQUIDACION");
            } catch (JRException ex) {
                Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);
        int[] seleccion = tblDeudaEmpleados.getSelectedRows();
        if (seleccion.length > 0) {
            for (int i = 0; i < seleccion.length; i++) {
                //System.out.println(i);
                //System.out.println(seleccion.length+"total filas");
                Object d = tblDeudaEmpleados.getValueAt(seleccion[i], 0);
                String s = String.valueOf(d);
                if (i < seleccion.length - 1) {
                    cadena += s + ",";
                } else {
                    cadena += s;
                }

            }
            System.out.println(cadena);

            String archivo = c + "\\Deudas.jasper";
            String imagen = c + "\\logo.jpeg";
            Conexion cn = new Conexion();
            JasperReport jr = null;
            try {
                Map parametro = new HashMap();
                parametro.put("ID", cadena);
                parametro.put("imagen", imagen);
                jr = (JasperReport) JRLoader.loadObjectFromFile(archivo);
                JasperPrint jp = JasperFillManager.fillReport(jr, parametro, cn.getConnection());
                JasperExportManager.exportReportToPdfFile(jp,"D:\\Reporte.pdf");
            String file = new String("D:\\Reporte.pdf");
            try {
                Runtime.getRuntime().exec("cmd /c start "+file);
            } catch (IOException ex) {
                Logger.getLogger(ifrmVentas3.class.getName()).log(Level.SEVERE, null, ex);
            }
//                JasperViewer jv = new JasperViewer(jp, false);
//                jv.setVisible(true);
//                jv.setTitle("NOMINA");
            } catch (JRException ex) {
                Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void btnActualizarTDeudasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarTDeudasActionPerformed
        llenarDeudas();
    }//GEN-LAST:event_btnActualizarTDeudasActionPerformed

    private void btnActualizarLiquidacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarLiquidacionesActionPerformed
        llenarLiquidaciones();
    }//GEN-LAST:event_btnActualizarLiquidacionesActionPerformed

    private void btnActualizarTNomiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarTNomiActionPerformed
        llenarTablaUsuarios();
    }//GEN-LAST:event_btnActualizarTNomiActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String a = b.buscar(CARPETA);
        int cantidad = tblNominaCedula.getRowCount();
        int c = 0;
        while (c < cantidad) {
            Object objectId = tblNominaCedula.getValueAt(c, 0);
            int numero = Integer.parseInt(String.valueOf(objectId));
            cadena += numero + "," + 0;
            c++;
        }
        System.out.println(cadena);

        //Object o = tblUsuario.getValueAt(0, 0);
        //String id = String.valueOf(o);
        String imagen = a + "\\logo.jpeg";
        String archivo = a + "\\NominaUsuario.jasper";
        Conexion cn = new Conexion();
        JasperReport jr = null;
        try {
            Map parametro = new HashMap();
            parametro.put("ID", cadena);
            parametro.put("imagen", imagen);
            jr = (JasperReport) JRLoader.loadObjectFromFile(archivo);
            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, cn.getConnection());
            JasperExportManager.exportReportToPdfFile(jp,"D:\\Reporte.pdf");
            String file = new String("D:\\Reporte.pdf");
            try {
                Runtime.getRuntime().exec("cmd /c start "+file);
            } catch (IOException ex) {
                Logger.getLogger(ifrmVentas3.class.getName()).log(Level.SEVERE, null, ex);
            }
            JasperViewer jv = new JasperViewer(jp, false);
//            jv.setVisible(true);
//            jv.setTitle("NOMINA");
        } catch (JRException ex) {
            Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String a = b.buscar(CARPETA);
        int cantidad = tblAdelanto.getRowCount();
        int c = 0;
        while (c < cantidad) {
            Object objectId = tblAdelanto.getValueAt(c, 0);
            int numero = Integer.parseInt(String.valueOf(objectId));
            cadena += numero + "," + 0;
            c++;
        }
        System.out.println(cadena);

        //Object o = tblUsuario.getValueAt(0, 0);
        //String id = String.valueOf(o);
        //String imagen = "src\\com\\zapateria\\main\\images\\logo.jpeg";
        String archivo = a + "\\AdelantoEmpleados.jasper";
        String imagen = a + "\\logo.jpeg";
        Conexion cn = new Conexion();
        JasperReport jr = null;
        try {
            Map parametro = new HashMap();
            parametro.put("ID", cadena);
            parametro.put("imagen", imagen);
            jr = (JasperReport) JRLoader.loadObjectFromFile(archivo);
            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, cn.getConnection());
            JasperExportManager.exportReportToPdfFile(jp,"D:\\Reporte.pdf");
            String file = new String("D:\\Reporte.pdf");
            try {
                Runtime.getRuntime().exec("cmd /c start "+file);
            } catch (IOException ex) {
                Logger.getLogger(ifrmVentas3.class.getName()).log(Level.SEVERE, null, ex);
            }
//            JasperViewer jv = new JasperViewer(jp, false);
//            jv.setVisible(true);
//            jv.setTitle("ADELANTO");
        } catch (JRException ex) {
            Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
        } 


    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String a = b.buscar(CARPETA);
        int cantidad = tblAbonado.getRowCount();
        int c = 0;
        while (c < cantidad) {
            Object objectId = tblAbonado.getValueAt(c, 0);
            int numero = Integer.parseInt(String.valueOf(objectId));
            cadena += numero + "," + 0;
            c++;
        }
        System.out.println(cadena);

        //Object o = tblUsuario.getValueAt(0, 0);
        //String id = String.valueOf(o);
        //String imagen = "src\\com\\zapateria\\main\\images\\logo.jpeg";
        String archivo = a + "\\AbonoNomina.jasper";
        Conexion cn = new Conexion();
        JasperReport jr = null;
        try {
            Map parametro = new HashMap();
            parametro.put("ID", cadena);
            //  parametro.put("imagen",imagen);
            jr = (JasperReport) JRLoader.loadObjectFromFile(archivo);
            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, cn.getConnection());
            JasperExportManager.exportReportToPdfFile(jp,"D:\\Reporte.pdf");
            String file = new String("D:\\Reporte.pdf");
            try {
                Runtime.getRuntime().exec("cmd /c start "+file);
            } catch (IOException ex) {
                Logger.getLogger(ifrmVentas3.class.getName()).log(Level.SEVERE, null, ex);
            }
//            JasperViewer jv = new JasperViewer(jp, false);
//            jv.setVisible(true);
//            jv.setTitle("ABONADOS");
        } catch (JRException ex) {
            Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        
       String te = comboBuscarEmpleado8.getSelectedItem().toString();
        String[] parts = te.split("-");
        String part1 = parts[0]; // 123
        String part2 = parts[1];
        //UsuarioDTO DTO = servicio.consultarUsuario(Long.parseLong(part1));
        llenarDeudasCedula(Long.parseLong(part1));
    }//GEN-LAST:event_jButton15ActionPerformed

    private void btnBuscarFechaNomina1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFechaNomina1ActionPerformed
        Date fechaDesde = Date.valueOf(this.dpDesdeN1.getDateStringOrSuppliedString(LocalDate.now().toString()));
        Date fechaHasta = Date.valueOf(this.dpHastaN1.getDateStringOrSuppliedString(LocalDate.now().toString()));

        if (fechaDesde.toString().equalsIgnoreCase("") || fechaHasta.toString().equalsIgnoreCase("")) {

            JOptionPane.showMessageDialog(null, "Seleccionar Fecha");
        } else {

            //String fecha= 
//           Date h= Date.valueOf(dpHasta.getDateStringOrEmptyString().toString());
            buscarFechaFact(fechaDesde, fechaHasta);
            // buscarFechaFactura(fechaDesde);
        }
    }//GEN-LAST:event_btnBuscarFechaNomina1ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);
        Date fechaDesde = Date.valueOf(dpDesdeN1.getDateStringOrSuppliedString(LocalDate.now().toString()));
        Date fechaHasta = Date.valueOf(dpHastaN1.getDateStringOrSuppliedString(LocalDate.now().toString()));
        int cantidad = tblIngreso.getRowCount();
        int a = 0;
        double total = 0;
        while (a < cantidad) {
            Object d = tblIngreso.getValueAt(a, 0);
            String s = String.valueOf(tblIngreso.getValueAt(a, 4));
             
            String id = String.valueOf(d);
            
            cadena += id + ",";
            s = s.replace(",", ".");
            s = s.replace(".", "");
            total += Double.parseDouble(s);
            a++;
        }
        cadena = cadena.substring(0, cadena.length() - 1);
//        <int[] seleccion = tblBuscarNominaFecha.getSelectedRows();

        String archivo = c + "\\Ingresos.jasper";
        String imagen = c + "\\logo.jpeg";
        Conexion cn = new Conexion();
        JasperReport jr = null;
        try {
            Map parametro = new HashMap();
            parametro.put("ID", cadena);
            parametro.put("imagen", imagen);
            parametro.put("FEHA_INICIO", fechaDesde);
            parametro.put("FECHA_FIN", fechaHasta);
            parametro.put("total", total);
            jr = (JasperReport) JRLoader.loadObjectFromFile(archivo);
            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, cn.getConnection());
            JasperExportManager.exportReportToPdfFile(jp,"D:\\Reporte.pdf");
            String file = new String("D:\\Reporte.pdf");
            try {
                Runtime.getRuntime().exec("cmd /c start "+file);
            } catch (IOException ex) {
                Logger.getLogger(ifrmVentas3.class.getName()).log(Level.SEVERE, null, ex);
            }
//            JasperViewer jv = new JasperViewer(jp, false);
//            jv.setVisible(true);
//            jv.setTitle("NOMINA");
        } catch (JRException ex) {
            Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);

        }
    }//GEN-LAST:event_jButton14ActionPerformed
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
    private void btnBuscarFechaNomina2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFechaNomina2ActionPerformed
        Date fechaDesde = Date.valueOf(this.dpDesdeN2.getDateStringOrSuppliedString(LocalDate.now().toString()));
        Date fechaHasta = Date.valueOf(this.dpHastaN2.getDateStringOrSuppliedString(LocalDate.now().toString()));

        if (fechaDesde.toString().equalsIgnoreCase("") || fechaHasta.toString().equalsIgnoreCase("")) {

            JOptionPane.showMessageDialog(null, "Seleccionar Fecha");
        } else {

            //String fecha=
            //           Date h= Date.valueOf(dpHasta.getDateStringOrEmptyString().toString());
            buscarFechaLiquidaciones(fechaDesde, fechaHasta);
            // buscarFechaFactura(fechaDesde);
        }
    }//GEN-LAST:event_btnBuscarFechaNomina2ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);
        Date fechaDesde = Date.valueOf(this.dpDesdeN.getDateStringOrSuppliedString(LocalDate.now().toString()));
        Date fechaHasta = Date.valueOf(this.dpHastaN.getDateStringOrSuppliedString(LocalDate.now().toString()));
        int[] seleccion = tblBuscarNominaFecha.getSelectedRows();
        int cantidad = tblBuscarNominaFecha1.getRowCount();
        int cant = 0;
        while (cant < cantidad) {

            Object d = tblBuscarNominaFecha1.getValueAt(cant, 0);
            String s = String.valueOf(d);

            cadena += s + ",";
            cant++;

        }
        if (cantidad > 0) {
            cadena = cadena.substring(0, cadena.length() - 1);
            System.out.println(cadena);

            String archivo = c + "\\liquidacion2.jasper";
            String imagen = c + "\\logo.jpeg";
            Conexion cn = new Conexion();
            JasperReport jr = null;
            try {
                Map parametro = new HashMap();
                parametro.put("ID", cadena);
                parametro.put("imagen", imagen);
                parametro.put("FEHA_INICIO", fechaDesde);
                parametro.put("FECHA_FIN", fechaHasta);
                jr = (JasperReport) JRLoader.loadObjectFromFile(archivo);
                JasperPrint jp = JasperFillManager.fillReport(jr, parametro, cn.getConnection());
                JasperExportManager.exportReportToPdfFile(jp,"C:\\Reporte.pdf");
            String file = new String("C:\\Reporte.pdf");
            try {
                Runtime.getRuntime().exec("cmd /c start "+file);
            } catch (IOException ex) {
                Logger.getLogger(ifrmVentas3.class.getName()).log(Level.SEVERE, null, ex);
            }
//                JasperViewer jv = new JasperViewer(jp, false);
//                jv.setVisible(true);
//                jv.setTitle("Liquidacion");
            } catch (JRException ex) {
                Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }//GEN-LAST:event_jButton9ActionPerformed

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

    private void comboBuscarEmpleado8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBuscarEmpleado8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBuscarEmpleado8ActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
     llenarComboMaterial5();
  //     llenarComboMaterial6();
    //   llenarComboMaterial7();
      // llenarComboMaterial8();
       //llenarComboMaterial9();
       
    }//GEN-LAST:event_formInternalFrameOpened

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        llenarComboMaterial5();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        llenarComboMaterial6();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
      llenarComboMaterial7();
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        llenarComboMaterial8();
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        llenarComboMaterial9();
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
       int fila=tblAdelanto.getSelectedRow();
       long id=Long.parseLong(String.valueOf(tblAdelanto.getValueAt(fila,0)));
        
       String a=String.valueOf(tblAdelanto.getValueAt(fila,1));
       a=a.replace(",", ".");
       a=a.replace(".", "");
       double adelanto=Double.parseDouble(a);
       
//       tblAdelanto.remove(fila);
      boolean xa= servicio.eliminarPrestamoCliente(id);
//      mostrarMensaje(id+"-"+fila+"-"+xa);
       String te = comboBuscarEmpleado5.getSelectedItem().toString();
        String[] parts = te.split("-");
        String part1 = parts[0]; // 123
        String part2 = parts[1];
        
        Abono_PrestamoDTO abono = servicio.buscarAbonoPrestamo(Long.parseLong(part1));
            double prestamo = abono.getTotal_prestamo()-adelanto ;
            double total_debe = abono.getTotal_debe() - adelanto;
            boolean x = servicio.actualizarAbonoPrestamo(abono.getId(), prestamo, total_debe);
        
       
    }//GEN-LAST:event_jButton20ActionPerformed

    private void btnBuscarFechaNomina3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFechaNomina3ActionPerformed
        int filas=tblBuscarNominaFecha.getRowCount();
        int d=0;
         Date fechaDesde = Date.valueOf(this.dpDesdeN.getDateStringOrSuppliedString(LocalDate.now().toString()));
        Date fechaHasta = Date.valueOf(this.dpHastaN.getDateStringOrSuppliedString(LocalDate.now().toString()));
        ArrayList<NominaDTO> n = servicio.listarFechaNomi(fechaDesde, fechaHasta);
        boolean exito=false;
        for (NominaDTO no : n) {
            
            exito=servicio.BorrarNominas(no.getId());
        }
        if(exito){
            mostrarMensaje("Las nominas se eliminaron");
        }
    }//GEN-LAST:event_btnBuscarFechaNomina3ActionPerformed

    private void txtTotalPagadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalPagadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalPagadoActionPerformed

    private void tpPestanaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tpPestanaMouseClicked
        if(tpPestana.getSelectedIndex()==1){
        llenarComboMaterial6();
        }
        if(tpPestana.getSelectedIndex()==2){
        llenarComboMaterial7();
        }
        if(tpPestana.getSelectedIndex()==3){
        llenarComboMaterial8();
        }
        if(tpPestana.getSelectedIndex()==4){
         llenarTablaUsuarios();
        }
        if(tpPestana.getSelectedIndex()==8){
        llenarComboMaterial9();
        }
    }//GEN-LAST:event_tpPestanaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarLiquidaciones;
    private javax.swing.JButton btnActualizarTDeudas;
    private javax.swing.JButton btnActualizarTNomi;
    private javax.swing.JButton btnBuscarFechaNomina;
    private javax.swing.JButton btnBuscarFechaNomina1;
    private javax.swing.JButton btnBuscarFechaNomina2;
    private javax.swing.JButton btnBuscarFechaNomina3;
    private javax.swing.JButton btnBuscarLiquidacionE;
    private javax.swing.JButton btnBuscarUsuario1;
    private javax.swing.JButton btnBuscarUsuario3;
    private javax.swing.JButton btnBuscarUsuarioAdelanto;
    private javax.swing.JComboBox<Object> comboBuscarEmpleado4;
    private javax.swing.JComboBox<Object> comboBuscarEmpleado5;
    private javax.swing.JComboBox<Object> comboBuscarEmpleado6;
    private javax.swing.JComboBox<Object> comboBuscarEmpleado7;
    private javax.swing.JComboBox<Object> comboBuscarEmpleado8;
    private com.github.lgooddatepicker.components.DatePicker dpDesdeN;
    private com.github.lgooddatepicker.components.DatePicker dpDesdeN1;
    private com.github.lgooddatepicker.components.DatePicker dpDesdeN2;
    private com.github.lgooddatepicker.components.DatePicker dpHastaN;
    private com.github.lgooddatepicker.components.DatePicker dpHastaN1;
    private com.github.lgooddatepicker.components.DatePicker dpHastaN2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
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
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lbDocumento2;
    private javax.swing.JTable tblAbonado;
    private javax.swing.JTable tblAdelanto;
    private javax.swing.JTable tblBuscarNominaFecha;
    private javax.swing.JTable tblBuscarNominaFecha1;
    private javax.swing.JTable tblDeudaEmpleados;
    private javax.swing.JTable tblDeudaEmpleadosCedula;
    private javax.swing.JTable tblGeneral;
    private javax.swing.JTable tblIngreso;
    private javax.swing.JTable tblLiquidacionE;
    private javax.swing.JTable tblLiquidaciones;
    private javax.swing.JTable tblNominaCedula;
    private javax.swing.JTabbedPane tpPestana;
    private javax.swing.JTextField txtTotalPagado;
    // End of variables declaration//GEN-END:variables
}
