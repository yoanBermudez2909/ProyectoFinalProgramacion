package co.edu.uniquindio.cinemauq.controller;

import co.edu.uniquindio.cinemauq.excepciones.FuncionNoEncontradaException;
import co.edu.uniquindio.cinemauq.modelo.CinemaUQ;
import co.edu.uniquindio.cinemauq.modelo.Funcion;
import co.edu.uniquindio.cinemauq.modelo.Pelicula;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReporteController {

    private CinemaUQ cinema;

    public ReporteController(CinemaUQ cinema) {
        this.cinema = cinema;
    }

    public int totalEntradasVendidas() {
        return cinema.totalEntradasVendidas();
    }

    public double totalIngresos() {
        return cinema.totalIngresos();
    }

    public Pelicula peliculaConMasVentas() {
        return cinema.peliculaConMasVentas();
    }

    public double porcentajeOcupacion(String codigoFuncion) throws FuncionNoEncontradaException {
        return cinema.porcentajeOcupacion(codigoFuncion);
    }

    public ArrayList<Funcion> consultarFuncionesProgramadasPorFecha(LocalDate fecha) {
        return cinema.consultarFuncionesProgramadasPorFecha(fecha);
    }

    public ArrayList<Funcion> obtenerListaFunciones() {
        return cinema.listarFunciones();
    }
}