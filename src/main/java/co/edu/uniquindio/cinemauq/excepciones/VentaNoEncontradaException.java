package co.edu.uniquindio.cinemauq.excepciones;

/**
 * Se lanza al intentar cancelar o consultar una venta cuyo codigo no existe.
 */
public class VentaNoEncontradaException extends Exception {
    public VentaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
