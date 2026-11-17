package co.edu.uniquindio.cinemauq.modelo;

import java.time.LocalDateTime;

public class Venta {

    private String codigo;
    private Cliente cliente;
    private Funcion funcion;
    private Asiento asiento;
    private LocalDateTime fechaHoraVenta;
    private double valorPagado;
    private EstadoVenta estado;

    /**
     * Metodo constructor de la clase Venta
     * @param codigo de la venta
     * @param cliente de la venta
     * @param funcion de la venta
     * @param asiento de la venta
     * @param valorPagado de la venta
     */
    public Venta(String codigo, Cliente cliente, Funcion funcion, Asiento asiento, double valorPagado) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.funcion = funcion;
        this.asiento = asiento;
        this.fechaHoraVenta = LocalDateTime.now();
        this.valorPagado = valorPagado;
        this.estado = EstadoVenta.ACTIVA;
    }

    /**
     * Metodo que permite cancelar la venta
     */
    public void cancelar() {
        estado = EstadoVenta.CANCELADA;
    }

    /**
     * Metodo que permite saber si la venta esta activa
     * @return true si la venta esta activa, false en caso contrario
     */
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