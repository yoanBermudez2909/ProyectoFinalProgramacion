package co.edu.uniquindio.cinemauq.controller;

import co.edu.uniquindio.cinemauq.excepciones.SalaDuplicadaException;
import co.edu.uniquindio.cinemauq.excepciones.SalaNoEncontradaException;
import co.edu.uniquindio.cinemauq.modelo.CinemaUQ;
import co.edu.uniquindio.cinemauq.modelo.Sala;

import java.util.ArrayList;

public class SalaController {

    private CinemaUQ cinema;

    public SalaController(CinemaUQ cinema) {
        this.cinema = cinema;
    }

    public void habilitarSala(String codigo) throws SalaNoEncontradaException {
        cinema.habilitarSala(codigo);
    }

    public void registrarSala(Sala sala) throws SalaDuplicadaException {
        cinema.registrarSala(sala);
    }

    public void modificarSala(String codigo, String nuevoNombre) throws SalaNoEncontradaException {
        cinema.modificarSala(codigo, nuevoNombre);
    }

    public void deshabilitarSala(String codigo) throws SalaNoEncontradaException {
        cinema.deshabilitarSala(codigo);
    }

    public ArrayList<Sala> obtenerListaSalas() {
        return cinema.listarSalas();
    }
}