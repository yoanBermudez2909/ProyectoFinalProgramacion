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

public class CinemaUQ {

    private String nombre;
    private ArrayList<Pelicula> peliculas;
    private ArrayList<Sala> salas;
    private ArrayList<Funcion> funciones;
    private ArrayList<Venta> ventas;
    private HashMap<TipoCliente, Double> tarifas;

    /**
     * Metodo constructor de la clase CinemaUQ
     * @param nombre del cinema
     */
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

    /**
     * Metodo que permite registrar una pelicula en el cinema
     * @param pelicula a registrar
     * @throws PeliculaDuplicadaException si ya existe una pelicula con el mismo codigo
     */
    public void registrarPelicula(Pelicula pelicula) throws PeliculaDuplicadaException {
        if (buscarPeliculaPorCodigo(pelicula.getCodigo()) != null) {
            throw new PeliculaDuplicadaException("Ya existe una pelicula con el codigo " + pelicula.getCodigo());
        }
        peliculas.add(pelicula);
    }

    /**
     * Metodo que permite buscar una pelicula por su codigo
     * @param codigo de la pelicula a buscar
     * @return la pelicula encontrada o null si no existe
     */
    public Pelicula buscarPeliculaPorCodigo(String codigo) {
        for (int i = 0; i < peliculas.size(); i++) {
            if (peliculas.get(i).getCodigo().equals(codigo)) {
                return peliculas.get(i);
            }
        }
        return null;
    }

    /**
     * Metodo que permite listar las peliculas del cinema
     * @return lista de peliculas registradas
     */
    public ArrayList<Pelicula> listarPeliculas() {
        return peliculas;
    }

    /**
     * Metodo que permite modificar la informacion de una pelicula
     * @param codigo de la pelicula a modificar
     * @param nombre nuevo de la pelicula
     * @param genero nuevo de la pelicula
     * @param duracionMinutos nueva de la pelicula
     * @param clasificacion nueva de la pelicula
     * @throws PeliculaNoEncontradaException si no existe una pelicula con ese codigo
     */
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

    /**
     * Metodo que permite deshabilitar una pelicula
     * @param codigo de la pelicula a deshabilitar
     * @throws PeliculaNoEncontradaException si no existe una pelicula con ese codigo
     */
    public void deshabilitarPelicula(String codigo) throws PeliculaNoEncontradaException {
        Pelicula pelicula = buscarPeliculaPorCodigo(codigo);
        if (pelicula == null) {
            throw new PeliculaNoEncontradaException("No existe una pelicula con el codigo " + codigo);
        }
        pelicula.deshabilitar();
    }

    /**
     * Metodo que permite registrar una sala en el cinema
     * @param sala a registrar
     * @throws SalaDuplicadaException si ya existe una sala con el mismo codigo
     */
    public void registrarSala(Sala sala) throws SalaDuplicadaException {
        if (buscarSalaPorCodigo(sala.getCodigo()) != null) {
            throw new SalaDuplicadaException("Ya existe una sala con el codigo " + sala.getCodigo());
        }
        salas.add(sala);
    }

    /**
     * Metodo que permite buscar una sala por su codigo
     * @param codigo de la sala a buscar
     * @return la sala encontrada o null si no existe
     */
    public Sala buscarSalaPorCodigo(String codigo) {
        for (int i = 0; i < salas.size(); i++) {
            if (salas.get(i).getCodigo().equals(codigo)) {
                return salas.get(i);
            }
        }
        return null;
    }

    /**
     * Metodo que permite listar las salas del cinema
     * @return lista de salas registradas
     */
    public ArrayList<Sala> listarSalas() {
        return salas;
    }

    /**
     * Metodo que permite modificar el nombre de una sala
     * @param codigo de la sala a modificar
     * @param nuevoNombre de la sala
     * @throws SalaNoEncontradaException si no existe una sala con ese codigo
     */
    public void modificarSala(String codigo, String nuevoNombre) throws SalaNoEncontradaException {
        Sala sala = buscarSalaPorCodigo(codigo);
        if (sala == null) {
            throw new SalaNoEncontradaException("No existe una sala con el codigo " + codigo);
        }
        sala.setNombre(nuevoNombre);
    }

