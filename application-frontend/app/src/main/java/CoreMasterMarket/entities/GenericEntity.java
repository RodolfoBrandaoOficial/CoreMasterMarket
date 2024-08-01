package CoreMasterMarket.entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class GenericEntity<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        Gson gson = new Gson();
        return gson.fromJson(json, typeOfT);
    }

    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static Map<String, Object> toMap(Object object) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        return gson.fromJson(gson.toJson(object), type);
    }
}
