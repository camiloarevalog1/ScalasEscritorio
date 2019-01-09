/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.view;

import com.zapateria.main.dto.CompraDTO;
import com.zapateria.main.dto.FacturaDTO;
import com.zapateria.main.dto.MaterialDTO;
import com.zapateria.main.dto.MaterialUsadoDTO;
import com.zapateria.main.dto.NombreGastoDTO;
import com.zapateria.main.dto.NombreIngresosDTO;
import com.zapateria.main.dto.PagoDTO;
import com.zapateria.main.dto.PagosProv;
import com.zapateria.main.dto.ProveedorDTO;
import static com.zapateria.main.view.MarcoInternoPersonalizado.REGEX_DECIMAL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
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

    private DefaultTableModel dtblmMateriales, dtblmProveedores, dtblmCompras, dtblmMaterialesUsados, dtbmPagos;

    /**
     * Creates new form ifrmInventario
     */
    public ifrmInventario() {
        initComponents();
        llenarComboMaterial();
        llenarComboMaterial2();
        
    }
    
    private void llenarTablaPagos(long ids) {
        
        final String[] columnas = {"Codigo", "Compra", "Abono", "Fecha"};

        // Configurando los eventos de edicion de la tabla
        dtbmPagos = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0 && column < 5;
            }
        };
        // Llenando la tabla
        Object[] object = new Object[14];
        servicio.listarPagoProveedor(ids).forEach((PagosProv pago) -> {
            dtbmPagos.addRow(pago.toArray());

        });
        
        //Agregando el modelo de la tabla
        tblPagosCompras.setModel(dtbmPagos);

        dtbmPagos.addTableModelListener((TableModelEvent e) -> {

            long id;
            long factura;
            double abono;
            long cuotas = 0;

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tblPagosCompras.getValueAt(e.getFirstRow(), 0);
                Object objFactura = tblPagosCompras.getValueAt(e.getFirstRow(), 1);
                Object objAbono = tblPagosCompras.getValueAt(e.getFirstRow(), 2);
                //Object objnumero = tblPagosCompras.getValueAt(e.getFirstRow(), 4);

                if (objFactura == null || objAbono == null) {
                    return;
                }

                if (!String.valueOf(objAbono).matches(REGEX_DECIMAL)) {
                    mostrarMensaje("Sólo se permiten números para el valor");
                    return;
                } else {
                    abono = Double.parseDouble(String.valueOf(objAbono));
                }

                CompraDTO compraDTO = (CompraDTO) objFactura;
                factura = compraDTO.getId();

                //FacturaDTO facturaDTO = (FacturaDTO) objFactura;
//                factura = facturaDTO.getId();
//                if (objnumero != null) {
//                    cuotas = Long.parseLong(String.valueOf(objnumero));
////                }
                if (objId != null) {
                    id = Long.parseLong(String.valueOf(objId));
                    
                    double TotalPago=compraDTO.getAbonado();
                    TotalPago=TotalPago-abono;
                    System.out.println(TotalPago);
                    boolean exito=servicio.editar(id, abono, factura);
                    
                    System.out.println(exito);
                    if (exito) {
                        //TotalPago=TotalPago-abono;
                        
                        double p=servicio.total( factura);
                        mostrarMensaje(p+""+exito);
                        System.out.println(p);
                        servicio.actualizarPagoProv(factura,p);
                        mostrarMensaje("Se ha editado el pago satisfactoriamente");
//                        long a = servicio.consultaTotalPagos(factura);
//                        servicio.registrarCuotasPagadas(factura, a);
//                        System.out.println("total" + a);

                    } else {
                        mostrarMensaje("No se pudo editar el pago");
                    }
                    actualizarTablaPagos();
                } else {
                    if (servicio.registrarPagoProv(abono, factura)) {
                        Timestamp fecha=new Timestamp(System.currentTimeMillis());
                        servicio.registrarNombreIngreso(fecha,factura+"", 11, abono);
                        double totalAbonado = compraDTO.getAbonado() + abono;
                        servicio.actualizarPagoProv(factura, totalAbonado);
//                        long a = servicio.consultaTotalPagos(factura);
//                        servicio.registrarCuotasPagadas(factura, a);
//                        System.out.println("total" + a);
                        mostrarMensaje("Se ha registrado el pago satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo registrar el pago");
                    }
                    actualizarTablaPagos();
                }

            }
        });
        agregarJComboBoxAColumna(tblPagosCompras, 1, servicio.listarCompras().toArray());
        aplicarEventosPersonalizados(tblPagosCompras);
    }

    private void llenarTablaPagos() {

        final String[] columnas = {"Codigo", "Compra", "Abono", "Fecha"};

        // Configurando los eventos de edicion de la tabla
        dtbmPagos = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0 && column < 5;
            }
        };
        // Llenando la tabla
        servicio.listarPagosProv().forEach((PagosProv pago) -> {
            dtbmPagos.addRow(pago.toArray());

        });
        //Agregando el modelo de la tabla
        tblPagosCompras.setModel(dtbmPagos);

        dtbmPagos.addTableModelListener((TableModelEvent e) -> {

            long id;
            long factura;
            double abono;
            long cuotas = 0;

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tblPagosCompras.getValueAt(e.getFirstRow(), 0);
                Object objFactura = tblPagosCompras.getValueAt(e.getFirstRow(), 1);
                Object objAbono = tblPagosCompras.getValueAt(e.getFirstRow(), 2);
                //Object objnumero = tblPagosCompras.getValueAt(e.getFirstRow(), 4);

                if (objFactura == null || objAbono == null) {
                    return;
                }

                if (!String.valueOf(objAbono).matches(REGEX_DECIMAL)) {
                    mostrarMensaje("Sólo se permiten números para el valor");
                    return;
                } else {
                    abono = Double.parseDouble(String.valueOf(objAbono));
                }

                CompraDTO compraDTO = (CompraDTO) objFactura;
                factura = compraDTO.getId();

                //FacturaDTO facturaDTO = (FacturaDTO) objFactura;
//                factura = facturaDTO.getId();
//                if (objnumero != null) {
//                    cuotas = Long.parseLong(String.valueOf(objnumero));
////                }
                if (objId != null) {
                    id = Long.parseLong(String.valueOf(objId));

                    if (servicio.editar(id, abono, factura)) {
                        System.out.println("entroo");
                        System.out.println(factura);
                        double p=servicio.total( factura);
                       
                        System.out.println(p);
                        System.out.println(factura);
                        servicio.actualizarPagoProv(factura,p);
                        mostrarMensaje("Se ha editado el pago satisfactoriamente");
//                        long a = servicio.consultaTotalPagos(factura);
//                        servicio.registrarCuotasPagadas(factura, a);
//                        System.out.println("total" + a);

                    } else {
                        mostrarMensaje("No se pudo editar el pago");
                    }
                    actualizarTablaPagos();
                } else {
                    if (servicio.registrarPagoProv(abono, factura)) {
                        double totalAbonado = compraDTO.getAbonado() + abono;
                        servicio.actualizarPagoProv(factura, totalAbonado);
//                        long a = servicio.consultaTotalPagos(factura);
//                        servicio.registrarCuotasPagadas(factura, a);
//                        System.out.println("total" + a);
                        mostrarMensaje("Se ha registrado el pago satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo registrar el pago");
                    }
                    actualizarTablaPagos();
                }

            }
        });
        agregarJComboBoxAColumna(tblPagosCompras, 1, servicio.listarCompras().toArray());
        aplicarEventosPersonalizados(tblPagosCompras);
    }

    private void actualizarTablaPagos() {
        tblPagosCompras.removeAll();
        dtbmPagos = null;
        llenarTablaPagos();
    }

    // Tabla Secciones
    private void llenarTablaMateriales() {

        final String[] campos = {"Codigo Material", "Nombre", "Patron"};

        // Configurando los eventos de edicion de la tabla
        dtblmMateriales = new DefaultTableModel(null, campos) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0 && column != 3;
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
            String patron = "";

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tblMateriales.getValueAt(e.getFirstRow(), 0);
                Object objNombre = tblMateriales.getValueAt(e.getFirstRow(), 1);
                Object objPatron = tblMateriales.getValueAt(e.getFirstRow(), 2);

                if (objNombre == null) {
                    return;
                }

                nombre = String.valueOf(objNombre);
                patron = String.valueOf(objPatron);

                if (objId != null) {

                    id = Long.parseLong(String.valueOf(objId));

                    if (servicio.editarMaterial(id, nombre, patron)) {
                        actualizarTablaMateriales();
                        mostrarMensaje("Se ha editado el material satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo editar el material");
                    }
                } else {

                    if (servicio.registrarMaterial(nombre, patron)) {
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

        final String[] columnas = {"Codigo Proveedor", "Nombre", "NIT", "Dirección", "Teléfono", "Fecha"};

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

        final String[] columnas = {"Codigo ", "Cantidad", "Valor unitario", "Valor total", "Fecha", "Proveedor", "Material", "Abonado"};

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

               
                String c=String.valueOf(objCantidad);
//                c=c.replace(",", ".");
//                c=c.replace(".", "");
//                
                
               
                    cantidad = Long.parseLong(c);
                

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

        //  aplicarEventosPersonalizados(tblCompras);
    }
    DecimalFormat formateador = new DecimalFormat("###,###.###");
    //filtro
    private void llenarTablaCompras(long ids) {

        final String[] columnas = {"Codigo ", "Cantidad", "Valor unitario", "Valor total", "Fecha", "Proveedor", "Material", "Abonado"};

        // Configurando los eventos de edicion de la tabla
        dtblmCompras = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0 && column != 4 && column != 3;
            }
        };
        // Llenando la tabla
        
        Object[] object = new Object[14];
        double total=0;
        double abonado=0;
        double total_pagar=0;
        for (CompraDTO u : servicio.listarId(ids)) {
            double debe = 0;
//            NombreGastoDTO abono = servicio.consultarN(u.getNombre_G());
            // double debe = abono.getTotal_debe();
            MaterialDTO ma=servicio.consultarMaterial(u.getMaterial());
            ProveedorDTO p= servicio.consultarProveedor(u.getProveedor());
            object[0] = u.getId();
            object[1] = u.getCantidad();
            object[2] = formateador.format(u.getValorUnitario());
            object[3] = formateador.format(u.getValorTotal());
            object[4] = u.getFecha();
            object[5] = p.getNombre();
            object[6] = ma.getNombre();
            object[7] = formateador.format(u.getAbonado()) ;
            abonado=abonado+u.getAbonado();
            total_pagar=total_pagar+u.getValorTotal();
            dtblmCompras.addRow(object);

        }
        
        //Agregando el modelo de la tabla
        tblCompras.setModel(dtblmCompras);
        
        total=total_pagar-abonado;
        txtTotalDebe.setText(formateador.format(total)+"");

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

        //  aplicarEventosPersonalizados(tblCompras);
    }

    private void llenarTablaMaterialesUsados() {

        final String[] columnas = {"Codigo material", "Cantidad", "Fecha", "Material"};

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
       

        
    }

    private void actualizarTablaCompras() {
        tblCompras.removeAll();
        dtblmCompras = null;
        llenarTablaCompras();
        actualizarTablaPagos();
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
        cmbTipoMaterial = new javax.swing.JComboBox<>();
        txtTotalDebe = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        panelMateriales = new javax.swing.JPanel();
        spMateriales = new javax.swing.JScrollPane();
        tblMateriales = new TablaPersonalizada();
        panelProveedores = new javax.swing.JPanel();
        spProveedores = new javax.swing.JScrollPane();
        tblProveedores = new TablaPersonalizada();
        panelPagosCompras = new javax.swing.JPanel();
        spCompras1 = new javax.swing.JScrollPane();
        tblPagosCompras = new TablaPersonalizada();
        cmbTipoMaterial1 = new javax.swing.JComboBox<>();
        btnNuevo = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnActualizarEmpleado1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Proveedor");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/icon2.png"))); // NOI18N
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

        tpPestana.setBackground(new java.awt.Color(255, 255, 255));

        panelCompras.setBackground(new java.awt.Color(255, 255, 255));

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

        cmbTipoMaterial.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "CONTADO", "CUOTAS" }));
        cmbTipoMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoMaterialActionPerformed(evt);
            }
        });

        jLabel1.setText("Total Debe Proveedor");

        javax.swing.GroupLayout panelComprasLayout = new javax.swing.GroupLayout(panelCompras);
        panelCompras.setLayout(panelComprasLayout);
        panelComprasLayout.setHorizontalGroup(
            panelComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelComprasLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(spCompras, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
                .addGap(10, 10, 10))
            .addGroup(panelComprasLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(cmbTipoMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addComponent(txtTotalDebe, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelComprasLayout.setVerticalGroup(
            panelComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelComprasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spCompras, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbTipoMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalDebe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)))
        );

        if (SESION_USUARIO.isVistaPermitida("COMPRAS")) {

            tpPestana.addTab("Compras", panelCompras);
        }

        panelMateriales.setBackground(new java.awt.Color(255, 255, 255));

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
            .addGroup(panelMaterialesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spMateriales, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelMaterialesLayout.setVerticalGroup(
            panelMaterialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMaterialesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spMateriales, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addGap(72, 72, 72))
        );

        if (SESION_USUARIO.isVistaPermitida("MATERIALES")) {

            tpPestana.addTab("Materiales", panelMateriales);
        }

        panelProveedores.setBackground(new java.awt.Color(255, 255, 255));

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
                .addComponent(spProveedores, javax.swing.GroupLayout.DEFAULT_SIZE, 1004, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelProveedoresLayout.setVerticalGroup(
            panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProveedoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spProveedores, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                .addContainerGap())
        );

        if (SESION_USUARIO.isVistaPermitida("PROVEEDORES")) {

            tpPestana.addTab("Proveedores", panelProveedores);
        }

        panelPagosCompras.setBackground(new java.awt.Color(255, 255, 255));

        tblPagosCompras.setModel(new javax.swing.table.DefaultTableModel(
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
        spCompras1.setViewportView(tblPagosCompras);

        cmbTipoMaterial1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "CONTADO", "CUOTAS" }));
        cmbTipoMaterial1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoMaterial1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPagosComprasLayout = new javax.swing.GroupLayout(panelPagosCompras);
        panelPagosCompras.setLayout(panelPagosComprasLayout);
        panelPagosComprasLayout.setHorizontalGroup(
            panelPagosComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPagosComprasLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(spCompras1, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
                .addGap(10, 10, 10))
            .addGroup(panelPagosComprasLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(cmbTipoMaterial1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelPagosComprasLayout.setVerticalGroup(
            panelPagosComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagosComprasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spCompras1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbTipoMaterial1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tpPestana.addTab("Pagos Compras", panelPagosCompras);

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/agregar.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Eliminar.png"))); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/SALIR.png"))); // NOI18N
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnActualizarEmpleado1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Actualizar_opt.png"))); // NOI18N
        btnActualizarEmpleado1.setText("Actualizar");
        btnActualizarEmpleado1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarEmpleado1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(btnActualizarEmpleado1)
                .addGap(189, 189, 189))
            .addGroup(layout.createSequentialGroup()
                .addComponent(tpPestana, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(tpPestana)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnActualizarEmpleado1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened

        
 if (SESION_USUARIO.isVistaPermitida(MATERIALES)) {
            llenarTablaMateriales();
        }
        if (SESION_USUARIO.isVistaPermitida(PROVEEDORES)) {
            llenarTablaProveedores();
        }
        if (SESION_USUARIO.isVistaPermitida(COMPRAS)) {
            llenarTablaCompras();
            llenarTablaPagos();
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

        if (panelPagosCompras.isShowing()) {
            dtbmPagos.addRow(new Object[]{});
        }
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void formInternalFrameIconified(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameIconified
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameIconified

    private void btnActualizarEmpleado1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarEmpleado1ActionPerformed
        llenarTablaCompras();
        txtTotalDebe.setText("");
    }//GEN-LAST:event_btnActualizarEmpleado1ActionPerformed

    private void cmbTipoMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoMaterialActionPerformed
        if (cmbTipoMaterial.getSelectedItem() != null) {
            String nombre = cmbTipoMaterial.getSelectedItem().toString();
            ProveedorDTO ingreso = servicio.consultarNombr(nombre);
            //        System.out.println(ingreso.getId() + "ingreso");
            if (cmbTipoMaterial.getSelectedItem().toString().equalsIgnoreCase("ninguno")) {
                //llenarTablaPagos();
            }

            if (ingreso != null) {
                llenarTablaCompras(ingreso.getId());
            }
        }
    }//GEN-LAST:event_cmbTipoMaterialActionPerformed

    private void cmbTipoMaterial1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoMaterial1ActionPerformed
  if (cmbTipoMaterial1.getSelectedItem() != null) {
            String nombre = cmbTipoMaterial1.getSelectedItem().toString();
            ProveedorDTO ingreso = servicio.consultarNombr(nombre);
            //        System.out.println(ingreso.getId() + "ingreso");
            if (cmbTipoMaterial1.getSelectedItem().toString().equalsIgnoreCase("ninguno")) {
                llenarTablaPagos();
            }

            if (ingreso != null) {
               
                llenarTablaPagos(ingreso.getId());
            }
        }       
    }//GEN-LAST:event_cmbTipoMaterial1ActionPerformed
  public void llenarComboMaterial() {

        cmbTipoMaterial.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<ProveedorDTO> listar = servicio.listarProveedores();
        cmbTipoMaterial.addItem("ninguno");

        for (ProveedorDTO n : listar) {
            cmbTipoMaterial.addItem(n.getNombre());
        }
    }
  public void llenarComboMaterial2() {

        cmbTipoMaterial1.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<ProveedorDTO> listar = servicio.listarProveedores();
        cmbTipoMaterial1.addItem("ninguno");

        for (ProveedorDTO n : listar) {
            cmbTipoMaterial1.addItem(n.getNombre());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarEmpleado1;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cmbTipoMaterial;
    private javax.swing.JComboBox<String> cmbTipoMaterial1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel panelCompras;
    private javax.swing.JPanel panelMateriales;
    private javax.swing.JPanel panelPagosCompras;
    private javax.swing.JPanel panelProveedores;
    private javax.swing.JScrollPane spCompras;
    private javax.swing.JScrollPane spCompras1;
    private javax.swing.JScrollPane spMateriales;
    private javax.swing.JScrollPane spProveedores;
    private javax.swing.JTable tblCompras;
    private javax.swing.JTable tblMateriales;
    private javax.swing.JTable tblPagosCompras;
    private javax.swing.JTable tblProveedores;
    private javax.swing.JTabbedPane tpPestana;
    private javax.swing.JTextField txtTotalDebe;
    // End of variables declaration//GEN-END:variables
}
