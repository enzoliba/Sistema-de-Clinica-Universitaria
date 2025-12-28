package Datos;

import Entidades.E_Especialidad;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DEspecialidad {
    
    public static List<E_Especialidad> listarEspecialidades() {
        List<E_Especialidad> listaEspecialidades = new ArrayList<>();
        // Declaramos los recursos JDBC aquí para poder cerrarlos en el bloque finally
        Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnect().getConexion(); // Obtener la conexión
            if (conn == null) {
                System.err.println("ERROR: No se pudo establecer la conexión a la base de datos.");
                return listaEspecialidades; 
            }

            // Preparar la llamada al stored procedure
            cs = conn.prepareCall("{CALL usp_ObtenerEspecialidades()}");

            // Ejecutar el stored procedure
            rs = cs.executeQuery();

            while (rs.next()) {
                // Usar el constructor con parámetros
                E_Especialidad especialidad = new E_Especialidad(
                    rs.getString("idEspecialidad"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );

                // Agregar a la lista
                listaEspecialidades.add(especialidad);
            }

        } catch(SQLException e){
            System.err.println("ERROR SQL al listar especialidades: " + e.getMessage());
            e.printStackTrace(); 
        } finally {
            // Cierre de recursos en el bloque finally
            try {
                if (rs != null) rs.close();
                if (cs != null) cs.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("ERROR al cerrar recursos de la base de datos: " + e.getMessage());
            }
        }

        return listaEspecialidades;
    }
    
    public static E_Especialidad obtenerEspecialidad(String nombre){
        E_Especialidad esp = null;
        // Declaramos los recursos JDBC aquí para poder cerrarlos en el bloque finally
        Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnect().getConexion(); // Obtener la conexión
            if (conn == null) {
                System.err.println("ERROR: No se pudo establecer la conexión a la base de datos.");
                return esp; 
            }

            // Preparar la llamada al stored procedure
            cs = conn.prepareCall("{CALL usp_BuscarEspecialidadPorNombre(?)}");
            cs.setString(1, nombre);
            
            // Ejecutar el stored procedure
            rs = cs.executeQuery();

            if (rs.next()) {
            esp = new E_Especialidad();
            esp.setIdEspecialidad(rs.getString("idEspecialidad"));
            esp.setNombre(rs.getString("nombre"));
            esp.setDescripcion(rs.getString("descripcion"));
            }

        } catch(SQLException e){
            System.err.println("ERROR SQL al obtener especialidad: " + e.getMessage());
            e.printStackTrace(); 
        } finally {
            // Cierre de recursos en el bloque finally
            try {
                if (rs != null) rs.close();
                if (cs != null) cs.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("ERROR al cerrar recursos de la base de datos: " + e.getMessage());
            }
        }

        return esp;
    }
}
