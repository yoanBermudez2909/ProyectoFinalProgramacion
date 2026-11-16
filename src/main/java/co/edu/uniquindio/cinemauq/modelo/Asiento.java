package co.edu.uniquindio.cinemauq.modelo;

/**
 * Asiento individual dentro de una sala. Cada asiento conoce su fila, su
 * numero dentro de la fila y su estado de ocupacion.
 */
public class Asiento {

    private String codigo;
    private String fila;
    private int numero;
    private EstadoAsiento estado;

    public Asiento(String fila, int numero) {
        this.fila = fila;
        this.numero = numero;
        this.codigo = fila + numero;
        this.estado = EstadoAsiento.DISPONIBLE;
    }

    /** Marca el asiento como ocupado (se usa al concretar una venta). */
    public void ocupar() {
        estado = EstadoAsiento.OCUPADO;
    }

    /** Libera el asiento (se usa al cancelar una venta). */
    public void liberar() {
        estado = EstadoAsiento.DISPONIBLE;
    }

    public boolean estaDisponible() {
        return estado == EstadoAsiento.DISPONIBLE;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getFila() {
        return fila;
    }

    public int getNumero() {
        return numero;
    }

    public EstadoAsiento getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return codigo + " - " + estado;
    }
}
