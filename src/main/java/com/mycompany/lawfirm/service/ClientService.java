package com.mycompany.lawfirm.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mycompany.lawfirm.model.Client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class ClientService {

    private static final String BASE_URL = "http://localhost:8080/clients";

    private final ObjectMapper mapper;

    public ClientService() {
        mapper = new ObjectMapper();
        // ✅ Hỗ trợ Java 8 LocalDate / LocalDateTime
        mapper.registerModule(new JavaTimeModule());
        // ✅ Không serialize LocalDate dưới dạng timestamp
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // ✅ Bỏ qua các field lạ trả về từ backend (tránh lỗi)
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // ✅ GET tất cả client
    public List<Client> getAll() throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        return mapper.readValue(conn.getInputStream(), new TypeReference<List<Client>>() {});
    }

    // ✅ POST tạo mới client
    public Client create(Client client) throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        mapper.writeValue(conn.getOutputStream(), client);
        return mapper.readValue(conn.getInputStream(), Client.class);
    }

    // ✅ PUT cập nhật client
    public Client update(Long id, Client client) throws IOException {
        URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        mapper.writeValue(conn.getOutputStream(), client);
        return mapper.readValue(conn.getInputStream(), Client.class);
    }

    // ✅ DELETE client
    public void delete(Long id) throws IOException {
        URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.getInputStream().close();
    }
}
