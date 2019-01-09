/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.view;

import com.zapateria.main.dto.Abono_PrestamoDTO;
import com.zapateria.main.dto.CompraDTO;
import com.zapateria.main.dto.Ingresos_EgresosDTO;
import com.zapateria.main.dto.MaterialDTO;
import com.zapateria.main.dto.NombreGastoDTO;
import com.zapateria.main.dto.NombreIngresosDTO;
import com.zapateria.main.dto.PagosProv;
import com.zapateria.main.dto.ProveedorDTO;
import com.zapateria.main.dto.UsuarioDTO;
import static com.zapateria.main.view.MarcoInternoPersonalizado.REGEX_ENTERO_POSITIVO;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class ifrmLibroAux extends MarcoInternoPersonalizado {

    /**
     * Creates new form ifrmLibroAux
     */
    private DefaultTableModel dtblmProveedores, dtblmCompras, dtbmPagos, dtblmProveedores1, dtblmProveedores2;

    public ifrmLibroAux() {
        initComponents();
        llenarTablaProveedores();
        
       llenarComboMaterial2();

//        llenarTablaCompras();
//        llenarCombo();
        dateFechaEntrega1.setEnabled(true);

    }

    private void llenarTablaProveedores() {
        double total=0;

        final String[] columnas = {"Codigo Gasto", "Nombre Gasto", "Descripcion", "Pago", "Fecha"};
        

        // Configurando los eventos de edicion de la tabla
        dtblmProveedores = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2 || column == 3;
            }
        };

        Object[] object = new Object[14];
        // Llenando la tabla
        ArrayList<NombreIngresosDTO>nombresIngresos=servicio.listarNombres();
        for (NombreIngresosDTO u :nombresIngresos ) {
            double debe = 0;
            //NombreGastoDTO abono = servicio.consultarN(u.getNombre_G());
            // double debe = abono.getTotal_debe();
            total=total+u.getPago();
            object[0] = u.getId();
            object[1] = u.getNombreGasto();
            object[2] = u.getNombre();
            object[3] = formateador.format(u.getPago());
            object[4] = u.getFecha();

            dtblmProveedores.addRow(object);

        }
        txtTotalGasto.setText(formateador.format(total));

        //Agregando el modelo de la tabla
        tblNombreIngreso.setModel(dtblmProveedores);

        tblNombreIngreso.getColumnModel().getColumn(0).setMaxWidth(90);
        tblNombreIngreso.getColumnModel().getColumn(1).setMaxWidth(100);
        tblNombreIngreso.getColumnModel().getColumn(2).setMaxWidth(1000);
        tblNombreIngreso.getColumnModel().getColumn(3).setMaxWidth(100);
        tblNombreIngreso.getColumnModel().getColumn(4).setMaxWidth(100);

        dtblmProveedores.addTableModelListener((TableModelEvent e) -> {

            long id = 0;
            String nombre = "", nit = "", direccion = "";
            String telefono = "";
            //Timestamp fecha = null;
             Timestamp fecha=new Timestamp(System.currentTimeMillis());
            double pago = 0;
            String p = "";

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tblNombreIngreso.getValueAt(e.getFirstRow(), 0);

                Object objNombreg = tblNombreIngreso.getValueAt(e.getFirstRow(), 1);
                Object objNombre = tblNombreIngreso.getValueAt(e.getFirstRow(), 2);
                Object objPago = tblNombreIngreso.getValueAt(e.getFirstRow(), 3);

                if (objNombre == null || objPago == null) {
                    return;
                }
                String nombres = String.valueOf(objNombreg);

                NombreGastoDTO nom = servicio.consultarNombreN(nombres);
                p = String.valueOf(objPago);
                p = p.replace(",", ".");
                p = p.replace(".", "");
                pago = Double.parseDouble(p);
                nombre = String.valueOf(objNombre);

                if (objId != null) {
                    id = Long.parseLong(String.valueOf(objId));
                    mostrarMensaje(id + nom.getId() + nombre + pago);
                    if (servicio.editarNombre(id, nom.getId(), nombre, pago)) {

                        actualizarTablaProveedores();
                        mostrarMensaje("Se ha editado ");
                    } else {
                        mostrarMensaje("No se pudo editar ");
                    }
                    actualizarTablaProveedores();
                } else {
                    System.out.println(fecha+"fech aprueba");
                    if (servicio.registrarNombreIngreso(fecha,nombre, nom.getId(), pago)) {

                        actualizarTablaProveedores();

                        mostrarMensaje("Se ha registrado el Nombre satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo registrar el Nombre");
                    }
                }

            }
        });
        agregarJComboBoxAColumna(tblNombreIngreso, 1, servicio.listarN().toArray());
        aplicarEventosPersonalizados(tblNombreIngreso);

    }

    private void llenarTablaProveedores(long ids) {
double total=0;
        final String[] columnas = {"Codigo Gasto", "Nombre Gasto", "Descripcion", "Pago", "Fecha"};

        // Configurando los eventos de edicion de la tabla
        dtblmProveedores = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2 || column == 3;
            }
        };

        Object[] object = new Object[14];
        // Llenando la tabla
        for (NombreIngresosDTO u : servicio.listarIngre(ids)) {
            double debe = 0;
            NombreGastoDTO abono = servicio.consultarN(u.getNombre_G());
            // double debe = abono.getTotal_debe();
            total=u.getPago()+total;
            object[0] = u.getId();
            object[1] = abono.getNombre();
            object[2] = u.getNombre();
            object[3] = formateador.format(u.getPago());
            object[4] = u.getFecha();

            dtblmProveedores.addRow(object);

        }
        txtTotalGasto.setText(formateador.format(total));

        //Agregando el modelo de la tabla
        tblNombreIngreso.setModel(dtblmProveedores);

        tblNombreIngreso.getColumnModel().getColumn(0).setMaxWidth(90);
        tblNombreIngreso.getColumnModel().getColumn(1).setMaxWidth(100);
        tblNombreIngreso.getColumnModel().getColumn(2).setMaxWidth(1000);
        tblNombreIngreso.getColumnModel().getColumn(3).setMaxWidth(100);
        tblNombreIngreso.getColumnModel().getColumn(4).setMaxWidth(100);

        dtblmProveedores.addTableModelListener((TableModelEvent e) -> {

            long id = 0;
            String nombre = "", nit = "", direccion = "";
            String telefono = "";
           Timestamp fecha=new Timestamp(System.currentTimeMillis());
            double pago = 0;
            String p = "";

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tblNombreIngreso.getValueAt(e.getFirstRow(), 0);

                Object objNombreg = tblNombreIngreso.getValueAt(e.getFirstRow(), 1);
                Object objNombre = tblNombreIngreso.getValueAt(e.getFirstRow(), 2);
                Object objPago = tblNombreIngreso.getValueAt(e.getFirstRow(), 3);

                if (objNombre == null || objPago == null) {
                    return;
                }
                String nombres = String.valueOf(objNombreg);

                NombreGastoDTO nom = servicio.consultarNombreN(nombres);
                p = String.valueOf(objPago);
                p = p.replace(",", ".");
                p = p.replace(".", "");
                pago = Double.parseDouble(p);
                nombre = String.valueOf(objNombre);

                if (objId != null) {
                    id = Long.parseLong(String.valueOf(objId));
                    mostrarMensaje(id + nom.getId() + nombre + pago);
                    if (servicio.editarNombre(id, nom.getId(), nombre, pago)) {

                        actualizarTablaProveedores();
                        mostrarMensaje("Se ha editado ");
                    } else {
                        mostrarMensaje("No se pudo editar ");
                    }
                    actualizarTablaProveedores();
                } else {
                    if (servicio.registrarNombreIngreso(fecha,nombre, nom.getId(), pago)) {

                        actualizarTablaProveedores();

                        mostrarMensaje("Se ha registrado el Nombre satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo registrar el Nombre");
                    }
                }

            }
        });
        agregarJComboBoxAColumna(tblNombreIngreso, 1, servicio.listarN().toArray());
        aplicarEventosPersonalizados(tblNombreIngreso);

    }

    private void llenarTablaNombres() {

        final String[] columnas = {"Codigo ", "Nombre"};

        // Configurando los eventos de edicion de la tabla
        dtblmProveedores2 = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2;
            }
        };
        // Llenando la tabla
        servicio.listarN().forEach((NombreGastoDTO proveedor) -> {
            dtblmProveedores2.addRow(proveedor.toArray());

        });
        //Agregando el modelo de la tabla
        tblNombreIngreso2.setModel(dtblmProveedores2);

        tblNombreIngreso2.getColumnModel().getColumn(0).setMaxWidth(200);
        tblNombreIngreso2.getColumnModel().getColumn(1).setMaxWidth(4000);
