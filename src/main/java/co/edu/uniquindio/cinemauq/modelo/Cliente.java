package co.edu.uniquindio.cinemauq.modelo;

/**
 * Cliente que compra entradas. El tipo de cliente determina la tarifa
 * aplicada sobre el valor base de la funcion (ver {@link CinemaUQ}).
 */
public class Cliente {

    private String identificacion;
    private String nombre;
    private TipoCliente tipoCliente;

    public Cliente(String identificacion, String nombre, TipoCliente tipoCliente) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.tipoCliente = tipoCliente;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    @Override
    public String toString() {
        return identificacion + " - " + nombre + " - " + tipoCliente;
    }
}
