/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.view;

import com.zapateria.main.dto.CompraDTO;
import com.zapateria.main.dto.MaterialDTO;
import com.zapateria.main.dto.MaterialUsadoDTO;
import com.zapateria.main.dto.ProveedorDTO;
import java.sql.Timestamp;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Diego
 */
public class ifrmInventario extends MarcoInternoPersonalizado {

    private static final long serialVersionUID = -2407724079917861481L;
    private static final String MATERIALES = "MATERIALES", PROVEEDORES = "PROVEEDORES",
            COMPRAS = "COMPRAS", MATERIALES_USADOS = "MATERIALES USADOS";

    private DefaultTableModel dtblmMateriales, dtblmProveedores, dtblmCompras, dtblmMaterialesUsados;

    /**
     * Creates new form ifrmInventario
     */
    public ifrmInventario() {
        initComponents();
    }

    // Tabla Secciones
    private void llenarTablaMateriales() {

        final String[] campos = {"Id", "Nombre", "Cantidad"};

        // Configurando los eventos de edicion de la tabla
        dtblmMateriales = new DefaultTableModel(null, campos) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0 && column != 2;
            }
        };
        // Llenando la tabla
        servicio.listarMateriales().forEach((material) -> {
            dtblmMateriales.addRow(material.toArray());
        });

        //Agregando el modelo de la tabla
        tblMateriales.setModel(dtblmMateriales);

        dtblmMateriales.addTableModelListener((TableModelEvent e) -> {

            long id = 0;
            String nombre = "";

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tblMateriales.getValueAt(e.getFirstRow(), 0);
                Object objNombre = tblMateriales.getValueAt(e.getFirstRow(), 1);

                if (objNombre == null) {
                    return;
                }

                nombre = String.valueOf(objNombre);

                if (objId != null) {

                    id = Long.parseLong(String.valueOf(objId));

                    if (servicio.editarMaterial(id, nombre)) {
                        actualizarTablaMateriales();
                        mostrarMensaje("Se ha editado el material satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo editar el material");
                    }
                } else {

                    if (servicio.registrarMaterial(nombre)) {
                        actualizarTablaMateriales();
                        mostrarMensaje("Se ha registrado el material satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo registrar el material");
                    }
                }

            }
        });

        // Agregando combo para las celdas necesarias
        aplicarEventosPersonalizados(tblMateriales);
    }

    //Llenar tabla proveedores
    private void llenarTablaProveedores() {

        final String[] columnas = {"Id", "Nombre", "NIT", "Dirección", "Teléfono", "Fecha"};

        // Configurando los eventos de edicion de la tabla
        dtblmProveedores = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0 && column != 5;
            }
        };
        // Llenando la tabla
        servicio.listarProveedores().forEach((ProveedorDTO proveedor) -> {
            dtblmProveedores.addRow(proveedor.toArray());
        });
        //Agregando el modelo de la tabla
        tblProveedores.setModel(dtblmProveedores);

        dtblmProveedores.addTableModelListener((TableModelEvent e) -> {

            long id = 0;
            String nombre = "", nit = "", direccion = "";
            String telefono = "";
            Timestamp fecha = null;

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tblProveedores.getValueAt(e.getFirstRow(), 0);

                Object objNombre = tblProveedores.getValueAt(e.getFirstRow(), 1);
                Object objNit = tblProveedores.getValueAt(e.getFirstRow(), 2);
                Object objDireccion = tblProveedores.getValueAt(e.getFirstRow(), 3);
                Object objTelefono = tblProveedores.getValueAt(e.getFirstRow(), 4);

                if (objNombre == null
                        || objNit == null
                        || objDireccion == null
                        || objTelefono == null) {
                    return;
                }

                nombre = String.valueOf(objNombre);
                direccion = String.valueOf(objDireccion);
                nit = String.valueOf(objNit);
                if (!String.valueOf(objTelefono).matches(REGEX_ENTERO_POSITIVO)) {
                    mostrarMensaje("Sólo se permiten números para el teléfono");
                    return;
                }
                if (!String.valueOf(objTelefono).matches(REGEX_ENTERO_POSITIVO)) {
                    mostrarMensaje("Sólo se permiten números para el teléfono");
                    return;
                }

                telefono = String.valueOf(objTelefono);

                if (objId != null) {
                    id = Long.parseLong(String.valueOf(objId));
                    if (servicio.editarProveedor(id, nombre, nit, direccion, telefono)) {
                        actualizarTablaProveedores();
                        mostrarMensaje("Se ha editado el proveedor satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo editar el proveedor");
                    }
                } else {
                    if (servicio.registrarProveedor(nombre, nit, direccion, telefono)) {
                        actualizarTablaProveedores();
                        mostrarMensaje("Se ha registrado el proveedor satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo registrar el proveedor");
                    }
                }

            }
        });
        aplicarEventosPersonalizados(tblProveedores);

    }

    //Llenar tabla compras
    private void llenarTablaCompras() {

        final String[] columnas = {"Id", "Cantidad", "Valor unitario", "Valor total", "Fecha", "Proveedor", "Material"};

        // Configurando los eventos de edicion de la tabla
        dtblmCompras = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0 && column != 4 && column != 3;
            }
        };
        // Llenando la tabla
        servicio.listarCompras().forEach((CompraDTO compra) -> {
            dtblmCompras.addRow(compra.toArray());
        });
        //Agregando el modelo de la tabla
        tblCompras.setModel(dtblmCompras);

        dtblmCompras.addTableModelListener((TableModelEvent e) -> {

            long id, cantidad;
            double valorUnitario = 0, valorTotal = 0;
            long proveedor, material;

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tblCompras.getValueAt(e.getFirstRow(), 0);
                Object objCantidad = tblCompras.getValueAt(e.getFirstRow(), 1);
                Object objValorUnitario = tblCompras.getValueAt(e.getFirstRow(), 2);
                Object objProveedor = tblCompras.getValueAt(e.getFirstRow(), 5);
                Object objMaterial = tblCompras.getValueAt(e.getFirstRow(), 6);

                if (objCantidad == null
                        || objValorUnitario == null
                        || objProveedor == null
                        || objMaterial == null) {
                    return;
                }

                if (!validarInt(objCantidad)) {
                    mostrarMensaje("Número demasiado grande para la cantidad.");
                    return;
                }
                if (!validarDouble(objValorUnitario)) {
                    mostrarMensaje("Número demasiado grande para el valor unitario.");
                    return;
                }

                if (!String.valueOf(objCantidad).matches(REGEX_ENTERO_POSITIVO)) {
                    mostrarMensaje("Sólo se permiten números para la cantidad");
                    return;
                } else {
                    cantidad = Long.parseLong(String.valueOf(objCantidad));
                }

                if (!String.valueOf(objValorUnitario).matches(REGEX_DECIMAL)) {
                    mostrarMensaje("Sólo se permiten números para el valor unitario");
                    return;
                } else {
                    valorUnitario = Double.valueOf(String.valueOf(objValorUnitario));
                }

                ProveedorDTO proveedorDTO = (ProveedorDTO) objProveedor;
                proveedor = proveedorDTO.getId();

                MaterialDTO materialDTO = (MaterialDTO) objMaterial;
                material = materialDTO.getId();

                // Calculando el valor total
                valorTotal = valorUnitario * cantidad;

                if (objId != null) {
                    id = Long.parseLong(String.valueOf(objId));

                    if (servicio.editarCompra(id, cantidad, valorUnitario, valorTotal, proveedor, material)) {
                        actualizarTablaMateriales();
                        mostrarMensaje("Se ha editado la compra satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo editar la compra");
                    }
                } else {
                    if (servicio.registrarCompra(cantidad, valorUnitario, valorTotal, proveedor, material)) {
                        actualizarTablaMateriales();
                        mostrarMensaje("Se ha registrado la compra satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo registrar la compra");
                    }
                }

            }
        });

        agregarJComboBoxAColumna(tblCompras, 5, servicio.listarProveedores().toArray());
        agregarJComboBoxAColumna(tblCompras, 6, servicio.listarMateriales().toArray());

        aplicarEventosPersonalizados(tblCompras);
    }

    private void llenarTablaMaterialesUsados() {

        final String[] columnas = {"Id", "Cantidad", "Fecha", "Material"};

        // Configurando los eventos de edicion de la tabla
        dtblmMaterialesUsados = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Llenando la tabla
        servicio.listarMaterialesUsados().forEach((MaterialUsadoDTO materialUsado) -> {
            dtblmMaterialesUsados.addRow(materialUsado.toArray());
        });

        //Agregando el modelo de la tabla
        tblMaterialesUsados.setModel(dtblmMaterialesUsados);

        aplicarEventosPersonalizados(tblMaterialesUsados);
    }

    private void actualizarTablaCompras() {
        tblCompras.removeAll();
        dtblmCompras = null;
        llenarTablaCompras();
    }

    private void actualizarTablaMateriales() {
        tblMateriales.removeAll();
        dtblmMateriales = null;
        llenarTablaMateriales();
        actualizarTablaCompras();
    }

    private void actualizarTablaProveedores() {
        tblProveedores.removeAll();
        dtblmProveedores = null;
        llenarTablaProveedores();
        actualizarTablaCompras();
    }

    public void actualizarTablaMaterialesUsados() {
        tblMaterialesUsados.removeAll();
        dtblmMaterialesUsados = null;
        llenarTablaMaterialesUsados();
        actualizarTablaMateriales();
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
        panelCompras = new javax.swing.JPanel();
        spCompras = new javax.swing.JScrollPane();
        tblCompras = new TablaPersonalizada();
        panelMateriales = new javax.swing.JPanel();
        spMateriales = new javax.swing.JScrollPane();
        tblMateriales = new TablaPersonalizada();
        panelProveedores = new javax.swing.JPanel();
        spProveedores = new javax.swing.JScrollPane();
        tblProveedores = new TablaPersonalizada();
        panelMaterialesUsados = new javax.swing.JPanel();
        spMaterialesUsados = new javax.swing.JScrollPane();
        tblMaterialesUsados = new javax.swing.JTable();
        panelBotonesAccion = new javax.swing.JPanel();
        btnCerrar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnUsarMaterial = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Inventario");
        setMaximumSize(new java.awt.Dimension(1920, 1080));
        setMinimumSize(new java.awt.Dimension(687, 378));
        setPreferredSize(new java.awt.Dimension(687, 378));
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
                formInternalFrameIconified(evt);
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        tblCompras.setModel(new javax.swing.table.DefaultTableModel(
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
        spCompras.setViewportView(tblCompras);

        javax.swing.GroupLayout panelComprasLayout = new javax.swing.GroupLayout(panelCompras);
        panelCompras.setLayout(panelComprasLayout);
        panelComprasLayout.setHorizontalGroup(
            panelComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelComprasLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(spCompras, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        panelComprasLayout.setVerticalGroup(
            panelComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelComprasLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(spCompras, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addGap(11, 11, 11))
        );

        if (SESION_USUARIO.isVistaPermitida("COMPRAS")) {

            tpPestana.addTab("Compras", panelCompras);
        }

        tblMateriales.setModel(new javax.swing.table.DefaultTableModel(
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
        spMateriales.setViewportView(tblMateriales);

        javax.swing.GroupLayout panelMaterialesLayout = new javax.swing.GroupLayout(panelMateriales);
        panelMateriales.setLayout(panelMaterialesLayout);
        panelMaterialesLayout.setHorizontalGroup(
            panelMaterialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMaterialesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spMateriales, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelMaterialesLayout.setVerticalGroup(
            panelMaterialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMaterialesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spMateriales, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addContainerGap())
        );

        if (SESION_USUARIO.isVistaPermitida("MATERIALES")) {

            tpPestana.addTab("Materiales", panelMateriales);
        }

        tblProveedores.setModel(new javax.swing.table.DefaultTableModel(
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
        spProveedores.setViewportView(tblProveedores);

        javax.swing.GroupLayout panelProveedoresLayout = new javax.swing.GroupLayout(panelProveedores);
        panelProveedores.setLayout(panelProveedoresLayout);
        panelProveedoresLayout.setHorizontalGroup(
            panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProveedoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spProveedores, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelProveedoresLayout.setVerticalGroup(
            panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProveedoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spProveedores, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addContainerGap())
        );

        if (SESION_USUARIO.isVistaPermitida("PROVEEDORES")) {

            tpPestana.addTab("Proveedores", panelProveedores);
        }

        tblMaterialesUsados.setModel(new javax.swing.table.DefaultTableModel(
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
        spMaterialesUsados.setViewportView(tblMaterialesUsados);

        javax.swing.GroupLayout panelMaterialesUsadosLayout = new javax.swing.GroupLayout(panelMaterialesUsados);
        panelMaterialesUsados.setLayout(panelMaterialesUsadosLayout);
        panelMaterialesUsadosLayout.setHorizontalGroup(
            panelMaterialesUsadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMaterialesUsadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spMaterialesUsados, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelMaterialesUsadosLayout.setVerticalGroup(
            panelMaterialesUsadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMaterialesUsadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spMaterialesUsados, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addContainerGap())
        );

        if (SESION_USUARIO.isVistaPermitida(MATERIALES_USADOS)) {

            tpPestana.addTab("Materiales usados", panelMaterialesUsados);
        }

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnUsarMaterial.setText("Usar material");
        btnUsarMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsarMaterialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBotonesAccionLayout = new javax.swing.GroupLayout(panelBotonesAccion);
        panelBotonesAccion.setLayout(panelBotonesAccionLayout);
        panelBotonesAccionLayout.setHorizontalGroup(
            panelBotonesAccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesAccionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBotonesAccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsarMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );
        panelBotonesAccionLayout.setVerticalGroup(
            panelBotonesAccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotonesAccionLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUsarMaterial)
                .addGap(195, 195, 195)
                .addComponent(btnCerrar)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tpPestana)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panelBotonesAccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tpPestana)
                .addGap(10, 10, 10))
            .addComponent(panelBotonesAccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened

        btnUsarMaterial.setVisible(false);

        if (SESION_USUARIO.isVistaPermitida(MATERIALES)) {
            llenarTablaMateriales();
        }
        if (SESION_USUARIO.isVistaPermitida(PROVEEDORES)) {
            llenarTablaProveedores();
        }
        if (SESION_USUARIO.isVistaPermitida(COMPRAS)) {
            llenarTablaCompras();
        }
        if (SESION_USUARIO.isVistaPermitida(MATERIALES_USADOS)) {
            llenarTablaMaterialesUsados();
            tpPestana.addChangeListener((e) -> {
                btnUsarMaterial.setVisible(panelMateriales.isShowing());
                btnNuevo.setVisible(!panelMaterialesUsados.isShowing());
                btnEliminar.setVisible(!panelMaterialesUsados.isShowing());

            });
        }

    }//GEN-LAST:event_formInternalFrameOpened

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        cerrar();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        if (panelMateriales.isShowing()) {
            int filaSeleccionada = tblMateriales.getSelectedRow();

            if (filaSeleccionada < 0) {
                mostrarMensaje("Seleccione un material de la tabla");
                return;
            }

            if (!mostrarDecision("¿Desea eliminar este material?")) {
                return;
            }

            Object idObj = tblMateriales.getValueAt(filaSeleccionada, 0);

            if (idObj == null) {
                dtblmMateriales.removeRow(filaSeleccionada);
                return;
            }

            long id = Long.parseLong(String.valueOf(idObj));

            if (servicio.eliminarMaterial(id)) {
                actualizarTablaMateriales();
                mostrarMensaje("Material eliminado satisfactoriamente");
            } else {
                mostrarMensaje("No se pudo eliminar el material");
            }
        }

        if (panelProveedores.isShowing()) {
            int filaSeleccionada = tblProveedores.getSelectedRow();

            if (filaSeleccionada < 0) {
                mostrarMensaje("Seleccione un proveedor de la tabla");
                return;
            }

            if (!mostrarDecision("¿Desea eliminar este proveedor?")) {
                return;
            }

            Object idObj = tblProveedores.getValueAt(filaSeleccionada, 0);

            if (idObj == null) {
                dtblmProveedores.removeRow(filaSeleccionada);
                return;
            }

            long id = Long.parseLong(String.valueOf(idObj));

            if (servicio.eliminarProveedor(id)) {
                actualizarTablaProveedores();
                mostrarMensaje("Proveedor eliminado satisfactoriamente");
            } else {
                mostrarMensaje("No se pudo eliminar el proveedor");
            }
        }

        if (panelCompras.isShowing()) {
            int filaSeleccionada = tblCompras.getSelectedRow();

            if (filaSeleccionada < 0) {
                mostrarMensaje("Seleccione una compra de la tabla");
                return;
            }

            if (!mostrarDecision("¿Desea eliminar esta compra?")) {
                return;
            }

            Object idObj = tblCompras.getValueAt(filaSeleccionada, 0);

            if (idObj == null) {
                dtblmCompras.removeRow(filaSeleccionada);
                return;
            }

            long id = Long.parseLong(String.valueOf(idObj));

            if (servicio.eliminarCompra(id)) {
                actualizarTablaMateriales();
                mostrarMensaje("Compra eliminada satisfactoriamente");
            } else {
                mostrarMensaje("No se pudo eliminar la compra");
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

        if (panelMateriales.isShowing()) {
            dtblmMateriales.addRow(new Object[]{});
        }
        if (panelProveedores.isShowing()) {
            dtblmProveedores.addRow(new Object[]{});
        }
        if (panelCompras.isShowing()) {
            dtblmCompras.addRow(new Object[]{});
        }
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void formInternalFrameIconified(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameIconified
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameIconified

    private void btnUsarMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsarMaterialActionPerformed
        int filaSeleccionada = tblMateriales.getSelectedRow();

        if (filaSeleccionada < 0) {
            mostrarMensaje("Seleccione un material de la tabla");
            return;
        }

        Object idObj = tblMateriales.getValueAt(filaSeleccionada, 0);
        Object cantidadObj = tblMateriales.getValueAt(filaSeleccionada, 2);

        long id = Long.parseLong(String.valueOf(idObj));
        long cantidad = Integer.parseInt(String.valueOf(cantidadObj));

        if (cantidad < 1) {
            mostrarMensaje("No hay unidades disponibles del material seleccionado");
            return;
        }

        ifrmUsarMaterial vistaUsarMaterial = new ifrmUsarMaterial(id, cantidad, this);
        getDesktopPane().add(vistaUsarMaterial);
        vistaUsarMaterial.setVisible(true);
        vistaUsarMaterial.setSize(312, 151);
    }//GEN-LAST:event_btnUsarMaterialActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnUsarMaterial;
    private javax.swing.JPanel panelBotonesAccion;
    private javax.swing.JPanel panelCompras;
    private javax.swing.JPanel panelMateriales;
    private javax.swing.JPanel panelMaterialesUsados;
    private javax.swing.JPanel panelProveedores;
    private javax.swing.JScrollPane spCompras;
    private javax.swing.JScrollPane spMateriales;
    private javax.swing.JScrollPane spMaterialesUsados;
    private javax.swing.JScrollPane spProveedores;
    private javax.swing.JTable tblCompras;
    private javax.swing.JTable tblMateriales;
    private javax.swing.JTable tblMaterialesUsados;
    private javax.swing.JTable tblProveedores;
    private javax.swing.JTabbedPane tpPestana;
    // End of variables declaration//GEN-END:variables
}
