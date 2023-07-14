package com.proyecto.proyectofinal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GestorContraseñasController implements Initializable {
    @FXML
    private TableView<CredencialesUsuario> tablaCredenciales;

    @FXML
    private AnchorPane panelPrincipal;

    @FXML
    private TableColumn<CredencialesUsuario, String> columNomPass;

    @FXML
    private TableColumn<CredencialesUsuario, String> columUser;

    @FXML
    private TableColumn<CredencialesUsuario, String> columPass;

    @FXML
    private TableColumn<CredencialesUsuario, Void> columBoton;

    @FXML
    private TextField buscador;

    @FXML
    private Label lblUsuarioActual;

    @FXML
    private PasswordField txtAñadirOModificarPass;

    @FXML
    private TextField txtAñadirOModificarUsuario;

    @FXML
    private Button btnAñadirCredencial;

    @FXML
    private Button btnModificarCredencial;

    @FXML
    private TextField txtAñadirOModificarNomCredencial;

    @FXML
    private Label lblModificarOAñadir;

    @FXML
    private AnchorPane panelModOAñadir;

    @FXML
    private Button cerrarVentanaMod;

    ConexionBBDD con;

    private ObservableList<CredencialesUsuario> credenciales;

    private String usuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cerrarVentanaMod.setGraphic(new ImageView( new Image(getClass().getResourceAsStream("/com/proyecto/proyectofinal/icon cerrar.png"), 10, 10, false, false)));
        panelPrincipal.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                panelPrincipal.requestFocus();
            }
        });
    }

    public void iniciarTabla(String usuario) {
        con = new ConexionBBDD();
        this.usuario = usuario;
        this.credenciales = FXCollections.observableList(con.consultarCredencialesGuardadas(usuario));
        lblUsuarioActual.setText(this.usuario);

        colocarColums();
        llenarTablaCredenciales();
    }

    /**
     * Introduce crea tantas barras como datos haya en el TreeMap e introduce esos datos en ellas
     */
    private void llenarTablaCredenciales() {
        FilteredList<CredencialesUsuario> filteredList = new FilteredList<>(credenciales, p -> true);

        buscador.textProperty().addListener((prop, old, text) -> {
            filteredList.setPredicate(CredencialesUsuario -> {
                if (text == null || text.isEmpty()) return true;
                String nom = CredencialesUsuario.getNomCredencial();
                return nom.toLowerCase().contains(text) || nom.toUpperCase().contains(text) || nom.contains(text);
            });
        });
        tablaCredenciales.setItems(filteredList);
    }

    private void colocarColums() {
        columNomPass.setCellValueFactory(new PropertyValueFactory<>("nomCredencial"));
        columUser.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        columPass.setCellValueFactory(new PropertyValueFactory<>("copiaContraseña"));
        columBoton.setCellFactory(param -> new TableCell<CredencialesUsuario, Void>() {
            private Button boton = new Button();
            private Image img = new Image(getClass().getResourceAsStream("/com/proyecto/proyectofinal/eye_show_pass_icon_.png"), 20, 20, false, false);
            private ImageView imagenBoton = new ImageView(img);

            {
                boton.setGraphic(imagenBoton);
                boton.setStyle("-fx-background-color:transparent");
                boton.setOnAction(actionEvent -> {
                    CredencialesUsuario credencial = credenciales.get(getIndex());
                    credencial.mostrarContraseñaOculta();
                    tablaCredenciales.refresh();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(boton);
                }
            }
        });
    }

    @FXML
    private void cerrarSesion() {
        Stage stageGestor = (Stage) this.panelPrincipal.getScene().getWindow();
        stageGestor.close();
        new GestorContraseñasApplication().start(new Stage());
    }

    @FXML
    private void añadirCredenciales() {
        lblModificarOAñadir.setText("Añadir credencial");
        btnAñadirCredencial.setVisible(true);
        btnModificarCredencial.setVisible(false);
        mostrarOcultarPanelModOAñadir("mostrar");
    }

    @FXML
    private void modificarCredenciales() {
        if (tablaCredenciales.getSelectionModel().isSelected(tablaCredenciales.getSelectionModel().getSelectedIndex())) {
            lblModificarOAñadir.setText("Modificar credencial");
            btnModificarCredencial.setVisible(true);
            btnAñadirCredencial.setVisible(false);
            mostrarOcultarPanelModOAñadir("mostrar");

            CredencialesUsuario credencial = tablaCredenciales.getSelectionModel().getSelectedItem();

            txtAñadirOModificarNomCredencial.setText(credencial.getNomCredencial());
            txtAñadirOModificarUsuario.setText(credencial.getUsuario());
            txtAñadirOModificarPass.setText(credencial.getContraseña());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Debes seleccionar la credencial que deseas modificar");
            alert.showAndWait();
        }
    }

    private void mostrarOcultarPanelModOAñadir(String accion) {
        txtAñadirOModificarNomCredencial.setText("");
        txtAñadirOModificarUsuario.setText("");
        txtAñadirOModificarPass.setText("");
        if (accion.equals("mostrar")) {
            panelModOAñadir.setVisible(true);
            tablaCredenciales.setDisable(true);
        } else {
            panelModOAñadir.setVisible(false);
            tablaCredenciales.setDisable(false);
        }
    }

    @FXML
    private void añadirCredencialBBDD() {
        if(!txtAñadirOModificarUsuario.getText().equals("") && !txtAñadirOModificarPass.getText().equals("") && !txtAñadirOModificarNomCredencial.getText().equals("")){
            con.insertarCredencial(usuario, txtAñadirOModificarUsuario.getText(), txtAñadirOModificarPass.getText(), txtAñadirOModificarNomCredencial.getText());
            iniciarTabla(usuario);
            mostrarOcultarPanelModOAñadir("");
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Debes rellenar todos los datos");
            alert.showAndWait();
        }
    }

    @FXML
    private void modificarCredencialBBDD() {
        if (tablaCredenciales.getSelectionModel().isSelected(tablaCredenciales.getSelectionModel().getSelectedIndex())) {
            if(!txtAñadirOModificarUsuario.getText().equals("") && !txtAñadirOModificarPass.getText().equals("") && !txtAñadirOModificarNomCredencial.getText().equals("")){
                CredencialesUsuario credencial = tablaCredenciales.getSelectionModel().getSelectedItem();
                con.modificarCredencial(credencial.getIdContraseña(), txtAñadirOModificarUsuario.getText(), txtAñadirOModificarPass.getText(), txtAñadirOModificarNomCredencial.getText());
                iniciarTabla(usuario);
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Debes rellenar todos los datos");
                alert.showAndWait();
            }
            mostrarOcultarPanelModOAñadir("");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Debes seleccionar la credencial que deseas modificar");
            alert.showAndWait();
        }
    }

    @FXML
    private void BorrarCredencialBBDD() {
        if (tablaCredenciales.getSelectionModel().isSelected(tablaCredenciales.getSelectionModel().getSelectedIndex())) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("¿Estas seguro de confirmar la acción?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                CredencialesUsuario credencial = tablaCredenciales.getSelectionModel().getSelectedItem();
                con.eliminarCredencial(credencial.getIdContraseña());
                iniciarTabla(usuario);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Debes seleccionar la credencial que deseas borrar");
            alert.showAndWait();
        }
    }

    @FXML
    private void cerrarVentanaModOAñadir() {
        mostrarOcultarPanelModOAñadir("");
    }
}
