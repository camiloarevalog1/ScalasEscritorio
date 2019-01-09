/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.view;

import com.zapateria.main.dto.ClienteDTO;
import com.zapateria.main.dto.FacturaDTO;
import com.zapateria.main.dto.ImpuestoDTO;
import com.zapateria.main.dto.PagoDTO;
import com.zapateria.main.dto.ProductoDTO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Diego
 */
public class ifrmVentas extends MarcoInternoPersonalizado {

    private static final long serialVersionUID = -5960080089952460575L;

    private static final String VENTAS = "VENTAS", PRODUCTOS = "PRODUCTOS",
            FACTURAS = "FACTURAS", PAGOS = "PAGOS", IMPUESTOS = "IMPUESTOS";

    private FacturaDTO facturaDTO = new FacturaDTO();

    private DefaultTableModel dtbmProductos, dtbmImpuestos, dtbmFacturas,
            dtbmPagos, dtbmVentas;

    private DefaultComboBoxModel dComboProductos;

    private DefaultListModel<Object> dlmProductos = new DefaultListModel<>(), dlmClientes = new DefaultListModel<>();
    private JDesktopPane escritorio;

    /**
     * Creates new form ifrmVentas
     */
    public ifrmVentas() {

        initComponents();
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

        lblNumeroCuotas.setVisible(false);
        txtCuotas.setText("");
        txtCuotas.setVisible(false);

        //this.btnNuevaFactura.setText("Nueva factura");
    }

    public void cambiarmensaje(ActionEvent evt) {
        String m = evt.getActionCommand();
        if (m.equalsIgnoreCase("Nueva factura")) {
            this.btnNuevaFactura.setText("Cancelar");
        } else {
            this.btnNuevaFactura.setText("Nueva factura");
        }

    }

    private void buscarProductos(String texto) {
        // comboBuscarProducto.removeAllItems();
        servicio.buscarProducto(texto).forEach((producto) -> {
            dComboProductos.addElement(producto);
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
                        editarCombo(combo, servicio.buscarProducto(texto).toArray());
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

                        ProductoDTO producto = null;

                        producto = (ProductoDTO) seleccion;

                        agregarProductoAFactura(producto);

                        calcularTotales();
                        //mostrarMensaje(producto.getPrecioUnitario()+"");
                        break;
                    case "CLIENTES":

                        ClienteDTO cliente = (ClienteDTO) seleccion;
                        textCodigoCliente.setText(cliente.getId() + "");
                        textDocumento.setText(cliente.getDocumento() + "");
                        textNombres.setText(cliente.getNombres());
                        textApellidos.setText(cliente.getApellidos());
                        textTelefono.setText(cliente.getTelefono() + "");
                        textDireccion.setText(cliente.getDireccion());
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        tpPestania = new javax.swing.JTabbedPane();
        panelVentas = new javax.swing.JPanel();
        panelVentaProductos = new javax.swing.JPanel();
        spVentaProductos = new javax.swing.JScrollPane();
        tbVentas = new TablaPersonalizada();
        jPanel1 = new javax.swing.JPanel();
        texTotalPagado = new javax.swing.JTextField();
        lbTotalPagado = new javax.swing.JLabel();
        lbIVA = new javax.swing.JLabel();
        lbSubtotal = new javax.swing.JLabel();
        textSubtotal = new javax.swing.JTextField();
        textIVA = new javax.swing.JTextField();
        btnCobrar = new javax.swing.JButton();
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
        jPanel2 = new javax.swing.JPanel();
        lblTotalPagar = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        panelFactura = new javax.swing.JPanel();
        lblNumeroFactura = new javax.swing.JLabel();
        textNumeroFactura = new javax.swing.JTextField();
        lbFormaDePago = new javax.swing.JLabel();
        comboFormaDePago = new javax.swing.JComboBox<>();
        lbFormaDePago2 = new javax.swing.JLabel();
        dateFechaEntrega = new com.github.lgooddatepicker.components.DatePicker();
        lbEstadoFactura = new javax.swing.JLabel();
        comboEstadoFactura = new javax.swing.JComboBox<>();
        lblNumeroCuotas = new javax.swing.JLabel();
        comboBuscarFactura = new javax.swing.JComboBox<>();
        lbBuscarFactura1 = new javax.swing.JLabel();
        txtCuotas = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        lbBuscarCliente1 = new javax.swing.JLabel();
        comboBuscarProducto = new javax.swing.JComboBox<>();
        btnQuitarProducto = new javax.swing.JButton();
        btnNuevaFactura = new javax.swing.JButton();
        panelPagos = new javax.swing.JPanel();
        spPagos = new javax.swing.JScrollPane();
        tbPagos = new TablaPersonalizada();
        btnEliminarPago = new javax.swing.JButton();
        btnNuevoPago = new javax.swing.JButton();
        panelImpuestos = new javax.swing.JPanel();
        spImpuestos = new javax.swing.JScrollPane();
        tbImpuestos = new TablaPersonalizada();
        btnNuevoImpuesto = new javax.swing.JButton();
        btnEliminarImpuesto = new javax.swing.JButton();
        panelProductos = new javax.swing.JPanel();
        spProductos = new javax.swing.JScrollPane();
        tbProductos = new TablaPersonalizada();
        btnNuevoProducto = new javax.swing.JButton();
        btnEliminarProducto = new javax.swing.JButton();
        panelFacturas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbFacturas = new TablaPersonalizada();
        jLabel1 = new javax.swing.JLabel();
        btnVerProductosFactura = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Ventas");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/icon2.png"))); // NOI18N
        setMaximumSize(new java.awt.Dimension(1920, 1080));
        setMinimumSize(new java.awt.Dimension(997, 643));
        setPreferredSize(new java.awt.Dimension(997, 643));
        setRequestFocusEnabled(false);
        setToolTipText("");
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
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panelVentas.setBackground(new java.awt.Color(255, 255, 255));
        panelVentas.setMinimumSize(new java.awt.Dimension(300, 300));
        panelVentas.setPreferredSize(new java.awt.Dimension(1600, 400));
        panelVentas.setLayout(new java.awt.GridBagLayout());

