package Datos;

import Entidades.EAmbiente;
import Entidades.ECita;
import Entidades.EPaciente;
import Entidades.E_Empleado;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Types;
import java.sql.Time;

public class DCita {
    private ResultSet rs; //para recoger el resultado de una consulta
    
        public ArrayList<ECita> listarCitas(){ // con instrucciones sql
            ArrayList<ECita> lista = new ArrayList<>();
            Conexion con = new Conexion();
            String sql = "select * from cita;";

            try{
                Conexion conex = Conexion.getConnect();
                Statement st = conex.getConexion().createStatement();
                rs = st.executeQuery(sql);

                while (rs.next()) {
                    EPaciente pac = new EPaciente();
                    pac.setIdPersona("idPersona");
                    
                    E_Empleado emp = new E_Empleado();
                    emp.setIdPersona("idEmpleado");
                    
                    EAmbiente amb = new EAmbiente();
                    amb.setIdAmbiente("idAmbiente");
                    ECita cita = new ECita.CitaBuilder()
                        .setIdCita(rs.getString("idCita"))
                        .setFecha(rs.getDate("fecha"))
                        .setHora(rs.getTime("hora") != null ? new java.util.Date(rs.getTime("hora").getTime()) : null)
                        .setMotivo(rs.getString("motivo"))
                        .setEstado(rs.getString("estado"))
                        .setEmpleado(emp)
                        .setPaciente(pac)
                        .setAmbiente(amb)
                        .Build();

                    lista.add(cita);
                }


            } catch(SQLException e){
                System.out.println("Error: " + e.getMessage());
            }

            return lista;
        }
    
    public void registrarCita (ECita cit) throws SQLException{
        Connection cn = null;
        CallableStatement smt = null;
        Conexion con = new Conexion();
        
        try {
            cn = con.getConexion();
            String sql = "{CALL usp_RegistrarCita(?, ?, ?, ?, ?, ?, ?, ?)}";
            smt = cn.prepareCall(sql);
            smt.setString(1, cit.getIdCita());
            //Fecha de la cita
            if (cit.getFecha() != null) {
                smt.setDate(2, new java.sql.Date(cit.getFecha().getTime())); // ¡CONVERSIÓN!
            } else {
                // Si la fecha de la Cita es obligatorio en la BD, esto causaría un error SQL
                // Si no es obligatorio, setear a NULL
                smt.setNull(2, Types.DATE); 
            }
            //Hora de la cita
            if (cit.getHora() != null) {
                smt.setTime(3, new java.sql.Time(cit.getHora().getTime()));  
            } else {
                smt.setNull(3, java.sql.Types.TIME); 
            }
            smt.setString(4, cit.getMotivo());
            smt.setString(5, cit.getEstado());
            smt.setString(6, cit.getPaciente().getIdPersona());
            //EMPLEADO
            smt.setString(7, cit.getEmpleado().getIdPersona());
            //CONSULTORIO
            smt.setString(8, cit.getAmbiente().getIdAmbiente());
            
            smt.execute();
            
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }finally{
            if (smt != null) smt.close();
            if (cn != null) cn.close();
        }
    }
    
    public static String generarCodigoCitaConTransaccion() throws SQLException {
        String codigoGenerado = null;
        Conexion con = new Conexion();
        Connection conn = con.getConexion();

        try {
            conn.setAutoCommit(false);  // iniciar transaccion

            // bloqueo de tablas para evitar conflictos
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("LOCK TABLES CITA WRITE");
            }

            // generar el codigo desde el sp
            try (CallableStatement cstmt = conn.prepareCall("{call usp_GenerarCodigoCita(?)}")) {
                cstmt.registerOutParameter(1, Types.VARCHAR);
                cstmt.execute();
                codigoGenerado = cstmt.getString(1);
            }

            // liberamos las tablas previamente bloqueadas
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
