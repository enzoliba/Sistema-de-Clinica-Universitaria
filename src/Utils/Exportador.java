package Utils;

import Entidades.EAmbiente;
import Entidades.EHistorialMedico;
import Entidades.EHorario;
import Entidades.EPaciente;
import Entidades.E_Empleado;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import com.itextpdf.text.*;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.JOptionPane;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class Exportador{

    public static void pacienteExportarExcel(List<EPaciente> pacientes, String ruta) {
        if (!ruta.toLowerCase().endsWith(".xlsx")) {
            ruta += ".xlsx";
        }

        try (Workbook libro = new XSSFWorkbook()) {
            Sheet hoja = libro.createSheet("Pacientes");

            // Encabezados
            Row headerRow = hoja.createRow(0);
            String[] headers = {"ID", "Nombre", "Apellidos", "DNI", "Genero", "Rol", 
                               "Telefono", "Correo", "Direccion", 
                               "Fecha de Nacimiento", "Tipo de Sangre", "Fecha de Registro"};

            // Crear estilo para encabezados
            CellStyle headerStyle = libro.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont;
            headerFont = libro.createFont();
            headerStyle.setFont(headerFont);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Datos
            int numFila = 1;
            for (EPaciente p : pacientes) {
                Row fila = hoja.createRow(numFila++);
                fila.createCell(0).setCellValue(p.getIdPersona());
                fila.createCell(1).setCellValue(p.getNombre());
                fila.createCell(2).setCellValue(p.getApellidoPaterno() + " " + p.getApellidoMaterno());
                fila.createCell(3).setCellValue(p.getDni());
                fila.createCell(4).setCellValue(p.getGenero());
                fila.createCell(5).setCellValue(p.getRol().getDbValue());
                fila.createCell(6).setCellValue(p.getTelefono());
                fila.createCell(7).setCellValue(p.getCorreo());
                fila.createCell(8).setCellValue(p.getDireccion());
                fila.createCell(9).setCellValue(p.getFechaNacimiento().toString());
                fila.createCell(10).setCellValue(p.getTipoSangre().getDbValue());
                fila.createCell(11).setCellValue(p.getFechaRegistro().toString());
            }

            // Autoajustar columnas
            for (int i = 0; i < headers.length; i++) {
                hoja.autoSizeColumn(i);
            }

            // Guardar
            try (FileOutputStream outputStream = new FileOutputStream(ruta)) {
                libro.write(outputStream);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al exportar: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, "El archivo fue exportado a: " + ruta);
    }
    
    // Exportar a PDF
    public static void pacienteExportarPDF(List<EPaciente> pacientes, String ruta) {
        Document documento = new Document(PageSize.A4.rotate());
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(ruta));
            documento.open();
            
            // Título
            documento.add(new Paragraph("Reporte de Pacientes", 
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            documento.add(Chunk.NEWLINE);
            
            // Tabla
            PdfPTable tabla = new PdfPTable(12);
            tabla.setWidthPercentage(100);
            float[] anchos = {0.8f, 1.6f, 2.1f, 1.1f, 0.8f, 1.1f, 1.3f, 1.6f, 2.1f, 1.6f, 1.1f, 1.6f};
            tabla.setWidths(anchos);
            
            // Encabezados
            String[] headers = {"ID", "Nombre", "Apellidos", "DNI", "Genero", "Rol", "Telefono", "Correo", "Direccion", "Fecha de Nacimiento", "Tipo de Sangre", "Fecha de Registro"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, 
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(new BaseColor(200, 200, 200));
                tabla.addCell(cell);
            }
            
        // 7. Fuente más pequeña para datos
        com.itextpdf.text.Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
        
        // 8. Datos
        for (EPaciente p : pacientes) {
            agregarCelda(tabla, p.getIdPersona(), dataFont);
            agregarCelda(tabla, p.getNombre(), dataFont);
            agregarCelda(tabla, p.getApellidoPaterno() + " " + p.getApellidoMaterno(), dataFont);
            agregarCelda(tabla, p.getDni(), dataFont);
            agregarCelda(tabla, p.getGenero(), dataFont);
            agregarCelda(tabla, p.getRol().getDbValue(), dataFont);
            agregarCelda(tabla, p.getTelefono(), dataFont);
            agregarCelda(tabla, p.getCorreo(), dataFont);
            agregarCelda(tabla, p.getDireccion(), dataFont);
            agregarCelda(tabla, p.getFechaNacimiento().toString(), dataFont);
            agregarCelda(tabla, p.getTipoSangre().getDbValue(), dataFont);
            agregarCelda(tabla, p.getFechaRegistro().toString(), dataFont);
        }
            
            documento.add(tabla);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "El archivo no pudo ser creado.");
            return;
        } finally {
            documento.close();
        }
        JOptionPane.showMessageDialog(null, "El archivo fue descargado a: " + ruta);
    }
    
    // Método auxiliar para agregar celdas con formato
    private static void agregarCelda (PdfPTable tabla, String contenido, com.itextpdf.text.Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(contenido, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(4);
        tabla.addCell(cell);
    }
    
    //EXPORTAR AMBIENTES EN EXCEL
    public static void exportarExcelAmbientes(List<EAmbiente> ambientes, String ruta) {
    if (!ruta.toLowerCase().endsWith(".xlsx")) {
        ruta += ".xlsx";
    }

    try (Workbook libro = new XSSFWorkbook()) {
        Sheet hoja = libro.createSheet("Ambientes");
        
        // Encabezados
        Row headerRow = hoja.createRow(0);
        String[] headers = {"ID", "Nombre", "Capacidad", "Disponibilidad"};
        
        // Crear estilo para encabezados
        CellStyle headerStyle = libro.createCellStyle();
        org.apache.poi.ss.usermodel.Font headerFont;
        headerFont = libro.createFont();
        headerStyle.setFont(headerFont);
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        // Datos
        int numFila = 1;
        for (EAmbiente a : ambientes) {
            Row fila = hoja.createRow(numFila++);
            fila.createCell(0).setCellValue(a.getIdAmbiente());
            fila.createCell(1).setCellValue(a.getNombre());
            fila.createCell(2).setCellValue(a.getCapacidad());
            if(a.isDisponibilidad() == true){
                String valor = "Disponible";
                fila.createCell(3).setCellValue(valor);
            }else{
                String valor = "No Disponible";
                fila.createCell(3).setCellValue(valor);
            }
        }
        
        // Autoajustar columnas
        for (int i = 0; i < headers.length; i++) {
            hoja.autoSizeColumn(i);
        }
        
        // Guardar
        try (FileOutputStream outputStream = new FileOutputStream(ruta)) {
            libro.write(outputStream);
        }
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, 
            "Error al exportar: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    JOptionPane.showMessageDialog(null, "El archivo fue exportado a: " + ruta);
}


// EXPORTAR AMBIENTES A PDF
    public static void exportarPDFAmbientes(List<EAmbiente> ambientes, String ruta) {
        Document documento = new Document(PageSize.A4.rotate());
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(ruta));
            documento.open();
            
            // Título
            documento.add(new Paragraph("Reporte de Ambientes", 
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            documento.add(Chunk.NEWLINE);
            
            // Tabla
            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            float[] anchos = {0.8f, 1.6f, 2.1f, 2.1f};
            tabla.setWidths(anchos);
            
            // Encabezados
            String[] headers = {"ID", "Nombre", "Capacidad", "Disponibilidad"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, 
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(new BaseColor(200, 200, 200));
                tabla.addCell(cell);
            }
            
        // 7. Fuente más pequeña para datos
        com.itextpdf.text.Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
        
        // 8. Datos
        for (EAmbiente a : ambientes) {
            agregarCelda(tabla, a.getIdAmbiente(), dataFont);
            agregarCelda(tabla, a.getNombre(), dataFont);
            agregarCelda(tabla, String.valueOf(a.getCapacidad()) , dataFont);
            if(a.isDisponibilidad() == true){
                String valor = "Disponible";
                agregarCelda(tabla, valor, dataFont);
            }else{
                String valor = "No Disponible";
                agregarCelda(tabla, valor, dataFont);
            }
        }
            
            documento.add(tabla);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "El archivo no pudo ser creado.");
            return;
        } finally {
            documento.close();
        }
        JOptionPane.showMessageDialog(null, "El archivo fue descargado a: " + ruta);
    }
    
       //EXPORTAR HISTORIAL PDF ->>
    
public static void HistorialExportarPDF(List<EHistorialMedico> Historial, String ruta) {
    Document documento = new Document(PageSize.A4.rotate());
    try {
        PdfWriter.getInstance(documento, new FileOutputStream(ruta));
        documento.open();

        // Título
        documento.add(new Paragraph("Reporte de Pacientes", 
            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
        documento.add(Chunk.NEWLINE);

        // Tabla con 10 columnas
        PdfPTable tabla = new PdfPTable(10);
        tabla.setWidthPercentage(100);
        float[] anchos = {0.8f, 2.1f, 1.1f, 1.1f, 1.6f, 1.1f, 1.6f, 1.6f, 2.1f, 1.2f};
        tabla.setWidths(anchos);

        // Encabezados
        String[] headers = {
            "Código", "DNI del Paciente", "Rol Paciente", "Teléfono",
            "Fecha de Creación", "Tipo Sangre", "Alergias", 
            "Condiciones Crónicas", "Medicamentos Frecuentes", "Nro. Atenciones"
        };
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header,
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(new BaseColor(200, 200, 200));
            tabla.addCell(cell);
        }

        // Fuente para los datos
        com.itextpdf.text.Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);

        // Filas de datos
        for (EHistorialMedico Gaudy : Historial) {
            agregarCelda(tabla, String.valueOf(Gaudy.getIdHistorial()), dataFont);
            agregarCelda(tabla, Gaudy.getPaciente() != null ? Gaudy.getPaciente().getDni() : "", dataFont);
            agregarCelda(tabla, (Gaudy.getPaciente() != null && Gaudy.getPaciente().getRol() != null) ? 
                Gaudy.getPaciente().getRol().getDbValue() : "", dataFont);
            agregarCelda(tabla, Gaudy.getPaciente() != null ? Gaudy.getPaciente().getTelefono() : "", dataFont);
            agregarCelda(tabla, Gaudy.getFechaCreacion() != null ? Gaudy.getFechaCreacion().toString() : "", dataFont);
            agregarCelda(tabla, (Gaudy.getPaciente() != null && Gaudy.getPaciente().getTipoSangre() != null) ?
                Gaudy.getPaciente().getTipoSangre().getDbValue() : "", dataFont);
            agregarCelda(tabla, Gaudy.getAlergias() != null ? Gaudy.getAlergias() : "", dataFont);
            agregarCelda(tabla, Gaudy.getCondicionesCronicas() != null ? Gaudy.getCondicionesCronicas() : "", dataFont);
            agregarCelda(tabla, Gaudy.getMedicamentosFrecuentes() != null ? Gaudy.getMedicamentosFrecuentes() : "N/A", dataFont);
            agregarCelda(tabla, Gaudy.getAtenciones() != null ? String.valueOf(Gaudy.getAtenciones().size()) : "0", dataFont);
        }

        documento.add(tabla);
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "El archivo no pudo ser creado.");
        return;
    } finally {
        documento.close();
    }
    JOptionPane.showMessageDialog(null, "El archivo fue descargado a: " + ruta);
}

//EXPORTAR PARA HISTORIAL EN EXCEL ->>
public static void exportarExcelHistorial(List<EHistorialMedico> historial, String ruta) {
    if (!ruta.toLowerCase().endsWith(".xlsx")) {
        ruta += ".xlsx";
    }

    try (Workbook libro = new XSSFWorkbook()) {
        Sheet hoja = libro.createSheet("Historial Médico");

        // Encabezados
        Row headerRow = hoja.createRow(0);
        String[] headers = {
            "ID", "Fecha Creación", "DNI Paciente", "Rol", "Teléfono", 
            "Tipo de Sangre", "Alergias", "Condiciones Crónicas", 
            "Medicamentos Frecuentes", "Nro. Atenciones"
        };

        // Estilo de encabezado
        CellStyle headerStyle = libro.createCellStyle();
        org.apache.poi.ss.usermodel.Font headerFont = libro.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Datos
        int numFila = 1;
        for (EHistorialMedico gaudy : historial) {
            Row fila = hoja.createRow(numFila++);
            fila.createCell(0).setCellValue(gaudy.getIdHistorial());
            fila.createCell(1).setCellValue(gaudy.getFechaCreacion().toString());
            fila.createCell(2).setCellValue(gaudy.getPaciente().getDni());
            fila.createCell(3).setCellValue(gaudy.getPaciente().getRol().getDbValue());
            fila.createCell(4).setCellValue(gaudy.getPaciente().getTelefono());
            fila.createCell(5).setCellValue(gaudy.getPaciente().getTipoSangre().getDbValue());
            fila.createCell(6).setCellValue(gaudy.getAlergias() != null ? gaudy.getAlergias() : "N/A");
            fila.createCell(7).setCellValue(gaudy.getCondicionesCronicas() != null ? gaudy.getCondicionesCronicas() : "N/A");
            fila.createCell(8).setCellValue(gaudy.getMedicamentosFrecuentes() != null ? gaudy.getMedicamentosFrecuentes() : "N/A");
            fila.createCell(9).setCellValue(gaudy.getAtenciones() != null ? gaudy.getAtenciones().size() : 0);
        }

        // Autoajustar columnas
        for (int i = 0; i < headers.length; i++) {
            hoja.autoSizeColumn(i);
        }

        // Guardar archivo
        try (FileOutputStream outputStream = new FileOutputStream(ruta)) {
            libro.write(outputStream);
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null,
            "Error al exportar: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
    }

    JOptionPane.showMessageDialog(null, "El archivo fue exportado a: " + ruta);
}


    //Para exportar PDF Empleado
    public static void empleadoExportarPDF(List<E_Empleado> empleados, String ruta) {
        Document documento = new Document(PageSize.A4.rotate());
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(ruta));
            documento.open();
            
            // Título
            documento.add(new Paragraph("Reporte de Empleados", 
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            documento.add(Chunk.NEWLINE);
            
            // Tabla
            PdfPTable tabla = new PdfPTable(12);
            tabla.setWidthPercentage(100);
            float[] anchos = {0.8f, 1.6f, 2.1f, 1.1f, 0.8f, 1.1f, 1.3f, 1.6f, 2.1f, 1.6f, 1.1f, 1.6f};
            tabla.setWidths(anchos);
            
            // Encabezados
            String[] headers = {"ID", "Nombre", "Apellidos", "DNI", "Genero", "Rol", "Telefono", "Correo", "Direccion", "Fecha de Nacimiento", "Horario", "Especialidad"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, 
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(new BaseColor(200, 200, 200));
                tabla.addCell(cell);
            }
            
        // 7. Fuente más pequeña para datos
        com.itextpdf.text.Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
        
        // 8. Datos
        for (E_Empleado p : empleados) {
            String especialidad;
            if( p.getEspecialidad() == null ){
                especialidad = "No tiene";
            }else{
                especialidad = p.getEspecialidad().getNombre();
            }
            agregarCelda(tabla, p.getIdPersona(), dataFont);
            agregarCelda(tabla, p.getNombre(), dataFont);
            agregarCelda(tabla, p.getApellidoPaterno() + " " + p.getApellidoMaterno(), dataFont);
            agregarCelda(tabla, p.getDni(), dataFont);
            agregarCelda(tabla, p.getGenero(), dataFont);
            agregarCelda(tabla, p.getRol().getDbValue(), dataFont);
            agregarCelda(tabla, p.getTelefono(), dataFont);
            agregarCelda(tabla, p.getCorreo(), dataFont);
            agregarCelda(tabla, p.getDireccion(), dataFont);
            agregarCelda(tabla, p.getFechaNacimiento().toString(), dataFont);
            agregarCelda(tabla, cargarHorarios(p), dataFont);
            agregarCelda(tabla, especialidad, dataFont);
        }
            
            documento.add(tabla);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "El archivo no pudo ser creado.");
            return;
        } finally {
            documento.close();
        }
        JOptionPane.showMessageDialog(null, "El archivo fue descargado a: " + ruta);
    }
    
    //Exportar Excel Empleado
    public static void empleadoExportarExcel(List<E_Empleado> empleados, String ruta) {
        if (!ruta.toLowerCase().endsWith(".xlsx")) {
            ruta += ".xlsx";
        }

        try (Workbook libro = new XSSFWorkbook()) {
            Sheet hoja = libro.createSheet("Pacientes");

            // Encabezados
            Row headerRow = hoja.createRow(0);
            String[] headers = {"ID", "Nombre", "Apellidos", "DNI", "Genero", "Rol", 
                               "Telefono", "Correo", "Direccion", 
                               "Fecha de Nacimiento", "Horarios", "Especialidad"};

            // Crear estilo para encabezados
            CellStyle headerStyle = libro.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont;
            headerFont = libro.createFont();
            headerStyle.setFont(headerFont);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Datos
            int numFila = 1;
            for (E_Empleado p : empleados) {
                String especialidad;
                if( p.getEspecialidad() == null ){
                    especialidad = "No tiene";
                }else{
                    especialidad = p.getEspecialidad().getNombre();
                }
                Row fila = hoja.createRow(numFila++);
                fila.createCell(0).setCellValue(p.getIdPersona());
                fila.createCell(1).setCellValue(p.getNombre());
                fila.createCell(2).setCellValue(p.getApellidoPaterno() + " " + p.getApellidoMaterno());
                fila.createCell(3).setCellValue(p.getDni());
                fila.createCell(4).setCellValue(p.getGenero());
                fila.createCell(5).setCellValue(p.getRol().getDbValue());
                fila.createCell(6).setCellValue(p.getTelefono());
                fila.createCell(7).setCellValue(p.getCorreo());
                fila.createCell(8).setCellValue(p.getDireccion());
                fila.createCell(9).setCellValue(p.getFechaNacimiento().toString());
                fila.createCell(10).setCellValue(cargarHorarios(p));
                fila.createCell(11).setCellValue(especialidad);
            }

            // Autoajustar columnas
            for (int i = 0; i < headers.length; i++) {
                hoja.autoSizeColumn(i);
            }

            // Guardar
            try (FileOutputStream outputStream = new FileOutputStream(ruta)) {
                libro.write(outputStream);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al exportar: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, "El archivo fue exportado a: " + ruta);
    }
    
    private static String cargarHorarios(E_Empleado obj){
        String ho = "";
        for( EHorario horario : obj.getHorarios() ){
            ho += horario.getDia() + ": " + horario.getHoraEntrada() + " hasta las " + horario.getHoraSalida() + "; ";
        }
        return ho;
    }
}

