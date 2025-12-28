package Datos;

import Entidades.EPaciente;
import Entidades.Rol;
import Entidades.TipoSangre;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.List;

public class DPaciente {
    
    private ResultSet rs; //para recoger el resultado de una consulta
    
    public List<EPaciente> listarTodosLosPacientes(String rolFiltro) {
        List<EPaciente> listaPacientes = new ArrayList<>();

        // Declaramos los recursos JDBC aquí para poder cerrarlos en el bloque finally
        Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnect().getConexion(); // Obtener la conexión
            if (conn == null) {
                System.err.println("ERROR: No se pudo establecer la conexión a la base de datos.");
                return listaPacientes; 
            }

            // Preparar la llamada al stored procedure
            cs = conn.prepareCall("{CALL usp_ListarPacientes(?)}");

            // Establecer el parámetro de entrada
            cs.setString(1, rolFiltro);

            // Ejecutar el stored procedure
            rs = cs.executeQuery();

            while (rs.next()) {
                EPaciente.PacienteBuilder builder = EPaciente.builder()
                    .setIdPersona(rs.getString("idPersona"))
                    .setNombre(rs.getString("nombre"))
                    .setApellidoPaterno(rs.getString("apellidoPaterno"))
                    .setApellidoMaterno(rs.getString("apellidoMaterno"))
                    .setGenero(rs.getString("genero"))
                    .setDni(rs.getString("dni"))
                    .setTelefono(rs.getString("telefono"))
                    .setCorreo(rs.getString("correo"))
                    .setDireccion(rs.getString("direccion"))
                    .setFechaNacimiento(rs.getDate("fechaNacimiento"))
                    .setFechaRegistro(rs.getDate("fechaRegistro"));

                // Manejo del Rol
                String rolDbValue = rs.getString("rol");
                try {
                    builder.setRol(Rol.fromDbValue(rolDbValue));
                } catch (IllegalArgumentException e) {
                    System.err.println("Advertencia: Valor de rol inválido en la BD para persona " + rs.getString("idPersona") + ": " + rolDbValue);
                    builder.setRol(null); 
                }

                // Manejo del TipoSangre
                String tipoSangreDbValue = rs.getString("tipoSangre");
                try {
                    builder.setTipoSangre(TipoSangre.fromDbValue(tipoSangreDbValue));
                } catch (IllegalArgumentException e) {
                    System.err.println("Advertencia: Valor de tipo sangre inválido en la BD para persona " + rs.getString("idPersona") + ": " + tipoSangreDbValue);
                    builder.setTipoSangre(null);
                }

                EPaciente paciente = builder.build();
                listaPacientes.add(paciente);
            }
        } catch(SQLException e){
            System.err.println("ERROR SQL al listar pacientes: " + e.getMessage());
            e.printStackTrace(); 
        } finally {
            // Cierre de recursos en el bloque finally
            try {
                if (rs != null) rs.close();
                if (cs != null) cs.close();
                if (conn != null) conn.close(); // Cierra la conexión si no usas un pool de conexiones gestionado
            } catch (SQLException e) {
                System.err.println("ERROR al cerrar recursos de la base de datos: " + e.getMessage());
            }
        }

