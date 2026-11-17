package co.edu.uniquindio.cinemauq.excepciones;

/**
 * Se lanza al intentar vender una entrada para una funcion que ya no tiene asientos disponibles.
 */
public class SinAsientosDisponiblesException extends Exception {
    public SinAsientosDisponiblesException(String mensaje) {
        super(mensaje);
    }
}
