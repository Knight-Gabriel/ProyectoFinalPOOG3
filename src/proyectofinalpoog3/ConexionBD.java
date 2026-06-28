package proyectofinalpoog3;

import java.sql.*;
import java.util.ArrayList;

/**
 * Clase para manejar la conexión a SQLite.
 * Proporciona métodos CRUD para productos, clientes y compras.
 * 
 * Dependencias: sqlite-jdbc-3.x.x.jar
 * Descargar desde: https://github.com/xerial/sqlite-jdbc/releases
 */
public class ConexionBD {
    
    private static final String URL = "jdbc:sqlite:minimarket.db";
    private static Connection conexion;
    
    /**
     * Inicializa la conexión a la BD y crea las tablas si no existen.
     */
    public static void inicializar() {
        try {
            // Cargar driver de SQLite
            Class.forName("org.sqlite.JDBC");
            
            // Crear conexión
            conexion = DriverManager.getConnection(URL);
            System.out.println("✔ Conexión a SQLite establecida: " + URL);
            
            // Crear tablas si no existen
            crearTablas();
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver SQLite no encontrado. Descarga sqlite-jdbc desde:");
            System.err.println("   https://github.com/xerial/sqlite-jdbc/releases");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a SQLite: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Crea las tablas necesarias si no existen.
     */
    private static void crearTablas() {
        String[] sqlTablas = {
            // Tabla de Productos
            "CREATE TABLE IF NOT EXISTS productos (" +
            "  id_producto VARCHAR(50) PRIMARY KEY," +
            "  nombre VARCHAR(100) NOT NULL," +
            "  precio DECIMAL(10,2) NOT NULL," +
            "  stock INTEGER NOT NULL," +
            "  tipo VARCHAR(30)," + // ELECTRONICO, ALIMENTO, ACCESORIO
            "  detalles TEXT," +
            "  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ")",
            
            // Tabla de Clientes
            "CREATE TABLE IF NOT EXISTS clientes (" +
            "  dni INTEGER PRIMARY KEY," +
            "  nombre VARCHAR(100) NOT NULL," +
            "  edad INTEGER," +
            "  telefono VARCHAR(20)," +
            "  total_compras INTEGER DEFAULT 0," +
            "  fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ")",
            
            // Tabla de Compras
            "CREATE TABLE IF NOT EXISTS compras (" +
            "  id_compra INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  dni_cliente INTEGER NOT NULL," +
            "  fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "  total DECIMAL(10,2)," +
            "  estado VARCHAR(20) DEFAULT 'completada'," +
            "  FOREIGN KEY (dni_cliente) REFERENCES clientes(dni)" +
            ")",
            
            // Tabla de Líneas de Compra
            "CREATE TABLE IF NOT EXISTS lineas_compra (" +
            "  id_linea INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  id_compra INTEGER NOT NULL," +
            "  id_producto VARCHAR(50) NOT NULL," +
            "  cantidad INTEGER," +
            "  precio_unitario DECIMAL(10,2)," +
            "  FOREIGN KEY (id_compra) REFERENCES compras(id_compra)," +
            "  FOREIGN KEY (id_producto) REFERENCES productos(id_producto)" +
            ")"
        };
        
        try (Statement stmt = conexion.createStatement()) {
            for (String sql : sqlTablas) {
                stmt.execute(sql);
            }
            System.out.println("✔ Tablas de BD inicializadas correctamente");
        } catch (SQLException e) {
            System.err.println("❌ Error al crear tablas: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // ════════════════════════════════════════════════════════════
    // ══ MÉTODOS PARA PRODUCTOS
    // ════════════════════════════════════════════════════════════
    
    /**
     * Inserta un nuevo producto en la BD.
     */
    public static boolean insertarProducto(String id, String nombre, double precio, 
                                           int stock, String tipo, String detalles) {
        String sql = "INSERT INTO productos (id_producto, nombre, precio, stock, tipo, detalles) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, nombre);
            pstmt.setDouble(3, precio);
            pstmt.setInt(4, stock);
            pstmt.setString(5, tipo);
            pstmt.setString(6, detalles);
            pstmt.executeUpdate();
            System.out.println("✔ Producto insertado: " + nombre);
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar producto: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene todos los productos de la BD.
     */
    public static ArrayList<Object[]> obtenerProductos() {
        ArrayList<Object[]> productos = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, precio, stock, tipo FROM productos";
        
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                productos.add(new Object[]{
                    rs.getString("id_producto"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getInt("stock"),
                    rs.getString("tipo")
                });
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener productos: " + e.getMessage());
        }
        return productos;
    }
    
    /**
     * Busca un producto por ID.
     */
    public static Object[] buscarProductoPorId(String id) {
        String sql = "SELECT * FROM productos WHERE id_producto = ?";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Object[]{
                    rs.getString("id_producto"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getInt("stock"),
                    rs.getString("tipo"),
                    rs.getString("detalles")
                };
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar producto: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Actualiza un producto.
     */
    public static boolean actualizarProducto(String id, String nombre, double precio, 
                                             int stock, String detalles) {
        String sql = "UPDATE productos SET nombre = ?, precio = ?, stock = ?, detalles = ? " +
                    "WHERE id_producto = ?";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setDouble(2, precio);
            pstmt.setInt(3, stock);
            pstmt.setString(4, detalles);
            pstmt.setString(5, id);
            pstmt.executeUpdate();
            System.out.println("✔ Producto actualizado: " + nombre);
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Reduce el stock de un producto.
     */
    public static boolean reducirStock(String idProducto, int cantidad) {
        String sql = "UPDATE productos SET stock = stock - ? WHERE id_producto = ? AND stock >= ?";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, cantidad);
            pstmt.setString(2, idProducto);
            pstmt.setInt(3, cantidad);
            int filasActualizadas = pstmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al reducir stock: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina un producto.
     */
    public static boolean eliminarProducto(String id) {
        String sql = "DELETE FROM productos WHERE id_producto = ?";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            System.out.println("✔ Producto eliminado");
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
    
    // ════════════════════════════════════════════════════════════
    // ══ MÉTODOS PARA CLIENTES
    // ════════════════════════════════════════════════════════════
    
    /**
     * Inserta un nuevo cliente en la BD.
     */
    public static boolean insertarCliente(int dni, String nombre, int edad, String telefono) {
        String sql = "INSERT INTO clientes (dni, nombre, edad, telefono, total_compras) " +
                    "VALUES (?, ?, ?, ?, 0)";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, dni);
            pstmt.setString(2, nombre);
            pstmt.setInt(3, edad);
            pstmt.setString(4, telefono);
            pstmt.executeUpdate();
            System.out.println("✔ Cliente registrado: " + nombre);
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene todos los clientes.
     */
    public static ArrayList<Object[]> obtenerClientes() {
        ArrayList<Object[]> clientes = new ArrayList<>();
        String sql = "SELECT dni, nombre, edad, telefono, total_compras FROM clientes";
        
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                clientes.add(new Object[]{
                    rs.getInt("dni"),
                    rs.getString("nombre"),
                    rs.getInt("edad"),
                    rs.getString("telefono"),
                    rs.getInt("total_compras")
                });
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener clientes: " + e.getMessage());
        }
        return clientes;
    }
    
    /**
     * Busca un cliente por DNI.
     */
    public static Object[] buscarClientePorDni(int dni) {
        String sql = "SELECT * FROM clientes WHERE dni = ?";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, dni);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Object[]{
                    rs.getInt("dni"),
                    rs.getString("nombre"),
                    rs.getInt("edad"),
                    rs.getString("telefono"),
                    rs.getInt("total_compras")
                };
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar cliente: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Incrementa el contador de compras del cliente.
     */
    public static boolean incrementarComprasCliente(int dni) {
        String sql = "UPDATE clientes SET total_compras = total_compras + 1 WHERE dni = ?";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, dni);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al incrementar compras: " + e.getMessage());
            return false;
        }
    }
    
    // ════════════════════════════════════════════════════════════
    // ══ MÉTODOS PARA COMPRAS
    // ════════════════════════════════════════════════════════════
    
    /**
     * Inserta una compra y retorna su ID.
     */
    public static int insertarCompra(int dniCliente, double total) {
        String sql = "INSERT INTO compras (dni_cliente, total) VALUES (?, ?)";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, dniCliente);
            pstmt.setDouble(2, total);
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar compra: " + e.getMessage());
        }
        return -1;
    }
    
    /**
     * Inserta una línea de compra.
     */
    public static boolean insertarLineaCompra(int idCompra, String idProducto, 
                                              int cantidad, double precioUnitario) {
        String sql = "INSERT INTO lineas_compra (id_compra, id_producto, cantidad, precio_unitario) " +
                    "VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idCompra);
            pstmt.setString(2, idProducto);
            pstmt.setInt(3, cantidad);
            pstmt.setDouble(4, precioUnitario);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar línea de compra: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene todas las compras.
     */
    public static ArrayList<Object[]> obtenerCompras() {
        ArrayList<Object[]> compras = new ArrayList<>();
        String sql = "SELECT c.id_compra, c.dni_cliente, cl.nombre, c.fecha, c.total " +
                    "FROM compras c JOIN clientes cl ON c.dni_cliente = cl.dni " +
                    "ORDER BY c.fecha DESC";
        
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                compras.add(new Object[]{
                    rs.getInt("id_compra"),
                    rs.getInt("dni_cliente"),
                    rs.getString("nombre"),
                    rs.getString("fecha"),
                    rs.getDouble("total")
                });
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener compras: " + e.getMessage());
        }
        return compras;
    }
    
    /**
     * Obtiene las líneas de una compra específica.
     */
    public static ArrayList<Object[]> obtenerLineasCompra(int idCompra) {
        ArrayList<Object[]> lineas = new ArrayList<>();
        String sql = "SELECT lc.id_linea, p.nombre, lc.cantidad, lc.precio_unitario, " +
                    "(lc.cantidad * lc.precio_unitario) as subtotal " +
                    "FROM lineas_compra lc JOIN productos p ON lc.id_producto = p.id_producto " +
                    "WHERE lc.id_compra = ?";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idCompra);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                lineas.add(new Object[]{
                    rs.getInt("id_linea"),
                    rs.getString("nombre"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio_unitario"),
                    rs.getDouble("subtotal")
                });
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener líneas de compra: " + e.getMessage());
        }
        return lineas;
    }
    
    // ════════════════════════════════════════════════════════════
    // ══ MÉTODOS DE REPORTES
    // ════════════════════════════════════════════════════════════
    
    /**
     * Calcula el total de ventas.
     */
    public static double obtenerTotalVentas() {
        String sql = "SELECT COALESCE(SUM(total), 0) as total FROM compras";
        
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al calcular total: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Obtiene productos con stock bajo.
     */
    public static ArrayList<Object[]> obtenerProductosStockBajo(int limite) {
        ArrayList<Object[]> productos = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, stock FROM productos WHERE stock < ? " +
                    "ORDER BY stock ASC";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, limite);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                productos.add(new Object[]{
                    rs.getString("id_producto"),
                    rs.getString("nombre"),
                    rs.getInt("stock")
                });
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener productos con stock bajo: " + e.getMessage());
        }
        return productos;
    }
    
    /**
     * Cierra la conexión a la BD.
     */
    public static void cerrar() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("✔ Conexión a SQLite cerrada");
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}
