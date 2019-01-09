/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.view;

import com.zapateria.main.dao.Servicio;
import com.zapateria.main.dto.Abono_PrestamoDTO;
import com.zapateria.main.dto.ClienteDTO;
import com.zapateria.main.dto.FacturaDTO;
import com.zapateria.main.dto.MaterialDTO;
import com.zapateria.main.dto.NombreIngresosDTO;
import com.zapateria.main.dto.PagoDTO;
import com.zapateria.main.dto.RegistroDTO;
import com.zapateria.main.dto.UsuarioDTO;
import com.zapateria.main.dto.VentaDTO;
import com.zapateria.main.util.BuscadorCarpeta;
import com.zapateria.main.util.Conexion;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
 * @author JHON
 */
public class ifrmFactura extends MarcoInternoPersonalizado {

    private static final long serialVersionUID = -8428097790361023720L;

    private static final String USUARIO = "USUARIOS", FACTURAFECHA = "FACTURAFECHA", ESTADO = "ESTADO", ID = "ID";
    private FacturaDTO facturaDTO = new FacturaDTO();
    private DefaultTableModel dtbmUsuarios, dtbmFacFecha, dtbmEstado, dtbmId,dtbmUsuariosItem;

    //private Servicio servicio;
    private JDesktopPane escritorio;
    FormatoTabla f = new FormatoTabla();

//lista los usuarios por el estado
    private void buscarUsuarioCedula(long cedula) {

        final String[] columnas = {"N° Remision", "Fecha", "Iva", "Total a pagar", "Total pagado", "Cliente", "Usuario", "Saldo Debe", "Forma de pago", "Factura Iva"};
        //configurando los eventos de la tabla
        dtbmUsuarios = new DefaultTableModel(null, columnas) {

            private static final long serialVersionUID = 5157407284026187529L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2;
            }
        };
        //Llenando la tabla
        servicio.listarUsuarioFactura(cedula).forEach((FacturaDTO factura) -> {
            dtbmUsuarios.addRow(factura.toArray());
        });
        
        ArrayList<FacturaDTO>a=servicio.listarUsuarioFactura(cedula);
        double total=0;
        double totalPagado=0;
        double totalPagar=0;
        for (FacturaDTO fa : a) {
            totalPagado=fa.getTotalPagado()+totalPagado;
            totalPagar=fa.getTotalPagar()+totalPagar;
        }
        total=totalPagar-totalPagado;
        txtTotalAbonado.setText(formateador.format(totalPagado));
        txtTotalIngresado.setText(formateador.format(totalPagar));
        txtSaldo.setText(formateador.format(total));
        tblUsuario.setModel(dtbmUsuarios);

        dtbmUsuarios.addTableModelListener((TableModelEvent e) -> {

            long id;
        });
    }
DecimalFormat formateador = new DecimalFormat("###,###.##");
    public Image getIconImage() {
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(c + "//icon2.png");

        return retValue;
    }

    public void llenarComboMaterial() {

        cmbTipoMaterial.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<MaterialDTO> listar = servicio.listarMateriales();
        cmbTipoMaterial.addItem("ninguno");

        for (MaterialDTO n : listar) {
            cmbTipoMaterial.addItem(n.getNombre());
            cmbTipoMaterial1.addItem(n.getNombre());
        }
    }
    
    private void llenarTablaVentasRegistro(long i) {
        String est = "";
        final String[] columnas = {"Codigo", "Fecha", "Producto", "Cantidad", "Precio unitario", "Comentario", "Estado Item", "Total a pagar", "Iva", "Numero Remision", "Usado"};

        // Configurando los eventos de edicion de la tabla
        dtbmUsuariosItem = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column <0;
            }

           

           
        };
        // Llenando la tabla
        //        servicio.listarVentas().forEach((VentaDTO venta) -> {
        //            dtbmVentas.addRow(venta.toArray());
        //        });
        ;
        ArrayList<RegistroDTO> ventas = servicio.listarVentasClienteRemision(i);

        System.out.println(ventas + "lista");
        for (RegistroDTO venta : ventas) {
           // MaterialDTO m = servicio.consultarMaterial(venta.getId_material());
            String estado = "";

            if (venta.getImpreso() == 1) {
                estado = "OK";
            }
            Timestamp stamp = venta.getFecha();
            Date date = new Date(stamp.getTime());
            String S = new SimpleDateFormat("dd/MM/yyyy").format(date);
            dtbmUsuariosItem.addRow(new Object[]{venta.getId(), S, venta.getMaterial().getNombre(), venta.getCantidad(), formateador.format(venta.getPrecio()), venta.getComentario(), venta.getEstado(), formateador.format(venta.getTotal_pagar()), formateador.format(venta.getIva()), venta.getId_factura(), estado});
//            tbVentas1.setDefaultRenderer(Object.class, f);
        }

        //Agregando el modelo de la tabla
        tblUsuario1.setModel(dtbmUsuariosItem);

        // cambia el tipo de letra del encabezado de la tabla
        tblUsuario1.getTableHeader().setFont(new Font("Tahoma", 1, 11));

        //le da formato a toda la tabla
        tblUsuario1.setFont(new Font("Tahoma", 1, 11));
//        // cambia el fondo del encabezado de la tabla
//        tbVentas.getTableHeader().setBackground(Color.cyan);

        // cambia el color de la letra del encabezado de la tabla
        tblUsuario1.getTableHeader().setForeground(Color.blue);

        //da formato a la fila de la celda
        tblUsuario1.setSelectionBackground(Color.GRAY);
//        tbVentas1.setSelectionForeground(Color.cyan);
        tblUsuario1.setIntercellSpacing(new Dimension(4, 4));
        tblUsuario1.setRowMargin(2);
        tblUsuario1.setOpaque(false);

        //Definiendo el ancho de la columna ed la tabla
        tblUsuario1.getColumnModel().getColumn(0).setMaxWidth(70);
        tblUsuario1.getColumnModel().getColumn(1).setMaxWidth(90);
        tblUsuario1.getColumnModel().getColumn(2).setMaxWidth(90);
        tblUsuario1.getColumnModel().getColumn(3).setMaxWidth(70);
        tblUsuario1.getColumnModel().getColumn(4).setMaxWidth(100);
        tblUsuario1.getColumnModel().getColumn(5).setMaxWidth(500);
        tblUsuario1.getColumnModel().getColumn(6).setMaxWidth(90);
        tblUsuario1.getColumnModel().getColumn(7).setMaxWidth(60);
        tblUsuario1.getColumnModel().getColumn(8).setMaxWidth(110);
        tblUsuario1.getColumnModel().getColumn(9).setMaxWidth(90);
        
        tblUsuario1.getColumnModel().getColumn(10).setMaxWidth(60);
        //     tbVentas.getColumnModel().getColumn(4).setMaxWidth(80);

        //centrando las columnas de la tabla
//        DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
//        modelocentrar.setHorizontalAlignment(SwingConstants.CENTER);
//        tbVentas1.getColumnModel().getColumn(0).setCellRenderer(modelocentrar);
//        tbVentas1.getColumnModel().getColumn(1).setCellRenderer(modelocentrar);
//        tbVentas1.getColumnModel().getColumn(2).setCellRenderer(modelocentrar);
//        tbVentas1.getColumnModel().getColumn(3).setCellRenderer(modelocentrar);
        dtbmUsuariosItem.addTableModelListener((TableModelEvent e) -> {

            long id;
            long cantidad;

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tblUsuario1.getValueAt(e.getFirstRow(), 0);
                Object objCantidad = tblUsuario1.getValueAt(e.getFirstRow(), 3);
//                int cantidad=Integer.parseInt(String.valueOf(objCantidad));
                Object objPrecio = tblUsuario1.getValueAt(e.getFirstRow(), 4);
                Object objIva = tblUsuario1.getValueAt(e.getFirstRow(), 8);
                String precio= String.valueOf(objPrecio);
                String iva=String.valueOf(objIva);
                precio=precio.replace(",",".");
                precio=precio.replace(".","");
                
                iva=iva.replace(",", ".");
                iva=iva.replace(".", "");
                double pre= Double.parseDouble(precio);
                double iv=Double.parseDouble(iva);
                double ivaa=0;
                double ivaSistema = servicio.consultarImpuesto(1).getValor();
                
                cantidad = Long.parseLong(String.valueOf(objCantidad));
                if(iv>0){
                    ivaa=(pre*cantidad)*(ivaSistema/100);
                }

                if (objCantidad == null) {
                    return;
                }

                
                int s = tblUsuario1.getSelectedRow();
                if (objId != null) {
                    String esta = "";
                    long p = Long.parseLong(String.valueOf(tblUsuario1.getValueAt(s, 0)));
                    esta = String.valueOf(tblUsuario1.getValueAt(s, 6));
                    servicio.cambiarEstado(p, esta);
//                    servicio.editarPrecio(p, pre, (pre*cantidad)+ivaa,ivaa);
                    id = Long.parseLong(String.valueOf(objId));
//                    boolean c = tblUsuario1.isSelected();

//                    if (servicio.editarVenta(id, cantidad)) {
//                        actualizarTablaProductos();
                 
//                    } else {
//                        mostrarMensaje("No se pudo editar el producto");
//                    }
                } else {
//                    if (servicio.registrarProducto(nombre, precioUnitario)) {
//                        actualizarTablaProductos();
//                        mostrarMensaje("Se ha registrado la producto satisfactoriamente");
//                    } else {
//                        mostrarMensaje("No se pudo registrar la producto");
//                    }
                }

            }
        });
        ArrayList<String> a = new ArrayList<>();
        a.add("DEPOSITO");
        a.add("ENTREGADO");
        a.add("PROCESO");
        agregarJComboBoxAColumna(tblUsuario1, 6, a.toArray());
