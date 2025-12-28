package Entidades;

import java.time.LocalTime;
import java.util.Date;

public class EHorario {
    private String idHorario;
    private DiaSemana dia;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    
    public String getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(String idHorario) {
        this.idHorario = idHorario;
    }

    public DiaSemana getDia() {
        return dia;
    }

    public void setDia(DiaSemana dia) {
        this.dia = dia;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public EHorario() {
    }

    public EHorario(String idHorario, DiaSemana dia, LocalTime horaEntrada, LocalTime horaSalida) {
        this.idHorario = idHorario;
        this.dia = dia;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }
}
