package Entidades;

import Interfaces.IBuilder;
import java.util.Date;

public abstract class EPersona {
    private String idPersona;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String dni;
    private String genero;
    private Rol rol;
    private String telefono;
    private String correo;
    private String direccion;
    private Date fechaNacimiento;

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getDni() {
        return dni;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public EPersona() {
    }

    public EPersona(String idPersona, String nombre, String apellidoPaterno, String apellidoMaterno, String dni, String genero, Rol rol, String telefono, String correo, String direccion, Date fechaNacimiento) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.dni = dni;
        this.genero = genero;
        this.rol = rol;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public static abstract class PersonaBuilder<B extends PersonaBuilder<B>> implements IBuilder<EPersona> {
        protected String idPersona;
        protected String nombre;
        protected String apellidoPaterno;
        protected String apellidoMaterno;
        protected String dni;
        protected String genero;
        protected Rol rol;
        protected String telefono;
        protected String correo;
        protected String direccion;
        protected Date fechaNacimiento;

        public B setIdPersona(String idPersona) {
            this.idPersona = idPersona;
            return self();
        }

        public B setNombre(String nombre) {
            this.nombre = nombre;
            return self();
        }

        public B setApellidoPaterno(String apellidoPaterno) {
            this.apellidoPaterno = apellidoPaterno;
            return self();
        }

        public B setApellidoMaterno(String apellidoMaterno) {
            this.apellidoMaterno = apellidoMaterno;
            return self();
        }

        public B setDni(String dni) {
            this.dni = dni;
            return self();
        }

        public B setGenero(String genero) {
            this.genero = genero;
            return self();
        }

        public B setRol(Rol rol) {
            this.rol = rol;
            return self();
        }

        public B setTelefono(String telefono) {
            this.telefono = telefono;
            return self();
        }

        public B setCorreo(String correo) {
            this.correo = correo;
            return self();
        }

        public B setDireccion(String direccion) {
            this.direccion = direccion;
            return self();
        }

        public B setFechaNacimiento(Date fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
            return self();
        }

        protected abstract B self();
        
        public abstract EPersona build();
    }


    @Override
    public String toString() {
        return "Persona{" + "idPersona=" + idPersona + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", dni=" + dni + ", rol=" + rol.getDbValue() + ", telefono=" + telefono + ", correo=" + correo + ", direccion=" + direccion + ", fechaNacimiento=" + fechaNacimiento + '}';
    }
}
