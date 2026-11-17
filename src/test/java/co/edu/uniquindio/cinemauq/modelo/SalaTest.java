package co.edu.uniquindio.cinemauq.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SalaTest {

    @Test
    void generaLaCantidadDeAsientosIgualALaCapacidad() {
        Sala sala = new Sala("S1", "Sala 1", 25);
        assertEquals(25, sala.getAsientos().size());
    }

    @Test
    void distribuyeLosAsientosEnFilasDeHastaDiez() {
        Sala sala = new Sala("S2", "Sala 2", 12);
        // fila A: A1..A10, fila B: B1..B2
        assertNotNull(sala.buscarAsientoPorCodigo("A10"));
        assertNotNull(sala.buscarAsientoPorCodigo("B2"));
        assertNull(sala.buscarAsientoPorCodigo("B3"));
    }

    @Test
    void contarAsientosDisponiblesDisminuyeAlOcuparUno() {
        Sala sala = new Sala("S3", "Sala 3", 10);
        assertEquals(10, sala.contarAsientosDisponibles());
        sala.buscarAsientoPorCodigo("A1").ocupar();
        assertEquals(9, sala.contarAsientosDisponibles());
        assertEquals(1, sala.contarAsientosOcupados());
    }

    @Test
    void deshabilitarCambiaElEstadoAFueraDeServicio() {
        Sala sala = new Sala("S4", "Sala 4", 10);
        sala.deshabilitar();
        assertFalse(sala.estaDisponible());
        assertEquals(EstadoSala.FUERA_DE_SERVICIO, sala.getEstado());
    }

    @Test
    void buscarAsientoPorCodigoInexistenteDevuelveNull() {
        Sala sala = new Sala("S5", "Sala 5", 10);
        assertNull(sala.buscarAsientoPorCodigo("Z99"));
    }
}
