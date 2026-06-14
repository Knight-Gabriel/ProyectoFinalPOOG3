package proyectofinalpoog3;

/**
 * Clase abstracta base para todos los tipos de producto del minimarket.
 * Aplica: encapsulamiento, abstracción, herencia (clase padre).
 */
public abstract class Producto {
    private String id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // ── Getters ──────────────────────────────────────────────
    public String getId()      { return id; }
    public String getNombre()  { return nombre; }
    public double getPrecio()  { return precio; }
    public int    getStock()   { return stock; }

    // ── Setters ──────────────────────────────────────────────
    public void setNombre(String nombre)  { this.nombre = nombre; }
    public void setPrecio(double precio)  { this.precio = precio; }
    public void setStock(int stock)       { this.stock = stock; }

    /**
     * Método abstracto: cada subclase devuelve sus detalles específicos.
     * Aplica: polimorfismo + método abstracto.
     */
    public abstract String getDetallesEspecificos();

    /**
     * Reduce el stock al realizar una venta.
     * @return true si había suficiente stock, false si no.
     */
    public boolean reducirStock(int cantidad) {
        if (cantidad > stock) return false;
        this.stock -= cantidad;
        return true;
    }

    /**
     * Muestra la información completa del producto.
     * Aplica: polimorfismo — cada subclase muestra sus propios detalles.
     */
    public void mostrarDatos() {
        System.out.println("==============================");
        System.out.println("ID       : " + id);
        System.out.println("Nombre   : " + nombre);
        System.out.printf ("Precio   : S/ %.2f%n", precio);
        System.out.println("Stock    : " + stock + " unidades");
        System.out.println("Detalles : " + getDetallesEspecificos());
        System.out.println("==============================");
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - S/ %.2f (Stock: %d)", id, nombre, precio, stock);
    }
}
