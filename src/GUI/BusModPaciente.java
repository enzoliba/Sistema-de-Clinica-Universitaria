/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Datos.DPaciente;
import Entidades.EPaciente;
import Entidades.Rol;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ENZO
 */
public class BusModPaciente extends javax.swing.JFrame {

    public DefaultTableModel modelo;
    private DPaciente pacDao = new DPaciente();
    public String cabecera[] = {"idPaciente", "DNI", "Tipo de Sangre", "Nombre", "Apellidos", "Rol", "Telefono", "Correo"};
    public int pagTemp;
    
    /**
     * Creates new form BusModPaciente
     */
    public BusModPaciente() throws SQLException {
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
                if (criterioID.isSelected()) {
                    id = textoBusqueda;
                } else if (criterioDNI.isSelected()) {
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
            List<EPaciente> pacientes = new ArrayList<>();
            try {
                pacientes = pacDao.listarBuscarPaciente(id, apellido, dni, rolSeleccionado, null, Integer.parseInt(TxtNumPag.getText()), 20);
            } catch(SQLException ex) {
                Logger.getLogger(BusModPaciente.class.getName()).log(Level.SEVERE, null, ex);
            }
            actualizarTabla(pacientes);
        });
        cargarRoles();
        
        this.btnGroupCriterio.add(this.criterioApellido);
        this.btnGroupCriterio.add(this.criterioDNI);
        this.btnGroupCriterio.add(this.criterioID);
        this.criterioDNI.setSelected(true);
        
