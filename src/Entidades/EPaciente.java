package Entidades;

import java.util.Date;

public class EPaciente extends EPersona{
    private TipoSangre tipoSangre;
    private Date fechaRegistro;

    public TipoSangre getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(TipoSangre tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public EPaciente() {
    }

    private EPaciente(TipoSangre tipoSangre, Date fechaRegistro) {
        this.tipoSangre = tipoSangre;
        this.fechaRegistro = fechaRegistro;
    }

    private EPaciente(TipoSangre tipoSangre, Date fechaRegistro, String idPersona, String nombre, String apellidoPaterno, String apellidoMaterno, String dni, String genero, Rol rol, String telefono, String correo, String direccion, Date fechaNacimiento) {
        super(idPersona, nombre, apellidoPaterno, apellidoMaterno, dni, genero, rol, telefono, correo, direccion, fechaNacimiento);
        this.tipoSangre = tipoSangre;
        this.fechaRegistro = fechaRegistro;
    }
    
    public static class PacienteBuilder extends EPersona.PersonaBuilder<PacienteBuilder> {
        private TipoSangre tipoSangre;
        private Date fechaRegistro;

        public PacienteBuilder setTipoSangre(TipoSangre tipoSangre) {
            this.tipoSangre = tipoSangre;
            return this;
        }

        public PacienteBuilder setFechaRegistro(Date fechaRegistro) {
            this.fechaRegistro = fechaRegistro;
            return this;
        }

        @Override
        protected PacienteBuilder self() {
            return this;
        }

         @Override
        public EPaciente build() {
            EPaciente paciente = new EPaciente(tipoSangre, fechaRegistro, idPersona, nombre, apellidoPaterno, apellidoMaterno, dni,
                                                genero, rol, telefono, correo, direccion, fechaNacimiento);
            return paciente;
        }

        @Override
        public EPersona Build() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }

    public static PacienteBuilder builder() {
        return new PacienteBuilder();
    }

    @Override
    public String toString() {
        return "Paciente{" + "tipoSangre=" + tipoSangre + ", fechaRegistro=" + fechaRegistro + '}';
    }
}