//        tblNombreIngreso.getColumnModel().getColumn(2).setMaxWidth(900);

        dtblmProveedores2.addTableModelListener((TableModelEvent e) -> {

            long id = 0;
            String nombre = "", nit = "", direccion = "";
            String telefono = "";
            Timestamp fecha = null;
            double pago = 0;
            String p = "";

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tblNombreIngreso2.getValueAt(e.getFirstRow(), 0);

                Object objNombre = tblNombreIngreso2.getValueAt(e.getFirstRow(), 1);

                if (objNombre == null) {
                    return;
                }

                nombre = String.valueOf(objNombre);

                if (objId != null) {
                    id = Long.parseLong(String.valueOf(objId));

                    if (servicio.editarN(id, nombre)) {

                        actualizarTablaNombres();
                        mostrarMensaje("Se ha editado ");
                    } else {
                        mostrarMensaje("No se pudo editar ");
                    }
                    actualizarTablaNombres();
                } else {
                    if (servicio.registrarN(nombre)) {

                        actualizarTablaNombres();

                        mostrarMensaje("Se ha registrado el Nombre satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo registrar el Nombre");
                    }
                }

            }
        });
        llenarComboMaterial2();
        agregarJComboBoxAColumna(tblNombreIngreso, 1, servicio.listarN().toArray());
        aplicarEventosPersonalizados(tblNombreIngreso2);

    }

    private void llenarTablaProveedoresFecha(Date fecha) {

        final String[] columnas = {"Codigo Gasto", "nombre Gasto", "Descripcion", "Pago", "Fecha"};

        // Configurando los eventos de edicion de la tabla
        dtblmProveedores = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2;
            }
        };
        // Llenando la tabla

       // ArrayList<NombreIngresosDTO> n = servicio.listar(fecha);

        servicio.listar(fecha).forEach((NombreIngresosDTO proveedor) -> {
            dtblmProveedores.addRow(proveedor.toArray());
        });
        //Agregando el modelo de la tabla
        tblNombreIngreso.setModel(dtblmProveedores);

        dtblmProveedores.addTableModelListener((TableModelEvent e) -> {

            long id = 0;
            String nombre = "", nit = "", direccion = "";
            String telefono = "";

            double pago = 0;
            String p = "";
            Timestamp fecha1=new Timestamp(System.currentTimeMillis());

            //Obteniendo el objeto almacenado en la celda seleccionada
            if (e.getType() == TableModelEvent.UPDATE) {
                // Capturando los datos que contiene las celdas a editar

                Object objId = tblNombreIngreso.getValueAt(e.getFirstRow(), 0);

                Object objNombreG = tblNombreIngreso.getValueAt(e.getFirstRow(), 1);
                Object objNombre = tblNombreIngreso.getValueAt(e.getFirstRow(), 2);
                Object objPago = tblNombreIngreso.getValueAt(e.getFirstRow(), 3);

                if (objNombreG == null || objPago == null) {
                    return;
                }

                String nombres = String.valueOf(objNombreG);
                NombreGastoDTO no = servicio.consultarNombreN(nombres);
                p = String.valueOf(objPago);
                p = p.replace(",", ".");
                p = p.replace(".", "");
                pago = Double.parseDouble(p);
                nombre = String.valueOf(objNombre);
                

                if (objId != null) {
                    id = Long.parseLong(String.valueOf(objId));
                    if (servicio.editarNombre(id, no.getId(), nombre, pago)) {

                        actualizarTablaProveedores();
                        mostrarMensaje("Se ha editado el Nombre satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo editar el Nombre");
                    }
                } else {
                    if (servicio.registrarNombreIngreso(fecha1,nombre, no.getId(), pago)) {

                        actualizarTablaProveedores();

                        mostrarMensaje("Se ha registrado el Nombre satisfactoriamente");
                    } else {
                        mostrarMensaje("No se pudo registrar el Nombre");
                    }
                }

            }
        });
        aplicarEventosPersonalizados(tblNombreIngreso);

    }

    private void llenarTablaCierres() {

        final String[] columnas = {"Codigo Cierre", "Fecha", "Abonos", "Deudas", "Liquidaciones", "Nominas", "Salio", "Entro", "Caja"};

        // Configurando los eventos de edicion de la tabla
        dtblmProveedores1 = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = -4082996669139353200L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 0;
            }
        };
        // Llenando la tabla

        servicio.listar().forEach((Ingresos_EgresosDTO proveedor) -> {
            dtblmProveedores1.addRow(proveedor.toArray());
        });
      
        //Agregando el modelo de la tabla
        tblNombreIngreso1.setModel(dtblmProveedores1);

        dtblmProveedores.addTableModelListener((TableModelEvent e) -> {

            long id = 0;
            String nombre = "", nit = "", direccion = "";
            String telefono = "";

            double pago = 0;
            String p = "";

            //Obteniendo el objeto almacenado en la celda seleccionada
//            if (e.getType() == TableModelEvent.UPDATE) {
//                // Capturando los datos que contiene las celdas a editar
//
//                Object objId = tblNombreIngreso1.getValueAt(e.getFirstRow(), 0);
//
//                Object objNombre = tblNombreIngreso1.getValueAt(e.getFirstRow(), 1);
//                Object objPago = tblNombreIngreso1.getValueAt(e.getFirstRow(), 2);
//
//                if (objNombre == null || objPago == null) {
//                    return;
//                }
//                p = String.valueOf(objPago);
//                p = p.replace(".", "");
//                p = p.replace(",", ".");
//                pago = Double.parseDouble(p);
//                nombre = String.valueOf(objNombre);
//
//                if (objId != null) {
//                    id = Long.parseLong(String.valueOf(objId));
//                    if (servicio.editarNombre(id, nombre)) {
//
//                        actualizarTablaProveedores();
//                        mostrarMensaje("Se ha editado el Nombre satisfactoriamente");
//                    } else {
//                        mostrarMensaje("No se pudo editar el Nombre");
//                    }
//                } else {
//                    if (servicio.registrarNombreIngreso(nombre, pago)) {
//
//                        actualizarTablaProveedores();
//
//                        mostrarMensaje("Se ha registrado el Nombre satisfactoriamente");
//                    } else {
//                        mostrarMensaje("No se pudo registrar el Nombre");
//                    }
//                }
//
//            }
        });
