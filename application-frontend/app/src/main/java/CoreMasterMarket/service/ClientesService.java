package CoreMasterMarket.service;

import static CoreMasterMarket.config.ConfigReal.GlobalToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.Iterator;
import okhttp3.*;

public class ClientesService extends javax.swing.JInternalFrame {

    private static final String API_URL = "http://localhost:8081/api/v1/clientes";
    private static final OkHttpClient client = new OkHttpClient();
    private String authToken = GlobalToken;

    private JTextField searchField;
    private JTable clienteTable;
    private DefaultTableModel tableModel;

    public ClientesService(String authToken) {
        this.authToken = authToken;
        initComponents();
        fetchClientes();
    }

    private void initComponents() {
        setTitle("Gestão de Clientes");
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Buscar");
        JButton addButton = new JButton("Cadastrar");
        JButton updateButton = new JButton("Atualizar");
        JButton toggleButton = new JButton("Ativar/Desativar");

        searchButton.addActionListener(e -> searchClientes());
        addButton.addActionListener(e -> openCadastroClienteDialog());
        updateButton.addActionListener(e -> openAtualizarClienteDialog());
        toggleButton.addActionListener(e -> toggleClienteAtivo());

        topPanel.add(new JLabel("Buscar:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(addButton);
        topPanel.add(updateButton);
        topPanel.add(toggleButton);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{
            "ID", "Nome", "Data Nascimento", "Rua", "Bairro", "Número", "CPF/CNPJ", "RG/IE",
            "Telefone 1", "Telefone 2", "Emitir Nota", "Email", "CEP", "Cidade", "Estado",
            "Data Cadastro", "Data Alteração", "Ativo", "Observação", "Limite Crédito",
            "Data Pagamento", "Data Vencimento", "Data Fechamento Fatura"
        }, 0);

        clienteTable = new JTable(tableModel);
        add(new JScrollPane(clienteTable), BorderLayout.CENTER);
    }

    private void fetchClientes() {
        Request request = new Request.Builder()
            .url(API_URL + "/listar")
            .addHeader("Authorization", "Bearer " + authToken)
            .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> 
                    JOptionPane.showMessageDialog(null, "Erro ao buscar clientes.")
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    SwingUtilities.invokeLater(() -> updateTableData(jsonResponse));
                } else {
                    SwingUtilities.invokeLater(() -> 
                        JOptionPane.showMessageDialog(null, "Erro ao buscar clientes.")
                    );
                }
            }
        });
    }

    private void updateTableData(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode contentNode = rootNode.path("content");

            tableModel.setRowCount(0); // Limpa os dados da tabela

            Iterator<JsonNode> elements = contentNode.elements();
            while (elements.hasNext()) {
                JsonNode clientNode = elements.next();
                tableModel.addRow(new Object[]{
                    clientNode.path("id").asInt(),
                    clientNode.path("nome").asText(),
                    formatData(clientNode.path("dataNascimento")),
                    clientNode.path("rua").asText(),
                    clientNode.path("bairro").asText(),
                    clientNode.path("numero").asText(),
                    clientNode.path("cpfCnpj").asText(),
                    clientNode.path("rgIe").asText(),
                    clientNode.path("telefone1").asText(),
                    clientNode.path("telefone2").asText(),
                    clientNode.path("emitirNota").asBoolean(),
                    clientNode.path("email").asText(),
                    clientNode.path("cep").asText(),
                    clientNode.path("cidade").asText(),
                    clientNode.path("estado").asText(),
                    formatDataHora(clientNode.path("dataCadastro")),
                    formatDataHora(clientNode.path("dataAlteracao")),
                    clientNode.path("ativo").asBoolean(),
                    clientNode.path("observacao").asText(),
                    clientNode.path("limiteCredito").asDouble(),
                    formatData(clientNode.path("dataPagamento")),
                    formatData(clientNode.path("dataVencimento")),
                    formatData(clientNode.path("dataFechamentoFatura"))
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao processar dados dos clientes.");
        }
    }

    private String formatData(JsonNode dataNode) {
        if (dataNode.isArray() && dataNode.size() >= 3) {
            int year = dataNode.get(0).asInt();
            int month = dataNode.get(1).asInt();
            int day = dataNode.get(2).asInt();
            return String.format("%04d-%02d-%02d", year, month, day);
        }
        return "";
    }

    private String formatDataHora(JsonNode dataNode) {
        if (dataNode.isArray() && dataNode.size() >= 5) {
            int year = dataNode.get(0).asInt();
            int month = dataNode.get(1).asInt();
            int day = dataNode.get(2).asInt();
            int hour = dataNode.get(3).asInt();
            int minute = dataNode.get(4).asInt();
            return String.format("%04d-%02d-%02d %02d:%02d", year, month, day, hour, minute);
        }
        return "";
    }

    private void searchClientes() {
        String searchQuery = searchField.getText();
        // Implement search logic based on searchQuery
    }

    private void openCadastroClienteDialog() {
        // Implement the logic to open a dialog for creating a new customer
    }

    private void openAtualizarClienteDialog() {
        int selectedRow = clienteTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para atualizar.");
            return;
        }
        // Implement the logic to open a dialog for updating the selected customer
    }

    private void toggleClienteAtivo() {
        int selectedRow = clienteTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para ativar/desativar.");
            return;
        }
        int clientId = (int) tableModel.getValueAt(selectedRow, 0);
        toggleClienteAtivo(clientId);
    }

    private void toggleClienteAtivo(int clientId) {
        String url = API_URL + "/enabled";
        String json = "{\"id\":" + clientId + "}";

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
            .url(url)
            .put(body)
            .addHeader("Authorization", "Bearer " + authToken)
            .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> 
                    JOptionPane.showMessageDialog(null, "Erro ao ativar/desativar cliente.")
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    SwingUtilities.invokeLater(() -> fetchClientes());
                } else {
                    SwingUtilities.invokeLater(() -> 
                        JOptionPane.showMessageDialog(null, "Erro ao ativar/desativar cliente.")
                    );
                }
            }
        });
    }
}
