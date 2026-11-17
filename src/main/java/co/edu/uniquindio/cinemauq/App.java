package co.edu.uniquindio.cinemauq;

import co.edu.uniquindio.cinemauq.modelo.Clasificacion;
import co.edu.uniquindio.cinemauq.modelo.CinemaUQ;
import co.edu.uniquindio.cinemauq.modelo.Pelicula;
import co.edu.uniquindio.cinemauq.modelo.Sala;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Clase de arranque de la aplicacion CinemaUQ. Guarda la unica instancia del
 * modelo (compartida por todas las pantallas) y abre la vista principal.
 */
public class App extends Application {

    private Stage primaryStage;
    public static CinemaUQ cinema = new CinemaUQ("CinemaUQ");

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CinemaUQ - Sistema de gestion de cine");
        inicializarData();
        openViewPrincipal();
    }

    private void openViewPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("primary.fxml"));
            TabPane rootLayout = (TabPane) loader.load();

            Scene scene = new Scene(rootLayout, 980, 650);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    /** Carga peliculas, salas y una funcion de ejemplo para no arrancar con la aplicacion vacia. */
    public void inicializarData() {
        try {
            cinema.registrarPelicula(new Pelicula("PEL01", "Avatar: El camino del agua", "Ciencia ficcion", 192, Clasificacion.MAYORES_12));
            cinema.registrarPelicula(new Pelicula("PEL02", "Intensamente 2", "Animacion", 100, Clasificacion.TODOS));
            cinema.registrarSala(new Sala("SAL01", "Sala 1", 30));
            cinema.registrarSala(new Sala("SAL02", "Sala 2 (3D)", 20));
            cinema.programarFuncion("FUN01", "PEL01", "SAL01", LocalDate.now().plusDays(1), LocalTime.of(19, 30), 15000.0);
        } catch (Exception e) {
            // Datos de ejemplo: si algo falla aqui no se debe detener el arranque de la app.
        }
    }
}