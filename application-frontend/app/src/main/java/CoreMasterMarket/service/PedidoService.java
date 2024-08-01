package CoreMasterMarket.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.awt.GridLayout;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PedidoService {

    private static final String API_URL = "http://localhost:8081/api/v1/pedidos/list";
    private static final String ADD_API_URL = "http://localhost:8081/api/v1/pedidos/add";
    private static final String UPDATE_API_URL = "http://localhost:8081/api/v1/pedidos/update";
    private static final String DELETE_API_URL = "http://localhost:8081/api/v1/pedidos/delete";
    private static final String AUTH_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb2RvbGZvYnJhbmRhbyIsImlzcyI6ImF1dGgtYXBpIiwiZXhwIjoxNzIyNDQ4MzI5LCJ1c2VySWQiOiJkNjY1NWUwZC1hOTIxLTQ2ZTMtYjM2Ny04NzZmMDA0NjdkYWQifQ.-h-7c1XYxCzBl6z5tq-yxB99JQKe0UtmbadACp9wp3w";
    private static final String REQUEST_BODY = "{\"page\":1,\"size\":100,\"sortname\":\"id\",\"sortorder\":\"asc\"}";

    private List<Map<String, Object>> data = new ArrayList<>();
    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();
    private DefaultListModel<String> model;

    public PedidoService(DefaultListModel<String> model) {
        this.model = model;
    }

    public void loadDataFromAPI() {
        RequestBody body = RequestBody.create(REQUEST_BODY, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", AUTH_TOKEN)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                showMessage("Erro ao carregar dados: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    showMessage("Erro ao carregar dados: " + response.message());
                    return;
                }

                String responseBody = response.body().string();
                List<Map<String, Object>> result = parseJSON(responseBody);
                SwingUtilities.invokeLater(() -> {
                    model.clear();
                    data = result;
                    for (Map<String, Object> item : result) {
                        model.addElement(item.get("nome").toString());
                    }
                });
            }
        });
    }

    private List<Map<String, Object>> parseJSON(String responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONArray contentArray = jsonObject.getJSONArray("content");

            Type listType = new TypeToken<List<Map<String, Object>>>() {}.getType();
            return gson.fromJson(contentArray.toString(), listType);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void editItemAt(int index) {
        if (index < 0 || index >= data.size()) return;

        Map<String, Object> item = data.get(index);
        JPanel panel = new JPanel(new GridLayout(item.size(), 2));
        List<JTextField> fields = new ArrayList<>();

        item.forEach((key, value) -> {
            panel.add(new JLabel(key + ":"));
            JTextField field = new JTextField(String.valueOf(value));
            panel.add(field);
            fields.add(field);
        });

        int result = JOptionPane.showConfirmDialog(null, panel, "Edit Item", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int i = 0;
            for (String key : item.keySet()) {
                item.put(key, fields.get(i).getText());
                i++;
            }
            updateItem(item);
        }
    }

    public void addItem() {
        Map<String, Object> newItem = new java.util.HashMap<>();
        JPanel panel = new JPanel(new GridLayout(newItem.size(), 2));
        List<JTextField> fields = new ArrayList<>();

        newItem.put("nome", "");
        newItem.put("email", "");
        newItem.put("telefone", "");

        newItem.forEach((key, value) -> {
            panel.add(new JLabel(key + ":"));
            JTextField field = new JTextField(String.valueOf(value));
            panel.add(field);
            fields.add(field);
        });

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Item", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int i = 0;
            for (String key : newItem.keySet()) {
                newItem.put(key, fields.get(i).getText());
                i++;
            }
            addItemToAPI(newItem);
        }
    }

    private void addItemToAPI(Map<String, Object> item) {
        String json = gson.toJson(item);

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(ADD_API_URL)
                .header("Authorization", AUTH_TOKEN)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                showMessage("Erro ao adicionar item: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    showMessage("Erro ao adicionar item: " + response.message());
                    return;
                }

                String responseBody = response.body().string();
                System.out.println("Item added: " + responseBody);
                SwingUtilities.invokeLater(() -> loadDataFromAPI());
            }
        });
    }

    private void updateItem(Map<String, Object> item) {
        String json = gson.toJson(item);

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(UPDATE_API_URL)
                .header("Authorization", AUTH_TOKEN)
                .put(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                showMessage("Erro ao atualizar item: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    showMessage("Erro ao atualizar item: " + response.message());
                    return;
                }

                String responseBody = response.body().string();
                System.out.println("Item updated: " + responseBody);
                SwingUtilities.invokeLater(() -> loadDataFromAPI());
            }
        });
    }

    public void deleteItemAt(int index) {
        if (index < 0 || index >= data.size()) return;

        Map<String, Object> item = data.get(index);
        Long id = ((Number) item.get("id")).longValue();

        if (id == null || id <= 0) {
            JOptionPane.showMessageDialog(null, "ID do pedido inválido");
            return;
        }

        HttpUrl url = HttpUrl.parse(DELETE_API_URL).newBuilder()
                .addQueryParameter("id", id.toString())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", AUTH_TOKEN)
                .delete()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                showMessage("Erro ao excluir item: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    showMessage("Erro ao excluir item: " + response.message());
                    return;
                }

                String responseBody = response.body().string();
                System.out.println("Item deleted: " + responseBody);
                SwingUtilities.invokeLater(() -> loadDataFromAPI());
            }
        });
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
