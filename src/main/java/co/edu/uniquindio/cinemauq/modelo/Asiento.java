package co.edu.uniquindio.cinemauq.modelo;

public class Asiento {

    private String codigo;
    private String fila;
    private int numero;
    private EstadoAsiento estado;

    /**
     * Metodo constructor de la clase Asiento
     * @param fila del asiento
     * @param numero del asiento dentro de la fila
     */
    public Asiento(String fila, int numero) {
        this.fila = fila;
        this.numero = numero;
        this.codigo = fila + numero;
        this.estado = EstadoAsiento.DISPONIBLE;
    }

    /**
     * Metodo que permite marcar el asiento como ocupado
     */
    public void ocupar() {
        estado = EstadoAsiento.OCUPADO;
    }

    /**
     * Metodo que permite liberar el asiento dejandolo disponible
     */
    public void liberar() {
        estado = EstadoAsiento.DISPONIBLE;
    }

    /**
     * Metodo que permite saber si el asiento esta disponible
     * @return true si el asiento esta disponible, false en caso contrario
     */
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