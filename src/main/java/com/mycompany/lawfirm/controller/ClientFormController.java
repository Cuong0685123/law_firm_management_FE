package com.mycompany.lawfirm.controller;

import com.mycompany.lawfirm.model.Client;
import com.mycompany.lawfirm.service.ClientService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class ClientFormController {

    @FXML private TextField txtFullName;
    @FXML private DatePicker dpBirthDate;
    @FXML private TextField txtIdNumber;
    @FXML private TextField txtAddress;
    @FXML private TextField txtPhone;
    @FXML private TextField txtEmail;

    private final ClientService clientService = new ClientService();

    @FXML
    private void handleSave() {
        try {
            Client client = new Client();
            client.setFullName(txtFullName.getText());
            client.setBirthDate(dpBirthDate.getValue());
            client.setIdNumber(txtIdNumber.getText());
            client.setAddress(txtAddress.getText());
            client.setPhone(txtPhone.getText());
            client.setEmail(txtEmail.getText());

            clientService.create(client); // Gọi API thêm mới
            showAlert("Thành công", "Thêm khách hàng thành công!");
            closeForm();
        } catch (IOException e) {
            showAlert("Lỗi", "Không thể lưu khách hàng: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        closeForm();
    }

    private void closeForm() {
        Stage stage = (Stage) txtFullName.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
