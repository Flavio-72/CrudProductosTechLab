package com.techlab.repository;

import com.techlab.model.Pedido;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository {
    private static final List<Pedido> pedidos = new ArrayList<>();

    // Guardar un pedido
    public static void guardar(Pedido pedido) {
        pedidos.add(pedido);
    }

    // Obtener todos los pedidos
    public static List<Pedido> obtenerTodos() {
        return new ArrayList<>(pedidos); // Copia defensiva
    }

    // Buscar pedido por código
    public static Pedido buscarPorCodigo(String codigo) {
        for (Pedido pedido : pedidos) {
            if (pedido.getCodigo().equals(codigo)) {
                return pedido;
            }
        }
        return null;
    }
}