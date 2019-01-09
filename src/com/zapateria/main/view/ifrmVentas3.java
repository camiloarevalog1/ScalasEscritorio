/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.view;

import com.zapateria.firebase.Firebase;
import com.zapateria.main.dto.ClienteDTO;
import com.zapateria.main.dto.FacturaDTO;
import com.zapateria.main.dto.ImpuestoDTO;
import com.zapateria.main.dto.MaterialDTO;
import com.zapateria.main.dto.PagoDTO;
import com.zapateria.main.dto.PagoFacDTO;
import com.zapateria.main.dto.ProductoDTO;
import com.zapateria.main.dto.RegistroDTO;
import com.zapateria.main.dto.UsuarioDTO;
import com.zapateria.main.dto.VentaDTO;
import com.zapateria.main.util.BuscadorCarpeta;
import com.zapateria.main.util.Conexion;
import static com.zapateria.main.view.MarcoInternoPersonalizado.REGEX_DECIMAL;
import static com.zapateria.main.view.MarcoInternoPersonalizado.SESION_USUARIO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
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
 * @author DELL
 */
public class ifrmVentas3 extends MarcoInternoPersonalizado {

    /**
     * Creates new form ifrmVentas3
     * 
     * 
     */
    
    private boolean exitoItems=false;
    private static final long serialVersionUID = -5960080089952460575L;

    private static final String VENTAS = "VENTAS", PRODUCTOS = "PRODUCTOS",
            FACTURAS = "FACTURAS", PAGOS = "PAGOS", IMPUESTOS = "IMPUESTOS";

    private FacturaDTO facturaDTO = new FacturaDTO();

    private DefaultTableModel dtbmProductos, dtbmImpuestos, dtbmFacturas,
            dtbmPagos, dtbmVentas, dtbmVentas2;

    private DefaultComboBoxModel dComboProductos;

    private DefaultListModel<Object> dlmProductos = new DefaultListModel<>(), dlmClientes = new DefaultListModel<>();
    private JDesktopPane escritorio;
    private long codigo;
    FormatoTabla f = new FormatoTabla();

    /**
     * Creates new form ifrmVentas
     */
    public ifrmVentas3() {

        initComponents();
       llenarComboMaterial();
        llenarComboClientesventas();
       
       llenarComboClientesAbonos();
        comboBuscarCliente1.setVisible(false);
        comboBuscarCliente2.setVisible(false);
        jLabel2.setVisible(false);
        txtCedulaPago.setVisible(false);
        jLabel3.setVisible(false);
        AutoCompleteDecorator.decorate(jComboBox1);
        AutoCompleteDecorator.decorate(jComboBox2);
        AutoCompleteDecorator.decorate(jComboBox3);
        AutoCompleteDecorator.decorate(jComboBox5);
        comboBuscarProducto.setVisible(false);
        btnEliminarPago.setVisible(true);
//        btnVerProductosFactura.setVisible(false);
        texTotalPagado.setVisible(false);
        lbTotalPagado.setVisible(false);
        jPanel3.setVisible(false);
        jButton8.setVisible(false);

        comboBuscarCliente2.setEnabled(true);
        comboFormaDePago.setEnabled(true);
        comboFormaDePago.setEditable(false);
        comboBuscarCliente.setVisible(false);

        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        jPanel2.setSize(pantalla.width / 2, pantalla.height / 2);
        textDocumento.setEditable(false);
        textNombres.setEditable(false);
        textApellidos.setEditable(false);
        textTelefono.setEditable(false);
        textDireccion.setEditable(false);

        btnAnadirCliente.setEnabled(false);
        btnEditarCliente.setEnabled(false);
        btnNuevoPago.setBackground(Color.white);
        comboFormaDePago.setVisible(true);
        comboFormaDePago.setEnabled(true);
        comboFormaDePago.setEditable(true);

        dateFechaEditada1.setVisible(false);
        btnAceptarFecha1.setVisible(false);
        //tabla registro/

        comboBuscarCliente1.setEnabled(true);
        tbVentas1.setEnabled(true);

        textNumeroFactura.setVisible(false);
        ckIVA1.setVisible(false);
        btnNuevoPago.setVisible(false);
        btnCobrar.setVisible(false);
        jButton5.setVisible(false);
        dtbmVentas2 = (DefaultTableModel) tbVentas1.getModel();
//        jButton7.setVisible(false);
      //  llenarTablaPagos();

//        jPanel1.setVisible(false);
        //this.btnNuevaFactura.setText("Nueva factura");
    }

    public void calcularSubTotal() {

        while (true) {
            int filas = tbVentas1.getRowCount();
            int c = 0;
            while (filas > 0) {
                System.out.println("entro");
                double total = 0;
                while (c < filas) {
                    Object seleccion = tbVentas1.getValueAt(c, 6);
                    boolean chulo = (boolean) seleccion;

                    if (chulo) {
                        Object valor = tbVentas1.getValueAt(c, 3);
                        String va = String.valueOf(valor);
                        va = va.replace(".", "");
                        va = va.replace(",", ".");
                        double v = Double.parseDouble(va);
                        total = total + v;
                        c++;
                    } else {
                        c++;
                    }

                }
                textSubtotal1.setText(total + "");
            }
        }
    }

    public void cambiarmensaje(ActionEvent evt) {
        String m = evt.getActionCommand();
        if (m.equalsIgnoreCase("Nueva factura")) {
            this.btnNuevaFactura.setText("Cancelar");
        } else {
            this.btnNuevaFactura.setText("Nueva factura");
        }

    }

//    public void cambiarmensaje2(ActionEvent evt) {
//        String m = evt.getActionCommand();
//        if (m.equalsIgnoreCase("Nuevo Registro")) {
//            this.btnNuevoRegistro.setText("Cancelar");
//        } else {
//            this.btnNuevoRegistro.setText("Nuevo Registro");
//        }
//
//    }
    private void buscarProductos(String texto) {
        // comboBuscarProducto.removeAllItems();
        servicio.buscar(texto).forEach((material) -> {
            dComboProductos.addElement(material);
        });
    }

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

                        agregarProductoAFactura(producto);

                        // calcularTotales();
                        //mostrarMensaje(producto.getPrecioUnitario()+"");
                        break;
                    case "CLIENTES":
                        codigo = 0;
                        ClienteDTO cliente = (ClienteDTO) seleccion;
                        textDocumento1.setText(cliente.getId() + "");
                        llenarTablaPagosFiltro(cliente.getId());
                        System.out.println(cliente.getId() + "codigo");
                        codigo = cliente.getId();

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

