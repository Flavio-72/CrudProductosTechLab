package com.techlab.model;

public class ItemCarrito {
    private Producto producto;
    private int cantidad;
    private double descPromoCant;
    private int cantidadPromo;

    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;

    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }

    public void setCantidad(int cantidad) {
        if (cantidad > 0) {
            this.cantidad = cantidad;
        }
    }

    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    public double getSubtotalConDescuento() {
        double subtotal = getSubtotal();
        if (cantidad >= 3) {
            double factorDesc = 1 - 10/100;
            subtotal *= factorDesc; // 10% descuento
        }
        return subtotal;
    }


    @Override
    public String toString() {
        return String.format("%s x%d  $%.2f",
                producto.getNombre(), cantidad, getSubtotal());
    }

}
