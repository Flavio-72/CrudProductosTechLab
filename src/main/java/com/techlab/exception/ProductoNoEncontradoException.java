package com.techlab.exception;

public class ProductoNoEncontradoException extends RuntimeException {
    private final int idProducto;

    public ProductoNoEncontradoException(int idProducto) {
        super("Producto con ID " + idProducto + " no encontrado");
        this.idProducto = idProducto;
    }

    public int getIdProducto() {
        return idProducto;
    }
}