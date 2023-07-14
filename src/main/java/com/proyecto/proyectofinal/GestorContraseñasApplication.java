package com.proyecto.proyectofinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GestorContraseñasApplication extends Application {

    /**
     * Muestra la ventana donde el usuario podra ver todas sus credenciales añadidas u añadir nuevas
     */
    public void mostrarVentanaPrincipal(String usuario) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GestorContraseñasApplication.class.getResource("GestorContraseñasview.fxml"));
            Scene scene = new Scene(fxmlLoader.load()); //Cargamos la escena
            GestorContraseñasController controlador = fxmlLoader.getController();
            controlador.iniciarTabla(usuario);
            Stage stage = new Stage();
            stage.setTitle("Damn!KeySafe");
            stage.setScene(scene); //Asignamos la escena
            stage.setResizable(false);
            stage.show(); //Mostramos escena
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Damn!KeySafe");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        launch();
    }
}