package com.techlab.service;

import com.techlab.model.Carrito;
import com.techlab.model.ItemCarrito;
import com.techlab.model.Pedido;
import com.techlab.repository.PedidoRepository;

import java.util.List;

public class PedidoService {

    // Crear un pedido a partir del carrito
    public Pedido crearPedido(Carrito carrito) {
        double total = carrito.calcularTotal();
        Pedido pedido = new Pedido(carrito.getCliente(), carrito.getItems(), total);

        // Guardar en el repositorio
        PedidoRepository.guardar(pedido);

        return pedido;
    }

    // Mostrar detalles de un pedido
    public void mostrarDetallePedido(Pedido pedido) {
        System.out.println("\n=== DETALLE DEL PEDIDO ===");
        System.out.println("Código: " + pedido.getCodigo());
        System.out.println("Cliente: " + pedido.getCliente().getNombreCompleto());

        System.out.println("\nProductos:");
        for (ItemCarrito item : pedido.getItems()) {
            System.out.printf("- %s x%d - $%.2f\n",
                    item.getProducto().getNombre(),
                    item.getCantidad(),
                    item.getSubtotalConDescuento());
        }

        System.out.printf("\nTOTAL: $%.2f\n", pedido.getTotal());
    }

    // Mostrar todos los pedidos
    public void mostrarTodosPedidos() {
        List<Pedido> pedidos = PedidoRepository.obtenerTodos();

        System.out.println("\n=== TODOS LOS PEDIDOS ===");

        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados");
            return;
        }

        for (Pedido pedido : pedidos) {
            System.out.println(pedido);
        }
    }
}