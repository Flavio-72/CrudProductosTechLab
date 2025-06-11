package com.techlab.repository;

import com.techlab.model.Bebida;
import com.techlab.model.Comida;
import com.techlab.model.Producto;
import java.util.ArrayList;
import java.util.List;
import com.techlab.exception.ProductoNoEncontradoException;

public class Inventario {
    private static final List<Producto> productos = new ArrayList<>();

    public static void inicializar() {
        productos.add(new Bebida("Café con leche", 3000.0, 50, 400));
        productos.add(new Comida("Tostado Jamón y queso", 2200.0, 50, "20/06/25"));
        productos.add(new Bebida("Té Chai", 1000.0, 100, 500));

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
        throw new ProductoNoEncontradoException(id);
    }

    public static void mostrarProductos() {
        System.out.println("\n=== PRODUCTOS DISPONIBLES ===");
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }
    public static void agregarProducto(String nombre, double precio, int stock) {
        productos.add(new Producto(nombre, precio, stock));
    }

    public static void agregarComida(String nombre, double precio, int stock, String fechaVencimiento) {
        productos.add(new Comida(nombre, precio, stock, fechaVencimiento));
    }

    public static void agregarBebida(String nombre, double precio, int stock, int ml) {
        productos.add(new Bebida(nombre, precio, stock, ml));
    }

    public static void modificarNombre(int id, String nuevoNombre) {
        Producto producto = buscarPorId(id);
        if (producto != null) {
            producto.setNombre(nuevoNombre);
        }
    }

    public static void modificarPrecio(int id, double nuevoPrecio) {
        Producto producto = buscarPorId(id);
        if (producto != null) {
            producto.setPrecio(nuevoPrecio);
        }
    }

    public static void eliminarProducto(int id) {
        productos.removeIf(p -> p.getId() == id);
    }
}
