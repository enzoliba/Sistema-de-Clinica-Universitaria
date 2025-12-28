package Datos;

import Entidades.EHistorialMedico;
import java.sql.CallableStatement;
import Entidades.EPaciente;
import Entidades.EPersona;
import Entidades.EAtencion; 
import Entidades.EDiagnostico;
import Entidades.E_Empleado;
import Entidades.EReceta;
import Entidades.ECita;
import Entidades.Rol;
import Entidades.TipoSangre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class DHistorialMedico {

    public List<EHistorialMedico> listarHistorialesConAtenciones() {
        List<EHistorialMedico> listaHistoriales = new ArrayList<>();
        Connection conn = null;
        CallableStatement pstmt = null;
        ResultSet rs = null;
        String sql = "{CALL SP_ListarHistorialMedicoCompleto()}";
        try {
            Conexion conex = Conexion.getConnect();
            conn = conex.getConexion();
            if (conn == null) {
                System.err.println("ERROR: No se pudo establecer la conexión a la base de datos.");
                return listaHistoriales;
            }

            pstmt = conn.prepareCall(sql); //prepareStatement();
            rs = pstmt.executeQuery();

            EHistorialMedico historialActual = null; // Para construir el objeto HistorialMedico único

            while (rs.next()) {
                String idHistorialFilaActual = rs.getString("idHistorial");

                // --- Lógica para detectar si es un nuevo HistorialMedico ---
                if (historialActual == null || !historialActual.getIdHistorial().equals(idHistorialFilaActual)) {
                    // Es un nuevo HistorialMedico. Crear una nueva instancia.
                    historialActual = new EHistorialMedico();
                    
                    // --- Cargar Atributos de HISTORIAL_MEDICO ---
                    historialActual.setIdHistorial(idHistorialFilaActual);
                    historialActual.setFechaCreacion(rs.getDate("fechaCreacion").toLocalDate());
                    historialActual.setAlergias(rs.getString("alergias"));
                    historialActual.setCondicionesCronicas(rs.getString("condicionesCronicas"));
                    historialActual.setMedicamentosFrecuentes(rs.getString("medicamentosFrecuentes"));

                    // --- Cargar el Paciente (relación 1:1) ---
                    // Siempre se carga con el HistorialMedico, ya que la relación es INNER JOIN
                    EPaciente paciente = new EPaciente();
                    // Atributos de Persona (heredados por Paciente)
                    paciente.setIdPersona(rs.getString("idPersonaPaciente"));
                    paciente.setNombre(rs.getString("nombrePaciente"));
                    paciente.setApellidoPaterno(rs.getString("apPaternoPaciente"));
                    paciente.setApellidoMaterno(rs.getString("apMaternoPaciente"));
                    paciente.setGenero(rs.getString("generoPaciente"));
                    paciente.setDni(rs.getString("dniPaciente"));
                    try { paciente.setRol(Rol.fromDbValue(rs.getString("rolPaciente"))); } catch (IllegalArgumentException e) { paciente.setRol(null); }
                    paciente.setTelefono(rs.getString("telPaciente"));
                    paciente.setCorreo(rs.getString("correoPaciente"));
                    paciente.setDireccion(rs.getString("dirPaciente"));
                    paciente.setFechaNacimiento(rs.getDate("fnacPaciente"));
                    // Atributos específicos de Paciente
                    TipoSangre tipoSan = TipoSangre.fromDbValue(rs.getString("tipoSangre"));
                    paciente.setTipoSangre(tipoSan);
                    paciente.setFechaRegistro(rs.getDate("pacienteFechaRegistro"));
                    historialActual.setPaciente(paciente);
                    
                    // Inicializar la lista de atenciones para este nuevo HistorialMedico
                    historialActual.setAtenciones(new ArrayList<>()); 

                    listaHistoriales.add(historialActual); // Añadir el nuevo historial a la lista final
                }

                // --- Ahora, procesar la Atencion de la fila actual (si existe) y añadirla al historialActual ---
                // Se ejecuta para CADA fila del ResultSet.
                // Si la fila actual contiene información de una Atencion (idAtencion no es NULL)
                String idAtencion = rs.getString("idAtencion"); 
                if (idAtencion != null) { // Si hay una Atencion asociada en esta fila (LEFT JOIN)
                    EAtencion atencion = new EAtencion();
                    atencion.setIdAtencion(idAtencion);
                    atencion.setFecha(rs.getTimestamp("atencionFecha").toLocalDateTime()); // TIMESTAMP a LocalDateTime
                    atencion.setDescripcion(rs.getString("atencionDescripcion"));
                    atencion.setTratamiento(rs.getString("tratamiento"));
                    atencion.setObservaciones(rs.getString("observaciones"));
                    
                    EDiagnostico diagStub = new EDiagnostico(); 
                    diagStub.setIdDiagnostico(rs.getString("atencionIdDiagnostico"));
                    atencion.setDiagnostico(diagStub);
                    
                    E_Empleado empStub = new E_Empleado(); 
                    empStub.setIdPersona(rs.getString("atencionIdEmpleado")); 
                    atencion.setEmpleado(empStub);
                    
                    String idReceta = rs.getString("atencionIdReceta");
                    if (idReceta != null) { EReceta recStub = new EReceta(); recStub.setIdReceta(idReceta); atencion.setReceta(recStub); }
                    else { atencion.setReceta(null); }
                    
                    String idCita = rs.getString("atencionIdCita");
                    if (idCita != null) { ECita citStub = new ECita(); citStub.setIdCita(idCita); atencion.setCita(citStub); }
                    else { atencion.setCita(null); }

                    // Añadir la atención a la lista del HistorialMedico actual
                    historialActual.agregarAtencion(atencion);
                }
            }
            
        } catch(SQLException e){
            System.err.println("ERROR SQL al listar historiales médicos con atenciones: " + e.getMessage());
            e.printStackTrace(); 
        } finally {
            // Cierre de recursos en el bloque finally, asegurando que se cierren incluso si hay excepciones
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("ERROR al cerrar recursos de la base de datos: " + e.getMessage());
            }
        }
        
        return listaHistoriales;
    }
    
    // Método para buscar un historial específico por su ID con sus atenciones
    public EHistorialMedico buscarHistorialPorId(String idHistorial) {
        EHistorialMedico historialEncontrado = null;
        Connection conn = null;
        CallableStatement pstmt = null;
        ResultSet rs = null;

        String sql = "{CALL SP_ObtenerHistorialPorId(?)}";
        try {
            Conexion conex = Conexion.getConnect();
            conn = conex.getConexion();
            if (conn == null) {
                System.err.println("ERROR: No se pudo establecer la conexión a la base de datos.");
                return null;
            }

            pstmt = conn.prepareCall(sql); //prepareCallableStatement(sql);
            pstmt.setString(1, idHistorial); // Establecer el parámetro del ID
            rs = pstmt.executeQuery();

            while (rs.next()) {
                // La lógica es similar a listar, pero solo construimos un historial
                if (historialEncontrado == null) {
                    historialEncontrado = new EHistorialMedico();
                    historialEncontrado.setIdHistorial(rs.getString("idHistorial"));
                    historialEncontrado.setFechaCreacion(rs.getDate("fechaCreacion").toLocalDate());
                    historialEncontrado.setAlergias(rs.getString("alergias"));
                    historialEncontrado.setCondicionesCronicas(rs.getString("condicionesCronicas"));
                    historialEncontrado.setMedicamentosFrecuentes(rs.getString("medicamentosFrecuentes"));

                    EPaciente paciente = new EPaciente();
                    paciente.setIdPersona(rs.getString("idPersonaPaciente"));
                    paciente.setNombre(rs.getString("nombrePaciente"));
                    paciente.setApellidoPaterno(rs.getString("apPaternoPaciente"));
                    paciente.setApellidoMaterno(rs.getString("apMaternoPaciente"));
                    paciente.setGenero(rs.getString("generoPaciente"));
                    paciente.setDni(rs.getString("dniPaciente"));
                    try { paciente.setRol(Rol.fromDbValue(rs.getString("rolPaciente"))); } catch (IllegalArgumentException e) { paciente.setRol(null); }
                    paciente.setTelefono(rs.getString("telPaciente"));
                    paciente.setCorreo(rs.getString("correoPaciente"));
                    paciente.setDireccion(rs.getString("dirPaciente"));
                    paciente.setFechaNacimiento(rs.getDate("fnacPaciente"));
                    TipoSangre tipoSan = TipoSangre.fromDbValue(rs.getString("tipoSangre"));
                    paciente.setTipoSangre(tipoSan);
                    paciente.setFechaRegistro(rs.getDate("pacienteFechaRegistro"));
                    historialEncontrado.setPaciente(paciente);
                    
                    historialEncontrado.setAtenciones(new ArrayList<>()); 
                }

                String idAtencion = rs.getString("idAtencion");
                if (idAtencion != null) { // Si hay una Atención asociada
                    EAtencion atencion = new EAtencion();
                    atencion.setIdAtencion(idAtencion);
                    atencion.setFecha(rs.getTimestamp("atencionFecha").toLocalDateTime());
                    atencion.setDescripcion(rs.getString("atencionDescripcion"));
                    atencion.setTratamiento(rs.getString("tratamiento"));
                    atencion.setObservaciones(rs.getString("observaciones"));
                    
                    // Cargar solo las IDs para las relaciones de Atencion
                    EDiagnostico diagStub = new EDiagnostico(); diagStub.setIdDiagnostico(rs.getString("atencionIdDiagnostico")); atencion.setDiagnostico(diagStub);
                    E_Empleado empStub = new E_Empleado(); empStub.setIdPersona(rs.getString("atencionIdEmpleado")); atencion.setEmpleado(empStub);
                    String idReceta = rs.getString("atencionIdReceta");
                    if (idReceta != null) { EReceta recStub = new EReceta(); recStub.setIdReceta(idReceta); atencion.setReceta(recStub); } else { atencion.setReceta(null); }
                    String idCita = rs.getString("atencionIdCita");
                    
                    if (idCita != null) { ECita citStub = new ECita(); citStub.setIdCita(idCita); atencion.setCita(citStub); } else { atencion.setCita(null); }

                    historialEncontrado.agregarAtencion(atencion);
                }
            }
            
        } catch(SQLException e){
            System.out.println("ERROR SQL al buscar historial por ID: " + e.getMessage());
            e.printStackTrace(); 
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("ERROR al cerrar recursos de la base de datos: " + e.getMessage());
            }
        }
        
        return historialEncontrado;
    }
    
    public void registrarHistorialMe( EHistorialMedico his) throws SQLException{
        Connection cn = null;
        CallableStatement smt = null;
        Conexion con = new Conexion();
        
        try {
            cn = con.getConexion();
            String sql = "{call sp_InsertarHistorialMedico(?, ?, ?, ?, ?)}";
            smt = cn.prepareCall(sql);
            smt.setString(1, his.getIdHistorial());
            smt.setString(2, his.getAlergias());
            smt.setString(3, his.getCondicionesCronicas());
            smt.setString(4, his.getMedicamentosFrecuentes());
            smt.setString(5, his.getPaciente().getIdPersona());
            smt.execute();
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }finally{
            if (smt != null) smt.close();
            if (cn != null) cn.close();
        }
    }
    
    public void actualizarHistorialMed(EHistorialMedico his) {
        Connection conn = null;
        CallableStatement smt = null;
        
        try {
            conn = Conexion.getConnect().getConexion();

            smt = conn.prepareCall("{CALL sp_ActualizarHistorialMedico(?, ?, ?, ?)}");

            // Establecer parámetros para AMBIENTE
            smt.setString(1, his.getIdHistorial());
            smt.setString(2, his.getAlergias());
            smt.setString(3, his.getCondicionesCronicas());
            smt.setString(4, his.getMedicamentosFrecuentes());
            smt.execute();
          
            // Ejecutar la actualización
            //smt.execute();

        } catch (SQLException e) {
            System.err.println("Error al actualizar historial: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (smt != null) smt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
    
    
    public static String generarCodigoHistorialConTransaccion() throws SQLException {
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
            try (CallableStatement cstmt = conn.prepareCall("{call usp_GenerarCodigoHistorial(?)}")) {
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
    
    
    
}