//        aplicarEventosPersonalizados(tbVentas1);

//        aplicarEventosPersonalizados(tbVentas);
    }

    //buscar facturas por el tipo de pago 
    private void buscarTipoFacturas(String estado) {

        final String[] columnas = {"N° Remision", "Fecha", "Iva", "Total a pagar", "Total pagado", "Cliente", "Usuario", "Saldo Debe", "Forma de pago", "Factura id"};
        //configurando los eventos de la tabla
        dtbmEstado = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 8;
            }
        };
        //Llenando la tabla
        servicio.listarEstadosFactura(estado).forEach((FacturaDTO factura) -> {
            dtbmEstado.addRow(factura.toArray());
        });
        tbTipoPago.setModel(dtbmEstado);

        dtbmEstado.addTableModelListener((TableModelEvent e) -> {

            long id;
        });
    }

    //lista las facturas por el estado
    private void buscarEstadoFacturas(String estado) {

        final String[] columnas = {"N° Item", "Cliente", "Fecha", "Total a pagar",
            "Fecha Entrega", "Material", "Estado", "Codigo Remision","Comentario"};
        //configurando los eventos de la tabla
        dtbmEstado = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 6;
            }
        };
        //Llenando la tabla
//        servicio.listarRegistrosEstado(estado).forEach((RegistroDTO factura) -> {
//            dtbmEstado.addRow(factura.toArray());
//        });
         Object[] object = new Object[14];
        //servicio.listarUsuariosNomina()
        //      .forEach((UsuarioDTO usuario) -> {
        //        dtblNomina.addRow(usuario.toArray());
        //      
        double totalPagar=0;
        for (RegistroDTO u : servicio.listarRegistrosEstado(estado)) {
         
            
            
            Timestamp stamp = u.getFecha();
             Date date = new Date(stamp.getTime());
             String S = new SimpleDateFormat("dd/MM/yyyy").format(date);
//            mostrarMensaje();
            
            object[0] = u.getId();
            object[1] = u.getCliente().getNombres() +" "+u.getCliente().getApellidos();
            object[2] = S;
            object[3] = formateador.format(u.getTotal_pagar());
            object[4] = u.getFechaEntrega();
            object[5] = u.getMaterial().getNombre();
            object[6] = u.getEstado();
            object[7] = u.getId_factura();
            object[8] = u.getComentario();

            totalPagar=totalPagar+u.getTotal_pagar();
            dtbmEstado.addRow(object);

        }
