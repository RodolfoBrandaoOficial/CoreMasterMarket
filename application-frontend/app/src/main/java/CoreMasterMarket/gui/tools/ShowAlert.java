package CoreMasterMarket.gui.tools;


import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 *
 * @author rodolfobrandao
 */
public class ShowAlert extends javax.swing.JFrame {

    private String message;
    private String tiposervico;

    /**
     * Construtor da classe ShowAlert.
     *
     * @param message a mensagem a ser exibida no alerta.
     * @param tiposervico o tipo de serviço associado à mensagem.
     */
    public ShowAlert(String message, String tiposervico) {
        initComponents();
        this.message = message;
        this.tiposervico = tiposervico;
        jTxtAlertRecebido.setText(message);
        jTxtAlertService.setText(tiposervico);

        // Exibe a tela assim que o construtor é chamado
        setVisible(true);
        // Define a janela para sempre estar no topo
        setAlwaysOnTop(true);
        // Trás a janela para frente de todas as outras
        toFront();

        // Inicia uma thread para executar falarMensagem() em paralelo
        new Thread(() -> falarMensagem(message, tiposervico)).start();

        // Programa a tela para ser fechada após 6 segundos
        FecharTela(this);
    }

    /**
     * Método responsável por reproduzir a mensagem de áudio.
     *
     * @param message a mensagem a ser convertida em áudio.
     * @param tiposervico o tipo de serviço associado à mensagem.
     */
        static void falarMensagem(String message, String tiposervico) {
        try {
            String encodedMessage = URLEncoder.encode("" + tiposervico + " " + message + "!", "UTF-8");
            String url = "https://translate.google.com/translate_tts?ie=UTF-8&tl=pt&q=" + encodedMessage + "&client=tw-ob";
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (InputStream stream = connection.getInputStream()) {
                    if (!connection.getContentType().startsWith("audio")) {
                        throw new IOException("O conteúdo retornado pela URL não é um arquivo de áudio.");
                    }

                    AdvancedPlayer player = new AdvancedPlayer(stream);
                    player.play();
                } catch (JavaLayerException ex) {
                    ex.printStackTrace();
                }
            } else {
                System.err.println("Erro ao acessar a URL da API do Google Translate TTS. Código de resposta: " + connection.getResponseCode());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Método responsável por fechar a tela após um determinado período de
     * tempo.
     *
     * @param instanciaJanela a instância da janela que será fechada.
     */
    private void FecharTela(final ShowAlert instanciaJanela) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                instanciaJanela.dispose();
            }
        }, 7000);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jTxtAlertRecebido = new javax.swing.JTextField();
        jTxtAlertEquipe = new javax.swing.JTextField();
        jTxtAlertService = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel.setBackground(new java.awt.Color(255, 51, 0));

        jDesktopPane1.setBackground(new java.awt.Color(255, 255, 255));
        jDesktopPane1.setForeground(new java.awt.Color(255, 255, 255));

        jTxtAlertRecebido.setEditable(false);
        jTxtAlertRecebido.setBackground(new java.awt.Color(255, 255, 255));
        jTxtAlertRecebido.setFont(jTxtAlertRecebido.getFont().deriveFont(jTxtAlertRecebido.getFont().getSize()+17f));
        jTxtAlertRecebido.setForeground(new java.awt.Color(0, 0, 0));
        jTxtAlertRecebido.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtAlertRecebido.setText("COLABORADOR");
        jTxtAlertRecebido.setBorder(null);

        jTxtAlertEquipe.setEditable(false);
        jTxtAlertEquipe.setBackground(new java.awt.Color(255, 255, 255));
        jTxtAlertEquipe.setFont(jTxtAlertEquipe.getFont().deriveFont(jTxtAlertEquipe.getFont().getSize()+43f));
        jTxtAlertEquipe.setForeground(new java.awt.Color(102, 0, 204));
        jTxtAlertEquipe.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtAlertEquipe.setText("CORE MASTER!");
        jTxtAlertEquipe.setBorder(null);
        jTxtAlertEquipe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtAlertEquipeActionPerformed(evt);
            }
        });

        jTxtAlertService.setEditable(false);
        jTxtAlertService.setBackground(new java.awt.Color(255, 255, 255));
        jTxtAlertService.setFont(new java.awt.Font("Helvetica Neue", 0, 36)); // NOI18N
        jTxtAlertService.setForeground(new java.awt.Color(0, 0, 204));
        jTxtAlertService.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtAlertService.setText("CAIXA");
        jTxtAlertService.setBorder(null);
        jTxtAlertService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtAlertServiceActionPerformed(evt);
            }
        });

        jDesktopPane1.setLayer(jTxtAlertRecebido, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jTxtAlertEquipe, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jTxtAlertService, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTxtAlertService, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtAlertRecebido, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtAlertEquipe, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTxtAlertEquipe, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTxtAlertRecebido, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTxtAlertService, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 701, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 408, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTxtAlertEquipeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtAlertEquipeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtAlertEquipeActionPerformed

    private void jTxtAlertServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtAlertServiceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtAlertServiceActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JPanel jPanel;
    private javax.swing.JTextField jTxtAlertEquipe;
    private javax.swing.JTextField jTxtAlertRecebido;
    private javax.swing.JTextField jTxtAlertService;
    // End of variables declaration//GEN-END:variables

}
