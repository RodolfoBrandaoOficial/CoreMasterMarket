package CoreMasterMarket.gui;

import CoreMasterMarket.DefinicaoOS;
import CoreMasterMarket.config.ConfigReal;
import CoreMasterMarket.service.PDVService;
import static CoreMasterMarket.service.PDVService.listModel;
import static CoreMasterMarket.service.PDVService.tabelaProdutos;
import static CoreMasterMarket.service.PDVService.tableModel;
import static CoreMasterMarket.service.PDVService.totalLabel;
import static CoreMasterMarket.service.PDVService.txtCodBarras;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rodolfobrandao
 */
public class App extends javax.swing.JFrame {

    private CardLayout cardLayout;
    static PDVService pdvService = new PDVService();

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

    public App() {
   
        initComponents();
        App.this.getRootPane().setBorder(new LineBorder(new Color(255, 51, 51)));
        lblTitle.setText(this.getTitle());
        cardLayout = (CardLayout) pnlRight.getLayout();
        ExecutarDadosUser();

        if (DefinicaoOS.getOSType() == DefinicaoOS.OSType.MacOS) {
            pnlTop.remove(pnlTitle);
            pnlTop.remove(pnlRight);

            pnlTop.add(pnlTitle, BorderLayout.EAST);
            pnlTop.add(pnlActions, BorderLayout.WEST);

            pnlActions.remove(lblClose);
            pnlActions.remove(lblMaximize);
            pnlActions.remove(lblMinimize);

            pnlActions.add(lblClose);
            pnlActions.add(lblMaximize);
            pnlActions.add(lblMinimize);

            pnlTitle.remove(lblTitle);
            pnlTitle.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 8));
            pnlTitle.add(lblTitle);

        }
        if (DefinicaoOS.getOSType() == DefinicaoOS.OSType.Windows) {
            pnlTop.remove(pnlTitle);
            pnlTop.remove(pnlRight);

            pnlTop.add(pnlTitle, BorderLayout.WEST);
            pnlTop.add(pnlActions, BorderLayout.EAST);

            pnlActions.remove(lblClose);
            pnlActions.remove(lblMaximize);
            pnlActions.remove(lblMinimize);

            pnlActions.add(lblMinimize);
            pnlActions.add(lblMaximize);
            pnlActions.add(lblClose);

            pnlTitle.remove(lblTitle);
            pnlTitle.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 8));
            pnlTitle.add(lblTitle);

            pdvService.produtos = new ArrayList<>();
            // Solicita o código do cliente
            String clienteInput = JOptionPane.showInputDialog("Digite o código do cliente:");
            try {
                pdvService.clienteId = Long.parseLong(clienteInput);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Código do cliente inválido!");
                System.exit(1);
            }
            // Inicializa o JTextField
            txtCodBarras = new JTextField(20);
            txtCodBarras.setToolTipText("Digite código de barras, descrição ou ID do produto");

            // Inicializa a tabela de produtos
            String[] colunas = {"ID", "Descrição", "Preço", "Quantidade"};
            tableModel = new DefaultTableModel(colunas, 0);
            tabelaProdutos = new JTable(tableModel);
            tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//            // Inicializa o JLabel para mostrar o valor total
            totalLabel = new JLabel("Total: R$ 0.0");
//
            // Inicializa o JList para sugestões
            listModel = new DefaultListModel<>();
            listSuggestions = new JList<>(listModel);
            listSuggestions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listSuggestions.setVisibleRowCount(5);
            listSuggestions.setBorder(BorderFactory.createTitledBorder("Sugestões"));
            // Botão Adicionar
            JButton btnAdicionar = new JButton("Adicionar");
            btnAdicionar.addActionListener(e -> pdvService.adicionarProdutoSelecionado());

            // Botão Adicionar Quantidade
            JButton btnAdicionarQuantidade = new JButton("Adicionar Quantidade");
            btnAdicionarQuantidade.addActionListener(e -> pdvService.adicionarQuantidadeProduto());

            // Botão Método de Pagamento
            JButton btnMetodoPagamento = new JButton("Método de Pagamento");
            btnMetodoPagamento.addActionListener(e -> pdvService.selecionarMetodoPagamento());

            // Botão Excluir Produto
            JButton btnExcluir = new JButton("Excluir Produto");
            btnExcluir.addActionListener(e -> pdvService.excluirProduto());

            // Botão Finalizar Venda
            JButton btnFinalizarVenda = new JButton("Finalizar Venda");
            btnFinalizarVenda.addActionListener(e -> pdvService.finalizarVenda());

            // Layout da interface
            JPanel panel = new JPanel(new BorderLayout());
            JPanel inputPanel = new JPanel();
            inputPanel.add(txtCodBarras);
            inputPanel.add(btnAdicionar);
            inputPanel.add(btnAdicionarQuantidade);
            inputPanel.add(btnMetodoPagamento);
            inputPanel.add(btnExcluir);
            inputPanel.add(btnFinalizarVenda);

            JPanel centerPanel = new JPanel(new BorderLayout());
            centerPanel.add(new JScrollPane(tabelaProdutos), BorderLayout.CENTER);
            centerPanel.add(new JScrollPane(listSuggestions), BorderLayout.EAST);

            panel.add(inputPanel, BorderLayout.NORTH);
            panel.add(centerPanel, BorderLayout.CENTER);
            panel.add(totalLabel, BorderLayout.SOUTH);

            // Criação do JFrame
            JFrame frame = new JFrame("PDV - Lista de Produtos");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(panel);
            frame.pack();
            frame.setVisible(true);
