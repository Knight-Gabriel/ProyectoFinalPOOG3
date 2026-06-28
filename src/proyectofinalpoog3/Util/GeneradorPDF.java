package proyectofinalpoog3.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase para generar boletas/facturas en PDF.
 * Usa la librería iText: https://itextpdf.com/
 * 
 * Dependencias: itextpdf-5.5.13.jar
 * Descargar: https://github.com/itext/itextpdf/releases
 * 
 * Nota: Si quieres versión gratuita, usa iText 5.5.13 o migra a iText 7 con licencia AGPL
 */

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneradorPDF {
    
    private static final String CARPETA_BOLETAS = "boletas/";
    
    static {
        // Crear carpeta si no existe
        File carpeta = new File(CARPETA_BOLETAS);
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }
    }
    
    /**
     * Genera una boleta en PDF.
     * 
     * @param idCompra Número único de la compra
     * @param nombreCliente Nombre del cliente
     * @param dniCliente DNI del cliente
     * @param lineasCompra Array con detalles: {producto, cantidad, precioUnitario}
     * @param total Monto total
     * @return Ruta del archivo generado o null si hay error
     */
    public static String generarBoleta(int idCompra, String nombreCliente, int dniCliente,
                                       String[][] lineasCompra, double total) {
        try {
            String nombreArchivo = String.format("Boleta_%06d.pdf", idCompra);
            String rutaArchivo = CARPETA_BOLETAS + nombreArchivo;
            
            // Crear documento PDF
            Document documento = new Document(PageSize.A4);
            PdfWriter.getInstance(documento, new java.io.FileOutputStream(rutaArchivo));
            documento.open();
            
            // ══ ENCABEZADO ══════════════════════════════════════════════════
            Font fontTitulo = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
            Font fontSubtitulo = new Font(Font.FontFamily.TIMES_ROMAN, 14);
            Font fontNormal = new Font(Font.FontFamily.TIMES_ROMAN, 11);
            Font fontPequeno = new Font(Font.FontFamily.TIMES_ROMAN, 9);
            
            // Título
            Paragraph titulo = new Paragraph("MINIMARKET", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            
            // Subtítulo
            Paragraph subtitulo = new Paragraph("BOLETA DE VENTA", fontSubtitulo);
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(subtitulo);
            
            // Número de boleta
            Paragraph numBoleta = new Paragraph(
                String.format("Boleta No. %06d", idCompra), fontPequeno);
            numBoleta.setAlignment(Element.ALIGN_CENTER);
            documento.add(numBoleta);
            
            documento.add(new Paragraph("\n"));
            
            // ══ DATOS DEL CLIENTE ═══════════════════════════════════════════
            documento.add(new Paragraph("DATOS DEL CLIENTE", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            
            PdfPTable tablaCliente = new PdfPTable(2);
            tablaCliente.setWidthPercentage(100);
            
            agregarCeldaTabla(tablaCliente, "Nombre:", nombreCliente, fontNormal);
            agregarCeldaTabla(tablaCliente, "DNI:", String.valueOf(dniCliente), fontNormal);
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            agregarCeldaTabla(tablaCliente, "Fecha:", sdf.format(new Date()), fontNormal);
            
            documento.add(tablaCliente);
            documento.add(new Paragraph("\n"));
            
            // ══ TABLA DE PRODUCTOS ══════════════════════════════════════════
            documento.add(new Paragraph("DETALLE DE COMPRA", 
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            
            PdfPTable tablaProductos = new PdfPTable(4);
            tablaProductos.setWidthPercentage(100);
            float[] widths = {3, 1, 1.5f, 1.5f};
            tablaProductos.setWidths(widths);
            
            // Encabezado de tabla
            agregarCeldaTablaProductos(tablaProductos, "PRODUCTO", fontNormal, true);
            agregarCeldaTablaProductos(tablaProductos, "CANTIDAD", fontNormal, true);
            agregarCeldaTablaProductos(tablaProductos, "P. UNITARIO", fontNormal, true);
            agregarCeldaTablaProductos(tablaProductos, "SUBTOTAL", fontNormal, true);
            
            // Filas de productos
            for (String[] linea : lineasCompra) {
                agregarCeldaTablaProductos(tablaProductos, linea[0], fontNormal, false);
                agregarCeldaTablaProductos(tablaProductos, linea[1], fontNormal, false);
                agregarCeldaTablaProductos(tablaProductos, "S/ " + linea[2], fontNormal, false);
                agregarCeldaTablaProductos(tablaProductos, "S/ " + linea[3], fontNormal, false);
            }
            
            documento.add(tablaProductos);
            documento.add(new Paragraph("\n"));
            
            // ══ TOTAL ═══════════════════════════════════════════════════════
            Paragraph totalParr = new Paragraph(
                String.format("TOTAL: S/ %.2f", total),
                new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD));
            totalParr.setAlignment(Element.ALIGN_RIGHT);
            documento.add(totalParr);
            
            documento.add(new Paragraph("\n"));
            
            // ══ PIE DE PÁGINA ═══════════════════════════════════════════════
            Paragraph piePagina = new Paragraph(
                "Gracias por su compra. ¡Vuelva pronto!",
                fontPequeno);
            piePagina.setAlignment(Element.ALIGN_CENTER);
            documento.add(piePagina);
            
            documento.close();
            System.out.println("✔ Boleta generada: " + rutaArchivo);
            return rutaArchivo;
            
        } catch (DocumentException e) {
            System.err.println("❌ Error de documento PDF: " + e.getMessage());
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.err.println("❌ Error al crear archivo: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Agregza celdas a la tabla de cliente.
     */
    private static void agregarCeldaTabla(PdfPTable tabla, String etiqueta, String valor, Font font) {
        PdfPCell celdaEtiqueta = new PdfPCell(new Phrase(etiqueta, new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
        celdaEtiqueta.setBackgroundColor(BaseColor.LIGHT_GRAY);
        tabla.addCell(celdaEtiqueta);
        
        PdfPCell celdaValor = new PdfPCell(new Phrase(valor, font));
        tabla.addCell(celdaValor);
    }
    
    /**
     * Agrega celdas a la tabla de productos.
     */
    private static void agregarCeldaTablaProductos(PdfPTable tabla, String contenido, 
                                                   Font font, boolean esEncabezado) {
        PdfPCell celda = new PdfPCell(new Phrase(contenido, font));
        
        if (esEncabezado) {
            celda.setBackgroundColor(BaseColor.DARK_GRAY);
            celda.setPhrase(new Phrase(contenido, new Font(Font.FontFamily.TIMES_ROMAN, 10, 
                Font.BOLD, BaseColor.WHITE)));
        }
        
        celda.setPadding(5);
        tabla.addCell(celda);
    }
    
    /**
     * Abre la boleta con el visor PDF predeterminado.
     */
    public static void abrirBoleta(String rutaArchivo) {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                // Windows
                Runtime.getRuntime().exec("cmd /c start \"\" \"" + rutaArchivo + "\"");
            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                // macOS
                Runtime.getRuntime().exec("open " + rutaArchivo);
            } else {
                // Linux
                Runtime.getRuntime().exec("xdg-open " + rutaArchivo);
            }
            System.out.println("✔ Abriendo boleta: " + rutaArchivo);
        } catch (Exception e) {
            System.err.println("❌ Error al abrir boleta: " + e.getMessage());
        }
    }
}
