package co.edu.uniquindio.cinemauq.modelo;

import co.edu.uniquindio.cinemauq.excepciones.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class CinemaUQTest {

    private CinemaUQ cinema;
    private Pelicula pelicula;
    private Sala sala;
    private Funcion funcion;

    @BeforeEach
    void preparar() throws Exception {
        cinema = new CinemaUQ("CinemaUQ");
        pelicula = new Pelicula("P1", "Avatar", "Ciencia ficcion", 162, Clasificacion.MAYORES_12);
        cinema.registrarPelicula(pelicula);
        sala = new Sala("S1", "Sala 1", 10);
        cinema.registrarSala(sala);
        funcion = cinema.programarFuncion("F1", "P1", "S1",
                LocalDate.of(2026, 11, 20), LocalTime.of(19, 0), 15000.0);
    }

    @Test
    void registrarPeliculaDuplicadaLanzaExcepcion() {
        Pelicula duplicada = new Pelicula("P1", "Otra", "Drama", 100, Clasificacion.TODOS);
        assertThrows(PeliculaDuplicadaException.class, () -> cinema.registrarPelicula(duplicada));
    }

    @Test
    void registrarSalaDuplicadaLanzaExcepcion() {
        Sala duplicada = new Sala("S1", "Otra sala", 20);
        assertThrows(SalaDuplicadaException.class, () -> cinema.registrarSala(duplicada));
    }

    @Test
    void programarFuncionEnSalaFueraDeServicioLanzaExcepcion() throws Exception {
        cinema.deshabilitarSala("S1");
        assertThrows(SalaNoDisponibleException.class,
                () -> cinema.programarFuncion("F2", "P1", "S1", LocalDate.of(2026, 11, 21), LocalTime.of(20, 0), 15000.0));
    }

    @Test
    void venderEntradaCalculaElValorSegunElTipoDeCliente() throws Exception {
        Cliente general = new Cliente("1000", "Juan", TipoCliente.GENERAL);
        Cliente estudiante = new Cliente("1001", "Maria", TipoCliente.ESTUDIANTE);

        Venta ventaGeneral = cinema.venderEntrada("V1", "F1", general, "A1");
        Venta ventaEstudiante = cinema.venderEntrada("V2", "F1", estudiante, "A2");

        assertEquals(15000.0, ventaGeneral.getValorPagado(), 0.001);
        assertEquals(12000.0, ventaEstudiante.getValorPagado(), 0.001);
    }

    @Test
    void venderAsientoYaOcupadoLanzaExcepcion() throws Exception {
        Cliente cliente = new Cliente("1000", "Juan", TipoCliente.GENERAL);
        cinema.venderEntrada("V1", "F1", cliente, "A1");
        assertThrows(AsientoOcupadoException.class, () -> cinema.venderEntrada("V2", "F1", cliente, "A1"));
    }

    @Test
    void venderEntradaParaFuncionInexistenteLanzaExcepcion() {
        Cliente cliente = new Cliente("1000", "Juan", TipoCliente.GENERAL);
        assertThrows(FuncionNoEncontradaException.class, () -> cinema.venderEntrada("V1", "F999", cliente, "A1"));
    }

    @Test
    void venderEntradaSinAsientosDisponiblesLanzaExcepcion() throws Exception {
        Sala salaChica = new Sala("S2", "Sala pequena", 1);
        cinema.registrarSala(salaChica);
        Funcion funcionChica = cinema.programarFuncion("F2", "P1", "S2",
                LocalDate.of(2026, 11, 21), LocalTime.of(21, 0), 10000.0);
        Cliente cliente = new Cliente("1000", "Juan", TipoCliente.GENERAL);
        cinema.venderEntrada("V1", "F2", cliente, "A1");
        assertThrows(SinAsientosDisponiblesException.class, () -> cinema.venderEntrada("V2", "F2", cliente, "A1"));
    }

    @Test
    void cancelarVentaLiberaElAsientoYCambiaElEstado() throws Exception {
        Cliente cliente = new Cliente("1000", "Juan", TipoCliente.GENERAL);
        cinema.venderEntrada("V1", "F1", cliente, "A1");
        cinema.cancelarVenta("V1");
        assertFalse(cinema.buscarVentaPorCodigo("V1").estaActiva());
        assertTrue(sala.buscarAsientoPorCodigo("A1").estaDisponible());
    }

    @Test
    void cancelarVentaInexistenteLanzaExcepcion() {
        assertThrows(VentaNoEncontradaException.class, () -> cinema.cancelarVenta("V999"));
    }

    @Test
    void totalEntradasVendidasEIngresosIgnoranLasVentasCanceladas() throws Exception {
        Cliente cliente = new Cliente("1000", "Juan", TipoCliente.GENERAL);
        cinema.venderEntrada("V1", "F1", cliente, "A1");
        cinema.venderEntrada("V2", "F1", cliente, "A2");
        cinema.cancelarVenta("V2");

        assertEquals(1, cinema.totalEntradasVendidas());
        assertEquals(15000.0, cinema.totalIngresos(), 0.001);
    }

    @Test
    void porcentajeOcupacionSeCalculaCorrectamente() throws Exception {
        Cliente cliente = new Cliente("1000", "Juan", TipoCliente.GENERAL);
        cinema.venderEntrada("V1", "F1", cliente, "A1");
        // 1 de 10 asientos ocupados -> 10%
        assertEquals(10.0, cinema.porcentajeOcupacion("F1"), 0.001);
    }
}
