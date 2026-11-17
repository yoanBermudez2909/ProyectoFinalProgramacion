package co.edu.uniquindio.cinemauq.modelo;

/**
 * Tipo de cliente. Cada tipo tiene asociada una tarifa (factor de descuento)
 * definida en {@link CinemaUQ} y aplicada sobre el valor base de la funcion.
 */
public enum TipoCliente {
    GENERAL,
    ESTUDIANTE,
    NINO,
    ADULTO_MAYOR
}
