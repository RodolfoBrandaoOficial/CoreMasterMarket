package CoreMasterMarket.service;

import static CoreMasterMarket.config.ConfigReal.urlAPI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ClientesService extends javax.swing.JInternalFrame {

    private JTable tableClientes;
    private JButton btnListar;
    private JButton btnHabilitar;
    private JButton btnCriar;
    private JButton btnAtualizar;
    private JTextArea textAreaResponse;
    private static final String BASE_URL = urlAPI + "/api/v1/clientes";
    private static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb2RvbGZvYnJhbmRhbyIsImlzcyI6ImF1dGgtYXBpIiwiZXhwIjoxNzIyNjk2NDExLCJ1c2VySWQiOiJkNjY1NWUwZC1hOTIxLTQ2ZTMtYjM2Ny04NzZmMDA0NjdkYWQifQ.g1QCQBNmJ3sB-ouJfjjZqMrdQuGpFYlYNLJb1j6jtAE";

    public ClientesService() {
        initComponents();
        setTitle("Gerenciamento de Clientes");
        setPreferredSize(new Dimension(920, 600)); // Define um tamanho preferido
        pack();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Tabela de clientes
        tableClientes = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableClientes);
        add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(1, 4, 10, 10)); // Organiza os botões em uma linha com espaçamento
        btnListar = new JButton("Listar Clientes");
        btnHabilitar = new JButton("Habilitar Cliente");
        btnCriar = new JButton("Criar Cliente");
        btnAtualizar = new JButton("Atualizar Cliente");
        panelButtons.add(btnListar);
        panelButtons.add(btnHabilitar);
        panelButtons.add(btnCriar);
        panelButtons.add(btnAtualizar);
        add(panelButtons, BorderLayout.SOUTH);

        // Área de resposta
        textAreaResponse = new JTextArea(10, 50);
        textAreaResponse.setEditable(false);
        JScrollPane responseScrollPane = new JScrollPane(textAreaResponse);
        add(responseScrollPane, BorderLayout.EAST);

        // Adiciona ação aos botões
        btnListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarClientes();
            }
        });

        btnHabilitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                habilitarCliente();
            }
        });

        btnCriar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarCliente();
            }
        });

        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarCliente();
            }
        });

        // Permite arrastar colunas na tabela
        tableClientes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableClientes.setAutoCreateRowSorter(true); // Permite ordenar por colunas
    }

    private void listarClientes() {
        String url = BASE_URL + "/listar";
        String response = sendGetRequest(url);
        if (response != null) {
            JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
            JsonArray content = jsonResponse.getAsJsonArray("content");
            List<String[]> data = new ArrayList<>();
            for (int i = 0; i < content.size(); i++) {
                JsonObject cliente = content.get(i).getAsJsonObject();
                String[] row = {
                    cliente.get("id").getAsString(),
                    cliente.get("nome").getAsString(),
                    cliente.get("dataNascimento").toString(),
                    cliente.get("rua").getAsString(),
                    cliente.get("bairro").getAsString(),
                    cliente.get("numero").getAsString(),
                    cliente.get("cpfCnpj").getAsString(),
                    cliente.get("rgIe").getAsString(),
                    cliente.get("telefone1").getAsString(),
                    cliente.get("telefone2").getAsString(),
                    cliente.get("emitirNota").getAsString(),
                    cliente.get("email").getAsString(),
                    cliente.get("cep").getAsString(),
                    cliente.get("cidade").getAsString(),
                    cliente.get("estado").getAsString(),
                    cliente.get("dataCadastro").toString(),
                    cliente.get("dataAlteracao").toString(),
                    cliente.get("ativo").getAsString(),
                    cliente.get("observacao").getAsString(),
                    cliente.get("limiteCredito").getAsString(),
                    cliente.get("dataPagamento").toString(),
                    cliente.get("dataVencimento").toString(),
                    cliente.get("dataFechamentoFatura").toString()
                };
                data.add(row);
            }

            String[] columns = {
                "ID", "Nome", "Data Nascimento", "Rua", "Bairro", "Número", "CPF/CNPJ", "RG/IE", "Telefone 1",
                "Telefone 2", "Emitir Nota", "Email", "CEP", "Cidade", "Estado", "Data Cadastro", "Data Alteração",
                "Ativo", "Observação", "Limite Crédito", "Data Pagamento", "Data Vencimento", "Data Fechamento Fatura"
            };
            DefaultTableModel model = new DefaultTableModel(data.toArray(new Object[][]{}), columns);
            tableClientes.setModel(model);
        }
    }

    private void habilitarCliente() {
        String id = JOptionPane.showInputDialog("Digite o ID do cliente a ser habilitado:");
        String url = BASE_URL + "/enabled";
        JsonObject jsonRequest = new JsonObject();
        jsonRequest.addProperty("id", id);
        String response = sendPutRequest(url, jsonRequest.toString());
        if (response != null) {
            textAreaResponse.setText(response);
        }
    }

    private void criarCliente() {
        JsonObject jsonRequest = new JsonObject();
        jsonRequest.addProperty("nome", "João da Silva");
        jsonRequest.addProperty("dataNascimento", "1990-05-15");
        jsonRequest.addProperty("rua", "Rua Exemplo");
        jsonRequest.addProperty("bairro", "Bairro Exemplo");
        jsonRequest.addProperty("numero", "123");
        jsonRequest.addProperty("cpfCnpj", "123.456.789-00");
        jsonRequest.addProperty("rgIe", "12.345.678-9");
        jsonRequest.addProperty("telefone1", "(11) 98765-4321");
        jsonRequest.addProperty("telefone2", "(11) 12345-6789");
        jsonRequest.addProperty("emitirNota", true);
        jsonRequest.addProperty("email", "joao.silva@example.com");
        jsonRequest.addProperty("cep", "01234-567");
        jsonRequest.addProperty("cidade", "São Paulo");
        jsonRequest.addProperty("estado", "SP");
        jsonRequest.addProperty("dataAlteracao", "2024-07-23T14:30:00");
        jsonRequest.addProperty("dataExclusao", "2024-07-23T14:30:00");
        jsonRequest.addProperty("ativo", true);
        jsonRequest.addProperty("observacao", "Cliente VIP");
        jsonRequest.addProperty("limiteCredito", 5000);
        jsonRequest.addProperty("dataPagamento", "2024-07-23T14:30:00");
        jsonRequest.addProperty("dataVencimento", "2024-08-23T14:30:00");
        jsonRequest.addProperty("dataFechamentoFatura", "2024-08-23T14:30:00");

        String url = BASE_URL + "/create";
        String response = sendPostRequest(url, jsonRequest.toString());
        if (response != null) {
            textAreaResponse.setText(response);
        }
    }

    private void atualizarCliente() {
        JsonObject jsonRequest = new JsonObject();
        jsonRequest.addProperty("id", 1);
        jsonRequest.addProperty("nome", "João da Silva");
        jsonRequest.addProperty("dataNascimento", "1990-05-15");
        jsonRequest.addProperty("rua", "Rua Exemplo");
        jsonRequest.addProperty("bairro", "Bairro Exemplo");
        jsonRequest.addProperty("numero", "123");
        jsonRequest.addProperty("cpfCnpj", "123.456.789-00");
        jsonRequest.addProperty("rgIe", "12.345.678-9");
        jsonRequest.addProperty("telefone1", "(11) 98765-4321");
        jsonRequest.addProperty("telefone2", "(11) 12345-6789");
        jsonRequest.addProperty("emitirNota", true);
        jsonRequest.addProperty("email", "joao.silva@example.com");
        jsonRequest.addProperty("cep", "01234-567");
        jsonRequest.addProperty("cidade", "São Paulo");
        jsonRequest.addProperty("estado", "SP");
        jsonRequest.addProperty("dataAlteracao", "2024-07-23T14:30:00");
        jsonRequest.addProperty("dataExclusao", "");
        jsonRequest.addProperty("ativo", true);
        jsonRequest.addProperty("observacao", "Cliente VIP");
        jsonRequest.addProperty("limiteCredito", 5000);
        jsonRequest.addProperty("dataPagamento", "2024-07-23T14:30:00");
        jsonRequest.addProperty("dataVencimento", "2024-08-23T14:30:00");
        jsonRequest.addProperty("dataFechamentoFatura", "2024-08-23T14:30:00");

        String url = BASE_URL + "/update";
        String response = sendPutRequest(url, jsonRequest.toString());
        if (response != null) {
            textAreaResponse.setText(response);
        }
    }

    private String sendGetRequest(String urlString) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + TOKEN);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                byte[] responseBytes = connection.getInputStream().readAllBytes();
                return new String(responseBytes, StandardCharsets.UTF_8);
            } else {
                return "GET request failed. Response Code: " + responseCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String sendPostRequest(String urlString, String jsonInputString) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + TOKEN);
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            connection.getOutputStream().write(input, 0, input.length);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                byte[] responseBytes = connection.getInputStream().readAllBytes();
                return new String(responseBytes, StandardCharsets.UTF_8);
            } else {
                return "POST request failed. Response Code: " + responseCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String sendPutRequest(String urlString, String jsonInputString) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", "Bearer " + TOKEN);
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            connection.getOutputStream().write(input, 0, input.length);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                byte[] responseBytes = connection.getInputStream().readAllBytes();
                return new String(responseBytes, StandardCharsets.UTF_8);
            } else {
                return "PUT request failed. Response Code: " + responseCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
