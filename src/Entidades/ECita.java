package Entidades;

import Interfaces.IBuilder;
import java.util.Date;

public class ECita {
    private String idCita;
    private Date fecha;
    private Date hora;
    private String motivo;
    private String estado;
    private EPaciente pac;
    private E_Empleado emp;
    private EAmbiente amb;

    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public EPaciente getPaciente(){
        return pac;
    }
    
    public void setPaciente( EPaciente pac ){
        this.pac = pac;
    }

    public E_Empleado getEmpleado(){
        return emp;
    }
    
    public void setEmpleado( E_Empleado emp ){
        this.emp = emp;
    }

    public EAmbiente getAmbiente() {
        return amb;
    }

    public void setAmbiente(EAmbiente amb) {
        this.amb = amb;
    }

    public ECita() {
    }

    public ECita(String idCita, Date fecha, Date hora, String motivo, String estado, EPaciente pac, E_Empleado emp, EAmbiente amb) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = estado;
        this.pac = pac;
        this.emp = emp;
        this.amb = amb;
    }

    public static class CitaBuilder implements IBuilder<ECita>{
        private ECita cita;

        public CitaBuilder() {
            cita = new ECita();
        }

        public CitaBuilder setIdCita(String idCita) {
            cita.setIdCita(idCita);
            return this;
        }

        public CitaBuilder setFecha(Date fecha) {
            cita.setFecha(fecha);
            return this;
        }

        public CitaBuilder setHora(Date hora) {
            cita.setHora(hora);
            return this;
        }

        public CitaBuilder setMotivo(String motivo) {
            cita.setMotivo(motivo);
            return this;
        }

        public CitaBuilder setEstado(String estado) {
            cita.setEstado(estado);
            return this;
        }

        public CitaBuilder setPaciente(EPaciente paciente) {
            cita.setPaciente(paciente);
            return this;
        }

        public CitaBuilder setEmpleado(E_Empleado emp) {
            cita.setEmpleado(emp);
            return this;
        }

        public CitaBuilder setAmbiente(EAmbiente ambiente) {
            cita.setAmbiente(ambiente);
            return this;
        }

        @Override
        public ECita Build() {
            return cita;
        }
    }
    
    public static CitaBuilder builder() {
        return new CitaBuilder();
    }

    @Override
    public String toString() {
        return "ECita{" + "idCita=" + idCita + ", fecha=" + fecha + ", hora=" + hora + ", motivo=" + motivo + ", estado=" + estado + ", Paciente=" + pac + ", Empleado=" + emp + ", Ambiente=" + amb + '}';
    }

    
}
