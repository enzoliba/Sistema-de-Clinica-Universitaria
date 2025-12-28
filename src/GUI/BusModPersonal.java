/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Datos.DEmpleado;
import Entidades.EHorario;
import Entidades.E_Empleado;
import Entidades.Rol;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ENZO
 */
public class BusModPersonal extends javax.swing.JFrame {

    /**
     * Creates new form BusModPersonal
     */
    
    public DefaultTableModel modelo;
    private DEmpleado empDao = new DEmpleado();
    public String cabecera[] = {"idEmpleado", "DNI", "Nombre", "Apellidos", "Rol", "Telefono", "Correo", "Especialidad", "Horarios"};
    public int pagTemp;
    
    public BusModPersonal() throws SQLException {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        modelo = new DefaultTableModel ( null, cabecera );
        pagTemp = 1;
        this.CboxFiltroRol.addActionListener((ActionEvent e) -> {
            // Obtener texto de búsqueda actual
            String rolSeleccionado = (String) CboxFiltroRol.getSelectedItem();
            String textoBusqueda;
            
            String id = null;
            String dni = null;
            String apellido = null;
            
            if(!(TxtBuscar.getText().length()==0)){
                textoBusqueda = TxtBuscar.getText().trim();
                // Determinar criterio seleccionado
                if (criterioId.isSelected()) {
                    id = textoBusqueda;
                } else if (criterioDni.isSelected()) {
                    dni = textoBusqueda;
                } else if (criterioApellido.isSelected()) {
                    apellido = textoBusqueda;
                }
            }else{
                id = null;
                dni = null;
                apellido = null;
            }
            
            // Realizar búsqueda con los parámetros actuales
            List<E_Empleado> pacientes = new ArrayList<>();
            try {
                pacientes = empDao.listarBuscarEmpleado(id, apellido, dni, rolSeleccionado, null, Integer.parseInt(TxtNumPag.getText()), 20);
            } catch(SQLException ex) {
                Logger.getLogger(BusModPaciente.class.getName()).log(Level.SEVERE, null, ex);
            }
            actualizarTabla(pacientes);
        });
        cargarRoles();
        
        this.btnGroupCriterio.add(this.criterioApellido);
        this.btnGroupCriterio.add(this.criterioDni);
        this.btnGroupCriterio.add(this.criterioId);
        this.criterioDni.setSelected(true);
        
        this.TbEmpleados.setModel(modelo);
        actualizarTabla("Todos");
    }

        private void cargarRoles() {
        this.CboxFiltroRol.removeAllItems();
        this.CboxFiltroRol.addItem("Todos");
        // Recorrer el enum Rol para añadir las opciones
        for (Rol rol : Rol.values()) {
            if (rol.getDbValue().equalsIgnoreCase("Medico") || rol.getDbValue().equalsIgnoreCase("Enfermero") || rol.getDbValue().equalsIgnoreCase("Seguridad") || rol.getDbValue().equalsIgnoreCase("Limpieza")) { 
                this.CboxFiltroRol.addItem(rol.getDbValue());
            }
        }
    } 
    
