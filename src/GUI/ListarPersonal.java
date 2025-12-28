/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Datos.DEmpleado;
import Entidades.EHorario;
import Entidades.E_Empleado;
import Entidades.Rol;
import Utils.Exportador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author GUILLERMO
 */
public class ListarPersonal extends javax.swing.JFrame {

    /**
     * Creates new form ListarPersonal
     */
    public DEmpleado empDao;
    public DefaultTableModel modelo;
    public String cabecera[] = {"Codigo", "DNI", "Apellidos", "Nombres", "Especialidad", "Rol", "Fecha de Ingreso", "telefono", "correo", "direccion", "Horarios"};
    
    public ListarPersonal() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        modelo = new DefaultTableModel ( null, cabecera );
        cargarRolesComboBox();
        cbBuscador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rolSeleccionado = (String) cbBuscador.getSelectedItem();
                actualizarTabla(rolSeleccionado); // Llama al método para actualizar la tabla
            }
        });
        this.LPersonal.setModel(modelo);
        actualizarTabla("Todos");
    }

    /*
    private void cargarPersonal(){
        empDao = new DEmpleado();
        ArrayList<E_Empleado> lista;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try{
            lista = empDao.listarPersonalConHorarios("Todos");
            for( E_Empleado obj : lista ){
                String fechaIngreso = formato.format(obj.getFechaIngreso());
                String fila[] = {obj.getIdPersona(),
                                obj.getDni(),
                                obj.getApellidoPaterno() + " " + obj.getApellidoMaterno(),
                                obj.getNombre(),
                                obj.getEspecialidad().getNombre(),
                                obj.getRol().getDbValue(),
                                fechaIngreso,
                                obj.getTelefono(),
                                obj.getCorreo(),
                                obj.getDireccion(),
                                cargarHorarios(obj)
                                };
                this.modelo.addRow(fila);
            }
        }catch( Exception e ){
            
        }
    }
    */
    
    private void cargarRolesComboBox() {
        cbBuscador.removeAllItems();
        // Añadir la opción "Todos" al inicio
        cbBuscador.addItem("Todos");
        // Recorrer el enum Rol para añadir las opciones
        for (Rol rol : Rol.values()) {
            if ( !rol.getDbValue().equalsIgnoreCase("Administrativo") && !rol.getDbValue().equalsIgnoreCase("Estudiante") && !rol.getDbValue().equalsIgnoreCase("Docente") ) { 
                cbBuscador.addItem(rol.getDbValue());
            }
        }
    }
    
    private void actualizarTabla(String rol){
        
        empDao = new DEmpleado();
        modelo = new DefaultTableModel ( null, cabecera );
        this.LPersonal.setModel(modelo);

        ArrayList<E_Empleado> empleados;

        if ("Todos".equals(rol)) {
            empleados = empDao.listarPersonalConHorarios(rol);
        } else {
            // Convertir el String del ComboBox a un enum Rol
            Rol filtroRol = null;
            try {
                filtroRol = Rol.fromDbValue(rol);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Rol seleccionado en ComboBox no válido: " + rol);
                return; 
            }
            empleados = empDao.listarPersonalConHorarios(filtroRol.getDbValue());
        }

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        // Llenar la tabla con los nuevos datos
        if (empleados != null) {
            for (E_Empleado obj : empleados) {

                String fechaIngreso = formato.format(obj.getFechaIngreso());
                String fila[] = {obj.getIdPersona(),
                                obj.getDni(),
                                obj.getApellidoPaterno() + " " + obj.getApellidoMaterno(),
                                obj.getNombre(),
                                obj.getEspecialidad() != null ? obj.getEspecialidad().getNombre() : "N/A", // Nombre de especialidad o "N/A"
                                obj.getRol().getDbValue(),
                                fechaIngreso,
                                obj.getTelefono(),
                                obj.getCorreo(),
                                obj.getDireccion(),
                                cargarHorarios(obj)
                                };
                this.modelo.addRow(fila); // Añadir la fila al modelo de la tabla
            }
        }
    }
    
    private String cargarHorarios(E_Empleado obj){
        String ho = "";
        for( EHorario horario : obj.getHorarios() ){
            ho += horario.getDia() + ": " + horario.getHoraEntrada() + " hasta las " + horario.getHoraSalida() + "; ";
        }
        return ho;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        LPersonal = new javax.swing.JTable();
        cbBuscador = new javax.swing.JComboBox<>();
        ExpPDF = new javax.swing.JButton();
        ExpEXCEL = new javax.swing.JButton();
        BtnCerrar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LPersonal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Apellido Paterno", "Apellido Materno", "Nombres", "Especialidad", "Dias laborales", "Horario laboral"
            }
        ));
        jScrollPane1.setViewportView(LPersonal);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 980, 510));

        cbBuscador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbBuscador.setToolTipText("");
        cbBuscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBuscadorActionPerformed(evt);
            }
        });
        getContentPane().add(cbBuscador, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 20, 145, -1));

        ExpPDF.setBackground(new java.awt.Color(68, 1, 30));
        ExpPDF.setFont(new java.awt.Font("Agave Nerd Font", 1, 20)); // NOI18N
        ExpPDF.setForeground(new java.awt.Color(255, 255, 255));
        ExpPDF.setText("Descargar PDF");
        ExpPDF.setActionCommand("ExpPDF");
        ExpPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExpPDFActionPerformed(evt);
            }
        });
        getContentPane().add(ExpPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 650, -1, -1));

        ExpEXCEL.setBackground(new java.awt.Color(68, 1, 30));
        ExpEXCEL.setFont(new java.awt.Font("Agave Nerd Font", 1, 20)); // NOI18N
        ExpEXCEL.setForeground(new java.awt.Color(255, 255, 255));
        ExpEXCEL.setText("Descargar EXCEL");
        ExpEXCEL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExpEXCELActionPerformed(evt);
            }
        });
        getContentPane().add(ExpEXCEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 650, -1, -1));

        BtnCerrar.setBackground(new java.awt.Color(68, 1, 30));
        BtnCerrar.setFont(new java.awt.Font("Agave Nerd Font", 1, 20)); // NOI18N
        BtnCerrar.setForeground(new java.awt.Color(255, 255, 255));
        BtnCerrar.setText("Regresar");
        BtnCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCerrarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 650, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Imagenes/ListaPersonal.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbBuscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBuscadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbBuscadorActionPerformed

    private void ExpPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExpPDFActionPerformed
        DEmpleado empDao = new DEmpleado();
        List<E_Empleado> empleados = empDao.listarPersonalConHorarios(this.cbBuscador.getSelectedItem().toString());
        
        JFileChooser elegirRuta = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos PDF (*.pdf)", "pdf");
        elegirRuta.setFileFilter(filter);
        
        // Establecer nombre por defecto
        elegirRuta.setSelectedFile(new File("ListaEmpleados.pdf"));
        
        // Establecer directorio inicial (opcional)
        elegirRuta.setCurrentDirectory(new File(System.getProperty("user.home")));
        
        // Mostrar el diálogo para guardar
        int result = elegirRuta.showSaveDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = elegirRuta.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            
            // Asegurar que tenga extensión .pdf
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }
            
            // Exportar el PDF
            Exportador.empleadoExportarPDF(empleados, filePath);
        }
    }//GEN-LAST:event_ExpPDFActionPerformed

    private void BtnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_BtnCerrarActionPerformed

    private void ExpEXCELActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExpEXCELActionPerformed
        try{
        DEmpleado empDao = new DEmpleado();
        List<E_Empleado> empleados = empDao.listarPersonalConHorarios(this.cbBuscador.getSelectedItem().toString());
        
        // Crear el JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        
        // Configurar filtros para archivos Excel
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx");
        fileChooser.setFileFilter(filter);
        
        // Establecer nombre por defecto
        fileChooser.setSelectedFile(new File("ListaEmpleados.xlsx"));
        
        // Establecer directorio inicial (opcional)
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        
        // Mostrar el diálogo para guardar
        int result = fileChooser.showSaveDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            
            // Asegurar que tenga extensión .xlsx
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }
            
            // Exportar el Excel
            Exportador.empleadoExportarExcel(empleados, filePath);
        }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al exportar Excel: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ExpEXCELActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ListarPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListarPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListarPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListarPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListarPersonal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCerrar;
    private javax.swing.JButton ExpEXCEL;
    private javax.swing.JButton ExpPDF;
    private javax.swing.JTable LPersonal;
    private javax.swing.JComboBox<String> cbBuscador;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
