package com.proyecto.proyectofinal;


public class CredencialesUsuario {

    private int idContraseña;

    private String usuario;

    private String contraseña;

    private String nomCredencial;

    private String copiaContraseña;

    private boolean mostrarContraseñaOculta;

    public CredencialesUsuario(String usuario, String contraseña, String nomCredencial,int idContraseña) {
        this.idContraseña = idContraseña;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.nomCredencial = nomCredencial;
        this.copiaContraseña = "*".repeat(contraseña.length());
        this.mostrarContraseñaOculta = false;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getNomCredencial() { return nomCredencial; }

    public int getIdContraseña() {
        return idContraseña;
    }

    public String getCopiaContraseña() {
        return copiaContraseña;
    }

    public void mostrarContraseñaOculta() {
        if (mostrarContraseñaOculta) {
            mostrarContraseñaOculta = false;
            copiaContraseña = "*".repeat(contraseña.length());
        } else {
            mostrarContraseñaOculta = true;
            copiaContraseña = contraseña;
        }
    }
}