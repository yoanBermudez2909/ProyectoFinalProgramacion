package co.edu.uniquindio.cinemauq.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AsientoTest {

    @Test
    void creaAsientoDisponiblePorDefecto() {
        Asiento asiento = new Asiento("A", 1);
        assertEquals("A1", asiento.getCodigo());
        assertTrue(asiento.estaDisponible());
        assertEquals(EstadoAsiento.DISPONIBLE, asiento.getEstado());
    }

    @Test
    void ocuparCambiaElEstadoAOcupado() {
        Asiento asiento = new Asiento("B", 5);
        asiento.ocupar();
        assertFalse(asiento.estaDisponible());
        assertEquals(EstadoAsiento.OCUPADO, asiento.getEstado());
    }

    @Test
    void liberarDevuelveElAsientoADisponible() {
        Asiento asiento = new Asiento("C", 3);
        asiento.ocupar();
        asiento.liberar();
        assertTrue(asiento.estaDisponible());
    }

    @Test
    void elCodigoSeConstruyeConFilaYNumero() {
        Asiento asiento = new Asiento("D", 10);
        assertEquals("D10", asiento.getCodigo());
        assertEquals("D", asiento.getFila());
        assertEquals(10, asiento.getNumero());
    }

    @Test
    void ocuparDosVecesMantieneElEstadoOcupado() {
        Asiento asiento = new Asiento("A", 2);
        asiento.ocupar();
        asiento.ocupar();
        assertEquals(EstadoAsiento.OCUPADO, asiento.getEstado());
    }
}
