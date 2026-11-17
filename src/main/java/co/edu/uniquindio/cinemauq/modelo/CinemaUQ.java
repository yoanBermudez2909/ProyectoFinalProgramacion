package co.edu.uniquindio.cinemauq.modelo;

import co.edu.uniquindio.cinemauq.excepciones.AsientoOcupadoException;
import co.edu.uniquindio.cinemauq.excepciones.FuncionDuplicadaException;
import co.edu.uniquindio.cinemauq.excepciones.FuncionNoEncontradaException;
import co.edu.uniquindio.cinemauq.excepciones.PeliculaDuplicadaException;
import co.edu.uniquindio.cinemauq.excepciones.PeliculaNoDisponibleException;
import co.edu.uniquindio.cinemauq.excepciones.PeliculaNoEncontradaException;
import co.edu.uniquindio.cinemauq.excepciones.SalaDuplicadaException;
import co.edu.uniquindio.cinemauq.excepciones.SalaNoDisponibleException;
import co.edu.uniquindio.cinemauq.excepciones.SalaNoEncontradaException;
import co.edu.uniquindio.cinemauq.excepciones.SinAsientosDisponiblesException;
import co.edu.uniquindio.cinemauq.excepciones.VentaDuplicadaException;
import co.edu.uniquindio.cinemauq.excepciones.VentaNoEncontradaException;
import co.edu.uniquindio.cinemauq.excepciones.VentaYaCanceladaException;
import co.edu.uniquindio.cinemauq.excepciones.TarifaInvalidaException;
import co.edu.uniquindio.cinemauq.excepciones.SalaOcupadaException;
import co.edu.uniquindio.cinemauq.excepciones.FuncionNoProgramadaException;
import java.time.LocalDateTime;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase raiz del sistema CinemaUQ. Centraliza el catalogo de peliculas,
 * salas, funciones y ventas, y expone las funcionalidades de operador y
 * de administrador exigidas por el proyecto, junto con los reportes.
 *
 * <p>Tarifas: cada tipo de cliente tiene asociado un factor de descuento
 * que se aplica sobre el valor base de la funcion para calcular el valor
 * final de la entrada (decision del grupo, documentada en el analisis de
 * pensamiento computacional):</p>
 * <ul>
 *   <li>GENERAL: 100% del valor base</li>
 *   <li>ESTUDIANTE: 80% del valor base</li>
 *   <li>NINO: 65% del valor base</li>
 *   <li>ADULTO_MAYOR: 75% del valor base</li>
 * </ul>
 */
public class CinemaUQ {

    private String nombre;
    private ArrayList<Pelicula> peliculas;
    private ArrayList<Sala> salas;
    private ArrayList<Funcion> funciones;
    private ArrayList<Venta> ventas;
    private HashMap<TipoCliente, Double> tarifas;

    public CinemaUQ(String nombre) {
        this.nombre = nombre;
        this.peliculas = new ArrayList<Pelicula>();
        this.salas = new ArrayList<Sala>();
        this.funciones = new ArrayList<Funcion>();
        this.ventas = new ArrayList<Venta>();
        this.tarifas = new HashMap<TipoCliente, Double>();
        tarifas.put(TipoCliente.GENERAL, 1.00);
        tarifas.put(TipoCliente.ESTUDIANTE, 0.80);
        tarifas.put(TipoCliente.NINO, 0.65);
        tarifas.put(TipoCliente.ADULTO_MAYOR, 0.75);
    }

    // ================== Gestion de peliculas (administrador) ==================

    public void registrarPelicula(Pelicula pelicula) throws PeliculaDuplicadaException {
        if (buscarPeliculaPorCodigo(pelicula.getCodigo()) != null) {
            throw new PeliculaDuplicadaException("Ya existe una pelicula con el codigo " + pelicula.getCodigo());
        }
        peliculas.add(pelicula);
    }

    public Pelicula buscarPeliculaPorCodigo(String codigo) {
        for (int i = 0; i < peliculas.size(); i++) {
            if (peliculas.get(i).getCodigo().equals(codigo)) {
                return peliculas.get(i);
            }
        }
        return null;
    }

    public ArrayList<Pelicula> listarPeliculas() {
        return peliculas;
    }