//        tblEstado.setDefaultRenderer(Object.class, f);
            txtTotalEstado.setText(formateador.format(totalPagar));
        tblEstado.setModel(dtbmEstado);
 tblEstado.getColumnModel().getColumn(0).setMaxWidth(90);
        tblEstado.getColumnModel().getColumn(1).setMaxWidth(400);
        tblEstado.getColumnModel().getColumn(2).setMaxWidth(300);
        tblEstado.getColumnModel().getColumn(3).setMaxWidth(100);
        tblEstado.getColumnModel().getColumn(4).setMaxWidth(100);
        tblEstado.getColumnModel().getColumn(5).setMaxWidth(90);
        tblEstado.getColumnModel().getColumn(6).setMaxWidth(90);
        tblEstado.getColumnModel().getColumn(7).setMaxWidth(80);
        tblEstado.getColumnModel().getColumn(8).setMaxWidth(500);
        dtbmEstado.addTableModelListener((TableModelEvent e) -> {

            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar
                long id;
                Object objId = tblEstado.getValueAt(e.getFirstRow(), 0);
                String formaDePago = String.valueOf(tblEstado.getValueAt(e.getFirstRow(), 1));

                String estados = String.valueOf(tblEstado.getValueAt(e.getFirstRow(), 6));

                if (objId != null) {
                    id = Long.parseLong(String.valueOf(objId));
                    boolean exito = servicio.cambiarEstado(id, estados);
                    System.out.println(exito + "cambio");
                    if (exito) {

                        mostrarMensaje("La factura se ha editado satisfactoriamente.");
                        actualizarTablaVentas(estado);
                        // actualizarTablaFacturas();
//                    calcularTotales();
                    } else {
                        mostrarMensaje("No se pudo editar la factura.");
                    }
                } else {
//                    if (servicio.registrarProducto(nombre, precioUnitario)) {
//                        actualizarTablaProductos();
//                        mostrarMensaje("Se ha registrado la producto satisfactoriamente");
//                    } else {
//                        mostrarMensaje("No se pudo registrar la producto");
//                    }
                }

            }
        });
        ArrayList<String> a = new ArrayList<>();
        a.add("DEPOSITO");
        a.add("ENTREGADO");
        a.add("PROCESO");
        agregarJComboBoxAColumna(tblEstado, 6, a.toArray());
    }

    private void actualizarTablaVentas(String estado) {
        tblEstado.removeAll();
        dtbmEstado = null;
        buscarEstadoFacturas(estado);

    }

    //Buscar Factura id
    private void buscarFacturaId(long Id) {
        final String[] columnas = {"N° Remision", "Fecha", "Iva", "Total a pagar", "Total pagado", "Cliente", "Usuario", "Saldo Debe", "Forma de pago", "Factura id"};
        //configurando los eventos de la tabla
        dtbmId = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2;
            }
        };
        //Llenando la tabla
        servicio.buscarFactura(Id).forEach((FacturaDTO factura) -> {
            dtbmId.addRow(factura.toArray());
        });
        tblId.setModel(dtbmId);

        dtbmId.addTableModelListener((TableModelEvent e) -> {

            long id;
        });
    }

    //Buscar facturas por fechas
    private void buscarFechaFacturas(Date FechaD, Date FechaH) {
        double pagar=0;
        double pagado=0;

        final String[] columnas = {"N° Factura", "Fecha", "Iva", "Total a pagar", "Total pagado", "Cliente", "Usuario", "Saldo Debe", "Forma de pago", "Factura iva"};
        //configurando los eventos de la tabla
        dtbmFacFecha = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2;
            }
        };
        //Llenando la tablaicio
        ArrayList<FacturaDTO>f=servicio.listarFechaFac(FechaD, FechaH);
        for (FacturaDTO fa : f) {
            pagar=pagar+fa.getTotalPagar();
            pagado=pagado+fa.getTotalPagado();
        }
        servicio.listarFechaFac(FechaD, FechaH).forEach((FacturaDTO factura) -> {
            dtbmFacFecha.addRow(factura.toArray());
        });
        txtTotalIngresado1.setText(formateador.format(pagar));
        txtTotalAbonado1.setText(formateador.format(pagado));
        txtSaldo1.setText(formateador.format(pagar-pagado));
        tblFacturaFec.setModel(dtbmFacFecha);

        dtbmFacFecha.addTableModelListener((TableModelEvent e) -> {

            long id;
        });
    }

    //Busca las facturas en una fecha correspondiente
    private void buscarFechaFactura(Date Fecha) {

        final String[] columnas = {"N° Factura", "Forma de pago", "Estado", "Fecha", "Iva", "Total a pagar", "Total pagado",
            "Fecha Entrega", "Cliente", "Usuario", "Numero Cuotas", "Cuotas Pagadas", "Saldo Debe"};
        //configurando los eventos de la tabla
        dtbmFacFecha = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2;
            }
        };
        //Llenando la tabla
        servicio.listarFechaFact(Fecha).forEach((FacturaDTO factura) -> {
            dtbmFacFecha.addRow(factura.toArray());
        });
        tblFacturaFec.setModel(dtbmFacFecha);

        dtbmFacFecha.addTableModelListener((TableModelEvent e) -> {

            long id;
        });
    }

    public void actualizarTablaUsuarios() {

        dtbmUsuarios = null;

    }

    /**
     * Creates new form ifrmFactura
     */
    public ifrmFactura() {
        initComponents();
        
        txtCedula.setVisible(false);
        jLabel3.setVisible(false);
        
        
        btnBuscarUsuario.setVisible(false);
        
        AutoCompleteDecorator.decorate(jComboBox4);
//        String CARPETA = "db";
//        BuscadorCarpeta b = null;
//        String c= b.buscar(CARPETA);
//        ImageIcon ImageIcon = new ImageIcon(c+"//icon2.png");
//        Image Image = ImageIcon.getImage();
//        this.setIconImage(Image);
//        
        // Centering on screen...
        setSize(400, 300);
        jButton3.setVisible(false);
        btnImprimirMaterial.setVisible(false);

        btnImprimirTipo.setVisible(false);

        jButton4.setVisible(true);
        btnVerProductos.setVisible(false);
        comboBuscarCliente.setEditable(true);
        comboBuscarCliente.setEnabled(true);
        comboBuscarCliente.setVisible(false);


    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jMenu1 = new javax.swing.JMenu();
        tbpestana = new javax.swing.JTabbedPane();
        panelUsuario = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuario = new javax.swing.JTable();
        txtCedula = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnBuscarUsuario = new javax.swing.JButton();
        btnVerProductos2 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        comboBuscarCliente = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        txtSaldo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTotalAbonado = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTotalIngresado = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        PanelFecha = new javax.swing.JPanel();
        dpHasta = new com.github.lgooddatepicker.components.DatePicker();
        dpDesde = new com.github.lgooddatepicker.components.DatePicker();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnBuscarFecha = new javax.swing.JButton();
        btnVerProductos1 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblFacturaFec = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        btnCerrar1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtTotalIngresado1 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtTotalAbonado1 = new javax.swing.JTextField();
        txtSaldo1 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        panelEstado = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        cmbEstado = new javax.swing.JComboBox<>();
        btnVerProductos = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblEstado = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        btnCerrar2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtTotalEstado = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnBuscarId = new javax.swing.JButton();
        txtId = new javax.swing.JTextField();
        btnVerProductosId = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblId = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        btnCerrar3 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbTipoPago = new javax.swing.JTable();
        cmbTipoPago = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        btnBuscarTipo = new javax.swing.JButton();
        btnVerProductosTipo = new javax.swing.JButton();
        btnImprimirTipo = new javax.swing.JButton();
        btnCerrar4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tbTipoMaterial = new javax.swing.JTable();
        cmbTipoMaterial = new javax.swing.JComboBox<>();
        btnVerProductosMaterial = new javax.swing.JButton();
        btnImprimirMaterial = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btnCerrarMaterial = new javax.swing.JButton();
        txtTotalIngresado2 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtTotalAbonado2 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtSaldo2 = new javax.swing.JTextField();
        panelUsuario1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblUsuario1 = new javax.swing.JTable();
        btnCerrar5 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tbTipoMaterial1 = new javax.swing.JTable();
        cmbTipoMaterial1 = new javax.swing.JComboBox<>();
        btnVerProductosMaterial1 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        btnCerrarMaterial1 = new javax.swing.JButton();
        txtTotalIngresado3 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        dpDesde1 = new com.github.lgooddatepicker.components.DatePicker();
        jLabel24 = new javax.swing.JLabel();
        dpDesde2 = new com.github.lgooddatepicker.components.DatePicker();
        jLabel25 = new javax.swing.JLabel();

        jMenu1.setText("jMenu1");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
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

        tbpestana.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbpestanaMouseClicked(evt);
            }
        });

        panelUsuario.setBackground(new java.awt.Color(255, 255, 255));

        tblUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "N° Factura", "Forma de pago", "Estado", "Fecha", "Iva", "Total a pagar", "Total pagado", "Fecha Entrega", "Cliente", "Usuario"
            }
        ));
        jScrollPane1.setViewportView(tblUsuario);

        jLabel3.setText("Cedula");

        btnBuscarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnBuscarUsuario.setText("Buscar");
        btnBuscarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarUsuarioActionPerformed(evt);
            }
        });

        btnVerProductos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnVerProductos2.setText("Ver Productos");
        btnVerProductos2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerProductos2ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/SALIR.png"))); // NOI18N
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        comboBuscarCliente.setEnabled(false);
        comboBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBuscarClienteActionPerformed(evt);
            }
        });

        jLabel8.setText("Clientes");

        jButton5.setText("Abonar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton8.setText("ELIMINAR");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("EDITAR NUMERO IVA");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jComboBox3.setEditable(true);
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MADERA", "ROBLE", "METAL" }));

        jButton10.setText("OK");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("ACT");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel9.setText("Debe");

        jLabel10.setText("Total Abonado");

        jLabel11.setText("Total mandado hacer");

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/SWN.png"))); // NOI18N
        jLabel13.setText("jLabel5");

        javax.swing.GroupLayout panelUsuarioLayout = new javax.swing.GroupLayout(panelUsuario);
        panelUsuario.setLayout(panelUsuarioLayout);
        panelUsuarioLayout.setHorizontalGroup(
            panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUsuarioLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jButton10)
                        .addGap(3, 3, 3)
                        .addComponent(jButton11)
                        .addGap(58, 58, 58)
                        .addComponent(btnVerProductos2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)
                        .addGap(18, 18, 18)
                        .addComponent(jButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUsuarioLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUsuarioLayout.createSequentialGroup()
                                .addComponent(comboBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(713, 713, 713)
                                .addComponent(jLabel3)
                                .addGap(63, 63, 63)
                                .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(btnBuscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUsuarioLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTotalIngresado, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTotalAbonado, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(txtSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(97, 97, 97))))))
            .addGroup(panelUsuarioLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelUsuarioLayout.setVerticalGroup(
            panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelUsuarioLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelUsuarioLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(btnBuscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelUsuarioLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTotalAbonado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(txtTotalIngresado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)))
                .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUsuarioLayout.createSequentialGroup()
                        .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnVerProductos2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(40, 40, 40))
                    .addGroup(panelUsuarioLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );

        tbpestana.addTab("Usuario", panelUsuario);

        PanelFecha.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Desde");

        jLabel2.setText("Hasta");

        btnBuscarFecha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnBuscarFecha.setText("Buscar");
        btnBuscarFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFechaActionPerformed(evt);
            }
        });

        btnVerProductos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnVerProductos1.setText("Ver Productos");
        btnVerProductos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerProductos1ActionPerformed(evt);
            }
        });

        tblFacturaFec.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "N° Factura", "Forma de pago", "Estado", "Fecha", "Iva", "Total a pagar", "Total pagado", "Fecha Entrega", "Cliente", "Usuario"
            }
        ));
        jScrollPane5.setViewportView(tblFacturaFec);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btnCerrar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/SALIR.png"))); // NOI18N
        btnCerrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrar1ActionPerformed(evt);
            }
        });

        jLabel14.setText("Total mandado hacer");

        jLabel16.setText("Total Abonado");

        jLabel17.setText("Deben");

        javax.swing.GroupLayout PanelFechaLayout = new javax.swing.GroupLayout(PanelFecha);
        PanelFecha.setLayout(PanelFechaLayout);
        PanelFechaLayout.setHorizontalGroup(
            PanelFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFechaLayout.createSequentialGroup()
                .addGroup(PanelFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelFechaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1201, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelFechaLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(PanelFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelFechaLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(dpHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelFechaLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(dpDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(62, 62, 62)
                        .addComponent(btnBuscarFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(btnVerProductos1)
                        .addGap(18, 18, 18)
                        .addGroup(PanelFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelFechaLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTotalIngresado1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelFechaLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)
                                .addComponent(txtTotalAbonado1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(txtSaldo1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCerrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        PanelFechaLayout.setVerticalGroup(
            PanelFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFechaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(PanelFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PanelFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelFechaLayout.createSequentialGroup()
                                .addGroup(PanelFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(dpDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(dpHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(52, 52, 52))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelFechaLayout.createSequentialGroup()
                                .addGroup(PanelFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnCerrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(PanelFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(btnVerProductos1, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                                        .addComponent(btnBuscarFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(PanelFechaLayout.createSequentialGroup()
                                        .addGroup(PanelFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtTotalIngresado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel14))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(PanelFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtTotalAbonado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel16))))
                                .addGap(37, 37, 37)))
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelFechaLayout.createSequentialGroup()
                        .addGroup(PanelFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSaldo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addGap(64, 64, 64))))
        );

        tbpestana.addTab("Remision/Fecha", PanelFecha);

        panelEstado.setBackground(new java.awt.Color(255, 255, 255));

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "DEPOSITO", "ENTREGADO", "PROCESO" }));

        btnVerProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnVerProductos.setText("Ver Productos");
        btnVerProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerProductosActionPerformed(evt);
            }
        });

        tblEstado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "N° Factura", "Forma de pago", "Estado", "Fecha", "Iva", "Total a pagar", "Total pagado", "Fecha Entrega", "Cliente", "Usuario"
            }
        ));
        jScrollPane6.setViewportView(tblEstado);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnCerrar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/SALIR.png"))); // NOI18N
        btnCerrar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrar2ActionPerformed(evt);
            }
        });

        jLabel5.setText("Estado:");

        jLabel12.setText("Total");

        javax.swing.GroupLayout panelEstadoLayout = new javax.swing.GroupLayout(panelEstado);
        panelEstado.setLayout(panelEstadoLayout);
        panelEstadoLayout.setHorizontalGroup(
            panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1221, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEstadoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addComponent(btnVerProductos)
                .addGap(72, 72, 72)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTotalEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCerrar2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelEstadoLayout.setVerticalGroup(
            panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEstadoLayout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
                .addGroup(panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEstadoLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(btnCerrar2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelEstadoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel12)
                                .addComponent(txtTotalEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnVerProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
        );

        tbpestana.addTab("Estado", panelEstado);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnBuscarId.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnBuscarId.setText("Buscar");
        btnBuscarId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarIdActionPerformed(evt);
            }
        });

        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        btnVerProductosId.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnVerProductosId.setText("Ver Productos");
        btnVerProductosId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerProductosIdActionPerformed(evt);
            }
        });

        jLabel4.setText("Id Factura");

        tblId.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "N° Factura", "Forma de pago", "Estado", "Fecha", "Iva", "Total a pagar", "Total pagado", "Fecha Entrega", "Cliente", "Usuario"
            }
        ));
        jScrollPane7.setViewportView(tblId);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnCerrar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/SALIR.png"))); // NOI18N
        btnCerrar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrar3ActionPerformed(evt);
            }
        });

        jButton7.setText("Abonar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1221, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(btnBuscarId, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(btnVerProductosId)
                .addGap(31, 31, 31)
                .addComponent(jButton7)
                .addGap(33, 33, 33)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCerrar3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(btnCerrar3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jButton7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBuscarId, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnVerProductosId, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        tbpestana.addTab("Numero Remision", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tbTipoPago.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "N° Factura", "Forma de pago", "Estado", "Fecha", "Iva", "Total a pagar", "Total pagado", "Fecha Entrega", "Cliente", "Usuario"
            }
        ));
        jScrollPane8.setViewportView(tbTipoPago);

        cmbTipoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "CONTADO", "CREDITO" }));

        jLabel6.setText("Estado:");

        btnBuscarTipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnBuscarTipo.setText("Buscar");
        btnBuscarTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarTipoActionPerformed(evt);
            }
        });

        btnVerProductosTipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnVerProductosTipo.setText("Ver Productos");
        btnVerProductosTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerProductosTipoActionPerformed(evt);
            }
        });

        btnImprimirTipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        btnImprimirTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirTipoActionPerformed(evt);
            }
        });

        btnCerrar4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/SALIR.png"))); // NOI18N
        btnCerrar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrar4ActionPerformed(evt);
            }
        });

        jButton6.setText("Abonar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 1201, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTipoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(btnBuscarTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnVerProductosTipo)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6)
                        .addGap(18, 18, 18)
                        .addComponent(btnImprimirTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrar4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 270, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(cmbTipoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(7, 7, 7))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnImprimirTipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnBuscarTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnVerProductosTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(btnCerrar4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75))
        );

        tbpestana.addTab("Tipo Pago", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tbTipoMaterial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "N° Factura", "Forma de pago", "Estado", "Fecha", "Iva", "Total a pagar", "Total pagado", "Fecha Entrega", "Cliente", "Usuario"
            }
        ));
        jScrollPane9.setViewportView(tbTipoMaterial);

        cmbTipoMaterial.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "CONTADO", "CUOTAS" }));
        cmbTipoMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoMaterialActionPerformed(evt);
            }
        });

        btnVerProductosMaterial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnVerProductosMaterial.setText("Ver Productos");
        btnVerProductosMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerProductosMaterialActionPerformed(evt);
            }
        });

        btnImprimirMaterial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        btnImprimirMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirMaterialActionPerformed(evt);
            }
        });

        jLabel7.setText("Material");

        btnCerrarMaterial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/SALIR.png"))); // NOI18N
        btnCerrarMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarMaterialActionPerformed(evt);
            }
        });

        jLabel18.setText("Total mandado hacer");

        jLabel20.setText("Total Abonado");

        jLabel21.setText("Deben");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTipoMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnImprimirMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(213, 213, 213)
                        .addComponent(btnVerProductosMaterial)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTotalIngresado2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(txtTotalAbonado2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel21)
                        .addGap(10, 10, 10)
                        .addComponent(txtSaldo2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCerrarMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCerrarMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnVerProductosMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(cmbTipoMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(btnImprimirMaterial, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtSaldo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel21))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtTotalAbonado2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel20))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtTotalIngresado2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel18))))))
        );

        tbpestana.addTab("Tipo Materia/Factural", jPanel3);

        panelUsuario1.setBackground(new java.awt.Color(255, 255, 255));

        tblUsuario1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "N° Factura", "Forma de pago", "Estado", "Fecha", "Iva", "Total a pagar", "Total pagado", "Fecha Entrega", "Cliente", "Usuario"
            }
        ));
        jScrollPane2.setViewportView(tblUsuario1);

        btnCerrar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/SALIR.png"))); // NOI18N
        btnCerrar5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrar5ActionPerformed(evt);
            }
        });

        jLabel15.setText("Clientes");

        jComboBox4.setEditable(true);
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MADERA", "ROBLE", "METAL" }));

        jButton16.setText("OK");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setText("ACT");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/SWN.png"))); // NOI18N
        jLabel19.setText("jLabel5");

        javax.swing.GroupLayout panelUsuario1Layout = new javax.swing.GroupLayout(panelUsuario1);
        panelUsuario1.setLayout(panelUsuario1Layout);
        panelUsuario1Layout.setHorizontalGroup(
            panelUsuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUsuario1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton16)
                .addGap(3, 3, 3)
                .addComponent(jButton17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 756, Short.MAX_VALUE)
                .addComponent(btnCerrar5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(panelUsuario1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelUsuario1Layout.setVerticalGroup(
            panelUsuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuario1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                .addGroup(panelUsuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelUsuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15))
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCerrar5, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 186, Short.MAX_VALUE)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );

        tbpestana.addTab("Items/Usuario", panelUsuario1);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        tbTipoMaterial1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "N° Factura", "Forma de pago", "Estado", "Fecha", "Iva", "Total a pagar", "Total pagado", "Fecha Entrega", "Cliente", "Usuario"
            }
        ));
        jScrollPane10.setViewportView(tbTipoMaterial1);

        cmbTipoMaterial1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoMaterial1ActionPerformed(evt);
            }
        });

        btnVerProductosMaterial1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnVerProductosMaterial1.setText("Buscar Fechas");
        btnVerProductosMaterial1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerProductosMaterial1ActionPerformed(evt);
            }
        });

        jLabel22.setText("Material");

        btnCerrarMaterial1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/SALIR.png"))); // NOI18N
        btnCerrarMaterial1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarMaterial1ActionPerformed(evt);
            }
        });

        jLabel23.setText("Total mandado hacer");

        jLabel24.setText("Desde");

        jLabel25.setText("Hasta");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTipoMaterial1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(jLabel24)
                        .addGap(18, 18, 18)
                        .addComponent(dpDesde1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel25)
                        .addGap(18, 18, 18)
                        .addComponent(dpDesde2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnVerProductosMaterial1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTotalIngresado3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                        .addComponent(btnCerrarMaterial1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 726, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCerrarMaterial1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtTotalIngresado3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel23))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel24)
                                .addComponent(dpDesde1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel25)
                                .addComponent(dpDesde2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnVerProductosMaterial1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(cmbTipoMaterial1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6))
        );

        tbpestana.addTab("Tipo Material", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbpestana)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbpestana)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarUsuarioActionPerformed
        String cedula = txtCedula.getText();

        if (cedula.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Ingrese una cedula");
        } else {
            ClienteDTO c = servicio.buscarClienteCedula(cedula);
            buscarUsuarioCedula(c.getId());
            txtCedula.setText("");
        }
    }//GEN-LAST:event_btnBuscarUsuarioActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String estado = cmbEstado.getSelectedItem().toString();

        if (estado.equalsIgnoreCase("-")) {
            JOptionPane.showMessageDialog(null, "Seleccione estado");
        } else {
            buscarEstadoFacturas(estado);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnBuscarFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFechaActionPerformed
        Date fechaDesde = Date.valueOf(dpDesde.getDateStringOrSuppliedString(LocalDate.now().toString()));
        //       Date fechaEntrega = Date.valueOf(dpDesde.getDateStringOrEmptyString());
        //Calendar ahoraCal = Calendar.getInstance();
        //System.out.println(ahoraCal.getClass());

        Date fechaHasta = Date.valueOf(dpHasta.getDateStringOrSuppliedString(LocalDate.now().toString()));
        if (fechaDesde.toString().equalsIgnoreCase("") || fechaHasta.toString().equalsIgnoreCase("")) {

            JOptionPane.showMessageDialog(null, "Seleccionar Fecha");
        } else {

            //String fecha= 
//           Date h= Date.valueOf(dpHasta.getDateStringOrEmptyString().toString());
            buscarFechaFacturas(fechaDesde, fechaHasta);
            // buscarFechaFactura(fechaDesde);

        }


    }//GEN-LAST:event_btnBuscarFechaActionPerformed

    private void btnVerProductos2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerProductos2ActionPerformed
        int filaSeleccionada = tblUsuario.getSelectedRow();

        if (filaSeleccionada < 0) {
            mostrarMensaje("Seleccione una factura de la tabla");
            return;
        }

        Object idObj = tblUsuario.getValueAt(filaSeleccionada, 0);

        long id = Long.parseLong(String.valueOf(idObj));

        ifrmProductosPorFactura vistaProductosPorFactura = new ifrmProductosPorFactura(id);
        getDesktopPane().add(vistaProductosPorFactura);
        vistaProductosPorFactura.setVisible(true);
        vistaProductosPorFactura.setSize(1000, 500);

    }//GEN-LAST:event_btnVerProductos2ActionPerformed

    private void btnVerProductos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerProductos1ActionPerformed
        int filaSeleccionada = tblFacturaFec.getSelectedRow();

        if (filaSeleccionada < 0) {
            mostrarMensaje("Seleccione una factura de la tabla");
            return;
        }

        Object idObj = tblFacturaFec.getValueAt(filaSeleccionada, 0);

        long id = Long.parseLong(String.valueOf(idObj));

        ifrmProductosPorFactura vistaProductosPorFactura = new ifrmProductosPorFactura(id);
        getDesktopPane().add(vistaProductosPorFactura);
        vistaProductosPorFactura.setVisible(true);
        vistaProductosPorFactura.setSize(1000, 500);
    }//GEN-LAST:event_btnVerProductos1ActionPerformed

    private void btnVerProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerProductosActionPerformed
        int filaSeleccionada = tblEstado.getSelectedRow();

        if (filaSeleccionada < 0) {
            mostrarMensaje("Seleccione una factura de la tabla");
            return;
        }

        Object idObj = tblEstado.getValueAt(filaSeleccionada, 0);

        long id = Long.parseLong(String.valueOf(idObj));

        ifrmProductosPorFactura vistaProductosPorFactura = new ifrmProductosPorFactura(id);
        getDesktopPane().add(vistaProductosPorFactura);
        vistaProductosPorFactura.setVisible(true);
        vistaProductosPorFactura.setSize(471, 337);
    }//GEN-LAST:event_btnVerProductosActionPerformed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void btnBuscarIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarIdActionPerformed
        String id = txtId.getText();
        long ID = Long.parseLong(id);

        if (id.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Ingrese id");
        } else {
            buscarFacturaId(ID);
            txtId.setText("");
        }
    }//GEN-LAST:event_btnBuscarIdActionPerformed

    private void btnVerProductosIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerProductosIdActionPerformed
        int filaSeleccionada = tblId.getSelectedRow();

        if (filaSeleccionada < 0) {
            mostrarMensaje("Seleccione una factura de la tabla");
            return;
        }

        Object idObj = tblId.getValueAt(filaSeleccionada, 0);

        long id = Long.parseLong(String.valueOf(idObj));

        ifrmProductosPorFactura vistaProductosPorFactura = new ifrmProductosPorFactura(id);
        getDesktopPane().add(vistaProductosPorFactura);
        vistaProductosPorFactura.setVisible(true);
        vistaProductosPorFactura.setSize(1000, 500);
    }//GEN-LAST:event_btnVerProductosIdActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        cerrar();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);
        long ids = 0;
        int[] seleccion = tblId.getSelectedRows();
        for (int i = 0; i < seleccion.length; i++) {
            //System.out.println(i);
            //System.out.println(seleccion.length+"total filas");
            Object d = tblId.getValueAt(seleccion[i], 0);
            String s = String.valueOf(d);
            ids = Long.parseLong(s);
            if (i < seleccion.length - 1) {
                cadena += s + ",";
            } else {
                cadena += s;
            }

        }
        System.out.println(cadena);

        long productos = servicio.consultaTotalProductos(ids);
        Object o = tblUsuario.getValueAt(0, 0);
        String id = String.valueOf(o);
        String imagen = "";
        String archivo = "";
        if (productos < 6) {
            imagen = c + "\\logo.jpeg";
            archivo = c + "\\Factura4.jasper";
//            mostrarMensaje("entro 1");
        } else {
            imagen = c + "\\logo.jpeg";
            archivo = c + "\\Factura2.jasper";
//         mostrarMensaje("entro 2");
        }
        Conexion cn = new Conexion();
        JasperReport jr = null;
        try {
            Map parametro = new HashMap();
            parametro.put("IDS", cadena);
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
//            JasperViewer jv = new JasperViewer(jp, false);
//            jv.setVisible(true);
//            jv.setTitle("FACTURA");
        } catch (JRException ex) {
            Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
        }    // TODO add you
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        long ids = 0;
        String c = b.buscar(CARPETA);
        int[] seleccion = tblUsuario.getSelectedRows();
        for (int i = 0; i < seleccion.length; i++) {
            //System.out.println(i);
            //System.out.println(seleccion.length+"total filas");
            Object d = tblUsuario.getValueAt(seleccion[i], 0);
            String s = String.valueOf(d);
            ids = Long.parseLong(s);
            if (i < seleccion.length - 1) {
                cadena += s + ",";
            } else {
                cadena += s;
            }

        }
        System.out.println(cadena);
        long productos = servicio.consultaTotalProductos(ids);
        Object o = tblUsuario.getValueAt(0, 0);
        String id = String.valueOf(o);
        String imagen = "";
        String archivo = "";
        if (productos < 6) {
            imagen = c + "\\logo.jpeg";
            archivo = c + "\\Factura4.jasper";
//            mostrarMensaje("entro 1");
        } else {
            imagen = c + "\\logo.jpeg";
            archivo = c + "\\Factura2.jasper";
//         mostrarMensaje("entro 2");
        }

        Conexion cn = new Conexion();
        JasperReport jr = null;
        try {
            Map parametro = new HashMap();
            parametro.put("IDS", cadena);
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
//            JasperViewer jv = new JasperViewer(jp, false);
//            jv.setVisible(true);
//            jv.setTitle("FACTURA");
        } catch (JRException ex) {
            Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);
        int[] seleccion = tblFacturaFec.getSelectedRows();
        for (int i = 0; i < seleccion.length; i++) {
            //System.out.println(i);
            //System.out.println(seleccion.length+"total filas");
            Object d = tblFacturaFec.getValueAt(seleccion[i], 0);
            String s = String.valueOf(d);
            if (i < seleccion.length - 1) {
                cadena += s + ",";
            } else {
                cadena += s;
            }

        }
        System.out.println(cadena);
        String imagen = c + "\\logo.jpeg";
        String archivo = c + "\\Factura2.jasper";
        Conexion cn = new Conexion();
        JasperReport jr = null;
        try {
            Map parametro = new HashMap();
            parametro.put("IDS", cadena);
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
//            JasperViewer jv = new JasperViewer(jp, false);
//            jv.setVisible(true);
//            jv.setTitle("FACTURA");
        } catch (JRException ex) {
            Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
        }       // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);
        int cantidad = tblEstado.getRowCount();
        int ca = 0;
        while (ca < cantidad) {
            Object objectId = tblEstado.getValueAt(ca, 0);
            int numero = Integer.parseInt(String.valueOf(objectId));
            cadena += numero + "," + 0;
            ca++;
        }
        System.out.println(cadena);
        String imagen = c + "\\logo.jpeg";
        String archivo = c + "\\Registro2.jasper";

        Conexion cn = new Conexion();
        JasperReport jr = null;
        try {
            Map parametro = new HashMap();
            parametro.put("IDS", cadena);
            parametro.put("imagen", imagen);
            jr = (JasperReport) JRLoader.loadObjectFromFile(archivo);
            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, cn.getConnection());
            JasperExportManager.exportReportToPdfFile(jp, "D:\\Registro2.pdf");
            String file = new String("D:\\Registro2.pdf");
            try {
                Runtime.getRuntime().exec("cmd /c start " + file);
            } catch (IOException ex) {
                Logger.getLogger(ifrmVentas3.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JRException ex) {
            Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
        }      // TODO add you
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnCerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrar1ActionPerformed
        cerrar();        // TODO add your handling code here:
    }//GEN-LAST:event_btnCerrar1ActionPerformed

    private void btnCerrar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrar2ActionPerformed
        cerrar();
    }//GEN-LAST:event_btnCerrar2ActionPerformed

    private void btnCerrar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrar3ActionPerformed
        cerrar();
    }//GEN-LAST:event_btnCerrar3ActionPerformed

    private void btnBuscarTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarTipoActionPerformed

        String estado = cmbTipoPago.getSelectedItem().toString();

        if (estado.equalsIgnoreCase("-")) {
            JOptionPane.showMessageDialog(null, "Seleccione tipo");
        } else {

            buscarTipoFacturas(estado);
        }
    }//GEN-LAST:event_btnBuscarTipoActionPerformed

    private void btnVerProductosTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerProductosTipoActionPerformed
        int filaSeleccionada = tbTipoPago.getSelectedRow();

        if (filaSeleccionada < 0) {
            mostrarMensaje("Seleccione una factura de la tabla");
            return;
        }

        Object idObj = tbTipoPago.getValueAt(filaSeleccionada, 0);

        long id = Long.parseLong(String.valueOf(idObj));

        ifrmProductosPorFactura vistaProductosPorFactura = new ifrmProductosPorFactura(id);
        getDesktopPane().add(vistaProductosPorFactura);
        vistaProductosPorFactura.setVisible(true);
        vistaProductosPorFactura.setSize(1000, 500);

    }//GEN-LAST:event_btnVerProductosTipoActionPerformed

    private void btnImprimirTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirTipoActionPerformed
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);
        int[] seleccion = tbTipoPago.getSelectedRows();
        for (int i = 0; i < seleccion.length; i++) {
            //System.out.println(i);
            //System.out.println(seleccion.length+"total filas");
            Object d = tbTipoPago.getValueAt(seleccion[i], 0);
            String s = String.valueOf(d);
            if (i < seleccion.length - 1) {
                cadena += s + ",";
            } else {
                cadena += s;
            }

        }
        System.out.println(cadena);
        String imagen = c + "\\logo.jpeg";
        String archivo = c + "\\Factura.jasper";
        Conexion cn = new Conexion();
        JasperReport jr = null;
        try {
            Map parametro = new HashMap();
            parametro.put("IDS", cadena);
            parametro.put("imagen", imagen);
            jr = (JasperReport) JRLoader.loadObjectFromFile(archivo);
            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, cn.getConnection());
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
            jv.setTitle("FACTURA");
        } catch (JRException ex) {
            Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }//GEN-LAST:event_btnImprimirTipoActionPerformed

    private void btnCerrar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrar4ActionPerformed
        cerrar();
    }//GEN-LAST:event_btnCerrar4ActionPerformed

    private void btnVerProductosMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerProductosMaterialActionPerformed
        int filaSeleccionada = tbTipoMaterial.getSelectedRow();

        if (filaSeleccionada < 0) {
            mostrarMensaje("Seleccione una factura de la tabla");
            return;
        }

        Object idObj = tbTipoMaterial.getValueAt(filaSeleccionada, 0);

        long id = Long.parseLong(String.valueOf(idObj));

        ifrmProductosPorFactura vistaProductosPorFactura = new ifrmProductosPorFactura(id);
        getDesktopPane().add(vistaProductosPorFactura);
        vistaProductosPorFactura.setVisible(true);
        vistaProductosPorFactura.setSize(1000, 500);
    }//GEN-LAST:event_btnVerProductosMaterialActionPerformed

    private void btnCerrarMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarMaterialActionPerformed
        cerrar();
    }//GEN-LAST:event_btnCerrarMaterialActionPerformed
    private void buscarTipoMaterial(long id) {
        double pagar=0;
        double pagado=0;

        final String[] columnas = {"N° Remision", "Fecha", "Iva", "Total a pagar", "Total pagado", "Cliente", "Usuario", "Saldo Debe", "Forma de pago", "Factura iva"};
        //configurando los eventos de la tabla
        dtbmEstado = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 8;
            }
        };
        //Llenando la tabla
        ArrayList<FacturaDTO>f=servicio.listarMaterialFactura(id);
        for (FacturaDTO fa : f) {
            pagar=fa.getTotalPagar()+pagar;
            pagado=fa.getTotalPagado()+pagado;
        }
        servicio.listarMaterialFactura(id).forEach((FacturaDTO factura) -> {
            dtbmEstado.addRow(factura.toArray());
        });

        txtTotalIngresado2.setText(formateador.format(pagar));
        txtTotalAbonado2.setText(formateador.format(pagado));
        txtSaldo2.setText(formateador.format(pagar-pagado));
        tbTipoMaterial.setModel(dtbmEstado);
        

        dtbmEstado.addTableModelListener((TableModelEvent e) -> {

        });
    }
    
    private void buscarTipoMaterial1(long id) {
        double pagar=0;
        double pagado=0;

        final String[] columnas = {"N° item", "Cliente", "Fecha", "Total a pagar", "Fecha entrega", "Material", "Estado", "Codigo Remision", "Comentario"};
        //configurando los eventos de la tabla
        dtbmEstado = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 8;
            }
        };
        //Llenando la tabla
         Object[] object = new Object[14];
        //servicio.listarUsuariosNomina()
        //      .forEach((UsuarioDTO usuario) -> {
        //        dtblNomina.addRow(usuario.toArray());
        //      
        double totalPagar=0;
        for (RegistroDTO u : servicio.listarRegistrosMaterial(id)) {
         
            
            
            Timestamp stamp = u.getFecha();
             Date date = new Date(stamp.getTime());
             String S = new SimpleDateFormat("dd/MM/yyyy").format(date);
//            mostrarMensaje();
            
            object[0] = u.getId();
            object[1] = u.getCliente().getNombres() +" "+u.getCliente().getApellidos();
            object[2] = S;
            object[3] = formateador.format(u.getTotal_pagar());
            object[4] = u.getFechaEntrega();
            object[5] = u.getMaterial().getNombre();
            object[6] = u.getEstado();
            object[7] = u.getId_factura();
            object[8] = u.getComentario();

            totalPagar=totalPagar+u.getTotal_pagar();
            dtbmEstado.addRow(object);

        }
