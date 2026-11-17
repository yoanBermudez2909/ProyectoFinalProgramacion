package co.edu.uniquindio.cinemauq.modelo;

import java.time.LocalDateTime;

/**
 * Venta de una entrada: asocia un cliente, una funcion y un asiento, y
 * congela el valor pagado en el momento de la venta (independiente de
 * cambios posteriores en las tarifas).
 */
public class Venta {

    private String codigo;
    private Cliente cliente;
    private Funcion funcion;
    private Asiento asiento;
    private LocalDateTime fechaHoraVenta;
    private double valorPagado;
    private EstadoVenta estado;

    public Venta(String codigo, Cliente cliente, Funcion funcion, Asiento asiento, double valorPagado) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.funcion = funcion;
        this.asiento = asiento;
        this.fechaHoraVenta = LocalDateTime.now();
        this.valorPagado = valorPagado;
        this.estado = EstadoVenta.ACTIVA;
    }

    public void cancelar() {
        estado = EstadoVenta.CANCELADA;
    }

    public boolean estaActiva() {
        return estado == EstadoVenta.ACTIVA;
    }

    public String getCodigo() {
        return codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public LocalDateTime getFechaHoraVenta() {
        return fechaHoraVenta;
    }

    public double getValorPagado() {
        return valorPagado;
    }

    public EstadoVenta getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return codigo + " - " + cliente.getNombre() + " - " + funcion.getPelicula().getNombre()
                + " - Asiento " + asiento.getCodigo() + " - $" + (long) valorPagado + " - " + estado;
    }
}
