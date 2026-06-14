package proyectofinalpoog3.Productos;

import proyectofinalpoog3.Producto;
import proyectofinalpoog3.Vendible;

/**
 * Producto de tipo alimento.
 * Aplica: herencia (extiende Producto) + implementación de interfaz Vendible.
 */
public class ProductoAlimento extends Producto implements Vendible {
    private String fechaVencimiento;
    private boolean refrigerado;

    public ProductoAlimento(String id, String nombre, double precio, int stock,
                            String fechaVencimiento, boolean refrigerado) {
        super(id, nombre, precio, stock);
        this.fechaVencimiento = fechaVencimiento;
        this.refrigerado = refrigerado;
    }

    public String getFechaVencimiento() { return fechaVencimiento; }
    public boolean isRefrigerado()      { return refrigerado; }

    @Override
    public String getDetallesEspecificos() {
        return "Vence: " + fechaVencimiento + " | Refrigerado: " + (refrigerado ? "Sí" : "No");
    }

    @Override
    public double calcularPrecioFinal() {
        // Los alimentos no llevan IGV adicional en minimarket
        return getPrecio();
    }

    @Override
    public boolean estaDisponible() {
        return getStock() > 0;
    }
}
