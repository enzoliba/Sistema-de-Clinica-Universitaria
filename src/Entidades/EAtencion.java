/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.time.LocalDateTime;

/**
 *
 * @author guillermo
 */
public class EAtencion {
    
    private String idAtencion;
    private LocalDateTime fecha; 
    private String descripcion;  
    private String tratamiento;  
    private String observaciones;
    
    // Relaciones (claves foráneas como objetos)
    private EHistorialMedico historial;
    private EDiagnostico diagnostico;
    private E_Empleado empleado;
    private EReceta receta;
    private ECita cita;   

    public String getIdAtencion() {
        return idAtencion;
    }

    public void setIdAtencion(String idAtencion) {
        this.idAtencion = idAtencion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public EHistorialMedico getHistorial() {
        return historial;
    }

    public void setHistorial(EHistorialMedico historial) {
        this.historial = historial;
    }

    public EDiagnostico getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(EDiagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }

    public E_Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(E_Empleado empleado) {
        this.empleado = empleado;
    }

    public EReceta getReceta() {
        return receta;
    }

    public void setReceta(EReceta receta) {
        this.receta = receta;
    }

    public ECita getCita() {
        return cita;
    }

    public void setCita(ECita cita) {
        this.cita = cita;
    }

    public EAtencion() {
    }

    public EAtencion(String idAtencion, LocalDateTime fecha, String descripcion, String tratamiento, String observaciones, EHistorialMedico historial, EDiagnostico diagnostico, E_Empleado empleado, EReceta receta, ECita cita) {
        this.idAtencion = idAtencion;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.tratamiento = tratamiento;
        this.observaciones = observaciones;
        this.historial = historial;
        this.diagnostico = diagnostico;
        this.empleado = empleado;
        this.receta = receta;
        this.cita = cita;
    }

    @Override // el toString creo que no va
    public String toString() {
        return "EAtencion{" + "idAtencion=" + idAtencion + ", fecha=" + fecha + ", descripcion=" + descripcion + ", tratamiento=" + tratamiento + ", observaciones=" + observaciones + ", historial=" + historial + ", diagnostico=" + diagnostico + ", empleado=" + empleado + ", receta=" + receta + ", cita=" + cita + '}';
    }
    
    //nuevos metodos agregados 
    
    //para obtener el nombre completo del paciente 
    public String getNombreComletoPaciente (){
        if(cita != null && cita.getPaciente() != null){
            EPaciente paciente = cita.getPaciente();
            return paciente.getNombre() + "" + 
                   paciente.getApellidoPaterno() + "" + 
                   paciente.getApellidoMaterno();
        }
        return "N/A"; 
    }
    
    //para obtener el nombre completo del o "N/A" sino hay empleado
    public String getNombreCompletoEmpleado (){
        if(empleado != null){
            return empleado.getNombre() +"" + 
                    empleado.getApellidoPaterno()+"" + 
                    empleado.getApellidoMaterno();
        }
        return "N/A"; 
    }
    
    public String getEspecialidadEmpleado(){
        if(empleado != null && empleado.getEspecialidad() != null){
            return empleado.getEspecialidad().getNombre();
        }
        return "N/A"; 
    }
}