    /**
     * Metodo que permite deshabilitar una sala
     * @param codigo de la sala a deshabilitar
     * @throws SalaNoEncontradaException si no existe una sala con ese codigo
     */
    public void deshabilitarSala(String codigo) throws SalaNoEncontradaException {
        Sala sala = buscarSalaPorCodigo(codigo);
        if (sala == null) {
            throw new SalaNoEncontradaException("No existe una sala con el codigo " + codigo);
        }
        sala.deshabilitar();
    }

    /**
     * Metodo que permite programar una funcion en el cinema
     * @param codigo de la funcion
     * @param codigoPelicula de la funcion
     * @param codigoSala de la funcion
     * @param fecha de la funcion
     * @param hora de la funcion
     * @param valorBase de la funcion
     * @return la funcion programada
     * @throws PeliculaNoEncontradaException si no existe la pelicula
     * @throws SalaNoEncontradaException si no existe la sala
     * @throws SalaNoDisponibleException si la sala esta fuera de servicio
     * @throws PeliculaNoDisponibleException si la pelicula no esta disponible
     * @throws FuncionDuplicadaException si ya existe una funcion con el mismo codigo
     * @throws SalaOcupadaException si la sala ya tiene una funcion en ese horario
     */
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

    /**
     * Metodo que permite buscar una funcion programada que se cruce con un horario en una sala
     * @param codigoSala de la sala a revisar
     * @param inicio del horario a revisar
     * @param fin del horario a revisar
     * @return la funcion en conflicto o null si no hay ninguna
     */
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

    /**
     * Metodo que permite buscar una funcion por su codigo
     * @param codigo de la funcion a buscar
     * @return la funcion encontrada o null si no existe
     */
    public Funcion buscarFuncionPorCodigo(String codigo) {
        for (int i = 0; i < funciones.size(); i++) {
            if (funciones.get(i).getCodigo().equals(codigo)) {
                return funciones.get(i);
            }
        }
        return null;
    }

    /**
     * Metodo que permite listar las funciones del cinema
     * @return lista de funciones registradas
     */
    public ArrayList<Funcion> listarFunciones() {
        return funciones;
    }

    /**
     * Metodo que permite consultar las funciones programadas en una fecha
     * @param fecha de las funciones a consultar
     * @return lista de funciones programadas en esa fecha
     */
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

    /**
     * Metodo que permite cancelar una funcion y sus ventas activas
     * @param codigoFuncion de la funcion a cancelar
     * @return cantidad de ventas que se cancelaron
     * @throws FuncionNoEncontradaException si no existe la funcion
     * @throws FuncionNoProgramadaException si la funcion no esta programada
     */
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

    /**
     * Metodo que permite finalizar una funcion
     * @param codigoFuncion de la funcion a finalizar
     * @throws FuncionNoEncontradaException si no existe la funcion
     * @throws FuncionNoProgramadaException si la funcion no esta programada
     */
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

    /**
     * Metodo que permite contar las ventas activas de una funcion
     * @param codigoFuncion de la funcion a consultar
     * @return cantidad de ventas activas de la funcion
     */
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

    /**
     * Metodo que permite habilitar una pelicula
     * @param codigo de la pelicula a habilitar
     * @throws PeliculaNoEncontradaException si no existe una pelicula con ese codigo
     */
    public void habilitarPelicula(String codigo) throws PeliculaNoEncontradaException {
        Pelicula pelicula = buscarPeliculaPorCodigo(codigo);
        if (pelicula == null) {
            throw new PeliculaNoEncontradaException("No existe una pelicula con el codigo " + codigo);
        }
        pelicula.habilitar();
    }

    /**
     * Metodo que permite habilitar una sala
     * @param codigo de la sala a habilitar
     * @throws SalaNoEncontradaException si no existe una sala con ese codigo
     */
    public void habilitarSala(String codigo) throws SalaNoEncontradaException {
        Sala sala = buscarSalaPorCodigo(codigo);
        if (sala == null) {
            throw new SalaNoEncontradaException("No existe una sala con el codigo " + codigo);
        }
        sala.habilitar();
    }

