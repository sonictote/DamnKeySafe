package com.proyecto.proyectofinal;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.LinkedList;

public class ConexionBBDD {

    private final String URL = "jdbc:mysql://localhost:3306/proyectofinal";
    private final String USER_BBDD = "root";
    private final String PASS_BBDD = "";
    private Connection con;

    public ConexionBBDD() {
        try {
            con = DriverManager.getConnection(URL, USER_BBDD, PASS_BBDD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Con los parametros pasados verifica la cantidad de usuarios existen en la BBDD con esas credenciales
     *
     * @param nombreUsuario
     * @param contrasenya
     * @return si el recuento es mayor a 0, devuelve ture, Si es mayor, devuleve false
     */
    public boolean verificarCredenciales(String nombreUsuario, String contrasenya) {
        try {
            String query = "SELECT COUNT(*) FROM usuarios_aplicacion WHERE nom_usuario = ? AND contrasenya = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nombreUsuario);
            statement.setString(2, contrasenya);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verificarNomUsuarioExistente(String nombreUsuario) {
        try {
            String query = "SELECT COUNT(*) FROM usuarios_aplicacion WHERE nom_usuario = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nombreUsuario);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Consulta las credenciles guardadas por un usuario para despues mostrarlas
     *
     * @return
     * @throws SQLException
     */
    public LinkedList<CredencialesUsuario> consultarCredencialesGuardadas(String usuario)  {
        LinkedList<CredencialesUsuario> credenciales = new LinkedList<>();
        try {
            String consulta = "SELECT nombre_credencial,usuario_guardar, contrasenya_guardar, id_contrasenya FROM credenciales_usuario WHERE nom_usuario = ?";
            PreparedStatement preparedStatement = con.prepareStatement(consulta);
            preparedStatement.setString(1, usuario); //Habra que encontrar forma de guardar user

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                credenciales.add(new CredencialesUsuario(resultSet.getString("usuario_guardar"), resultSet.getString("contrasenya_guardar"), resultSet.getString("nombre_credencial"), resultSet.getInt("id_contrasenya")));
            }
            resultSet.close();
            preparedStatement.close();
            return credenciales;
        } catch (SQLException e) {
        }
        return credenciales;
    }

    public void insertarCredencial(String nomUsuario, String usuarioGuardar, String contraseniaGuardar, String nombreCredencial)  {
        String sql = "INSERT INTO credenciales_usuario (nom_usuario, usuario_guardar, contrasenya_guardar, nombre_credencial) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(sql);
            statement.setString(1, nomUsuario);
            statement.setString(2, usuarioGuardar);
            statement.setString(3, contraseniaGuardar);
            statement.setString(4, nombreCredencial);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean agregarUsuario(String nombreUsuario, String contrasena) {
        try {
            // Preparar la sentencia SQL para insertar un nuevo usuario
            String query = "INSERT INTO usuarios_aplicacion (nom_usuario, contrasenya) VALUES (?, ?)";
            PreparedStatement statement = con.prepareStatement(query);

            if(!verificarNomUsuarioExistente(nombreUsuario)){
                // Establecer los valores de los parámetros
                statement.setString(1, nombreUsuario);
                statement.setString(2, contrasena);

                // Ejecutar la sentencia SQL
                statement.executeUpdate();
                return true;
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Nombre de usuario existente");
                alert.setHeaderText(null);
                alert.setContentText("El usuario nombre de usuario ya existe.");
                alert.showAndWait();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void modificarCredencial(int idContrasenya, String usuarioGuardar, String contraseniaGuardar, String nombreCredencial)  {
        try {
            String sql = "UPDATE credenciales_usuario SET usuario_guardar = ?, contrasenya_guardar = ?, nombre_credencial = ? WHERE id_contrasenya = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, usuarioGuardar);
            statement.setString(2, contraseniaGuardar);
            statement.setString(3, nombreCredencial);
            statement.setInt(4, idContrasenya);

            int rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarCredencial(int idContraseña){
        try {
            String query = "DELETE FROM credenciales_usuario WHERE id_contrasenya = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, idContraseña);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}