    private void crearComboEditable2(JComboBox<Object> combo, String tabla) {

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

                        agregarProductoAFactura(producto);

                        // calcularTotales();
                        //mostrarMensaje(producto.getPrecioUnitario()+"");
                        break;
                    case "CLIENTES":
                        codigo = 0;
                        ClienteDTO cliente = (ClienteDTO) seleccion;
                        textDocumento1.setText(cliente.getId() + "");
                        llenarTablaVentasRegistro(cliente.getId());

                        System.out.println(cliente.getId() + "codigo");
                        codigo = cliente.getId();

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

                        agregarProductoAFactura(producto);

                        calcularTotales();
                        //mostrarMensaje(producto.getPrecioUnitario()+"");
                        break;
                    case "CLIENTES":

                        ClienteDTO cliente = (ClienteDTO) seleccion;
                        textCodigoCliente.setText(cliente.getId() + "");
                        double debe = servicio.consultaTotalPagarPagado(cliente.getId());
                        ArrayList<RegistroDTO> r = servicio.listarVentasCliente(cliente.getId());
                        String palabra = "";
                        for (RegistroDTO re : r) {
                            if (re.getEstado().equalsIgnoreCase("DEPOSITO") || re.getEstado().equalsIgnoreCase("PROCESO")) {
                                palabra = palabra + "Numero Item:" + re.getId() + "Estado:" + re.getEstado() + " || ";
                            }
                        }
                        if (debe > 0) {
                            mostrarMensaje("El cliente debe" + "   " + "$" + formateador.format(debe));
                        }
                        String document = "";
                        String direcc = "";
                        if (cliente.getDocumento().equalsIgnoreCase("null")) {
                            document = "";
                        }
                        if (cliente.getDireccion().equalsIgnoreCase("null")) {
                            direcc = "";
                        }
                        if (!cliente.getDocumento().equalsIgnoreCase("null")) {
                            document = cliente.getDocumento();
                        }
                        if (!cliente.getDireccion().equalsIgnoreCase("null")) {
                            direcc = cliente.getDireccion();
                        }
                        textDocumento.setText(document);
                        textNombres.setText(cliente.getNombres());
                        textApellidos.setText(cliente.getApellidos());
                        textTelefono.setText(cliente.getTelefono() + "");
                        textDireccion.setText(direcc);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        tpPestania = new javax.swing.JTabbedPane();
        panelVentas = new javax.swing.JPanel();
        panelVentaProductos = new javax.swing.JPanel();
        spVentaProductos = new javax.swing.JScrollPane();
        tbVentas = new TablaPersonalizada();
        panelCliente = new javax.swing.JPanel();
        textDocumento = new javax.swing.JTextField();
        lbDocumento = new javax.swing.JLabel();
        lbNombres = new javax.swing.JLabel();
        textNombres = new javax.swing.JTextField();
        lbTelefono = new javax.swing.JLabel();
        textTelefono = new javax.swing.JTextField();
        textDireccion = new javax.swing.JTextField();
        lbDireccion = new javax.swing.JLabel();
        lbApellidos = new javax.swing.JLabel();
        textApellidos = new javax.swing.JTextField();
        lbDocumento1 = new javax.swing.JLabel();
        textCodigoCliente = new javax.swing.JTextField();
        comboBuscarCliente = new javax.swing.JComboBox<>();
        lbBuscarCliente = new javax.swing.JLabel();
        btnAnadirCliente = new javax.swing.JButton();
        btnEditarCliente = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblTotalPagar = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        panelFactura = new javax.swing.JPanel();
        textNumeroFactura = new javax.swing.JTextField();
        lbFormaDePago2 = new javax.swing.JLabel();
        dateFechaEntrega = new com.github.lgooddatepicker.components.DatePicker();
        lbEstadoFactura = new javax.swing.JLabel();
        comboEstadoFactura = new javax.swing.JComboBox<>();
        btnNuevaFactura = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lbBuscarCliente1 = new javax.swing.JLabel();
        comboBuscarProducto = new javax.swing.JComboBox<>();
        btnCobrar = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        ckIVA = new javax.swing.JCheckBox();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton8 = new javax.swing.JButton();
        lbBuscarCliente2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnQuitarProducto = new javax.swing.JButton();
        texTotalPagado = new javax.swing.JTextField();
        lbTotalPagado = new javax.swing.JLabel();
        textIVA = new javax.swing.JTextField();
        lbIVA = new javax.swing.JLabel();
        textSubtotal = new javax.swing.JTextField();
        lbSubtotal = new javax.swing.JLabel();
        panelPagos = new javax.swing.JPanel();
        spPagos = new javax.swing.JScrollPane();
        tbPagos = new TablaPersonalizada();
        btnEliminarPago = new javax.swing.JButton();
        btnNuevoPago = new javax.swing.JButton();
        comboBuscarCliente2 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCedulaPago = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jComboBox5 = new javax.swing.JComboBox<>();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        panelImpuestos = new javax.swing.JPanel();
        spImpuestos = new javax.swing.JScrollPane();
        tbImpuestos = new TablaPersonalizada();
        btnNuevoImpuesto = new javax.swing.JButton();
        btnEliminarImpuesto = new javax.swing.JButton();
        panelFacturas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbFacturas = new TablaPersonalizada();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnCartera = new javax.swing.JButton();
        cmbEstado = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        dpDesde = new com.github.lgooddatepicker.components.DatePicker();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        dpHasta = new com.github.lgooddatepicker.components.DatePicker();
        btnBuscarFecha = new javax.swing.JButton();
        txtTotalItems = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        panelVentas1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        panelVentaProductos1 = new javax.swing.JPanel();
        spVentaProductos1 = new javax.swing.JScrollPane();
        tbVentas1 = new TablaPersonalizada();
        jPanel12 = new javax.swing.JPanel();
        lblTotalPagar1 = new javax.swing.JLabel();
        btnCobrar1 = new javax.swing.JButton();
        texTotalPagado1 = new javax.swing.JTextField();
        textIVA1 = new javax.swing.JTextField();
        textSubtotal1 = new javax.swing.JTextField();
        lbIVA1 = new javax.swing.JLabel();
        lbSubtotal1 = new javax.swing.JLabel();
        lbTotalPagado1 = new javax.swing.JLabel();
        lbTotalPagado2 = new javax.swing.JLabel();
        panelFactura1 = new javax.swing.JPanel();
        lblNumeroFactura1 = new javax.swing.JLabel();
        textNumeroFactura1 = new javax.swing.JTextField();
        comboBuscarCliente1 = new javax.swing.JComboBox<>();
        lbBuscarCliente3 = new javax.swing.JLabel();
        lbDocumento2 = new javax.swing.JLabel();
        ckIVA1 = new javax.swing.JCheckBox();
        textDocumento1 = new javax.swing.JTextField();
        lbFormaDePago = new javax.swing.JLabel();
        comboFormaDePago = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        dateFechaEditada1 = new com.github.lgooddatepicker.components.DatePicker();
        btnAceptarFecha1 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        txtDebeRemision = new javax.swing.JTextField();
        lbDocumento3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("VENTAS");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/icon2.png"))); // NOI18N
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
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
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tpPestania.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tpPestaniaMouseClicked(evt);
            }
        });

        panelVentas.setBackground(new java.awt.Color(255, 255, 255));
        panelVentas.setMinimumSize(new java.awt.Dimension(300, 300));
        panelVentas.setPreferredSize(new java.awt.Dimension(1600, 400));
        panelVentas.setLayout(null);

        panelVentaProductos.setBackground(new java.awt.Color(255, 255, 255));
        panelVentaProductos.setBorder(javax.swing.BorderFactory.createTitledBorder("Materiales"));
        panelVentaProductos.setMaximumSize(new java.awt.Dimension(2000, 2000));
        panelVentaProductos.setMinimumSize(new java.awt.Dimension(100, 100));
        panelVentaProductos.setPreferredSize(new java.awt.Dimension(100, 100));
        panelVentaProductos.setLayout(new java.awt.GridLayout(1, 0));

        spVentaProductos.setPreferredSize(new java.awt.Dimension(100, 100));

        tbVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Producto", "Cantidad ", "Precio  Unitario", "Comentario", "Título 6"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbVentas.setEnabled(false);
        tbVentas.setMaximumSize(new java.awt.Dimension(2000, 2000));
        tbVentas.setOpaque(false);
        tbVentas.setSelectionBackground(new java.awt.Color(153, 153, 153));
        tbVentas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbVentasKeyPressed(evt);
            }
        });
        spVentaProductos.setViewportView(tbVentas);

        panelVentaProductos.add(spVentaProductos);

        panelVentas.add(panelVentaProductos);
        panelVentaProductos.setBounds(30, 190, 810, 326);

        panelCliente.setBackground(new java.awt.Color(255, 255, 255));
        panelCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        textDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textDocumentoActionPerformed(evt);
            }
        });

        lbDocumento.setText("Documento:");
        lbDocumento.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        lbNombres.setText("Nombres:");

        textNombres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textNombresActionPerformed(evt);
            }
        });

        lbTelefono.setText("Teléfono:");

        textTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textTelefonoActionPerformed(evt);
            }
        });

        textDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textDireccionActionPerformed(evt);
            }
        });

        lbDireccion.setText("Dirección:");

        lbApellidos.setText("Apellidos:");

        textApellidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textApellidosActionPerformed(evt);
            }
        });

        lbDocumento1.setText("Cod. cliente:");
        lbDocumento1.setOpaque(true);

        textCodigoCliente.setEditable(false);
        textCodigoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textCodigoClienteActionPerformed(evt);
            }
        });

        comboBuscarCliente.setEnabled(false);
        comboBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBuscarClienteActionPerformed(evt);
            }
        });

        lbBuscarCliente.setText("Buscar cliente:");
        lbBuscarCliente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        btnAnadirCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/agregarCli.png"))); // NOI18N
        btnAnadirCliente.setBackground(new java.awt.Color(51, 255, 0));
        btnAnadirCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnadirClienteMouseClicked(evt);
            }
        });
        btnAnadirCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirClienteActionPerformed(evt);
            }
        });

        btnEditarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/editarCli.png"))); // NOI18N
        btnEditarCliente.setBackground(new java.awt.Color(0, 0, 204));
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });

        jComboBox2.setEditable(true);
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MADERA", "ROBLE", "METAL" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jButton7.setText("OK");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton9.setText("ACT");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelClienteLayout = new javax.swing.GroupLayout(panelCliente);
        panelCliente.setLayout(panelClienteLayout);
        panelClienteLayout.setHorizontalGroup(
            panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClienteLayout.createSequentialGroup()
                .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelClienteLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbBuscarCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9))
                    .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelClienteLayout.createSequentialGroup()
                            .addGap(35, 35, 35)
                            .addComponent(comboBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelClienteLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panelClienteLayout.createSequentialGroup()
                                    .addComponent(lbDocumento1)
                                    .addGap(22, 22, 22)
                                    .addComponent(textCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelClienteLayout.createSequentialGroup()
                                    .addGap(15, 15, 15)
                                    .addComponent(lbTelefono)
                                    .addGap(22, 22, 22)
                                    .addComponent(textTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelClienteLayout.createSequentialGroup()
                                    .addGap(14, 14, 14)
                                    .addComponent(lbDireccion)
                                    .addGap(22, 22, 22)
                                    .addComponent(textDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelClienteLayout.createSequentialGroup()
                                    .addGap(3, 3, 3)
                                    .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbDocumento)
                                        .addGroup(panelClienteLayout.createSequentialGroup()
                                            .addGap(12, 12, 12)
                                            .addComponent(lbNombres))
                                        .addGroup(panelClienteLayout.createSequentialGroup()
                                            .addGap(11, 11, 11)
                                            .addComponent(lbApellidos)))
                                    .addGap(12, 12, 12)
                                    .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(textDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnAnadirCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnEditarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 26, Short.MAX_VALUE))
        );
        panelClienteLayout.setVerticalGroup(
            panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbBuscarCliente)
                    .addComponent(jButton7))
                .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelClienteLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(btnAnadirCliente)
                        .addGap(12, 12, 12)
                        .addComponent(btnEditarCliente))
                    .addGroup(panelClienteLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbDocumento1)
                            .addGroup(panelClienteLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(textCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12)
                        .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelClienteLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lbDocumento)
                                .addGap(9, 9, 9)
                                .addComponent(lbNombres)
                                .addGap(12, 12, 12)
                                .addComponent(lbApellidos))
                            .addGroup(panelClienteLayout.createSequentialGroup()
                                .addComponent(textDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(textNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(textApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(6, 6, 6)
                        .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelClienteLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(lbTelefono))
                            .addComponent(textTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelClienteLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(lbDireccion))
                            .addComponent(textDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(comboBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelVentas.add(panelCliente);
        panelCliente.setBounds(850, 200, 420, 310);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setMaximumSize(new java.awt.Dimension(472, 472));
        jPanel2.setPreferredSize(new java.awt.Dimension(1017, 154));

        lblTotalPagar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalPagar.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        lblTotalPagar.setForeground(new java.awt.Color(0, 153, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(lblTotalPagar, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelVentas.add(jPanel2);
        jPanel2.setBounds(700, 510, 250, 50);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(null);

        panelFactura.setBackground(new java.awt.Color(255, 255, 255));
        panelFactura.setBorder(javax.swing.BorderFactory.createTitledBorder("Fecha"));
        panelFactura.setPreferredSize(new java.awt.Dimension(898, 200));
        panelFactura.setLayout(null);

        textNumeroFactura.setEditable(false);
        textNumeroFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textNumeroFacturaActionPerformed(evt);
            }
        });
        panelFactura.add(textNumeroFactura);
        textNumeroFactura.setBounds(20, 30, 50, 28);

        lbFormaDePago2.setText("Fecha de entrega:");
        lbFormaDePago2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        panelFactura.add(lbFormaDePago2);
        lbFormaDePago2.setBounds(100, 30, 130, 28);

        dateFechaEntrega.setEnabled(false);
        panelFactura.add(dateFechaEntrega);
        dateFechaEntrega.setBounds(230, 30, 166, 28);

        lbEstadoFactura.setText("Estado:");
        lbEstadoFactura.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        panelFactura.add(lbEstadoFactura);
        lbEstadoFactura.setBounds(420, 30, 60, 28);

        comboEstadoFactura.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PROCESO", "DEPOSITO", "ENTREGADO", " " }));
        comboEstadoFactura.setEnabled(false);
        comboEstadoFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEstadoFacturaActionPerformed(evt);
            }
        });
        panelFactura.add(comboEstadoFactura);
        comboEstadoFactura.setBounds(480, 30, 153, 28);

        btnNuevaFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/agregarfac2.png"))); // NOI18N
        btnNuevaFactura.setText("Nueva factura");
        btnNuevaFactura.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNuevaFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaFacturaActionPerformed(evt);
            }
        });
        panelFactura.add(btnNuevaFactura);
        btnNuevaFactura.setBounds(750, 30, 220, 57);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/SWN.png"))); // NOI18N
        jLabel5.setText("jLabel5");
        panelFactura.add(jLabel5);
        jLabel5.setBounds(1060, 20, 110, 70);

        jPanel4.add(panelFactura);
        panelFactura.setBounds(0, 0, 1214, 110);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        lbBuscarCliente1.setText("Buscar y Agregar Material");
        lbBuscarCliente1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbBuscarCliente1.setForeground(new java.awt.Color(102, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 170;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 0, 0, 0);
        jPanel3.add(lbBuscarCliente1, gridBagConstraints);

        comboBuscarProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "MADERA", "METAL" }));
        comboBuscarProducto.setEnabled(false);
        comboBuscarProducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        comboBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBuscarProductoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 261;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 4, 0, 0);
        jPanel3.add(comboBuscarProducto, gridBagConstraints);

        jPanel4.add(jPanel3);
        jPanel3.setBounds(10, 110, 800, 20);

        btnCobrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/guardar.png"))); // NOI18N
        btnCobrar.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        btnCobrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCobrarActionPerformed(evt);
            }
        });
        jPanel4.add(btnCobrar);
        btnCobrar.setBounds(1081, 110, 80, 70);

        jButton5.setText("Finalizar Compra");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton5);
        jButton5.setBounds(930, 130, 110, 30);

        ckIVA.setText("IVA");
        ckIVA.setBackground(new java.awt.Color(255, 255, 255));
        ckIVA.setMargin(new java.awt.Insets(2, 4, 2, 2));
        ckIVA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ckIVAMouseClicked(evt);
            }
        });
        ckIVA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckIVAActionPerformed(evt);
            }
        });
        jPanel4.add(ckIVA);
        ckIVA.setBounds(860, 130, 65, 36);

        jComboBox1.setEditable(true);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MADERA", "ROBLE", "METAL" }));
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        jPanel4.add(jComboBox1);
        jComboBox1.setBounds(280, 140, 320, 20);

        jButton8.setText("Añadir");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton8);
        jButton8.setBounds(630, 140, 73, 23);

        lbBuscarCliente2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbBuscarCliente2.setForeground(new java.awt.Color(102, 0, 0));
        lbBuscarCliente2.setText("Buscar y Agregar Material");
        jPanel4.add(lbBuscarCliente2);
        lbBuscarCliente2.setBounds(0, 0, 177, 17);

        jLabel4.setText("Buscar y Agregar Producto");
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 0, 0));
        jPanel4.add(jLabel4);
        jLabel4.setBounds(20, 140, 220, 17);

        btnQuitarProducto.setText("Quitar");
        btnQuitarProducto.setEnabled(false);
        btnQuitarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarProductoActionPerformed(evt);
            }
        });
        jPanel4.add(btnQuitarProducto);
        btnQuitarProducto.setBounds(710, 140, 100, 23);

        panelVentas.add(jPanel4);
        jPanel4.setBounds(26, 5, 1214, 180);

        texTotalPagado.setText("0");
        texTotalPagado.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        panelVentas.add(texTotalPagado);
        texTotalPagado.setBounds(530, 530, 170, 30);

        lbTotalPagado.setText("Total pagado:");
        panelVentas.add(lbTotalPagado);
        lbTotalPagado.setBounds(460, 540, 67, 14);

        textIVA.setEditable(false);
        textIVA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textIVAActionPerformed(evt);
            }
        });
        panelVentas.add(textIVA);
        textIVA.setBounds(310, 540, 126, 20);

        lbIVA.setText("Iva al 19%:");
        panelVentas.add(lbIVA);
        lbIVA.setBounds(250, 540, 57, 14);

        textSubtotal.setEditable(false);
        panelVentas.add(textSubtotal);
        textSubtotal.setBounds(90, 540, 125, 20);

        lbSubtotal.setText("Subtotal:");
        panelVentas.add(lbSubtotal);
        lbSubtotal.setBounds(40, 540, 44, 14);

        if (SESION_USUARIO.isVistaPermitida(VENTAS)) {

            tpPestania.addTab("Ventas", panelVentas);
        }

        panelPagos.setBackground(new java.awt.Color(255, 255, 255));

        tbPagos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spPagos.setViewportView(tbPagos);

        btnEliminarPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Eliminar.png"))); // NOI18N
        btnEliminarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPagoActionPerformed(evt);
            }
        });

        btnNuevoPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/agregar.png"))); // NOI18N
        btnNuevoPago.setText("Pago");
        btnNuevoPago.setBackground(new java.awt.Color(255, 255, 255));
        btnNuevoPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoPagoActionPerformed(evt);
            }
        });

        comboBuscarCliente2.setEditable(true);
        comboBuscarCliente2.setEnabled(false);
        comboBuscarCliente2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBuscarCliente2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Nombre");

        jLabel3.setText("Cedula");

        txtCedulaPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedulaPagoActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Actualizar_opt.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jComboBox5.setEditable(true);
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MADERA", "ROBLE", "METAL" }));
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        jButton14.setText("OK");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setText("ACT");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPagosLayout = new javax.swing.GroupLayout(panelPagos);
        panelPagos.setLayout(panelPagosLayout);
        panelPagosLayout.setHorizontalGroup(
            panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagosLayout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton15)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(txtCedulaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(640, Short.MAX_VALUE))
            .addGroup(panelPagosLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPagosLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(30, 30, 30)
                        .addComponent(comboBuscarCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelPagosLayout.createSequentialGroup()
                        .addComponent(spPagos, javax.swing.GroupLayout.DEFAULT_SIZE, 1139, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEliminarPago, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNuevoPago, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10))))
        );
        panelPagosLayout.setVerticalGroup(
            panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagosLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPagosLayout.createSequentialGroup()
                        .addComponent(btnNuevoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarPago)
                        .addGap(29, 29, 29)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spPagos, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBuscarCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(7, 7, 7)
                .addGroup(panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCedulaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton15)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14))
                .addContainerGap(116, Short.MAX_VALUE))
        );

        if (SESION_USUARIO.isVistaPermitida(PAGOS)) {

            tpPestania.addTab("Abono Credito", panelPagos);
        }

        panelImpuestos.setBackground(new java.awt.Color(255, 255, 255));
        panelImpuestos.setMinimumSize(new java.awt.Dimension(1, 1));
        panelImpuestos.setPreferredSize(new java.awt.Dimension(400, 400));

        tbImpuestos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spImpuestos.setViewportView(tbImpuestos);

        btnNuevoImpuesto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/agregar.png"))); // NOI18N
        btnNuevoImpuesto.setText("Impuesto");
        btnNuevoImpuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoImpuestoActionPerformed(evt);
            }
        });

        btnEliminarImpuesto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Eliminar.png"))); // NOI18N
        btnEliminarImpuesto.setText("Impuesto");
        btnEliminarImpuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarImpuestoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelImpuestosLayout = new javax.swing.GroupLayout(panelImpuestos);
        panelImpuestos.setLayout(panelImpuestosLayout);
        panelImpuestosLayout.setHorizontalGroup(
            panelImpuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImpuestosLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(spImpuestos, javax.swing.GroupLayout.DEFAULT_SIZE, 1108, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addGroup(panelImpuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEliminarImpuesto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevoImpuesto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelImpuestosLayout.setVerticalGroup(
            panelImpuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImpuestosLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelImpuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImpuestosLayout.createSequentialGroup()
                        .addComponent(btnNuevoImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(spImpuestos, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        if (SESION_USUARIO.isVistaPermitida(IMPUESTOS)) {

            tpPestania.addTab("Impuestos", panelImpuestos);
        }

        panelFacturas.setBackground(new java.awt.Color(255, 255, 255));

        tbFacturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Forma de pago", "Fecha", "IVA", "Total a pagar", "Total pagado", "Fecha entrega", "Cliente", "Usuario"
            }
        ));
        jScrollPane1.setViewportView(tbFacturas);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Actualizar_opt.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnCartera.setText("Generar Cartera");
        btnCartera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarteraActionPerformed(evt);
            }
        });

        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "DEPOSITO", "ENTREGADO", "PROCESO" }));

        jLabel6.setText("Estado:");

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel7.setText("Desde");

        jLabel8.setText("Hasta");

        btnBuscarFecha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA2.png"))); // NOI18N
        btnBuscarFecha.setText("Buscar");
        btnBuscarFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFechaActionPerformed(evt);
            }
        });

        jLabel9.setText("Total");

        javax.swing.GroupLayout panelFacturasLayout = new javax.swing.GroupLayout(panelFacturas);
        panelFacturas.setLayout(panelFacturasLayout);
        panelFacturasLayout.setHorizontalGroup(
            panelFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFacturasLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addContainerGap())
            .addGroup(panelFacturasLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCartera)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(dpDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(dpHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscarFecha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTotalItems, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        panelFacturasLayout.setVerticalGroup(
            panelFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFacturasLayout.createSequentialGroup()
                .addGroup(panelFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addGroup(panelFacturasLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnCartera, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7)
                                .addComponent(dpDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(dpHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8)
                                .addComponent(btnBuscarFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTotalItems, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9))
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        if (SESION_USUARIO.isVistaPermitida(FACTURAS)) {

            tpPestania.addTab("Items", panelFacturas);
        }

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        panelVentas1.setBackground(new java.awt.Color(255, 255, 255));
        panelVentas1.setMinimumSize(new java.awt.Dimension(300, 300));
        panelVentas1.setPreferredSize(new java.awt.Dimension(1600, 400));
        panelVentas1.setLayout(null);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel7.setMaximumSize(new java.awt.Dimension(472, 472));
        jPanel7.setPreferredSize(new java.awt.Dimension(1017, 154));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        panelVentas1.add(jPanel7);
        jPanel7.setBounds(0, 0, 0, 0);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.GridBagLayout());

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = -217;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 10, 35, 0);
        jPanel8.add(jPanel9, gridBagConstraints);

        panelVentas1.add(jPanel8);
        jPanel8.setBounds(0, 0, 0, 0);

        panelVentaProductos1.setBackground(new java.awt.Color(255, 255, 255));
        panelVentaProductos1.setBorder(javax.swing.BorderFactory.createTitledBorder("Materiales"));
        panelVentaProductos1.setMaximumSize(new java.awt.Dimension(2000, 2000));
        panelVentaProductos1.setMinimumSize(new java.awt.Dimension(100, 100));
        panelVentaProductos1.setPreferredSize(new java.awt.Dimension(100, 100));
        panelVentaProductos1.setLayout(new java.awt.GridLayout(1, 0));

        spVentaProductos1.setPreferredSize(new java.awt.Dimension(100, 100));

        tbVentas1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Producto", "Cantidad ", "Precio  Unitario", "Comentario", "Estado Pago", "Forma de pagor", "Seleccionar", "Título 9", "null", "Título 11"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbVentas1.setEnabled(false);
        tbVentas1.setMaximumSize(new java.awt.Dimension(2000, 2000));
        tbVentas1.setOpaque(false);
        tbVentas1.setSelectionBackground(new java.awt.Color(153, 153, 153));
        spVentaProductos1.setViewportView(tbVentas1);

        panelVentaProductos1.add(spVentaProductos1);

        panelVentas1.add(panelVentaProductos1);
        panelVentaProductos1.setBounds(0, 80, 1190, 350);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel12.setMaximumSize(new java.awt.Dimension(472, 472));
        jPanel12.setPreferredSize(new java.awt.Dimension(1017, 154));

        lblTotalPagar1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalPagar1.setText("0");
        lblTotalPagar1.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        lblTotalPagar1.setForeground(new java.awt.Color(0, 153, 0));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotalPagar1, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(lblTotalPagar1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        panelVentas1.add(jPanel12);
        jPanel12.setBounds(520, 470, 270, 50);

        btnCobrar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        btnCobrar1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        btnCobrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCobrar1ActionPerformed(evt);
            }
        });
        panelVentas1.add(btnCobrar1);
        btnCobrar1.setBounds(1540, 50, 73, 49);

        texTotalPagado1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        texTotalPagado1.setText("0");
        texTotalPagado1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        texTotalPagado1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                texTotalPagado1ActionPerformed(evt);
            }
        });
        panelVentas1.add(texTotalPagado1);
        texTotalPagado1.setBounds(910, 470, 270, 50);

        textIVA1.setEditable(false);
        textIVA1.setText("0");
        panelVentas1.add(textIVA1);
        textIVA1.setBounds(290, 490, 126, 20);

        textSubtotal1.setEditable(false);
        textSubtotal1.setText("0");
        textSubtotal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSubtotal1ActionPerformed(evt);
            }
        });
        panelVentas1.add(textSubtotal1);
        textSubtotal1.setBounds(70, 490, 125, 20);

        lbIVA1.setText("Iva al 19%:");
        panelVentas1.add(lbIVA1);
        lbIVA1.setBounds(220, 490, 57, 14);

        lbSubtotal1.setText("Subtotal:");
        panelVentas1.add(lbSubtotal1);
        lbSubtotal1.setBounds(20, 490, 44, 14);

        lbTotalPagado1.setText("Total a pagar");
        panelVentas1.add(lbTotalPagado1);
        lbTotalPagado1.setBounds(450, 500, 64, 14);

        lbTotalPagado2.setText("Total pagado:");
        panelVentas1.add(lbTotalPagado2);
        lbTotalPagado2.setBounds(840, 500, 67, 14);

        panelFactura1.setBackground(new java.awt.Color(255, 255, 255));
        panelFactura1.setBorder(javax.swing.BorderFactory.createTitledBorder("Factura"));
        panelFactura1.setMinimumSize(new java.awt.Dimension(1150, 112));
        panelFactura1.setPreferredSize(new java.awt.Dimension(898, 200));
        panelFactura1.setLayout(null);

        lblNumeroFactura1.setText("N° Remision");
        lblNumeroFactura1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        panelFactura1.add(lblNumeroFactura1);
        lblNumeroFactura1.setBounds(10, 30, 90, 17);
        panelFactura1.add(textNumeroFactura1);
        textNumeroFactura1.setBounds(110, 30, 30, 28);

        comboBuscarCliente1.setEditable(true);
        comboBuscarCliente1.setEnabled(false);
        comboBuscarCliente1.setMinimumSize(new java.awt.Dimension(200, 20));
        comboBuscarCliente1.setPreferredSize(new java.awt.Dimension(100, 100));
        comboBuscarCliente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBuscarCliente1ActionPerformed(evt);
            }
        });
        panelFactura1.add(comboBuscarCliente1);
        comboBuscarCliente1.setBounds(10, 60, 20, 20);

        lbBuscarCliente3.setText("Buscar cliente:");
        lbBuscarCliente3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        panelFactura1.add(lbBuscarCliente3);
        lbBuscarCliente3.setBounds(150, 30, 82, 20);

        lbDocumento2.setText("DEBE");
        lbDocumento2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        panelFactura1.add(lbDocumento2);
        lbDocumento2.setBounds(590, 70, 27, 14);

        ckIVA1.setText("IVA");
        ckIVA1.setBackground(new java.awt.Color(255, 255, 255));
        ckIVA1.setMargin(new java.awt.Insets(2, 4, 2, 2));
        ckIVA1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ckIVA1MouseClicked(evt);
            }
        });
        ckIVA1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckIVA1ActionPerformed(evt);
            }
        });
        panelFactura1.add(ckIVA1);
        ckIVA1.setBounds(1130, 70, 20, 10);

        textDocumento1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textDocumento1ActionPerformed(evt);
            }
        });
        panelFactura1.add(textDocumento1);
        textDocumento1.setBounds(710, 30, 30, 20);

        lbFormaDePago.setText("Forma de pago:");
        lbFormaDePago.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbFormaDePago.setMaximumSize(new java.awt.Dimension(74, 17));
        lbFormaDePago.setMinimumSize(new java.awt.Dimension(74, 17));
        panelFactura1.add(lbFormaDePago);
        lbFormaDePago.setBounds(760, 30, 120, 20);

        comboFormaDePago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CREDITO", "CONTADO", " " }));
        comboFormaDePago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFormaDePagoActionPerformed(evt);
            }
        });
        panelFactura1.add(comboFormaDePago);
        comboFormaDePago.setBounds(880, 30, 100, 20);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Imrpimir_opt.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        panelFactura1.add(jButton3);
        jButton3.setBounds(1053, 20, 60, 40);

        jButton6.setText("Quitar iva");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        panelFactura1.add(jButton6);
        jButton6.setBounds(760, 70, 100, 23);

        jComboBox3.setEditable(true);
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MADERA", "ROBLE", "METAL" }));
        panelFactura1.add(jComboBox3);
        jComboBox3.setBounds(240, 30, 220, 20);

        jButton10.setText("OK");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        panelFactura1.add(jButton10);
        jButton10.setBounds(470, 30, 47, 30);

        jButton11.setText("ACT");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        panelFactura1.add(jButton11);
        jButton11.setBounds(520, 30, 53, 30);

        dateFechaEditada1.setEnabled(false);
        panelFactura1.add(dateFechaEditada1);
        dateFechaEditada1.setBounds(250, 70, 166, 20);

        btnAceptarFecha1.setText("Aceptar");
        btnAceptarFecha1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarFecha1ActionPerformed(evt);
            }
        });
        panelFactura1.add(btnAceptarFecha1);
        btnAceptarFecha1.setBounds(440, 70, 71, 23);

        jButton12.setText("Cambiar Fecha De Entrega");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        panelFactura1.add(jButton12);
        jButton12.setBounds(70, 70, 161, 23);

        jButton13.setText("Eliminar Item");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        panelFactura1.add(jButton13);
        jButton13.setBounds(1000, 70, 100, 23);
        panelFactura1.add(txtDebeRemision);
        txtDebeRemision.setBounds(630, 70, 90, 20);

        lbDocumento3.setText("Id Cliente");
        lbDocumento3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        panelFactura1.add(lbDocumento3);
        lbDocumento3.setBounds(640, 30, 54, 14);

        jButton2.setText("Añadir Iva");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        panelFactura1.add(jButton2);
        jButton2.setBounds(890, 70, 90, 23);

        panelVentas1.add(panelFactura1);
        panelFactura1.setBounds(0, -20, 1170, 100);

        if (SESION_USUARIO.isVistaPermitida(VENTAS)) {

            javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
            jPanel5.setLayout(jPanel5Layout);
            jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(0, 36, Short.MAX_VALUE)
                    .addComponent(panelVentas1, javax.swing.GroupLayout.PREFERRED_SIZE, 1193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 36, Short.MAX_VALUE))
            );
            jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panelVentas1, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE))
            );

        }

        tpPestania.addTab("Remision", jPanel5);

        getContentPane().add(tpPestania, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1270, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents
 private void llenarTablaVentas2(long codigo) {

        final String[] columnas = {"Codigo", "Producto", "Cantidad", "Precio unitario", "Comentario", "Iva", "Total"};

        // Configurando los eventos de edicion de la tabla
        dtbmVentas = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 3 || column == 4;
            }
        };
        // Llenando la tabla
        //        servicio.listarVentas().forEach((VentaDTO venta) -> {
        //            dtbmVentas.addRow(venta.toArray());
        //        });
        ;

        //Agregando el modelo de la tabla
        tbVentas.setModel(dtbmVentas);

        // cambia el tipo de letra del encabezado de la tabla
        tbVentas.getTableHeader().setFont(new Font("Tahoma", 1, 14));

        //le da formato a toda la tabla
        tbVentas.setFont(new Font("Tahoma", 1, 14));
