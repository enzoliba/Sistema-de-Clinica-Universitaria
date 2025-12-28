package Datos;

import Entidades.DiaSemana;
import Entidades.EHorario;
import Entidades.E_Empleado;
import Entidades.E_Especialidad;
import Entidades.Rol;
import Utils.GeneradorCode;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Time;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class DEmpleado {

    private ResultSet rs; 
    
    public List<E_Empleado> listarBuscarEmpleado(String id, String apellido, String dni, String rol, String idEspecialidad, int pagina, int tamañoPagina) throws SQLException {
        List<E_Empleado> listaEmpleados = new ArrayList<>();

        Connection conn = null;
        ResultSet rs = null;
        CallableStatement smt = null;

        try {
            conn = Conexion.getConnect().getConexion();
            smt = conn.prepareCall("{call usp_EmpleadoLisBusPag(?,?,?,?,?,?,?)}");

            smt.setString(1, id);
            smt.setString(2, apellido);
            smt.setString(3, dni);
            
            //Si rol es nulo, que no filtre por rol
            if(rol != null && rol.equals("Todos")){
                rol = null;
            }
            smt.setString(4, rol);
            smt.setString(5, idEspecialidad);
            smt.setInt(6, pagina);
            smt.setInt(7, tamañoPagina);

            rs = smt.executeQuery();

            while (rs.next()) {
                String espNombre;
                E_Especialidad esp = new E_Especialidad();
                esp.setIdEspecialidad(rs.getString("idEspecialidad"));
                if(rs.getString("nombreEsp") == null || rs.getString("nombreEsp").isEmpty()){
                    espNombre = "No tiene";
                }else{
                    espNombre = rs.getString("nombreEsp");
                }
                esp.setNombre(espNombre);
                esp.setDescripcion(rs.getString("descripcion"));
                
                E_Empleado.EmpleadoBuilder builder = E_Empleado.builder()
                    .setIdPersona(rs.getString("idPersona"))
                    .setNombre(rs.getString("nombrePersona"))
                    .setApellidoPaterno(rs.getString("apellidoPaterno"))
                    .setApellidoMaterno(rs.getString("apellidoMaterno"))
                    .setGenero(rs.getString("genero"))
                    .setDni(rs.getString("dni"))
                    .setTelefono(rs.getString("telefono"))
                    .setCorreo(rs.getString("correo"))
                    .setDireccion(rs.getString("direccion"))
                    .setFechaNacimiento(rs.getDate("fechaNacimiento"))
                    .setDisponibilidad(rs.getBoolean("disponibilidad"))
                    .setEstado(rs.getBoolean("estado"))
                    .setFechaIngreso(rs.getDate("fechaIngreso"))
                    .setEspecialidad(esp);

                try {
                    builder.setRol(Rol.fromDbValue(rs.getString("rol")));
                } catch (IllegalArgumentException e) {
                    System.err.println("Rol inválido en BD: " + rs.getString("rol"));
                    builder.setRol(null);
                }

                listaEmpleados.add(builder.build());
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("página inválido")) {
                return listarBuscarEmpleado(id, apellido, dni, rol, idEspecialidad, 1, 10);
            }
            throw new RuntimeException("Error al buscar empleados", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (smt != null) smt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return listaEmpleados;
    }

    public void registrarEmpleado(E_Empleado emp) throws SQLException {
        Connection cn = null;
        CallableStatement smt = null;
        Conexion con = new Conexion();

        try {
            cn = con.getConexion();
            String sql = "{CALL usp_RegistrarEmpleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            smt = cn.prepareCall(sql);

            // Parámetros de PERSONA (herencia)
            smt.setString(1, emp.getIdPersona());
            smt.setString(2, emp.getNombre());
            smt.setString(3, emp.getApellidoPaterno());
            smt.setString(4, emp.getApellidoMaterno());
            smt.setString(5, emp.getGenero());
            smt.setString(6, emp.getDni());
            smt.setString(7, emp.getRol().getDbValue());
            smt.setString(8, emp.getTelefono());
            smt.setString(9, emp.getCorreo());
            smt.setString(10, emp.getDireccion());

            // Fecha de nacimiento
            if (emp.getFechaNacimiento() != null) {
                smt.setDate(11, new java.sql.Date(emp.getFechaNacimiento().getTime()));
            } else {
                smt.setNull(11, Types.DATE);
            }

            // Parámetros específicos de EMPLEADO
            smt.setBoolean(12, emp.isDisponibilidad());
            smt.setBoolean(13, emp.isEstado());

            // Fecha de ingreso
            if (emp.getFechaIngreso() != null) {
                smt.setDate(14, new java.sql.Date(emp.getFechaIngreso().getTime()));
            } else {
                smt.setNull(14, Types.DATE);
            }

            // Especialidad (puede ser NULL)
            if (emp.getEspecialidad() != null && emp.getEspecialidad().getIdEspecialidad() != null) {
                smt.setString(15, emp.getEspecialidad().getIdEspecialidad());
            } else {
                smt.setNull(15, Types.VARCHAR);
            }

            smt.execute();

        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (smt != null) smt.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para registrar empleado con conexión existente (para transacciones)
    private void registrarEmpleadoConConexion(E_Empleado emp, Connection cn) throws SQLException {
        CallableStatement smt = null;

        try {
            String sql = "{CALL usp_RegistrarEmpleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            smt = cn.prepareCall(sql);

            // Parámetros de PERSONA (herencia)
            smt.setString(1, emp.getIdPersona());
            smt.setString(2, emp.getNombre());
            smt.setString(3, emp.getApellidoPaterno());
            smt.setString(4, emp.getApellidoMaterno());
            smt.setString(5, emp.getGenero());
            smt.setString(6, emp.getDni());
            smt.setString(7, emp.getRol().getDbValue());
            smt.setString(8, emp.getTelefono());
            smt.setString(9, emp.getCorreo());
            smt.setString(10, emp.getDireccion());

            // Fecha de nacimiento
            if (emp.getFechaNacimiento() != null) {
                smt.setDate(11, new java.sql.Date(emp.getFechaNacimiento().getTime()));
            } else {
                smt.setNull(11, Types.DATE);
            }

            // Parámetros específicos de EMPLEADO
            smt.setBoolean(12, emp.isDisponibilidad());
            smt.setBoolean(13, emp.isEstado());

            // Fecha de ingreso
            if (emp.getFechaIngreso() != null) {
                smt.setDate(14, new java.sql.Date(emp.getFechaIngreso().getTime()));
            } else {
                smt.setNull(14, Types.DATE);
            }

            // Especialidad (puede ser NULL)
            if (emp.getEspecialidad() != null && emp.getEspecialidad().getIdEspecialidad() != null) {
                smt.setString(15, emp.getEspecialidad().getIdEspecialidad());
            } else {
                smt.setNull(15, Types.VARCHAR);
            }

            smt.execute();

        } finally {
            if (smt != null) smt.close();
            // NO cerrar la conexión aquí, la maneja quien la envió
        }
    }

    // MÉTODO PRINCIPAL CON ATOMICIDAD COMPLETA
    public void registrarEmpleadoCompleto(E_Empleado emp) throws SQLException {
        Connection cn = null;
        Conexion con = new Conexion();

        try {
            cn = con.getConexion();
            cn.setAutoCommit(false); // *** INICIAR TRANSACCIÓN ***

            // PASO 1: Registrar el empleado
            System.out.println("Registrando empleado: " + emp.getIdPersona());
            registrarEmpleadoConConexion(emp, cn);

            // PASO 2: Crear y asignar horarios si existen
            if (emp.getHorarios() != null && !emp.getHorarios().isEmpty()) {
                System.out.println("Creando horarios para empleado: " + emp.getIdPersona());
                crearYAsignarHorarios(emp.getIdPersona(), emp.getHorarios(), cn);
            }

            // *** SI TODO SALE BIEN, CONFIRMAR TRANSACCIÓN ***
            cn.commit();
            System.out.println("Empleado registrado exitosamente con todos sus horarios");

        } catch (SQLException e) {
            System.err.println("Error en registrarEmpleadoCompleto: " + e.getMessage());

            // *** SI HAY ERROR, DESHACER TODO ***
            if (cn != null) {
                try {
                    System.out.println("Deshaciendo transacción...");
                    cn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Error en rollback: " + rollbackEx.getMessage());
                    rollbackEx.printStackTrace();
                }
            }
            throw new SQLException("Error al registrar empleado completo: " + e.getMessage(), e);

        } finally {
            try {
                if (cn != null) {
                    cn.setAutoCommit(true); // Restaurar autocommit
                    cn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // MÉTODO AUXILIAR CORREGIDO - Genera IDs y maneja errores
    private void crearYAsignarHorarios(String idEmpleado, List<EHorario> horarios, Connection cn) throws SQLException {
        PreparedStatement smtHorario = null;
        PreparedStatement smtRelacion = null;

        try {
            // Verificar que el empleado existe antes de continuar
            if (!existeEmpleado(idEmpleado, cn)) {
                throw new SQLException("El empleado " + idEmpleado + " no existe");
            }

            // Preparar statements
            String sqlHorario = "INSERT INTO HORARIO (idHorario, dia, horaEntrada, horaSalida) VALUES (?, ?, ?, ?)";
            String sqlRelacion = "INSERT INTO EMPLEADO_HORARIO (idEmpleado, idHorario) VALUES (?, ?)";

            smtHorario = cn.prepareStatement(sqlHorario);
            smtRelacion = cn.prepareStatement(sqlRelacion);

            // Procesar cada horario
            for (int i = 0; i < horarios.size(); i++) {
                EHorario horario = horarios.get(i);
                if (horario != null) {

                    // *** GENERAR ID SI NO EXISTE ***
                    if (horario.getIdHorario() == null || horario.getIdHorario().isEmpty()) {
                        String nuevoId = GeneradorCode.CodeGenerador("HOR", null);
                        horario.setIdHorario(nuevoId);
                        System.out.println("ID generado para horario " + i + ": " + nuevoId);
                    }

                    // *** VALIDAR LONGITUD ***
                    if (horario.getIdHorario().length() > 15) { // Ajusta según tu BD
                        throw new SQLException("ID Horario demasiado largo (" + 
                            horario.getIdHorario().length() + " caracteres): " + horario.getIdHorario());
                    }

                    // *** VALIDAR DATOS ***
                    if (horario.getDia() == null || horario.getHoraEntrada() == null || horario.getHoraSalida() == null) {
                        throw new SQLException("Datos incompletos en horario " + i + 
                            ": día=" + horario.getDia() + 
                            ", entrada=" + horario.getHoraEntrada() + 
                            ", salida=" + horario.getHoraSalida());
                    }

                    try {
                        // Insertar horario
                        smtHorario.setString(1, horario.getIdHorario());
                        smtHorario.setString(2, horario.getDia().getDbValue());
                        smtHorario.setTime(3, Time.valueOf(horario.getHoraEntrada()));
                        smtHorario.setTime(4, Time.valueOf(horario.getHoraSalida()));
                        smtHorario.addBatch();

                        // Preparar relación
                        smtRelacion.setString(1, idEmpleado);
                        smtRelacion.setString(2, horario.getIdHorario());
                        smtRelacion.addBatch();

                    } catch (Exception e) {
                        throw new SQLException("Error procesando horario " + i + ": " + e.getMessage(), e);
                    }
                }
            }

            // *** EJECUTAR EN ORDEN ***
            System.out.println("Insertando " + horarios.size() + " horarios...");
            int[] resultadosHorarios = smtHorario.executeBatch();
            System.out.println("Horarios insertados: " + resultadosHorarios.length);

            System.out.println("Creando relaciones empleado-horario...");
            int[] resultadosRelaciones = smtRelacion.executeBatch();
            System.out.println("Relaciones creadas: " + resultadosRelaciones.length);

        } catch (SQLException e) {
            System.err.println("Error en crearYAsignarHorarios: " + e.getMessage());
            throw e; // Re-lanzar para que se haga rollback
        } finally {
            if (smtHorario != null) smtHorario.close();
            if (smtRelacion != null) smtRelacion.close();
        }
    }

    // MÉTODO AUXILIAR - Verificar que el empleado existe
    private boolean existeEmpleado(String idEmpleado, Connection cn) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT COUNT(*) FROM EMPLEADO WHERE idPersona = ?";
            smt = cn.prepareStatement(sql);
            smt.setString(1, idEmpleado);
            rs = smt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;

        } finally {
            if (rs != null) rs.close();
            if (smt != null) smt.close();
        }
    }

    // MÉTODO SIMPLE PARA CASOS SIN HORARIOS (mantén tu método original intacto)
    public void registrarEmpleadoSinHorarios(E_Empleado emp) throws SQLException {
        registrarEmpleado(emp); // Usa tu método original
    }

    public ArrayList<E_Empleado> listarPersonalConHorarios(String rolFiltro) {
        ArrayList<E_Empleado> listaEmpleados = new ArrayList<>();
        
        // Declaramos los recursos JDBC aquí para poder cerrarlos en el bloque finally
        Connection conn = null;
        Statement st = null;
        rs = null;

        String sql = "SELECT " +
                     "    P.idPersona, P.nombre, P.apellidoPaterno, P.apellidoMaterno, P.genero, P.dni, P.rol, P.telefono, P.correo, P.direccion, P.fechaNacimiento, " +
                     "    E.disponibilidad, E.estado, E.fechaIngreso, " +
                     "    ESP.idEspecialidad, ESP.nombre AS nombreEspecialidad, ESP.descripcion AS descripcionEspecialidad, " +
                     "    H.idHorario, H.dia, H.horaEntrada, H.horaSalida " +
                     "FROM PERSONA P " +
                     "INNER JOIN EMPLEADO E ON P.idPersona = E.idPersona " +
                     "LEFT JOIN ESPECIALIDAD ESP ON E.idEspecialidad = ESP.idEspecialidad " + // LEFT JOIN porque Especialidad puede ser NULL
                     "LEFT JOIN EMPLEADO_HORARIO EH ON E.idPersona = EH.idEmpleado " + 
                     "LEFT JOIN HORARIO H ON EH.idHorario = H.idHorario "; // LEFT JOIN si un empleado podría no tener horarios
                
        // Si hay un filtro de rol, añadir la cláusula WHERE
        if (rolFiltro != null && !rolFiltro.isEmpty() && !rolFiltro.equals("Todos")) {
            sql += "WHERE P.rol = '" + rolFiltro + "'"; // Añade el filtro por rol
        }
        
        sql += "ORDER BY P.idPersona, H.idHorario"; // Siempre ordenar

        try {
            Conexion conex = Conexion.getConnect();
            conn = conex.getConexion(); // Obtener la conexión usando tu clase Conexion
            if (conn == null) {
                System.out.println("ERROR: No se pudo establecer la conexión a la base de datos.");
                return listaEmpleados; // Devolver lista vacía si no hay conexión
            }

            st = conn.createStatement();
            rs = st.executeQuery(sql);

            E_Empleado empleadoActual = null; // Variable para almacenar el empleado que estamos construyendo

            while (rs.next()) {
                String idPersonaFilaActual = rs.getString("idPersona");

                // --- Lógica para detectar si es un nuevo empleado o un horario adicional del mismo empleado ---
                // Si empleadoActual es null (primera fila del ResultSet)
                // O si el idPersona de la fila actual es diferente al del empleado que estamos construyendo
                if (empleadoActual == null || !empleadoActual.getIdPersona().equals(idPersonaFilaActual)) {
                    // Es un nuevo empleado. Creamos una nueva instancia y la agregamos a la lista principal.
                    empleadoActual = new E_Empleado();
                    
                    // --- Cargar Atributos de PERSONA ---
                    empleadoActual.setIdPersona(idPersonaFilaActual); // Correcto: lee el valor de la BD
                    empleadoActual.setNombre(rs.getString("nombre"));
                    empleadoActual.setApellidoPaterno(rs.getString("apellidoPaterno"));
                    empleadoActual.setApellidoMaterno(rs.getString("apellidoMaterno"));
                    empleadoActual.setGenero(rs.getString("genero")); // ¡Asegúrate de cargar el género!
                    empleadoActual.setDni(rs.getString("dni"));
                    
                    String rolDbValue = rs.getString("rol");
                    try {
                        empleadoActual.setRol(Rol.fromDbValue(rolDbValue));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Advertencia: Valor de rol inválido en la BD para persona " + idPersonaFilaActual + ": " + rolDbValue);
                        empleadoActual.setRol(null); // O podrías asignar un Rol.DESCONOCIDO si lo tienes
                    }
                    
                    empleadoActual.setTelefono(rs.getString("telefono"));
                    empleadoActual.setCorreo(rs.getString("correo"));
                    empleadoActual.setDireccion(rs.getString("direccion"));
                    empleadoActual.setFechaNacimiento(rs.getDate("fechaNacimiento")); // java.sql.Date a LocalDate

                    // --- Cargar Atributos de EMPLEADO ---
                    empleadoActual.setDisponibilidad(rs.getBoolean("disponibilidad"));
                    empleadoActual.setEstado(rs.getBoolean("estado"));
                    empleadoActual.setFechaIngreso(rs.getDate("fechaIngreso")); // java.sql.Date a LocalDate

                    // --- Cargar Especialidad (si existe) ---
                    String idEspecialidad = rs.getString("idEspecialidad");
                    if (idEspecialidad != null) { // Solo crear Especialidad si hay datos
                        E_Especialidad esp = new E_Especialidad();
                        esp.setIdEspecialidad(idEspecialidad);
                        esp.setNombre(rs.getString("nombreEspecialidad")); // Usa el alias
                        esp.setDescripcion(rs.getString("descripcionEspecialidad")); // Usa el alias
                        empleadoActual.setEspecialidad(esp);
                    } else {
                        empleadoActual.setEspecialidad(null); // Asegurar que es null si no hay especialidad
                    }
                    
                    // Inicializar la lista de horarios para el NUEVO empleado
                    // La clase E_Empleado ya hace esto en su constructor por defecto

                    listaEmpleados.add(empleadoActual); // Añadir el nuevo empleado a la lista final
                }

                // --- Ahora, procesar el Horario de la fila actual y añadirlo al empleadoActual ---
                // Esto se ejecuta para CADA fila del ResultSet.
                // Si la fila actual representa un horario para el empleadoActual
                String idHorario = rs.getString("idHorario");
                if (idHorario != null) { // Solo si hay datos de horario en esta fila (LEFT JOIN)
                    EHorario ho = new EHorario(); 
                    ho.setIdHorario(idHorario);
                    
                    String diaDbValue = rs.getString("dia");
                    try {
                        ho.setDia(DiaSemana.fromDbValue(diaDbValue));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Advertencia: Valor de día inválido en la BD para horario " + idHorario + ": " + diaDbValue);
                        ho.setDia(null);
                    }
                    
                    // Correcto: usar rs.getTime() para columnas TIME y convertir a LocalTime
                    ho.setHoraEntrada(rs.getTime("horaEntrada").toLocalTime());
                    ho.setHoraSalida(rs.getTime("horaSalida").toLocalTime());
                    
                    // Añadir el horario a la lista del empleado que estamos construyendo
                    empleadoActual.agregarHorario(ho);
                }
            }
            
        } catch(SQLException e){
            System.err.println("ERROR SQL al listar empleados con horarios: " + e.getMessage());
        } finally {
            // Cierre de recursos en el bloque finally, asegurando que se cierren incluso si hay excepciones
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conn != null) conn.close(); // Cierra la conexión si no usas un pool de conexiones gestionado
            } catch (SQLException e) {
                System.err.println("ERROR al cerrar recursos de la base de datos: " + e.getMessage());
            }
        }
        
        return listaEmpleados;
    }
    
    public E_Empleado buscarIdPorApellido(String ApellidoP, String ApellidoM) throws SQLException{
        rs=null;
        Conexion con = new Conexion();
        Connection conn = con.getConexion();
        CallableStatement smt = null;
        E_Empleado empleadoActual = new E_Empleado();
        
        smt = conn.prepareCall("{CALL usp_BuscarPorApellido(?, ?)}");
        try {
            smt.setString(1, ApellidoP);
            smt.setString(2, ApellidoM);
            rs = smt.executeQuery();
            if(rs.next()) {
                String idPersonaFilaActual = rs.getString("idPersona");

                // --- Lógica para detectar si es un nuevo empleado o un horario adicional del mismo empleado ---
                // Si empleadoActual es null (primera fila del ResultSet)
                // O si el idPersona de la fila actual es diferente al del empleado que estamos construyendo
                if (empleadoActual == null || !empleadoActual.getIdPersona().equals(idPersonaFilaActual)) {
                    // Es un nuevo empleado. Creamos una nueva instancia y la agregamos a la lista principal.
                    empleadoActual = new E_Empleado();
                    
                    // --- Cargar Atributos de PERSONA ---
                    empleadoActual.setIdPersona(idPersonaFilaActual); // Correcto: lee el valor de la BD
                    empleadoActual.setNombre(rs.getString("nombre"));
                    empleadoActual.setApellidoPaterno(rs.getString("apellidoPaterno"));
                    empleadoActual.setApellidoMaterno(rs.getString("apellidoMaterno"));
                    empleadoActual.setGenero(rs.getString("genero")); // ¡Asegúrate de cargar el género!
                    empleadoActual.setDni(rs.getString("dni"));
                    
                    String rolDbValue = rs.getString("rol");
                    try {
                        empleadoActual.setRol(Rol.fromDbValue(rolDbValue));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Advertencia: Valor de rol inválido en la BD para persona " + idPersonaFilaActual + ": " + rolDbValue);
                        empleadoActual.setRol(null); // O podrías asignar un Rol.DESCONOCIDO si lo tienes
                    }
                    
                    empleadoActual.setTelefono(rs.getString("telefono"));
                    empleadoActual.setCorreo(rs.getString("correo"));
                    empleadoActual.setDireccion(rs.getString("direccion"));
                    empleadoActual.setFechaNacimiento(rs.getDate("fechaNacimiento")); // java.sql.Date a LocalDate

                    // --- Cargar Atributos de EMPLEADO ---
                    empleadoActual.setDisponibilidad(rs.getBoolean("disponibilidad"));
                    empleadoActual.setEstado(rs.getBoolean("estado"));
                    empleadoActual.setFechaIngreso(rs.getDate("fechaIngreso")); // java.sql.Date a LocalDate

                    // --- Cargar Especialidad (si existe) ---
                    String idEspecialidad = rs.getString("idEspecialidad");
                    if (idEspecialidad != null) { // Solo crear Especialidad si hay datos
                        E_Especialidad esp = new E_Especialidad();
                        esp.setIdEspecialidad(idEspecialidad);
                        esp.setNombre(rs.getString("nombreEspecialidad")); // Usa el alias
                        esp.setDescripcion(rs.getString("descripcionEspecialidad")); // Usa el alias
                        empleadoActual.setEspecialidad(esp);
                    } else {
                        empleadoActual.setEspecialidad(null); // Asegurar que es null si no hay especialidad
                    }
                }

                // --- Ahora, procesar el Horario de la fila actual y añadirlo al empleadoActual ---
                // Esto se ejecuta para CADA fila del ResultSet.
                // Si la fila actual representa un horario para el empleadoActual
                String idHorario = rs.getString("idHorario");
                if (idHorario != null) { // Solo si hay datos de horario en esta fila (LEFT JOIN)
                    EHorario ho = new EHorario(); 
                    ho.setIdHorario(idHorario);
                    
                    String diaDbValue = rs.getString("dia");
                    try {
                        ho.setDia(DiaSemana.fromDbValue(diaDbValue));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Advertencia: Valor de día inválido en la BD para horario " + idHorario + ": " + diaDbValue);
                        ho.setDia(null);
                    }
                    
                    // Correcto: usar rs.getTime() para columnas TIME y convertir a LocalTime
                    ho.setHoraEntrada(rs.getTime("horaEntrada").toLocalTime());
                    ho.setHoraSalida(rs.getTime("horaSalida").toLocalTime());
                    
                    // Añadir el horario a la lista del empleado que estamos construyendo
                    empleadoActual.agregarHorario(ho);
                }
            }
            return empleadoActual;
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return null;
    }
    
    public static String generarCodigoEmpleadoConTransaccion(String rol) throws SQLException {
        String codigoGenerado = null;
        Conexion con = new Conexion();
        Connection conn = con.getConexion();

        try {
            conn.setAutoCommit(false);  // iniciar transaccion

            // bloqueo de tablas para evitar conflictos
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("LOCK TABLES PERSONA WRITE, EMPLEADO WRITE");
            }

            // generar el codigo desde el sp
            try (CallableStatement cstmt = conn.prepareCall("{call usp_GenerarCodigoEmpleado(?, ?)}")) {
                cstmt.setString(1, rol);
                cstmt.registerOutParameter(2, Types.VARCHAR);
                cstmt.execute();
                codigoGenerado = cstmt.getString(2);
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
    
    
    public void consultar_medicos(JComboBox ComboBoxMedico){
        Connection conn = null;
        Conexion con = new Conexion();
        String SSQL = "{CALL usp_GeneracionComboBoxMedico}";
        try {
            //LIMPIAR LOS DATOS PREDETERMINADOS DEL COMBOBOX
            ComboBoxMedico.removeAllItems();
            //ESTABLECER CONEXION CON LA BD
            conn = Conexion.getConnect().getConexion();
            //PREPARA LA CONSULTA SQL
            PreparedStatement pst = conn.prepareStatement(SSQL);
            //EJECUTAR LA CONSULTA SQL  
            ResultSet result = pst.executeQuery();
            //LLENAMOS EL COMBOBOX
            ComboBoxMedico.addItem("Seleccionar");
            while(result.next()){
                ComboBoxMedico.addItem(result.getString("apellidoPaterno")+ " " + result.getString("apellidoMaterno") + " " + result.getString("nombre"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        }
    } 
}