    /**
     * Metodo que permite vender una entrada para una funcion
     * @param codigoVenta de la venta
     * @param codigoFuncion de la funcion
     * @param cliente que compra la entrada
     * @param codigoAsiento del asiento seleccionado
     * @return la venta registrada
     * @throws FuncionNoEncontradaException si no existe la funcion
     * @throws FuncionNoProgramadaException si la funcion no esta programada
     * @throws SinAsientosDisponiblesException si no hay asientos disponibles
     * @throws AsientoOcupadoException si el asiento no esta disponible
     * @throws VentaDuplicadaException si ya existe una venta con el mismo codigo
     */
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

    /**
     * Metodo que permite calcular el valor de una entrada segun el tipo de cliente
     * @param funcion de la cual se toma el valor base
     * @param tipoCliente del cliente que compra la entrada
     * @return valor de la entrada
     */
    public double calcularValorEntrada(Funcion funcion, TipoCliente tipoCliente) {
        double factor = tarifas.get(tipoCliente);
        return funcion.getValorBase() * factor;
    }

    /**
     * Metodo que permite cancelar una venta y liberar su asiento
     * @param codigoVenta de la venta a cancelar
     * @throws VentaNoEncontradaException si no existe la venta
     * @throws VentaYaCanceladaException si la venta ya fue cancelada
     */
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

    /**
     * Metodo que permite buscar una venta por su codigo
     * @param codigo de la venta a buscar
     * @return la venta encontrada o null si no existe
     */
    public Venta buscarVentaPorCodigo(String codigo) {
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).getCodigo().equals(codigo)) {
                return ventas.get(i);
            }
        }
        return null;
    }

    /**
     * Metodo que permite listar las ventas del cinema
     * @return lista de ventas registradas
     */
    public ArrayList<Venta> listarVentas() {
        return ventas;
    }

    /**
     * Metodo que permite obtener el total de entradas vendidas
     * @return cantidad de ventas activas
     */
    public int totalEntradasVendidas() {
        int total = 0;
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).estaActiva()) {
                total++;
            }
        }
        return total;
    }

    /**
     * Metodo que permite obtener el total de ingresos del cinema
     * @return suma del valor pagado de las ventas activas
     */
    public double totalIngresos() {
        double total = 0.0;
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).estaActiva()) {
                total += ventas.get(i).getValorPagado();
            }
        }
        return total;
    }

    /**
     * Metodo que permite obtener la pelicula con mayor cantidad de entradas vendidas
     * @return la pelicula con mas ventas o null si no hay ventas
     */
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

    /**
     * Metodo que permite contar las ventas activas de una pelicula
     * @param pelicula de la cual se cuentan las ventas
     * @return cantidad de ventas activas de la pelicula
     */
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

    /**
     * Metodo que permite calcular el porcentaje de ocupacion de una funcion
     * @param codigoFuncion de la funcion a consultar
     * @return porcentaje de asientos ocupados
     * @throws FuncionNoEncontradaException si no existe la funcion
     */
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

    /**
     * Metodo que permite modificar la tarifa de un tipo de cliente
     * @param tipoCliente al cual se le modifica la tarifa
     * @param factor nuevo que se aplica sobre el valor base
     * @throws TarifaInvalidaException si el factor es menor o igual a 0 o mayor a 1
     */
    public void modificarTarifa(TipoCliente tipoCliente, double factor) throws TarifaInvalidaException {
        if (factor <= 0 || factor > 1) {
            throw new TarifaInvalidaException("La tarifa debe ser mayor que 0 y no superar el 100% del valor base.");
        }
        tarifas.put(tipoCliente, factor);
    }

    /**
     * Metodo que permite obtener la tarifa de un tipo de cliente
     * @param tipoCliente del cual se consulta la tarifa
     * @return factor que se aplica sobre el valor base
     */
    public double obtenerTarifa(TipoCliente tipoCliente) {
        return tarifas.get(tipoCliente);
    }
}