//        // cambia el fondo del encabezado de la tabla
//        tbVentas.getTableHeader().setBackground(Color.cyan);

        // cambia el color de la letra del encabezado de la tabla
        tbVentas.getTableHeader().setForeground(Color.blue);

        //da formato a la fila de la celda
        tbVentas.setSelectionBackground(Color.GRAY);
        tbVentas.setSelectionForeground(Color.cyan);
        tbVentas.setIntercellSpacing(new Dimension(4, 4));
        tbVentas.setRowMargin(2);
        tbVentas.setOpaque(false);

        //Definiendo el ancho de la columna ed la tabla
        tbVentas.getColumnModel().getColumn(0).setMaxWidth(10);
        tbVentas.getColumnModel().getColumn(1).setMaxWidth(10);
        tbVentas.getColumnModel().getColumn(2).setMaxWidth(10);
        tbVentas.getColumnModel().getColumn(3).setMaxWidth(60);
        tbVentas.getColumnModel().getColumn(4).setMaxWidth(100);
        tbVentas.getColumnModel().getColumn(5).setMaxWidth(10);
        tbVentas.getColumnModel().getColumn(6).setMaxWidth(10);
        //     tbVentas.getColumnModel().getColumn(4).setMaxWidth(80);

        //centrando las columnas de la tabla
        DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
        modelocentrar.setHorizontalAlignment(SwingConstants.CENTER);
        tbVentas.getColumnModel().getColumn(0).setCellRenderer(modelocentrar);
        tbVentas.getColumnModel().getColumn(1).setCellRenderer(modelocentrar);
        tbVentas.getColumnModel().getColumn(2).setCellRenderer(modelocentrar);
        tbVentas.getColumnModel().getColumn(3).setCellRenderer(modelocentrar);

        dtbmVentas.addTableModelListener((TableModelEvent e) -> {

            long id;
            long cantidad;

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tbVentas.getValueAt(e.getFirstRow(), 0);
                Object objCantidad = tbVentas.getValueAt(e.getFirstRow(), 2);

                if (objCantidad == null) {
                    return;
                }

                cantidad = Long.parseLong(String.valueOf(objCantidad));

                if (objId != null) {
                    id = Long.parseLong(String.valueOf(objId));
                    boolean c = ckIVA.isSelected();

//                    if (servicio.editarVenta(id, cantidad)) {
//                        actualizarTablaProductos();
                    if (c) {
                        calcularTotales2();
                    } else {
                        calcularTotales();
                    }

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

//        aplicarEventosPersonalizados(tbVentas);
    }

// Llenando las tablas del módulo de ventas.
    public void cambiarColores(long i) {
        ArrayList<RegistroDTO> ventas = servicio.listarVentasCliente(i);
        System.out.println(ventas);
//        tbVentas1.rowAtPoint(point)

    }

    //llenar tabla de registrar factura
    private void llenarTablaVentas() {

        final String[] columnas = {"Codigo", "Producto", "Cantidad", "Precio unitario", "Comentario", "Iva", "Total"};

        // Configurando los eventos de edicion de la tabla
        dtbmVentas = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 3 || column == 4;
            }
        };
        // Llenando la tabla
        //        servicio.listarVentas().forEach((VentaDTO venta) -> {
        //            dtbmVentas.addRow(venta.toArray());
        //        });

        //Agregando el modelo de la tabla
        tbVentas.setModel(dtbmVentas);

        // cambia el tipo de letra del encabezado de la tabla
        tbVentas.getTableHeader().setFont(new Font("Tahoma", 1, 14));

        //le da formato a toda la tabla
        tbVentas.setFont(new Font("Tahoma", 1, 14));
//        // cambia el fondo del encabezado de la tabla
//        tbVentas.getTableHeader().setBackground(Color.cyan);

        // cambia el color de la letra del encabezado de la tabla
        tbVentas.getTableHeader().setForeground(Color.blue);

        //da formato a la fila de la celda
        tbVentas.setSelectionBackground(Color.GRAY);
        tbVentas.setSelectionForeground(Color.cyan);
        tbVentas.setIntercellSpacing(new Dimension(4, 4));
        tbVentas.setRowMargin(2);
        tbVentas.setOpaque(false);

        //Definiendo el ancho de la columna ed la tabla
        tbVentas.getColumnModel().getColumn(0).setMaxWidth(60);
        tbVentas.getColumnModel().getColumn(1).setMaxWidth(90);
        tbVentas.getColumnModel().getColumn(2).setMaxWidth(90);
        tbVentas.getColumnModel().getColumn(3).setMaxWidth(130);
        tbVentas.getColumnModel().getColumn(4).setMaxWidth(420);
        tbVentas.getColumnModel().getColumn(5).setMaxWidth(50);
        tbVentas.getColumnModel().getColumn(6).setMaxWidth(50);
        //     tbVentas.getColumnModel().getColumn(4).setMaxWidth(80);

        //centrando las columnas de la tabla
        DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
        modelocentrar.setHorizontalAlignment(SwingConstants.CENTER);
        tbVentas.getColumnModel().getColumn(0).setCellRenderer(modelocentrar);
        tbVentas.getColumnModel().getColumn(1).setCellRenderer(modelocentrar);
        tbVentas.getColumnModel().getColumn(2).setCellRenderer(modelocentrar);
        tbVentas.getColumnModel().getColumn(3).setCellRenderer(modelocentrar);

        dtbmVentas.addTableModelListener((TableModelEvent e) -> {

            long id;
            long cantidad;

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tbVentas.getValueAt(e.getFirstRow(), 0);
                Object objCantidad = tbVentas.getValueAt(e.getFirstRow(), 2);
                Object objPrecio = tbVentas.getValueAt(e.getFirstRow(), 3);

                if (objCantidad == null) {
                    return;
                }

                cantidad = Long.parseLong(String.valueOf(objCantidad));

                if (objId != null) {
//                     totalUno();

                    id = Long.parseLong(String.valueOf(objId));
                    boolean c = ckIVA.isSelected();

//                    if (servicio.editarVenta(id, cantidad)) {
//                        actualizarTablaProductos();
                    if (c) {
                        calcularTotales2();

                    } else {
                        calcularTotales();

                    }

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

//            
        });

//        aplicarEventosPersonalizados(tbVentas);
    }
    DecimalFormat formateador = new DecimalFormat("###,###.###");

    private void llenarTablaVentasRegistro(long i) {
        String est = "";
        final String[] columnas = {"Codigo", "Fecha ", "Fecha E", "Producto", "Cant", "Precio unitario", "Comentario", "Estado Item", "Total a pagar", "Iva", "Numero Remision", "Seleccionar", "Usado"};

        // Configurando los eventos de edicion de la tabla
        dtbmVentas2 = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7 || column == 5 || column == 3 || column == 4 || column == 6 || column == 9 || column == 10 || column == 11;
            }

            @Override
            public Class getColumnClass(int columna) {
                if (columna == 11) {
                    return Boolean.class;
                }

                return Object.class;
            }
        };
        // Llenando la tabla
        //        servicio.listarVentas().forEach((VentaDTO venta) -> {
        //            dtbmVentas.addRow(venta.toArray());
        //        });
        ;
        ArrayList<RegistroDTO> ventas = servicio.listarVentasCliente(i);

        System.out.println(ventas + "lista");
        for (RegistroDTO venta : ventas) {
            //MaterialDTO m = servicio.consultarMaterial(venta.getId_material());
            String estado = "";

            if (venta.getImpreso() == 1) {
                estado = "OK";
            }
            Timestamp stamp = venta.getFecha();
            Date date = new Date(stamp.getTime());
            String S = new SimpleDateFormat("dd/MM/yyyy").format(date);

            Date date2 = new Date(venta.getFechaEntrega().getTime());
            String S2 = new SimpleDateFormat("dd/MM/yyyy").format(date2);
            dtbmVentas2.addRow(new Object[]{venta.getId(), S, S2,venta.getMaterial().getNombre(), venta.getCantidad(), formateador.format(venta.getPrecio()), venta.getComentario(), venta.getEstado(), formateador.format(venta.getTotal_pagar()), formateador.format(venta.getIva()), venta.getId_factura(), false, estado});
//            tbVentas1.setDefaultRenderer(Object.class, f);
        }

        //Agregando el modelo de la tabla
        tbVentas1.setModel(dtbmVentas2);

        // cambia el tipo de letra del encabezado de la tabla
        tbVentas1.getTableHeader().setFont(new Font("Tahoma", 1, 11));

        //le da formato a toda la tabla
        tbVentas1.setFont(new Font("Tahoma", 1, 11));
//        // cambia el fondo del encabezado de la tabla
//        tbVentas.getTableHeader().setBackground(Color.cyan);

        // cambia el color de la letra del encabezado de la tabla
        tbVentas1.getTableHeader().setForeground(Color.blue);

        //da formato a la fila de la celda
        tbVentas1.setSelectionBackground(Color.GRAY);
//        tbVentas1.setSelectionForeground(Color.cyan);
        tbVentas1.setIntercellSpacing(new Dimension(4, 4));
        tbVentas1.setRowMargin(2);
        tbVentas1.setOpaque(false);

        //Definiendo el ancho de la columna ed la tabla
        tbVentas1.getColumnModel().getColumn(0).setMaxWidth(70);
        tbVentas1.getColumnModel().getColumn(1).setMaxWidth(90);
        tbVentas1.getColumnModel().getColumn(2).setMaxWidth(90);
        tbVentas1.getColumnModel().getColumn(3).setMaxWidth(90);
        tbVentas1.getColumnModel().getColumn(4).setMaxWidth(70);
        tbVentas1.getColumnModel().getColumn(5).setMaxWidth(100);
        tbVentas1.getColumnModel().getColumn(6).setMaxWidth(350);
        tbVentas1.getColumnModel().getColumn(7).setMaxWidth(90);
        tbVentas1.getColumnModel().getColumn(8).setMaxWidth(60);
        tbVentas1.getColumnModel().getColumn(9).setMaxWidth(110);
        tbVentas1.getColumnModel().getColumn(10).setMaxWidth(90);
        tbVentas1.getColumnModel().getColumn(11).setMaxWidth(60);
        tbVentas1.getColumnModel().getColumn(12).setMaxWidth(60);
        //     tbVentas.getColumnModel().getColumn(4).setMaxWidth(80);

        //centrando las columnas de la tabla
