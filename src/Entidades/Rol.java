package Entidades;


public enum Rol {
    ESTUDIANTE("Estudiante"),
    MEDICO("Medico"),
    ENFERMERO("Enfermero"),
    ADMINISTRATIVO("Administrativo"),
    DOCENTE("Docente"),
    SEGURIDAD("Seguridad"),
    LIMPIEZA("Limpieza");

    private final String dbValue;

    Rol(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static Rol fromDbValue(String dbValue) {
        for (Rol rol : Rol.values()) {
            if (rol.getDbValue().equalsIgnoreCase(dbValue)) {
                return rol;
            }
        }
        throw new IllegalArgumentException("Valor de rol inválido en la BD: " + dbValue);
    }
}
