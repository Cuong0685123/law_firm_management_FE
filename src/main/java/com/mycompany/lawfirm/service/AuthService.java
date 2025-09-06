package com.mycompany.lawfirm.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.lawfirm.model.Staff;
import java.net.http.*;
import java.net.URI;

public class AuthService {
    private static final String BASE_URL = "http://localhost:8080/auth";

    public static Staff login(String username, String password) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        String requestBody = String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        try {
            // Thử parse JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(body);

            if (node.has("success") && !node.get("success").asBoolean()) {
                throw new RuntimeException(node.get("message").asText());
            }

            Staff staff = new Staff();
            staff.setUsername(node.get("username").asText());
            return staff;

        } catch (Exception e) {
            // Nếu không phải JSON, backend trả plain text
            throw new RuntimeException(body);
        }
    }
    
    // Đăng ký
    public static boolean register(String username, String email, String password) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        String requestBody = String.format(
                "{\"username\":\"%s\", \"email\":\"%s\", \"password\":\"%s\"}",
                username, email, password
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == 200;
    }
}
