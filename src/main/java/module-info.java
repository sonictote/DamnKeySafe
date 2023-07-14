module com.proyecto.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    exports com.proyecto.proyectofinal;
    opens com.proyecto.proyectofinal to javafx.fxml;
}