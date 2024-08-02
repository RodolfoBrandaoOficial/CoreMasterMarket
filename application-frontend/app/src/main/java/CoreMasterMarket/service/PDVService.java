package CoreMasterMarket.service;

import javax.swing.*;
import java.awt.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import CoreMasterMarket.gui.pdv.CupomFiscal;
import static CoreMasterMarket.config.ConfigReal.GlobalToken;
import static CoreMasterMarket.config.ConfigReal.userName;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import com.google.gson.JsonObject;

public class PDVService extends javax.swing.JInternalFrame {

    private JTextField txtCodBarras;
    private JTable tabelaProdutos;
    private DefaultTableModel tableModel;
    private List<JsonObject> produtos;
    private JLabel totalLabel;
    private double totalValue = 0.0;
    private long clienteId = -1;
    private JList<String> listSuggestions;
    private DefaultListModel<String> listModel;
    private int metodoPagamentoSelecionado = -1;

    public PDVService() {
        super("PDV - MERCADO CORE MASTER MARKT = ATENDIMENTO FEITO POR " + userName, true, true, true, true);
        setSize(1030, 720);
        setLayout(new BorderLayout());

        produtos = new ArrayList<>();

        // Solicita o código do cliente
        String clienteInput = JOptionPane.showInputDialog("Digite o código do cliente:");
        try {
            clienteId = Long.parseLong(clienteInput);
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
        tabelaProdutos.setFillsViewportHeight(true);

        // Inicializa o JLabel para mostrar o valor total com destaque
        totalLabel = new JLabel("Total: R$ 0.0");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 24));
        totalLabel.setForeground(Color.RED);

        // Inicializa o JList para sugestões
        listModel = new DefaultListModel<>();
        listSuggestions = new JList<>(listModel);
        listSuggestions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSuggestions.setVisibleRowCount(5);
        listSuggestions.setBorder(BorderFactory.createTitledBorder("Sugestões"));

        // Botões com ícones e ações
        JButton btnAdicionar = createButton("F8 Adicionar", "add_icon.png", e -> adicionarProdutoSelecionado(), KeyEvent.VK_F8);
        JButton btnAdicionarQuantidade = createButton("F9 Adicionar Quantidade", "quantity_icon.png", e -> adicionarQuantidadeProduto(), KeyEvent.VK_F9);
        JButton btnMetodoPagamento = createButton("F10 Método de Pagamento", "payment_icon.png", e -> selecionarMetodoPagamento(), KeyEvent.VK_F10);
        JButton btnExcluir = createButton("F11 Excluir Produto", "delete_icon.png", e -> excluirProduto(), KeyEvent.VK_F11);
        JButton btnFinalizarVenda = createButton("F12 Finalizar Venda", "finish_icon.png", e -> finalizarVenda(), KeyEvent.VK_F12);

