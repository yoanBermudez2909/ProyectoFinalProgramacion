package co.edu.uniquindio.cinemauq.viewController;

import co.edu.uniquindio.cinemauq.App;
import co.edu.uniquindio.cinemauq.controller.PeliculaController;
import co.edu.uniquindio.cinemauq.excepciones.PeliculaDuplicadaException;
import co.edu.uniquindio.cinemauq.excepciones.PeliculaNoEncontradaException;
import co.edu.uniquindio.cinemauq.modelo.Clasificacion;
import co.edu.uniquindio.cinemauq.modelo.EstadoPelicula;
import co.edu.uniquindio.cinemauq.modelo.Pelicula;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PeliculaViewController {

    PeliculaController peliculaController;
    ObservableList<Pelicula> listPeliculas = FXCollections.observableArrayList();

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtGenero;

    @FXML
    private TextField txtDuracion;

    @FXML
    private ComboBox<Clasificacion> cmbClasificacion;

    @FXML
    private TableView<Pelicula> tblListPelicula;

    @FXML
    private TableColumn<Pelicula, String> tbcCodigo;

    @FXML
    private TableColumn<Pelicula, String> tbcNombre;

    @FXML
    private TableColumn<Pelicula, String> tbcGenero;

    @FXML
    private TableColumn<Pelicula, Integer> tbcDuracion;

    @FXML
    private TableColumn<Pelicula, Clasificacion> tbcClasificacion;

    @FXML
    private TableColumn<Pelicula, EstadoPelicula> tbcEstado;

    @FXML
    void onRegistrar() {
        registrarPelicula();
    }

    @FXML
    void onModificar() {
        modificarPelicula();
    }

    @FXML
    void onDeshabilitar() {
        deshabilitarPelicula();
    }

    @FXML
    void onHabilitar() {
        habilitarPelicula();
    }

    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }

    @FXML
    void initialize() {
        peliculaController = new PeliculaController(App.cinema);
        initView();
    }

    private void initView() {
        initDataBinding();
        cmbClasificacion.setItems(FXCollections.observableArrayList(Clasificacion.values()));
        cmbClasificacion.getSelectionModel().selectFirst();
        tblListPelicula.setItems(listPeliculas);
        refrescar();
        listenerSelection();
    }

    private void initDataBinding() {
        tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tbcGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        tbcDuracion.setCellValueFactory(new PropertyValueFactory<>("duracionMinutos"));
        tbcClasificacion.setCellValueFactory(new PropertyValueFactory<>("clasificacion"));
        tbcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }

    /** Vuelve a cargar la tabla desde el modelo (se llama al cambiar de pestana). */
    public void refrescar() {
        listPeliculas.setAll(peliculaController.obtenerListaPeliculas());
        tblListPelicula.refresh();
    }

    private void listenerSelection() {
        tblListPelicula.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccionada) -> {
            mostrarInformacionPelicula(seleccionada);
        });
    }

    private void mostrarInformacionPelicula(Pelicula pelicula) {
        if (pelicula != null) {
            txtCodigo.setText(pelicula.getCodigo());
            txtNombre.setText(pelicula.getNombre());
            txtGenero.setText(pelicula.getGenero());
            txtDuracion.setText(String.valueOf(pelicula.getDuracionMinutos()));
            cmbClasificacion.getSelectionModel().select(pelicula.getClasificacion());
        }
    }

    private void registrarPelicula() {
        if (!camposCompletos()) {
            mostrarError("Debe diligenciar todos los campos.");
            return;
        }
        try {
            int duracion = Integer.parseInt(txtDuracion.getText().trim());
            Pelicula pelicula = new Pelicula(txtCodigo.getText().trim(), txtNombre.getText().trim(),
                    txtGenero.getText().trim(), duracion, cmbClasificacion.getValue());
            peliculaController.registrarPelicula(pelicula);
            refrescar();
            limpiarSeleccion();
            mostrarInfo("Pelicula registrada correctamente.");
        } catch (NumberFormatException e) {
            mostrarError("La duracion debe ser un numero entero de minutos.");
        } catch (PeliculaDuplicadaException e) {
            mostrarError(e.getMessage());
        }
    }

    private void modificarPelicula() {
        if (!camposCompletos()) {
            mostrarError("Debe diligenciar todos los campos.");
            return;
        }
        try {
            int duracion = Integer.parseInt(txtDuracion.getText().trim());
            peliculaController.modificarPelicula(txtCodigo.getText().trim(), txtNombre.getText().trim(),
                    txtGenero.getText().trim(), duracion, cmbClasificacion.getValue());
            refrescar();
            limpiarSeleccion();
            mostrarInfo("Pelicula modificada correctamente.");
        } catch (NumberFormatException e) {
            mostrarError("La duracion debe ser un numero entero de minutos.");
        } catch (PeliculaNoEncontradaException e) {
            mostrarError(e.getMessage());
        }
    }

    private void deshabilitarPelicula() {
        if (txtCodigo.getText().trim().isEmpty()) {
            mostrarError("Seleccione una pelicula de la tabla o escriba su codigo.");
            return;
        }
        try {
            peliculaController.deshabilitarPelicula(txtCodigo.getText().trim());
            refrescar();
            limpiarSeleccion();
            mostrarInfo("Pelicula deshabilitada.");
        } catch (PeliculaNoEncontradaException e) {
            mostrarError(e.getMessage());
        }
    }

    private void habilitarPelicula() {
        if (txtCodigo.getText().trim().isEmpty()) {
            mostrarError("Seleccione una pelicula de la tabla o escriba su codigo.");
            return;
        }
        try {
            peliculaController.habilitarPelicula(txtCodigo.getText().trim());
            refrescar();
            limpiarSeleccion();
            mostrarInfo("Pelicula habilitada.");
        } catch (PeliculaNoEncontradaException e) {
            mostrarError(e.getMessage());
        }
    }

    private boolean camposCompletos() {
        return !txtCodigo.getText().trim().isEmpty()
                && !txtNombre.getText().trim().isEmpty()
                && !txtGenero.getText().trim().isEmpty()
                && !txtDuracion.getText().trim().isEmpty();
    }

    private void limpiarSeleccion() {
        tblListPelicula.getSelectionModel().clearSelection();
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtNombre.clear();
        txtGenero.clear();
        txtDuracion.clear();
        cmbClasificacion.getSelectionModel().selectFirst();
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