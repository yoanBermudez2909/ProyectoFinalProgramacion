package co.edu.uniquindio.cinemauq.modelo;

import java.util.ArrayList;

/**
 * Sala de proyeccion. Al crearse genera automaticamente su coleccion de
 * asientos (filas A, B, C... de hasta 10 asientos cada una) hasta completar
 * la capacidad indicada.
 */
public class Sala {

    private static final int ASIENTOS_POR_FILA = 10;

    private String codigo;
    private String nombre;
    private int capacidad;
    private EstadoSala estado;
    private ArrayList<Asiento> asientos;

    public Sala(String codigo, String nombre, int capacidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.estado = EstadoSala.DISPONIBLE;
        this.asientos = generarAsientos(capacidad);
    }

    /**
     * Genera los asientos de la sala distribuidos en filas de hasta
     * {@value #ASIENTOS_POR_FILA} asientos, identificadas con letras
     * consecutivas (A1..A10, B1..B10, etc.) hasta alcanzar la capacidad.
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

    public Asiento buscarAsientoPorCodigo(String codigoAsiento) {
        for (int i = 0; i < asientos.size(); i++) {
            if (asientos.get(i).getCodigo().equalsIgnoreCase(codigoAsiento)) {
                return asientos.get(i);
            }
        }
        return null;
    }

    public int contarAsientosDisponibles() {
        int total = 0;
        for (int i = 0; i < asientos.size(); i++) {
            if (asientos.get(i).estaDisponible()) {
                total++;
            }
        }
        return total;
    }

    public int contarAsientosOcupados() {
        return asientos.size() - contarAsientosDisponibles();
    }

    public void deshabilitar() {
        estado = EstadoSala.FUERA_DE_SERVICIO;
    }

    public void habilitar() {
        estado = EstadoSala.DISPONIBLE;
    }

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
