package com.techlab.repository;

import com.techlab.model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {
    private static final List<Cliente> clientes = new ArrayList<>();

    public static void guardar(Cliente cliente) {
        clientes.add(cliente);
    }

    public static Cliente buscarPorEmail(String email) {
        for (Cliente cliente : clientes) {
            if (cliente.getEmail().equalsIgnoreCase(email)) {
                return cliente;
            }
        }
        return null;
    }

    public static void mostrarTodosClientes() {
        System.out.println("\n=== TODOS LOS CLIENTES ===");

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados");
            return;
        }

        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    public static boolean existe(String email) {
        return buscarPorEmail(email) != null;
    }
}
