/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.*;
import com.zapateria.main.interfaces.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Servicio implements java.io.Serializable {

    private static final long serialVersionUID = 5914241159610091585L;

    private final IUsuariosDAO usuariosDAO;
    private final IRolesDAO rolesDAO;
    private final IPermisosDAO permisosDAO;
    private final IVistasDAO vistasDAO;
    private final IProveedoresDAO proveedoresDAO;
    private final IMaterialesDAO materialesDAO;
    private final IComprasDAO comprasDAO;
    private final IClientesDAO clientesDAO;
    private final IMaterialesUsadosDAO materialesUsadosDAO;
    private final IProductosDAO productosDAO;
    private final IFacturasDAO facturasDAO;
    private final IVentasDAO ventasDAO;
    private final IPagosDAO pagosDAO;
    private final IImpuestosDAO impuestosDAO;
    private final IAbonoPrestamoDAO IAbonoPres;
    private final IPrestamoDAO prestamosDAO;
    private final IAbonoDAO abonoDAO;
    private final INominaDAO nominaDAO;
    private final ILiquidacionDAO liquidacion;
    private final IBasesDAO base;
    private final IPagosProvDAO PagosProvDAO;
    private final INombreIngresos INombresIngresos;
    private final IIngresos_Egresos IIngresos_e;
    private final IRegistroDAO IRegistro;
    private final INombreGastoDAO INombreGastoDAO;

    public Servicio() {
        usuariosDAO = new UsuariosDAO();
        rolesDAO = new RolesDAO();
        permisosDAO = new PermisosDAO();
        vistasDAO = new VistasDAO();
        proveedoresDAO = new ProveedoresDAO();
        materialesDAO = new MaterialesDAO();
        comprasDAO = new ComprasDAO();
        clientesDAO = new ClientesDAO();
        materialesUsadosDAO = new MaterialesUsadosDAO();
        productosDAO = new ProductosDAO();
        facturasDAO = new FacturasDAO();
        ventasDAO = new VentasDAO();
        pagosDAO = new PagosDAO();
        impuestosDAO = new ImpuestosDAO();
        IAbonoPres = new Abono_PrestamoDAO();
        prestamosDAO = new PrestamoDAO();
        abonoDAO = new AbonoDAO();
        nominaDAO = new NominaDAO();
        liquidacion = new LiquidacionDAO();
        base = new BaseDAO();
        PagosProvDAO = new PagosProvDAO();
        INombresIngresos = new NombreIngresosDAO();
        IIngresos_e = new Ingresos_EgresosDAO();
        IRegistro = new RegistroDAO();
        INombreGastoDAO= new NombreGastoDAO();
    }
    public boolean Material(long id, long fecha){
        return IRegistro.Material(id, fecha);
    }
    public boolean eliminarPrestamoCliente(long id){
        return prestamosDAO.eliminar(id);
    }
    public long consultaTotalProductos(long id){
        return facturasDAO.consultaTotalProductos(id);
    }
    public ArrayList<UsuarioDTO> buscarNombre(String busqueda){
        return usuariosDAO.buscarNombre(busqueda);
    }
    
    public boolean clave(String password){
        return usuariosDAO.clave(password);
    }
    
    public boolean registrarN(String nombre){
        return INombreGastoDAO.registrar(nombre);
    }
     public NombreGastoDTO consultarN(long id){
         return INombreGastoDAO.consultar(id);
     }
     public NombreGastoDTO consultarNombreN(String nombre){
         return INombreGastoDAO.consultarNombre(nombre);
     }
     public ArrayList<NombreGastoDTO> listarN(){
         return  INombreGastoDAO.listar();
     }
     public boolean editarN(long id, String nombre){
         return INombreGastoDAO.editar(id, nombre);
     }
     public boolean eliminarN(long id){
         return INombreGastoDAO.eliminar(id);
     }
    
    public ArrayList<PagosProv> listarPagoProveedor(long id){
        return PagosProvDAO.listarProveedor(id);
    }
    public ArrayList<NombreIngresosDTO> listarIngre(long id){
        return INombresIngresos.listar(id);
    }

    //VentaRegistro
    public boolean registrarRegistro(long id_usuario, long id_cliente, long id_material, double precio, long cantidad, String comentario,
            String estado, Date fecha_entrega, double iva, double total_pagar) {
        return IRegistro.registrar(id_usuario,
                id_cliente, id_material, precio, cantidad, comentario, estado, fecha_entrega, iva, total_pagar);
    }

    public ArrayList<RegistroDTO> listar2() {
        return IRegistro.listar();
    }
    public ArrayList<RegistroDTO> listarRegistroFecha(Date fechaD, Date fechaH){
        return IRegistro.listarRegistroFecha(fechaD, fechaH);
    }
     public boolean cambiarComentario(long id, String estado){
         return IRegistro.cambiarComentario(id, estado);
     }

    public ArrayList<RegistroDTO> listarVentasCliente(long id) {
        return IRegistro.listarVentasCliente(id);
    }
    public ArrayList<RegistroDTO> listarVentasClienteRemision(long id){
        return IRegistro.listarVentasClienteRemision(id);
    }
    public boolean eliminarRegistro(long id) {
        return IRegistro.eliminarRegistro(id);
    }
    

    public boolean cambiarEstado(long id, String estado) {
        return IRegistro.cambiarEstado(id, estado);
    }
    public boolean BorrarAbono_Prestamo(long id){
        return IAbonoPres.BorrarAbono_Prestamo(id);
    }
     public boolean BorrarNominas(long id){
         return nominaDAO.BorrarNominas(id);
     }
     public boolean BorrarLiquidaciones(long id){
         return liquidacion.BorrarLiquidaciones(id);
     }
    public boolean cambiarImpresion(long id, long estado) {
        return IRegistro.cambiarImpresion(id, estado);
    }

    public boolean cambiarIdFactura(long id, long factura) {
        return IRegistro.cambiarIdFactura(id, factura);
    }
    public boolean cambiarIdFacturasCero(long id, long factura){
        return IRegistro.cambiarIdFacturasCero(id, factura);
    }
    public boolean editarPrecio(long id, double precio,double total,double iva,long cantidad){
        return IRegistro.editarPrecio(id, precio, total, iva,cantidad);
    }
    public boolean registrarContado(long id_usuario, long id_cliente, long id_material, double precio, long cantidad, String comentario,
            String estado, String forma_pago, long numero_cuotas, long cuotas_pagadas, Date fecha_entrega, double iva, double total_pagar, double total_pagado) {
        return IRegistro.registrarContado(id_usuario, id_cliente, id_material, precio, cantidad,
                comentario, estado, forma_pago, numero_cuotas, cuotas_pagadas, fecha_entrega, iva, total_pagar, total_pagado);
    }
    public boolean editarIvaFactura(long id, String idF){
        return facturasDAO.editarIvaFactura(id, idF);
    }

    public ArrayList<RegistroDTO> listarIngresos(Date FechaD, Date FechaH) {
        return IRegistro.listarIngresos(FechaD, FechaH);
    }

    // Consultas de usuarios
    public boolean validar(String usuario, String contrasena) {
        return usuariosDAO.validar(usuario, contrasena);
    }

    public ArrayList<VentaDTO> listarIngresos2(Date FechaD, Date FechaH) {
        return ventasDAO.listarIngresos(FechaD, FechaH);
    }
    
    public ArrayList<FacturaDTO> listarIngresosF(Date FechaD, Date FechaH){
        return facturasDAO.listarIngresos(FechaD, FechaH);
    }

    public boolean registrarUsuario(String nombres, String apellidos, String documento,
            String nombreUsuario, String contrasena, long rol,String telefono,String direccion) {
        return usuariosDAO.registrar(nombres, apellidos, documento, nombreUsuario,
                contrasena, rol,telefono,direccion);
    }
     public long consultarUltimoUsuario(){
         return usuariosDAO.consultarUltimoUsuario();
     }

    public boolean editarUsuario(long id, String nombres, String apellidos,
            String documento, String nombreUsuario, long rol,String telefono,String direccion) {
        return usuariosDAO.editar(id, nombres, apellidos, documento,
                nombreUsuario, rol,telefono,direccion);
    }

    public boolean cambiarContrasena(long id, String contrasena) {
        return usuariosDAO.editar(id, contrasena);
    }

    public boolean eliminarUsuario(long id) {
        return usuariosDAO.eliminar(id);
    }

    public UsuarioDTO consultarUsuario(long id) {
        return usuariosDAO.consultar(id);
    }

    public ArrayList<UsuarioDTO> listarUsuarios() {
        return usuariosDAO.listar();
    }

    public ArrayList<UsuarioDTO> listarUsuariosNomina() {
        return usuariosDAO.listarUsuariosNomina();
    }

    public ArrayList<NominaDTO> listarNominas() {
        return nominaDAO.listarNominas();
    }

    public ArrayList<NominaDTO> listarNomin() {
        return nominaDAO.listarNomin();
    }

    public ArrayList<LiquidacionDTO> listarLiquidaciones() {
        return liquidacion.listarLiquidaciones();
    }

    public ArrayList<LiquidacionDTO> listarLiquida() {
        return liquidacion.listarLiquida();
    }

    public ArrayList<Abono_PrestamoDTO> listarDebe() {
        return IAbonoPres.listarDebe();
    }

    public UsuarioDTO buscarUsuarioCedula(String cedula) {
        return usuariosDAO.buscarUsuarioCedula(cedula);
    }

    // Consultas de roles
    public boolean registrarRol(String rol, double valor) {
        return rolesDAO.registrar(rol, valor);
    }

    public RolDTO consultarRol(long id) {
        return rolesDAO.consultar(id);
    }

    public boolean editarRol(long id, String rol, double valor) {
        return rolesDAO.editar(id, rol, valor);
    }

    public ArrayList<RolDTO> listarRoles() {
        return rolesDAO.listar();
    }

    public boolean eliminarRol(long id) {
        return rolesDAO.eliminar(id);
    }

    // Consultas de permisos
    public boolean registrarPermiso(long rol, long vista) {
        return permisosDAO.registrar(rol, vista);
    }

    public ArrayList<PermisoDTO> listarPermisos() {
        return permisosDAO.listar();
    }

    public boolean editarPermiso(long id, long rol, long vista) {
        return permisosDAO.editar(id, rol, vista);
    }

    public boolean eliminarPermiso(long id) {
        return permisosDAO.eliminar(id);
    }

    // Consultas para vistas
    public boolean registrarVista(String vista) {
        return vistasDAO.registrar(vista);
    }

    public VistaDTO consultarVista(long id) {
        return vistasDAO.consultar(id);
    }

    public boolean editarVista(long id, String vista) {
        return vistasDAO.editar(id, vista);
    }

    public ArrayList<VistaDTO> listarVistas() {
        return vistasDAO.listar();
    }

    public boolean eliminarVista(long id) {
        return vistasDAO.eliminar(id);
    }
    
    public boolean EliminarSquemaSQL(){
        return base.EliminarSquemaSQL();
    }

    // Consultas para proveedores
    public boolean registrarProveedor(String nombre, String nit, String direccion, String telefono) {
        return proveedoresDAO.registrar(nombre, nit, direccion, telefono);
    }

    public ProveedorDTO consultarProveedor(long id) {
        return proveedoresDAO.consultar(id);
    }

    public ArrayList<ProveedorDTO> listarProveedores() {
        return proveedoresDAO.listar();
    }

    public boolean editarProveedor(long id, String nombre, String nit, String direccion, String telefono) {
        return proveedoresDAO.editar(id, nombre, nit, direccion, telefono);
    }

    public boolean eliminarProveedor(long id) {
        return proveedoresDAO.eliminar(id);
    }

    //consultas nombres de ingresos 
    public boolean registrarNombreIngreso(Timestamp fecha,String nombre,long id ,double pago) {
        return INombresIngresos.registrar(fecha,nombre, id,pago);
    }
    public boolean registrar2(String nombre,long id_n ,double pago,Date fecha){
        return INombresIngresos.registrar2(nombre, id_n, pago, fecha);
    }

    public NombreIngresosDTO consultarNombreIngreso(String nombre) {
        return INombresIngresos.consultarNombre(nombre);
    }

    public NombreIngresosDTO consultarNombre(long id) {
        return INombresIngresos.consultar(id);
    }

    public ArrayList<NombreIngresosDTO> listarNombres() {
        return INombresIngresos.listar();
    }

    public ArrayList<NombreIngresosDTO> listar(Date fecha) {
        return INombresIngresos.listar(fecha);
    }

    public boolean editarNombre(long id,long nombreg ,String nombre ,double pago) {
        return INombresIngresos.editar(id, nombreg, nombre, pago);
    }
    public boolean editarPrecio(long id, double pago){
        return INombresIngresos.editarPrecio(id, pago);
    }

    public boolean eliminarNombre(long id) {
        return INombresIngresos.eliminar(id);
    }

    // Consultas para materiales
    public boolean registrarMaterial(String nombre, String patron) {
        return materialesDAO.registrar(nombre, patron);
    }

    public MaterialDTO consultarMaterial(long id) {
        return materialesDAO.consultar(id);
    }

    public ArrayList<MaterialDTO> listarMateriales() {
        return materialesDAO.listar();
    }

    public boolean editarMaterial(long id, String nombre, String patron) {
        return materialesDAO.editar(id, nombre, patron);
    }

//    public boolean editarMaterial(long id, long cantidad) {
//        return materialesDAO.editar(id, cantidad);
//    }
    public boolean eliminarMaterial(long id) {
        return materialesDAO.eliminar(id);
    }

    public MaterialDTO consultarMaterialNombre(String nombre) {
        return materialesDAO.consultarNombreM(nombre);
    }
     public ProveedorDTO consultarNombr(String nombre){
         return proveedoresDAO.consultarNombre(nombre);
     }
     public ArrayList<CompraDTO> listarId(long id){
         return comprasDAO.listarId(id);
     }

    //consultas para ingresos e egresos
    public boolean eliminarIngresoE(long id) {
        return IIngresos_e.eliminar(id);
    }

    public ArrayList<Ingresos_EgresosDTO> listarIngresoEliminar(long id) {
        return IIngresos_e.listarIngreso(id);
    }

//    public boolean editarIngreso(long id, String descripcion, double credito, double debito, long id_n) {
//        return IIngresos_e.editar(id, descripcion, credito, debito, id_n);
//    }

    public ArrayList<Ingresos_EgresosDTO> listar() {
        return IIngresos_e.listar();
    }
    public int horasTrabajados(long id_us){
        return nominaDAO.HorasTrabajadas(id_us);
    }

    public boolean registrarIngreso(Timestamp fecha,double deudas,double abonado, double liquidacion, double nominas, double salio, double entro, double caja) {
        return IIngresos_e.registrar(fecha,deudas, abonado, liquidacion, nominas, salio, entro, caja);
    }
    public boolean editarCaja(long id,double caja){
        return IIngresos_e.editarCaja(id, caja);
    }

    public Ingresos_EgresosDTO consultar(long id) {
        return IIngresos_e.consultar(id);
    }
    public boolean ActualizarPago(long id, double pago){
        return facturasDAO.ActualizarPago(id, pago);
    }

    // Consultas para compras
    public boolean registrarCompra(long cantidad, double valorUnitario, double valorTotal,
            long proveedor, long material) {
        return comprasDAO.registrar(cantidad, valorUnitario, valorTotal, proveedor, material);
    }
     public double  listarFacturaTotal(Date fecha){
         return facturasDAO.listarFacturaTotal(fecha);
     }
    public CompraDTO consultarCompra(long id) {
        return comprasDAO.consultar(id);
    }

    public ArrayList<CompraDTO> listarCompras() {
        return comprasDAO.listar();
    }

    public boolean editarCompra(long id, long cantidad, double valorUnitario, double valorTotal,
            long proveedor, long material) {
        return comprasDAO.editar(id, cantidad, valorUnitario, valorTotal, proveedor, material);
    }
    public double  listarDeudaFecha(Date fecha){
        return prestamosDAO.listarDeudaFecha(fecha);
    }

    public boolean eliminarCompra(long id) {
        return comprasDAO.eliminar(id);
    }

    public long registrarClien(String documento, String nombres, String apellidos,
            String direccion, String telefono) {
        return clientesDAO.registrarCliente(documento, nombres, apellidos, direccion, telefono);
    }

    public boolean validarCedula(String cedula) {
        return clientesDAO.validarCedula(cedula);
    }

    // Consultas para clientes 
    public boolean registrarCliente(String documento, String nombres, String apellidos, String direccion, String telefono) {
        return clientesDAO.registrar(documento, nombres, apellidos, direccion, telefono);
    }

    public boolean registrarCliente(String documento, String nombres, String apellidos) {
        return clientesDAO.registrar(documento, nombres, apellidos);
    }

    public ClienteDTO consultarCliente(long id) {
        return clientesDAO.consultar(id);
    }
    public ClienteDTO consultarCliendeDeuda(long id) {
        return clientesDAO.consultarCliendeDeuda(id);
    }

    public boolean editarCliente(long id, String documento, String nombres,
            String apellidos, String direccion, String telefono) {

        return clientesDAO.editar(id, documento, nombres, apellidos, direccion, telefono);
    }

    public boolean editarCliente(long id, String documento, String nombres, String apellidos) {
        return clientesDAO.editar(id, documento, nombres, apellidos);
    }

    public ArrayList<ClienteDTO> listarClientes() {
        return clientesDAO.listar();
    }

    public ClienteDTO buscarClienteCedula(String cedula) {
        return clientesDAO.buscarClienteCedula(cedula);
    }

    public ArrayList<ClienteDTO> buscarCliente(String busqueda) {
        return clientesDAO.buscar(busqueda);
    }

    public boolean eliminarCliente(long id) {
        return clientesDAO.eliminar(id);
    }

    // Consultas para los Materiales Usados
    public boolean registrarMaterialUsado(long cantidad, long material) {
        return materialesUsadosDAO.registrar(cantidad, material);
    }

    public MaterialUsadoDTO consultarMaterialUsado(long id) {
        return materialesUsadosDAO.consultar(id);
    }

    public ArrayList<MaterialUsadoDTO> listarMaterialesUsados() {
        return materialesUsadosDAO.listar();
    }

    public boolean eliminarMaterialUsado(long id) {
        return materialesUsadosDAO.eliminar(id);
    }

    public ArrayList<MaterialDTO> buscar(String busqueda) {
        return materialesDAO.buscar(busqueda);
    }

    // Consultas para productos
    public boolean registrarProducto(String nombre, double precioUnitario) {
        return productosDAO.registrar(nombre, precioUnitario);
    }

    public ProductoDTO consultarProducto(long id) {
        return productosDAO.consultar(id);
    }

    public ArrayList<ProductoDTO> listarProductos() {
        return productosDAO.listar();
    }

    public ArrayList<ProductoDTO> buscarProducto(String busqueda) {
        return productosDAO.buscar(busqueda);
    }

    public boolean editarProducto(long id, String nombre, double precioUnitario) {
        return productosDAO.editar(id, nombre, precioUnitario);
    }

    public boolean eliminarProducto(long id) {
        return productosDAO.eliminar(id);
    }

    // Consultas para facturas
    public boolean registrarFactura(String formaDePago, double iva,
            double totalPagar, double totalPagado,
            Date fechaEntrega, long cliente, long usuario, long cuota) {

        return facturasDAO.registrar(formaDePago, iva, totalPagar,
                totalPagado, fechaEntrega, cliente, usuario, cuota);
    }

//    public boolean registrar2(double iva,
//            double totalPagar, double totalPagado,
//            long cliente, long usuario) {
//        return facturasDAO.registrar2(iva, totalPagar, totalPagado, cliente, usuario);
//    }

    public boolean registrarFacturaFa(String formaDePago, double iva,
            double totalPagar, double totalPagado,
            Date fechaEntrega, long cliente, long usuario, long cuota) {

        return facturasDAO.registrarFa(formaDePago, iva, totalPagar,
                totalPagado, fechaEntrega, cliente, usuario, cuota);
    }

    public ArrayList<FacturaDTO> listarFactuNomina(Date fechaD, Date fechaH) {
        return facturasDAO.listarFechaNomi(fechaD, fechaH);
    }

    public FacturaDTO consultarFactura(long id) {
        return facturasDAO.consultar(id);
    }

    //Listar los usuarios con esa factura 
    public ArrayList<FacturaDTO> listarUsuarioFactura(long cedula) {
        return facturasDAO.listarUsuarioFactura(cedula);
    }

    public double consultaTotalPagarPagado(long id) {
        return facturasDAO.consultaTotalPagarPagado(id);
    }

    public ArrayList<FacturaDTO> filtroMateriales(long id) {
        return facturasDAO.filtroMateriales(id);
    }

    //Listar Facturas por fechas
    public ArrayList<FacturaDTO> listarFechaFac(Date FechaD, Date FechaH) {
        return facturasDAO.listarFechasFac(FechaD, FechaH);
    }

    public ArrayList<NominaDTO> listarFechaNomi(Date fechaD, Date fechaH) {
        return nominaDAO.listarFechaNomi(fechaD, fechaH);
    }

    public ArrayList<NominaDTO> buscarCedulaNomina(long cedula) {
        return nominaDAO.listarCedulaNomi(cedula);
    }

    public ArrayList<NominaDTO> listarCedulaNomin(String cedula) {
        return nominaDAO.listarCedulaNomin(cedula);
    }

    public double totalNomina(long cedula) {
        return nominaDAO.totalNomina(cedula);
    }

    public ArrayList<Abono_PrestamoDTO> listarDebeCedula(long id) {
        return IAbonoPres.listarDebeCedula(id);
    }

    public ArrayList<AbonoDTO> buscarCedulaAbono(long cedula) {
        return abonoDAO.buscarCedulaAbono(cedula);
    }

    public ArrayList<LiquidacionDTO> buscarLiquidacionEmpleado(long cedula) {
        return liquidacion.buscarLiquidacionEmpleado(cedula);
    }

    public ArrayList<LiquidacionDTO> listarFechaLiquidacion(Date fechaD, Date fechaH) {
        return liquidacion.listarFechaLiquidacion(fechaD, fechaH);
    }

    public ArrayList<PrestamoDTO> buscarCedulaAdelanto(long cedula) {
        return prestamosDAO.buscarCedulaAdelanto(cedula);
    }

    public ArrayList<FacturaDTO> buscarFactura(long id) {
        return facturasDAO.listarFactura(id);
    }
    public boolean editarRemision(long id, double total, double iva){
        return facturasDAO.editarRemision(id, total, iva);
    }

    public ArrayList<FacturaDTO> listarFechaFact(Date Fecha) {
        return facturasDAO.listarFechasFact(Fecha);
    }

    public ArrayList<FacturaDTO> listarEstadosFactura(String estado) {
        return facturasDAO.listarEstadosFactura(estado);
    }

    //Listar los usuarios con esa factura
    public ArrayList<FacturaDTO> listarEstadoFactura(String estado) {
        return facturasDAO.listarEstadosFactura(estado);
    }

    public ArrayList<FacturaDTO> listarPagoFactura(String estado) {
        return facturasDAO.listarPagoFactura(estado);
    }

//    public ArrayList<FacturaDTO> listarFacturas() {
//        return facturasDAO.listar();
//    }

    public ArrayList<FacturaDTO> buscarFactura(String busqueda) {
        return facturasDAO.buscar(busqueda);
    }

    public boolean editarFactura(long id, String formaDePago, double iva,
            double totalPagar, double totalPagado,
            Date fechaEntrega, long cliente, long usuario) {

        return facturasDAO.editar(id, formaDePago, iva, totalPagar,
                totalPagado, fechaEntrega, cliente, usuario);
    }

    public boolean editarFactura(long id, String formaDePago, String estado) {
        return facturasDAO.editar(id, formaDePago, estado);
    }

    public boolean editarFechaFactura(long id, Date fecha) {
        return facturasDAO.editarFechaFactura(id, fecha);
    }

    public boolean editarFechaRegistro(long id, Date fecha) {
        return IRegistro.editarFechaRegistro(id, fecha);
    }

    public boolean cambiarEstadoFactura(long id, String estado) {
        return facturasDAO.cambiarEstado(id, estado);
    }

    public boolean cambiarFormaDePago(long id, String formaDePago) {
        return IRegistro.cambiarFormaDePago(id, formaDePago);
    }

    public boolean cambiarFormaDePagoFactura(long id, String formaDePago) {
        return facturasDAO.cambiarFormaDePago(id, formaDePago);
    }

    public boolean eliminarFactura(long id) {
        return facturasDAO.eliminar(id);
    }
    public boolean eliminarPagos(long id){
        return pagosDAO.eliminarPagos(id);
    }
    
    public boolean eliminarRegistros(long id) {
        return IRegistro.eliminarRegistros(id);
    }

//    public long consultarIncrementoFacturas() {
//        return facturasDAO.consultarIncremento();
//    }

    // Consultas para ventas 
//    public boolean registrarVenta(long cantidad, String comentario, long factura, long producto, double precio, String estado) {
//        return ventasDAO.registrar(cantidad, comentario, factura, producto, precio, estado);
//    }
//
//    public boolean registrarVenta2(long cantidad, String comentario, long factura, long producto, double precio, String estado) {
//        return ventasDAO.registrarFa(cantidad, comentario, factura, producto, precio, estado);
//    }
//
//   public boolean registrarRegistroRemision(long id_usuario, long id_cliente, long id_factura,long id_re) {
//        return IRegistro.registrarRegistroRemision(id_usuario, id_cliente, id_factura,id_re);
//    }
//
//    public VentaDTO consultarVenta(long id) {
//        return ventasDAO.consultar(id);
//    }
//
//    public ArrayList<VentaDTO> listarVentasClien(long id) {
//        return ventasDAO.listarVentasCliente(id);
//    }
//
//    public ArrayList<VentaDTO> listarVentas() {
//        return ventasDAO.listar();
//    }
//
//    public ArrayList<VentaDTO> listarVentas(long factura) {
//        return ventasDAO.listar(factura);
//    }
////    public ArrayList<VentaDTO> filtroMateriales(long id){
//    //      return ventasDAO.filtroMateriales(id);
//    //}
//
//    public boolean editarVenta(long id, String comentario, long cantidad, long factura, long producto, double precio, String estado) {
//        return ventasDAO.editar(id, cantidad, comentario, factura, producto, precio, estado);
//    }
//
//    public boolean editarVenta(long id, long cantidad) {
//        return ventasDAO.editar(id, cantidad);
//    }
//
//    public boolean eliminarVenta(long id) {
//        return ventasDAO.eliminar(id);
//    }
    public ArrayList<VentaDTO> listarRegistros(long id) {
        return ventasDAO.listar(id);
    }

    //Consultas para pagos
    public boolean registrarPago(double abono, long factura) {
        return pagosDAO.registrar(abono, factura);
    }

    public boolean registrarPagoProv(double abono, long factura) {
        return PagosProvDAO.registrar(abono, factura);
    }

    public boolean actualizarPagoProv(long factura, double abono) {
        return PagosProvDAO.actualizarPago(factura, abono);
    }

    public boolean actualizarPago(long factura, double abono) {
        return pagosDAO.actualizarPago(factura, abono);
    }

    public ArrayList<RegistroDTO> listarRegistro(long id) {
        return IRegistro.listarRegistro(id);
    }

    public ArrayList<RegistroDTO> listar(long id) {
        return IRegistro.listar(id);
    }

    public ArrayList<PagoDTO> listarFechaAbo(Date fechaD, Date fechaH) {
        return pagosDAO.listarFechaAbo(fechaD, fechaH);
    }

    public PagoDTO consultarPago(long id) {
        return pagosDAO.consultar(id);
    }

    public ArrayList<PagoFacDTO> listarPagos() {
        return pagosDAO.listar();
    }
    public ArrayList<PagoFacDTO> listarNombrePagos(long id) {
        return pagosDAO.listarNombrePagos(id);
    }

    public ArrayList<PagosProv> listarPagosProv() {
        return PagosProvDAO.listar();
    }

    public boolean editarPago(long id, double abono, long factura, long cuota) {
        return pagosDAO.editar(id, abono, factura, cuota);
    }

    public boolean editar(long id, double abono, long factura) {
        return PagosProvDAO.editar(id, abono, factura);
    }
    public double total(long id){
        return PagosProvDAO.total(id);
    }
    public double itemTotalFacturas(long id){
        return IRegistro.itemTotalFacturas(id);
    }
    
    public double IvaTotalFacturas(long id){
        return IRegistro.IvaTotalFacturas(id);
    }

    public boolean eliminarPago(long id) {
        return pagosDAO.eliminar(id);
    }

    public long consultaTotalPagos(long id) {
        return pagosDAO.consultaTotalPagos(id);
    }

    // Consultas para los impuestos
    public boolean registrarImpuesto(String nombre, double valor) {
        return impuestosDAO.registrar(nombre, valor);
    }

    public ImpuestoDTO consultarImpuesto(long id) {
        return impuestosDAO.consultar(id);
    }

    public ArrayList<ImpuestoDTO> listarImpuestos() {
        return impuestosDAO.listar();
    }

    public boolean editarImpuesto(long id, String nombre, double valor) {
        return impuestosDAO.editar(id, nombre, valor);
    }

    public boolean eliminarImpuesto(long id) {
        return impuestosDAO.eliminar(id);
    }

    public boolean registrarAdelanto(long id, double valor, Date fecha, String concepto) {
        return prestamosDAO.registrarAdelanto(id, valor, fecha, concepto);
    }

    public double BuscarValor(long id) {
        return IAbonoPres.Buscarvalor(id);
    }

    public RolDTO consultarNombre(String nombre) {
        return rolesDAO.consultarNombre(nombre);
    }

    public boolean registrarNomina(long id, double valorHora, double pagoTot, Date fechaInicio, Date fechaFin, int horasTrabajadas, int diasTrabajados, long horas, double abonado) {
        System.out.println("2");
        return nominaDAO.registrarNomina(id, valorHora, pagoTot, fechaInicio, fechaFin, horasTrabajadas, diasTrabajados, horas, abonado);
    }
    public long consultarUltimaNomina(){
        return nominaDAO.consultarUltimaNomina();
    }

    public boolean crearAbonoUsuario(long id, double total, double total_debe, double total_abono) {
        return IAbonoPres.crearAbonoUsuario(id, total, total_debe, total_abono);
    }

    public Abono_PrestamoDTO buscarAbonoPrestamo(long id) {
        return IAbonoPres.buscarAbonoPrestamo(id);
    }

    public boolean actualizarAbonoPrestamo(long abo, double prestamo, double total_debe) {
        return IAbonoPres.actualizarAbonoPrestamo(abo, prestamo, total_debe);
    }

    public boolean registrarAbono(long usuario, double valor, Date fecha) {
        return abonoDAO.registrarAbono(usuario, valor, fecha);

    }

    public boolean actualizarAbonoPresta(long id, double total_abonado, double total_debe) {
        return IAbonoPres.actualizarAbonoPresta(id, total_abonado, total_debe);
    }

    public boolean registrarLiquidacion(long id_usuario, Date fecha_salida, int horas_Trabajadas, int dias_trabajados,
            Date fecha_ingreso, double sueldo_basico,
            double cesantias, double intereses_cesantias, double prima,
            double vacaciones, double valor_liquidacion, double bonificacion) {
        return liquidacion.registrarLiquidacion(id_usuario, fecha_salida, horas_Trabajadas,
                dias_trabajados, fecha_ingreso, sueldo_basico, cesantias,
                intereses_cesantias, prima, vacaciones, valor_liquidacion, bonificacion);
    }

    public UsuarioDTO buscarFechaIngreso(long cedula) {
        return usuariosDAO.buscarFechaIngreso(cedula);
    }

    public ArrayList<UsuarioDTO> buscarUsuario(String busqueda) {
        return usuariosDAO.buscarNombre(busqueda);
    }

    public int diasTrabajados(long id_us) {
        return nominaDAO.diasTrabajados(id_us);
    }

    public boolean registrarRemision(double iva, double totalPagado, double totalPagar, long cliente, long usuario, String forma) {
        return facturasDAO.registrarRemision(iva, totalPagado, totalPagar, cliente, usuario, forma);
    }

    public ArrayList<FacturaDTO> listarMaterialFactura(long id) {
        return facturasDAO.listarMaterialFactura(id);
    }

    public boolean registrarRemisionIva(double iva, double totalPagado, double totalPagar, long cliente, long usuario, String forma, String factura) {
        return facturasDAO.registrarRemisionIva(iva, totalPagado, totalPagar, cliente, usuario, forma, factura);
    }

    public long consultarIncrementoRemision() {
        return facturasDAO.consultarIncrementoRemision();
    }

    public int HorasTrabajadas(long id_us) {
        return nominaDAO.HorasTrabajadas(id_us);
    }

    public int AusenteDias(long id_us) {
        return nominaDAO.AusenteDias(id_us);
    }

    public int cantidadAbono() {
        return abonoDAO.cantidadAbono();
    }

    public ArrayList<AbonoDTO> listarFechaAbon(Date fechaD, Date fechaH) {
        return abonoDAO.listarFechaAbon(fechaD, fechaH);
    }

    public int cantidadAbonoPrestamo() {
        return IAbonoPres.cantidadAbono_Prestamo();
    }

    public int cantidadPrestamo() {
        return prestamosDAO.cantidadPrestamo();
    }

    public int cantidadLiquidacion() {
        return liquidacion.cantidadliqidacion();
    }

    public int cantidadNomina() {
        return nominaDAO.cantidadNomina();
    }

    public boolean validarCedulaUsuario(String cedula) {
        return usuariosDAO.validarCedula(cedula);
    }

    public boolean validarUsuario(String usuario) {
        return usuariosDAO.validarUsuario(usuario);
    }

    public boolean registrarCuotasPagadas(long id, long cuota) {
        return facturasDAO.registrarCuotasPagadas(id, cuota);
    }

    public long consultarUltimaFactura() {
        return facturasDAO.consultarUltimaFactura();
    }
    public long consultarUltimaCaja(){
        return IIngresos_e.consultarUltimaCaja();
    }

    public boolean crearSQL() {
        return base.crearSQL();
    }
    public boolean LimpiarLibro(){
        return base.LimpiarLibro();
    }
    public boolean LimpiarComprasP(){
        return base.LimpiarComprasP();
    }
    public boolean LimpiarPagosP(){
        return base.LimpiarPagosP();
    }

    public boolean LimpiarBase() {
        return base.LimpiarBase();
    }
    public boolean LimpiarCierres(){
        return  base.LimpiarCierres();
    }
    public boolean LimpiarIngresos(){
        return base.LimpiarIngresos();
    }
    public boolean LimpiarCompras(){
        return base.LimpiarCompras();
    }

    public boolean LimpiarAbonoUsuario() {
        return base.LimpiarAbonoUsuario();
    }

    public boolean LimpiarVentas2() {
        return base.LimpiarVentas2();
    }

    public boolean LimpiarFacturas2() {
        return base.LimpiarFacturas2();
    }

    public boolean LimpiarFacturas() {
        return base.LimpiarFacturas();
    }

    public boolean LimpiarRegistro() {
        return base.LimpiarRegistro();
    }

    public boolean LimpiarRegistro2() {
        return base.LimpiarRegistro2();
    }

    public boolean LimpiarVentas() {
        return base.LimpiarVentas();
    }

    public boolean LimpiarFactura() {
        return base.LimpiarFactura();
    }

    public boolean LimpiarLiquidacion() {
        return base.LimpiarLiquidacion();
    }

    public boolean LimpiarNominaUsuario() {
        return base.LimpiarNominaUsuario();
    }

    public boolean LimpiarPagos() {
        return base.LimpiarPagos();
    }

    public boolean LimpiarPrestamoUsuario() {
        return base.LimpiarPrestamoUsuario();
    }

    public ArrayList<RegistroDTO> listarRegistrosEstado(String estado) {
        return IRegistro.listarRegistrosEstado(estado);
    }
    public ArrayList<RegistroDTO> listarRegistrosMaterial(long id) {
        return IRegistro.listarRegistroMaterial(id);
    }
    public ArrayList<RegistroDTO> listarRegistrosMaterialFecha(long id,Date fecha,Date hasta) {
        return IRegistro.listarRegistroMaterialFecha(id,fecha,hasta);
    }

//    public ArrayList<RegistroDTO> listarRegistrosPago(String estado) {
//        return IRegistro.listarRegistrosPago(estado);
//    }
   

    public ArrayList<PagoDTO> listarNombre(long id) {
        return pagosDAO.listarNombre(id);
    }

    public boolean registrarCuotasPagad(long id, long cuota) {
        return IRegistro.registrarCuotasPagadas(id, cuota);
    }

    public double listarLiquidacionTotal(Date fecha) {
        return liquidacion.listarLiquidacionTotal(fecha);
    }

    public double listarNominaFecha(Date fecha) {
        return nominaDAO.listarNominaFecha(fecha);
    }
    public double  listarAbonoFecha(Date fecha) {
        return abonoDAO.listarAbonoFecha(fecha);
        
    }
    
    public double  listarPagosTotal(Date fecha){
        return pagosDAO.listarPagosTotal(fecha);
    }

}
