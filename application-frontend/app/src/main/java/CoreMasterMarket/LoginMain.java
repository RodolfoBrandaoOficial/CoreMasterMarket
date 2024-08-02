/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package CoreMasterMarket;

import CoreMasterMarket.gui.App;
import CoreMasterMarket.config.ConfigReal;
import CoreMasterMarket.gui.tools.CondicaoTempo;
import CoreMasterMarket.gui.tools.ShowAlert;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author rodolfobrandao
 */
public class LoginMain extends javax.swing.JDialog {

    private String urlAPI = ConfigReal.urlAPI;
    private String tokenAPI = ConfigReal.token;
    private String userId = ConfigReal.userId;
    private String userLogin = ConfigReal.userLogin;
    private String userPassword = ConfigReal.userPassword;
    private String userRole = ConfigReal.userRole;
    private String userName = ConfigReal.userName;
    private String userFullname = ConfigReal.userFullname;
    private String userUsername = ConfigReal.userUsername;
    private String userProfileImg = ConfigReal.userProfileImg;
    
    String saudacao = CondicaoTempo.obterSaudacao();
    
    private static void ShowAlert(String message, String tiposervico) {
        SwingUtilities.invokeLater(() -> {
            ShowAlert showAlert = new ShowAlert(message, tiposervico);
            showAlert.setVisible(true);
            showAlert.toFront();
        });
    }
    /**
     * Creates new form LoginMain
     */
    public LoginMain(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jpanelesquerdo = new javax.swing.JPanel();
        usernameinsert = new javax.swing.JTextField();
        lbuser = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        lbpass = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        passwordinsert = new javax.swing.JPasswordField();
        iconpass = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        Login = new javax.swing.JLabel();
        iconuser = new javax.swing.JLabel();
        iconuser1 = new javax.swing.JLabel();
        jpaneldireito = new javax.swing.JPanel();
        jpaneldireitocentro = new javax.swing.JPanel();
        iconlogo1 = new javax.swing.JLabel();
        textlogo = new javax.swing.JLabel();
        iconlogo2 = new javax.swing.JLabel();
        iconlogo0 = new javax.swing.JLabel();
        iconlogo3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(254, 254, 254));

        jpanelesquerdo.setBackground(new java.awt.Color(254, 254, 254));

