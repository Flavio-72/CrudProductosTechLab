
// ===================================================
// PRODUCTO.JAVA - Simplificado
// ===================================================

package com.techlab.model;

public class Producto {
    private static int contador = 1;

    private int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String nombre, double precio, int stock) {
        this.id = contador++;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters básicos
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | %s | $%.2f | Stock: %d",
                id, nombre, precio, stock);
    }
}


// ===================================================
// CLIENTE.JAVA - Simplificado
// ===================================================


// ===================================================
// CARRITO.JAVA - Simplificado con lógica de duplicados
// ===================================================

// ===================================================
// INVENTARIO.JAVA - Simplificado
// ===================================================


// ===================================================
// CLIENTEREPOSITORY.JAVA - Simplificado
// ===================================================


// ===================================================
// CARRITOSERVICE.JAVA - Lógica de negocio centralizada
// ===================================================


// ===================================================
// UTILS.JAVA - Validaciones básicas
// ===================================================


// ===================================================
// MAIN.JAVA - Aplicación principal simplificada
// ===================================================

