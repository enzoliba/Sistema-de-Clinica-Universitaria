package Entidades;

import Interfaces.IBuilder;

public class EAmbiente {
    private String idAmbiente;
    private String nombre;
    private int capacidad;
    private boolean disponibilidad;

    public String getIdAmbiente() {
        return idAmbiente;
    }

    public void setIdAmbiente(String idAmbiente) {
        this.idAmbiente = idAmbiente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public EAmbiente() {
    }

    public EAmbiente(String idAmbiente, String nombre, int capacidad, boolean disponibilidad) {
        this.idAmbiente = idAmbiente;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.disponibilidad = disponibilidad;
    }
    
    public static class AmbienteBuilder implements IBuilder<EAmbiente>{
        private EAmbiente ambiente;
        public AmbienteBuilder() {
            ambiente = new EAmbiente();
        }
        public AmbienteBuilder setIdAmbiente(String idAmbiente) {
            ambiente.setIdAmbiente(idAmbiente);
            return this;
        }
        public AmbienteBuilder setNombre(String nombre) {
            ambiente.setNombre(nombre);
            return this;
        }
        public AmbienteBuilder setCapacidad(int capacidad) {
            ambiente.setCapacidad(capacidad);
            return this;
        }
        public AmbienteBuilder setDisponibilidad(boolean disponibilidad) {
            ambiente.setDisponibilidad(disponibilidad);
            return this;
        }

        @Override
        public EAmbiente Build() {
            return ambiente;
        }
    }

    @Override
    public String toString() {
        return "EAmbiente{" + "idAmbiente=" + idAmbiente + ", nombre=" + nombre + ", capacidad=" + capacidad + ", disponibilidad=" + disponibilidad + '}';
    }
    
    
}
