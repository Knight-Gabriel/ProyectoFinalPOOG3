package proyectofinalpoog3;

import java.util.ArrayList;

/**
 * Gestiona el inventario de productos del minimarket.
 * Aplica: relación de agregación con Producto (administra la colección).
 *         uso de colecciones (ArrayList) + polimorfismo (trabaja con Producto base).
 */
public class GestionDeProductos {

    private ArrayList<Producto> inventario;

    public GestionDeProductos() {
        this.inventario = new ArrayList<>();
    }

    // ── CRUD básico ───────────────────────────────────────────

    /**
     * Agrega un nuevo producto al inventario.
     */
    public void agregarProducto(Producto producto) {
        for (Producto p : inventario) {
            if (p.getId().equals(producto.getId())) {
                System.out.println("⚠ Ya existe un producto con ID: " + producto.getId());
                return;
            }
        }
        inventario.add(producto);
        System.out.println("✔ Producto agregado: " + producto.getNombre());
    }

    /**
     * Busca un producto por su ID.
     * @return el producto encontrado o null.
     */
    public Producto buscarPorId(String id) {
        for (Producto p : inventario) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    /**
     * Busca productos por nombre (búsqueda parcial, sin distinguir mayúsculas).
     */
    public ArrayList<Producto> buscarPorNombre(String nombre) {
        ArrayList<Producto> resultado = new ArrayList<>();
        for (Producto p : inventario) {
            if (p.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    /**
     * Edita el nombre y precio de un producto existente.
     */
    public void editarProducto(String id, String nuevoNombre, double nuevoPrecio) {
        Producto p = buscarPorId(id);
        if (p == null) {
            System.out.println("✘ Producto no encontrado con ID: " + id);
            return;
        }
        p.setNombre(nuevoNombre);
        p.setPrecio(nuevoPrecio);
        System.out.println("✔ Producto actualizado: " + p);
    }

    /**
     * Elimina un producto del inventario por ID.
     */
    public void eliminarProducto(String id) {
        Producto p = buscarPorId(id);
        if (p != null) {
            inventario.remove(p);
            System.out.println("✔ Producto eliminado: " + p.getNombre());
        } else {
            System.out.println("✘ Producto no encontrado con ID: " + id);
        }
    }

    // ── Stock ─────────────────────────────────────────────────

    /**
     * Actualiza (suma) el stock de un producto.
     */
    public void actualizarStock(String id, int cantidad) {
        Producto p = buscarPorId(id);
        if (p == null) {
            System.out.println("✘ Producto no encontrado: " + id);
            return;
        }
        p.setStock(p.getStock() + cantidad);
        System.out.println("✔ Stock actualizado. " + p.getNombre() + " → " + p.getStock() + " unidades");
    }

    /**
     * Devuelve lista de productos con stock bajo (menos de 5 unidades).
     */
    public ArrayList<Producto> getProductosStockBajo() {
        ArrayList<Producto> bajos = new ArrayList<>();
        for (Producto p : inventario) {
            if (p.getStock() < 5) bajos.add(p);
        }
        return bajos;
    }

    // ── Mostrar inventario ────────────────────────────────────

    public void mostrarInventario() {
        if (inventario.isEmpty()) {
            System.out.println("El inventario está vacío.");
            return;
        }
        System.out.println("\n========== INVENTARIO ==========");
        for (Producto p : inventario) {
            p.mostrarDatos();   // polimorfismo: cada producto muestra sus propios detalles
        }
        System.out.println("Total de productos: " + inventario.size());
    }

    public ArrayList<Producto> getInventario() { return inventario; }
}
