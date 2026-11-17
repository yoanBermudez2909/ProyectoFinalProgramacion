package co.edu.uniquindio.cinemauq.modelo;

/**
 * Pelicula del catalogo de CinemaUQ.
 */
public class Pelicula {

    private String codigo;
    private String nombre;
    private String genero;
    private int duracionMinutos;
    private Clasificacion clasificacion;
    private EstadoPelicula estado;

    public Pelicula(String codigo, String nombre, String genero, int duracionMinutos, Clasificacion clasificacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.genero = genero;
        this.duracionMinutos = duracionMinutos;
        this.clasificacion = clasificacion;
        this.estado = EstadoPelicula.DISPONIBLE;
    }

    public void deshabilitar() {
        estado = EstadoPelicula.NO_DISPONIBLE;
    }

    public void habilitar() {
        estado = EstadoPelicula.DISPONIBLE;
    }

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
