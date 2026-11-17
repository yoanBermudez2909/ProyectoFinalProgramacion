package co.edu.uniquindio.cinemauq.controller;

import co.edu.uniquindio.cinemauq.excepciones.TarifaInvalidaException;
import co.edu.uniquindio.cinemauq.modelo.CinemaUQ;
import co.edu.uniquindio.cinemauq.modelo.TipoCliente;

public class TarifaController {

    private CinemaUQ cinema;

    public TarifaController(CinemaUQ cinema) {
        this.cinema = cinema;
    }

    public double obtenerTarifa(TipoCliente tipoCliente) {
        return cinema.obtenerTarifa(tipoCliente);
    }

    public void modificarTarifa(TipoCliente tipoCliente, double factor) throws TarifaInvalidaException {
        cinema.modificarTarifa(tipoCliente, factor);
    }
}