    private void actualizarTabla(String rol) throws SQLException{
        
        empDao = new DEmpleado();
        modelo = new DefaultTableModel ( null, cabecera );
        this.TbEmpleados.setModel(modelo);

        List<E_Empleado> empleados;

        if ("Todos".equals(rol)) {
            empleados = empDao.listarBuscarEmpleado(null, null, null, null, null, Integer.parseInt(this.TxtNumPag.getText()), 20);
        } else {
            // Convertir el String del ComboBox a un enum Rol
            Rol filtroRol = null;
            try {
                filtroRol = Rol.fromDbValue(rol);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Rol seleccionado en ComboBox no válido: " + rol);
                return; 
            }
            empleados = empDao.listarBuscarEmpleado(null, null, null, null, null, Integer.parseInt(this.TxtNumPag.getText()), 20);
        }
        // Llenar la tabla con los nuevos datos
        if (empleados != null) {
            for (E_Empleado obj : empleados) {
                String fila[] = {obj.getIdPersona(),
                                obj.getDni(),
                                obj.getNombre(),
                                obj.getApellidoPaterno() + " " + obj.getApellidoMaterno(),
                                obj.getRol().getDbValue(),
                                obj.getTelefono(),
                                obj.getCorreo(),
                                obj.getEspecialidad() != null ? obj.getEspecialidad().getNombre() : "N/A", // Nombre de especialidad o "N/A"
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
    
    private void actualizarTabla(List<E_Empleado> empleados) {
        modelo = new DefaultTableModel(null, cabecera);
        this.TbEmpleados.setModel(modelo);

        // Llenar la tabla con los nuevos datos
        if (empleados != null) {
            for (E_Empleado obj : empleados) {
                String fila[] = {obj.getIdPersona(),
                                obj.getDni(),
                                obj.getNombre(),
                                obj.getApellidoPaterno() + " " + obj.getApellidoMaterno(),
                                obj.getRol().getDbValue(),
                                obj.getTelefono(),
                                obj.getCorreo(),
                                obj.getEspecialidad() != null ? obj.getEspecialidad().getNombre() : "N/A", // Nombre de especialidad o "N/A"
                                cargarHorarios(obj)
                                };
                this.modelo.addRow(fila); // Añadir la fila al modelo de la tabla
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupCriterio = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        TxtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TbEmpleados = new javax.swing.JTable();
        BtnCerrar = new javax.swing.JButton();
        BtnModif = new javax.swing.JButton();
        BtnBuscar = new javax.swing.JButton();
        BtnSigPag = new javax.swing.JButton();
        BtnAntPag = new javax.swing.JButton();
        TxtNumPag = new javax.swing.JLabel();
        criterioId = new javax.swing.JRadioButton();
        criterioApellido = new javax.swing.JRadioButton();
        criterioDni = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        CboxFiltroRol = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("Buscar por Codigo:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, -1, -1));

        TxtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(TxtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 140, -1));

        TbEmpleados.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(TbEmpleados);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, 940, 410));

        BtnCerrar.setBackground(new java.awt.Color(0, 102, 102));
        BtnCerrar.setFont(new java.awt.Font("Agave Nerd Font", 1, 20)); // NOI18N
        BtnCerrar.setForeground(new java.awt.Color(255, 255, 255));
        BtnCerrar.setText("Regresar");
        BtnCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCerrarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 640, -1, -1));

        BtnModif.setBackground(new java.awt.Color(0, 102, 102));
        BtnModif.setFont(new java.awt.Font("Agave Nerd Font", 1, 20)); // NOI18N
        BtnModif.setForeground(new java.awt.Color(255, 255, 255));
        BtnModif.setText("Modificar");
        BtnModif.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnModifActionPerformed(evt);
            }
        });
        getContentPane().add(BtnModif, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 640, -1, -1));

        BtnBuscar.setBackground(new java.awt.Color(0, 102, 102));
        BtnBuscar.setFont(new java.awt.Font("Agave Nerd Font", 1, 20)); // NOI18N
        BtnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        BtnBuscar.setText("Buscar");
        BtnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 90, -1, 30));

        BtnSigPag.setBackground(new java.awt.Color(0, 102, 102));
        BtnSigPag.setFont(new java.awt.Font("Agave Nerd Font", 1, 20)); // NOI18N
        BtnSigPag.setForeground(new java.awt.Color(255, 255, 255));
        BtnSigPag.setText("Siguiente ->");
        BtnSigPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSigPagActionPerformed(evt);
            }
        });
        getContentPane().add(BtnSigPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 130, -1, -1));

        BtnAntPag.setBackground(new java.awt.Color(0, 102, 102));
        BtnAntPag.setFont(new java.awt.Font("Agave Nerd Font", 1, 20)); // NOI18N
        BtnAntPag.setForeground(new java.awt.Color(255, 255, 255));
        BtnAntPag.setText("<- Anterior");
        BtnAntPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAntPagActionPerformed(evt);
            }
        });
        getContentPane().add(BtnAntPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, -1, -1));

        TxtNumPag.setForeground(new java.awt.Color(51, 51, 51));
        TxtNumPag.setText("1");
        getContentPane().add(TxtNumPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 150, -1, -1));

        criterioId.setForeground(new java.awt.Color(51, 51, 51));
        criterioId.setText("ID");
        getContentPane().add(criterioId, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 90, -1, -1));

        criterioApellido.setForeground(new java.awt.Color(51, 51, 51));
        criterioApellido.setText("Apellidos");
        getContentPane().add(criterioApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, -1, -1));

        criterioDni.setForeground(new java.awt.Color(51, 51, 51));
        criterioDni.setText("DNI");
        getContentPane().add(criterioDni, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 90, -1, -1));

        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Criterio de Busqueda:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, -1, -1));

        CboxFiltroRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(CboxFiltroRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 90, 120, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Imagenes/bus-modPersonal.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1070, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_BtnCerrarActionPerformed

    private void BtnModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnModifActionPerformed

    }//GEN-LAST:event_BtnModifActionPerformed

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed
        String textoBusqueda = TxtBuscar.getText().trim();
        String rolFiltro = CboxFiltroRol.getSelectedItem().toString();

        // Convertir rol a formato adecuado ("Todos" se convierte en null)
        String rol = rolFiltro.equals("Todos") ? null : rolFiltro;

        // Determinar el criterio de búsqueda seleccionado
        String id = null;
        String dni = null;
        String apellido = null;
        
        if(textoBusqueda.isEmpty()){ textoBusqueda = null; }

        if (criterioId.isSelected()) {
            id = textoBusqueda;
        } else if (criterioDni.isSelected()) {
            dni = textoBusqueda;
        } else if (criterioApellido.isSelected()) {
            apellido = textoBusqueda;
        }

        // Llamar al DAO para buscar pacientes
        List<E_Empleado> listaEmpleados = new ArrayList<>();
        try {
            listaEmpleados = empDao.listarBuscarEmpleado(id, apellido, dni, rol, null, Integer.parseInt(this.TxtNumPag.getText()), 20);
        } catch (SQLException ex) {
            Logger.getLogger(BusModPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(listaEmpleados == null || listaEmpleados.isEmpty()){
            if(!this.TxtNumPag.getText().equals("1")){ 
                pagTemp--;
                this.TxtNumPag.setText(String.valueOf(pagTemp));
                JOptionPane.showMessageDialog(this, "No se encontraron registros", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        // Actualizar la tabla con los resultados
        actualizarTabla(listaEmpleados);
    }//GEN-LAST:event_BtnBuscarActionPerformed

    private void BtnAntPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAntPagActionPerformed
        String numPag = this.TxtNumPag.getText();
        if( numPag.equals("1") ){
            JOptionPane.showMessageDialog(this, "Esta es la primera pagina, no hay anterior", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        pagTemp = Integer.parseInt(numPag) - 1;
        
        this.TxtNumPag.setText(String.valueOf(pagTemp));
        this.BtnBuscarActionPerformed(evt);
    }//GEN-LAST:event_BtnAntPagActionPerformed

    private void TxtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtBuscarActionPerformed

    private void BtnSigPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSigPagActionPerformed
        String numPag = this.TxtNumPag.getText();
        
        pagTemp = Integer.parseInt(numPag) + 1;
        
        this.TxtNumPag.setText(String.valueOf(pagTemp));
        this.BtnBuscarActionPerformed(evt);
    }//GEN-LAST:event_BtnSigPagActionPerformed

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
            java.util.logging.Logger.getLogger(BusModPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BusModPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BusModPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BusModPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new BusModPersonal().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(BusModPersonal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAntPag;
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnCerrar;
    private javax.swing.JButton BtnModif;
    private javax.swing.JButton BtnSigPag;
    private javax.swing.JComboBox<String> CboxFiltroRol;
    private javax.swing.JTable TbEmpleados;
    private javax.swing.JTextField TxtBuscar;
    private javax.swing.JLabel TxtNumPag;
    private javax.swing.ButtonGroup btnGroupCriterio;
    private javax.swing.JRadioButton criterioApellido;
    private javax.swing.JRadioButton criterioDni;
    private javax.swing.JRadioButton criterioId;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