//        DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
//        modelocentrar.setHorizontalAlignment(SwingConstants.CENTER);
//        tbVentas1.getColumnModel().getColumn(0).setCellRenderer(modelocentrar);
//        tbVentas1.getColumnModel().getColumn(1).setCellRenderer(modelocentrar);
//        tbVentas1.getColumnModel().getColumn(2).setCellRenderer(modelocentrar);
//        tbVentas1.getColumnModel().getColumn(3).setCellRenderer(modelocentrar);
        dtbmVentas2.addTableModelListener((TableModelEvent e) -> {

            long id;
            long cantidad;

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tbVentas1.getValueAt(e.getFirstRow(), 0);

                Object objCantidad = tbVentas1.getValueAt(e.getFirstRow(), 4);
                Object objmaterial = tbVentas1.getValueAt(e.getFirstRow(), 3);
                String material = String.valueOf(objmaterial);
//                int cantidad=Integer.parseInt(String.valueOf(objCantidad));
                Object objPrecio = tbVentas1.getValueAt(e.getFirstRow(), 5);
                Object objIva = tbVentas1.getValueAt(e.getFirstRow(), 9);
                Object objComentario = tbVentas1.getValueAt(e.getFirstRow(), 6);
                String precio = String.valueOf(objPrecio);
                String iva = String.valueOf(objIva);
                precio = precio.replace(",", ".");
                precio = precio.replace(".", "");

                iva = iva.replace(",", ".");
                iva = iva.replace(".", "");

                String comen = String.valueOf(objComentario);
                double pre = Double.parseDouble(precio);
                double iv = Double.parseDouble(iva);
                double ivaa = 0;
                double ivaSistema = servicio.consultarImpuesto(1).getValor();

                cantidad = Long.parseLong(String.valueOf(objCantidad));
                if (iv > 0) {
                    ivaa = (pre * cantidad) * (ivaSistema / 100);
                }

                if (objCantidad == null) {
                    return;
                }

                double totalPagarV = 0;
               // double ivaV = 0;
                int x = tbVentas1.getSelectedRow();
                Object objIds = tbVentas1.getValueAt(x, 0);
                long ids = Long.parseLong(String.valueOf(objIds));
               // ArrayList<RegistroDTO> r = servicio.listarRegistro(ids);
                //for (RegistroDTO re : r) {
                  //  totalPagarV = re.getTotal_pagar();
                   // ivaV = re.getIva();
               // }

                int s = tbVentas1.getSelectedRow();
                if (objId != null) {

                    String esta = "";
                    long p = Long.parseLong(String.valueOf(tbVentas1.getValueAt(s, 0)));
                    esta = String.valueOf(tbVentas1.getValueAt(s, 7));
                    
                    servicio.cambiarEstado(p, esta);
                    MaterialDTO mate = servicio.consultarMaterialNombre(material);
                    servicio.Material(p, mate.getId());
                    servicio.editarPrecio(p, pre, (pre * cantidad) + ivaa, ivaa, cantidad);
                    servicio.cambiarComentario(p, comen);
                    id = Long.parseLong(String.valueOf(objId));
                    boolean c = ckIVA1.isSelected();
                    Object pagar = tbVentas1.getValueAt(e.getFirstRow(), 8);

                    String pa = String.valueOf(pagar);
                    pa = pa.replace(",", ".");
                    pa = pa.replace(".", "");

                    String ivaaa = String.valueOf(objIva);
                    ivaaa = ivaaa.replace(",", ".");
                    ivaaa = ivaaa.replace(".", "");
//                    long id = Long.parseLong(String.valueOf(objId));
                    Object NUmeroRemision = tbVentas1.getValueAt(e.getFirstRow(), 10);
                    long idRemision = Long.parseLong(String.valueOf(NUmeroRemision));

                    ArrayList<FacturaDTO> f = servicio.buscarFactura(idRemision);
                    double ivas = Double.parseDouble(ivaaa);
                    double totalPagar = Double.parseDouble(pa);
                    double total = 0;
                    double is = 0;
                    
                    double totalItemRemision=0;
                    double ivaRemision=0;
                    
                    totalItemRemision=servicio.itemTotalFacturas(idRemision);
                    ivaRemision=servicio.IvaTotalFacturas(idRemision);
                    
//                    ArrayList<RegistroDTO> regi = servicio.listar(idRemision);
//                    for (RegistroDTO re : regi) {
//                        total = total + re.getTotal_pagar();
//                        is = is + re.getIva();
//                    }
                      

                    boolean xa = servicio.editarRemision(idRemision, totalItemRemision,ivaRemision);
                    boolean a = false;

//                    if (servicio.editarVenta(id, cantidad)) {
//                        actualizarTablaProductos();
                    if (c) {
                        calcularTotales2();
                    } else {
                        calcularTotales4();
                    }

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
        ArrayList<MaterialDTO> m = servicio.listarMateriales();
        agregarJComboBoxAColumna(tbVentas1, 7, a.toArray());

        agregarJComboBoxAColumna(tbVentas1, 3, m.toArray());
//        aplicarEventosPersonalizados(tbVentas1);

//        aplicarEventosPersonalizados(tbVentas);
    }

    private void llenarTablaFacturas2(String esta) {

        final String[] columnas = {
            "N°Item",
            "Cliente",
            "Fecha Item",
            "Total a pagar",
            "Fecha entrega",
            "Material",
            "Estado",
            "Numero Remision",
            "Comentario"
        };

        // Configurando los eventos de edicion de la tabla
        dtbmFacturas = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 6;
            }
        };
        //  Llenando la tabla
        Object[] object = new Object[14];
        //servicio.listarUsuariosNomina()
        //      .forEach((UsuarioDTO usuario) -> {
        //        dtblNomina.addRow(usuario.toArray());
        //      
        double totalPagar = 0;
        for (RegistroDTO u : servicio.listarRegistrosEstado(esta)) {

            Timestamp stamp = u.getFecha();
            Date date = new Date(stamp.getTime());
            String S = new SimpleDateFormat("dd/MM/yyyy").format(date);
//            mostrarMensaje();

            object[0] = u.getId();
            object[1] = u.getCliente().getNombres() + " " + u.getCliente().getApellidos();
            object[2] = S;
            object[3] = formateador.format(u.getTotal_pagar());
            object[4] = u.getFechaEntrega();
            object[5] = u.getMaterial().getNombre();
            object[6] = u.getEstado();
            object[7] = u.getId_factura();
            object[8] = u.getComentario();

            totalPagar = totalPagar + u.getTotal_pagar();
            dtbmFacturas.addRow(object);

        }

//        ArrayList<FacturaDTO> a = servicio.listarFacturas();
//        int count = 0;
//        for (FacturaDTO f : a) {
//            System.out.println(f.getId()+"id");
//            System.out.println(count+"count");
//            tbFacturas.setValueAt(f.getId(),count, 0);
//            
////            tbFacturas.setValueAt(f.getFormaDePago(), count, 1);
////            tbFacturas.setValueAt(f.getEstado(), count, 2);
////            tbFacturas.setValueAt(f.getFechaFactura(), count, 3);
////            tbFacturas.setValueAt(f.getIva(), count, 4);
////            tbFacturas.setValueAt(f.getTotalPagar(), count, 5);
////            tbFacturas.setValueAt(f.getTotalPagado(), count, 6);
////            tbFacturas.setValueAt(f.getFechaEntrega(), count, 7);
////            tbFacturas.setValueAt(f.getCliente(), count, 8);
////            tbFacturas.setValueAt(f.getUsuario(), count, 9);
////            tbFacturas.setValueAt(f.getNumeroCuotas(), count, 10);
////            tbFacturas.setValueAt(f.getCuotas_pagadas(), count, 11);
////            tbFacturas.setValueAt(f.getTotalPagar() - f.getTotalPagado(), count, 12);
//            count++;
//        }
        //Agregando el modelo de la tabla
        tbFacturas.setModel(dtbmFacturas);

        tbFacturas.getColumnModel().getColumn(0).setMaxWidth(60);
        tbFacturas.getColumnModel().getColumn(1).setMaxWidth(100);
        tbFacturas.getColumnModel().getColumn(2).setMaxWidth(90);

        tbFacturas.getColumnModel().getColumn(3).setMaxWidth(80);
        tbFacturas.getColumnModel().getColumn(4).setMaxWidth(90);

        tbFacturas.getColumnModel().getColumn(5).setMaxWidth(60);
        tbFacturas.getColumnModel().getColumn(6).setMaxWidth(90);
        tbFacturas.getColumnModel().getColumn(7).setMaxWidth(100);
        tbFacturas.getColumnModel().getColumn(8).setMaxWidth(570);

        dtbmFacturas.addTableModelListener((TableModelEvent e) -> {

            long id;
//            long cantidad;

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tbFacturas.getValueAt(e.getFirstRow(), 0);
                String formaDePago = String.valueOf(tbFacturas.getValueAt(e.getFirstRow(), 2));

                String estado = String.valueOf(tbFacturas.getValueAt(e.getFirstRow(), 6));

                if (objId != null) {
                    id = Long.parseLong(String.valueOf(objId));
                    if (servicio.cambiarEstado(id, estado)) {
                        mostrarMensaje("Se edito el estado");
                    }

//                    if (servicio.cambiarFormaDePago(id, formaDePago)) {
//                        mostrarMensaje("El item se ha editado satisfactoriamente.");
//
//                        actualizarTablaFacturas();
//                        calcularTotales();
//                    } else {
//                        mostrarMensaje("No se pudo editar el item.");
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

//        agregarJComboBoxAColumna(tbFacturas, 2, new String[]{
//            FacturaDTO.FORMA_PAGO_CONTADO,
//            FacturaDTO.FORMA_PAGO_CUOTAS
//        });
        agregarJComboBoxAColumna(tbFacturas, 6, new String[]{
            FacturaDTO.ESTADO_DEPOSITO,
            FacturaDTO.ESTADO_ENTREGADO,
            FacturaDTO.ESTADO_PROCESO
        });

        aplicarEventosPersonalizados(tbVentas);
    }

    private void llenarTablaFacturas() {

        final String[] columnas = {
            "N°Item",
            "Cliente",
            "Fecha Item",
            "Total a pagar",
            "Fecha entrega",
            "Material",
            "Estado",
            "Numero Remision",
            "Comentario"
        };

        // Configurando los eventos de edicion de la tabla
        dtbmFacturas = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 6;
            }
        };
        //  Llenando la tabla
        servicio.listar2().forEach((RegistroDTO factura) -> {

            dtbmFacturas.addRow(factura.toArray());
            tbFacturas.setDefaultRenderer(Object.class, f);

        });

//        ArrayList<FacturaDTO> a = servicio.listarFacturas();
//        int count = 0;
//        for (FacturaDTO f : a) {
//            System.out.println(f.getId()+"id");
//            System.out.println(count+"count");
//            tbFacturas.setValueAt(f.getId(),count, 0);
//            
////            tbFacturas.setValueAt(f.getFormaDePago(), count, 1);
////            tbFacturas.setValueAt(f.getEstado(), count, 2);
////            tbFacturas.setValueAt(f.getFechaFactura(), count, 3);
////            tbFacturas.setValueAt(f.getIva(), count, 4);
////            tbFacturas.setValueAt(f.getTotalPagar(), count, 5);
////            tbFacturas.setValueAt(f.getTotalPagado(), count, 6);
////            tbFacturas.setValueAt(f.getFechaEntrega(), count, 7);
////            tbFacturas.setValueAt(f.getCliente(), count, 8);
////            tbFacturas.setValueAt(f.getUsuario(), count, 9);
////            tbFacturas.setValueAt(f.getNumeroCuotas(), count, 10);
////            tbFacturas.setValueAt(f.getCuotas_pagadas(), count, 11);
////            tbFacturas.setValueAt(f.getTotalPagar() - f.getTotalPagado(), count, 12);
//            count++;
//        }
        //Agregando el modelo de la tabla
        tbFacturas.setModel(dtbmFacturas);

        tbFacturas.getColumnModel().getColumn(0).setMaxWidth(60);
        tbFacturas.getColumnModel().getColumn(1).setMaxWidth(100);
        tbFacturas.getColumnModel().getColumn(2).setMaxWidth(90);

        tbFacturas.getColumnModel().getColumn(3).setMaxWidth(80);
        tbFacturas.getColumnModel().getColumn(4).setMaxWidth(90);

        tbFacturas.getColumnModel().getColumn(5).setMaxWidth(60);
        tbFacturas.getColumnModel().getColumn(6).setMaxWidth(90);
        tbFacturas.getColumnModel().getColumn(7).setMaxWidth(100);
        tbFacturas.getColumnModel().getColumn(8).setMaxWidth(570);

        dtbmFacturas.addTableModelListener((TableModelEvent e) -> {

            long id;
//            long cantidad;

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tbFacturas.getValueAt(e.getFirstRow(), 0);
                String formaDePago = String.valueOf(tbFacturas.getValueAt(e.getFirstRow(), 2));

                String estado = String.valueOf(tbFacturas.getValueAt(e.getFirstRow(), 6));

                if (objId != null) {
                    id = Long.parseLong(String.valueOf(objId));
                    if (servicio.cambiarEstado(id, estado)) {
                        mostrarMensaje("Se edito el estado");
                        Firebase fire= new Firebase();
                       
                        try {
                            fire.enviarMensaje();
                        } catch (IOException ex) {
                            Logger.getLogger(ifrmVentas3.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

//                    if (servicio.cambiarFormaDePago(id, formaDePago)) {
//                        mostrarMensaje("El item se ha editado satisfactoriamente.");
//
//                        actualizarTablaFacturas();
//                        calcularTotales();
//                    } else {
//                        mostrarMensaje("No se pudo editar el item.");
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

//        agregarJComboBoxAColumna(tbFacturas, 2, new String[]{
//            FacturaDTO.FORMA_PAGO_CONTADO,
//            FacturaDTO.FORMA_PAGO_CUOTAS
//        });
        agregarJComboBoxAColumna(tbFacturas, 6, new String[]{
            FacturaDTO.ESTADO_DEPOSITO,
            FacturaDTO.ESTADO_ENTREGADO,
            FacturaDTO.ESTADO_PROCESO
        });

        aplicarEventosPersonalizados(tbVentas);
    }

    private void llenarTablaFacturasFechas(Date FechaD, Date FechaH) {

        final String[] columnas = {
            "N°Item",
            "Cliente",
            "Fecha Item",
            "Total a pagar",
            "Fecha entrega",
            "Material",
            "Estado",
            "Numero Remision",
            "Comentario"
        };

        // Configurando los eventos de edicion de la tabla
        dtbmFacturas = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 6;
            }
        };
        //  Llenando la tabla
        servicio.listarRegistroFecha(FechaD, FechaH).forEach((RegistroDTO factura) -> {

            dtbmFacturas.addRow(factura.toArray());
            tbFacturas.setDefaultRenderer(Object.class, f);

        });
        ArrayList<RegistroDTO> r = servicio.listarRegistroFecha(FechaD, FechaH);
        double total = 0;
        for (RegistroDTO re : r) {
            total = total + re.getTotal_pagar();
        }
        txtTotalItems.setText(formateador.format(total));

//        ArrayList<FacturaDTO> a = servicio.listarFacturas();
//        int count = 0;
//        for (FacturaDTO f : a) {
//            System.out.println(f.getId()+"id");
//            System.out.println(count+"count");
//            tbFacturas.setValueAt(f.getId(),count, 0);
//            
////            tbFacturas.setValueAt(f.getFormaDePago(), count, 1);
////            tbFacturas.setValueAt(f.getEstado(), count, 2);
////            tbFacturas.setValueAt(f.getFechaFactura(), count, 3);
////            tbFacturas.setValueAt(f.getIva(), count, 4);
////            tbFacturas.setValueAt(f.getTotalPagar(), count, 5);
////            tbFacturas.setValueAt(f.getTotalPagado(), count, 6);
////            tbFacturas.setValueAt(f.getFechaEntrega(), count, 7);
////            tbFacturas.setValueAt(f.getCliente(), count, 8);
////            tbFacturas.setValueAt(f.getUsuario(), count, 9);
////            tbFacturas.setValueAt(f.getNumeroCuotas(), count, 10);
////            tbFacturas.setValueAt(f.getCuotas_pagadas(), count, 11);
////            tbFacturas.setValueAt(f.getTotalPagar() - f.getTotalPagado(), count, 12);
//            count++;
//        }
        //Agregando el modelo de la tabla
        tbFacturas.setModel(dtbmFacturas);

        tbFacturas.getColumnModel().getColumn(0).setMaxWidth(60);
        tbFacturas.getColumnModel().getColumn(1).setMaxWidth(100);
        tbFacturas.getColumnModel().getColumn(2).setMaxWidth(90);

        tbFacturas.getColumnModel().getColumn(3).setMaxWidth(80);
        tbFacturas.getColumnModel().getColumn(4).setMaxWidth(90);

        tbFacturas.getColumnModel().getColumn(5).setMaxWidth(60);
        tbFacturas.getColumnModel().getColumn(6).setMaxWidth(90);
        tbFacturas.getColumnModel().getColumn(7).setMaxWidth(100);
        tbFacturas.getColumnModel().getColumn(8).setMaxWidth(570);

        dtbmFacturas.addTableModelListener((TableModelEvent e) -> {

            long id;
//            long cantidad;

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tbFacturas.getValueAt(e.getFirstRow(), 0);
                String formaDePago = String.valueOf(tbFacturas.getValueAt(e.getFirstRow(), 2));

                String estado = String.valueOf(tbFacturas.getValueAt(e.getFirstRow(), 6));

                if (objId != null) {
                    id = Long.parseLong(String.valueOf(objId));
                    if (servicio.cambiarEstado(id, estado)) {
                        mostrarMensaje("Se edito el estado");
                    }

//                    if (servicio.cambiarFormaDePago(id, formaDePago)) {
//                        mostrarMensaje("El item se ha editado satisfactoriamente.");
//
//                        actualizarTablaFacturas();
//                        calcularTotales();
//                    } else {
//                        mostrarMensaje("No se pudo editar el item.");
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

//        agregarJComboBoxAColumna(tbFacturas, 2, new String[]{
//            FacturaDTO.FORMA_PAGO_CONTADO,
//            FacturaDTO.FORMA_PAGO_CUOTAS
//        });
        agregarJComboBoxAColumna(tbFacturas, 6, new String[]{
            FacturaDTO.ESTADO_DEPOSITO,
            FacturaDTO.ESTADO_ENTREGADO,
            FacturaDTO.ESTADO_PROCESO
        });

        aplicarEventosPersonalizados(tbVentas);
    }

    private void llenarTablaImpuestos() {

        final String[] columnas = {"Codigo", "Nombre", "Valor"};

        // Configurando los eventos de edicion de la tabla
        dtbmImpuestos = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0;
            }
        };
        // Llenando la tabla
        servicio.listarImpuestos().forEach((ImpuestoDTO impuesto) -> {
            dtbmImpuestos.addRow(impuesto.toArray());
        });
        //Agregando el modelo de la tabla
        tbImpuestos.setModel(dtbmImpuestos);

        dtbmImpuestos.addTableModelListener((TableModelEvent e) -> {

            long id;
            String nombre;
            double valor;

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tbImpuestos.getValueAt(e.getFirstRow(), 0);
                Object objNombre = tbImpuestos.getValueAt(e.getFirstRow(), 1);
                Object objValor = tbImpuestos.getValueAt(e.getFirstRow(), 2);

                if (objNombre == null || objValor == null) {
                    return;
                }

                if (!String.valueOf(objValor).matches(REGEX_DECIMAL)) {
                    mostrarMensaje("Sólo se permiten números para el valor");
                    return;
                } else {
                    valor = Double.valueOf(String.valueOf(objValor));
                }

                nombre = String.valueOf(objNombre);

                if (objId != null) {
                    id = Long.parseLong(String.valueOf(objId));

                    if (servicio.editarImpuesto(id, nombre, valor)) {
                        actualizarTablaImpuestos();
                        mostrarMensaje("Se ha editado el impuesto satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo editar el impuesto");
                    }
                } else {
                    if (servicio.registrarImpuesto(nombre, valor)) {
                        actualizarTablaImpuestos();
                        mostrarMensaje("Se ha registrado el impuesto satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo registrar el impuesto");
                    }
                }
            }
        });

        aplicarEventosPersonalizados(tbImpuestos);
    }

    private void llenarTablaPagosFiltro(long i) {

        final String[] columnas = {"N°Pago", "Nombre", "Numero Factura", "Abono", "Fecha"};

        // Configurando los eventos de edicion de la tabla
        dtbmPagos = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0 && column < 5;
            }
        };
        // Llenando la tabla
        ArrayList<PagoDTO> paagos = servicio.listarNombre(i);
        for (PagoDTO p : paagos) {

            String nombre = "";

            dtbmPagos.addRow(new Object[]{p.getId(), nombre, p.getRegist(), p.getAbono(), p.getFecha(), p.getCuotas()});
        }
        //Agregando el modelo de la tabla
        tbPagos.setModel(dtbmPagos);

        dtbmPagos.addTableModelListener((TableModelEvent e) -> {

            long id;
            long factura = 0;
            double abono = 0;
            long cuotas = 0;
            double totalAbonado = 0;

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tbPagos.getValueAt(e.getFirstRow(), 0);
                Object objFactura = tbPagos.getValueAt(e.getFirstRow(), 1);
                Object objAbono = tbPagos.getValueAt(e.getFirstRow(), 2);
                Object objnumero = tbPagos.getValueAt(e.getFirstRow(), 4);

                if (objFactura == null || objAbono == null) {
                    return;
                }

                if (!String.valueOf(objAbono).matches(REGEX_DECIMAL)) {
                    mostrarMensaje("Sólo se permiten números para el valor");
                    return;
                } else {
                    String a = String.valueOf(objAbono);

                    System.out.println(a + "abono");
                    abono = Double.parseDouble(String.valueOf(objAbono));
                }

                ArrayList<RegistroDTO> facturaDTO = servicio.listarRegistro(Long.parseLong(String.valueOf(objFactura)));
                for (RegistroDTO registroDTO : facturaDTO) {
                    factura = registroDTO.getId();
                    totalAbonado = registroDTO.getTotal_pagado();
                }

                if (objnumero != null) {
                    cuotas = Long.parseLong(String.valueOf(objnumero));
                }
                System.out.println(objId + "objId");
                if (objId != null) {
                    System.out.println("entra 1");
                    id = Long.parseLong(String.valueOf(objId));
                    System.out.println("entra 2");
                    System.out.println(id);
                    System.out.println(abono);
                    System.out.println(factura);
                    System.out.println(cuotas);
                    boolean x = servicio.editarPago(id, abono, factura, cuotas);
                    System.out.println(x);
                    if (x) {
                        System.out.println("entra 3");
                        mostrarMensaje("Se ha editado el pago satisfactoriamente");
                        long a = servicio.consultaTotalPagos(factura);
//                        servicio.registrarCuotasPagadas(factura, a);
                        System.out.println("total" + a);

                    } else {
                        mostrarMensaje("No se pudo editar el pago");
                    }
                    actualizarTablaPagos();
                } else {
                    System.out.println(abono);
                    if (servicio.registrarPago(abono, factura)) {
                        double totalAbonad = totalAbonado + abono;
                        servicio.actualizarPago(factura, totalAbonad);
                        long a = servicio.consultaTotalPagos(factura);
                        servicio.registrarCuotasPagadas(factura, a);
                        System.out.println("total" + a);
                        mostrarMensaje("Se ha registrado el pago satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo registrar el pago");
                    }
                    actualizarTablaPagos();
                }

            }
        });
//        agregarJComboBoxAColumna(tbPagos, 1, servicio.listarFacturas().toArray());
        aplicarEventosPersonalizados(tbPagos);
    }

    private void llenarTablaPagosFiltros(long ids) {

        final String[] columnas = {"N°Pago", "Nombre", "Numero Factura", "Abono", "Fecha"};

        // Configurando los eventos de edicion de la tabla
        dtbmPagos = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

//            @Override
            public boolean isCellEditable(int row, int column) {
                return column < 0;
            }
        };
        // Llenando la tabla
        ArrayList<PagoFacDTO> paagos = servicio.listarNombrePagos(ids);
        for (PagoFacDTO p : paagos) {
            //ArrayList<FacturaDTO> r = servicio.buscarFactura(p.getFactura());
            //String nombre = "";
            //for (FacturaDTO re : r) {
               // ClienteDTO c = servicio.consultarCliente(p.get);
                //nombre = c.getNombres() + " " + c.getApellidos();
            //}
            dtbmPagos.addRow(new Object[]{p.getId(), p.getNombre()+" "+p.getApellido(), p.getFactura(), formateador.format(p.getAbono()), p.getFecha()});
        }
        //Agregando el modelo de la tabla
        tbPagos.setModel(dtbmPagos);

        dtbmPagos.addTableModelListener((TableModelEvent e) -> {

            long id;
            long factura = 0;
            double abono = 0;
            long cuotas = 0;
            double totalAbonado = 0;

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tbPagos.getValueAt(e.getFirstRow(), 0);
                Object objFactura = tbPagos.getValueAt(e.getFirstRow(), 1);
                Object objAbono = tbPagos.getValueAt(e.getFirstRow(), 2);
                Object objnumero = tbPagos.getValueAt(e.getFirstRow(), 4);

                if (objFactura == null || objAbono == null) {
                    return;
                }

                if (!String.valueOf(objAbono).matches(REGEX_DECIMAL)) {
                    mostrarMensaje("Sólo se permiten números para el valor");
                    return;
                } else {
                    String a = String.valueOf(objAbono);

                    System.out.println(a + "abono");
                    abono = Double.parseDouble(String.valueOf(objAbono));
                }

                System.out.println(objId + "objId");
                if (objId != null) {
                    System.out.println("entra 1");
                    id = Long.parseLong(String.valueOf(objId));
                    System.out.println("entra 2");
                    System.out.println(id);
                    System.out.println(abono);
                    System.out.println(factura);
                    System.out.println(cuotas);
                    boolean x = servicio.editarPago(id, abono, factura, cuotas);
                    System.out.println(x);
                    if (x) {
                        System.out.println("entra 3");
                        mostrarMensaje("Se ha editado el pago satisfactoriamente");
                        long a = servicio.consultaTotalPagos(factura);
//                        servicio.registrarCuotasPagadas(factura, a);
                        System.out.println("total" + a);

                    } else {
                        mostrarMensaje("No se pudo editar el pago");
                    }
                    actualizarTablaPagos();
                } else {
                    System.out.println(abono);
                    if (servicio.registrarPago(abono, factura)) {
                        double totalAbonad = totalAbonado + abono;
                        servicio.actualizarPago(factura, totalAbonad);
                        long a = servicio.consultaTotalPagos(factura);
                        servicio.registrarCuotasPagadas(factura, a);
                        System.out.println("total" + a);
                        mostrarMensaje("Se ha registrado el pago satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo registrar el pago");
                    }
                    actualizarTablaPagos();
                }

            }
        });
//        agregarJComboBoxAColumna(tbPagos, 1, servicio.listarFacturas().toArray());
        aplicarEventosPersonalizados(tbPagos);
    }

    private void llenarTablaPagos() {

        final String[] columnas = {"N°Pago", "Nombre", "Numero Factura", "Abono", "Fecha"};

        // Configurando los eventos de edicion de la tabla
        dtbmPagos = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

//            @Override
            public boolean isCellEditable(int row, int column) {
                return column < 0;
            }
        };
        // Llenando la tabla
        ArrayList<PagoFacDTO> paagos = servicio.listarPagos();
        for (PagoFacDTO p : paagos) {
           
            dtbmPagos.addRow(new Object[]{p.getId(),p.getNombre()+" "+p.getApellido(), p.getFactura(), formateador.format(p.getAbono()), p.getFecha()});
        }
        //Agregando el modelo de la tabla
        tbPagos.setModel(dtbmPagos);

        dtbmPagos.addTableModelListener((TableModelEvent e) -> {

            long id;
            long factura = 0;
            double abono = 0;
            long cuotas = 0;
            double totalAbonado = 0;

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tbPagos.getValueAt(e.getFirstRow(), 0);
                Object objFactura = tbPagos.getValueAt(e.getFirstRow(), 1);
                Object objAbono = tbPagos.getValueAt(e.getFirstRow(), 2);
                Object objnumero = tbPagos.getValueAt(e.getFirstRow(), 4);

                if (objFactura == null || objAbono == null) {
                    return;
                }

                if (!String.valueOf(objAbono).matches(REGEX_DECIMAL)) {
                    mostrarMensaje("Sólo se permiten números para el valor");
                    return;
                } else {
                    String a = String.valueOf(objAbono);

                    System.out.println(a + "abono");
                    abono = Double.parseDouble(String.valueOf(objAbono));
                }

                System.out.println(objId + "objId");
                if (objId != null) {
                    System.out.println("entra 1");
                    id = Long.parseLong(String.valueOf(objId));
                    System.out.println("entra 2");
                    System.out.println(id);
                    System.out.println(abono);
                    System.out.println(factura);
                    System.out.println(cuotas);
                    boolean x = servicio.editarPago(id, abono, factura, cuotas);
                    System.out.println(x);
                    if (x) {
                        System.out.println("entra 3");
                        mostrarMensaje("Se ha editado el pago satisfactoriamente");
                        long a = servicio.consultaTotalPagos(factura);
//                        servicio.registrarCuotasPagadas(factura, a);
                        System.out.println("total" + a);

                    } else {
                        mostrarMensaje("No se pudo editar el pago");
                    }
                    actualizarTablaPagos();
                } else {
                    System.out.println(abono);
                    if (servicio.registrarPago(abono, factura)) {
                        double totalAbonad = totalAbonado + abono;
                        servicio.actualizarPago(factura, totalAbonad);
                        long a = servicio.consultaTotalPagos(factura);
                        servicio.registrarCuotasPagadas(factura, a);
                        System.out.println("total" + a);
                        mostrarMensaje("Se ha registrado el pago satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo registrar el pago");
                    }
                    actualizarTablaPagos();
                }

            }
        });
