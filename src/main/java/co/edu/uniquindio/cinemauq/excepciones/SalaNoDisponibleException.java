package co.edu.uniquindio.cinemauq.excepciones;

/**
 * Se lanza al intentar programar una funcion en una sala fuera de servicio.
 */
public class SalaNoDisponibleException extends Exception {
    public SalaNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
