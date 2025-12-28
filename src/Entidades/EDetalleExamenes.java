package Entidades;

import java.time.LocalDate;

public class EDetalleExamenes {
     // No tiene su propio ID primario, sus PKs son las FKs compuestas.
    // Aquí almacenamos las referencias a los objetos completos.
    private EDiagnostico diagnostico; // Referencia al objeto Diagnostico (la "parte padre" de esta relación)
    private E_ExamenMedico examenMedico; // Referencia al objeto ExamenMedico (la "parte hija" de esta relación)

    private String observaciones;
    private LocalDate fecha;

    public EDiagnostico getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(EDiagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }

    public E_ExamenMedico getExamenMedico() {
        return examenMedico;
    }

    public void setExamenMedico(E_ExamenMedico examenMedico) {
        this.examenMedico = examenMedico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public EDetalleExamenes() {
    }

    public EDetalleExamenes(EDiagnostico diagnostico, E_ExamenMedico examenMedico, String observaciones, LocalDate fecha) {
        this.diagnostico = diagnostico;
        this.examenMedico = examenMedico;
        this.observaciones = observaciones;
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "E_DetalleDiagnostico{" + "diagnostico=" + diagnostico + ", examenMedico=" + examenMedico + ", observaciones=" + observaciones + ", fecha=" + fecha + '}';
    }
    
    
}