    public void modificarPelicula(String codigo, String nombre, String genero, int duracionMinutos,
                                  Clasificacion clasificacion) throws PeliculaNoEncontradaException {
        Pelicula pelicula = buscarPeliculaPorCodigo(codigo);
        if (pelicula == null) {
            throw new PeliculaNoEncontradaException("No existe una pelicula con el codigo " + codigo);
        }
        pelicula.setNombre(nombre);
        pelicula.setGenero(genero);
        pelicula.setDuracionMinutos(duracionMinutos);
        pelicula.setClasificacion(clasificacion);
    }

    public void deshabilitarPelicula(String codigo) throws PeliculaNoEncontradaException {
        Pelicula pelicula = buscarPeliculaPorCodigo(codigo);
        if (pelicula == null) {
            throw new PeliculaNoEncontradaException("No existe una pelicula con el codigo " + codigo);
        }
        pelicula.deshabilitar();
    }

    // ================== Gestion de salas (administrador) ==================

    public void registrarSala(Sala sala) throws SalaDuplicadaException {
        if (buscarSalaPorCodigo(sala.getCodigo()) != null) {
            throw new SalaDuplicadaException("Ya existe una sala con el codigo " + sala.getCodigo());
        }
        salas.add(sala);
    }

    public Sala buscarSalaPorCodigo(String codigo) {
        for (int i = 0; i < salas.size(); i++) {
            if (salas.get(i).getCodigo().equals(codigo)) {
                return salas.get(i);
            }
        }
        return null;
    }

    public ArrayList<Sala> listarSalas() {
        return salas;
    }

    public void modificarSala(String codigo, String nuevoNombre) throws SalaNoEncontradaException {
        Sala sala = buscarSalaPorCodigo(codigo);
        if (sala == null) {
            throw new SalaNoEncontradaException("No existe una sala con el codigo " + codigo);
        }
        sala.setNombre(nuevoNombre);
    }

    public void deshabilitarSala(String codigo) throws SalaNoEncontradaException {
        Sala sala = buscarSalaPorCodigo(codigo);
        if (sala == null) {
            throw new SalaNoEncontradaException("No existe una sala con el codigo " + codigo);
        }
        sala.deshabilitar();
    }

    // ================== Gestion de funciones (administrador) ==================

    public Funcion programarFuncion(String codigo, String codigoPelicula, String codigoSala,
                                    LocalDate fecha, LocalTime hora, double valorBase)
            throws PeliculaNoEncontradaException, SalaNoEncontradaException, SalaNoDisponibleException,
            PeliculaNoDisponibleException, FuncionDuplicadaException, SalaOcupadaException {
        Pelicula pelicula = buscarPeliculaPorCodigo(codigoPelicula);
        if (pelicula == null) {
            throw new PeliculaNoEncontradaException("No existe una pelicula con el codigo " + codigoPelicula);
        }
        Sala sala = buscarSalaPorCodigo(codigoSala);
        if (sala == null) {
            throw new SalaNoEncontradaException("No existe una sala con el codigo " + codigoSala);
        }
        if (!sala.estaDisponible()) {
            throw new SalaNoDisponibleException("La sala " + codigoSala + " se encuentra fuera de servicio.");
        }
        if (!pelicula.estaDisponible()) {
            throw new PeliculaNoDisponibleException("La pelicula " + codigoPelicula + " no esta disponible.");
        }
        if (buscarFuncionPorCodigo(codigo) != null) {
            throw new FuncionDuplicadaException("Ya existe una funcion con el codigo " + codigo);
        }
        LocalDateTime inicio = LocalDateTime.of(fecha, hora);
        LocalDateTime fin = inicio.plusMinutes(pelicula.getDuracionMinutos());
        Funcion enConflicto = buscarFuncionEnConflicto(codigoSala, inicio, fin);
        if (enConflicto != null) {
            throw new SalaOcupadaException("La sala " + codigoSala + " ya tiene la funcion " + enConflicto.getCodigo()
                    + " programada el " + enConflicto.getFecha() + " de " + enConflicto.getHora() + " a "
                    + enConflicto.getHora().plusMinutes(enConflicto.getPelicula().getDuracionMinutos()) + ".");
        }
        Funcion funcion = new Funcion(codigo, pelicula, sala, fecha, hora, valorBase);
        funciones.add(funcion);
        return funcion;
    }

