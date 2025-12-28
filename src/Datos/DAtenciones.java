package Datos;

import Entidades.EAtencion;
import Entidades.ECita;
import Entidades.EDiagnostico;
import Entidades.E_Empleado;
import Entidades.E_Especialidad;
import Entidades.EHistorialMedico;
import Entidades.EPaciente;
import Entidades.EPersona; // Necesario para los objetos Paciente y Empleado
import Entidades.EReceta;
import Entidades.Rol; // Necesario para los objetos Persona
import Entidades.EAmbiente; // Necesario para el objeto Ambiente dentro de Cita
import Entidades.EAmbiente.AmbienteBuilder;
import Entidades.ECita.CitaBuilder;
import Entidades.TipoSangre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAtenciones {

    public List<EAtencion> listarTodasAtenciones() {
        List<EAtencion> listaAtenciones = new ArrayList<>();
        String sql = "SELECT " +
                     "A.idAtencion, A.fecha, A.descripcion, A.tratamiento, A.observaciones, " +
                     "H.idHistorial, H.fechaCreacion AS historialFechaCreacion, H.alergias, H.condicionesCronicas, H.medicamentosFrecuentes, " +
                     "PA.tipoSangre, PA.fechaRegistro AS pacienteFechaRegistro, " +
                     "P_Pac.idPersona AS idPersonaPaciente, P_Pac.nombre AS nombrePaciente, P_Pac.apellidoPaterno AS apPaternoPaciente, P_Pac.apellidoMaterno AS apMaternoPaciente, P_Pac.genero AS generoPaciente, P_Pac.dni AS dniPaciente, P_Pac.rol AS rolPaciente, P_Pac.telefono AS telPaciente, P_Pac.correo AS correoPaciente, P_Pac.direccion AS dirPaciente, P_Pac.fechaNacimiento AS fnacPaciente, " +
                     "DI.idDiagnostico, DI.descripcionAdicional AS diagnosticoDescripcion, DI.observaciones AS diagnosticoObservaciones, DI.procedimiento AS diagnosticoProcedimiento, " +
                     "EM.disponibilidad, EM.estado, EM.fechaIngreso, " +
                     "P_Emp.idPersona AS idPersonaEmpleado, P_Emp.nombre AS nombreEmpleado, P_Emp.apellidoPaterno AS apPaternoEmpleado, P_Emp.apellidoMaterno AS apMaternoEmpleado, P_Emp.genero AS generoEmpleado, P_Emp.dni AS dniEmpleado, P_Emp.rol AS rolEmpleado, P_Emp.telefono AS telEmpleado, P_Emp.correo AS correoEmpleado, P_Emp.direccion AS dirEmpleado, P_Emp.fechaNacimiento AS fnacEmpleado, " +
                     "ESP.idEspecialidad, ESP.nombre AS nombreEspecialidad, ESP.descripcion AS descripcionEspecialidad, " +
                     "R.idReceta, R.descripcionAdicional AS recetaDescripcion, " +
                     "C.idCita, C.fecha AS citaFecha, C.hora AS citaHora, C.motivo AS citaMotivo, C.estado AS citaEstado, " +
                     "AMB.idAmbiente, AMB.nombre AS ambienteNombre, AMB.capacidad, AMB.disponibilidad AS ambienteDisponibilidad " +
                     "FROM ATENCION A " +
                     "INNER JOIN HISTORIAL_MEDICO H ON A.idHistorial = H.idHistorial " +
                     "INNER JOIN DIAGNOSTICO DI ON A.idDiagnostico = DI.idDiagnostico " +
                     "INNER JOIN EMPLEADO EM ON A.idEmpleado = EM.idPersona " +
                     "INNER JOIN PERSONA P_Emp ON EM.idPersona = P_Emp.idPersona " +
                     "LEFT JOIN ESPECIALIDAD ESP ON EM.idEspecialidad = ESP.idEspecialidad " +
                     "LEFT JOIN RECETA R ON A.idReceta = R.idReceta " +
                     "LEFT JOIN CITA C ON A.idCita = C.idCita " +
                     "LEFT JOIN PACIENTE PA ON H.idPaciente = PA.idPersona " +
                     "LEFT JOIN PERSONA P_Pac ON PA.idPersona = P_Pac.idPersona " +
                     "LEFT JOIN AMBIENTE AMB ON C.idAmbiente = AMB.idAmbiente " +
                     "ORDER BY A.idAtencion";

        try (Connection conn = Conexion.getConnect().getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                EAtencion atencion = construirAtencionDesdeResultSet(rs);
                listaAtenciones.add(atencion);
            }

        } catch (SQLException e) {
            System.err.println("ERROR SQL al listar atenciones: " + e.getMessage());
        }

        return listaAtenciones;
    }

    private EAtencion construirAtencionDesdeResultSet(ResultSet rs) throws SQLException {
        // Construir Paciente con builder
        EPaciente paciente = EPaciente.builder()
            .setIdPersona(rs.getString("idPersonaPaciente"))
            .setNombre(rs.getString("nombrePaciente"))
            .setApellidoPaterno(rs.getString("apPaternoPaciente"))
            .setApellidoMaterno(rs.getString("apMaternoPaciente"))
            .setGenero(rs.getString("generoPaciente"))
            .setDni(rs.getString("dniPaciente"))
            .setRol(Rol.fromDbValue(rs.getString("rolPaciente")))
            .setTelefono(rs.getString("telPaciente"))
            .setCorreo(rs.getString("correoPaciente"))
            .setDireccion(rs.getString("dirPaciente"))
            .setFechaNacimiento(rs.getDate("fnacPaciente"))
            .setTipoSangre(TipoSangre.fromDbValue(rs.getString("tipoSangre")))
            .setFechaRegistro(rs.getDate("pacienteFechaRegistro"))
            .build();

        // Construir HistorialMedico
        EHistorialMedico historial = new EHistorialMedico();
        historial.setIdHistorial(rs.getString("idHistorial"));
        historial.setFechaCreacion(rs.getDate("historialFechaCreacion").toLocalDate());
        historial.setAlergias(rs.getString("alergias"));
        historial.setCondicionesCronicas(rs.getString("condicionesCronicas"));
        historial.setMedicamentosFrecuentes(rs.getString("medicamentosFrecuentes"));
        historial.setPaciente(paciente);

        // Construir Diagnostico
        EDiagnostico diagnostico = new EDiagnostico();
        diagnostico.setIdDiagnostico(rs.getString("idDiagnostico"));
        diagnostico.setDescripcionAdicional(rs.getString("diagnosticoDescripcion"));

        // Construir Especialidad
        E_Especialidad especialidad = null;
        String idEspecialidad = rs.getString("idEspecialidad");
        if (idEspecialidad != null) {
            especialidad = new E_Especialidad();
            especialidad.setIdEspecialidad(idEspecialidad);
            especialidad.setNombre(rs.getString("nombreEspecialidad"));
            especialidad.setDescripcion(rs.getString("descripcionEspecialidad"));
        }

        // Construir Empleado
        E_Empleado empleado = new E_Empleado();
        empleado.setIdPersona(rs.getString("idPersonaEmpleado"));
        empleado.setNombre(rs.getString("nombreEmpleado"));
        empleado.setApellidoPaterno(rs.getString("apPaternoEmpleado"));
        empleado.setApellidoMaterno(rs.getString("apMaternoEmpleado"));
        empleado.setGenero(rs.getString("generoEmpleado"));
        empleado.setDni(rs.getString("dniEmpleado"));
        empleado.setRol(Rol.fromDbValue(rs.getString("rolEmpleado")));
        empleado.setTelefono(rs.getString("telEmpleado"));
        empleado.setCorreo(rs.getString("correoEmpleado"));
        empleado.setDireccion(rs.getString("dirEmpleado"));
        empleado.setFechaNacimiento(rs.getDate("fnacEmpleado"));
        empleado.setDisponibilidad(rs.getBoolean("disponibilidad"));
        empleado.setEstado(rs.getBoolean("estado"));
        empleado.setFechaIngreso(rs.getDate("fechaIngreso"));
        empleado.setEspecialidad(especialidad);
        empleado.setHorarios(new ArrayList<>());

        // Construir Receta
        EReceta receta = null;
        String idReceta = rs.getString("idReceta");
        if (idReceta != null) {
            receta = new EReceta();
            receta.setIdReceta(idReceta);
            receta.setDescripcionAdicional(rs.getString("recetaDescripcion"));
        }

        // Construir Ambiente
        EAmbiente ambiente = null;
        String idAmbiente = rs.getString("idAmbiente");
        if (idAmbiente != null) {
            ambiente = new EAmbiente();
            ambiente.setIdAmbiente(idAmbiente);
            ambiente.setNombre(rs.getString("ambienteNombre"));
            ambiente.setCapacidad(rs.getInt("capacidad"));
            ambiente.setDisponibilidad(rs.getBoolean("ambienteDisponibilidad"));
        }

        // Construir Cita
        ECita cita = null;
        String idCita = rs.getString("idCita");
        if (idCita != null) {
            cita = new ECita();
            cita.setIdCita(idCita);
            cita.setFecha(rs.getDate("citaFecha"));
            cita.setHora(rs.getTime("citaHora"));
            cita.setMotivo(rs.getString("citaMotivo"));
            cita.setEstado(rs.getString("citaEstado"));
            cita.setAmbiente(ambiente);
            cita.setEmpleado(empleado);
            cita.setPaciente(paciente);
        }

        // Construir Atencion
        EAtencion atencion = new EAtencion();
        atencion.setIdAtencion(rs.getString("idAtencion"));
        atencion.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
        atencion.setDescripcion(rs.getString("descripcion"));
        atencion.setTratamiento(rs.getString("tratamiento"));
        atencion.setObservaciones(rs.getString("observaciones"));
        atencion.setHistorial(historial);
        atencion.setDiagnostico(diagnostico);
        atencion.setEmpleado(empleado);
        atencion.setReceta(receta);
        atencion.setCita(cita);

        return atencion;
    }

    public List<ECita> listarCitasDisponibles() {
        List<ECita> listaCitas = new ArrayList<>();

        String sql = "SELECT C.idCita, C.fecha, C.hora, C.motivo, C.estado, " +
                     "P_Pac.dni AS dniPaciente, P_Pac.nombre AS nombrePaciente, " +
                     "P_Pac.apellidoPaterno AS apPaternoPaciente, P_Pac.apellidoMaterno AS apMaternoPaciente, " +
                     "P_Emp.nombre AS nombreEmpleado, P_Emp.apellidoPaterno AS apPaternoEmpleado, " +
                     "P_Emp.apellidoMaterno AS apMaternoEmpleado, " +
                     "AMB.nombre AS ambienteNombre " +
                     "FROM CITA C " +
                     "INNER JOIN PACIENTE PA ON C.idPaciente = PA.idPersona " +
                     "INNER JOIN PERSONA P_Pac ON PA.idPersona = P_Pac.idPersona " +
                     "INNER JOIN EMPLEADO EM ON C.idEmpleado = EM.idPersona " +
                     "INNER JOIN PERSONA P_Emp ON EM.idPersona = P_Emp.idPersona " +
                     "LEFT JOIN AMBIENTE AMB ON C.idAmbiente = AMB.idAmbiente " +
                     "WHERE C.estado IN ('PROGRAMADA', 'CONFIRMADA') " +
                     "ORDER BY C.fecha, C.hora";

        try (Connection conn = Conexion.getConnect().getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Construir paciente con builder
                EPaciente paciente = EPaciente.builder()
                    .setDni(rs.getString("dniPaciente"))
                    .setNombre(rs.getString("nombrePaciente"))
                    .setApellidoPaterno(rs.getString("apPaternoPaciente"))
                    .setApellidoMaterno(rs.getString("apMaternoPaciente"))
                    .build();

                // Construir empleado con builder
                E_Empleado empleado = E_Empleado.builder()
                    .setNombre(rs.getString("nombreEmpleado"))
                    .setApellidoPaterno(rs.getString("apPaternoEmpleado"))
                    .setApellidoMaterno(rs.getString("apMaternoEmpleado"))
                    .build();

                // Construir ambiente con builder
                EAmbiente ambiente = new AmbienteBuilder()
                    .setNombre(rs.getString("ambienteNombre"))
                    .Build();

                // Construir cita con builder
                ECita cita = new CitaBuilder()
                    .setIdCita(rs.getString("idCita"))
                    .setFecha(rs.getDate("fecha"))
                    .setHora(rs.getTime("hora") != null ? new java.util.Date(rs.getTime("hora").getTime()) : null)
                    .setMotivo(rs.getString("motivo"))
                    .setEstado(rs.getString("estado"))
                    .setPaciente(paciente)
                    .setEmpleado(empleado)
                    .setAmbiente(ambiente)
                    .Build();

                listaCitas.add(cita);
            }

        } catch (SQLException e) {
            System.err.println("ERROR SQL al listar citas disponibles: " + e.getMessage());
        }

        return listaCitas;
    }

    
    public List<EAmbiente> listarAmbientesDisponibles(){
         List<EAmbiente> listaAmbientes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null; 
        ResultSet rs = null; 
        
        String sql = "SELECT idAmbiente, nombre, capacidad, disponibilidad " +
                "FROM AMBIENTE " + 
                "WHERE disponibilidad = 1  " + 
                "ORDER BY nombre " ; 
        try{
            Conexion conex = Conexion.getConnect(); 
            conn = conex.getConexion(); 
            if(conn == null){
                System.out.println("ERROR: no se pudo establecer conexion");
                return listaAmbientes; 
            }
            
            pstmt = conn.prepareStatement(sql); 
            rs = pstmt.executeQuery();
            
            while (rs.next()){
                 EAmbiente ambiente = new EAmbiente ();
                 ambiente.setIdAmbiente(rs.getString("idAmbiente"));
                 ambiente.setNombre(rs.getString("nombre"));
                 ambiente.setCapacidad(rs.getInt("capacidad"));
                 ambiente.setDisponibilidad(rs.getBoolean("disponibildad"));
                 
                 listaAmbientes.add(ambiente);
            }
        }catch (SQLException e){
            System.out.println("ERROR SQL al listar ambientes: " + e.getMessage());
        }finally {
            try{
                if(rs != null ) rs.close();
                if(pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            }catch (SQLException e){
                System.out.println("ERROR al cerrar recursos : " + e.getMessage());
            }
        }
        return listaAmbientes; 
    }
    
    public EAtencion buscarAtencionPorId(String idAtencion) {
        String sql = "SELECT A.idAtencion, A.descripcion, A.observaciones, A.fecha, " +
                     "H.idHistorial, H.fechaCreacion AS historialFechaCreacion, H.alergias, H.condicionesCronicas, H.medicamentosFrecuentes, " +
                     "PA.tipoSangre, PA.fechaRegistro AS pacienteFechaRegistro, " +
                     "P_Pac.idPersona AS idPersonaPaciente, P_Pac.nombre AS nombrePaciente, P_Pac.apellidoPaterno AS apPaternoPaciente, P_Pac.apellidoMaterno AS apMaternoPaciente, P_Pac.genero AS generoPaciente, P_Pac.dni AS dniPaciente, P_Pac.rol AS rolPaciente, P_Pac.telefono AS telPaciente, P_Pac.correo AS correoPaciente, P_Pac.direccion AS dirPaciente, P_Pac.fechaNacimiento AS fnacPaciente, " +
                     "DI.idDiagnostico, DI.descripcionAdicional AS diagnosticoDescripcion, DI.observaciones AS diagnosticoObservaciones, DI.procedimiento AS diagnosticoProcedimiento, " +
                     "EM.disponibilidad, EM.estado, EM.fechaIngreso, " +
                     "P_Emp.idPersona AS idPersonaEmpleado, P_Emp.nombre AS nombreEmpleado, P_Emp.apellidoPaterno AS apPaternoEmpleado, P_Emp.apellidoMaterno AS apMaternoEmpleado, P_Emp.genero AS generoEmpleado, P_Emp.dni AS dniEmpleado, P_Emp.rol AS rolEmpleado, P_Emp.telefono AS telEmpleado, P_Emp.correo AS correoEmpleado, P_Emp.direccion AS dirEmpleado, P_Emp.fechaNacimiento AS fnacEmpleado, " +
                     "ESP.idEspecialidad, ESP.nombre AS nombreEspecialidad, ESP.descripcion AS descripcionEspecialidad, " +
                     "R.idReceta, R.descripcionAdicional AS recetaDescripcion, " +
                     "C.idCita, C.fecha AS citaFecha, C.hora AS citaHora, C.motivo AS citaMotivo, C.estado AS citaEstado, " +
                     "AMB.idAmbiente, AMB.nombre AS ambienteNombre, AMB.capacidad, AMB.disponibilidad AS ambienteDisponibilidad " +
                     "FROM ATENCION A " +
                     "INNER JOIN HISTORIAL_MEDICO H ON A.idHistorial = H.idHistorial " +
                     "INNER JOIN DIAGNOSTICO DI ON A.idDiagnostico = DI.idDiagnostico " +
                     "INNER JOIN EMPLEADO EM ON A.idEmpleado = EM.idPersona " +
                     "INNER JOIN PERSONA P_Emp ON EM.idPersona = P_Emp.idPersona " +
                     "LEFT JOIN ESPECIALIDAD ESP ON EM.idEspecialidad = ESP.idEspecialidad " +
                     "LEFT JOIN RECETA R ON A.idReceta = R.idReceta " +
                     "LEFT JOIN CITA C ON A.idCita = C.idCita " +
                     "LEFT JOIN PACIENTE PA ON H.idPaciente = PA.idPersona " +
                     "LEFT JOIN PERSONA P_Pac ON PA.idPersona = P_Pac.idPersona " +
                     "LEFT JOIN AMBIENTE AMB ON C.idAmbiente = AMB.idAmbiente " +
                     "WHERE A.idAtencion = ?";

        try (Connection conn = Conexion.getConnect().getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idAtencion);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Construir paciente
                    EPaciente paciente = EPaciente.builder()
                        .setIdPersona(rs.getString("idPersonaPaciente"))
                        .setNombre(rs.getString("nombrePaciente"))
                        .setApellidoPaterno(rs.getString("apPaternoPaciente"))
                        .setApellidoMaterno(rs.getString("apMaternoPaciente"))
                        .setGenero(rs.getString("generoPaciente"))
                        .setDni(rs.getString("dniPaciente"))
                        .setRol(Rol.fromDbValue(rs.getString("rolPaciente")))
                        .setTelefono(rs.getString("telPaciente"))
                        .setCorreo(rs.getString("correoPaciente"))
                        .setDireccion(rs.getString("dirPaciente"))
                        .setFechaNacimiento(rs.getDate("fnacPaciente"))
                        .setTipoSangre(TipoSangre.fromDbValue(rs.getString("tipoSangre")))
                        .setFechaRegistro(rs.getDate("pacienteFechaRegistro"))
                        .build();

                    // Construir historial
                    EHistorialMedico historial = new EHistorialMedico();
                    historial.setIdHistorial(rs.getString("idHistorial"));
                    historial.setFechaCreacion(rs.getDate("historialFechaCreacion").toLocalDate());
                    historial.setAlergias(rs.getString("alergias"));
                    historial.setCondicionesCronicas(rs.getString("condicionesCronicas"));
                    historial.setMedicamentosFrecuentes(rs.getString("medicamentosFrecuentes"));
                    historial.setPaciente(paciente);

                    // Construir diagnostico
                    EDiagnostico diagnostico = new EDiagnostico();
                    diagnostico.setIdDiagnostico(rs.getString("idDiagnostico"));
                    diagnostico.setDescripcionAdicional(rs.getString("diagnosticoDescripcion"));

                    // Construir especialidad
                    E_Especialidad especialidad = null;
                    String idEspecialidad = rs.getString("idEspecialidad");
                    if (idEspecialidad != null) {
                        especialidad = new E_Especialidad();
                        especialidad.setIdEspecialidad(idEspecialidad);
                        especialidad.setNombre(rs.getString("nombreEspecialidad"));
                        especialidad.setDescripcion(rs.getString("descripcionEspecialidad"));
                    }

                    // Construir empleado
                    E_Empleado empleado = new E_Empleado();
                    empleado.setIdPersona(rs.getString("idPersonaEmpleado"));
                    empleado.setNombre(rs.getString("nombreEmpleado"));
                    empleado.setApellidoPaterno(rs.getString("apPaternoEmpleado"));
                    empleado.setApellidoMaterno(rs.getString("apMaternoEmpleado"));
                    empleado.setGenero(rs.getString("generoEmpleado"));
                    empleado.setDni(rs.getString("dniEmpleado"));
                    empleado.setRol(Rol.fromDbValue(rs.getString("rolEmpleado")));
                    empleado.setTelefono(rs.getString("telEmpleado"));
                    empleado.setCorreo(rs.getString("correoEmpleado"));
                    empleado.setDireccion(rs.getString("dirEmpleado"));
                    empleado.setFechaNacimiento(rs.getDate("fnacEmpleado"));
                    empleado.setDisponibilidad(rs.getBoolean("disponibilidad"));
                    empleado.setEstado(rs.getBoolean("estado"));
                    empleado.setFechaIngreso(rs.getDate("fechaIngreso"));
                    empleado.setEspecialidad(especialidad);
                    empleado.setHorarios(new ArrayList<>());

                    // Construir ambiente
                    EAmbiente ambiente = null;
                    String idAmbiente = rs.getString("idAmbiente");
                    if (idAmbiente != null) {
                        ambiente = new EAmbiente();
                        ambiente.setIdAmbiente(idAmbiente);
                        ambiente.setNombre(rs.getString("ambienteNombre"));
                        ambiente.setCapacidad(rs.getInt("capacidad"));
                        ambiente.setDisponibilidad(rs.getBoolean("ambienteDisponibilidad"));
                    }

                    // Construir cita
                    ECita cita = null;
                    String idCita = rs.getString("idCita");
                    if (idCita != null) {
                        cita = new ECita();
                        cita.setIdCita(idCita);
                        cita.setFecha(rs.getDate("citaFecha"));
                        cita.setHora(rs.getTime("citaHora"));
                        cita.setMotivo(rs.getString("citaMotivo"));
                        cita.setEstado(rs.getString("citaEstado"));
                        cita.setAmbiente(ambiente);
                        cita.setEmpleado(empleado);
                        cita.setPaciente(paciente);
                    }

                    // Construir atencion
                    EAtencion atencion = new EAtencion();
                    atencion.setIdAtencion(rs.getString("idAtencion"));
                    atencion.setDescripcion(rs.getString("descripcion"));
                    atencion.setObservaciones(rs.getString("observaciones"));
                    atencion.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                    atencion.setHistorial(historial);
                    atencion.setDiagnostico(diagnostico);
                    atencion.setEmpleado(empleado);
                    atencion.setReceta(null); // Si tienes receta, cargarla aquí
                    atencion.setCita(cita);

                    return atencion;
                }
            }

        } catch (SQLException e) {
            System.err.println("ERROR SQL al buscar atención por ID: " + e.getMessage());
        }

        return null;
    }
}