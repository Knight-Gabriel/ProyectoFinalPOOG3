package proyectofinalpoog3;

import java.util.ArrayList;

/**
 * Genera reportes de ventas del minimarket.
 * Aplica: relación de dependencia con Cliente y CompraDeProductos.
 *         uso de colecciones (ArrayList).
 */
public class ReportesDeVentas {

    private ArrayList<CompraDeProductos> historialCompras;

    public ReportesDeVentas() {
        this.historialCompras = new ArrayList<>();
    }

    /**
     * Registra una compra en el historial de ventas.
     */
    public void registrarVenta(CompraDeProductos compra) {
        historialCompras.add(compra);
        compra.getCliente().registrarCompra(); // notifica al cliente
        System.out.println("✔ Venta #" + compra.getIdCompra() + " registrada.");
    }

    /**
     * Genera un reporte de texto de una compra específica.
     * (Mantiene compatibilidad con tu código original)
     */
    public String generarReporte(Cliente cliente, CompraDeProductos compra) {
        StringBuilder reporte = new StringBuilder();
        reporte.append("==============================\n");
        reporte.append("REPORTE DE COMPRA\n");
        reporte.append("==============================\n");
        reporte.append("Cliente : ").append(cliente.getNombre()).append("\n");
        reporte.append("DNI     : ").append(cliente.getDni()).append("\n");
        reporte.append("Compra  : #").append(compra.getIdCompra()).append("\n");
        reporte.append("------------------------------\n");

        for (CompraDeProductos.LineaDeCompra linea : compra.getLineas()) {
            reporte.append("- ").append(linea.toString()).append("\n");
        }

        reporte.append("------------------------------\n");
        reporte.append(String.format("TOTAL: S/ %.2f%n", compra.calcularTotal()));
        reporte.append("==============================\n");

        return reporte.toString();
    }

    /**
     * Calcula el total de dinero vendido en todas las compras.
     */
    public double calcularTotalVendido() {
        double total = 0;
        for (CompraDeProductos c : historialCompras) {
            total += c.calcularTotal();
        }
        return total;
    }

    /**
     * Muestra el resumen general de todas las ventas.
     */
    public void mostrarResumenVentas() {
        System.out.println("\n========== REPORTE GENERAL DE VENTAS ==========");
        System.out.println("Total de ventas realizadas : " + historialCompras.size());
        System.out.printf ("Total recaudado            : S/ %.2f%n", calcularTotalVendido());
        System.out.println("------------------------------------------------");

        for (CompraDeProductos c : historialCompras) {
            System.out.printf("Venta #%d | Cliente: %-20s | Total: S/ %.2f%n",
                    c.getIdCompra(), c.getCliente().getNombre(), c.calcularTotal());
        }
        System.out.println("================================================\n");
    }

    /**
     * Muestra los clientes frecuentes (más de 5 compras).
     */
    public void mostrarClientesFrecuentes() {
        System.out.println("\n===== CLIENTES FRECUENTES ⭐ =====");
        boolean hayFrecuentes = false;
        ArrayList<String> vistos = new ArrayList<>();

        for (CompraDeProductos c : historialCompras) {
            Cliente cl = c.getCliente();
            String dni = String.valueOf(cl.getDni());
            if (cl.esFrecuente() && !vistos.contains(dni)) {
                System.out.println(cl);
                vistos.add(dni);
                hayFrecuentes = true;
            }
        }

        if (!hayFrecuentes) System.out.println("Aún no hay clientes frecuentes.");
        System.out.println("==================================\n");
    }

    public ArrayList<CompraDeProductos> getHistorial() { return historialCompras; }
}
