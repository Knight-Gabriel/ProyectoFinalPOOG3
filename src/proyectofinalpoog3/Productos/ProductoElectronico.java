package proyectofinalpoog3.Productos;

import proyectofinalpoog3.Producto;
import proyectofinalpoog3.Vendible;

/**
 * Producto electrónico con garantía.
 * Aplica: herencia + polimorfismo (sobreescribe getDetallesEspecificos).
 */
public class ProductoElectronico extends Producto implements Vendible {
    private int mesesGarantia;
    private String marca;

    public ProductoElectronico(String id, String nombre, double precio, int stock,
                               int mesesGarantia, String marca) {
        super(id, nombre, precio, stock);
        this.mesesGarantia = mesesGarantia;
        this.marca = marca;
    }

    public int getMesesGarantia() { return mesesGarantia; }
    public String getMarca()      { return marca; }

    @Override
    public String getDetallesEspecificos() {
        return "Marca: " + marca + " | Garantía: " + mesesGarantia + " meses";
    }

    @Override
    public double calcularPrecioFinal() {
        // Electrónicos llevan 18% IGV
        return getPrecio() * 1.18;
    }

    @Override
    public boolean estaDisponible() {
        return getStock() > 0;
    }
}
