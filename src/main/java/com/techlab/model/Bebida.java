package com.techlab.model;

public class Bebida extends Producto {
    private int ml;

    public Bebida(String nombre, double precio, int stock, int ml) {
        super(nombre, precio, stock);
        this.ml = ml;
    }

    public int getMl() {
        return ml;
    }

    @Override
    public String toString() {
        return super.toString() + " - " + ml + "ml";
    }
}