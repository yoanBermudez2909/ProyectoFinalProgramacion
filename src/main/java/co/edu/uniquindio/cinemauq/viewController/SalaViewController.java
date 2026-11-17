package co.edu.uniquindio.cinemauq.viewController;

import co.edu.uniquindio.cinemauq.App;
import co.edu.uniquindio.cinemauq.controller.SalaController;
import co.edu.uniquindio.cinemauq.excepciones.SalaDuplicadaException;
import co.edu.uniquindio.cinemauq.excepciones.SalaNoEncontradaException;
import co.edu.uniquindio.cinemauq.modelo.EstadoSala;
import co.edu.uniquindio.cinemauq.modelo.Sala;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SalaViewController {

    SalaController salaController;
    ObservableList<Sala> listSalas = FXCollections.observableArrayList();

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCapacidad;

    @FXML
    private TableView<Sala> tblListSala;

    @FXML
    private TableColumn<Sala, String> tbcCodigo;

    @FXML
    private TableColumn<Sala, String> tbcNombre;

    @FXML
    private TableColumn<Sala, Integer> tbcCapacidad;

    @FXML
    private TableColumn<Sala, EstadoSala> tbcEstado;

    /**
     * Metodo que atiende el evento del boton para registrar una sala
     */
    @FXML
    void onRegistrar() {
        registrarSala();
    }

    /**
     * Metodo que atiende el evento del boton para modificar el nombre de una sala
     */
    @FXML
    void onModificarNombre() {
        modificarNombreSala();
    }

    /**
     * Metodo que atiende el evento del boton para deshabilitar una sala
     */
    @FXML
    void onDeshabilitar() {
        deshabilitarSala();
    }

    /**
     * Metodo que atiende el evento del boton para habilitar una sala
     */
    @FXML
    void onHabilitar() {
        habilitarSala();
    }

    /**
     * Metodo que atiende el evento del boton para limpiar la seleccion y los campos
     */
    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }

    /**
     * Metodo que inicializa el controlador de la vista de salas
     */
    @FXML
    void initialize() {
        salaController = new SalaController(App.cinema);
        initView();
    }

    /**
     * Metodo que prepara los componentes de la vista con sus valores iniciales
     */
    private void initView() {
        initDataBinding();
        tblListSala.setItems(listSalas);
        refrescar();
        listenerSelection();
    }

    /**
     * Metodo que enlaza las columnas de la tabla con los datos de las salas
     */
    private void initDataBinding() {
        tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tbcCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        tbcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }

    /**
     * Metodo que vuelve a cargar la tabla desde el modelo
     */
    public void refrescar() {
        listSalas.setAll(salaController.obtenerListaSalas());
        tblListSala.refresh();
    }

    /**
     * Metodo que escucha la seleccion de una sala en la tabla para mostrar su informacion
     */
    private void listenerSelection() {
        tblListSala.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccionada) -> {
            mostrarInformacionSala(seleccionada);
        });
    }

    /**
     * Metodo que muestra en el formulario la informacion de una sala
     * @param sala cuya informacion se muestra
     */
    private void mostrarInformacionSala(Sala sala) {
        if (sala != null) {
            txtCodigo.setText(sala.getCodigo());
            txtNombre.setText(sala.getNombre());
            txtCapacidad.setText(String.valueOf(sala.getCapacidad()));
        }
    }

    /**
     * Metodo que valida los datos del formulario y registra una sala
     */
    private void registrarSala() {
        if (txtCodigo.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()
                || txtCapacidad.getText().trim().isEmpty()) {
            mostrarError("Debe diligenciar todos los campos.");
            return;
        }
        try {
            int capacidad = Integer.parseInt(txtCapacidad.getText().trim());
            if (capacidad <= 0) {
                mostrarError("La capacidad debe ser mayor que cero.");
                return;
            }
            Sala sala = new Sala(txtCodigo.getText().trim(), txtNombre.getText().trim(), capacidad);
            salaController.registrarSala(sala);
            refrescar();
            limpiarSeleccion();
            mostrarInfo("Sala registrada correctamente (se generaron " + capacidad + " asientos).");
        } catch (NumberFormatException e) {
            mostrarError("La capacidad debe ser un numero entero.");
        } catch (SalaDuplicadaException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Metodo que modifica el nombre de la sala cuyo codigo esta en el formulario
     */
    private void modificarNombreSala() {
        if (txtCodigo.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()) {
            mostrarError("Debe indicar el codigo y el nuevo nombre de la sala.");
            return;
        }
        try {
            salaController.modificarSala(txtCodigo.getText().trim(), txtNombre.getText().trim());
            refrescar();
            limpiarSeleccion();
            mostrarInfo("Sala modificada correctamente.");
        } catch (SalaNoEncontradaException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Metodo que deshabilita la sala cuyo codigo esta en el formulario
     */
    private void deshabilitarSala() {
        if (txtCodigo.getText().trim().isEmpty()) {
            mostrarError("Seleccione una sala de la tabla o escriba su codigo.");
            return;
        }
        try {
            salaController.deshabilitarSala(txtCodigo.getText().trim());
            refrescar();
            limpiarSeleccion();
            mostrarInfo("Sala deshabilitada.");
        } catch (SalaNoEncontradaException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Metodo que habilita la sala cuyo codigo esta en el formulario
     */
    private void habilitarSala() {
        if (txtCodigo.getText().trim().isEmpty()) {
            mostrarError("Seleccione una sala de la tabla o escriba su codigo.");
            return;
        }
        try {
            salaController.habilitarSala(txtCodigo.getText().trim());
            refrescar();
            limpiarSeleccion();
            mostrarInfo("Sala habilitada.");
        } catch (SalaNoEncontradaException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Metodo que limpia la seleccion de la tabla y los campos del formulario
     */
    private void limpiarSeleccion() {
        tblListSala.getSelectionModel().clearSelection();
        limpiarCampos();
    }

    /**
     * Metodo que limpia los campos del formulario
     */
    private void limpiarCampos() {
        txtCodigo.clear();
        txtNombre.clear();
        txtCapacidad.clear();
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