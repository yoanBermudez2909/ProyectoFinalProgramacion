package co.edu.uniquindio.cinemauq.controller;

import co.edu.uniquindio.cinemauq.excepciones.PeliculaDuplicadaException;
import co.edu.uniquindio.cinemauq.excepciones.PeliculaNoEncontradaException;
import co.edu.uniquindio.cinemauq.modelo.CinemaUQ;
import co.edu.uniquindio.cinemauq.modelo.Clasificacion;
import co.edu.uniquindio.cinemauq.modelo.Pelicula;

import java.util.ArrayList;

public class PeliculaController {

    private CinemaUQ cinema;

    public PeliculaController(CinemaUQ cinema) {
        this.cinema = cinema;
    }

    public void habilitarPelicula(String codigo) throws PeliculaNoEncontradaException {
        cinema.habilitarPelicula(codigo);
    }

    public void registrarPelicula(Pelicula pelicula) throws PeliculaDuplicadaException {
        cinema.registrarPelicula(pelicula);
    }

    public void modificarPelicula(String codigo, String nombre, String genero, int duracionMinutos,
                                  Clasificacion clasificacion) throws PeliculaNoEncontradaException {
        cinema.modificarPelicula(codigo, nombre, genero, duracionMinutos, clasificacion);
    }

    public void deshabilitarPelicula(String codigo) throws PeliculaNoEncontradaException {
        cinema.deshabilitarPelicula(codigo);
    }

    public ArrayList<Pelicula> obtenerListaPeliculas() {
        return cinema.listarPeliculas();
    }
}