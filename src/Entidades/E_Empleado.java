package Entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class E_Empleado extends EPersona {
    
    private boolean disponibilidad;
    private boolean estado;
    private Date fechaIngreso;
    private E_Especialidad especialidad; 
    private List<EHorario> horarios; 

    // Getters y Setters
    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public E_Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(E_Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public List<EHorario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<EHorario> horarios) {
        this.horarios = horarios;
    }
    
    public void agregarHorario(EHorario ho) {
        if (horarios == null) {
            horarios = new ArrayList<>();
        }
        horarios.add(ho);
    }

    // Constructores
    public E_Empleado() {
        horarios = new ArrayList<>();
    }

    public E_Empleado(boolean disponibilidad, boolean estado, Date fechaIngreso, 
                     E_Especialidad especialidad, List<EHorario> horarios) {
        this.disponibilidad = disponibilidad;
        this.estado = estado;
        this.fechaIngreso = fechaIngreso;
        this.especialidad = especialidad;
        this.horarios = horarios != null ? horarios : new ArrayList<>();
    }

    public E_Empleado(boolean disponibilidad, boolean estado, Date fechaIngreso, 
                     E_Especialidad especialidad, List<EHorario> horarios, 
                     String idPersona, String nombre, String apellidoPaterno, 
                     String apellidoMaterno, String dni, String genero, Rol rol, 
                     String telefono, String correo, String direccion, Date fechaNacimiento) {
        super(idPersona, nombre, apellidoPaterno, apellidoMaterno, dni, genero, 
              rol, telefono, correo, direccion, fechaNacimiento);
        this.disponibilidad = disponibilidad;
        this.estado = estado;
        this.fechaIngreso = fechaIngreso;
        this.especialidad = especialidad;
        this.horarios = horarios != null ? horarios : new ArrayList<>();
    }

    public static class EmpleadoBuilder extends PersonaBuilder<EmpleadoBuilder> {
        private boolean disponibilidad = true; // valor por defecto
        private boolean estado = true; // valor por defecto
        private Date fechaIngreso;
        private E_Especialidad especialidad;
        private List<EHorario> horarios;

        public EmpleadoBuilder() {
            this.horarios = new ArrayList<>();
        }

        public EmpleadoBuilder setDisponibilidad(boolean disponibilidad) {
            this.disponibilidad = disponibilidad;
            return this;
        }

        public EmpleadoBuilder setEstado(boolean estado) {
            this.estado = estado;
            return this;
        }

        public EmpleadoBuilder setFechaIngreso(Date fechaIngreso) {
            this.fechaIngreso = fechaIngreso;
            return this;
        }

        public EmpleadoBuilder setEspecialidad(E_Especialidad especialidad) {
            this.especialidad = especialidad;
            return this;
        }

        public EmpleadoBuilder setHorarios(List<EHorario> horarios) {
            this.horarios = horarios != null ? new ArrayList<>(horarios) : new ArrayList<>();
            return this;
        }

        public EmpleadoBuilder agregarHorario(EHorario horario) {
            if (this.horarios == null) {
                this.horarios = new ArrayList<>();
            }
            this.horarios.add(horario);
            return this;
        }

        public EmpleadoBuilder agregarHorarios(List<EHorario> horarios) {
            if (horarios != null) {
                if (this.horarios == null) {
                    this.horarios = new ArrayList<>();
                }
                this.horarios.addAll(horarios);
            }
            return this;
        }

        @Override
        protected EmpleadoBuilder self() {
            return this;
        }

        @Override
        public E_Empleado build() {
            E_Empleado empleado = new E_Empleado();
            
            // Establecer propiedades de la clase padre (Persona)
            empleado.setIdPersona(this.idPersona);
            empleado.setNombre(this.nombre);
            empleado.setApellidoPaterno(this.apellidoPaterno);
            empleado.setApellidoMaterno(this.apellidoMaterno);
            empleado.setDni(this.dni);
            empleado.setGenero(this.genero);
            empleado.setRol(this.rol);
            empleado.setTelefono(this.telefono);
            empleado.setCorreo(this.correo);
            empleado.setDireccion(this.direccion);
            empleado.setFechaNacimiento(this.fechaNacimiento);
            
            // Establecer propiedades específicas de Empleado
            empleado.setDisponibilidad(this.disponibilidad);
            empleado.setEstado(this.estado);
            empleado.setFechaIngreso(this.fechaIngreso);
            empleado.setEspecialidad(this.especialidad);
            empleado.setHorarios(this.horarios != null ? new ArrayList<>(this.horarios) : new ArrayList<>());
            
            return empleado;
        }

        @Override
        public EPersona Build() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
    public static EmpleadoBuilder builder() {
        return new EmpleadoBuilder();
    }

    @Override
    public String toString() {
        return "E_Empleado{" + 
               "idPersona=" + getIdPersona() + 
               ", nombre=" + getNombre() + 
               ", apellidoPaterno=" + getApellidoPaterno() + 
               ", apellidoMaterno=" + getApellidoMaterno() + 
               ", dni=" + getDni() + 
               ", disponibilidad=" + disponibilidad + 
               ", estado=" + estado + 
               ", fechaIngreso=" + fechaIngreso + 
               ", especialidad=" + especialidad + 
               ", horarios=" + horarios + 
               '}';
    }
}
