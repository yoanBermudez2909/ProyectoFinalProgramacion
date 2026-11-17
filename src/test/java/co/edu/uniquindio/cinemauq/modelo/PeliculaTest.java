package co.edu.uniquindio.cinemauq.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PeliculaTest {

    @Test
    void creaPeliculaDisponiblePorDefecto() {
        Pelicula pelicula = new Pelicula("P1", "Avatar", "Ciencia ficcion", 162, Clasificacion.MAYORES_12);
        assertTrue(pelicula.estaDisponible());
        assertEquals(EstadoPelicula.DISPONIBLE, pelicula.getEstado());
    }

    @Test
    void deshabilitarCambiaElEstadoANoDisponible() {
        Pelicula pelicula = new Pelicula("P2", "Shrek", "Animacion", 90, Clasificacion.TODOS);
        pelicula.deshabilitar();
        assertFalse(pelicula.estaDisponible());
    }

    @Test
    void habilitarDevuelveLaPeliculaADisponible() {
        Pelicula pelicula = new Pelicula("P3", "It", "Terror", 135, Clasificacion.MAYORES_15);
        pelicula.deshabilitar();
        pelicula.habilitar();
        assertTrue(pelicula.estaDisponible());
    }

    @Test
    void losSettersActualizanLosAtributos() {
        Pelicula pelicula = new Pelicula("P4", "Nombre", "Drama", 100, Clasificacion.TODOS);
        pelicula.setNombre("Nombre nuevo");
        pelicula.setGenero("Comedia");
        pelicula.setDuracionMinutos(110);
        pelicula.setClasificacion(Clasificacion.MAYORES_7);
        assertEquals("Nombre nuevo", pelicula.getNombre());
        assertEquals("Comedia", pelicula.getGenero());
        assertEquals(110, pelicula.getDuracionMinutos());
        assertEquals(Clasificacion.MAYORES_7, pelicula.getClasificacion());
    }

    @Test
    void elCodigoNoCambiaDespuesDeCreada() {
        Pelicula pelicula = new Pelicula("P5", "Nombre", "Drama", 100, Clasificacion.TODOS);
        assertEquals("P5", pelicula.getCodigo());
    }
}
