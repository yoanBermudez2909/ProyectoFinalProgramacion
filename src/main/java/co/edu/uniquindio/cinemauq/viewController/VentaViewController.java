package co.edu.uniquindio.cinemauq.viewController;

import co.edu.uniquindio.cinemauq.App;
import co.edu.uniquindio.cinemauq.controller.VentaController;
import co.edu.uniquindio.cinemauq.excepciones.AsientoOcupadoException;
import co.edu.uniquindio.cinemauq.excepciones.FuncionNoEncontradaException;
import co.edu.uniquindio.cinemauq.excepciones.SinAsientosDisponiblesException;
import co.edu.uniquindio.cinemauq.excepciones.VentaNoEncontradaException;
import co.edu.uniquindio.cinemauq.modelo.Asiento;
import co.edu.uniquindio.cinemauq.modelo.Cliente;
import co.edu.uniquindio.cinemauq.modelo.EstadoVenta;
import co.edu.uniquindio.cinemauq.modelo.Funcion;
import co.edu.uniquindio.cinemauq.modelo.TipoCliente;
import co.edu.uniquindio.cinemauq.modelo.Venta;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import co.edu.uniquindio.cinemauq.excepciones.VentaDuplicadaException;
import co.edu.uniquindio.cinemauq.excepciones.VentaYaCanceladaException;
import co.edu.uniquindio.cinemauq.excepciones.FuncionNoProgramadaException;

import java.time.format.DateTimeFormatter;

public class VentaViewController {

    private static final String ESTILO_LIBRE = "-fx-background-color: #a5d6a7; -fx-text-fill: black;";
    private static final String ESTILO_OCUPADO = "-fx-background-color: #ef9a9a; -fx-text-fill: black;";
    private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    VentaController ventaController;
    ObservableList<Venta> listVentas = FXCollections.observableArrayList();
    ObservableList<Funcion> listFunciones = FXCollections.observableArrayList();

    @FXML
    private TextField txtCodigoVenta;

    @FXML
    private TextField txtIdCliente;

    @FXML
    private TextField txtNombreCliente;

    @FXML
    private TextField txtCodigoAsiento;

    @FXML
    private ComboBox<Funcion> cmbFuncion;

    @FXML
    private ComboBox<TipoCliente> cmbTipoCliente;

    @FXML
    private Label lblValor;

    @FXML
    private Label lblDisponibilidad;

    @FXML
    private FlowPane fpAsientos;

    @FXML
    private TableView<Venta> tblListVenta;

    @FXML
    private TableColumn<Venta, String> tbcCodigo;

    @FXML
    private TableColumn<Venta, String> tbcCliente;

    @FXML
    private TableColumn<Venta, String> tbcPelicula;

    @FXML
    private TableColumn<Venta, String> tbcAsiento;

    @FXML
    private TableColumn<Venta, TipoCliente> tbcTipo;

    @FXML
    private TableColumn<Venta, Double> tbcValor;

    @FXML
    private TableColumn<Venta, EstadoVenta> tbcEstado;

    /**
     * Metodo que atiende el evento del boton para vender una entrada
     */
    @FXML
    void onVender() {
        venderEntrada();
    }

    /**
     * Metodo que atiende el evento del boton para cancelar una venta
     */
    @FXML
    void onCancelar() {
        cancelarVenta();
    }

    /**
     * Metodo que atiende el evento del boton para limpiar la seleccion y los campos
     */
    @FXML
    void onLimpiar() {
        tblListVenta.getSelectionModel().clearSelection();
        limpiarCampos();
    }

    /**
     * Metodo que inicializa el controlador de la vista de ventas
     */
    @FXML
    void initialize() {
        ventaController = new VentaController(App.cinema);
        initView();
    }

    /**
     * Metodo que prepara los componentes de la vista con sus valores iniciales
     */
    private void initView() {
        initDataBinding();
        cmbFuncion.setItems(listFunciones);
        cmbTipoCliente.setItems(FXCollections.observableArrayList(TipoCliente.values()));
        cmbTipoCliente.getSelectionModel().selectFirst();
        tblListVenta.setItems(listVentas);
        listenerSelection();
        refrescar();
    }

