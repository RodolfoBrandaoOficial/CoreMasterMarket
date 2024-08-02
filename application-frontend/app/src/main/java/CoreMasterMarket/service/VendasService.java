package CoreMasterMarket.service;

import javax.swing.*;
import java.awt.*;
import static CoreMasterMarket.config.ConfigReal.GlobalToken;
import static CoreMasterMarket.config.ConfigReal.token;
import static CoreMasterMarket.config.ConfigReal.urlAPI;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author rodolfobrandao
 */


public class VendasService extends javax.swing.JInternalFrame {

    private JTextField txtVendaId;
    private JTextField txtClienteId;
    private JTextField txtDataInicio;
    private JTextField txtDataFim;
    private JTable tabelaVendas;
    private DefaultTableModel tableModel;
    private JButton btnDeletarVenda;
    private JButton btnEditarVenda;

    public VendasService() {
        super("Consulta de Vendas", true, true, true, true);
        setSize(900, 600);
        setLayout(new BorderLayout());

        // Painel de filtros de busca
        JPanel filterPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filtros de Busca"));

        txtVendaId = new JTextField();
        txtClienteId = new JTextField();
        txtDataInicio = new JTextField();
        txtDataFim = new JTextField();

        filterPanel.add(new JLabel("ID da Venda:"));
        filterPanel.add(txtVendaId);
        filterPanel.add(new JLabel("ID do Cliente:"));
        filterPanel.add(txtClienteId);
        filterPanel.add(new JLabel("Data Início (yyyy-MM-dd):"));
        filterPanel.add(txtDataInicio);
        filterPanel.add(new JLabel("Data Fim (yyyy-MM-dd):"));
        filterPanel.add(txtDataFim);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarVendas();
            }
        });

        filterPanel.add(btnBuscar);

        // Inicializa a tabela de vendas
        String[] colunas = {"ID", "Data Início", "Data Fim", "Observação", "Modo de Pagamento", "Cliente", "CPF/CNPJ"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaVendas = new JTable(tableModel);
        tabelaVendas.setFillsViewportHeight(true);
        tabelaVendas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Adiciona um listener para habilitar/desabilitar os botões de deletar e editar
        tabelaVendas.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                boolean rowSelected = tabelaVendas.getSelectedRow() != -1;
                btnDeletarVenda.setEnabled(rowSelected);
                btnEditarVenda.setEnabled(rowSelected);
            }
        });

        // Painel central para a tabela de vendas
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(tabelaVendas), BorderLayout.CENTER);

        // Botão de deletar venda
        btnDeletarVenda = new JButton("Deletar Venda");
        btnDeletarVenda.setEnabled(false);
        btnDeletarVenda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarVendaSelecionada();
            }
        });

        // Botão de editar venda
        btnEditarVenda = new JButton("Editar Venda");
        btnEditarVenda.setEnabled(false);
        btnEditarVenda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarVendaSelecionada();
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnDeletarVenda);
        bottomPanel.add(btnEditarVenda);

        add(filterPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

   private void buscarVendas() {
    String vendaId = txtVendaId.getText();
    String clienteId = txtClienteId.getText();
    String dataInicio = txtDataInicio.getText();
    String dataFim = txtDataFim.getText();
    
       System.err.println("TOKEN: " + token + "TOKENGLOBAL: " + GlobalToken);

    String url = urlAPI + "/api/v1/vendas/listar";

    try {
        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + GlobalToken);
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONArray vendasArray = new JSONArray(response.toString());
            carregarVendas(vendasArray, vendaId, clienteId, dataInicio, dataFim);
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao buscar vendas: Código de resposta " + responseCode);
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
    }
}

