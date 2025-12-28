package Datos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class DHorario {
    public static String generarCodigoHorarioConTransaccion() throws SQLException{
        String codigoGenerado = null;
        Conexion con = new Conexion();
        Connection conn = con.getConexion();

        try {
            conn.setAutoCommit(false);  // iniciar transaccion

            // generar el codigo desde el sp
            try (CallableStatement cstmt = conn.prepareCall("{call usp_GenerarCodigoHorario(?)}")) {
                cstmt.registerOutParameter(1, Types.VARCHAR);
                cstmt.execute();
                codigoGenerado = cstmt.getString(1);
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
