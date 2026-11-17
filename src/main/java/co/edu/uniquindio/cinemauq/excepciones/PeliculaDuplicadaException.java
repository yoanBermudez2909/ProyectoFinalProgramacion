package co.edu.uniquindio.cinemauq.excepciones;

/**
 * Se lanza al intentar registrar una pelicula con un codigo que ya existe.
 */
public class PeliculaDuplicadaException extends Exception {
    public PeliculaDuplicadaException(String mensaje) {
        super(mensaje);
    }
}
