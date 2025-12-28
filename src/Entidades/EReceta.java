package Entidades;

import Interfaces.IBuilder;
import java.util.ArrayList;
import java.util.List;

public class EReceta {
    private String idReceta;
    private String descripcionAdicional;
    
    // Una Receta tiene muchos detalles de receta (medicamentos específicos con dosis, etc.)
    private List<EDetalleReceta> detallesMedicamentos; // Cambiado de 'detallesExamenes' a 'detallesMedicamentos'

    public String getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(String idReceta) {
        this.idReceta = idReceta;
    }

    public String getDescripcionAdicional() {
        return descripcionAdicional;
    }

    public void setDescripcionAdicional(String descripcionAdicional) {
        this.descripcionAdicional = descripcionAdicional;
    }

    public List<EDetalleReceta> getDetallesMedicamentos() {
        return detallesMedicamentos;
    }

    public void setDetallesMedicamentos(List<EDetalleReceta> detallesMedicamentos) {
        this.detallesMedicamentos = detallesMedicamentos;
    }

    public EReceta(String idReceta, String descripcionAdicional, List<EDetalleReceta> detallesMedicamentos) {
        this.idReceta = idReceta;
        this.descripcionAdicional = descripcionAdicional;
        this.detallesMedicamentos = detallesMedicamentos;
    }    
    
    public EReceta(){
        this.detallesMedicamentos = new ArrayList<>(); // Inicializar lista
    }

    public class RecetaBuilder implements IBuilder<EReceta>{
        private EReceta receta;

        public RecetaBuilder() {
            receta = new EReceta();
        }

        public RecetaBuilder setIdReceta(String idReceta) {
            receta.setIdReceta(idReceta);
            return this;
        }

        public RecetaBuilder setDescripcionAdicional(String descripcionAdicional) {
            receta.setDescripcionAdicional(descripcionAdicional);
            return this;
        }

        public RecetaBuilder setDetallesMedicamentos(List<EDetalleReceta> detallesMedicamentos) {
            receta.setDetallesMedicamentos(detallesMedicamentos != null ? new ArrayList<>(detallesMedicamentos) : new ArrayList<>());
            return this;
        }

        public RecetaBuilder agregarDetalleMedicamento(EDetalleReceta detalle) {
            if (receta.getDetallesMedicamentos() == null) {
                receta.setDetallesMedicamentos(new ArrayList<>());
            }
            receta.getDetallesMedicamentos().add(detalle);
            return this;
        }

        @Override
        public EReceta Build() {
            return receta;
        }
    }
    
    @Override
    public String toString() {
        return "EReceta{" + "idReceta=" + idReceta + ", descripcionAdicional=" + descripcionAdicional + ", detallesMedicamentos=" + detallesMedicamentos + '}';
    }
    
    
}
