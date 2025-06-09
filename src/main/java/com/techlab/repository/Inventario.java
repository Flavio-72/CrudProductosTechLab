package com.techlab.repository;

import com.techlab.model.Producto;
import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private static final List<Producto> productos = new ArrayList<>();

    public static void inicializar() {
        productos.add(new Producto("Café Premium", 1500.0, 10));
        productos.add(new Producto("Helado Artesanal", 2200.0, 5));
        productos.add(new Producto("Té Chai", 1000.0, 8));
    }

    public static List<Producto> obtenerTodos() {
        return new ArrayList<>(productos); // Copia defensiva
    }

    public static Producto buscarPorId(int id) {
        for (Producto producto : productos) {
            if (producto.getId() == id) {
                return producto;
            }
        }
        return null;
    }

    public static void mostrarProductos() {
        System.out.println("\n=== PRODUCTOS DISPONIBLES ===");
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }
}
