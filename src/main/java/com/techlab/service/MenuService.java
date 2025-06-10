package com.techlab.service;

import com.techlab.model.Carrito;
import com.techlab.model.Cliente;
import com.techlab.model.Pedido;
import com.techlab.model.Producto;
import com.techlab.repository.ClienteRepository;
import com.techlab.repository.Inventario;
import com.techlab.util.Utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuService {
    private final Scanner scanner;
    private final Carrito carrito;
    private final CarritoService carritoService;
    private final PedidoService pedidoService;

    public MenuService(Scanner scanner, Carrito carrito,
                       CarritoService carritoService, PedidoService pedidoService) {
        this.scanner = scanner;
        this.carrito = carrito;
        this.carritoService = carritoService;
        this.pedidoService = pedidoService;
    }

    // ==================== MENÚ PRINCIPAL ====================

    public void agregarAlCarrito() {
        if (carrito.getCliente() == null) {
            identificarCliente();
        }

        Inventario.mostrarProductos();

        int id = leerEntero("Ingrese ID del producto: ");
        int cantidad = leerEntero("Ingrese cantidad: ");

        String error = carritoService.validarAgregarProducto(id, cantidad);
        if (error != null) {
            System.out.println("Error: " + error);
            return;
        }

        System.out.println("Tip: 3+ unidades = 10% descuento");
        carritoService.agregarProducto(carrito, id, cantidad);
        System.out.println("✅ Producto agregado correctamente");
        System.out.printf("Agregado: %s x%d ud.\n",
                Inventario.buscarPorId(id).getNombre(), cantidad);
    }

    public void identificarCliente() {
        System.out.println("\n--- IDENTIFICACIÓN ---");
        scanner.nextLine(); // limpiar buffer

        String email;
        do {
            System.out.print("Email: ");
            email = scanner.nextLine();
            if (!Utils.esEmailValido(email)) {
                System.out.println("Email inválido");
            }
        } while (!Utils.esEmailValido(email));

        Cliente cliente = ClienteRepository.buscarPorEmail(email);

        if (cliente != null) {
            System.out.println("¡Bienvenido " + cliente.getNombreCompleto() + "!");
            carrito.setCliente(cliente);
        } else {
            System.out.println("Cliente nuevo. Complete sus datos:");

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();

            cliente = new Cliente(nombre, apellido, email);
            ClienteRepository.guardar(cliente);
            carrito.setCliente(cliente);

            System.out.println("✅ Cliente registrado");
        }
    }

    public void confirmarCompra() {
        if (carrito.estaVacio()) {
            System.out.println("El carrito está vacío. Agregue productos antes de confirmar.");
            return;
        }

        carritoService.mostrarResumen(carrito);

        System.out.print("\n¿Confirma la compra? (s/n): ");
        scanner.nextLine(); // limpiar buffer
        String respuesta = scanner.nextLine().toLowerCase();

        if (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) {

            carritoService.procesarCompra(carrito);

            Pedido pedido = pedidoService.crearPedido(carrito);

            System.out.println("\n ¡COMPRA CONFIRMADA!");
            System.out.println("Se ha generado el pedido: " + pedido.getCodigo());
            System.out.println("Gracias por su compra, " + pedido.getCliente().getNombreCompleto());

            pedidoService.mostrarDetallePedido(pedido);

            limpiarCarrito();
            System.out.println("\nPuede continuar comprando con otro cliente...");
        } else {
            System.out.println("Compra cancelada. Puede seguir modificando su carrito.");
        }
    }

    // ==================== MENÚ ADMIN ====================

    public void modoAdmin() {
        System.out.print("Contraseña admin: ");
        scanner.nextLine();
        String password = scanner.nextLine();

        if (password.equals("admin123")) {
            menuAdmin();
        } else {
            System.out.println("Acceso denegado");
        }
    }

    public void menuAdmin() {
        System.out.println("\n=== MODO ADMINISTRADOR ===");

        boolean continuar = true;
        while (continuar) {
            mostrarMenuAdmin();
            int opcion = leerEntero("Seleccione opción: ");

            switch (opcion) {
                case 1 -> pedidoService.mostrarTodosPedidos();
                case 2 -> Inventario.mostrarProductos();
                case 3 -> ClienteRepository.mostrarTodosClientes();
                case 4 -> modificarStock();
                case 5 -> {
                    System.out.println("Saliendo del modo admin...");
                    continuar = false;
                }
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void mostrarMenuAdmin() {
        System.out.println("\n--- MENÚ ADMIN ---");
        System.out.println("1. Ver todos los pedidos");
        System.out.println("2. Ver inventario");
        System.out.println("3. Ver clientes");
        System.out.println("4. Modificar stock");
        System.out.println("5. Volver al menú principal");
    }

    private void modificarStock() {
        Inventario.mostrarProductos();

        int id = leerEntero("ID del producto a modificar: ");
        Producto producto = Inventario.buscarPorId(id);

        if (producto == null) {
            System.out.println("Producto no encontrado");
            return;
        }

        System.out.println("Stock actual: " + producto.getStock());
        int nuevoStock = leerEntero("Nuevo stock: ");

        if (nuevoStock < 0) {
            System.out.println("El stock no puede ser negativo");
            return;
        }

        producto.setStock(nuevoStock);
        System.out.println("✅ Stock actualizado correctamente");
    }

    // ==================== UTILIDADES ====================

    private void limpiarCarrito() {
        carrito.getItems().clear();
        carrito.setCliente(null);
    }

    public int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un número");
                scanner.nextLine();
            }
        }
    }
}