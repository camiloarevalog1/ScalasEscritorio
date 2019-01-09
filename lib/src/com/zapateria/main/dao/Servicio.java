/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.dao;

import com.zapateria.main.dto.*;
import com.zapateria.main.interfaces.*;
import java.sql.Date;
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
    }

    // Consultas de usuarios
    public boolean registrarUsuario(String nombres, String apellidos, String documento,
            String nombreUsuario, String contrasena, long rol) {
        return usuariosDAO.registrar(nombres, apellidos, documento, nombreUsuario,
                contrasena, rol);
    }

    public boolean editarUsuario(long id, String nombres, String apellidos,
            String documento, String nombreUsuario, long rol) {
        return usuariosDAO.editar(id, nombres, apellidos, documento,
                nombreUsuario, rol);
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

    // Consultas de roles
    public boolean registrarRol(String rol) {
        return rolesDAO.registrar(rol);
    }

    public RolDTO consultarRol(long id) {
        return rolesDAO.consultar(id);
    }

    public boolean editarRol(long id, String rol) {
        return rolesDAO.editar(id, rol);
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

    // Consultas para materiales
    public boolean registrarMaterial(String nombre) {
        return materialesDAO.registrar(nombre);
    }

    public MaterialDTO consultarMaterial(long id) {
        return materialesDAO.consultar(id);
    }

    public ArrayList<MaterialDTO> listarMateriales() {
        return materialesDAO.listar();
    }

    public boolean editarMaterial(long id, String nombre) {
        return materialesDAO.editar(id, nombre);
    }

    public boolean editarMaterial(long id, long cantidad) {
        return materialesDAO.editar(id, cantidad);
    }

    public boolean eliminarMaterial(long id) {
        return materialesDAO.eliminar(id);
    }

    // Consultas para compras
    public boolean registrarCompra(long cantidad, double valorUnitario, double valorTotal,
            long proveedor, long material) {
        return comprasDAO.registrar(cantidad, valorUnitario, valorTotal, proveedor, material);
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

    public boolean eliminarCompra(long id) {
        return comprasDAO.eliminar(id);
    }
    
    public long registrarClien(String documento, String nombres, String apellidos,
            String direccion, String telefono){
        return clientesDAO.registrarCliente(documento, nombres, apellidos, direccion, telefono);
    }
    
    public boolean validarCedula(String cedula){
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
    public boolean registrarFactura(String formaDePago, String estado, double iva,
            double totalPagar, double totalPagado,
            Date fechaEntrega, long cliente, long usuario) {

        return facturasDAO.registrar(formaDePago, estado, iva, totalPagar,
                totalPagado, fechaEntrega, cliente, usuario);
    }

    public FacturaDTO consultarFactura(long id) {
        return facturasDAO.consultar(id);
    }

    public ArrayList<FacturaDTO> listarFacturas() {
        return facturasDAO.listar();
    }

    public ArrayList<FacturaDTO> buscarFactura(String busqueda) {
        return facturasDAO.buscar(busqueda);
    }

    public boolean editarFactura(long id, String formaDePago, String estado, double iva,
            double totalPagar, double totalPagado,
            Date fechaEntrega, long cliente, long usuario) {

        return facturasDAO.editar(id, formaDePago, estado, iva, totalPagar,
                totalPagado, fechaEntrega, cliente, usuario);
    }

    public boolean editarFactura(long id, String formaDePago, String estado) {
        return facturasDAO.editar(id, formaDePago, estado);
    }

    public boolean cambiarEstadoFactura(long id, String estado) {
        return facturasDAO.cambiarEstado(id, estado);
    }

    public boolean cambiarFormaDePagoFactura(long id, String formaDePago) {
        return facturasDAO.cambiarFormaDePago(id, formaDePago);
    }

    public boolean eliminarFactura(long id) {
        return facturasDAO.eliminar(id);
    }

    public long consultarIncrementoFacturas() {
        return facturasDAO.consultarIncremento();
    }

    // Consultas para ventas 
    public boolean registrarVenta(long cantidad, String comentario, long factura, long producto) {
        return ventasDAO.registrar(cantidad, comentario, factura, producto);
    }

    public VentaDTO consultarVenta(long id) {
        return ventasDAO.consultar(id);
    }

    public ArrayList<VentaDTO> listarVentas() {
        return ventasDAO.listar();
    }

    public ArrayList<VentaDTO> listarVentas(long factura) {
        return ventasDAO.listar(factura);
    }

    public boolean editarVenta(long id, String comentario, long cantidad, long factura, long producto) {
        return ventasDAO.editar(id, cantidad, comentario, factura, producto);
    }

    public boolean editarVenta(long id, long cantidad) {
        return ventasDAO.editar(id, cantidad);
    }

    public boolean eliminarVenta(long id) {
        return ventasDAO.eliminar(id);
    }

    //Consultas para pagos
    public boolean registrarPago(double abono, long factura) {
        return pagosDAO.registrar(abono, factura);
    }

    public PagoDTO consultarPago(long id) {
        return pagosDAO.consultar(id);
    }

    public ArrayList<PagoDTO> listarPagos() {
        return pagosDAO.listar();
    }

    public boolean editarPago(long id, double abono, long factura) {
        return pagosDAO.editar(id, abono, factura);
    }

    public boolean eliminarPago(long id) {
        return pagosDAO.eliminar(id);
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
}