//        agregarJComboBoxAColumna(tbPagos, 1, servicio.listarFacturas().toArray());
        aplicarEventosPersonalizados(tbPagos);
    }

    //Actualizando la información de las tablas teniendo en cuenta sus relaciones.
    private void actualizarTablaVentas() {
        tbVentas.removeAll();
        dtbmVentas = null;
        llenarTablaVentas();

    }

    private void actualizarTablaVentas2() {
        tbVentas1.removeAll();
        dtbmVentas2 = null;

        llenarTablaVentasRegistro(codigo);

    }

    private void actualizarTablaImpuestos() {
        tbImpuestos.removeAll();
        dtbmImpuestos = null;
        llenarTablaImpuestos();
        actualizarIvaFactura();
    }

    private void actualizarTablaFacturas() {
        tbFacturas.removeAll();
        dtbmFacturas = null;
        llenarTablaFacturas();
        actualizarTablaPagos();
    }

    private void actualizarTablaPagos() {
        tbPagos.removeAll();
        dtbmPagos = null;
        llenarTablaPagos();
    }

    private void actualizarIvaFactura() {
        lbIVA.setText("IVA aL " + servicio.consultarImpuesto(1).getValor() + "%:");
    }

//    private void actualizarCodigoFactura() {
//        textNumeroFactura.setText(String.format("%04d", servicio.consultarIncrementoFacturas()));
//    }

    private void actualizarCodigoFactura2() {
        textNumeroFactura1.setText(String.format("%04d", servicio.consultarUltimaFactura()));
    }

    //total cada uno
    private void totalUnoIva() {
        int cantidad = tbVentas.getRowCount();
        int cont = 0;
        double total = 0;
        double preciot = 0;
        int cantida = 0;
        double iva = servicio.consultarImpuesto(1).getValor();
        double i = 0;

        while (cont < cantidad) {
            Object objCantidad = tbVentas.getValueAt(cont, 2);
            Object objPrecio = tbVentas.getValueAt(cont, 3);
            Object objIva = tbVentas.getValueAt(cont, 5);

            String iv = String.valueOf(objIva);
            iv = iv.replace(".", "");
            iv = iv.replace(",", ".");
            cantida = Integer.parseInt(String.valueOf(objCantidad));
            String precio = String.valueOf(objPrecio);
            precio = precio.replace(".", "");
            precio = precio.replace(",", ".");
            preciot = Double.parseDouble(precio);

            total = cantida * preciot;
            i = total * (iva / 100);
            tbVentas.setValueAt(formateador.format(preciot), cont, 3);
            tbVentas.setValueAt(formateador.format(i), cont, 5);
            tbVentas.setValueAt(formateador.format(total + i), cont, 6);
            System.out.println(total + "total");

            cont++;
        }

    }

    private void totalUno() {
        int cantidad = tbVentas.getRowCount();
        int cont = 0;
        double total = 0;
        double preciot = 0;
        int cantida = 0;
        double iva = servicio.consultarImpuesto(1).getValor();
        double i = 0;
System.out.println("cantidad fila "+cantidad);
        while (cont < cantidad) {
            Object objCantidad = tbVentas.getValueAt(cont, 2);
            Object objPrecio = tbVentas.getValueAt(cont, 3);
            Object objIva = tbVentas.getValueAt(cont, 5);

            cantida = Integer.parseInt(String.valueOf(objCantidad));
            String precio = String.valueOf(objPrecio);
            precio = precio.replace(",", ".");
            precio = precio.replace(".", "");
            preciot = Double.parseDouble(precio);

            total = cantida * preciot;
            
            System.out.println("TotalUno ");

            tbVentas.setValueAt(0, cont, 5);
            tbVentas.setValueAt(formateador.format(preciot), cont, 3);
            tbVentas.setValueAt(formateador.format(total + 0), cont, 6);
            System.out.println(total + "total");

            cont++;
        }

    }

    // Calculando los totales para el registro de la factura de venta.
    private void calcularTotales() {

        double subTotal = 0;
        double iva = servicio.consultarImpuesto(1).getValor();
        double valorIva = 0;
        double totalPagar = 0;

        for (int f = 0; f < tbVentas.getRowCount(); f++) {
            Object objCantidad = tbVentas.getValueAt(f, 2);
            Object objPrecioUnitario = tbVentas.getValueAt(f, 3);

            if (objCantidad == null
                    || objPrecioUnitario == null) {
                return;
            }

            long cantidad = Long.parseLong(String.valueOf(objCantidad));
            String precio = String.valueOf(objPrecioUnitario);
            precio = precio.replace(",", ".");
            precio = precio.replace(".", "");
            double precioUnitario = Double.parseDouble(precio);
//            double total=precioUnitario*cantidad;
            if (!ckIVA.isSelected()) {
                subTotal += precioUnitario * cantidad;
            }
//            tbVentas.setValueAt(formateador.format(total), f, 6);

        }

        textSubtotal.setText(formateador.format(subTotal));

        valorIva = subTotal * (iva / 100);
        textIVA.setText("0");

        facturaDTO.setIva(valorIva);
        totalPagar = subTotal + valorIva;

        facturaDTO.setTotalPagar(totalPagar);

        //   texTotalPagar.setText(String.format("%.2f", totalPagar));
        DecimalFormat formateador = new DecimalFormat("###,###.##");

        System.out.println("" + formateador.format(totalPagar));
        lblTotalPagar.setText("$" + formateador.format(subTotal));

    }

    //Agrega producto a la tabla Ventas
    private void agregarProductoAFactura(MaterialDTO producto) {

        boolean agregar = true;

        for (int f = 0; f < tbVentas.getRowCount(); f++) {
            Object objId = tbVentas.getValueAt(f, 1);

            if (objId == null) {

                agregar = false;
                break;
            }

            MaterialDTO p = (MaterialDTO) objId;
            if ((producto == null)) {
                agregar = false;
                break;
            }

        }

//        if (quitar) {
//            dtbmVentas.removeRow(tbVentas.getRowCount() - 1);
//        }
        if (agregar) {
            dtbmVentas.addRow(new Object[]{producto.getId(), producto, 1, 0, "", 0, 0});
        }

    }

    public void llenarComboClientesAbonos() {

        jComboBox5.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<ClienteDTO> listar = servicio.listarClientes();
        jComboBox5.addItem("-");
        for (ClienteDTO n : listar) {
            jComboBox5.addItem(n.getNombres() + " " + n.getApellidos() + "/" + n.getId());
        }
    }

    public void llenarComboClientesventas() {

        jComboBox2.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<ClienteDTO> listar = servicio.listarClientes();
        jComboBox2.addItem("-");
        for (ClienteDTO n : listar) {
            jComboBox2.addItem(n.getNombres() + " " + n.getApellidos() + "/" + n.getId());
        }
    }

    public void llenarComboClientesventas2() {

        jComboBox3.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<ClienteDTO> listar = servicio.listarClientes();
        jComboBox3.addItem("-");
        for (ClienteDTO n : listar) {
            jComboBox3.addItem(n.getNombres() + " " + n.getApellidos() + "/" + n.getId());
        }
    }

    public void llenarComboMaterial() {

        jComboBox1.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<MaterialDTO> listar = servicio.listarMateriales();

        for (MaterialDTO n : listar) {
            jComboBox1.addItem(n.getNombre());
        }
    }

    //Agregando los productos a la factura a través de la tabla venta.
    private boolean registrarVentas() {

        MaterialDTO producto = null;
        long cantidad = 0;
        String comentario = "";
        long idFactura = 0;
        double precio = 0;
        String estado = "";

        for (int i = 0; i < tbVentas.getRowCount(); i++) {

            if (tbVentas.getValueAt(i, 0) == null) {
                break;
            }

//            idFactura = servicio.consultarIncrementoFacturas() - 1;
            producto = servicio.consultarMaterialNombre(String.valueOf(tbVentas.getValueAt(i, 1)));
            cantidad = Long.parseLong(String.valueOf(tbVentas.getValueAt(i, 2)));
            String p = String.valueOf(tbVentas.getValueAt(i, 3));
            p = p.replace(".", "");
            p = p.replace(",", ".");
            precio = Double.parseDouble(p);

            comentario = String.valueOf(tbVentas.getValueAt(i, 4));
            estado = String.valueOf(comboEstadoFactura.getSelectedItem());
            System.out.println(producto.getId());
            System.out.println(idFactura);
            Object check = tbVentas1.getValueAt(i, 6);
            boolean ch = new Boolean(String.valueOf(check));

//            boolean f = servicio.registrarVenta(cantidad, comentario, idFactura, producto.getId(), precio, estado);
//            System.out.println("f: " + f);
        }
        return true;
    }

    private int registrarVentas2(long factura) {

        MaterialDTO producto = null;
        long cantidad = 0;
        String comentario = "";
        long idFactura = 0;
        double precio = 0;
        String estado = "";
        int productos = 0;
       // idFactura = servicio.consultarIncrementoRemision() - 1;
        for (int i = 0; i < tbVentas1.getRowCount(); i++) {

            if (tbVentas1.getValueAt(i, 0) == null) {
                break;
            }
            long id = 0;

            Object objId = tbVentas1.getValueAt(i, 0);
            id = Long.parseLong(String.valueOf(objId));
            estado = String.valueOf(tbVentas1.getValueAt(i, 7));

            comentario = String.valueOf(tbVentas1.getValueAt(i, 6));

            Object check = tbVentas1.getValueAt(i, 11);
            boolean ch = new Boolean(String.valueOf(check));

            Object o = tbVentas1.getValueAt(i, 12);
            String v = String.valueOf(o);
            if (ch) {

                //;
                //System.out.println(idUltima);
                ArrayList<RegistroDTO> registro = servicio.listarRegistro(id);
                long usuario = 0, cliente = 0, material = 0, cantid = 0, numeroCuotas = 0, CuotasPagadas = 0;
                double prec = 0, iva = 0, total_pagar = 0, totalPagado = 0;
                Date d = null;
                String comentar = "", esta = "", forma_de_pago = "";
                productos = productos + 1;
                for (RegistroDTO r : registro) {
                    servicio.cambiarEstado(id, estado);
                    servicio.cambiarImpresion(id, 1);
                    servicio.cambiarIdFactura(id, factura);
                    servicio.cambiarComentario(id, comentario);
                }

            }
        }
        return productos;
    }
