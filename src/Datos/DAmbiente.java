package Datos;

import Entidades.EAmbiente;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DAmbiente {
    
    private ResultSet rs; //para recoger el resultado de una consulta
    
    public ArrayList<EAmbiente> listarAmbientes(){ // con instrucciones sql
        ArrayList<EAmbiente> lista = new ArrayList<>();
        Conexion con = new Conexion();
        String sql = "SELECT * FROM AMBIENTE ORDER BY idAmbiente ASC;";
        
        try{
            Conexion conex = Conexion.getConnect();
            Statement st = conex.getConexion().createStatement();
            rs = st.executeQuery(sql);
            
            while( rs.next() ){
                EAmbiente amb = new EAmbiente();
                
                amb.setIdAmbiente(rs.getString("idAmbiente"));
                amb.setNombre(rs.getString("nombre"));
                amb.setCapacidad(rs.getInt("capacidad"));
                amb.setDisponibilidad(rs.getBoolean("disponibilidad"));
                
                lista.add(amb);
            }
            
        } catch(SQLException e){
            
        }
        
        return lista;
    }
    
    public boolean actualizarAmbiente(EAmbiente ambiente) {
        Connection conn = null;
        CallableStatement stm = null;
        
        try {
            conn = Conexion.getConnect().getConexion();
            if (conn == null) {
                System.err.println("ERROR: No se pudo establecer la conexión");
                return false;
            }

            stm = conn.prepareCall("{CALL usp_ActualizarAmbiente(?, ?, ?, ?)}");

            // Establecer parámetros para AMBIENTE
            stm.setString(1, ambiente.getIdAmbiente());
            stm.setString(2, ambiente.getNombre());
            stm.setString(3, String.valueOf(ambiente.getCapacidad()));
            stm.setBoolean(4, ambiente.isDisponibilidad());
          
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
    
    public void buscarPorCodigo(JTextField NombreBusqueda, JTextField CodigoRes, JTextField NombreRes, JTextField CapacidadRes, JTextField DisponibilidadRes){
        String consulta = "SELECT idAmbiente, nombre, capacidad, disponibilidad FROM AMBIENTE where nombre =(?) ;";
        Conexion con = new Conexion();
        try {
            CallableStatement cs = con.getConexion().prepareCall(consulta) ;
            cs.setString(1, NombreBusqueda.getText());
            cs.execute();
            
            ResultSet rs = cs.executeQuery();
            
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "REGISTRO ENCONTRADO");
                CodigoRes.setText(rs.getString("idAmbiente"));
                NombreRes.setText(rs.getString("nombre"));
                CapacidadRes.setText(String.valueOf(rs.getInt("capacidad")));
                boolean res = rs.getBoolean("disponibilidad");
                if (res == true){
                    String resu = "Disponible";
                    DisponibilidadRes.setText(resu);
                }else{
                    String resu = "No Disponible";
                    DisponibilidadRes.setText(resu);
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "REGISTRO NO ENCONTRADO");
                CodigoRes.setText(null);
                NombreRes.setText(null);
                CapacidadRes.setText(null);
                DisponibilidadRes.setText(null);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR" + ex.toString());
        }
    }
    
    public void registrarAmbiente( EAmbiente amb ) throws SQLException{
        Connection cn = null;
        CallableStatement smt = null;
        Conexion con = new Conexion();
        
        try {
            cn = con.getConexion();
            String sql = "{CALL usp_RegistrarAmbiente(?, ?, ?, ?)}";
            smt = cn.prepareCall(sql);
            smt.setString(1, amb.getIdAmbiente());
            smt.setString(2, amb.getNombre());
            smt.setInt(3, amb.getCapacidad());
            smt.setBoolean(4, amb.isDisponibilidad());
            smt.execute();
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }finally{
            if (smt != null) smt.close();
            if (cn != null) cn.close();
        }
    }
    
    public void consultar_consultorios(JComboBox ComboBoxConsultorio){
        Connection conn = null;
        Conexion con = new Conexion();
        String SSQL = "{CALL usp_GeneracionComboBoxConsultorio}";
        try {
            //LIMPIAR DATOS PREDETERMINADOS DEL COMBOBOX
            ComboBoxConsultorio.removeAllItems();
            //ESTABLECER CONEXION CON LA BD
            conn = Conexion.getConnect().getConexion();
            //PREPARAR CONSULTA SQL
            PreparedStatement pst = conn.prepareStatement(SSQL);
            //EJECUTAR CONSULTA SQL
            ResultSet result = pst.executeQuery();
            //LLENAMOS EL COMBOBOX
            ComboBoxConsultorio.addItem("Seleccionar");
            while(result.next()){
                ComboBoxConsultorio.addItem(result.getString("nombre"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }finally{
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        }
    }
    
    public EAmbiente buscarAmbientePorId(String idAmbiente){
        EAmbiente ambiente = null;
        Connection conn = null;
        CallableStatement st = null;
        rs = null;
        
        String sql = "{CALL usp_BuscarAmbientePorId(?)}";
        
        try {
            conn = Conexion.getConnect().getConexion();
            if (conn == null) {
                System.err.println("ERROR: No se pudo establecer la conexión a la base de datos.");
                return null;
            }
            st = conn.prepareCall(sql);
            st.setString(1, idAmbiente);
            rs = st.executeQuery();
            if (rs.next()) {
                ambiente = new EAmbiente();
                ambiente.setIdAmbiente(rs.getString("idAmbiente"));
                ambiente.setNombre(rs.getString("nombre"));
                ambiente.setCapacidad(rs.getInt("capacidad"));
                ambiente.setDisponibilidad(rs.getBoolean("disponibilidad"));
            }
        } catch (SQLException e) {
            System.err.println("ERROR SQL al buscar ambiente por ID: " + e.getMessage());
            e.printStackTrace();
        } finally{
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("ERROR al cerrar recursos DB: " + e.getMessage());
            }
        }
        return ambiente;
    }
    
    public String buscarIdPorNombre(String Nombre) throws SQLException{
        rs = null;
        Conexion con = new Conexion();
        Connection conn = con.getConexion();
        CallableStatement smt = null;
        
        smt = conn.prepareCall("{CALL usp_BuscarPorNombre(?)}");
        try {
            smt.setString(1, Nombre);
            rs = smt.executeQuery();
            if(rs.next()){
            String idAmbiente = rs.getString("idAmbiente");
            return idAmbiente;
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return null;
    }
    
    public static String generarCodigoAmbienteConTransaccion() throws SQLException {
        String codigoGenerado = null;
        Conexion con = new Conexion();
        Connection conn = con.getConexion();

        try {
            conn.setAutoCommit(false);  // iniciar transaccion

            // bloqueo de tablas para evitar conflictos
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("LOCK TABLES AMBIENTE WRITE");
            }

            // generar el codigo desde el sp
            try (CallableStatement cstmt = conn.prepareCall("{call usp_GenerarCodigoAmbiente(?)}")) {
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
