package CoreMasterMarket.service;

import javax.swing.*;
import java.awt.*;
import static CoreMasterMarket.config.ConfigReal.GlobalToken;
import static CoreMasterMarket.config.ConfigReal.urlAPI;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProdutoService extends javax.swing.JInternalFrame {

    private JTextField txtCodigoBarras;
    private JTextField txtDescricao;
    private JTextField txtPrecoMin;
    private JTextField txtPrecoMax;
    private JTable tabelaProdutos;
    private DefaultTableModel tableModel;
    private JButton btnDeletarProduto;
    private JButton btnEditarProduto;

    public ProdutoService() {
        super("Consulta de Produtos", true, true, true, true);
        setSize(900, 600);
        setLayout(new BorderLayout());

        // Painel de filtros de busca
        JPanel filterPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filtros de Busca"));

        txtCodigoBarras = new JTextField();
        txtDescricao = new JTextField();
        txtPrecoMin = new JTextField();
        txtPrecoMax = new JTextField();

        filterPanel.add(new JLabel("Código de Barras:"));
        filterPanel.add(txtCodigoBarras);
        filterPanel.add(new JLabel("Descrição:"));
        filterPanel.add(txtDescricao);
        filterPanel.add(new JLabel("Preço Mínimo:"));
        filterPanel.add(txtPrecoMin);
        filterPanel.add(new JLabel("Preço Máximo:"));
        filterPanel.add(txtPrecoMax);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProdutos();
            }
        });

        filterPanel.add(btnBuscar);

        // Inicializa a tabela de produtos
        String[] colunas = {"ID", "Código de Barras", "Descrição", "Preço de Venda", "Tipo de Embalagem", "Quantidade", "Marca", "Ativo"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaProdutos = new JTable(tableModel);
        tabelaProdutos.setFillsViewportHeight(true);
        tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Adiciona um listener para habilitar/desabilitar os botões de deletar e editar
        tabelaProdutos.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                boolean rowSelected = tabelaProdutos.getSelectedRow() != -1;
                btnDeletarProduto.setEnabled(rowSelected);
                btnEditarProduto.setEnabled(rowSelected);
            }
        });

        // Painel central para a tabela de produtos
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(tabelaProdutos), BorderLayout.CENTER);

        // Botão de deletar produto
        btnDeletarProduto = new JButton("Deletar Produto");
        btnDeletarProduto.setEnabled(false);
        btnDeletarProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarProdutoSelecionado();
            }
        });

        // Botão de editar produto
        btnEditarProduto = new JButton("Editar Produto");
        btnEditarProduto.setEnabled(false);
        btnEditarProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarProdutoSelecionado();
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnDeletarProduto);
        bottomPanel.add(btnEditarProduto);

        add(filterPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void buscarProdutos() {
        String codigoBarras = txtCodigoBarras.getText();
        String descricao = txtDescricao.getText();
        String precoMin = txtPrecoMin.getText();
        String precoMax = txtPrecoMax.getText();

        String url = urlAPI + "/api/v1/produtos/list";

        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + GlobalToken);
            connection.setRequestProperty("Content-Type", "application/json");

            JSONObject json = new JSONObject();
            json.put("page", 1);
            json.put("size", 100);
            json.put("sortname", "id");
            json.put("sortorder", "asc");

            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            os.write(json.toString().getBytes());
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray produtosArray = jsonResponse.getJSONArray("content");
                carregarProdutos(produtosArray);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao buscar produtos: Código de resposta " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void carregarProdutos(JSONArray produtosArray) {
        tableModel.setRowCount(0);

        for (int i = 0; i < produtosArray.length(); i++) {
            JSONObject produto = produtosArray.getJSONObject(i);

            Object[] row = {
                produto.getLong("id"),
                produto.getString("codigoBarras"),
                produto.getString("descricao"),
                produto.getDouble("precoVenda"),
                produto.getString("tipoEmbalagem"),
                produto.getDouble("quantidade"),
                produto.getJSONObject("marca").getString("nome"),
                produto.getBoolean("ativo")
            };
            tableModel.addRow(row);
        }
    }

    private void deletarProdutoSelecionado() {
        int selectedRow = tabelaProdutos.getSelectedRow();
        if (selectedRow != -1) {
            long produtoId = (long) tabelaProdutos.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar o produto ID: " + produtoId + "?", "Confirmar Deleção", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                deletarProduto(produtoId);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para deletar.");
        }
    }

    private void deletarProduto(long produtoId) {
        String url = urlAPI + "/api/v1/produtos/delete?id=" + produtoId;

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

                tableModel.removeRow(tabelaProdutos.getSelectedRow());
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao deletar produto: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void editarProdutoSelecionado() {
        int selectedRow = tabelaProdutos.getSelectedRow();
        if (selectedRow != -1) {
            long produtoId = (long) tabelaProdutos.getValueAt(selectedRow, 0);
            String descricao = (String) tabelaProdutos.getValueAt(selectedRow, 2);
            double precoVenda = (double) tabelaProdutos.getValueAt(selectedRow, 3);

            String novaDescricao = JOptionPane.showInputDialog(this, "Nova Descrição:", descricao);
            if (novaDescricao != null) {
                String precoVendaStr = JOptionPane.showInputDialog(this, "Novo Preço de Venda:", precoVenda);
                if (precoVendaStr != null) {
                    atualizarProduto(produtoId, novaDescricao, Double.parseDouble(precoVendaStr));
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para editar.");
        }
    }

    private void atualizarProduto(long produtoId, String descricao, double precoVenda) {
        String url = urlAPI + "/api/v1/produtos/update";

        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", "Bearer " + GlobalToken);
            connection.setRequestProperty("Content-Type", "application/json");

            JSONObject json = new JSONObject();
            json.put("id", produtoId);
            json.put("descricao", descricao);
            json.put("precoVenda", precoVenda);

            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            os.write(json.toString().getBytes());
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                JOptionPane.showMessageDialog(this, jsonResponse.getString("message"));

                int rowIndex = tabelaProdutos.getSelectedRow();
                tabelaProdutos.setValueAt(descricao, rowIndex, 2);
                tabelaProdutos.setValueAt(precoVenda, rowIndex, 3);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar produto: Código de resposta " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }
}
