package co.edu.uniquindio.cinemauq.viewController;

import co.edu.uniquindio.cinemauq.App;
import co.edu.uniquindio.cinemauq.controller.TarifaController;
import co.edu.uniquindio.cinemauq.excepciones.TarifaInvalidaException;
import co.edu.uniquindio.cinemauq.modelo.TipoCliente;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TarifaViewController {

    private static final double VALOR_EJEMPLO = 15000;

    TarifaController tarifaController;
    ObservableList<TipoCliente> listTipos = FXCollections.observableArrayList();

    @FXML
    private ComboBox<TipoCliente> cmbTipoCliente;

    @FXML
    private TextField txtPorcentaje;

    @FXML
    private TableView<TipoCliente> tblListTarifa;

    @FXML
    private TableColumn<TipoCliente, String> tbcTipo;

    @FXML
    private TableColumn<TipoCliente, String> tbcPorcentaje;

    @FXML
    private TableColumn<TipoCliente, String> tbcEjemplo;

    /**
     * Metodo que atiende el evento del boton para modificar una tarifa
     */
    @FXML
    void onModificar() {
        modificarTarifa();
    }

    /**
     * Metodo que atiende el evento del boton para limpiar la seleccion y los campos
     */
    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }

    /**
     * Metodo que inicializa el controlador de la vista de tarifas
     */
    @FXML
    void initialize() {
        tarifaController = new TarifaController(App.cinema);
        initView();
    }

    /**
     * Metodo que prepara los componentes de la vista con sus valores iniciales
     */
    private void initView() {
        initDataBinding();
        listTipos.setAll(TipoCliente.values());
        tblListTarifa.setItems(listTipos);
        cmbTipoCliente.setItems(FXCollections.observableArrayList(TipoCliente.values()));
        cmbTipoCliente.getSelectionModel().selectFirst();
        listenerSelection();
    }

    /**
     * Metodo que enlaza las columnas de la tabla con los datos de las tarifas
     */
    private void initDataBinding() {
        tbcTipo.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().name()));
        tbcPorcentaje.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(
                String.format("%.1f%%", tarifaController.obtenerTarifa(d.getValue()) * 100)));
        tbcEjemplo.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(
                "$" + String.format("%,.0f", VALOR_EJEMPLO * tarifaController.obtenerTarifa(d.getValue()))));
    }

    /**
     * Metodo que actualiza la tabla de tarifas
     */
    public void refrescar() {
        tblListTarifa.refresh();
    }

    /**
     * Metodo que escucha la seleccion de un tipo de cliente en la tabla para mostrar su tarifa
     */
    private void listenerSelection() {
        tblListTarifa.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccionado) -> {
            if (seleccionado != null) {
                cmbTipoCliente.setValue(seleccionado);
                txtPorcentaje.setText(String.format("%.0f", tarifaController.obtenerTarifa(seleccionado) * 100));
            }
        });
    }

    /**
     * Metodo que valida el porcentaje ingresado y modifica la tarifa del tipo de cliente seleccionado
     */
    private void modificarTarifa() {
        TipoCliente tipo = cmbTipoCliente.getValue();
        String texto = txtPorcentaje.getText().trim();
        if (tipo == null || texto.isEmpty()) {
            mostrarError("Seleccione el tipo de cliente e indique el porcentaje.");
            return;
        }
        try {
            int porcentaje = Integer.parseInt(texto);
            tarifaController.modificarTarifa(tipo, porcentaje / 100.0);
            tblListTarifa.refresh();
            limpiarSeleccion();
            mostrarInfo("Tarifa de " + tipo + " actualizada a " + porcentaje + "% del valor base.");
        } catch (NumberFormatException e) {
            mostrarError("El porcentaje debe ser un numero entero, por ejemplo 80.");
        } catch (TarifaInvalidaException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Metodo que limpia la seleccion de la tabla y los campos del formulario
     */
    private void limpiarSeleccion() {
        tblListTarifa.getSelectionModel().clearSelection();
        txtPorcentaje.clear();
        cmbTipoCliente.getSelectionModel().selectFirst();
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