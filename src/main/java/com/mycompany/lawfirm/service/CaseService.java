package com.mycompany.lawfirm.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mycompany.lawfirm.model.Case;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CaseService {
    private static final String BASE_URL = "http://localhost:8080/cases";
    private final ObjectMapper mapper;

    public CaseService() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // hỗ trợ LocalDate
    }

    // Lấy tất cả case
    public List<Case> getAll() throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        return mapper.readValue(conn.getInputStream(), new TypeReference<List<Case>>() {});
    }

    // Tạo case
    public Case create(Case c) throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        mapper.writeValue(conn.getOutputStream(), c);
        return mapper.readValue(conn.getInputStream(), Case.class);
    }

    // Update case
    public Case update(Long id, Case c) throws IOException {
        URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        mapper.writeValue(conn.getOutputStream(), c);
        return mapper.readValue(conn.getInputStream(), Case.class);
    }

    // Delete case
    public void delete(Long id) throws IOException {
        URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.getInputStream().close();
    }
}
