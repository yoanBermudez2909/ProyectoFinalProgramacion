package co.edu.uniquindio.cinemauq.excepciones;

/**
 * Se lanza cuando se busca o se referencia una funcion cuyo codigo no existe.
 */
public class FuncionNoEncontradaException extends Exception {
    public FuncionNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
