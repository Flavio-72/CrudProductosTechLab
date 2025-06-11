package com.techlab.service;

import com.techlab.model.Cliente;
import com.techlab.repository.ClienteRepository;
import com.techlab.util.Utils;

public class ClienteService {

    public String validarDatosCliente(String nombre, String apellido, String email) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return "El nombre es obligatorio";
        }

        if (apellido == null || apellido.trim().isEmpty()) {
            return "El apellido es obligatorio";
        }

        if (!Utils.esEmailValido(email)) {
            return "Email inválido";
        }

        if (ClienteRepository.existe(email)) {
            return "Ya existe un cliente con ese email";
        }

        return null; // null = válido
    }

    public Cliente registrarNuevoCliente(String nombre, String apellido, String email) {
        Cliente cliente = new Cliente(nombre, apellido, email);
        ClienteRepository.guardar(cliente);
        return cliente;
    }

    public void mostrarBienvenida(Cliente cliente) {
        System.out.println("¡Bienvenido " + cliente.getNombreCompleto() + "!");
    }


}