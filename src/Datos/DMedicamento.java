package Datos;

import Entidades.EMedicamento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;

public class DMedicamento {
    private ResultSet rs; //para recoger el resultado de una consulta
    
    public ArrayList<EMedicamento> listarMedicamentos1(){ // con instrucciones sql
        ArrayList<EMedicamento> lista = new ArrayList<>();
        Conexion con = new Conexion();
        String sql = "SELECT * FROM MEDICAMENTO;";
        
        try{
            Conexion conex = Conexion.getConnect();
            Statement st = conex.getConexion().createStatement();
            rs = st.executeQuery(sql);
            
            while( rs.next() ){
                EMedicamento med = new EMedicamento();
                
                med.setIdMedicamento(rs.getString("idMedicamento"));
                med.setNombre(rs.getString("nombre"));
                med.setDescripcion(rs.getString("descripcion"));
                med.setStock(rs.getInt("stock"));
                
                lista.add(med);
            }
            
        } catch(SQLException e){
            
        }
        
        return lista;
    }
}
