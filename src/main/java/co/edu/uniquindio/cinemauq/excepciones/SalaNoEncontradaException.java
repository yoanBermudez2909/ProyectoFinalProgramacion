package co.edu.uniquindio.cinemauq.excepciones;

/**
 * Se lanza cuando se busca o se referencia una sala cuyo codigo no existe.
 */
public class SalaNoEncontradaException extends Exception {
    public SalaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
