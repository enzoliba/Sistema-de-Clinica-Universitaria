/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Datos.DAtenciones;
import Entidades.EAtencion;
import Entidades.ECita;
import Entidades.EHistorialMedico;
import Entidades.EPaciente;
import Entidades.E_Empleado;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ENZO
 */
public class BusModAtenciones extends javax.swing.JFrame {

    
     public DefaultTableModel modelo; 
    private DAtenciones atencionDao = new DAtenciones(); 
    public String cabecera [] = {
        "codigo Cita", "DNI Paciente", "Nombre Paciente", "Apellido Paciente", "Medico", 
        "Fecha", "Hora", "Diagnostico ", "Receta"
    };
    
    public BusModAtenciones() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        modelo = new DefaultTableModel ( null, cabecera );
        this.TAtencion.setModel(modelo);
        actualizarTabla (); 
        
    }
    
    
      private void actualizarTabla() {
          modelo = new  DefaultTableModel (null, cabecera); 
          this.TAtencion.setModel(modelo);
          
          List<EAtencion> atenciones = atencionDao.listarTodasAtenciones(); 
          SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
          SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
          
          if(atenciones != null){
              for(EAtencion a : atenciones ){
                  if ( a == null) continue; 
                  
                  ECita c = a.getCita();
                  EPaciente p = (a.getHistorial() != null)
                          ? a.getHistorial().getPaciente() : null;
                  E_Empleado m = a.getEmpleado();
                  
                  if (c == null || p == null || m == null) continue; 
                  String fila [] = {
                      c.getIdCita() != null ? c.getIdCita() : "",
                      p.getDni() != null ? p.getDni() : "", 
                      p.getNombre() != null ? p.getNombre() : "", 
                      ((p.getApellidoPaterno() != null ? p.getApellidoPaterno() + "" : "")
                          + (p.getApellidoMaterno() != null ? p.getApellidoMaterno() : "").trim()),
                      ((m.getNombre() != null ? m.getNombre() + "" : "") 
                          + (m.getApellidoPaterno() != null ? m.getApellidoPaterno() + "" : "")
                          + (m.getApellidoMaterno() != null ? m.getApellidoMaterno() : "").trim()),
                      c.getFecha() != null ? formatoFecha.format(c.getFecha()) : "",
                      c.getHora() != null ? formatoHora.format(c.getHora()) : "",
                      (a.getDiagnostico() != null && a.getDiagnostico().getDescripcionAdicional() != null)
                          ? a.getDiagnostico().getDescripcionAdicional() : "",
                      (a.getReceta() != null && a.getReceta().getDescripcionAdicional() != null )
                          ? a.getReceta().getDescripcionAdicional() : ""
                          
                  };
                  modelo.addRow(fila);
              }
          }
          
      }   
      
      private void buscarPorCodigo (String codigo){
           modelo = new  DefaultTableModel (null, cabecera); 
           EAtencion a = atencionDao.buscarAtencionPorId(codigo); 
           
           if(a != null){
               EHistorialMedico h = a.getHistorial(); 
               EPaciente paciente = (h != null) ? h.getPaciente() : null;
               E_Empleado medico = a.getEmpleado(); 
               
               if(paciente != null && medico != null){
                   SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                   
                   String fila[] = {
                       a.getIdAtencion() != null ? a.getIdAtencion() : "", 
                       paciente.getDni() != null ? paciente.getDni() : "",
                       paciente.getNombre() != null ? paciente.getNombre() : "",
                       (paciente.getApellidoPaterno() != null ? paciente.getApellidoPaterno() : "") + "" +
                       (paciente.getApellidoMaterno()!= null ? paciente.getApellidoMaterno(): ""),
                       (medico.getNombre() != null ? medico.getNombre () : "" ) + "" +
                       (medico.getApellidoMaterno() != null ? medico.getApellidoMaterno(): "" ),
                       (a.getCita() != null && a.getCita().getAmbiente() != null ? a.getCita().getAmbiente().getIdAmbiente() : ""),
                        a.getFecha() != null ? formatoFechaHora.format(a.getFecha()) : "",
                        a.getDescripcion() != null ? a.getDescripcion() : "",
                        a.getObservaciones() != null ? a.getObservaciones() : ""       
                   };
                   this.modelo.addRow(fila);
               }
                       
           }
           TAtencion.setModel(modelo);
               
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
        TAtencion = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TAtencion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo Cita", "Dni Paciente", "Nombre Paciente", "Apellidos Paciente", "Medico", "Fecha Cita", "Diagnóstico", "Receta"
            }
        ));
        jScrollPane1.setViewportView(TAtencion);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel1.setText("Buscar por Codigo:");

        jButton1.setText("Buscar");

        jButton2.setText("Regresar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(BusModAtenciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BusModAtenciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BusModAtenciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BusModAtenciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BusModAtenciones().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TAtencion;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

  
}