//        aplicarEventosPersonalizados(tblNombreIngreso1);

    }

    private void actualizarTablaProveedores() {
        tblNombreIngreso.removeAll();
        dtblmProveedores = null;
        llenarTablaProveedores();

//        llenarTablaCompras();
        //actualizarTablaCompras();
    }

    private void actualizarTablaNombres() {
        tblNombreIngreso2.removeAll();
        dtblmProveedores2 = null;
        llenarTablaNombres();

//        llenarTablaCompras();
        //actualizarTablaCompras();
    }

    private void actualizarTablaCierres() {
        tblNombreIngreso1.removeAll();
        dtblmProveedores1 = null;
        llenarTablaCierres();
        totalCierres();
//        llenarTablaCompras();
        //actualizarTablaCompras();
    }

//    private void llenarTablaPagos() {
//
//        final String[] columnas = {"Codigo ", "Tipo", "Descipcion", "Credito", "Debito", "Fecha"};
//        //NombreIngresosDTO materialDTO=null;
//
//        // Configurando los eventos de edicion de la tabla
//        dtbmPagos = new DefaultTableModel(null, columnas) {
//            private static final long serialVersionUID = -4082996669139353200L;
//
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return column > 0 && column < 5;
//            }
//        };
//        // Llenando la tabla
//        servicio.listar().forEach((Ingresos_EgresosDTO pago) -> {
//            dtbmPagos.addRow(pago.toArray());
//
//        });
//        //Agregando el modelo de la tabla
//        
//
//        dtbmPagos.addTableModelListener((TableModelEvent e) -> {
//
//            long id, factura;
//            String descripcion;
//            double credito = 0, debito = 0;
//            NombreIngresosDTO materialDTO = null;
//            //long cuotas = 0;
//
//            //Obteniendo el objeto almacenado en la celda seleccionada
//            if (e.getType() == TableModelEvent.UPDATE) {
//                // Capturando los datos que contiene las celdas a editar
//
////                Object objId = tblLibroAuxiliar.getValueAt(e.getFirstRow(), 0);
////                Object objDes = tblLibroAuxiliar.getValueAt(e.getFirstRow(), 2);
////                Object objCredito = tblLibroAuxiliar.getValueAt(e.getFirstRow(), 3);
////                Object objDebito = tblLibroAuxiliar.getValueAt(e.getFirstRow(), 4);
////                //Object objnumero = tblPagosCompras.getValueAt(e.getFirstRow(), 4);
////                Object objIdN = tblLibroAuxiliar.getValueAt(e.getFirstRow(), 1);
//
//                if (objDes == null || objCredito == null || objDebito == null) {
//                    return;
//                }
//
//                if (!String.valueOf(objCredito).matches(REGEX_DECIMAL) || !String.valueOf(objDebito).matches(REGEX_DECIMAL)) {
//                    mostrarMensaje("Sólo se permiten números para el valor");
//                    return;
//                } else {
//                    credito = Double.parseDouble(String.valueOf(objCredito));
//                    debito = Double.parseDouble(String.valueOf(objDebito));
//                }
//
//                System.out.println(objIdN + "ingreso");
//
//                materialDTO = (NombreIngresosDTO) objIdN;
//                factura = materialDTO.getId();
//
//                descripcion = String.valueOf(objDes);
//                if (objCredito != null) {
//                    credito = Double.parseDouble(String.valueOf(objCredito));
//                }
//
//                if (objDebito != null) {
//                    debito = Double.parseDouble(String.valueOf(objDebito));
//
//                }
//                //FacturaDTO facturaDTO = (FacturaDTO) objFactura;
////                factura = facturaDTO.getId();
////                if (objnumero != null) {
////                    cuotas = Long.parseLong(String.valueOf(objnumero));
//////                }
//                if (objId != null) {
//                    id = Long.parseLong(String.valueOf(objId));
//
//                    if (servicio.editarIngreso(id, descripcion, credito, debito, factura)) {
//                        mostrarMensaje("Se ha editado el pago satisfactoriamente");
////                        long a = servicio.consultaTotalPagos(factura);
////                        servicio.registrarCuotasPagadas(factura, a);
////                        System.out.println("total" + a);
//
//                    } else {
//                        mostrarMensaje("No se pudo editar el pago");
//                    }
//                    actualizarTablaPagos();
//                } else {
//                    if (servicio.registrarIngreso(descripcion, credito, debito, factura)) {
//                        //double totalAbonado = compraDTO.getAbonado() + abono;
//                        //servicio.actualizarPagoProv(factura, totalAbonado);
////                        long a = servicio.consultaTotalPagos(factura);
////                        servicio.registrarCuotasPagadas(factura, a);
////                        System.out.println("total" + a);
//                        mostrarMensaje("Se ha registrado el pago satisfactoriamente");
//                    } else {
//                        mostrarMensaje("No se pudo registrar el pago");
//                    }
//                    actualizarTablaPagos();
//                }
//
//            }
//        });
//        agregarJComboBoxAColumna(tblLibroAuxiliar, 1, servicio.listarNombres().toArray());
//        aplicarEventosPersonalizados(tblLibroAuxiliar);
//    }
//    private void llenarTablaPagFiltro(long filtro) {
//
//        final String[] columnas = {"Codigo ", "Tipo", "Descipcion", "Credito", "Debito", "Fecha"};
//        //NombreIngresosDTO materialDTO=null;
//
//        // Configurando los eventos de edicion de la tabla
//        dtbmPagos = new DefaultTableModel(null, columnas) {
//            private static final long serialVersionUID = -4082996669139353200L;
//
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return column > 0 && column < 2;
//            }
//        };
//        // Llenando la tabla
//        servicio.listarIngreso(filtro).forEach((Ingresos_EgresosDTO pago) -> {
//            dtbmPagos.addRow(pago.toArray());
//
//        });
//        //Agregando el modelo de la tabla
////        tblLibroAuxiliar.setModel(dtbmPagos);
//
//        dtbmPagos.addTableModelListener((TableModelEvent e) -> {
//
//            long id, factura;
//            String descripcion;
//            double credito = 0, debito = 0;
//            NombreIngresosDTO materialDTO = null;
//            //long cuotas = 0;
//
//            //Obteniendo el objeto almacenado en la celda seleccionada
//            if (e.getType() == TableModelEvent.UPDATE) {
//                // Capturando los datos que contiene las celdas a editar
//
//                Object objId = tblLibroAuxiliar.getValueAt(e.getFirstRow(), 0);
//                Object objDes = tblLibroAuxiliar.getValueAt(e.getFirstRow(), 2);
//                Object objCredito = tblLibroAuxiliar.getValueAt(e.getFirstRow(), 3);
//                Object objDebito = tblLibroAuxiliar.getValueAt(e.getFirstRow(), 4);
//                //Object objnumero = tblPagosCompras.getValueAt(e.getFirstRow(), 4);
//                Object objIdN = tblLibroAuxiliar.getValueAt(e.getFirstRow(), 1);
//
//                if (objDes == null || objCredito == null || objDebito == null) {
//                    return;
//                }
//
//                if (!String.valueOf(objCredito).matches(REGEX_DECIMAL) || !String.valueOf(objDebito).matches(REGEX_DECIMAL)) {
//                    mostrarMensaje("Sólo se permiten números para el valor");
//                    return;
//                } else {
//                    credito = Double.parseDouble(String.valueOf(objCredito));
//                    debito = Double.parseDouble(String.valueOf(objDebito));
//                }
//
//                System.out.println(objIdN + "ingreso");
//
//                materialDTO = (NombreIngresosDTO) objIdN;
//                factura = materialDTO.getId();
//
//                descripcion = String.valueOf(objDes);
//                if (objCredito != null) {
//                    credito = Double.parseDouble(String.valueOf(objCredito));
//                }
//
//                if (objDebito != null) {
//                    debito = Double.parseDouble(String.valueOf(objDebito));
//
//                }
//                //FacturaDTO facturaDTO = (FacturaDTO) objFactura;
////                factura = facturaDTO.getId();
////                if (objnumero != null) {
////                    cuotas = Long.parseLong(String.valueOf(objnumero));
//////                }
//                if (objId != null) {
//                    id = Long.parseLong(String.valueOf(objId));
//
//                    if (servicio.editarIngreso(id, descripcion, credito, debito, factura)) {
//                        mostrarMensaje("Se ha editado el pago satisfactoriamente");
////                        long a = servicio.consultaTotalPagos(factura);
////                        servicio.registrarCuotasPagadas(factura, a);
////                        System.out.println("total" + a);
//
//                    } else {
//                        mostrarMensaje("No se pudo editar el pago");
//                    }
//                    actualizarTablaPagos();
//                } else {
//                    if (servicio.registrarIngreso(descripcion, credito, debito, factura)) {
//                        //double totalAbonado = compraDTO.getAbonado() + abono;
//                        //servicio.actualizarPagoProv(factura, totalAbonado);
////                        long a = servicio.consultaTotalPagos(factura);
////                        servicio.registrarCuotasPagadas(factura, a);
////                        System.out.println("total" + a);
//                        mostrarMensaje("Se ha registrado el pago satisfactoriamente");
//                    } else {
//                        mostrarMensaje("No se pudo registrar el pago");
//                    }
//                    actualizarTablaPagos();
//                }
//
//            }
//        });
//        agregarJComboBoxAColumna(tblLibroAuxiliar, 1, servicio.listarNombres().toArray());
//        aplicarEventosPersonalizados(tblLibroAuxiliar);
//    }
//
//    private void actualizarTablaPagos() {
//        tblLibroAuxiliar.removeAll();
//        dtbmPagos = null;
//        llenarTablaPagos();
//    }
//    private void llenarTablaCompras() {
//
//        final String[] columnas = {"Id ", "Descipcion", "Credito", "Debito", "Fecha", "Tipo"};
//
//        // Configurando los eventos de edicion de la tabla
//        dtblmCompras = new DefaultTableModel(null, columnas) {
//            private static final long serialVersionUID = -4082996669139353200L;
//
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return column > 0 && column != 4;
//            }
//        };
//        // Llenando la tabla
//        servicio.listar().forEach((Ingresos_EgresosDTO compra) -> {
//            dtblmCompras.addRow(compra.toArray());
//        });
//        //Agregando el modelo de la tabla
//        tblLibroAuxiliar.setModel(dtblmCompras);
//
//        dtblmCompras.addTableModelListener((TableModelEvent e) -> {
//
//            long id, material;
//            String descripcion;
//            Timestamp fecha;
//            double credito = 0, debito = 0;
//
//            //Obteniendo el objeto almacenado en la celda seleccionada
//            if (e.getType() == TableModelEvent.UPDATE) {
//                // Capturando los datos que contiene las celdas a editar
//
//                Object objId = tblLibroAuxiliar.getValueAt(e.getFirstRow(), 0);
//                Object objDescripcion = tblLibroAuxiliar.getValueAt(e.getFirstRow(), 1);
//                Object objCredito = tblLibroAuxiliar.getValueAt(e.getFirstRow(), 2);
//                Object objDebito = tblLibroAuxiliar.getValueAt(e.getFirstRow(), 3);
//                Object objFecha = tblLibroAuxiliar.getValueAt(e.getFirstRow(), 4);
//                Object objTipo = tblLibroAuxiliar.getValueAt(e.getFirstRow(), 5);
//
//                if (objDescripcion == null
//                        || objCredito == null
//                        || objDebito == null
//                        || objFecha == null
//                        || objTipo == null) {
//                    return;
//                }
//
//                NombreIngresosDTO materialDTO = (NombreIngresosDTO) objTipo;
//                material = materialDTO.getId();
//                descripcion = String.valueOf(objDescripcion);
//                credito = Double.parseDouble(String.valueOf(objCredito));
//                debito = Double.parseDouble(String.valueOf(objDebito));
//                //fecha=Timestamp.parseTimestamp(String.valueOf(objId));
//
//                // Calculando el valor total
//                if (objId != null) {
//                    id = Long.parseLong(String.valueOf(objId));
//
//                    if (servicio.editarIngreso(id, descripcion, credito, debito, material)) {
//                        //actualizarTablaMateriales();
//                        actualizarTablaProveedores();
//                        mostrarMensaje("Se ha editado el Ingreso satisfactoriamente");
//                    } else {
//                        mostrarMensaje("No se pudo editar el ingreso");
//                    }
//                } else {
//                    if (servicio.registrarIngreso(descripcion, credito, debito, material)) {
//                        //actualizarTablaMateriales();
//                        actualizarTablaProveedores();
//                        mostrarMensaje("Se ha registrado la ingreso satisfactoriamente");
//                    } else {
//                        mostrarMensaje("No se pudo registrar la ingreso");
//                    }
//                }
//
//            }
//        });
//
//        //agregarJComboBoxAColumna(tblLibroAuxiliar, 5, servicio.listarProveedores().toArray());
//        agregarJComboBoxAColumna(tblLibroAuxiliar, 5, servicio.listarNombres().toArray());
//
//        aplicarEventosPersonalizados(tblLibroAuxiliar);
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tpPestana = new javax.swing.JTabbedPane();
        panelIngresoNombre = new javax.swing.JPanel();
        spCompras = new javax.swing.JScrollPane();
        tblNombreIngreso = new TablaPersonalizada();
        btnNuevo = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        dateFechaEntrega1 = new com.github.lgooddatepicker.components.DatePicker();
        btnCerrar = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        cmbTipoMaterial2 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        txtTotalGasto = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        panelIngresoNombre1 = new javax.swing.JPanel();
        spCompras1 = new javax.swing.JScrollPane();
        tblNombreIngreso1 = new TablaPersonalizada();
        jButton3 = new javax.swing.JButton();
        btnCerrar1 = new javax.swing.JButton();
        txtTotalDebe = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        panelIngresoNombre2 = new javax.swing.JPanel();
        spCompras2 = new javax.swing.JScrollPane();
        tblNombreIngreso2 = new TablaPersonalizada();
        btnCerrar2 = new javax.swing.JButton();
        btnNuevo1 = new javax.swing.JButton();
        btnEliminar1 = new javax.swing.JButton();

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

        tpPestana.setBackground(new java.awt.Color(255, 255, 255));
        tpPestana.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tpPestanaMouseClicked(evt);
            }
        });

        panelIngresoNombre.setBackground(new java.awt.Color(255, 255, 255));

        tblNombreIngreso.setModel(new javax.swing.table.DefaultTableModel(
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
        spCompras.setViewportView(tblNombreIngreso);

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

        jButton2.setText("CIERRE DIARIO");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        dateFechaEntrega1.setEnabled(false);

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/SALIR.png"))); // NOI18N
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        jButton4.setText("Actualizar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        cmbTipoMaterial2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "CONTADO", "CUOTAS" }));
        cmbTipoMaterial2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoMaterial2ActionPerformed(evt);
            }
        });

        jLabel18.setText("TOTAL");

        javax.swing.GroupLayout panelIngresoNombreLayout = new javax.swing.GroupLayout(panelIngresoNombre);
        panelIngresoNombre.setLayout(panelIngresoNombreLayout);
        panelIngresoNombreLayout.setHorizontalGroup(
            panelIngresoNombreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIngresoNombreLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(spCompras, javax.swing.GroupLayout.DEFAULT_SIZE, 1214, Short.MAX_VALUE)
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIngresoNombreLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dateFechaEntrega1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jButton4)
                .addGap(7, 7, 7)
                .addComponent(cmbTipoMaterial2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelIngresoNombreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelIngresoNombreLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelIngresoNombreLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTotalGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(45, 45, 45))
        );
        panelIngresoNombreLayout.setVerticalGroup(
            panelIngresoNombreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIngresoNombreLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spCompras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelIngresoNombreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelIngresoNombreLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelIngresoNombreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminar)
                            .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelIngresoNombreLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(panelIngresoNombreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelIngresoNombreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelIngresoNombreLayout.createSequentialGroup()
                                    .addGap(14, 14, 14)
                                    .addComponent(dateFechaEntrega1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelIngresoNombreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbTipoMaterial2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTotalGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18))))
                        .addGap(0, 90, Short.MAX_VALUE))))
        );

        if (SESION_USUARIO.isVistaPermitida("COMPRAS")) {

            tpPestana.addTab("Nombre Ingreso/Egreso", panelIngresoNombre);
        }

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        panelIngresoNombre1.setBackground(new java.awt.Color(255, 255, 255));

        tblNombreIngreso1.setModel(new javax.swing.table.DefaultTableModel(
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
        spCompras1.setViewportView(tblNombreIngreso1);

        javax.swing.GroupLayout panelIngresoNombre1Layout = new javax.swing.GroupLayout(panelIngresoNombre1);
        panelIngresoNombre1.setLayout(panelIngresoNombre1Layout);
        panelIngresoNombre1Layout.setHorizontalGroup(
            panelIngresoNombre1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIngresoNombre1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(spCompras1, javax.swing.GroupLayout.DEFAULT_SIZE, 1214, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        panelIngresoNombre1Layout.setVerticalGroup(
            panelIngresoNombre1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIngresoNombre1Layout.createSequentialGroup()
                .addComponent(spCompras1, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                .addContainerGap())
        );

        if (SESION_USUARIO.isVistaPermitida("COMPRAS")) {

            jButton3.setText("Actualizar Tabla cierres");
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

            jLabel1.setText("Total Cierres");

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(430, 430, 430)
                    .addComponent(jButton3)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 273, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(txtTotalDebe, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(82, 82, 82)
                    .addComponent(btnCerrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelIngresoNombre1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(0, 524, Short.MAX_VALUE)
                    .addComponent(btnCerrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTotalDebe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap())
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(panelIngresoNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 123, Short.MAX_VALUE)))
            );

        }

        tpPestana.addTab("Cierres", jPanel1);

        panelIngresoNombre2.setBackground(new java.awt.Color(255, 255, 255));

        tblNombreIngreso2.setModel(new javax.swing.table.DefaultTableModel(
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
        spCompras2.setViewportView(tblNombreIngreso2);

        btnCerrar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/SALIR.png"))); // NOI18N
        btnCerrar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrar2ActionPerformed(evt);
            }
        });

        btnNuevo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/agregar.png"))); // NOI18N
        btnNuevo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevo1ActionPerformed(evt);
            }
        });

        btnEliminar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/zapateria/main/images/Eliminar.png"))); // NOI18N
        btnEliminar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelIngresoNombre2Layout = new javax.swing.GroupLayout(panelIngresoNombre2);
        panelIngresoNombre2.setLayout(panelIngresoNombre2Layout);
        panelIngresoNombre2Layout.setHorizontalGroup(
            panelIngresoNombre2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIngresoNombre2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(spCompras2, javax.swing.GroupLayout.DEFAULT_SIZE, 1214, Short.MAX_VALUE)
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIngresoNombre2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEliminar1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(316, 316, 316)
                .addComponent(btnCerrar2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        panelIngresoNombre2Layout.setVerticalGroup(
            panelIngresoNombre2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIngresoNombre2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spCompras2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addGroup(panelIngresoNombre2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCerrar2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar1)
                    .addComponent(btnNuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        if (SESION_USUARIO.isVistaPermitida("COMPRAS")) {

            tpPestana.addTab("Nombre Del Gasto", panelIngresoNombre2);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tpPestana)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tpPestana)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

        if (panelIngresoNombre.isShowing()) {
            dtblmProveedores.addRow(new Object[]{});

        }
//        if (PanelLibro.isShowing()) {
//            dtbmPagos.addRow(new Object[]{});
//        }

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        if (panelIngresoNombre.isShowing()) {
            int filaSeleccionada = tblNombreIngreso.getSelectedRow();

            if (filaSeleccionada < 0) {
                mostrarMensaje("Seleccione un Nombre de la tabla");
                return;
            }

            if (!mostrarDecision("¿Desea eliminar este nombre?")) {
                return;
            }

            Object idObj = tblNombreIngreso.getValueAt(filaSeleccionada, 0);
            Object pago = tblNombreIngreso.getValueAt(filaSeleccionada, 3);
            String p=String.valueOf(pago);
            p=p.replace(",",".");
            p=p.replace(".","");

            if (idObj == null) {
                dtblmProveedores.removeRow(filaSeleccionada);
                return;
            }
            double pagoE=Double.parseDouble(p);

            long id = Long.parseLong(String.valueOf(idObj));
            long ids=servicio.consultarUltimaCaja();  
            ArrayList<Ingresos_EgresosDTO>e=servicio.listarIngresoEliminar(ids);
            double caja=0;
            for (Ingresos_EgresosDTO i : e) {
                caja=i.getCaja();
            }
            if (servicio.eliminarNombre(id)) {
                servicio.editarCaja(ids, caja+pagoE);
//                llenarCombo();
                actualizarTablaProveedores();

                mostrarMensaje("Nombre eliminado satisfactoriamente");
            } else {
                mostrarMensaje("No se pudo eliminar el Nombre");
            }
        }
//
//        if (PanelLibro.isShowing()) {
//            int filaSeleccionada = tblLibroAuxiliar.getSelectedRow();
//
//            if (filaSeleccionada < 0) {
//                mostrarMensaje("Seleccione un proveedor de la tabla");
//                return;
//            }
//
//            if (!mostrarDecision("¿Desea eliminar este proveedor?")) {
//                return;
//            }
//
//            Object idObj = tblLibroAuxiliar.getValueAt(filaSeleccionada, 0);
//
//            if (idObj == null) {
//                dtblmCompras.removeRow(filaSeleccionada);
//                return;
//            }
//
//            long id = Long.parseLong(String.valueOf(idObj));
//
//            if (servicio.eliminarIngresoE(id)) {
//                actualizarTablaProveedores();
//                mostrarMensaje("ingreso eliminado satisfactoriamente");
//            } else {
//                mostrarMensaje("No se pudo eliminar el ingreso");
//            }
//        }
//
//        if (panelIngresoNombre.isShowing()) {
//            int filaSeleccionada = tblCompras.getSelectedRow();
//
//            if (filaSeleccionada < 0) {
//                mostrarMensaje("Seleccione una compra de la tabla");
//                return;
//            }
//
//            if (!mostrarDecision("¿Desea eliminar esta compra?")) {
//                return;
//            }
//
//            Object idObj = tblCompras.getValueAt(filaSeleccionada, 0);
//
//            if (idObj == null) {
//                dtblmCompras.removeRow(filaSeleccionada);
//                return;
//            }
//
//            long id = Long.parseLong(String.valueOf(idObj));
//
//            if (servicio.eliminarCompra(id)) {
//                actualizarTablaMateriales();
//                mostrarMensaje("Compra eliminada satisfactoriamente");
//            } else {
//                mostrarMensaje("No se pudo eliminar la compra");
//            }
//        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        cerrar();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
//llenarTablaProveedores();
        //llenarTablaCompras();
//        llenarTablaPagos();

    }//GEN-LAST:event_formInternalFrameOpened

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Date fechaDesde = Date.valueOf(dateFechaEntrega1.getDateStringOrSuppliedString(LocalDate.now().toString()));
        if (fechaDesde.toString().equalsIgnoreCase("")) {

            JOptionPane.showMessageDialog(null, "Seleccionar Fecha");
        } else {
            
            llenarTablaProveedoresFecha(fechaDesde);
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    DecimalFormat formateador = new DecimalFormat("###,###.###");
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        Date fechaDesde = Date.valueOf(LocalDate.now().toString());
        System.out.println("fecha desde"+fechaDesde);

        double gastos = 0;

        ArrayList<NombreIngresosDTO> l = servicio.listar(fechaDesde);
        for (NombreIngresosDTO n : l) {
            gastos = gastos + n.getPago();
        }
        double nomina = servicio.listarNominaFecha(fechaDesde);
        double liquidacion = servicio.listarLiquidacionTotal(fechaDesde);
        double abonados = servicio.listarAbonoFecha(fechaDesde);
        double entro = servicio.listarFacturaTotal(fechaDesde) + servicio.listarPagosTotal(fechaDesde) + abonados;
        double deuda = servicio.listarDeudaFecha(fechaDesde);
        double caja = entro - (gastos);
        mostrarMensaje("Abonados: " + formateador.format(abonados) + "\n"
                + "Prestamos: " + formateador.format(deuda) + "\n"
                + "Ingresos: " + formateador.format(entro) + "\n"
                + "Gastos: " + formateador.format(gastos) + "\n"
                + "Total Caja: " + formateador.format(caja)
        );
        boolean a = mostrarDecision("Desea cerrar caja");
        if (a) {
            Timestamp fecha=new Timestamp(System.currentTimeMillis());
            System.out.println(fecha+"fecha");
  //          Timestamp fecha=null;
//            System.out.println(fecha.getDate()+"fecha");
            boolean x = servicio.registrarIngreso(fecha,deuda, abonados, liquidacion, nomina, gastos, entro, caja);
            actualizarTablaCierres();
            //servicio.crearSQL();<
            if (x) {
                mostrarMensaje("Caja cerrada con exito");
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed
 public void totalCierres(){
     int filas=tblNombreIngreso1.getRowCount();
     int count=0;
     double totalCaja=0;
     while(count<filas){
         Object total= tblNombreIngreso1.getValueAt(count, 8);
         String to=String.valueOf(total);
         to=to.replace(",", ".");
         to=to.replace(".", "");
         double p=Double.parseDouble(to);
         
         totalCaja=totalCaja+p;
         
         count++;
     }
    
     txtTotalDebe.setText(formateador.format(totalCaja)+"");
 }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        actualizarTablaCierres();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnCerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrar1ActionPerformed
        cerrar();
    }//GEN-LAST:event_btnCerrar1ActionPerformed

    private void btnCerrar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrar2ActionPerformed
        cerrar();
    }//GEN-LAST:event_btnCerrar2ActionPerformed

    private void btnNuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo1ActionPerformed
        if (panelIngresoNombre2.isShowing()) {
            dtblmProveedores2.addRow(new Object[]{});

        }
    }//GEN-LAST:event_btnNuevo1ActionPerformed

    private void btnEliminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminar1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        llenarTablaProveedores();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void cmbTipoMaterial2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoMaterial2ActionPerformed
        if (cmbTipoMaterial2.getSelectedItem() != null) {
            String nombre = cmbTipoMaterial2.getSelectedItem().toString();
            NombreGastoDTO ingreso = servicio.consultarNombreN(nombre);
            //        System.out.println(ingreso.getId() + "ingreso");
            if (cmbTipoMaterial2.getSelectedItem().toString().equalsIgnoreCase("CAJA")) {
                llenarTablaProveedores();
            } else {
                llenarTablaProveedores(ingreso.getId());
            }

        }
        
    }//GEN-LAST:event_cmbTipoMaterial2ActionPerformed

    private void tpPestanaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tpPestanaMouseClicked
       if(tpPestana.getSelectedIndex()==1){
           llenarTablaCierres();
       }
       if(tpPestana.getSelectedIndex()==2){
           llenarTablaNombres();
       }
    }//GEN-LAST:event_tpPestanaMouseClicked
    public void llenarComboMaterial2() {

        cmbTipoMaterial2.removeAllItems();
        //cmbLibro.removeAll();
        ArrayList<NombreGastoDTO> listar = servicio.listarN();
        

        for (NombreGastoDTO n : listar) {
            cmbTipoMaterial2.addItem(n.getNombre());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnCerrar1;
    private javax.swing.JButton btnCerrar2;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminar1;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnNuevo1;
    private javax.swing.JComboBox<String> cmbTipoMaterial2;
    private com.github.lgooddatepicker.components.DatePicker dateFechaEntrega1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panelIngresoNombre;
    private javax.swing.JPanel panelIngresoNombre1;
    private javax.swing.JPanel panelIngresoNombre2;
    private javax.swing.JScrollPane spCompras;
    private javax.swing.JScrollPane spCompras1;
    private javax.swing.JScrollPane spCompras2;
    private javax.swing.JTable tblNombreIngreso;
    private javax.swing.JTable tblNombreIngreso1;
    private javax.swing.JTable tblNombreIngreso2;
    private javax.swing.JTabbedPane tpPestana;
    private javax.swing.JTextField txtTotalDebe;
    private javax.swing.JTextField txtTotalGasto;
    // End of variables declaration//GEN-END:variables
}
