package GUI;

import Datos.DPaciente;
import Entidades.EPaciente;
import Entidades.Rol;
import Entidades.TipoSangre;
import Utils.GeneradorCode;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class NewPaciente extends javax.swing.JFrame {

    private String codeGen;
    
    public NewPaciente() throws SQLException {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        //Hacer un listener para el checkbox para que reaccione al evento
        chbFechaHoy.addItemListener((ItemEvent e) -> {
            // Verificar si ha sido seleccionado o deseleccionado
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // Si se marcó el checkbox, poner la fecha de hoy
                //Se elige LocalDate ya que es la mas actualizada que Date
                LocalDate hoy = LocalDate.now();
                TxtFechaRegistro.setDate(Date.from(hoy.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                TxtFechaRegistro.setEnabled(false); // deshabilitar edicion manual
            } else {
                // Si se desmarcó el checkbox, dejar la casilla en blanco
                TxtFechaRegistro.setDate(null);
                TxtFechaRegistro.setEnabled(true); //habilitar la edición manual
            }
        });
        
        codeGen = GeneradorCode.CodeGenerador("PAC", null);
        this.codeGenerado.setText(codeGen);
        cargarRoles();
        cargarTipoSangre();
    }
    
    public NewPaciente(EPaciente pac) throws SQLException{
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        //Hacer un listener para el checkbox para que reaccione al evento
        chbFechaHoy.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Verificar si ha sido seleccionado o deseleccionado
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // Si se marcó el checkbox, poner la fecha de hoy
                    //Se elige LocalDate ya que es la mas actualizada que Date
                    LocalDate hoy = LocalDate.now();
                    TxtFechaRegistro.setDate(Date.from(hoy.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    TxtFechaRegistro.setEnabled(false); // deshabilitar edicion manual
                } else {
                    // Si se desmarcó el checkbox, dejar la casilla en blanco
                    TxtFechaRegistro.setDate(null);
                    TxtFechaRegistro.setEnabled(true); //habilitar la edición manual
                }
            }
        });
        
        cargarRoles();
        cargarTipoSangre();
        cargarDatosPacienteModificar(pac);
    }
    
    public void cargarDatosPacienteModificar(EPaciente pac){
        this.codeGenerado.setText(pac.getIdPersona());
        this.BtnRegistrar.setText("Actualizar");
        this.TxtApellidoMa.setText(pac.getApellidoMaterno());
        this.TxtApellidoPa.setText(pac.getApellidoPaterno());
        this.TxtDni.setText(pac.getDni());
        this.TxtDomicilio.setText(pac.getDireccion());
        this.TxtEmail.setText(pac.getCorreo());
        
        this.TxtFechaNac.setDate(pac.getFechaNacimiento());
        this.TxtFechaRegistro.setDate(pac.getFechaRegistro());
        this.TxtGenero.setText(pac.getGenero());
        this.TxtNombres.setText(pac.getNombre());
        this.TxtTelefono.setText(pac.getTelefono());
        for(int i = 0; i<this.ComboxRolPaciente.getItemCount(); i++){
            if(this.ComboxRolPaciente.getItemAt(i).equals(pac.getRol().getDbValue())){
                this.ComboxRolPaciente.setSelectedIndex(i);
                break;
            }
        }
        for(int i = 0; i<this.ComboBoxTipoSangre.getItemCount(); i++){
            if(this.ComboBoxTipoSangre.getItemAt(i).equals(pac.getTipoSangre().getDbValue())){
                this.ComboBoxTipoSangre.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void cargarRoles() {
        this.ComboxRolPaciente.removeAllItems();
        this.ComboxRolPaciente.addItem("Seleccionar");
        // Recorrer el enum Rol para añadir las opciones
        for (Rol rol : Rol.values()) {
            if ( !rol.getDbValue().equalsIgnoreCase("Medico") && !rol.getDbValue().equalsIgnoreCase("Enfermero") && !rol.getDbValue().equalsIgnoreCase("Seguridad") && !rol.getDbValue().equalsIgnoreCase("Limpieza")) { 
                this.ComboxRolPaciente.addItem(rol.getDbValue());
            }
        }
    }  
    
    private void cargarTipoSangre() {
        this.ComboBoxTipoSangre.removeAllItems();
        this.ComboBoxTipoSangre.addItem("Seleccionar");
        // Recorrer el enum Rol para añadir las opciones
        for (TipoSangre tipoS : TipoSangre.values()) {
            this.ComboBoxTipoSangre.addItem(tipoS.getDbValue());
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

        TxtNombres = new javax.swing.JTextField();
        TxtTelefono = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        TxtDni = new javax.swing.JTextField();
        TxtDomicilio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        BtnCancelar = new javax.swing.JButton();
        BtnRegistrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        TxtApellidoPa = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        TxtApellidoMa = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        TxtEmail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        ComboxRolPaciente = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        ComboBoxTipoSangre = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        TxtGenero = new javax.swing.JTextField();
        chbFechaHoy = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        codeGenerado = new javax.swing.JLabel();
        TxtFechaNac = new com.toedter.calendar.JDateChooser();
        TxtFechaRegistro = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(TxtNombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 240, 218, -1));
        getContentPane().add(TxtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 400, 220, -1));

        jLabel3.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(68, 1, 30));
        jLabel3.setText("Dni:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 280, -1, -1));

        jLabel8.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(68, 1, 30));
        jLabel8.setText("Email:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 440, -1, -1));
        getContentPane().add(TxtDni, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 280, 220, -1));
        getContentPane().add(TxtDomicilio, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 480, 220, -1));

        jLabel4.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(68, 1, 30));
        jLabel4.setText("Fecha Nacimiento:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 320, -1, -1));

        BtnCancelar.setBackground(new java.awt.Color(68, 1, 30));
        BtnCancelar.setFont(new java.awt.Font("Agave Nerd Font", 1, 22)); // NOI18N
        BtnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        BtnCancelar.setText("Cancelar");
        BtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 700, 150, 47));

        BtnRegistrar.setBackground(new java.awt.Color(68, 1, 30));
        BtnRegistrar.setFont(new java.awt.Font("Agave Nerd Font", 1, 22)); // NOI18N
        BtnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        BtnRegistrar.setText("Registrar");
        BtnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRegistrarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 700, 200, 47));

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(68, 1, 30));
        jLabel1.setText("Apellido Paterno:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, -1, -1));

        TxtApellidoPa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtApellidoPaActionPerformed(evt);
            }
        });
        getContentPane().add(TxtApellidoPa, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, 218, -1));

        jLabel2.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(68, 1, 30));
        jLabel2.setText("Nombres:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, -1, -1));

        jLabel6.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(68, 1, 30));
        jLabel6.setText("Telefono:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 400, -1, -1));
        getContentPane().add(TxtApellidoMa, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, 218, -1));

        jLabel9.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(68, 1, 30));
        jLabel9.setText("Apellido Materno:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, -1, -1));

        jLabel11.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(68, 1, 30));
        jLabel11.setText("Dirección Domicilio:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 480, -1, -1));
        getContentPane().add(TxtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 440, 220, -1));

        jLabel5.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(68, 1, 30));
        jLabel5.setText("Rol:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 600, -1, -1));

        ComboxRolPaciente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboxRolPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboxRolPacienteActionPerformed(evt);
            }
        });
        getContentPane().add(ComboxRolPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 600, 220, -1));

        jLabel12.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(68, 1, 30));
        jLabel12.setText("Tipo de Sangre:");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 520, -1, -1));

        jLabel13.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(68, 1, 30));
        jLabel13.setText("fecha de Registro:");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 560, -1, -1));

        ComboBoxTipoSangre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(ComboBoxTipoSangre, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 520, 220, -1));

        jLabel7.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(68, 1, 30));
        jLabel7.setText("Genero:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 360, -1, -1));

        TxtGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtGeneroActionPerformed(evt);
            }
        });
        getContentPane().add(TxtGenero, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 360, 220, -1));

        chbFechaHoy.setText("Hoy");
        chbFechaHoy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbFechaHoyActionPerformed(evt);
            }
        });
        getContentPane().add(chbFechaHoy, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 560, -1, -1));

        jLabel10.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(68, 1, 30));
        jLabel10.setText("Codigo:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, -1, -1));
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(201, 42, -1, -1));

        codeGenerado.setText("jLabel15");
        getContentPane().add(codeGenerado, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, -1, -1));
        getContentPane().add(TxtFechaNac, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 320, 220, -1));
        getContentPane().add(TxtFechaRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 560, 160, -1));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Imagenes/Pacientes.png"))); // NOI18N
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 770));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ComboxRolPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboxRolPacienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboxRolPacienteActionPerformed

    private boolean validarCampos(){
        // validaciones, que todos los datos esten llenos
        if( this.TxtApellidoPa.getText().length() == 0){
            JOptionPane.showMessageDialog(rootPane, "Ingrese el Apellido Paterno del Paciente");
            return false;
        }
        if( this.TxtApellidoMa.getText().length() == 0){
            JOptionPane.showMessageDialog(rootPane, "Ingrese el Apellido Materno del Paciente");
            return false;
        }
        if( this.TxtDni.getText().length() != 8 ){
            JOptionPane.showMessageDialog(rootPane, "Ingrese DNI valido");
            return false;
        }
        if( this.TxtDomicilio.getText().length() == 0){
            JOptionPane.showMessageDialog(rootPane, "Ingrese el domicilio del Paciente");
            return false;
        }
        if( this.TxtEmail.getText().length() == 0 || !(this.TxtEmail.getText().contains("@uss.edu.pe"))){ //Solo se recibe el registro por el correo universitario
            JOptionPane.showMessageDialog(rootPane, "Ingrese un email valido");
            return false;
        }
        if( this.TxtFechaNac.getDate().toString().isEmpty()){
            JOptionPane.showMessageDialog(rootPane, "Ingrese la fecha del nacimiento del Paciente");
            return false;
        }
        if( this.TxtFechaRegistro.getDate().toString().isEmpty()){
            JOptionPane.showMessageDialog(rootPane, "Ingrese la fecha de Registro del Paciente");
            return false;
        }
        if( this.TxtGenero.getText().length() == 0){
            JOptionPane.showMessageDialog(rootPane, "Ingrese el genero del Paciente");
            return false;
        }
        if( this.TxtNombres.getText().length() == 0){
            JOptionPane.showMessageDialog(rootPane, "Ingrese el nombre del Paciente");
            return false;
        }
        if( this.TxtTelefono.getText().length() == 0){
            JOptionPane.showMessageDialog(rootPane, "Ingrese el telefono del Paciente");
            return false;
        }              
        if( this.ComboxRolPaciente.getSelectedIndex() <= 0){
            JOptionPane.showMessageDialog(rootPane, "Ingrese el rol del Paciente");
            return false;
        } 
        if( this.ComboBoxTipoSangre.getSelectedIndex() <= 0){
            JOptionPane.showMessageDialog(rootPane, "Ingrese el tipo de sangre del Paciente");
            return false;
        }
        LocalDate hoy = LocalDate.now();
        Date hoyDate = Date.from(hoy.atStartOfDay(ZoneId.systemDefault()).toInstant());
        if( this.TxtFechaNac.getDate().after(hoyDate) || this.TxtFechaRegistro.getDate().after(hoyDate) ){
            JOptionPane.showMessageDialog(rootPane, "Las fechas ingresadas no pueden ser superiores a la fecha de hoy.");
            return false;
        }
        if( this.TxtFechaRegistro.getDate().before(this.TxtFechaNac.getDate()) ){
            JOptionPane.showMessageDialog(rootPane, "Las fecha de registro no puede ocurrir antes a la fecha de nacimiento.");
            return false;
        }
        return true;
    }
    
    private void BtnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRegistrarActionPerformed
        
        if(!validarCampos()){ return; };          
        
        // Construcción del objeto EPaciente usando Builder
        EPaciente.PacienteBuilder pacBuilder = EPaciente.builder()
            .setIdPersona(this.BtnRegistrar.getText().equals("Actualizar") ? 
                          this.codeGenerado.getText() : codeGen)
            .setNombre(this.TxtNombres.getText())
            .setApellidoPaterno(this.TxtApellidoPa.getText())
            .setApellidoMaterno(this.TxtApellidoMa.getText())
            .setDni(this.TxtDni.getText())
            .setGenero(this.TxtGenero.getText())
            .setRol(Rol.fromDbValue((String) this.ComboxRolPaciente.getSelectedItem()))
            .setTelefono(this.TxtTelefono.getText())
            .setCorreo(this.TxtEmail.getText())
            .setDireccion(this.TxtDomicilio.getText())
            .setFechaNacimiento(this.TxtFechaNac.getDate())
            .setTipoSangre(TipoSangre.fromDbValue((String) this.ComboBoxTipoSangre.getSelectedItem()))
            .setFechaRegistro(this.TxtFechaRegistro.getDate());

        EPaciente pac = pacBuilder.build();

        // Manejo de la operación (registrar o actualizar)
        String accion = this.BtnRegistrar.getText().equals("Registrar") ? "registrar" : "actualizar";
        String accionPasado = accion.equals("registrar") ? "registrado" : "actualizado";

        int opc = confirmacionRegistroModificar(pac, accion.equals("registrar") ? "Registro" : "Actualizacion");
        if (opc != JOptionPane.YES_OPTION) return;

        try {
            DPaciente pacDao = new DPaciente();
            if (accion.equals("registrar")) {
                pacDao.registrarPaciente(pac);
            } else {
                pacDao.actualizarPaciente(pac);
            }
            JOptionPane.showMessageDialog(rootPane, "Paciente " + accionPasado + " correctamente");
            limpiar();
            if (!accion.equals("registrar")) {
                this.dispose();
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewPaciente.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, "El Paciente no se pudo " + accion + ", revise que los datos sean correctos.");
        }
    }//GEN-LAST:event_BtnRegistrarActionPerformed
    
    public int confirmacionRegistroModificar(EPaciente pac, String confir){
        int opc = JOptionPane.showConfirmDialog(
            rootPane,
            "<html><b>Datos del Paciente:</b><br>" +
            "Nombre: " + pac.getNombre() + "<br>" +
            "Apellidos: " + pac.getApellidoPaterno() + " " + pac.getApellidoMaterno() + "<br>" +
            "Correo: " + pac.getCorreo() + "<br>" +
            "Dirección: " + pac.getDireccion() + "<br>" +
            "DNI: " + pac.getDni() + "<br>" +
            "Género: " + pac.getGenero() + "<br>" +
            "Teléfono: " + pac.getTelefono() + "<br>" +
            "Tipo de Sangre: " + pac.getTipoSangre().getDbValue() + "<br>" +
            "Rol: " + pac.getRol().getDbValue(),
            "Verificación de " + confir,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE
        );
        return opc;
    }
    
    public void limpiar() throws SQLException{
        this.TxtDni.setText(null);
        this.TxtApellidoMa.setText(null);
        this.TxtApellidoPa.setText(null);
        this.TxtDomicilio.setText(null);
        this.TxtEmail.setText(null);
        this.TxtFechaNac.setDate(null);
        this.TxtFechaRegistro.setDate(null);
        this.TxtGenero.setText(null);
        this.TxtNombres.setText(null);
        this.TxtTelefono.setText(null);
        this.ComboBoxTipoSangre.setSelectedIndex(0);
        this.ComboxRolPaciente.setSelectedIndex(0);
        this.codeGen = GeneradorCode.CodeGenerador("PAC", null);
        this.codeGenerado.setText(codeGen);
        this.chbFechaHoy.setSelected(false);
    }

    private void TxtApellidoPaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtApellidoPaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtApellidoPaActionPerformed

    private void TxtGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtGeneroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtGeneroActionPerformed

    private void chbFechaHoyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbFechaHoyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chbFechaHoyActionPerformed

    private void BtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_BtnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(NewPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new NewPaciente().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(NewPaciente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCancelar;
    private javax.swing.JButton BtnRegistrar;
    private javax.swing.JComboBox<String> ComboBoxTipoSangre;
    private javax.swing.JComboBox<String> ComboxRolPaciente;
    private javax.swing.JTextField TxtApellidoMa;
    private javax.swing.JTextField TxtApellidoPa;
    private javax.swing.JTextField TxtDni;
    private javax.swing.JTextField TxtDomicilio;
    private javax.swing.JTextField TxtEmail;
    private com.toedter.calendar.JDateChooser TxtFechaNac;
    private com.toedter.calendar.JDateChooser TxtFechaRegistro;
    private javax.swing.JTextField TxtGenero;
    private javax.swing.JTextField TxtNombres;
    private javax.swing.JTextField TxtTelefono;
    private javax.swing.JCheckBox chbFechaHoy;
    private javax.swing.JLabel codeGenerado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
