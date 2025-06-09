package com.techlab.model;

public class Cliente {
    private static int contadorId = 1;

    private int id;
    private String nombre;
    private String apellido;
    private String email;

    public Cliente(String nombre, String apellido, String email) {
        this.id = contadorId++;
        this.nombre = formatearNombre(nombre);
        this.apellido = formatearNombre(apellido);
        this.email = email;
    }

    private String formatearNombre(String texto) {
        if (texto == null || texto.trim().isEmpty()) return "";
        texto = texto.trim();
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getEmail() { return email; }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    @Override
    public String toString() {
        return "Cliente: " + getNombreCompleto() + " | Email: " + email;
    }
}
