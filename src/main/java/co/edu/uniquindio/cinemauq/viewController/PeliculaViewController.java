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

    /**
     * Metodo que atiende el evento del boton para registrar una pelicula
     */
    @FXML
    void onRegistrar() {
        registrarPelicula();
    }

    /**
     * Metodo que atiende el evento del boton para modificar una pelicula
     */
    @FXML
    void onModificar() {
        modificarPelicula();
    }

    /**
     * Metodo que atiende el evento del boton para deshabilitar una pelicula
     */
    @FXML
    void onDeshabilitar() {
        deshabilitarPelicula();
    }

    /**
     * Metodo que atiende el evento del boton para habilitar una pelicula
     */
    @FXML
    void onHabilitar() {
        habilitarPelicula();
    }

    /**
     * Metodo que atiende el evento del boton para limpiar la seleccion y los campos
     */
    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }

    /**
     * Metodo que inicializa el controlador de la vista de peliculas
     */
    @FXML
    void initialize() {
        peliculaController = new PeliculaController(App.cinema);
        initView();
    }

    /**
     * Metodo que prepara los componentes de la vista con sus valores iniciales
     */
    private void initView() {
        initDataBinding();
        cmbClasificacion.setItems(FXCollections.observableArrayList(Clasificacion.values()));
        cmbClasificacion.getSelectionModel().selectFirst();
        tblListPelicula.setItems(listPeliculas);
        refrescar();
        listenerSelection();
    }

    /**
     * Metodo que enlaza las columnas de la tabla con los datos de las peliculas
     */
    private void initDataBinding() {
        tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tbcGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        tbcDuracion.setCellValueFactory(new PropertyValueFactory<>("duracionMinutos"));
        tbcClasificacion.setCellValueFactory(new PropertyValueFactory<>("clasificacion"));
        tbcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }

    /**
     * Metodo que vuelve a cargar la tabla desde el modelo
     */
    public void refrescar() {
        listPeliculas.setAll(peliculaController.obtenerListaPeliculas());
        tblListPelicula.refresh();
    }

    /**
     * Metodo que escucha la seleccion de una pelicula en la tabla para mostrar su informacion
     */
    private void listenerSelection() {
        tblListPelicula.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccionada) -> {
            mostrarInformacionPelicula(seleccionada);
        });
    }

    /**
     * Metodo que muestra en el formulario la informacion de una pelicula
     * @param pelicula cuya informacion se muestra
     */
    private void mostrarInformacionPelicula(Pelicula pelicula) {
        if (pelicula != null) {
            txtCodigo.setText(pelicula.getCodigo());
            txtNombre.setText(pelicula.getNombre());
            txtGenero.setText(pelicula.getGenero());
            txtDuracion.setText(String.valueOf(pelicula.getDuracionMinutos()));
            cmbClasificacion.getSelectionModel().select(pelicula.getClasificacion());
        }
    }

    /**
     * Metodo que valida los datos del formulario y registra una pelicula
     */
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

    /**
     * Metodo que valida los datos del formulario y modifica una pelicula
     */
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

    /**
     * Metodo que deshabilita la pelicula cuyo codigo esta en el formulario
     */
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

    /**
     * Metodo que habilita la pelicula cuyo codigo esta en el formulario
     */
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

    /**
     * Metodo que verifica si los campos de texto del formulario estan diligenciados
     * @return true si todos los campos estan completos, false en caso contrario
     */
    private boolean camposCompletos() {
        return !txtCodigo.getText().trim().isEmpty()
                && !txtNombre.getText().trim().isEmpty()
                && !txtGenero.getText().trim().isEmpty()
                && !txtDuracion.getText().trim().isEmpty();
    }

    /**
     * Metodo que limpia la seleccion de la tabla y los campos del formulario
     */
    private void limpiarSeleccion() {
        tblListPelicula.getSelectionModel().clearSelection();
        limpiarCampos();
    }

    /**
     * Metodo que limpia los campos del formulario
     */
    private void limpiarCampos() {
        txtCodigo.clear();
        txtNombre.clear();
        txtGenero.clear();
        txtDuracion.clear();
        cmbClasificacion.getSelectionModel().selectFirst();
    }

    /**
     * Metodo que muestra una alerta informativa
     * @param mensaje que se muestra en la alerta
     */
    private void mostrarInfo(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION, mensaje);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    /**
     * Metodo que muestra una alerta de error
     * @param mensaje que se muestra en la alerta
     */
    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR, mensaje);
        alerta.setHeaderText("No fue posible completar la operacion");
        alerta.showAndWait();
    }
}