package co.edu.uniquindio.cinemauq.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Proyeccion programada de una pelicula en una sala, fecha y hora especificas.
 * El valor base es el precio general de referencia para esa funcion; el
 * precio final que paga cada cliente se calcula aplicando la tarifa de su
 * tipo de cliente (ver {@link CinemaUQ#venderEntrada}).
 */
public class Funcion {

    private String codigo;
    private Pelicula pelicula;
    private Sala sala;
    private LocalDate fecha;
    private LocalTime hora;
    private double valorBase;
    private EstadoFuncion estado;

    public Funcion(String codigo, Pelicula pelicula, Sala sala, LocalDate fecha, LocalTime hora, double valorBase) {
        this.codigo = codigo;
        this.pelicula = pelicula;
        this.sala = sala;
        this.fecha = fecha;
        this.hora = hora;
        this.valorBase = valorBase;
        this.estado = EstadoFuncion.PROGRAMADA;
    }

    public void finalizar() {
        estado = EstadoFuncion.FINALIZADA;
    }

    public void cancelar() {
        estado = EstadoFuncion.CANCELADA;
    }

    public boolean estaProgramada() {
        return estado == EstadoFuncion.PROGRAMADA;
    }

    public int contarAsientosDisponibles() {
        return sala.contarAsientosDisponibles();
    }

    public int contarAsientosOcupados() {
        return sala.contarAsientosOcupados();
    }

    public String getCodigo() {
        return codigo;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public Sala getSala() {
        return sala;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public double getValorBase() {
        return valorBase;
    }

    public EstadoFuncion getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return codigo + " - " + pelicula.getNombre() + " - Sala " + sala.getNombre()
                + " - " + fecha + " " + hora + " - $" + (long) valorBase + " - " + estado;
    }
}