        // Layout da interface
        JPanel inputPanel = new JPanel(new GridLayout(1, 6, 10, 10));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.add(txtCodBarras);
        inputPanel.add(btnAdicionar);
        inputPanel.add(btnAdicionarQuantidade);
        inputPanel.add(btnMetodoPagamento);
        inputPanel.add(btnExcluir);
        inputPanel.add(btnFinalizarVenda);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(tabelaProdutos), BorderLayout.CENTER);
        centerPanel.add(new JScrollPane(listSuggestions), BorderLayout.EAST);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        bottomPanel.add(totalLabel);

        add(inputPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Carrega os produtos
        carregarProdutos();

        // Adiciona um KeyListener para buscar enquanto digita
        txtCodBarras.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    adicionarProdutoSelecionado();
                } else if (e.getKeyCode() == KeyEvent.VK_F7) {
                    adicionarQuantidadeProduto();
                } else if (e.getKeyCode() == KeyEvent.VK_F8) {
                    excluirProduto();
                } else {
                    buscarProduto(txtCodBarras.getText());
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

        // Configura o comportamento do fechamento do JInternalFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private JButton createButton(String text, String iconName, ActionListener actionListener, int keyCode) {
        JButton button = new JButton(text);
        try {
            URL iconUrl = getClass().getResource("/icons/" + iconName);
            if (iconUrl != null) {
                button.setIcon(new ImageIcon(iconUrl));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        button.addActionListener(actionListener);
        button.setPreferredSize(new Dimension(150, 40));
        setKeyAction(button, keyCode);
        return button;
    }

    private void setKeyAction(JButton button, int keyCode) {
        // Cria uma ação para o botão
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.doClick(); // Simula o clique no botão
            }
        };

        // Configura o mapa de ações e a tecla de atalho
        InputMap inputMap = button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(keyCode, 0), button.getActionCommand());
        button.getActionMap().put(button.getActionCommand(), action);
    }


    private JButton createButton(String text, String iconName, java.awt.event.ActionListener actionListener) {
        JButton button = new JButton(text);
        try {
            URL iconUrl = getClass().getResource("/icons/" + iconName);
            if (iconUrl != null) {
                button.setIcon(new ImageIcon(iconUrl));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        button.addActionListener(actionListener);
        button.setPreferredSize(new Dimension(150, 40));
        return button;
    }

    private JButton createIconButton(String text, String iconPath, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setIcon(new ImageIcon(getClass().getResource(iconPath)));
        button.addActionListener(actionListener);
        button.setMargin(new Insets(2, 2, 2, 2));
        button.setPreferredSize(new Dimension(160, 40));
        return button;
    }
public void carregarProdutos() {
        try {
            URL url = new URL("http://localhost:8081/api/v1/produtos/list");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + GlobalToken);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            String jsonInputString = "{\"page\":1,\"size\":10000000,\"sortname\":\"id\",\"sortorder\":\"asc\"}";
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
            }

            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonArray content = jsonResponse.getAsJsonArray("content");

            produtos.clear();
            for (int i = 0; i < content.size(); i++) {
                JsonObject produto = content.get(i).getAsJsonObject();
                produtos.add(produto);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar produtos: " + e.getMessage());
        }
    }

    public void buscarProduto(String text) {
        listModel.clear();
        for (JsonObject produto : produtos) {
            String codigoBarras = produto.get("codigoBarras").getAsString();
            String descricao = produto.get("descricao").getAsString();
            String id = produto.get("id").getAsString();
            if (codigoBarras.contains(text) || descricao.toLowerCase().contains(text.toLowerCase()) || id.contains(text)) {
                listModel.addElement(codigoBarras + " - " + descricao);
            }
        }
    }

    public void adicionarProdutoSelecionado() {
        String text = txtCodBarras.getText();
        for (JsonObject produto : produtos) {
            String codigoBarras = produto.get("codigoBarras").getAsString();
            if (text.startsWith(codigoBarras)) {
                adicionarProdutoATabela(produto);
                txtCodBarras.setText("");
                listModel.clear();
                break;
            }
        }
    }

    public void adicionarQuantidadeProduto() {
        String text = txtCodBarras.getText();
        for (JsonObject produto : produtos) {
            String codigoBarras = produto.get("codigoBarras").getAsString();
            if (text.startsWith(codigoBarras)) {
                String quantidadeStr = JOptionPane.showInputDialog("Quantidade:");
                try {
                    double quantidade = Double.parseDouble(quantidadeStr);
                    adicionarProdutoATabela(produto, quantidade);
                    txtCodBarras.setText("");
                    listModel.clear();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Quantidade inválida!");
                }
                break;
            }
        }
    }

    private void adicionarProdutoATabela(JsonObject produto) {
        adicionarProdutoATabela(produto, 1.0);
    }

    private void adicionarProdutoATabela(JsonObject produto, double quantidade) {
        String codigoBarras = produto.get("codigoBarras").getAsString();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(codigoBarras)) {
                double quantidadeAtual = (double) tableModel.getValueAt(i, 3);
                quantidadeAtual += quantidade;
                tableModel.setValueAt(quantidadeAtual, i, 3);
                atualizarTotal();
                return;
            }
        }

        Object[] linha = {
            produto.get("id").getAsInt(),
            produto.get("descricao").getAsString(),
            produto.get("precoVenda").getAsDouble(),
            quantidade
        };
        tableModel.addRow(linha);
        atualizarTotal();
    }

    private void atualizarTotal() {
        totalValue = 0.0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            double preco = (double) tableModel.getValueAt(i, 2);
            double quantidade = (double) tableModel.getValueAt(i, 3);
            totalValue += preco * quantidade;
        }
        totalLabel.setText(String.format("Total: R$ %.2f", totalValue));
    }

    public void excluirProduto() {
        int rowIndex = tabelaProdutos.getSelectedRow();
        if (rowIndex != -1) {
            String senhaFiscal = JOptionPane.showInputDialog("Digite a senha do Fiscal para excluir o produto:");
            // Aqui você pode verificar a senha fiscal com o banco de dados ou qualquer outro sistema de autenticação
            if (verificarSenhaFiscal(senhaFiscal)) {
                tableModel.removeRow(rowIndex);
                atualizarTotal();
            } else {
                JOptionPane.showMessageDialog(null, "Senha do Fiscal inválida!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um produto para excluir.");
        }
    }

    public void selecionarMetodoPagamento() {
        String[] opcoes = {"Dinheiro", "Débito", "Crédito", "Pix", "Crediário", "Cheque", "Voucher", "VR Software"};
        String metodoPagamento = (String) JOptionPane.showInputDialog(null, "Selecione o método de pagamento:",
                "Método de Pagamento", JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
        if (metodoPagamento != null) {
            metodoPagamentoSelecionado = switch (metodoPagamento) {
                case "Dinheiro" ->
                    1;
                case "Débito" ->
                    2;
                case "Crédito" ->
                    3;
                case "Pix" ->
                    4;
                case "Crediário" ->
                    5;
                case "Cheque" ->
                    6;
                case "Voucher" ->
                    7;
                case "VR Software" ->
                    8;
                default ->
                    -1;
            };
            if (metodoPagamentoSelecionado == 3) {
                exibirInformacoesCredito();
            } else if (metodoPagamentoSelecionado == 5) { // Atualizado para "Crediário"
                consultarLimiteCredito();
            } else if (metodoPagamentoSelecionado == 1) {
                pagarDinheiro();
            } else if (metodoPagamentoSelecionado == 3) {
                consultarLimiteDisponivel();
            }
        }
    }

    private void consultarLimiteCredito() {
        // Substitua o ID do cliente e o token de autenticação conforme necessário
        int clienteId = 1; // ID do cliente
        String url = "http://localhost:8081/api/v1/clientes/buscar/" + clienteId;
        String authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb2RvbGZvYnJhbmRhbyIsImlzcyI6ImF1dGgtYXBpIiwiZXhwIjoxNzIyNTM1ODI4LCJ1c2VySWQiOiJkNjY1NWUwZC1hOTIxLTQ2ZTMtYjM2Ny04NzZmMDA0NjdkYWQifQ.DHyMs5vIUaitJ0Hj5SMWnER9Ez9EFuP8Cn5uisK0N-g";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader("Authorization", "Bearer " + authToken);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    String jsonResponse = EntityUtils.toString(response.getEntity());
                    JSONObject jsonObject = new JSONObject(jsonResponse);

                    // Obtenha o limite de crédito
                    double limiteCredito = jsonObject.optDouble("limiteCredito", 0.0);
                    JOptionPane.showMessageDialog(null, "Limite de crédito disponível: " + limiteCredito);
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao consultar limite de crédito: " + statusCode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao consultar limite de crédito: " + e.getMessage());
        }
    }

    private void exibirInformacoesCredito() {
        // Pergunta ao usuário quantas parcelas ele deseja
        String parcelasStr = JOptionPane.showInputDialog("Número de parcelas (1 a 12):");
        int parcelas;
        try {
            parcelas = Integer.parseInt(parcelasStr);
            if (parcelas < 1 || parcelas > 12) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número de parcelas inválido! Deve ser entre 1 e 12.");
            return;
        }

        // Exemplo de informações, ajuste conforme necessário
        double limiteCredito = 1000.00; // Exemplo
        double taxaJuros = 2.5; // Exemplo

        double valorParcela = totalValue / parcelas;
        JOptionPane.showMessageDialog(null, "Informações de Crédito:\n"
                + "Limite disponível: R$ " + limiteCredito + "\n"
                + "Número de parcelas: " + parcelas + "\n"
                + "Taxa de juros: " + taxaJuros + "%\n"
                + "Valor da parcela: R$ " + String.format("%.2f", valorParcela));
    }

    private void consultarLimiteDisponivel() {
        try {
            URL url = new URL("http://localhost:8081/api/v1/clientes/buscar/" + clienteId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + GlobalToken);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }

                    JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                    double limiteCredito = 0.0;

                    if (jsonResponse.has("limiteCredito")) {
                        JsonElement limiteCreditoElement = jsonResponse.get("limiteCredito");
                        if (limiteCreditoElement != null && limiteCreditoElement.isJsonPrimitive() && limiteCreditoElement.getAsJsonPrimitive().isNumber()) {
                            limiteCredito = limiteCreditoElement.getAsDouble();
                        } else {
                            JOptionPane.showMessageDialog(null, "Campo 'limiteCredito' é nulo ou não é um número.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Campo 'limiteCredito' não encontrado.");
                    }

                    JOptionPane.showMessageDialog(null, "Limite disponível: R$ " + limiteCredito);

                    if (totalValue <= limiteCredito) {
                        JsonObject jsonRequest = criarVendaRequest();
                        enviarVenda(jsonRequest);
                        // Exibir o cupom fiscal
                        CupomFiscal.mostrarCupom(jsonRequest);
                    } else {
                        JOptionPane.showMessageDialog(null, "Limite de crédito insuficiente.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao consultar dados do cliente. Código de resposta: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao consultar dados do cliente: " + e.getMessage());
        }
    }

    public void finalizarVenda() {
        if (metodoPagamentoSelecionado == -1) {
            JOptionPane.showMessageDialog(null, "Selecione o método de pagamento antes de finalizar a venda.");
            return;
        }

        // Registrar o modo de pagamento antes de finalizar a venda
        registrarModoPagamento();

        JsonObject jsonRequest = new JsonObject();
        jsonRequest.addProperty("dataHoraInicio", "2024-07-26T10:00:00");
        jsonRequest.addProperty("dataHoraTermino", "2024-07-26T11:00:00");
        jsonRequest.addProperty("observacao", "Observação de teste");
        jsonRequest.addProperty("modoPagamento", metodoPagamentoSelecionado + 1); // 1 para Dinheiro, 2 para Cartão de Crédito, etc.
        jsonRequest.addProperty("pdv", 1); // Exemplo: PDV 1

        JsonArray listVendaItens = new JsonArray();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            JsonObject item = new JsonObject();
            item.addProperty("quantidade", (double) tableModel.getValueAt(i, 3));
            item.addProperty("desconto", 0.0);
            item.addProperty("acrescimo", 10.0); // Exemplo
            item.addProperty("idProduto", (int) tableModel.getValueAt(i, 0));
            listVendaItens.add(item);
        }
        jsonRequest.add("listVendaItens", listVendaItens);
        jsonRequest.addProperty("idCliente", clienteId);
        jsonRequest.addProperty("totalValue", totalValue);

        if (metodoPagamentoSelecionado == 3) { // "Crediário"
            consultarDadosCliente();
        } else {
            enviarVenda(jsonRequest);
            // Exibir o cupom fiscal
            CupomFiscal.mostrarCupom(jsonRequest);
        }
    }

    private void consultarDadosCliente() {
        try {
            URL url = new URL("http://localhost:8081/api/v1/clientes/buscar/" + clienteId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + GlobalToken);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }

                    JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                    double limiteCredito = 0.0; // Valor padrão

                    // Verifica se o campo "limiteCredito" está presente e é um número
                    if (jsonResponse.has("limiteCredito")) {
                        JsonElement limiteCreditoElement = jsonResponse.get("limiteCredito");

                        // Verifica se o elemento não é nulo e é um número
                        if (limiteCreditoElement != null && limiteCreditoElement.isJsonPrimitive() && limiteCreditoElement.getAsJsonPrimitive().isNumber()) {
                            limiteCredito = limiteCreditoElement.getAsDouble();
                        } else {
                            JOptionPane.showMessageDialog(null, "Campo 'limiteCredito' é nulo ou não é um número.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Campo 'limiteCredito' não encontrado.");
                    }

                    JOptionPane.showMessageDialog(null, "Limite disponível: R$ " + limiteCredito);

                    if (totalValue <= limiteCredito) {
                        // Prepara os dados do modo de pagamento e chama o método de envio
                        JsonObject jsonModoPagamento = criarModoPagamento();
                        enviarModoPagamento(jsonModoPagamento);

                        JsonObject jsonRequest = criarVendaRequest();
                        enviarVenda(jsonRequest);
                    } else {
                        JOptionPane.showMessageDialog(null, "Limite de crédito insuficiente.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao consultar dados do cliente. Código de resposta: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao consultar dados do cliente: " + e.getMessage());
        }
    }

    private JsonObject criarModoPagamento() {
        JsonObject jsonModoPagamento = new JsonObject();
        jsonModoPagamento.addProperty("limite", 1000.0); // Exemplo
        jsonModoPagamento.addProperty("saldo", 500.0); // Exemplo
        jsonModoPagamento.addProperty("juros", 1.5); // Exemplo
        jsonModoPagamento.addProperty("parcelas", 12); // Exemplo
        jsonModoPagamento.addProperty("dataGerada", "2024-07-28T10:00:00"); // Exemplo
        jsonModoPagamento.addProperty("descricao", "Pagamento com cartão de crédito em 12 vezes");
        JsonArray tiposPagamento = new JsonArray();
        tiposPagamento.add(1); // Exemplo, 1 para "Cartão de Crédito"
        jsonModoPagamento.add("tiposPagamento", tiposPagamento);
        return jsonModoPagamento;
    }

    private void enviarModoPagamento(JsonObject jsonModoPagamento) {
        try {
            URL url = new URL("http://localhost:8081/api/v1/modo-pagamento/create");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + GlobalToken);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonModoPagamento.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }
                    JOptionPane.showMessageDialog(null, "Modo de pagamento criado com sucesso! Resposta do servidor: " + response.toString());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao criar o modo de pagamento. Código de resposta: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao enviar o modo de pagamento: " + e.getMessage());
        }
    }

    private JsonObject criarVendaRequest() {
        JsonObject jsonRequest = new JsonObject();
        jsonRequest.addProperty("dataHoraInicio", "2024-07-26T10:00:00");
        jsonRequest.addProperty("dataHoraTermino", "2024-07-26T11:00:00");
        jsonRequest.addProperty("observacao", "Observação de teste");
        jsonRequest.addProperty("modoPagamento", metodoPagamentoSelecionado + 1); // 1 para Dinheiro, 2 para Cartão de Crédito, etc.
        jsonRequest.addProperty("pdv", 1); // Exemplo: PDV 1

        JsonArray listVendaItens = new JsonArray();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            JsonObject item = new JsonObject();
            item.addProperty("quantidade", (double) tableModel.getValueAt(i, 3));
            item.addProperty("desconto", 0.0);
            item.addProperty("acrescimo", 10.0); // Exemplo
            item.addProperty("idProduto", (int) tableModel.getValueAt(i, 0));
            listVendaItens.add(item);
        }
        jsonRequest.add("listVendaItens", listVendaItens);
        jsonRequest.addProperty("idCliente", clienteId);

        return jsonRequest;
    }

    private void enviarVenda(JsonObject jsonRequest) {
        try {
            URL url = new URL("http://localhost:8081/api/v1/vendas/create");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + GlobalToken);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonRequest.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }
                    JOptionPane.showMessageDialog(null, "Venda finalizada com sucesso! Resposta do servidor: " + response.toString());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao finalizar a venda. Código de resposta: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao enviar a venda: " + e.getMessage());
        }
    }

    private void registrarModoPagamento() {
        try {
            URL url = new URL("http://localhost:8081/api/v1/modo-pagamento/create");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + GlobalToken);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            JsonObject jsonRequest = new JsonObject();
            jsonRequest.addProperty("limite", 1000.0); // Adapte conforme necessário
            jsonRequest.addProperty("saldo", 500.0); // Adapte conforme necessário
            jsonRequest.addProperty("juros", 1.5); // Adapte conforme necessário
            jsonRequest.addProperty("parcelas", 12); // Adapte conforme necessário
            jsonRequest.addProperty("dataGerada", "2024-07-28T10:00:00"); // Adapte conforme necessário

            JsonArray tiposPagamento = new JsonArray();
            tiposPagamento.add(metodoPagamentoSelecionado + 1); // Adapte conforme necessário
            jsonRequest.add("tiposPagamento", tiposPagamento);

            jsonRequest.addProperty("descricao", "Descrição do pagamento");

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonRequest.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }
                    System.out.println("Modo de pagamento registrado: " + response.toString());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao registrar o modo de pagamento. Código de resposta: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao registrar o modo de pagamento: " + e.getMessage());
        }
    }

    private void pagarDinheiro() {
        // Solicita o valor pago pelo cliente
        String valorPagoStr = JOptionPane.showInputDialog("Digite o valor pago pelo cliente:");
        try {
            double valorPago = Double.parseDouble(valorPagoStr);
            if (valorPago < totalValue) {
                JOptionPane.showMessageDialog(null, "O valor pago é menor que o total da venda.");
                return;
            }
            double troco = valorPago - totalValue;
            JOptionPane.showMessageDialog(null, "Troco: R$ " + String.format("%.2f", troco));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor inválido!");
        }
    }

    private boolean verificarSenhaFiscal(String senha) {
        return "senha123".equals(senha);
    }

    static void limparTela() {
        JFrame frame = new JFrame();
        frame.getContentPane().removeAll();
        frame.repaint();
    }
//    public static void main(String[] args) {;
//        SwingUtilities.invokeLater(PDVService::new);
//    }
}