private void carregarVendas(JSONArray vendasArray, String vendaId, String clienteId, String dataInicio, String dataFim) {
    tableModel.setRowCount(0);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    for (int i = 0; i < vendasArray.length(); i++) {
        JSONObject venda = vendasArray.getJSONObject(i);

        boolean matches = true;

        if (!vendaId.isEmpty() && venda.getLong("id") != Long.parseLong(vendaId)) {
            matches = false;
        }

        if (!clienteId.isEmpty() && venda.getJSONObject("cliente").getLong("id") != Long.parseLong(clienteId)) {
            matches = false;
        }

        if (!dataInicio.isEmpty() || !dataFim.isEmpty()) {
            try {
                Date dataInicioVenda = sdf.parse(dataInicio);
                Date dataFimVenda = sdf.parse(dataFim);

                JSONArray dataHoraInicioArray = venda.getJSONArray("dataHoraInicio");
                Date dataVendaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(
                        dataHoraInicioArray.getInt(0) + "-" +
                        (dataHoraInicioArray.getInt(1) + 1) + "-" +
                        dataHoraInicioArray.getInt(2)
                );

                JSONArray dataHoraTerminoArray = venda.getJSONArray("dataHoraTermino");
                Date dataVendaFim = new SimpleDateFormat("yyyy-MM-dd").parse(
                        dataHoraTerminoArray.getInt(0) + "-" +
                        (dataHoraTerminoArray.getInt(1) + 1) + "-" +
                        dataHoraTerminoArray.getInt(2)
                );

                if (dataVendaInicio.before(dataInicioVenda) || dataVendaFim.after(dataFimVenda)) {
                    matches = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao processar datas: " + e.getMessage());
                return;
            }
        }

        if (matches) {
            Object[] row = {
                venda.getLong("id"),
                sdf.format(new Date(venda.getJSONArray("dataHoraInicio").getInt(0) - 1900,
                        venda.getJSONArray("dataHoraInicio").getInt(1) - 1,
                        venda.getJSONArray("dataHoraInicio").getInt(2))),
                sdf.format(new Date(venda.getJSONArray("dataHoraTermino").getInt(0) - 1900,
                        venda.getJSONArray("dataHoraTermino").getInt(1) - 1,
                        venda.getJSONArray("dataHoraTermino").getInt(2))),
                venda.getString("observacao"),
                venda.getString("modoPagamento"),
                venda.getJSONObject("cliente").getString("nome"),
                venda.getJSONObject("cliente").getString("cpfCnpj")
            };
            tableModel.addRow(row);
        }
    }
}


    private void deletarVendaSelecionada() {
        int selectedRow = tabelaVendas.getSelectedRow();
        if (selectedRow != -1) {
            long vendaId = (long) tabelaVendas.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar a venda ID: " + vendaId + "?", "Confirmar Deleção", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                deletarVenda(vendaId);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma venda para deletar.");
        }
    }

    private void deletarVenda(long vendaId) {
        String url = urlAPI + "/api/v1/vendas/delete?id=" + vendaId;

        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Authorization", "Bearer " + GlobalToken);
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                JOptionPane.showMessageDialog(this, jsonResponse.getString("message"));

                tableModel.removeRow(tabelaVendas.getSelectedRow());
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao deletar venda: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void editarVendaSelecionada() {
        int selectedRow = tabelaVendas.getSelectedRow();
        if (selectedRow != -1) {
            long vendaId = (long) tabelaVendas.getValueAt(selectedRow, 0);
            String observacao = (String) tabelaVendas.getValueAt(selectedRow, 3);
            String modoPagamento = (String) tabelaVendas.getValueAt(selectedRow, 4);

            String novaObservacao = JOptionPane.showInputDialog(this, "Nova Observação:", observacao);
            if (novaObservacao != null) {
                atualizarVenda(vendaId, novaObservacao, modoPagamento);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma venda para editar.");
        }
    }

    private void atualizarVenda(long vendaId, String observacao, String modoPagamento) {
        String url = urlAPI + "/api/v1/vendas/update";

        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", "Bearer " + GlobalToken);
            connection.setRequestProperty("Content-Type", "application/json");

            JSONObject vendaJson = new JSONObject();
            vendaJson.put("id", vendaId);
            vendaJson.put("observacao", observacao);
            vendaJson.put("modoPagamentos", modoPagamento);

            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            os.write(vendaJson.toString().getBytes());
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                JOptionPane.showMessageDialog(this, "Venda atualizada com sucesso!");

                int selectedRow = tabelaVendas.getSelectedRow();
                tableModel.setValueAt(observacao, selectedRow, 3);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar venda: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sistema de Vendas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.add(new VendasService());
        frame.setVisible(true);
    }
}