        usernameinsert.setBorder(null);
        usernameinsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameinsertActionPerformed(evt);
            }
        });

        lbuser.setFont(new java.awt.Font("Simplifica", 0, 24)); // NOI18N
        lbuser.setText("Usuário");

        lbpass.setFont(new java.awt.Font("Simplifica", 0, 24)); // NOI18N
        lbpass.setText("Senha");

        passwordinsert.setBorder(null);

        iconpass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/files/vr-cores/icons8-Password-26.png"))); // NOI18N

        jLabel32.setForeground(new java.awt.Color(43, 43, 43));
        jLabel32.setText("Esqueceu a senha?");
        jLabel32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel32MousePressed(evt);
            }
        });

        Login.setBackground(new java.awt.Color(255, 51, 0));
        Login.setFont(new java.awt.Font("Simplifica", 0, 24)); // NOI18N
        Login.setForeground(new java.awt.Color(254, 254, 254));
        Login.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Login.setText("Entrar");
        Login.setOpaque(true);
        Login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                LoginMousePressed(evt);
            }
        });

        iconuser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/files/vr-cores/icons8-User-26.png"))); // NOI18N

        iconuser1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/files/vr-cores/icons8-Back-20.png"))); // NOI18N

        javax.swing.GroupLayout jpanelesquerdoLayout = new javax.swing.GroupLayout(jpanelesquerdo);
        jpanelesquerdo.setLayout(jpanelesquerdoLayout);
        jpanelesquerdoLayout.setHorizontalGroup(
            jpanelesquerdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelesquerdoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanelesquerdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iconpass)
                    .addComponent(iconuser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpanelesquerdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpanelesquerdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lbpass)
                        .addComponent(lbuser)
                        .addComponent(usernameinsert)
                        .addComponent(jSeparator6)
                        .addComponent(jSeparator7)
                        .addComponent(passwordinsert, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpanelesquerdoLayout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(iconuser1))
                    .addComponent(Login, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(151, Short.MAX_VALUE))
        );
        jpanelesquerdoLayout.setVerticalGroup(
            jpanelesquerdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelesquerdoLayout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addGroup(jpanelesquerdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpanelesquerdoLayout.createSequentialGroup()
                        .addComponent(lbuser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(usernameinsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(iconuser))
                .addGap(15, 15, 15)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jpanelesquerdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbpass)
                    .addComponent(iconpass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordinsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Login, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jpanelesquerdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32)
                    .addComponent(iconuser1))
                .addGap(151, 151, 151))
        );

        jpaneldireito.setBackground(new java.awt.Color(255, 51, 0));

        jpaneldireitocentro.setBackground(new java.awt.Color(255, 51, 0));
        jpaneldireitocentro.setToolTipText("");

        iconlogo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/files/login/icons8-Facebook-26.png"))); // NOI18N
        iconlogo1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                iconlogo1MousePressed(evt);
            }
        });

        textlogo.setFont(new java.awt.Font("Simplifica", 0, 48)); // NOI18N
        textlogo.setForeground(new java.awt.Color(254, 254, 254));
        textlogo.setText("LOGIN SISTEMA");

        iconlogo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/files/login/icons8-Instagram-26.png"))); // NOI18N
        iconlogo2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                iconlogo2MousePressed(evt);
            }
        });

        iconlogo0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/files/login/icons8-Computer-100.png"))); // NOI18N

        iconlogo3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/files/login/icons8-Skype-26.png"))); // NOI18N
        iconlogo3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                iconlogo3MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jpaneldireitocentroLayout = new javax.swing.GroupLayout(jpaneldireitocentro);
        jpaneldireitocentro.setLayout(jpaneldireitocentroLayout);
        jpaneldireitocentroLayout.setHorizontalGroup(
            jpaneldireitocentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpaneldireitocentroLayout.createSequentialGroup()
                .addContainerGap(131, Short.MAX_VALUE)
                .addGroup(jpaneldireitocentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpaneldireitocentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jpaneldireitocentroLayout.createSequentialGroup()
                            .addComponent(iconlogo1)
                            .addGap(18, 18, 18)
                            .addComponent(iconlogo2)
                            .addGap(26, 26, 26)
                            .addComponent(iconlogo3))
                        .addComponent(textlogo))
                    .addComponent(iconlogo0, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jpaneldireitocentroLayout.setVerticalGroup(
            jpaneldireitocentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpaneldireitocentroLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(iconlogo0, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(textlogo)
                .addGap(16, 16, 16)
                .addGroup(jpaneldireitocentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(iconlogo1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iconlogo2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iconlogo3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jpaneldireitoLayout = new javax.swing.GroupLayout(jpaneldireito);
        jpaneldireito.setLayout(jpaneldireitoLayout);
        jpaneldireitoLayout.setHorizontalGroup(
            jpaneldireitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpaneldireitoLayout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addComponent(jpaneldireitocentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(256, Short.MAX_VALUE))
        );
        jpaneldireitoLayout.setVerticalGroup(
            jpaneldireitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpaneldireitoLayout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jpaneldireitocentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpanelesquerdo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpaneldireito, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelesquerdo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpaneldireito, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void usernameinsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameinsertActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameinsertActionPerformed

    private void jLabel32MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MousePressed
        //evento esqueceu a senha
    }//GEN-LAST:event_jLabel32MousePressed

    private void LoginMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LoginMousePressed
        String login = usernameinsert.getText();
        String password = new String(passwordinsert.getPassword());

        try {
            // URL do endpoint de login
            URL url = new URL(urlAPI + "/auth/login");
            System.out.println(url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            // Corpo da requisição
            String jsonInputString = String.format("{\"login\":\"%s\",\"password\":\"%s\"}", login, password);
            connection.getOutputStream().write(jsonInputString.getBytes("UTF-8"));

            // Ler a resposta
            StringBuilder response;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
                response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }

            // Parsear a resposta JSON
            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            tokenAPI = jsonResponse.get("token").getAsString();

            // Fazer a requisição para obter informações do usuário
            URL userInfoUrl = new URL(urlAPI + "/auth/userinfo");
            HttpURLConnection userInfoConnection = (HttpURLConnection) userInfoUrl.openConnection();
            userInfoConnection.setRequestMethod("GET");
            userInfoConnection.setRequestProperty("Authorization", "Bearer " + tokenAPI);

            // Ler a resposta da requisição de informações do usuário
            StringBuilder userInfoResponse;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(userInfoConnection.getInputStream(), "UTF-8"))) {
                userInfoResponse = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    userInfoResponse.append(line);
                }
            }

            // Parsear a resposta JSON de informações do usuário
            JsonObject userInfoJsonResponse = JsonParser.parseString(userInfoResponse.toString()).getAsJsonObject();

            // Armazenar os dados do usuário nas variáveis de instância
            ConfigReal.setUserId(userInfoJsonResponse.get("id").getAsString());
            ConfigReal.setUserLogin(userInfoJsonResponse.get("login").getAsString());
            ConfigReal.setUserPassword(userInfoJsonResponse.get("password").getAsString());
            ConfigReal.setUserRole(userInfoJsonResponse.get("role").getAsString());
            ConfigReal.setUserName(userInfoJsonResponse.get("name").getAsString());
            ConfigReal.setUserFullname(userInfoJsonResponse.get("fullname").getAsString());
            ConfigReal.setUserUsername(userInfoJsonResponse.get("username").getAsString());
            ConfigReal.setUserProfileImg(userInfoJsonResponse.has("profileImg") ? userInfoJsonResponse.get("profileImg").getAsString() : "");
            ConfigReal.setToken(tokenAPI);

            // Exibir mensagem de sucesso e as informações do usuário
            JOptionPane.showMessageDialog(this, "Login bem-sucedido! " + ConfigReal.getUserFullname());

            // Fazer o restante do fluxo após o login
            ShowAlert(ConfigReal.getUserFullname() + "! seu Caixa foi aberto!", saudacao);
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new App().setVisible(true);
                    dispose();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Falha no login: " + e.getMessage());
        }
    }//GEN-LAST:event_LoginMousePressed

    private void iconlogo1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconlogo1MousePressed
        // click facebook
    }//GEN-LAST:event_iconlogo1MousePressed

    private void iconlogo2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconlogo2MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_iconlogo2MousePressed

    private void iconlogo3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconlogo3MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_iconlogo3MousePressed

    private void iconlogo4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconlogo4MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_iconlogo4MousePressed

    private void iconlogo5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconlogo5MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_iconlogo5MousePressed

    private void iconlogo6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconlogo6MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_iconlogo6MousePressed

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
            java.util.logging.Logger.getLogger(LoginMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginMain dialog = new LoginMain(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Login;
    private javax.swing.JLabel iconlogo0;
    private javax.swing.JLabel iconlogo1;
    private javax.swing.JLabel iconlogo2;
    private javax.swing.JLabel iconlogo3;
    private javax.swing.JLabel iconpass;
    private javax.swing.JLabel iconuser;
    private javax.swing.JLabel iconuser1;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JPanel jpaneldireito;
    private javax.swing.JPanel jpaneldireitocentro;
    private javax.swing.JPanel jpanelesquerdo;
    private javax.swing.JLabel lbpass;
    private javax.swing.JLabel lbuser;
    private javax.swing.JPasswordField passwordinsert;
    private javax.swing.JLabel textlogo;
    private javax.swing.JTextField usernameinsert;
    // End of variables declaration//GEN-END:variables
}
