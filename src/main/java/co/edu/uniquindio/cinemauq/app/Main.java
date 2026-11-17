package co.edu.uniquindio.cinemauq.app;

import co.edu.uniquindio.cinemauq.excepciones.*;
import co.edu.uniquindio.cinemauq.modelo.*;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Interfaz grafica de CinemaUQ. Esta ventana implementa las funcionalidades
 * de operador (venta y cancelacion de entradas, consultas) y de
 * administrador (gestion de peliculas, salas, funciones y reportes)
 * descritas en el documento del proyecto, organizadas en pestanas.
 *
 * <p>El estado del sistema vive en una sola instancia de {@link CinemaUQ},
 * compartida por todas las pestanas, y se recarga con datos de ejemplo al
 * iniciar la aplicacion para facilitar las pruebas en clase.</p>
 */
public class Main extends Application {

    private final CinemaUQ cinema = new CinemaUQ("CinemaUQ");
    private final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final ObservableList<Pelicula> datosPeliculas = FXCollections.observableArrayList();
    private final ObservableList<Sala> datosSalas = FXCollections.observableArrayList();
    private final ObservableList<Funcion> datosFunciones = FXCollections.observableArrayList();
    private final ObservableList<Venta> datosVentas = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        cargarDatosDeEjemplo();

        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(new Tab("Peliculas", construirPestanaPeliculas()));
        tabPane.getTabs().add(new Tab("Salas", construirPestanaSalas()));
        tabPane.getTabs().add(new Tab("Funciones", construirPestanaFunciones()));
        tabPane.getTabs().add(new Tab("Venta de entradas", construirPestanaVentas()));
        tabPane.getTabs().add(new Tab("Reportes", construirPestanaReportes()));
        tabPane.getTabs().forEach(t -> t.setClosable(false));

