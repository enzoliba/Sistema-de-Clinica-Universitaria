package Entidades;

public class E_ExamenMedico {
    private String idExamenMedico;
    private String tipoExamen;
    private String procedimiento;

    public String getIdExamenMedico() {
        return idExamenMedico;
    }

    public void setIdExamenMedico(String idExamenMedico) {
        this.idExamenMedico = idExamenMedico;
    }

    public String getTipoExamen() {
        return tipoExamen;
    }

    public void setTipoExamen(String tipoExamen) {
        this.tipoExamen = tipoExamen;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public E_ExamenMedico() {
    }

    public E_ExamenMedico(String idExamenMedico, String tipoExamen, String procedimiento) {
        this.idExamenMedico = idExamenMedico;
        this.tipoExamen = tipoExamen;
        this.procedimiento = procedimiento;
    }

    @Override
    public String toString() {
        return "E_ExamenMedico{" + "idExamenMedico=" + idExamenMedico + ", tipoExamen=" + tipoExamen + ", procedimiento=" + procedimiento + '}';
    }
}
