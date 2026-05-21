/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinalpoog3;

/**
 *
 * @author Dueli
 */
public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int stock;
    private String categoria;

    public Producto(int id, String nombre, double precio, int stock, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }
    
    public void mostrarDatos() {
    System.out.println("ID: " + id);
    System.out.println("Nombre: " + nombre);
    System.out.println("Precio: " + precio);
    System.out.println("Stock: " + stock);
    System.out.println("Categoria: " + categoria);
    }
    
    public void ActualizarStock(){
        
    }
    public void EditarProducto(){
        
    }
    
    
    
    
}
