package proyectofinalpoog3;

/**
 * Clase principal - arranca la interfaz gráfica del minimarket.
 */


import com.formdev.flatlaf.themes.FlatMacDarkLaf;

public class ProyectoFinalPOOG3 {
    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(new FlatMacDarkLaf());
        } catch (Exception ex) {
            System.err.println("No se pudo aplicar FlatLaf: " + ex.getMessage());
        }

        ConexionBD.inicializar();
        java.awt.EventQueue.invokeLater(() -> new FormularioGeneralUpdate().setVisible(true));
    }
}