    private Funcion buscarFuncionEnConflicto(String codigoSala, LocalDateTime inicio, LocalDateTime fin) {
        for (int i = 0; i < funciones.size(); i++) {
            Funcion existente = funciones.get(i);
            if (existente.estaProgramada() && existente.getSala().getCodigo().equals(codigoSala)) {
                LocalDateTime inicioExistente = LocalDateTime.of(existente.getFecha(), existente.getHora());
                LocalDateTime finExistente = inicioExistente.plusMinutes(existente.getPelicula().getDuracionMinutos());
                if (inicio.isBefore(finExistente) && inicioExistente.isBefore(fin)) {
                    return existente;
                }
            }
        }
        return null;
    }

    public Funcion buscarFuncionPorCodigo(String codigo) {
        for (int i = 0; i < funciones.size(); i++) {
            if (funciones.get(i).getCodigo().equals(codigo)) {
                return funciones.get(i);
            }
        }
        return null;
    }

    public ArrayList<Funcion> listarFunciones() {
        return funciones;
    }

    public ArrayList<Funcion> consultarFuncionesProgramadasPorFecha(LocalDate fecha) {
        ArrayList<Funcion> resultado = new ArrayList<Funcion>();
        for (int i = 0; i < funciones.size(); i++) {
            Funcion funcion = funciones.get(i);
            if (funcion.getFecha().equals(fecha) && funcion.estaProgramada()) {
                resultado.add(funcion);
            }
        }
        return resultado;
    }

    // ================== Venta de entradas (operador) ==================

    public int cancelarFuncion(String codigoFuncion) throws FuncionNoEncontradaException, FuncionNoProgramadaException {
        Funcion funcion = buscarFuncionPorCodigo(codigoFuncion);
        if (funcion == null) {
            throw new FuncionNoEncontradaException("No existe una funcion con el codigo " + codigoFuncion);
        }
        if (!funcion.estaProgramada()) {
            throw new FuncionNoProgramadaException("La funcion " + codigoFuncion
                    + " no esta programada (estado " + funcion.getEstado() + ").");
        }
        int ventasCanceladas = 0;
        for (int i = 0; i < ventas.size(); i++) {
            Venta venta = ventas.get(i);
            if (venta.estaActiva() && venta.getFuncion().getCodigo().equals(codigoFuncion)) {
                venta.getAsiento().liberar();
                venta.cancelar();
                ventasCanceladas++;
            }
        }
        funcion.cancelar();
        return ventasCanceladas;
    }

    public void finalizarFuncion(String codigoFuncion) throws FuncionNoEncontradaException, FuncionNoProgramadaException {
        Funcion funcion = buscarFuncionPorCodigo(codigoFuncion);
        if (funcion == null) {
            throw new FuncionNoEncontradaException("No existe una funcion con el codigo " + codigoFuncion);
        }
        if (!funcion.estaProgramada()) {
            throw new FuncionNoProgramadaException("La funcion " + codigoFuncion
                    + " no esta programada (estado " + funcion.getEstado() + ").");
        }
        funcion.finalizar();
    }

    public int contarVentasActivasPorFuncion(String codigoFuncion) {
        int total = 0;
        for (int i = 0; i < ventas.size(); i++) {
            Venta venta = ventas.get(i);
            if (venta.estaActiva() && venta.getFuncion().getCodigo().equals(codigoFuncion)) {
                total++;
            }
        }
        return total;
    }

    public void habilitarPelicula(String codigo) throws PeliculaNoEncontradaException {
        Pelicula pelicula = buscarPeliculaPorCodigo(codigo);
        if (pelicula == null) {
            throw new PeliculaNoEncontradaException("No existe una pelicula con el codigo " + codigo);
        }
        pelicula.habilitar();
    }

    public void habilitarSala(String codigo) throws SalaNoEncontradaException {
        Sala sala = buscarSalaPorCodigo(codigo);
        if (sala == null) {
            throw new SalaNoEncontradaException("No existe una sala con el codigo " + codigo);
        }
        sala.habilitar();
    }

