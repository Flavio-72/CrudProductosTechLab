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

        productos.add(new Comida("Tostado Jamón Y Queso", 2500, 50, "20/06/25"));
        productos.add(new Comida("Medialuna Manteca", 1000, 30, "20/06/25"));
        productos.add(new Bebida("Café Con Leche", 3000, 50, 400));
        productos.add(new Bebida("Té Chai", 1500, 50, 500));
        productos.add(new Producto("Menú Tostado + Café Con Leche", 5500, 25));
        productos.add(new Producto("Menú 2 Medialunas + Café Con Leche", 4500, 40));
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

    public static void mostrarProductosAdmin() {
        System.out.println("\n=== PRODUCTOS DISPONIBLES (ADMIN) ===");
        for (Producto producto : productos) {
            if (producto instanceof Comida) {
                System.out.println(((Comida) producto).toStringAdmin());
            } else {
                System.out.println(producto);
            }
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