        this.TbPacientes.setModel(modelo);
        actualizarTabla("Todos");
    }
    
    private void cargarRoles() {
        this.CboxFiltroRol.removeAllItems();
        this.CboxFiltroRol.addItem("Todos");
        // Recorrer el enum Rol para añadir las opciones
        for (Rol rol : Rol.values()) {
            if ( !rol.getDbValue().equalsIgnoreCase("Medico") && !rol.getDbValue().equalsIgnoreCase("Enfermero") && !rol.getDbValue().equalsIgnoreCase("Seguridad") && !rol.getDbValue().equalsIgnoreCase("Limpieza")) { 
                this.CboxFiltroRol.addItem(rol.getDbValue());
            }
        }
    } 
    
    private void actualizarTabla(String rol) throws SQLException{
        
        pacDao = new DPaciente();
        modelo = new DefaultTableModel ( null, cabecera );
        this.TbPacientes.setModel(modelo);

        List<EPaciente> pacientes;

        if ("Todos".equals(rol)) {
            pacientes = pacDao.listarBuscarPaciente(null, null, null, null, null, Integer.parseInt(this.TxtNumPag.getText()), 20);
        } else {
            // Convertir el String del ComboBox a un enum Rol
            Rol filtroRol = null;
            try {
                filtroRol = Rol.fromDbValue(rol);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Rol seleccionado en ComboBox no válido: " + rol);
                return; 
            }
            pacientes = pacDao.listarBuscarPaciente(null, null, null, null, null, Integer.parseInt(this.TxtNumPag.getText()), 20);
        }

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        // Llenar la tabla con los nuevos datos
        if (pacientes != null) {
            for( EPaciente obj : pacientes ){
                String fila[] = {obj.getIdPersona(),
                                 obj.getDni(),
                                 obj.getTipoSangre().getDbValue(),
                                 obj.getNombre(),
                                 obj.getApellidoPaterno() + " " + obj.getApellidoMaterno(),
                                 obj.getRol().getDbValue(),
                                 obj.getTelefono(),
                                 obj.getCorreo()
                                };
                this.modelo.addRow(fila);
            }
        }
    }
    
    private void actualizarTabla(List<EPaciente> pacientes) {
        modelo = new DefaultTableModel(null, cabecera);
        this.TbPacientes.setModel(modelo);

        // Llenar la tabla con los nuevos datos
        if (pacientes != null) {
            for (EPaciente obj : pacientes) {
                String fila[] = {
                    obj.getIdPersona(),
                    obj.getDni(),
                    obj.getTipoSangre().getDbValue(),
                    obj.getNombre(),
                    obj.getApellidoPaterno() + " " + obj.getApellidoMaterno(),
                    obj.getRol().getDbValue(),
                    obj.getTelefono(),
                    obj.getCorreo()
                };
                this.modelo.addRow(fila);
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
        buttonGroup1 = new javax.swing.ButtonGroup();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        TbPacientes = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        TxtBuscar = new javax.swing.JTextField();
        CboxFiltroRol = new javax.swing.JComboBox<>();
        criterioID = new javax.swing.JRadioButton();
        criterioDNI = new javax.swing.JRadioButton();
        criterioApellido = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        BtnBuscar = new javax.swing.JButton();
        BtnModif = new javax.swing.JButton();
        BtnCerrar = new javax.swing.JButton();
        BtnSigPag = new javax.swing.JButton();
        BtnAntPag = new javax.swing.JButton();
        TxtNumPag = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TbPacientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Apellido Paterno", "Apellido Materno", "Nombres", "DNI", "Historia Clinica"
            }
        ));
        jScrollPane1.setViewportView(TbPacientes);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 930, 410));

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("Buscar:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, -1, -1));
        getContentPane().add(TxtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 153, 30));

        CboxFiltroRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(CboxFiltroRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(902, 96, 105, -1));

        criterioID.setText("ID");
        criterioID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                criterioIDActionPerformed(evt);
            }
        });
        getContentPane().add(criterioID, new org.netbeans.lib.awtextra.AbsoluteConstraints(612, 97, -1, -1));

        criterioDNI.setText("DNI");
        getContentPane().add(criterioDNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(658, 97, -1, -1));

        criterioApellido.setText("Apellidos");
        getContentPane().add(criterioApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(713, 97, -1, -1));

        jLabel2.setText("Criterio de Busqueda:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(493, 99, -1, -1));

        jLabel3.setText("Filtrar Resultados:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(803, 99, -1, -1));

        BtnBuscar.setBackground(new java.awt.Color(0, 102, 102));
        BtnBuscar.setFont(new java.awt.Font("Agave Nerd Font", 1, 14)); // NOI18N
        BtnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        BtnBuscar.setText("BUSCAR");
        BtnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, 90, -1));

        BtnModif.setBackground(new java.awt.Color(0, 102, 102));
        BtnModif.setFont(new java.awt.Font("Agave Nerd Font", 1, 20)); // NOI18N
        BtnModif.setForeground(new java.awt.Color(255, 255, 255));
        BtnModif.setText("MODIFICAR");
        BtnModif.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnModifActionPerformed(evt);
            }
        });
        getContentPane().add(BtnModif, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 640, -1, 36));

        BtnCerrar.setBackground(new java.awt.Color(0, 102, 102));
        BtnCerrar.setFont(new java.awt.Font("Agave Nerd Font", 1, 20)); // NOI18N
        BtnCerrar.setForeground(new java.awt.Color(255, 255, 255));
        BtnCerrar.setText("REGRESAR");
        BtnCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCerrarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 640, -1, 40));

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

        TxtNumPag.setForeground(new java.awt.Color(102, 102, 102));
        TxtNumPag.setText("1");
        getContentPane().add(TxtNumPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 140, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Imagenes/Bus-modPacientes.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void criterioIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_criterioIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_criterioIDActionPerformed

    private void BtnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_BtnCerrarActionPerformed

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

        if (criterioID.isSelected()) {
            id = textoBusqueda;
        } else if (criterioDNI.isSelected()) {
            dni = textoBusqueda;
        } else if (criterioApellido.isSelected()) {
            apellido = textoBusqueda;
        }

        // Llamar al DAO para buscar pacientes
        List<EPaciente> listaPacientes = new ArrayList<>();
        try {
            listaPacientes = pacDao.listarBuscarPaciente(id, apellido, dni, rol, null, Integer.parseInt(this.TxtNumPag.getText()), 20);
        } catch (SQLException ex) {
            Logger.getLogger(BusModPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(listaPacientes == null || listaPacientes.isEmpty()){
            if(!this.TxtNumPag.getText().equals("1")){ 
                pagTemp--;
                this.TxtNumPag.setText(String.valueOf(pagTemp));
                JOptionPane.showMessageDialog(this, "No se encontraron registros", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        // Actualizar la tabla con los resultados
        actualizarTabla(listaPacientes);
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

    private void BtnModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnModifActionPerformed
        DPaciente pacDao = new DPaciente();
        String idPaciente;
        EPaciente pac = new EPaciente();
        if (this.TbPacientes.getSelectedRow() != -1){
            
            idPaciente = (String)this.TbPacientes.getValueAt(this.TbPacientes.getSelectedRow(), 0);
            pac = pacDao.buscarPacientePorId(idPaciente);
            NewPaciente np;
            try {
                np = new NewPaciente(pac);
                np.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(BusModPaciente.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else{
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un paciente", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
    }//GEN-LAST:event_BtnModifActionPerformed

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
            java.util.logging.Logger.getLogger(BusModPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BusModPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BusModPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BusModPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new BusModPaciente().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(BusModPaciente.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JTable TbPacientes;
    private javax.swing.JTextField TxtBuscar;
    private javax.swing.JLabel TxtNumPag;
    private javax.swing.ButtonGroup btnGroupCriterio;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton criterioApellido;
    private javax.swing.JRadioButton criterioDNI;
    private javax.swing.JRadioButton criterioID;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
