package Entidades;

public enum TipoSangre {
    ONegativo("O-"),
    OPositivo("O+"),
    ANegativo("A-"),
    APositivo("A+"),
    BNegativo("B-"),
    BPositivo("B+"),
    ABNegativo("AB-"),
    ABPositivo("AB+");
    
    private final String dbValue;
    
    TipoSangre(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static TipoSangre fromDbValue(String dbValue) {
        for (TipoSangre tipoSangre : TipoSangre.values()) {
            if (tipoSangre.getDbValue().equalsIgnoreCase(dbValue)) {
                return tipoSangre;
            }
        }
        throw new IllegalArgumentException("Valor de rol inválido en la BD: " + dbValue);
    }
}
