package com.techlab.service;

import com.techlab.model.*;
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
    private final ClienteService clienteService = new ClienteService();

    public MenuService(Scanner scanner, Carrito carrito,
                       CarritoService carritoService, PedidoService pedidoService) {
        this.scanner = scanner;
        this.carrito = carrito;
        this.carritoService = carritoService;
        this.pedidoService = pedidoService;
    }

    //  MENÚ PRINCIPAL
    public void agregarAlCarrito() {
        if (carrito.getCliente() == null) {
            identificarCliente();
        }

        Inventario.mostrarProductos();

        int id = leerEntero("Ingrese ID del producto: ");
        System.out.println("Tip: " + ItemCarrito.cantidadPromo + "+ unidades = " + ItemCarrito.descPromoCant + "% descuento");
        int cantidad = leerEntero("Ingrese cantidad: ");

        String error = carritoService.validarAgregarProducto(id, cantidad);
        if (error != null) {
            System.out.println("Error: " + error);
            return;
        }

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

            cliente = clienteService.registrarNuevoCliente(nombre, apellido, email);
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

    // MENÚ ADMIN
    public void modoAdmin() {
        System.out.print("Contraseña admin(Etapa Desarrollo= 'techlab123' ) : ");
        scanner.nextLine();
        String password = scanner.nextLine();

        if (password.equals("techlab123")) {
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
                case 4 -> gestionarProductos();
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
        System.out.println("4. Gestionar productos");
        System.out.println("5. Volver al menú principal");
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
    private void gestionarProductos() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Crear producto");
            System.out.println("2. Modificar producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Volver al menú admin");

            int opcion = leerEntero("Seleccione opción: ");

            switch (opcion) {
                case 1 -> crearProducto();
                case 2 -> modificarProducto();
                case 3 -> eliminarProducto();
                case 4 -> continuar = false;
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void crearProducto() {
        System.out.println("\n--- TIPO DE PRODUCTO ---");
        System.out.println("1. Producto general");
        System.out.println("2. Comida");
        System.out.println("3. Bebida");

        int tipo = leerEntero("Seleccione tipo: ");

        scanner.nextLine(); // limpiar buffer

        System.out.print("Nombre del producto: ");
        String nombre = Utils.formatearTitleCase(scanner.nextLine());

        double precio = leerDouble("Precio: $");
        int stock = leerEntero("Stock inicial: ");
        scanner.nextLine();

        if (precio <= 0) {
            System.out.println("El precio debe ser mayor a 0");
            return;
        }

        if (stock < 0) {
            System.out.println("El stock no puede ser negativo");
            return;
        }

        switch (tipo) {
            case 1 -> {
                Inventario.agregarProducto(nombre, precio, stock);
                System.out.println("✅ Producto creado correctamente");
            }
            case 2 -> {
                System.out.print("Fecha de vencimiento: ");
                String fechaVencimiento = scanner.nextLine();
                Inventario.agregarComida(nombre, precio, stock, fechaVencimiento);
                System.out.println("✅ Comida creada correctamente");
            }
            case 3 -> {
                int ml = leerEntero("Mililitros: ");
                Inventario.agregarBebida(nombre, precio, stock, ml);
                System.out.println("✅ Bebida creada correctamente");
            }
            default -> System.out.println("Tipo inválido");
        }
    }

    private void modificarProducto() {
        Inventario.mostrarProductos();

        int id = leerEntero("ID del producto a modificar: ");
        Producto producto = Inventario.buscarPorId(id);

        if (producto == null) {
            System.out.println("Producto no encontrado");
            return;
        }

        System.out.println("Producto actual: " + producto);
        System.out.println("\n¿Qué desea modificar?");
        System.out.println("1. Nombre");
        System.out.println("2. Precio");
        System.out.println("3. Stock");

        int opcion = leerEntero("Seleccione opción: ");

        switch (opcion) {
            case 1 -> {
                scanner.nextLine();
                System.out.print("Nuevo nombre: ");
                String nuevoNombre = Utils.formatearTitleCase(scanner.nextLine());
                Inventario.modificarNombre(id, nuevoNombre);
                System.out.println("✅ Nombre actualizado");
            }
            case 2 -> {
                double nuevoPrecio = leerDouble("Nuevo precio: $");
                if (nuevoPrecio <= 0) {
                    System.out.println("El precio debe ser mayor a 0");
                    return;
                }
                Inventario.modificarPrecio(id, nuevoPrecio);
                System.out.println("✅ Precio actualizado");
            }
            case 3 -> {
                int nuevoStock = leerEntero("Nuevo stock: ");
                if (nuevoStock < 0) {
                    System.out.println("El stock no puede ser negativo");
                    return;
                }
                producto.setStock(nuevoStock);
                System.out.println("✅ Stock actualizado");
            }
            default -> System.out.println("Opción inválida");
        }
    }

    private void eliminarProducto() {
        Inventario.mostrarProductos();

        int id = leerEntero("ID del producto a eliminar: ");
        Producto producto = Inventario.buscarPorId(id);

        if (producto == null) {
            System.out.println("Producto no encontrado");
            return;
        }

        System.out.println("Producto a eliminar: " + producto);
        scanner.nextLine();
        System.out.print("¿Confirma eliminación? (s/n): ");
        String confirmacion = scanner.nextLine().toLowerCase();

        if (confirmacion.equals("s") || confirmacion.equals("si") || confirmacion.equals("sí")) {
            Inventario.eliminarProducto(id);
            System.out.println("✅ Producto eliminado correctamente");
        } else {
            System.out.println("Eliminación cancelada");
        }
    }

    private double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un número válido");
                scanner.nextLine();
            }
        }
    }
}