        panelVentaProductos.setBackground(new java.awt.Color(255, 255, 255));
        panelVentaProductos.setBorder(javax.swing.BorderFactory.createTitledBorder("Productos"));
        panelVentaProductos.setMaximumSize(new java.awt.Dimension(2000, 2000));
        panelVentaProductos.setMinimumSize(new java.awt.Dimension(100, 100));
        panelVentaProductos.setPreferredSize(new java.awt.Dimension(100, 100));
        panelVentaProductos.setLayout(new java.awt.GridLayout(1, 0));

        spVentaProductos.setPreferredSize(new java.awt.Dimension(100, 100));

        tbVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Producto", "Cantidad", "Precio unitario", "Comentario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbVentas.setEnabled(false);
        tbVentas.setMaximumSize(new java.awt.Dimension(2000, 2000));
        tbVentas.setOpaque(false);
        tbVentas.setSelectionBackground(new java.awt.Color(153, 153, 153));
        spVentaProductos.setViewportView(tbVentas);

        panelVentaProductos.add(spVentaProductos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 373;
        gridBagConstraints.ipady = 226;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 40, 63, 0);
        panelVentas.add(panelVentaProductos, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        texTotalPagado.setText("0");
        texTotalPagado.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 290;
        gridBagConstraints.ipady = -5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 26, 15, 31);
        jPanel1.add(texTotalPagado, gridBagConstraints);

        lbTotalPagado.setText("Total pagado:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 26, 0, 0);
        jPanel1.add(lbTotalPagado, gridBagConstraints);

        lbIVA.setText("Iva al 19%:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 36, 0, 0);
        jPanel1.add(lbIVA, gridBagConstraints);

        lbSubtotal.setText("Subtotal:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(23, 36, 0, 0);
        jPanel1.add(lbSubtotal, gridBagConstraints);

        textSubtotal.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 119;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 0, 0);
        jPanel1.add(textSubtotal, gridBagConstraints);

        textIVA.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 4, 0, 0);
        jPanel1.add(textIVA, gridBagConstraints);

        btnCobrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/guardar.png"))); // NOI18N
        btnCobrar.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        btnCobrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCobrarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.ipady = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 18, 0, 31);
        jPanel1.add(btnCobrar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = -45;
        gridBagConstraints.ipady = 91;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 40);
        panelVentas.add(jPanel1, gridBagConstraints);

