/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package com.zapateria.main.util;
//
////import java.sql.Connection;
//import com.mysql.jdbc.Connection;
//
//import java.sql.DriverManager;
//import java.sql.SQLException;
////import java.sql.SQLException;
////import javax.swing.JOptionPane;
////import org.h2.jdbcx.JdbcConnectionPool;
////import org.apache.commons.dbcp2.BasicDataSource;
//
///**
// *
// * @author Usuario
// */
//public class Conexion implements java.io.Serializable {
//
//    private static final long serialVersionUID = -2145318330355997647L;
//
//    private static Connection con;
//    private static Conexion INSTANCIA;
//    private static final String Driver = "com.mysql.jdbc.Driver";
//    private static final String User = "root";
//    private static final String Password = "";
//    private static final String Url = "jdbc:mysql://localhost:3306/Zapateria";
//
//    public Conexion() {
////
//
//    }
//
//    public Connection crearConexion() {
//        con = null;
//        try {
//            Class.forName(Driver);
//            con = (Connection) DriverManager.getConnection(Url, User, Password);
//            if (con != null) {
//                System.out.println("conexion establecida");
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            System.out.println(e);
//        }
//        return con;
//
//    }
//
//    public Connection getConnection() {
//        return crearConexion();
//    }
//
//    public void dispose() {
//        con = null;
//    }
//
//    public static Conexion getInstance() {
//        if (INSTANCIA == null) {
//            INSTANCIA = new Conexion();
//        }
//        return INSTANCIA;
//    }
//        dataSource.dispose();
//    }
//
//        con = dataSource.getConnection();
//
//        if (con != null) {
//            con.close();
//        }
//        System.exit(0);
//
//        return con;
//    }

//    private static final long serialVersionUID = -2145318330355997647L;
//
//    private static Conexion INSTANCIA;
//    private Connection con;
//    private static final String USUARIO = "root";
//    private static final String PORT = "80";
//    private static final String CONTRASENA = "";
//    private static final String ARCHIVO = "Zapateria";
//    private static final String CARPETA = "db";
//    private static final String SCHEMA = "ZAPATERIA";
//    private static final String DRIVER = "com.mysql.jdbc.Driver";
//
//    private static final String HOST = "localhost";
////    private static final String URL = "jdbc:mysql://127.0.0.1/Zapateria";
//
////    private final JdbcConnectionPool cp;
//    private final BasicDataSource dataSource;
//
//    private static final String URL = "jdbc:h2:file:" + BuscadorCarpeta.buscar(CARPETA) + ARCHIVO + ";SCHEMA=" + SCHEMA + ";AUTO_SERVER=FALSE";
//
//    public Conexion() {
//
//        dataSource = new BasicDataSource();
//
//        dataSource.setUsername(USUARIO);
//        dataSource.setPassword(CONTRASENA);
//        dataSource.setUrl("jdbc:mysql://" + HOST + ":" + PORT + "/" + SCHEMA);
//
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setMinIdle(0);
//        dataSource.setMaxIdle(4);
//    }
//
//    public static Conexion getInstance() {
//        if (INSTANCIA == null) {
//            INSTANCIA = new Conexion();
//        }
//        return INSTANCIA;
//    }
//
//    public Connection getConnection() throws SQLException {
//
//        con = dataSource.getConnection();
//
//        if (con != null) {
//            con.close();
//        }
//        System.exit(0);
//
//        return con;
//    }
//
//    public void dispose() {
//        dataSource.dispose();
//    }
//}
package com.zapateria.main.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.swing.JOptionPane;
import org.apache.commons.dbcp.BasicDataSource;

//import org.h2.jdbcx.JdbcConnectionPool;

/**
 *
 * @author Usuario
 */
public class Conexion implements java.io.Serializable {

    private static final long serialVersionUID = -2145318330355997647L;
    private static Conexion INSTANCIA;
    private Connection con;
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "12345";
    private static final String ARCHIVO = "zapateria";
    private static final String CARPETA = "db";
    private static final String SCHEMA = "ZAPATERIA";
    
    //Nueva conexion 
       public DataSource dataSource;
    private String driver = "org.gjt.mm.mysql.Driver";//es el driver varia segun la base de datos que usemos
    private String nombreBD;
    private String host;
    private String user;
    private String contra;
    
   // private final JdbcConnectionPool cp;

    //private static final String URL = "jdbc:h2:file:" + BuscadorCarpeta.buscar(CARPETA) + ARCHIVO + ";SCHEMA=" + SCHEMA + ";AUTO_SERVER=FALSE";

    public Conexion() {
         this.inicializarDataSource();
      
    }
    
    public Connection conexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
           //con=DriverManager.getConnection("jdbc:mysql://sql10.freesqldatabase.com:3306/sql10252323","sql10252323",
           //"bYJhqTAVAv");
         //con=DriverManager.getConnection("jdbc:mysql://127.0.0.1/Zapate","root","");
        con=DriverManager.getConnection("jdbc:mysql://46.105.228.190:3306/escalascarlosart_scalas","escalascarlosart_escalas","(CXB+,y5%}.X");     
        System.out.println("entrooo");
        } catch (Exception e) {
        }
        
        System.out.println(con.toString());
        return con;
    }

    public static Conexion getInstance() {
        if (INSTANCIA == null) {
            INSTANCIA = new Conexion();
        }
        return INSTANCIA;
    }
    private String getUrl() {
        return "jdbc:mysql://46.105.228.190:3306/escalascarlosart_scalas";
    }
    
     private void inicializarDataSource() {
//        String url = "jdbc:mysql://sandbox2.ufps.edu.co/ufps_76";
//        String contra = "ufps_29";
//        String user = "ufps_76";

        BasicDataSource basic = new BasicDataSource();
        basic.setDriverClassName(driver);
        basic.setUsername("escalascarlosart_escalas");
        basic.setPassword("(CXB+,y5%}.X");
        basic.setUrl(this.getUrl());
        basic.setMaxActive(5);
        this.dataSource = basic;
    }

    public Connection getConnection() {
        try {
            
    con=DriverManager.getConnection("jdbc:mysql://46.105.228.190:3306/escalascarlosart_scalas","escalascarlosart_escalas","(CXB+,y5%}.X");
    //    con=DriverManager.getConnection("jdbc:mysql://127.0.0.1/Zapate","root","");
          } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos,"
                    + " puede que ya esté siendo utilizada."+ex.getMessage());
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            try {
                if (con != null) {
                    con.close();
                }
                System.exit(0);
            } catch (SQLException e) {
                e.printStackTrace();
                ex.printStackTrace();
                
            }
        }
        return con;
    }

    public void dispose() throws SQLException {
        con.close();
    }
}

