package co.edu.uniquindio.cinemauq.controller;

import co.edu.uniquindio.cinemauq.excepciones.AsientoOcupadoException;
import co.edu.uniquindio.cinemauq.excepciones.FuncionNoEncontradaException;
import co.edu.uniquindio.cinemauq.excepciones.SinAsientosDisponiblesException;
import co.edu.uniquindio.cinemauq.excepciones.VentaDuplicadaException;
import co.edu.uniquindio.cinemauq.excepciones.VentaNoEncontradaException;
import co.edu.uniquindio.cinemauq.excepciones.VentaYaCanceladaException;
import co.edu.uniquindio.cinemauq.modelo.CinemaUQ;
import co.edu.uniquindio.cinemauq.modelo.Cliente;
import co.edu.uniquindio.cinemauq.modelo.Funcion;
import co.edu.uniquindio.cinemauq.modelo.TipoCliente;
import co.edu.uniquindio.cinemauq.modelo.Venta;
import co.edu.uniquindio.cinemauq.excepciones.FuncionNoProgramadaException;

import java.util.ArrayList;

public class VentaController {

    private CinemaUQ cinema;

    public VentaController(CinemaUQ cinema) {
        this.cinema = cinema;
    }

    public Venta venderEntrada(String codigoVenta, String codigoFuncion, Cliente cliente, String codigoAsiento)
            throws FuncionNoEncontradaException, FuncionNoProgramadaException, SinAsientosDisponiblesException,
            AsientoOcupadoException, VentaDuplicadaException {
        return cinema.venderEntrada(codigoVenta, codigoFuncion, cliente, codigoAsiento);
    }

    public void cancelarVenta(String codigoVenta) throws VentaNoEncontradaException, VentaYaCanceladaException {
        cinema.cancelarVenta(codigoVenta);
    }

    public double calcularValorEntrada(Funcion funcion, TipoCliente tipoCliente) {
        return cinema.calcularValorEntrada(funcion, tipoCliente);
    }

    public ArrayList<Venta> obtenerListaVentas() {
        return cinema.listarVentas();
    }

    public ArrayList<Funcion> obtenerListaFunciones() {
        ArrayList<Funcion> programadas = new ArrayList<Funcion>();
        for (Funcion funcion : cinema.listarFunciones()) {
            if (funcion.estaProgramada()) {
                programadas.add(funcion);
            }
        }
        return programadas;
    }
}