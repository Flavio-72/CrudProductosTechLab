// ===================================================
// PRODUCTOSERVICE.JAVA - Lógica de negocio para productos
// ===================================================

package com.techlab.service;

import com.techlab.model.Producto;
import com.techlab.repository.Inventario;

public class ProductoService {

    public String validarCompra(int idProducto, int cantidad) {
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

    public void actualizarStock(int idProducto, int cantidadVendida) {
        Producto producto = Inventario.buscarPorId(idProducto);
        producto.setStock(producto.getStock() - cantidadVendida);
    }
}