//registrarfactura

    private void registrarFactura() {
        long id;
        String formaDePago = String.valueOf(comboFormaDePago.getSelectedItem());
        String estado = String.valueOf(comboEstadoFactura.getSelectedItem());
        double iva = 0;
        double totalPagar = 0;
        double totalPagado = 0;
        long numeroCuotas = 0;
        boolean z = ckIVA.isSelected();

        // Entrada de la fecha de entrega, si no se selecciona ninguna, registrará la fecha actual por defecto.
        Date fechaEntrega = Date.valueOf(dateFechaEntrega.getDateStringOrSuppliedString(LocalDate.now().toString()));
//        Timestamp fecha = Timestamp.valueOf(dateFechaEntrega.getDateStringOrSuppliedString(LocalDate.now().toString()));
        long cliente = 0, usuario = SESION_USUARIO.getId();

        if (!texTotalPagado.getText().matches(REGEX_DECIMAL)) {
            mostrarMensaje("Sólo se permiten numeros para el total pagado.");
            return;
        }

        if (!validarDouble(texTotalPagado.getText())) {
            mostrarMensaje("Número demasiado grande para el valor de total pagado.");
            return;
        }

        totalPagado = Long.parseLong(texTotalPagado.getText());

        String codigoCliente = textCodigoCliente.getText();

        if (codigoCliente.matches(REGEX_ENTERO_POSITIVO) && !codigoCliente.equals("0")) {
            cliente = Long.parseLong(codigoCliente);
        } else {
            mostrarMensaje("Seleccione un cliente.");
            return;
        }
        MaterialDTO producto = null;
        int cantida = tbVentas.getRowCount();
        int cont = 0;
        double precio = 0;
        long cantidad = 0;
        String comentario = "";

        while (cont < cantida) {
            producto = (MaterialDTO) tbVentas.getValueAt(cont, 1);
            cantidad = Long.parseLong(String.valueOf(tbVentas.getValueAt(cont, 2)));
            precio = Double.parseDouble(String.valueOf(tbVentas.getValueAt(cont, 3)));
            comentario = String.valueOf(tbVentas.getValueAt(cont, 4));
//            boolean x = servicio.registrarRegistro(usuario, cliente, usuario, precio,
//                    cantidad, comentario, fecha, estado, formaDePago, numeroCuotas, cantidad, fechaEntrega);

//            System.out.println(x);
            cont++;
            System.out.println(cont);
        }
        if (cont == cantida) {
            mostrarMensaje("Se registro la venta exitosamente");
        }
//        if (servicio.registrarFactura(formaDePago, estado, iva, totalPagar,
//                totalPagado, fechaEntrega, cliente, usuario, numeroCuotas)) {
//            actualizarTablaFacturas();
//            if (!registrarVentas()) {
//                mostrarMensaje("Hubo un error al registrar los productos a la factura de venta.");
//                return;
//            }
//            mostrarMensaje("Se ha registrado la factura de venta satisfactoriamente.");
//            actualizarTablaPagos();
//        } else {
//            mostrarMensaje("No se pudo registrar la factura de venta.");
//        }
    }

    private void registrarFactura2() {
        long id;
        String formaDePago = String.valueOf(comboFormaDePago.getSelectedItem());
        String estado = String.valueOf(comboEstadoFactura.getSelectedItem());
        double iva = 0;
        double totalPagar = 0;
        double totalPagado = 0;
        long numeroCuotas = 0;

        // Entrada de la fecha de entrega, si no se selecciona ninguna, registrará la fecha actual por defecto.
        Date fechaEntrega = Date.valueOf(dateFechaEntrega.getDateStringOrSuppliedString(LocalDate.now().toString()));
//        Timestamp fecha = Timestamp.valueOf(dateFechaEntrega.getDateStringOrSuppliedString(LocalDate.now().toString()));
        long cliente = 0, usuario = SESION_USUARIO.getId();

        try {
            String s = textIVA.getText();
            System.out.println("1");
            String p = lblTotalPagar.getText();
            p = p.replace("$", "");
            System.out.println("2");
            String camIv = s.replace(",", ".");
            System.out.println("3");
            String camTo = p.replace(".", "");
            System.out.println("4");
            camTo = camTo.replace(",", ".");

            if (ckIVA.isSelected()) {
                System.out.println("entro 0");
                s = s.replace(".", "");
                String nuevo = s.replaceAll(",", ".");
                iva = Double.parseDouble(nuevo);

                //  iva = String.format(p, os);
                System.out.println(iva);

            } else {
                iva = Double.parseDouble(s);
            }

            System.out.println(iva + "ivaa");
            System.out.println(camTo + "camTo");

            totalPagar = Double.parseDouble(camTo);
            System.out.println(totalPagar + "" + "total a pagar");

//            int filas=tbVentas.getRowCount();
//            int cant=0;
//            while(cant<filas){
//                String pe=String.valueOf(tbVentas.getValueAt(cant, 6));
//                pe=pe.replace(".", "");
//                pe=pe.replace(",", ".");
//                double te=Double.parseDouble(pe);
//                totalPagar=totalPagar+te;
//            }
        } catch (NumberFormatException e) {
            mostrarMensaje("Por favor agregue uno o más productos.");
            return;
        }

        if (!texTotalPagado.getText().matches(REGEX_DECIMAL)) {
            mostrarMensaje("Sólo se permiten numeros para el total pagado.");
            return;
        }

        if (!validarDouble(texTotalPagado.getText())) {
            mostrarMensaje("Número demasiado grande para el valor de total pagado.");
            return;
        }

        totalPagado = Long.parseLong(texTotalPagado.getText());

        String codigoCliente = textCodigoCliente.getText();

        if (codigoCliente.matches(REGEX_ENTERO_POSITIVO) && !codigoCliente.equals("0")) {
            cliente = Long.parseLong(codigoCliente);
        } else {
            mostrarMensaje("Seleccione un cliente.");
            return;
        }

        MaterialDTO producto = null;
        int cantida = tbVentas.getRowCount();
        int cont = 0;
        double precio = 0;
        long cantidad = 0;
        double iv = 0;
        double to = 0;
        String comentario = "";
        

//      
        
            actualizarTablaFacturas();
            
            while (cont < cantida) {
                producto = (MaterialDTO) tbVentas.getValueAt(cont, 1);
                cantidad = Long.parseLong(String.valueOf(tbVentas.getValueAt(cont, 2)));
                String precioo = String.valueOf(tbVentas.getValueAt(cont, 3));
                precioo = precioo.replace(",", ".");
                precioo = precioo.replace(".", "");
                precio = Double.parseDouble(precioo);
                comentario = String.valueOf(tbVentas.getValueAt(cont, 4));

                String m = String.valueOf(tbVentas.getValueAt(cont, 5));
                m = m.replace(",", ".");
                m = m.replace(".", "");
                System.out.println(m + "iva");
                iv = Double.parseDouble(m);
                String n = String.valueOf(tbVentas.getValueAt(cont, 6));

                n = n.replace(",", ".");
                n = n.replace(".", "");

                to = Double.parseDouble(n);

                boolean x = servicio.registrarRegistro(usuario, cliente, producto.getId(), precio, cantidad, comentario,
                        estado, fechaEntrega, iv, to);

                System.out.println(x);
                cont++;
                System.out.println(cont);
            
            }

//            if (!registrarVentas()) {
//                mostrarMensaje("Hubo un error al registrar los productos a la factura de venta.");
//                return;
//            }
            mostrarMensaje("Se ha registrado la factura de venta satisfactoriamente.");
    //        actualizarTablaPagos();
        
    }

    private void registrarFacturaFa() {
        long id;
//        String formaDePago = String.valueOf(comboFormaDePago.getSelectedItem());
//        String estado = String.valueOf(comboEstadoFactura.getSelectedItem());
        double iva = 0;
        double totalPagar = 0;
        double totalPagado = 0;
        long numeroCuotas = 0;
        long cuotasPagdas = 0;

        // Entrada de la fecha de entrega, si no se selecciona ninguna, registrará la fecha actual por defecto.
//        Date fechaEntrega = Date.valueOf(dateFechaEntrega.getDateStringOrSuppliedString(LocalDate.now().toString()));
//        Timestamp fecha = Timestamp.valueOf(dateFechaEntrega.getDateStringOrSuppliedString(LocalDate.now().toString()));
        long cliente = 0, usuario = SESION_USUARIO.getId();

        try {
            String s = textIVA1.getText();
            System.out.println("1");
            String p = lblTotalPagar1.getText();
            System.out.println("2");
            String camIv = s.replace(",", ".");
            System.out.println("3");
            String camTo = p.replace(".", "");
            System.out.println("4");
            camTo = camTo.replace(",", ".");

            if (ckIVA1.isSelected()) {
                System.out.println("entro 0");
                s = s.replace(".", "");
                String nuevo = s.replaceAll(",", ".");
                iva = Double.parseDouble(nuevo);

                //  iva = String.format(p, os);
                System.out.println(iva);

            } else {
                iva = Double.parseDouble(s);
            }

            System.out.println(iva + "ivaa");
            System.out.println(camTo + "camTo");
            camTo = camTo.replace("$", "");
            totalPagar = Double.parseDouble(camTo);
            System.out.println(totalPagar + "" + "total a pagar");

        } catch (NumberFormatException e) {
            mostrarMensaje("Por favor agregue uno o más productos.");
            return;
        }

//        totalPagado = Long.parseLong(texTotalPagado1.getText());
        // String codigoCliente = textCodigoCliente.getText();
//        if (codigoCliente.matches(REGEX_ENTERO_POSITIVO) && !codigoCliente.equals("0")) {
//            cliente = Long.parseLong(codigoCliente);
//        } else {
//            mostrarMensaje("Seleccione un cliente.");
//            return;
//        }
        MaterialDTO producto = null;
        int cantida = tbVentas1.getRowCount();
        int cont = 0;
        double precio = 0;
        long cantidad = 0;
        String comentario = "";
        Date d = Date.valueOf(LocalDate.MIN);
        int filas = tbVentas1.getRowCount();
        int count = 0;
        double abon = 0;
        long i = 0;
//      
        while (count < filas) {
            Object check = tbVentas1.getValueAt(count, 15);
            boolean ch = new Boolean(String.valueOf(check));
            if (ch) {

                String forma = String.valueOf(tbVentas1.getValueAt(count, 6));
                String estado = String.valueOf(tbVentas1.getValueAt(count, 5));
                String abono = String.valueOf(tbVentas1.getValueAt(count, 11));
                String id_f = String.valueOf(tbVentas1.getValueAt(count, 0));
                String material = String.valueOf(tbVentas1.getValueAt(count, 1));
                cantidad = Long.parseLong(String.valueOf(tbVentas1.getValueAt(count, 2)));
                String pre = String.valueOf(tbVentas1.getValueAt(count, 3));
                comentario = String.valueOf(tbVentas1.getValueAt(count, 4));
                long cuotas = Long.parseLong(String.valueOf(tbVentas1.getValueAt(count, 14)));
                pre = pre.replace(".", "");
                pre = pre.replace(",", ".");
                precio = Double.parseDouble(pre);
                MaterialDTO mate = servicio.consultarMaterialNombre(material);
                abono = abono.replace(".", "");
                abono = abono.replace(",", ".");
                abon = Double.parseDouble(abono);
                i = Long.parseLong(id_f);

                ClienteDTO clien = servicio.consultarCliente(Long.parseLong(textDocumento1.getText()));
                cliente = clien.getId();
//                RegistroDTO r= servicio.listarRegistro(i);
                ArrayList<RegistroDTO> regist = servicio.listarRegistro(i);
                System.out.println(regist + "registro");

                for (RegistroDTO registroDTO : regist) {

                    double abonon = registroDTO.getTotal_pagado() + abon;
                    if (servicio.cambiarEstado(i, estado)) {
                        System.out.println("se edito");
                    } else {

                    }
                    if (servicio.cambiarImpresion(i, 1)) {
                        System.out.println("se cambio");
                    } else {

                    }

                    numeroCuotas = registroDTO.getNumeroCuotas();
                    cuotasPagdas = registroDTO.getCuotas_pagadas();
                    iva = registroDTO.getIva();
                    totalPagar = registroDTO.getTotal_pagar();
                    totalPagado = registroDTO.getTotal_pagado();

                    if (servicio.actualizarPago(i, abonon)) {
                        mostrarMensaje("Se actualizo ");
                    } else {
                        mostrarMensaje("No se actualizo");
                    }

                }
//                        servicio.actualizarPago(i, totalAbonado);
                if (forma.equalsIgnoreCase("CUOTAS")) {
                    if (servicio.registrarPago(abon, i)) {
                        long ad = servicio.consultaTotalPagos(i);
                        servicio.registrarCuotasPagad(i, ad);
                        actualizarTablaPagos();
                        mostrarMensaje("Se registro un pago");
                    } else {
                        mostrarMensaje("No se registro el pago");
                    }
                }

                if (servicio.registrarContado(usuario, cliente, mate.getId(), precio, cantidad, comentario, estado, forma,
                        numeroCuotas, cuotasPagdas, d, iva, totalPagar, totalPagado)) {
                    mostrarMensaje("se registro");
                }

            }
            count++;
        }

    }
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened

        if (SESION_USUARIO.isVistaPermitida(IMPUESTOS)) {
          //  llenarTablaImpuestos();
           // actualizarIvaFactura();
//            actualizarCodigoFactura();
            //actualizarCodigoFactura2();
            
            
            
        }
//        if (SESION_USUARIO.isVistaPermitida(PAGOS)) {
//            llenarTablaPagos();
//        }

        if (SESION_USUARIO.isVistaPermitida(VENTAS)) {
            llenarTablaVentas();

            //llenarTablaFacturas();
            //crearComboEditable(comboBuscarProducto, "PRODUCTOS");
//            crearComboEditable(comboBuscarFactura, "FACTURAS");
            //crearComboEditable(comboBuscarCliente, "CLIENTES");
            //crearComboEditable2(comboBuscarCliente1, "CLIENTES");
            //crearComboEditable3(comboBuscarCliente2, "CLIENTES");
            //  calcularSubTotal();
        }
    }//GEN-LAST:event_formInternalFrameOpened
    private void calcularTotales2() {

        double subTotal = 0;
        double iva = servicio.consultarImpuesto(1).getValor();
        double valorIva = 0;
        double totalPagar = 0;

        for (int f = 0; f < tbVentas.getRowCount(); f++) {
            Object objCantidad = tbVentas.getValueAt(f, 2);
            Object objPrecioUnitario = tbVentas.getValueAt(f, 3);

            if (objCantidad == null
                    || objPrecioUnitario == null) {
                return;
            }

            long cantidad = Long.parseLong(String.valueOf(objCantidad));
            String p = String.valueOf(objPrecioUnitario);
            p = p.replace(",", ".");
            p = p.replace(".", "");
            double precioUnitario = Double.parseDouble(p);

            if (ckIVA.isSelected()) {
                subTotal += precioUnitario * cantidad;
            }
            System.out.println(subTotal);
            System.out.println(formateador.format(subTotal) + "sub");

        }

//        textSubtotal.setText(String.format("%.2f", subTotal));
        textSubtotal.setText(formateador.format(subTotal));

        valorIva = subTotal * (iva / 100);
        System.out.println(valorIva);
        textIVA.setText(formateador.format(valorIva));

        facturaDTO.setIva(valorIva);

        totalPagar = subTotal + valorIva;
        System.out.println(totalPagar + "totaaaaaaal");

        facturaDTO.setTotalPagar(totalPagar);

        //   texTotalPagar.setText(String.format("%.2f", totalPagar));
        System.out.println("" + formateador.format(totalPagar));
        lblTotalPagar.setText("$" + formateador.format(totalPagar));

    }

    private void calcularTotales3() {

        double subTotal = 0;
        double iva = servicio.consultarImpuesto(1).getValor();
        double valorIva = 0;
        double totalPagar = 0;

        for (int f = 0; f < tbVentas1.getRowCount(); f++) {
            Object objCantidad = tbVentas1.getValueAt(f, 2);
            Object objPrecioUnitario = tbVentas1.getValueAt(f, 3);

            if (objCantidad == null
                    || objPrecioUnitario == null) {
                return;
            }
            tbVentas.setValueAt(objCantidad, f, 4);
            long cantidad = Long.parseLong(String.valueOf(objCantidad));
            String p = String.valueOf(objPrecioUnitario);
            p = p.replace(",", ".");
            p = p.replace(".", "");
            double precioUnitario = Double.parseDouble(p);
            Object check = tbVentas1.getValueAt(f, 15);
            boolean ch = new Boolean(String.valueOf(check));
            if (ch) {
                subTotal += precioUnitario * cantidad;
            }
            System.out.println(subTotal);
            System.out.println(formateador.format(subTotal) + "sub");

        }

//        textSubtotal.setText(String.format("%.2f", subTotal));
        textSubtotal1.setText(formateador.format(subTotal));

        valorIva = subTotal * (iva / 100);
        System.out.println(valorIva);
        textIVA1.setText(formateador.format(valorIva));

        facturaDTO.setIva(valorIva);

        totalPagar = subTotal + valorIva;
        System.out.println(totalPagar + "totaaaaaaal");

        facturaDTO.setTotalPagar(totalPagar);

        //   texTotalPagar.setText(String.format("%.2f", totalPagar));
        System.out.println("" + formateador.format(totalPagar));
        lblTotalPagar1.setText("$" + formateador.format(totalPagar));

    }