//            pdvService.Carrega os produtos
//            pdvService.carregarProdutos();

            // Adiciona um KeyListener para buscar enquanto digita
            txtCodBarras.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        pdvService.adicionarProdutoSelecionado();
                    } else if (e.getKeyCode() == KeyEvent.VK_F7) {
                        pdvService.adicionarQuantidadeProduto();
                    } else if (e.getKeyCode() == KeyEvent.VK_F8) {
                        pdvService.excluirProduto();
                    } else {
                        pdvService.buscarProduto(txtCodBarras.getText());
                    }
                }
            });

            // Adiciona um ListSelectionListener para o JList
            listSuggestions.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    String selectedValue = listSuggestions.getSelectedValue();
                    if (selectedValue != null) {
                        txtCodBarras.setText(selectedValue.split(" - ")[0]);
                    }
                }
            });
        }

    }


    public void ExecutarDadosUser() {
        NomeAtendenteCaixa.setText(userName);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTop = new javax.swing.JPanel();
        pnlActions = new javax.swing.JPanel();
        lblMinimize = new javax.swing.JLabel();
        lblMaximize = new javax.swing.JLabel();
        lblClose = new javax.swing.JLabel();
        pnlTitle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        pnlParent = new javax.swing.JPanel();
        sidepane = new javax.swing.JPanel();
        btn_typo = new javax.swing.JPanel();
        ind_typo = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btn_produto = new javax.swing.JPanel();
        ind_data1 = new javax.swing.JPanel();
        jLabel7Cadastro = new javax.swing.JLabel();
        btn_pdv = new javax.swing.JPanel();
        ind_data = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel7PRODUTOS = new javax.swing.JLabel();
        jLabel7PDV = new javax.swing.JLabel();
        btn_btns = new javax.swing.JPanel();
        ind_btns = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        NomeAtendenteCaixa = new javax.swing.JLabel();
        pnlRight = new javax.swing.JPanel();
        Display1 = new javax.swing.JPanel();
        panel1 = new java.awt.Panel();
        pnlHistorico = new javax.swing.JPanel();
        scroll = new javax.swing.JScrollPane();
        tabelaProdutos = new javax.swing.JTable();
        btnAdicionar = new javax.swing.JButton();
        bntAddContidade = new javax.swing.JButton();
        txtCodBarras = new javax.swing.JTextField();
        btnExcluir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        background2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listSuggestions = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        pnlPagento = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JLabel();
        txtNome = new javax.swing.JLabel();
        txtLimite = new javax.swing.JLabel();
        txtDiaFechamento = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        totalLabel = new javax.swing.JLabel();
        btnFinalizarVenda = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        txtDesconto = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        cboPagamento = new javax.swing.JComboBox<>();
        pnlTroco = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        txtDinheiro = new javax.swing.JFormattedTextField();
        txtTroco = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        btnMetadoPagamento = new javax.swing.JButton();
        Display3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jbAddProduto = new javax.swing.JButton();
        jbAddProduto1 = new javax.swing.JButton();
        jbAddProduto2 = new javax.swing.JButton();
        jbAddProduto3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlistCadastros = new javax.swing.JList<>();
        Display2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        lbl_instals1 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        DisplayIncial = new javax.swing.JPanel();
        background1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Painel do gestor");
        setLocationByPlatform(true);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1280, 760));

        pnlTop.setBackground(new java.awt.Color(255, 51, 51));
        pnlTop.setPreferredSize(new java.awt.Dimension(1024, 30));
        pnlTop.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlTopMouseDragged(evt);
            }
        });
        pnlTop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlTopMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlTopMousePressed(evt);
            }
        });
        pnlTop.setLayout(new java.awt.BorderLayout());

        pnlActions.setBackground(new java.awt.Color(255, 51, 0));
        pnlActions.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        lblMinimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/files/imgs/iconminimizar.png"))); // NOI18N
        lblMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblMinimizeMousePressed(evt);
            }
        });
        pnlActions.add(lblMinimize);

        lblMaximize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/files/imgs/iconmaximizar.png"))); // NOI18N
        lblMaximize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblMaximizeMousePressed(evt);
            }
        });
        pnlActions.add(lblMaximize);

        lblClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/files/imgs/iconfechar.png"))); // NOI18N
        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblCloseMousePressed(evt);
            }
        });
        pnlActions.add(lblClose);

        pnlTop.add(pnlActions, java.awt.BorderLayout.LINE_END);

        pnlTitle.setBackground(new java.awt.Color(255, 51, 0));
        pnlTitle.setPreferredSize(new java.awt.Dimension(200, 30));
        pnlTitle.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 8));

        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("Painel do gestor");
        lblTitle.setToolTipText("");
        pnlTitle.add(lblTitle);

        pnlTop.add(pnlTitle, java.awt.BorderLayout.LINE_START);

        getContentPane().add(pnlTop, java.awt.BorderLayout.PAGE_START);

        pnlParent.setLayout(new java.awt.BorderLayout());

        sidepane.setBackground(new java.awt.Color(255, 51, 0));
        sidepane.setForeground(new java.awt.Color(51, 51, 51));
        sidepane.setPreferredSize(new java.awt.Dimension(250, 200));
        sidepane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                sidepaneMouseDragged(evt);
            }
        });
        sidepane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                sidepaneMousePressed(evt);
            }
        });

        btn_typo.setBackground(new java.awt.Color(255, 102, 0));
        btn_typo.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btn_typoMouseMoved(evt);
            }
        });
        btn_typo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_typoMousePressed(evt);
            }
        });
        btn_typo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ind_typo.setOpaque(false);
        ind_typo.setPreferredSize(new java.awt.Dimension(4, 40));

        javax.swing.GroupLayout ind_typoLayout = new javax.swing.GroupLayout(ind_typo);
        ind_typo.setLayout(ind_typoLayout);
        ind_typoLayout.setHorizontalGroup(
            ind_typoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );
        ind_typoLayout.setVerticalGroup(
            ind_typoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        btn_typo.add(ind_typo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 40));

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/files/vr-cores/cliente64.png"))); // NOI18N
        jLabel12.setText("CLIENTES");
        btn_typo.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 160, 70));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("CoreMasterMarket/gui/Bundle"); // NOI18N
        jLabel1.setText(bundle.getString("App.jLabel1.text")); // NOI18N

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Teste Prátrico VR-Software");

        btn_produto.setBackground(new java.awt.Color(255, 51, 0));
        btn_produto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_produtoMousePressed(evt);
            }
        });
        btn_produto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ind_data1.setOpaque(false);
        ind_data1.setPreferredSize(new java.awt.Dimension(4, 40));

        javax.swing.GroupLayout ind_data1Layout = new javax.swing.GroupLayout(ind_data1);
        ind_data1.setLayout(ind_data1Layout);
        ind_data1Layout.setHorizontalGroup(
            ind_data1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );
        ind_data1Layout.setVerticalGroup(
            ind_data1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        btn_produto.add(ind_data1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 40));

        jLabel7Cadastro.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel7Cadastro.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7Cadastro.setText("CADASTRO");

        btn_pdv.setBackground(new java.awt.Color(255, 51, 0));
        btn_pdv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_pdvMousePressed(evt);
            }
        });
        btn_pdv.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ind_data.setOpaque(false);
        ind_data.setPreferredSize(new java.awt.Dimension(4, 40));

        javax.swing.GroupLayout ind_dataLayout = new javax.swing.GroupLayout(ind_data);
        ind_data.setLayout(ind_dataLayout);
        ind_dataLayout.setHorizontalGroup(
            ind_dataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );
        ind_dataLayout.setVerticalGroup(
            ind_dataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        btn_pdv.add(ind_data, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 40));

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/files/vr-cores/pdv64f.png"))); // NOI18N
        jLabel10.setText("PDV");
        jLabel10.setMaximumSize(new java.awt.Dimension(512, 512));
        jLabel10.setMinimumSize(new java.awt.Dimension(512, 512));
        btn_pdv.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 140, 60));

        jLabel7PRODUTOS.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel7PRODUTOS.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7PRODUTOS.setText("PRODUTOS");

        jLabel7PDV.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel7PDV.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7PDV.setText("PDV");

        btn_btns.setBackground(new java.awt.Color(255, 51, 0));
        btn_btns.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_btnsMousePressed(evt);
            }
        });
        btn_btns.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ind_btns.setOpaque(false);
        ind_btns.setPreferredSize(new java.awt.Dimension(4, 40));

        javax.swing.GroupLayout ind_btnsLayout = new javax.swing.GroupLayout(ind_btns);
        ind_btns.setLayout(ind_btnsLayout);
        ind_btnsLayout.setHorizontalGroup(
            ind_btnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );
        ind_btnsLayout.setVerticalGroup(
            ind_btnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        btn_btns.add(ind_btns, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 40));

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/files/vr-cores/produtos64.png"))); // NOI18N
        jLabel14.setText("PRODUTOS");
        jLabel14.setMaximumSize(new java.awt.Dimension(512, 512));
        jLabel14.setMinimumSize(new java.awt.Dimension(512, 512));
        btn_btns.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 80));

        NomeAtendenteCaixa.setText(bundle.getString("App.NomeAtendenteCaixa.text")); // NOI18N

        javax.swing.GroupLayout sidepaneLayout = new javax.swing.GroupLayout(sidepane);
        sidepane.setLayout(sidepaneLayout);
        sidepaneLayout.setHorizontalGroup(
            sidepaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidepaneLayout.createSequentialGroup()
                .addGroup(sidepaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_typo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidepaneLayout.createSequentialGroup()
                        .addComponent(btn_produto, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_btns, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(sidepaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(sidepaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_pdv, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(sidepaneLayout.createSequentialGroup()
                                .addGroup(sidepaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7PDV)
                                    .addComponent(jLabel7Cadastro)
                                    .addComponent(jLabel7PRODUTOS))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidepaneLayout.createSequentialGroup()
                                .addGroup(sidepaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(sidepaneLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(sidepaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(NomeAtendenteCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2))))
                                .addGap(23, 23, 23)))))
                .addContainerGap())
        );
        sidepaneLayout.setVerticalGroup(
            sidepaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidepaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NomeAtendenteCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel7Cadastro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_typo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7PRODUTOS)
                .addGroup(sidepaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sidepaneLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(btn_produto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(sidepaneLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_btns, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7PDV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_pdv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(2022, Short.MAX_VALUE))
        );

        pnlParent.add(sidepane, java.awt.BorderLayout.LINE_START);

        pnlRight.setLayout(new java.awt.CardLayout());

        Display1.setForeground(new java.awt.Color(255, 255, 255));
        Display1.setToolTipText("");
        Display1.setVerifyInputWhenFocusTarget(false);

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        pnlHistorico.setBackground(new java.awt.Color(255, 255, 204));
        pnlHistorico.setForeground(new java.awt.Color(255, 255, 255));

        scroll.setBackground(new java.awt.Color(255, 204, 0));
        scroll.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        tabelaProdutos.setBackground(new java.awt.Color(255, 204, 0));
        tabelaProdutos.setForeground(new java.awt.Color(51, 51, 51));
        tabelaProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabelaProdutos.getTableHeader().setReorderingAllowed(false);
        scroll.setViewportView(tabelaProdutos);

        btnAdicionar.setBackground(new java.awt.Color(255, 255, 255));
        btnAdicionar.setText(bundle.getString("App.btnAdicionar.text")); // NOI18N
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        bntAddContidade.setBackground(new java.awt.Color(255, 255, 255));
        bntAddContidade.setText(bundle.getString("App.bntAddContidade.text")); // NOI18N
        bntAddContidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntAddContidadeActionPerformed(evt);
            }
        });

        txtCodBarras.setBackground(new java.awt.Color(255, 255, 255));
        txtCodBarras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBarrasActionPerformed(evt);
            }
        });
        txtCodBarras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodBarrasKeyPressed(evt);
            }
        });

        btnExcluir.setBackground(new java.awt.Color(255, 255, 255));
        btnExcluir.setText(bundle.getString("App.btnExcluir.text")); // NOI18N
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        jPanel1.setFocusable(false);
        jPanel1.setVerifyInputWhenFocusTarget(false);

        background2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        background2.setAlignmentY(0.0F);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(background2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(507, 507, 507))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(background2)
                .addContainerGap())
        );

        jScrollPane2.setViewportView(listSuggestions);

        jButton1.setText(bundle.getString("App.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlHistoricoLayout = new javax.swing.GroupLayout(pnlHistorico);
        pnlHistorico.setLayout(pnlHistoricoLayout);
        pnlHistoricoLayout.setHorizontalGroup(
            pnlHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHistoricoLayout.createSequentialGroup()
                .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(pnlHistoricoLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(pnlHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                    .addComponent(txtCodBarras))
                .addGroup(pnlHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHistoricoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bntAddContidade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluir))
                    .addGroup(pnlHistoricoLayout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlHistoricoLayout.setVerticalGroup(
            pnlHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHistoricoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntAddContidade, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHistoricoLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHistoricoLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(25, 25, 25)))
                .addGroup(pnlHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 764, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pnlPagento.setBackground(new java.awt.Color(255, 255, 255));
        pnlPagento.setForeground(new java.awt.Color(204, 204, 204));
        pnlPagento.setAutoscrolls(true);

        jLabel7.setBackground(java.awt.Color.white);
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText(bundle.getString("App.jLabel7.text")); // NOI18N

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText(bundle.getString("App.jLabel8.text")); // NOI18N

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText(bundle.getString("App.jLabel9.text")); // NOI18N

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText(bundle.getString("App.jLabel11.text")); // NOI18N

        jLabel28.setBackground(new java.awt.Color(255, 255, 255));
        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(51, 51, 51));
        jLabel28.setText(bundle.getString("App.jLabel28.text")); // NOI18N

        txtIdCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtIdCliente.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIdCliente.setForeground(new java.awt.Color(51, 51, 51));
        txtIdCliente.setText(bundle.getString("App.txtIdCliente.text")); // NOI18N

        txtNome.setBackground(java.awt.Color.white);
        txtNome.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtNome.setForeground(new java.awt.Color(51, 51, 51));
        txtNome.setText(bundle.getString("App.txtNome.text")); // NOI18N

        txtLimite.setBackground(java.awt.Color.white);
        txtLimite.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtLimite.setForeground(new java.awt.Color(51, 51, 51));
        txtLimite.setText(bundle.getString("App.txtLimite.text")); // NOI18N

        txtDiaFechamento.setBackground(java.awt.Color.white);
        txtDiaFechamento.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDiaFechamento.setForeground(new java.awt.Color(51, 51, 51));
        txtDiaFechamento.setText(bundle.getString("App.txtDiaFechamento.text")); // NOI18N

        jSeparator7.setBackground(java.awt.Color.white);

        totalLabel.setBackground(java.awt.Color.white);
        totalLabel.setFont(new java.awt.Font("Segoe UI", 1, 50)); // NOI18N
        totalLabel.setForeground(new java.awt.Color(51, 51, 51));
        totalLabel.setText(bundle.getString("App.totalLabel.text")); // NOI18N

        btnFinalizarVenda.setBackground(new java.awt.Color(0, 204, 51));
        btnFinalizarVenda.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnFinalizarVenda.setForeground(new java.awt.Color(255, 255, 255));
        btnFinalizarVenda.setText(bundle.getString("App.btnFinalizarVenda.text")); // NOI18N
        btnFinalizarVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarVendaActionPerformed(evt);
            }
        });

        jLabel29.setBackground(java.awt.Color.white);
        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(51, 51, 51));
        jLabel29.setText(bundle.getString("App.jLabel29.text")); // NOI18N

        txtDesconto.setBackground(java.awt.Color.white);
        txtDesconto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtDesconto.setForeground(new java.awt.Color(51, 51, 51));
        txtDesconto.setText(bundle.getString("App.txtDesconto.text")); // NOI18N

        jLabel30.setBackground(java.awt.Color.white);
        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(51, 51, 51));
        jLabel30.setText(bundle.getString("App.jLabel30.text")); // NOI18N

        txtSubTotal.setBackground(java.awt.Color.white);
        txtSubTotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSubTotal.setForeground(new java.awt.Color(51, 51, 51));
        txtSubTotal.setText(bundle.getString("App.txtSubTotal.text")); // NOI18N

        jSeparator8.setBackground(java.awt.Color.white);

        jLabel31.setBackground(java.awt.Color.white);
        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(51, 51, 51));
        jLabel31.setText(bundle.getString("App.jLabel31.text")); // NOI18N

        jLabel32.setBackground(java.awt.Color.white);
        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(51, 51, 51));
        jLabel32.setText(bundle.getString("App.jLabel32.text")); // NOI18N

        cboPagamento.setBackground(java.awt.Color.white);
        cboPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE", "CARTAO CREDITO", "CARTAO DEBITO", "PIX", "DINHEIRO" }));
        cboPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPagamentoActionPerformed(evt);
            }
        });

        pnlTroco.setBackground(new java.awt.Color(255, 255, 255));

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel33.setText(bundle.getString("App.jLabel33.text")); // NOI18N

        txtDinheiro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDinheiroKeyPressed(evt);
            }
        });

        txtTroco.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTroco.setText(bundle.getString("App.txtTroco.text")); // NOI18N

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel34.setText(bundle.getString("App.jLabel34.text")); // NOI18N

        javax.swing.GroupLayout pnlTrocoLayout = new javax.swing.GroupLayout(pnlTroco);
        pnlTroco.setLayout(pnlTrocoLayout);
        pnlTrocoLayout.setHorizontalGroup(
            pnlTrocoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTrocoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTrocoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTrocoLayout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTroco))
                    .addGroup(pnlTrocoLayout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDinheiro, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlTrocoLayout.setVerticalGroup(
            pnlTrocoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTrocoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTrocoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDinheiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTrocoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(txtTroco))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnMetadoPagamento.setBackground(new java.awt.Color(0, 102, 255));
        btnMetadoPagamento.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        btnMetadoPagamento.setForeground(new java.awt.Color(255, 255, 255));
        btnMetadoPagamento.setText(bundle.getString("App.btnMetadoPagamento.text")); // NOI18N
        btnMetadoPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMetadoPagamentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPagentoLayout = new javax.swing.GroupLayout(pnlPagento);
        pnlPagento.setLayout(pnlPagentoLayout);
        pnlPagentoLayout.setHorizontalGroup(
            pnlPagentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPagentoLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(pnlPagentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPagentoLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlPagentoLayout.createSequentialGroup()
                        .addComponent(pnlTroco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPagentoLayout.createSequentialGroup()
                        .addGroup(pnlPagentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlPagentoLayout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cboPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnFinalizarVenda, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlPagentoLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(pnlPagentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(pnlPagentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtIdCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtLimite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtDiaFechamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pnlPagentoLayout.createSequentialGroup()
                                .addComponent(btnMetadoPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(pnlPagentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlPagentoLayout.createSequentialGroup()
                                        .addComponent(jLabel32)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                                        .addComponent(totalLabel))
                                    .addGroup(pnlPagentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(pnlPagentoLayout.createSequentialGroup()
                                            .addComponent(jLabel29)
                                            .addGap(198, 198, 198)
                                            .addComponent(txtDesconto))
                                        .addGroup(pnlPagentoLayout.createSequentialGroup()
                                            .addComponent(jLabel30)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtSubTotal))))))
                        .addGap(12, 12, 12))))
        );
        pnlPagentoLayout.setVerticalGroup(
            pnlPagentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPagentoLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPagentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtIdCliente))
                .addGap(5, 5, 5)
                .addGroup(pnlPagentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtNome))
                .addGap(5, 5, 5)
                .addGroup(pnlPagentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtLimite))
                .addGap(5, 5, 5)
                .addGroup(pnlPagentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtDiaFechamento))
                .addGap(10, 10, 10)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPagentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(cboPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlTroco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPagentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPagentoLayout.createSequentialGroup()
                        .addGroup(pnlPagentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(txtSubTotal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlPagentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(txtDesconto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                        .addGroup(pnlPagentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32))
                        .addGap(20, 20, 20))
                    .addGroup(pnlPagentoLayout.createSequentialGroup()
                        .addComponent(btnMetadoPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(btnFinalizarVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlPagento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(152, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlPagento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1824, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Display1Layout = new javax.swing.GroupLayout(Display1);
        Display1.setLayout(Display1Layout);
        Display1Layout.setHorizontalGroup(
            Display1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Display1Layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1025, Short.MAX_VALUE))
        );
        Display1Layout.setVerticalGroup(
            Display1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Display1Layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnlRight.add(Display1, "card1");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Cadastro Clientes");

        jLabel13.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Abaixo você pode cadastrar novos clientes:");

        jbAddProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/files/imgs/adicionar.png"))); // NOI18N
        jbAddProduto.setText("ADICIONAR");
        jbAddProduto.setAutoscrolls(true);
        jbAddProduto.setMaximumSize(new java.awt.Dimension(64, 64));
        jbAddProduto.setMinimumSize(new java.awt.Dimension(64, 64));
        jbAddProduto.setPreferredSize(new java.awt.Dimension(64, 64));
        jbAddProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddProdutoActionPerformed(evt);
            }
        });

        jbAddProduto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/files/imgs/excluir.png"))); // NOI18N
        jbAddProduto1.setText("EXCLUIR");
        jbAddProduto1.setAutoscrolls(true);
        jbAddProduto1.setMaximumSize(new java.awt.Dimension(64, 64));
        jbAddProduto1.setMinimumSize(new java.awt.Dimension(64, 64));
        jbAddProduto1.setPreferredSize(new java.awt.Dimension(64, 64));
        jbAddProduto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddProduto1ActionPerformed(evt);
            }
        });

        jbAddProduto2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/files/imgs/salvar.png"))); // NOI18N
        jbAddProduto2.setText("SALVAR");
        jbAddProduto2.setAutoscrolls(true);
        jbAddProduto2.setMaximumSize(new java.awt.Dimension(64, 64));
        jbAddProduto2.setMinimumSize(new java.awt.Dimension(64, 64));
        jbAddProduto2.setPreferredSize(new java.awt.Dimension(64, 64));
        jbAddProduto2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddProduto2ActionPerformed(evt);
            }
        });

        jbAddProduto3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/files/imgs/ajuda.png"))); // NOI18N
        jbAddProduto3.setText("EDITAR");
        jbAddProduto3.setAutoscrolls(true);
        jbAddProduto3.setMaximumSize(new java.awt.Dimension(64, 64));
        jbAddProduto3.setMinimumSize(new java.awt.Dimension(64, 64));
        jbAddProduto3.setPreferredSize(new java.awt.Dimension(64, 64));
        jbAddProduto3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddProduto3ActionPerformed(evt);
            }
        });

        jlistCadastros.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jlistCadastros);

        javax.swing.GroupLayout Display3Layout = new javax.swing.GroupLayout(Display3);
        Display3.setLayout(Display3Layout);
        Display3Layout.setHorizontalGroup(
            Display3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Display3Layout.createSequentialGroup()
                .addGap(209, 209, 209)
                .addGroup(Display3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addGroup(Display3Layout.createSequentialGroup()
                        .addGroup(Display3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jbAddProduto1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(jbAddProduto2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbAddProduto3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbAddProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 909, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(962, Short.MAX_VALUE))
        );
        Display3Layout.setVerticalGroup(
            Display3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Display3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addGroup(Display3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Display3Layout.createSequentialGroup()
                        .addComponent(jbAddProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbAddProduto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jbAddProduto2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbAddProduto3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 2014, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pnlRight.add(Display3, "card3");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("PRODUTOS CADASTRADOS");

        jLabel21.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Abaixo você pode cadastrar novos clientes:");

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lbl_instals1.setFont(new java.awt.Font("Century Gothic", 1, 48)); // NOI18N
        lbl_instals1.setForeground(new java.awt.Color(255, 255, 255));
        lbl_instals1.setText("10");

        jLabel22.setFont(new java.awt.Font("Century Gothic", 1, 48)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("13");

        jLabel23.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Nome:");

        jLabel24.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(204, 204, 204));
        jLabel24.setText("FIM");

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("V0.1");

        jLabel25.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Documento:");

        jLabel26.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Endereço");

        jLabel27.setFont(new java.awt.Font("Century Gothic", 1, 48)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("189");

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout Display2Layout = new javax.swing.GroupLayout(Display2);
        Display2.setLayout(Display2Layout);
        Display2Layout.setHorizontalGroup(
            Display2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Display2Layout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addGroup(Display2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel5)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(Display2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(Display2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel24)
                            .addGroup(Display2Layout.createSequentialGroup()
                                .addGroup(Display2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_instals1)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(Display2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel25))
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(Display2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel27)
                                    .addComponent(jLabel26))))))
                .addGap(45, 45, 45))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Display2Layout.createSequentialGroup()
                .addContainerGap(2214, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addContainerGap())
        );
        Display2Layout.setVerticalGroup(
            Display2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Display2Layout.createSequentialGroup()
                .addGroup(Display2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Display2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel24))
                    .addGroup(Display2Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21)
                        .addGap(28, 28, 28)
                        .addGroup(Display2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Display2Layout.createSequentialGroup()
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(Display2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(Display2Layout.createSequentialGroup()
                                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel22))
                                    .addGroup(Display2Layout.createSequentialGroup()
                                        .addComponent(jLabel23)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lbl_instals1))
                                    .addGroup(Display2Layout.createSequentialGroup()
                                        .addComponent(jLabel26)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel27))))
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2095, Short.MAX_VALUE)
                        .addComponent(jLabel6)))
                .addContainerGap())
        );

        pnlRight.add(Display2, "card2");

        DisplayIncial.setPreferredSize(new java.awt.Dimension(1072, 773));
        DisplayIncial.setVerifyInputWhenFocusTarget(false);

        background1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/files/vr-cores/fundoPDVProduto.png"))); // NOI18N
        background1.setAlignmentY(0.0F);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/files/vr-cores/background_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, 1078, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, 746, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1060, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout DisplayIncialLayout = new javax.swing.GroupLayout(DisplayIncial);
        DisplayIncial.setLayout(DisplayIncialLayout);
        DisplayIncialLayout.setHorizontalGroup(
            DisplayIncialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DisplayIncialLayout.createSequentialGroup()
                .addGap(602, 602, 602)
                .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, 1038, Short.MAX_VALUE)
                .addGap(602, 602, 602))
            .addGroup(DisplayIncialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(DisplayIncialLayout.createSequentialGroup()
                    .addGap(554, 554, 554)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(554, Short.MAX_VALUE)))
        );
        DisplayIncialLayout.setVerticalGroup(
            DisplayIncialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DisplayIncialLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(background1)
                .addContainerGap(1694, Short.MAX_VALUE))
            .addGroup(DisplayIncialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(DisplayIncialLayout.createSequentialGroup()
                    .addGap(315, 315, 315)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(316, Short.MAX_VALUE)))
        );

        pnlRight.add(DisplayIncial, "card0");

        pnlParent.add(pnlRight, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlParent, java.awt.BorderLayout.CENTER);

        getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    int xy, xx;

    private void lblMaximizeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMaximizeMousePressed
        if (App.this.getExtendedState() == MAXIMIZED_BOTH) {
            App.this.setExtendedState(JFrame.NORMAL);
        } else {
            App.this.setExtendedState(MAXIMIZED_BOTH);
        }
    }//GEN-LAST:event_lblMaximizeMousePressed

    private void lblCloseMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMousePressed
        System.exit(0);
    }//GEN-LAST:event_lblCloseMousePressed

    private void lblMinimizeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizeMousePressed
        App.this.setState(Frame.ICONIFIED);
    }//GEN-LAST:event_lblMinimizeMousePressed

    private void pnlTopMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTopMousePressed
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_pnlTopMousePressed

    private void pnlTopMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTopMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_pnlTopMouseDragged

    private void pnlTopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTopMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            if (App.this.getExtendedState() == MAXIMIZED_BOTH) {
                App.this.setExtendedState(JFrame.NORMAL);
            } else {
                App.this.setExtendedState(MAXIMIZED_BOTH);
            }
        }
    }//GEN-LAST:event_pnlTopMouseClicked

    private void sidepaneMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sidepaneMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_sidepaneMousePressed

    private void sidepaneMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sidepaneMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_sidepaneMouseDragged

    private void btn_pdvMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_pdvMousePressed
        // TODO add your handling code here:
        setColor(btn_pdv);
        resetColor(btn_typo);
        resetColor(btn_btns);
