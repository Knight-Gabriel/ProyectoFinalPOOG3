package proyectofinalpoog3;

/**
 * Representa un cliente del minimarket.
 * Aplica: encapsulamiento (atributos privados + getters/setters).
 * Relación: Cliente tiene ComprasDeProductos (asociación).
 */
public class Cliente {
    private int    dni;
    private String nombre;
    private int    edad;
    private String telefono;
    private int    totalCompras; // contador de compras realizadas

    public Cliente(int dni, String nombre, int edad, String telefono) {
        this.dni         = dni;
        this.nombre      = nombre;
        this.edad        = edad;
        this.telefono    = telefono;
        this.totalCompras = 0;
    }

    // ── Getters ──────────────────────────────────────────────
    public int    getDni()          { return dni; }
    public String getNombre()       { return nombre; }
    public int    getEdad()         { return edad; }
    public String getTelefono()     { return telefono; }
    public int    getTotalCompras() { return totalCompras; }

    // ── Setters ──────────────────────────────────────────────
    public void setNombre(String nombre)    { this.nombre = nombre; }
    public void setTelefono(String telefono){ this.telefono = telefono; }

    /**
     * Registra una compra realizada por el cliente.
     */
    public void registrarCompra() {
        this.totalCompras++;
    }

    /**
     * Indica si el cliente es frecuente (más de 5 compras).
     */
    public boolean esFrecuente() {
        return totalCompras > 5;
    }

    public void mostrarDatos() {
        System.out.println("==============================");
        System.out.println("DNI      : " + dni);
        System.out.println("Nombre   : " + nombre);
        System.out.println("Edad     : " + edad);
        System.out.println("Teléfono : " + telefono);
        System.out.println("Compras  : " + totalCompras);
        System.out.println("Frecuente: " + (esFrecuente() ? "Sí ⭐" : "No"));
        System.out.println("==============================");
    }

    @Override
    public String toString() {
        return String.format("Cliente[DNI:%d | %s | Compras:%d]", dni, nombre, totalCompras);
    }
}
