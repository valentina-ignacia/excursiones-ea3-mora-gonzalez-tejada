package cl.duoc.excursiones.controller;

import cl.duoc.excursiones.dao.ExcursionDAO;
import cl.duoc.excursiones.dao.PersistenciaException;
import cl.duoc.excursiones.model.Excursion;
import cl.duoc.excursiones.model.Kayak;
import cl.duoc.excursiones.model.Trekking;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Locale;

/**
 * Controlador de la ventana principal: formulario, tabla y área de mensajes.
 * Accede a los datos únicamente a través de la interfaz ExcursionDAO.
 */
public class MainController {

    // =====================================================================
    // EA3: asigna aquí tu implementación de ExcursionDAO
    //      (por ejemplo: = new MiImplementacionDAO();).
    //      No es necesario modificar ninguna otra parte de esta clase.
    // =====================================================================
    private final ExcursionDAO dao = null;

    private static final Locale LOCALE_CL = Locale.forLanguageTag("es-CL");

    @FXML private ComboBox<String> cbTipo;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtParticipantes;
    @FXML private TextField txtDistanciaKm;
    @FXML private TextField txtDesnivelMetros;
    @FXML private CheckBox chkGuiaBilingue;
    @FXML private CheckBox chkSeguroIncluido;
    @FXML private TextField txtDuracionHoras;
    @FXML private Button btnRegistrar;
    @FXML private Button btnCancelar;

    @FXML private TableView<Excursion> tabla;
    @FXML private TableColumn<Excursion, Integer> colId;
    @FXML private TableColumn<Excursion, String> colTipo;
    @FXML private TableColumn<Excursion, String> colCodigo;
    @FXML private TableColumn<Excursion, Integer> colParticipantes;
    @FXML private TableColumn<Excursion, Double> colDistancia;
    @FXML private TableColumn<Excursion, String> colValorPersona;
    @FXML private TableColumn<Excursion, String> colValorTotal;

    @FXML private Label lblMensaje;

    /** Excursión que se está editando; null cuando el formulario registra una nueva. */
    private Excursion enEdicion;

