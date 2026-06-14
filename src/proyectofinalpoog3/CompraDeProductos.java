package proyectofinalpoog3;

import java.util.ArrayList;
import java.util.Date;

/**
 * Representa una compra realizada por un cliente.
 * Aplica: relación de composición con Producto (tiene una lista de productos).
 *         relación de asociación con Cliente.
 *         uso de colecciones (ArrayList).
 */
public class CompraDeProductos {

    // Clase interna para guardar producto + cantidad comprada
    public static class LineaDeCompra {
        private Producto producto;
        private int cantidad;

        public LineaDeCompra(Producto producto, int cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
        }

        public Producto getProducto() { return producto; }
        public int getCantidad()      { return cantidad; }

        public double getSubtotal() {
            return producto.getPrecio() * cantidad;
        }

        @Override
        public String toString() {
            return String.format("%-25s x%d  S/ %.2f",
                    producto.getNombre(), cantidad, getSubtotal());
        }
    }

    // ── Atributos ─────────────────────────────────────────────
    private static int contadorId = 1;

    private int    idCompra;
    private Cliente cliente;
    private ArrayList<LineaDeCompra> lineas;
    private Date   fecha;

    public CompraDeProductos(Cliente cliente) {
        this.idCompra = contadorId++;
        this.cliente  = cliente;
        this.lineas   = new ArrayList<>();
        this.fecha    = new Date();
    }

    // ── Métodos ───────────────────────────────────────────────

    /**
     * Agrega un producto a la compra y reduce el stock del inventario.
     * @return true si se agregó correctamente, false si no hay stock suficiente.
     */
    public boolean agregarProducto(Producto producto, int cantidad) {
        if (!producto.reducirStock(cantidad)) {
            System.out.println("⚠ Stock insuficiente para: " + producto.getNombre());
            return false;
        }
        lineas.add(new LineaDeCompra(producto, cantidad));
        return true;
    }

    /**
     * Calcula el total de la compra.
     */
    public double calcularTotal() {
        double total = 0;
        for (LineaDeCompra linea : lineas) {
            total += linea.getSubtotal();
        }
        return total;
    }

    public ArrayList<LineaDeCompra> getLineas() { return lineas; }
    public Cliente getCliente()                 { return cliente; }
    public int getIdCompra()                    { return idCompra; }
    public Date getFecha()                      { return fecha; }

    public boolean estaVacia() {
        return lineas.isEmpty();
    }

    /**
     * Imprime el ticket de la compra en consola.
     */
    public void imprimirTicket() {
        System.out.println("\n========================================");
        System.out.println("       MINIMARKET - TICKET #" + idCompra);
        System.out.println("========================================");
        System.out.println("Cliente : " + cliente.getNombre());
        System.out.println("DNI     : " + cliente.getDni());
        System.out.println("Fecha   : " + fecha);
        System.out.println("----------------------------------------");
        for (LineaDeCompra linea : lineas) {
            System.out.println(linea);
        }
        System.out.println("----------------------------------------");
        System.out.printf("TOTAL           : S/ %.2f%n", calcularTotal());
        System.out.println("========================================\n");
    }
}
