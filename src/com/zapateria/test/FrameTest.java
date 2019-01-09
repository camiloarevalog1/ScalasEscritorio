/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.test;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author JHON
 */
public class FrameTest extends JFrame {
    
    public JButton nn = new JButton("Aceptar");
    public static int est; 
    
	public static void main(String[] args){
		new FrameTest();
	}
        
        protected void this_windowOpened(WindowEvent e) {
                centrarVentana();
        }
        private void centrarVentana() {
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                Dimension ventana = getSize();
                setLocation((pantalla.width - ventana.width) / 2,
                (pantalla.height - ventana.height) / 2);
        }
	
	public FrameTest()
	{
		super("Ejempplo!"); //titulo de ventana
                setSize(400, 400);
                this.this_windowOpened(null); //Centrar Ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setLayout(null); //Deshabilitar auto resize
                
                this.add(nn); //Adicionar boton
                nn.setSize(300, 100); //Tamaño del boton
                nn.setLocation(20,20); //Ubicación del boton
		
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(ComponentEvent e){
                                if(est>=2){
                                    System.out.println("JFrame was resized");
                                    // Se obtienen las dimensiones en pixels de la pantalla.
                                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                                    // Se obtienen las dimensiones en pixels de la ventana.
                                    Dimension ventana = getSize();
                                    
                                    nn.setSize(ventana.width-80, nn.getSize().height);
                                    //nn.setLocation(nn.getLocation().x, nn.getLocation().y );
                                }
                                est++;
			}
		});
                
                setVisible(true);
		
	}

}