//        tblEstado.setDefaultRenderer(Object.class, f);
            txtTotalIngresado3.setText(formateador.format(totalPagar));
        tbTipoMaterial1.setModel(dtbmEstado);
 tbTipoMaterial1.getColumnModel().getColumn(0).setMaxWidth(90);
        tbTipoMaterial1.getColumnModel().getColumn(1).setMaxWidth(400);
        tbTipoMaterial1.getColumnModel().getColumn(2).setMaxWidth(300);
        tbTipoMaterial1.getColumnModel().getColumn(3).setMaxWidth(100);
        tbTipoMaterial1.getColumnModel().getColumn(4).setMaxWidth(100);
        tbTipoMaterial1.getColumnModel().getColumn(5).setMaxWidth(90);
        tbTipoMaterial1.getColumnModel().getColumn(6).setMaxWidth(90);
        tbTipoMaterial1.getColumnModel().getColumn(7).setMaxWidth(80);
        tbTipoMaterial1.getColumnModel().getColumn(8).setMaxWidth(500);
        dtbmEstado.addTableModelListener((TableModelEvent e) -> {

            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar
                //long id;
                Object objId = tblEstado.getValueAt(e.getFirstRow(), 0);
                String formaDePago = String.valueOf(tblEstado.getValueAt(e.getFirstRow(), 1));

                String estados = String.valueOf(tblEstado.getValueAt(e.getFirstRow(), 6));

                

            }
        });
        ArrayList<String> a = new ArrayList<>();
        a.add("DEPOSITO");
        a.add("ENTREGADO");
        a.add("PROCESO");
        agregarJComboBoxAColumna(tblEstado, 6, a.toArray());
    }
    private void buscarTipoMaterialFecha(long id,Date fecha,Date fecha2) {
        double pagar=0;
        double pagado=0;

        final String[] columnas = {"N° item", "Cliente", "Fecha", "Total a pagar", "Fecha entrega", "Material", "Estado", "Codigo Remision", "Comentario"};
        //configurando los eventos de la tabla
        dtbmEstado = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 8;
            }
        };
        //Llenando la tabla
         Object[] object = new Object[14];
        //servicio.listarUsuariosNomina()
        //      .forEach((UsuarioDTO usuario) -> {
        //        dtblNomina.addRow(usuario.toArray());
        //      
        double totalPagar=0;
        for (RegistroDTO u : servicio.listarRegistrosMaterialFecha(id,fecha,fecha2)) {
         
            
            
            Timestamp stamp = u.getFecha();
             Date date = new Date(stamp.getTime());
             String S = new SimpleDateFormat("dd/MM/yyyy").format(date);
//            mostrarMensaje();
            
            object[0] = u.getId();
            object[1] = u.getCliente().getNombres() +" "+u.getCliente().getApellidos();
            object[2] = S;
            object[3] = formateador.format(u.getTotal_pagar());
            object[4] = u.getFechaEntrega();
            object[5] = u.getMaterial().getNombre();
            object[6] = u.getEstado();
            object[7] = u.getId_factura();
            object[8] = u.getComentario();

            totalPagar=totalPagar+u.getTotal_pagar();
            dtbmEstado.addRow(object);

        }
