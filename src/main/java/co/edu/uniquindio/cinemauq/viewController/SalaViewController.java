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

    @FXML
    void onRegistrar() {
        registrarSala();
    }

    @FXML
    void onModificarNombre() {
        modificarNombreSala();
    }

    @FXML
    void onDeshabilitar() {
        deshabilitarSala();
    }

    @FXML
    void onHabilitar() {
        habilitarSala();
    }

    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }

    @FXML
    void initialize() {
        salaController = new SalaController(App.cinema);
        initView();
    }

    private void initView() {
        initDataBinding();
        tblListSala.setItems(listSalas);
        refrescar();
        listenerSelection();
    }

    private void initDataBinding() {
        tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tbcCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        tbcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }

    /** Vuelve a cargar la tabla desde el modelo (se llama al cambiar de pestana). */
    public void refrescar() {
        listSalas.setAll(salaController.obtenerListaSalas());
        tblListSala.refresh();
    }

    private void listenerSelection() {
        tblListSala.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccionada) -> {
            mostrarInformacionSala(seleccionada);
        });
    }

    private void mostrarInformacionSala(Sala sala) {
        if (sala != null) {
            txtCodigo.setText(sala.getCodigo());
            txtNombre.setText(sala.getNombre());
            txtCapacidad.setText(String.valueOf(sala.getCapacidad()));
        }
    }

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

    private void limpiarSeleccion() {
        tblListSala.getSelectionModel().clearSelection();
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtNombre.clear();
        txtCapacidad.clear();
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