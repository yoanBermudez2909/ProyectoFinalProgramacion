package co.edu.uniquindio.cinemauq.modelo;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class FuncionTest {

    private Funcion crearFuncion() {
        Pelicula pelicula = new Pelicula("P1", "Avatar", "Ciencia ficcion", 162, Clasificacion.MAYORES_12);
        Sala sala = new Sala("S1", "Sala 1", 10);
        return new Funcion("F1", pelicula, sala, LocalDate.of(2026, 11, 20), LocalTime.of(19, 0), 15000.0);
    }

    @Test
    void creaFuncionProgramadaPorDefecto() {
        Funcion funcion = crearFuncion();
        assertTrue(funcion.estaProgramada());
        assertEquals(EstadoFuncion.PROGRAMADA, funcion.getEstado());
    }

    @Test
    void cancelarCambiaElEstadoACancelada() {
        Funcion funcion = crearFuncion();
        funcion.cancelar();
        assertFalse(funcion.estaProgramada());
        assertEquals(EstadoFuncion.CANCELADA, funcion.getEstado());
    }

    @Test
    void finalizarCambiaElEstadoAFinalizada() {
        Funcion funcion = crearFuncion();
        funcion.finalizar();
        assertEquals(EstadoFuncion.FINALIZADA, funcion.getEstado());
    }

    @Test
    void contarAsientosDisponiblesReflejaLosDeLaSala() {
        Funcion funcion = crearFuncion();
        assertEquals(10, funcion.contarAsientosDisponibles());
        funcion.getSala().buscarAsientoPorCodigo("A1").ocupar();
        assertEquals(9, funcion.contarAsientosDisponibles());
        assertEquals(1, funcion.contarAsientosOcupados());
    }

    @Test
    void elValorBaseSeAlmacenaCorrectamente() {
        Funcion funcion = crearFuncion();
        assertEquals(15000.0, funcion.getValorBase(), 0.001);
    }
}
