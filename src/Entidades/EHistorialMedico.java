package Entidades;

import Interfaces.IBuilder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EHistorialMedico {
    private String idHistorial;
    private LocalDate fechaCreacion;
    private String alergias;
    private String condicionesCronicas;
    private String medicamentosFrecuentes;
    private EPaciente paciente;  
    private List<EAtencion> atenciones;

    public String getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(String idHistorial) {
        this.idHistorial = idHistorial;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getCondicionesCronicas() {
        return condicionesCronicas;
    }

    public void setCondicionesCronicas(String condicionesCronicas) {
        this.condicionesCronicas = condicionesCronicas;
    }

    public String getMedicamentosFrecuentes() {
        return medicamentosFrecuentes;
    }

    public void setMedicamentosFrecuentes(String medicamentosFrecuentes) {
        this.medicamentosFrecuentes = medicamentosFrecuentes;
    }

    public EPaciente getPaciente() {
        return paciente;
    }

    public void setPaciente(EPaciente paciente) {
        this.paciente = paciente;
    }

    public List<EAtencion> getAtenciones() {
        return atenciones;
    }

    public void setAtenciones(List<EAtencion> atenciones) {
        this.atenciones = atenciones;
    }
    
    public void agregarAtencion(EAtencion aten){
        atenciones.add(aten);
    }

    public EHistorialMedico() {
        atenciones = new ArrayList<>();
    }

    public EHistorialMedico(String idHistorial, LocalDate fechaCreacion, String alergias, String condicionesCronicas, String medicamentosFrecuentes, EPaciente paciente) {
        this.idHistorial = idHistorial;
        this.fechaCreacion = fechaCreacion;
        this.alergias = alergias;
        this.condicionesCronicas = condicionesCronicas;
        this.medicamentosFrecuentes = medicamentosFrecuentes;
        this.paciente = paciente;
    }
    
    public class HistorialMedicoBuilder implements IBuilder<EHistorialMedico>{
        private EHistorialMedico historial;

        public HistorialMedicoBuilder() {
            historial = new EHistorialMedico();
            historial.setAtenciones(new ArrayList<>()); // Inicializar lista para evitar NullPointerException
        }

        public HistorialMedicoBuilder setIdHistorial(String idHistorial) {
            historial.setIdHistorial(idHistorial);
            return this;
        }

        public HistorialMedicoBuilder setFechaCreacion(LocalDate fechaCreacion) {
            historial.setFechaCreacion(fechaCreacion);
            return this;
        }

        public HistorialMedicoBuilder setAlergias(String alergias) {
            historial.setAlergias(alergias);
            return this;
        }

        public HistorialMedicoBuilder setCondicionesCronicas(String condicionesCronicas) {
            historial.setCondicionesCronicas(condicionesCronicas);
            return this;
        }

        public HistorialMedicoBuilder setMedicamentosFrecuentes(String medicamentosFrecuentes) {
            historial.setMedicamentosFrecuentes(medicamentosFrecuentes);
            return this;
        }

        public HistorialMedicoBuilder setPaciente(EPaciente paciente) {
            historial.setPaciente(paciente);
            return this;
        }

        public HistorialMedicoBuilder setAtenciones(List<EAtencion> atenciones) {
            historial.setAtenciones(atenciones != null ? new ArrayList<>(atenciones) : new ArrayList<>());
            return this;
        }

        public HistorialMedicoBuilder agregarAtencion(EAtencion atencion) {
            if (historial.getAtenciones() == null) {
                historial.setAtenciones(new ArrayList<>());
            }
            historial.getAtenciones().add(atencion);
            return this;
        }

        @Override
        public EHistorialMedico Build() {
            return historial;
        }
    }


    @Override
    public String toString() {
        return "EHistorialMedico{" + "idHistorial=" + idHistorial + ", fechaCreacion=" + fechaCreacion + ", alergias=" + alergias + ", condicionesCronicas=" + condicionesCronicas + ", medicamentosFrecuentes=" + medicamentosFrecuentes + ", paciente=" + paciente + '}';
    }
}