//calcula los totales de la tabla remisiones

    private void calcularTotales4() {

        double subTotal = 0;
        double iva = 0;
        double valorIva = 0;
        double totalPagar = 0;
        double totalAbonado = 0;

        for (int f = 0; f < tbVentas1.getRowCount(); f++) {
            Object objCantidad = tbVentas1.getValueAt(f, 4);
            Object objPrecioUnitario = tbVentas1.getValueAt(f, 5);
            Object objtotal = tbVentas1.getValueAt(f, 8);
            Object objIva = tbVentas1.getValueAt(f, 9);
//            Object objAbon = tbVentas1.getValueAt(f, 11);
            Object objpagar = tbVentas1.getValueAt(f, 8);

            if (objCantidad == null
                    || objPrecioUnitario == null) {
                return;
            }

            long cantidad = Long.parseLong(String.valueOf(objCantidad));
            String precio = String.valueOf(objPrecioUnitario);
            precio = precio.replace(",", ".");
            precio = precio.replace(".", "");
            double precioUnitario = Double.parseDouble(precio);
            String t = String.valueOf(objtotal);
            t = t.replace(",", ".");
            t = t.replace(".", "");
            double tot = Double.parseDouble(t);

            String iv = String.valueOf(objIva);
            iv = iv.replace(",", ".");
            iv = iv.replace(".", "");
            double ivaa = Double.parseDouble(iv);

            String pa = String.valueOf(objpagar);
            pa = pa.replace(",", ".");
            pa = pa.replace(".", "");
            double pagar = Double.parseDouble(pa);

//            String abonado = String.valueOf(objAbon);
//            double ab = Double.parseDouble(abonado);
            Object check = tbVentas1.getValueAt(f, 11);
            boolean ch = new Boolean(String.valueOf(check));
            if (ch) {
                subTotal += pagar;
                iva += ivaa;
//                totalAbonado += ab;
            }
        }

        textSubtotal1.setText(formateador.format(subTotal - iva));

//        valorIva = subTotal * (iva / 100);
        textIVA1.setText(formateador.format(iva));

        facturaDTO.setIva(iva);
        totalPagar = subTotal;

        facturaDTO.setTotalPagar(totalPagar);

        //   texTotalPagar.setText(String.format("%.2f", totalPagar));
        DecimalFormat formateador = new DecimalFormat("###,###.##");

        System.out.println("" + formateador.format(totalPagar));
        lblTotalPagar1.setText("$" + formateador.format(subTotal));
        texTotalPagado1.setText("$" + formateador.format(totalAbonado));

    }
    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseEntered

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved

    }//GEN-LAST:event_formMouseMoved

    private void btnCarteraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarteraActionPerformed
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);

        String archivo = c + "\\CuentasCobrar.jasper";
        String imagen = c + "\\logo.jpeg";
        Conexion cn = new Conexion();
        JasperReport jr = null;
        try {
            Map parametro = new HashMap();

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
    }//GEN-LAST:event_btnCarteraActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        actualizarTablaFacturas();
        txtTotalItems.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnEliminarImpuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarImpuestoActionPerformed
        if (panelImpuestos.isShowing()) {
            int filaSeleccionada = tbImpuestos.getSelectedRow();

            if (filaSeleccionada < 0) {
                mostrarMensaje("Seleccione un impuesto de la tabla");
                return;
            }

            if (!mostrarDecision("¿Desea eliminar este impuesto?")) {
                return;
            }

            Object idObj = tbImpuestos.getValueAt(filaSeleccionada, 0);

            if (idObj == null) {
                dtbmImpuestos.removeRow(filaSeleccionada);
                return;
            }

            long id = Long.parseLong(String.valueOf(idObj));

            if (servicio.eliminarImpuesto(id)) {
                actualizarTablaImpuestos();
                mostrarMensaje("Impuesto eliminado satisfactoriamente");
            } else {
                mostrarMensaje("No se pudo eliminar el impuesto");
            }
        }
    }//GEN-LAST:event_btnEliminarImpuestoActionPerformed

    private void btnNuevoImpuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoImpuestoActionPerformed
        dtbmImpuestos.addRow(new Object[]{});
    }//GEN-LAST:event_btnNuevoImpuestoActionPerformed

    private void btnNuevoPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoPagoActionPerformed
        dtbmPagos.addRow(new Object[]{});
    }//GEN-LAST:event_btnNuevoPagoActionPerformed

    private void btnEliminarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPagoActionPerformed
        String clave=JOptionPane.showInputDialog("Digite la clave",getKeyListeners());
        boolean exito=servicio.clave(clave);
            JPasswordField pwd = new JPasswordField(10);
    JOptionPane.showInputDialog(null,"Your password is "+new String(pwd.getPassword()));

        if(exito){
            JOptionPane.showMessageDialog(null,"clave correcta");
        }else{
                        JOptionPane.showMessageDialog(null,"clave incorrecta");

        }
        if(exito){
        if (panelPagos.isShowing()) {
            int filaSeleccionada = tbPagos.getSelectedRow();
            double total = 0;
            long id_r = 0;
            if (filaSeleccionada < 0) {
                mostrarMensaje("Seleccione un pago de la tabla");
                return;
            }

            if (!mostrarDecision("¿Desea eliminar este pago?")) {
                return;
            }

            Object idObj = tbPagos.getValueAt(filaSeleccionada, 0);
            Object factObj = tbPagos.getValueAt(filaSeleccionada, 2);
            Object abonoObj = tbPagos.getValueAt(filaSeleccionada, 3);
            String a = String.valueOf(abonoObj);
            a = a.replace(",", ".");
            a = a.replace(".", "");

            double abon = Double.parseDouble(a);

            ArrayList<RegistroDTO> regis = servicio.listarRegistro(Long.parseLong(String.valueOf(factObj)));

            if (idObj == null) {
                dtbmPagos.removeRow(filaSeleccionada);
                return;
            }

            long id = Long.parseLong(String.valueOf(idObj));
            System.out.println(id + "id");
            PagoDTO pago = servicio.consultarPago(id);
            System.out.println(pago);
            String mensaje = "No se pudo eliminar el pago";
            double totalPagado = 0;
            if (pago != null) {
                if (servicio.eliminarPago(id)) {
                    ArrayList<FacturaDTO> f = servicio.buscarFactura(Long.parseLong(String.valueOf(factObj)));
                    for (FacturaDTO fa : f) {
                        totalPagado = fa.getTotalPagado();
                    }
                    servicio.ActualizarPago(Long.parseLong(String.valueOf(factObj)), totalPagado - abon);
                    mensaje = "Abono eliminado";
                    actualizarTablaPagos();

                }
            }
            mostrarMensaje(mensaje);
        }
        }
    }//GEN-LAST:event_btnEliminarPagoActionPerformed

    private void btnNuevaFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaFacturaActionPerformed

        String estado = btnNuevaFactura.getText();

        if (estado.equalsIgnoreCase("Nueva factura")) {
            this.textNumeroFactura.setEditable(true);

            this.comboEstadoFactura.setEnabled(true);
            this.dateFechaEntrega.setEnabled(true);
            this.comboBuscarCliente.setEditable(true);
            this.comboBuscarCliente.setEnabled(true);
            this.comboBuscarProducto.setEditable(true);
            this.comboBuscarProducto.setEnabled(true);
            this.btnQuitarProducto.setEnabled(true);

            btnAnadirCliente.setEnabled(true);
            btnEditarCliente.setEnabled(true);
            this.tbVentas.setEnabled(true);
            textDocumento.setEditable(true);
            textNombres.setEditable(true);
            textApellidos.setEditable(true);
            textTelefono.setEditable(true);
            textDireccion.setEditable(true);
            jButton5.setVisible(true);
            jButton8.setVisible(true);
//            jButton7.setVisible(true);
            cambiarmensaje(evt);
        } else if (estado.equalsIgnoreCase("Cancelar")) {
            LimpiarTabla();
            btnAnadirCliente.setEnabled(false);
            this.textNumeroFactura.setEditable(false);

            this.comboEstadoFactura.setEnabled(false);
            this.dateFechaEntrega.setEnabled(false);
            this.comboBuscarCliente.setEditable(false);
            this.comboBuscarCliente.setEnabled(false);
            this.comboBuscarProducto.setEditable(false);
            this.comboBuscarProducto.setEnabled(false);
            this.btnQuitarProducto.setEnabled(false);

            btnEditarCliente.setEnabled(false);
            this.tbVentas.setEnabled(false);
            textDocumento.setEditable(false);
            textNombres.setEditable(false);
            textApellidos.setEditable(false);
            textTelefono.setEditable(false);
            textDireccion.setEditable(false);
            jButton8.setVisible(false);
            textNombres.setText("");
            textApellidos.setText("");
            lblTotalPagar.setText("");
            textCodigoCliente.setText("");

            textDireccion.setText("");

            textDocumento.setText("");
            textTelefono.setText("");
            textSubtotal.setText("");
            textIVA.setText("");
            dateFechaEntrega.setText("");
            comboBuscarCliente.removeAllItems();
            jButton5.setVisible(false);
            btnCobrar.setVisible(false);
//            jButton7.setVisible(false);

            cambiarmensaje(evt);
        }
    }//GEN-LAST:event_btnNuevaFacturaActionPerformed

    private void btnQuitarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarProductoActionPerformed

        int filaSeleccionada = tbVentas.getSelectedRow();
        Object objId = tbVentas.getValueAt(filaSeleccionada, 0);
        if (objId == null) {
            return;
        }
        long id = Long.parseLong(String.valueOf(objId));
        dtbmVentas.removeRow(filaSeleccionada);
        calcularTotales();
    }//GEN-LAST:event_btnQuitarProductoActionPerformed
    private void LimpiarTabla() {
        int filaSeleccionada = tbVentas.getRowCount();
        int count = 0;
        System.out.println("filas" + filaSeleccionada);
        if (filaSeleccionada > 0) {
            while (count < filaSeleccionada) {
                dtbmVentas.removeRow(0);
                System.out.println(count + "---------------");
                count++;
            }
        }
        tbVentas.setModel(dtbmVentas);

    }
    private void comboBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBuscarProductoActionPerformed

    }//GEN-LAST:event_comboBuscarProductoActionPerformed

    private void comboEstadoFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEstadoFacturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboEstadoFacturaActionPerformed

    private void comboFormaDePagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFormaDePagoActionPerformed
        String s = comboFormaDePago.getSelectedItem().toString();
        if (s.equalsIgnoreCase("CONTADO")) {

            texTotalPagado.setVisible(false);
            lbTotalPagado.setVisible(false);

        } else {

            texTotalPagado.setVisible(false);
            lbTotalPagado.setVisible(false);
        }
    }//GEN-LAST:event_comboFormaDePagoActionPerformed

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        String documento = textDocumento.getText();
        String nombre = textNombres.getText();
        String apellido = textApellidos.getText();
        String telefono = textTelefono.getText();
        String direccion = textDireccion.getText();
        String id = textCodigoCliente.getText();
        long codigo = Long.parseLong(id);

        if (nombre.equalsIgnoreCase("") | apellido.equalsIgnoreCase("")) {
            mostrarMensaje("NO PUEDE HABER NINGUN CAMPO VACIO");
        } else {
            boolean x = servicio.editarCliente(codigo, documento, nombre, apellido, direccion, telefono);
            if (x) {
                mostrarMensaje("SE EDITO");
            }
        }
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void btnAnadirClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirClienteActionPerformed
        String documento = textDocumento.getText();
        String nombre = textNombres.getText();
        String apellido = textApellidos.getText();
        String telefono = textTelefono.getText();
        String direccion = textDireccion.getText();

        if (nombre.equalsIgnoreCase("") | apellido.equalsIgnoreCase("")) {
            mostrarMensaje("POR FAVOR LLEVAR TODOS LOS CAMPOS");

        } else {
            comboBuscarCliente.removeAllItems();
            long x = servicio.registrarClien(documento, nombre, apellido, direccion, telefono);

            if (x > 0) {
                mostrarMensaje("SE REGISTRO CON EXITO");
                comboBuscarCliente.addItem(x);
            }
        }
        //
        //        escritorio = getDesktopPane();
        //        ifrmUsuarios registrarUsuario = new ifrmUsuarios(this);
        //        escritorio.add(registrarUsuario);
        //        registrarUsuario.setVisible(true);
        //
        //        registrarUsuario.setSize(391, 355);
    }//GEN-LAST:event_btnAnadirClienteActionPerformed

    private void btnAnadirClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnadirClienteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAnadirClienteMouseClicked

    private void comboBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBuscarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBuscarClienteActionPerformed

    private void textApellidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textApellidosActionPerformed
        textApellidos.setEditable(true);
    }//GEN-LAST:event_textApellidosActionPerformed

    private void textDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textDireccionActionPerformed
        textDireccion.setEditable(true);
    }//GEN-LAST:event_textDireccionActionPerformed

    private void textTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textTelefonoActionPerformed
        textTelefono.setEditable(true);
    }//GEN-LAST:event_textTelefonoActionPerformed

    private void textNombresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNombresActionPerformed
        textNombres.setEditable(true);
    }//GEN-LAST:event_textNombresActionPerformed

    private void textDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textDocumentoActionPerformed
        String cedula = textDocumento.getText().toString();
        ClienteDTO c = servicio.buscarClienteCedula(cedula);
        if (c != null) {
            textCodigoCliente.setText(c.getId() + "");
            textNombres.setText(c.getNombres() + "");
            textApellidos.setText(c.getApellidos() + "");
            textTelefono.setText(c.getTelefono());
            textDireccion.setText(c.getDireccion());
            llenarTablaVentasRegistro(c.getId());
            calcularSubTotal();

        }
    }//GEN-LAST:event_textDocumentoActionPerformed

    public static void reiniciarJTable(javax.swing.JTable Tabla) {
        DefaultTableModel modelo = (DefaultTableModel) Tabla.getModel();
        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }

        TableColumnModel modCol = Tabla.getColumnModel();
        while (modCol.getColumnCount() > 0) {
            modCol.removeColumn(modCol.getColumn(0));
        }
    }

    void limpiaTabla() {
        try {
            dtbmVentas = (DefaultTableModel) tbVentas.getModel();
            int a = dtbmVentas.getRowCount() - 1;
            for (int i = 0; i < a; i++) {
                dtbmVentas.removeRow(0); //aquí estaba el error, antes pasaba la i como parametro.... soy un bacín  XD
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private void btnCobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCobrarActionPerformed
        boolean exito=mostrarDecision("Desea registrar los registros");
        if(exito){
        registrarFactura2();
        cambiarmensaje(evt);
        btnAnadirCliente.setEnabled(false);
        this.textNumeroFactura.setEditable(false);
        jButton8.setVisible(false);

        this.comboEstadoFactura.setEnabled(false);
        this.dateFechaEntrega.setEnabled(false);
        this.comboBuscarCliente.setEditable(false);
        this.comboBuscarCliente.setEnabled(false);
        this.comboBuscarProducto.setEditable(false);
        this.comboBuscarProducto.setEnabled(false);
        this.btnQuitarProducto.setEnabled(false);

        btnEditarCliente.setEnabled(false);
        this.tbVentas.setEnabled(false);
        textDocumento.setEditable(false);
        textNombres.setEditable(false);
        textApellidos.setEditable(false);
        textTelefono.setEditable(false);
        textDireccion.setEditable(false);
        textNombres.setText("");
        textApellidos.setText("");
        lblTotalPagar.setText("");
        textCodigoCliente.setText("");

        textDireccion.setText("");

        textDocumento.setText("");
        textTelefono.setText("");
        textSubtotal.setText("");
        textIVA.setText("");
        dateFechaEntrega.setText("");
        comboBuscarCliente.removeAllItems();
        jButton5.setVisible(false);
        btnCobrar.setVisible(false);
        LimpiarTabla();
        }


    }//GEN-LAST:event_btnCobrarActionPerformed

    private void textCodigoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textCodigoClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textCodigoClienteActionPerformed

    private void btnCobrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCobrar1ActionPerformed

        registrarFacturaFa();
        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);
        cadena = servicio.consultarUltimaFactura() + "";
        System.out.println(cadena);

        // Object o = tblUsuario.getValueAt(0, 0);
        // String id = String.valueOf(o);
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
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
            jv.setTitle("FACTURA");
        } catch (JRException ex) {
            Logger.getLogger(ifrmReportes.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }//GEN-LAST:event_btnCobrar1ActionPerformed

    private void comboBuscarCliente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBuscarCliente1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBuscarCliente1ActionPerformed

    private void textSubtotal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSubtotal1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSubtotal1ActionPerformed

    private void textDocumento1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textDocumento1ActionPerformed
        String cedula = textDocumento1.getText().toString();
        ClienteDTO c = servicio.consultarCliente(Long.parseLong(cedula));
        if (c != null) {

            llenarTablaVentasRegistro(c.getId());

        }
    }//GEN-LAST:event_textDocumento1ActionPerformed

    private void texTotalPagado1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_texTotalPagado1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_texTotalPagado1ActionPerformed

    private void textNumeroFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNumeroFacturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textNumeroFacturaActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
//        String xas = JOptionPane.showInputDialog(null, "Digite el numero de factura");
        long usuario = 0;

        usuario = SESION_USUARIO.getId();
        String cedula = textDocumento1.getText();
        long cedu = Long.parseLong(cedula);
       // ClienteDTO cliente = servicio.consultarCliente(cedu);
        String iva = textIVA1.getText();
        String totalPa = lblTotalPagar1.getText();
        String totalPagado = texTotalPagado1.getText();
        totalPagado = totalPagado.replace("$", "");
        totalPagado = totalPagado.replace(",", ".");
        totalPagado = totalPagado.replace(".", "");
        iva = iva.replace(",", ".");
        iva = iva.replace(".", "");
        double iv = Double.parseDouble(iva);
        totalPa = totalPa.replace(",", ".");
        totalPa = totalPa.replace(".", "");
        totalPa = totalPa.replace("$", "");
        double totalpagar = Double.parseDouble(totalPa);
        int productos = 0;

        double totalpagado = Double.parseDouble(totalPagado);

        String forma = comboFormaDePago.getSelectedItem().toString();
        if (iv == 0) {
            if (servicio.registrarRemision(iv, totalpagado, totalpagar, cedu, usuario, forma)) {
                System.out.println("entro remision");
                long factura = servicio.consultarUltimaFactura();
                productos = registrarVentas2(factura);
            }
        } else {
            System.out.println("entrooooooooooooooooo");
            String xa = JOptionPane.showInputDialog(null, "Digite el numero de factura");
            if (servicio.registrarRemisionIva(iv, totalpagado, totalpagar, cedu, usuario, forma, xa)) {
                System.out.println("entro remision");
                long factura = servicio.consultarUltimaFactura();
                productos = registrarVentas2(factura);

            }
        }

        if (forma.equalsIgnoreCase("CREDITO")) {
            if (totalpagado != 0) {
                long factura = servicio.consultarUltimaFactura();
                servicio.registrarPago(totalpagado, factura);
            }
        }

        String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c = b.buscar(CARPETA);
        cadena = servicio.consultarUltimaFactura() + "";
        System.out.println(cadena);

        // Object o = tblUsuario.getValueAt(0, 0);
        // String id = String.valueOf(o);
        String imagen = "";
        String archivo = "";
        if (productos < 6) {
            imagen = c + "\\logo.jpeg";
            archivo = c + "\\Factura4.jasper";
            
        } else {
            imagen = c + "\\logo.jpeg";
            archivo = c + "\\Factura2.jasper";
         
        }
