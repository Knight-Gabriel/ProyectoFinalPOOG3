package proyectofinalpoog3;

/**
 * Interfaz que deben implementar los productos que pueden venderse.
 * Aplica: interfaces (concepto de POO requerido).
 */
public interface Vendible {
    /**
     * Calcula el precio final del producto (puede incluir descuentos, IGV, etc.)
     */
    double calcularPrecioFinal();

    /**
     * Verifica si el producto está disponible para la venta.
     */
    boolean estaDisponible();
}
