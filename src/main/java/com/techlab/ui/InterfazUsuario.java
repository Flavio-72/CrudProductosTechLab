package com.techlab.ui;

import com.techlab.model.Carrito;
import com.techlab.model.Cliente;
import com.techlab.model.Pedido;
import com.techlab.repository.ClienteRepository;
import com.techlab.repository.Inventario;
import com.techlab.service.CarritoService;
import com.techlab.service.PedidoService;
import com.techlab.util.Utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InterfazUsuario {
    private final Scanner scanner = new Scanner(System.in);
    private final Carrito carrito = new Carrito();
    private final CarritoService carritoService = new CarritoService();
    private final PedidoService pedidoService = new PedidoService();

    public void iniciar() {
        System.out.println("\n=== TechLab ===");

        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = leerEntero("Seleccione opción: ");

            switch (opcion) {
                case 1 -> agregarAlCarrito();
                case 2 -> carritoService.mostrarResumen(carrito);
                case 3 -> confirmarCompra();
                case 4 -> pedidoService.mostrarTodosPedidos();
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
        System.out.println("4. Ver pedidos");
        System.out.println("5. Salir");
    }

    private void agregarAlCarrito() {
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

    private void identificarCliente() {
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

    private void confirmarCompra() {
        if (carrito.estaVacio()) {
            System.out.println("El carrito está vacío. Agregue productos antes de confirmar.");
            return;
        }

        carritoService.mostrarResumen(carrito);

        System.out.print("\n¿Confirma la compra? (s/n): ");
        scanner.nextLine(); // limpiar buffer
        String respuesta = scanner.nextLine().toLowerCase();

        if (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) {
            // CREAR EL PEDIDO
            Pedido pedido = pedidoService.crearPedido(carrito);

            System.out.println("\n✅ ¡COMPRA CONFIRMADA!");
            System.out.println("Se ha generado el pedido: " + pedido.getCodigo());
            System.out.println("Gracias por su compra, " + pedido.getCliente().getNombreCompleto());

            // Mostrar detalle del pedido creado
            pedidoService.mostrarDetallePedido(pedido);

            limpiarCarrito();
            System.out.println("\nPuede continuar comprando con otro cliente...");
        } else {
            System.out.println("Compra cancelada. Puede seguir modificando su carrito.");
        }
    }

    private void limpiarCarrito() {
        carrito.getItems().clear();
        carrito.setCliente(null);
    }

    private int leerEntero(String mensaje) {
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