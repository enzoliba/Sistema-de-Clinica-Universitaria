
package GUI;

import Datos.DHistorialMedico;
import Datos.DPaciente;
import Entidades.EPaciente;
import Entidades.EHistorialMedico;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class NewHistorial extends javax.swing.JFrame {
   // String IDPaciente;

   
    public NewHistorial() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public NewHistorial(EHistorialMedico his) throws SQLException{
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        CodigoHistorial = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        FechaHistorial = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TxtAlergias = new javax.swing.JTextField();
        TxtCondicionesCro = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        TxtMedicamentosFre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        BtnRegistrar = new javax.swing.JButton();
        BtnCancelar = new javax.swing.JButton();
        DniPaHistorial = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(68, 1, 30));
        jLabel1.setText("Codigo Historial Medico:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, -1, -1));

        CodigoHistorial.setText("codigo");
        getContentPane().add(CodigoHistorial, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, -1, -1));

        jLabel2.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(68, 1, 30));
        jLabel2.setText("Fecha actual:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, -1, -1));
        getContentPane().add(FechaHistorial, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, 140, -1));

        jLabel3.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(68, 1, 30));
        jLabel3.setText("Alergias del paciente:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, -1, 20));

        jLabel4.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(68, 1, 30));
        jLabel4.setText("Condiciones cronicas del paciente:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 370, -1, -1));
        getContentPane().add(TxtAlergias, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, 270, 60));

        TxtCondicionesCro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtCondicionesCroActionPerformed(evt);
            }
        });
        getContentPane().add(TxtCondicionesCro, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 390, 270, 60));

        jLabel5.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(68, 1, 30));
        jLabel5.setText("Medicamentos frecuentes del paciente:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 480, -1, -1));
        getContentPane().add(TxtMedicamentosFre, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 500, 270, 60));

        jLabel6.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(68, 1, 30));
        jLabel6.setText("DNI del paciente:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, -1, -1));

        BtnRegistrar.setBackground(new java.awt.Color(68, 1, 30));
        BtnRegistrar.setFont(new java.awt.Font("Agave Nerd Font", 1, 22)); // NOI18N
        BtnRegistrar.setText("REGISTRAR");
        BtnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRegistrarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 600, 140, 40));

        BtnCancelar.setBackground(new java.awt.Color(68, 1, 30));
        BtnCancelar.setFont(new java.awt.Font("Agave Nerd Font", 1, 22)); // NOI18N
        BtnCancelar.setText("CANCELAR");
        BtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 600, 130, 40));

        DniPaHistorial.setText("DNI");
        getContentPane().add(DniPaHistorial, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Imagenes/RegistgroHistorial.png"))); // NOI18N
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TxtCondicionesCroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtCondicionesCroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtCondicionesCroActionPerformed

    private void BtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCancelarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCancelarActionPerformed

    
    
    
    
    
    
    
    //-->>DESDE AQUI->>
    
    private void BtnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRegistrarActionPerformed
        String IDPaciente = null;
        if(this.FechaHistorial.getText().length() == 0){
           JOptionPane.showMessageDialog(rootPane, "Ingrese fecha actual");
           return; 
        }
        if(this.TxtAlergias.getText().length() == 0){
           JOptionPane.showMessageDialog(rootPane, "Ingrese las alergias del paciente");
           return; 
        }
        if(this.TxtCondicionesCro .getText().length() == 0){
           JOptionPane.showMessageDialog(rootPane, "Ingrese las condiciones cronicas del paciente");
           return; 
        }
        if(this.TxtMedicamentosFre.getText().length() == 0){
           JOptionPane.showMessageDialog(rootPane, "Ingrese los medicamentos frcuentes del paciente");
           return; 
        }

        
        EHistorialMedico His = new EHistorialMedico();
        DPaciente Dpa = new DPaciente();
        EPaciente Epa = new EPaciente(); 
        
        His.setIdHistorial(this.CodigoHistorial.getText());
        String dni = this.DniPaHistorial.getText();
        try {
            IDPaciente = Dpa.buscarIdPorDni(dni);
        } catch (SQLException ex) {
            Logger.getLogger(NewHistorial.class.getName()).log(Level.SEVERE, null, ex);
        }
        Epa = Dpa.buscarPacientePorId(IDPaciente);
        His.setPaciente(Epa);
        His.setAlergias(this.TxtAlergias.getText());
        His.setCondicionesCronicas(this.TxtCondicionesCro.getText());
        His.setMedicamentosFrecuentes(this.TxtMedicamentosFre.getText());
        
        
        String tipo[] = {"",""};
        DHistorialMedico HisDao = new DHistorialMedico();
        int opc = 1;
        if (this.BtnRegistrar .getText().equals("Registrar")) {
            opc = confirmacionRegistroModificar(His, "Registro");
            tipo[0] = "registrar";
            tipo[1] = "registrado";
        }else if (this.BtnRegistrar.getText().equals("Actualizar")){
            opc = confirmacionRegistroModificar(His, "Actualizacion");
            tipo[0] = "actualizar";
            tipo[1] = "actualizado";
            //amb.setIdAmbiente(this.codigoAmbiente.getText());
        }
        
         if (opc != 0) {
            return;
        }
         
         try {
            if(this.BtnRegistrar.getText().equals("Registrar")){
                HisDao.registrarHistorialMe(His);
            
            }else if(this.BtnRegistrar.getText().equals("Actualizar")){
                HisDao.actualizarHistorialMed(His);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewAmbiente.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, "El Ambiente no se pudo registrar, revise que los datos sean correctos.");
            return;
        }
         JOptionPane.showMessageDialog(rootPane, "Ambiente registrado correctamente ");
         try {
            limpiar();
        } catch (SQLException ex) {
            Logger.getLogger(NewPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BtnRegistrarActionPerformed

    //HASTA AQUI ->>
   
    public int confirmacionRegistroModificar(EHistorialMedico His, String confir){
        int opcion = JOptionPane.showConfirmDialog(
            rootPane,
            "<html><b>Datos del Paciente:</b><br>" +
            "IdHistorial: " + His.getIdHistorial() + "<br>" +
            "DniPaciente: " + His.getPaciente().getDni() + "<br>" +
            "Alergias: " + His.getAlergias() + "<br>" +
            "CondicionesCro: " + His.getCondicionesCronicas() + "<br>" +
            "MedicamentosFre: " + His.getMedicamentosFrecuentes(),
            "Verificación del Registro",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE
        );
        return opcion;
    }
    
    public void limpiar() throws SQLException{
        this.CodigoHistorial.setText(null);
        this.DniPaHistorial.setText(null);
        this.TxtAlergias.setText(null);
        this.TxtCondicionesCro.setText(null);
        this.TxtCondicionesCro.setText(null);
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
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
            java.util.logging.Logger.getLogger(NewHistorial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewHistorial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewHistorial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewHistorial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewHistorial().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCancelar;
    private javax.swing.JButton BtnRegistrar;
    private javax.swing.JLabel CodigoHistorial;
    private javax.swing.JLabel DniPaHistorial;
    private javax.swing.JFormattedTextField FechaHistorial;
    private javax.swing.JTextField TxtAlergias;
    private javax.swing.JTextField TxtCondicionesCro;
    private javax.swing.JTextField TxtMedicamentosFre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables
}