    @FXML
    private void initialize() {
        cbTipo.setItems(FXCollections.observableArrayList(Trekking.TIPO, Kayak.TIPO));
        cbTipo.valueProperty().addListener((obs, anterior, actual) -> actualizarCamposPorTipo());

        colId.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getId()));
        colTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipo()));
        colCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        colParticipantes.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getParticipantes()));
        colDistancia.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getDistanciaKm()));
        colValorPersona.setCellValueFactory(c ->
                new SimpleStringProperty(formatearMonto(c.getValue().calcularValorPersona())));
        colValorTotal.setCellValueFactory(c ->
                new SimpleStringProperty(formatearMonto(c.getValue().calcularValorTotal())));
        tabla.setPlaceholder(new Label("No hay excursiones para mostrar."));

        limpiarFormulario();
        cargarTabla();
    }

    // ---------------------------------------------------------------- acciones

    @FXML
    private void onRegistrar() {
        if (!hayDao()) {
            return;
        }
        Excursion excursion;
        try {
            excursion = crearDesdeFormulario();
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
            return;
        }

        try {
            if (enEdicion == null) {
                dao.insertar(excursion);
                cargarTabla();
                limpiarFormulario();
                mostrarExito(describir(excursion) + " registrada correctamente.");
            } else {
                excursion.setId(enEdicion.getId());
                dao.actualizar(excursion);
                cargarTabla();
                limpiarFormulario();
                mostrarExito(describir(excursion) + " actualizada correctamente.");
            }
        } catch (PersistenciaException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    private void onEditar() {
        Excursion seleccionada = tabla.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarError("Selecciona una excursión de la tabla para editar.");
            return;
        }
        enEdicion = seleccionada;
        cbTipo.setValue(seleccionada.getTipo());
        cbTipo.setDisable(true);
        txtCodigo.setText(seleccionada.getCodigo());
        txtParticipantes.setText(String.valueOf(seleccionada.getParticipantes()));
        txtDistanciaKm.setText(String.valueOf(seleccionada.getDistanciaKm()));
        if (seleccionada instanceof Trekking trekking) {
            txtDesnivelMetros.setText(String.valueOf(trekking.getDesnivelMetros()));
            chkGuiaBilingue.setSelected(trekking.isGuiaBilingue());
            chkSeguroIncluido.setSelected(trekking.isSeguroIncluido());
        } else if (seleccionada instanceof Kayak kayak) {
            txtDuracionHoras.setText(String.valueOf(kayak.getDuracionHoras()));
        }
        btnRegistrar.setText("Guardar cambios");
        btnCancelar.setDisable(false);
        txtCodigo.requestFocus();
        mostrarInfo("Editando " + describir(seleccionada)
                + ". Modifica los datos y presiona Guardar cambios.");
    }

    @FXML
    private void onCancelar() {
        limpiarFormulario();
        mostrarInfo("Edición cancelada.");
    }

    @FXML
    private void onEliminar() {
        if (!hayDao()) {
            return;
        }
        Excursion seleccionada = tabla.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarError("Selecciona una excursión de la tabla para eliminar.");
            return;
        }
        try {
            dao.eliminar(seleccionada.getId());
            if (enEdicion != null && enEdicion.getId() == seleccionada.getId()) {
                limpiarFormulario();
            }
            cargarTabla();
            mostrarExito(describir(seleccionada) + " eliminada correctamente.");
        } catch (PersistenciaException e) {
            mostrarError(e.getMessage());
        }
    }

    // ---------------------------------------------------------------- apoyo

    /** Vuelve a leer todas las excursiones desde el almacenamiento y las muestra en la tabla. */
    private void cargarTabla() {
        if (!hayDao()) {
            tabla.getItems().clear();
            return;
        }
        try {
            tabla.setItems(FXCollections.observableArrayList(dao.obtenerTodos()));
        } catch (PersistenciaException e) {
            tabla.getItems().clear();
            mostrarError("Error al leer la información: " + e.getMessage());
        }
    }

    private boolean hayDao() {
        if (dao == null) {
            mostrarError("Aún no hay una implementación de ExcursionDAO asignada en MainController.");
            return false;
        }
        return true;
    }

    private Excursion crearDesdeFormulario() {
        String tipo = cbTipo.getValue();
        if (tipo == null) {
            throw new IllegalArgumentException("Selecciona el tipo.");
        }
        String codigo = txtCodigo.getText();
        int participantes = leerEntero(txtParticipantes.getText(), "Participantes: debe ser un número entero.");
        double distanciaKm = leerDecimal(txtDistanciaKm.getText(), "Distancia: debe ser un número.");

        if (Trekking.TIPO.equals(tipo)) {
            return new Trekking(codigo, participantes, distanciaKm,
                    leerDecimal(txtDesnivelMetros.getText(), "Desnivel: debe ser un número."),
                    chkGuiaBilingue.isSelected(), chkSeguroIncluido.isSelected());
        }
        return new Kayak(codigo, participantes, distanciaKm,
                leerDecimal(txtDuracionHoras.getText(), "Duración: debe ser un número."));
    }

    private int leerEntero(String texto, String mensajeError) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(mensajeError);
        }
    }

    private double leerDecimal(String texto, String mensajeError) {
        try {
            return Double.parseDouble(texto.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(mensajeError);
        }
    }

    private void actualizarCamposPorTipo() {
        boolean esTrekking = Trekking.TIPO.equals(cbTipo.getValue());
        boolean esKayak = Kayak.TIPO.equals(cbTipo.getValue());
        txtDesnivelMetros.setDisable(!esTrekking);
        chkGuiaBilingue.setDisable(!esTrekking);
        chkSeguroIncluido.setDisable(!esTrekking);
        txtDuracionHoras.setDisable(!esKayak);
    }

    private void limpiarFormulario() {
        enEdicion = null;
        cbTipo.setDisable(false);
        cbTipo.setValue(null);
        txtCodigo.clear();
        txtParticipantes.clear();
        txtDistanciaKm.clear();
        txtDesnivelMetros.clear();
        chkGuiaBilingue.setSelected(false);
        chkSeguroIncluido.setSelected(false);
        txtDuracionHoras.clear();
        btnRegistrar.setText("Registrar");
        btnCancelar.setDisable(true);
        actualizarCamposPorTipo();
        tabla.getSelectionModel().clearSelection();
    }

    private String describir(Excursion excursion) {
        return excursion.getCodigo() + " (" + excursion.getTipo() + ")";
    }

    private String formatearMonto(double monto) {
        return String.format(LOCALE_CL, "$%,.0f", monto);
    }

    private void mostrarExito(String mensaje) {
        mostrarMensaje(mensaje, "#eef5ec", "#a9cfa1", "#1e4620");
    }

    private void mostrarInfo(String mensaje) {
        mostrarMensaje(mensaje, "#eef3f8", "#a8c1d9", "#1f3a56");
    }

    private void mostrarError(String mensaje) {
        mostrarMensaje(mensaje, "#fbeeee", "#e0a3a3", "#8a1f1f");
    }

    private void mostrarMensaje(String mensaje, String fondo, String borde, String texto) {
        lblMensaje.setStyle("-fx-padding: 8 10 8 10; -fx-background-color: " + fondo
                + "; -fx-border-color: " + borde + "; -fx-text-fill: " + texto + ";");
        lblMensaje.setText(mensaje);
    }
}
