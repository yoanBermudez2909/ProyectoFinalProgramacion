package co.edu.uniquindio.cinemauq.modelo;

import java.util.ArrayList;

public class Sala {

    private static final int ASIENTOS_POR_FILA = 10;

    private String codigo;
    private String nombre;
    private int capacidad;
    private EstadoSala estado;
    private ArrayList<Asiento> asientos;

    /**
     * Metodo constructor de la clase Sala
     * @param codigo de la sala
     * @param nombre de la sala
     * @param capacidad de la sala
     */
    public Sala(String codigo, String nombre, int capacidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.estado = EstadoSala.DISPONIBLE;
        this.asientos = generarAsientos(capacidad);
    }

    /**
     * Metodo que permite generar los asientos de la sala en filas de hasta 10 asientos
     * @param capacidadSala cantidad de asientos que se deben generar
     * @return lista con los asientos generados
     */
    private ArrayList<Asiento> generarAsientos(int capacidadSala) {
        ArrayList<Asiento> lista = new ArrayList<Asiento>();
        int creados = 0;
        int filaIndice = 0;
        while (creados < capacidadSala) {
            char letraFila = (char) ('A' + filaIndice);
            for (int numero = 1; numero <= ASIENTOS_POR_FILA && creados < capacidadSala; numero++) {
                lista.add(new Asiento(String.valueOf(letraFila), numero));
                creados++;
            }
            filaIndice++;
        }
        return lista;
    }

    /**
     * Metodo que permite buscar un asiento de la sala por su codigo
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
     * Metodo que permite contar los asientos disponibles de la sala
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
     * Metodo que permite contar los asientos ocupados de la sala
     * @return cantidad de asientos ocupados
     */
    public int contarAsientosOcupados() {
        return asientos.size() - contarAsientosDisponibles();
    }

    /**
     * Metodo que permite deshabilitar la sala dejandola fuera de servicio
     */
    public void deshabilitar() {
        estado = EstadoSala.FUERA_DE_SERVICIO;
    }

    /**
     * Metodo que permite habilitar la sala
     */
    public void habilitar() {
        estado = EstadoSala.DISPONIBLE;
    }

    /**
     * Metodo que permite saber si la sala esta disponible
     * @return true si la sala esta disponible, false en caso contrario
     */
    public boolean estaDisponible() {
        return estado == EstadoSala.DISPONIBLE;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public EstadoSala getEstado() {
        return estado;
    }

    public ArrayList<Asiento> getAsientos() {
        return asientos;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " (capacidad " + capacidad + ") - " + estado;
    }
}