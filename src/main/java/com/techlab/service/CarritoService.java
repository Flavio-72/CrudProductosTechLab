package com.techlab.service;

import com.techlab.model.Carrito;
import com.techlab.model.ItemCarrito;
import com.techlab.model.Producto;
import com.techlab.repository.Inventario;

public class CarritoService {

    public String validarAgregarProducto(int idProducto, int cantidad) {
        Producto producto = Inventario.buscarPorId(idProducto);

        if (producto == null) {
            return "Producto no encontrado";
        }

        if (cantidad <= 0) {
            return "Cantidad debe ser mayor a 0";
        }

        if (cantidad > producto.getStock()) {
            return "Stock insuficiente. Disponible: " + producto.getStock();
        }

        return null; // null = válido
    }

    public void agregarProducto(Carrito carrito, int idProducto, int cantidad) {
        Producto producto = Inventario.buscarPorId(idProducto);

        // SOLO agregar al carrito, NO descontar stock aún
        carrito.agregarProducto(producto, cantidad);
    }

    // NUEVO: Descontar stock al confirmar compra
    public void procesarCompra(Carrito carrito) {
        for (ItemCarrito item : carrito.getItems()) {
            Producto producto = item.getProducto();
            producto.setStock(producto.getStock() - item.getCantidad());
        }
    }

    public void mostrarResumen(Carrito carrito) {
        System.out.println("\n=== RESUMEN DEL CARRITO ===");

        if (carrito.getCliente() != null) {
            System.out.println("Cliente: " + carrito.getCliente().getNombreCompleto());
        } else {
            System.out.println("Cliente: Invitado");
        }

        if (carrito.estaVacio()) {
            System.out.println("El carrito está vacío");
            return;
        }

        double subtotal = 0;

        for (ItemCarrito item : carrito.getItems()) {
            double itemSubtotal = item.getSubtotal();

            if (item.getCantidad() >= 3) {
                double descuento = itemSubtotal * 0.1;
                itemSubtotal -= descuento;
                System.out.printf("%s → $%.2f (10%% descuento aplicado)\n",
                        item, itemSubtotal);
            } else {
                System.out.printf("%s\n", item);
            }

            subtotal += itemSubtotal;
        }

        double iva = subtotal * 0.21;
        double total = subtotal + iva;

        System.out.printf("\nSubtotal: $%.2f\n", subtotal);
        System.out.printf("IVA (21%%): $%.2f\n", iva);
        System.out.printf("TOTAL: $%.2f\n", total);
    }
}