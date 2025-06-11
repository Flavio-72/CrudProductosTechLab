package com.techlab.model;

import static com.techlab.util.Utils.formatearTitleCase;

public class Cliente {
    private static int contadorId = 1;

    private int id;
    private String nombre;
    private String apellido;
    private String email;

    public Cliente(String nombre, String apellido, String email) {
        this.id = contadorId++;
        this.nombre = formatearTitleCase(nombre);
        this.apellido = formatearTitleCase(apellido);
        this.email = email;
    }

    public String getEmail() { return email; }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    @Override
    public String toString() {
        return "Cliente: " + getNombreCompleto() + " | Email: " + email;
    }
}
