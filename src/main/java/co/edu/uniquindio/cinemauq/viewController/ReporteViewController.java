package co.edu.uniquindio.cinemauq.viewController;

import co.edu.uniquindio.cinemauq.App;
import co.edu.uniquindio.cinemauq.controller.ReporteController;
import co.edu.uniquindio.cinemauq.excepciones.FuncionNoEncontradaException;
import co.edu.uniquindio.cinemauq.modelo.Funcion;
import co.edu.uniquindio.cinemauq.modelo.Pelicula;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ReporteViewController {

    ReporteController reporteController;
    ObservableList<Funcion> listFunciones = FXCollections.observableArrayList();
    ObservableList<Funcion> listFuncionesFecha = FXCollections.observableArrayList();

    @FXML
    private Label lblEntradasVendidas;

    @FXML
    private Label lblIngresos;

    @FXML
    private Label lblMasVendida;

    @FXML
    private ComboBox<Funcion> cmbFuncion;

    @FXML
    private Label lblOcupacion;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private TableView<Funcion> tblFuncionesFecha;

    @FXML
    private TableColumn<Funcion, String> tbcCodigo;

    @FXML
    private TableColumn<Funcion, String> tbcPelicula;

    @FXML
    private TableColumn<Funcion, String> tbcSala;

    @FXML
    private TableColumn<Funcion, LocalTime> tbcHora;

    @FXML
    private TableColumn<Funcion, Integer> tbcDisponibles;

    @FXML
    void onGenerarReportes() {
        generarReportesGenerales();
    }

    @FXML
    void onConsultarOcupacion() {
        consultarOcupacion();
    }

    @FXML
    void onConsultarPorFecha() {
        consultarPorFecha();
    }

    @FXML
    void initialize() {
        reporteController = new ReporteController(App.cinema);
        initView();
    }

    private void initView() {
        initDataBinding();
        cmbFuncion.setItems(listFunciones);
        tblFuncionesFecha.setItems(listFuncionesFecha);
        dpFecha.setValue(LocalDate.now());
        refrescar();
    }

    private void initDataBinding() {
        tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tbcPelicula.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getPelicula().getNombre()));
        tbcSala.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getSala().getNombre()));
        tbcHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        tbcDisponibles.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().contarAsientosDisponibles()));
    }

    public void refrescar() {
        listFunciones.setAll(reporteController.obtenerListaFunciones());
        cmbFuncion.setValue(null);
        lblOcupacion.setText("Seleccione una funcion y consulte su ocupacion.");
        listFuncionesFecha.clear();
        generarReportesGenerales();
    }

    private void generarReportesGenerales() {
        int totalEntradas = reporteController.totalEntradasVendidas();
        lblEntradasVendidas.setText("Total entradas vendidas: " + totalEntradas);
        lblIngresos.setText("Total ingresos: $" + String.format("%,.0f", reporteController.totalIngresos()));
        Pelicula masVendida = reporteController.peliculaConMasVentas();
        if (totalEntradas == 0 || masVendida == null) {
            lblMasVendida.setText("Pelicula mas vendida: sin ventas registradas");
        } else {
            lblMasVendida.setText("Pelicula mas vendida: " + masVendida.getNombre());
        }
    }

    private void consultarOcupacion() {
        Funcion funcion = cmbFuncion.getValue();
        if (funcion == null) {
            mostrarError("Debe seleccionar una funcion.");
            return;
        }
        try {
            double porcentaje = reporteController.porcentajeOcupacion(funcion.getCodigo());
            lblOcupacion.setText(String.format("Total: %d | Disponibles: %d | Ocupados: %d | Ocupacion: %.1f%%",
                    funcion.getSala().getCapacidad(), funcion.contarAsientosDisponibles(),
                    funcion.contarAsientosOcupados(), porcentaje));
        } catch (FuncionNoEncontradaException e) {
            mostrarError(e.getMessage());
        }
    }

    private void consultarPorFecha() {
        if (dpFecha.getValue() == null) {
            mostrarError("Seleccione la fecha a consultar.");
            return;
        }
        ArrayList<Funcion> resultado = reporteController.consultarFuncionesProgramadasPorFecha(dpFecha.getValue());
        listFuncionesFecha.setAll(resultado);
        if (resultado.isEmpty()) {
            mostrarInfo("No hay funciones programadas para esa fecha.");
        }
    }

    private void mostrarInfo(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION, mensaje);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR, mensaje);
        alerta.setHeaderText("No fue posible completar la operacion");
        alerta.showAndWait();
    }
}