//        tblEstado.setDefaultRenderer(Object.class, f);
            txtTotalIngresado3.setText(formateador.format(totalPagar));
        tbTipoMaterial1.setModel(dtbmEstado);
 tbTipoMaterial1.getColumnModel().getColumn(0).setMaxWidth(90);
        tbTipoMaterial1.getColumnModel().getColumn(1).setMaxWidth(400);
        tbTipoMaterial1.getColumnModel().getColumn(2).setMaxWidth(300);
        tbTipoMaterial1.getColumnModel().getColumn(3).setMaxWidth(100);
        tbTipoMaterial1.getColumnModel().getColumn(4).setMaxWidth(100);
        tbTipoMaterial1.getColumnModel().getColumn(5).setMaxWidth(90);
        tbTipoMaterial1.getColumnModel().getColumn(6).setMaxWidth(90);
        tbTipoMaterial1.getColumnModel().getColumn(7).setMaxWidth(80);
        tbTipoMaterial1.getColumnModel().getColumn(8).setMaxWidth(500);
        dtbmEstado.addTableModelListener((TableModelEvent e) -> {

            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar
                //long id;
                Object objId = tblEstado.getValueAt(e.getFirstRow(), 0);
                String formaDePago = String.valueOf(tblEstado.getValueAt(e.getFirstRow(), 1));

                String estados = String.valueOf(tblEstado.getValueAt(e.getFirstRow(), 6));

                

            }
        });
        ArrayList<String> a = new ArrayList<>();
        a.add("DEPOSITO");
        a.add("ENTREGADO");
        a.add("PROCESO");
        agregarJComboBoxAColumna(tblEstado, 6, a.toArray());
    }
    private void cmbTipoMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoMaterialActionPerformed
        if (cmbTipoMaterial.getSelectedItem() != null) {
            String nombre = cmbTipoMaterial.getSelectedItem().toString();
            MaterialDTO ingreso = servicio.consultarMaterialNombre(nombre);
//        System.out.println(ingreso.getId() + "ingreso");
            if (cmbTipoMaterial.getSelectedItem().toString().equalsIgnoreCase("ninguno")) {
                //llenarTablaPagos();
            }

            if (ingreso != null) {
                buscarTipoMaterial(ingreso.getId());
            }
        }
    }//GEN-LAST:event_cmbTipoMaterialActionPerformed
    private void crearComboEditable3(JComboBox<Object> combo, String tabla) {

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
                    case "CLIENTES":
                        //Busca el cliente por nombre
                        editarCombo(combo, servicio.buscarCliente(texto).toArray());
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

                        //   agregarProductoAFactura(producto);
                        // calcularTotales();
                        //mostrarMensaje(producto.getPrecioUnitario()+"");
                        break;
                    case "CLIENTES":

                        ClienteDTO cliente = (ClienteDTO) seleccion;
                        buscarUsuarioCedula(cliente.getId());
                        System.out.println("id cliente" + cliente.getId());

                        System.out.println(cliente.getId() + "codigo");

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
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        
        crearComboEditable(comboBuscarCliente, "CLIENTES");
        llenarComboClientesventas2();
        AutoCompleteDecorator.decorate(jComboBox3);
    }//GEN-LAST:event_formInternalFrameOpened
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
                        //editarCombo(combo, servicio.buscar(texto).toArray());
                        System.out.println("si entro");
                        break;
                    case "CLIENTES":
                        //Busca el cliente por nombre
                        editarCombo(combo, servicio.buscarCliente(texto).toArray());
                        System.out.println(texto);
                        break;
                    case "FACTURAS":
                        //editarCombo(combo, servicio.buscarFactura(texto).toArray());
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
                    case "CLIENTES":

                        ClienteDTO cliente = (ClienteDTO) seleccion;