    public Venta venderEntrada(String codigoVenta, String codigoFuncion, Cliente cliente, String codigoAsiento)
            throws FuncionNoEncontradaException, FuncionNoProgramadaException, SinAsientosDisponiblesException,
            AsientoOcupadoException, VentaDuplicadaException {
        Funcion funcion = buscarFuncionPorCodigo(codigoFuncion);
        if (funcion == null) {
            throw new FuncionNoEncontradaException("No existe una funcion con el codigo " + codigoFuncion);
        }
        if (!funcion.estaProgramada()) {
            throw new FuncionNoProgramadaException("La funcion " + codigoFuncion
                    + " no esta programada (estado " + funcion.getEstado() + ").");
        }
        if (funcion.contarAsientosDisponibles() <= 0) {
            throw new SinAsientosDisponiblesException("No hay asientos disponibles para la funcion " + codigoFuncion);
        }
        Asiento asiento = funcion.buscarAsientoPorCodigo(codigoAsiento);
        if (asiento == null || !asiento.estaDisponible()) {
            throw new AsientoOcupadoException("El asiento " + codigoAsiento + " no esta disponible.");
        }
        if (buscarVentaPorCodigo(codigoVenta) != null) {
            throw new VentaDuplicadaException("Ya existe una venta con el codigo " + codigoVenta);
        }

        double valor = calcularValorEntrada(funcion, cliente.getTipoCliente());
        asiento.ocupar();
        Venta venta = new Venta(codigoVenta, cliente, funcion, asiento, valor);
        ventas.add(venta);
        return venta;
    }

    /** Calcula el valor de la entrada aplicando la tarifa del tipo de cliente sobre el valor base de la funcion. */
    public double calcularValorEntrada(Funcion funcion, TipoCliente tipoCliente) {
        double factor = tarifas.get(tipoCliente);
        return funcion.getValorBase() * factor;
    }

    public void cancelarVenta(String codigoVenta) throws VentaNoEncontradaException, VentaYaCanceladaException {
        Venta venta = buscarVentaPorCodigo(codigoVenta);
        if (venta == null) {
            throw new VentaNoEncontradaException("No existe una venta con el codigo " + codigoVenta);
        }
        if (!venta.estaActiva()) {
            throw new VentaYaCanceladaException("La venta " + codigoVenta + " ya fue cancelada.");
        }
        venta.getAsiento().liberar();
        venta.cancelar();
    }

    public Venta buscarVentaPorCodigo(String codigo) {
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).getCodigo().equals(codigo)) {
                return ventas.get(i);
            }
        }
        return null;
    }

    public ArrayList<Venta> listarVentas() {
        return ventas;
    }

    // ================== Reportes ==================

    public int totalEntradasVendidas() {
        int total = 0;
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).estaActiva()) {
                total++;
            }
        }
        return total;
    }

    public double totalIngresos() {
        double total = 0.0;
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).estaActiva()) {
                total += ventas.get(i).getValorPagado();
            }
        }
        return total;
    }

    public Pelicula peliculaConMasVentas() {
        Pelicula mejor = null;
        int mejorCantidad = 0;
        for (int i = 0; i < peliculas.size(); i++) {
            Pelicula pelicula = peliculas.get(i);
            int cantidad = contarVentasActivasPorPelicula(pelicula);
            if (cantidad > mejorCantidad) {
                mejorCantidad = cantidad;
                mejor = pelicula;
            }
        }
        return mejor;
    }

    private int contarVentasActivasPorPelicula(Pelicula pelicula) {
        int total = 0;
        for (int i = 0; i < ventas.size(); i++) {
            Venta venta = ventas.get(i);
            if (venta.estaActiva() && venta.getFuncion().getPelicula().getCodigo().equals(pelicula.getCodigo())) {
                total++;
            }
        }
        return total;
    }

    public double porcentajeOcupacion(String codigoFuncion) throws FuncionNoEncontradaException {
        Funcion funcion = buscarFuncionPorCodigo(codigoFuncion);
        if (funcion == null) {
            throw new FuncionNoEncontradaException("No existe una funcion con el codigo " + codigoFuncion);
        }
        int capacidadTotal = funcion.getSala().getCapacidad();
        int ocupados = funcion.contarAsientosOcupados();
        if (capacidadTotal == 0) {
            return 0.0;
        }
        return (ocupados * 100.0) / capacidadTotal;
    }

    public String getNombre() {
        return nombre;
    }

    public HashMap<TipoCliente, Double> getTarifas() {
        return tarifas;
    }
    public void modificarTarifa(TipoCliente tipoCliente, double factor) throws TarifaInvalidaException {
        if (factor <= 0 || factor > 1) {
            throw new TarifaInvalidaException("La tarifa debe ser mayor que 0 y no superar el 100% del valor base.");
        }
        tarifas.put(tipoCliente, factor);
    }

    public double obtenerTarifa(TipoCliente tipoCliente) {
        return tarifas.get(tipoCliente);
    }
}