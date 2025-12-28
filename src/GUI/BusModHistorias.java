
package GUI;

import Datos.DHistorialMedico;
import Datos.DPaciente;
import Entidades.EHistorialMedico;
import Entidades.EPaciente;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author ENZO
 */
public class BusModHistorias extends javax.swing.JFrame {

    private DefaultTableModel modeloTabla;
    private String cabecera[] = {"Codigo", "DNI del Paciente", "Fecha de Creación", "Alergias", "Condiciones Cronicas", "Medicamentos frecuentes", "Numero de atenciones"};
    private DHistorialMedico hisMedDao;
    private DateTimeFormatter dateFormatter;
    private boolean OpcLlenadoT = false; 
    
    public BusModHistorias() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.hisMedDao = new DHistorialMedico(); // Crea una instancia de tu DAO
        
        // Inicializa el modelo de la tabla con la cabecera
        modeloTabla = new DefaultTableModel(null, cabecera) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace que las celdas no sean editables
            }
        };
        this.TablaHistoriales.setModel(modeloTabla); // Asigna el modelo a tu JTable
        
        this.dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        cargarHistoriasEnTabla(OpcLlenadoT);
    }
    
    public void cargarHistoriasEnTabla(boolean opcllT) {
        // Limpiar todas las filas existentes en la tabla antes de cargar nuevas
        modeloTabla.setRowCount(0); 

        // Obtener la lista de historiales médicos de la base de datos a través del DAO
        List<EHistorialMedico> listaHistorias = new ArrayList<>();
        if(opcllT == false){
             listaHistorias = hisMedDao.listarHistorialesConAtenciones();
        }else{
            String codigo = this.TxtBusPorCodico.getText();
            EHistorialMedico Historia = hisMedDao.buscarHistorialPorId(codigo);
            if (opcllT == true){
                listaHistorias.add(Historia);
            }
        }
            
        

        // Verificar si la lista no es nula y llenarla
        if (listaHistorias != null && !listaHistorias.isEmpty()) {
            for (EHistorialMedico historial : listaHistorias) {
                // Preparar los datos para una fila
               String[] fila = new String[cabecera.length]; // Usar la longitud de la cabecera

                // Columna 1: Código del Historial
                fila[0] = historial.getIdHistorial();

                // Datos del Paciente (relacionado 1:1 con HistorialMedico)
                if (historial.getPaciente() != null) {
                    EPaciente paciente = historial.getPaciente();
                    fila[1] = paciente.getDni(); // DNI del Paciente
                } else {
                    fila[1] = "N/A";
                }

                // Columna 3: Fecha de Creación del Historial (usando tu campo "Fecha Cita" como placeholder)
                if (historial.getFechaCreacion() != null) {
                    fila[2] = historial.getFechaCreacion().format(dateFormatter);
                } else {
                    fila[2] = "N/A";
                }
                // Columna 4: Alergias
                fila[3] = historial.getAlergias() != null && !historial.getAlergias().isEmpty() ? historial.getAlergias() : "N/A";
                // Columna 5: Condiciones Crónicas
                fila[4] = historial.getCondicionesCronicas() != null && !historial.getCondicionesCronicas().isEmpty() ? historial.getCondicionesCronicas() : "N/A";
                // Columna 6: medicamentos frecuentes
                fila[5] = historial.getMedicamentosFrecuentes() != null && !historial.getMedicamentosFrecuentes().isEmpty() ? historial.getMedicamentosFrecuentes(): "N/A";
                // Aquí mostramos la cantidad de atenciones asociadas a este historial
                int atencionesTotal = historial.getAtenciones().size();
                fila[6] = "" + atencionesTotal; // Muestra el conteo de atenciones
                // Añadir la fila al modelo de la tabla
                this.modeloTabla.addRow(fila);
            }
        } else {
            System.out.println("La lista de historiales médicos obtenida de la base de datos es nula o vacía.");
        }
    }
    
    
    
    public void cargarHistoriaEncontrada(){
        
        
        
    }
    
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TablaHistoriales = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        TxtBusPorCodico = new javax.swing.JTextField();
        BtnSigPag = new javax.swing.JButton();
        BtnAntPag = new javax.swing.JButton();
        BtnBuscar = new javax.swing.JButton();
        BtnModif = new javax.swing.JButton();
        BtnCerrar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaHistoriales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "DNI Paciente", "Fecha Creación", "Alergias", "Condiciones Crónicas", "Medicamentos Frecuentes"
            }
        ));
        jScrollPane1.setViewportView(TablaHistoriales);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 913, 420));

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("Buscar por Codigo:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, -1, -1));
        getContentPane().add(TxtBusPorCodico, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, 210, -1));

        BtnSigPag.setBackground(new java.awt.Color(0, 102, 102));
        BtnSigPag.setFont(new java.awt.Font("Agave Nerd Font", 1, 20)); // NOI18N
        BtnSigPag.setForeground(new java.awt.Color(255, 255, 255));
        BtnSigPag.setText("Siguiente ->");
        getContentPane().add(BtnSigPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 130, -1, -1));

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

        BtnBuscar.setBackground(new java.awt.Color(0, 102, 102));
        BtnBuscar.setFont(new java.awt.Font("Agave Nerd Font", 1, 20)); // NOI18N
        BtnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        BtnBuscar.setText("BUSCAR");
        BtnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 640, -1, 36));

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
        getContentPane().add(BtnModif, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 640, -1, 36));

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
        getContentPane().add(BtnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 640, -1, 39));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Imagenes/bus-modHistoriales.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnAntPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAntPagActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAntPagActionPerformed

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed
        if (this.TxtBusPorCodico.getText().trim().isEmpty()){
            cargarHistoriasEnTabla(false);
        }else{
            cargarHistoriasEnTabla(true);
        }
        
    }//GEN-LAST:event_BtnBuscarActionPerformed

    private void BtnModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnModifActionPerformed
        DHistorialMedico DHis = new DHistorialMedico();
        String idHistorial;
        EHistorialMedico His = new EHistorialMedico();
        if (this.TablaHistoriales.getSelectedRow() != -1){
            
            idHistorial = (String)this.TablaHistoriales.getValueAt(this.TablaHistoriales.getSelectedRow(), 0);
            His = DHis.buscarHistorialPorId(idHistorial);
            NewHistorial NH = null;
            try {
                NH = new NewHistorial(His);
            } catch (SQLException ex) {
                Logger.getLogger(BusModHistorias.class.getName()).log(Level.SEVERE, null, ex);
            }
            NH.setVisible(true);
            
        }else{
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un Historial", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
    }//GEN-LAST:event_BtnModifActionPerformed

    private void BtnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_BtnCerrarActionPerformed

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
            java.util.logging.Logger.getLogger(BusModHistorias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BusModHistorias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BusModHistorias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BusModHistorias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BusModHistorias().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAntPag;
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnCerrar;
    private javax.swing.JButton BtnModif;
    private javax.swing.JButton BtnSigPag;
    private javax.swing.JTable TablaHistoriales;
    private javax.swing.JTextField TxtBusPorCodico;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