        panelCliente.setBackground(new java.awt.Color(255, 255, 255));
        panelCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));
        panelCliente.setLayout(new java.awt.GridBagLayout());

        textDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textDocumentoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 196;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 0, 0);
        panelCliente.add(textDocumento, gridBagConstraints);

        lbDocumento.setText("Documento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 19, 0, 0);
        panelCliente.add(lbDocumento, gridBagConstraints);

        lbNombres.setText("Nombres:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 31, 0, 0);
        panelCliente.add(lbNombres, gridBagConstraints);

        textNombres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textNombresActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 196;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 0, 0);
        panelCliente.add(textNombres, gridBagConstraints);

        lbTelefono.setText("Teléfono:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 31, 0, 0);
        panelCliente.add(lbTelefono, gridBagConstraints);

        textTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textTelefonoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 196;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 0, 0);
        panelCliente.add(textTelefono, gridBagConstraints);

        textDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textDireccionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 196;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 10, 7, 0);
        panelCliente.add(textDireccion, gridBagConstraints);

        lbDireccion.setText("Dirección:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 30, 0, 0);
        panelCliente.add(lbDireccion, gridBagConstraints);

        lbApellidos.setText("Apellidos:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 30, 0, 0);
        panelCliente.add(lbApellidos, gridBagConstraints);

        textApellidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textApellidosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.ipadx = 196;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 0, 0);
        panelCliente.add(textApellidos, gridBagConstraints);

        lbDocumento1.setText("Cod. cliente:");
        lbDocumento1.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 16, 0, 0);
        panelCliente.add(lbDocumento1, gridBagConstraints);

        textCodigoCliente.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 196;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 0);
        panelCliente.add(textCodigoCliente, gridBagConstraints);

        comboBuscarCliente.setEnabled(false);
        comboBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBuscarClienteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 174;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(32, 10, 0, 0);
        panelCliente.add(comboBuscarCliente, gridBagConstraints);

        lbBuscarCliente.setText("Buscar cliente:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(32, 7, 0, 0);
        panelCliente.add(lbBuscarCliente, gridBagConstraints);

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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.ipadx = -31;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(32, 6, 0, 18);
        panelCliente.add(btnAnadirCliente, gridBagConstraints);

        btnEditarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/editarCli.png"))); // NOI18N
        btnEditarCliente.setBackground(new java.awt.Color(0, 0, 204));
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.ipadx = -31;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 18);
        panelCliente.add(btnEditarCliente, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        panelVentas.add(panelCliente, gridBagConstraints);

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
                .addComponent(lblTotalPagar, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 706;
        gridBagConstraints.ipady = -20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 63, 40);
        panelVentas.add(jPanel2, gridBagConstraints);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        panelFactura.setBackground(new java.awt.Color(255, 255, 255));
        panelFactura.setBorder(javax.swing.BorderFactory.createTitledBorder("Factura"));
        panelFactura.setPreferredSize(new java.awt.Dimension(898, 200));
        panelFactura.setLayout(new java.awt.GridBagLayout());

        lblNumeroFactura.setText("N° Factura");
        lblNumeroFactura.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        panelFactura.add(lblNumeroFactura, gridBagConstraints);

        textNumeroFactura.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 147;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 0, 0);
        panelFactura.add(textNumeroFactura, gridBagConstraints);

        lbFormaDePago.setText("Forma de pago:");
        lbFormaDePago.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbFormaDePago.setMaximumSize(new java.awt.Dimension(74, 17));
        lbFormaDePago.setMinimumSize(new java.awt.Dimension(74, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = -2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 4, 0, 0);
        panelFactura.add(lbFormaDePago, gridBagConstraints);

        comboFormaDePago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CONTADO", "CUOTAS" }));
        comboFormaDePago.setEnabled(false);
        comboFormaDePago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFormaDePagoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 78;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        panelFactura.add(comboFormaDePago, gridBagConstraints);

        lbFormaDePago2.setText("Fecha de entrega:");
        lbFormaDePago2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 11, 0);
        panelFactura.add(lbFormaDePago2, gridBagConstraints);

        dateFechaEntrega.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 2, 11, 0);
        panelFactura.add(dateFechaEntrega, gridBagConstraints);

        lbEstadoFactura.setText("Estado:");
        lbEstadoFactura.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 11, 0);
        panelFactura.add(lbEstadoFactura, gridBagConstraints);

        comboEstadoFactura.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DEPOSITO", "ENTREGADO", "PROCESO" }));
        comboEstadoFactura.setEnabled(false);
        comboEstadoFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEstadoFacturaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 67;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 11, 0);
        panelFactura.add(comboEstadoFactura, gridBagConstraints);

        lblNumeroCuotas.setText("N. Cuotas");
        lblNumeroCuotas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipady = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 4, 0, 0);
        panelFactura.add(lblNumeroCuotas, gridBagConstraints);

        comboBuscarFactura.setEditable(true);
        comboBuscarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBuscarFacturaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(27, 10, 0, 270);
        panelFactura.add(comboBuscarFactura, gridBagConstraints);

        lbBuscarFactura1.setText("Buscar Factura:");
        lbBuscarFactura1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipady = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(27, 4, 0, 0);
        panelFactura.add(lbBuscarFactura1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        panelFactura.add(txtCuotas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipady = -25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(panelFactura, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        lbBuscarCliente1.setText("Buscar y agregar producto:");
        lbBuscarCliente1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbBuscarCliente1.setForeground(new java.awt.Color(102, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 170;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 0, 0, 0);
        jPanel3.add(lbBuscarCliente1, gridBagConstraints);

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

        btnQuitarProducto.setText("Quitar");
        btnQuitarProducto.setEnabled(false);
        btnQuitarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarProductoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 37;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 45, 6, 135);
        jPanel3.add(btnQuitarProducto, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = -217;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 10, 35, 0);
        jPanel4.add(jPanel3, gridBagConstraints);

        btnNuevaFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/agregarfac2.png"))); // NOI18N
        btnNuevaFactura.setText("Nueva factura");
        btnNuevaFactura.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNuevaFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaFacturaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 35, 0);
        jPanel4.add(btnNuevaFactura, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 40);
        panelVentas.add(jPanel4, gridBagConstraints);

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

        javax.swing.GroupLayout panelPagosLayout = new javax.swing.GroupLayout(panelPagos);
        panelPagos.setLayout(panelPagosLayout);
        panelPagosLayout.setHorizontalGroup(
            panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spPagos, javax.swing.GroupLayout.DEFAULT_SIZE, 1052, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addGroup(panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminarPago, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoPago, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
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
                        .addGap(0, 424, Short.MAX_VALUE))
                    .addComponent(spPagos))
                .addGap(10, 10, 10))
        );

        if (SESION_USUARIO.isVistaPermitida(PAGOS)) {

            tpPestania.addTab("Pagos", panelPagos);
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
                .addComponent(spImpuestos, javax.swing.GroupLayout.DEFAULT_SIZE, 1025, Short.MAX_VALUE)
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
                    .addComponent(spImpuestos, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        if (SESION_USUARIO.isVistaPermitida(IMPUESTOS)) {

            tpPestania.addTab("Impuestos", panelImpuestos);
        }

        panelProductos.setBackground(new java.awt.Color(255, 255, 255));

        tbProductos.setModel(new javax.swing.table.DefaultTableModel(
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
        tbProductos.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        spProductos.setViewportView(tbProductos);

        btnNuevoProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/agregar.png"))); // NOI18N
        btnNuevoProducto.setText("Producto");
        btnNuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProductoActionPerformed(evt);
            }
        });

        btnEliminarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Eliminar.png"))); // NOI18N
        btnEliminarProducto.setText("Producto");
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelProductosLayout = new javax.swing.GroupLayout(panelProductos);
        panelProductos.setLayout(panelProductosLayout);
        panelProductosLayout.setHorizontalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(spProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 1037, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEliminarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevoProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelProductosLayout.setVerticalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProductosLayout.createSequentialGroup()
                        .addComponent(btnNuevoProducto)
                        .addGap(10, 10, 10)
                        .addComponent(btnEliminarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(spProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        if (SESION_USUARIO.isVistaPermitida(PRODUCTOS)) {

            tpPestania.addTab("Productos", panelProductos);
        }

        panelFacturas.setBackground(new java.awt.Color(255, 255, 255));

        tbFacturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Forma de pago", "Estado", "Fecha", "IVA", "Total a pagar", "Total pagado", "Fecha entrega", "Cliente", "Usuario"
            }
        ));
        jScrollPane1.setViewportView(tbFacturas);

        btnVerProductosFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/LUPA3.png"))); // NOI18N
        btnVerProductosFactura.setText("Ver productos");
        btnVerProductosFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerProductosFacturaActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Actualizar_opt.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFacturasLayout = new javax.swing.GroupLayout(panelFacturas);
        panelFacturas.setLayout(panelFacturasLayout);
        panelFacturasLayout.setHorizontalGroup(
            panelFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFacturasLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1140, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addContainerGap())
            .addGroup(panelFacturasLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(btnVerProductosFactura)
                .addGap(30, 30, 30)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelFacturasLayout.setVerticalGroup(
            panelFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFacturasLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelFacturasLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnVerProductosFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        if (SESION_USUARIO.isVistaPermitida(FACTURAS)) {

            tpPestania.addTab("Facturas", panelFacturas);
        }

        getContentPane().add(tpPestania);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Llenando las tablas del módulo de ventas.
    private void llenarTablaProductos() {

        final String[] columnas = {"Codigo Producto", "Nombre", "Precio unitario"};

        // Configurando los eventos de edicion de la tabla
        dtbmProductos = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0;
            }
        };
        // Llenando la tabla
        servicio.listarProductos().forEach((ProductoDTO producto) -> {
            dtbmProductos.addRow(producto.toArray());
        });
        //Agregando el modelo de la tabla
        tbProductos.setModel(dtbmProductos);

        dtbmProductos.addTableModelListener((TableModelEvent e) -> {

            long id;
            double precioUnitario = 0;
            String nombre = "";

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tbProductos.getValueAt(e.getFirstRow(), 0);
                Object objNombre = tbProductos.getValueAt(e.getFirstRow(), 1);
                Object objPrecioUnitario = tbProductos.getValueAt(e.getFirstRow(), 2);

                if (objNombre == null || objPrecioUnitario == null) {
                    return;
                }

                if (!String.valueOf(objPrecioUnitario).matches(REGEX_DECIMAL)) {
                    mostrarMensaje("Sólo se permiten números para el precio unitario");
                    return;
                } else {
                    precioUnitario = Double.valueOf(String.valueOf(objPrecioUnitario));
                }

                nombre = String.valueOf(objNombre);

                if (objId != null) {
                    id = Long.parseLong(String.valueOf(objId));

                    if (servicio.editarProducto(id, nombre, precioUnitario)) {
                        actualizarTablaProductos();
                        mostrarMensaje("Se ha editado la producto satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo editar el producto");
                    }
                } else {
                    if (servicio.registrarProducto(nombre, precioUnitario)) {
                        actualizarTablaProductos();
                        mostrarMensaje("Se ha registrado la producto satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo registrar la producto");
                    }
                }

            }
        });

        aplicarEventosPersonalizados(tbProductos);
    }

    private void llenarTablaVentas() {

        final String[] columnas = {"Codigo", "Producto", "Cantidad", "Precio unitario", "Comentario"};

        // Configurando los eventos de edicion de la tabla
        dtbmVentas = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 4;
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
        tbVentas.getColumnModel().getColumn(0).setMaxWidth(70);
        tbVentas.getColumnModel().getColumn(1).setMaxWidth(80);
        tbVentas.getColumnModel().getColumn(2).setMaxWidth(100);
        tbVentas.getColumnModel().getColumn(3).setMaxWidth(150);
        tbVentas.getColumnModel().getColumn(4).setMaxWidth(120);
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

//                    if (servicio.editarVenta(id, cantidad)) {
//                        actualizarTablaProductos();
                    calcularTotales();
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

    private void llenarTablaFacturas() {

        final String[] columnas = {
            "Codigo",
            "Forma de pago",
            "Estado",
            "Fecha factura",
            "IVA",
            "Total a pagar",
            "Total pagado",
            "Fecha entrega",
            "Cliente",
            "Usuario",
            "Numero Cuotas",
            "Cuotas Pagadas"
        };

        // Configurando los eventos de edicion de la tabla
        dtbmFacturas = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2;
            }
        };
        // Llenando la tabla
