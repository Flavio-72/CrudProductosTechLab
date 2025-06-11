package com.techlab.model;

import com.techlab.util.Utils;

public class ItemCarrito {
    private Producto producto;
    private int cantidad;
    public static double descPromoCant = 15;
    public static int cantidadPromo= 4;

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
        if (cantidad >= cantidadPromo) {
            double factorDesc = 1 - descPromoCant/100;
            subtotal *= factorDesc;
        }
        return subtotal;
    }


    @Override
    public String toString() {
        return String.format("%s x%d  $%s",
                producto.getNombre(), cantidad, Utils.separadorDeMiles(getSubtotal()));
    }

}