//                        textCodigoCliente.setText(cliente.getId() + "");
                        buscarUsuarioCedula(cliente.getId());
                        String document = "";
                        String direcc = "";

//                        textDocumento.setText(document);
//                        textNombres.setText(cliente.getNombres());
//                        textApellidos.setText(cliente.getApellidos());
//                        textTelefono.setText(cliente.getTelefono() + "");
//                        textDireccion.setText(direcc);
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

    private void editarCombo2(JComboBox<Object> combo, Object[] array) {

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
    private void comboBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBuscarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBuscarClienteActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int x = tbTipoPago.getSelectedRow();
        double pago = 0;
        String FormaPago = String.valueOf(tbTipoPago.getValueAt(x, 8));
        long id = Long.parseLong(String.valueOf(tbTipoPago.getValueAt(x, 0)));
        if (FormaPago.equalsIgnoreCase("CREDITO")) {
            String valor = JOptionPane.showInputDialog("Cuanto valor va abonar?");
            valor = valor.replace(".", "");
            valor = valor.replace(",", ".");
            double v = Double.parseDouble(valor);
            servicio.registrarPago(v, id);
            ArrayList<FacturaDTO> f = servicio.buscarFactura(id);
            for (FacturaDTO fa : f) {
                pago = fa.getTotalPagado();
            }
            servicio.ActualizarPago(id, pago + v);
            JOptionPane.showMessageDialog(null, valor);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int x = tblUsuario.getSelectedRow();
        double pago = 0;
        String FormaPago = String.valueOf(tblUsuario.getValueAt(x, 8));
        long id = Long.parseLong(String.valueOf(tblUsuario.getValueAt(x, 0)));
        if (FormaPago.equalsIgnoreCase("CREDITO")) {
            String valor = JOptionPane.showInputDialog("Cuanto valor va abonar?");
            valor = valor.replace(".", "");
            valor = valor.replace(",", ".");
            double v = Double.parseDouble(valor);
            servicio.registrarPago(v, id);
            ArrayList<FacturaDTO> f = servicio.buscarFactura(id);
            for (FacturaDTO fa : f) {
                pago = fa.getTotalPagado();
            }
            servicio.ActualizarPago(id, pago + v);
            JOptionPane.showMessageDialog(null, valor);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        int x = tblId.getSelectedRow();
        double pago = 0;
        String FormaPago = String.valueOf(tblId.getValueAt(x, 8));
        long id = Long.parseLong(String.valueOf(tblId.getValueAt(x, 0)));
        if (FormaPago.equalsIgnoreCase("CREDITO")) {
            String valor = JOptionPane.showInputDialog("Cuanto valor va abonar?");
            valor = valor.replace(".", "");
            valor = valor.replace(",", ".");
            double v = Double.parseDouble(valor);
            servicio.registrarPago(v, id);
            ArrayList<FacturaDTO> f = servicio.buscarFactura(id);
            for (FacturaDTO fa : f) {
                pago = fa.getTotalPagado();
            }
            servicio.ActualizarPago(id, pago + v);
            JOptionPane.showMessageDialog(null, valor);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        int filaSeleccionada = tblUsuario.getSelectedRow();
        double total = 0;
        long id_r = 0;
        if (filaSeleccionada < 0) {
            mostrarMensaje("Seleccione un remision de la tabla");
            return;
        }

        Object idObj = tblUsuario.getValueAt(filaSeleccionada, 0);

        if (idObj == null) {
            dtbmUsuarios.removeRow(filaSeleccionada);
            return;
        }

        double totalPagado = 0;

        ArrayList<FacturaDTO> f = servicio.buscarFactura(Long.parseLong(String.valueOf(idObj)));
        boolean x = servicio.eliminarFactura(Long.parseLong(String.valueOf(idObj)));
        if (x) {
            
           boolean z= servicio.eliminarPagos(Long.parseLong(String.valueOf(idObj)));
//            mostrarMensaje(Long.parseLong(String.valueOf(idObj))+"hrthrt"+z);
            long fac = 0;
            String usado = "";
            servicio.cambiarIdFacturasCero(fac, Long.parseLong(String.valueOf(idObj)));
            mostrarMensaje("SE ELIMINO REMISION");
        }


    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        int x = tblUsuario.getSelectedRow();
        double pago = 0;
//        String FormaPago = String.valueOf(tblUsuario.getValueAt(x, 2));
        
        long id = Long.parseLong(String.valueOf(tblUsuario.getValueAt(x, 0)));
//        if (FormaPago.equalsIgnoreCase("CREDITO")) {
            String valor = JOptionPane.showInputDialog("ESCRIBA EL NUMERO DE FACTURA CON IVA");
            
           boolean z= servicio.editarIvaFactura(id, valor);
           if(z){
               mostrarMensaje("Se ha editado el numero de iva");
           }
//        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if (jComboBox3 != null) {
            String string = jComboBox3.getSelectedItem().toString();

            if (!string.equalsIgnoreCase("")) {
                String[] parts = string.split("/");
                String part1 = parts[0]; // 123
                String part2 = parts[1];

               // ClienteDTO cliente = servicio.consultarCliente(Long.parseLong(part2));

//                textDocumento1.setText(cliente.getId() + "");
                buscarUsuarioCedula(Long.parseLong(part2));

             //   System.out.println(cliente.getId() + "codigo");
                
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed
public void llenarComboClientesventas2() {

        jComboBox3.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<ClienteDTO> listar = servicio.listarClientes();
        jComboBox3.addItem("-");
        for (ClienteDTO n : listar) {
            jComboBox3.addItem(n.getNombres() + " " + n.getApellidos() + "/" + n.getId());
        }
    }
public void llenarComboClientesventas3() {

        jComboBox4.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<ClienteDTO> listar = servicio.listarClientes();
        jComboBox4.addItem("-");
        for (ClienteDTO n : listar) {
            jComboBox4.addItem(n.getNombres() + " " + n.getApellidos() + "/" + n.getId());
        }
    }
    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        llenarComboClientesventas2();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void btnImprimirMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirMaterialActionPerformed
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);
        int[] seleccion = tbTipoMaterial.getSelectedRows();
        for (int i = 0; i < seleccion.length; i++) {
            //System.out.println(i);
            //System.out.println(seleccion.length+"total filas");
            Object d = tbTipoMaterial.getValueAt(seleccion[i], 0);
            String s = String.valueOf(d);
            if (i < seleccion.length - 1) {
                cadena += s + ",";
            } else {
                cadena += s;
            }

        }
        System.out.println(cadena);
        String imagen = c + "\\logo.jpeg";
        String archivo = c + "\\Factura.jasper";
        Conexion cn = new Conexion();
        JasperReport jr = null;
        try {
            Map parametro = new HashMap();
            parametro.put("IDS", cadena);
            parametro.put("imagen", imagen);
            jr = (JasperReport) JRLoader.loadObjectFromFile(archivo);
            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, cn.getConnection());
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
            jv.setTitle("FACTURA");
        } catch (JRException ex) {
            Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }//GEN-LAST:event_btnImprimirMaterialActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        llenarComboClientesventas3();
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
 if (jComboBox4 != null) {
            String string = jComboBox4.getSelectedItem().toString();

            if (!string.equalsIgnoreCase("")) {
                String[] parts = string.split("/");
                String part1 = parts[0]; // 123
                String part2 = parts[1];

                //ClienteDTO cliente = servicio.consultarCliente(Long.parseLong(part2));

//                textDocumento1.setText(cliente.getId() + "");
                llenarTablaVentasRegistro(Long.parseLong(part2));

                //System.out.println(cliente.getId() + "codigo");
                
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton16ActionPerformed

    private void btnCerrar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrar5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCerrar5ActionPerformed

    private void cmbTipoMaterial1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoMaterial1ActionPerformed
        if (cmbTipoMaterial1.getSelectedItem() != null) {
            String nombre = cmbTipoMaterial1.getSelectedItem().toString();
            MaterialDTO ingreso = servicio.consultarMaterialNombre(nombre);
//        System.out.println(ingreso.getId() + "ingreso");
            if (cmbTipoMaterial1.getSelectedItem().toString().equalsIgnoreCase("ninguno")) {
                //llenarTablaPagos();
            }

            if (ingreso != null) {
                buscarTipoMaterial1(ingreso.getId());
            }
        }
    }//GEN-LAST:event_cmbTipoMaterial1ActionPerformed

    private void btnVerProductosMaterial1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerProductosMaterial1ActionPerformed
         String nombre = cmbTipoMaterial1.getSelectedItem().toString();
            MaterialDTO ingreso = servicio.consultarMaterialNombre(nombre);       
        Date fechaDesde = Date.valueOf(dpDesde1.getDateStringOrSuppliedString(LocalDate.now().toString()));
        //       Date fechaEntrega = Date.valueOf(dpDesde.getDateStringOrEmptyString());
        //Calendar ahoraCal = Calendar.getInstance();
        //System.out.println(ahoraCal.getClass());
        
        

        Date fechaHasta = Date.valueOf(dpDesde2.getDateStringOrSuppliedString(LocalDate.now().toString()));
        if (fechaDesde.toString().equalsIgnoreCase("") || fechaHasta.toString().equalsIgnoreCase("")) {

            JOptionPane.showMessageDialog(null, "Seleccionar Fecha");
        } else {

            //String fecha= 
//           Date h= Date.valueOf(dpHasta.getDateStringOrEmptyString().toString());
            buscarTipoMaterialFecha(ingreso.getId(),fechaDesde, fechaHasta);
            // buscarFechaFactura(fechaDesde);

        }

    }//GEN-LAST:event_btnVerProductosMaterial1ActionPerformed

    private void btnCerrarMaterial1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarMaterial1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCerrarMaterial1ActionPerformed

    private void tbpestanaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbpestanaMouseClicked
       if(tbpestana.getSelectedIndex()==6){
        llenarComboClientesventas3();
       }
       if(tbpestana.getSelectedIndex()==5){
        
        llenarComboMaterial();;
       }
       if(tbpestana.getSelectedIndex()==7){
        
        llenarComboMaterial();;
       }
    }//GEN-LAST:event_tbpestanaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelFecha;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarFecha;
    private javax.swing.JButton btnBuscarId;
    private javax.swing.JButton btnBuscarTipo;
    private javax.swing.JButton btnBuscarUsuario;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnCerrar1;
    private javax.swing.JButton btnCerrar2;
    private javax.swing.JButton btnCerrar3;
    private javax.swing.JButton btnCerrar4;
    private javax.swing.JButton btnCerrar5;
    private javax.swing.JButton btnCerrarMaterial;
    private javax.swing.JButton btnCerrarMaterial1;
    private javax.swing.JButton btnImprimirMaterial;
    private javax.swing.JButton btnImprimirTipo;
    private javax.swing.JButton btnVerProductos;
    private javax.swing.JButton btnVerProductos1;
    private javax.swing.JButton btnVerProductos2;
    private javax.swing.JButton btnVerProductosId;
    private javax.swing.JButton btnVerProductosMaterial;
    private javax.swing.JButton btnVerProductosMaterial1;
    private javax.swing.JButton btnVerProductosTipo;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JComboBox<String> cmbTipoMaterial;
    private javax.swing.JComboBox<String> cmbTipoMaterial1;
    private javax.swing.JComboBox<String> cmbTipoPago;
    private javax.swing.JComboBox<Object> comboBuscarCliente;
    private com.github.lgooddatepicker.components.DatePicker dpDesde;
    private com.github.lgooddatepicker.components.DatePicker dpDesde1;
    private com.github.lgooddatepicker.components.DatePicker dpDesde2;
    private com.github.lgooddatepicker.components.DatePicker dpHasta;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPanel panelEstado;
    private javax.swing.JPanel panelUsuario;
    private javax.swing.JPanel panelUsuario1;
    private javax.swing.JTable tbTipoMaterial;
    private javax.swing.JTable tbTipoMaterial1;
    private javax.swing.JTable tbTipoPago;
    private javax.swing.JTable tblEstado;
    private javax.swing.JTable tblFacturaFec;
    private javax.swing.JTable tblId;
    private javax.swing.JTable tblUsuario;
    private javax.swing.JTable tblUsuario1;
    private javax.swing.JTabbedPane tbpestana;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtSaldo;
    private javax.swing.JTextField txtSaldo1;
    private javax.swing.JTextField txtSaldo2;
    private javax.swing.JTextField txtTotalAbonado;
    private javax.swing.JTextField txtTotalAbonado1;
    private javax.swing.JTextField txtTotalAbonado2;
    private javax.swing.JTextField txtTotalEstado;
    private javax.swing.JTextField txtTotalIngresado;
    private javax.swing.JTextField txtTotalIngresado1;
    private javax.swing.JTextField txtTotalIngresado2;
    private javax.swing.JTextField txtTotalIngresado3;
    // End of variables declaration//GEN-END:variables
}
