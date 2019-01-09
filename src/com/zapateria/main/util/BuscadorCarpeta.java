/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class BuscadorCarpeta implements java.io.Serializable {

    private static String resultado = null;
    private static final String SEPARADOR = File.separator;
    private static final long serialVersionUID = 7271688792461427157L;

    public static String buscar(String carpetaDestino) {

        try {
            Files.newDirectoryStream(Paths.get("."))
                    .forEach(path -> {
                        if (path.toString().equals("." + SEPARADOR + carpetaDestino)) {
                            try {
                                resultado = path.toFile().getCanonicalPath();

                            } catch (IOException ex) {
                                Logger.getLogger(BuscadorCarpeta.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return resultado + SEPARADOR;
    }


}
