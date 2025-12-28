package Entidades;

public enum DiaSemana {
    LUNES("Lunes"),
    MARTES("Martes"),
    MIERCOLES("Miércoles"), // OJO con el acento, asegúrate de que el valor en la BD sea exactamente igual
    JUEVES("Jueves"),
    VIERNES("Viernes"),
    SABADO("Sábado"),
    DOMINGO("Domingo");

    private final String dbValue;

    DiaSemana(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    // Método estático para convertir el String de la BD a la constante del enum
    public static DiaSemana fromDbValue(String dbValue) {
        for (DiaSemana dia : DiaSemana.values()) {
            if (dia.getDbValue().equalsIgnoreCase(dbValue)) {
                return dia;
            }
        }
        // Lanza una excepción si el valor de la BD no coincide con ninguna constante
        throw new IllegalArgumentException("Valor de dia de la semana inválido en la BD: " + dbValue);
    }
}