        return listaPacientes;
    }
    
    // Puedes añadir otros métodos como buscarPacientePorId si lo necesitas:
    public EPaciente buscarPacientePorId(String idPaciente) {
        EPaciente paciente = null;
        Connection conn = null;
        CallableStatement st = null;
        rs = null;

        String sql = "{call usp_BuscarPacientePorId(?)}";

        try {
            conn = Conexion.getConnect().getConexion();
            if (conn == null) {
                System.err.println("ERROR: No se pudo establecer la conexión a la base de datos.");
                return null;
            }

            st = conn.prepareCall(sql);
            st.setString(1, idPaciente);
            rs = st.executeQuery(); 

            if (rs.next()) { // Si encuentra un resultado
                EPaciente.PacienteBuilder builder = EPaciente.builder()
                    .setIdPersona(rs.getString("idPersona"))
                    .setNombre(rs.getString("nombre"))
                    .setApellidoPaterno(rs.getString("apellidoPaterno"))
                    .setApellidoMaterno(rs.getString("apellidoMaterno"))
                    .setGenero(rs.getString("genero"))
                    .setDni(rs.getString("dni"))
                    .setTelefono(rs.getString("telefono"))
                    .setCorreo(rs.getString("correo"))
                    .setDireccion(rs.getString("direccion"))
                    .setFechaNacimiento(rs.getDate("fechaNacimiento"))
                    .setFechaRegistro(rs.getDate("fechaRegistro"));

                // Manejo del Rol
                String rolDbValue = rs.getString("rol");
                try {
                    builder.setRol(Rol.fromDbValue(rolDbValue));
                } catch (IllegalArgumentException e) {
                    System.err.println("Advertencia: Valor de rol inválido en la BD para persona " + rs.getString("idPersona") + ": " + rolDbValue);
                    builder.setRol(null); 
                }

                // Manejo del TipoSangre
                String tipoSangreDbValue = rs.getString("tipoSangre");
                try {
                    builder.setTipoSangre(TipoSangre.fromDbValue(tipoSangreDbValue));
                } catch (IllegalArgumentException e) {
                    System.err.println("Advertencia: Valor de tipo sangre inválido en la BD para persona " + rs.getString("idPersona") + ": " + tipoSangreDbValue);
                    builder.setTipoSangre(null);
                }

                paciente = builder.build(); 
            }
        } catch (SQLException e) {
            System.err.println("ERROR SQL al buscar paciente por ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("ERROR al cerrar recursos DB: " + e.getMessage());
            }
        }
        return paciente;
    }
    
    public List<EPaciente> listarBuscarPaciente(String id, String apellido, String dni, String rol, String tipoSangre, int pagina, int tamañoPagina) throws SQLException{
        List<EPaciente> listaPacientes = new ArrayList<>();
        
        // Declaramos los recursos JDBC aquí para poder cerrarlos en el bloque finally
        Connection conn = null;
        rs = null;
        CallableStatement smt = null;
        
        conn = Conexion.getConnect().getConexion();
        smt = conn.prepareCall("{call usp_PacienteLisBusPag(?,?,?,?,?,?,?)}");
        
            try {
            smt.setString(1, id);
            smt.setString(2, apellido);
            smt.setString(3, dni);
            
            //Si rol es nulo, que no filtre por rol
            if(rol != null && rol.equals("Todos")){
                rol = null;
            }
            smt.setString(4, rol);
            smt.setString(5, tipoSangre);
            smt.setInt(6, pagina);
            smt.setInt(7, tamañoPagina);

            rs = smt.executeQuery();
        
        while (rs.next()) {
            EPaciente.PacienteBuilder builder = EPaciente.builder()
                    .setIdPersona(rs.getString("idPersona"))
                    .setNombre(rs.getString("nombre"))
                    .setApellidoPaterno(rs.getString("apellidoPaterno"))
                    .setApellidoMaterno(rs.getString("apellidoMaterno"))
                    .setGenero(rs.getString("genero"))
                    .setDni(rs.getString("dni"))
                    .setTelefono(rs.getString("telefono"))
                    .setCorreo(rs.getString("correo"))
                    .setDireccion(rs.getString("direccion"))
                    .setFechaNacimiento(rs.getDate("fechaNacimiento"))
                    .setFechaRegistro(rs.getDate("fechaRegistro"));

                // Manejo del Rol
                String rolDbValue = rs.getString("rol");
                try {
                    builder.setRol(Rol.fromDbValue(rolDbValue));
                } catch (IllegalArgumentException e) {
                    System.err.println("Advertencia: Valor de rol inválido en la BD para persona " + rs.getString("idPersona") + ": " + rolDbValue);
                    builder.setRol(null); 
                }

                // Manejo del TipoSangre
                String tipoSangreDbValue = rs.getString("tipoSangre");
                try {
                    builder.setTipoSangre(TipoSangre.fromDbValue(tipoSangreDbValue));
                } catch (IllegalArgumentException e) {
                    System.err.println("Advertencia: Valor de tipo sangre inválido en la BD para persona " + rs.getString("idPersona") + ": " + tipoSangreDbValue);
                    builder.setTipoSangre(null);
                }

                EPaciente paciente = builder.build();
                listaPacientes.add(paciente);
        }
    } catch (SQLException e) {
        // Manejar error específico de paginación
        if (e.getMessage().contains("página inválido")) {
            // Recuperación: usar valores por defecto
            return listarBuscarPaciente(id, apellido, dni, rol, tipoSangre, 1, 10);
        }
        throw new RuntimeException("Error en búsqueda", e);
    }finally {
            try {
                if (rs != null) rs.close();
                if (smt != null) smt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("ERROR al cerrar recursos DB: " + e.getMessage());
            }
    }
    return listaPacientes;
    }
    
    public void registrarPaciente( EPaciente pac ) throws SQLException{
        Connection cn = null;
        CallableStatement smt = null;
        Conexion con = new Conexion();
        
        try {
            cn = con.getConexion();
            String sql = "{CALL usp_RegistrarPaciente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            smt = cn.prepareCall(sql);
            smt.setString(1, pac.getIdPersona());
            smt.setString(2, pac.getNombre());
            smt.setString(3, pac.getApellidoPaterno());
            smt.setString(4, pac.getApellidoMaterno());
            smt.setString(5, pac.getGenero());
            smt.setString(6, pac.getDni());
            smt.setString(7, pac.getRol().getDbValue());
            smt.setString(8, pac.getTelefono());
            smt.setString(9, pac.getCorreo());
            smt.setString(10, pac.getDireccion());
            
            //Correccion para la fecha de nacimiento
            if (pac.getFechaNacimiento() != null) {
                smt.setDate(11, new java.sql.Date(pac.getFechaNacimiento().getTime())); // ¡CONVERSIÓN!
            } else {
                // Si fechaNacimiento es obligatorio en la BD, esto causaría un error SQL
                // Si no es obligatorio, setear a NULL
                smt.setNull(11, Types.DATE); 
            }
            
            smt.setString(12, pac.getTipoSangre().getDbValue());
            
            //Correcion para fecha de registro
            if (pac.getFechaRegistro() != null) {
                smt.setDate(13, new java.sql.Date(pac.getFechaRegistro().getTime())); // ¡CONVERSIÓN!
            } else {
                // Enviar NULL para que la BD use su valor DEFAULT (CURRENT_DATE)
                smt.setNull(13, Types.DATE); 
            }

            
            smt.execute();
        } catch (SQLException e) {   
            throw e;
        }
    }
    
    public boolean actualizarPaciente(EPaciente paciente) {
        Connection conn = null;
        CallableStatement stm = null;
        
        try {
            conn = Conexion.getConnect().getConexion();
            if (conn == null) {
                System.err.println("ERROR: No se pudo establecer la conexión");
                return false;
            }

            stm = conn.prepareCall("{CALL usp_ActualizarPaciente(?,?,?,?,?,?,?,?,?,?,?,?)}");

            // Establecer parámetros para PERSONA
            stm.setString(1, paciente.getIdPersona());
            stm.setString(2, paciente.getNombre());
            stm.setString(3, paciente.getApellidoPaterno());
            stm.setString(4, paciente.getApellidoMaterno());
            stm.setString(5, paciente.getGenero());
            stm.setString(6, paciente.getDni());
            stm.setString(7, paciente.getRol().getDbValue());
            stm.setString(8, paciente.getTelefono());
            stm.setString(9, paciente.getCorreo());
            stm.setString(10, paciente.getDireccion());
            stm.setDate(11, new java.sql.Date(paciente.getFechaNacimiento().getTime()));

            // Parámetro para PACIENTE
            stm.setString(12, paciente.getTipoSangre().getDbValue());

            // Ejecutar la actualización
            stm.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al actualizar paciente: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            // Cerrar recursos
            try {
                if (stm != null) stm.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
    
    public static String generarCodigoPacienteConTransaccion() throws SQLException {
        String codigoGenerado = null;
        Conexion con = new Conexion();
        Connection conn = con.getConexion();

        try {
            conn.setAutoCommit(false);  // iniciar transaccion

            // bloqueo de tablas para evitar conflictos
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("LOCK TABLES PERSONA WRITE, PACIENTE WRITE");
            }

            // generar el codigo desde el sp
            try (CallableStatement cstmt = conn.prepareCall("{call usp_GenerarCodigoPaciente(?)}")) {
                cstmt.registerOutParameter(1, Types.VARCHAR);
                cstmt.execute();
                codigoGenerado = cstmt.getString(1);
            }

            // liberamos las atblas previamente bloqueadas
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("UNLOCK TABLES");
            }
            
            //hacemos commit para informar que todo salio bien
            conn.commit();

        } catch (SQLException e) {
            //Si algo sale mal hacemos rollback
            conn.rollback();
            throw e; //propagamos el error
        } finally {
            //finalizamos la transaccion
            conn.setAutoCommit(true);
        }
        
        //retornamos el codigo generado
        return codigoGenerado;
    }
    
    public String buscarIdPorDni(String DNI) throws SQLException{
        rs = null;
        Conexion con = new Conexion();
        Connection conn = con.getConexion();
        CallableStatement smt = null;
        
        smt = conn.prepareCall("{CALL usp_BuscarPorDNI(?)}");
        try {
            smt.setString(1, DNI);
            rs = smt.executeQuery();
            if(rs.next()){
                String idPersona = rs.getString("idPersona");
                return idPersona;
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return null;
    }
}
