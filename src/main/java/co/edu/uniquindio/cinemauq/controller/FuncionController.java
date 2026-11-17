package co.edu.uniquindio.cinemauq.controller;

import co.edu.uniquindio.cinemauq.excepciones.FuncionDuplicadaException;
import co.edu.uniquindio.cinemauq.excepciones.PeliculaNoDisponibleException;
import co.edu.uniquindio.cinemauq.excepciones.PeliculaNoEncontradaException;
import co.edu.uniquindio.cinemauq.excepciones.SalaNoDisponibleException;
import co.edu.uniquindio.cinemauq.excepciones.SalaNoEncontradaException;
import co.edu.uniquindio.cinemauq.modelo.CinemaUQ;
import co.edu.uniquindio.cinemauq.modelo.Funcion;
import co.edu.uniquindio.cinemauq.modelo.Pelicula;
import co.edu.uniquindio.cinemauq.modelo.Sala;
import co.edu.uniquindio.cinemauq.excepciones.SalaOcupadaException;
import co.edu.uniquindio.cinemauq.excepciones.FuncionNoEncontradaException;
import co.edu.uniquindio.cinemauq.excepciones.FuncionNoProgramadaException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class FuncionController {

    private CinemaUQ cinema;

    public FuncionController(CinemaUQ cinema) {
        this.cinema = cinema;
    }

    public Funcion programarFuncion(String codigo, String codigoPelicula, String codigoSala,
                                    LocalDate fecha, LocalTime hora, double valorBase)
            throws PeliculaNoEncontradaException, SalaNoEncontradaException, SalaNoDisponibleException,
            PeliculaNoDisponibleException, FuncionDuplicadaException, SalaOcupadaException {
        return cinema.programarFuncion(codigo, codigoPelicula, codigoSala, fecha, hora, valorBase);
    }

    public int cancelarFuncion(String codigo) throws FuncionNoEncontradaException, FuncionNoProgramadaException {
        return cinema.cancelarFuncion(codigo);
    }

    public void finalizarFuncion(String codigo) throws FuncionNoEncontradaException, FuncionNoProgramadaException {
        cinema.finalizarFuncion(codigo);
    }

    public int contarVentasActivas(String codigoFuncion) {
        return cinema.contarVentasActivasPorFuncion(codigoFuncion);
    }

    public ArrayList<Funcion> obtenerListaFunciones() {
        return cinema.listarFunciones();
    }

    public ArrayList<Funcion> consultarFuncionesProgramadasPorFecha(LocalDate fecha) {
        return cinema.consultarFuncionesProgramadasPorFecha(fecha);
    }

    public ArrayList<Pelicula> obtenerPeliculasDisponibles() {
        ArrayList<Pelicula> disponibles = new ArrayList<Pelicula>();
        for (Pelicula pelicula : cinema.listarPeliculas()) {
            if (pelicula.estaDisponible()) {
                disponibles.add(pelicula);
            }
        }
        return disponibles;
    }

    public ArrayList<Sala> obtenerListaSalas() {
        return cinema.listarSalas();
    }
}