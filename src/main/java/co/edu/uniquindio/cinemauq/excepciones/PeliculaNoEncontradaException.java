package co.edu.uniquindio.cinemauq.excepciones;

/**
 * Se lanza cuando se busca o se referencia una pelicula cuyo codigo no existe.
 */
public class PeliculaNoEncontradaException extends Exception {
    public PeliculaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
