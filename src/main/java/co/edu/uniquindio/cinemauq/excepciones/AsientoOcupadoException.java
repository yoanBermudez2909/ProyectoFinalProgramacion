package co.edu.uniquindio.cinemauq.excepciones;

/**
 * Se lanza al intentar vender un asiento que ya esta ocupado o que no existe en la sala.
 */
public class AsientoOcupadoException extends Exception {
    public AsientoOcupadoException(String mensaje) {
        super(mensaje);
    }
}
