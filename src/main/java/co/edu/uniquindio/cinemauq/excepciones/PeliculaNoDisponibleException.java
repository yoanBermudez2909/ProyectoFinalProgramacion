package co.edu.uniquindio.cinemauq.excepciones;

public class PeliculaNoDisponibleException extends Exception {

    public PeliculaNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}