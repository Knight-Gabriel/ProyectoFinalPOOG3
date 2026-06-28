package proyectofinalpoog3;

/**
 * Clase principal - arranca la interfaz gráfica del minimarket.
 */
public class ProyectoFinalPOOG3 {
    public static void main(String[] args) {
        
        ConexionBD.inicializar(); // ← esta línea, antes de new FormularioGeneral()
        
        
        // Lanza la ventana principal en el hilo de eventos de Swing
        java.awt.EventQueue.invokeLater(() -> new FormularioGeneral().setVisible(true));
        
    }
}
