package co.edu.uniquindio.cinemauq.modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Funcion {

    private String codigo;
    private Pelicula pelicula;
    private Sala sala;
    private LocalDate fecha;
    private LocalTime hora;
    private double valorBase;
    private EstadoFuncion estado;
    private ArrayList<Asiento> asientos;

    /**
     * Metodo constructor de la clase Funcion
     * @param codigo de la funcion
     * @param pelicula de la funcion
     * @param sala de la funcion
     * @param fecha de la funcion
     * @param hora de la funcion
     * @param valorBase de la funcion
     */
    public Funcion(String codigo, Pelicula pelicula, Sala sala, LocalDate fecha, LocalTime hora, double valorBase) {
        this.codigo = codigo;
        this.pelicula = pelicula;
        this.sala = sala;
        this.fecha = fecha;
        this.hora = hora;
        this.valorBase = valorBase;
        this.estado = EstadoFuncion.PROGRAMADA;
        this.asientos = copiarAsientosDeSala(sala);
    }

    /**
     * Metodo que permite crear una copia de los asientos de la sala para la funcion
     * @param salaFuncion sala de la cual se copian los asientos
     * @return lista con los asientos copiados, todos disponibles
     */
    private ArrayList<Asiento> copiarAsientosDeSala(Sala salaFuncion) {
        ArrayList<Asiento> copia = new ArrayList<Asiento>();
        for (int i = 0; i < salaFuncion.getAsientos().size(); i++) {
            Asiento original = salaFuncion.getAsientos().get(i);
            copia.add(new Asiento(original.getFila(), original.getNumero()));
        }
        return copia;
    }

    /**
     * Metodo que permite buscar un asiento de la funcion por su codigo
     * @param codigoAsiento del asiento a buscar
     * @return el asiento encontrado o null si no existe
     */
    public Asiento buscarAsientoPorCodigo(String codigoAsiento) {
        for (int i = 0; i < asientos.size(); i++) {
            if (asientos.get(i).getCodigo().equalsIgnoreCase(codigoAsiento)) {
                return asientos.get(i);
            }
        }
        return null;
    }

    /**
     * Metodo que permite finalizar la funcion
     */
    public void finalizar() {
        estado = EstadoFuncion.FINALIZADA;
    }

    /**
     * Metodo que permite cancelar la funcion
     */
    public void cancelar() {
        estado = EstadoFuncion.CANCELADA;
    }

    /**
     * Metodo que permite saber si la funcion esta programada
     * @return true si la funcion esta programada, false en caso contrario
     */
    public boolean estaProgramada() {
        return estado == EstadoFuncion.PROGRAMADA;
    }

    /**
     * Metodo que permite contar los asientos disponibles de la funcion
     * @return cantidad de asientos disponibles
     */
    public int contarAsientosDisponibles() {
        int total = 0;
        for (int i = 0; i < asientos.size(); i++) {
            if (asientos.get(i).estaDisponible()) {
                total++;
            }
        }
        return total;
    }

    /**
     * Metodo que permite contar los asientos ocupados de la funcion
     * @return cantidad de asientos ocupados
     */
    public int contarAsientosOcupados() {
        return asientos.size() - contarAsientosDisponibles();
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

    public ArrayList<Asiento> getAsientos() {
        return asientos;
    }

    @Override
    public String toString() {
        return codigo + " - " + pelicula.getNombre() + " - Sala " + sala.getNombre()
                + " - " + fecha + " " + hora + " - $" + (long) valorBase + " - " + estado;
    }
}