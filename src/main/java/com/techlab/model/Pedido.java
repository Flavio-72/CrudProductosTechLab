package com.techlab.model;

import com.techlab.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static int contadorCodigo = 1;

    private String codigo;
    private Cliente cliente;
    private List<ItemCarrito> items;
    private double total;

    public Pedido(Cliente cliente, List<ItemCarrito> items, double total) {
        this.codigo = "P" + contadorCodigo++;
        this.cliente = cliente;
        this.items = new ArrayList<>(items); // Copiamos la lista para evitar referencias
        this.total = total;
    }

    public String getCodigo() { return codigo; }
    public Cliente getCliente() { return cliente; }
    public List<ItemCarrito> getItems() { return items; }
    public double getTotal() { return total; }

    @Override
    public String toString() {
        return String.format("Pedido %s - %s - $%s",
                codigo, cliente.getNombreCompleto(), Utils.separadorDeMiles(total));
    }
}