//        resetColor(btn_fonts);
//        resetColor(btn_icons);

        ind_typo.setOpaque(false);
        ind_btns.setOpaque(false);
        ind_data.setOpaque(true);
//        ind_fonts.setOpaque(false);
//        ind_icons.setOpaque(false);

        cardLayout.show(pnlRight, "card1");
    }//GEN-LAST:event_btn_pdvMousePressed

    private void btn_btnsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_btnsMousePressed
        // TODO add your handling code here:
        setColor(btn_btns);
        resetColor(btn_pdv);
        resetColor(btn_typo);
//        resetColor(btn_fonts);
//        resetColor(btn_icons);

        ind_typo.setOpaque(false);
        ind_btns.setOpaque(true);
        ind_data.setOpaque(false);
//        ind_fonts.setOpaque(false);
//        ind_icons.setOpaque(false);
        cardLayout.show(pnlRight, "card2");
    }//GEN-LAST:event_btn_btnsMousePressed

    private void btn_produtoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_produtoMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_produtoMousePressed

    private void btn_typoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_typoMousePressed
        // TODO add your handling code here:

        setColor(btn_typo);
        resetColor(btn_pdv);
        resetColor(btn_btns);
//        resetColor(btn_fonts);
//        resetColor(btn_icons);
        ind_typo.setOpaque(true);
        ind_btns.setOpaque(false);
        ind_data.setOpaque(false);
