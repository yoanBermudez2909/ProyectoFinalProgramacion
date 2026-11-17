package co.edu.uniquindio.cinemauq.modelo;

public class Cliente {

    private String identificacion;
    private String nombre;
    private TipoCliente tipoCliente;

    /**
     * Metodo constructor de la clase Cliente
     * @param identificacion del cliente
     * @param nombre del cliente
     * @param tipoCliente del cliente
     */
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