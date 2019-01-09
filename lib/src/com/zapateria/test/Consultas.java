/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.test;

import com.zapateria.main.dao.Servicio;


/**
 *
 * @author Diego
 */
public class Consultas {

    private static Servicio consultas;

    private Consultas() {
        consultas = new Servicio();
    }

    private void pruebas() {
//        int id;
//        String formaDePago = "CONTADO", estado = "EN MORA";
//        double iva = 19, totalPagar = 201, totalPagado = 300;
//        Date plazoFechaInicio = Date.valueOf("2017-07-01"), plazoFechaFinal = Date.valueOf("2017-07-01");
//        String comentario = "soifjd sdfasj";
//        int cliente = 1, usuario = 1;
//
//        consultas.registrarFactura(formaDePago, estado, iva, totalPagar,
//                totalPagado, plazoFechaInicio, plazoFechaFinal,
//                comentario, cliente, usuario);
//        System.out.println(consultas.buscarFactura("5").size());

//        FacturaDTO factura = consultas.consultarFactura(9);
//
//        PagoDTO pago = consultas.consultarPago(6);
//
//        double abono = pago.getAbono();
//
//        double totalPagado = factura.getTotalPagado();
//
//        double abonoActualizado = 23;
//
//        double aux = totalPagado - (abono - abonoActualizado);
//        System.out.println(totalPagado);
//        System.out.println(consultas.editarPago(pago.getId(), abonoActualizado, factura.getId()));


//        System.out.println(consultas.eliminarPago(14));

//        System.out.println((""+dijad).length());
//
// 
//        System.out.println((Double.MAX_VALUE + "").length());

String valor = "2342342.0";

        System.out.println(valor.substring(0,valor.length()-2));
    }

    public static void main(String args[]) {
        new Consultas().pruebas();
    }

}
