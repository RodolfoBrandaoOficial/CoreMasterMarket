package CoreMasterMarket.gui.tools;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import javax.swing.SwingUtilities;
import static spark.Spark.before;
import static spark.Spark.halt;
import static spark.Spark.port;

/**
 *
 * @author rodolfobrandao
 */
public class TestarSistema extends javax.swing.JFrame {

    /**
     * Creates new form SendAlert
     */
    public TestarSistema() {
        initComponents();
    }
    private static final String USERNAME = "Admin";
    private static final String PASSWORD = "Senha@123123";
    
    public static String dadosLog;
    
    public static void enviarWebhook(String mensagem, String tiposervico) {

        dadosLog = "\nCodigo de debugação: \n\n\n" + "Tipo do Serviço: " + tiposervico + "\n\n" + "Nome dos envolvidos: " + mensagem;
        System.out.println(dadosLog);

        TestarSistema sendAlert = new TestarSistema();
        sendAlert.setLogText(dadosLog);

        try {
            // Enviar a solicitação POST
            URL url = new URL("http://127.0.0.1:3730/webhook?alert=sim&mensagem=NOME2 e NOME1&tiposervico=LIMPEZA");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            // Adicionar autenticação básica aos headers
            String authString = USERNAME + ":" + PASSWORD;
            String encodedAuth = Base64.getEncoder().encodeToString(authString.getBytes());
            connection.setRequestProperty("Authorization", "Basic " + encodedAuth);
            int responseCode = connection.getResponseCode();
            System.out.println("Response code: " + responseCode);

            // Exibir a notificação de alerta
            ShowAlert(mensagem, tiposervico);

            // Exibir um alerta oculto
            showHiddenAlert();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static void showHiddenAlert() {
        // Exibir um alerta oculto
//        JOptionPane.showMessageDialog(null, "Este é um alerta oculto");

    }
    
    private static void ShowAlert(String message, String tiposervico) {
        SwingUtilities.invokeLater(() -> {
            ShowAlert showAlert = new ShowAlert(message, tiposervico);
            showAlert.setVisible(true);
            showAlert.toFront();
        });
    }
    
    public void mostrarJanela() {
//        SendAlert.setVisible(true);
    }

    public void setLogText(String text) {

        jtxtLog.setText(text);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jColorChooser1 = new javax.swing.JColorChooser();
        jDialog1 = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        lbtSendAlert = new javax.swing.JButton();
        jtxtMensagem = new javax.swing.JTextField();
        jtxtTiposervico = new javax.swing.JTextField();
        jlbEquipe = new javax.swing.JLabel();
        jlbTipoServico = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtxtLog = new javax.swing.JTextPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MODO TESTE ENVIO DE ALERT");
        setBackground(new java.awt.Color(51, 0, 255));

        jPanel1.setBackground(new java.awt.Color(102, 51, 255));

        lbtSendAlert.setBackground(new java.awt.Color(51, 51, 255));
        lbtSendAlert.setText("ENVIAR TESTE ALERT");
        lbtSendAlert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lbtSendAlertActionPerformed(evt);
            }
        });

        jtxtMensagem.setText("JULIA");

        jtxtTiposervico.setText("SEU CAIXA IRÁ INICIAR");
        jtxtTiposervico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtTiposervicoActionPerformed(evt);
            }
        });

        jlbEquipe.setText("COLABORADOR");

        jlbTipoServico.setText("CAIXA");

        jtxtLog.setForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jtxtLog);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(56, 56, 56)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jlbEquipe, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(3, 3, 3)
                            .addComponent(jtxtMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jlbTipoServico, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jtxtTiposervico, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(58, 58, 58)
                            .addComponent(lbtSendAlert, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(57, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(125, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(37, 37, 37)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlbEquipe, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtxtMensagem))
                    .addGap(58, 58, 58)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtxtTiposervico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlbTipoServico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(230, 230, 230)
                    .addComponent(lbtSendAlert, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(38, 38, 38)))
        );

        jMenuBar1.setBackground(new java.awt.Color(102, 51, 255));

        jMenu1.setText("File");

        jMenuItem1.setText("Fechar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lbtSendAlertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lbtSendAlertActionPerformed
        // TODO add your handling code here:
        TestarSistema.enviarWebhook(jtxtMensagem.getText(), jtxtTiposervico.getText());
        setLogText(TestarSistema.dadosLog);
        
        

    }//GEN-LAST:event_lbtSendAlertActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jtxtTiposervicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtTiposervicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtTiposervicoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        
        
        //CHAMAR TELA
        SwingUtilities.invokeLater(new Runnable() {
            ;
            @Override
            public void run() {
                TestarSistema testarSistema = new TestarSistema();
                testarSistema.setVisible(true);
            }
        });
//FIM DA CHAMAR TELA
port(3730);

        // Configurar filtro para autenticação básica
        before((request, response) -> {
            String authHeader = request.headers("Authorization");
            if (authHeader == null || !authHeader.startsWith("Basic ")) {
                halt(401, "Autenticação necessária");
            } else {
                String encodedAuth = authHeader.substring("Basic ".length()).trim();
                String decodedAuth = new String(Base64.getDecoder().decode(encodedAuth));
                String[] credentials = decodedAuth.split(":");
                if (credentials.length != 2 || !credentials[0].equals(USERNAME) || !credentials[1].equals(PASSWORD)) {
                    halt(403, "Credenciais inválidas");
                }
            }
        });

        
        
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
            java.util.logging.Logger.getLogger(TestarSistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TestarSistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TestarSistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TestarSistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TestarSistema().setVisible(true);
                
               
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JColorChooser jColorChooser1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jlbEquipe;
    private javax.swing.JLabel jlbTipoServico;
    private javax.swing.JTextPane jtxtLog;
    private javax.swing.JTextField jtxtMensagem;
    private javax.swing.JTextField jtxtTiposervico;
    private javax.swing.JButton lbtSendAlert;
    // End of variables declaration//GEN-END:variables
}