//        ind_fonts.setOpaque(false);
//        ind_icons.setOpaque(false);

        cardLayout.show(pnlRight, "card3");
    }//GEN-LAST:event_btn_typoMousePressed

    private void btn_typoMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_typoMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_typoMouseMoved

    private void jbAddProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddProdutoActionPerformed

    }//GEN-LAST:event_jbAddProdutoActionPerformed

    private void jbAddProduto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddProduto1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbAddProduto1ActionPerformed

    private void jbAddProduto2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddProduto2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbAddProduto2ActionPerformed

    private void jbAddProduto3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddProduto3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbAddProduto3ActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        pdvService.adicionarProdutoSelecionado();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void bntAddContidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntAddContidadeActionPerformed
        pdvService.adicionarQuantidadeProduto();
    }//GEN-LAST:event_bntAddContidadeActionPerformed

    private void txtCodBarrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBarrasActionPerformed

    }//GEN-LAST:event_txtCodBarrasActionPerformed

    private void txtCodBarrasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodBarrasKeyPressed

    }//GEN-LAST:event_txtCodBarrasKeyPressed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        pdvService.excluirProduto();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnFinalizarVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarVendaActionPerformed
        pdvService.finalizarVenda();        //        pdvService.finalizarVenda();
    }//GEN-LAST:event_btnFinalizarVendaActionPerformed

    private void cboPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPagamentoActionPerformed

    }//GEN-LAST:event_cboPagamentoActionPerformed

    private void txtDinheiroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDinheiroKeyPressed

    }//GEN-LAST:event_txtDinheiroKeyPressed

    private void btnMetadoPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMetadoPagamentoActionPerformed
        pdvService.selecionarMetodoPagamento();
    }//GEN-LAST:event_btnMetadoPagamentoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        pdvService.carregarProdutos();           // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    // set and reset color
    void setColor(JPanel panel) {
        panel.setBackground(new Color(255, 160, 0));
    }

    void resetColor(JPanel panel) {
        panel.setBackground(new Color(255, 102, 0));
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
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new App().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel Display1;
    public javax.swing.JPanel Display2;
    public javax.swing.JPanel Display3;
    public javax.swing.JPanel DisplayIncial;
    private javax.swing.JLabel NomeAtendenteCaixa;
    private javax.swing.JLabel background;
    private javax.swing.JLabel background1;
    private javax.swing.JLabel background2;
    private javax.swing.JButton bntAddContidade;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnFinalizarVenda;
    private javax.swing.JButton btnMetadoPagamento;
    private javax.swing.JPanel btn_btns;
    private javax.swing.JPanel btn_pdv;
    private javax.swing.JPanel btn_produto;
    private javax.swing.JPanel btn_typo;
    private javax.swing.JComboBox<String> cboPagamento;
    private javax.swing.JPanel ind_btns;
    private javax.swing.JPanel ind_data;
    private javax.swing.JPanel ind_data1;
    private javax.swing.JPanel ind_typo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel7Cadastro;
    private javax.swing.JLabel jLabel7PDV;
    private javax.swing.JLabel jLabel7PRODUTOS;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JButton jbAddProduto;
    private javax.swing.JButton jbAddProduto1;
    private javax.swing.JButton jbAddProduto2;
    private javax.swing.JButton jbAddProduto3;
    private javax.swing.JList<String> jlistCadastros;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblMaximize;
    private javax.swing.JLabel lblMinimize;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lbl_instals1;
    public javax.swing.JList<String> listSuggestions;
    private java.awt.Panel panel1;
    private javax.swing.JPanel pnlActions;
    private javax.swing.JPanel pnlHistorico;
    private javax.swing.JPanel pnlPagento;
    private javax.swing.JPanel pnlParent;
    private javax.swing.JPanel pnlRight;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JPanel pnlTroco;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JPanel sidepane;
    public javax.swing.JTable tabelaProdutos;
    public javax.swing.JLabel totalLabel;
    public javax.swing.JTextField txtCodBarras;
    private javax.swing.JLabel txtDesconto;
    private javax.swing.JLabel txtDiaFechamento;
    private javax.swing.JFormattedTextField txtDinheiro;
    private javax.swing.JLabel txtIdCliente;
    private javax.swing.JLabel txtLimite;
    private javax.swing.JLabel txtNome;
    private javax.swing.JLabel txtSubTotal;
    private javax.swing.JLabel txtTroco;
    // End of variables declaration//GEN-END:variables

}
