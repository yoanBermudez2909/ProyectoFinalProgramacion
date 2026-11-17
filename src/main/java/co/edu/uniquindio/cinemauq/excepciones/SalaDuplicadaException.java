package co.edu.uniquindio.cinemauq.excepciones;

/**
 * Se lanza al intentar registrar una sala con un codigo que ya existe.
 */
public class SalaDuplicadaException extends Exception {
    public SalaDuplicadaException(String mensaje) {
        super(mensaje);
    }
}
