package Entidades;

import Interfaces.IBuilder;

public class E_Especialidad {
    private String idEspecialidad;
    private String nombre;
    private String descripcion;

    public String getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(String idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public E_Especialidad() {
    }

    public E_Especialidad(String idEspecialidad, String nombre, String descripcion) {
        this.idEspecialidad = idEspecialidad;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    public class EspecialidadBuilder implements IBuilder<E_Especialidad>{
        private E_Especialidad especialidad;
        public EspecialidadBuilder() {
            especialidad = new E_Especialidad();
        }
        public EspecialidadBuilder setIdEspecialidad(String idEspecialidad) {
            especialidad.setIdEspecialidad(idEspecialidad);
            return this;
        }
        public EspecialidadBuilder setNombre(String nombre) {
            especialidad.setNombre(nombre);
            return this;
        }
        public EspecialidadBuilder setDescripcion(String descripcion) {
            especialidad.setDescripcion(descripcion);
            return this;
        }

        @Override
        public E_Especialidad Build() {
            return especialidad;
        }
    }
    
    @Override
    public String toString() {
        return "E_Especialidad{" + "idEspecialidad=" + idEspecialidad + ", nombre=" + nombre + ", descripcion=" + descripcion + '}';
    }
}
