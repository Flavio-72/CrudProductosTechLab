package com.techlab.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static int contadorCodigo = 1000; // Empezamos en 1000 para que se vea más profesional

    private String codigo;
    private Cliente cliente;
    private List<ItemCarrito> items;
    private double total;

    // Constructor
    public Pedido(Cliente cliente, List<ItemCarrito> items, double total) {
        this.codigo = "P" + contadorCodigo++; // P1000, P1001, P1002...
        this.cliente = cliente;
        this.items = new ArrayList<>(items); // Copiamos la lista para evitar referencias
        this.total = total;
    }

    // Getters
    public String getCodigo() { return codigo; }
    public Cliente getCliente() { return cliente; }
    public List<ItemCarrito> getItems() { return items; }
    public double getTotal() { return total; }

    @Override
    public String toString() {
        return String.format("Pedido %s - %s - $%.2f",
                codigo, cliente.getNombreCompleto(), total);
    }
}