package com.proyecto.proyectofinal;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private AnchorPane panelPrincipal;

    @FXML
    private AnchorPane panelInicioSesion;

    @FXML
    private PasswordField accederPass;

    @FXML
    private TextField accederUser;

    @FXML
    private AnchorPane panelRegistro;

    @FXML
    private PasswordField registroPass;

    @FXML
    private TextField registroUser;

    @FXML
    private Label textErrorRegistro;

    private ConexionBBDD con;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    con = new ConexionBBDD();
        panelPrincipal.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                panelPrincipal.requestFocus();
            }
        });
    }

    private boolean validarRegistro() {
        if (registroUser.getText().equalsIgnoreCase("") && registroPass.getText().equalsIgnoreCase("")) {
            textErrorRegistro.setText("Falta completar el usuario y contraseña");
            return false;
        } else if (registroUser.getText().equalsIgnoreCase("")) {
            textErrorRegistro.setText("Falta completar el usuario");
            return false;
        } else if (registroPass.getText().equalsIgnoreCase("")) {
            textErrorRegistro.setText("Falta completar la contraseña");
            return false;
        } else if (registroPass.getText().length() < 8) {
            textErrorRegistro.setText("La contraseña debe contener 8 caracteres minimo");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Comprueba que en el inicio de sesion las credenciales introducidas son correctas en la BBDD
     */
    @FXML
    private void comprobarInicioSesion() {
        GestorContraseñasApplication gestor = new GestorContraseñasApplication();
        String usuario = accederUser.getText();
        String pass = accederPass.getText();

        if (con.verificarCredenciales(usuario, pass)) { //Compruba que las credenciales son correctas en la BBDD
            Stage stage = (Stage) this.panelPrincipal.getScene().getWindow(); //Saca el stage de esa ventana
            stage.close(); //Cierra el stage
            gestor.mostrarVentanaPrincipal(usuario); //Muestra la ventana principal
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("El usuario y la contraseña no son correctas");
            alert.showAndWait();
        }
    }

    @FXML
    private void registrarUsuario() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (validarRegistro()) {
            if(con.agregarUsuario(registroUser.getText(), registroPass.getText())){
                alert.setTitle("Usuario creado correctamente");
                alert.setHeaderText(null);
                alert.setContentText("El usuario fue creado de manera correcta");
                alert.showAndWait();
                irInicioSesion();
            }
        }
    }

    @FXML
    private void irARegistro() {
        registroUser.setText("");
        registroPass.setText("");
        textErrorRegistro.setText("");
        panelInicioSesion.setVisible(false);
        panelRegistro.setVisible(true);
    }

    @FXML
    private void irInicioSesion() {
        accederUser.setText("");
        accederPass.setText("");
        panelRegistro.setVisible(false);
        panelInicioSesion.setVisible(true);
    }
}
