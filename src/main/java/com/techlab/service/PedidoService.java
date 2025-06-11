package com.techlab.service;

import com.techlab.model.Carrito;
import com.techlab.model.ItemCarrito;
import com.techlab.model.Pedido;
import com.techlab.repository.PedidoRepository;
import com.techlab.util.Utils;

import java.util.List;

public class PedidoService {

    public Pedido crearPedido(Carrito carrito) {
        double total = carrito.calcularTotal();
        Pedido pedido = new Pedido(carrito.getCliente(), carrito.getItems(), total);

        PedidoRepository.guardar(pedido);

        return pedido;
    }

    public void mostrarDetallePedido(Pedido pedido) {
        System.out.println("\n=== DETALLE DEL PEDIDO ===");
        System.out.println("Código: " + pedido.getCodigo());
        System.out.println("Cliente: " + pedido.getCliente().getNombreCompleto());

        System.out.println("\nProductos:");
        for (ItemCarrito item : pedido.getItems()) {
            System.out.printf("- %s x%d - $%s\n",
                    item.getProducto().getNombre(),
                    item.getCantidad(),
                    Utils.separadorDeMiles(item.getSubtotalConDescuento()));
        }

        System.out.printf("\nTOTAL: $%s\n", Utils.separadorDeMiles(pedido.getTotal()));
    }

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