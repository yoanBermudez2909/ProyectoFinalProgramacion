package co.edu.uniquindio.cinemauq.modelo;

public class Pelicula {

    private String codigo;
    private String nombre;
    private String genero;
    private int duracionMinutos;
    private Clasificacion clasificacion;
    private EstadoPelicula estado;

    /**
     * Metodo constructor de la clase Pelicula
     * @param codigo de la pelicula
     * @param nombre de la pelicula
     * @param genero de la pelicula
     * @param duracionMinutos de la pelicula
     * @param clasificacion de la pelicula
     */
    public Pelicula(String codigo, String nombre, String genero, int duracionMinutos, Clasificacion clasificacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.genero = genero;
        this.duracionMinutos = duracionMinutos;
        this.clasificacion = clasificacion;
        this.estado = EstadoPelicula.DISPONIBLE;
    }

    /**
     * Metodo que permite deshabilitar la pelicula
     */
    public void deshabilitar() {
        estado = EstadoPelicula.NO_DISPONIBLE;
    }

    /**
     * Metodo que permite habilitar la pelicula
     */
    public void habilitar() {
        estado = EstadoPelicula.DISPONIBLE;
    }

    /**
     * Metodo que permite saber si la pelicula esta disponible
     * @return true si la pelicula esta disponible, false en caso contrario
     */
    public boolean estaDisponible() {
        return estado == EstadoPelicula.DISPONIBLE;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public EstadoPelicula getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " (" + genero + ", " + duracionMinutos + " min, " + clasificacion + ") - " + estado;
    }
}