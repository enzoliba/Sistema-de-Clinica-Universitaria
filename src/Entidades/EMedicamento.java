package Entidades;

public class EMedicamento {
    private String idMedicamento;
    private String nombre;
    private String descripcion;
    private int stock;

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public EMedicamento() {
    }

    public EMedicamento(String idMedicamento, String nombre, String descripcion, int stock) {
        this.idMedicamento = idMedicamento;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "EMedicamento{" + "idMedicamento=" + idMedicamento + ", nombre=" + nombre + ", descripcion=" + descripcion + ", stock=" + stock + '}';
    }
    
}