//        mostrarMensaje("Se esta generando la factura");
        textSubtotal1.setText("0");
        textIVA1.setText("0");
        lblTotalPagar1.setText("0");
        texTotalPagado1.setText("0");
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
        }     // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void ckIVA1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckIVA1ActionPerformed
        boolean c = false;
        c = ckIVA1.isSelected();
        if (c) {
            calcularTotales3();
        }
        if (!c) {
            calcularTotales4();
        }
    }//GEN-LAST:event_ckIVA1ActionPerformed

    private void ckIVA1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ckIVA1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ckIVA1MouseClicked

    private void textIVAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textIVAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textIVAActionPerformed

    private void comboBuscarCliente2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBuscarCliente2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBuscarCliente2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        llenarTablaPagos();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtCedulaPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaPagoActionPerformed

        String cedula = txtCedulaPago.getText();
        ClienteDTO cliente = servicio.buscarClienteCedula(cedula);
        llenarTablaPagosFiltro(cliente.getId());

    }//GEN-LAST:event_txtCedulaPagoActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        btnCobrar.setVisible(true);
        if (ckIVA.isSelected()) {
            System.out.println("Seleccionado 1");
            totalUnoIva();
            System.out.println("Seleccionado 1.1");
        } else {
            System.out.println("Seleccionado 2");
            totalUno();
            System.out.println("Mostrar Boton de guardar");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void ckIVAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ckIVAMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ckIVAMouseClicked

    private void ckIVAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckIVAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ckIVAActionPerformed

    private void tbVentasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbVentasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbVentasKeyPressed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int x = tbVentas1.getSelectedRow();
        double precio = 0;
        double ivaa = 0;

        Object objId = tbVentas1.getValueAt(x, 0);
        long id = Long.parseLong(String.valueOf(objId));
        Object objPrecio = tbVentas1.getValueAt(x, 5);
        Object objIva = tbVentas1.getValueAt(x, 9);
        Object objCantidad = tbVentas1.getValueAt(x, 4);

        long cantidad = Long.parseLong(String.valueOf(objCantidad));
        String p = String.valueOf(objPrecio);
        p = p.replace(",", ".");
        p = p.replace(".", "");
        String i = String.valueOf(objIva);
        i = i.replace(",", ".");
        i = i.replace(".", "");
        double pre = Double.parseDouble(p);
        ivaa = Double.parseDouble(i);
        boolean xa = servicio.editarPrecio(id, pre, (pre * cantidad), 0, cantidad);
        if (xa) {
            mostrarMensaje("Iva borrado");
        }
        Object NUmeroRemision = tbVentas1.getValueAt(x, 10);
        long idRemision = Long.parseLong(String.valueOf(NUmeroRemision));

        ArrayList<FacturaDTO> f = servicio.buscarFactura(idRemision);

        double total = 0;
        double is = 0;
//        ArrayList<RegistroDTO> regi = servicio.listar(idRemision);
//        for (RegistroDTO re : regi) {
//            total = total + re.getTotal_pagar();
//            is = is + re.getIva();
//        }

        boolean editarRemision = servicio.editarRemision(idRemision, total, is);


    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        String texto = jComboBox1.getSelectedItem().toString();
        ArrayList<MaterialDTO> m = servicio.buscar(texto);
        for (MaterialDTO producto : m) {
            System.out.println(producto.getId()+producto.getPatron()+"Materia Escogido");
            dtbmVentas.addRow(new Object[]{producto.getId(), producto, 1, 0, "", 0, 0});
        }

    }//GEN-LAST:event_jButton8ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
//        if (jComboBox2 != null) {
//            
//            String string = jComboBox2.getSelectedItem().toString();
//
//            if (!string.equalsIgnoreCase("")) {
//                String[] parts = string.split("/");
//                String part1 = parts[0]; // 123
//                String part2 = parts[1];
//
//                ClienteDTO cliente = servicio.consultarCliente(Long.parseLong(part2));
//                if (cliente != null) {
//                    textCodigoCliente.setText(cliente.getId() + "");
//                    double debe = servicio.consultaTotalPagarPagado(cliente.getId());
//                    ArrayList<RegistroDTO> r = servicio.listarVentasCliente(cliente.getId());
//                    String palabra = "";
//                    for (RegistroDTO re : r) {
//                        if (re.getEstado().equalsIgnoreCase("DEPOSITO") || re.getEstado().equalsIgnoreCase("PROCESO")) {
//                            palabra = palabra + "Numero Item:" + re.getId() + "Estado:" + re.getEstado() + " || ";
//                        }
//                    }
//                    if (debe > 0) {
//                        mostrarMensaje("El cliente debe" + "   " + "$" + formateador.format(debe));
//                    }
//                    String document = "";
//                    String direcc = "";
//                    if (cliente.getDocumento().equalsIgnoreCase("null")) {
//                        document = "";
//                    }
//                    if (cliente.getDireccion().equalsIgnoreCase("null")) {
//                        direcc = "";
//                    }
//                    if (!cliente.getDocumento().equalsIgnoreCase("null")) {
//                        document = cliente.getDocumento();
//                    }
//                    if (!cliente.getDireccion().equalsIgnoreCase("null")) {
//                        direcc = cliente.getDireccion();
//                    }
//                    textDocumento.setText(document);
//                    textNombres.setText(cliente.getNombres());
//                    textApellidos.setText(cliente.getApellidos());
//                    textTelefono.setText(cliente.getTelefono() + "");
//                    textDireccion.setText(direcc);
//                }
//            }
//        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (jComboBox2 != null) {
            String string = jComboBox2.getSelectedItem().toString();

            if (!string.equalsIgnoreCase("")) {
                String[] parts = string.split("/");
                String part1 = parts[0]; // 123
                String part2 = parts[1];

                ClienteDTO cliente = servicio.consultarCliendeDeuda(Long.parseLong(part2));
                if (cliente != null) {
                    //textCodigoCliente.setText(cliente.getId() + "");
                   // double debe = servicio.consultaTotalPagarPagado(cliente.getId());
                    //ArrayList<RegistroDTO> r = servicio.listarVentasCliente(cliente.getId());
                    //String palabra = "";
                    //for (RegistroDTO re : r) {
                      //  if (re.getEstado().equalsIgnoreCase("DEPOSITO") || re.getEstado().equalsIgnoreCase("PROCESO")) {
                       //     palabra = palabra + "Numero Item:" + re.getId() + "Estado:" + re.getEstado() + " || ";
                        //}
                    //}
                    if (cliente.getDebe() > 0) {
                        mostrarMensaje("El cliente debe" + "   " + "$" + formateador.format(cliente.getDebe()));
                    }
                    String document = "";
                    String direcc = "";
                    if (cliente.getDocumento().equalsIgnoreCase("null")) {
                        document = "";
                    }
                    if (cliente.getDireccion().equalsIgnoreCase("null")) {
                        direcc = "";
                    }
                    if (!cliente.getDocumento().equalsIgnoreCase("null")) {
                        document = cliente.getDocumento();
                    }
                    if (!cliente.getDireccion().equalsIgnoreCase("null")) {
                        direcc = cliente.getDireccion();
                    }
                    textCodigoCliente.setText(cliente.getId()+"");
                    textDocumento.setText(document);
                    textNombres.setText(cliente.getNombres());
                    textApellidos.setText(cliente.getApellidos());
                    textTelefono.setText(cliente.getTelefono() + "");
                    textDireccion.setText(direcc);
                }
            }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        llenarComboClientesventas();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if (jComboBox3 != null) {
            String string = jComboBox3.getSelectedItem().toString();

            if (!string.equalsIgnoreCase("")) {
                String[] parts = string.split("/");
                String part1 = parts[0]; // 123
                String part2 = parts[1];

                //ClienteDTO cliente = servicio.consultarCliente(Long.parseLong(part2));

                textDocumento1.setText(Long.parseLong(part2) + "");
                llenarTablaVentasRegistro(Long.parseLong(part2));
                double debe = servicio.consultaTotalPagarPagado(Long.parseLong(part2));
                txtDebeRemision.setText(formateador.format(debe));

               // System.out.println(cliente.getId() + "codigo");
                codigo = Long.parseLong(part2);
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        llenarComboClientesventas2();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        String string = jComboBox5.getSelectedItem().toString();
        String[] parts = string.split("/");
        String part1 = parts[0]; // 123
        String part2 = parts[1];
        //ClienteDTO cliente = servicio.consultarCliente(Long.parseLong(part2));
        llenarTablaPagosFiltros(Long.parseLong(part2));

    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        llenarComboClientesAbonos();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String estado = cmbEstado.getSelectedItem().toString();

        if (estado.equalsIgnoreCase("-")) {
            JOptionPane.showMessageDialog(null, "Seleccione estado");
        } else {
//            buscarEstadoFacturas(estado);
            llenarTablaFacturas2(estado);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnAceptarFecha1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarFecha1ActionPerformed
        Date FechaEditada = Date.valueOf(dateFechaEditada1.getDateStringOrSuppliedString(LocalDate.now().toString()));

        int filaSeleccionada = tbVentas1.getSelectedRow();

        System.out.println(filaSeleccionada);
        if (filaSeleccionada < 0) {
            mostrarMensaje("Seleccione un registro de la tabla");
            return;
        }

        Object idObj = tbVentas1.getValueAt(filaSeleccionada, 0);

        long id = Long.parseLong(String.valueOf(idObj));
        boolean x = servicio.editarFechaRegistro(id, FechaEditada);
        if (x) {
            mostrarMensaje("Fecha Editada Con Exito");
            dateFechaEditada1.setVisible(false);
            btnAceptarFecha1.setVisible(false);
            actualizarTablaFacturas();
        } else {
            mostrarMensaje("No se pudo actualizar la fecha de entrega");
        }
    }//GEN-LAST:event_btnAceptarFecha1ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        dateFechaEditada1.setVisible(true);
        dateFechaEditada1.setEnabled(true);
        btnAceptarFecha1.setVisible(true);
    }//GEN-LAST:event_jButton12ActionPerformed

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
//            buscarFechaFacturas(fechaDesde, fechaHasta);
            // buscarFechaFactura(fechaDesde);
            llenarTablaFacturasFechas(fechaDesde, fechaHasta);

        }

    }//GEN-LAST:event_btnBuscarFechaActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        int x = tbVentas1.getSelectedRow();
        Object objId = tbVentas1.getValueAt(x, 0);
        Object NUmeroRemision = tbVentas1.getValueAt(x, 10);
        Object pagar = tbVentas1.getValueAt(x, 8);
        Object iv = tbVentas1.getValueAt(x, 9);
        String p = String.valueOf(pagar);
        p = p.replace(",", ".");
        p = p.replace(".", "");

        String ivaa = String.valueOf(iv);
        ivaa = ivaa.replace(",", ".");
        ivaa = ivaa.replace(".", "");
        long id = Long.parseLong(String.valueOf(objId));
        long idRemision = Long.parseLong(String.valueOf(NUmeroRemision));

        ArrayList<FacturaDTO> f = servicio.buscarFactura(idRemision);
        double iva = Double.parseDouble(ivaa);
        double totalPagar = Double.parseDouble(p);
        double total = 0;
        double i = 0;
        for (FacturaDTO fa : f) {
            total = fa.getTotalPagar();
            i = fa.getIva();
        }

        boolean xa = servicio.editarRemision(idRemision, total - totalPagar, i - iva);
        boolean a = false;
        if (xa) {
            mostrarMensaje("La remision se ha actualizado");
        }
        a = servicio.eliminarRegistro(id);
        if (a) {
            mostrarMensaje("El item se ha eliminado");
        }


    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        double ivaas = servicio.consultarImpuesto(1).getValor();
        int x = tbVentas1.getSelectedRow();
        long cantidad = 0;
        Object objId = tbVentas1.getValueAt(x, 0);

        Object objCantidad = tbVentas1.getValueAt(x, 4);
        Object objmaterial = tbVentas1.getValueAt(x, 3);
        String material = String.valueOf(objmaterial);
//                int cantidad=Integer.parseInt(String.valueOf(objCantidad));
        Object objPrecio = tbVentas1.getValueAt(x, 5);
        Object objIva = tbVentas1.getValueAt(x, 9);
        Object objComentario = tbVentas1.getValueAt(x, 6);
        String precio = String.valueOf(objPrecio);
        String iva = String.valueOf(objIva);
        precio = precio.replace(",", ".");
        precio = precio.replace(".", "");

        iva = iva.replace(",", ".");
        iva = iva.replace(".", "");

        String comen = String.valueOf(objComentario);
        double pre = Double.parseDouble(precio);
        double iv = Double.parseDouble(iva);
        double ivaa = 0;
        double ivaSistema = servicio.consultarImpuesto(1).getValor();

        cantidad = Long.parseLong(String.valueOf(objCantidad));

        ivaa = (pre * cantidad) * (ivaSistema / 100);

        if (objCantidad == null) {
            return;
        }

        int s = tbVentas1.getSelectedRow();
        if (objId != null) {
            String esta = "";
            long p = Long.parseLong(String.valueOf(tbVentas1.getValueAt(s, 0)));
            esta = String.valueOf(tbVentas1.getValueAt(s, 7));
            servicio.cambiarEstado(p, esta);
            MaterialDTO mate = servicio.consultarMaterialNombre(material);
            servicio.Material(p, mate.getId());
            servicio.editarPrecio(p, pre, (pre * cantidad) + ivaa, ivaa, cantidad);
            
            Object NUmeroRemision = tbVentas1.getValueAt(x, 10);
                    long idRemision = Long.parseLong(String.valueOf(NUmeroRemision));

                    ArrayList<FacturaDTO> f = servicio.buscarFactura(idRemision);
                  
                    double total = 0;
                    double is = 0;
//                    ArrayList<RegistroDTO>regi =servicio.listar(idRemision);
//                    for (RegistroDTO re : regi) {
//                        total=total+re.getTotal_pagar();
//                        is=is+re.getIva();
//                    }

                    boolean xa = servicio.editarRemision(idRemision, total , is );
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tpPestaniaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tpPestaniaMouseClicked
    //llenarTablaPagos(); 
    //Llenar tabla de pagos
        System.out.println("Bolean"+exitoItems);
    int text= tpPestania.getSelectedIndex();
        System.out.println(text+"numero Pestaña");
        if(text==1){
          llenarComboClientesAbonos();
          llenarTablaPagos();
        }else 
        if(text==2){
            llenarTablaImpuestos();
        }else 
        if(text==3&&!exitoItems){
            llenarTablaFacturas();
            exitoItems=true;
        }
        if(text==4){
            llenarComboClientesventas2();
           
        }
    }//GEN-LAST:event_tpPestaniaMouseClicked

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
        System.out.println("ClickComboMateriales");
    }//GEN-LAST:event_jComboBox1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptarFecha1;
    private javax.swing.JButton btnAnadirCliente;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarFecha;
    private javax.swing.JButton btnCartera;
    private javax.swing.JButton btnCobrar;
    private javax.swing.JButton btnCobrar1;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEliminarImpuesto;
    private javax.swing.JButton btnEliminarPago;
    private javax.swing.JButton btnNuevaFactura;
    private javax.swing.JButton btnNuevoImpuesto;
    private javax.swing.JButton btnNuevoPago;
    private javax.swing.JButton btnQuitarProducto;
    private javax.swing.JCheckBox ckIVA;
    private javax.swing.JCheckBox ckIVA1;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JComboBox<Object> comboBuscarCliente;
    private javax.swing.JComboBox<Object> comboBuscarCliente1;
    private javax.swing.JComboBox<Object> comboBuscarCliente2;
    private javax.swing.JComboBox<Object> comboBuscarProducto;
    private javax.swing.JComboBox<Object> comboEstadoFactura;
    private javax.swing.JComboBox<Object> comboFormaDePago;
    private com.github.lgooddatepicker.components.DatePicker dateFechaEditada1;
    private com.github.lgooddatepicker.components.DatePicker dateFechaEntrega;
    private com.github.lgooddatepicker.components.DatePicker dpDesde;
    private com.github.lgooddatepicker.components.DatePicker dpHasta;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbApellidos;
    private javax.swing.JLabel lbBuscarCliente;
    private javax.swing.JLabel lbBuscarCliente1;
    private javax.swing.JLabel lbBuscarCliente2;
    private javax.swing.JLabel lbBuscarCliente3;
    private javax.swing.JLabel lbDireccion;
    private javax.swing.JLabel lbDocumento;
    private javax.swing.JLabel lbDocumento1;
    private javax.swing.JLabel lbDocumento2;
    private javax.swing.JLabel lbDocumento3;
    private javax.swing.JLabel lbEstadoFactura;
    private javax.swing.JLabel lbFormaDePago;
    private javax.swing.JLabel lbFormaDePago2;
    private javax.swing.JLabel lbIVA;
    private javax.swing.JLabel lbIVA1;
    private javax.swing.JLabel lbNombres;
    private javax.swing.JLabel lbSubtotal;
    private javax.swing.JLabel lbSubtotal1;
    private javax.swing.JLabel lbTelefono;
    private javax.swing.JLabel lbTotalPagado;
    private javax.swing.JLabel lbTotalPagado1;
    private javax.swing.JLabel lbTotalPagado2;
    private javax.swing.JLabel lblNumeroFactura1;
    private javax.swing.JLabel lblTotalPagar;
    private javax.swing.JLabel lblTotalPagar1;
    private javax.swing.JPanel panelCliente;
    private javax.swing.JPanel panelFactura;
    private javax.swing.JPanel panelFactura1;
    private javax.swing.JPanel panelFacturas;
    private javax.swing.JPanel panelImpuestos;
    private javax.swing.JPanel panelPagos;
    private javax.swing.JPanel panelVentaProductos;
    private javax.swing.JPanel panelVentaProductos1;
    private javax.swing.JPanel panelVentas;
    private javax.swing.JPanel panelVentas1;
    private javax.swing.JScrollPane spImpuestos;
    private javax.swing.JScrollPane spPagos;
    private javax.swing.JScrollPane spVentaProductos;
    private javax.swing.JScrollPane spVentaProductos1;
    private javax.swing.JTable tbFacturas;
    private javax.swing.JTable tbImpuestos;
    private javax.swing.JTable tbPagos;
    private javax.swing.JTable tbVentas;
    private javax.swing.JTable tbVentas1;
    private javax.swing.JTextField texTotalPagado;
    private javax.swing.JTextField texTotalPagado1;
    private javax.swing.JTextField textApellidos;
    private javax.swing.JTextField textCodigoCliente;
    private javax.swing.JTextField textDireccion;
    private javax.swing.JTextField textDocumento;
    private javax.swing.JTextField textDocumento1;
    private javax.swing.JTextField textIVA;
    private javax.swing.JTextField textIVA1;
    private javax.swing.JTextField textNombres;
    private javax.swing.JTextField textNumeroFactura;
    private javax.swing.JTextField textNumeroFactura1;
    private javax.swing.JTextField textSubtotal;
    private javax.swing.JTextField textSubtotal1;
    private javax.swing.JTextField textTelefono;
    private javax.swing.JTabbedPane tpPestania;
    private javax.swing.JTextField txtCedulaPago;
    private javax.swing.JTextField txtDebeRemision;
    private javax.swing.JTextField txtTotalItems;
    // End of variables declaration//GEN-END:variables
}
