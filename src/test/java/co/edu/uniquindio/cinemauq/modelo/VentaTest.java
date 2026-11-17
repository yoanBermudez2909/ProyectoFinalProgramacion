package co.edu.uniquindio.cinemauq.modelo;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class VentaTest {

    private Venta crearVenta() {
        Pelicula pelicula = new Pelicula("P1", "Avatar", "Ciencia ficcion", 162, Clasificacion.MAYORES_12);
        Sala sala = new Sala("S1", "Sala 1", 10);
        Funcion funcion = new Funcion("F1", pelicula, sala, LocalDate.of(2026, 11, 20), LocalTime.of(19, 0), 15000.0);
        Cliente cliente = new Cliente("1000", "Juan Perez", TipoCliente.GENERAL);
        Asiento asiento = sala.buscarAsientoPorCodigo("A1");
        asiento.ocupar();
        return new Venta("V1", cliente, funcion, asiento, 15000.0);
    }

    @Test
    void creaVentaActivaPorDefecto() {
        Venta venta = crearVenta();
        assertTrue(venta.estaActiva());
        assertEquals(EstadoVenta.ACTIVA, venta.getEstado());
    }

    @Test
    void cancelarCambiaElEstadoACancelada() {
        Venta venta = crearVenta();
        venta.cancelar();
        assertFalse(venta.estaActiva());
        assertEquals(EstadoVenta.CANCELADA, venta.getEstado());
    }

    @Test
    void elValorPagadoQuedaCongeladoEnLaVenta() {
        Venta venta = crearVenta();
        assertEquals(15000.0, venta.getValorPagado(), 0.001);
    }

    @Test
    void laVentaConservaReferenciaAlAsientoVendido() {
        Venta venta = crearVenta();
        assertEquals("A1", venta.getAsiento().getCodigo());
    }

    @Test
    void laVentaConservaReferenciaAlClienteYALaFuncion() {
        Venta venta = crearVenta();
        assertEquals("1000", venta.getCliente().getIdentificacion());
        assertEquals("F1", venta.getFuncion().getCodigo());
    }
}