//        servicio.listarFacturas().forEach((FacturaDTO factura) -> {
//            dtbmFacturas.addRow(factura.toArray());
//        });
        //Agregando el modelo de la tabla
        tbFacturas.setModel(dtbmFacturas);

        dtbmFacturas.addTableModelListener((TableModelEvent e) -> {

            long id;
//            long cantidad;

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tbFacturas.getValueAt(e.getFirstRow(), 0);
                String formaDePago = String.valueOf(tbFacturas.getValueAt(e.getFirstRow(), 1));

                String estado = String.valueOf(tbFacturas.getValueAt(e.getFirstRow(), 2));

                if (objId != null) {
                    id = Long.parseLong(String.valueOf(objId));

                    if (servicio.editarFactura(id, formaDePago, estado)) {
                        mostrarMensaje("La factura se ha editado satisfactoriamente.");

                        actualizarTablaFacturas();
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

        agregarJComboBoxAColumna(tbFacturas, 1, new String[]{
            FacturaDTO.FORMA_PAGO_CONTADO,
            FacturaDTO.FORMA_PAGO_CUOTAS
        });
        agregarJComboBoxAColumna(tbFacturas, 2, new String[]{
            FacturaDTO.ESTADO_DEPOSITO,
            FacturaDTO.ESTADO_ENTREGADO,
            FacturaDTO.ESTADO_PROCESO
        });

//        aplicarEventosPersonalizados(tbVentas);
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

    private void llenarTablaPagos() {

        final String[] columnas = {"Codigo", "Factura", "Abono", "Fecha", "Numero Cuotas"};

        // Configurando los eventos de edicion de la tabla
        dtbmPagos = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0 && column < 5;
            }
        };
        // Llenando la tabla
   //     servicio.listarPagos().forEach((PagoDTO pago) -> {
     //       dtbmPagos.addRow(pago.toArray());

       // });
        //Agregando el modelo de la tabla
        tbPagos.setModel(dtbmPagos);

        dtbmPagos.addTableModelListener((TableModelEvent e) -> {

            long id;
            long factura;
            double abono;
            long cuotas = 0;

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
                    abono = Double.parseDouble(String.valueOf(objAbono));
                }

                FacturaDTO facturaDTO = (FacturaDTO) objFactura;

                factura = facturaDTO.getId();
                if (objnumero != null) {
                    cuotas = Long.parseLong(String.valueOf(objnumero));
                }

                if (objId != null) {
                    id = Long.parseLong(String.valueOf(objId));

                    if (servicio.editarPago(id, abono, factura, cuotas)) {
                        mostrarMensaje("Se ha editado el pago satisfactoriamente");
                        long a = servicio.consultaTotalPagos(factura);
                        servicio.registrarCuotasPagadas(factura, a);
                        System.out.println("total" + a);
                        

                    } else {
                        mostrarMensaje("No se pudo editar el pago");
                    }
                    actualizarTablaPagos();
                } else {
                    if (servicio.registrarPago(abono, factura)) {
                        double totalAbonado = facturaDTO.getTotalPagado() + abono;
                        servicio.actualizarPago(factura, totalAbonado);
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
    private void actualizarTablaProductos() {
        tbProductos.removeAll();
        dtbmImpuestos = null;
        llenarTablaProductos();
    }

    private void actualizarTablaVentas() {
        tbVentas.removeAll();
        dtbmVentas = null;
        llenarTablaVentas();
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
            double precioUnitario = Double.parseDouble(String.valueOf(objPrecioUnitario));

            subTotal += precioUnitario * cantidad;

        }

        textSubtotal.setText(String.format("%.2f", subTotal));

        valorIva = subTotal * (iva / 100);
        textIVA.setText(String.format("%.2f", valorIva));

        facturaDTO.setIva(valorIva);
        totalPagar = subTotal + valorIva;

        facturaDTO.setTotalPagar(totalPagar);

        //   texTotalPagar.setText(String.format("%.2f", totalPagar));
        DecimalFormat formateador = new DecimalFormat("###,###.##");

        System.out.println("" + formateador.format(totalPagar));
        lblTotalPagar.setText("$" + formateador.format(totalPagar));

    }

    //Agrega producto a la tabla Ventas
    private void agregarProductoAFactura(ProductoDTO producto) {

        boolean agregar = true;

        for (int f = 0; f < tbVentas.getRowCount(); f++) {
            Object objId = tbVentas.getValueAt(f, 1);

            if (objId == null) {

                agregar = false;
                break;
            }

            ProductoDTO p = (ProductoDTO) objId;
            if ((producto == null) || (producto.getId() == p.getId())) {
                agregar = false;
                break;
            }

        }

//        if (quitar) {
//            dtbmVentas.removeRow(tbVentas.getRowCount() - 1);
//        }
        if (agregar) {
            dtbmVentas.addRow(new Object[]{producto.getId(), producto, 1, producto.getPrecioUnitario(), ""});
        }

    }

    //Agregando los productos a la factura a través de la tabla venta.
    private boolean registrarVentas() {

        ProductoDTO producto = null;
        long cantidad = 0;
        String comentario = "";
        long idFactura = 0;

        for (int i = 0; i < tbVentas.getRowCount(); i++) {

            if (tbVentas.getValueAt(i, 0) == null) {
                break;
            }

//            idFactura = servicio.consultarIncrementoFacturas() - 1;
            producto = (ProductoDTO) tbVentas.getValueAt(i, 1);
            cantidad = Long.parseLong(String.valueOf(tbVentas.getValueAt(i, 2)));
            comentario = String.valueOf(tbVentas.getValueAt(i, 4));
            System.out.println(producto.getId());
            System.out.println(idFactura);
            //boolean f = servicio.registrarVenta(cantidad, comentario, idFactura, producto.getId());
//            System.out.println("f: " + f);
//            if (!f) {
//                return false;
//            }
        }
        return true;
    }

    private void registrarFactura() {
        long id;
        String formaDePago = String.valueOf(comboFormaDePago.getSelectedItem());
        String estado = String.valueOf(comboEstadoFactura.getSelectedItem());
        double iva = 0;
        double totalPagar = 0;
        double totalPagado = 0;
        long numeroCuotas = 0;

        // Entrada de la fecha de entrega, si no se selecciona ninguna, registrará la fecha actual por defecto.
        Date fechaEntrega = Date.valueOf(dateFechaEntrega.getDateStringOrSuppliedString(LocalDate.now().toString()));

        long cliente = 0, usuario = SESION_USUARIO.getId();

        try {
            iva = facturaDTO.getIva();
            totalPagar = facturaDTO.getTotalPagar();
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
        if (!txtCuotas.getText().equalsIgnoreCase("")) {
            String cuota = txtCuotas.getText();
            long valor = Long.parseLong(cuota);
            numeroCuotas = valor;

        }

        totalPagado = Long.parseLong(texTotalPagado.getText());

        String codigoCliente = textCodigoCliente.getText();

        if (codigoCliente.matches(REGEX_ENTERO_POSITIVO) && !codigoCliente.equals("0")) {
            cliente = Long.parseLong(codigoCliente);
        } else {
            mostrarMensaje("Seleccione un cliente.");
            return;
        }

        if (servicio.registrarFactura(formaDePago, iva, totalPagar,
                totalPagado, fechaEntrega, cliente, usuario, numeroCuotas)) {
            actualizarTablaFacturas();
            if (!registrarVentas()) {
                mostrarMensaje("Hubo un error al registrar los productos a la factura de venta.");
                return;
            }
            mostrarMensaje("Se ha registrado la factura de venta satisfactoriamente.");
            actualizarTablaPagos();
        } else {
            mostrarMensaje("No se pudo registrar la factura de venta.");
        }
    }

    // Abriendo el JFrame
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened

        if (SESION_USUARIO.isVistaPermitida(PRODUCTOS)) {
            llenarTablaProductos();
        }
        if (SESION_USUARIO.isVistaPermitida(IMPUESTOS)) {
            llenarTablaImpuestos();
            actualizarIvaFactura();
//            actualizarCodigoFactura();
        }
        if (SESION_USUARIO.isVistaPermitida(PAGOS)) {
            llenarTablaPagos();
        }

        if (SESION_USUARIO.isVistaPermitida(VENTAS)) {
            llenarTablaVentas();
            llenarTablaFacturas();
            crearComboEditable(comboBuscarProducto, "PRODUCTOS");
            crearComboEditable(comboBuscarFactura, "FACTURAS");
            crearComboEditable(comboBuscarCliente, "CLIENTES");
        }
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnNuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProductoActionPerformed
        dtbmProductos.addRow(new Object[]{});
    }//GEN-LAST:event_btnNuevoProductoActionPerformed

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
        if (panelProductos.isShowing()) {
            int filaSeleccionada = tbProductos.getSelectedRow();

            if (filaSeleccionada < 0) {
                mostrarMensaje("Seleccione un producto de la tabla");
                return;
            }

            if (!mostrarDecision("¿Desea eliminar este producto?")) {
                return;
            }

            Object idObj = tbProductos.getValueAt(filaSeleccionada, 0);

            if (idObj == null) {
                dtbmProductos.removeRow(filaSeleccionada);
                return;
            }

            long id = Long.parseLong(String.valueOf(idObj));

            if (servicio.eliminarProducto(id)) {
                actualizarTablaProductos();
                mostrarMensaje("Producto eliminado satisfactoriamente");
            } else {
                mostrarMensaje("No se pudo eliminar el producto");
            }
        }
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void btnNuevoImpuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoImpuestoActionPerformed
        dtbmImpuestos.addRow(new Object[]{});
    }//GEN-LAST:event_btnNuevoImpuestoActionPerformed

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

    private void btnEliminarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPagoActionPerformed
        if (panelPagos.isShowing()) {
            int filaSeleccionada = tbPagos.getSelectedRow();

            if (filaSeleccionada < 0) {
                mostrarMensaje("Seleccione un pago de la tabla");
                return;
            }

            if (!mostrarDecision("¿Desea eliminar este pago?")) {
                return;
            }

            Object idObj = tbPagos.getValueAt(filaSeleccionada, 0);
            Object factObj = tbPagos.getValueAt(filaSeleccionada, 1);
            Object abonoObj = tbPagos.getValueAt(filaSeleccionada, 2);
            double abon = Double.parseDouble(String.valueOf(abonoObj));

            FacturaDTO facturaDTO = (FacturaDTO) factObj;

            if (idObj == null) {
                dtbmPagos.removeRow(filaSeleccionada);
                return;
            }

            long id = Long.parseLong(String.valueOf(idObj));

            PagoDTO pago = servicio.consultarPago(id);
            String mensaje = "No se pudo eliminar el pago";

            if (pago != null) {
                if (servicio.eliminarPago(id)) {
                    double totalPago = facturaDTO.getTotalPagado() - abon;
                    System.out.println(totalPago);
                    long a = servicio.consultaTotalPagos(facturaDTO.getId());
                    servicio.registrarCuotasPagadas(facturaDTO.getId(), a);
                    servicio.actualizarPago(facturaDTO.getId(), totalPago);
                    mensaje = "Pago eliminador";
                    actualizarTablaPagos();

                }
            }
            mostrarMensaje(mensaje);
        }
    }//GEN-LAST:event_btnEliminarPagoActionPerformed

    private void btnNuevoPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoPagoActionPerformed
        dtbmPagos.addRow(new Object[]{});
    }//GEN-LAST:event_btnNuevoPagoActionPerformed

    private void comboBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBuscarProductoActionPerformed

    }//GEN-LAST:event_comboBuscarProductoActionPerformed

    private void btnNuevaFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaFacturaActionPerformed

        String estado = btnNuevaFactura.getText();

        if (estado.equalsIgnoreCase("Nueva factura")) {
            this.textNumeroFactura.setEditable(true);
            this.comboFormaDePago.setEnabled(true);
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
            cambiarmensaje(evt);
        } else if (estado.equalsIgnoreCase("Cancelar")) {
            this.textNumeroFactura.setEditable(false);
            this.comboFormaDePago.setEnabled(false);
            this.comboEstadoFactura.setEnabled(false);
            this.dateFechaEntrega.setEnabled(false);
            this.comboBuscarCliente.setEditable(false);
            this.comboBuscarCliente.setEnabled(false);
            this.comboBuscarProducto.setEditable(false);
            this.comboBuscarProducto.setEnabled(false);
            this.btnQuitarProducto.setEnabled(false);

            btnAnadirCliente.setEnabled(false);
            btnEditarCliente.setEnabled(false);
            this.tbVentas.setEnabled(false);
            textDocumento.setEditable(false);
            textNombres.setEditable(false);
            textApellidos.setEditable(false);
            textTelefono.setEditable(false);
            textDireccion.setEditable(false);
            cambiarmensaje(evt);
        }

    }//GEN-LAST:event_btnNuevaFacturaActionPerformed

    private void btnCobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCobrarActionPerformed
        registrarFactura();
        textNombres.setText("");
        textApellidos.setText("");

        textCodigoCliente.setText("");

        textDireccion.setText("");

        textDocumento.setText("");
        textTelefono.setText("");
        textSubtotal.setText("");
        textIVA.setText("");
        dateFechaEntrega.setText("");
        comboBuscarCliente.setToolTipText("");
        tbVentas.removeAll();


    }//GEN-LAST:event_btnCobrarActionPerformed

    private void comboBuscarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBuscarFacturaActionPerformed

    }//GEN-LAST:event_comboBuscarFacturaActionPerformed

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

    private void textDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textDocumentoActionPerformed

    }//GEN-LAST:event_textDocumentoActionPerformed

    private void textApellidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textApellidosActionPerformed
        textApellidos.setEditable(true);
    }//GEN-LAST:event_textApellidosActionPerformed

    private void textTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textTelefonoActionPerformed
        textTelefono.setEditable(true);
    }//GEN-LAST:event_textTelefonoActionPerformed

    private void textDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textDireccionActionPerformed
        textDireccion.setEditable(true);
    }//GEN-LAST:event_textDireccionActionPerformed

    private void btnVerProductosFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerProductosFacturaActionPerformed

        int filaSeleccionada = tbFacturas.getSelectedRow();

        System.out.println(filaSeleccionada);
        if (filaSeleccionada < 0) {
            mostrarMensaje("Seleccione una factura de la tabla");
            return;
        }

        Object idObj = tbFacturas.getValueAt(filaSeleccionada, 0);

        long id = Long.parseLong(String.valueOf(idObj));

        ifrmProductosPorFactura vistaProductosPorFactura = new ifrmProductosPorFactura(id);
        getDesktopPane().add(vistaProductosPorFactura);
        vistaProductosPorFactura.setVisible(true);
        vistaProductosPorFactura.setSize(471, 337);
    }//GEN-LAST:event_btnVerProductosFacturaActionPerformed

    private void comboFormaDePagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFormaDePagoActionPerformed
        String s = comboFormaDePago.getSelectedItem().toString();
        if (s.equalsIgnoreCase("CONTADO")) {
            lblNumeroCuotas.setVisible(false);
            txtCuotas.setText("");
            txtCuotas.setVisible(false);

        } else {
            lblNumeroCuotas.setVisible(true);
            txtCuotas.setVisible(true);
        }
    }//GEN-LAST:event_comboFormaDePagoActionPerformed


    private void btnAnadirClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirClienteActionPerformed
        String documento = textDocumento.getText();
        String nombre = textNombres.getText();
        String apellido = textApellidos.getText();
        String telefono = textTelefono.getText();
        String direccion = textDireccion.getText();

        if (documento.equalsIgnoreCase("") || nombre.equalsIgnoreCase("") | apellido.equalsIgnoreCase("") || telefono.equalsIgnoreCase("") || direccion.equalsIgnoreCase("")) {
            mostrarMensaje("POR FAVOR LLEVAR TODOS LOS CAMPOS");
        } else if (servicio.validarCedula(documento)) {
            mostrarMensaje("EL USUARIO YA EXISTE");
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

    private void textNombresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNombresActionPerformed
        textNombres.setEditable(true);
    }//GEN-LAST:event_textNombresActionPerformed

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        String documento = textDocumento.getText();
        String nombre = textNombres.getText();
        String apellido = textApellidos.getText();
        String telefono = textTelefono.getText();
        String direccion = textDireccion.getText();
        String id = textCodigoCliente.getText();
        long codigo = Long.parseLong(id);

        if (documento.equalsIgnoreCase("") || nombre.equalsIgnoreCase("") | apellido.equalsIgnoreCase("") || telefono.equalsIgnoreCase("") || direccion.equalsIgnoreCase("")) {
            mostrarMensaje("NO PUEDE HABER NINGUN CAMPO VACIO");
        } else {
            boolean x = servicio.editarCliente(codigo, documento, nombre, apellido, direccion, telefono);
            if (x) {
                mostrarMensaje("SE EDITO");
            }
        }

    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void comboBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBuscarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBuscarClienteActionPerformed

    private void comboEstadoFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEstadoFacturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboEstadoFacturaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        actualizarTablaFacturas();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnadirCliente;
    private javax.swing.JButton btnCobrar;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEliminarImpuesto;
    private javax.swing.JButton btnEliminarPago;
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnNuevaFactura;
    private javax.swing.JButton btnNuevoImpuesto;
    private javax.swing.JButton btnNuevoPago;
    private javax.swing.JButton btnNuevoProducto;
    private javax.swing.JButton btnQuitarProducto;
    private javax.swing.JButton btnVerProductosFactura;
    private javax.swing.JComboBox<Object> comboBuscarCliente;
    private javax.swing.JComboBox<Object> comboBuscarFactura;
    private javax.swing.JComboBox<Object> comboBuscarProducto;
    private javax.swing.JComboBox<Object> comboEstadoFactura;
    private javax.swing.JComboBox<Object> comboFormaDePago;
    private com.github.lgooddatepicker.components.DatePicker dateFechaEntrega;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbApellidos;
    private javax.swing.JLabel lbBuscarCliente;
    private javax.swing.JLabel lbBuscarCliente1;
    private javax.swing.JLabel lbBuscarFactura1;
    private javax.swing.JLabel lbDireccion;
    private javax.swing.JLabel lbDocumento;
    private javax.swing.JLabel lbDocumento1;
    private javax.swing.JLabel lbEstadoFactura;
    private javax.swing.JLabel lbFormaDePago;
    private javax.swing.JLabel lbFormaDePago2;
    private javax.swing.JLabel lbIVA;
    private javax.swing.JLabel lbNombres;
    private javax.swing.JLabel lbSubtotal;
    private javax.swing.JLabel lbTelefono;
    private javax.swing.JLabel lbTotalPagado;
    private javax.swing.JLabel lblNumeroCuotas;
    private javax.swing.JLabel lblNumeroFactura;
    private javax.swing.JLabel lblTotalPagar;
    private javax.swing.JPanel panelCliente;
    private javax.swing.JPanel panelFactura;
    private javax.swing.JPanel panelFacturas;
    private javax.swing.JPanel panelImpuestos;
    private javax.swing.JPanel panelPagos;
    private javax.swing.JPanel panelProductos;
    private javax.swing.JPanel panelVentaProductos;
    private javax.swing.JPanel panelVentas;
    private javax.swing.JScrollPane spImpuestos;
    private javax.swing.JScrollPane spPagos;
    private javax.swing.JScrollPane spProductos;
    private javax.swing.JScrollPane spVentaProductos;
    private javax.swing.JTable tbFacturas;
    private javax.swing.JTable tbImpuestos;
    private javax.swing.JTable tbPagos;
    private javax.swing.JTable tbProductos;
    private javax.swing.JTable tbVentas;
    private javax.swing.JTextField texTotalPagado;
    private javax.swing.JTextField textApellidos;
    private javax.swing.JTextField textCodigoCliente;
    private javax.swing.JTextField textDireccion;
    private javax.swing.JTextField textDocumento;
    private javax.swing.JTextField textIVA;
    private javax.swing.JTextField textNombres;
    private javax.swing.JTextField textNumeroFactura;
    private javax.swing.JTextField textSubtotal;
    private javax.swing.JTextField textTelefono;
    private javax.swing.JTabbedPane tpPestania;
    private javax.swing.JTextField txtCuotas;
    // End of variables declaration//GEN-END:variables
}
