package Datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private String server;
    private String dataBase;
    private String user;
    private String password;
    private Connection con;
    private String driver;
    private static Conexion instance = null;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
    
    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Conexion() {
        // lineas para conexion con mySql:
        //this.driver = "org.mariadb.jdbc.Driver";
        this.server = "jdbc:mysql://localhost:3306/";
        this.driver = "com.mysql.cj.jdbc.Driver";
        //this.server = "jdbc:mariadb://localhost:3306/";
        this.dataBase = "clinicaUniversidad";
        this.user = "root";
        this.password = "";
        this.con = null;
    }
    
    public Connection getConexion() {
    try {
        if (con == null || con.isClosed()) {
            Class.forName(driver);
            con = DriverManager.getConnection(
                    server + dataBase,
                    user,
                    password
            );
            System.out.println("Conexion establecida");
        }
    } catch (ClassNotFoundException | SQLException e) {
        System.err.println("Error en conexion: " + e.getMessage());
    }
    return con;
}
    
    public void closeConnection() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Conexion cerrada");
                con = null;
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexion");
            }
        }
    }
    
    public static Conexion getConnect(){
        if(instance == null){
            instance = new Conexion();
            instance.getConexion();
        }else{
            System.out.println("Ya hay una conexion establecida");
        }
        return instance;
    }
}
