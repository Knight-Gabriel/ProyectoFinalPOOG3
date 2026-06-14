package proyectofinalpoog3;

import proyectofinalpoog3.Productos.ProductoAlimento;
import proyectofinalpoog3.Productos.ProductoElectronico;
import proyectofinalpoog3.Productos.ProductoAccesorio;

/**
 * Clase principal del sistema de minimarket.
 * Demuestra el funcionamiento completo del sistema.
 *
 * Conceptos de POO aplicados:
 *  - Encapsulamiento   : atributos privados + getters/setters en todas las clases
 *  - Herencia          : Producto <- ProductoAlimento, ProductoElectronico, ProductoAccesorio
 *  - Polimorfismo      : mostrarDatos() y getDetallesEspecificos() se comportan distinto en cada subclase
 *  - Abstracción       : Producto es clase abstracta; Vendible es interfaz
 *  - Relaciones        : GestionDeProductos agrega Productos; CompraDeProductos asocia Cliente con Productos
 */
public class ProyectoFinalPOOG3 {

    public static void main(String[] args) {

        // ── 1. Crear el inventario ─────────────────────────────────────
        GestionDeProductos inventario = new GestionDeProductos();

        // Polimorfismo: todos son Producto, pero cada uno es de un tipo diferente
        inventario.agregarProducto(new ProductoAlimento(
                "A001", "Leche Gloria 1L", 4.50, 30, "31/12/2025", true));
        inventario.agregarProducto(new ProductoAlimento(
                "A002", "Pan de molde", 6.90, 15, "15/07/2025", false));
        inventario.agregarProducto(new ProductoElectronico(
                "E001", "Audífonos Bluetooth", 89.90, 10, 12, "Sony"));
        inventario.agregarProducto(new ProductoElectronico(
                "E002", "Cargador USB-C", 25.00, 20, 6, "Samsung"));
        inventario.agregarProducto(new ProductoAccesorio(
                "AC001", "Bolsa de tela", 3.50, 50, "Algodón", "Azul"));

        // ── 2. Mostrar inventario completo ────────────────────────────
        inventario.mostrarInventario();

        // ── 3. Crear clientes ─────────────────────────────────────────
        Cliente cliente1 = new Cliente(12345678, "María García", 32, "987654321");
        Cliente cliente2 = new Cliente(87654321, "Carlos Pérez", 45, "912345678");

        // ── 4. Realizar compras ───────────────────────────────────────
        ReportesDeVentas reportes = new ReportesDeVentas();

        // Compra 1 — María
        CompraDeProductos compra1 = new CompraDeProductos(cliente1);
        compra1.agregarProducto(inventario.buscarPorId("A001"), 2); // 2 leches
        compra1.agregarProducto(inventario.buscarPorId("E001"), 1); // 1 audífono
        compra1.agregarProducto(inventario.buscarPorId("AC001"), 3); // 3 bolsas
        compra1.imprimirTicket();
        reportes.registrarVenta(compra1);

        // Compra 2 — Carlos
        CompraDeProductos compra2 = new CompraDeProductos(cliente2);
        compra2.agregarProducto(inventario.buscarPorId("A002"), 1);
        compra2.agregarProducto(inventario.buscarPorId("E002"), 2);
        compra2.imprimirTicket();
        reportes.registrarVenta(compra2);

        // ── 5. Generar reportes ───────────────────────────────────────
        reportes.mostrarResumenVentas();

        // ── 6. Ver stock bajo ─────────────────────────────────────────
        System.out.println("===== PRODUCTOS CON STOCK BAJO =====");
        for (Producto p : inventario.getProductosStockBajo()) {
            System.out.println("⚠ " + p);
        }

        // ── 7. Actualizar stock ───────────────────────────────────────
        inventario.actualizarStock("A001", 20);

        // ── 8. Buscar producto ────────────────────────────────────────
        System.out.println("\n===== BÚSQUEDA: 'usb' =====");
        for (Producto p : inventario.buscarPorNombre("usb")) {
            p.mostrarDatos();
        }

        // ── 9. Editar producto ────────────────────────────────────────
        inventario.editarProducto("AC001", "Bolsa ecológica grande", 4.00);
    }
}
