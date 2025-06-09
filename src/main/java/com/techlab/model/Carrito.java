package com.techlab.model;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private Cliente cliente;
    private List<ItemCarrito> items;

    public Carrito() {
        this.items = new ArrayList<>();
    }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public List<ItemCarrito> getItems() { return items; }

    public void agregarProducto(Producto producto, int cantidad) {
        // Buscar si el producto ya existe
        for (ItemCarrito item : items) {
            if (item.getProducto().getId() == producto.getId()) {
                // Producto existe, sumar cantidad
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        // Producto nuevo, agregarlo
        items.add(new ItemCarrito(producto, cantidad));
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }

    public double calcularTotal() {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getSubtotalConDescuento();
        }
        // Agregar IVA 21%
        return subtotal * 1.21;
    }
}
