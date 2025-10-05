package com.mycompany.lawfirm.controller;

import com.mycompany.lawfirm.service.AuthService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterController {
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            boolean success = AuthService.register(
                    usernameField.getText(),
                   
                    passwordField.getText()
            );
            if (success) {
                messageLabel.setText("Đăng ký thành công, vui lòng đăng nhập.");
            } else {
                messageLabel.setText("Đăng ký thất bại.");
            }
        } catch (Exception e) {
            messageLabel.setText("Lỗi: " + e.getMessage());
        }
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/view/LoginView.fxml"));
            javafx.scene.Scene scene = new javafx.scene.Scene(loader.load());
            javafx.stage.Stage stage = (javafx.stage.Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
