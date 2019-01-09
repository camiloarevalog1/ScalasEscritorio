/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.view;
import com.zapateria.main.util.BuscadorCarpeta;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
/**
 *
 * @author JHON
 */
public class ImagenFondo implements Border {    
    BufferedImage fondo;
    public ImagenFondo(){    
        try {       
            //se obtiene la imagen            
             String cadena = "";
        String CARPETA = "db";
        BuscadorCarpeta b = null;
        String c= b.buscar(CARPETA);
        File f = new File(c+"\\fondo.jpg");
            System.out.println(f.toString()+"FONDOOOOOO");
        
//            URL url = new URL(getClass().getResource(c+"\\fondo.jpeg").toString());
            fondo = ImageIO.read(f);    
            
        } catch (IOException ex) {
            Logger.getLogger(ImagenFondo.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    // se sobreescriben metodos propios de Border
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height){     
        //se dibuja la imagen de fondo en el centro del contenedor
        //cada que se redimensione el contenedor, la imagen automaticamente se posiciona en el centro
        g.drawImage(fondo, (x + (width - fondo.getWidth())/2),(y + (height - fondo.getHeight())/2), null);
    }

    public Insets getBorderInsets(Component c){
    return new Insets(0,0,0,0);
    }

    public boolean isBorderOpaque(){
    return true;
    }
}

