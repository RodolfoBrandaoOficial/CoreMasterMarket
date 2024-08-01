/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoreMasterMarket.gui.pdv;

/**
 *
 * @author rodolfobrandao
 */
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;

public class ProdutoList {

    private JTextField txtCodBarras;
    private List<String> produtos;

    public ProdutoList() {
        // Inicializa o JTextField
        txtCodBarras = new JTextField(20);
        produtos = new ArrayList<>();

        // Adiciona um KeyListener para buscar enquanto digita
        txtCodBarras.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = txtCodBarras.getText();
                buscarProduto(text);
            }
        });

        // Adiciona o JTextField em um JFrame para exibição
        JFrame frame = new JFrame("Lista de Produtos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(txtCodBarras);
        frame.pack();
        frame.setVisible(true);

        // Carrega os produtos
        carregarProdutos();
    }

    private void carregarProdutos() {
        try {
            // URL do endpoint de produtos
            URL url = new URL("http://localhost:8081/api/v1/produtos/list");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb2RvbGZvYnJhbmRhbyIsImlzcyI6ImF1dGgtYXBpIiwiZXhwIjoxNzIyNTM1ODI4LCJ1c2VySWQiOiJkNjY1NWUwZC1hOTIxLTQ2ZTMtYjM2Ny04NzZmMDA0NjdkYWQifQ.DHyMs5vIUaitJ0Hj5SMWnER9Ez9EFuP8Cn5uisK0N-g"); // Adicione seu token aqui
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            // Corpo da requisição
            String jsonInputString = "{\"page\":1,\"size\":10000000,\"sortname\":\"id\",\"sortorder\":\"asc\"}";
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Ler a resposta
            StringBuilder response;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
            }

            // Parsear a resposta JSON
            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonArray content = jsonResponse.getAsJsonArray("content");

            // Limpa a lista de produtos
            produtos.clear();

            // Adiciona os produtos à lista
            for (int i = 0; i < content.size(); i++) {
                JsonObject produto = content.get(i).getAsJsonObject();
                String descricao = produto.get("descricao").getAsString();
                String codigoBarras = produto.get("codigoBarras").getAsString();
                produtos.add(codigoBarras + " - " + descricao);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar produtos: " + e.getMessage());
        }
    }

    private void buscarProduto(String text) {
        StringBuilder resultados = new StringBuilder();
        for (String produto : produtos) {
            if (produto.toLowerCase().contains(text.toLowerCase())) {
                resultados.append(produto).append("\n");
            }
        }
        txtCodBarras.setText(resultados.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProdutoList());
    }
}
