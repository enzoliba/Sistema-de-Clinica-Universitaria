package Utils;

import Datos.DAmbiente;
import Datos.DCita;
import Datos.DEmpleado;
import Datos.DHistorialMedico;
import Datos.DHorario;
import Datos.DPaciente;
import java.sql.SQLException;

public class GeneradorCode {
    
    public static String CodeGenerador(String opc, String rol) throws SQLException{
        switch(opc){
            case "EMP" -> {
                if( rol == null ){ break; }
                return DEmpleado.generarCodigoEmpleadoConTransaccion(rol);
            }
            case "PAC" -> {
                return DPaciente.generarCodigoPacienteConTransaccion();
            }
            case "CIT" ->{
                return DCita.generarCodigoCitaConTransaccion();
            }
            case "AMB" ->{
                return DAmbiente.generarCodigoAmbienteConTransaccion();
            }
            case "HOR" ->{
                return DHorario.generarCodigoHorarioConTransaccion();
            }
            case "HIS" ->{
                return DHistorialMedico.generarCodigoHistorialConTransaccion();
            }
        }
        return "No se pudo generar el Codigo";
    }
}
