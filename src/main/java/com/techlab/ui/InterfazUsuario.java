package com.techlab.ui;

import com.techlab.model.Carrito;
import com.techlab.service.CarritoService;
import com.techlab.service.MenuService;
import com.techlab.service.PedidoService;

import java.util.Scanner;

public class InterfazUsuario {
    private final Scanner scanner = new Scanner(System.in);
    private final Carrito carrito = new Carrito();
    private final CarritoService carritoService = new CarritoService();
    private final PedidoService pedidoService = new PedidoService();
    private final com.techlab.service.MenuService menuService;

    public InterfazUsuario() {
        this.menuService = new MenuService(scanner, carrito, carritoService, pedidoService);
    }

    public void iniciar() {
        System.out.println("\n=== TechLab ===");

        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = menuService.leerEntero("Seleccione opción: ");

            switch (opcion) {
                case 1 -> menuService.agregarAlCarrito();
                case 2 -> carritoService.mostrarResumen(carrito);
                case 3 -> menuService.confirmarCompra();
                case 4 -> menuService.modoAdmin();
                case 5 -> {
                    System.out.println("¡Gracias por su visita!");
                    continuar = false;
                }
                default -> System.out.println("Opción inválida");
            }
        }

        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Agregar producto");
        System.out.println("2. Ver carrito");
        System.out.println("3. Confirmar compra");
        System.out.println("4. Modo Admin");
        System.out.println("5. Salir");
    }
}