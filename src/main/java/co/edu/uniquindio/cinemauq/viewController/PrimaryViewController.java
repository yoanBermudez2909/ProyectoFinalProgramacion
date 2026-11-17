package co.edu.uniquindio.cinemauq.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class PrimaryViewController {

    @FXML
    private TabPane tabPane;

    @FXML
    private PeliculaViewController peliculaViewController;

    @FXML
    private SalaViewController salaViewController;

    @FXML
    private FuncionViewController funcionViewController;

    @FXML
    private TarifaViewController tarifaViewController;

    @FXML
    private VentaViewController ventaViewController;

    @FXML
    private ReporteViewController reporteViewController;

    @FXML
    void initialize() {
        tabPane.getSelectionModel().selectedIndexProperty().addListener((obs, anterior, nuevo) -> {
            refrescarPestana(nuevo.intValue());
        });
    }

    private void refrescarPestana(int indice) {
        switch (indice) {
            case 0:
                peliculaViewController.refrescar();
                break;
            case 1:
                salaViewController.refrescar();
                break;
            case 2:
                funcionViewController.refrescar();
                break;
            case 3:
                tarifaViewController.refrescar();
                break;
            case 4:
                ventaViewController.refrescar();
                break;
            case 5:
                reporteViewController.refrescar();
                break;
        }
    }
}