    /**
     * Metodo que enlaza las columnas de la tabla con los datos de las ventas
     */
    private void initDataBinding() {
        tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tbcCliente.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getCliente().getNombre()));
        tbcPelicula.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getFuncion().getPelicula().getNombre()));
        tbcAsiento.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getAsiento().getCodigo()));
        tbcTipo.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getCliente().getTipoCliente()));
        tbcValor.setCellValueFactory(new PropertyValueFactory<>("valorPagado"));
        tbcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }

    /**
     * Metodo que escucha la seleccion de funcion, tipo de cliente y venta para actualizar la vista
     */
    private void listenerSelection() {
        cmbFuncion.valueProperty().addListener((obs, anterior, nueva) -> {
            actualizarAsientos();
            actualizarValor();
        });
        cmbTipoCliente.valueProperty().addListener((obs, anterior, nuevo) -> actualizarValor());

        tblListVenta.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccionada) -> {
            if (seleccionada != null) {
                txtCodigoVenta.setText(seleccionada.getCodigo());
            }
        });
    }

    /**
     * Metodo que recarga las funciones y las ventas desde el modelo
     */
    public void refrescar() {
        listFunciones.setAll(ventaController.obtenerListaFunciones());
        cmbFuncion.setValue(null);
        actualizarVentas();
        actualizarAsientos();
        actualizarValor();
    }

    /**
     * Metodo que recarga la tabla de ventas desde el modelo
     */
    private void actualizarVentas() {
        listVentas.setAll(ventaController.obtenerListaVentas());
        tblListVenta.refresh();
    }

    /**
     * Metodo que dibuja el mapa de asientos de la funcion seleccionada, en verde los libres y en rojo los ocupados
     */
    private void actualizarAsientos() {
        fpAsientos.getChildren().clear();
        Funcion funcion = cmbFuncion.getValue();
        if (funcion == null) {
            lblDisponibilidad.setText("Seleccione una funcion para ver sus asientos.");
            return;
        }
        for (Asiento asiento : funcion.getAsientos()) {
            Button boton = new Button(asiento.getCodigo());
            boton.setPrefWidth(55);
            boton.setStyle(asiento.estaDisponible() ? ESTILO_LIBRE : ESTILO_OCUPADO);
            boton.setOnAction(e -> txtCodigoAsiento.setText(asiento.getCodigo()));
            fpAsientos.getChildren().add(boton);
        }
        lblDisponibilidad.setText(funcion.getSala().getNombre()
                + " | Total de asientos: " + funcion.getSala().getCapacidad()
                + " | Disponibles: " + funcion.contarAsientosDisponibles()
                + " | Ocupados: " + funcion.contarAsientosOcupados());
    }

    /**
     * Metodo que calcula y muestra el valor a pagar segun la funcion y el tipo de cliente seleccionados
     */
    private void actualizarValor() {
        Funcion funcion = cmbFuncion.getValue();
        TipoCliente tipo = cmbTipoCliente.getValue();
        if (funcion == null || tipo == null) {
            lblValor.setText("Valor a pagar: -");
            return;
        }
        double valor = ventaController.calcularValorEntrada(funcion, tipo);
        lblValor.setText("Valor a pagar: $" + String.format("%,.0f", valor));
    }

    /**
     * Metodo que valida los datos del formulario y vende una entrada
     */
    private void venderEntrada() {
        String codigoVenta = txtCodigoVenta.getText().trim();
        String idCliente = txtIdCliente.getText().trim();
        String nombreCliente = txtNombreCliente.getText().trim();
        String codigoAsiento = txtCodigoAsiento.getText().trim().toUpperCase();
        Funcion funcion = cmbFuncion.getValue();

        if (funcion == null || codigoVenta.isEmpty() || idCliente.isEmpty()
                || nombreCliente.isEmpty() || codigoAsiento.isEmpty()) {
            mostrarError("Debe seleccionar una funcion y diligenciar todos los campos.");
            return;
        }
        try {
            Cliente cliente = new Cliente(idCliente, nombreCliente, cmbTipoCliente.getValue());
            Venta venta = ventaController.venderEntrada(codigoVenta, funcion.getCodigo(), cliente, codigoAsiento);
            actualizarVentas();
            actualizarAsientos();
            limpiarCampos();

            Alert entrada = new Alert(Alert.AlertType.INFORMATION, construirEntrada(venta));
            entrada.setHeaderText("Entrada vendida");
            entrada.showAndWait();
        } catch (FuncionNoEncontradaException | FuncionNoProgramadaException | SinAsientosDisponiblesException
                 | AsientoOcupadoException | VentaDuplicadaException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Metodo que cancela la venta cuyo codigo esta en el formulario y libera su asiento
     */
    private void cancelarVenta() {
        String codigoVenta = txtCodigoVenta.getText().trim();
        if (codigoVenta.isEmpty()) {
            mostrarError("Escriba el codigo de la venta o seleccionela en la tabla.");
            return;
        }
        try {
            ventaController.cancelarVenta(codigoVenta);
            actualizarVentas();
            actualizarAsientos();
            limpiarCampos();
            mostrarInfo("Venta cancelada y asiento liberado.");
        } catch (VentaNoEncontradaException | VentaYaCanceladaException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Metodo que arma la informacion de la entrada que se muestra al concretar la venta
     * @param venta de la cual se construye la informacion
     * @return texto con la informacion de la entrada
     */
    private String construirEntrada(Venta venta) {
        Funcion funcion = venta.getFuncion();
        return "Venta: " + venta.getCodigo() + "\n"
                + "Pelicula: " + funcion.getPelicula().getNombre() + "\n"
                + "Sala: " + funcion.getSala().getNombre() + "\n"
                + "Funcion: " + funcion.getFecha() + " " + funcion.getHora() + "\n"
                + "Asiento: " + venta.getAsiento().getCodigo() + "\n"
                + "Cliente: " + venta.getCliente().getNombre() + " (" + venta.getCliente().getTipoCliente() + ")\n"
                + "Valor pagado: $" + String.format("%,.0f", venta.getValorPagado()) + "\n"
                + "Fecha y hora de venta: " + venta.getFechaHoraVenta().format(FORMATO_FECHA_HORA);
    }

    /**
     * Metodo que limpia los campos del formulario
     */
    private void limpiarCampos() {
        txtCodigoVenta.clear();
        txtIdCliente.clear();
        txtNombreCliente.clear();
        txtCodigoAsiento.clear();
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