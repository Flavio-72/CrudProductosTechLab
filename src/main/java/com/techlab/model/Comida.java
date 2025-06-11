package com.techlab.model;

public class Comida extends Producto {
    private String fechaVencimiento;

    public Comida (String nombre, double precio, int stock, String fechaVencimiento){
        super(nombre, precio, stock);
        this.fechaVencimiento = fechaVencimiento;
    }
    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public String toStringAdmin() {
        return super.toString() + " - Vence: " + fechaVencimiento;
    }

}
