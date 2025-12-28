package Entidades;

import java.util.ArrayList;
import java.util.List;

public class EDiagnostico {
    private String idDiagnostico;
    private String descripcionAdicional;
    
    // Un Diagnostico puede tener muchos detalles de examen
    // Cada DetalleDiagnostico representa la unión con un ExamenMedico y sus atributos específicos
    private List<EDetalleExamenes> listaDetallesExamenes;

    public String getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(String idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public String getDescripcionAdicional() {
        return descripcionAdicional;
    }

    public void setDescripcionAdicional(String descripcionAdicional) {
        this.descripcionAdicional = descripcionAdicional;
    }

    public List<EDetalleExamenes> getListaDetallesExamenes() {
        return listaDetallesExamenes;
    }

    public void setListaDetallesExamenes(List<EDetalleExamenes> listaDetallesExamenes) {
        this.listaDetallesExamenes = listaDetallesExamenes;
    }
    
    public EDiagnostico() {
        this.listaDetallesExamenes = new ArrayList<>(); // Inicializar lista
    }

    public EDiagnostico(String idDiagnostico, String descripcionAdicional, List<EDetalleExamenes> listaDetallesExamenes) {
        this.idDiagnostico = idDiagnostico;
        this.descripcionAdicional = descripcionAdicional;
        this.listaDetallesExamenes = listaDetallesExamenes;
    }

    @Override
    public String toString() {
        return "EDiagnostico{" + "idDiagnostico=" + idDiagnostico + ", descripcionAdicional=" + descripcionAdicional + ", listaDetallesExamenes=" + listaDetallesExamenes + '}';
    }
}
