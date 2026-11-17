package co.edu.uniquindio.cinemauq.viewController;

import co.edu.uniquindio.cinemauq.App;
import co.edu.uniquindio.cinemauq.controller.FuncionController;
import co.edu.uniquindio.cinemauq.excepciones.PeliculaNoEncontradaException;
import co.edu.uniquindio.cinemauq.excepciones.SalaNoDisponibleException;
import co.edu.uniquindio.cinemauq.excepciones.SalaNoEncontradaException;
import co.edu.uniquindio.cinemauq.modelo.EstadoFuncion;
import co.edu.uniquindio.cinemauq.modelo.Funcion;
import co.edu.uniquindio.cinemauq.modelo.Pelicula;
import co.edu.uniquindio.cinemauq.modelo.Sala;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import co.edu.uniquindio.cinemauq.excepciones.FuncionDuplicadaException;
import co.edu.uniquindio.cinemauq.excepciones.PeliculaNoDisponibleException;
import co.edu.uniquindio.cinemauq.excepciones.SalaOcupadaException;
import co.edu.uniquindio.cinemauq.excepciones.FuncionNoEncontradaException;
import co.edu.uniquindio.cinemauq.excepciones.FuncionNoProgramadaException;
import javafx.scene.control.ButtonType;
import java.util.Optional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class FuncionViewController {

    FuncionController funcionController;
    ObservableList<Funcion> listFunciones = FXCollections.observableArrayList();
    ObservableList<Pelicula> listPeliculas = FXCollections.observableArrayList();
    ObservableList<Sala> listSalas = FXCollections.observableArrayList();

    @FXML
    private TextField txtCodigo;

    @FXML
    private ComboBox<Pelicula> cmbPelicula;

    @FXML
    private ComboBox<Sala> cmbSala;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private TextField txtHora;

    @FXML
    private TextField txtValorBase;

    @FXML
    private DatePicker dpFechaConsulta;

    @FXML
    private TableView<Funcion> tblListFuncion;

    @FXML
    private TableColumn<Funcion, String> tbcCodigo;

    @FXML
    private TableColumn<Funcion, String> tbcPelicula;

    @FXML
    private TableColumn<Funcion, String> tbcSala;

    @FXML
    private TableColumn<Funcion, LocalDate> tbcFecha;

    @FXML
    private TableColumn<Funcion, LocalTime> tbcHora;

    @FXML
    private TableColumn<Funcion, Double> tbcValorBase;

    @FXML
    private TableColumn<Funcion, EstadoFuncion> tbcEstado;

    @FXML
    private TableColumn<Funcion, Integer> tbcDisponibles;

    /**
     * Metodo que atiende el evento del boton para programar una funcion
     */
    @FXML
    void onProgramarFuncion() {
        programarFuncion();
    }

    /**
     * Metodo que atiende el evento del boton para limpiar los campos del formulario
     */
    @FXML
    void onLimpiar() {
        limpiarCampos();
    }

    /**
     * Metodo que atiende el evento del boton para consultar las funciones de una fecha
     */
    @FXML
    void onConsultarPorFecha() {
        consultarPorFecha();
    }

    /**
     * Metodo que atiende el evento del boton para mostrar todas las funciones
     */
    @FXML
    void onVerTodas() {
        listFunciones.setAll(funcionController.obtenerListaFunciones());
        tblListFuncion.refresh();
    }

    /**
     * Metodo que inicializa el controlador de la vista de funciones
     */
    @FXML
    void initialize() {
        funcionController = new FuncionController(App.cinema);
        initView();
    }

    /**
     * Metodo que atiende el evento del boton para cancelar una funcion
     */
    @FXML
    void onCancelarFuncion() {
        cancelarFuncion();
    }

    /**
     * Metodo que atiende el evento del boton para finalizar una funcion
     */
    @FXML
    void onFinalizarFuncion() {
        finalizarFuncion();
    }

    /**
     * Metodo que prepara los componentes de la vista con sus valores iniciales
     */
    private void initView() {
        initDataBinding();
        cmbPelicula.setItems(listPeliculas);
        cmbSala.setItems(listSalas);
        tblListFuncion.setItems(listFunciones);
        dpFecha.setValue(LocalDate.now());
        dpFechaConsulta.setValue(LocalDate.now());
        txtHora.setText("19:00");
        refrescar();
    }

    /**
     * Metodo que enlaza las columnas de la tabla con los datos de las funciones
     */
    private void initDataBinding() {
        tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tbcPelicula.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getPelicula().getNombre()));
        tbcSala.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getSala().getNombre()));
        tbcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tbcHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        tbcValorBase.setCellValueFactory(new PropertyValueFactory<>("valorBase"));
        tbcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tbcDisponibles.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().contarAsientosDisponibles()));
    }

    /**
     * Metodo que recarga los combos y la tabla desde el modelo
     */
    public void refrescar() {
        listPeliculas.setAll(funcionController.obtenerPeliculasDisponibles());
        listSalas.setAll(funcionController.obtenerListaSalas());
        listFunciones.setAll(funcionController.obtenerListaFunciones());
        cmbPelicula.setValue(null);
        cmbSala.setValue(null);
        tblListFuncion.refresh();
    }

    /**
     * Metodo que valida los datos del formulario y programa una funcion
     */
    private void programarFuncion() {
        String codigo = txtCodigo.getText().trim();
        String textoValor = txtValorBase.getText().trim();
        if (codigo.isEmpty() || cmbPelicula.getValue() == null || cmbSala.getValue() == null
                || dpFecha.getValue() == null || txtHora.getText().trim().isEmpty() || textoValor.isEmpty()) {
            mostrarError("Debe diligenciar todos los campos.");
            return;
        }
        if (textoValor.contains(".") || textoValor.contains(",")) {
            mostrarError("Escriba el valor base sin puntos ni comas, por ejemplo 15000.");
            return;
        }
        try {
            double valorBase = Double.parseDouble(textoValor);
            if (valorBase <= 0) {
                mostrarError("El valor base debe ser mayor que cero.");
                return;
            }
            LocalTime hora = LocalTime.parse(txtHora.getText().trim());
            funcionController.programarFuncion(codigo, cmbPelicula.getValue().getCodigo(),
                    cmbSala.getValue().getCodigo(), dpFecha.getValue(), hora, valorBase);
            refrescar();
            limpiarCampos();
            mostrarInfo("Funcion programada correctamente.");
        } catch (NumberFormatException e) {
            mostrarError("El valor base debe ser un numero, por ejemplo 15000.");
        } catch (DateTimeParseException e) {
            mostrarError("La hora debe tener el formato HH:mm, por ejemplo 19:30.");
        } catch (PeliculaNoEncontradaException | SalaNoEncontradaException | SalaNoDisponibleException
                 | PeliculaNoDisponibleException | FuncionDuplicadaException | SalaOcupadaException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Metodo que muestra en la tabla las funciones programadas en la fecha seleccionada
     */
    private void consultarPorFecha() {
        if (dpFechaConsulta.getValue() == null) {
            mostrarError("Seleccione la fecha a consultar.");
            return;
        }
        ArrayList<Funcion> resultado = funcionController.consultarFuncionesProgramadasPorFecha(dpFechaConsulta.getValue());
        listFunciones.setAll(resultado);
        if (resultado.isEmpty()) {
            mostrarInfo("No hay funciones programadas para esa fecha.");
        }
    }

    /**
     * Metodo que cancela la funcion seleccionada y sus ventas activas previa confirmacion
     */
    private void cancelarFuncion() {
        Funcion funcion = tblListFuncion.getSelectionModel().getSelectedItem();
        if (funcion == null) {
            mostrarError("Seleccione una funcion de la tabla.");
            return;
        }
        if (!funcion.estaProgramada()) {
            mostrarError("La funcion " + funcion.getCodigo() + " no esta programada (estado " + funcion.getEstado() + ").");
            return;
        }
        int ventasActivas = funcionController.contarVentasActivas(funcion.getCodigo());
        boolean confirmado = confirmar("Se cancelara la funcion " + funcion.getCodigo() + " ("
                + funcion.getPelicula().getNombre() + ").\nSe cancelaran " + ventasActivas
                + " ventas activas y se liberaran sus asientos.");
        if (!confirmado) {
            return;
        }
        try {
            int canceladas = funcionController.cancelarFuncion(funcion.getCodigo());
            refrescar();
            mostrarInfo("Funcion cancelada. Ventas canceladas: " + canceladas + ".");
        } catch (FuncionNoEncontradaException | FuncionNoProgramadaException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Metodo que finaliza la funcion seleccionada previa confirmacion
     */
    private void finalizarFuncion() {
        Funcion funcion = tblListFuncion.getSelectionModel().getSelectedItem();
        if (funcion == null) {
            mostrarError("Seleccione una funcion de la tabla.");
            return;
        }
        if (!funcion.estaProgramada()) {
            mostrarError("La funcion " + funcion.getCodigo() + " no esta programada (estado " + funcion.getEstado() + ").");
            return;
        }
        boolean confirmado = confirmar("Se finalizara la funcion " + funcion.getCodigo() + " ("
                + funcion.getPelicula().getNombre() + ").\nSus ventas se conservan, pero no se podran vender mas entradas.");
        if (!confirmado) {
            return;
        }
        try {
            funcionController.finalizarFuncion(funcion.getCodigo());
            refrescar();
            mostrarInfo("Funcion finalizada.");
        } catch (FuncionNoEncontradaException | FuncionNoProgramadaException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Metodo que muestra una alerta de confirmacion al usuario
     * @param mensaje que se muestra en la alerta
     * @return true si el usuario acepta, false en caso contrario
     */
    private boolean confirmar(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, mensaje);
        alerta.setHeaderText("Confirmar accion");
        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    /**
     * Metodo que limpia los campos del formulario y restablece sus valores por defecto
     */
    private void limpiarCampos() {
        txtCodigo.clear();
        cmbPelicula.setValue(null);
        cmbSala.setValue(null);
        dpFecha.setValue(LocalDate.now());
        txtHora.setText("19:00");
        txtValorBase.clear();
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