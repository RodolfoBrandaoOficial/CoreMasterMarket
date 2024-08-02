package CoreMasterMarket.service;

import static CoreMasterMarket.config.ConfigReal.GlobalToken;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ProdutoService  extends javax.swing.JInternalFrame {

    private static final String API_BASE_URL = "http://localhost:8081/api/v1/produtos";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN = "Bearer " + GlobalToken;
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json; charset=UTF-8";

    private static final Gson gson = new Gson();

    public JsonObject listarProdutos(int page, int size, String sortname, String sortorder) throws Exception {
        URL url = new URL(API_BASE_URL + "/list");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty(AUTHORIZATION_HEADER, BEARER_TOKEN);
        connection.setRequestProperty(CONTENT_TYPE_HEADER, CONTENT_TYPE_JSON);
        connection.setDoOutput(true);

        JsonObject requestJson = new JsonObject();
        requestJson.addProperty("page", page);
        requestJson.addProperty("size", size);
        requestJson.addProperty("sortname", sortname);
        requestJson.addProperty("sortorder", sortorder);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(requestJson.toString().getBytes("UTF-8"));
            os.flush();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                return JsonParser.parseReader(br).getAsJsonObject();
            }
        } else {
            throw new RuntimeException("Failed to list products: HTTP error code : " + responseCode);
        }
    }

    public JsonObject criarProduto(JsonObject produto) throws Exception {
        URL url = new URL(API_BASE_URL + "/create");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty(AUTHORIZATION_HEADER, BEARER_TOKEN);
        connection.setRequestProperty(CONTENT_TYPE_HEADER, CONTENT_TYPE_JSON);
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(produto.toString().getBytes("UTF-8"));
            os.flush();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == 201) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                return JsonParser.parseReader(br).getAsJsonObject();
            }
        } else {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                return JsonParser.parseReader(br).getAsJsonObject();
            }
        }
    }

    public JsonObject deletarProduto(int id) throws Exception {
        URL url = new URL(API_BASE_URL + "/delete?id=" + id);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setRequestProperty(AUTHORIZATION_HEADER, BEARER_TOKEN);

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                return JsonParser.parseReader(br).getAsJsonObject();
            }
        } else {
            throw new RuntimeException("Failed to delete product: HTTP error code : " + responseCode);
        }
    }

    public JsonObject ativarProduto(int id) throws Exception {
        URL url = new URL(API_BASE_URL + "/enabled");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty(AUTHORIZATION_HEADER, BEARER_TOKEN);
        connection.setRequestProperty(CONTENT_TYPE_HEADER, CONTENT_TYPE_JSON);
        connection.setDoOutput(true);

        JsonObject requestJson = new JsonObject();
        requestJson.addProperty("id", id);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(requestJson.toString().getBytes("UTF-8"));
            os.flush();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                return JsonParser.parseReader(br).getAsJsonObject();
            }
        } else {
            throw new RuntimeException("Failed to enable product: HTTP error code : " + responseCode);
        }
    }

    public static void main(String[] args) {
        ProdutoService service = new ProdutoService();

        try {
            JsonObject produtos = service.listarProdutos(1, 100, "id", "asc");
            System.out.println(produtos);

            JsonObject novoProduto = new JsonObject();
            novoProduto.addProperty("codigoBarras", "7891000100101");
            novoProduto.addProperty("descricao", "LEITE CONDENSADO MOCA");
            novoProduto.addProperty("precoVenda", 10);
            novoProduto.addProperty("tipoEmbalagem", "UN");
            novoProduto.addProperty("quantidade", 10);
            novoProduto.addProperty("criadoEm", "2024-07-23T14:30:00");
            novoProduto.addProperty("atualizadoEm", "2024-07-23T14:30:00");
            novoProduto.addProperty("marca", 1);
            novoProduto.addProperty("ativo", true);

            JsonObject criadoProduto = service.criarProduto(novoProduto);
            System.out.println(criadoProduto);

            JsonObject deletadoProduto = service.deletarProduto(52);
            System.out.println(deletadoProduto);

            JsonObject ativadoProduto = service.ativarProduto(52);
            System.out.println(ativadoProduto);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