        BorderPane raiz = new BorderPane(tabPane);
        Scene escena = new Scene(raiz, 980, 650);
        stage.setTitle("CinemaUQ - Sistema de gestion de cine");
        stage.setScene(escena);
        stage.show();
    }

    /** Carga peliculas, salas y una funcion de ejemplo para no arrancar con la aplicacion vacia. */
    private void cargarDatosDeEjemplo() {
        try {
            cinema.registrarPelicula(new Pelicula("PEL01", "Avatar: El camino del agua", "Ciencia ficcion", 192, Clasificacion.MAYORES_12));
            cinema.registrarPelicula(new Pelicula("PEL02", "Intensamente 2", "Animacion", 100, Clasificacion.TODOS));
            cinema.registrarSala(new Sala("SAL01", "Sala 1", 30));
            cinema.registrarSala(new Sala("SAL02", "Sala 2 (3D)", 20));
            cinema.programarFuncion("FUN01", "PEL01", "SAL01", LocalDate.now().plusDays(1), LocalTime.of(19, 30), 15000.0);
        } catch (Exception e) {
            // Datos de ejemplo: si algo falla aqui no se debe detener el arranque de la app.
        }
        refrescarTodo();
    }

    private void refrescarTodo() {
        datosPeliculas.setAll(cinema.listarPeliculas());
        datosSalas.setAll(cinema.listarSalas());
        datosFunciones.setAll(cinema.listarFunciones());
        datosVentas.setAll(cinema.listarVentas());
    }

    // ================== Pestana: Peliculas ==================

    private Node construirPestanaPeliculas() {
        TextField txtCodigo = new TextField();
        TextField txtNombre = new TextField();
        TextField txtGenero = new TextField();
        TextField txtDuracion = new TextField();
        ComboBox<Clasificacion> cmbClasificacion = new ComboBox<>(FXCollections.observableArrayList(Clasificacion.values()));
        cmbClasificacion.getSelectionModel().selectFirst();

        GridPane formulario = new GridPane();
        formulario.setHgap(8);
        formulario.setVgap(6);
        formulario.setPadding(new Insets(10));
        formulario.addRow(0, new Label("Codigo:"), txtCodigo);
        formulario.addRow(1, new Label("Nombre:"), txtNombre);
        formulario.addRow(2, new Label("Genero:"), txtGenero);
        formulario.addRow(3, new Label("Duracion (min):"), txtDuracion);
        formulario.addRow(4, new Label("Clasificacion:"), cmbClasificacion);

        TableView<Pelicula> tabla = new TableView<>(datosPeliculas);
        TableColumn<Pelicula, String> colCodigo = new TableColumn<>("Codigo");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        TableColumn<Pelicula, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TableColumn<Pelicula, String> colGenero = new TableColumn<>("Genero");
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        TableColumn<Pelicula, Integer> colDuracion = new TableColumn<>("Duracion");
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracionMinutos"));
        TableColumn<Pelicula, Clasificacion> colClasificacion = new TableColumn<>("Clasificacion");
        colClasificacion.setCellValueFactory(new PropertyValueFactory<>("clasificacion"));
        TableColumn<Pelicula, EstadoPelicula> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tabla.getColumns().addAll(colCodigo, colNombre, colGenero, colDuracion, colClasificacion, colEstado);

        tabla.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccionada) -> {
            if (seleccionada != null) {
                txtCodigo.setText(seleccionada.getCodigo());
                txtNombre.setText(seleccionada.getNombre());
                txtGenero.setText(seleccionada.getGenero());
                txtDuracion.setText(String.valueOf(seleccionada.getDuracionMinutos()));
                cmbClasificacion.getSelectionModel().select(seleccionada.getClasificacion());
            }
        });

        Button btnRegistrar = new Button("Registrar");
        btnRegistrar.setOnAction(e -> {
            try {
                int duracion = Integer.parseInt(txtDuracion.getText().trim());
                Pelicula pelicula = new Pelicula(txtCodigo.getText().trim(), txtNombre.getText().trim(),
                        txtGenero.getText().trim(), duracion, cmbClasificacion.getValue());
                cinema.registrarPelicula(pelicula);
                refrescarTodo();
                mostrarInfo("Pelicula registrada correctamente.");
            } catch (NumberFormatException ex) {
                mostrarError("La duracion debe ser un numero entero de minutos.");
            } catch (PeliculaDuplicadaException ex) {
                mostrarError(ex.getMessage());
            }
        });

        Button btnModificar = new Button("Modificar");
        btnModificar.setOnAction(e -> {
            try {
                int duracion = Integer.parseInt(txtDuracion.getText().trim());
                cinema.modificarPelicula(txtCodigo.getText().trim(), txtNombre.getText().trim(),
                        txtGenero.getText().trim(), duracion, cmbClasificacion.getValue());
                refrescarTodo();
                mostrarInfo("Pelicula modificada correctamente.");
            } catch (NumberFormatException ex) {
                mostrarError("La duracion debe ser un numero entero de minutos.");
            } catch (PeliculaNoEncontradaException ex) {
                mostrarError(ex.getMessage());
            }
        });

        Button btnDeshabilitar = new Button("Deshabilitar");
        btnDeshabilitar.setOnAction(e -> {
            try {
                cinema.deshabilitarPelicula(txtCodigo.getText().trim());
                refrescarTodo();
                mostrarInfo("Pelicula deshabilitada.");
            } catch (PeliculaNoEncontradaException ex) {
                mostrarError(ex.getMessage());
            }
        });

        HBox botones = new HBox(10, btnRegistrar, btnModificar, btnDeshabilitar);
        botones.setPadding(new Insets(0, 10, 10, 10));

        VBox contenedor = new VBox(formulario, botones, tabla);
        VBox.setVgrow(tabla, Priority.ALWAYS);
        return contenedor;
    }

    // ================== Pestana: Salas ==================

    private Node construirPestanaSalas() {
        TextField txtCodigo = new TextField();
        TextField txtNombre = new TextField();
        TextField txtCapacidad = new TextField();

        GridPane formulario = new GridPane();
        formulario.setHgap(8);
        formulario.setVgap(6);
        formulario.setPadding(new Insets(10));
        formulario.addRow(0, new Label("Codigo:"), txtCodigo);
        formulario.addRow(1, new Label("Nombre:"), txtNombre);
        formulario.addRow(2, new Label("Capacidad:"), txtCapacidad);

        TableView<Sala> tabla = new TableView<>(datosSalas);
        TableColumn<Sala, String> colCodigo = new TableColumn<>("Codigo");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        TableColumn<Sala, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TableColumn<Sala, Integer> colCapacidad = new TableColumn<>("Capacidad");
        colCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        TableColumn<Sala, EstadoSala> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        TableColumn<Sala, Integer> colDisponibles = new TableColumn<>("Disponibles");
        colDisponibles.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().contarAsientosDisponibles()));
        TableColumn<Sala, Integer> colOcupados = new TableColumn<>("Ocupados");
        colOcupados.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().contarAsientosOcupados()));
        tabla.getColumns().addAll(colCodigo, colNombre, colCapacidad, colEstado, colDisponibles, colOcupados);

        tabla.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccionada) -> {
            if (seleccionada != null) {
                txtCodigo.setText(seleccionada.getCodigo());
                txtNombre.setText(seleccionada.getNombre());
                txtCapacidad.setText(String.valueOf(seleccionada.getCapacidad()));
            }
        });

        Button btnRegistrar = new Button("Registrar");
        btnRegistrar.setOnAction(e -> {
            try {
                int capacidad = Integer.parseInt(txtCapacidad.getText().trim());
                Sala sala = new Sala(txtCodigo.getText().trim(), txtNombre.getText().trim(), capacidad);
                cinema.registrarSala(sala);
                refrescarTodo();
                mostrarInfo("Sala registrada correctamente (se generaron " + capacidad + " asientos).");
            } catch (NumberFormatException ex) {
                mostrarError("La capacidad debe ser un numero entero.");
            } catch (SalaDuplicadaException ex) {
                mostrarError(ex.getMessage());
            }
        });

        Button btnModificarNombre = new Button("Modificar nombre");
        btnModificarNombre.setOnAction(e -> {
            try {
                cinema.modificarSala(txtCodigo.getText().trim(), txtNombre.getText().trim());
                refrescarTodo();
                mostrarInfo("Sala modificada correctamente.");
            } catch (SalaNoEncontradaException ex) {
                mostrarError(ex.getMessage());
            }
        });

        Button btnDeshabilitar = new Button("Deshabilitar");
        btnDeshabilitar.setOnAction(e -> {
            try {
                cinema.deshabilitarSala(txtCodigo.getText().trim());
                refrescarTodo();
                mostrarInfo("Sala deshabilitada.");
            } catch (SalaNoEncontradaException ex) {
                mostrarError(ex.getMessage());
            }
        });

        HBox botones = new HBox(10, btnRegistrar, btnModificarNombre, btnDeshabilitar);
        botones.setPadding(new Insets(0, 10, 10, 10));

        VBox contenedor = new VBox(formulario, botones, tabla);
        VBox.setVgrow(tabla, Priority.ALWAYS);
        return contenedor;
    }

    // ================== Pestana: Funciones ==================

    private Node construirPestanaFunciones() {
        TextField txtCodigo = new TextField();
        ComboBox<Pelicula> cmbPelicula = new ComboBox<>(datosPeliculas);
        ComboBox<Sala> cmbSala = new ComboBox<>(datosSalas);
        DatePicker fecha = new DatePicker(LocalDate.now());
        TextField txtHora = new TextField("19:00");
        TextField txtValorBase = new TextField();

        GridPane formulario = new GridPane();
        formulario.setHgap(8);
        formulario.setVgap(6);
        formulario.setPadding(new Insets(10));
        formulario.addRow(0, new Label("Codigo:"), txtCodigo);
        formulario.addRow(1, new Label("Pelicula:"), cmbPelicula);
        formulario.addRow(2, new Label("Sala:"), cmbSala);
        formulario.addRow(3, new Label("Fecha:"), fecha);
        formulario.addRow(4, new Label("Hora (HH:mm):"), txtHora);
        formulario.addRow(5, new Label("Valor base:"), txtValorBase);

        TableView<Funcion> tabla = new TableView<>(datosFunciones);
        TableColumn<Funcion, String> colCodigo = new TableColumn<>("Codigo");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        TableColumn<Funcion, String> colPelicula = new TableColumn<>("Pelicula");
        colPelicula.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getPelicula().getNombre()));
        TableColumn<Funcion, String> colSala = new TableColumn<>("Sala");
        colSala.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getSala().getNombre()));
        TableColumn<Funcion, LocalDate> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        TableColumn<Funcion, LocalTime> colHora = new TableColumn<>("Hora");
        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        TableColumn<Funcion, Double> colValorBase = new TableColumn<>("Valor base");
        colValorBase.setCellValueFactory(new PropertyValueFactory<>("valorBase"));
        TableColumn<Funcion, EstadoFuncion> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        TableColumn<Funcion, Integer> colDisponibles = new TableColumn<>("Disponibles");
        colDisponibles.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().contarAsientosDisponibles()));
        tabla.getColumns().addAll(colCodigo, colPelicula, colSala, colFecha, colHora, colValorBase, colEstado, colDisponibles);

        Button btnProgramar = new Button("Programar funcion");
        btnProgramar.setOnAction(e -> {
            try {
                if (cmbPelicula.getValue() == null || cmbSala.getValue() == null) {
                    mostrarError("Debe seleccionar una pelicula y una sala.");
                    return;
                }
                double valorBase = Double.parseDouble(txtValorBase.getText().trim());
                LocalTime hora = LocalTime.parse(txtHora.getText().trim());
                cinema.programarFuncion(txtCodigo.getText().trim(), cmbPelicula.getValue().getCodigo(),
                        cmbSala.getValue().getCodigo(), fecha.getValue(), hora, valorBase);
                refrescarTodo();
                mostrarInfo("Funcion programada correctamente.");
            } catch (NumberFormatException ex) {
                mostrarError("El valor base debe ser un numero.");
            } catch (java.time.format.DateTimeParseException ex) {
                mostrarError("La hora debe tener el formato HH:mm, por ejemplo 19:30.");
            } catch (PeliculaNoEncontradaException | SalaNoEncontradaException | SalaNoDisponibleException ex) {
                mostrarError(ex.getMessage());
            }
        });

        DatePicker fechaConsulta = new DatePicker(LocalDate.now());
        Button btnConsultarPorFecha = new Button("Consultar funciones programadas de esta fecha");
        btnConsultarPorFecha.setOnAction(e -> {
            ArrayList<Funcion> resultado = cinema.consultarFuncionesProgramadasPorFecha(fechaConsulta.getValue());
            datosFunciones.setAll(resultado.isEmpty() ? cinema.listarFunciones() : resultado);
            if (resultado.isEmpty()) {
                mostrarInfo("No hay funciones programadas para esa fecha. Se muestra el listado completo.");
            }
        });
        Button btnVerTodas = new Button("Ver todas");
        btnVerTodas.setOnAction(e -> datosFunciones.setAll(cinema.listarFunciones()));

        HBox botones = new HBox(10, btnProgramar);
        botones.setPadding(new Insets(0, 10, 10, 10));
        HBox consulta = new HBox(10, new Label("Fecha a consultar:"), fechaConsulta, btnConsultarPorFecha, btnVerTodas);
        consulta.setPadding(new Insets(0, 10, 10, 10));
        consulta.setAlignment(Pos.CENTER_LEFT);

        VBox contenedor = new VBox(formulario, botones, consulta, tabla);
        VBox.setVgrow(tabla, Priority.ALWAYS);
        return contenedor;
    }

    // ================== Pestana: Venta de entradas ==================

    private Node construirPestanaVentas() {
        TextField txtIdCliente = new TextField();
        TextField txtNombreCliente = new TextField();
        ComboBox<TipoCliente> cmbTipoCliente = new ComboBox<>(FXCollections.observableArrayList(TipoCliente.values()));
        cmbTipoCliente.getSelectionModel().selectFirst();

        ComboBox<Funcion> cmbFuncion = new ComboBox<>(datosFunciones);
        TextField txtCodigoAsiento = new TextField();
        TextField txtCodigoVenta = new TextField();
        Label lblAsientosDisponibles = new Label("Seleccione una funcion para ver sus asientos.");

        cmbFuncion.valueProperty().addListener((obs, anterior, seleccionada) -> {
            if (seleccionada != null) {
                StringBuilder texto = new StringBuilder("Asientos de " + seleccionada.getSala().getNombre() + ": ");
                for (Asiento asiento : seleccionada.getSala().getAsientos()) {
                    texto.append(asiento.getCodigo()).append(asiento.estaDisponible() ? "(libre) " : "(ocupado) ");
                }
                lblAsientosDisponibles.setText(texto.toString());
            }
        });

        GridPane formularioCliente = new GridPane();
        formularioCliente.setHgap(8);
        formularioCliente.setVgap(6);
        formularioCliente.setPadding(new Insets(10));
        formularioCliente.addRow(0, new Label("Identificacion cliente:"), txtIdCliente);
        formularioCliente.addRow(1, new Label("Nombre cliente:"), txtNombreCliente);
        formularioCliente.addRow(2, new Label("Tipo de cliente:"), cmbTipoCliente);

        GridPane formularioVenta = new GridPane();
        formularioVenta.setHgap(8);
        formularioVenta.setVgap(6);
        formularioVenta.setPadding(new Insets(10));
        formularioVenta.addRow(0, new Label("Codigo de venta:"), txtCodigoVenta);
        formularioVenta.addRow(1, new Label("Funcion:"), cmbFuncion);
        formularioVenta.addRow(2, new Label("Asiento (ej. A1):"), txtCodigoAsiento);

        Button btnVender = new Button("Vender entrada");
        btnVender.setOnAction(e -> {
            try {
                if (cmbFuncion.getValue() == null) {
                    mostrarError("Debe seleccionar una funcion.");
                    return;
                }
                Cliente cliente = new Cliente(txtIdCliente.getText().trim(), txtNombreCliente.getText().trim(), cmbTipoCliente.getValue());
                Venta venta = cinema.venderEntrada(txtCodigoVenta.getText().trim(), cmbFuncion.getValue().getCodigo(),
                        cliente, txtCodigoAsiento.getText().trim().toUpperCase());
                refrescarTodo();
                mostrarInfo("Entrada vendida. Valor pagado: $" + (long) venta.getValorPagado());
            } catch (FuncionNoEncontradaException | SinAsientosDisponiblesException | AsientoOcupadoException ex) {
                mostrarError(ex.getMessage());
            }
        });

        Button btnCancelar = new Button("Cancelar venta");
        btnCancelar.setOnAction(e -> {
            try {
                cinema.cancelarVenta(txtCodigoVenta.getText().trim());
                refrescarTodo();
                mostrarInfo("Venta cancelada y asiento liberado.");
            } catch (VentaNoEncontradaException ex) {
                mostrarError(ex.getMessage());
            }
        });

        TableView<Venta> tabla = new TableView<>(datosVentas);
        TableColumn<Venta, String> colCodigo = new TableColumn<>("Codigo");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        TableColumn<Venta, String> colCliente = new TableColumn<>("Cliente");
        colCliente.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getCliente().getNombre()));
        TableColumn<Venta, String> colPelicula = new TableColumn<>("Pelicula");
        colPelicula.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getFuncion().getPelicula().getNombre()));
        TableColumn<Venta, String> colAsiento = new TableColumn<>("Asiento");
        colAsiento.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getAsiento().getCodigo()));
        TableColumn<Venta, Double> colValor = new TableColumn<>("Valor pagado");
        colValor.setCellValueFactory(new PropertyValueFactory<>("valorPagado"));
        TableColumn<Venta, EstadoVenta> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tabla.getColumns().addAll(colCodigo, colCliente, colPelicula, colAsiento, colValor, colEstado);

        HBox botones = new HBox(10, btnVender, btnCancelar);
        botones.setPadding(new Insets(0, 10, 10, 10));

        VBox contenedor = new VBox(formularioCliente, formularioVenta, lblAsientosDisponibles, botones, tabla);
        VBox.setVgrow(tabla, Priority.ALWAYS);
        return contenedor;
    }

    // ================== Pestana: Reportes ==================

    private Node construirPestanaReportes() {
        Label lblEntradasVendidas = new Label("Total entradas vendidas: -");
        Label lblIngresos = new Label("Total ingresos: -");
        Label lblMasVendida = new Label("Pelicula mas vendida: -");

        Button btnGenerar = new Button("Generar reportes generales");
        btnGenerar.setOnAction(e -> {
            lblEntradasVendidas.setText("Total entradas vendidas: " + cinema.totalEntradasVendidas());
            lblIngresos.setText("Total ingresos: $" + (long) cinema.totalIngresos());
            Pelicula mejor = cinema.peliculaConMasVentas();
            lblMasVendida.setText("Pelicula mas vendida: " + (mejor == null ? "sin datos" : mejor.getNombre()));
        });

        VBox panelGenerales = new VBox(8, btnGenerar, lblEntradasVendidas, lblIngresos, lblMasVendida);
        panelGenerales.setPadding(new Insets(10));

        ComboBox<Funcion> cmbFuncion = new ComboBox<>(datosFunciones);
        Label lblOcupacion = new Label("Seleccione una funcion y consulte su ocupacion.");
        Button btnOcupacion = new Button("Consultar ocupacion");
        btnOcupacion.setOnAction(e -> {
            try {
                if (cmbFuncion.getValue() == null) {
                    mostrarError("Debe seleccionar una funcion.");
                    return;
                }
                Funcion funcion = cmbFuncion.getValue();
                double porcentaje = cinema.porcentajeOcupacion(funcion.getCodigo());
                lblOcupacion.setText(String.format("Disponibles: %d | Ocupados: %d | Ocupacion: %.1f%%",
                        funcion.contarAsientosDisponibles(), funcion.contarAsientosOcupados(), porcentaje));
            } catch (FuncionNoEncontradaException ex) {
                mostrarError(ex.getMessage());
            }
        });
        HBox filaOcupacion = new HBox(10, new Label("Funcion:"), cmbFuncion, btnOcupacion);
        filaOcupacion.setAlignment(Pos.CENTER_LEFT);
        VBox panelOcupacion = new VBox(8, filaOcupacion, lblOcupacion);
        panelOcupacion.setPadding(new Insets(10));

        DatePicker fechaConsulta = new DatePicker(LocalDate.now());
        TableView<Funcion> tablaFuncionesPorFecha = new TableView<>();
        TableColumn<Funcion, String> colCodigo = new TableColumn<>("Codigo");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        TableColumn<Funcion, String> colPelicula = new TableColumn<>("Pelicula");
        colPelicula.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getPelicula().getNombre()));
        TableColumn<Funcion, LocalTime> colHora = new TableColumn<>("Hora");
        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        tablaFuncionesPorFecha.getColumns().addAll(colCodigo, colPelicula, colHora);
        Button btnBuscarPorFecha = new Button("Funciones programadas por fecha");
        btnBuscarPorFecha.setOnAction(e ->
                tablaFuncionesPorFecha.setItems(FXCollections.observableArrayList(
                        cinema.consultarFuncionesProgramadasPorFecha(fechaConsulta.getValue()))));
        HBox filaFecha = new HBox(10, new Label("Fecha:"), fechaConsulta, btnBuscarPorFecha);
        filaFecha.setAlignment(Pos.CENTER_LEFT);
        VBox panelFecha = new VBox(8, filaFecha, tablaFuncionesPorFecha);
        panelFecha.setPadding(new Insets(10));

        VBox contenedor = new VBox(15, panelGenerales, new Separator(), panelOcupacion, new Separator(), panelFecha);
        return contenedor;
    }

    // ================== Utilidades ==================

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

    public static void main(String[] args) {
        launch(args);
    }
}
