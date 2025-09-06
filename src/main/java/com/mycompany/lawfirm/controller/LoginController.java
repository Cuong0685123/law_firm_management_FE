package com.mycompany.lawfirm.controller;

import com.mycompany.lawfirm.model.Staff;
import com.mycompany.lawfirm.service.AuthService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
private void handleLogin(ActionEvent event) {
    String username = usernameField.getText();
    String password = passwordField.getText();

    if (username.isEmpty() || password.isEmpty()) {
        messageLabel.setText("Vui lòng nhập đầy đủ thông tin");
        return;
    }

    try {
        Staff staff = AuthService.login(username, password);

        if (staff != null) {
            // Chuyển sang MainView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
            Scene scene = new Scene(loader.load());

            // Gửi username sang MainController
            MainController mainCtrl = loader.getController();
            mainCtrl.setUsername(staff.getUsername());

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
        } else {
            messageLabel.setText("Đăng nhập thất bại!");
        }

    } catch (Exception e) {
        String errMsg = e.getMessage();
        if (errMsg != null && errMsg.contains("Unrecognized token")) {
            messageLabel.setText("Sai tài khoản hoặc mật khẩu");
        } else {
            messageLabel.setText("Đăng nhập thất bại: " + errMsg);
        }
    }
}


    @FXML
    private void goToRegister(ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/view/RegisterView.fxml"));
            javafx.scene.Scene scene = new javafx.scene.Scene(loader.load());
            javafx.stage.Stage stage = (javafx.stage.Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
