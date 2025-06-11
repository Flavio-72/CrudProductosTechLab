package com.techlab.service;

import com.techlab.model.Carrito;
import com.techlab.model.ItemCarrito;
import com.techlab.model.Producto;
import com.techlab.repository.Inventario;
import com.techlab.util.Utils;

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

        carrito.agregarProducto(producto, cantidad);
    }

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

             if (item.getCantidad() >= ItemCarrito.cantidadPromo) {
                double descuento = itemSubtotal * ItemCarrito.descPromoCant/100;
                itemSubtotal -= descuento;
                 System.out.printf("%s → $%s (%.0f%% descuento aplicado)\n",
                         item, Utils.separadorDeMiles(itemSubtotal), ItemCarrito.descPromoCant);
            } else {
                System.out.printf("%s\n", item);
            }

            subtotal += itemSubtotal;
        }

        double iva = subtotal * 0.21;
        double total = subtotal + iva;

        System.out.printf("\nSubtotal: $%s\n", Utils.separadorDeMiles(subtotal));
        System.out.printf("IVA (21%%): $%s\n", Utils.separadorDeMiles(iva));
        System.out.printf("TOTAL: $%s\n", Utils.separadorDeMiles(total));
    }
}