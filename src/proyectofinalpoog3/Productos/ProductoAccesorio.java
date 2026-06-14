package proyectofinalpoog3.Productos;

import proyectofinalpoog3.Producto;
import proyectofinalpoog3.Vendible;

/**
 * Producto de tipo accesorio (bolsas, útiles, artículos del hogar, etc.)
 * Aplica: herencia + polimorfismo.
 */
public class ProductoAccesorio extends Producto implements Vendible {
    private String material;
    private String color;

    public ProductoAccesorio(String id, String nombre, double precio, int stock,
                             String material, String color) {
        super(id, nombre, precio, stock);
        this.material = material;
        this.color = color;
    }

    public String getMaterial() { return material; }
    public String getColor()    { return color; }

    @Override
    public String getDetallesEspecificos() {
        return "Material: " + material + " | Color: " + color;
    }

    @Override
    public double calcularPrecioFinal() {
        return getPrecio();
    }

    @Override
    public boolean estaDisponible() {
        return getStock() > 0;
    }
}
