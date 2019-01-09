/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.main.view;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.border.Border;
/**
 *
 * @author JHON
 */
public class JCBorder {
     private Image imagen_fondo;

    public JCBorder(){
       imagen_fondo = new ImageIcon(getClass().getResource("../images/fonEscritorio.jpg")).getImage();
    }

    public void imagen(Image imagen)
    {
        this.imagen_fondo = imagen;
    }  

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        if( imagen_fondo.getWidth(null) < width || imagen_fondo.getHeight(null) < height)
            g.drawImage(imagen_fondo, (x + (width - imagen_fondo.getWidth(null))/2),(y + (height - imagen_fondo.getHeight(null))/2), null);
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(0,0,0,0);
    }

    public boolean isBorderOpaque() {
        return true;
    }
}
